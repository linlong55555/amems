package com.eray.thjw.dao;


import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Role;


/**
 * @author liub
 * @description 角色 Mapper
 * @develop date 2016.07.29
 */
public interface RoleMapper {
	
	 List<Role> queryAllPageList(Role role) throws Exception;
	 
	 int queryCount(Role role)throws Exception;
	
	 Role queryAll(Role role) throws Exception;
	
	 int save(Role role) throws Exception;
	
	 Map<String, Object> modify(Role role) throws Exception;
	
	 void delete(Role role) throws Exception;

	 
	 List<Role> queryAllRole(Role role)throws Exception;

	 
	 public Role findOneByRoleID(String id) throws Exception;

	 List <Role> queryRole(String id);

	 List <Role> queryUserNotRole(String id);
	 
	 public Map<String, Object> checkUpdMt(String roleCode) throws Exception;//角色代码唯一的验证
	 
	 public Map<String, Object> checkUpdRole(String roleCode) throws Exception;//角色代码修改唯一的验证
}
