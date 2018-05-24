package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.RoleToModel;


/**
 * @author liub
 * @description 机型角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface RoleToModelService {
	
	public void save(RoleToModel roleToModel) throws Exception;
	
	public void modify(RoleToModel roleToModel) throws Exception;

	public List<RoleToModel> queryAll(RoleToModel roleTo)throws Exception;

	public List<RoleToModel> selectFjzch(RoleToModel roleToModel);

	public void delete(RoleToModel roleToModel);

	public int queryCount(RoleToModel roleToModel) throws Exception;
	
	/**
	 * 根据角色Id查询已分配的机型飞机号权限
	 * @author xu.yong
	 * @param roleId
	 * @return
	 */
	public List<RoleToModel> queryByRoleId(String roleId);
}
