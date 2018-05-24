package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.RoleToMenu;

public interface RoleToMenuMapper {
	public List<RoleToMenu> queryAllPageList(RoleToMenu roleToMenu) throws Exception;
	
	public int queryCount(RoleToMenu roleToMenu) throws Exception;
	
	public void save(RoleToMenu roleToMenu) throws Exception;
	
	public Map<String, Object> modify(RoleToMenu roleToMenu) throws Exception;
	   
	public void delete(RoleToMenu roleToMenu) throws Exception;
	
	/**
	 * 根据角色查询
	 * @param role
	 * @return
	 */
	List<RoleToMenu> selectByRole(String roleId);
	
	int deleteRoleMenus(@Param("roleId")String roleId, @Param("menuIds")List<String> menuIds);
	
	int insertRoleMenu(String id,String roleId, String menuId);
	
}
