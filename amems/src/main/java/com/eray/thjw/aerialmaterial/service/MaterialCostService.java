package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.MaterialCost;

public interface MaterialCostService {
	
	List<MaterialCost> selectMaterialCostList(MaterialCost record);  //航材成本维护列表
	
	int insertSelective(MaterialCost record);  //新增航材成本维护

	int deleteByPrimaryKey(String id);    //删除航材成本维护记录
	
}
