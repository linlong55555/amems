package com.eray.thjw.quality.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.quality.po.AnnualAuditPlan;
/**
 * 
 * @Description 年度审核计划 Mapper
 * @CreateTime 2018年1月4日 上午11:01:52
 * @CreateBy 林龙
 */
public interface AnnualAuditPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(AnnualAuditPlan record);

    int insertSelective(AnnualAuditPlan record);

    AnnualAuditPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AnnualAuditPlan record);

    int updateByPrimaryKey(AnnualAuditPlan record);

	List<AnnualAuditPlan> queryAllPageList(AnnualAuditPlan annualAuditPlan);

	AnnualAuditPlan getById(AnnualAuditPlan annualAuditPlan);
	
	List<AnnualAuditPlan> queryOldData(@Param("dprtcode")String dprtcode, @Param("bbh")Integer bbh, @Param("nf")Integer nf);
}