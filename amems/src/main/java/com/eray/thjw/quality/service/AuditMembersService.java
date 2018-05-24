package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.AuditMembers;

/**
 * 
 * @Description 审核成员service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface AuditMembersService {

	/**
	 * 
	 * @Description 修改审核成员
	 * @CreateTime 2018年1月8日 下午4:02:30
	 * @CreateBy 林龙
	 * @param auditMembers
	 * @return
	 * @throws BusinessException
	 */
	public void updateSelective(List<AuditMembers> auditMemberslist, String uuid,Integer ywlx,String dprtcode,User user)throws BusinessException;
	/**
	 * 
	 * @Description 新增审核成员
	 * @CreateTime 2018年1月8日 下午4:02:30
	 * @CreateBy 林龙
	 * @param auditMembers
	 * @return
	 * @throws BusinessException
	 */
	public void inertSelective(List<AuditMembers> auditMemberslist, String uuid,Integer ywlx,String dprtcode,User user)throws BusinessException;
	
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2018-1-10 下午2:40:53
	 * @CreateBy 孙霁
	 * @param auditMembersList
	 * @param ywid
	 * @param ywlx
	 * @param user
	 * @throws BusinessException
	 */
	public void insert(List <AuditMembers> auditMembersList, String ywid, Integer ywlx,User user)throws BusinessException;

	/**
	 * 
	 * @Description 根据mainid 获取审核成员信息
	 * @CreateTime 2018-1-11 上午11:17:37
	 * @CreateBy 孙霁
	 * @param mainid
	 * @throws BusinessException
	 */
	public List<AuditMembers> selectByYwid(String ywid)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据mainid 获取审核成员信息
	 * @CreateTime 2018-1-11 上午11:17:37
	 * @CreateBy 孙霁
	 * @param mainid
	 * @throws BusinessException
	 */
	public void delete(String ywid)throws BusinessException;
}
