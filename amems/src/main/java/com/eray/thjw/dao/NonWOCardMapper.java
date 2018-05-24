package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.NonWOCard;

public interface NonWOCardMapper {
    int deleteByPrimaryKey(String id);
//
//    int insert(NonWOCard record);

      int insertSelective(NonWOCard record);        //增加非例行工单 相关工卡的数据
    
     List<NonWOCard> selectByNonWOCardList(NonWOCard record);        //查询工单的关联工单集合

     int doByWOCard(NonWOCard record);                 //更新工单的相关工单
     
     int deleteById(String id);                               //删除工单的相关工单
     
     

     //    NonWOCard selectByPrimaryKey(String id);
//
//
//    int updateByPrimaryKey(NonWOCard record);
     
     List<NonWOCard> selectedXggkList(NonWOCard record);
     
     int updateByPrimaryKeySelective(NonWOCard record);
     
}