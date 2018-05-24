package com.eray.thjw.service;


import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.po.WorkOrder;

public interface WOActionObjService {

    int insertSelective(WorkOrder wrokorder)throws RuntimeException;     //对工单的执行对象表进行操作
    
    WOActionObj selectByActionObj(WOActionObj woobj)throws RuntimeException;                     // 查询工单的执行对象
    
    int doByActionObj(WorkOrder workorder);                  //更新工单
    
    int updateByActionObj(WOActionObj woActionObj);                  //更新工单
    
    int  deleteByMainid(String mainid);      
    
}
