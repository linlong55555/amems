package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProjectRelationshipMapper;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProjectRelationship;
import com.eray.thjw.project2.service.ProjectRelationshipService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;


/**
 * @Description 关联维修项目service实现类
 * @CreateTime 2017年8月16日 上午10:45:04
 * @CreateBy 韩武
 */
@Service
public class ProjectRelationshipServiceImpl implements ProjectRelationshipService{
	
	@Resource
	private ProjectRelationshipMapper projectRelationshipMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存关联维修项目
	 * @CreateTime 2017年8月16日 上午10:38:45
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void save(MaintenanceProject project) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 删除维修项目关联
		delete(project);
		
		// 保存维修项目关联
		if(project.getProjectRelationshipList() != null &&
				!project.getProjectRelationshipList().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ProjectRelationship projectRelationship : project.getProjectRelationshipList()) {
				projectRelationship.setId(UUID.randomUUID().toString());
				projectRelationship.setMainid(project.getId());
				projectRelationship.setZt(EffectiveEnum.YES.getId());
				projectRelationship.setWhrid(user.getId());
				projectRelationship.setWhsj(new Date());
				projectRelationship.setWhdwid(user.getBmdm());
				idList.add(projectRelationship.getId());
				projectRelationshipMapper.insertSelective(projectRelationship);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01202, user.getId(), 
						project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.SAVE, project.getScheme().getId());
			}
			
		}
	}

	/**
	 * @Description 删除关联维修项目
	 * @CreateTime 2017年9月5日 下午4:17:54
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
		commonRecService.write("mainid", delList, TableEnum.B_G2_01202, user.getId(), 
				project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.DELETE, project.getScheme().getId());
		
		// 删除维修项目关联
		projectRelationshipMapper.deleteByMainid(project.getId());
	}

}
