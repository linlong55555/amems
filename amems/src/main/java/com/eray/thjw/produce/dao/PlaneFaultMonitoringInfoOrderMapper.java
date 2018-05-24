package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.PlaneFaultMonitoringInfoOrder;

public interface PlaneFaultMonitoringInfoOrderMapper {
	/**
	 * 新增
	 * @param record
	 */
	void insertInfoOrder(PlaneFaultMonitoringInfoOrder record);
	/**
	 * 获取工单id列表
	 * @param mainid
	 * @return
	 */
	List<PlaneFaultMonitoringInfoOrder> getorderIdByMainid(String mainid);
	/**
	 * 删除
	 * @param mainid
	 */
	void deleteByMainid(String mainid);

}