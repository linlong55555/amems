package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.TaskInfo;

public interface TaskInfoMapper {

	/**
	 * @Description 根据id查询
	 * @CreateTime 2018年4月11日 下午13:19:29
	 * @CreateBy hchu
	 * @param id
	 * @return
	 */
	public TaskInfo selectByPrimaryKey(String taskid);
	
	/**
	 * @Description 修改任务状态
	 * @CreateTime 2018年4月11日 下午13:55:10
	 * @CreateBy hchu
	 * @param id
	 * @return
	 */
	public void updateTaskInfoByPrimaryKey(TaskInfo taskinfo);
	/**
	 * 
	 * @Description 新增任务
	 * @CreateTime 2018年4月12日 下午3:28:15
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void insertSelective(TaskInfo record);
	/**
	 * 
	 * @Description 获取所有待处理的导出任务
	 * @CreateTime 2018年4月13日 下午4:07:47
	 * @CreateBy 岳彬彬
	 * @return
	 */
	List<TaskInfo> getAllTodoList();
	/**
	 * 
	 * @Description 获取所有导出任务
	 * @CreateTime 2018年4月14日 上午10:22:26
	 * @CreateBy 岳彬彬
	 * @return
	 */
	List<TaskInfo> getAllList(TaskInfo record);
	/**
	 * 
	 * @Description 状态为2的改为1
	 * @CreateTime 2018年4月14日 下午3:34:06
	 * @CreateBy 岳彬彬
	 */
	void updateZt4Export();
	/**
	 * 
	 * @Description 获取所有待处理的发送邮件任务
	 * @CreateTime 2018年4月28日 上午9:46:35
	 * @CreateBy 岳彬彬
	 * @return
	 */
	List<TaskInfo> getAllTodoEmail();
	/**
	 * 
	 * @Description 获取单个任务
	 * @CreateTime 2018年4月28日 上午10:44:03
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	TaskInfo getTaskInfoById(String id);
	/**
	 * 
	 * @Description 将email状态为2修改为1
	 * @CreateTime 2018年4月28日 下午2:44:30
	 * @CreateBy 岳彬彬
	 */
	void updateZt4Email();
	/**
	 * 
	 * @Description 删除任务
	 * @CreateTime 2018年5月9日 上午11:12:20
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	void deleteById(String id);
}
