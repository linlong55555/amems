package com.eray.thjw.system.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.po.Login;

/**
 * 账号管理
 * @author xu.yong
 *
 */
public interface AccountService {

	/**
	 * 分页查询账号信息
	 * @param baseEntity
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryPageList(BaseEntity baseEntity) throws BusinessException;
	
	/**
	 * 分页查询未绑定的账号
	 * @param baseEntity
	 * @return
	 */
	public Map<String, Object> queryUnboundPageList(BaseEntity baseEntity);
	
	/**
	 * 保存账号
	 * @param login
	 * @throws BusinessException 
	 */
	public void saveAccount(Login login) throws BusinessException;
	
	/**
	 * 启用/禁用 账号
	 * 修改账号状态
	 * @param id
	 * @param state
	 */
	public void updateAccountState(String id, Integer state);
	
	/**
	 * 重置账号密码
	 * @param id
	 */
	public void updateAccountPassword4Reset(String id);
	
	/**
	 * 重置账号密码
	 * @param id
	 * @param password 加密过的密码
	 */
	public void updateAccountPassword(String id, String password);
	
	/**
	 * 删除账号
	 * @param id
	 * @throws BusinessException 
	 */
	public void deleteAccount(String id) throws BusinessException;

	public Login selectByPrimaryKey(String drzhid);

	public void updateByPrimaryKey(Login login) throws BusinessException;

	public Login selectByPrimaryName(String accountName);
}
