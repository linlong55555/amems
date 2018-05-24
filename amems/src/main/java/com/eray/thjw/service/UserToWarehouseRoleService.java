package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.UserToWarehouseRole;
//库房角色
public interface UserToWarehouseRoleService {

	public int insert(UserToWarehouseRole userToRole);
	
	//根据id查询所有角色
	public List<UserToWarehouseRole> queryRole(String userId);
	
	//查询所有除id对应角色以外的角色信息
	public List<UserToWarehouseRole> queryUserNotRole(String userId);
	
	//删除
	public int delete(UserToWarehouseRole userToRole);
	
	//查询根据角色id查询用户
	public List<UserToWarehouseRole> getParentRegionAll(UserToWarehouseRole userToRole);
}
