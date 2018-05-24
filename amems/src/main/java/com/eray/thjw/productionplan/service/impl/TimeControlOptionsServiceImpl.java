package com.eray.thjw.productionplan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.productionplan.dao.TimeControlOptionsMapper;
import com.eray.thjw.productionplan.po.TimeControlOptions;
import com.eray.thjw.productionplan.service.TimeControlOptionsService;


@Service
public class TimeControlOptionsServiceImpl implements TimeControlOptionsService {
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private TimeControlOptionsMapper timeControlOptionsMapper;
	
	
	@Override
	public List<TimeControlOptions> queryAllList(TimeControlOptions timeControlOptions)throws Exception {
		return timeControlOptionsMapper.queryAllList(timeControlOptions);
	}


	@Override
	public List<TimeControlOptions> queryAllsj()throws Exception {
		return timeControlOptionsMapper.queryAllsj();
	}


}
