package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.MountFixedMonitor;

import enu.LogOperationEnum;

/**
 * b_s_00602010103 装上件-定检件定检项目
 * @author hanwu
 *
 */
public interface MountFixedMonitorService {
	
    /**
     * 保存装上件-定检件定检项目
     * @param data
     */
    void save(List<MountFixedMonitor> list, String czls, LogOperationEnum logOperationEnum, String fxjldid, String dprtcode);
    
    /**
     * 根据装上件id查询对应的定检件定检项目
     * @param mainid
     * @return
     */
    List<MountFixedMonitor> findByMainid(String mainid);
	
}
