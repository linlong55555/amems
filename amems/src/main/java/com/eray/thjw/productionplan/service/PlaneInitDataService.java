package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneInitData;

import enu.LogOperationEnum;
import enu.UpdateTypeEnum;

public interface PlaneInitDataService {
	
	void add(List<PlaneInitData> initDatas, String fjzch,String dprtcode, UpdateTypeEnum type, 
			String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	//新增初始化数据
	
	void deleteByFjzch(String fjzch,String dprtcode, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	//根据飞机注册号删除飞机初始化数据
	
	PlaneInitData selectByKey(PlaneInitData pd)throws Exception;       //  根据给出条件查询飞机数据
	
	/**
	 * 根据飞机注册号查询对应的初始化数据
	 * @return
	 */
	Map<String, Object> findByFjzch(String pd, String dprtcode);
}
