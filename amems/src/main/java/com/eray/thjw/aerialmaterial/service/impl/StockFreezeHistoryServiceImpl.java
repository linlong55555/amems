package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OutFieldStockMapper;
import com.eray.thjw.aerialmaterial.dao.StockFreezeHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.StockFreezeHistoryService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.aerialmaterial.StockTypeEnum;

/**
 * 库存冻结履历
 * @author hanwu
 *
 */
@Service
public class StockFreezeHistoryServiceImpl implements StockFreezeHistoryService {
	
	@Resource
	private StockFreezeHistoryMapper stockFreezeHistoryMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private OutFieldStockMapper outFieldStockMapper;

	/**
	 * 刷新库存冻结数量
	 */
	@Override
	public void refreshStockFreezeCount(String kcid, StockTypeEnum stockTypeEnum) {
		// 获取该库存的所有冻结数量之和
		BigDecimal djsl = stockFreezeHistoryMapper.sumFreezeCount(kcid);
		djsl = djsl == null ? BigDecimal.ZERO : djsl;
		User user = ThreadVarUtil.getUser();
		
		// 库内
		if(stockTypeEnum == StockTypeEnum.INNER_STOCK){
			Stock stock = stockMapper.selectById(kcid);
			stock.setDjsl(djsl);
			stock.setWhrid(user.getId());
			stock.setWhsj(new Date());
			stockMapper.updateByPrimaryKeySelective(stock);
		}
		// 外场
		else if(stockTypeEnum == StockTypeEnum.OUTER_STOCK){
			OutFieldStock outFieldStock = outFieldStockMapper.selectByPrimaryKey(kcid);
			outFieldStock.setDjsl(djsl);
			outFieldStock.setWhrid(user.getId());
			outFieldStock.setWhsj(new Date());
			outFieldStockMapper.updateByPrimaryKeySelective(outFieldStock);
		}
	}
	
}
