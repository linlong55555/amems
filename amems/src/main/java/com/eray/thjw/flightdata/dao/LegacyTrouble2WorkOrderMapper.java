package com.eray.thjw.flightdata.dao;

import com.eray.thjw.flightdata.po.LegacyTrouble2WorkOrder;

public interface LegacyTrouble2WorkOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(LegacyTrouble2WorkOrder record);

    int insertSelective(LegacyTrouble2WorkOrder record);

    LegacyTrouble2WorkOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LegacyTrouble2WorkOrder record);

    int updateByPrimaryKey(LegacyTrouble2WorkOrder record);
}