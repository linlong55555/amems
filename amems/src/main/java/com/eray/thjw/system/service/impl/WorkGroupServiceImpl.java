package com.eray.thjw.system.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.system.dao.WorkGroupMapper;
import com.eray.thjw.system.po.WorkGroup;
import com.eray.thjw.system.service.WorkGroupService;
import com.eray.thjw.util.ThreadVarUtil;

@Service
public class WorkGroupServiceImpl implements WorkGroupService {
	
	@Resource
	private WorkGroupMapper workGroupMapper;

	@Override
	public List<WorkGroup> selectWorkGroupList(WorkGroup wg) {
		
		return workGroupMapper.selectWorkGroupList(wg);
	}

	@Override
	public void insertWorkGroup(WorkGroup wg) {
		User user = ThreadVarUtil.getUser();		
		if(wg.getMrbs() == 1){
			wg.setDprtcode(user.getJgdm());
			workGroupMapper.updateToMrbs(wg);
		}
		workGroupMapper.insertWorkGroup(wg);
	}

	@Override
	public void updateWorkGroupByPrimaryKey(WorkGroup wg) {
		User user = ThreadVarUtil.getUser();		
		if(wg.getMrbs() == 1){
			wg.setDprtcode(user.getJgdm());
			workGroupMapper.updateToMrbs(wg);
		}
		workGroupMapper.updateWorkGroupByPrimaryKey(wg);
	}

	@Override
	public void updateToInvalid(WorkGroup wg) {
		workGroupMapper.updateToInvalid(wg);
	}

	@Override
	public int getCountByDprtCode2(WorkGroup wg) {
		
		return workGroupMapper.getCountByDprtCode2(wg);
	}

	@Override
	public WorkGroup getWorkGroupById(WorkGroup wg) {
		
		return workGroupMapper.getWorkGroupById(wg);
	}
	@Override
	public List<WorkGroup> getWorkGroupList(WorkGroup wg) {
		return workGroupMapper.getWorkGroupList(wg);
	}
}
