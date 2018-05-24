package com.eray.thjw.aerialmaterial.service;

import java.util.Map;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.Contract;
import com.eray.thjw.aerialmaterial.po.ContractPay;
import com.eray.thjw.exception.BusinessException;


import com.eray.thjw.po.BaseEntity;

public interface ContractService {
	
	/**
	 * 查询航材历史采购价格
	 * @param map bjid、PAGINATION
	 * @author xu.yong
	 * @return
	 */
	public Map<String, Object> queryCostHisByBjId(BaseEntity entity);
	/**
	 * @author liub
	 * @description 新增合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	public String add(Contract contract) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	public void edit(Contract contract) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据条件修改合同
	 * @param contract
	 * @develop date 2016.11.09
	 * @throws BusinessException 
	 */
	public void updateByPrimaryKeySelective(Contract contract);
	
	/**
	 * @author liub
	 * @description 合同指定结束
	 * @param contract
	 * @develop date 2016.11.09
	 * @throws BusinessException 
	 */
	public void updateShutDown(Contract contract) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 收货
	 * @param contract
	 * @develop date 2016.11.10
	 * @throws BusinessException 
	 */
	public void updateComeGood(Contract contract) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 新增合同付款
	 * @param contractPay
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	public void addPay(ContractPay contractPay) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改合同付款
	 * @param contractPay
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	public void editPay(ContractPay contractPay) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  删除合同付款
	 * @param id，mainId
	 * @return
	 * @develop date 2016.11.12
	 */
	public void deletePay(String id,String mainId) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @return
	 * @develop date 2016.11.09
	 */
	public void cancel(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查合同修改权限
	 * @param id
	 * @develop date 2016.11.08
	 */
	public void checkEdit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据主键id查询合同信息
	 * @param id
	 * @return Contract
	 * @develop date 2016.11.08
	 */
	public Contract selectByPrimaryKey(String id);
	
	/**
	 * @author liub
	 * @description  根据id查询合同信息(制单人、指定结束人、合同费用)
	 * @param id
	 * @return Contract
	 * @develop date 2016.12.09
	 */
	public Contract selectById(String id);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询合同信息
	 * @param contract
	 * @return List<Contract>
	 * @develop date 2016.11.08
	 */
	public List<Contract> queryAllPageList(Contract contract);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询合同付款信息
	 * @param contractPay
	 * @return List<ContractPay>
	 * @develop date 2016.11.12
	 */
	public List<ContractPay> queryContractPayPageList(ContractPay contractPay);
	
	/**
	 * @author liub
	 * @description  根据条件分页查询付款统计列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.08
	 */
	public List<Map<String, Object>> queryPayStatisticsPageList(BaseEntity baseEntity);
	
	/**
	 * @author liub
	 * @description  根据条件分页查询付款明细列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.08
	 */
	public List<Map<String, Object>> queryPayDetailPageList(BaseEntity baseEntity);
	
	/**
	 * 查询合同列表(模态框)
	 * @param contract
	 * @return
	 */
	public List<Contract> queryPageInModal(Contract contract); 
	
}
