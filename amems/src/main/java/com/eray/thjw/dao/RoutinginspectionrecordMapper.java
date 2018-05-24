package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.Routinginspectionrecord;

public interface RoutinginspectionrecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(Routinginspectionrecord record);

    int insertSelective(Routinginspectionrecord record);

    Routinginspectionrecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Routinginspectionrecord record);

    int updateByPrimaryKey(Routinginspectionrecord record);
    
    List<Routinginspectionrecord> queryAll(Routinginspectionrecord record);
}