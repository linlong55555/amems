package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.productionplan.po.PlaneLoad;

public interface PlaneLoadMapper {
	/**
	 * @author sunji
	 * @description 添加
	 */
	void insertPlaneLoad(PlaneLoad record);
	/**
	 * @author sunji
	 * @description 条件查询列表
	 */
	List<PlaneLoad> getPlaneLoadList(PlaneLoad record);
	/**
	 * @author sunji
	 * @description 根据id查询单个
	 */
	PlaneLoad selectByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description 根据对象修改数据
	 */
	int updateByPrimaryKeySelective(PlaneLoad record);
	
	/**
	 * @author sunji
	 * @description 根据对象修改数据
	 */
	int deleteByPrimaryKey(String id);
}