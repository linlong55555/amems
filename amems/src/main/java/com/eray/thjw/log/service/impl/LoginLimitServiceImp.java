package com.eray.thjw.log.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.log.dao.LoginLimitMapper;
import com.eray.thjw.log.po.LoginLimit;
import com.eray.thjw.log.service.LoginLimitService;

/**
 * 限制登录
 * @author ll
 *
 */
@Service
public class LoginLimitServiceImp implements LoginLimitService {

	@Resource
	private LoginLimitMapper loginLimitMapper;

	@Override
	public List<LoginLimit> queryAllPageList(LoginLimit loginLimit) throws RuntimeException{
		return loginLimitMapper.queryAllPageList(loginLimit);
	}

	@Override
	public void cancel(String id) throws RuntimeException {
		loginLimitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void save(LoginLimit loginLimit) throws RuntimeException {
		
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		loginLimit.setId(uuid.toString());
		loginLimitMapper.insertSelective(loginLimit);
	}

	@Override
	public void update(LoginLimit loginLimit) throws RuntimeException {
		loginLimitMapper.updateByPrimaryKeySelective(loginLimit);
		
	}
	
	

}
