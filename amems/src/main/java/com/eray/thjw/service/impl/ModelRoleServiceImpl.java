package com.eray.thjw.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.ModelRoleMapper;
import com.eray.thjw.dao.UserToModelRoleMapper;
import com.eray.thjw.po.ModelRole;
import com.eray.thjw.po.RoleToDprt;
import com.eray.thjw.po.RoleToModel;
import com.eray.thjw.po.UserToModelRole;
import com.eray.thjw.po.UserToRole;
import com.eray.thjw.service.ModelRoleService;
import com.eray.thjw.service.RoleToDprtService;
import com.eray.thjw.service.RoleToModelService;
import com.eray.thjw.service.UserToRoleService;


/**
 * @author ll
 * @description 机型角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
@Service
public class ModelRoleServiceImpl implements   ModelRoleService{
	
	Map<String, Object> resultMap = new HashMap<String, Object>();

	@Autowired
	private ModelRoleMapper modelRoleMapper;
	@Autowired
	private UserToRoleService userToRoleService;
	@Autowired
	private UserToModelRoleMapper userToModelRoleMapper;
	
	@Autowired
	private RoleToModelService roleToModelService;
	
	@Autowired
	private RoleToDprtService roleToDprtService;
	
	/**
	 * 根据ID查询机型角色
	 * @author xu.yong
	 * @param roleId
	 * @return
	 */
	public ModelRole findById(String roleId){
		return modelRoleMapper.findOneByRoleID(roleId);
	}
	
	@Override
	public List<ModelRole> queryAllPageList(ModelRole modelRole)
			throws Exception {
		return modelRoleMapper.queryAllPageList(modelRole);
	}

	@Override
	public int queryCount(ModelRole modelRole) throws Exception {
		return modelRoleMapper.queryCount(modelRole);
	}

	@Override
	public Map<String, Object> save(ModelRole modelRole) {
		
		try {
			String rid=UUID.randomUUID().toString();
			modelRole.setId(rid);
			modelRoleMapper.save(modelRole);
			
			RoleToDprt roleToDprt = new RoleToDprt();
			String rtdid=UUID.randomUUID().toString();
			roleToDprt.setId(rtdid);
			roleToDprt.setRoleId(String.valueOf(modelRole.getId()));
			roleToDprt.setDprtId(modelRole.getDprtId());
			roleToDprtService.save(roleToDprt);
			
			if(modelRole.getModelToRoleList().size()>0){
				for (RoleToModel modelToRole : modelRole.getModelToRoleList()) {
					
					if(modelToRole.getFjzch()!=null){
						modelToRole.setLx(2);
					}else{
						modelToRole.setFjzch("");
						modelToRole.setLx(1);
					}
					String rtdid1=UUID.randomUUID().toString();
					modelToRole.setId(rtdid1);
					modelToRole.setRoleId(rid);
					roleToModelService.save(modelToRole);
				}
			}
			resultMap.put("state", "Success");
			 
		} catch (Exception e) {
		
			resultMap.put("state", "Failure");
		}
		finally{
		}
		return	resultMap;
	}

	@Override
	public ModelRole queryAll(ModelRole role1) {
		return modelRoleMapper.queryAll(role1);
	}

	@Override
	public Map<String, Object> modify(ModelRole modelRole) {
		try {
			modelRoleMapper.modify(modelRole);
			
			RoleToModel roleToModel=new RoleToModel();
			roleToModel.setRoleId(modelRole.getId());
			roleToModelService.delete(roleToModel);
			
			if(modelRole.getModelToRoleList().size()>0){
				for (RoleToModel modelToRole : modelRole.getModelToRoleList()) {
					
					if(modelToRole.getFjzch()!=null){
						modelToRole.setLx(2);
					}else{
						modelToRole.setFjzch("");
						modelToRole.setLx(1);
					}
					String rtdid1=UUID.randomUUID().toString();
					modelToRole.setId(rtdid1);
					modelToRole.setRoleId(modelRole.getId());
					roleToModelService.save(modelToRole);
				}
			}
			
			
			resultMap.put("state", "Success");
		} catch (Exception e) {
		resultMap.put("state", "Failure");
		}
		finally{
		}
		return	resultMap;
	}

	@Override
	public List<ModelRole> queryAllModelRole(ModelRole role) {
		return modelRoleMapper.queryAllModelRole(role);
	}

	@Override
	public List<ModelRole> queryRole(String userId) {
		return modelRoleMapper.queryRole(userId);
	}

	@Override
	public Map<String, Object> checkUpdMt(String modelRoleCode,String dprtId) throws Exception {
		ModelRole modelRole=new ModelRole();
		
		modelRole.setRoleCode(modelRoleCode);
		modelRole.setDprtId(dprtId);
		
		int cont= modelRoleMapper.queryCount(modelRole);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		if(cont<1){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "机型角色代码已存在!");
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

		/*	RoleToModel roleToModel=new RoleToModel();
			roleToModel.setRoleId(String.valueOf(item[i]));
			int num2=roleToModelService.queryCount(roleToModel);
			if(num2>0){
				 resultMap.put("state", "dprt");
				 return	resultMap;
			}*/
			
			UserToModelRole userToModelRoleOld=new UserToModelRole();
			userToModelRoleOld.setRoleId(String.valueOf(item[i]));
			int num=userToModelRoleMapper.queryCount(userToModelRoleOld);
			if(num>0){
				 resultMap.put("state", "userToRole");
				 return	resultMap;
			}
			
		/*	UserToModelRole userToModelRole=new UserToModelRole(); 
			userToModelRole.setRoleId(String.valueOf(item[i]));
			userToModelRoleMapper.delete(userToModelRole);*/
			
			ModelRole modelRole=new ModelRole(); 
			modelRole.setId(String.valueOf(item[i]));
			modelRoleMapper.delete(modelRole);
			
			 resultMap.put("state", "Success");
		}
			 
		} catch (Exception e) {
			resultMap.put("state", "Failure");
			throw new Exception("ServiceException delete RoleServiceImpl：",e);
		}
		return	resultMap;
	}
	
	
}
