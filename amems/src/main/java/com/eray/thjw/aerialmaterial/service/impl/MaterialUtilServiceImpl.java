package com.eray.thjw.aerialmaterial.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.service.MaterialUtilService;

/**
 * 航材公共工具服务
 * @author xu.yong
 *
 */
@Service("materialUtilService")
public class MaterialUtilServiceImpl implements MaterialUtilService {
	
	@Resource
	private StockMapper stockMapper;
	
	/**
	 * 验证序列号唯一性
	 * @param dprtcode
	 * @param bjh
	 * @param sn
	 * @return
	 */
	public boolean validateSnUniqueness (String dprtcode, String bjh, String sn){
		int count = this.stockMapper.selectCountBjSn(dprtcode, bjh, sn);
		if(count > 1){
			return false;
		}
		return true;
	}
	
}
