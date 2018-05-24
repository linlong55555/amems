package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.MountSubcomponent;

public interface MountSubcomponentMapper {
    int deleteByPrimaryKey(String id);

    int insert(MountSubcomponent record);

    int insertSelective(MountSubcomponent record);

    MountSubcomponent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MountSubcomponent record);

    int updateByPrimaryKey(MountSubcomponent record);
    
    int deleteByParam(MountSubcomponent record);
    
    List<MountSubcomponent> findByMainid(String mainid);
}