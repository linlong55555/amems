package com.eray.thjw.baseinfo.servcice;

import java.util.List;

import com.eray.thjw.baseinfo.po.ProjectMainArea;

/** 
 * @Description 
 * @CreateTime 2017-9-25 下午4:55:27
 * @CreateBy 甘清	
 */
public interface ProjectMainAreaService {
	
	/**
	 * @Description 根据ID获得对应的关键部件
	 * @CreateTime 2017-9-25 下午4:58:45
	 * @CreateBy 甘清
	 * @param projectMainArea 参数封装
	 * @return ProjectMainArea
	 */
	ProjectMainArea getProjectMainAreaById(ProjectMainArea projectMainArea);
	
	/**
	 * @Description 根据MainID的条件获得【项目信息-关键部件】
	 * @CreateTime 2017-9-25 下午4:54:31
	 * @CreateBy 甘清
	 * @param projectMainArea 参数封装
	 * @return List<ProjectMainArea> 集合
	 */
	List<ProjectMainArea> getProjectMainAreaByMainid(ProjectMainArea projectMainArea);
	
	/**
	 * @Description 添加项目关键部位信息
	 * @CreateTime 2017-9-25 下午5:03:35
	 * @CreateBy 甘清
	 * @param projectMainArea 关键部位参数
	 */
	void addProjectMainArea(ProjectMainArea projectMainArea);
	
	/**
	 * @Description 更新关键部位信息
	 * @CreateTime 2017-9-25 下午5:04:26
	 * @CreateBy 甘清
	 * @param projectMainArea
	 */
	void updateProjectMainArea(ProjectMainArea projectMainArea);
	
}
