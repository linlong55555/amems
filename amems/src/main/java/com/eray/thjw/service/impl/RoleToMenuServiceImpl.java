package com.eray.thjw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RoleToMenuMapper;
import com.eray.thjw.po.RoleToMenu;
import com.eray.thjw.service.RoleToMenuService;
@Service
public class RoleToMenuServiceImpl implements RoleToMenuService {

private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private RoleToMenuMapper roleToMenuMapper;
	
	
	@Override
	public int queryCount(RoleToMenu roleToMenu) throws Exception {
		logger.debug("进入 queryCount RoleToMenuServiceImpl");
		
		try {
			return roleToMenuMapper.queryCount(roleToMenu);
		} catch (Exception e) {
			
		logger.error("ServiceException queryCount RoleToMenuServiceImpl：",e);
		throw new Exception("ServiceException queryCount RoleToMenuServiceImpl：",e);
		}
	}

	@Override
	public void save(RoleToMenu roleToMenu) throws Exception {
		if (roleToMenu == null) {
			throw new Exception("role是null save RoleToMenuServiceImpl");
		}
		try {
			String id=UUID.randomUUID().toString();
			roleToMenu.setId(id);
			roleToMenuMapper.save(roleToMenu);
			 
		} catch (Exception e) {
		throw new Exception("ServiceException save RoleToMenuServiceImpl：",e);
		}
		finally{
		}
	}

	@Override
	public void delete(RoleToMenu roleToMenu) throws Exception {
		if (roleToMenu == null) {
			throw new Exception("role是null delete RoleToMenuServiceImpl");
		}
		try {
			roleToMenuMapper.delete(roleToMenu);
		} catch (Exception e) {
		throw new Exception("ServiceException delete RoleToMenuServiceImpl：",e);
		}
		finally{
		}
	}

	
	@Override
	public Map<String, Object> modify(RoleToMenu roleToMenu) throws Exception {
		return null;
	}
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	public List<RoleToMenu> queryByRoleId(String roleId) {
		return roleToMenuMapper.selectByRole(roleId);
	}
	
	public int deleteRoleMenus(String roleId, List<String> menuIds){
		int length = menuIds.size();
		for (int i = 0; i < length; i = i+50) {
			this.roleToMenuMapper.deleteRoleMenus(roleId, menuIds.subList(i, i+50>length?length:i+50 ));
		}
		return length;
	}
	
	public int insertRoleMenus(String roleId, List<String> menuIds){
		for (String str : menuIds) {
			String id=UUID.randomUUID().toString();
			this.roleToMenuMapper.insertRoleMenu(id,roleId, str);
		}
		return menuIds.size();
	}

	
}
