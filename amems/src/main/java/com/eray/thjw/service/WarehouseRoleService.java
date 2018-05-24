package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.WarehouseRole;


/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface WarehouseRoleService {

	public List<WarehouseRole> queryAllPageList(WarehouseRole role) throws Exception;
	
	public int queryCount(WarehouseRole role) throws Exception;

	public Map<String, Object> save(WarehouseRole role);

	public WarehouseRole queryAll(WarehouseRole role1);

	public Map<String, Object> modify(WarehouseRole warehouseRole);


	public List<WarehouseRole> queryRole(String userId);

	public List<WarehouseRole> queryAllWarehouseRole(WarehouseRole role);

	public Map<String, Object> checkUpdMt(String warehouseRoleCode, String dprtId)throws Exception;

	public Map<String, Object> delete(String ids)throws Exception;
	
}
