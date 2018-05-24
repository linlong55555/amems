package com.eray.thjw.quality.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.quality.po.Auditnotice;

/** 
 * @Description 审核通知单
 * @CreateTime 2018-1-8 上午10:04:39
 * @CreateBy 孙霁	
 */
public interface AuditNoticeService {


	/**
	 * 
	 * @Description 根据条件查询列表数据
	 * @CreateTime 2018-1-8 上午10:05:30
	 * @CreateBy 孙霁
	 * @param auditNotice
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAll(Auditnotice auditNotice)throws BusinessException ;
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditnotice
	 * @return
	 * @throws BusinessException
	 */
	public String insert(Auditnotice auditnotice)throws BusinessException ;
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditnotice
	 * @return
	 * @throws BusinessException
	 */
	public String update(Auditnotice auditnotice)throws BusinessException ;
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Auditnotice selectById(String id)throws BusinessException ;
	
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
	 * @Description 下发
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void updateIssue(String id)throws BusinessException ;
	/**
	 * 
	 * @Description 接收
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void updateAccept(String id)throws BusinessException ;
	/**
	 * 
	 * @Description 接收审核项目单
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public void updateacceptAuditNotice(String id)throws BusinessException ;
	
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
