package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.GodownEntryDetailMapper;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.aerialmaterial.service.GodownEntryDetailService;

/**
 * 入库附表service
 * @author ll
 *
 */
@Service("godownEntryDetailService")
public class GodownEntryDetailServiceImpl implements GodownEntryDetailService {
	
	@Resource
	private GodownEntryDetailMapper godownEntryDetailMapper;

	@Override
	public List<GodownEntryDetail> queryGetreceiptSheetDetails(String id)
			throws RuntimeException {
		 
		return godownEntryDetailMapper.queryGetreceiptSheetDetails(id);
	}

	@Override
	public void updateByPrimaryKeySelective(GodownEntryDetail godownEntryDetail) {
		godownEntryDetailMapper.updateByPrimaryKeySelective(godownEntryDetail);
		
	}

	@Override
	public void insertSelective(GodownEntryDetail godownEntryDetail) {
		godownEntryDetailMapper.insertSelective(godownEntryDetail);
		
	}

	@Override
	public GodownEntryDetail selectByPrimaryKey(String id) {
		return godownEntryDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public GodownEntryDetail selectByPrimary(
			GodownEntryDetail godownEntryDetail1) {
		return godownEntryDetailMapper.selectByPrimary(godownEntryDetail1);
	}
	
	


}
