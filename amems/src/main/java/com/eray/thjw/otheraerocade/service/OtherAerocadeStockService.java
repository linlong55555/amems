package com.eray.thjw.otheraerocade.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;

public interface OtherAerocadeStockService {

	/**
	 * 按条件查询一页其他飞行队
	 * @author sunji
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageList(Stock stock)  throws BusinessException;
	 
}
