package com.eray.thjw.produce.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.PlaneBasic;

/** 
 * @Description 
 * @CreateTime 2017-12-5 下午5:08:10
 * @CreateBy 孙霁	
 */
public interface PlaneBasicService {

	/**
	 * 
	 * @Description 添加飞机基本信息表（D_010）,先查询数据库，如果有，那么进行修改操作，如果没有，进行添加
	 * @CreateTime 2017-12-5 下午5:09:12
	 * @CreateBy 孙霁
	 * @param planeBasic
	 */
	void insertUpdate(Aircraftinfo aircraftinfo) throws BusinessException ;
	/**
	 * 
	 * @Description 获取组织机构下所有有效的飞机
	 * @CreateTime 2017年12月13日 下午2:48:27
	 * @CreateBy 岳彬彬
	 * @param plane
	 * @return
	 */
	List<PlaneBasic> getFjList(PlaneBasic plane);
}
