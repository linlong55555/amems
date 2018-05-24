package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.framework.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.TaskInfo;
import com.eray.thjw.project2.po.Todo;

public interface TaskService {
	
	/**
	 * @Description 执行任务
	 * @CreateTime 2018年4月11日 上午 11:35
	 * @author 胡黄驰
	 * @param TaskInfo
	 * @return Map<String code,String info>说明
	 * @ R01：执行完毕
	 * @ E01：任务对象数据不合法
	 * @ E02：无法执行不存在的任务
	 * @ E03：任务已经被执行/正在执行
	 */
	public Map<String, String> executeTask(TaskInfo taskInfo) throws Exception;
	/**
	 * 
	 * @Description 新增任务
	 * @CreateTime 2018年4月12日 下午3:14:10
	 * @CreateBy 岳彬彬
	 * @param taskInfo
	 * @return
	 * @throws SaibongException 
	 * @throws Exception
	 */
	public TaskInfo addTask(TaskInfo taskInfo) throws Exception;
	/**
	 * 
	 * @Description 获取所有待处理的任务
	 * @CreateTime 2018年4月13日 下午3:50:23
	 * @CreateBy 岳彬彬
	 * @return
	 * @throws Exception
	 */
	public List<TaskInfo> getAllTodoList() throws Exception;
	
	/**
	 * 
	 * @Description 获取所有任务
	 * @CreateTime 2018年4月14日 上午10:18:48
	 * @CreateBy 岳彬彬
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAll(TaskInfo taskInfo) throws Exception;
	/**
	 * 
	 * @Description 处理单个待处理任务
	 * @CreateTime 2018年4月14日 上午11:57:25
	 * @CreateBy 岳彬彬
	 * @return
	 */
	public void doTodoTask(TaskInfo taskInfo) throws Exception;
	/**
	 * 
	 * @Description 处理所有待处理任务
	 * @CreateTime 2018年4月14日 下午3:39:53
	 * @CreateBy 岳彬彬
	 * @throws Exception
	 */
	public void doTodoTaskList();
	
	/**
	 * 
	 * @Description 新增发送邮件任务
	 * @CreateTime 2018年4月27日 下午5:21:38
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param dbsm
	 * @param dbid
	 * @return
	 */
	public void doEmailTask(Todo todo);
	/**
	 * 
	 * @Description 删除任务及已压缩文件
	 * @CreateTime 2018年5月9日 上午10:22:23
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException 
	 */
	public String deleteTask(String id) throws BusinessException;
}
