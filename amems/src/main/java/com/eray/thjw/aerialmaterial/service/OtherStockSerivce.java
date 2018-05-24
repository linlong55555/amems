package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;

public interface OtherStockSerivce {
	
	/**
	 * 按条件查询其他飞行队库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageList(Stock stock)  throws RuntimeException;
	
}
