package com.eray.thjw.log.dao;

import java.util.List;

import com.eray.thjw.log.po.LoginLimit;

public interface LoginLimitMapper {

	List<LoginLimit> queryAllPageList(LoginLimit loginLimit)throws RuntimeException;

	void deleteByPrimaryKey(String id)throws RuntimeException;

	void insertSelective(LoginLimit loginLimit)throws RuntimeException;

	void updateByPrimaryKeySelective(LoginLimit loginLimit)throws RuntimeException;
   
}