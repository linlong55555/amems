package com.eray.thjw.aerialmaterial.service;

import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;

/**
 * 航材库存历史记录服务
 * @author xu.yong
 */
public interface MaterialRecService {

	/**
	 * 记录库存表日志
	 * @param stockId 库存ID
	 * @param czls 操作流水，用户一次操作记录涉及的日志使用同一个操作流水
	 * @param bizId 操作的业务ID ，如入库时 入库ID
	 * @param bizNo 操作的业务编号 ， 如入库时 入库单号
	 * @param stockRecBizTypeEnum 业务类型
	 * @param typeEnum 库存表数据 数据库操作类型
	 * @param args 相关依据的参数
	 * @return
	 */
	public int writeStockRec(String stockId, String czls, String bizId, String bizNo, StockRecBizTypeEnum stockRecBizTypeEnum, UpdateTypeEnum typeEnum, Object...args );

	/**
	 * 记录外场库存表日志
	 * @param outfieldId 外场库存ID
	 * @param czls 操作流水，用户一次操作记录涉及的日志使用同一个操作流水
	 * @param bizId 操作的业务ID ，如入库时 入库ID
	 * @param bizNo 操作的业务编号 ， 如入库时 入库单号
	 * @param stockRecBizTypeEnum 业务类型
	 * @param typeEnum 库存表数据 数据库操作类型
	 * @param args 相关依据的参数
	 * @return
	 */
	public int writeOutfieldRec(String outfieldId, String czls, String bizId, String bizNo, OutfieldRecBizTypeEnum outfieldRecBizTypeEnum, UpdateTypeEnum typeEnum, Object...args );
}
