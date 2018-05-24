package com.eray.thjw.productionplan.service;

import com.eray.thjw.productionplan.po.CheckingMonitoring;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;

public interface CheckingMonitoringSerivce {
	
	void save(String id,ScheduledCheckItem scheduledCheckItem, String czls) throws Exception;	// // 新增b_g_01401 定检任务监控项目

	CheckingMonitoring selectByPrimaryKey(String mainid);          //查询单个定检任务单的监控项
	
}
