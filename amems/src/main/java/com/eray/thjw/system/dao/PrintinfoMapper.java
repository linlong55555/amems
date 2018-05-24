package com.eray.thjw.system.dao;

import com.eray.thjw.system.po.Printinfo;

public interface PrintinfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Printinfo record);

    int insertSelective(Printinfo record);

    Printinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Printinfo record);

    int updateByPrimaryKey(Printinfo record);
}