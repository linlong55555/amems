package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.produce.po.Workorder;

/**
 * b_s_00602 飞行记录关联计划
 * @author hanwu
 *
 */
public interface FlightRecordSheetToPlanService {
	
    /**
     * 保存飞行记录关联计划
     * @param data
     */
    void save(FlightRecordSheetToPlan data);
    
    /**
     * 删除飞行记录关联计划
     * @param flightRecordSheet
     */
    void deleteNotExist(FlightRecordSheet flightRecordSheet);
	
    /**
     * 根据飞行记录单查找
     * @param fxjldid
     * @return
     */
    List<FlightRecordSheetToPlan> findByFxjldid(String fxjldid);
    
    List<FlightRecordSheetToPlan> getZlhAndId(FlightRecordSheetToPlan param);

    /**
     * 
     * @Description 查询工单指令号
     * @CreateTime 2017年10月7日 下午10:34:59
     * @CreateBy 胡才秋
     * @param data
     * @return
     */
	List<Workorder> getWorkorderList(Workorder data);
}
