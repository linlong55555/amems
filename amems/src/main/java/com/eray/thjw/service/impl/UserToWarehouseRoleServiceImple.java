package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.UserToWarehouseRoleMapper;
import com.eray.thjw.po.UserToWarehouseRole;
import com.eray.thjw.service.UserToWarehouseRoleService;
@Service
public class UserToWarehouseRoleServiceImple implements UserToWarehouseRoleService{

	@Autowired
	private UserToWarehouseRoleMapper userToWarehouseRoleMapper;
	//添加
	@Override
	public int insert(UserToWarehouseRole userToRole) {
		return userToWarehouseRoleMapper.insert(userToRole);
	}
	
	//根据id查询所有角色
	@Override
	public List<UserToWarehouseRole> queryRole(String userId) {

		UserToWarehouseRole userToRole=new UserToWarehouseRole();
		userToRole.setUserId(userId);
		return userToWarehouseRoleMapper.queryRole(userToRole);
	}

	//查询所有除id对应角色以外的角色信息
	public List<UserToWarehouseRole> queryUserNotRole(String userId) {
		
		UserToWarehouseRole userToRole=new UserToWarehouseRole();
		userToRole.setUserId(userId);
		return userToWarehouseRoleMapper.queryUserNotRole(userToRole);
	}

	@Override
	public int delete(UserToWarehouseRole userToRole) {
		return userToWarehouseRoleMapper.delete(userToRole);
	}

	@Override
	public List<UserToWarehouseRole> getParentRegionAll(UserToWarehouseRole userToRole) {
		
		return userToWarehouseRoleMapper.getParentRegionAll(userToRole);
	}

}
