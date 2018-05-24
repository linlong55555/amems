package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.ScheduledCheckItemEditableMapper;
import com.eray.thjw.productionplan.dao.ScheduledCheckItemMapper;
import com.eray.thjw.productionplan.po.PlaneInitData;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.po.ScheduledTask;
import com.eray.thjw.productionplan.service.CheckTaskBillService;
import com.eray.thjw.productionplan.service.PlaneInitDataService;
import com.eray.thjw.productionplan.service.ScheduledCheckItemService;
import com.eray.thjw.productionplan.service.ScheduledCheckMonitorItemService;
import com.eray.thjw.productionplan.service.ScheduledTaskSerivce;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.sched.dao.ProductPlanSchedMapper;
import com.eray.thjw.sched.dao.ServiceInstructionMapper;
import com.eray.thjw.sched.po.ProductPlanSched;
import com.eray.thjw.sched.po.ServiceInstruction;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.BinaryEnum;
import enu.LogOperationEnum;
import enu.MonitorItemEnum;
import enu.ScheduledEnum;
import enu.ShedLifeUnit;
import enu.SrvInstructEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.ThresholdEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;


@Service
public class ScheduledCheckItemServiceImpl implements ScheduledCheckItemService {
	
	@Autowired
	private ScheduledCheckItemMapper scheduledCheckItemMapper;
	
	@Autowired
	private ScheduledCheckItemEditableMapper scheduledCheckItemEditableMapper;
	
	@Autowired
	private CommonRecService commonRecService;
	
	@Resource
	private ScheduledCheckMonitorItemService scheduledCheckMonitorItemService; //b_s_00304 生效区-定检监控项目service
	
	@Resource
	private PlaneInitDataService planeInitDataService; //飞机基础数据service
	
	@Autowired
	private CheckTaskBillService checkTaskBillService;//b_g_014 定检任务主表 service
	
	@Autowired
	private TimeControllWareItemService timeControllWareItemService;//b_g_014 定检任务主表 service
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ScheduledTaskSerivce scheduledTaskSerivce;
	
	@Resource
	private PlaneModelDataService planeModelDataService; //飞机数据service
	
	@Resource
	private ProductPlanSchedMapper  productPlanSchedMapper; //计划预排
	@Resource
	private ServiceInstructionMapper  serviceInstructionMapper;//服务指令
	
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Override
	public List<ScheduledCheckItem> queryAllPageList(
			ScheduledCheckItem scheduledCheckItem) throws Exception {
		
		try {
			return scheduledCheckItemMapper.queryAllPageList(scheduledCheckItem);
		} catch (Exception e) {
			
		throw new Exception("ServiceException selectByPrimaryKey RoleServiceImpl：",e);
		}
	}

	@Override
	public int queryCount(ScheduledCheckItem scheduledCheckItem)
			throws Exception {
		
		try {
			return scheduledCheckItemMapper.queryCount(scheduledCheckItem);
		} catch (Exception e) {
			
		throw new Exception("ServiceException queryCount RoleServiceImpl：",e);
		}
	}

	/**
	 * 编辑区-保存定检项目
	 */
	@Override
	public String saveEditable(ScheduledCheckItem scheduledCheckItem, String czls, LogOperationEnum logOperationEnum)
			throws RuntimeException {
		// 生成id
		scheduledCheckItem.setId(UUID.randomUUID().toString());
		// 状态
		scheduledCheckItem.setZt(1);
		// 设置默认值
		setDefaultValue(scheduledCheckItem);
		// 保存定检项目
		scheduledCheckItemEditableMapper.insertSelective(scheduledCheckItem);
		// 插入日志
		commonRecService.write(scheduledCheckItem.getId(), TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.SAVE, scheduledCheckItem.getZjqdid());
		return scheduledCheckItem.getId();
	}
	
	
	/**
	 * 设置默认值
	 * @param ll
	 */
	private void setDefaultValue(ScheduledCheckItem item){
		// 当前登录用户
		User user = ThreadVarUtil.getUser();
		// 待同步
		item.setTbbs(SynchronzeEnum.TO_DO.getCode());
		// 维护人id
		item.setWhrid(user.getId());
		// 维护单位id
		item.setWhdwid(user.getBmdm());
		// 维护时间
		item.setWhsj(new Date());
		// 组织机构
		item.setDprtcode(user.getJgdm());
	}

	/**
	 * 编辑区-根据装机清单查询
	 */
	@Override
	public List<ScheduledCheckItem> queryEditableByZjqdid(
			String zjqdid) throws RuntimeException {
		return scheduledCheckItemEditableMapper.queryByZjqdid(zjqdid);
	}

	/**
	 * 删除定检项目
	 */
	@Override
	public void deleteEditable(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		// 状态
		item.setZt(0);
		// 同步标识
		item.setTbbs(SynchronzeEnum.TO_DO.getCode());
		// 设置默认值
		setDefaultValue(item);
		// 更新状态为0
		scheduledCheckItemEditableMapper.updateByPrimaryKeySelective(item);
		// 插入日志
		commonRecService.write(item.getId(), TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(),
				czls, logOperationEnum, UpdateTypeEnum.DELETE, item.getZjqdid());
	}
	
	/**
	 * 
	 * 格式化数据
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<ScheduledCheckItem> geshihua(List<ScheduledCheckItem> list,PlaneModelData planeModelData, ScheduledCheckItem scheduledCheckItemParam)throws Exception{
		Pagination pagination = scheduledCheckItemParam.getPagination();
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String> resultMap1 = new HashMap<String, String>();
		StringBuffer jh=new StringBuffer();//计划
		StringBuffer sy=new StringBuffer();//剩余
		String syts=null;//剩余天数
		
		Map<String, Object> paramCondition = new HashMap<String, Object>();
		paramCondition.put("fjzch", scheduledCheckItemParam.getFjzch());
		paramCondition.put("dprtcode", scheduledCheckItemParam.getDprtcode());
		List<ScheduledCheckMonitorItem> jihualist=scheduledCheckMonitorItemService.queryAllList(paramCondition);//查询计划
		List<ScheduledCheckMonitorItem> shijilist=scheduledCheckMonitorItemService.queryAllsj(paramCondition);//查询实际
		
		//遍历定检件定检项目
		for (ScheduledCheckItem scheduledCheckItem : list) {
			//清空StringBuffer
			jh.delete(0,jh.length());
			sy.delete(0,sy.length());
			resultMap.clear();
			resultMap1.clear();
			//遍历所有计划
			for (ScheduledCheckMonitorItem smjh : jihualist) {
				if(smjh.getMainid().equals(scheduledCheckItem.getId())){
				
					jh.append(smjh.getMs()+":"+jihua(smjh)+",");//拼接计划值
					resultMap.put(smjh.getJklbh(), smjh.getJshz());
				}
			}
			
			//遍历所有实际
			for (ScheduledCheckMonitorItem smsj : shijilist) {
				if(smsj.getMainid().equals(scheduledCheckItem.getId())){
				
				sy.append(shengyu(smsj,resultMap)+",");//拼接剩余值
				resultMap1.put(smsj.getJklbh(), smsj.getSy());
				}
			}
			
			//根据飞机注册号查询部件日使用量
			if(planeModelData.getFjzch().equals(scheduledCheckItem.getFjzch())){
				//计算剩余天数
				if(resultMap1.size()!=0){
					syts=timeControllWareItemService.shengyutianshu(planeModelData,resultMap1);
					if(syts==null){
						syts="";
					}
				}
			}
			
			scheduledCheckItem.setJhc(jh.toString());//计划
			scheduledCheckItem.setSy(sy.toString());//剩余
			scheduledCheckItem.setSyts(syts);//剩余天数
			
			//任务单
			if(scheduledCheckItem.getRwid()!=null){
				if(scheduledCheckItem.getRwdh()!=null&&scheduledCheckItem.getWhsj1()!=null&&scheduledCheckItem.getWhrid1()!=null){
					scheduledCheckItem.setJhrw(scheduledCheckItem.getRwdh()+"/"+scheduledCheckItem.getWhsj1()+"/"+scheduledCheckItem.getWhrid1());
				}
			}
		}
		List<ScheduledCheckItem> list2=new ArrayList<ScheduledCheckItem>();
		
		if("auto".equals(pagination.getSort())||("syts".equals(pagination.getSort())&&pagination.getOrder().equals("desc"))){
			list2=sytspaixu(list);//剩余天数排序
		}else{
			list2=sytspaixuzheng(list);
		}
		return list2;
	}
	
	
	/**
	 * 
	 * 格式化数据
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ScheduledCheckItem> installRecursivelyScheduledCheck( ScheduledCheckItem param,User user)throws BusinessException{
		try {
			if(StringUtils.isEmpty( param.getFjzch()) ){
				throw new BusinessException("请选择正确的飞机注册号");
			}
			//初始化截至日志
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(param.getJzrq());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);  
			calendar.set(Calendar.SECOND, 59); 
			param.setJzrq(calendar.getTime());
			
			List<ScheduledCheckItem> list= queryAllPageList1(param); //查询定检监控主数据
			PlaneModelData planeModelData= planeModelDataService.selectPlanes(param.getFjzch(),param.getDprtcode());//查询 d_004 所有部件日使用量
			if(planeModelData == null){
				throw new BusinessException("找不到指定机构下的飞机主数据");
			}
			//监控项目编号-计划映射-此计划值包含了周期值，所以是完整的计划值
			Map<String, String> curjhMap = new HashMap<String, String>();
			//监控项目编号-剩余天数映射
			Map<String, String> sytsMap = new HashMap<String, String>();
			//监控项目编号-周期值映射
			Map<String, BigDecimal> zqzMap = new HashMap<String, BigDecimal>();
			
			//监控项目编号-实际映射-此实际值包含了飞机初始值，所以是完整的实际值
			Map<String, String> cursjMap = new HashMap<String, String>();
			
			Map<String, Object> paramCondition = new HashMap<String, Object>();
			paramCondition.put("fjzch", param.getFjzch());
			paramCondition.put("dprtcode", param.getDprtcode());
			List<ScheduledCheckMonitorItem> jihualist=scheduledCheckMonitorItemService.queryAllList(paramCondition);//查询计划
			List<ScheduledCheckMonitorItem> shijilist=scheduledCheckMonitorItemService.queryAllsj(paramCondition);//查询实际
			String curSj = "";
			Date currentDate = commonService.getSysdate();
			
			//预排项目
			List<ScheduledCheckItem> ypItems = new ArrayList<ScheduledCheckItem>();
			for (ScheduledCheckItem scheduledCheckItem : list) {
				sytsMap.clear();
				zqzMap.clear();
				//遍历所有计划
				for (ScheduledCheckMonitorItem smjh : jihualist) {
					if(smjh.getMainid().equals(scheduledCheckItem.getId())){
						//算监控项计划值
						String curJh = jihua(smjh);
						smjh.setCurJh(curJh);
						curjhMap.put(smjh.getJklbh(), smjh.getJshz());
						zqzMap.put(smjh.getJklbh(), smjh.getZqz());
						
						//将各个监控项计划值 加入监控集合
						List<ScheduledCheckMonitorItem> monitorItems = scheduledCheckItem.getMonitorItemList();
						if (monitorItems==null) {
							monitorItems = new ArrayList<ScheduledCheckMonitorItem>();
						}
						monitorItems.add(smjh);
						scheduledCheckItem.setMonitorItemList(monitorItems);
					}
				}
				
				//遍历所有实际
				for (ScheduledCheckMonitorItem smsj : shijilist) {
					if(smsj.getMainid().equals(scheduledCheckItem.getId())){
						//算监控项实际值
						if (StringUtils.isEmpty(smsj.getSj())) {
							smsj.setSj("0");
						}
						curSj = calSj(smsj);
						shengyu(smsj,curjhMap);
						sytsMap.put(smsj.getJklbh(), smsj.getSy());
						cursjMap.put(smsj.getJklbh(),curSj);
						
						//将各个监控项实际值加入监控集合
						List<ScheduledCheckMonitorItem> monitorItems = scheduledCheckItem.getMonitorItemList();
						if (monitorItems!=null) {
							for (ScheduledCheckMonitorItem item : monitorItems) {
								if (item.getJklbh().equals(smsj.getJklbh())) {
									item.setSj(curSj);
									item.setCurSj(curSj);
									item.setSy(smsj.getSy());
								}
							}
						}
						//将各个监控项实际值加入监控集合
					}
				}
				 
				scheduledCheckItem.setJzrq(param.getJzrq());
				//根据飞机注册号查询部件日使用量
				if(planeModelData.getFjzch().equals(scheduledCheckItem.getFjzch())){
					//计算剩余天数
					if(sytsMap.size()!=0){
						timeControllWareItemService.calculationScheduledCheck(planeModelData,sytsMap,scheduledCheckItem,zqzMap,cursjMap,curjhMap);
						//当前实际，计划，日使用量，周期值为空计算预排 
						if(StringUtils.isBlank(scheduledCheckItem.getSyts())
								||StringUtils.isBlank(scheduledCheckItem.getRsyl() )
								||scheduledCheckItem.getZqz() == null 
								/*||StringUtils.isBlank(scheduledCheckItem.getCurjh() )
								||StringUtils.isBlank(scheduledCheckItem.getCursj() )*/){
							continue;
						}
						else {
							 
							//计算和生成预排信息
							//计算计划执行日期
							Date jzrq = scheduledCheckItem.getJzrq();
							int syts = Integer.valueOf(scheduledCheckItem.getSyts());
							scheduledCheckItem.setJhzxrq(DateUtil.getDatePlus(currentDate, syts));
							
							//由于剩余天数为负数（较小：-1000）造成大量预排，所以做以下处理：2017-04-12 开始
							//剩余天数为负数  :计划执行日期 = 当前日期
							if (syts<0) {
								scheduledCheckItem.setJhzxrq(DateUtil.getDateStr());
							}
							//由于剩余天数为负数（较小：-1000）造成大量预排，所以做以下处理：2017-04-12 结束
							
							Date jhzxrq = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, scheduledCheckItem.getJhzxrq());
							
							
							if (scheduledCheckItem.getJzrq().getTime() - jhzxrq.getTime()>=0) {
								scheduledCheckItem.setJhzxrqFir(scheduledCheckItem.getJhzxrq());
								//计划执行日期大于截至日期，没有计划信息
								ScheduledCheckItem parent = scheduledCheckItem;
								parent.calJhAndSj();
								ypItems.add(parent);
								while(true){
									//获取下次定检项目，计算计划执行日期，在截至日期前保留，否则丢弃。
									ScheduledCheckItem next = getDjjhNext(parent,currentDate);
									if (jzrq.getTime() - DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, next.getJhzxrq()).getTime() >=0) {
										//监控项：计划值
										next.calJhAndSj();
										ypItems.add(next);
										parent = next;
									}
									else {
										break;
									}
									
								}
								
							}
						}    
					}
				}
			}
			
			//按计划执行日期排序()
			if (null!= ypItems && !ypItems.isEmpty()) {
				 Comparator<ScheduledCheckItem> comp = new Comparator<ScheduledCheckItem>() {
						@Override
						public int compare(ScheduledCheckItem o1, ScheduledCheckItem o2) {
							Long time1 = 0L;
							Long time2 = 0L ;
							try {
								 time1 = StringUtils.isBlank(o1.getJhzxrq()) ?0L:DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, o1.getJhzxrq()).getTime();
								 time2 = StringUtils.isBlank(o2.getJhzxrq()) ?0L:DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, o2.getJhzxrq()).getTime();
								 
							} catch (ParseException e) {
								e.printStackTrace();
							}
							return time1.compareTo(time2);
						}
					 } ;
					Collections.sort(ypItems, comp);
			}
			return ypItems;
		} catch (Exception e) {
			throw new BusinessException("计算预排出错", e);
		}
		
	}
	
	/**
	 * 计算预排
	 * @param param
	 * @param user
	 * @return
	 * @throws BusinessException
	 */
	private List<ScheduledCheckItem> calSched(ScheduledCheckItem param)throws BusinessException{
		try {
			if(StringUtils.isEmpty( param.getFjzch()) ){
				throw new BusinessException("请选择正确的飞机注册号");
			}
			//初始化截至日志
			/*Calendar calendar = Calendar.getInstance();
			calendar.setTime(param.getJzrq());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);  
			calendar.set(Calendar.SECOND, 59); 
			param.setJzrq(calendar.getTime());*/
			
			List<ScheduledCheckItem> list= queryAllPageList1(param); //查询定检监控主数据
			PlaneModelData planeModelData= planeModelDataService.selectPlanes(param.getFjzch(),param.getDprtcode());//查询 d_004 所有部件日使用量
			if(planeModelData == null){
				throw new BusinessException("找不到指定机构下的飞机主数据");
			}
			//监控项目编号-计划映射-此计划值包含了周期值，所以是完整的计划值
			Map<String, String> curjhMap = new HashMap<String, String>();
			//监控项目编号-剩余天数映射
			Map<String, String> sytsMap = new HashMap<String, String>();
			//监控项目编号-周期值映射
			Map<String, BigDecimal> zqzMap = new HashMap<String, BigDecimal>();
			
			//监控项目编号-实际映射-此实际值包含了飞机初始值，所以是完整的实际值
			Map<String, String> cursjMap = new HashMap<String, String>();
			
			Map<String, Object> paramCondition = new HashMap<String, Object>();
			paramCondition.put("fjzch", param.getFjzch());
			paramCondition.put("dprtcode", param.getDprtcode());
			List<ScheduledCheckMonitorItem> jihualist=scheduledCheckMonitorItemService.queryAllList(paramCondition);//查询计划
			List<ScheduledCheckMonitorItem> shijilist=scheduledCheckMonitorItemService.queryAllsj(paramCondition);//查询实际
			String curSj = "";
			Date currentDate = commonService.getSysdate();
			
			//预排项目
			List<ScheduledCheckItem> ypItems = new ArrayList<ScheduledCheckItem>();
			for (ScheduledCheckItem scheduledCheckItem : list) {
				sytsMap.clear();
				zqzMap.clear();
				//遍历所有计划
				for (ScheduledCheckMonitorItem smjh : jihualist) {
					if(smjh.getMainid().equals(scheduledCheckItem.getId())){
						//算监控项计划值
						String curJh = jihua(smjh);
						smjh.setCurJh(curJh);
						curjhMap.put(smjh.getJklbh(), smjh.getJshz());
						zqzMap.put(smjh.getJklbh(), smjh.getZqz());
						
						//将各个监控项计划值 加入监控集合
						List<ScheduledCheckMonitorItem> monitorItems = scheduledCheckItem.getMonitorItemList();
						if (monitorItems==null) {
							monitorItems = new ArrayList<ScheduledCheckMonitorItem>();
						}
						monitorItems.add(smjh);
						scheduledCheckItem.setMonitorItemList(monitorItems);
					}
				}
				
				//遍历所有实际
				for (ScheduledCheckMonitorItem smsj : shijilist) {
					if(smsj.getMainid().equals(scheduledCheckItem.getId())){
						//算监控项实际值
						if (StringUtils.isEmpty(smsj.getSj())) {
							smsj.setSj("0");
						}
						curSj = calSj(smsj);
						shengyu(smsj,curjhMap);
						sytsMap.put(smsj.getJklbh(), smsj.getSy());
						cursjMap.put(smsj.getJklbh(),curSj);
						
						//将各个监控项实际值加入监控集合
						List<ScheduledCheckMonitorItem> monitorItems = scheduledCheckItem.getMonitorItemList();
						if (monitorItems!=null) {
							for (ScheduledCheckMonitorItem item : monitorItems) {
								if (item.getJklbh().equals(smsj.getJklbh())) {
									item.setSj(curSj);
									item.setCurSj(curSj);
									item.setSy(smsj.getSy());
								}
							}
						}
						//将各个监控项实际值加入监控集合
					}
				}
				 
				scheduledCheckItem.setJzrq(param.getJzrq());
				//根据飞机注册号查询部件日使用量
				if(planeModelData.getFjzch().equals(scheduledCheckItem.getFjzch())){
					//计算剩余天数
					if(sytsMap.size()!=0){
						timeControllWareItemService.calculationScheduledCheck(planeModelData,sytsMap,scheduledCheckItem,zqzMap,cursjMap,curjhMap);
						//当前实际，计划，日使用量，周期值为空计算预排 
						if(StringUtils.isBlank(scheduledCheckItem.getSyts())
								||StringUtils.isBlank(scheduledCheckItem.getRsyl() )
								||scheduledCheckItem.getZqz() == null 
								/*||StringUtils.isBlank(scheduledCheckItem.getCurjh() )
								||StringUtils.isBlank(scheduledCheckItem.getCursj() )*/){
							continue;
						}
						else {
							 
							//计算和生成预排信息
							//计算计划执行日期
							Date jzrq = scheduledCheckItem.getJzrq();
							int syts = Integer.valueOf(scheduledCheckItem.getSyts());
							scheduledCheckItem.setJhzxrq(DateUtil.getDatePlus(currentDate, syts));
							
							//由于剩余天数为负数（较小：-1000）造成大量预排，所以做以下处理：2017-04-12 开始
							//剩余天数为负数  :计划执行日期 = 当前日期
							if (syts<0) {
								scheduledCheckItem.setJhzxrq(DateUtil.getDateStr());
							}
							//由于剩余天数为负数（较小：-1000）造成大量预排，所以做以下处理：2017-04-12 结束
							
							Date jhzxrq = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, scheduledCheckItem.getJhzxrq());
							
							
							if (scheduledCheckItem.getJzrq().getTime() - jhzxrq.getTime()>=0) {
								scheduledCheckItem.setJhzxrqFir(scheduledCheckItem.getJhzxrq());
								//计划执行日期大于截至日期，没有计划信息
								ScheduledCheckItem parent = scheduledCheckItem;
								parent.calJhAndSj();
								ypItems.add(parent);
								while(true){
									//获取下次定检项目，计算计划执行日期，在截至日期前保留，否则丢弃。
									ScheduledCheckItem next = getDjjhNext(parent,currentDate);
									if (jzrq.getTime() - DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, next.getJhzxrq()).getTime() >=0) {
										//监控项：计划值
										next.calJhAndSj();
										ypItems.add(next);
										parent = next;
									}
									else {
										break;
									}
									
								}
								
							}
						}    
					}
				}
			}
			return ypItems;
		} catch (Exception e) {
			throw new BusinessException("计算预排出错", e);
		}
	}
	
	private ScheduledCheckItem getDjjhNext(ScheduledCheckItem scheduledCheckItem,Date currentDate) throws ParseException {
		ScheduledCheckItem next = scheduledCheckItem.deepClone(ScheduledCheckItem.class);
		List<ScheduledCheckMonitorItem> monitorItemList = next.getMonitorItemList();
		if (monitorItemList!=null && !monitorItemList.isEmpty()) {
			 for (ScheduledCheckMonitorItem monitorItem : monitorItemList) {
				 monitorItem.setYpSeq(scheduledCheckItem.getYpSeq());
				 monitorItem.setJhzxrq(scheduledCheckItem.getJhzxrq());
				 
				 monitorItem.calJhNext();
				 monitorItem.calSytsNext();
			 }
			 
			 ScheduledCheckMonitorItem min = Collections.min(monitorItemList,new Comparator<ScheduledCheckMonitorItem>() {
				 @Override
				 public int compare(ScheduledCheckMonitorItem o1, ScheduledCheckMonitorItem o2) {
					 return new BigDecimal(o1.getSyts()).compareTo(new BigDecimal(o2.getSyts()));
				 }
			 });
			 next.setSyts(min.getSyts());
			 next.setJhzxrq(DateUtil.getDatePlus(next.getJhzxrq(), new BigDecimal(min.getSyts()).intValue() ));
		}
		return next;
		
	}
	
	public String calSj(ScheduledCheckMonitorItem scheduledCheckMonitorItem) throws Exception{
		String curSj = scheduledCheckMonitorItem.getSj();
		if(scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.calendar.getId().intValue()){
			curSj = scheduledCheckMonitorItem.getSj();
		}else if(scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.DAY.getId().intValue()||scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.MOON.getId().intValue()||scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.YEAR.getId().intValue()){
			curSj = scheduledCheckMonitorItem.getSj();
		}else if(scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.TIME.getId().intValue()){
			
			//机身飞行时间+需要累加飞机注册表.机身飞行时间
			if(scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString().toLowerCase())){
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_time_jsfx");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				curSj = StringAndDate_Util.operateTime(sjs, scheduledCheckMonitorItem.getSj(), TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);
			}else{
				curSj=scheduledCheckMonitorItem.getSj();
			}
		}else if(scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.LOOP.getId().intValue()){
			
			//起落循环+需要累加飞机注册表.起落循环
			if(scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_loop_qlj");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				DecimalFormat df = new DecimalFormat("0.00");   
				curSj=String.valueOf(df.format(Double.parseDouble(sjs)+Double.parseDouble(scheduledCheckMonitorItem.getSj())));
			}else if(scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
				//TS1+需要累加飞机注册表.TS1
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_loop_ts1");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				DecimalFormat df = new DecimalFormat("0.00");   
				curSj=df.format(Double.valueOf(sjs)+Double.valueOf(scheduledCheckMonitorItem.getSj()))+"";
			
			}else if (scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){
				//TS2+需要累加飞机注册表.TS2
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_loop_ts2");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				DecimalFormat df = new DecimalFormat("0.00");   
				curSj=df.format(Double.valueOf(sjs)+Double.valueOf(scheduledCheckMonitorItem.getSj()))+"";
			}else{
				DecimalFormat df = new DecimalFormat("0.00");  
				curSj=df.format(Double.valueOf(scheduledCheckMonitorItem.getSj()))+"";
			}
		}
		
		//更新各个定检项，当前实际值
		scheduledCheckMonitorItem.setCurSj(curSj);
		return curSj;
	}

	/**
	 * 剩余天数排序正序
	 * @param list
	 * @return
	 */
	public List<ScheduledCheckItem> sytspaixuzheng(List<ScheduledCheckItem> list){
		
		
		Collections.sort(list, new Comparator<ScheduledCheckItem>() {
			@Override
			public int compare(ScheduledCheckItem o1, ScheduledCheckItem o2) {
				
				Integer zyxs1=o1.getZyxs();
          		if(zyxs1==null||zyxs1==0){
          			zyxs1=1;
          		}
          		Integer zyxs2=o2.getZyxs();
          		if(zyxs2==null||zyxs2==0){
          			zyxs2=1;
          		}
				
				
				if(StringUtils.isNotBlank(o1.getRwdh())){
					return 1;
				}
				if(StringUtils.isNotBlank(o2.getRwdh())){
					return -1;
				}
				if(StringUtils.isNotBlank(o1.getSyts()) && StringUtils.isNotBlank(o2.getSyts())){
					return Integer.valueOf((int)(Double.valueOf(o2.getSyts())/zyxs2)).compareTo(Integer.valueOf((int)(Double.valueOf(o1.getSyts())/zyxs1)) );
				}else{
					return StringUtils.isNotBlank(o1.getSyts())?-1:(StringUtils.isNotBlank(o1.getSyts())?1:0);
				}
			}
			
		});
		return list ;
	}

	/**
	 * 剩余天数排序倒序
	 * @param list
	 * @return
	 */
	public List<ScheduledCheckItem> sytspaixu(List<ScheduledCheckItem> list){
		
		
		Collections.sort(list, new Comparator<ScheduledCheckItem>() {
			@Override
			public int compare(ScheduledCheckItem o1, ScheduledCheckItem o2) {
				
				Integer zyxs1=o1.getZyxs();
          		if(zyxs1==null||zyxs1==0){
          			zyxs1=1;
          		}
          		Integer zyxs2=o2.getZyxs();
          		if(zyxs2==null||zyxs2==0){
          			zyxs2=1;
          		}
				
				
				if(StringUtils.isNotBlank(o1.getRwdh())){
					return 1;
				}
				if(StringUtils.isNotBlank(o2.getRwdh())){
					return -1;
				}
				if(StringUtils.isNotBlank(o1.getSyts()) && StringUtils.isNotBlank(o2.getSyts())){
					return Integer.valueOf((int)(Double.valueOf(o1.getSyts())/zyxs1)).compareTo( Integer.valueOf((int)(Double.valueOf(o2.getSyts())/zyxs2)));
				}else{
					return StringUtils.isNotBlank(o1.getSyts())?-1:(StringUtils.isNotBlank(o1.getSyts())?1:0);
				}
			}
			
		});
		return list ;
	}

	

	/**
	 * 计算剩余 ,剩余算法：上限=计划值-实际值（日历除外，日历计划值=预拆值，sql已实现）
	 * 特殊：机身飞行时间+需要累加飞机注册表.机身飞行时间，起落循环+需要累加飞机注册表.起落循环，TS1+需要累加飞机注册表.TS1，需要累加飞机注册表.TS1+需要累加飞机注册表.TS2
	 */
	@Override
	public String shengyu(ScheduledCheckMonitorItem scheduledCheckMonitorItem,Map<String, String> map) throws Exception{
		String sy=null;
		
		 if(StringUtils.isEmpty(map.get(scheduledCheckMonitorItem.getJklbh()))) {
			 sy="";
		 }else{
		
		if(scheduledCheckMonitorItem.getDw()==ScheduledEnum.DAY.getId().intValue()||scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.MOON.getId().intValue()||scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.YEAR.getId().intValue()){
			sy=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0", map.get(scheduledCheckMonitorItem.getJklbh()))+"";
		}
		else if(scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.TIME.getId().intValue()){
			
			//机身飞行时间+需要累加飞机注册表.机身飞行时间
			if(scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString().toLowerCase())){
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_time_jsfx");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				
				String sj=StringAndDate_Util.operateTime(sjs, StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);
				sy=StringAndDate_Util.operateTime(map.get(scheduledCheckMonitorItem.getJklbh()),sj, TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);
			}else{
				sy=StringAndDate_Util.operateTime(map.get(scheduledCheckMonitorItem.getJklbh()),StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0", TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);
			}
		}else if(scheduledCheckMonitorItem.getDw().intValue()==ScheduledEnum.LOOP.getId().intValue()){
			
			//起落循环+需要累加飞机注册表.起落循环
			if(scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_loop_qlj");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				DecimalFormat df = new DecimalFormat("0.00");   
			
				sy=String.valueOf(df.format(Double.parseDouble(map.get(scheduledCheckMonitorItem.getJklbh()))-Double.parseDouble(sjs)-Double.parseDouble(StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0")));
			}else if(scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
				//TS1+需要累加飞机注册表.TS1
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_loop_ts1");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				DecimalFormat df = new DecimalFormat("0.00");   
				sy=df.format(Double.valueOf(map.get(scheduledCheckMonitorItem.getJklbh()))-Double.valueOf(sjs)-Double.valueOf(StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0"));
			
			}else if (scheduledCheckMonitorItem.getJklbh().equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){
				//TS2+需要累加飞机注册表.TS2
				PlaneInitData pd=new PlaneInitData();
				pd.setFjzch(scheduledCheckMonitorItem.getFjzch());
				pd.setInitXmbh("init_loop_ts2");
				pd.setDprtcode(scheduledCheckMonitorItem.getDprtcode());
				PlaneInitData into=planeInitDataService.selectByKey(pd);
				String sjs="";
				if(into==null){
					sjs="0";
				}else{
					sjs=into.getInitValue();
				}
				DecimalFormat df = new DecimalFormat("0.00");   
				sy=df.format(Double.valueOf(map.get(scheduledCheckMonitorItem.getJklbh()))-Double.valueOf(sjs)-Double.valueOf(StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0"));
			}else{
				DecimalFormat df = new DecimalFormat("0.00");  
				sy=df.format(Double.valueOf(map.get(scheduledCheckMonitorItem.getJklbh()))-Double.valueOf(StringUtils.isNotBlank(scheduledCheckMonitorItem.getSj())?scheduledCheckMonitorItem.getSj():"0"));
			}
		}
		 }
		if(sy==null){
			sy="";
		}
		scheduledCheckMonitorItem.setSy(sy);
		sy=scheduledCheckMonitorItem.getMs()+":"+sy;
		return sy;
	}
	
	/**
	 * 计算计划值
	 */
	@Override
	public String jihua(ScheduledCheckMonitorItem scheduledCheckMonitorItem)
			throws Exception {
		String jh=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isEmpty(scheduledCheckMonitorItem.getJhz())) {
			jh = "";
		} else {
			if (scheduledCheckMonitorItem.getCsbj() == 1) {
				jh = scheduledCheckMonitorItem.getJhz();
			} else {
				if (scheduledCheckMonitorItem.getDw().intValue() == ScheduledEnum.calendar.getId().intValue()) {
					Date date = sdf.parse(scheduledCheckMonitorItem.getJhz());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int b = scheduledCheckMonitorItem.getZqz().intValue();
					cal.add(Calendar.DATE, b);
					date = cal.getTime();
					jh = new SimpleDateFormat("yyyy-MM-dd").format(date);

				} else if (scheduledCheckMonitorItem.getDw().intValue() == ScheduledEnum.DAY.getId().intValue()) {
					Date date = sdf.parse(scheduledCheckMonitorItem.getJhz());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int b = scheduledCheckMonitorItem.getZqz().intValue();
					cal.add(Calendar.DATE, b);
					date = cal.getTime();
					jh = new SimpleDateFormat("yyyy-MM-dd").format(date);

				} else if (scheduledCheckMonitorItem.getDw().intValue() == ScheduledEnum.MOON.getId().intValue()) {
					Date date = sdf.parse(scheduledCheckMonitorItem.getJhz());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int b = scheduledCheckMonitorItem.getZqz().intValue();
					cal.add(Calendar.MONTH, b);
					date = cal.getTime();
					jh = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else if (scheduledCheckMonitorItem.getDw().intValue() == ScheduledEnum.YEAR.getId().intValue()) {
					Date date = sdf.parse(scheduledCheckMonitorItem.getJhz());
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int b = scheduledCheckMonitorItem.getZqz().intValue();
					cal.add(Calendar.YEAR, b);
					date = cal.getTime();
					jh = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else if (scheduledCheckMonitorItem.getDw().intValue() == ScheduledEnum.TIME.getId().intValue()) {
					if (scheduledCheckMonitorItem.getJhz() == null) {
						scheduledCheckMonitorItem.setJhz("0");
					}
					jh = StringAndDate_Util.operateTime(scheduledCheckMonitorItem.getJhz(),
							scheduledCheckMonitorItem.getZqz().toString(), TimeSeparatorEnum.COLON,
							TimeOperationEnum.ADD);

				} else if (scheduledCheckMonitorItem.getDw().intValue() == ScheduledEnum.LOOP.getId().intValue()) {

					if (scheduledCheckMonitorItem.getJhz() == null) {
						scheduledCheckMonitorItem.setJhz("0");
					}
					BigDecimal ss = new BigDecimal(scheduledCheckMonitorItem.getJhz());
					BigDecimal sss = ss.add(scheduledCheckMonitorItem.getZqz());
					jh = sss.toString();
				}
			}
		 
		}
		//更新各个定检项，周期值，剩余天数，日使用量
		scheduledCheckMonitorItem.setCurJh(jh);
				
		scheduledCheckMonitorItem.setJshz(jh);

		return null != jh ? jh : "";
	}

	@Override
	public Map<String, Object> checkUpdMt(User user, String ids)throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		ScheduledCheckItem scheduledCheckItem=new ScheduledCheckItem();
		//scheduledCheckItem.setId(ids);
		String[] idnums=new String[0];
		if(!ids.equals("")){
			idnums = ids.split(",");//勾选的id集合
		}
		for (int i = 0; i < idnums.length; i++) {
			scheduledCheckItem.setId(idnums[i]);
			List<ScheduledCheckItem> list= scheduledCheckItemMapper.queryAllPageList1(scheduledCheckItem);
			if(list.get(0).getRwdh()==null&&list.get(0).getWhsj1()==null&&list.get(0).getWhrid1()==null){
				returnMap.put("state", "success");
			}else{
				returnMap.put("state", "error");
				returnMap.put("message", "任务单已存在不可提交!");
				return returnMap;
			}
		}

		return returnMap;
	}

	@Override
	public Map<String, Object> saveScheduledcheckitem(String ids) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		ScheduledCheckItem scheduledCheckItem=new ScheduledCheckItem();
		try {
			String[] idnums=new String[0];
			if(!ids.equals("")){
				idnums = ids.split(",");//勾选的id集合
			}
			for (int i = 0; i < idnums.length; i++) {
				scheduledCheckItem.setId(idnums[i]);
				List<ScheduledCheckItem> list=scheduledCheckItemMapper.queryAllPageList1(scheduledCheckItem);
				checkTaskBillService.save(list.get(0));//save b_g_014  定检任务主表
				returnMap.put("state", "success");
			}
		} catch (Exception e) {
			returnMap.put("state", "error");
		}
		return returnMap;
	}

	/**
	 * 更新定检项目
	 */
	@Override
	public void updateEditable(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		// 当前登录用户
		User user = ThreadVarUtil.getUser();
		// 待同步
		item.setTbbs(SynchronzeEnum.TO_DO.getCode());
		// 维护人id
		item.setWhrid(user.getId());
		// 维护单位id
		item.setWhdwid(user.getBmdm());
		// 维护时间
		item.setWhsj(new Date());
		// 更新定检项目
		scheduledCheckItemEditableMapper.updateByPrimaryKeySelective(item);
		// 插入日志
		commonRecService.write(item.getId(), TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.UPDATE, item.getZjqdid());
	}

	/**
	 * 级联删除定检项目
	 */
	@Override
	public void cascadeDeleteByZjqdid(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum)
			throws RuntimeException {
		// 状态
		item.setZt(0);
		// 同步标识
		item.setTbbs(SynchronzeEnum.TO_DO.getCode());
		// 设置默认值
		setDefaultValue(item);
		// 更新状态为0
		scheduledCheckItemEditableMapper.cascadeDeleteByZjqdid(item);
		// 插入日志
		commonRecService.write(item.getId(), TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, item.getZjqdid());
	}
	
	/**
	 * 根据装机清单id删除定检项目
	 */
	@Override
	public void deleteByZjqdid(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum)
			throws RuntimeException {
		// 状态
		item.setZt(0);
		// 同步标识
		item.setTbbs(SynchronzeEnum.TO_DO.getCode());
		// 设置默认值
		setDefaultValue(item);
		// 更新状态为0
		scheduledCheckItemEditableMapper.deleteByZjqdid(item);
		// 插入日志
		List<String> keys = new ArrayList<String>();
		keys.add(item.getZjqdid());
		commonRecService.write("zjqdid", keys, TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, item.getZjqdid());
	}

	@Override
	public ScheduledCheckItem selectXlh(ScheduledCheckItem sc)
			throws RuntimeException {
		return scheduledCheckItemEditableMapper.selectXlh(sc);
	}

	@Override
	public void updatePlaneEditable(ScheduledCheckItem scheduledCheckItem1) {
		
		scheduledCheckItemMapper.updatePlaneEditable(scheduledCheckItem1);
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public Map<String, Object> saveJkbz(ScheduledCheckItem scheduledCheckItem)
			throws RuntimeException {
		//更新BS00303表监控备注
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		 scheduledCheckItemMapper.updatePlaneEditable(scheduledCheckItem);
		
		try {
			 List<ScheduledCheckItem> list=	scheduledCheckItemMapper.queryAllPageList1(scheduledCheckItem);
			if(list.get(0).getRwid()!=null&&!list.get(0).getRwid().equals("")){
				 ScheduledTask scheduledTask=  scheduledTaskSerivce.queryKey(list.get(0).getRwid());
				 scheduledTask.setBz(scheduledCheckItem.getJkbz());
				 scheduledTaskSerivce.update(scheduledTask);//更新BS009表备注
			
				 CheckTaskBill CheckTaskBill=checkTaskBillService.selectByPrimaryKey(scheduledTask.getXggdid());
				 CheckTaskBill.setJkbz(scheduledCheckItem.getJkbz());//更新BS014监控备注
				 checkTaskBillService.updateByPrimaryKeySelective1(CheckTaskBill);
				 
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 
		 returnMap.put("state", "success");
		 return returnMap;
	}

	@Override
	public List<ScheduledCheckItem> queryAllPageList1(ScheduledCheckItem scheduledCheckItem) throws Exception {
		return scheduledCheckItemMapper.queryAllPageList1(scheduledCheckItem);
	}

	@Override
	public Map<String, Object> doQuerySched(ScheduledCheckItem scheduledCheckItem,User user) throws BusinessException {
		
		String dprtcode = scheduledCheckItem.getDprtcode();
		Date jzrq = scheduledCheckItem.getJzrq();
		String fjzch = scheduledCheckItem.getFjzch();
		//当前时间
		Date currentDate = commonService.getSysdate();
		ServiceInstruction param = new ServiceInstruction();
		param.setZllx(SrvInstructEnum.SCJHYP.getId());
		param.setDprtcode(dprtcode);
		param.setYwrq(jzrq);
		param.setYwdx(fjzch);
		ServiceInstruction instructionInDb  = serviceInstructionMapper.selectOne(param);
		//有效期 
		Monitorsettings term4Validity = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.YXX_YPJH_YXQ.getName(), dprtcode);
		if (term4Validity == null) {
			throw new BusinessException("未初始化预排有效期");
		}
		
		//最大期
		Monitorsettings maximumPeriod =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.YXX_YPJH_MAX.getName(), dprtcode);
		if (maximumPeriod == null) {
			throw new BusinessException("未初始化预排最大期");
		}
		
		if (instructionInDb!=null) {
			//找到了服务指令
			Boolean inLife = inLifecycle(instructionInDb.getZxpc(),currentDate,dprtcode,term4Validity);
			if(inLife){
				//在有效期内，按用户截至日期查数据.
				return querySched4Page(scheduledCheckItem, instructionInDb.getId());
			}
			else{
				//写指令，写数据，查一页
				//不在有效期内，按用户截至日期查数据
				Date duedate = calDueDate(instructionInDb.getYwrq(),currentDate,jzrq,dprtcode,maximumPeriod);
				scheduledCheckItem.setJzrq(duedate);
				return doSched(scheduledCheckItem, user, currentDate,term4Validity,maximumPeriod);
			}
		}
		else{
			return doSched(scheduledCheckItem, user, currentDate,term4Validity,maximumPeriod);
		}
		 
	}

	/**
	 * 查询一页预排数据
	 * @param scheduledCheckItem
	 * @param jzrq
	 * @param instructionInDb
	 * @return
	 */
	private Map<String, Object> querySched4Page(ScheduledCheckItem scheduledCheckItem,String zlid) {
		PageHelper.startPage(scheduledCheckItem.getPagination());
		List<ProductPlanSched> rows = querySchedList(scheduledCheckItem, zlid);
		return PageUtil.pack4PageHelper(rows, scheduledCheckItem.getPagination());
	}

	@Override
	public List<ProductPlanSched> querySchedList(ScheduledCheckItem scheduledCheckItem, String zlid) {
		ProductPlanSched productPlanSched = new ProductPlanSched();
		productPlanSched.setZlid(zlid);
		if (scheduledCheckItem.getJzrq()!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(scheduledCheckItem.getJzrq());
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			
			productPlanSched.getParamsMap().put("jzrq", cal.getTime());
		}
		 
		productPlanSched.setKeyword(scheduledCheckItem.getKeyword());
		List<ProductPlanSched>rows = productPlanSchedMapper.selectList(productPlanSched);
		if (rows!=null && !rows.isEmpty()) {
			for (ProductPlanSched row : rows) {
				row.setJkxmbhF(MonitorItemEnum.getName(row.getJkxmbhF()));
				row.setJkxmbhS(MonitorItemEnum.getName(row.getJkxmbhS()));
				row.setJkxmbhT(MonitorItemEnum.getName(row.getJkxmbhT()));
			}
		}
		return rows;
	}

	/**
	 * 写指令，写预排，查数据
	 * @param scheduledCheckItem
	 * @param user
	 * @param userJgdm
	 * @param dprtcode
	 * @param jzrq
	 * @param currentDate
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> doSched(ScheduledCheckItem scheduledCheckItem, User user ,
			Date currentDate,Monitorsettings term4Validity,Monitorsettings maximumPeriod) throws BusinessException {
		//没找到了服务指令，写指令，写数据，查一页
		//写指令
		//备注：有效期 + 最大器
		String zlid = UUID.randomUUID().toString();
		saveSched(scheduledCheckItem, zlid,user, currentDate,term4Validity, maximumPeriod);
		return querySched4Page(scheduledCheckItem, zlid);
	}

	/**
	 * 保存指令和预排数据
	 * @param scheduledCheckItem
	 * @param user
	 * @param userJgdm
	 * @param dprtcode
	 * @param jzrq
	 * @param currentDate
	 * @param term4Validity
	 * @param maximumPeriod
	 * @throws BusinessException
	 */
	@Override
	public void saveSched(ScheduledCheckItem scheduledCheckItem,String zlid, User user,
			 Date currentDate, Monitorsettings term4Validity, Monitorsettings maximumPeriod)
			throws BusinessException {
		
		String userId = user!=null?user.getId():null;
		String userJgdm = user!=null?user.getJgdm():null;
		StringBuffer bz = new StringBuffer();
		bz.append("有效期:");
		bz.append(term4Validity.getYjtsJb1());
		bz.append(" 最大范围:");
		bz.append(maximumPeriod.getYjtsJb1());
		//单位 天数-预警级别2：（11天、12月、13年）
		bz.append(" 最大范围单位:");
		bz.append(maximumPeriod.getYjtsJb2());
		 
		ServiceInstruction record = new ServiceInstruction();
		record.setId(zlid);
		record.setDprtcode(scheduledCheckItem.getDprtcode());
		record.setYwdx(scheduledCheckItem.getFjzch());
		record.setYwrq(scheduledCheckItem.getJzrq());
		record.setZddwid(userJgdm);
		record.setZdrid(userId);
		record.setZdsj(currentDate);
		record.setZllx(SrvInstructEnum.SCJHYP.getId());
		String zxpc = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE_TIME, currentDate);
		record.setZxpc(zxpc);
		record.setZt(BinaryEnum.YES.getId().shortValue());
		record.setBz(bz.toString());
		serviceInstructionMapper.insertSelective(record);
		
		//在内存中生成预排
		List<ScheduledCheckItem> list = calSched(scheduledCheckItem);
		List<ProductPlanSched> plist = new ArrayList<ProductPlanSched>();
		ScheduledCheckMonitorItem monitorItem0 = null;
		ScheduledCheckMonitorItem monitorItem1 = null;
		ScheduledCheckMonitorItem monitorItem2 = null;
		List<ScheduledCheckMonitorItem> monitorItemList = null;
		
		//将内存中的预判以500条每次写入db
		if (list!= null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				
				if (list.get(i).getMonitorItemList()!=null) {
					monitorItemList = list.get(i).getMonitorItemList();
					if (monitorItemList.size()>0) {
						monitorItem0 = monitorItemList.get(0);
					}
					if (monitorItemList.size()>1) {
						monitorItem1 = monitorItemList.get(1);
					}
					if (monitorItemList.size()>2) {
						monitorItem2 = monitorItemList.get(2);
					}
				}
				
				ProductPlanSched productPlanSched = new ProductPlanSched();
				productPlanSched.setId(UUID.randomUUID().toString());
				productPlanSched.setZlid(record.getId());
				productPlanSched.setDprtcode(scheduledCheckItem.getDprtcode());
				try {
					productPlanSched.setZxrq(DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE_TIME, list.get(i).getJhzxrq()));
				} catch (Exception e) {
					throw new BusinessException("日期格式化错误！",e);
				}
				
				productPlanSched.setGkid(list.get(i).getDjxmid());//定检项目ID
				productPlanSched.setBjid(list.get(i).getBjid());
				productPlanSched.setFjzch(list.get(i).getFjzch());
				productPlanSched.setZjqdid(list.get(i).getZjqdid());
				productPlanSched.setBjh(list.get(i).getJh());
				productPlanSched.setBjxlh(list.get(i).getXlh());
				
				productPlanSched.setJkxmbhF(monitorItem0.getJklbh());
				productPlanSched.setJkflbhF(monitorItem0.getJkflbh());
				productPlanSched.setJkzF(monitorItem0.getCurJh());
				
				productPlanSched.setJkxmbhS(monitorItem1.getJklbh());
				productPlanSched.setJkflbhS(monitorItem1.getJkflbh());
				productPlanSched.setJkzS(monitorItem1.getCurJh());
				
				productPlanSched.setJkxmbhT(monitorItem2.getJklbh());
				productPlanSched.setJkflbhT(monitorItem2.getJkflbh());
				productPlanSched.setJkzT(monitorItem2.getCurJh());
				productPlanSched.setZt(BinaryEnum.YES.getId().shortValue());
				productPlanSched.setZdsj(currentDate);
				
				plist.add(productPlanSched);
				if (i>=0 && (i == (list.size()-1) || (i % 500==0))) {
					productPlanSchedMapper.insertList(plist);
					plist.clear();
				}
			}
		}
	}

	/**
	 * 计算截至日期：先将指令预排日期与设定预排最大期取小，在将该值与用户日期取大
	 * @param ywrq 指令预排日期
	 * @param currentDate 当前日期
	 * @param dueDate4User 截止日期
	 * @param dprtcode 机构
	 * @return
	 * @throws BusinessException 
	 */
	private Date calDueDate(Date ywrq, Date currentDate,Date dueDate4User,String dprtcode,Monitorsettings monitorsettings) throws BusinessException {
		Date jzrq = getMaxDate(currentDate, monitorsettings);
		//指令预排日期与设定预排最大期取小
		if (jzrq.compareTo(ywrq)>=0) {
			jzrq = ywrq;
		}
		
		//结果和用户截至日期取大
		if (dueDate4User.compareTo(jzrq)>=0) {
			jzrq = dueDate4User;
		}
		
		return jzrq;
	}

	public Date getMaxDate(Date currentDate, Monitorsettings monitorsettings) {
		//周期
		int amount = monitorsettings.getYjtsJb1();
		//单位 天数-预警级别2：（11天、12月、13年）
		int amountBit = monitorsettings.getYjtsJb2();
		int field = ShedLifeUnit.getName(amountBit);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(field, amount);
		//最大期
		Date maxDate = cal.getTime();
		Date jzrq = maxDate;
		return jzrq;
	}

	/**
	 * 检查指令的批次是否有效
	 * @param zxpc
	 * @return
	 * @throws ParseException 
	 */
	private boolean inLifecycle(String zxpc,Date currentDate,String dprtcode,Monitorsettings monitorsettings) throws BusinessException {
		try {
			Date maxdateInLife = maxDateInLife(zxpc, monitorsettings);
			//当前时间 < 极限生存时间 ，有效，否则无效
			return currentDate.getTime() < maxdateInLife.getTime()?Boolean.TRUE:Boolean.FALSE;
		} catch (Exception e) {
			throw new BusinessException("日期计算出错",e);
		}
		
	}

	public Date maxDateInLife(String zxpc, Monitorsettings monitorsettings) throws ParseException {
		//有效期 
		int amount = monitorsettings.getYjtsJb1();
		Date maxZxpc = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE_TIME, zxpc);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(maxZxpc); 
		cal.add(Calendar.MINUTE, amount);
		//极限生存时间
		Date maxdateInLife = cal.getTime();
		return maxdateInLife;
	}

	@Override
	public  void batchUpdateSched() throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		/**
		 * 1.有服务指令的取最大的重算一遍。
		 * 2.没服务指令的（没查询过的）全部以最大期作为截至日期重算。
		 */
		Date currentDate = commonService.getSysdate();
		List<ServiceInstruction> list = serviceInstructionMapper.selectList4Planes();
		Map<String, Monitorsettings>validityMap = null;
		Map<String, Monitorsettings>maximumPeriodMap = null;
		List<String>dprtcodes = new ArrayList<String>();
		if (list!=null && !list.isEmpty()) {
			//获取服务指令机构
			for (ServiceInstruction serviceInstruction : list) {
				if (!dprtcodes.contains(serviceInstruction.getDprtcode())) {
					dprtcodes.add(serviceInstruction.getDprtcode());
				}
			}
			
			//根据机构查询有效期和最大期
			//有效期 
			validityMap = monitorsettingsService.getByKeyInDprtcodes(ThresholdEnum.YXX_YPJH_YXQ.getName(), dprtcodes);
			if (validityMap == null || validityMap.isEmpty()) {
				throw new BusinessException("未初始化预排有效期");
			}
			//最大期
			maximumPeriodMap =monitorsettingsService.getByKeyInDprtcodes(ThresholdEnum.YXX_YPJH_MAX.getName(), dprtcodes);
			if (maximumPeriodMap == null||maximumPeriodMap.isEmpty()) {
				throw new BusinessException("未初始化预排最大期");
			}
			
			//删除所有预排i数据
			serviceInstructionMapper.deleteAll4Sched( );
			productPlanSchedMapper.deleteAll( );
			
			//按最新的指令重新生成数据
			for (ServiceInstruction serviceInstruction : list) {
				Monitorsettings term4Validity = validityMap.get(serviceInstruction.getDprtcode());
				Monitorsettings maximumPeriod = maximumPeriodMap.get(serviceInstruction.getDprtcode());
				//没有业务日期，的就以最大期作为截至日期
				if (serviceInstruction.getYwrq()==null) {
					serviceInstruction.setYwrq(getMaxDate(currentDate,maximumPeriod));
				}
				ScheduledCheckItem scheduledCheckItem = new ScheduledCheckItem();
				scheduledCheckItem.setFjzch(serviceInstruction.getYwdx());
				scheduledCheckItem.setJzrq(serviceInstruction.getYwrq());
				scheduledCheckItem.setDprtcode(serviceInstruction.getDprtcode());
				
				this.saveSched(scheduledCheckItem, UUID.randomUUID().toString(), null, currentDate, term4Validity, maximumPeriod);
			}
		}
	}

	@Override
	public List<ProductPlanSched> querySchedAll(ScheduledCheckItem scheduledCheckItem) {
		List<ProductPlanSched> rows = null;
		ServiceInstruction param = new ServiceInstruction();
		param.setZllx(SrvInstructEnum.SCJHYP.getId());
		param.setDprtcode(scheduledCheckItem.getDprtcode());
		param.setYwrq(scheduledCheckItem.getJzrq());
		param.setYwdx(scheduledCheckItem.getFjzch());
		ServiceInstruction instructionInDb  = serviceInstructionMapper.selectOne(param);
		if (instructionInDb != null) {
			rows = querySchedList(scheduledCheckItem, instructionInDb.getId());
		}
		else{
			rows = new ArrayList<ProductPlanSched>();
		}
		return rows;
	}

	
}
