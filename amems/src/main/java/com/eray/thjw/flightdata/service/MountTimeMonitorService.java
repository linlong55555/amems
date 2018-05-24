package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.MountTimeMonitor;

import enu.LogOperationEnum;

/**
 * b_s_00602010101 装上件-时控件设置表
 * @author hanwu
 *
 */
public interface MountTimeMonitorService {
	
    /**
     * 保存装上件-时控件设置
     * @param data
     */
    void save(List<MountTimeMonitor> settings, String mainid, String czls, LogOperationEnum logOperationEnum, String fxjldid, String dprtcode);
    
    /**
     * 根据装上件id查找对应的时控件设置
     * @param mainid
     * @return
     */
    List<MountTimeMonitor> findByMainid(String mainid);
	
}
