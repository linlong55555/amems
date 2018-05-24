package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.InspectionRecord;

/**
 * b_s_00603 航间检查记录
 * @author hanwu
 *
 */
public interface InspectionRecordService {
	
    /**
     * 保存航间检查记录
     * @param data
     */
    void save(InspectionRecord data);
    
    /**
     * 删除航间检查记录
     * @param flightRecordSheet
     */
    void deleteNotExist(FlightRecordSheet flightRecordSheet);
    
    /**
     * 根据飞行记录单查找
     * @return
     */
    List<InspectionRecord> findByFxjldid(String fxjldid);
	
}
