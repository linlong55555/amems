package com.eray.thjw.produce.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.PlaneFaultMonitoring;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;

public interface PlaneFaultMonitoringService {
	/**
	 * 获取列表
	 * @param record
	 * @return
	 */
	 Map<String, Object> getList(PlaneFaultMonitoring record);
	 
	 /**
	  * 
	  * @Description 新增飞机故障监控
	  * @CreateTime 2017年9月15日 上午9:53:06
	  * @CreateBy 林龙
	  * @param planeFaultMonitoring 飞机缺陷表
	  * @return id
	  * @throws BusinessException
	  */
	 public String insertPlaneFaultMonitoring(PlaneFaultMonitoring planeFaultMonitoring) throws BusinessException;
	 
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
	  * 根据id获取故障监控
	  * @param id
	  * @return
	  */
	 PlaneFaultMonitoring getPlaneFaultMonitoringById(String id);
	 /**
	  * 更新
	  * @param record
	 * @return 
	  * @throws BusinessException
	  */
	 String updatePlaneFaultMonitoringById(PlaneFaultMonitoring record) throws BusinessException;
	/**
	 * 
	 * @Description 新增新增提交技术评估单
	 * @CreateTime 2017年9月14日 下午12:05:34
	 * @CreateBy 林龙
	 * @param record
	 * @return
	 */
	 String addGzcl(PlaneFaultMonitoringInfo record)throws BusinessException;

	void delete(PlaneFaultMonitoringInfo record)throws BusinessException;
}
