package com.eray.pbs.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.EmpAvailableDao;
import com.eray.pbs.dao.EmpTradeConfigBaseDao;
import com.eray.pbs.dao.ShiftConfigDao;
import com.eray.pbs.dao.ShiftInOutDao;
import com.eray.pbs.dao.ShiftInOutHistoryDao;
import com.eray.pbs.dao.SyncKqDataDoorDao;
import com.eray.pbs.po.EmpAvailable;
import com.eray.pbs.po.EmpTradeConfigBase;
import com.eray.pbs.po.ShiftConfig;
import com.eray.pbs.po.ShiftInOut;
import com.eray.pbs.po.ShiftInOutHistory;
import com.eray.pbs.po.SyncKqDataDoor;
import com.eray.pbs.util.SyncKqDataDoorCompare;
import com.eray.util.Global;
import com.eray.util.compare.SyncKqDataDoorCompator;
import com.eray.util.date.DateCalculation;
import com.eray.util.format.FormatUtil;

/**
 * 处理 IN&OUT 数据 2016.07.22
 * @author ganqing
 *
 */
@Component
@Transactional(readOnly = true)
public class ShiftInOutService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiftInOutService.class);
	
	@Autowired
	private ShiftInOutDao shiftInOutDao; //供用户修改的数据
	
	@Autowired
	private ShiftInOutHistoryDao shiftInOutHistoryDao; //历史数据，不允许修改
	
	@Autowired
	private SyncKqDataDoorDao syncKqDataDoorDao; //原始数据
	
	@Autowired
	private ShiftConfigDao shiftConfigDao; //排班配置DAO
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate; //查询模板
	
	@Autowired
	private EmpAvailableDao empAvailableDao; //每个人的
	
	@Autowired
	private EmpTradeConfigBaseDao empTradeConfigBaseDao; //员工与trade之间关系
	
	
	/**
	 * in & out 匹配逻辑.IN&OUT不断的有写入
	 * @return 返回所有处理日期的数据
	 */
	@Transactional(readOnly = false)
	public Set<String> handDatas() {
		Set<String> ymdSet = new TreeSet<String>(); //存放yyyyMMdd的时间
		try {
			List<SyncKqDataDoor> doors = this.getNeedHandleSyncKqDatas(); //待处理的IN&OUT数据
			logger.info("getNeedHandleSyncKqDatas data size {}", doors == null ? 0 : doors.size());
			if (doors != null & doors.size() > 0) {
				Set<String> set = new HashSet<String>(); //存放所有的员工编号
				for (SyncKqDataDoor door : doors) {
					set.add(door.getEmpID());
				}
				if (set.size() > 0) {
					Iterator<String> it = set.iterator();
					while (it.hasNext()) { //循环集合中的员工编号
						List<SyncKqDataDoor> sameEmpIddoors = new ArrayList<SyncKqDataDoor>(); //将相同的员工考勤信息存放在此（yyyyMMdd可能不一致);
						String empId = this.formatEmpid(it.next()); //考勤系统读取的数据是四位数字
						for (SyncKqDataDoor door : doors) {
							if (this.formatEmpid(door.getEmpID()).equals(empId)) {
								logger.info("The Emp about shift:{}", door); //对员工编号进行处理
								door.setEmpID(empId); //用户格式化的数据替代
								sameEmpIddoors.add(door);
							}
						}
						if (sameEmpIddoors.size() > 0) {
							//检查员工是否离职 增加过滤条件 2017-01-09 GQ,状态为3表示在职，1为离职
							StringBuffer sb = new StringBuffer();
							sb.append("SELECT COUNT(1) from pbs_employee where eid_='").append(empId).append("' and status_='3'");
							Double t = jdbcTemplate.queryForObject(sb.toString(), Double.class);
							if (t == null || t < 1) {
								this.changehandleStatus(sameEmpIddoors); //修改已经处理了的数据的状态位
								continue;
							}
							ymdSet = this.mateInAndOutMatchShift(empId, sameEmpIddoors, ymdSet);  //开始匹配数据xx
							logger.info("the {} mateInAndOut is over,shange status start.", empId);
							this.changehandleStatus(sameEmpIddoors); //修改已经处理了的数据的状态位
							sameEmpIddoors.clear();
						} else {
							logger.info("No employee match.");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("system error");
			e.printStackTrace();
		}	
		return ymdSet;
	}
	/**
	 * 格式化数据(考勤系统读取的数据只有4位)，对不足的8位的员工号,按前面加0补齐
	 * @param empId 员工编号
	 * @return 补齐的8位员工编号
	 */
	private String formatEmpid(String empId) throws Exception {
		//logger.info("the original empId:{}",empId);
		if (empId == null) { //员工编号为空，报错
			//throw new RuntimeException();
			return null;
		}
		if (empId.length() == 8) { //为合同工，直接返回;合同工以9开头，长度固定为8位
			return empId;
		}
		Long eid = 100000000 + Long.valueOf(empId); //员工编号8位长度
		return String.valueOf(eid).substring(1);
	}
	
	/**
	 * 变更已经处理的数据的状态位
	 * @param sameEmpdoors 待处理的数据
	 */
	@Transactional(readOnly = false)
	private void changehandleStatus(List<SyncKqDataDoor> sameEmpdoors) throws Exception {
		List<SyncKqDataDoor> syn = new ArrayList<SyncKqDataDoor>();
		for (SyncKqDataDoor d : sameEmpdoors ) {
			d.setHandleDate(new Date());
			d.setHandleStatus(Global.YES);
			logger.info("update status: {}" , d);
			syn.add(d);
		}
		syncKqDataDoorDao.save(syn);
		syn.clear();
	}
	/**
	 * 匹配IN & OUT数据
	 * 宕机几天没有处理，堆积大量的数据要处理,可能存在不同的YYYYMMDD;
	 * 晚上12点截断的处理：晚上先预处理夜班按时间计算出来,如果第二天的第一笔
	 * 记录是out的时间，那么需要拿该out与前一天的in进行匹配（如果in&out在同一个班次，进行调整，如果不在一个班次，按实际写入）
	 * 
	 * @param empid 员工编号
	 * @param sameEmpdoors 同一个员工所有未处理的数据,可能不是一天的
	 * @return Set<String> 返回所有的YYYYMMDD
	 */
	@Transactional(readOnly = false)
	private Set<String> mateInAndOutMatchShift(String empid, List<SyncKqDataDoor> sameEmpdoors, Set<String> ymdSet) throws Exception {
		logger.info("mateInAndOutMatchShift empId param:{}" , empid);
		Set<String> set  = new TreeSet<String>(); //存放年月日信息
		for (SyncKqDataDoor door : sameEmpdoors) {
			if (door.getShiftdate() == null || StringUtils.isEmpty(door.getType())) continue;
			set.add(FormatUtil.formatDate(door.getShiftdate(), "yyyyMMdd"));
			ymdSet.add(FormatUtil.formatDate(door.getShiftdate(), "yyyyMMdd"));
		}
		
		if (set.size() > 0) { 
			Iterator<String> iter = set.iterator();
			while (iter.hasNext()) { //循环处理每天的数据
				String mrakDate = iter.next();
				logger.info("handle mrakDate:{}", mrakDate);
				//获得当前天的历史数据
				List<ShiftInOut> sameDayDBdatas = shiftInOutDao.getShiftInOutByEmpIdAndMarkDateOrderByIdAsc(empid, mrakDate);
				List<SyncKqDataDoor> sameDayDatas = this.getSameEmpIDAndSameDateDoorDatas(mrakDate, sameEmpdoors, empid);
				if (sameDayDBdatas != null && sameDayDBdatas.size() > 0) {
					//当前处理的数据是非当天第一笔数据,数据库中已经存在着当天的历史数据
					logger.info("the data is not first data in : {}", mrakDate);
					this.mateInAndOutShiftSubMethod(empid, false, sameDayDBdatas, sameDayDatas);
				} else { //该批数据为当天的第一批次数据
					logger.info("the data is first data in : {}", mrakDate);
					this.mateInAndOutShiftSubMethod(empid,true, null, sameDayDatas);
				}
			}
		}
		return ymdSet;
	}
	
	/**
	 * 获得某个某员工在markDate内sameEmpdoors的数据:2016.07.28
	 * @param markDate yyyyMMdd日期
	 * @param sameEmpdoors 同一个员工所有天待处理的数据
	 * @param empid 员工编号
	 * @return List<SyncKqDataDoor>
	 */
	private List<SyncKqDataDoor> getSameEmpIDAndSameDateDoorDatas(String markDate, 
			List<SyncKqDataDoor> sameEmpdoors,String empid) throws Exception {
		List<SyncKqDataDoor> lists = new ArrayList<SyncKqDataDoor>();
		for (SyncKqDataDoor door : sameEmpdoors) {
			String str = FormatUtil.formatDate(door.getShiftdate(), "yyyyMMdd");
			if (str.equals(markDate) && empid.equals(door.getEmpID())) {
				lists.add(door);
			}
		}
		Collections.sort(lists, new SyncKqDataDoorCompare());
		return lists;
	}
	
	/**
	 * @param empid 员工编号
	 * @param flag 是否是今天第一笔待处理的数据,true为第一笔数据，false为非第一笔数据
	 * @param sameDayDBdatas 今天前几个批次已经入库的数据 
	 * @param sameEmpAndDateDoors 待处理的数据(某个员工某yyyyMMdd天的所有待处理数据)
	 */
	@Transactional(readOnly = false)
	private void mateInAndOutShiftSubMethod(String empid, boolean flag,
			List<ShiftInOut> sameDayDBdatas,List<SyncKqDataDoor> sameEmpAndDateDoors) throws Exception{
		List<ShiftConfig> confs = this.getShiftConfig(); //获得所有排班信息
		logger.info("mateInAndOutShiftSubMethod param empid:{}", empid);
		logger.info("mateInAndOutShiftSubMethod param flag:{}", flag);
		if (flag) { 
			this.markDateFirstdata(empid, sameEmpAndDateDoors, confs);
		} else {
			this.markDateNotFirstdata(empid, sameDayDBdatas, sameEmpAndDateDoors, confs)	;
		}
	}
	
	/**
	 * 处理非yyyyMMdd天的第一笔数据（即第N笔数据）2016.07.27 GQ
	 * 先在同批次数据中匹配in与out，没有配对成功崽崽已经入库的当天老数据中匹配
	 * @param empid 员工编号
	 * @param alreadyInouts yyyyMMdd已经入库的所有数据
	 * @param sameEmpAndDateDoors 某个员工待处理的yyyyMMdd的数据
	 * @param confs 排班配置信息
	 */
	@Transactional(readOnly = false)
	private void markDateNotFirstdata(String empid,List<ShiftInOut> alreadyInouts,
			List<SyncKqDataDoor> sameEmpAndDateDoors, List<ShiftConfig> confs) throws Exception {
		logger.info("230-->markDateNoFirstdata empid:{}", empid);
		List<ShiftInOut> shifts = new ArrayList<ShiftInOut>(); //保存最新的数据
		List<ShiftInOutHistory> historyShifts = new ArrayList<ShiftInOutHistory>();
		List<String> typeLists = new ArrayList<String>(); //存放in&out出现的次序
		//Integer partnerOutTotal = 0; //统计所有out匹配到in的数量
		String currentmarkDate = FormatUtil.formatDate(sameEmpAndDateDoors.get(0).getShiftdate(),"yyyyMMdd");
		EmpAvailable empAv = empAvailableDao.findByEmpIdAndMarkDate(empid, currentmarkDate); //查看当前时间的available hrs
		logger.info("237-->The empAv one :{}", empAv);
		if (empAv == null) { //没有相关数据，则插入一条
			empAv = new EmpAvailable();
			empAv.setEmpId(empid);
			empAv.setMarkDate(currentmarkDate);
			EmpTradeConfigBase empt = empTradeConfigBaseDao.findByEmpId(empid);
			if (empt == null) {
				return;
			}
			logger.info("246-->The empAv two :{}", empAv);
			empAv.setEmpName(empt.getEmpName());
			empAv.setEmpTrade(empt.getWorkCenter());
			empAv.setDeptNumber(empt.getDeptNumber());
			empAv.setDepartment(empt.getDepartment());
		}
		for (int i=0; i<sameEmpAndDateDoors.size(); i++) {
			SyncKqDataDoor syncDoor  = sameEmpAndDateDoors.get(i);
			logger.info("254-->tht for syncDoor:{}", syncDoor);
			typeLists.add(syncDoor.getType());
			if (syncDoor.getType().equals(Global.SHIFT_IN)) { //如果是in则直接入库
				ShiftInOut shift = new ShiftInOut();
				shift.setBatchNumber(UUID.randomUUID().toString());
				shift.setEmpId(empid);
				shift.setMarkDate(currentmarkDate);
				shift.setInDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
				shift.setSysInDate(syncDoor.getShiftdate());
				logger.info("the first data is in:{}", shift);
				shifts.add(shift); //out空缺
				
				ShiftInOutHistory history = new ShiftInOutHistory();
				history.setBatchNumber(shift.getBatchNumber());
				history.setMarkDate(currentmarkDate);
				history.setEmpId(empid);
				history.setInDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
				history.setSysInDate(syncDoor.getShiftdate());
				historyShifts.add(history); ////out空缺
			} else { //如果是out则匹配之前是否有in
				int index = -1;
				for (int k = shifts.size() -1 ;k >= 0 ; k--) {
					ShiftInOut s = shifts.get(k);
					if (StringUtils.isEmpty(s.getOutDate())) { //找到与之匹配的in
						index = k;
						break;
					}
				}
				if (index >= 0) { //说明out与in匹配上了
					ShiftInOut shift = shifts.get(index); //将out写入缺少out的实体中
					logger.info("the old data who lost out:{}", shift);
					ShiftInOutHistory history = historyShifts.get(index);//历史记录信息
					shifts.remove(index);
					historyShifts.remove(index);
					ShiftConfig mateShift = this.synDatamatchingShift(syncDoor, confs); //记录out匹配到的班次信息
					logger.info("the shift config is :{}", mateShift);
					if (mateShift != null) { //out有对应的班次,再去检验in是否与班次匹配
						boolean flag = this.matchingShiftInTime(shift, mateShift, true);
						if (flag) {//out匹配到的班次，in也正好在该班次内
							shift.setInDate(mateShift.getActualInTime());
							shift.setOutDate(mateShift.getActualOutTime());
							shift.setSysInDate(DateCalculation.StringToDate(currentmarkDate + mateShift.getActualInTime(),"yyyyMMddHH:mm"));
							shift.setSysOutDate(DateCalculation.StringToDate(currentmarkDate + mateShift.getActualOutTime(),"yyyyMMddHH:mm"));
							logger.info("the first data is in shiftConfig:{}", shift);
							shifts.add(index, shift);
							
							history.setInDate(mateShift.getActualInTime());
							history.setOutDate(mateShift.getActualOutTime());
							history.setSysInDate(shift.getSysInDate());
							history.setSysOutDate(shift.getSysOutDate());
							historyShifts.add(index, history);
						} else { //out匹配到班次，但是in不在该班次内
							shift.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
							shift.setSysOutDate(syncDoor.getShiftdate());
							logger.info("the out is match shiftConfig,but in is not:{}", shift);
							shifts.add(index, shift);
							
							history.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
							history.setSysOutDate(syncDoor.getShiftdate());
							historyShifts.add(index, history);
						}
					} else { //out没有匹配到班次，将该out直写入到靠近的in
						shift.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
						shift.setSysOutDate(syncDoor.getShiftdate());
						shifts.add(index, shift);
						logger.info("319-->the data is out of shiftconfig:{}", shift);
						
						history.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
						history.setSysOutDate(syncDoor.getShiftdate());
						historyShifts.add(index, history);
					}
				} else { //匹配不到in，再去匹配先前入库的老数据
					boolean flag = false; //out是否与老数据匹配的标识
					for (int j = alreadyInouts.size() - 1; j >= 0; j--) {
						ShiftInOut oldDBShift = alreadyInouts.get(j);
						if (StringUtils.isEmpty(oldDBShift.getOutDate())) { //有空缺的out进行匹配
							ShiftConfig mateShift = this.synDatamatchingShift(syncDoor, confs); //记录out匹配到的班次信息
							if (mateShift != null) { //out匹配到班次
								boolean tag = this.matchingShiftInTime(oldDBShift, mateShift, true);
								if (tag) { //in 与 out恰好在一个班次内
									oldDBShift.setInDate(mateShift.getActualInTime());
									oldDBShift.setSysInDate(DateCalculation.StringToDate(currentmarkDate + mateShift.getActualInTime(),"yyyyMMddHH:mm"));
									oldDBShift.setOutDate(mateShift.getActualOutTime());
									oldDBShift.setSysOutDate(DateCalculation.StringToDate(currentmarkDate + mateShift.getActualOutTime(),"yyyyMMddHH:mm"));
								} else { //in 与 out不再一个班次内,写入out值
									oldDBShift.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
									oldDBShift.setSysOutDate(syncDoor.getShiftdate());
								}
							} else { //out没有匹配到班次,直接写入
								oldDBShift.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
								oldDBShift.setSysOutDate(syncDoor.getShiftdate());
							}
							alreadyInouts.remove(j);
							alreadyInouts.add(j, oldDBShift);
							logger.info("348-->the shift will be save now:{}", oldDBShift);
							shiftInOutDao.save(oldDBShift); //保存已经匹配到内容项
							flag = false;
							break;
						} 
						flag = true;
					}
					if (flag) { //out与老数据没有匹配上，out落单空缺in
						ShiftInOut shift = new ShiftInOut();
						shift.setBatchNumber(UUID.randomUUID().toString());
						shift.setEmpId(empid);
						shift.setOutDate(FormatUtil.formatDate(syncDoor.getShiftdate(), "HH:mm"));
						shift.setSysOutDate(syncDoor.getShiftdate());
						shift.setMarkDate(currentmarkDate);
						shift.setModifyStatus(Global.NO);
						logger.info("the out is not match:{}", shift);
						shifts.add(shift);
						
						ShiftInOutHistory history = new ShiftInOutHistory();
						history.setBatchNumber(shift.getBatchNumber());
						history.setEmpId(empid);
						history.setOutDate(shift.getOutDate());
						history.setSysOutDate(syncDoor.getShiftdate());
						history.setMarkDate(currentmarkDate);
						history.setInsertDate(new Date());
						historyShifts.add(history);
						
						empAv.setFullShift(Global.NO);
						empAv.setIsreportHandle(Global.NO);
						empAv.setIsAllOk(Global.NO);
						empAv.setEverWarn(Global.YES);
						logger.info("the empAv property:{}", empAv);
					}
				}	
			}
		}
		
		shiftInOutHistoryDao.save(historyShifts);
		shiftInOutDao.save(shifts);	
		empAv.setPersonShift(Global.NO);
		empAv.setIsreportHandle(Global.NO);
		empAv.setFullShift(Global.NO);
		empAv.setIsAllOk(Global.NO);
		empAv.setEverWarn(Global.YES);
	    empAvailableDao.save(empAv);
		logger.info("after insert DB:{}", empAv);
	}
	

	
	
	/**
	 * 如果数据是yyyyMMdd当天第一笔数据，处理方式（先判断第一条数据是否是out）
	 * @param empid 员工编号
	 * @param sameEmpAndDateDoors 某个员工某天待处理的数据
	 * @param resultList 需要返回的结果
	 * @param confs 排班班次配置
	 */
	@Transactional(readOnly = false)
	private void markDateFirstdata(String empid,List<SyncKqDataDoor> sameEmpAndDateDoors, 
			List<ShiftConfig> confs) throws Exception {
			Collections.sort(sameEmpAndDateDoors, new SyncKqDataDoorCompator());
			SyncKqDataDoor syncfirstDoor = sameEmpAndDateDoors.get(0); //首先判断第一条数据
			logger.info("syncfirstDoor first data: {}" , syncfirstDoor);
			ShiftConfig mateShiftConfig = null; //存储匹配到的排班
			String currentmarkDate = FormatUtil.formatDate(syncfirstDoor.getShiftdate(),"yyyyMMdd"); //第一条数据的当前时间
			if (syncfirstDoor.getType().equals(Global.SHIFT_OUT)) { //第一条数据是OUT,需要匹配前天的最后一条数据（最后一条数据是否为in）
				String beformarkDate = DateCalculation.getProDate(currentmarkDate, -1);//返回currentmarkDate的前一天
				List<ShiftInOut> beforDBinouts = shiftInOutDao.
						getShiftInOutByEmpIdAndMarkDateOrderByIdAsc(empid, beformarkDate); //前一天的所有所有in&out数据
				mateShiftConfig = this.synDatamatchingShift(syncfirstDoor, confs); //记录out匹配到的班次信息
				logger.info("Get mateShift info: {}", mateShiftConfig);
				if (mateShiftConfig != null) { //说明第一批的第一条out数据，匹配到班次(开夜班了，需要截断数据),继续匹配前一天的in是否在班次内
					if (beforDBinouts != null && beforDBinouts.size() > 0) { //前一天有数据
						ShiftInOut yesterdayLastshift = beforDBinouts.get(beforDBinouts.size() - 1); //取最后一条数据
						if (StringUtils.isEmpty(yesterdayLastshift.getOutDate())) { //核实yesterdayLastshift是否在mateShift班次内
							boolean identy = this.matchingShiftInTime(yesterdayLastshift, mateShiftConfig, false);
							ShiftInOut currentInOut = this.instanceShiftInOut(empid, currentmarkDate);
							if (identy) { //匹配到班次校准前一天的时间
								yesterdayLastshift.setInDate(mateShiftConfig.getActualInTime()); //in时间调整到班级时间
								yesterdayLastshift.setSysInDate(DateCalculation.StringToDate(beformarkDate + mateShiftConfig.getActualInTime(),"yyyyMMddHH:mm"));
								yesterdayLastshift.setSysOutDate(DateCalculation.StringToDate(beformarkDate + "24:00","yyyyMMddHH:mm"));
								yesterdayLastshift.setOutDate("23:59"); //out时间设为24:00
								
								currentInOut.setSysInDate(DateCalculation.StringToDate(beformarkDate + "24:00","yyyyMMddHH:mm"));
								currentInOut.setInDate("23:59");
								currentInOut.setSysOutDate(DateCalculation.StringToDate(currentmarkDate + mateShiftConfig.getActualOutTime(),"yyyyMMddHH:mm"));
								currentInOut.setOutDate(mateShiftConfig.getActualOutTime()); //取班次的out
							} else { //如果前天最后一条数据in没有匹配到班次
								yesterdayLastshift.setSysOutDate(DateCalculation.StringToDate(beformarkDate + "24:00","yyyyMMddHH:mm"));
								yesterdayLastshift.setOutDate("23:59"); //out时间设为24:00
								//同时将out直接写入数据库（out直接写入数据库）
								currentInOut.setInDate("23:59");
								currentInOut.setSysInDate(DateCalculation.StringToDate(beformarkDate + "24:00","yyyyMMddHH:mm")); //20161130
								currentInOut.setOutDate(FormatUtil.formatDate(syncfirstDoor.getShiftdate(), "HH:mm"));
								currentInOut.setSysOutDate(syncfirstDoor.getShiftdate());
							}
							logger.info("yesterdayLastshift: {}" , yesterdayLastshift);
							logger.info("currentInOut: {}" , currentInOut);
							if (StringUtils.isNotEmpty(currentInOut.getInDate()) &&
									StringUtils.isNotEmpty(currentInOut.getOutDate())) {
								if (!currentInOut.getInDate().equals(currentInOut.getOutDate())) {
									//凌晨退卡，存在跨夜班,如in 27 5:30,out 28 00:00:23，但是此时in和out都是23:59
									shiftInOutDao.save(currentInOut); // 如果第一条数据的in和out不相等才保存
								}
							} else {
								shiftInOutDao.save(currentInOut); //保存out信息
							}
							shiftInOutDao.save(yesterdayLastshift); //完成对前一天in的调整
							this.modifyHistoryDatas(empid, beformarkDate, identy); 
						} else { //前一天的最后一条数据的out不为空.out直接写入.in空缺
							ShiftInOut currentInOut = this.instanceShiftInOut(empid, currentmarkDate);
							currentInOut.setOutDate(FormatUtil.formatDate(syncfirstDoor.getShiftdate(), "HH:mm")); 
							currentInOut.setSysOutDate(syncfirstDoor.getShiftdate());
							shiftInOutDao.save(currentInOut);
						} 
					} else { //前一天没有数据,out直接写入数据库，in空缺
						ShiftInOut currentInOut = this.instanceShiftInOut(empid, currentmarkDate);
						currentInOut.setOutDate(FormatUtil.formatDate(syncfirstDoor.getShiftdate(), "HH:mm")); 
						currentInOut.setSysOutDate(syncfirstDoor.getShiftdate());
						shiftInOutDao.save(currentInOut);
					}
				} else { //第一批次的out没有配匹配到任何一个班次(检查前一天最后一条数据是否是in,即out空缺)
					ShiftInOut currentInOut = this.instanceShiftInOut(empid, currentmarkDate);;
					if (beforDBinouts != null && beforDBinouts.size() > 0) {
						ShiftInOut lastOne = beforDBinouts.get(beforDBinouts.size() - 1);
						if (StringUtils.isEmpty(lastOne.getOutDate())) { //前一天最后一条数据out为空
							lastOne.setSysOutDate(DateCalculation.StringToDate(beformarkDate + "24:00","yyyyMMddHH:mm"));
							lastOne.setOutDate("23:59"); //out时间设为24:00
							logger.info("lastOne data:{}" , lastOne);
							shiftInOutDao.save(lastOne); 
							this.modifyHistoryDatas(empid, beformarkDate, false);
							currentInOut.setInDate("23:59"); 
							currentInOut.setSysInDate(DateCalculation.StringToDate(beformarkDate + "23:59","yyyyMMddHH:mm")); //系统推测可能是00:00
						} 
					}
					//out直接写入数据库
					currentInOut.setOutDate(FormatUtil.formatDate(syncfirstDoor.getShiftdate(), "HH:mm")); 
					currentInOut.setSysOutDate(syncfirstDoor.getShiftdate());
					shiftInOutDao.save(currentInOut);
					logger.info("shift currentInOut: {}",currentInOut);
				}
				//从第二条数据库开始继续处理（第一条out数据已经入库,从第二条数据起继续匹配班次）
				sameEmpAndDateDoors.remove(0);//移除第0个元素，因为已经处理过
				this.goOnHandleRemainDatas(sameEmpAndDateDoors, empid, currentmarkDate, confs);
			} else { //说明是in，则直接入库。
				this.goOnHandleRemainDatas(sameEmpAndDateDoors, empid, currentmarkDate, confs);
			}
	}
	/**
	 * 处理后面的所有数据 2016.07.27
	 * @param sameEmpAndDateDoors 某个员工某yyyyMMdd待处理的数据
	 * @param step  循环起始位置
	 * @param empid 员工编号
	 * @param confs 排班配置
	 */
	@Transactional(readOnly = false)
	private void goOnHandleRemainDatas(List<SyncKqDataDoor> sameEmpAndDateDoors, String empid,
			String currentmarkDate, List<ShiftConfig> confs) throws Exception {
		logger.info("goOnHandleRemainDatas param empid: {}" , empid);
		logger.info("goOnHandleRemainDatas param currentmarkDate: {}" , currentmarkDate);
		EmpAvailable empAv = empAvailableDao.findByEmpIdAndMarkDate(empid, currentmarkDate); //查看当前时间的available hrs
		logger.info("goOnHandleRemainDatas get empAv: {}" , empAv);
		if (empAv == null) { //没有数据，就写入数据
			empAv  = new EmpAvailable();
			empAv.setEmpId(empid);
			empAv.setMarkDate(currentmarkDate);
			EmpTradeConfigBase empt = empTradeConfigBaseDao.findByEmpId(empid);
			if (empt == null) {
				logger.error("Emp {} no trade match", empid);
				return;
			}
			empAv.setEmpName(empt.getEmpName());
			empAv.setEmpTrade(empt.getWorkCenter());
			empAv.setDeptNumber(empt.getDeptNumber());
			empAv.setDepartment(empt.getDepartment());
		}  //有数据就开始更新
		empAv.setContainShift(Global.YES);
		empAv.setPersonShift(Global.NO);
		empAv.setIsreportHandle(Global.NO);
		empAvailableDao.save(empAv);
		logger.info("570----> the empAv:{}", empAv);
		List<ShiftInOut> shifts = new ArrayList<ShiftInOut>();
		//List<ShiftInOutHistory> shiftsHistories = new ArrayList<ShiftInOutHistory>();
		//List<String> typeLists = new ArrayList<String>(); //存放in & out出现的次序
		for (int i = 0; i < sameEmpAndDateDoors.size(); i++) {
			SyncKqDataDoor synDoor = sameEmpAndDateDoors.get(i);
			if (synDoor.getType().toUpperCase().equals(Global.SHIFT_IN)) { //r如果是in则直接写入
				//typeLists.add(Global.SHIFT_IN);
				ShiftInOut shift = this.instanceShiftInOut(empid, currentmarkDate);
				//		new ShiftInOut();
				//shift.setBatchNumber(UUID.randomUUID().toString());
				//shift.setEmpId(empid);
				//shift.setModifyStatus(Global.NO);
				shift.setInDate(FormatUtil.formatDate(synDoor.getShiftdate(), "HH:mm"));
				shift.setSysInDate(synDoor.getShiftdate());
				//shift.setMarkDate(currentmarkDate);
				logger.info("save shift:{}" , shift);
				shifts.add(shift);
				
				/*ShiftInOutHistory history = new ShiftInOutHistory();
				history.setBatchNumber(shift.getBatchNumber());
				history.setEmpId(empid);
				history.setInDate(shift.getInDate());
				history.setSysInDate(synDoor.getShiftdate());
				history.setMarkDate(currentmarkDate);
				history.setInsertDate(new Date());
				logger.info("savr history:{}" , history);
				shiftsHistories.add(history);*/
			} else { //如果是out则检查是否与相应的in
				//typeLists.add(Global.SHIFT_OUT); 
				int index = -1;
				for (int k = shifts.size() -1 ;k >= 0 ; k--) {
					ShiftInOut s = shifts.get(k);
					if (StringUtils.isEmpty(s.getOutDate())) { //找到与之匹配的in
						index = k;
						break;
					}
				}
				if (index >= 0) { //说明out与in匹配上了
					ShiftInOut shift = shifts.get(index); //将out写入缺少in的实体中
					//logger.info("index value:{}",index);
					//logger.info("ShiftInOut:{}",shift);
					//ShiftInOutHistory history = shiftsHistories.get(index);//历史记录信息
					shifts.remove(index);
					//shiftsHistories.remove(index);
					ShiftConfig mateShift = this.synDatamatchingShift(synDoor, confs); //记录out匹配到的班次信息
					logger.info("701-->match mateShift: {}" , mateShift);
					if (mateShift != null) { //out有对应的班次,再去检验in是否与班次匹配
						boolean flag = this.matchingShiftInTime(shift, mateShift, true);
						logger.info("matchingShiftInTime flag:{}",flag);
						if (flag) {//out匹配到的班次，in也正好在该班次内
							shift.setInDate(mateShift.getActualInTime());
							shift.setOutDate(mateShift.getActualOutTime());
							shift.setSysInDate(DateCalculation.StringToDate(currentmarkDate + mateShift.getActualInTime(),"yyyyMMddHH:mm"));
							shift.setSysOutDate(DateCalculation.StringToDate(currentmarkDate + mateShift.getActualOutTime(),"yyyyMMddHH:mm"));
							//logger.info("599 save shift: {}" , shift);
							shifts.add(index, shift);
							
							/*history.setInDate(mateShift.getActualInTime());
							history.setOutDate(mateShift.getActualOutTime());
							history.setSysInDate(shift.getSysInDate());
							history.setSysOutDate(shift.getSysOutDate());
							shiftsHistories.add(index, history);*/
						} else { //out匹配到班次，但是in不在该班次内
							shift.setOutDate(FormatUtil.formatDate(synDoor.getShiftdate(), "HH:mm"));
							shift.setSysOutDate(synDoor.getShiftdate());
							//logger.info("611 save shift: {}" + shift);
							shifts.add(index, shift);
							
							/*history.setOutDate(FormatUtil.formatDate(synDoor.getShiftdate(), "HH:mm"));
							history.setSysOutDate(synDoor.getShiftdate());
							shiftsHistories.add(index, history);*/
						}
					} else { //out没有匹配到班次，将该out直写入到靠近的in
						shift.setOutDate(FormatUtil.formatDate(synDoor.getShiftdate(), "HH:mm"));
						shift.setSysOutDate(synDoor.getShiftdate());
						logger.info("622--> save shift:{}" , shift);
						shifts.add(index, shift);
						
						/*history.setOutDate(FormatUtil.formatDate(synDoor.getShiftdate(), "HH:mm"));
						history.setSysOutDate(synDoor.getShiftdate());
						shiftsHistories.add(index, history);*/
					}
				} else { //匹配不到in，直接写入
					ShiftInOut shift = this.instanceShiftInOut(empid, currentmarkDate);//new ShiftInOut();
					//shift.setBatchNumber(UUID.randomUUID().toString());
					//shift.setEmpId(empid);
					//shift.setSysInDate(DateCalculation.StringToDate(beformarkDate + "24:00","yyyyMMddHH:mm")); //系统推测时间为24:00
					shift.setOutDate(FormatUtil.formatDate(synDoor.getShiftdate(), "HH:mm"));
					shift.setSysOutDate(synDoor.getShiftdate());
					//shift.setMarkDate(currentmarkDate);
					//shift.setModifyStatus(Global.NO);
					logger.info(" 747-->save shift: {}" , shift);
					shifts.add(shift);
					
					/*ShiftInOutHistory history = new ShiftInOutHistory();
					history.setBatchNumber(shift.getBatchNumber());
					//history.setSysInDate(shift.getSysInDate());
					history.setEmpId(empid);
					history.setOutDate(shift.getOutDate());
					history.setSysOutDate(synDoor.getShiftdate());
					history.setMarkDate(currentmarkDate);
					history.setInsertDate(new Date());
					shiftsHistories.add(history);*/
				}				
			}
		}
		
		//shiftInOutHistoryDao.save(shiftsHistories);
		if (shifts.size() > 0 ) {
			shiftInOutDao.save(shifts);
		}
		empAv.setSysShift(Global.YES);
		//这里默认所有的数据都是一致的 2016.09.28
		empAv.setFullShift(Global.NO);
		empAv.setIsAllOk(Global.NO);
		empAv.setEverWarn(Global.YES);
		empAv.setPersonShift(Global.NO);
		logger.info("save EmpAvailabl:" + empAv);
		empAvailableDao.save(empAv);
	}
	
	/**
	 * 根据out匹配out所在的班次
	 * @param synDoor 实际打卡时间
	 * @param confs 班次配置信息
	 * @return mateShift匹配到的班次信息
	 */
	private ShiftConfig synDatamatchingShift(SyncKqDataDoor synDoor, List<ShiftConfig> confs) throws Exception {
		ShiftConfig mateShift = null;
		logger.info("wait matchingShift SyncKqDataDoor:{}", synDoor);
		if (confs != null && confs.size() > 0 && synDoor.getShiftdate() != null) {
			Integer out = Integer.valueOf(FormatUtil.formatDate(synDoor.getShiftdate(),"HHmm"));
			for (ShiftConfig conf : confs) {
				Integer outConfStart  = Integer.valueOf(conf.getOutStart().replace(":", ""));
				Integer outConfEnd = Integer.valueOf(conf.getOutEnd().replace(":", ""));
				if (outConfStart > outConfEnd) { //out落在跨夜夜班 23:30-00:30,2330 > 0030,打卡下班out00:20
					if (outConfEnd > out) { //匹配到班次（打卡时间一定是24:00之后，否则不是是以yyyyMMdd）
						mateShift = conf;
						break;
					}
				} else { //out落在非跨夜班
					if (outConfStart < out && out < outConfEnd) { //如班次：16:30-17:30  匹配到班次
						mateShift = conf;
						break;
					}
				}
			}
		}
		return mateShift;
	}
	/**
	 * 将打卡的in时间与班次匹配，判断in是否在班次内
	 * @param ShiftInOut 写入表中的打卡信息，有in信息存在
	 * @param mateShift 已经匹配到班次配置
	 * @param flag true表示in与out在同一天，false表示in和out不在同一天
	 * @return true in在班次内，false in不在班次内
	 */
	private boolean matchingShiftInTime(ShiftInOut ShiftInOut, ShiftConfig mateShift,boolean flag) throws Exception {
		logger.info("ShiftInOut:{}",ShiftInOut);
		logger.info("mateShift:{}",mateShift);
		logger.info("flag:{}",flag);
		if(mateShift != null && StringUtils.isNotEmpty(ShiftInOut.getInDate())) {
			Integer shiftIn = Integer.valueOf(ShiftInOut.getInDate().replace(":", ""));
			Integer inStartConf  = Integer.valueOf(mateShift.getInStart().replace(":", ""));
			Integer inEndConf = Integer.valueOf(mateShift.getInEnd().replace(":", ""));
			if (inStartConf > inEndConf ) { 
				if (flag) { //跨夜了,in必定是24:00之后，否则不满足in与out在同一天的说法
					if (inEndConf > shiftIn) { //如班次为23:00 -00:30 ,in如果在班次必定在24:00-00:30之间
						return true;
					}
				} else { //跨夜了,in必定在24:00之前，否在不满足in与out不同同一天的说法
					if (shiftIn > inStartConf) { //如班次为23:00 -00:30 ,in如果在班次必定在23:00-24:00之间
						return true;
					}
				}
			} else { //没有跨夜
				if (inStartConf < shiftIn && shiftIn < inEndConf) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获得所有待处理的数据
	 * @return List<SyncKqDataDoor>
	 */
	public List<SyncKqDataDoor> getNeedHandleSyncKqDatas() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select id_ as id,empid_ as empID,shiftdate_ as shiftdate,type_ as type,");
		sql.append("recid_ as recID,empno_ as empNo,devid_ as devID,devdesp_ as devDesp ");
		sql.append(" from pbs_shift_inout_base where handlestatus_='").append(Global.NO).append("'");
		sql.append(" order by empid_ asc, id_ asc");
		RowMapper<SyncKqDataDoor> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(SyncKqDataDoor.class);
		logger.info("getNeedHandleSyncKqDatas sql:{}",sql.toString());
		return jdbcTemplate.query(sql.toString(), rowMapper);
	}
	
	/**
	 * 获得所有的排班信息
	 * @return Iterable<ShiftConfig>
	 */
	public List<ShiftConfig> getShiftConfig() throws Exception {
		return shiftConfigDao.getAllShifts();
	}
	/**
	 * 校正考勤数据
	 */
	@Transactional(readOnly = false)
	public void modifyShiftInAndOutData(Set<String> yyyyMMddSet) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id_ as id,empid_ as empId,empname_ as empName,markdate_ as markDate ");
		sql.append(" from pbs_emp_available where markdate_ in (");
		if (yyyyMMddSet != null && yyyyMMddSet.size() > 0) {
			Iterator<String> iter = yyyyMMddSet.iterator();
			while (iter.hasNext()) {
				sql.append("'").append(iter.next()).append("',");
			}
			sql = sql.deleteCharAt(sql.length() - 1);
		} else {
			String currentmarkDate = FormatUtil.formatDate(new Date(),"yyyyMMdd"); //校验今天的数据
			String beformarkDate = DateCalculation.getProDate(currentmarkDate, -1); //校验昨天的数据
			logger.info("currentmarkDate:{}", currentmarkDate);
			logger.info("beformarkDate:{}", beformarkDate);
			sql.append("'").append(currentmarkDate).append("',");
			sql.append("'").append(beformarkDate).append("'");
		}
		sql.append(") and isallok_ ='N' and fullshift_='N' ");
		logger.info("modify data sql:{}",sql);
		RowMapper<EmpAvailable> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(EmpAvailable.class);
		List<EmpAvailable> avs = jdbcTemplate.query(sql.toString(), rowMapper); //获得
		if (avs != null && avs.size() >0) {
			if (avs != null && avs.size() >0) {
				for (EmpAvailable emp : avs) {
					if(StringUtils.isEmpty(emp.getEmpId()) || StringUtils.isEmpty(emp.getMarkDate())) {
						continue;
					}
					List<ShiftInOut> shifts = shiftInOutDao.getShiftInOutByEmpIdAndMarkDateOrderByIdAsc(emp.getEmpId(), emp.getMarkDate());
					if (shifts != null && shifts.size() > 0) { //有考勤in & out信息
						boolean flag = false;
						for (ShiftInOut sh : shifts) {
							if (StringUtils.isEmpty(sh.getInDate()) || StringUtils.isEmpty(sh.getOutDate())) {
								flag = false;
								break;
							}
							flag = true;
						}
						if (flag) {
							EmpAvailable  e = empAvailableDao.findOne(emp.getId());
							logger.info("update EmpAvailable id：{}",emp.getId());
							e.setIsAllOk("Y");
							e.setFullShift("Y");
							e.setIsreportHandle("N");
							e.setEverWarn("N");
							e.setPersonShift("N");
							empAvailableDao.save(e);
						}
					} else { //无in & out信息
						EmpAvailable  e = empAvailableDao.findOne(emp.getId());
						logger.info("the EmpAvailable has no in or out, the id：{}",emp.getId());
						e.setFullShift(null);
						e.setIsAllOk(null);
						e.setEverWarn("Y");
						e.setPersonShift("N");
						empAvailableDao.save(e);
					}
				}
			}
		}
	}
	
	/**
	 * 校正历史数据
	 * @param empid 员工编号
	 * @param markDate 修复时间节点数据
	 * @param containshift 是否匹配到班次
	 */
	@Transactional(readOnly = false)
	private void modifyHistoryDatas(String empid, String markDate,boolean containshift) {
		List<ShiftInOut> shifts = shiftInOutDao.getShiftInOutByEmpIdAndMarkDateOrderByIdAsc(empid, markDate);
		if (shifts != null && shifts.size() > 0) {
			boolean flag = false;
			for (ShiftInOut sh : shifts) {
				if (StringUtils.isEmpty(sh.getInDate()) || StringUtils.isEmpty(sh.getOutDate())) {
					flag = false;
					break;
				}
				flag = true;
			}
			EmpAvailable empAv = empAvailableDao.findByEmpIdAndMarkDate(empid, markDate);
			if (empAv == null) {
				empAv = new EmpAvailable();
				empAv.setEmpId(empid);
				empAv.setMarkDate(markDate);
				EmpTradeConfigBase empt = empTradeConfigBaseDao.findByEmpId(empid);
				if (empt == null) {
					return;
				}
				empAv.setEmpName(empt.getEmpName());
				empAv.setEmpTrade(empt.getWorkCenter());
				empAv.setDeptNumber(empt.getDeptNumber());
				empAv.setDepartment(empt.getDepartment());
			}
			empAv.setContainShift("Y"); //是否有in或者out数据
			//if(containshift) {
			//} else {
			//	empAv.setContainShift("N");
			//}
			empAv.setIsreportHandle("N");
			empAv.setPersonShift("N");
			if (flag) {
				empAv.setIsAllOk("Y");
				empAv.setFullShift("Y");
				empAv.setEverWarn("N");
			} else {
				empAv.setIsAllOk("N");
				empAv.setFullShift("N"); //in out 完整配对
				empAv.setEverWarn("Y");
			}
			empAvailableDao.save(empAv);
		}
	}
	
	/***
	 * 实例化一个ShiftInOut 2016.12.1
	 * @param empid 员工编号
	 * @param markDate 日期节点
	 * @return ShiftInOut
	 */
	private ShiftInOut instanceShiftInOut(String empid, String markDate) {
		ShiftInOut currentInOut = new ShiftInOut();
		currentInOut.setBatchNumber(UUID.randomUUID().toString());
		currentInOut.setEmpId(empid);
		currentInOut.setMarkDate(markDate);
		currentInOut.setModifyStatus(Global.NO);
		return currentInOut;
	}

}
