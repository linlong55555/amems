package com.eray.thjw.project2.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProjectRelationshipEffectiveMapper;
import com.eray.thjw.project2.dao.ProjectRelationshipMapper;
import com.eray.thjw.project2.dao.TaskReplaceMapper;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProjectModel;
import com.eray.thjw.project2.po.ProjectRelationship;
import com.eray.thjw.project2.po.ProjectRelationshipEffective;
import com.eray.thjw.project2.po.TaskReplace;
import com.eray.thjw.project2.service.TaskReplaceService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class TaskReplaceServiceImpl implements TaskReplaceService{
@Autowired	
private TaskReplaceMapper  taskReplaceMapper;
@Autowired	
private ProjectRelationshipEffectiveMapper projectRelationshipEffectiveMapper;
@Autowired
private ProjectRelationshipMapper projectRelationshipMapper;
@Autowired
private CommonRecService commonRecService;

	@Override
	public List<TaskReplace> queryAll(TaskReplace taskReplace)
			throws BusinessException {
		// TODO Auto-generated method stub
		return taskReplaceMapper.queryAll(taskReplace);
	}

	@Override	
	public int save(TaskReplace taskReplace) throws BusinessException {
		
		User user=ThreadVarUtil.getUser();
		taskReplaceMapper.updateEobhByWxxmid(taskReplace.getEobh(),taskReplace.getWxxmid());
		taskReplaceMapper.deleteProject(taskReplace.getWxxmid());
		taskReplaceMapper.deleteValidZone(taskReplace.getWxfaid(),taskReplace.getWxxmid());
				
		Map<String,Object> map=taskReplace.getParamsMap();
		List<Map> relativeMaintenanceProjectList=(List<Map>) map.get("relativeMaintenanceProject");
		  for(int i=0;i<relativeMaintenanceProjectList.size();i++){
			  Map<String,String>  relativeMaintenance=relativeMaintenanceProjectList.get(i);
			   String xgwxxmid=relativeMaintenance.get("xgwxxmid");
			   String xgrwh=relativeMaintenance.get("xgrwh");
			   if(StringUtils.isBlank(xgrwh))
			    	 continue;
			   //新增b_g2_01103 维修方案生效区-相关维修项目关系表数据
			   ProjectRelationshipEffective projectRelationshipEffective=new ProjectRelationshipEffective();			   
			   projectRelationshipEffective.setId(UUID.randomUUID().toString()); 
			   projectRelationshipEffective.setMainid(taskReplace.getWxfaid());
			   projectRelationshipEffective.setJx(taskReplace.getJx());
			   projectRelationshipEffective.setWxxmid(taskReplace.getWxxmid());
			   projectRelationshipEffective.setRwh(taskReplace.getRwh());
			   projectRelationshipEffective.setXgwxxmid(xgwxxmid);
			   projectRelationshipEffective.setXgrwh(xgrwh);
			   projectRelationshipEffectiveMapper.insert(projectRelationshipEffective);
			   //新增b_g2_01202 维修项目-关联维修项目关系表
			   ProjectRelationship projectRelationship=new ProjectRelationship();  
			   projectRelationship.setId(UUID.randomUUID().toString());
			   projectRelationship.setMainid(taskReplace.getWxxmid());
			   projectRelationship.setJx(taskReplace.getJx());
			   projectRelationship.setWxxmbh(xgrwh);
			   projectRelationship.setZt(1);
			   projectRelationship.setWhdwid(user.getBmdm());
			   projectRelationship.setWhrid(user.getId());
			   projectRelationshipMapper.insert(projectRelationship);
		  }
	   //写日志
		commonRecService.write(taskReplace.getWxxmid(), TableEnum.B_G2_011,
				user.getUsername(), UUID.randomUUID().toString(), LogOperationEnum.RELATIONCHANGE,
				UpdateTypeEnum.UPDATE, taskReplace.getWxxmid());		 
		 
		return 1;
	}	

	@Override
	public List<TaskReplace> initProjectByid(TaskReplace taskreplace)
			throws BusinessException {
		// TODO Auto-generated method stub
		return taskReplaceMapper.initProjectByid(taskreplace);
	}

	@Override
	public Map<String, Object> initProjectWindow(TaskReplace taskreplace)
			throws BusinessException {
		PageHelper.startPage(taskreplace.getPagination());
		List<TaskReplace> list = taskReplaceMapper.initProjectWindow(taskreplace);
		String syx = "";
		String fjzch="";
		for (TaskReplace taskReplace : list) {
			syx = taskReplace.getSyx();
			fjzch=taskReplace.getFjzch();
			if (syx!=null&&!"00000".equals(syx)&&syx!="") {
				taskReplace.setSyx("-");
				String [] ars=fjzch.split(",");
				for (int i = 0; i < ars.length; i++) {
					ProjectModel project = new ProjectModel();
					project.setFjzch(ars[i]);
					taskReplace.getProjectModelList().add(project);
				}
			}

		}

		return PageUtil.pack4PageHelper(list, taskreplace.getPagination());
	}

}
