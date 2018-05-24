package com.eray.thjw.flightdata.service;

import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;

/**
 * b_s_006020101 装上件-装机清单信息
 * @author hanwu
 *
 */
public interface MountLoadingListService {
	
    /**
     * 保存航间检查记录
     * @param data
     */
    void save(FlightRecordSheetToDisassembly data);
	
}
