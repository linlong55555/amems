package com.eray.thjw.aerialmaterial.dao;


import java.util.List;

import com.eray.thjw.aerialmaterial.po.MaterialCost;

public interface MaterialCostMapper {
	
	List<MaterialCost> selectMaterialCostList(MaterialCost record);  //航材成本维护列表
	
	int insertSelective(MaterialCost record);  //新增航材成本维护
	
	
	int deleteByPrimaryKey(String id);    //删除航材成本维护记录
	
	

	int insert(MaterialCost record);

    int updateByPrimaryKeySelective(MaterialCost record);

    int updateByPrimaryKey(MaterialCost record);
    
    /**
     * 根据部件ID查询最新的航材成本数据
     * @param mainid
     * @return
     */
    MaterialCost selectNewestByBjid(String mainid);
}