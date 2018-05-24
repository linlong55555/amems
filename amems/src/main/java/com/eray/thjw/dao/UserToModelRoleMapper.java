package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.ModelRole;
import com.eray.thjw.po.UserToModelRole;

public interface UserToModelRoleMapper {
    int deleteByPrimaryKey(String urId);

    int insert(UserToModelRole record);

    int insertSelective(UserToModelRole record);

    UserToModelRole selectByPrimaryKey(String urId);

    int updateByPrimaryKeySelective(UserToModelRole record);

    int updateByPrimaryKey(UserToModelRole record);
    
    List<UserToModelRole> queryRole(UserToModelRole record);
    
    List<UserToModelRole> queryUserNotRole(UserToModelRole record);
    
    int delete(UserToModelRole record);
    
  //查询根据角色id查询用户
  	public List<UserToModelRole> getParentRegionAll(UserToModelRole userToRole);

	void delete(ModelRole modelRole);

	int queryCount(UserToModelRole userToModelRole);
}