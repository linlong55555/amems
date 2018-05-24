package com.eray.pbs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.SpentHrsTotalBackSAPLogDao;
import com.eray.pbs.po.SpentHrsTotalBackSAPLog;
import com.eray.pbs.vo.RevisionSpentHrs;
import com.eray.util.date.DateCalculation;
import com.eray.util.format.FormatUtil;

/**
 * 按月回传spent hrs
 * @author ganqing
 *
 */
@Service
@Transactional(readOnly = true)
public class SpentHrsTotalBackSAPLogService {
	/**
	 * 按工包的按每月的凌晨6点回传数据
	 */
	@Autowired
	private SpentHrsTotalBackSAPLogDao spentHrsTotalBackSAPLogDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SpentHrsTotalBackSAPLogService.class);
	
	/**
	 * 查询模板
	 */
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 1.每个月有spent hrs的工包（不管状态),这样的工包要取出来 2016.08.08
	 * 2.如果是月中关闭的工单，不符合1,有comlete操作状态的工单与之关联的工包要取出来。
	 * 只回传本月有book on&或者book off的工单
	 * 如：9月1号6:00回传8月1号6:00至9月1号6:00之间状态为非complete的数据
	 * @return List<RevisionSpentHrs> 获得某个公包下未关闭工单的总的spent hrs总工时
	 */
	public List<RevisionSpentHrs> getSpentHrs() {
		Date currentDate = new Date();	//当前时间
		Date lastMonthDate  = DateCalculation.getLastMonth(currentDate, -1);//上个月的这个时候
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT rev.rid_ as rid,SUM (emp.spenthours_) as totalHrs ");
		sql.append("from pbs_revision rev,pbs_empworkdetail emp,pbs_empwork epw ");
		sql.append("WHERE rev.rid_ = emp.rid_ AND epw.rid_ = rev.rid_  and (");
		sql.append(" epw.bookondate_ >= '").append(FormatUtil.formatDate(lastMonthDate, "yyyy-MM-dd")).append(" 06:00'");
		sql.append(" and epw.bookondate_ < '").append(FormatUtil.formatDate(currentDate, "yyyy-MM-dd")).append(" 06:00'");
		sql.append(" )"); //epw.bookondate_ < '2016-09-01 06:00' and epw.bookondate_ >= '2016-08-01 06:00'
		sql.append("AND epw.workover_ = '0' ");
		sql.append("GROUP BY rev.rid_");
		logger.info("get getSpentHrs SQL:{}", sql.toString());
		RowMapper<RevisionSpentHrs> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(RevisionSpentHrs.class);
		List<RevisionSpentHrs> resultlist = jdbcTemplate.query(sql.toString(), rowMapper);
		return resultlist;
	}
	
	/**
	 * 获得某个或某几个工包下历史回传的total数据
	 * @param set 工包数据集合
	 * @return List<RevisionSpentHrs>
	 */
	private List<RevisionSpentHrs> getHistorySpentHrs(Set<String> set) {
		StringBuilder sql = new StringBuilder();
		sql.append("select rid_ as rid, SUM (backsaphrs_) as totalHrs ");
		sql.append(" from pbs_spenthrstotalbacksap_log ");
		sql.append(" where rid_ in ('").append(this.getRids(set)).append("')");
		RowMapper<RevisionSpentHrs> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(RevisionSpentHrs.class);
		return jdbcTemplate.query(sql.toString(), rowMapper);
	}
	
	/**
	 * 拼装rid编号
	 * @param set rid编号集合
	 * @return 工包编号
	 */
	private String getRids(Set<String> set) {
		StringBuilder rids = new StringBuilder();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			rids.append(iter.next()).append("'");
			
		}
		rids = rids.deleteCharAt(rids.length() -1);
		return  rids.toString();
	}
	
	/**
	 * 获得工包的编号
	 * @param hrs 工包编号集合
	 * @return Set<String>
	 */
	private Set<String> getRids(List<RevisionSpentHrs> hrs) {
		Set<String> set = new HashSet<String>();
		if (hrs != null && hrs.size() > 0) {
			for (RevisionSpentHrs hr : hrs) {
				set.add(hr.getRid());
			}
		}
		return set;
	}
	/**
	 * 获得currentHr在historyHrs的索引位
	 * @param currentHr 当前待处理的hrs
	 * @param historyHrs
	 * @return
	 */
	private Integer getIndex(RevisionSpentHrs currentHr, List<RevisionSpentHrs> historyHrs) {
		for (int i=0 ;i < historyHrs.size(); i++) {
			if (currentHr.equals(historyHrs.get(i).getRid())) {
				return i;
			}
		} 
		return null;
	}
	
	/**
	 * 获得回传SAP的数据
	 * @return List<SpentHrsTotalBackSAPLog>
	 */
	@Transactional(readOnly = false)
	public List<SpentHrsTotalBackSAPLog> backDatasToSAP() {
		logger.info("开始回传数据操作");
		List<RevisionSpentHrs> hrs = this.getSpentHrs();
		List<SpentHrsTotalBackSAPLog> logs = new ArrayList<SpentHrsTotalBackSAPLog>();
		if (hrs != null && hrs.size() > 0) {
			String currentMonth = FormatUtil.formatDate(new Date(), "yyyy-MM"); //格式为2016-04(dangqia)
			String batchNum = UUID.randomUUID().toString() + "_app";
			for (RevisionSpentHrs currentHr : hrs) {
				List<RevisionSpentHrs> historyHrs = this.getHistorySpentHrs(this.getRids(hrs));
				if (historyHrs != null && historyHrs.size() > 0) { //当前的工时减去历史的工时
					Integer index = this.getIndex(currentHr, historyHrs);
					if (index != null) { //找到与之对应的历史数据
						RevisionSpentHrs historyHr = historyHrs.get(index);//得到历史数据
						logs.add(this.getLog(batchNum, currentHr, currentMonth, historyHr));//要处理历史的数据
					} else { //没有与之匹配的数据
						logs.add(this.getLog(batchNum, currentHr, currentMonth,null));
					}				
				} else { //系首次传输
					logs.add(this.getLog(batchNum, currentHr, currentMonth,null));
				}
			}
			spentHrsTotalBackSAPLogDao.save(logs);
		}
		return logs;
	}
	
	/**
	 * 拼装回传的数据
	 * @param batchNum 批次号
	 * @param currentHr 当前时间
	 * @param currentMonth 当前月份
	 * @param historyHr 历史已经回传的数据
	 * @return SpentHrsTotalBackSAPLog拼装的结果
	 * 
	 */
	private SpentHrsTotalBackSAPLog getLog(String batchNum,RevisionSpentHrs currentHr,String currentMonth,RevisionSpentHrs historyHr) {
		logger.info("批次号:{} 当前数据:{} 回传月份:{} 历史数据:{}" ,new Object[]{batchNum, currentHr, currentMonth,historyHr});
		SpentHrsTotalBackSAPLog log = new SpentHrsTotalBackSAPLog();
		log.setBatcNum(batchNum);
		log.setRid(currentHr.getRid());
		log.setSpentMonth(currentMonth);
		log.setTotalhrs(new BigDecimal(currentHr.getTotalHrs()).setScale(2, BigDecimal.ROUND_HALF_UP));
		if (historyHr == null) {
			log.setBacksaphrs(log.getTotalhrs()); //传输所有的工时
		} else {
			log.setBacksaphrs(log.getTotalhrs().subtract(new BigDecimal(historyHr.getTotalHrs()))); //需要减去先前已经回传的数据
		}
		log.setStatisticalDate(new Date());
		logger.info("回传数据" + log);
		return log;
	}
}
