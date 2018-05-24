package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.PlanUsage;

public interface PlanUsageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlanUsage record);

    int insertSelective(PlanUsage record);

    PlanUsage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PlanUsage record);

    int updateByPrimaryKey(PlanUsage record);
    
    void insertAll(List<PlanUsage> planUsageList);

    void updateAll(List<PlanUsage> planUsageList);
    
    /**
     * @Description 查询日使用量
     * @CreateTime 2017-9-27 下午2:55:53
     * @CreateBy 刘兵
     * @param fjzch 飞机注册号
     * @param dprtcode 机构代码
     * @return List<PlanUsage> 日使用量集合
     */
    List<PlanUsage> queryByFjzch(@Param("fjzch")String fjzch, @Param("dprtcode")String dprtcode);
}