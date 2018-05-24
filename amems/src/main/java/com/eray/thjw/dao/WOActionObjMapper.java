package com.eray.thjw.dao;

import com.eray.thjw.po.WOActionObj;

public interface WOActionObjMapper {
//    int deleteByPrimaryKey(String id);
//
//    int insert(WOActionObj record);

    int insertSelective(WOActionObj record);                                                  //对工单的执行对象表进行操作
    
    WOActionObj selectByActionObj(WOActionObj woobj);                     // 查询工单的执行对象

//    WOActionObj selectByPrimaryKey(String id);
//
//    int updateByPrimaryKeySelective(WOActionObj record);
//
//    int updateByPrimaryKey(WOActionObj record);
    
    int doByActionObj(WOActionObj record);                  //更新工单
    
    int updateByActionObj(WOActionObj record);                  //更新工单
    
    int  deleteByMainid(String mainid);                     
    
}