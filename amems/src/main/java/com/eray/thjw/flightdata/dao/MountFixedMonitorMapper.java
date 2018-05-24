package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.MountFixedMonitor;

public interface MountFixedMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(MountFixedMonitor record);

    int insertSelective(MountFixedMonitor record);

    MountFixedMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MountFixedMonitor record);

    int updateByPrimaryKey(MountFixedMonitor record);
    
    int save(MountFixedMonitor record);
    
    List<MountFixedMonitor> findByMainid(String mainid);
}