package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.AnnualPlan;
/**
 * 
 * @Description 年度计划 Mapper
 * @CreateTime 2018年1月4日 上午11:02:21
 * @CreateBy 林龙
 */
public interface AnnualPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(AnnualPlan record);

    int insertSelective(AnnualPlan record);

    AnnualPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AnnualPlan record);

    int updateByPrimaryKey(AnnualPlan record);

    List<AnnualPlan> getLatestVersionList(String nf, String dprtcode);
    
    AnnualPlan getLatestVersion(String nf, String dprtcode,String bbh);
    
    /**
     * @Description 查询变更记录
     * @CreateTime 2018年1月11日 下午4:04:44
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<AnnualPlan> queryChangeRecord(AnnualPlan record);
    
    /**
     * @Description 根据状态，组织机构获取数据
     * @CreateTime 2018年1月11日 下午4:04:44
     * @CreateBy sunji
     * @param record
     * @return
     */
    List<AnnualPlan> queryAllByZt(AnnualPlan record);
    
    /**
     * @Description 根据id获取年度计划及最大版本号
     * @CreateTime 2018-1-19 上午9:13:05
     * @CreateBy 刘兵
     * @param id
     * @return
     */
    AnnualPlan getAnnualPlanById(String id);
}