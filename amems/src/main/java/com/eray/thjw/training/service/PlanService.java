package com.eray.thjw.training.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.po.TrainingPlan;


public interface PlanService {
	 
	/**
	 * @author liub
	 * @description 增加培训计划
	 * @param trainingPlan
	 * @throws BusinessException 
	 */
	public String add(TrainingPlan trainingPlan) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改培训计划
	 * @param trainingPlan
	 * @throws BusinessException 
	 */
	public void edit(TrainingPlan trainingPlan) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	public void cancel(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 下发
	 * @param id
	 */
	public void updateIssued(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param trainingPlan
	 */
	public void updateShutDown(TrainingPlan trainingPlan) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询培训计划信息
	 * @param trainingPlan
	 * @return List<TrainingPlan>
	 */
	public List<TrainingPlan> queryAllPageList(TrainingPlan trainingPlan);
	/**
	 * @author 孙霁
	 * @description 根据查询条件分页查询培训计划信息
	 * @param trainingPlan
	 * @return List<TrainingPlan>
	 */
	public List<TrainingPlan> queryAllPageListToPerson(TrainingPlan trainingPlan);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询年度视图信息
	 * @param trainingPlan
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryYearPageList(TrainingPlan trainingPlan);
	
	/**
	 * @author liub
	 * @description 根据id查询培训计划、课程及用户信息
	 * @param id
	 * @return TrainingPlan
	 */
	public TrainingPlan selectById(String id);

	
	/**
	 * 培训通知分页
	 * @param trainingPlan
	 * @return
	 */
	public List<TrainingPlan> queryAllPagetrainingnoticeList(
			TrainingPlan trainingPlan);
	/**
	 * @author sunji
	 * @description 获取年度数据
	 * @param i,j,message
	 */
	public List<TrainingPlan> queryAllTrainingPlan(TrainingPlan trainingPlan);

	public List<TrainingPlan> queryAllPagerecordsList(TrainingPlan trainingPlan);

	public TrainingPlan selectByPrimaryKey(String id);

	public List<TrainingPlan> queryAllPageKcList(TrainingPlan trainingPlan);

	public void save(TrainingPlan trainingPlan);
	
	/**
	 * 
	 * @Description 根据课程编号和人员id查询数据
	 * @CreateTime 2018-3-28 下午3:31:15
	 * @CreateBy 孙霁
	 * @param trainingPlan
	 * @return
	 */
	public Map<String, Object> queryAllByBhAndRy(PlanPerson planPerson);
}
