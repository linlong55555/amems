package com.eray.framework.saibong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.framework.saibong.po.SaibongRule;

/**
 * @Description 采番规则Mapper
 * @CreateTime 2018-1-10 下午4:01:06
 * @CreateBy 刘兵
 */
public interface SaibongRuleMapper {

    int insertSelective(SaibongRule record);

    int updateByPrimaryKeySelective(SaibongRule record);

    /**
     * @Description 查询采番规则
     * @CreateTime 2018-1-10 下午4:00:52
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param cfkey 采番key
     * @return SaibongRule 采番规则
     */
    SaibongRule getSaibongRuleByDprtAndKey(@Param("dprtcode")String dprtcode, @Param("cfkey")String cfkey);
    
    /**
     * @Description 查询所有组织机构的采番规则，无规则则使用通用规则代替
     * @CreateTime 2018年1月18日 下午3:51:58
     * @CreateBy 徐勇
     * @return
     */
    List<SaibongRule> selectAllDeptSaibongRules();
    
}