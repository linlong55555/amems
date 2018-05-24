package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ScrappedApply;

/**
 * 
 * @Description 报废申请service
 * @CreateTime 2018年3月22日 上午11:27:10
 * @CreateBy 岳彬彬
 */
public interface ScrappedApplyService {
	/**
	 * 
	 * @Description 报废申请主列表
	 * @CreateTime 2018年3月22日 下午2:30:17
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getScrappedApplyList(ScrappedApply record) throws BusinessException;
	/**
	 * 
	 * @Description 获取报废申请明细信息
	 * @CreateTime 2018年3月22日 下午2:28:39
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	Map<String, Object> getListById(ScrappedApply record);
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年3月22日 下午5:18:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String addRecord(ScrappedApply record) throws BusinessException;
	/**
	 * 
	 * @Description 根据id获取报废数据
	 * @CreateTime 2018年3月23日 下午3:32:44
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	ScrappedApply getRecord(String id) throws BusinessException;
	/**
	 * 
	 * @Description 更新
	 * @CreateTime 2018年3月23日 下午4:29:02
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String updateRecrod(ScrappedApply record) throws BusinessException;
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018年3月23日 下午5:54:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void deleteRecord(ScrappedApply record) throws BusinessException;
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年3月26日 上午11:45:56
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String updateRecord4Close(ScrappedApply record) throws BusinessException;
	/**
	 * 
	 * @Description 报废审核列表
	 * @CreateTime 2018年3月26日 下午2:07:23
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getAuditList(ScrappedApply record) throws BusinessException;
	/**
	 * 
	 * @Description 审核
	 * @CreateTime 2018年3月27日 上午9:57:56
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String update4Audit(ScrappedApply record) throws BusinessException;
}
