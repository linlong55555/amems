package com.eray.thjw.flightdata.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.LegacyTrouble;

/**
 * 故障保留单Dao
 * @author zhuchao
 *
 */
@Component("legacyTroubleMapper")
public interface LegacyTroubleMapper {
    int deleteByPrimaryKey(String id);

    int insert(LegacyTrouble record);

    int insertSelective(LegacyTrouble record);

    LegacyTrouble selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LegacyTrouble record);

    int updateByPrimaryKey(LegacyTrouble record);

	int queryListCount(LegacyTrouble legacytrouble);

	List<LegacyTrouble> queryList(LegacyTrouble legacytrouble);

	/**
	 * 故障保留单关闭，指定结束
	 * @param record
	 */
	void end(LegacyTrouble record);
	
	/**
	 * 根据飞行记录单查询故障保留单
	 * @param sheet
	 * @return
	 */
	List<LegacyTrouble> queryListByFlightRecord(FlightRecordSheet sheet);

}