package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledTask;
import com.eray.thjw.productionplan.po.TimeControllWareItem;

public interface ScheduledTaskSerivce {
	
	void save(String id,String rwdh,ScheduledCheckItem scheduledCheckItem, String czls) throws Exception;	//  新增定检 b_s_009 计划任务
	
	void saveskj(TimeControllWareItem timeControllWareItem) throws Exception;	//  新增时控件 b_s_009 计划任务
	
	
	/**
	 * 按条件查询一页计划任务
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<ScheduledTask> queryAllPageList(ScheduledTask scheduledTask)  throws RuntimeException;
	
	 Map<String, Object> modify(ScheduledTask scheduledTask)throws RuntimeException;
	 
	 Map<String, Object> subWorkersimplex(ScheduledTask scheduledTask)throws RuntimeException;

	ScheduledTask queryKey(String rwid)throws RuntimeException;

	void update(ScheduledTask scheduledTask)throws RuntimeException;
}
