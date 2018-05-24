package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.MountTimeMonitor;

public interface MountTimeMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(MountTimeMonitor record);

    int insertSelective(MountTimeMonitor record);

    MountTimeMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MountTimeMonitor record);

    int updateByPrimaryKey(MountTimeMonitor record);
    
    void deleteByParam(MountTimeMonitor record);
    
    List<MountTimeMonitor> findByMainid(String mainid);
}