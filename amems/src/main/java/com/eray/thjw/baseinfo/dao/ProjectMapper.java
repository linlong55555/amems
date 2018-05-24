package com.eray.thjw.baseinfo.dao;

import java.util.List;

import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.baseinfo.po.Project;

/** 
 * @Description 项目mapper
 * @CreateTime 2017-9-15 下午5:52:51
 * @CreateBy 甘清	
 */
public interface ProjectMapper {
	
	/**
	 * @Description 获得项目信息
	 * @CreateTime 2017-9-20 上午10:47:12
	 * @CreateBy 甘清
	 * @param project 项目信息查询参数
	 * @return List<Project>
	 */
	List<Project> getProjectList(Project project);
	
	/**
	 * @Description 添加项目信息
	 * @CreateTime 2017-9-20 上午10:48:01
	 * @CreateBy 甘清
	 * @param project 前端参数信息
	 */
	void addProject(Project project);
	/**
	 * @Description 根据项目编号获得项目信息
	 * @CreateTime 2017-9-20 上午10:49:13
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @return Project
	 */
	Project getProjectById(Project project);
	
	/**
	 * @Description 更新项目信息
	 * @CreateTime 2017-9-20 下午2:27:16
	 * @CreateBy 甘清
	 * @param project 项目参数
	 */
	void updateProject(Project project);
	
	/**
	 * @Description 检查一个项目是否已经存在
	 * @CreateTime 2017-9-27 下午4:44:33
	 * @CreateBy 甘清
	 * @param project 查询参数
	 * @return
	 */
	List<Project> checkProject(Project project);
	/**
	 * 
	 * @Description 获取组织机构下状态为2和10的项目
	 * @CreateTime 2017年10月17日 下午2:11:34
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Project> selectProjectByDprt(String dprtcode);
	/**
	 * 
	 * @Description 根据组织机构和客户id获取状态为2和10的项目
	 * @CreateTime 2017年10月18日 上午11:02:05
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param khid
	 * @return
	 */
	List<Project> selectProjectByDprtAndKhid(Project project);
	
	/**
	 * @Description 删除项目信息
	 * @CreateTime 2017-11-15 上午10:18:28
	 * @CreateBy 甘清
	 * @param project 项目
	 */
	void deleteProject(Project project);
}
