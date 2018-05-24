package com.eray.thjw.productionplan.service;

import com.eray.thjw.productionplan.po.PlaneData;

import enu.LogOperationEnum;

public interface PlaneDailyUsageService {
	
	void add(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	// 新增飞机日使用量
	
	void edit(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	// 修改飞机日使用量
	
	/**
	 * 删除飞机日使用量
	 * @param pd
	 * @param czls
	 * @param logOperationEnum
	 */
	void delete (PlaneData pd, String czls, LogOperationEnum logOperationEnum);
	
}
