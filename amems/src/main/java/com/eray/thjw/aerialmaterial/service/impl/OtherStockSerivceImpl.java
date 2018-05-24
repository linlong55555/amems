package com.eray.thjw.aerialmaterial.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OtherStockMapper;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.OtherStockSerivce;

@Service
public class OtherStockSerivceImpl implements OtherStockSerivce {
	
	@Resource
	private OtherStockMapper otherStockMapper;
	
	@Override
	public List<Stock> queryAllPageList(Stock stock) throws RuntimeException {
		return otherStockMapper.queryAllPageList(stock);
	}
}
