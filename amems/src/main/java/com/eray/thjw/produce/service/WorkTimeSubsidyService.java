package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.basic.po.HourCost;
import com.eray.thjw.produce.po.Workorder;
/**
 * 
 * @Description 工时统计
 * @CreateTime 2018年4月3日 下午6:31:56
 * @CreateBy 岳彬彬
 */
public interface WorkTimeSubsidyService {

	/**
	 * 
	 * @Description 工时补贴统计页面数据
	 * @CreateTime 2018年4月2日 上午10:40:53
	 * @CreateBy 岳彬彬
	 * @param paramObj
	 * @return
	 */
	Map<String,Object> getWorkTimeSubsidy(Workorder paramObj);
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年4月3日 下午6:36:01
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void addRecord(List<HourCost> record);
	/**
	 * 
	 * @Description 获取
	 * @CreateTime 2018年4月4日 上午9:28:28
	 * @CreateBy 岳彬彬
	 * @return
	 */
	List<HourCost> getRecord();
}