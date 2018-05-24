package com.eray.thjw.quality.service;

import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AuditDiscovery;

/** 
 * @Description 发现问题通知单service
 * @CreateTime 2018年1月8日 下午1:48:25
 * @CreateBy 韩武	
 */
public interface AuditDiscoveryService {
	
	/**
	 * @Description 保存发现问题通知单
	 * @CreateTime 2018年1月8日 下午1:49:43
	 * @CreateBy 韩武
	 * @param discovery
	 * @return
	 */
	String doSave(AuditDiscovery discovery) throws BusinessException, SaibongException;
	/**
	 * 
	 * @Description 发现问题通知单列表
	 * @CreateTime 2018年1月9日 下午5:12:53
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getList(AuditDiscovery discovery) throws BusinessException;
	/**
	 * 
	 * @Description 获取问题通知单
	 * @CreateTime 2018年1月10日 上午11:01:16
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @return
	 * @throws BusinessException
	 */
	AuditDiscovery getRecord(AuditDiscovery discovery) throws BusinessException;
	/**
	 * 
	 * @Description 删除通知单
	 * @CreateTime 2018年1月10日 下午5:03:27
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @throws BusinessException
	 */
	void deleteRecord(AuditDiscovery discovery) throws BusinessException;
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年1月10日 下午5:29:54
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @throws BusinessException
	 */
	void update4CloseRecord(AuditDiscovery discovery) throws BusinessException;
}
