package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.dao.UpMapper;


public interface OtherStockMapper extends UpMapper{
	
	/**
	 * 按条件查询一页库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageList(Stock stock)  throws RuntimeException;
	
}