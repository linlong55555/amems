package com.eray.thjw.production.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.production.po.AirworthinessDirective;
/**
 * 适航指令服务接口
 * @author zhuchao
 *
 */
public interface AirworthinessDirectiveService {
	/**
	 * 分页查询适航指令
	 * @param record
	 * @return
	 */
	public Map<String, Object> queryList(AirworthinessDirective record) throws BusinessException;
	
	/**
	 * 新增适航指令
	 * @param record
	 */
	public void insert(AirworthinessDirective record)throws BusinessException;
	/**
	 * 修改适航指令
	 * @param record
	 */
	public void update(AirworthinessDirective record)throws BusinessException;

	/**
	 * 加载一条适航指令
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	public AirworthinessDirective load(String id) throws BusinessException;

	/**
	 * 插入或更新一条适航指令
	 * @param record
	 */
	public void saveOrUpdate(AirworthinessDirective record) throws BusinessException;

	/**
	 * 作废一条适航指令
	 * @param record
	 */
	public void cancel(AirworthinessDirective record) throws BusinessException;

	/**
	 * 查询一页适航指令
	 * @param record
	 * @return
	 */
	public Map<String, Object> queryPage(AirworthinessDirective record)throws BusinessException;
}