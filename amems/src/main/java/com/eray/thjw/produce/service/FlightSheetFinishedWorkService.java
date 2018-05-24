package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FlightSheetLeg;


/**
 * @Description 飞行记录单-完成工作service
 * @CreateTime 2017年10月25日 下午1:43:45
 * @CreateBy 韩武
 */
public interface FlightSheetFinishedWorkService {
	
	/**
	 * @Description 保存飞行记录单-完成工作
	 * @CreateTime 2017年10月25日 下午1:50:39
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(FlightSheetLeg leg) throws BusinessException ;
	
}
