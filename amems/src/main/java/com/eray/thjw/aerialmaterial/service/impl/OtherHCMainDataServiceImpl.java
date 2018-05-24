package com.eray.thjw.aerialmaterial.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OtherHCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.OtherHCMainDataService;

@Service
public class OtherHCMainDataServiceImpl implements OtherHCMainDataService {
	
	@Resource
	private OtherHCMainDataMapper otherHCMainDataMapper;

	@Override
	public List<HCMainData> selectByIds() {
		return otherHCMainDataMapper.selectByIds();
	}

	


}
