package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.Oilprice;

public interface OilpriceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Oilprice record);

    int insertSelective(Oilprice record);

    Oilprice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Oilprice record);

    int updateByPrimaryKey(Oilprice record);
    
    List<Oilprice> selectByDprtcode(String dprtcode);
    
    List<Oilprice> selectByYpgg(Oilprice oilprice);
    
}