package com.eray.thjw.flightdata.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.FlightRecordSheet;

public interface FlightRecordSheetEffiectiveService {
	
	/**
	 * 飞行记录单生效
	 * @param flightRecordSheet
	 */
	String doTakeEffect(FlightRecordSheet flightRecordSheet) throws BusinessException;
	
	/**
	 * 飞行记录单撤销
	 * @param flightRecordSheet
	 */
	void doCancel(FlightRecordSheet flightRecordSheet);
    
}
