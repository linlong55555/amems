package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.framework.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractMgnt;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptSource;

/**
 * @Description 航材收货serivce
 * @CreateTime 2018年2月26日 上午11:45:34
 * @CreateBy 韩武
 */
public interface OutinReceiptService {
	
	/**
	 * @Description 查询收货来源-合同列表
	 * @CreateTime 2018年3月5日 下午2:54:28
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryContractList(ContractMgnt record);
	
	/**
	 * @Description 查询收货来源详细-合同详细/退料详细列表
	 * @CreateTime 2018年3月7日 上午9:54:11
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryOriginList(OutinReceipt record);
	
	/**
	 * @Description 保存收货单
	 * @CreateTime 2018年3月9日 下午4:26:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	OutinReceipt doSave(OutinReceipt record) throws BusinessException, SaibongException;
	
	/**
	 * @Description 提交收货单
	 * @CreateTime 2018年3月9日 下午4:26:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	OutinReceipt doSubmit(OutinReceipt record) throws BusinessException, SaibongException;
	
	/**
	 * @Description 查询收货单详情
	 * @CreateTime 2018年3月12日 下午2:29:27
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	OutinReceipt queryDetail(String id);
	
	/**
	 * @Description 查询当前人的收货单列表
	 * @CreateTime 2018年3月19日 下午2:46:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> querySelfList(OutinReceipt record);
	
	/**
	 * @Description 删除收货单
	 * @CreateTime 2018年3月19日 下午5:22:26
	 * @CreateBy 韩武
	 * @param id
	 */
	void doDelete(String id) throws BusinessException;
	
	/**
	 * @Description 撤销收货单
	 * @CreateTime 2018年3月19日 下午5:22:43
	 * @CreateBy 韩武
	 * @param id
	 */
	void doRevoke(String id) throws BusinessException;

	List<OutinReceiptSource> queryReturnMaterialList(OutinReceipt record)throws BusinessException;
}
