package com.eray.thjw.aerialmaterial.dao;

import com.eray.thjw.aerialmaterial.po.CancelStock;

public interface CancelStockMapper {
    int deleteByPrimaryKey(String id);

    int insert(CancelStock record);

    int insertSelective(CancelStock record);

    CancelStock selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CancelStock record);

    int updateByPrimaryKey(CancelStock record);
}