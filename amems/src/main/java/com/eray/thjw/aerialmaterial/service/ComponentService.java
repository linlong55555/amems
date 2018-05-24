package com.eray.thjw.aerialmaterial.service;

import com.eray.thjw.aerialmaterial.po.Component;

/**
 * @Description 部件service
 * @CreateTime 2017年10月9日 下午4:49:32
 * @CreateBy 韩武
 */
public interface ComponentService {
	
	/**
	 * @Description 根据件号和序列号查找
	 * @CreateTime 2017年10月9日 下午4:53:12
	 * @CreateBy 韩武
	 * @return
	 */
	Component findByJhAndXlh(Component component);
}
