package com.eray.thjw.log.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.log.po.Field;
import com.eray.thjw.log.po.LogAccessRule;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.po.SysLog;

/**
 * 日志服务接口
 * @author zhuchao
 *
 */
public interface LogService {

	/**
	 * 查询指定业务关键字下日志列表
	 * @param rule 
	 * @return
	 * @throws BusinessException
	 * @throws Exception 
	 */
	public Map<String, Object> queryList(LogAccessRule rule) throws BusinessException, Exception;

	/**
	 * 查询指定业务关键字下指定流水的日志以与上一版本的日志差异
	 * @param code 日志关键字（业务表名，参考TableEnum）
	 * @param czls 操作流水
	 * @param id 业务表主键 ID
	 * @return
	 * @throws BusinessException
	 */
	public LogAccessRule queryDifference(BaseEntity param) throws BusinessException;

	public List<Field> queryFields(LogAccessRule rule) throws BusinessException;

	public int queryCount(SysLog sysLog);

	public List<SysLog> queryAllPageList(SysLog sysLog);
	
}
