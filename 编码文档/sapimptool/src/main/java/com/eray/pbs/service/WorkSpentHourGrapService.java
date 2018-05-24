package com.eray.pbs.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.vo.AircraftWorkHour;
import com.eray.pbs.vo.WorkSpentHourGrapVo;
import com.eray.util.format.FormatUtil;

/**
 * 从派卡表中抓抓取当天的派卡计时数据并回写至工单信息表中 2017.1.10
 * @author ganqing
 *
 */
@Component
@Transactional(readOnly = true)
public class WorkSpentHourGrapService {

	private static final Logger logger = LoggerFactory.getLogger(WorkSpentHourGrapService.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate; //查询模板
	
	@Autowired
	private WorkPbsDao workPbsDao; //工单信息 
	
	/**
	 * 检索当前时间下是否有派卡信息，有的话更新当前天的工单
	 */
	@Transactional(readOnly = false)
	public void wirteSpentHour() {
		logger.info("spent hour write back.");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(W.spentHour) AS spentHour, W.wid as wid ");
		sql.append("FROM AircraftWorkHourConfirmDetailView W ");
		sql.append("WHERE W.wid in(").append(this.getMarkDateWid()).append(") ");
		sql.append(" and W.subStatus = '1' ");
		sql.append(" GROUP BY W.wid HAVING SUM(W.spentHour) > 0 ");
		logger.info("The wirteSpentHour SQL:{}",sql.toString());
		
		RowMapper<WorkSpentHourGrapVo> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(WorkSpentHourGrapVo.class);
		List<WorkSpentHourGrapVo> list = jdbcTemplate.query(sql.toString(), rowMapper);
		if (list != null && list.size() > 0) {
			for (WorkSpentHourGrapVo v : list) {
				WorkPbs w = workPbsDao.findByWid(v.getWid());
				logger.info("before update spent hour:{}", w);
				if (w != null) {
					BigDecimal t = new BigDecimal(v.getSpentHour()).setScale(3, BigDecimal.ROUND_HALF_UP);
					logger.info("work:{},statistics spent hour:{}", new Object[]{w.getWid(), t});
					if (w.getActualHours() == null) { //spent hour为空则直接写入
						w.setActualHours(t);
						w.setActualHourChanged("1");
						workPbsDao.save(w);
					} else {
						if(t.compareTo(w.getActualHours()) != 0) {
							w.setActualHours(t);
							w.setActualHourChanged("1");
							workPbsDao.save(w);
						}
					}
					logger.info("after update spent hour one :{}", w);
				}
			}
		}
	}
	/**
	 * 获得今天打卡的所有工单编号
	 * @param markDate
	 * @return
	 */
	private String getMarkDateWid() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT(wid) FROM AircraftWorkHourConfirmDetailView where groupByDate='");
		sql.append(FormatUtil.formatDate(new Date(), "yyyyMMdd")).append("'");
		return sql.toString();
	}
	
}
