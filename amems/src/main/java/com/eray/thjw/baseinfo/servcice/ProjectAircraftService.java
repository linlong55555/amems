package com.eray.thjw.baseinfo.servcice;

import com.eray.thjw.baseinfo.po.ProjectAircraft;

/** 
 * @Description 项目关联飞机接口
 * @CreateTime 2017-12-13 下午2:12:05
 * @CreateBy 甘清	
 */
public interface ProjectAircraftService {
	
	/**
	 * @Description 获得项目关联的飞机信息
	 * @CreateTime 2017-12-13 下午2:17:55
	 * @CreateBy 甘清
	 * @param airdraft 飞机信息
	 * @return
	 */
	ProjectAircraft findProjectAircraft(ProjectAircraft airdraft);

}
