package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.TaskReplace;

public interface TaskReplaceMapper {
     
	List<TaskReplace> queryAll(TaskReplace  taskReplace);
  
	
	//根据维修项目id更改eo编号
	
	
	/**
	 * @Description  根据维修项目id更改eo编号
	 * @param eobh
	 * @param wxxmid
	 * @return int
	 */
	int updateEobhByWxxmid(String eobh,String wxxmid);
		
	/**
	 * @Description  根据维修方案id，维修项目id删除维修方案生效区-相关维修项目
	 * @param wxfaid
	 * @param wxxmid
	 * @return
	 */
	int deleteValidZone(String wxfaid,String wxxmid);
	
	
	/**
	 * @Description  根据维修项目id删除维修项目-关联维修项目
	 * @param wxxmid
	 * @return
	 */
	int deleteProject(String wxxmid);
	
	
	//加载维修项目
	/**
	 * @Description  加载维修项目
	 * @param taskReplace
	 * @return
	 */
	List<TaskReplace> initProjectByid(TaskReplace  taskReplace);
	
	
	/**
	 * @Description  加载维修项目弹框
	 * @param taskReplace
	 * @return
	 */
	List<TaskReplace> initProjectWindow(TaskReplace  taskReplace);
}
