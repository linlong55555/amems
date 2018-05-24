package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.project2.po.MaintenanceProject;

/**
 * 维修项目service
 * @author hanwu
 *
 */
public interface MaintenanceProjectService {
	
	/**
	 * @Description 保存维修项目
	 * @CreateTime 2017年8月16日 上午9:33:40
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 * @throws BusinessException
	 */
	public MaintenanceProject doSave(MaintenanceProject maintenanceProject) throws BusinessException;
	
	/**
	 * @Description 根据维修方案查询对应的维修项目
	 * @CreateTime 2017年8月16日 下午2:32:51
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	List<Map<String, Object>> groupByATA(MaintenanceProject project);
	
	/**
	 * @Description 根据维修方案查询对应的维修项目（按照目录分组）
	 * @CreateTime 2017年8月16日 下午2:33:01
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	List<Map<String, Object>> groupByCatalog(MaintenanceProject project);
	
	/**
	 * @Description 查询相关维修项目
	 * @CreateTime 2017年8月16日 下午2:33:10
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	List<MaintenanceProject> queryRelatedMaintenanceProject(MaintenanceProject project);
	
	/**
	 * @Description 查询监控项目版本
	 * @CreateTime 2017年8月16日 下午2:33:16
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	List<MaintenanceProject> queryMonitorItemVersion(MaintenanceProject project);
	
	/**
	 * @Description 撤销维修项目
	 * @CreateTime 2017年8月16日 下午2:33:23
	 * @CreateBy 韩武
	 * @param project
	 */
	MaintenanceProject doRevoke(MaintenanceProject project) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询维修项目信息(弹窗需要的数据)
	 * @param maintenanceProject
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryWinAllPageList(MaintenanceProject maintenanceProject);
	
	/**
	 * @author liub
	 * @description 根据查询条件查询定检包信息
	 * @param maintenanceProject
	 * @return List<MaintenanceProject>
	 */
	List<MaintenanceProject> queryFixedPackageByWxfn(MaintenanceProject maintenanceProject);
	
	/**
	 * @Description 根据查询条件分页查询维修项目信息(工卡弹窗需要的数据)
	 * @CreateTime 2017-8-21 下午3:47:29
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryWinAllPageList4WorkCard(MaintenanceProject maintenanceProject);
	
	/**
	 * @Description 查询维修项目详情
	 * @CreateTime 2017年8月24日 下午6:30:06
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	MaintenanceProject queryDetail(MaintenanceProject project);
	
	/**
	 * @Description 查询维修项目版本历史
	 * @CreateTime 2017年8月25日 上午10:39:41
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	List<MaintenanceProject> queryVersionList(MaintenanceProject project);
	
	/**
	 * @Description 改版维修项目
	 * @CreateTime 2017年8月26日 下午4:56:39
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 * @throws BusinessException
	 */
	public MaintenanceProject doRevision(MaintenanceProject maintenanceProject) throws BusinessException;
	
	/**
     * @Description 查询监控项目适用性
     * @CreateTime 2017-8-26 下午3:04:46
     * @CreateBy 刘兵
     * @param id 维修项目id
     * @return MaintenanceProject 维修项目
     */
	MaintenanceProject queryMonitorItemModel(String id);
	
	/**
	 * @Description 根据条件查询监控项目适用性
	 * @CreateTime 2017-9-20 下午3:20:23
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return maintenanceProject 维修项目
	 */
	MaintenanceProject queryMonitorItemModelByWxxm(MaintenanceProject maintenanceProject);
	
	/**
	 * @Description 查询差异数据
	 * @CreateTime 2017年8月28日 下午5:37:02
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	Map<String, MaintenanceProject> queryDifferenceData(MaintenanceProject project);
	
	/**
	 * @Description 查询适用维修项目
	 * @CreateTime 2017年9月27日 下午2:07:03
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<MaintenanceProject> queryApplyList (InstallationListEditable record) throws BusinessException ;
	

	/**
     * @Description 查询适用维修项目(航材/工具检验)
     * @CreateTime 2018-5-2 上午10:45:01
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param bjh 部件号
     * @return
     */
	List<MaintenanceProject> queryApplyListByBjh(String bjh, String dprtcode);
	
	/**
	 * @Description 获取导出数据
	 * @CreateTime 2017年12月19日 上午10:35:39
	 * @CreateBy 韩武
	 * @param pro
	 * @return
	 */
	List<Map<String, Object>> getExportList(MaintenanceProject pro);
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-1-29 下午3:17:36
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<MaintenanceProject>
	 */
	List<MaintenanceProject> doExportExcel(MaintenanceProject paramObj);
	
}
