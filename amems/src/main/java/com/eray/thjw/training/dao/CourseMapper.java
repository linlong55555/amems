package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Course;

public interface CourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
    
    /**
	 * @author liub
	 * @description 根据查询条件分页查询课程信息
	 * @param course
	 * @return List<Course>
	 */
	public List<Course> queryAllPageList(Course record);
	/**
	 * @author sunji
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<Course>
	 */
	public List<Course> queryAllBygwid(String id);
	
	/**
	 * @author liub
	 * @description 根据id查询课程及用户信息
	 * @param id
	 * @return Course
	 */
	Course selectById(String id);
	
	/**
	 * @author liub
	 * @description 检查课程是否存在
	 * @param record
	 * @return List<Course>
	 */
	List<Course> checkExist(Course record);
	
	/**
	 * @author liub
	 * @description 检查课程是否可以修改作废
	 * @param id
	 * @return Course
	 */
	Course checkUptById(String id);
	
	/**
	 * @author liub
	 * @description 作废课程
	 * @param record
	 * @return int
	 */
	int cancel(Course record);
	
	/**
	 * @Description 查询课程-培训评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param record 课程
	 * @return List<Course> 课程集合
	 */
	public List<Course> queryCourseEval(Course record);

	Course selectkcbh(String kcbm, String dprtcode);
	/**
	 * 
	 * @Description 获取组织机构下状态为1的课程信息
	 * @CreateTime 2017年12月1日 上午9:37:50
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Course> selectALLCourse(String dprtcode);
	/**
	 * 
	 * @Description 批量新增
	 * @CreateTime 2017年12月1日 上午11:23:20
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	int batchInsert(List<Course> list);
	/**
	 * 
	 * @Description 批量修改
	 * @CreateTime 2017年12月1日 上午11:23:47
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	int batchUpdate(List<Course> list);
	
	/**
	 * 
	 * @Description 查询课程编号(去重)
	 * @CreateTime 2018-1-22 下午4:38:11
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	List<Course> selectDistinctKcbh(Course record);
	
	/**
	 * 
	 * @Description 校验课程编号和机型唯一性
	 * @CreateTime 2018-1-28 下午4:38:11
	 * @CreateBy 刘邓
	 * @param business
	 * @return
	 */
	String selectByCodeAndJx(String dprtcode,String jx,String kcbh);
}