package com.eray.thjw.project2.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProjectMaterial;

/**
 * @Description 维修项目-监控项设置service
 * @CreateTime 2017年8月17日 上午11:43:15
 * @CreateBy 韩武
 */
public interface ProjectMonitorService {
	
	/**
	 * @Description 保存维修项目-监控项设置（对应飞机）
	 * @CreateTime 2017年8月17日 上午11:43:22
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	void save(MaintenanceProject project) throws BusinessException;
	
	/**
	 * @Description 保存维修项目-监控项设置（对应部件）
	 * @CreateTime 2017年8月18日 下午1:51:24
	 * @CreateBy 韩武
	 * @param material
	 * @throws BusinessException
	 */
	void save(ProjectMaterial material) throws BusinessException;
	
	/**
	 * @Description 删除维修项目-监控项设置
	 * @CreateTime 2017年9月5日 下午4:26:03
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	void delete(MaintenanceProject project) throws BusinessException;
	
}
