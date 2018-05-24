package com.eray.rest.service;

import com.eray.rest.annotation.RestInterface;
import com.eray.rest.annotation.RestInterface.RestInfo;
import com.eray.rest.vo.DeferredDefect;

/** 
 * @Description 故障保留单接口
 * @CreateTime 2018年2月2日 下午1:06:37
 * @CreateBy 韩武	
 */
public interface DeferredDefectService {
	
	/**
	 * @Description 故障保留单同步
	 * @CreateTime 2018年2月2日 下午1:08:06
	 * @CreateBy 韩武
	 * @param record
	 */
	@RestInterface(restInfo=RestInfo.DEFERRED_DEFECT)
	String doSync(DeferredDefect record);
}
