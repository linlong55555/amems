package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.PlanInit;

public interface PlanInitMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlanInit record);

    int insertSelective(PlanInit record);

    PlanInit selectByPrimaryKey(String id);
    
    /**
     * @Description 查询飞机 机身初始化信息
     * @CreateTime 2017年11月20日 下午4:01:44
     * @CreateBy 徐勇
     * @param fjzch 飞机注册号
     * @param dprtcode 组织机构
     * @return
     */
    List<PlanInit> selectBodyInit(@Param("fjzch")String fjzch, @Param("dprtcode") String dprtcode);

    int updateByPrimaryKeySelective(PlanInit record);

    int updateByPrimaryKey(PlanInit record);
    
    void insertAll(List<PlanInit> planInitList);

    void updateAll(List<PlanInit> planInitList);
}