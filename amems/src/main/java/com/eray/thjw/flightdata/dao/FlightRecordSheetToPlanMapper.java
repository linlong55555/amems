package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;

public interface FlightRecordSheetToPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightRecordSheetToPlan record);

    int insertSelective(FlightRecordSheetToPlan record);

    FlightRecordSheetToPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightRecordSheetToPlan record);

    int updateByPrimaryKey(FlightRecordSheetToPlan record);
    
    int save(FlightRecordSheetToPlan record);
    
    List<FlightRecordSheetToPlan> findByFxjldid(String fxjldid);
    
    int deleteNotExist(FlightRecordSheetToPlan param);
    
    List<FlightRecordSheetToPlan> queryDelete(FlightRecordSheetToPlan param);
    
    List<FlightRecordSheetToPlan> getZlhAndId(FlightRecordSheetToPlan param);
}