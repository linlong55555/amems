package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToCreditRecord;

public interface PersonnelToCreditRecordService {

	/**
	 * 保存事故征候情况
	 * @param personnel
	 * @return
	 */
	void saveIncidentSituation(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 保存负有责任的不安全事件
	 * @param personnel
	 * @return
	 */
	void saveUnsafeIncident(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 保存不诚信行为
	 * @param personnel
	 * @return
	 */
	void saveDishonestBehaviors(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 查询事故征候情况
	 * @param id
	 * @return
	 */
	List<PersonnelToCreditRecord> queryIncidentSituations(String id);
	
	/**
	 * 查询负有责任的不安全事件
	 * @param id
	 * @return
	 */
	List<PersonnelToCreditRecord> queryUnsafeIncidents(String id);
	
	/**
	 * 查询不诚信行为
	 * @param id
	 * @return
	 */
	List<PersonnelToCreditRecord> queryDishonestBehaviors(String id);
}
