package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.log.po.Table;
import com.eray.thjw.po.BaseEntity;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 历史数据公共service,用于业务逻辑处理
 * @develop date 2016.07.22
 */
public interface CommonRecService {
	
	/**
	 * @author liub
	 * @description 根据条件新增历史记录
	 * @param 主键id(唯一),tableName(表名),tableEnum(记录操作人),typeEnum(记录-修改类型：1SAVE（新增）、2UPDATE（修改）、3DELETE（删除）)
	 * @return int
	 * @develop date 2016.08.22
	 */
	public int write(String id , TableEnum tableEnum , String username , UpdateTypeEnum typeEnum,String recZdid);
	
	/**
	 * @author liub
	 * @description 根据条件新增历史记录
	 * @param 主键id(唯一),tableName(表名),tableEnum(记录操作人),typeEnum(记录-修改类型：1SAVE（新增）、2UPDATE（修改）、3DELETE（删除）)
	 * @return int
	 * @develop date 2016.08.22
	 */
	//public int write(String column ,  List<String> columnValueList , TableEnum tableEnum , String username , UpdateTypeEnum typeEnum,String recZdid);

	/**
	 * 
	 * @param id
	 * @param tableEnum
	 * @param username
	 * @param recCzls
	 * @param recCzsm
	 * @param typeEnum
	 * @return
	 */
	public int write(String id, TableEnum tableEnum, String username, String recCzls, LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid);
	 
	/**
	 * 
	 * @param column
	 * @param columnValueList
	 * @param tableEnum
	 * @param username
	 * @param recCzls
	 * @param recCzsm
	 * @param typeEnum 
	 * @param recCzls
	 * @return
	 */
	public int write(String column, List<String> columnValueList, TableEnum tableEnum, String username, String recCzls,
			LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid);
	
	/**
	 * 
	 * @param conditions
	 * @param tableEnum
	 * @param username
	 * @param recCzls
	 * @param recCzsm
	 * @param typeEnum
	 * @return
	 */
	public int writeByWhere(String conditions,TableEnum tableEnum, String username, String recCzls,
			LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid);
	
	/**
	 * 查询下日志数量
	 * @param baseEntity
	 * @return
	 * @throws Exception 
	 */
	public int queryCount4Log(BaseEntity baseEntity) throws Exception;
	
	/**
	 * 查询下日志记录
	 * @param baseEntity
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> queryList4Log(BaseEntity baseEntity) throws Exception;

	/**
	 * 查询下日志差异记录
	 * @param master
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> queryDiff4Log(Table baseEntity) throws Exception;
	
}
