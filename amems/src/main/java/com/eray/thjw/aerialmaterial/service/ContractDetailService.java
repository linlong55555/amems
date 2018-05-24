package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ContractDetail;

public interface ContractDetailService {
	/**
	 * 按条件查询一页在途
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<ContractDetail> queryAllPageList(ContractDetail contractDetail)  throws RuntimeException;
	
	 List<ContractDetail> queryList(ContractDetail contractDetail) throws RuntimeException;
	 
	 /**
	 * @author liub
	 * @description  根据合同id查询提订合同详情信息
	 * @param mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	public List<Map<String, Object>> queryReserveContractDetailList(String mainid) throws RuntimeException;
		
	/**
	 * @author liub
	 * @description  根据合同id查询送修合同详情信息
	 * @param mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	public List<Map<String, Object>> queryRepairContractMaterialList(String mainid) throws RuntimeException;

	ContractDetail queryKey(String id) throws RuntimeException;
}