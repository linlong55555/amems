package com.eray.rest.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eray.rest.annotation.RestInterface.RestInfo;
import com.eray.rest.common.SysConfig;
import com.eray.rest.service.PlaneParkingService;
import com.eray.rest.util.HttpClientUtil;
import com.eray.rest.vo.PlaneParking;

/** 
 * @Description 飞机停场接口实现类
 * @CreateTime 2018年2月2日 下午1:05:22
 * @CreateBy 韩武	
 */
@Service
public class PlaneParkingServiceImpl implements PlaneParkingService{
	
	/**
	 * @Description 飞机停场同步
	 * @CreateTime 2018年2月5日 上午11:28:55
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public String doSync(PlaneParking record) {
		// 是否启用接口
		if(SysConfig.isHhykEnabled()){
			// http post请求
			return HttpClientUtil.getInstance()
					.sendHttpPost(RestInfo.PLANE_PARKING.getUrl(), JSON.toJSONString(record));
		}
		return null;
	}

}
