package com.eray.thjw.project2.service;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.po.EngineeringOrder;

import enu.LogOperationEnum;

/** 
 * @Description  EO-监控项设置 业务接口
 * @CreateTime 2017-8-23 上午10:22:54
 * @CreateBy 雷伟	
 */
public interface EOMonitorIemSetService {

	/**
	 * @Description 新增EO-监控项设置
	 * @CreateTime 2017-8-23 上午10:16:03
	 * @CreateBy 雷伟
	 * @param jkxszList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	void saveMonitorIemSetList(List<EOMonitorIemSet> jkxszList, User user,String eoId, Date currentDate, String czls,LogOperationEnum operation);

	/**
	 * @Description 查询EO监控项设置 
	 * @CreateTime 2017-8-24 下午8:07:12
	 * @CreateBy 雷伟
	 * @param eoMonitorIemSet
	 * @return
	 */
	List<EOMonitorIemSet> queryAllList(EOMonitorIemSet eoMonitorIemSet);

	void deleteByMainid(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls, LogOperationEnum operation);
	/**
	 * 
	 * @Description 根据mianid获取监控项
	 * @CreateTime 2018年3月15日 下午3:48:53
	 * @CreateBy 岳彬彬
	 * @param mainid
	 * @return
	 */
	List<EOMonitorIemSet> queryByMainid(String mainid);
}
