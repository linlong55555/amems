package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.WOJobEnclosure;

public interface WOJobEnclosureMapper {
    int deleteByPrimaryKey(String id);
//
//    int insert(WOJobEnclosure record);

    int insertSelective(WOJobEnclosure record);     // 对工单的附件表执行增加操作

    WOJobEnclosure selectByPrimaryKey(String id);    //附件下载
//
    int updateByPrimaryKeySelective(WOJobEnclosure record);
//
     int doByWOJobEnclosure(WOJobEnclosure record);
     
     int updateByWOJobEnclosure(WOJobEnclosure record);
     
    int deleteById(String id);
     
    public List<WOJobEnclosure> selectedFjList(WOJobEnclosure wOJobEnclosure);
}