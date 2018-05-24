package com.eray.thjw.dao;

import com.eray.thjw.po.QualityClose;

public interface QualityCloseMapper {
    int deleteByPrimaryKey(String id);

    int insert(QualityClose record);

    int insertSelective(QualityClose record);

    QualityClose selectByPrimaryKey(String id);
    
    QualityClose selectByMainId(String mainid);

    int updateByPrimaryKeySelective(QualityClose record);

    int updateByPrimaryKey(QualityClose record);
}