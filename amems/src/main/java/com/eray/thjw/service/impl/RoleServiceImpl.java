package com.eray.thjw.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RoleMapper;
import com.eray.thjw.po.Role;
import com.eray.thjw.po.RoleToBtn;
import com.eray.thjw.po.RoleToDprt;
import com.eray.thjw.po.RoleToMenu;
import com.eray.thjw.po.RoleToStore;
import com.eray.thjw.po.UserToRole;
import com.eray.thjw.service.RoleService;
import com.eray.thjw.service.RoleToBtnService;
import com.eray.thjw.service.RoleToDprtService;
import com.eray.thjw.service.RoleToMenuService;
import com.eray.thjw.service.RoleToStoreService;
import com.eray.thjw.service.UserToRoleService;


/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
@Service
public class RoleServiceImpl implements  RoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Resource
	private RoleToMenuService roleToMenuService; 
	@Resource
	private UserToRoleService userToRoleService; 
	

	@Resource
	private RoleToBtnService roleToBtnService; 
	
	@Resource
	private RoleToDprtService roleToDprtService; 
	
	@Resource
	private RoleToStoreService roleToStoreService; 
	
	
	/*
	 * 根据参数查询角色
	 * @see com.eray.thjw.service.RoleService#queryAll(com.eray.thjw.po.Role)
	 */
	@Override
	public Role queryAll(Role role) throws Exception {
		try {
			return roleMapper.queryAll(role);
		} catch (Exception e) {
			throw new Exception("ServiceException queryAll RoleServiceImpl：",e);
		}
	}
	
	/*
	 * 根据参数查询角色集合
	 * @see com.eray.thjw.service.RoleService#queryAllPageList(com.eray.thjw.po.Role)
	 */
	@Override
	public List<Role> queryAllPageList(Role role) throws Exception {
		return roleMapper.queryAllPageList(role);
	}
	
	/*
	 * 添加角色信息
	 * @see com.eray.thjw.service.RoleService#save(com.eray.thjw.po.Role)
	 */
	@Override
	public  Map<String, Object> save(Role role) throws Exception {
		try {
			String rid=UUID.randomUUID().toString();
			role.setId(rid);
			roleMapper.save(role);
			RoleToDprt roleToDprt = new RoleToDprt();
			String rtdid=UUID.randomUUID().toString();
			roleToDprt.setId(rtdid);
			roleToDprt.setRoleId(String.valueOf(role.getId()));
			roleToDprt.setDprtId(role.getDprtId());
			roleToDprtService.save(roleToDprt);
			resultMap.put("state", "Success");
			 
		} catch (Exception e) {
		
		resultMap.put("state", "Failure");
		throw new Exception("ServiceException save RoleServiceImpl：",e);
		}
		finally{
		}
		return	resultMap;
	}
	/*
	 * 修改角色信息
	 * @see com.eray.thjw.service.RoleService#modify(com.eray.thjw.po.Role)
	 */
	@Override
	public Map<String, Object> modify(Role role) throws Exception {
		
		try {
			 roleMapper.modify(role);
			resultMap.put("state", "Success");
		} catch (Exception e) {
		resultMap.put("state", "Failure");
		logger.error("ServiceException modify RoleServiceImpl：",e);
		throw new Exception("ServiceException modify RoleServiceImpl：",e);
		}
		finally{
		}
		return	resultMap;
	}
	
	/*
	 * 删除角色
	 * @see com.eray.thjw.service.RoleService#delete(java.lang.Integer)
	 */
	 
	public Map<String, Object> delete(String id) throws Exception {
		logger.debug("进入 delete RoleServiceImpl");
		
		if (id == null||id.equals("")) {
			logger.error("id是null delete RoleServiceImpl");
			throw new Exception("id是null delete RoleServiceImpl");
		}
		String[] item = id.split(",");
		try {
		for (int i = 0; i < item.length; i++) {
			
			//technicalFile.setId(item[i]);
			
			UserToRole userToRole=new UserToRole();
			userToRole.setRoleId(String.valueOf(item[i]));
			int num=userToRoleService.queryCount(userToRole);
			if(num>0){
				 resultMap.put("state", "userToRole");
				 return	resultMap;
			}
			/*
			RoleToBtn roleToBtn=new RoleToBtn();
			roleToBtn.setRoleId(String.valueOf(item[i]));
			if(num1>0){
				 resultMap.put("state", "btn");
			int num1=roleToBtnService.queryCount(roleToBtn);
				 
				 return	resultMap;
			}*/
			
			
		/*	RoleToStore roleToStore=new RoleToStore();
			roleToStore.setRoleId(String.valueOf(item[i]));
			
			int num3=roleToStoreService.queryCount(roleToStore);
			if(num3>0){
				 resultMap.put("state", "cangku");
				 
				 return	resultMap;
			}*/
			
			Role role=new Role(); 
			role.setId(String.valueOf(item[i]));
			roleMapper.delete(role);
			
			 resultMap.put("state", "Success");
		}
		
			 
		} catch (Exception e) {
			resultMap.put("state", "Failure");
			logger.error("ServiceException delete RoleServiceImpl：",e);
			throw new Exception("ServiceException delete RoleServiceImpl：",e);
		}
		return	resultMap;
	}


	@Override
	public List<Role> queryAllRole(Role role) throws Exception {
		logger.debug("进入 queryAllRole RoleServiceImpl");
		try {
			return roleMapper.queryAllRole(role);
		} catch (Exception e) {
			
		logger.error("ServiceException queryAllRole RoleServiceImpl：",e);
		throw new Exception("ServiceException queryAllRole RoleServiceImpl：",e);
		}
	}

	@Override
	public Role findOneByRoleID(String id) throws Exception {
		logger.debug("进入 findOneByRoleID RoleServiceImpl");
		
		if (id == null||id.equals("")) {
			logger.error("id是null findOneByRoleID RoleServiceImpl");
			throw new Exception("id是null findOneByRoleID RoleServiceImpl");
		}
		
		try {
			return roleMapper.findOneByRoleID(id);
		} catch (Exception e) {
			
		logger.error("ServiceException findOneByRoleID RoleServiceImpl：",e);
		throw new Exception("ServiceException findOneByRoleID RoleServiceImpl：",e);
		}
		
	}

	@Override
	public List<Role> queryRole(String userId) {
		return roleMapper.queryRole(userId);
	}

	@Override
	public List<Role> queryUserNotRole(String userId) {
		return roleMapper.queryUserNotRole(userId);
	}

	@Override
	public Map<String, Object> checkUpdMt(String roleCode,String dprtId) throws Exception {
		Role role=new Role();
		
		role.setRoleCode(roleCode);
		role.setDprtId(dprtId);
		
		int cont= roleMapper.queryCount(role);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		if(cont<1){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "角色代码已存在!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> checkUpdRole(String roleCode) throws Exception {
		
	Role role=new Role();
		
		role.setRoleCode(roleCode);
		
		int cont= roleMapper.queryCount(role);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		if(cont<2){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "角色代码已存在!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}
	
	/**
	 * 保存角色菜单对应关系
	 * @param roleId 角色ID
	 * @param addMenus 添加的菜单
	 * @param delMenus 删除的菜单
	 * @param addBtns 添加的按钮
	 * @param delBtns 删除的按钮
	 * @author xu.yong
	 */
	public void saveRole2Menu(String roleId, List<String> addMenus, List<String> delMenus, List<String> addBtns, List<String> delBtns){
		//删除菜单关系
		if(delMenus != null && !delMenus.isEmpty()){
			this.roleToMenuService.deleteRoleMenus(roleId, delMenus);
		}
		//添加菜单关系
		if(addMenus != null && !addMenus.isEmpty()){
			this.roleToMenuService.insertRoleMenus(roleId, addMenus);
		}
		//删除按钮关系
		if(delBtns != null && !delBtns.isEmpty()){
			this.roleToBtnService.deleteRoleBtns(roleId, delBtns);
		}
		//添加按钮关系
		if(addBtns != null && !addBtns.isEmpty()){
			this.roleToBtnService.insertRoleBtns(roleId, addBtns);
		}
	}

}
