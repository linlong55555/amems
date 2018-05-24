package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.RoleToBtn;

public interface RoleToBtnMapper {
	public List<RoleToBtn> queryAllPageList(RoleToBtn roleToBtn) throws Exception;
	
	public int queryCount(RoleToBtn roleToBtn) throws Exception;
	
	public void save(RoleToBtn roleToBtn) throws Exception;
	
	public void delete(RoleToBtn roleToBtn) throws Exception;
	
	public Map<String, Object> modify(RoleToBtn roleToBtn) throws Exception;
	
	/**
	 * 根据色色ID进行查询
	 * @param roleId
	 * @return
	 */
	List<RoleToBtn> selectByRole(String roleId);
	
	int deleteRoleBtns(@Param("roleId")String roleId, @Param("btnIds")List<String> btnIds);
	
	int insertRoleBtn(String id,String roleId, String btnId);
}
