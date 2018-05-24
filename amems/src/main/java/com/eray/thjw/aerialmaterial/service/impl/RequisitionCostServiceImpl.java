package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.RequisitionCostMapper;
import com.eray.thjw.aerialmaterial.service.RequisitionCostService;
import com.eray.thjw.po.BaseEntity;

/**
 * @author liub
 * @description 单击领用成本service,用于业务逻辑处理
 * @develop date 2016.12.06
 */
@Service
public class RequisitionCostServiceImpl implements RequisitionCostService {
	
	/**
	 * @author liub
	 * @description 单击领用成本mapper
	 * @develop date 2016.12.06
	 */
	@Autowired
	private RequisitionCostMapper requisitionCostMapper;

	/**
	 * @author liub
	 * @description 根据条件分页查询单击领用成本列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.06
	 */
	@Override
	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity)throws RuntimeException {
		return requisitionCostMapper.queryAllPageList(baseEntity);
	}
	
}
