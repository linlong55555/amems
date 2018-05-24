package com.eray.thjw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RoleToDprtMapper;
import com.eray.thjw.dao.RoleToStoreMapper;
import com.eray.thjw.po.RoleToDprt;
import com.eray.thjw.service.RoleToDprtService;
@Service
public class RoleToDprtServiceImpl implements RoleToDprtService {

private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private RoleToDprtMapper roleToDprtMapper;
	
	/**
	 * @author liub
	 * @description 角色仓库Mapper
	 * @develop date 2017.01.23
	 */
	@Autowired
	private RoleToStoreMapper roleToStoreMapper;
	
	
	@Override
	public List<RoleToDprt> queryAllPageList(RoleToDprt roleToDprt)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryCount(RoleToDprt roleToDprt) throws Exception {
		logger.debug("进入 queryCount RoleToDprtServiceImpl");
		
		try {
			return roleToDprtMapper.queryCount(roleToDprt);
		} catch (Exception e) {
			
		logger.error("ServiceException queryCount RoleToDprtServiceImpl：",e);
		throw new Exception("ServiceException queryCount RoleToDprtServiceImpl：",e);
		}
	}

	@Override
	public void save(RoleToDprt roleToDprt) throws Exception {
		logger.debug("进入 save RoleToDprtServiceImpl");
		
		if (roleToDprt == null) {
			logger.error("role是null save RoleToDprtServiceImpl");
			throw new Exception("role是null save RoleToDprtServiceImpl");
		}
		
		try {
			String id=UUID.randomUUID().toString();
			roleToDprt.setId(id);
			roleToDprtMapper.save(roleToDprt);
			 
		} catch (Exception e) {
		
		logger.error("ServiceException save RoleToDprtServiceImpl：",e);
		throw new Exception("ServiceException save RoleToDprtServiceImpl：",e);
		}
		finally{
		}
	}

	@Override
	public void delete(RoleToDprt roleToDprt) throws Exception {
		logger.debug("进入 delete RoleToDprtServiceImpl");
		
		if (roleToDprt == null) {
			logger.error("role是null delete RoleToDprtServiceImpl");
			throw new Exception("role是null delete RoleToDprtServiceImpl");
		}
		
		try {
			roleToDprtMapper.delete(roleToDprt);
			roleToStoreMapper.deleteByRD(String.valueOf(roleToDprt.getRoleId()), roleToDprt.getDprtId());
			 
		} catch (Exception e) {
		
		logger.error("ServiceException delete RoleToDprtServiceImpl：",e);
		throw new Exception("ServiceException delete RoleToDprtServiceImpl：",e);
		}
		finally{
		}
	}

	
	@Override
	public void modify(RoleToDprt roleToDprt) throws Exception {
		// TODO Auto-generated method stub
		roleToDprtMapper.modify(roleToDprt);
	}

	@Override
	public RoleToDprt selectNum(RoleToDprt roleToDprt) throws Exception {
		
		return roleToDprtMapper.selectNum(roleToDprt);
	}

	
}
