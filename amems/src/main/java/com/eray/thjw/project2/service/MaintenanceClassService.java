package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.MaintenanceClass;

/**
 * 
 * @Description 
 * @CreateTime 2017-8-17 上午11:24:19
 * @CreateBy 孙霁
 */
public interface MaintenanceClassService {
	
	/**
	 * 
	 * @Description 根据飞机机型查询维修方案大类(弹窗)
	 * @CreateTime 2017-8-17 上午11:22:44
	 * @CreateBy 韩武
	 * @param maintenanceClass
	 * @return
	 */
	List<MaintenanceClass> queryWinByFjjx(MaintenanceClass maintenanceClass);
	
	/**
	 * 
	 * @Description 根据飞机机型查询维修方案大类(分页)
	 * @CreateTime 2017-8-17 上午11:22:56
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> queryAll(MaintenanceClass maintenanceClass)throws BusinessException;
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-8-17 上午11:23:00
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @throws BusinessException
	 */
	public void insert(MaintenanceClass maintenanceClass)throws BusinessException ;
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-8-17 上午11:23:08
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @throws BusinessException
	 */
	public void update(MaintenanceClass maintenanceClass)throws BusinessException ;
	/**
	 * 
	 * @Description 作废
	 * @CreateTime 2017-8-17 上午11:23:14
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	public void delete(String id)throws BusinessException ;
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-9-14 下午5:55:33
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public MaintenanceClass selectById(String id)throws BusinessException ;
	
}
