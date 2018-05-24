package com.eray.thjw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RoleToBtnMapper;
import com.eray.thjw.po.RoleToBtn;
import com.eray.thjw.service.RoleToBtnService;

@Service
public class RoleToBtnServiceImpl implements RoleToBtnService {

private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private RoleToBtnMapper roleToBtnMapper;
	
	
	@Override
	public List<RoleToBtn> queryAllPageList(RoleToBtn roleToBtn)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryCount(RoleToBtn roleToBtn) throws Exception {
		logger.debug("进入 queryCount RoleToBtnServiceImpl");
		
		try {
			return roleToBtnMapper.queryCount(roleToBtn);
		} catch (Exception e) {
			
		logger.error("ServiceException queryCount RoleToBtnServiceImpl：",e);
		throw new Exception("ServiceException queryCount RoleToBtnServiceImpl：",e);
		}
	}

	@Override
	public void save(RoleToBtn roleToBtn) throws Exception {
		
		try {
			roleToBtnMapper.save(roleToBtn);
			 
		} catch (Exception e) {
		
		throw new Exception("ServiceException save RoleToBtnServiceImpl：",e);
		}
		finally{
		}
	}

	@Override
	public void delete(RoleToBtn roleToBtn) throws Exception {
		
		try {
			roleToBtnMapper.delete(roleToBtn);
			 
		} catch (Exception e) {
		
		throw new Exception("ServiceException delete RoleToBtnServiceImpl：",e);
		}
		finally{
		}
	}

	
	@Override
	public Map<String, Object> modify(RoleToBtn roleToBtn) throws Exception {
		return null;
	}
	
	/**
	 * 根据角色ID进行查询
	 * @param roleId
	 * @return
	 */
	public List<RoleToBtn> queryByRoleId(String roleId){
		return roleToBtnMapper.selectByRole(roleId);
	}
	
	public int deleteRoleBtns(String roleId, List<String> btnIds){
		int length = btnIds.size();
		for (int i = 0; i < length; i = i+50) {
			this.roleToBtnMapper.deleteRoleBtns(roleId, btnIds.subList(i, i+50>length?length:i+50 ));
		}
		return length;
	}
	
	public int insertRoleBtns(String roleId, List<String> btnIds){
		for (String str : btnIds) {
			String id=UUID.randomUUID().toString();
			 this.roleToBtnMapper.insertRoleBtn(id,roleId, str);
		}
		return btnIds.size();
	}
}
