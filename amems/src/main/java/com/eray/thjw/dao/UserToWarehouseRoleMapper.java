package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.UserToWarehouseRole;

public interface UserToWarehouseRoleMapper {
    int deleteByPrimaryKey(String urId);

    int insert(UserToWarehouseRole record);

    int insertSelective(UserToWarehouseRole record);

    UserToWarehouseRole selectByPrimaryKey(String urId);

    int updateByPrimaryKeySelective(UserToWarehouseRole record);

    int updateByPrimaryKey(UserToWarehouseRole record);
    
    List<UserToWarehouseRole> queryRole(UserToWarehouseRole record);
    
    List<UserToWarehouseRole> queryUserNotRole(UserToWarehouseRole record);
    
    int delete(UserToWarehouseRole record);
    
  //查询根据角色id查询用户
  	public List<UserToWarehouseRole> getParentRegionAll(UserToWarehouseRole userToRole);

	int queryCount(UserToWarehouseRole userToWarehouseRoleOld);
}