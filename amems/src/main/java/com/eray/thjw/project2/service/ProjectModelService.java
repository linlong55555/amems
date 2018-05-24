package com.eray.thjw.project2.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaintenanceProject;

/**
 * @Description 维修项目对应飞机关系serivice
 * @CreateTime 2017年8月16日 下午2:04:56
 * @CreateBy 韩武
 */
public interface ProjectModelService {
	
	/**
	 * @Description 保存维修项目对应飞机关系
	 * @CreateTime 2017年8月16日 下午2:06:20
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	void save(MaintenanceProject project) throws BusinessException;
	
	/**
	 * @Description 删除维修项目对应飞机关系
	 * @CreateTime 2017年9月5日 下午4:23:57
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	void delete(MaintenanceProject project) throws BusinessException;
	
}
