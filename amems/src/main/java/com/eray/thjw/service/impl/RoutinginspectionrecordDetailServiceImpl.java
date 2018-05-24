package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RoutinginspectionrecordDetailMapper;
import com.eray.thjw.po.RoutinginspectionrecordDetail;
import com.eray.thjw.service.RoutinginspectionrecordDetailService;
@Service
public class RoutinginspectionrecordDetailServiceImpl implements RoutinginspectionrecordDetailService{

	@Autowired
	private RoutinginspectionrecordDetailMapper routinginspectionrecordDetailMapper;
	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<RoutinginspectionrecordDetail> queryAllByMainid(
			RoutinginspectionrecordDetail routinginspectionrecordDetail) {
		return routinginspectionrecordDetailMapper.queryAllByMainid(routinginspectionrecordDetail);
	}

}
