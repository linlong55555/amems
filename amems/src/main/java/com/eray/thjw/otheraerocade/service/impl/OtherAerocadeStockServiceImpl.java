package com.eray.thjw.otheraerocade.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.otheraerocade.dao.OtherAerocadeStockMapper;
import com.eray.thjw.otheraerocade.service.OtherAerocadeStockService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;
@Service
public class OtherAerocadeStockServiceImpl implements OtherAerocadeStockService{
	
	@Resource
	private OtherAerocadeStockMapper otherAerocadeStockMapper;
	/**
	 * 按条件查询一页其他飞行队
	 * @author sunji
	 * @param param
	 * @param pagination
	 * @return
	 */
	public List<Stock> queryAllPageList(Stock stock) throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		Map<String,Object> map=stock.getParamsMap();
		map.put("userDprtcode", user.getJgdm());
		stock.setParamsMap(map);
		return otherAerocadeStockMapper.queryAllPageList(stock);
	}

}
