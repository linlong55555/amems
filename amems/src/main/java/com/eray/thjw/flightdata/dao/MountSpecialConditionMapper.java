package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.MountSpecialCondition;

public interface MountSpecialConditionMapper {
    int deleteByPrimaryKey(String id);

    int insert(MountSpecialCondition record);

    int insertSelective(MountSpecialCondition record);

    MountSpecialCondition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MountSpecialCondition record);

    int updateByPrimaryKey(MountSpecialCondition record);
    
    int deleteByParam(MountSpecialCondition record);
    
    List<MountSpecialCondition> findByMainid(String mainid);
}