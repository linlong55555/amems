package com.eray.thjw.productionplan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.productionplan.dao.SpecialFlightConditionMapper;
import com.eray.thjw.productionplan.po.SpecialFlightCondition;
import com.eray.thjw.productionplan.service.SpecialFlightConditionService;


@Service
public class SpecialFlightConditionServiceImpl implements SpecialFlightConditionService{
	
	@Resource
	private SpecialFlightConditionMapper specialFlightConditionMapper;

	/**
	 * 根据飞机机型查询所有有效的特殊飞行情况
	 */
	@Override
	public List<SpecialFlightCondition> findAll() throws RuntimeException {
		return specialFlightConditionMapper.select(new SpecialFlightCondition());
	}

}
