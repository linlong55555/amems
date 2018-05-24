package com.eray.thjw.oil.dao;

import java.util.List;

import com.eray.thjw.po.Oil;

public interface OilMapper {
    int deleteByPrimaryKey(String id);

    int insert(Oil record);

    int insertSelective(Oil record);

    Oil selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Oil record);

    int updateByPrimaryKey(Oil record);
    
    List<Oil> queryAll(Oil record);
    
    int validationYpgg(Oil record);
    
    List<Oil> queryByDprtcode(String dprtcode);
}