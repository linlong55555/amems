package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.flightdata.po.FlightRecordSheet;


public interface ComponentUsageService {
	
	/**
	 * 汇总部件使用情况 
	 * @param record
	 * @return
	 * @throws RuntimeException
	 */
	ComponentUsage sumComponentUsage(ComponentUsage record) throws RuntimeException;	
	
	/**
	 * 根据飞机注册号统计所有一级子部件的部件使用情况，截止至某次飞行记录单为止
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
	 * 根据飞行记录单号统计航次对应的一级子部件的部件使用情况
	 * @param paramMap
	 * @return
	 */
	List<ComponentUsage> sumComponentUsageByFxjldhHc(Map<String, Object> paramMap);
	
}
