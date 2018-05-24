package com.eray.thjw.productionplan.service;

import java.util.List;

import com.eray.thjw.productionplan.po.SpecialFlightCondition;

public interface SpecialFlightConditionService {

	 List<SpecialFlightCondition> findAll()throws RuntimeException;             //查询所有有效的特殊飞行情况
	 
}
