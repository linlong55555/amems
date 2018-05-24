package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.flightdata.po.FlightRecordSheet;

public interface ComponentUsageMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComponentUsage record);

    int insertSelective(ComponentUsage record);

    ComponentUsage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComponentUsage record);

    int updateByPrimaryKey(ComponentUsage record);
    /**
     * 报废审核新增使用情况
     * @param record
     * @return
     */
    int insertComponentUsage(String bjh,String sn, String dprtcode);
    /**
     * 报废审核删除使用情况
     * @param record
     * @return
     */
    int deleteComponentUsage(String bjh,String sn, String dprtcode);
    /**
     * 根据件号、序列号统计部件使用情况
     * @param record
     * @return
     */
    ComponentUsage sumComponentUsage(ComponentUsage record);
    
    /**
     * 根据飞机注册号统计所有一级子部件的部件使用情况
     * @param fjzch
     * @return
     */
    List<ComponentUsage> sumComponentUsageByPlane(FlightRecordSheet sheet);
    
    /**
     * 根据飞行记录单号统计这次拆解一级子部件的部件使用情况
     * @param fxjldh
     * @return
     */
    List<ComponentUsage> sumComponentUsageByFxjldh(String fxjldh);
    
    /**
     * 根据飞行记录单号统计航次对应的一级子部件的部件使用情况
     * @param paramMap
     * @return
     */
    List<ComponentUsage> sumComponentUsageByFxjldhHc(Map<String, Object> paramMap);
    
}