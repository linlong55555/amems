package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.UserToRoleMapper;
import com.eray.thjw.po.UserToRole;
import com.eray.thjw.service.UserToRoleService;
@Service
public class UserToRoleServiceImple implements UserToRoleService{

	@Autowired
	private UserToRoleMapper userToRoleMapper;
	//添加
	@Override
	public int insert(UserToRole userToRole) {
		return userToRoleMapper.insert(userToRole);
	}
	
	//根据id查询所有角色
	@Override
	public List<UserToRole> queryRole(String userId) {

		UserToRole userToRole=new UserToRole();
		userToRole.setUserId(userId);
		return userToRoleMapper.queryRole(userToRole);
	}

	//查询所有除id对应角色以外的角色信息
	public List<UserToRole> queryUserNotRole(String userId) {
		
		UserToRole userToRole=new UserToRole();
		userToRole.setUserId(userId);
		return userToRoleMapper.queryUserNotRole(userToRole);
	}

	@Override
	public int delete(UserToRole userToRole) {
		return userToRoleMapper.delete(userToRole);
	}

	@Override
	public List<UserToRole> getParentRegionAll(UserToRole userToRole) {
		
		return userToRoleMapper.getParentRegionAll(userToRole);
	}

	@Override
	public int queryCount(UserToRole userToRole) {
		return userToRoleMapper.queryCount(userToRole);
	}

}
