package com.eray.thjw.system.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.system.dao.SysLogMapper;
import com.eray.thjw.system.po.SysLog;
import com.eray.thjw.system.service.SystemLogService;


@Service
public class SystemLogServiceImpl implements SystemLogService {

	@Resource
	private SysLogMapper sysLogMapper;

	public void saveLog(SysLog log){
		log.setId(UUID.randomUUID().toString());
		sysLogMapper.insertSelective(log);
	}

}
