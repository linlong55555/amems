package com.eray.thjw.aerialmaterial.service;

import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;

/**
 * 收货
 * @author hanwu
 *
 */
public interface ReceiptService {

	/**
	 * 收货分页查询
	 * @param receipt
	 * @return
	 */
	Map<String, Object> queryPage(ReceiptSheet receipt);
	
	/**
	 * 收货页面部件分页查询
	 * @param param
	 * @return
	 */
	Map<String, Object> queryComponentPage(BaseEntity baseEntity);
	
	/**
	 * 保存收货单
	 * @param receipt
	 * @return
	 */
	String save(ReceiptSheet receipt) throws SaibongException, BusinessException;
	
	/**
	 * 根据id查询收货数据
	 * @param id
	 * @return
	 */
	ReceiptSheet queryById(String id);
	
	/**
	 * 根据id查询单表
	 * @param id
	 * @return
	 */
	ReceiptSheet selectByPrimaryKey(String id);
	
	/**
	 * 提交收货单
	 * @param receipt
	 */
	String doSubmit(ReceiptSheet receipt) throws SaibongException, BusinessException;
	
	/**
	 * 作废
	 * @param id
	 */
	void doScrap(String id);

	/**
	 * 撤销
	 * @param id
	 */
	void doRevoke(String id);
}
