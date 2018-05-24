package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.ScheduledTask;

public interface ScheduledTaskMapper {
	
	void save(ScheduledTask scheduledTask);	// // 新增b_s_009 计划任务
	
	/**
	 * 按条件查询一页计划任务
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<ScheduledTask> queryAllPageList(ScheduledTask scheduledTask)  throws RuntimeException;
	 
	 public Map<String, Object> modify(ScheduledTask scheduledTask) throws RuntimeException;
	 
	 Map<String, Object> subWorkersimplex(ScheduledTask scheduledTask)throws RuntimeException;

	ScheduledTask queryKey(String rwid)throws RuntimeException;

	void update(ScheduledTask scheduledTask)throws RuntimeException;

	void updateByPrimaryKeySelective1(ScheduledTask scheduledTask)throws RuntimeException;
}
