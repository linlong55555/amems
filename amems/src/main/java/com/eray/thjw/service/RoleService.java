package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Role;


/**
 * @author ll
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface RoleService {

	/**
	 * 角色分页
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List<Role> queryAllPageList(Role role) throws Exception;
	
	/**
	 * 根据条件查询
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public Role queryAll(Role role) throws Exception;
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Role findOneByRoleID(String id) throws Exception;
	
	/**
	 * 根据对象保存角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> save(Role role) throws Exception;
	
	/**
	 * 根据对象修改角色
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modify(Role role) throws Exception;
	
	/**
	 * 根据id删除角色
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> delete(String id) throws Exception;
	
	/**
	 * 根据对象查询角色集合
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List<Role> queryAllRole(Role role) throws Exception;
	
	/**
	 * 根据userid查询角色集合
	 * @param userId
	 * @return
	 */
	public List<Role> queryRole(String userId);

	/**
	 * 根据userid查询角色集合
	 * @param userId
	 * @return
	 */
	public List<Role> queryUserNotRole(String userId);
	
	/**
	 * 角色代码唯一的验证
	 * @param roleCode
	 * @param dprtId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkUpdMt(String roleCode, String dprtId) throws Exception;
	
	/**
	 * 角色代码修改唯一的验证
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkUpdRole(String roleCode) throws Exception;
	
	/**
	 * 保存角色菜单对应关系
	 * @param roleId 角色ID
	 * @param addMenus 添加的菜单
	 * @param delMenus 删除的菜单
	 * @param addBtns 添加的按钮
	 * @param delBtns 删除的按钮
	 * @author xu.yong
	 */
	public void saveRole2Menu(String roleId, List<String> addMenus, List<String> delMenus, List<String> addBtns, List<String> delBtns);
}
