package com.eray.thjw.material2.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.DemandMapper;
import com.eray.thjw.material2.dao.DemandSafeguardMapper;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandSafeguard;
import com.eray.thjw.material2.service.DemandSafeguardService;

/** 
 * @Description 
 * @CreateTime 2018-2-26 下午3:17:49
 * @CreateBy 孙霁	
 */
@Service
public class DemandSafeguardServiceImpl implements DemandSafeguardService{

	@Resource
	private DemandSafeguardMapper demandSafeguardMapper;

	/**
	 * 
	 * @Description 根据查询条件查询需求清单
	 * @CreateTime 2018-2-27 下午3:10:33
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public DemandSafeguard selectDetail(String id) throws BusinessException {
		
		DemandSafeguard demandSafeguard = demandSafeguardMapper.selectDetail(id);
		demandSafeguard.getParamsMap().put("ztText", "已校核");
		return demandSafeguard;
	}
}
