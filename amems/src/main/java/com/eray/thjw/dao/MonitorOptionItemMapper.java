package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.MonitorOptionItem;

public interface MonitorOptionItemMapper {
    int deleteByPrimaryKey(String jklbh);

    int insert(MonitorOptionItem record);

    int insertSelective(MonitorOptionItem record);

    MonitorOptionItem selectByPrimaryKey(String jklbh);

    int updateByPrimaryKeySelective(MonitorOptionItem record);

    int updateByPrimaryKey(MonitorOptionItem record);
    
    List<MonitorOptionItem> findOptionAll();
}