package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;


/**
 * b_s_00304 生效区-定检监控项目 dao
 * @author linlong
 *
 */
public interface ScheduledCheckMonitorItemMapper {
	
	public List<ScheduledCheckMonitorItem> queryAllList(Map<String, Object> map) throws Exception;
	
	public List<ScheduledCheckMonitorItem> queryAllsj(Map<String, Object> map) throws Exception;
}