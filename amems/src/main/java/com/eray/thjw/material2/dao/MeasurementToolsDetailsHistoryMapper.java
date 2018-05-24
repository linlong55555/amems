package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.MeasurementToolsDetailsHistory;

public interface MeasurementToolsDetailsHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeasurementToolsDetailsHistory record);

    int insertSelective(MeasurementToolsDetailsHistory record);

    MeasurementToolsDetailsHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeasurementToolsDetailsHistory record);

    int updateByPrimaryKey(MeasurementToolsDetailsHistory record);

	List<MeasurementToolsDetailsHistory> selectByPrimaryMainid(String id);

	void deleteByPrimarymainid(String id);
}