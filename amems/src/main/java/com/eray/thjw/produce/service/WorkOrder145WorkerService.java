package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder145;

import enu.LogOperationEnum;


/**
 * @Description 145工单信息-工作者service
 * @CreateTime 2018年1月24日 上午10:04:36
 * @CreateBy 韩武
 */
public interface WorkOrder145WorkerService {
	
	/**
	 * @Description 保存145工单信息-工作者
	 * @CreateTime 2018年1月24日 上午10:04:51
	 * @CreateBy 韩武
	 * @param work
	 * @throws BusinessException
	 */
	void save(Workorder145 workorder, String czls, LogOperationEnum operation) throws BusinessException;
	
}
