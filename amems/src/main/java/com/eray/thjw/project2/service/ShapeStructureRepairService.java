package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.ShapeStructureRepair;

public interface ShapeStructureRepairService {
	/**
	 * 获取列表
	 * @param record
	 * @return
	 */
	List<ShapeStructureRepair> getList(ShapeStructureRepair record);
	/**
	 * 新增
	 * @param record
	 * @throws BusinessException
	 */
	void insertRecord(ShapeStructureRepair record) throws BusinessException;
	/**
	 * 修改
	 * @param record
	 * @throws BusinessException
	 */
	void updateRecordById(ShapeStructureRepair record) throws BusinessException;
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	ShapeStructureRepair getRecordById(String id)throws BusinessException;
	/**
	 * 作废
	 * @param id
	 * @throws BusinessException 
	 */
	void deleteById(String id) throws BusinessException;

}
