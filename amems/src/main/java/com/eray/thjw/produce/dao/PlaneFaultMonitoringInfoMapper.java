package com.eray.thjw.produce.dao;

import java.util.Date;
import java.util.List;

import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;

public interface PlaneFaultMonitoringInfoMapper {
	/**
	 * 获取故障处理列表
	 * @param mainid
	 * @return
	 */
   List<PlaneFaultMonitoringInfo> getInfoList(String mainid);
   /**
    * 新增故障处理
    * @param info
    */
   void insertInfo(PlaneFaultMonitoringInfo info);
   /**
    * 获取故障信息
    * @param id
    * @return
    */
   PlaneFaultMonitoringInfo getInfoById(String id);
   /**
    * 更新故障信息
    * @param info
    */
   void updateById(PlaneFaultMonitoringInfo info);
   /**
    * 作废故障信息
    * @param info
    */
   void deleteById(PlaneFaultMonitoringInfo info);
	/**
	 * 获取故障次数
	 * @param mainid
	 * @return
	 */
   int getCountByMainidAndZt(String mainid);
   /**
    * 获取最近故障发生时间
    * @param mainid
    * @return
    */
   Date getNearTime(String mainid);
}