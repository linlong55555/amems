package com.eray.thjw.material2.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.MeasurementTools;

/**
 * 
 * @Description 计量工具service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface MeasurementToolsService {
	
	/**
	 * @Description 保存计量工具
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	public String save(MeasurementTools measurementTools)throws BusinessException;
	
	
}
