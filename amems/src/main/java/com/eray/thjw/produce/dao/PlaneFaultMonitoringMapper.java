package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.PlaneFaultMonitoring;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;


public interface PlaneFaultMonitoringMapper {
	/**
	 * 故障监控列表
	 * @param record
	 * @return
	 */
    List<PlaneFaultMonitoring> getList(PlaneFaultMonitoring record);
    /**
     * 新增故障监控
     * @param record
     */
    void insertPlaneFaultMonitoring(PlaneFaultMonitoring record);
    /**
     * 获取关闭原因
     * @param record
     * @return
     */
    PlaneFaultMonitoring getGbyy(PlaneFaultMonitoring record);
    /**
     * 新增关闭原因
     * @param record
     */
    void insertGbyy(PlaneFaultMonitoring record);
    /**
     * 根据id获取故障信息
     * @param id
     * @return
     */
    PlaneFaultMonitoring getPlaneFaultMonitoringById(String id);
    /**
     * 更新故障信息
     * @param record
     */
    void updatePlaneFaultMonitoringById(PlaneFaultMonitoring record);
    /**
     * 从表信息变动是更新主表信息方便记录日志
     * @param record
     */
    void updatePlaneFault(PlaneFaultMonitoring record);
    
	void deleteByPrimaryKey(PlaneFaultMonitoringInfo record);
}