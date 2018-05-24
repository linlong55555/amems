package com.eray.thjw.material2.service;

import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.StockInShelf;


/**
 * @Description 航材入库上架service
 * @CreateTime 2018年3月21日 上午9:48:15
 * @CreateBy 韩武
 */
public interface MaterialStockInService {
	
	/**
	 * @Description 查询航材入库上架列表
	 * @CreateTime 2018年3月21日 上午9:50:25
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryMaterialStockInList(Stock record);
	
	/**
	 * @Description 查询上架详情
	 * @CreateTime 2018年3月22日 下午5:03:56
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Stock queryShelfDetail(String id);
	
	/**
	 * @Description 入库上架
	 * @CreateTime 2018年3月23日 上午11:44:23
	 * @CreateBy 韩武
	 * @param record
	 */
	void doPutOnShelf(StockInShelf record) throws BusinessException;
}
