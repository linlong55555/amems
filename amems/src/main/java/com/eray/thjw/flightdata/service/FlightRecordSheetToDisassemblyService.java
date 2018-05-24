package com.eray.thjw.flightdata.service;

import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;

/**
 * b_s_0060201 拆解记录
 * @author hanwu
 *
 */
public interface FlightRecordSheetToDisassemblyService {
	
    /**
     * 保存拆解记录
     * @param data
     */
    void save(FlightRecordSheetToDisassembly data);
    
    /**
     * 删除拆解记录
     * @param plan
     */
    void deleteNotExist(FlightRecordSheetToPlan plan);
	
}
