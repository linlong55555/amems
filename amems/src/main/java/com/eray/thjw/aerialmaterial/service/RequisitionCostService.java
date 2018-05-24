package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;



public interface RequisitionCostService {
	
	/**
	 * @author liub
	 * @description 根据条件分页查询单击领用成本列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.06
	 */
	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity) throws RuntimeException;
	
}
