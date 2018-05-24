package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ReturnedPurchase;
import com.eray.thjw.exception.BusinessException;

/**
 * 退货
 * @author ll
 */
public interface ReturnedPurchaseService {

	/**
	 * 退货分页
	 * @param borrowApply
	 * @return
	 */
	public List<ReturnedPurchase> queryAllPageList(ReturnedPurchase returnedPurchase)throws RuntimeException;
	
	public ReturnedPurchase selectByPrimaryKey(String id)throws RuntimeException;

	public void cancel(ReturnedPurchase returnedPurchase)throws RuntimeException;

	public void updateByPrimaryKeySelective(ReturnedPurchase returnedPurchase)throws RuntimeException;

	public ReturnedPurchase selectById(String id)throws RuntimeException;

	public String save(ReturnedPurchase returnedPurchase)throws RuntimeException, BusinessException;

	public String submit(ReturnedPurchase returnedPurchase)throws RuntimeException, BusinessException ;

	String updatesubmit(ReturnedPurchase returnedPurchase)
			throws RuntimeException, BusinessException;

	public String updatesave(ReturnedPurchase returnedPurchase);

	public void cancel(String id) throws BusinessException;

	public void revoked(String id)throws BusinessException;

}
