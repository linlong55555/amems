package com.eray.thjw.produce.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.MaintenanceReportConfig;


/**
 * @Description 维修管理service
 * @CreateTime 2018年4月24日 下午4:01:31
 * @CreateBy 韩武
 */
public interface MaintenanceMgntService {
	
	/**
	 * @Description 查询维修执管月报
	 * @CreateTime 2018年4月24日 下午4:03:00
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	Map<String, Object> queryMonthlyReport(MaintenanceReportConfig config);
	
	/**
	 * @Description 保存工时费用设置
	 * @CreateTime 2018年4月26日 上午11:05:55
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	MaintenanceReportConfig doSave(MaintenanceReportConfig config) throws BusinessException;
	
	/**
	 * @Description 查询工时费用详情
	 * @CreateTime 2018年4月26日 下午1:50:02
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	MaintenanceReportConfig queryDetail(MaintenanceReportConfig config);
}
