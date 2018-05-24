package com.eray.rest.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eray.rest.annotation.RestInterface.RestInfo;
import com.eray.rest.common.SysConfig;
import com.eray.rest.service.DeferredDefectService;
import com.eray.rest.util.HttpClientUtil;
import com.eray.rest.vo.DeferredDefect;

/** 
 * @Description 故障保留单接口实现类
 * @CreateTime 2018年2月2日 下午1:05:22
 * @CreateBy 韩武	
 */
@Service
public class DeferredDefectServiceImpl implements DeferredDefectService{
	
	/**
	 * @Description 故障保留单同步
	 * @CreateTime 2018年2月2日 下午1:08:06
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public String doSync(DeferredDefect record) {
		// 是否启用接口
		if(SysConfig.isHhykEnabled()){
			// http post请求
			return HttpClientUtil.getInstance()
					.sendHttpPost(RestInfo.DEFERRED_DEFECT.getUrl(), JSON.toJSONString(record));
		}
		return null;
	}

}
