package com.eray.thjw.material2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractPayment;

/**
 * @Description 合同付款service
 * @CreateTime 2018-3-15 上午10:24:16
 * @CreateBy 刘兵
 */
public interface ContractPaymentService {
	
	/**
	 * @Description 保存合同付款
	 * @CreateTime 2018-3-15 上午10:25:32
	 * @CreateBy 刘兵
	 * @param payment 合同付款
	 */
	void save(ContractPayment payment) throws BusinessException;
	
	/**
	 * @Description 编辑合同付款
	 * @CreateTime 2018-3-15 上午10:25:32
	 * @CreateBy 刘兵
	 * @param payment 合同付款
	 */
	void update(ContractPayment payment) throws BusinessException;
	
	/**
	 * @Description 删除合同付款
	 * @CreateTime 2018-3-15 上午10:27:36
	 * @CreateBy 刘兵
	 * @param id
	 */
	void deleteByPrimaryKey(String id) throws BusinessException;
	
	/**
	 * @Description 根据mainid删除合同付款
	 * @CreateTime 2018-3-15 上午10:27:36
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 */
	void deleteByMainid(String mainid) throws BusinessException;
	
	/**
	 * @Description 根据id查询合同付款(带明细)
	 * @CreateTime 2018-3-15 上午10:29:26
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return 合同付款
	 */
    ContractPayment selectById(String id);
	
	/**
	 * @Description 根据合同id查询合同付款列表
	 * @CreateTime 2018-3-15 上午10:29:26
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return 合同付款集合
	 */
	List<ContractPayment> selectByMainid(String mainid);
	
}
