package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;

/** 
 * @Description 
 * @CreateTime 2017-10-12 下午2:30:10
 * @CreateBy 孙霁	
 */
public interface FlighthistoryService {

	/**
	 * 
	 * @Description 条件查询飞行履历主列表
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryAll(FlightSheetVoyage flightSheetVoyage);
	
	/**
	 * 
	 * @Description 获取导出数据
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	public List<FlightSheetVoyage> getList(FlightSheetVoyage flightSheetVoyage);
}
