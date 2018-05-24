package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.TakeStockScope;

public interface TakeStockScopeMapper {
    int deleteByPrimaryKey(String id);

    int insert(TakeStockScope record);

    int insertSelective(TakeStockScope record);

    TakeStockScope selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TakeStockScope record);

    int updateByPrimaryKey(TakeStockScope record);
    
    /**
	 * @author liub
	 * @description  根据盘点id查询盘点范围列表
	 * @param ckid
	 * @return List<TakeStockScope>
	 * @develop date 2016.11.22
	 */
	public List<TakeStockScope> queryTakeScopeListByMainId(String mainid);
}