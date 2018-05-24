package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.MeasurementTools;

public interface MeasurementToolsMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeasurementTools record);

    int insertSelective(MeasurementTools record);

    MeasurementTools selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeasurementTools record);

    int updateByPrimaryKey(MeasurementTools record);

	MeasurementTools selectBy(MeasurementTools measurementTools);
}