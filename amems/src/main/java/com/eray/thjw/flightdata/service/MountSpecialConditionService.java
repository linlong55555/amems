package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.MountSpecialCondition;

import enu.LogOperationEnum;

/**
 * b_s_00602010102 装上件-时控件特殊飞行情况
 * @author hanwu
 *
 */
public interface MountSpecialConditionService {
	
    /**
     * 保存装上件-时控件特殊飞行情况
     * @param data
     */
    void save(List<MountSpecialCondition> conditions, String mainid, String czls, LogOperationEnum logOperationEnum, String fxjldid, String dprtcode);
    
    /**
     * 根据装上件id查询对应的时控件特殊飞行情况
     * @param mainid
     * @return
     */
    List<MountSpecialCondition> findByMainid(String mainid);
}
