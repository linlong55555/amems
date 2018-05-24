package com.eray.thjw.baseinfo.dao;

import java.util.List;

import com.eray.thjw.baseinfo.po.ProjectAircraft;

/** 
 * @Description 飞机基本数据用于145单位数据接口
 * @CreateTime 2017-12-6 下午3:06:26
 * @CreateBy 甘清	
 */
public interface ProjectAircraftMapper {
	/**
	 * @Description 根据条件获得飞机基本信息
	 * @CreateTime 2017-12-6 下午4:05:45
	 * @CreateBy 甘清
	 * @param airdraft 查询条件
	 * @return List<ProjectAircraft>
	 */
	List<ProjectAircraft> findProjectAircraft(ProjectAircraft airdraft);

	/**
	 * @Description 新增飞机基本信息
	 * @CreateTime 2017-12-6 下午4:07:03
	 * @CreateBy 甘清
	 * @param airdraft 飞机基本信息
	 */
	void addProjectAircraft(ProjectAircraft airdraft);
	/**
	 * @Description 更新飞机基本信息
	 * @CreateTime 2017-12-6 下午4:07:30
	 * @CreateBy 甘清
	 * @param airdraft 飞机基本信息
	 */
	void updateProjectAircraft(ProjectAircraft airdraft);
}
