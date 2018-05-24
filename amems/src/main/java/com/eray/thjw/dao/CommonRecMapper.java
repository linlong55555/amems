package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.log.po.Field;
import com.eray.thjw.log.po.LogAccessRule;
import com.eray.thjw.po.BaseEntity;

/**
 * @author liub
 * @description 历史记录公共mapper
 * @develop date 2016.08.20
 */
public interface CommonRecMapper {
    
	/**
	 * @author liub
	 * @description 根据表名查询表字段
	 * @param maintenance
	 * @return List<String>
	 * @develop date 2016.08.20
	 */
	List<String> queryColumnName(String tableName);
	
	/**
	 * @author liub
	 * @description 根据条件新增历史记录
	 * @param map
	 * @return int
	 * @develop date 2016.08.20
	 */
	int insert(Map<String, Object> map);
	
	/**
	 * @author liub
	 * @description 根据动态条件新增历史记录
	 * @param map
	 * @return int
	 * @develop date 2016.09.07
	 */
	int insertList(Map<String, Object> map);
	/**
	 * @author liub
	 * @description 根据自定义条件新增历史记录
	 * @param map
	 * @return int
	 * @develop date 2016.09.07
	 */
	int insertListByConditions(Map<String, Object> map);
	
	/**
	 * 查询指定日志表日志列表
	 * @param param {table:日志表名称}
	 * @return
	 */
	public List<Map<String,Object>> queryPageList(LogAccessRule param);
	
	/**
	 * 查询日志总数
	 * @param param
	 * @return
	 */
	public int queryPageCount(LogAccessRule param);

	public List<Field> queryFields(Map<String, Object> param);
	
	
	/**
	 * 查询日志总数
	 * @param param
	 * @return
	 */
	public int queryCount4Bs013(BaseEntity baseEntity);

	/**
	 * 
	 * @param baseEntity
	 * @return
	 */
	public List<Map<String, Object>> queryList4Bs013(BaseEntity baseEntity);
	
	/**
	 * 查询日志差异(故障保留单-主表)
	 * @param baseEntity
	 * @return
	 */
	public List<Map<String, Object>> queryDiff4Bs013(BaseEntity baseEntity);
	
	/**
	 * 查询日志差异(附件-相关表)
	 * @param baseEntity
	 * @return
	 */
	public List<Map<String, Object>> queryDiff4D011(BaseEntity baseEntity);
	
	
}