package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.StockHistory;

/**
 * @Description 库存履历主信息mapper
 * @CreateTime 2018年3月13日 下午4:41:03
 * @CreateBy 韩武
 */
public interface StockHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(StockHistory record);

    int insertSelective(StockHistory record);

    StockHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StockHistory record);

    int updateByPrimaryKey(StockHistory record);
}