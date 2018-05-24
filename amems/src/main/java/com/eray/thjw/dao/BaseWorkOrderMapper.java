package com.eray.thjw.dao;

import com.eray.thjw.po.BaseWorkOrder;

public interface BaseWorkOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(BaseWorkOrder record);

    int insertSelective(BaseWorkOrder record);

    BaseWorkOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BaseWorkOrder record);

    int updateByPrimaryKey(BaseWorkOrder record);
    public BaseWorkOrder selectKey(String gdbh) throws RuntimeException;
}