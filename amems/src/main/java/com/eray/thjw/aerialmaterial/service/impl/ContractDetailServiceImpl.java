package com.eray.thjw.aerialmaterial.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ContractDetailMapper;
import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;

@Service
public class ContractDetailServiceImpl implements ContractDetailService {
	
	@Resource
	private ContractDetailMapper contractDetailMapper;
	
	@Override
	public List<ContractDetail> queryAllPageList(ContractDetail contractDetail)
			throws RuntimeException {
		
		return contractDetailMapper.queryAllPageList(contractDetail);
	}

	@Override
	public List<ContractDetail> queryList(ContractDetail contractDetail) throws RuntimeException {
		
		return contractDetailMapper.queryList(contractDetail);
	}
	
	/**
	 * @author liub
	 * @description  根据合同id查询提订合同详情信息
	 * @param mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	public List<Map<String, Object>> queryReserveContractDetailList(String mainid) throws RuntimeException{
		return contractDetailMapper.queryReserveContractDetailList(mainid);
	}
		
	/**
	 * @author liub
	 * @description  根据合同id查询送修合同详情信息
	 * @param mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	public List<Map<String, Object>> queryRepairContractMaterialList(String mainid) throws RuntimeException{
		return contractDetailMapper.queryRepairContractMaterialList(mainid);
	}

	@Override
	public ContractDetail queryKey(String id) throws RuntimeException {
		return contractDetailMapper.queryKey(id);
	}

}
