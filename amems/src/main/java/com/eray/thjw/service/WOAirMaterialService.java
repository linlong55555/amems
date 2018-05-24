package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.WOAirMaterial;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;

public interface WOAirMaterialService {
    int insertSelective(WorkOrder wrokorder)throws RuntimeException;                  // 对工单航材耗材表执行增加操作
    
    void save(ScheduledCheckItem scheduledCheckItem) throws Exception;	//对工单航材耗材表执行增加操作

    List<WOAirMaterial> selectWOAirMaterialList(WOAirMaterial record);        //查询工单的航材和耗材
    /**
	 * @author sunji
	 * @description 根据基础id查询已选航材
	 * @develop date 2016.08.15
	 */
    public List<WOAirMaterial> selectedHcList(WOAirMaterial wOAirMaterial)throws RuntimeException;  
    
    
    int doByAirMaterial(WorkOrder workorder);
    
    int deleteById(String id);
    
    int updateByAirMaterial(WOAirMaterial wOAirMaterial);
   
}
