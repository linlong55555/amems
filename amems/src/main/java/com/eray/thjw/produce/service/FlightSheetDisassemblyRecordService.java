package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;


/**
 * @Description 飞行记录单-拆装记录service
 * @CreateTime 2017年10月25日 下午2:44:28
 * @CreateBy 韩武
 */
public interface FlightSheetDisassemblyRecordService {
	
	/**
	 * @Description 保存飞行记录单-拆装记录
	 * @CreateTime 2017年10月25日 下午1:50:39
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(FlightSheetFinishedWork work) throws BusinessException;
	
}
