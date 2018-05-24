package com.eray.thjw.otheraerocade.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.dao.UpMapper;

/**
 * 其它飞行队库存
 * @author xu.yong
 *
 */
public interface OtherAerocadeStockMapper extends UpMapper {

	/**
	 * 按条件查询一页其他飞行队
	 * @author sunji
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageList(Stock stock)  throws RuntimeException;
}
