package com.eray.thjw.training.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Faculty;
import com.eray.thjw.training.po.FacultyCourse;
import com.eray.thjw.training.po.TrainingPlan;
public interface FacultyService {
	
	/** sunji
	 * 分页查询教员信息
	 * @return Map<String, Object>
	 * @throws Exception 
	 */
	public Map<String, Object> queryAll(Faculty faculty)throws BusinessException;
	
	/**
	 * 
	 * @Description 新增方法
	 * @CreateTime 2017年9月25日 上午11:34:20
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	public boolean add(Faculty faculty)throws BusinessException;
	
	/**
	 * 
	 * @Description 修改方法
	 * @CreateTime 2017年9月25日 下午2:35:08
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	public boolean update(Faculty faculty)throws BusinessException;
	
	
	/**
	 * 
	 * @Description 删除方法
	 * @CreateTime 2017年9月25日 上午11:38:33
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	public boolean delete(Faculty faculty)throws BusinessException;

	/**
	 * 
	 * @Description 获取详细信息
	 * @CreateTime 2017年10月9日 下午7:05:24
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> queryInfo(Faculty faculty) throws BusinessException;
	
	/**
	 * 
	 * @Description 获取授课记录
	 * @CreateTime 2017年10月10日 下午4:56:50
	 * @CreateBy 胡才秋
	 * @param data
	 * @return
	 * @throws BusinessException
	 */
	List<TrainingPlan> querCourseInfoList(TrainingPlan data) throws BusinessException;

	/**
	 * 
	 * @Description 删除授权课程
	 * @CreateTime 2017年10月12日 下午3:19:44
	 * @CreateBy 胡才秋
	 * @param facultyCourse
	 * @return
	 * @throws BusinessException
	 */
	boolean deleteCourse(FacultyCourse facultyCourse) throws BusinessException;

	public Map<String, Object> queryAllopenlist(Faculty faculty)throws BusinessException;
	
}
