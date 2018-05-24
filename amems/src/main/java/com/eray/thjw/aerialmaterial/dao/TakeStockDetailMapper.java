package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.TakeStockDetail;

public interface TakeStockDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(TakeStockDetail record);

    int insertSelective(TakeStockDetail record);

    TakeStockDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TakeStockDetail record);

    int updateByPrimaryKey(TakeStockDetail record);
    
    /**
	 * @author liub
	 * @description  根据盘点id查询盘点差异及履历信息、库存信息
	 * @param mainid
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.29
	 */
	public List<TakeStockDetail> queryByMainId(String mainid);
    
    /**
	 * @author liub
	 * @description  根据盘点差异id查询盘点差异及履历信息
	 * @param id
	 * @return TakeStockDetail
	 * @develop date 2016.11.25
	 */
  	public TakeStockDetail queryByDetailId(String id);
    
    /**
	 * @author liub
	 * @description  根据查询条件分页查询盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.23
	 */
	public List<TakeStockDetail> queryAllDetailPageList(TakeStockDetail record);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询库存及盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.23
	 */
	public List<TakeStockDetail> queryStockDetailPageList(TakeStockDetail record);
		
}