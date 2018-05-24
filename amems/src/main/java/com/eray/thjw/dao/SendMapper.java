package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.Send;

public interface SendMapper {
    int insert(Send record);

    int insertSelective(Send record);
    
    List<Send> querySendAll(Send record);
    
    int deleteSend(String mainid);
    
    List<Send> queryAll(Send send);
    
    List<Send> selectByPrimaryKey(Send send);
    
    int delete(Send send);
    
    int updateByPrimaryKeySelective(Send send);
    
    int updateByMainid(Send send);
    
    int updateJszt(Send send);
}