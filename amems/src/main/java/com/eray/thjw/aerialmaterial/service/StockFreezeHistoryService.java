package com.eray.thjw.aerialmaterial.service;

import enu.aerialmaterial.StockTypeEnum;


/**
 * 库存冻结履历
 * @author hanwu
 *
 */
public interface StockFreezeHistoryService {
	
	/**
	 * 刷新库存冻结数量
	 * @param kcid
	 */
	void refreshStockFreezeCount(String kcid, StockTypeEnum stockTypeEnum);
}
