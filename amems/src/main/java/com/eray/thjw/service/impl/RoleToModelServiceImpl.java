package com.eray.thjw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RoleToModelMapper;
import com.eray.thjw.po.RoleToModel;
import com.eray.thjw.service.RoleToModelService;
@Service
public class RoleToModelServiceImpl implements RoleToModelService {
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private RoleToModelMapper roleToModelMapper;

	@Override
	public void save(RoleToModel roleToModel) throws Exception {
		roleToModelMapper.save(roleToModel);
	}

	@Override
	public void modify(RoleToModel roleToModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RoleToModel> queryAll(RoleToModel roleTo) throws Exception{
		return roleToModelMapper.queryAll(roleTo);
	}

	@Override
	public List<RoleToModel> selectFjzch(RoleToModel roleToModel) {
		return roleToModelMapper.selectFjzch(roleToModel);
	}

	@Override
	public void delete(RoleToModel roleToModel) {
		roleToModelMapper.delete(roleToModel);
		
	}

	@Override
	public int queryCount(RoleToModel roleToModel) throws Exception {
		return roleToModelMapper.queryCount(roleToModel);
	}
	
	
	/**
	 * 根据角色Id查询已分配的机型飞机号权限
	 * @author xu.yong
	 * @param roleId
	 * @return
	 */
	public List<RoleToModel> queryByRoleId(String roleId){
		return this.roleToModelMapper.selectByRoleId(roleId);
	}
	
}
