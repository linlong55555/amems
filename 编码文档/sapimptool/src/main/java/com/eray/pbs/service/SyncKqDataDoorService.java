package com.eray.pbs.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.SyncKqDataDoorDao;
import com.eray.pbs.dao.SyncKqDataDoorLogDao;
import com.eray.pbs.po.SyncKqDataDoor;
import com.eray.pbs.po.SyncKqDataDoorLog;
import com.eray.pbs.vo.SyncKqDataDoorVO;
import com.eray.util.Global;

/**
 * 同步数据servcie方法 2016.07.21
 * @author ganqing
 *
 */
@Component
@Transactional(readOnly = true)
public class SyncKqDataDoorService {
	
	@Resource(name="jdbcTemplateShift")
	private JdbcTemplate jdbcTemplateShift;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate; //查询模板
	
	@Autowired
	private SyncKqDataDoorDao syncKqDataDoorDao;
	
	@Autowired
	private SyncKqDataDoorLogDao syncKqDataDoorLogDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SyncKqDataDoorService.class);
	
	/**
	 * 获得所有的IN&OUT原始数据
	 * @return List<SyncKqDataDoorVO>
	 */
	public List<SyncKqDataDoorVO> getSyncKqDataDoorVO(long maxRecId) {
		try {
			logger.info("The maxRecId:{}",maxRecId);
			StringBuffer sql = new StringBuffer();
			sql.append("select RecID as recID,EmpID as empID,EmpNo as EmpNo,");
			sql.append("Kqday as kqday,DevID as devID,DevDesp as devDesp,");
			sql.append("UPPER(InOut) as inout,IsSync as isSync ");
			sql.append("from Sync_KqData_Door ");
			if (maxRecId > 0) {
				sql.append(" where RecID > ").append(maxRecId);
			}
			sql.append(" and empID is not null ");
			sql.append(" ORDER BY RecID ASC");
			RowMapper<SyncKqDataDoorVO> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(SyncKqDataDoorVO.class);
			logger.info("getSyncKqDataDoorVO sql:{}" ,sql.toString());
			return jdbcTemplateShift.query(sql.toString(), rowMapper);
		} catch (DataAccessException e) {
			System.out.println("DataAccessException:" + e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 更新Sync_KqData_Door状态码
	 * @param resids 
	 */
	public void updateSyncKqDataDoor(String resids){
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update Sync_KqData_Door set IsSync = '1' ");
			sql.append("where RecID in(").append(resids).append(")");
			jdbcTemplateShift.update(sql.toString());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获得最大的已经同步设备ID
	 * @return long
	 */
	public long getMaxrecID(){
		Long maxRecId = syncKqDataDoorLogDao.getMaxrecID();
		return maxRecId == null ? 0 : maxRecId;
	}
	/**
	 * 保存原始数据至pbs系统 2016.07.21
	 */
	@Transactional(readOnly = false)
	public void addSyncKqDataDoorVO() {
		try {
			long maxRecId = this.getMaxrecID();
			List<SyncKqDataDoorVO> list = this.getSyncKqDataDoorVO(maxRecId);
			logger.info("get back datas.");
			List<SyncKqDataDoor> doors = new ArrayList<SyncKqDataDoor>();
			long maxrecID = 0;
			StringBuffer recIDS = new StringBuffer();
			if (list != null && list.size() > 0) {
				maxrecID = list.get(list.size()-1).getRecID();
				for (SyncKqDataDoorVO vo : list) {
				//	logger.info("original data:{}", vo);
					SyncKqDataDoor door = new SyncKqDataDoor();
					door.setRecID(vo.getRecID());
					door.setDevDesp(vo.getDevDesp());
					door.setEmpID(vo.getEmpNo()); //用户数据设计与pbs相反GQ
					door.setEmpNo(vo.getEmpID()); //同上
					door.setShiftdate(vo.getKqday());
					door.setType(vo.getInout());
					door.setDevID(vo.getDevID());
					door.setHandleStatus(Global.NO);
					doors.add(door);
					logger.info("before insert into DB: {}" , door);
					recIDS.append(vo.getRecID()).append(",");
				}
				if (doors.size() >0) {
					syncKqDataDoorDao.save(doors);
				}
				if (recIDS.length() > 0) { //有数据更新
					logger.info("The key:{}" , recIDS);
					recIDS  = recIDS.deleteCharAt(recIDS.length() - 1);
					SyncKqDataDoorLog log = new SyncKqDataDoorLog(recIDS.toString(), maxrecID, Global.YES);
					syncKqDataDoorLogDao.save(log); //同步日志日志信息
					logger.info("SyncKqDataDoorLog log:{}" , log);
					recIDS = null;
				} 
			} else { //没有同步到数据的处理,更新SyncKqDataDoorLog的update字段
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE pbs_synckqdatadoor_log SET lastupdate_ = getDate() WHERE id_=(");
				sql.append("SELECT max(id_) FROM pbs_synckqdatadoor_log");
				sql.append(")");
				//sql.append("where RecID in(").append(resids).append(")");
				jdbcTemplate.update(sql.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			e.printStackTrace();
		    logger.error("system error.");
		}
	}
	
	

}
