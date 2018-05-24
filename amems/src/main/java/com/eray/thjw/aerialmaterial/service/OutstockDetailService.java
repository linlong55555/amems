package com.eray.thjw.aerialmaterial.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.po.User;

public interface OutstockDetailService {

	void insert(OutstockDetail outstockDetail);

	List<OutstockDetail> queryKey(String id) throws RuntimeException;

	void update(OutstockDetail outstockDetail1) throws RuntimeException;

	List<OutstockDetail> queryKeyList(String id)throws RuntimeException;

	Map<String, Object> cancellingStock(OutstockDetail outstockDetail)throws RuntimeException;

	Map<String, Object> checkUpd(User userFromSession, String ids, BigDecimal tksl) throws RuntimeException;

	OutstockDetail queryReserveDetailListByMainId(String ckdh)throws RuntimeException;


	
	
}
