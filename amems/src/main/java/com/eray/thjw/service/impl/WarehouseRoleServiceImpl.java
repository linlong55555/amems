package com.eray.thjw.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.UserToWarehouseRoleMapper;
import com.eray.thjw.dao.WarehouseRoleMapper;
import com.eray.thjw.po.RoleToDprt;
import com.eray.thjw.po.RoleToStore;
import com.eray.thjw.po.UserToRole;
import com.eray.thjw.po.UserToWarehouseRole;
import com.eray.thjw.po.WarehouseRole;
import com.eray.thjw.service.RoleToDprtService;
import com.eray.thjw.service.RoleToStoreService;
import com.eray.thjw.service.UserToRoleService;
import com.eray.thjw.service.WarehouseRoleService;


/**
 * @author ll
 * @description 库房角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
@Service
public class WarehouseRoleServiceImpl implements   WarehouseRoleService{
	
	Map<String, Object> resultMap = new HashMap<String, Object>();

	@Autowired
	private WarehouseRoleMapper warehouseRoleMapper;
	@Autowired
	private UserToRoleService userToRoleService;
	
	@Autowired
	private UserToWarehouseRoleMapper userToWarehouseRoleMapper;
	@Autowired
	private RoleToStoreService roleToStoreService;
	@Autowired
	private RoleToDprtService roleToDprtService;
	@Override
	public List<WarehouseRole> queryAllPageList(WarehouseRole warehouseRole)
			throws Exception {
		return warehouseRoleMapper.queryAllPageList(warehouseRole);
	}

	@Override
	public int queryCount(WarehouseRole warehouseRole) throws Exception {
		return warehouseRoleMapper.queryCount(warehouseRole);
	}

	@Override
	public Map<String, Object> save(WarehouseRole warehouseRole) {
		
		try {
			String rid=UUID.randomUUID().toString();
			warehouseRole.setId(rid);
			warehouseRoleMapper.save(warehouseRole);
			
			RoleToDprt roleToDprt = new RoleToDprt();
			String rtdid=UUID.randomUUID().toString();
			roleToDprt.setId(rtdid);
			roleToDprt.setRoleId(String.valueOf(warehouseRole.getId()));
			roleToDprt.setDprtId(warehouseRole.getDprtId());
			roleToDprtService.save(roleToDprt);
			resultMap.put("state", "Success");
			 
		} catch (Exception e) {
		
			resultMap.put("state", "Failure");
		}
		finally{
		}
		return	resultMap;
	}

	@Override
	public WarehouseRole queryAll(WarehouseRole role1) {
		return warehouseRoleMapper.queryAll(role1);
	}

	@Override
	public Map<String, Object> modify(WarehouseRole warehouseRole) {
		try {
			warehouseRoleMapper.modify(warehouseRole);
			resultMap.put("state", "Success");
		} catch (Exception e) {
		resultMap.put("state", "Failure");
		}
		finally{
		}
		return	resultMap;
	}

	@Override
	public List<WarehouseRole> queryRole(String userId) {
		return warehouseRoleMapper.queryRole(userId);
	}

	@Override
	public List<WarehouseRole> queryAllWarehouseRole(WarehouseRole role) {
		return warehouseRoleMapper.queryAllWarehouseRole(role);
	}

	@Override
	public Map<String, Object> checkUpdMt(String warehouseRoleCode,String dprtId) throws Exception {
		WarehouseRole warehouseRole=new WarehouseRole();
		
		warehouseRole.setRoleCode(warehouseRoleCode);
		warehouseRole.setDprtId(dprtId);
		
		int cont= warehouseRoleMapper.queryCount(warehouseRole);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		if(cont<1){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "库房角色代码已存在!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> delete(String id) throws Exception {
		String[] item = id.split(",");
		try {
		for (int i = 0; i < item.length; i++) {

			RoleToStore roleToStore=new RoleToStore();
			roleToStore.setRoleId(String.valueOf(item[i]));
		/*	
			int num3=roleToStoreService.queryCount(roleToStore);
			if(num3>0){
				 resultMap.put("state", "cangku");
				 return	resultMap;
			}*/
			
			UserToWarehouseRole UserToWarehouseRoleOld=new UserToWarehouseRole();
			UserToWarehouseRoleOld.setRoleId(String.valueOf(item[i]));
			int num=userToWarehouseRoleMapper.queryCount(UserToWarehouseRoleOld);
			if(num>0){
				 resultMap.put("state", "userToRole");
				 return	resultMap;
			}
			
			/*UserToWarehouseRole userToWarehouseRole=new UserToWarehouseRole(); 
			userToWarehouseRole.setRoleId(String.valueOf(item[i]));
			userToWarehouseRoleMapper.delete(userToWarehouseRole);*/
			
			WarehouseRole warehouseRole=new WarehouseRole(); 
			warehouseRole.setId(String.valueOf(item[i]));
			warehouseRoleMapper.delete(warehouseRole);
			
			 resultMap.put("state", "Success");
		}
			 
		} catch (Exception e) {
			resultMap.put("state", "Failure");
			throw new Exception("ServiceException delete RoleServiceImpl：",e);
		}
		return	resultMap;
	}
	

	
	
	
}
