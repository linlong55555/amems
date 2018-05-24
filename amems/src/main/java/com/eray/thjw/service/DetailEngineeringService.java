package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.DetailEngineering;

public interface DetailEngineeringService {

	
	/**
	 * @author sunji
	 * @description 根据mainid查询数据
	 * @param mainid
	 * @return List
	 * @develop date 2016.09.13
	 *
	 */
	public List<DetailEngineering> selectByPrimaryKeyMainid(String id);
	 
	 public DetailEngineering selectByPrimaryKey(String id);
}
