package com.eray.thjw.project2.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaintenanceProject;

/**
 * @Description 关联维修项目service
 * @CreateTime 2017年8月16日 上午10:37:53
 * @CreateBy 韩武
 */
public interface ProjectRelationshipService {
	
	/**
	 * @Description 保存关联维修项目
	 * @CreateTime 2017年8月16日 上午10:42:25
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	void save(MaintenanceProject project) throws BusinessException;
	
	/**
	 * @Description 删除关联维修项目
	 * @CreateTime 2017年9月5日 下午4:17:54
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	void delete(MaintenanceProject project) throws BusinessException;
	
}
