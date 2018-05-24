package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;


public interface OutstockService {

	Map<String, Object> save(Outstock outstock) throws BusinessException;
	
	Map<String, Object> manualsave(Outstock outstock) throws BusinessException;
	
	Map<String, Object> checkoutsave(Outstock outstock) throws BusinessException;

	Map<String, Object> saveOutbound(MaterialRepair materialRepair)throws BusinessException;

	Map<String, Object> checkoutstockassign(Outstock outstock) throws BusinessException;

	Map<String, Object> returntheoutbound(Outstock outstock) throws BusinessException;

	Map<String, Object> doManualstockdumping(Outstock outstock) throws BusinessException, SaibongException;

	Map<String, Object> manualotherbackout(Outstock outstock) throws BusinessException;
	
	/**
	 * 按条件查询一页出库单
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Outstock> queryAllPageList(Outstock outstock)  throws RuntimeException;
	 
	Map<String, Object> backout(String id) throws BusinessException;

	/**
	 * 验证是否可以撤销
	 * @param userFromSession
	 * @param ids
	 * @return
	 * @throws RuntimeException
	 */
	Map<String, Object> checkUpdMt(User userFromSession, String ids)throws RuntimeException;

	Map<String, Object> stockRemoval(Outstock outstock)throws RuntimeException;

	List<Outstock> selectByAll(List<String> xgdjids)throws RuntimeException;

	Outstock selectById(String ckdh, String string)throws RuntimeException;

	/**
	 * 查询出库明细
	 * @param outstockDetail
	 * @return
	 * @throws RuntimeException
	 */
	Map<String, Object> queryDetailPage(OutstockDetail record)throws RuntimeException;


}
