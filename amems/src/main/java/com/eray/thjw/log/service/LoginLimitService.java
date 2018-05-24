package com.eray.thjw.log.service;

import java.util.List;

import com.eray.thjw.log.po.LoginLimit;

/**
 * 限制登录
 * @author ll
 *
 */
public interface LoginLimitService {

	public List<LoginLimit> queryAllPageList(LoginLimit loginLimit)throws RuntimeException;

	public void cancel(String id)throws RuntimeException;

	public void save(LoginLimit loginLimit)throws RuntimeException;

	public void update(LoginLimit loginLimit)throws RuntimeException;
	
}
