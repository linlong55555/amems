package com.eray.thjw.aerialmaterial.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialRequisitionDetailMapper;
import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionDetailService;

@Service
public class MaterialRequisitionDetailServiceImpl implements MaterialRequisitionDetailService {
	
	@Resource
	private MaterialRequisitionDetailMapper materialRequisitionDetailMapper;

	@Override
	public List<MaterialRequisitionDetail> queryAllPageList(
			MaterialRequisitionDetail materialRequisitionDetail)
			throws RuntimeException {
		return materialRequisitionDetailMapper.queryAllPageList(materialRequisitionDetail);
	}

	@Override
	public int queryCount(MaterialRequisitionDetail materialRequisitionDetail)
			throws RuntimeException {
		return materialRequisitionDetailMapper.queryCount(materialRequisitionDetail);
	}

	@Override
	public void updateByPrimaryKey(
			MaterialRequisitionDetail materialRequisitionDetail) {
		materialRequisitionDetailMapper.updateByPrimaryKey(materialRequisitionDetail);
		
	}

	@Override
	public MaterialRequisitionDetail selectByPrimaryKey(String lydmxid)
			throws RuntimeException {
		// TODO Auto-generated method stub
		return materialRequisitionDetailMapper.selectByPrimaryKey(lydmxid);
	}

	@Override
	public void updateByPrimaryKeySelectives(
			MaterialRequisitionDetail materialRequisitionDetail)
			throws RuntimeException {
		materialRequisitionDetailMapper.updateByPrimaryKeySelectives(materialRequisitionDetail);
		
	}
	
	
}
