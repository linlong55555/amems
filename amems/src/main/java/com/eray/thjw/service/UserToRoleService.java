package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.UserToRole;

public interface UserToRoleService {

	public int insert(UserToRole userToRole);
	
	//根据id查询所有角色
	public List<UserToRole> queryRole(String userId);
	
	//查询所有除id对应角色以外的角色信息
	public List<UserToRole> queryUserNotRole(String userId);
	
	//删除
	public int delete(UserToRole userToRole);
	
	//查询根据角色id查询用户
	public List<UserToRole> getParentRegionAll(UserToRole userToRole);

	public int queryCount(UserToRole userToRole);
}
