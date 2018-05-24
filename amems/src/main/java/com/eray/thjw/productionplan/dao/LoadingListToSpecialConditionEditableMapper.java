package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.productionplan.po.LoadingListToSpecialCondition;

public interface LoadingListToSpecialConditionEditableMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoadingListToSpecialCondition record);

    int insertSelective(LoadingListToSpecialCondition record);

    LoadingListToSpecialCondition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoadingListToSpecialCondition record);

    int updateByPrimaryKey(LoadingListToSpecialCondition record);
    
    List<LoadingListToSpecialCondition> select(LoadingListToSpecialCondition record);
    
    int deleteByParam(LoadingListToSpecialCondition record);
}