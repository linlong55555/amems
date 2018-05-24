package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.WOJobContent;

public interface WOJobContentMapper {
    int deleteByPrimaryKey(String id);
//
//    int insert(WOJobContent record);

    int insertSelective(WOJobContent record);    //对工单的工作内容执行增加操作

     List<WOJobContent> selectByWOJobContentList(WOJobContent record);        //查询工队的所有工作内容
// 
    int updateByPrimaryKeySelective(WOJobContent record);

//    int updateByPrimaryKey(WOJobContent record);
     List<WOJobContent> selectedGznrList(WOJobContent wOJobContent);
     
     int doByJobContent(WOJobContent wOJobContent);
     
     int deleteById(String id);
     
     int updateByJobContent(WOJobContent wOJobContent);
}