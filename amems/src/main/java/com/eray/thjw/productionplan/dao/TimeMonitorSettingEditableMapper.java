package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.productionplan.po.TimeMonitorSetting;

public interface TimeMonitorSettingEditableMapper {
    int deleteByPrimaryKey(String id);

    int insert(TimeMonitorSetting record);

    int insertSelective(TimeMonitorSetting record);

    TimeMonitorSetting selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TimeMonitorSetting record);

    int updateByPrimaryKey(TimeMonitorSetting record);
    
    List<TimeMonitorSetting> select (TimeMonitorSetting record);
    
    int deleteByParam(TimeMonitorSetting record);
    
    int synchronizeFixedMonitorTcAndTv(String zjqdid);
    
    int cascadeDeleteEditable(TimeMonitorSetting record);
}