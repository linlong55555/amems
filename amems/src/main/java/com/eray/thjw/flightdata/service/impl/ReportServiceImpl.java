package com.eray.thjw.flightdata.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.ReportMapper;
import com.eray.thjw.flightdata.service.ReportService;
import com.eray.thjw.util.PageUtil;

/**
 * 报表<br/>
 * 减速器滑耗（机身减速器滑油消耗量统计）<br/>
 * 发动机健康监控
 * @author xu.yong
 *
 */
@Service("flightdataReportService")
public class ReportServiceImpl implements ReportService {
	
	@Resource
	private ReportMapper flightdataReportMapper;

	/**
	 * 查询减速器滑耗（机身减速器滑油消耗量统计）/查询发动机健康监控
	 * @param map query parameters
	 * @return
	 */
	public Map<String, Object> query(Map<String, Object> map){
		return PageUtil.pack(this.flightdataReportMapper.query(map));
	}
	
}
