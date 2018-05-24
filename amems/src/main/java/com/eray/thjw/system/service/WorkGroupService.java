package com.eray.thjw.system.service;

import java.util.List;

import com.eray.thjw.system.po.WorkGroup;

public interface WorkGroupService {
	
	List<WorkGroup> selectWorkGroupList(WorkGroup wg);
	
	void insertWorkGroup(WorkGroup wg);
	
	void updateWorkGroupByPrimaryKey(WorkGroup wg);
	
	void updateToInvalid(WorkGroup wg);
	
	int getCountByDprtCode2(WorkGroup wg);
	
	WorkGroup getWorkGroupById(WorkGroup wg);
	
	/**
	 * @author meizhiliang
	 * @description 加载工单的工作组
	 * @param wg
	 * @return List<WorkGroup>
	 */
	List<WorkGroup> getWorkGroupList(WorkGroup wg);      
}
