package com.eray.thjw.project2.service;

import com.eray.thjw.project2.po.ProductionOrder;

/**
 * @Description 生产指令-监控项设置接口
 * @CreateTime 2018年5月4日 上午9:46:22
 * @CreateBy 韩武
 */
public interface ProductionOrderMonitorService {
	
	/**
	 * @Description 保存生产指令-监控项设置
	 * @CreateTime 2018年5月4日 上午9:47:43
	 * @CreateBy 韩武
	 * @param order
	 */
	void save(ProductionOrder order);
	
	/**
	 * @Description 删除生产指令-监控项设置
	 * @CreateTime 2018年5月4日 上午9:56:28
	 * @CreateBy 韩武
	 * @param order
	 */
	void delete(ProductionOrder order);
}
