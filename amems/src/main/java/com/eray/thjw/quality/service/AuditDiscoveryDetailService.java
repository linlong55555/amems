package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AuditDiscovery;
import com.eray.thjw.quality.po.AuditDiscoveryDetail;

/** 
 * @Description 发现问题通知单详情service
 * @CreateTime 2018年1月8日 下午1:48:25
 * @CreateBy 韩武	
 */
public interface AuditDiscoveryDetailService {
	
	/**
	 * @Description 保存发现问题通知单详情
	 * @CreateTime 2018年1月8日 下午2:25:47
	 * @CreateBy 韩武
	 * @param shwtdid
	 * @param shwtbh
	 * @param details
	 * @throws BusinessException
	 * @throws SaibongException
	 */
	void doSave(AuditDiscovery record) throws BusinessException, SaibongException;
	/**
	 * 
	 * @Description 根据审核问题单id获取审核问题清单
	 * @CreateTime 2018年1月10日 上午11:04:45
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 * @return
	 */
	List<AuditDiscoveryDetail> getByShwtdid(String shwtdid);
	/**
	 * 
	 * @Description 根据审核问题单id删除审核问题清单
	 * @CreateTime 2018年1月10日 下午5:08:45
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 */
	void deleteByShwtdid(String shwtdid);
	
	/**
     * 
     * @Description 批量关闭
     * @CreateTime 2018年1月11日 上午9:52:01
     * @CreateBy 岳彬彬
     * @param list
     */
	void doBatchClose(AuditDiscoveryDetail record);
    /**
     * 
     * @Description 问题整改措施列表
     * @CreateTime 2018年1月11日 下午3:25:18
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
    Map<String, Object> getList(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 获取数据
     * @CreateTime 2018年1月12日 上午10:25:17
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
    AuditDiscoveryDetail getRecord(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 修改数据
     * @CreateTime 2018年1月12日 上午11:47:07
     * @CreateBy 岳彬彬
     * @param record
     * @throws BusinessException
     */
    String updateRecord(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 反馈
     * @CreateTime 2018年1月15日 上午10:39:21
     * @CreateBy 岳彬彬
     * @param record
     * @throws BusinessException
     */
    void update4Feedback(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 批准
     * @CreateTime 2018年1月17日 上午11:50:39
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
    String update4Approve(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 整改措施评估
     * @CreateTime 2018年1月17日 下午4:37:49
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
    Map<String, Object> queryReticList(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 验证提交
     * @CreateTime 2018年1月18日 上午9:36:29
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
    String update4Valid(AuditDiscoveryDetail record) throws BusinessException;
    /**
     * 
     * @Description 关闭
     * @CreateTime 2018年1月18日 上午11:57:36
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
    String update4Close(AuditDiscoveryDetail record) throws BusinessException;
}
