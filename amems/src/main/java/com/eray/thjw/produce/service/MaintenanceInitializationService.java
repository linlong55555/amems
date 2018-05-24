package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringLast;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;

/**
 * @Description 维修计划初始化服务层接口
 * @CreateTime 2017-9-28 上午11:04:27
 * @CreateBy 刘兵
 */
public interface MaintenanceInitializationService{
	
	/**
	 * @Description 保存监控
	 * @CreateTime 2017-9-29 下午5:32:19
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return String
	 */
	String save(MonitoringObject monitoringObject)throws BusinessException;

	/**
	 * @Description 根据监控数据id查询任务信息(维修项目)
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 */
	MonitoringCurrent selectRelProjectById(String id);
	
	/**
	 * @Description 根据监控数据id查询任务信息(EO)
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 */
	MonitoringCurrent selectRelEOById(String id);
	
	/**
   	 * @Description 根据监控数据id查询任务信息(PO)
   	 * @CreateTime 2018-5-7 下午5:02:39
   	 * @CreateBy 刘兵
   	 * @param id 监控数据id
   	 * @return MonitoringCurrent 监控数据
   	 */
	MonitoringCurrent selectRelPoById(String id);
	
	/**
	 * @Description 根据监控数据id查询监控数据-上次执行数据
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return List<MonitoringLast> 监控数据-上次执行数据集合
	 */
	List<MonitoringLast> queryMonitoringLastById(String id);
	
	/**
	 * @Description 根据监控数据id查询监控对象及监控数据-上次执行数据
	 * @CreateTime 2017-9-30 上午9:42:15
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringObject 监控对象
	 */
	MonitoringObject getMonitoringObjectById(String id);
	
	/**
	 * @Description 根据监控数据id查询监控数据-(计划)执行数据
	 * @CreateTime 2017-9-29 上午11:41:22
	 * @CreateBy 刘兵
	 * @param jksjid 监控数据id
	 * @return List<MonitoringPlan> 监控数据-(计划)执行数据
	 */
	List<MonitoringPlan> queryMonitoringPlanByJksjid(String id);
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllPageMaintenanceList(MonitoringCurrent monitoringCurrent);
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllPageEOList(MonitoringCurrent monitoringCurrent);
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)
	 * @CreateTime 2018-5-7 下午3:15:41
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllPagePOList(MonitoringCurrent monitoringCurrent);
}
