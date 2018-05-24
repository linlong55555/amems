package com.eray.thjw.productionplan.dao;

import com.eray.thjw.productionplan.po.CheckingMonitoring;

public interface CheckingMonitoringMapper {
	
	void save(CheckingMonitoring checkingMonitoring) throws Exception;	// 新增b_g_01401 定检任务监控项目
	
	CheckingMonitoring selectByPrimaryKey(String mainid);                            //查询单个定检任务单的监控项
}
