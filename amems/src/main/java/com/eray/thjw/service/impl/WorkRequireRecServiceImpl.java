package com.eray.thjw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.WorkRequireRecMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkRequireRec;
import com.eray.thjw.service.WorkRequireRecService;
@Service
public class WorkRequireRecServiceImpl implements WorkRequireRecService{
@Autowired
private WorkRequireRecMapper  mapper;
	@Override
	public void writeLog(WorkRequireRec workRequireRec) throws BusinessException {
		   mapper.writeLog(workRequireRec);
		
	}

}
