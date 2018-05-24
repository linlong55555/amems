package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheet;

/**
 * b_s_00601 飞行履历服务接口
 * @author ll
 *
 */
public interface FlightRecordService {

	/**
	 * 按条件查询一页飞行履历
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<FlightRecord> queryAllPageList(FlightRecord flightRecord)  throws RuntimeException;
	 
	 /**
	  * 保存飞行履历
	  * @param flightRecord
	  * @throws RuntimeException
	  */
	 void save(FlightRecord flightRecord) throws RuntimeException;
	 
	 /**
	  * 删除不存在的飞行履历
	  * @param flightRecordSheet
	  * @throws RuntimeException
	  */
	 void deleteNotExist(FlightRecordSheet flightRecordSheet) throws RuntimeException;

}
