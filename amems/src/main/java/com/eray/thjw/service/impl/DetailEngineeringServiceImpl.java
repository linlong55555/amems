package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.DetailEngineeringMapper;
import com.eray.thjw.po.DetailEngineering;
import com.eray.thjw.service.DetailEngineeringService;
@Service
public class DetailEngineeringServiceImpl implements DetailEngineeringService{

	@Autowired
	private DetailEngineeringMapper detailEngineeringMapper;
	/**
	 * @author sunji
	 * @description 根据mainid查询数据
	 * @param mainid
	 * @return List
	 * @develop date 2016.09.13
	 *
	 */
	public List<DetailEngineering> selectByPrimaryKeyMainid(String id) {
		return detailEngineeringMapper.selectByPrimaryKeyMainid(id);
	}

	@Override
	public DetailEngineering selectByPrimaryKey(String id) {
		return detailEngineeringMapper.selectByPrimaryKey(id);
	}

	
}
