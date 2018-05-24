package com.eray.thjw.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eray.thjw.po.MonitorOptionClass;

@Component(value="monitorClassMapper")
public interface MonitorClassMapper {
    int deleteByPrimaryKey(String jkflbh);

    int insert(MonitorOptionClass record);

    int insertSelective(MonitorOptionClass record);

    MonitorOptionClass selectByPrimaryKey(String jkflbh);
    
    List<MonitorOptionClass> selectAll();

    int updateByPrimaryKeySelective(MonitorOptionClass record);

    int updateByPrimaryKey(MonitorOptionClass record);
}