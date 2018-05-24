package com.eray.thjw.baseinfo.dao;

import java.util.List;

import com.eray.thjw.baseinfo.po.ProjectMainArea;

/** 
 * @Description 飞机关键部位
 * @CreateTime 2017-9-25 下午4:34:42
 * @CreateBy 甘清	
 */
public interface ProjectMainAreaMapper {
	
	/**
	 * @Description 根据特定的条件获得【项目信息-关键部件】
	 * @CreateTime 2017-9-25 下午4:54:31
	 * @CreateBy 甘清
	 * @param projectMainArea
	 * @return List<ProjectMainArea>
	 */
	List<ProjectMainArea> getProjectMainArea(ProjectMainArea projectMainArea);
	
	/**
	 * @Description 保存项目关键部件信息
	 * @CreateTime 2017-9-25 下午5:36:45
	 * @CreateBy 甘清
	 * @param projectMainArea 部件信息
	 */
	void addProjectMainArea(ProjectMainArea projectMainArea);
	
	/**
	 * @Description 更新项目部件信息
	 * @CreateTime 2017-9-25 下午5:37:43
	 * @CreateBy 甘清
	 * @param projectMainArea 部件信息
	 */
	void updateProjectMainArea(ProjectMainArea projectMainArea);
	
	/**
	 * @Description 根据项目编号删除项目部件信息
	 * @CreateTime 2017-11-15 上午11:32:05
	 * @CreateBy 甘清
	 * @param projectMainArea
	 */
	void deleteProjectMainAreaByProjectId(ProjectMainArea projectMainArea);

}
