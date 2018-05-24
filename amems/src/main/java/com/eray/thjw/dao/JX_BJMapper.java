package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.JX_BJ;

public interface JX_BJMapper {
    
	int insert(JX_BJ record);

    int insertSelective(JX_BJ record);                //增加关联机型部件
    
    List<JX_BJ> selectByPrimaryKey(JX_BJ record);   //根据飞机机型的某个条件查出关联的部件号的集合
    
    int updateByPrimaryKeySelective(JX_BJ record);  //根据fjjx实现飞机机型关联部件号的更新
    
   void deleteJX_BJ(String fjjx);   //  根据飞机机型删除关联部件表的信息
	
}