package com.eray.thjw.dao;


import java.util.List;

import com.eray.thjw.po.WarehouseRole;


/**
 * @author ll
 * @description 库房角色 Mapper
 * @develop date 2016.07.29
 */
public interface WarehouseRoleMapper {
	
	 List<WarehouseRole> queryAllPageList(WarehouseRole role) throws Exception;
	 
	 int queryCount(WarehouseRole role)throws Exception;

	void save(WarehouseRole warehouseRole);

	WarehouseRole queryAll(WarehouseRole role1);

	void modify(WarehouseRole warehouseRole);

	List<WarehouseRole> queryRole(String userId);

	List<WarehouseRole> queryAllWarehouseRole(WarehouseRole role);

	void delete(WarehouseRole warehouseRole);
	
}
