package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Outstock;

public interface OutstockMapper {
    int deleteByPrimaryKey(String id);

    int insert(Outstock record);

    int insertSelective(Outstock record);

    Outstock selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Outstock record);

    int updateByPrimaryKey(Outstock record);
    
    /**
	 * 按条件查询一页出库单
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Outstock> queryAllPageList(Outstock outstock)  throws RuntimeException;
	 
	List<Outstock> selectByAll(List<String>   xgdjids) throws RuntimeException;

	Outstock selectById(String ckdh, String string)throws RuntimeException;

}