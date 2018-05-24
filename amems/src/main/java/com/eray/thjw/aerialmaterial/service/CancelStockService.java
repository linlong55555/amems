package com.eray.thjw.aerialmaterial.service;

import com.eray.thjw.aerialmaterial.po.CancelStock;

public interface CancelStockService {

	void insertSelective(CancelStock cancelStock) throws RuntimeException;
	
}
