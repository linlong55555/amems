package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProjectMaterialMapper;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProjectMaterial;
import com.eray.thjw.project2.service.ProjectMaterialService;
import com.eray.thjw.project2.service.ProjectMonitorService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.project2.MaintenanceProjectTypeEnum;


/**
 * @Description 维修项目对应飞机关系serivice实现类
 * @CreateTime 2017年8月16日 下午2:04:56
 * @CreateBy 韩武
 */
@Service
public class ProjectMaterialServiceImpl implements ProjectMaterialService{
	
	@Resource
	private ProjectMaterialMapper projectMaterialMapper;
	
	@Resource
	private ProjectMonitorService projectMonitorService;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存维修项目-关联部件关系（对应飞机）
	 * @CreateTime 2017年8月16日 下午2:06:20
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	@Override
	public void save(MaintenanceProject project) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 删除维修项目-关联部件关系
		delete(project);
		
		// 保存维修项目-关联部件关系
		if (project.getWxxmlx() != MaintenanceProjectTypeEnum.FIXEDPACKAGE.getId()
				&& project.getWxxmlx() != MaintenanceProjectTypeEnum.NO.getId()
				&& project.getProjectMaterialList() != null 
				&& !project.getProjectMaterialList().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ProjectMaterial projectMaterial : project.getProjectMaterialList()) {
				
				projectMaterial.setId(UUID.randomUUID().toString());
				projectMaterial.setMainid(project.getId());
				projectMaterial.setWhrid(user.getId());
				projectMaterial.setWhsj(new Date());
				projectMaterial.setLogOperationEnum(project.getLogOperationEnum());
				projectMaterial.setCzls(project.getCzls());
				projectMaterial.setWhdwid(user.getBmdm());
				projectMaterial.setZbid(project.getScheme().getId());
				idList.add(projectMaterial.getId());
				projectMaterialMapper.insertSelective(projectMaterial);
				
				// 保存维修项目-监控项设置（对应部件）
				projectMonitorService.save(projectMaterial);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01204, user.getId(), 
						project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.SAVE, project.getId());
			}
		}
	}

	/**
	 * @Description 删除维修项目-关联部件关系
	 * @CreateTime 2017年9月5日 下午4:28:04
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	@Override
	public void delete(MaintenanceProject project) throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
		List<String> delList = new ArrayList<String>();
		delList.add(project.getId());
		
		// 保存历史记录信息
		commonRecService.write("mainid", delList, TableEnum.B_G2_01204, user.getId(), 
				project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.DELETE, project.getId());
		
		// 删除维修项目-关联部件关系
		projectMaterialMapper.deleteByMainid(project.getId());
		
	}

}
