package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.TakeStock;

public interface TakeStockMapper {
    int deleteByPrimaryKey(String id);

    int insert(TakeStock record);

    int insertSelective(TakeStock record);

    TakeStock selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TakeStock record);

    int updateByPrimaryKey(TakeStock record);
    
    /**
	 * @author liub
	 * @description  根据id查询盘点单
	 * @param id
	 * @return TakeStock
	 * @develop date 2016.11.22
	 */
    TakeStock getById(String id);
    
    /**
	 * @author liub
	 * @description  根据仓库id查询盘点单列表
	 * @param ckid
	 * @return List<TakeStock>
	 * @develop date 2016.11.22
	 */
	public List<TakeStock> queryListByCkid(String ckid);
    
    /**
	 * @author liub
	 * @description  根据查询条件分页查询盘点单信息
	 * @param record
	 * @return List<TakeStock>
	 * @develop date 2016.11.21
	 */
	public List<TakeStock> queryAllPageList(TakeStock record);
		
}