package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.flightdata.po.MountMonitorItem;

import enu.LogOperationEnum;

/**
 * b_s_0060201010301 装上件-定检件监控项目
 * @author hanwu
 *
 */
public interface MountMonitorItemService {
	
    /**
     * 保存装上件-定检件监控项目
     * @param data
     */
    void save(List<MountMonitorItem> list, String id, String czls, LogOperationEnum logOperationEnum, String fxjldid, String dprtcode);
	
}
