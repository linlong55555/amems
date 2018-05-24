package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.RoutinginspectionrecordDetail;

public interface RoutinginspectionrecordDetailService {

	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<RoutinginspectionrecordDetail> queryAllByMainid(RoutinginspectionrecordDetail routinginspectionrecordDetail);
}
