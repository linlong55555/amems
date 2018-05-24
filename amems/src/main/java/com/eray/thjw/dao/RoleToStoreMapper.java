package com.eray.thjw.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.RoleToStore;

public interface RoleToStoreMapper {
	
	/**
	 * @author liub
	 * @description 根据角色id和机构代码删除角色对应的仓库
	 * @param roleId,dprtId
	 * @return int
	 * @develop date 2016.09.09
	 */
	int deleteByRD(@Param(value = "roleId")String roleId ,@Param(value = "dprtId")String dprtId);
	
	/**
	 * @author liub
	 * @description 根据机构代码删除对应的仓库
	 * @param roleId,dprtId
	 * @return int
	 * @develop date 2016.09.09
	 */
	int deleteByD(String dprtId);
	
	/**
	 * @author liub
	 * @description 
	 * @param roleId,ckId
	 * @return int
	 * @develop date 2016.09.09
	 */
	int deleteByRC(@Param(value = "roleId")String roleId ,@Param(value = "ckId")String ckId);
	
	/**
	 * @author liub
	 * @description 插入授权数据
	 * @param roleId，ckId
	 * @return Listint
	 * @develop date 2016.09.09
	 */
	int insertByRC(@Param(value = "id")String id ,@Param(value = "roleId")String roleId ,@Param(value = "ckId")String ckId);
	
    int deleteByPrimaryKey(String id);

    int insert(RoleToStore record);

    int insertSelective(RoleToStore record);

    RoleToStore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleToStore record);

    int updateByPrimaryKey(RoleToStore record);
    
    public int queryCount(RoleToStore roleToStore) throws Exception;
}