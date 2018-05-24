package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialRecMapper;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;

/**
 * 航材历史记录服务
 */
@Service("materialRecService")
public class MaterialRecServiceImpl implements MaterialRecService {
	
	@Resource
	private MaterialRecMapper materialRecMapper;
	
	
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
	public int writeStockRec(String stockId, String czls, String bizId, String bizNo, StockRecBizTypeEnum stockRecBizTypeEnum, UpdateTypeEnum typeEnum, Object...args ){
		
		User user = ThreadVarUtil.getUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("id", stockId);
		paramMap.put("recXglx", typeEnum.getId());
		paramMap.put("recCzrid", user.getId());
		String clientIp = ThreadVarUtil.getClientIp();
		paramMap.put("recIp", "0:0:0:0:0:0:0:1".equals(clientIp)?"127.0.0.1":clientIp);
		paramMap.put("recXgyj", MessageFormat.format(stockRecBizTypeEnum.getText(), args));//修改依据说明
		paramMap.put("recYjlx", stockRecBizTypeEnum.getId());
		paramMap.put("recYjid", bizId);
		paramMap.put("recYjbh", bizNo);
		paramMap.put("recCzls", czls);
		
		return this.materialRecMapper.insertStockRec(paramMap);
	} 
	
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
	public int writeOutfieldRec(String outfieldId, String czls, String bizId, String bizNo, OutfieldRecBizTypeEnum outfieldRecBizTypeEnum, UpdateTypeEnum typeEnum, Object...args ){
		
		User user = ThreadVarUtil.getUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("id", outfieldId);
		paramMap.put("recXglx", typeEnum.getId());
		paramMap.put("recCzrid", user.getId());
		String clientIp = ThreadVarUtil.getClientIp();
		paramMap.put("recIp", "0:0:0:0:0:0:0:1".equals(clientIp)?"127.0.0.1":clientIp);
		paramMap.put("recXgyj", MessageFormat.format(outfieldRecBizTypeEnum.getText(), args));//修改依据说明
		paramMap.put("recYjlx", outfieldRecBizTypeEnum.getId());
		paramMap.put("recYjid", bizId);
		paramMap.put("recYjbh", bizNo);
		paramMap.put("recCzls", czls);
		
		return this.materialRecMapper.insertOutfieldRec(paramMap);
	} 
	
	public static void format(Object... args){
		String xgyj = "{0}{1}，出库单：{2}，相关单据：{3}，出库数量：{4,number, #.00}";
		System.out.println(MessageFormat.format(xgyj, args));
	}
	
	public static void main(String[] args) {
		format("借出出库", "", "CK001", "手工制单",new BigDecimal("3232.333"));
	}
}
