package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.RoutinginspectionrecordDetail;

public interface RoutinginspectionrecordDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(RoutinginspectionrecordDetail record);

    int insertSelective(RoutinginspectionrecordDetail record);

    RoutinginspectionrecordDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoutinginspectionrecordDetail record);

    int updateByPrimaryKey(RoutinginspectionrecordDetail record);
    
    List<RoutinginspectionrecordDetail> queryAllByMainid(RoutinginspectionrecordDetail record);
}