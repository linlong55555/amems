package com.eray.thjw.system.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.po.UpdateLog;

/**
 * 
 * @Description 跟新日志service
 * @CreateTime 2018年4月24日 上午10:37:40
 * @CreateBy 林龙
 */
public interface UpdateLogService {

	/**
	 * 
	 * @Description 获取更新日志数据
	 * @CreateTime 2018年4月24日 上午10:37:58
	 * @CreateBy 林龙
	 * @param updateLog
	 * @return
	 * @throws BusinessException
	 */
	public List<UpdateLog> queryByAll(UpdateLog updateLog) throws BusinessException;

	
}
