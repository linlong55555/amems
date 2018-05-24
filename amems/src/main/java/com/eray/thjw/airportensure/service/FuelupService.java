package com.eray.thjw.airportensure.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.airportensure.po.Fuelup;
import com.eray.thjw.exception.BusinessException;

public interface FuelupService {

	/**
	 * 按条件查询飞机加油单
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Fuelup> queryAllPageList(Fuelup fuelup)  throws RuntimeException;
	
	void save(Fuelup fuelup)throws RuntimeException, BusinessException;

	void update(Fuelup fuelup)throws RuntimeException, BusinessException;

	void cancel(String id)throws RuntimeException;
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 飞机加油单明细列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	 Map<String, Object> queryfuelupDetailList(Fuelup fuelup)throws RuntimeException;
	 /**
	  * @author sunji
	  * @description 
	  * @param request,model
	  * @return 发油人加油单明细列表
	  * @throws Exception 
	  * @develop date 2016.07.25
	  */
	 Map<String, Object> queryfuelupFyrDetailList(Fuelup fuelup)throws RuntimeException;
	 /**
	  * @author sunji
	  * @description 
	  * @param request,model
	  * @return 飞机加油单汇总列表
	  * @throws Exception 
	  * @develop date 2016.07.25
	  */
	 Map<String, Object> queryfuelupSummaryList(Fuelup fuelup)throws RuntimeException;
	 /**
	  * @author sunji
	  * @description 
	  * @param request,model
	  * @return 发油人加油单汇总列表
	  * @throws Exception 
	  * @develop date 2016.07.25
	  */
	 Map<String, Object> fuelupFyrSummaryList(Fuelup fuelup)throws RuntimeException;

	

}
