package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneLoad;

public interface PlaneLoadService {
	/**
	 * @author sunji
	 * @description 添加
	 */
	void insertPlaneLoad(PlaneLoad record);
	/**
	 * @author sunji
	 * @description 修改
	 */
	void updatePlaneLoad(PlaneLoad record);
	/**
	 * @author sunji
	 * @description 查询列表
	 */
	List<PlaneLoad> getPlaneLoadList(PlaneLoad record);
	/**
	 * @author	sunji
	 * @description  根据id查询单个
	 */
	PlaneLoad selectByPrimaryKey(String id);
	/**
	 * @author	sunji
	 * @description  根据id删除
	 */
	Map<String ,Object> deletePlaneLoad(String id);
}
