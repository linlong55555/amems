package com.eray.thjw.produce.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.InstallationListEffective;

/**
 * @Description 装机清单-生效区mapper
 * @CreateTime 2017年9月28日 上午10:06:17
 * @CreateBy 韩武
 */
public interface InstallationListEffectiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstallationListEffective record);

    int insertSelective(InstallationListEffective record);

    InstallationListEffective selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstallationListEffective record);

    int updateByPrimaryKey(InstallationListEffective record);
    
    /**
     * @Description 查询装机清单-生效区的数据集合
     * @CreateTime 2017年9月23日 上午10:01:13
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<InstallationListEffective> queryList(InstallationListEffective record);
    
    /**
     * 
     * @Description List<InstallationListEffective>
     * @CreateTime 2017-10-16 下午2:39:07
     * @CreateBy 孙霁
     * @param record
     */
    List<InstallationListEffective> queryComponentiohistoryList(InstallationListEffective record);
    
    /**
     * 
     * @Description 查询部件履历
     * @CreateTime 2017-10-16 下午2:39:07
     * @CreateBy 孙霁
     * @param record
     */
    List<InstallationListEffective> queryComponenthistoryList(InstallationListEffective record);

    
    /**
     * @Description 从编辑区更新到生效区
     * @CreateTime 2017年10月19日 下午4:02:57
     * @CreateBy 徐勇
     * @param zjqdid 装机清单ID
     * @return
     */
    int updateFromEditable(@Param("id")String zjqdid);
    
    /**
     * @Description 从编辑区新增到生效区
     * @CreateTime 2017年10月19日 下午4:03:28
     * @CreateBy 徐勇
     * @param zjqdid 装机清单ID
     * @param thjh 拆下对应装上件号
     * @param thxlh 拆下对应装上序列号
     * @return
     */
    int insertFromEditable(@Param("id")String zjqdid, @Param("thjh")String thjh, @Param("thxlh")String thxlh);
    
    
    /**
     * 
     * @Description 查询部件数据
     * @CreateTime 2017-10-20 下午2:29:13
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    List<InstallationListEffective> queryAllBj(InstallationListEffective record);
    
    /**
     * @Description 查询生效区装机清单（附带初始化数据）
     * @CreateTime 2017年11月17日 下午2:50:46
     * @CreateBy 徐勇
     * @param id 装机清单ID
     * @return
     */
    InstallationListEffective selectWithInitById(@Param("id")String id);
    
    /**
     * 
     * @Description 获取状态为1序列号不为空的数据
     * @CreateTime 2017年11月20日 下午3:03:28
     * @CreateBy 岳彬彬
     * @param dprtcode
     * @param fjjx
     * @return
     */
    List<InstallationListEffective> getLoadinglistByfjzch(@Param("dprtcode")String dprtcode,@Param("fjjx")String fjjx,@Param("bjh")String bjh);
    
    /**
     * @Description 查询装机清单详情
     * @CreateTime 2017年11月27日 下午5:12:44
     * @CreateBy 韩武
     * @param record
     * @return
     */
    InstallationListEffective queryDetail(InstallationListEffective record);

	Integer getCountByParam(@Param("dprtcode")String dprtcode,@Param("fjzch")String fjzch);
    
    /**
     * @Description 查询飞机一级部件（除机身）
     * @CreateTime 2017年11月30日 上午10:27:29
     * @CreateBy 韩武
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @param jssj 计算时间
     * @return
     */
    List<InstallationListEffective> queryLevelOnePart(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("jssj")Date jssj);

    /**
     * 根据机构代码、飞机注册号查询d_007 飞机信息表，取得d_007.发动机数量
     * @Description 
     * @CreateTime 2017-12-7 下午4:59:19
     * @CreateBy 雷伟
     * @param dprtcode
     * @param fjzch
     * @return
     */
	Integer getCountFdjgsByParam(@Param("dprtcode")String dprtcode,  @Param("fjzch")String fjzch);

    /**
     * @Description 根据id清除拆下数据 
     * @CreateTime 2017年12月7日 下午1:57:46
     * @CreateBy 徐勇
     * @param id
     */
    int update4ClearRemoveDataById(@Param("id")String id);
    
    
    /**
     * @Description 查询物料履历
     * @CreateTime 2018-3-26 下午3:23:01
     * @CreateBy 雷伟
     * @param record
     */
	List<InstallationListEffective> queryMaterialhistoryList(InstallationListEffective installationListEffective);

	/**
	 * @Description  查询物料列表信息
	 * @CreateTime 2018-3-26 下午4:06:20
	 * @CreateBy 雷伟
	 * @param installationListEffective
	 * @return
	 */
	 List<InstallationListEffective> queryMaterialAllBj(InstallationListEffective installationListEffective);
}