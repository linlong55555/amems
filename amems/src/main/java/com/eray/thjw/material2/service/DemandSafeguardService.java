package com.eray.thjw.material2.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandSafeguard;

/** 
 * @Description 
 * @CreateTime 2018-2-26 下午3:17:17
 * @CreateBy 孙霁	
 */
public interface DemandSafeguardService {

	/**
	 * 
	 * @Description 根据id查询需求详细信息
	 * @CreateTime 2018-2-28 上午11:21:59
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public DemandSafeguard selectDetail(String id)throws BusinessException;
}
