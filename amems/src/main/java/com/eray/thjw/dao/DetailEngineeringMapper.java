package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.DetailEngineering;

public interface DetailEngineeringMapper {
    int deleteByPrimaryKey(String id);

    int insert(DetailEngineering record);

    int insertSelective(DetailEngineering record);

    DetailEngineering selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DetailEngineering record);

    int updateByPrimaryKey(DetailEngineering record);
    
    List<DetailEngineering> selectByPrimaryKeyMainid(String id);
    
}