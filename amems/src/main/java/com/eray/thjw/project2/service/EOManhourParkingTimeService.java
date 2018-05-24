package com.eray.thjw.project2.service;

import java.util.Date;
import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.EOManhourParkingTime;
import com.eray.thjw.project2.po.EngineeringOrder;

import enu.LogOperationEnum;

/** 
 * @Description EO-工时/停场时间业务接口
 * @CreateTime 2017-8-23 上午10:10:40
 * @CreateBy 雷伟	
 */
public interface EOManhourParkingTimeService {

	/**
	 * @Description 新增EO-工时/停场时间
	 * @CreateTime 2017-8-23 上午10:16:03
	 * @CreateBy 雷伟
	 * @param gstcshList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	void saveManhourParkingTimeList(List<EOManhourParkingTime> gstcshList,User user, String eoId, Date currentDate, String czls,LogOperationEnum operation);

	/**
	 * @Description 查询EO工时/停场时间
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 * @throws BusinessException
	 */
	List<EOManhourParkingTime> queryAllList(EOManhourParkingTime eoManhourParkingTime);

	void deleteByMainid(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls, LogOperationEnum operation);

}
