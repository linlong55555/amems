package com.eray.thjw.aerialmaterial.dao;

import java.util.Map;

/**
 * 航材历史记录操作
 * @author xu.yong
 *
 */
public interface MaterialRecMapper {

	/**
	 * 保存库存 历史记录
	 * @param map
	 * @return
	 */
    int insertStockRec(Map<String, Object> map);
    
    /**
     * 保存库存 历史记录
     * @param map
     * @return
     */
    int insertStockRecBatch(Map<String, Object> map);

    /**
     * 保存外场库存 历史记录
     * @param map
     * @return
     */
    int insertOutfieldRec(Map<String, Object> map);
}