package com.eray.thjw.airportensure.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.airportensure.po.Fuelup;


public interface FuelupMapper {
    int insert(Fuelup record);

    int insertSelective(Fuelup record);
    
	/**
	 * 按条件查询飞机加油单
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Fuelup> queryAllPageList(Fuelup fuelup)  throws RuntimeException;
	
	void delete(String id)throws RuntimeException;

	void updateByPrimaryKeySelective(Fuelup fuelup) throws RuntimeException;
	
	List <Fuelup> queryfuelupDetailList(Fuelup fuelup)throws RuntimeException;
	//查询飞机注册号（表头）
	List<String> queryAllFjzch(Fuelup fuelup)throws RuntimeException;
	//查询家有人（表头）
	List<String> queryAllFyr(Fuelup fuelup)throws RuntimeException;
	
	int queryfuelupDetailListCount(Fuelup fuelup)throws RuntimeException;
	
	List<Fuelup> queryfuelupFjSummaryList(Fuelup fuelup) throws RuntimeException;

	List<Fuelup> queryfuelupFyrSummaryList(Fuelup fuelup) throws RuntimeException;
	
	List<Map<String, String>> queryfuelupJdList(Fuelup fuelup) throws RuntimeException;
	
}