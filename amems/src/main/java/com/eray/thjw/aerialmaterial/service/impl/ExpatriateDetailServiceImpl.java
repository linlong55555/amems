package com.eray.thjw.aerialmaterial.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ExpatriateDetailMapper;
import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;
import com.eray.thjw.aerialmaterial.service.ExpatriateDetailService;

/**
 * 外派清单附表
 * @author xu.yong
 *
 */
@Service("expatriateDetailService")
public class ExpatriateDetailServiceImpl implements ExpatriateDetailService {
	
	
	@Resource
	private ExpatriateDetailMapper expatriateDetailMapper;

	@Override
	public void insertSelective(ExpatriateDetail record)
			throws RuntimeException {
		expatriateDetailMapper.insertSelective(record);
	}

	@Override
	public ExpatriateDetail queryByKey(String id, String kcllid)
			throws RuntimeException {
		
		return expatriateDetailMapper.queryByKey(id, kcllid);
	}

	@Override
	public void delete(String id) throws RuntimeException {
		expatriateDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(ExpatriateDetail expatriateDetail)
			throws RuntimeException {
		expatriateDetailMapper.updateByPrimaryKeySelective(expatriateDetail);
	}


	
	
	
}
