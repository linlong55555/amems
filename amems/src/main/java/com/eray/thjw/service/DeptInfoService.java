package com.eray.thjw.service;

import com.eray.thjw.po.DeptInfo;

public interface DeptInfoService {
	
	int insertDeptInfo(DeptInfo deptInfo);
	
	DeptInfo selectById(String id);
	
	int updateById(DeptInfo deptInfo);


}
