package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProjectMonitorMapper;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProjectMaterial;
import com.eray.thjw.project2.po.ProjectMonitor;
import com.eray.thjw.project2.service.ProjectMonitorService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.MonitorProjectEnum;


/**
 * @Description 维修项目-监控项设置service
 * @CreateTime 2017年8月17日 上午11:44:27
 * @CreateBy 韩武
 */
@Service
public class ProjectMonitorServiceImpl implements ProjectMonitorService{
	
	@Resource
	private ProjectMonitorMapper projectMonitorMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存维修项目-监控项设置
	 * @CreateTime 2017年8月17日 上午11:43:22
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	@Override
	public void save(MaintenanceProject project) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 删除维修项目-监控项设置
		delete(project);
		
		// 保存维修项目-监控项设置
		if(project.getProjectMonitors() != null &&
				!project.getProjectMonitors().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ProjectMonitor monitor : project.getProjectMonitors()) {
				
				monitor.setId(UUID.randomUUID().toString());
				monitor.setMainid(project.getId());
				monitor.setZt(EffectiveEnum.YES.getId());
				monitor.setWhrid(user.getId());
				monitor.setWhsj(new Date());
				monitor.setWhbmid(user.getBmdm());
				// 小时转换为分钟
				switchHourToMinute(monitor);
				idList.add(monitor.getId());
				projectMonitorMapper.insertSelective(monitor);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01201, user.getId(), 
						project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.SAVE, project.getScheme().getId());
			}
			
		}
	}

	/**
	 * @Description 保存维修项目-监控项设置（对应部件）
	 * @CreateTime 2017年8月18日 下午1:51:24
	 * @CreateBy 韩武
	 * @param material
	 * @throws BusinessException
	 */
	@Override
	public void save(ProjectMaterial material) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		/*
		 * 保存维修项目-监控项设置（对应部件）
		 */
		if(material.getProjectMonitors() != null &&
				!material.getProjectMonitors().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ProjectMonitor monitor : material.getProjectMonitors()) {
				monitor.setId(UUID.randomUUID().toString());
				monitor.setMainid(material.getMainid());
				monitor.setZt(EffectiveEnum.YES.getId());
				monitor.setJkdxid(material.getId());
				monitor.setWhrid(user.getId());
				monitor.setWhsj(new Date());
				idList.add(monitor.getId());
				monitor.setWhbmid(user.getBmdm());
				// 小时转换为分钟
				switchHourToMinute(monitor);
				projectMonitorMapper.insertSelective(monitor);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01201, user.getId(), 
						material.getCzls(), material.getLogOperationEnum(), UpdateTypeEnum.SAVE, material.getZbid());
			}
			
		}
	}
	
	/**
	 * @Description 小时转换为分钟
	 * @CreateTime 2017年8月30日 下午7:03:02
	 * @CreateBy 韩武
	 * @param monitor
	 */
	private void switchHourToMinute(ProjectMonitor monitor){
		
		// 是时间类型的监控项目
		if(MonitorProjectEnum.isTime(monitor.getJklbh())){
			
			monitor.setScz(StringAndDate_Util.convertToMinuteStr(monitor.getScz()));
			
			monitor.setZqz(StringAndDate_Util.convertToMinuteStr(monitor.getZqz()));
			
			monitor.setYdzQ(StringAndDate_Util.convertToMinuteStr(monitor.getYdzQ()));
			
			monitor.setYdzH(StringAndDate_Util.convertToMinuteStr(monitor.getYdzH()));
		}
	}

	/**
	 * @Description 删除维修项目-监控项设置
	 * @CreateTime 2017年9月5日 下午4:26:03
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
		commonRecService.write("mainid", delList, TableEnum.B_G2_01201, user.getId(), 
				project.getCzls(), project.getLogOperationEnum(), UpdateTypeEnum.DELETE, project.getScheme().getId());
		
		// 删除维修项目-监控项设置
		projectMonitorMapper.deleteByMainid(project.getId());
	}

}
