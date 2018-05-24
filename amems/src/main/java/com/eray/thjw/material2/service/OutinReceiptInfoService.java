package com.eray.thjw.material2.service;

import java.util.List;

import com.eray.framework.exception.SaibongException;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptInfo;

/**
 * @Description 航材收货单明细serivce
 * @CreateTime 2018年2月26日 上午11:45:34
 * @CreateBy 韩武
 */
public interface OutinReceiptInfoService {
	
	/**
	 * @Description 保存航材收货单明细
	 * @CreateTime 2018年3月12日 上午9:49:32
	 * @CreateBy 韩武
	 * @param mainid
	 * @param details
	 */
	void save(OutinReceipt record) throws SaibongException;
	
	/**
	 * @Description 根据收货单id删除收货单明细
	 * @CreateTime 2018年3月12日 上午9:50:15
	 * @CreateBy 韩武
	 * @param shdid
	 */
	void deleteNotExistByShdid(String shdid, List<OutinReceiptInfo> details);
	
}
