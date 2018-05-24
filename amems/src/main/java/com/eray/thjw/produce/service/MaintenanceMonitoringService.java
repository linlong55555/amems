package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringWorkpackage;
import com.eray.thjw.produce.po.Workpackage;

/**
 * @Description 飞机维修监控服务层接口
 * @CreateTime 2017-9-28 上午11:04:27
 * @CreateBy 刘兵
 */
public interface MaintenanceMonitoringService{
	
	/**
	 * @Description 保存监控
	 * @CreateTime 2017-10-12 上午10:42:37
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return String
	 */
	String save(MonitoringObject monitoringObject)throws BusinessException;
	
	/**
	 * @Description 保存选中的待组包的监控项目
	 * @CreateTime 2017-10-13 下午5:23:18
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return int 已选中数量
	 */
	int saveChecked(MonitoringObject monitoringObject)throws BusinessException;
	
	/**
	 * @Description 移除选中的待组包的监控项目
	 * @CreateTime 2017-10-13 下午5:23:18
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 */
	void deleteChecked(MonitoringObject monitoringObject)throws BusinessException;
	
	/**
	 * @Description 组包 
	 * @CreateTime 2017-10-16 下午2:01:22
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	int addWorkpackage(Workpackage workpackage)throws BusinessException;
	
	/**
	 * @Description 添加到已有工包
	 * @CreateTime 2017-10-16 下午2:01:22
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	int add2WorkPackage(Workpackage workpackage)throws BusinessException;
	
	/**
	 * @Description 删除预组包
	 * @CreateTime 2017-10-17 上午11:16:44
	 * @CreateBy 刘兵
	 * @param id 工包id
	 * @throws BusinessException
	 */
	void deletePackage(String id)throws BusinessException;
	
	/**
	 * @Description 提交预组包
	 * @CreateTime 2017-10-17 上午11:16:44
	 * @CreateBy 刘兵
	 * @param id 工包id
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	int doSubmitPackage(String id)throws BusinessException;
	
	/**
	 * @Description 删除工包下工单
	 * @CreateTime 2017-10-18 上午10:35:33
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @throws BusinessException
	 */
	void deleteWorkOrder4WorkPackage(Workpackage workpackage)throws BusinessException;
	
	/**
	 * @Description 根据飞机注册号、机构代码获取已选中的数量
	 * @CreateTime 2017-10-14 下午2:48:55
	 * @CreateBy 刘兵
	 * @param fjzch飞机注册号
	 * @param dprtcode 机构代码
	 * @return int 已选中数量
	 */
	int getCheckCount(String fjzch, String dprtcode);
	
	/**
	 * @Description 根据飞机注册号、机构代码获取预组包数量
	 * @CreateTime 2017-10-16 下午3:18:21
	 * @CreateBy 刘兵
	 * @param fjzch飞机注册号
	 * @param dprtcode 机构代码
	 * @return int 预组包数量
	 */
	int getBurstificationCount(String fjzch, String dprtcode);

	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllPageMaintenanceList(MonitoringCurrent monitoringCurrent);
	
	/**
	 * @Description 根据查询条件查询维修清单
	 * @CreateTime 2017-10-28 下午3:38:39
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryPackageMaintenanceDetailList(MonitoringCurrent monitoringCurrent);
	
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
	 * @CreateTime 2018-5-8 下午2:49:06
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllPagePOList(MonitoringCurrent monitoringCurrent);
	
	/**
	 * @Description 根据条件查询已选中的监控数据列表
	 * @CreateTime 2017-10-14 下午4:05:52
	 * @CreateBy 刘兵
	 * @param monitoringWorkpackage 待组包的监控项目
	 * @return List<MonitoringWorkpackage> 已选中的监控数据集合
	 */
	List<MonitoringWorkpackage> queryCheckedList(MonitoringWorkpackage monitoringWorkpackage);
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)(导出)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrentParam 当前监控数据参数
	 * @return List<MonitoringCurrent>
	 */
	List<MonitoringCurrent> doExportExcelEO(MonitoringCurrent monitoringCurrentParam);
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)(导出)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrentParam 当前监控数据参数
	 * @return List<MonitoringCurrent>
	 */
	List<MonitoringCurrent> exportExcelMt(MonitoringCurrent monitoringCurrentParam);
}
