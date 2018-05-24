package com.eray.thjw.training.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.Course;


public interface CourseService {
	 
	/**
	 * @author liub
	 * @description 增加课程
	 * @param course
	 * @throws BusinessException 
	 */
	public String add(Course course) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void edit(Course course) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	public void cancel(String id) throws BusinessException;
	
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询课程信息
	 * @param course
	 * @return List<Course>
	 */
	public List<Course> queryAllPageList(Course course);
	
	/**
	 * @author sunji
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<Course>
	 */
	public List<Course> queryAllBygwid(String gwid);
	
	/**
	 * @author liub
	 * @description 根据主键id查询课程信息
	 * @param id
	 * @return Course
	 */
	public Course selectById(String id);
	
	/**
	 * @Description 查询课程-培训评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param record 课程
	 * @return List<Course> 课程集合
	 */
	public List<Course> queryCourseEval(Course record);

	public Course selectkcbh(String kcbm, String dprtcode);
	
	/**
	 * 
	 * @Description 查询课程编号(去重)
	 * @CreateTime 2018-1-22 下午4:38:11
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	List<Course> selectDistinctKcbh(Course record)throws BusinessException;

}
