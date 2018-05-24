package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.produce.po.FlightSheet;

/**
 * 
 * @Description 飞行记录本 - 航次数据service
 * @CreateTime 2017年10月12日 下午1:52:50
 * @CreateBy 岳彬彬
 */
public interface FlightSheetVoyageService {
	/**
	 * 
	 * @Description 发动机监控数据
	 * @CreateTime 2017年10月14日 上午10:40:57
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 */
	List<Map<String, Object>> getMonitorData(BaseEntity baseEntity);
	/**
	 * 
	 * @Description APU监控数据
	 * @CreateTime 2017年10月14日 上午10:41:08
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 */
	List<Map<String, Object>> getApuData(BaseEntity baseEntity);
	
	/**
	 * @Description 保存飞行记录本 - 航次数据
	 * @CreateTime 2017年10月25日 上午10:57:48
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(FlightSheet record);
}
