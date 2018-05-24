package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.ModelRole;


/**
 * @author liub
 * @description 机型角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface ModelRoleService {
	
	/**
	 * 根据ID查询机型角色
	 * @author xu.yong
	 * @param roleId
	 * @return
	 */
	public ModelRole findById(String roleId);

	public List<ModelRole> queryAllPageList(ModelRole role) throws Exception;
	
	public int queryCount(ModelRole role) throws Exception;

	public Map<String, Object> save(ModelRole role);

	public ModelRole queryAll(ModelRole role1);

	public Map<String, Object> modify(ModelRole warehouseRole);

	public List<ModelRole> queryAllModelRole(ModelRole role);

	public List<ModelRole> queryRole(String userId);

	public Map<String, Object> checkUpdMt(String modelRoleCode, String dprtId)throws Exception ;

	public Map<String, Object> delete(String ids)throws Exception;
	
}
