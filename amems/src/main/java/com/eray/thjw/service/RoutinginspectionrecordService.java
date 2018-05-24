package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Routinginspectionrecord;


public interface RoutinginspectionrecordService {

	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Routinginspectionrecord> queryAll(Routinginspectionrecord routinginspectionrecord);
	
	/**
	 * @author sunji
	 * @description 添加
	 * @param inster
	 * @return int
	 * @develop date 2016.08.15
	 */
	public String inster(Routinginspectionrecord routinginspectionrecord);
	/**
	 * @author sunji
	 * @description 根据id查询数据
	 * @param id
	 * @return Routinginspectionrecord
	 * @develop date 2016.08.15
	 */
	public Routinginspectionrecord selectByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description 修改
	 * @param inster
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void update(Routinginspectionrecord routinginspectionrecord)throws BusinessException;
	/**
	 * @author sunji
	 * @description 作废
	 * @param inster
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void invalid(Routinginspectionrecord routinginspectionrecord)throws BusinessException;
}
