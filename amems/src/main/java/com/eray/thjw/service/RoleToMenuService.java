package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.RoleToMenu;


/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface RoleToMenuService {

	public int queryCount(RoleToMenu roleToMenu) throws Exception;
	
	public void save(RoleToMenu roleToMenu) throws Exception;
	
	public void delete(RoleToMenu roleToMenu) throws Exception;
	
	public Map<String, Object> modify(RoleToMenu roleToMenu) throws Exception;
	
	/**
	 * 根据角色ID查询
	 * @param roleId
	 * @return
	 */
	public List<RoleToMenu> queryByRoleId(String roleId);
	
	public int deleteRoleMenus(String roleId, List<String> menuIds);
	
	public int insertRoleMenus(String roleId, List<String> menuIds);
	
}
