package com.eray.thjw.dao;

import com.eray.thjw.po.MaintenanceToBook;

public interface MaintenanceToBookMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceToBook record);

    int insertSelective(MaintenanceToBook record);

    MaintenanceToBook selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceToBook record);

    int updateByPrimaryKey(MaintenanceToBook record);
}