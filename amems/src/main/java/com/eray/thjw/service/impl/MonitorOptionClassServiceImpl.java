package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.MonitorClassMapper;
import com.eray.thjw.dao.MonitorOptionItemMapper;
import com.eray.thjw.po.MonitorOptionClass;
import com.eray.thjw.po.MonitorOptionItem;
import com.eray.thjw.service.MonitorOptionClassService;
@Service
public class MonitorOptionClassServiceImpl implements MonitorOptionClassService{

	@Autowired
	private MonitorClassMapper monitorClassMapper;
	
	@Autowired
	private MonitorOptionItemMapper monitorOptionItemMapper;

	@Override
	public List<MonitorOptionClass> queryAll() {  
		return monitorClassMapper.selectAll();
	}
	
	@Override
	public List<MonitorOptionItem> findOptionAll() {  
		return monitorOptionItemMapper.findOptionAll();
	}

}
