package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.InspectionRecord;

public interface InspectionRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(InspectionRecord record);

    int insertSelective(InspectionRecord record);

    InspectionRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InspectionRecord record);

    int updateByPrimaryKey(InspectionRecord record);
    
    int save(InspectionRecord record);
    
    List<InspectionRecord> findByFxjldid(String fxjldid);
}