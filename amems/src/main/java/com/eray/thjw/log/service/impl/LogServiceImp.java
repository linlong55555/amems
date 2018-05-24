package com.eray.thjw.log.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.dao.CommonRecMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.log.po.Field;
import com.eray.thjw.log.po.LogAccessRule;
import com.eray.thjw.log.po.Table;
import com.eray.thjw.log.service.LogService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.SysLogMapper;
import com.eray.thjw.system.po.SysLog;

/**
 * 日志服务实现
 * @author zhuchao
 *
 */
@Service
public class LogServiceImp implements LogService {

//	private static final String table = "table";
	@Resource
	private SysLogMapper sysLogMapper;
	@Resource
	private CommonRecMapper commonRecMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Override
	public Map<String, Object> queryList(LogAccessRule rule) throws Exception {
		LogAccessRule config = SysContext.getLogConfig(rule.getCode());
		if (config == null) {
			throw new BusinessException("找不到日志规则");
		}
		
		config.getMaster().setCzls(rule.getCzls());
		config.getMaster().setPagination(rule.getPagination());
		config.getMaster().setParamsMap(rule.getParamsMap());
//		config.getMaster().getParamsMap().put("countSqlId", config.getSpace().concat(".").concat(config.getMaster().getCountSqlId()));
		config.getMaster().getParamsMap().put("listSqlId", config.getSpace().concat(".").concat(config.getMaster().getListSqlId()) );
		config.getMaster().setId(rule.getId());
		Map<String, Object> result = commonRecService.queryList4Log(config.getMaster());
		result.put("config", config);
		return result;
		 
	}

	@Override
	public LogAccessRule queryDifference(BaseEntity baseEntity) throws BusinessException {
		
		try {
			LogAccessRule config = SysContext.getLogConfig(baseEntity.getParamsMap().get("code").toString());
			if (config == null) {
				throw new BusinessException("找不到日志规则");
			}
			 
			if (config.getMaster()!= null) {
				
				//查询主表
				Table master = config.getMaster();
				master.setParamsMap(baseEntity.getParamsMap());
				master.getParamsMap().put("diffSqlId", config.getSpace().concat(".").concat(config.getMaster().getDiffSqlId()));
				List<Map<String,Object>> rows = commonRecService.queryDiff4Log(master);
				master.setRows(rows);
				
				//查询相关表
				if (config.getSlaves()!=null && !config.getSlaves().isEmpty()) {
					for (Table slave : config.getSlaves()) {
						slave.setParamsMap(baseEntity.getParamsMap());
						slave.getParamsMap().put("diffSqlId", config.getSpace().concat(".").concat(slave.getDiffSqlId()));
						List<Map<String,Object>> rows4Slave = commonRecService.queryDiff4Log(slave);
						slave.setRows(rows4Slave);
					}
				}
			}
			return config;
		} catch (Exception e) {
			throw new BusinessException("查询日志明细失败", e);
		}
	}

	@Override
	public List<Field> queryFields(LogAccessRule rule) throws BusinessException{
		LogAccessRule config = SysContext.getLogConfig(rule.getCode());
		if (config == null) {
			throw new BusinessException("找不到日志规则");
		}
		return config.getMaster().getFields();
		
	}

	@Override
	public int queryCount(SysLog sysLog) {
		return sysLogMapper.queryCount(sysLog);
	}

	@Override
	public List<SysLog> queryAllPageList(SysLog sysLog) {
		return sysLogMapper.queryAllPageList(sysLog);
	}

}
