package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.productionplan.po.SpecialFlightCondition;

public interface SpecialFlightConditionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpecialFlightCondition record);

    int insertSelective(SpecialFlightCondition record);

    SpecialFlightCondition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpecialFlightCondition record);

    int updateByPrimaryKey(SpecialFlightCondition record);
    
    List<SpecialFlightCondition> select(SpecialFlightCondition record);		// 查询特殊飞行情况
}