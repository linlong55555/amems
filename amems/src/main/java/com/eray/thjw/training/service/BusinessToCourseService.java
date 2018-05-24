package com.eray.thjw.training.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.BusinessToCourse;

public interface BusinessToCourseService {
	
	
	
	/**
	 * @author sunji
	 * @description 增加岗位To课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void insert(BusinessToCourse businessToCourse)throws BusinessException;
	/**
	 * @author sunji
	 * @description  修改岗位To课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void update(BusinessToCourse businessToCourse)throws BusinessException;
	/**
	 * @author sunji
	 * @description  修改岗位To课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void delete(String id)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据岗位id查询数据
	 * @CreateTime 2018-1-22 下午3:03:13
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public List<BusinessToCourse> queryAllBygwid(String gwid,String dprtcode)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-1-22 下午3:07:17
	 * @CreateBy 孙霁
	 * @param gwid
	 * @return
	 * @throws BusinessException
	 */
	BusinessToCourse selectByPrimaryKey(String id)throws BusinessException;
	
	/**
	 * 
	 * @Description 添加多个
	 * @CreateTime 2018-1-23 下午2:29:07
	 * @CreateBy 孙霁
	 * @param businessToCourseList
	 * @throws BusinessException
	 */
	void insertAll(List<BusinessToCourse> businessToCourseList)throws BusinessException;
}
