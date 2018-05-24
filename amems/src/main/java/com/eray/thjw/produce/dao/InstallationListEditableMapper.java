package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.InstallationListEditable;

/**
 * @Description 装机清单-编辑区mapper
 * @CreateTime 2017年9月28日 上午10:05:36
 * @CreateBy 韩武
 */
public interface InstallationListEditableMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstallationListEditable record);

    int insertSelective(InstallationListEditable record);

    InstallationListEditable selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstallationListEditable record);

    int updateByPrimaryKey(InstallationListEditable record);
    
    /**
     * @Description 装机清单页面更新
     * @CreateTime 2017年10月17日 上午11:06:47
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int update(InstallationListEditable record);
    
    /**
     * @Description 查询装机清单-编辑区的数据集合
     * @CreateTime 2017年9月23日 上午10:01:13
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<InstallationListEditable> queryList(InstallationListEditable record);
    
    /**
     * @Description 查询装机清单-编辑区的树状结构
     * @CreateTime 2017年12月7日 下午3:07:17
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<InstallationListEditable> queryTreeList(InstallationListEditable record);
    
    /**
     * @Description 查询装机清单详情
     * @CreateTime 2017年10月9日 上午11:19:58
     * @CreateBy 韩武
     * @param record
     * @return
     */
    InstallationListEditable queryDetail(InstallationListEditable record);
    
    /**
     * @Description 找到装机清单所有子节点
     * @CreateTime 2017年10月10日 下午3:29:51
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<InstallationListEditable> findChildren(InstallationListEditable record);
    
    /**
     * @Description 更新初始值与飞机设置不同的的装机清单同步状态为待同步
     * @CreateTime 2017年10月17日 下午2:36:05
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    int updateSyncFlagByInitDiffWithPlane(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);

    /**
     * @Description 查询飞机待同步的装机清单数据，关联生效区ID、编辑初始值
     * @CreateTime 2017年10月19日 上午11:00:00
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    List<InstallationListEditable> selectToBeSyncWithInit(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    
    /**
     * @Description 删除拆下部件装机清单编辑区的初始设置
     * @CreateTime 2017年10月19日 下午2:41:20
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    int deleteRemovedComponentInit(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    
    /**
     * @Description 删除拆下部件装机清单编辑区数据
     * @CreateTime 2017年10月19日 下午2:41:20
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    int deleteRemovedComponent(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    
    /**
     * @Description 将装机清单编辑区状态由需要同步改为无需同步
     * @CreateTime 2017年10月19日 下午2:41:20
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    int update2NoNeedSync(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    
    /**
     * @Description 获取一级部件的数量
     * @CreateTime 2017年10月26日 上午9:48:21
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int getLevelOneCount(InstallationListEditable record);
    
    /**
     * @Description 更新装机清单时控/时寿标识
     * @CreateTime 2017年11月7日 下午2:29:03
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param jx 机型
     * @param fjzch 飞机注册号
     * @return
     */
    int updateControlFlag(@Param("dprtcode")String dprtcode, @Param("jx")String jx, @Param("fjzch")String fjzch);
    
    
    /**
     * @Description 查询工单装上件，并包装成装机清单编辑区对象 
     * @CreateTime 2017年11月20日 上午9:44:18
     * @CreateBy 徐勇
     * @param workOrderId 134工单ID
     * @return
     */
    List<InstallationListEditable> selectWorkOrderZsj(@Param("workOrderId")String workOrderId);
    
    /**
     * @Description 查询工单拆下件，并包装成装机清单编辑区对象 
     * @CreateTime 2017年11月20日 上午9:44:18
     * @CreateBy 徐勇
     * @param workOrderId 134工单ID
     * @return
     */
    List<InstallationListEditable> selectWorkOrderCxj(@Param("workOrderId")String workOrderId);
    
    /**
     * @Description 查询工单拆装记录装上件，并包装成装机清单编辑区对象 
     * @CreateTime 2017年11月20日 上午9:44:18
     * @CreateBy 徐勇
     * @param woIOIdList 工单拆装记录ID集合
     * @return
     */
    List<InstallationListEditable> selectWorkOrderIOZsj(@Param("list")List<String> woIOIdList);
    
    /**
     * @Description 查询工单拆装记录拆下件，并包装成装机清单编辑区对象 
     * @CreateTime 2017年11月20日 上午9:44:18
     * @CreateBy 徐勇
     * @param woIOIdList 工单拆装记录ID集合
     * @return
     */
    List<InstallationListEditable> selectWorkOrderIOCxj(@Param("list")List<String> woIOIdList);
    
    /**
     * 
     * @Description 查询组织机构下所有的待同步标识为1的装机清单数据
     * @CreateTime 2017年11月23日 下午4:43:34
     * @CreateBy 岳彬彬
     * @param dprtcode
     * @return
     */
    List<InstallationListEditable> selectByDprtcode(@Param("dprtcode")String dprtcode);
    
    /**
     * @Description 从生效区同步装机清单数据
     * @CreateTime 2017年11月25日 下午3:51:04
     * @CreateBy 徐勇
     * @param id
     * @return
     */
    int insertFromEffectiveById(@Param("id")String id);

    /**
     * @Description 获取时控件/时寿件标识
     * @CreateTime 2017-11-30 下午4:58:55
     * @CreateBy 雷伟
     * @param param
     * @return
     */
    List<Map<String, Object>> getSkshMapByParam(Map<String, Object> param);

    /**
     * @Description 导入专用
     * @CreateTime 2017-12-6 上午11:24:44
     * @CreateBy 雷伟
     * @param installEdits
     */
	void insert4Batch(List<InstallationListEditable> installEdits);
    int deleteRemovedComponentInitImpl(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    int deleteRemovedComponentImpl(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
}