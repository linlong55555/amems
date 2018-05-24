package com.eray.thjw.flightdata.dao;

import com.eray.thjw.flightdata.po.MountMonitorItem;

public interface MountMonitorItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(MountMonitorItem record);

    int insertSelective(MountMonitorItem record);

    MountMonitorItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MountMonitorItem record);

    int updateByPrimaryKey(MountMonitorItem record);
    
    int deleteByParam(MountMonitorItem record);
}