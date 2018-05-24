package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.RoleToBtn;


/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface RoleToBtnService {

	public List<RoleToBtn> queryAllPageList(RoleToBtn roleToBtn) throws Exception;
	
	public int queryCount(RoleToBtn roleToBtn) throws Exception;
	
	public void save(RoleToBtn roleToBtn) throws Exception;
	
	public void delete(RoleToBtn roleToBtn) throws Exception;
	
	public Map<String, Object> modify(RoleToBtn roleToBtn) throws Exception;
	
	/**
	 * 根据角色ID进行查询
	 * @param roleId
	 * @return
	 */
	public List<RoleToBtn> queryByRoleId(String roleId);
	
	public int deleteRoleBtns(String roleId, List<String> btnIds);
	
	public int insertRoleBtns(String roleId, List<String> btnIds);
	
}
