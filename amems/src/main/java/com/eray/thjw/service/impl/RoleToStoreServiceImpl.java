package com.eray.thjw.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eray.thjw.dao.RoleToStoreMapper;
import com.eray.thjw.po.RoleToStore;
import com.eray.thjw.service.RoleToStoreService;

/**
 * @author liub
 * @description 角色仓库service,用于业务逻辑处理
 * @develop date 2016.09.09
 */
@Service
@Transactional(readOnly = true)
public class RoleToStoreServiceImpl implements RoleToStoreService{
	
	/**
	 * @author liub
	 * @description 角色仓库Mapper
	 * @develop date 2016.09.09
	 */
	@Autowired
	private RoleToStoreMapper roleToStoreMapper;
	
	/**
	 * @author liub
	 * @description 角色仓库授权
	 * @param roleId
	 * @develop date 2016.09.09
	 */
	@Override
	public void saveRoleToStore(String roleId,String oldStoreIds,String newStoreIds) throws RuntimeException{
		String[] oldStoreArr = oldStoreIds.split(",");
		String[] newStoreArr = newStoreIds.split(",");
		List<String> oldStoreList = Arrays.asList(oldStoreArr);
		List<String> newStoreList = Arrays.asList(newStoreArr);
		
		for(String newStoreId : newStoreList){
			if(!oldStoreList.contains(newStoreId)&&!newStoreId.equals("")){
				String id=UUID.randomUUID().toString();
				roleToStoreMapper.insertByRC(id,roleId, newStoreId);
			}
		}
		
	  for(String oldStoreId : oldStoreList){
			if(!newStoreList.contains(oldStoreId)){
				
				roleToStoreMapper.deleteByRC(roleId, oldStoreId);
			}
		}
	
	
	}

	@Override
	public int queryCount(RoleToStore roleToStore) throws Exception {
		
		try {
			return roleToStoreMapper.queryCount(roleToStore);
		} catch (Exception e) {
			
		throw new Exception("ServiceException queryCount RoleToMenuServiceImpl：",e);
		}
	}
}
