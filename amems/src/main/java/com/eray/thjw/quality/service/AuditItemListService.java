package com.eray.thjw.quality.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AuditItem;

/** 
 * @Description 审核项目单
 * @CreateTime 2018-1-16 下午2:55:34
 * @CreateBy 孙霁	
 */
public interface AuditItemListService {

	/**
	 * 
	 * @Description 根据条件查询列表数据
	 * @CreateTime 2018-1-8 上午10:05:30
	 * @CreateBy 孙霁
	 * @param auditItem
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAll(AuditItem auditItem)throws BusinessException ;
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditItem
	 * @return
	 * @throws BusinessException
	 */
	public String insert(AuditItem auditItem)throws BusinessException ;
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditItem
	 * @return
	 * @throws BusinessException
	 */
	public String update(AuditItem auditItem)throws BusinessException ;
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public AuditItem selectById(String id)throws BusinessException ;
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void delete(String id)throws BusinessException ;
	
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void updateClose(String id)throws BusinessException ;
}
