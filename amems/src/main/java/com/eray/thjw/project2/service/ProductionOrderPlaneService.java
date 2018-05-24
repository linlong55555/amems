package com.eray.thjw.project2.service;

import com.eray.thjw.project2.po.ProductionOrder;

/** 
 * @Description 生产指令飞机适用性接口
 * @CreateTime 2018年5月2日 上午9:38:48
 * @CreateBy 徐勇	
 */
public interface ProductionOrderPlaneService {
	
	/**
	 * @Description 保存生产指令飞机适用性
	 * @CreateTime 2018年5月4日 上午9:48:53
	 * @CreateBy 韩武
	 * @param order
	 */
	void save(ProductionOrder order);
	
	/**
	 * @Description 删除生产指令飞机适用性
	 * @CreateTime 2018年5月4日 上午9:56:28
	 * @CreateBy 韩武
	 * @param order
	 */
	void delete(ProductionOrder order);

}
