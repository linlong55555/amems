package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.UserToModelRole;

//机型角色
public interface UserToModelRoleService {

	public int insert(UserToModelRole userToRole);
	
	//根据id查询所有角色
	public List<UserToModelRole> queryRole(String userId);
	
	//查询所有除id对应角色以外的角色信息
	public List<UserToModelRole> queryUserNotRole(String userId);
	
	//删除
	public int delete(UserToModelRole userToRole);
	
	//查询根据角色id查询用户
	public List<UserToModelRole> getParentRegionAll(UserToModelRole userToRole);
}
