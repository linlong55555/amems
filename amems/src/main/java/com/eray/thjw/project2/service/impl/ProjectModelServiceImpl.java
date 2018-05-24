package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProjectModelMapper;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProjectModel;
import com.eray.thjw.project2.service.ProjectModelService;
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
public class ProjectModelServiceImpl implements ProjectModelService{
	
	@Resource
	private ProjectModelMapper projectModelMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存维修项目对应飞机关系
	 * @CreateTime 2017年8月16日 下午2:06:20
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	@Override
	public void save(MaintenanceProject project) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 删除维修项目对应飞机关系
		delete(project);		
		
		// 保存维修项目对应飞机关系
		if (project.getWxxmlx() != MaintenanceProjectTypeEnum.FIXEDPACKAGE.getId()
				&& "-".equals(project.getSyx())
				&& project.getProjectModelList() != null 
				&& !project.getProjectModelList().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ProjectModel projectModel : project.getProjectModelList()) {
				
				projectModel.setId(UUID.randomUUID().toString());
				projectModel.setMainid(project.getId());
				projectModel.setWhrid(user.getId());
				projectModel.setWhsj(new Date());
				projectModel.setWhdwid(user.getBmdm());
				idList.add(projectModel.getId());
				projectModelMapper.insertSelective(projectModel);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01203, user.getId(), 
						project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.SAVE, project.getId());
			}
		}
	}

	/**
	 * @Description 删除维修项目对应飞机关系
	 * @CreateTime 2017年9月5日 下午4:23:57
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
		commonRecService.write("mainid", delList, TableEnum.B_G2_01203, user.getId(), 
				project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.DELETE, project.getId());
		
		// 删除维修项目对应飞机关系
		projectModelMapper.deleteByMainid(project.getId());
	}

}
