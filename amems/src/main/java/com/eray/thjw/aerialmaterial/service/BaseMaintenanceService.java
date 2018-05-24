package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.BaseMaintenance;

public interface BaseMaintenanceService {
	/**
	 * 添加基地
	 * @param baseMaintenance
	 * @throws Exception
	 */
	void insertBaseMaintenance(BaseMaintenance baseMaintenance)throws Exception;
	/**
	 * 查询基地
	 * @param baseMaintenance
	 * @return
	 * @throws Exception
	 */
	List<BaseMaintenance> selectBaseMaintenanceList(BaseMaintenance baseMaintenance)throws Exception;

	/**
	 * 根据id获取基地信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	BaseMaintenance selectBaseMaintenanceById(String id)throws Exception;
	/**
	 * 修改基地信息
	 * @param baseMaintenance
	 * @throws Exception
	 */
	void updateBaseMaintenanceById(BaseMaintenance baseMaintenance)throws Exception;
	/**
	 * 修改基地状态
	 * @param id
	 * @throws Exception
	 */
	void deleteBaseMaintenanceById(String id)throws Exception;
	/*
	 * 查询基地描述是否存在
	 */
	int selectByJdms(BaseMaintenance baseMaintenance)throws Exception;
}
