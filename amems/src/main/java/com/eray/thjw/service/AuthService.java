package com.eray.thjw.service;

import com.eray.thjw.po.AuthUser;

/**
 * 鉴权用户服务接口
 * @author zhuchao
 *
 */
public interface AuthService {

	/**
	 * 验证指定顶层机构下，用户账号，密码是否存在
	 * @param userName
	 * @param password
	 * @return
	 */
	 public AuthUser exists(String userName,String password) throws RuntimeException;
	
	 /**
	  * 同步鉴权用户，机构
	  */
	 public void synAuthInfo() throws RuntimeException ;

	 /**
	  * 同步当前登录用户的用户以及归属部门
	  * @param user
	  * @throws RuntimeException
	  */
	 public void synAuthInfoByUser(AuthUser user) throws RuntimeException;
}
