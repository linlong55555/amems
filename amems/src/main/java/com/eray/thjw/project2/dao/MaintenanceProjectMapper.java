package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.ProjectMonitor;

/**
 * @Description 维修项目Mapper
 * @CreateTime 2017年8月16日 下午2:53:39
 * @CreateBy 韩武
 */
public interface MaintenanceProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceProject record);

    int insertSelective(MaintenanceProject record);

    MaintenanceProject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceProject record);

    int updateByPrimaryKey(MaintenanceProject record);
    
    /**
     * @Description 根据维修方案查询生效的维修项目
     * @CreateTime 2017年8月16日 下午2:54:11
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryEffective(MaintenanceProject record);
    
    /**
     * @Description 根据维修方案查询未生效的维修项目
     * @CreateTime 2017年8月16日 下午2:54:11
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryIneffective(MaintenanceProject record);
    
    /**
     * @Description 查询生效的维修项目的相关维修项目
     * @CreateTime 2017年8月16日 下午2:54:17
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryqueryEffectiveRelated(MaintenanceProject record);
    
    /**
     * @Description 查询未生效的维修项目的相关维修项目
     * @CreateTime 2017年8月16日 下午2:54:17
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryqueryIneffectiveRelated(MaintenanceProject record);
    
    /**
     * @Description 查询监控项目版本
     * @CreateTime 2017年8月16日 下午2:54:31
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryMonitorItemVersion(MaintenanceProject record);
    
    /**
     * @Description 根据查询条件分页查询维修项目信息(弹窗需要的数据)
     * @CreateTime 2017年8月16日 下午2:54:51
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryWinAllPageList(MaintenanceProject record);
    
    /**
     * @Description 根据查询条件查询定检包信息
     * @CreateTime 2017年8月16日 下午2:55:51
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryFixedPackageByWxfn(MaintenanceProject record);
    
    /**
     * @Description 检查维修项目是否存在
     * @CreateTime 2017年8月16日 上午9:59:33
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int checkExist(MaintenanceProject record);
    
    /**
     * @Description 检查维修项目是否存在（带版本）
     * @CreateTime 2017年9月6日 上午11:39:05
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int checkExistWithVersion(MaintenanceProject record);
    
    /**
	 * @Description 根据查询条件分页查询维修项目信息(工卡弹窗需要的数据)
	 * @CreateTime 2017-8-21 下午3:47:29
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return Map<String, Object>
	 */
    List<MaintenanceProject> queryWinAllPageList4WorkCard(MaintenanceProject maintenanceProject);
    
    /**
     * @Description 维修项目生效
     * @CreateTime 2017年8月21日 下午6:46:07
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    int effect(MaintenanceScheme scheme);
    
    /**
     * @Description 将维修项目工卡编号设为空
     * @CreateTime 2017-8-22 下午3:40:37
     * @CreateBy 刘兵
     * @param gkbh 工卡编号
     * @param jx 机型
     * @param dprtcode 机构代码
     * @return int
     */
    int updateGkbh2Null(@Param("gkbh")String gkbh, @Param("jx")String jx, @Param("dprtcode")String dprtcode);
    
    /**
     * @Description 将非生效状态维修项目工卡编号设为空
     * @CreateTime 2017-8-22 下午3:40:37
     * @CreateBy 刘兵
     * @param gkbh 工卡编号
     * @param jx 机型
     * @param dprtcode 机构代码
     * @return int
     */
    int updateGkbh2Null4NoEffective(@Param("gkbh")String gkbh, @Param("jx")String jx, @Param("dprtcode")String dprtcode);
    
    /**
     * @Description 修改维修项目工卡编号
     * @CreateTime 2017-8-22 下午3:40:37
     * @CreateBy 刘兵
     * @param gkbh 工卡编号
     * @param jx 机型
     * @param wxxmbh 维修项目编号
     * @param dprtcode 机构代码
     * @return int
     */
    int updateGkbh(@Param("gkbh")String gkbh, @Param("jx")String jx, @Param("wxxmbh")String wxxmbh, @Param("dprtcode")String dprtcode);
    
    /**
     * @Description 修改非生效状态维修项目工卡编号
     * @CreateTime 2017-8-22 下午3:40:37
     * @CreateBy 刘兵
     * @param gkbh 工卡编号
     * @param jx 机型
     * @param wxxmbh 维修项目编号
     * @param dprtcode 机构代码
     * @return int
     */
    int updateGkbh4NoEffective(@Param("gkbh")String gkbh, @Param("jx")String jx, @Param("wxxmbh")String wxxmbh, @Param("dprtcode")String dprtcode);
    
    /**
     * @Description 查询维修项目详情
     * @CreateTime 2017年8月24日 下午6:31:38
     * @CreateBy 韩武
     * @param project
     * @return
     */
    MaintenanceProject queryDetail(MaintenanceProject project);
    
    /**
     * @Description 查询维修项目版本历史版本
     * @CreateTime 2017年8月25日 上午10:52:10
     * @CreateBy 韩武
     * @param project
     * @return
     */
    List<MaintenanceProject> queryVersionList(MaintenanceProject project);
    
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
     * @Description 清空后版本id
     * @CreateTime 2017年9月4日 上午11:36:06
     * @CreateBy 韩武
     * @param project
     * @return
     */
    int updateBbbid(MaintenanceProject project);
    
    /**
     * @Description 查询维修项目有没有被其他维修项目关联
     * @CreateTime 2017年9月6日 下午2:19:00
     * @CreateBy 韩武
     * @param project
     * @return
     */
    List<MaintenanceProject> queryOtherRelation(MaintenanceProject project);
    
    /**
     * @Description 查询适用维修项目
     * @CreateTime 2017年9月27日 下午2:14:53
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceProject> queryApplyList(InstallationListEditable record);
    
    /**
     * @Description 根据生效的维修方案ID查询有效的维修项目（附带关联部件、监控项目）
     * @CreateTime 2017年10月9日 下午4:20:53
     * @CreateBy 徐勇
     * @param id 维修方案ID
     * @return
     */
    List<MaintenanceProject> selectEffectMPListWithMonitorBySId(@Param("id")String id);
	 
    /**
     * @Description 更新后版本id
     * @CreateTime 2017年10月11日 下午4:07:37
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int udpateBBbid(MaintenanceProject record);
    
    /**
     * @Description 维修项目更新
     * @CreateTime 2017年10月13日 下午2:46:15
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByMaintenanceProject(MaintenanceProject record);
    
    /**
     * @Description 根据多个维修项目id查询监控项目，以mainid,JKLBH进行排序 
     * @CreateTime 2017年10月16日 下午8:42:47
     * @CreateBy 徐勇
     * @param list 维修项目ID
     * @return
     */
    List<ProjectMonitor> selectMonitorByMainIds(@Param("list")List<String> list);
    
    /**
     * @Description 根据多个维修项目id及部件号查询监控项目，以mainid,JKLBH进行排序
     * @CreateTime 2017年10月25日 下午4:01:06
     * @CreateBy 徐勇
     * @param bjh 部件号
     * @param list 维修项目ID
     * @return
     */
    List<ProjectMonitor> selectMonitorByMainIdsAndBjh(@Param("bjh")String bjh, @Param("list")List<String> list);

    /**
     * @Description 根据机构代码、维修方案编号、任务号、版本号，导入专用 
     * @CreateTime 2017-12-15 上午11:21:10
     * @CreateBy 雷伟
     * @param dprtcode
     * @param wxfabh
     * @param rwh
     * @param bb
     * @return
     */
    MaintenanceProject getWxxmByParam(@Param("dprtcode")String dprtcode, @Param("wxfabh")String wxfabh,@Param("rwh")String rwh, @Param("bb")String bb);
    
    /**
     * @Description 查询适用维修项目(航材/工具检验)
     * @CreateTime 2018-5-2 上午10:45:01
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param bjh 部件号
     * @return
     */
    List<MaintenanceProject> queryApplyListByBjh(@Param("dprtcode")String dprtcode, @Param("bjh")String bjh);
    
    /**
     * @Description 前版本：机构代码、维修方案编号、任务号、有效标识=1、后版本id is null，导入专用 
     * @CreateTime 2017-12-15 下午12:02:43
     * @CreateBy 雷伟
     * @param dprtcode
     * @param wxfabh
     * @param rwh
     * @return
     */
	MaintenanceProject getPreWxxmByParam(@Param("dprtcode")String dprtcode, @Param("wxfabh")String wxfabh,@Param("rwh")String rwh);

	/**
	 * @Description 批量新增：导入专用
	 * @CreateTime 2017-12-15 下午2:28:51
	 * @CreateBy 雷伟
	 * @param addWxxms
	 */
	void insert4Batch(List<MaintenanceProject> addWxxms);

	/**
	 * @Description 批量修改：导入专用
	 * @CreateTime 2017-12-15 下午2:29:13
	 * @CreateBy 雷伟
	 * @param updateWxxms
	 */
	void update4Batch(List<MaintenanceProject> updateWxxms);

	/**
	 * @Description 批量更新前版本的后版本ID：导入专用
	 * @CreateTime 2017-12-15 下午2:29:38
	 * @CreateBy 雷伟
	 * @param preBbWxxms
	 */
	void updatePreBb4Batch(List<MaintenanceProject> preBbWxxms);
	
	/**
	 * @Description 获取最新的维修项目
	 * @CreateTime 2017年12月20日 下午3:11:21
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	MaintenanceProject getLatestWxxm(MaintenanceProject record);
	
	/**
	 * @Description 更新维修项目版本至最新
	 * @CreateTime 2017年12月21日 上午10:02:24
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateVersion(MaintenanceScheme record);
	
	/**
	 * @Description 查询差异导出数据
	 * @CreateTime 2018-1-30 下午2:43:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return
	 */
    List<MaintenanceProject> queryExportExcelList(MaintenanceProject record);
	
}