package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.UserToRole;

public interface UserToRoleMapper {
    int deleteByPrimaryKey(String urId);

    int insert(UserToRole record);

    int insertSelective(UserToRole record);

    UserToRole selectByPrimaryKey(String urId);

    int updateByPrimaryKeySelective(UserToRole record);

    int updateByPrimaryKey(UserToRole record);
    
    List<UserToRole> queryRole(UserToRole record);
    
    List<UserToRole> queryUserNotRole(UserToRole record);
    
    int delete(UserToRole record);
    
  //查询根据角色id查询用户
  	public List<UserToRole> getParentRegionAll(UserToRole userToRole);

	int queryCount(UserToRole userToRole);
}