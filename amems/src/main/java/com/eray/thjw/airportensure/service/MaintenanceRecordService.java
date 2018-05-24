package com.eray.thjw.airportensure.service;

import java.util.List;

import com.eray.thjw.airportensure.po.MaintenanceRecord;

public interface MaintenanceRecordService {

	/**
	 * 添加机场保障部维修记录
	 * 
	 * @param maintenanceRecord
	 * @throws Exception
	 */
	void insertMaintenanceRecord(MaintenanceRecord maintenanceRecord) throws Exception;

	/**
	 * 获取机场保障部维修记录
	 * 
	 * @param maintenanceRecord
	 * @return
	 * @throws Exception
	 */
	List<MaintenanceRecord> selectMaintenanceRecordList(MaintenanceRecord maintenanceRecord) throws Exception;

	/**
	 * 修改状态
	 * 
	 * @param id
	 * @throws Exception
	 */
	void updateZtById(String id) throws Exception;

	/**
	 * 根据id获取对应id记录信息
	 * 
	 * @param maintenanceRecord
	 * @return
	 * @throws Exception
	 */
	MaintenanceRecord getMaintenanceRecordById(MaintenanceRecord maintenanceRecord) throws Exception;

	/**
	 * 修改维修记录
	 * 
	 * @param maintenanceRecord
	 * @throws Exception
	 */
	void updateMaintenanceRecordById(MaintenanceRecord maintenanceRecord) throws Exception;
}
