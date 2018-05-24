package com.eray.thjw.flightdata.dao;

import java.util.Map;

import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.MountLoadingList;

public interface FlightRecordSheetToDisassemblyMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightRecordSheetToDisassembly record);

    int insertSelective(FlightRecordSheetToDisassembly record);

    FlightRecordSheetToDisassembly selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightRecordSheetToDisassembly record);

    int updateByPrimaryKey(FlightRecordSheetToDisassembly record);
    
    int save(FlightRecordSheetToDisassembly record);
    
    int deleteNotExist(FlightRecordSheetToDisassembly record);
    
    int deleteInvalid(Map<String, Object> paramMap);
    
    /**
     * 查询外场库存数据
     * @param ll
     * @return
     */
    OutFieldStock queryOutFieldData(MountLoadingList ll);
}