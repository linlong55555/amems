package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.InstallationListTemp;

/**
 * @Description 装机清单-临时区service
 * @CreateTime 2017年10月26日 下午2:44:22
 * @CreateBy 韩武
 */
public interface InstallationListTempService {
	
	/**
	 * @Description 保存装机清单-临时区
	 * @CreateTime 2017年10月26日 下午2:45:06
	 * @CreateBy 韩武
	 * @param temp
	 * @param id 装机清单id，可传null
	 * @return
	 * @throws BusinessException 
	 */
	public String save(InstallationListTemp record, String id) throws BusinessException;
	/**
	 * 
	 * @Description 删除装机清单-临时区
	 * @CreateTime 2017年11月25日 上午10:40:29
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	void delete(String id) throws BusinessException ;
}
