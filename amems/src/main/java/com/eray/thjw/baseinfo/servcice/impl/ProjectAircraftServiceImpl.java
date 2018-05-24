package com.eray.thjw.baseinfo.servcice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.baseinfo.dao.ProjectAircraftMapper;
import com.eray.thjw.baseinfo.po.ProjectAircraft;
import com.eray.thjw.baseinfo.servcice.ProjectAircraftService;

/** 
 * @Description 项目关联飞机实现类
 * @CreateTime 2017-12-13 下午2:14:12
 * @CreateBy 甘清	
 */

@Service
public class ProjectAircraftServiceImpl implements ProjectAircraftService {
	
	@Resource
	private ProjectAircraftMapper projectAircraftMapper; //飞机基本信息

	@Override
	public ProjectAircraft findProjectAircraft(ProjectAircraft airdraft) {
		List<ProjectAircraft> list = projectAircraftMapper.findProjectAircraft(airdraft);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
