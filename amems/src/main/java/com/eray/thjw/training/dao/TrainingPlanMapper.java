package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.po.User;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.po.TrainingPlan;

public interface TrainingPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(TrainingPlan record);

    int insertSelective(TrainingPlan record);

    TrainingPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TrainingPlan record);

    int updateByPrimaryKey(TrainingPlan record);
    
    /**
	 * @author liub
	 * @description 根据id修改培训计划表单数据
	 * @param record
	 * @return int
	 */
    int updatePlanById(TrainingPlan record);
    
    /**
	 * @author liub
	 * @description 根据查询条件分页查询培训计划信息
	 * @param record
	 * @return List<TrainingPlan>
	 */
	public List<TrainingPlan> queryAllPageList(TrainingPlan record);
	
    /**
	 * @author liub
	 * @description 根据查询条件分页查询年度视图信息
	 * @param record
	 * @return List<TrainingPlan>
	 */
	public List<TrainingPlan> queryYearPageList(TrainingPlan record);
	
	/**
	 * @author liub
	 * @description 根据id查询培训计划、课程及用户信息
	 * @param id
	 * @return TrainingPlan
	 */
	TrainingPlan selectById(String id);
	
	/**
	 * @author liub
	 * @description 修改状态
	 * @param record
	 * @return int
	 */
	int updateStatus(TrainingPlan record);

	/**
	 * 培训通知
	 * @param trainingPlan
	 * @return
	 */
	List<TrainingPlan> queryAllPagetrainingnoticeList(TrainingPlan trainingPlan);

	List<TrainingPlan> queryAllPagerecordsList(TrainingPlan trainingPlan);

	List<TrainingPlan> queryAllPageKcList(TrainingPlan trainingPlan);
	
	List<TrainingPlan> queryAllPageListToPerson(TrainingPlan trainingPlan);
	/**
	 * 
	 * @Description 获取授课记录
	 * @CreateTime 2017年10月10日 下午4:50:12
	 * @CreateBy 胡才秋
	 * @param trainingPlan
	 * @return
	 */
	List<TrainingPlan> queryInfoList(TrainingPlan trainingPlan);
	
	
	List<User> queryTrainMembersByDprtcode(String dprtcoe);
	
	int batchInsert(List<TrainingPlan> list);
	
	/**
	 * @Description 成绩录入修改
	 * @CreateTime 2018年2月7日 下午3:17:00
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateByRecordResult(TrainingPlan record);
}