package com.eray.thjw.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.AuthOrganizationMapper;
import com.eray.thjw.dao.AuthUserMapper;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.po.AuthOrganization;
import com.eray.thjw.po.AuthUser;
import com.eray.thjw.service.AuthService;

/**
 * 鉴权用户服务实现
 * @author zhuchao
 *
 */
@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private AuthUserMapper authUserMapper;
	
	@Autowired
	private AuthOrganizationMapper authOrganizationMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private DepartmentMapper departmentMapper;
	

	@Override
	public AuthUser exists(String username, String password)throws RuntimeException {
		
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new RuntimeException("密码不能为空");
		}
		
		AuthUser user = new AuthUser();
		user.setUsername(username);
		user.setPassword(password);
	 
		List<AuthUser> list = authUserMapper.queryList(user);
		if (list!=null && !list.isEmpty()) {
			user = list.get(0);
		}else{
			user = null;
		}
		return user;
	}

	@Override
	public void synAuthInfo() throws RuntimeException {
		
		List<AuthUser> authusers = authUserMapper.queryList(null);
		Map<String, Object> authUserMap = new HashMap<String, Object>();
		authUserMap.put("authusers", authusers);
		userMapper.synAuthInfo(authUserMap);
		
		List<AuthOrganization> authOrganizations = authOrganizationMapper.queryList(null);
		Map<String, Object> authOrgMap = new HashMap<String, Object>();
		authOrgMap.put("authOrganizations", authOrganizations);
		departmentMapper.synAuthInfo(authOrgMap);
		
	}
	
	@Override
	public void synAuthInfoByUser(AuthUser authuser) throws RuntimeException {
		if (authuser !=null ) {
			Map<String, Object> authUserMap = new HashMap<String, Object>();
			List<AuthUser>authusers = new ArrayList<AuthUser>();
			authusers.add(authuser);
			authUserMap.put("authusers", authusers);
 			userMapper.synAuthInfo(authUserMap);
			
			if (authuser.getOrgid() != null ) {
				AuthOrganization authOrganization = authOrganizationMapper.selectByPrimaryKey(authuser.getOrgid());
				if (authOrganization != null) {
					List<AuthOrganization> authOrganizations = new ArrayList<AuthOrganization>();
					authOrganizations.add(authOrganization);
					Map<String, Object> authOrgMap = new HashMap<String, Object>();
					authOrgMap.put("authOrganizations", authOrganizations);
					departmentMapper.synAuthInfo(authOrgMap);
				}
			}
		}
	}
 
}
