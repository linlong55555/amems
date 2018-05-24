package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Destruction;

public interface DestructionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Destruction record);

    int insertSelective(Destruction record);

    Destruction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Destruction record);

    int updateByPrimaryKey(Destruction record);
    
    List<Destruction> queryDestructionList(Destruction record);
}