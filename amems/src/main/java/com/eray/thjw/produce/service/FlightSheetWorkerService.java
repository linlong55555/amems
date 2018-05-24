package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;


/**
 * @Description 航段信息-工作者service
 * @CreateTime 2018年1月24日 上午10:04:36
 * @CreateBy 韩武
 */
public interface FlightSheetWorkerService {
	
	/**
	 * @Description 保存航段信息-工作者
	 * @CreateTime 2018年1月24日 上午10:04:51
	 * @CreateBy 韩武
	 * @param work
	 * @throws BusinessException
	 */
	void save(FlightSheetFinishedWork work) throws BusinessException;
	
}
