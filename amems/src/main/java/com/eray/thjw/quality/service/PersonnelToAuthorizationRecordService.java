package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAuthorizationRecord;

public interface PersonnelToAuthorizationRecordService {

	/**
	 * 保存授权
	 * @param personnel
	 * @return
	 */
	void saveAuthorizationRecord(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 保存维修执照
	 * @param personnel
	 * @return
	 */
	void saveMaintenanceLicense(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 保存机型执照
	 * @param personnel
	 * @return
	 */
	void saveTypeLicense(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 查询授权
	 * @param id
	 * @return
	 */
	List<PersonnelToAuthorizationRecord> queryAuthorizationRecords(String id);
	
	/**
	 * 查询维修执照
	 * @param id
	 * @return
	 */
	List<PersonnelToAuthorizationRecord> queryMaintenanceLicenses(String id);
	
	/**
	 * 查询机型执照
	 * @param id
	 * @return
	 */
	List<PersonnelToAuthorizationRecord> queryTypeLicenses(String id);

	/**
	 * 
	 * @Description 档案-授权记录分页查询
	 * @CreateTime 2017年9月19日 下午2:03:53
	 * @CreateBy 朱超
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	Map<String, Object> page(PersonnelToAuthorizationRecord record) throws BusinessException;
}
