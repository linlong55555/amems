package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.MountSubcomponent;

import enu.LogOperationEnum;

/**
 * b_s_00602010104 子部件关系
 * @author hanwu
 *
 */
public interface MountSubcomponentService {
	
    /**
     * 保存子部件关系
     * @param data
     */
    void save(List<MountSubcomponent> children, String mainid, String czls, LogOperationEnum logOperationEnum, String fxjldid, String dprtcode);
    
    /**
     * 查找父节点下的所有子节点
     * @param mainid
     * @return
     */
    List<MountSubcomponent> findByMainid(String mainid);
}
