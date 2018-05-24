package com.eray.thjw.baseinfo.servcice;

import java.util.List;
import java.util.Map;

import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.baseinfo.po.ProjectAircraft;
import com.eray.thjw.exception.BusinessException;

/** 
 * @Description 项目接口信息
 * @CreateTime 2017-9-15 下午5:53:11
 * @CreateBy 甘清	
 */
public interface ProjectService {
	
	/**
	 * @Description 获得项目列表信息
	 * @CreateTime 2017-9-20 上午10:21:14
	 * @CreateBy 甘清
	 * @param project
	 * @return Map<String, Object>
	 */
	Map<String, Object> getProjectList(Project project);
	
	/**
	 * @Description 添加项目信息
	 * @CreateTime 2017-9-20 上午10:37:20
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @throws BusinessException 
	 */
	Project addProject(Project project) throws BusinessException;
	
	/**
	 * @Description 根据ID获得项目信息
	 * @CreateTime 2017-9-20 上午10:39:43
	 * @CreateBy 甘清
	 * @param project 查询参数
	 * @return Project
	 */
	Project getProjectById(Project project);
	
	/**
	 * @Description 更新项目信息
	 * @CreateTime 2017-9-20 上午10:41:00
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @throws BusinessException
	 */
	void updateProject(Project project) throws BusinessException;
	/**
	 * 
	 * @Description 获取组织机构下状态为2和10的项目
	 * @CreateTime 2017年10月17日 下午2:13:06
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Project> getProjectByDprt(String dprtcode);
	/**
	 * 
	 * @Description 根据客户id和组织机构获取项目信息
	 * @CreateTime 2017年10月18日 上午10:59:16
	 * @CreateBy 岳彬彬
	 * @param project
	 * @return
	 */
	Map<String, Object> getProjectsList(Project project);
	
	/**
	 * @Description 删除项目信息
	 * @CreateTime 2017-11-15 上午10:10:05
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @throws BusinessException
	 */
	Map<String, Object> deleteProject(String id) throws BusinessException;
	
	/**
	 * @Description 获得飞机基本信息
	 * @CreateTime 2017-12-7 下午5:22:54
	 * @CreateBy 甘清
	 * @param projectAircraft 飞机基本信息查询条件
	 * @return 飞机基本信息
	 */
	Map<String, Object> getProjectAircraft(ProjectAircraft projectAircraft);
}
