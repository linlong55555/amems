package com.eray.thjw.aerialmaterial.dao;

import com.eray.thjw.aerialmaterial.po.MaterialToStore;

public interface MaterialToStoreMapper {
	
	/**
	 * @author liub
	 * @description  根据航材id查询航材仓库关系
	 * @param mainId
	 * @return MaterialToStore
	 * @develop date 2016.09.19
	 */
	public MaterialToStore getMaterialStoreByMainId(String mainId);
	
    int deleteByPrimaryKey(String id);

    int insert(MaterialToStore record);

    int insertSelective(MaterialToStore record);

    MaterialToStore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialToStore record);

    int updateByPrimaryKey(MaterialToStore record);
    
    void updateByPrimaryMainid(MaterialToStore record);
}