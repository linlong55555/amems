package com.eray.thjw.material2.service;

import java.util.List;

import com.eray.thjw.material2.po.OutinReceiptShelves;

/**
 * @Description 航材收货单-上架serivce
 * @CreateTime 2018年2月26日 上午11:45:34
 * @CreateBy 韩武
 */
public interface OutinReceiptShelvesService {
	
	/**
	 * @Description 保存航材收货单-上架信息
	 * @CreateTime 2018年3月12日 上午9:49:32
	 * @CreateBy 韩武
	 * @param mainid
	 * @param details
	 */
	void save(String mainid, List<OutinReceiptShelves> shelves);
	
	/**
	 * @Description 根据收货单id删除收货单-上架信息
	 * @CreateTime 2018年3月12日 上午9:50:15
	 * @CreateBy 韩武
	 * @param shdid
	 */
	void deleteNotExistByMainid(String mainid, List<OutinReceiptShelves> shelves);
	
}
