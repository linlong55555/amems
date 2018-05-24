package com.eray.thjw.service;

import com.eray.thjw.po.RoleToStore;



public interface RoleToStoreService {

	/**
	 * @author liub
	 * @description 角色仓库授权
	 * @param roleId,oldStoreIds，newStoreIds
	 * @develop date 2016.09.09
	 */
	public void saveRoleToStore(String roleId,String oldStoreIds,String newStoreIds) throws RuntimeException;
	
	public int queryCount(RoleToStore roleToStore) throws Exception;
	
}
