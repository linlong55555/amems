package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.AuthUser;

public interface AuthUserMapper extends AuthMapper{
	
	public int deleteByPrimaryKey(String id);

	public int insert(AuthUser record);

    public int insertSelective(AuthUser record);

    public AuthUser selectByPrimaryKey(String id);

    public int updateByPrimaryKeySelective(AuthUser record);

    public int updateByPrimaryKey(AuthUser record);

    public List<AuthUser> queryList(AuthUser user);

    public int authUserCount(AuthUser user);
}