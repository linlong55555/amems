package com.eray.thjw.produce.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.WorkorderProcessHours;

import enu.LogOperationEnum;

/** 
 * @Description 工单工序工时
 * @CreateTime 2017-10-23 下午3:15:35
 * @CreateBy 雷伟	
 */
public interface WorkorderProcessHoursService {

	/**
	 * @Description 保存工单工序工时
	 * @CreateTime 2017-10-23 下午3:21:04
	 * @CreateBy 雷伟
	 * @param processList 工单工序列表
	 * @param user 当前用户
	 * @param gdid 工单ID
	 * @param currentDate 当前数据库时间
	 * @param czls 操作流水
	 * @param operation 操作类型
	 */
	void saveProcessHoursList(List<WorkorderProcessHours> processList,User user, String gdid, Date currentDate, String czls,LogOperationEnum operation);

	/**
	 * @Description 根据mainId删除记录
	 * @CreateTime 2017-10-23 下午5:03:31
	 * @CreateBy 雷伟
	 * @param mainId
	 */
	void deleteByMainid(String mainId);

	/**
	 * @Description 根据参数查数据
	 * @CreateTime 2017-10-23 下午8:00:02
	 * @CreateBy 雷伟
	 * @param processHours
	 * @return
	 */
	List<WorkorderProcessHours> queryListByParam(WorkorderProcessHours processHours);

}
