package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractMgnt;


/**
 * @Description 合同管理service
 * @CreateTime 2018-3-8 上午11:32:12
 * @CreateBy 刘兵
 */
public interface ContractMgntService {

	
	/**
	 * @Description 保存合同
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	String save(ContractMgnt contractMgnt) throws BusinessException;
	
	/**
	 * @Description 编辑合同
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	String update(ContractMgnt contractMgnt) throws BusinessException;
	
	/**
	 * @Description 修订合同
	 * @CreateTime 2018-3-13 上午10:01:06
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	String updateRevise(ContractMgnt contractMgnt) throws BusinessException;
	
	/**
	 * @Description 删除合同
	 * @CreateTime 2018-3-13 上午10:27:55
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @throws BusinessException
	 */
	void delete(String id) throws BusinessException;
	
	/**
	 * @Description 关闭合同
	 * @CreateTime 2018-3-13 上午11:55:23
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @throws BusinessException
	 */
	void updateShutDown(ContractMgnt contractMgnt) throws BusinessException;
	
	/**
	 * @Description 合同分页列表查询
	 * @CreateTime 2018-3-9 上午11:19:00
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllPageList(ContractMgnt contractMgnt);
	
	/**
	 * @Description 模态框中获取合同 
	 * @CreateTime 2018-4-3 上午11:48:28
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return List<ContractMgnt> 合同集合
	 */
	List<ContractMgnt> queryModelList(ContractMgnt contractMgnt);
	
	/**
	 * @Description 根据合同id查询合同及用户信息
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @return ContractMgnt 合同
	 */
	ContractMgnt selectById(String id);
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-3-27 上午9:51:28
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @return Map<String, Object> 导出数据
	 */
	Map<String, Object> exportWord(String id);

}
