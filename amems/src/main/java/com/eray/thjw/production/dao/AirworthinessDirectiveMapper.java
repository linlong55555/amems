package com.eray.thjw.production.dao;

import java.util.List;

import com.eray.thjw.production.po.AirworthinessDirective;

public interface AirworthinessDirectiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(AirworthinessDirective record);

    int insertSelective(AirworthinessDirective record);

    AirworthinessDirective selectByPrimaryKey(String id) ;

    int updateByPrimaryKeySelective(AirworthinessDirective record);

    int updateByPrimaryKey(AirworthinessDirective record);

	List<AirworthinessDirective> queryList(AirworthinessDirective record);
	
	List<AirworthinessDirective> queryPage(AirworthinessDirective record);
}