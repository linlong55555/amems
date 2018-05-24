package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.AuthOrganization;

public interface AuthOrganizationMapper extends AuthMapper{
	
    int deleteByPrimaryKey(String id);

    int insert(AuthOrganization record);

    int insertSelective(AuthOrganization record);

    AuthOrganization selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthOrganization record);

    int updateByPrimaryKey(AuthOrganization record);

	List<AuthOrganization> queryList(AuthOrganization authOrganization);
}