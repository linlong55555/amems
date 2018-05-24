package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.system.po.WorkGroup;

public interface WorkGroupMapper {
  
	List<WorkGroup> selectWorkGroupList(WorkGroup wg);
	
	void insertWorkGroup(WorkGroup wg);
	
	void updateWorkGroupByPrimaryKey(WorkGroup wg);
	
	void updateToInvalid(WorkGroup wg);
	
	int getCountByDprtCode2(WorkGroup wg);
	
	WorkGroup getWorkGroupById(WorkGroup wg);
	
	List<WorkGroup> getWorkGroupList(WorkGroup wg);      //加载工单的工作组

	void updateToMrbs(WorkGroup wg);
	
	/**
	 * @Description 查询默认的工作组id
	 * @CreateTime 2018-5-16 下午1:44:34
	 * @CreateBy 刘兵
	 * @param dprtcode
	 * @return
	 */
	String getId4Mrbs(String dprtcode);
}