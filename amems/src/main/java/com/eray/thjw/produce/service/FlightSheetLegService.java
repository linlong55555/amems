package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FlightSheet;

/**
 * @Description 飞行记录本 - 航段数据
 * @CreateTime 2017年10月25日 下午2:09:14
 * @CreateBy 韩武
 */
public interface FlightSheetLegService {
	
	/**
	 * @Description 保存飞行记录本 - 航段数据
	 * @CreateTime 2017年10月25日 上午10:57:48
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(FlightSheet record) throws BusinessException ;
}
