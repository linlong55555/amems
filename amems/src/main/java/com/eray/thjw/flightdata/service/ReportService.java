package com.eray.thjw.flightdata.service;

import java.util.Map;

/**
 * 报表<br/>
 * 减速器滑耗（机身减速器滑油消耗量统计）<br/>
 * 发动机健康监控
 * @author xu.yong
 *
 */
public interface ReportService {
	
	/**
	 * 查询减速器滑耗（机身减速器滑油消耗量统计）/查询发动机健康监控
	 * @param map query parameters
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> map);
	
}
