package com.eray.thjw.produce.service;

import com.eray.thjw.produce.po.FlightSheetLeg;


/**
 * @Description 飞行记录单-防冰液信息service
 * @CreateTime 2017年10月25日 下午1:43:45
 * @CreateBy 韩武
 */
public interface FlightSheetAntiIceService {
	
	/**
	 * @Description 保存飞行记录单-防冰液信息
	 * @CreateTime 2017年10月25日 下午1:50:39
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(FlightSheetLeg leg);
	
}
