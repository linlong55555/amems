package com.eray.thjw.baseinfo.servcice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.baseinfo.dao.ProjectMainAreaMapper;
import com.eray.thjw.baseinfo.po.ProjectMainArea;
import com.eray.thjw.baseinfo.servcice.ProjectMainAreaService;
import com.eray.thjw.service.CommonRecService;

/** 
 * @Description 项目关键部位实现方法
 * @CreateTime 2017-9-25 下午4:56:16
 * @CreateBy 甘清	
 */
@Service
public class ProjectMainAreaServiceImpl implements ProjectMainAreaService {

	@Resource
	private CommonRecService commonRecService;
	
	@Resource
    private ProjectMainAreaMapper projectMainAreaMapper;
	
	/**
	 * @Description 根据特定的条件获得项目部件信息
	 * @CreateTime 2017-9-25 下午5:01:02
	 * @CreateBy 甘清
	 * @param projectMainArea 查询参数
	 * @return
	 */
	private List<ProjectMainArea> getProjectMainArea(
			ProjectMainArea projectMainArea) {
		return projectMainAreaMapper.getProjectMainArea(projectMainArea);
	}


	@Override
	public ProjectMainArea getProjectMainAreaById(
			ProjectMainArea projectMainArea) {
		List<ProjectMainArea> list = this.getProjectMainArea(projectMainArea);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<ProjectMainArea> getProjectMainAreaByMainid(
			ProjectMainArea projectMainArea) {
		return this.getProjectMainArea(projectMainArea);
	}


	@Override
	public void addProjectMainArea(ProjectMainArea projectMainArea) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateProjectMainArea(ProjectMainArea projectMainArea) {
		// TODO Auto-generated method stub
		
	}
	
	

}
