package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;

import enu.LogOperationEnum;


/**
 * @Description 135工单信息-工作者service
 * @CreateTime 2018年1月24日 上午10:04:36
 * @CreateBy 韩武
 */
public interface WorkOrderWorkerService {
	
	/**
	 * @Description 保存135工单信息-工作者
	 * @CreateTime 2018年1月24日 上午10:04:51
	 * @CreateBy 韩武
	 * @param work
	 * @throws BusinessException
	 */
	void save(Workorder workorder, String czls, LogOperationEnum operation);
	
	/**
	 * @Description 145工单工作者-->135工单工作者
	 * @CreateTime 2018年1月25日 下午3:44:27
	 * @CreateBy 韩武
	 * @param workorder
	 * @param czls
	 * @param operation
	 * @throws BusinessException
	 */
	void saveWorkorder145(Workorder145 workorder, String czls, LogOperationEnum operation);
	
	/**
	 * @Description 145工单工作者-->135工单工作者
	 * @CreateTime 2018年1月25日 下午3:44:27
	 * @CreateBy 韩武
	 * @param workorder
	 * @param czls
	 * @param operation
	 * @throws BusinessException
	 */
	void updateWorkorder145(Workorder145 workorder, String czls, LogOperationEnum operation);
	
	/**
	 * @Description 135工单-->FLB（工单信息修改后 工作者同步更新FLB信息）
	 * @CreateTime 2018年1月31日 上午10:02:55
	 * @CreateBy 韩武
	 * @param gdid
	 */
	void updateFlbWorkerByWorkorderWorker(String gdid);
}
