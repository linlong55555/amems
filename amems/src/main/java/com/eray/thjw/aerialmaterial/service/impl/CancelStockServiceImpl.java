package com.eray.thjw.aerialmaterial.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.CancelStockMapper;
import com.eray.thjw.aerialmaterial.dao.OutstockDetailMapper;
import com.eray.thjw.aerialmaterial.po.CancelStock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.service.CancelStockService;
import com.eray.thjw.aerialmaterial.service.OutstockDetailService;

@Service
public class CancelStockServiceImpl implements CancelStockService {
	
	@Resource
	private CancelStockMapper cancelStockMapper;

	@Override
	public void insertSelective(CancelStock cancelStock)
			throws RuntimeException {
		cancelStockMapper.insertSelective(cancelStock);
		
	}





}
