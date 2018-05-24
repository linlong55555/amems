package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.ShapeStructureRepair;

public interface ShapeStructureRepairMapper {
	/**
	 * 获取列表
	 * @param record
	 * @return
	 */
	List<ShapeStructureRepair> getList(ShapeStructureRepair record);
	/**
	 * 新增
	 * @param record
	 */
	void insertRecord(ShapeStructureRepair record);
	/**
	 * 修改
	 * @param record
	 */
	void updateRecordById(ShapeStructureRepair record);
	/**
	 * 根据id获取对象信息
	 * @param id
	 * @return
	 */
	ShapeStructureRepair getRecordById(String id);
	/**
	 * 作废
	 * @param id
	 */
	void deleteById(String id);
}