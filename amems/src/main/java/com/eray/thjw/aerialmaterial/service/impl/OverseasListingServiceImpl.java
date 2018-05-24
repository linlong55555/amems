package com.eray.thjw.aerialmaterial.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OverseasListingMapper;
import com.eray.thjw.aerialmaterial.po.OverseasListing;
import com.eray.thjw.aerialmaterial.service.OverseasListingService;

@Service
public class OverseasListingServiceImpl implements OverseasListingService {

	
	@Resource
	private OverseasListingMapper overseasListingMapper;
	
	@Override
	public void insert(OverseasListing overseasListing) {
		
		overseasListingMapper.insertSelective(overseasListing);
	}

}
