package com.eray.thjw.dao;


import java.util.List;

import com.eray.thjw.po.ModelRole;


/**
 * @author ll
 * @description机型角色 Mapper
 * @develop date 2016.07.29
 */
public interface ModelRoleMapper {
	
	 List<ModelRole> queryAllPageList(ModelRole role) throws Exception;
	 
	 int queryCount(ModelRole role)throws Exception;

	void save(ModelRole warehouseRole);

	ModelRole queryAll(ModelRole role1);

	void modify(ModelRole warehouseRole);

	List<ModelRole> queryAllModelRole(ModelRole role);

	List<ModelRole> queryRole(String userId);

	void delete(ModelRole modelRole);
	
	/**
	 * 根据ID查询机型
	 * @author xu.yong
	 * @param id
	 * @return
	 */
	ModelRole findOneByRoleID(String id);
	
}
