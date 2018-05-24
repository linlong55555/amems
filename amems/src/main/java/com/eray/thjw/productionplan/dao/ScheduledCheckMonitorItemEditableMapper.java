package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;



/**
 * b_s_0010301 编辑区-定检监控项目 dao
 * @author hanwu
 *
 */
public interface ScheduledCheckMonitorItemEditableMapper {
	
	int insertSelective(ScheduledCheckMonitorItem record);
	
	int batchInsert(List<ScheduledCheckMonitorItem> list);
	
	List<ScheduledCheckMonitorItemEditableMapper> queryByParam(ScheduledCheckMonitorItem record);
	
	int deleteByMainid(ScheduledCheckMonitorItem record);
	
	int synchronizeTimeMonitorTcAndTv(String zjqdid);
	
	int deleteByZjqdid(ScheduledCheckMonitorItem record);
    
	int cascadeDeleteByZjqdid(ScheduledCheckMonitorItem record);
	
	int updateTbbs(Map<String, Object> param);
}