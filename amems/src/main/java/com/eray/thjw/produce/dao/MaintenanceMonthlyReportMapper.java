package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.MaintenanceReportConfig;

/**
 * @Description 维修执管月报mapper
 * @CreateTime 2018年4月24日 下午4:15:28
 * @CreateBy 韩武
 */
public interface MaintenanceMonthlyReportMapper {
	
	/**
	 * @Description 查询机身及发动机小时数
	 * @CreateTime 2018年4月24日 下午4:20:36
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryHoursStatistics(MaintenanceReportConfig config);
	
	/**
	 * @Description 查询航线例行维修工作
	 * @CreateTime 2018年4月25日 下午1:54:50
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryRoutineWork(MaintenanceReportConfig config);
	
	/**
	 * @Description 查询航线非例行维修工作
	 * @CreateTime 2018年4月25日 下午2:13:23
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryNonRoutineWork(MaintenanceReportConfig config);
	
	/**
	 * @Description 查询EO/MAO执行情况
	 * @CreateTime 2018年4月25日 下午2:27:41
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryEoMaoImplementation(MaintenanceReportConfig config);
	
	/**
	 * @Description 查询适航指令,厂家服务通告等评估情况
	 * @CreateTime 2018年4月25日 下午2:44:12
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryEvaluationSituations(MaintenanceReportConfig config);
	
	/**
	 * @Description 查询飞机故障/缺陷监控
	 * @CreateTime 2018年4月25日 下午2:48:09
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryFaultMonitor(MaintenanceReportConfig config);
	
	/**
	 * @Description 查询非例行维修工作费用
	 * @CreateTime 2018年4月25日 下午2:52:20
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	List<Map<String, Object>> queryNonRoutinePayment(MaintenanceReportConfig config);
} 