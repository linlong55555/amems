package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ContractPay;
import com.eray.thjw.po.BaseEntity;

public interface ContractPayMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractPay record);

    int insertSelective(ContractPay record);

    ContractPay selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractPay record);

    int updateByPrimaryKey(ContractPay record);
    
    /**
	 * @author liub
	 * @description  根据查询条件分页查询合同付款信息
	 * @param record
	 * @return List<ContractPay>
	 * @develop date 2016.11.12
	 */
	public List<ContractPay> queryAllPageList(ContractPay record);
		
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
		
}