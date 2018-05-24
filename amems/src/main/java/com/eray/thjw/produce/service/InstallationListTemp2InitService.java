package com.eray.thjw.produce.service;

import com.eray.thjw.produce.po.InstallationListTemp;




/**
 * @Description 装机清单-临时区-部件初始化service
 * @CreateTime 2017年9月28日 下午4:25:17
 * @CreateBy 韩武
 */
public interface InstallationListTemp2InitService {
	
	/**
	 * @Description 保存装机清单-临时区-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:30:39
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(InstallationListTemp record);
	
	/**
	 * @Description 删除装机清单-临时区-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:54:19
	 * @CreateBy 韩武
	 * @param record
	 */
	void delete(InstallationListTemp record);
}
