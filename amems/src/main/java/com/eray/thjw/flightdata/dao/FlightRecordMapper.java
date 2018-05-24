package com.eray.thjw.flightdata.dao;

import java.util.List;

import com.eray.thjw.flightdata.po.FlightRecord;


public interface FlightRecordMapper {
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
	  * @return
	  * @throws RuntimeException
	  */
	 int save(FlightRecord flightRecord) throws RuntimeException;
	 
	 /**
	  * 删除飞行履历-调整值
	  * @param flightRecord
	  * @return
	  * @throws RuntimeException
	  */
	 int deleteAdjust(FlightRecord flightRecord) throws RuntimeException;
}