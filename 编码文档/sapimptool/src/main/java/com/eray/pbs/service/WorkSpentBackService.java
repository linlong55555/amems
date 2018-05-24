package com.eray.pbs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.EmpWorkBaseDao;
import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.EmpWorkBase;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.vo.WorkAndOperation;
import com.eray.pbs.vo.WorkSpentBackSAP;
import com.eray.util.Global;
import com.eray.util.format.FormatUtil;

/**
 * 按人按工单返回数据至SAP
 * @author ganqing
 *
 */
@Component
@Transactional(readOnly = true)
public class WorkSpentBackService {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate; //默认的查询模板，查询pbs库表
	
	@Autowired
	private EmpWorkBaseDao empWorkBaseDao; //员工book on & book off信息
	
	private static final Logger logger = LoggerFactory.getLogger(WorkSpentBackService.class);
	@Autowired
	private WorkPbsDao workPbsDao; //工单信息
	
	//@Autowired
	//private OperationPbsDao operationPbsDao; //工序
	//@Autowired
	//private ActivityTypeReWorkCenterDao activityTypeReWorkCenterDao;
	
	/**
	 * 回传至ftp的测试数据 2016,08,18
	 * @return List<WorkSpentBackSAP>
	 */
	public List<WorkSpentBackSAP> testFtpDatas() {
		List<WorkSpentBackSAP> list = new ArrayList<WorkSpentBackSAP>();
		WorkSpentBackSAP back001 = new WorkSpentBackSAP();
		back001.setActivityType("HM1");
		back001.setReservedField(" "); //占位符
		back001.setBookOnDate("18082016");
		back001.setBookOnTime("1545");
		back001.setBookOffDate("18082016");
		back001.setBookOffTime("1610");
		back001.setEmployeeid("00001295");
		back001.setHourUnit("H"); //默认H
		back001.setOperNum("0010"); //默认0010
		back001.setWid("100176772");
		back001.setWorkCenter("B_LGFUS");
		back001.setWorkHour("0.101"); //格式化double
		back001.setLastUnknownWord(" ");
		
		WorkSpentBackSAP back002 = new WorkSpentBackSAP();
		back002.setActivityType("HM1");
		back002.setReservedField(" "); //占位符
		back002.setBookOnDate("18082016");
		back002.setBookOnTime("1540");
		back002.setBookOffDate("18082016");
		back002.setBookOffTime("1605");
		back002.setEmployeeid("00001294");
		back002.setHourUnit("H"); //默认H
		back002.setOperNum("0010"); //默认0010
		back002.setWid("100176773");
		back002.setWorkCenter("BASE");
		back002.setWorkHour("0.300"); //格式化double
		back002.setLastUnknownWord(" ");
	    list.add(back001);
	    list.add(back002);
		return list;
	}
	
	/**
	 * 返回置顶的数据至SAP
	 * @return List<WorkSpentBackSAP>
	 */
	@Transactional(readOnly = false)
	public List<WorkSpentBackSAP> getDatasBackSAP() {
		logger.info("strat to getDatasBackSAP.");
		List<WorkSpentBackSAP> lists = new ArrayList<WorkSpentBackSAP>();
		List<EmpWorkBase>  empworks = empWorkBaseDao.findByBacksapstatusAndWorkOverOrderByToEidAsc(Global.NO, "1"); //所有待回传的数据
		Set<String> set = new HashSet<String>(); //存放工单编号
		if (empworks != null) {
			for (EmpWorkBase base : empworks) {
				logger.info("the all EmpWorkBase:{}",base);
				set.add(base.getWid());
			}
		}
		if (set.size() >0) {
			Iterator<String> it = set.iterator();
			List<EmpWorkBase> sameWorkBase = new ArrayList<EmpWorkBase>();
			while (it.hasNext()) {
				String wid = it.next();
				logger.info("the wid:{}",wid);
				for (EmpWorkBase base : empworks) {
					if (wid.equals(base.getWid())) {
						sameWorkBase.add(base); //将编号相同的工单一起处理
					}
				}
				if (sameWorkBase.size() > 0) {
					//logger.info("sameWorkBase here");
					lists = this.getDatas(lists, wid, sameWorkBase);
					this.updateStatus(sameWorkBase);
					sameWorkBase.clear();
				}
			}
		}
		//this.updateWorkSpentHour(lists); //将结束的spent hour回写工单信息表中
		return lists;
	}
	/**
	 * 回传sap数据的同时，将spent hour数据写入工单信息表中 2017.1.10 (每天都有定时的回写spent hour数据,因此此处没必要重复写入)
	 * @param lists 将回传的工单spent hour写入工单信息表
	 */
	@Transactional(readOnly = false)
	private void updateWorkSpentHour(List<WorkSpentBackSAP> lists) {
		logger.info("updateWorkSpentHour.");
		if (lists != null && lists.size() > 0) {
			for (WorkSpentBackSAP s : lists) {
				WorkPbs w = workPbsDao.findByWid(s.getWid());
				if (w != null) {
					logger.info("BEFORE update work spent success, the work{}",w);
					w.setActualHours(new BigDecimal(s.getWorkHour())); 
					workPbsDao.save(w);
					logger.info("AFTER update work spent success, the work{}",w);
				}
			}
		}
		
	}

	/**
	 * 生成返回SAP的数据
	 * @param lists 返回SAP的数据
	 * @param wid 员工编号
	 * @param sameWorkBase 待处理的集合(可能包含了不同的工单号)
	 * @return
	 */
	private List<WorkSpentBackSAP> getDatas(List<WorkSpentBackSAP> lists,String wid, List<EmpWorkBase> sameWorkBase) {
		logger.info("the getDatas param wid:{}",wid);
		return handleWork(lists, wid, sameWorkBase);
	}
	/**
	 * 将工单号相同的放在一起处理，减少查询工单的次数
	 * @param lists 返回的值
	 * @param wid 工单编号
	 * @param bases 某个员工的同一张工单信息
	 * @return List<WorkSpentBackSAP>
	 */
	private List<WorkSpentBackSAP> handleWork(List<WorkSpentBackSAP> lists, String wid, List<EmpWorkBase> bases) {
		//String rid = bases.get(0).getRid();
		//WorkPbs work = workPbsDao.findByWidAndRid(wid, rid); //获得工单
		//OperationPbs operation = operationPbsDao.findByWidAndOperationNumber(wid, "0010"); //获得工单对应的工序
		
		//这里的工作组取mainworkCenter,只有mainworkcenter不会变，工单的当前workcenter会不停的变化 2016.08.18
		StringBuilder workSql = new StringBuilder();
		workSql.append("select b.mainworkcenter_ as currentWorkCenter ,a.activitytype_  as activityType ");
		workSql.append(" from pbs_activity a,pbs_work b");
		workSql.append(" where a.workcenter_ = b.mainworkcenter_ ");
		workSql.append(" and b.wid_='").append(wid).append("'");
		logger.info("the handleWork first sql:{}",workSql.toString());
		RowMapper<WorkAndOperation> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(WorkAndOperation.class);
		List<WorkAndOperation> act = jdbcTemplate.query(workSql.toString(), rowMapper);
	
		WorkAndOperation workAndOperation = null;
		if (act != null && act.size() > 0) {
			workAndOperation = act.get(0);
		}
		logger.info("the workAndOperation:{}",workAndOperation);
		//DecimalFormat df = new DecimalFormat("######0.000"); //格式化字符串
		for (EmpWorkBase base : bases) {
			if (base.getBookOnDate() == null || base.getBookOffDate() == null) { //时间不完整，直接过滤掉，不予回传
				logger.info("This data will not back SAP:{}",base);
				continue;
			}
			StringBuilder sql = new StringBuilder();
			sql.append("select SUM(spenthours_) from pbs_empworkdetail o where o.empworkid_ in (").append(base.getId()).append(")");
			
			logger.info("the handleWork second sql:{}",sql.toString());
			
			Double total = jdbcTemplate.queryForObject(sql.toString(), Double.class);
			if (total == null) {
				total = 0.0;
			} 
			logger.info("the total:{}",total);
			Date bookOnDate = bases.get(0).getBookOnDate();
			Date bookOffDate = bases.get(bases.size() - 1).getBookOffDate();
			WorkSpentBackSAP back = new WorkSpentBackSAP();
			back.setActivityType(workAndOperation != null ?(workAndOperation.getActivityType() == null ?"" : workAndOperation.getActivityType()):"");
			back.setReservedField(" "); //占位符
			back.setBookOnDate(FormatUtil.formatDate(bookOnDate, "ddMMyyyy"));
			back.setBookOnTime(FormatUtil.formatDate(bookOnDate, "HHmm"));
			back.setBookOffDate(FormatUtil.formatDate(bookOffDate, "ddMMyyyy"));
			back.setBookOffTime(FormatUtil.formatDate(bookOffDate, "HHmm"));
			back.setEmployeeid(base.getToEid());
			back.setHourUnit("H"); //默认H
			back.setOperNum("0010"); //默认0010
			back.setWid(wid);
			back.setWorkCenter(workAndOperation != null ?(workAndOperation.getCurrentWorkCenter() == null ?"" : workAndOperation.getCurrentWorkCenter()):"");
			back.setWorkHour(new BigDecimal(total).setScale(3, BigDecimal.ROUND_HALF_UP).toString()); //格式化double
			back.setLastUnknownWord(" ");
			logger.info("the back content:{}",back);
			lists.add(back);
		} 
	    return lists;
	}
	
	/**
	 * 更新处理的标识位
	 * @param sameWorkBase 待更新的数据
	 */
	@Transactional(readOnly = false)
	private void updateStatus(List<EmpWorkBase> sameWorkBase) {
		logger.info("start updateStatus ");
		List<EmpWorkBase> bases = new ArrayList<EmpWorkBase>();
		for (EmpWorkBase emp : sameWorkBase) {
			logger.info("brfore update EmpWorkBase status:{}",emp);
			emp.setBacksapstatus(Global.YES);
			logger.info("after update EmpWorkBase status:{}",emp);
			bases.add(emp);
		}
		empWorkBaseDao.save(bases);
		bases.clear();
	}
}
