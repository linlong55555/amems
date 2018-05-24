package com.eray.thjw.project2.service;

import java.util.Date;
import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.EOPulicationAffected;
import com.eray.thjw.project2.po.EngineeringOrder;

import enu.LogOperationEnum;

/** 
 * @Description EO受影响出版物  业务接口
 * @CreateTime 2017-8-23 上午9:30:50
 * @CreateBy 雷伟	
 */
public interface EOPulicationAffectedService {

	/**
	 * @Description 新增受影响出版物
	 * @CreateTime 2017-8-23 上午9:36:02
	 * @CreateBy 雷伟
	 * @param syxcbwList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 流水号
	 * @param operation 操作类型
	 */
	void savePulicationAffectedList(List<EOPulicationAffected> syxcbwList,User user, String eoId,Date currentDate,String czls,LogOperationEnum operation);

	/**
	 * @Description 查询EO受影响出版物
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoPulicationAffected EO受影响出版物
	 * @return
	 * @throws BusinessException
	 */
	List<EOPulicationAffected> queryAllList(EOPulicationAffected eoPulicationAffected);

	void deleteByMainid(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls, LogOperationEnum operation);

}
