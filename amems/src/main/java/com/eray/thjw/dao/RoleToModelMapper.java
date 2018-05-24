package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.RoleToModel;


/**
 * @author liub
 * @description 机型角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface RoleToModelMapper {

	public List<RoleToModel> queryAllPageList(RoleToModel roleToModel) throws Exception;
	
	public int queryCount(RoleToModel roleToModel) throws Exception;
	
	public void save(RoleToModel roleToModel) throws Exception;
	
	public void delete(RoleToModel roleToModel);
	
	public void modify(RoleToModel roleToModel) throws Exception;
	
	public RoleToModel selectNum(RoleToModel roleToModel) throws Exception;

	public List<RoleToModel> queryAll(RoleToModel roleTo) throws Exception;

	public List<RoleToModel> selectFjzch(RoleToModel roleToModel);
	
	/**
	 * 根据角色Id查询已分配的机型飞机号权限
	 * @author xu.yong
	 * @param roleId
	 * @return
	 */
	public List<RoleToModel> selectByRoleId(String roleId);
	
}
