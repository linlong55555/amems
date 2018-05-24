package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;
import com.eray.thjw.produce.po.Workorder;

public interface PlaneFaultMonitoringInfoService {
	/**
	 * 根据故障监控id获取故障处理列表	
	 * @param mainid
	 * @return
	 */
	 List<PlaneFaultMonitoringInfo> getInfoList(String mainid);
	 /**
	  * 根据id获取故障处理
	  * @param id
	  * @return
	  */
	 PlaneFaultMonitoringInfo getInfoById(String id);
	 /**
	  * 更新
	  * @param info
	  */
	 String updateById(PlaneFaultMonitoringInfo info)throws BusinessException;
	 /**
	  * 作废
	  * @param info
	  */
	 void deleteById(PlaneFaultMonitoringInfo info);
	 /**
	  * 获取装上拆下信息
	  * @param fxjlgljlid
	  * @return
	  */
	 Map<String,List<String>> getZsClxx(String fxjlgljlid);
	 
	 
	 Map<String,Object> queryWorkOrderList(WorkOrder data);
	 /**
	  * @Description 获取装上拆下信息
	  * @CreateTime 2017年10月9日 上午9:56:23
	  * @CreateBy 胡才秋
	  * @param data
	  * @return
	  */
	 Map<String, List<String>> getZsCxInfo(Workorder data);
	 
	 }
