package com.eray.thjw.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.ThreadVarUtil;


@Service
public class DeptInfoServiceImpl implements DeptInfoService{
	
	@Autowired
	private DeptInfoMapper deptInfoMapper;
	
	@Autowired
	private DepartmentService departmentService;

	@Override
	public int insertDeptInfo(DeptInfo deptInfo) {		
		Department department=new Department();
		department.setId(deptInfo.getId());
		department.setDprtcode(deptInfo.getDprtcode());
		department.setDprtname(deptInfo.getDprtname());
		department.setDprttype("1");
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			department.setPxh(deptInfo.getPxh());			
		}
		department.setRemark(deptInfo.getRemark());
		departmentService.insertSelective(department);
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			deptInfo.setZcfj_max(null);
			deptInfo.setZczh_max(null);
			deptInfo.setYxqks(null);
			deptInfo.setYxqjs(null);			
		}
		return deptInfoMapper.insertDeptInfo(deptInfo);
	}

	@Override
	public DeptInfo selectById(String id) {
		return deptInfoMapper.selectById(id);
	}

	@Override
	public int updateById(DeptInfo deptInfo) {
		Department department=new Department();
		department.setId(deptInfo.getId());
		department.setDprtcode(deptInfo.getDprtcode());
		department.setDprtname(deptInfo.getDprtname());
		department.setDprttype("1");
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			deptInfo.setZcfj_max(null);
			deptInfo.setZczh_max(null);
			deptInfo.setYxqks(null);
			deptInfo.setYxqjs(null);
			deptInfo.setDeptType(null);
		}
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			department.setPxh(deptInfo.getPxh());			
		}
		department.setRemark(deptInfo.getRemark());
		departmentService.updateByPrimaryKeySelective(department);
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			return deptInfoMapper.updateById(deptInfo);		
		}
		return deptInfoMapper.updateByPrimaryKey(deptInfo);
	}
  
}
