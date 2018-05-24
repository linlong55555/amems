package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.UserToModelRoleMapper;
import com.eray.thjw.po.UserToModelRole;
import com.eray.thjw.service.UserToModelRoleService;
@Service
public class UserToModelRoleServiceImple implements UserToModelRoleService{

	@Autowired
	private UserToModelRoleMapper userToModelRoleMapper;
	//添加
	@Override
	public int insert(UserToModelRole userToRole) {
		return userToModelRoleMapper.insert(userToRole);
	}
	
	//根据id查询所有角色
	@Override
	public List<UserToModelRole> queryRole(String userId) {

		UserToModelRole userToRole=new UserToModelRole();
		userToRole.setUserId(userId);
		return userToModelRoleMapper.queryRole(userToRole);
	}

	//查询所有除id对应角色以外的角色信息
	public List<UserToModelRole> queryUserNotRole(String userId) {
		
		UserToModelRole userToRole=new UserToModelRole();
		userToRole.setUserId(userId);
		return userToModelRoleMapper.queryUserNotRole(userToRole);
	}

	@Override
	public int delete(UserToModelRole userToRole) {
		return userToModelRoleMapper.delete(userToRole);
	}

	@Override
	public List<UserToModelRole> getParentRegionAll(UserToModelRole userToRole) {
		
		return userToModelRoleMapper.getParentRegionAll(userToRole);
	}

}
