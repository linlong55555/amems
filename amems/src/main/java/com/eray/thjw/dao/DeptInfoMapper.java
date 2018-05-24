package com.eray.thjw.dao;

import com.eray.thjw.po.DeptInfo;

public interface DeptInfoMapper {
	
	int insertDeptInfo(DeptInfo deptInfo);
	
	DeptInfo selectById(String id);
	
	int updateById(DeptInfo deptInfo);

	DeptInfo selectCounts(String jgdm);
	
	void deleteDeptInfo(String id);
	
	int updateByPrimaryKey(DeptInfo deptInfo);
}