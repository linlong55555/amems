package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.JX_BJ;
import com.eray.thjw.po.PlaneModelData;

public interface JX_BJService {
    int insertSelective(PlaneModelData record)throws  RuntimeException;                //增加关联机型部件
    
    List<JX_BJ> selectByPrimaryKey(JX_BJ record)throws  RuntimeException;   //根据飞机机型的某个条件查出关联的部件号的集合
    
	 void deleteJX_BJ(String fjjx)throws  RuntimeException;   //  根据飞机机型删除关联部件表的信息
}
