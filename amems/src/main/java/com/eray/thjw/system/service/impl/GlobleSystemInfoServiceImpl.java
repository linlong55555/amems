package com.eray.thjw.system.service.impl;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.system.dao.GlobleSystemInfoMapper;
import com.eray.thjw.system.po.GlobleSystemInfo;
import com.eray.thjw.system.service.GlobleSystemInfoService;

@Service
public class GlobleSystemInfoServiceImpl implements GlobleSystemInfoService {

	@Resource
	private GlobleSystemInfoMapper globleSystemInfoMapper;

	@Override
	public List<GlobleSystemInfo> getAll() {
		
		return globleSystemInfoMapper.getAll();
	}
	
}
