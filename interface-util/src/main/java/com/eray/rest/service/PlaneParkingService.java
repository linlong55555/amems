package com.eray.rest.service;

import com.eray.rest.annotation.RestInterface;
import com.eray.rest.annotation.RestInterface.RestInfo;
import com.eray.rest.vo.PlaneParking;

/**
 * @Description 飞机停场接口
 * @CreateTime 2018年2月5日 上午11:28:35
 * @CreateBy 韩武
 */
public interface PlaneParkingService {
	
	/**
	 * @Description 飞机停场同步
	 * @CreateTime 2018年2月5日 上午11:28:55
	 * @CreateBy 韩武
	 * @param record
	 */
	@RestInterface(restInfo=RestInfo.PLANE_PARKING)
	String doSync(PlaneParking record);
}
