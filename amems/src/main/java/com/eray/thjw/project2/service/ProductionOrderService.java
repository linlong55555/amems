package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.framework.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.ProductionOrder;

/**
 * @Description 生产指令接口
 * @CreateTime 2018年5月3日 下午3:54:56
 * @CreateBy 韩武
 */
public interface ProductionOrderService {
	
	/**
	 * @Description 查询生产指令分页列表
	 * @CreateTime 2018年5月4日 上午10:27:07
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	Map<String, Object> queryPageableList(ProductionOrder order);
	
	/**
	 * @Description 查询生产指令详情
	 * @CreateTime 2018年5月7日 上午9:45:10
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	ProductionOrder queryDetail(String id);

	/**
	 * @Description 保存生产指令
	 * @CreateTime 2018年5月3日 下午3:56:12
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	String doSave(ProductionOrder order) throws BusinessException, SaibongException;
	
	/**
	 * @Description 提交生产指令
	 * @CreateTime 2018年5月7日 下午2:37:41
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 * @throws SaibongException
	 */
	String doSubmit(ProductionOrder order) throws BusinessException, SaibongException;
	
	/**
	 * @Description 生产指令审核通过
	 * @CreateTime 2018年5月7日 下午3:46:25
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	void doAuditAgree(ProductionOrder order) throws BusinessException;
	
	/**
	 * @Description 生产指令审核驳回
	 * @CreateTime 2018年5月7日 下午3:46:35
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	void doAuditReject(ProductionOrder order) throws BusinessException;
	
	/**
	 * @Description 生产指令审批通过
	 * @CreateTime 2018年5月7日 下午3:46:45
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	void doApproveAgree(ProductionOrder order) throws BusinessException;
	
	/**
	 * @Description 生产指令审批驳回
	 * @CreateTime 2018年5月7日 下午3:46:54
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	void doApproveReject(ProductionOrder order) throws BusinessException;
	
	/**
	 * @Description 生产指令改版保存
	 * @CreateTime 2018年5月8日 上午9:59:57
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	void doRevisionSave(ProductionOrder order) throws BusinessException, SaibongException;
	
	/**
	 * @Description 生产指令改版提交
	 * @CreateTime 2018年5月8日 上午10:00:19
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	void doRevisionSubmit(ProductionOrder order) throws BusinessException, SaibongException;
	
	/**
	 * @Description 查询生产指令版本历史
	 * @CreateTime 2018年5月8日 上午11:45:37
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	List<ProductionOrder> queryVersionList(ProductionOrder order);
	
	/**
	 * @Description 查询生产指令历史版本
	 * @CreateTime 2018年5月8日 下午1:25:25
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	List<ProductionOrder> queryHistoryList(ProductionOrder order);
	
	/**
	 * @Description 删除生产指令
	 * @CreateTime 2018年5月8日 下午3:25:40
	 * @CreateBy 韩武
	 * @param id
	 */
	void doDelete(String id) throws BusinessException;
	
	/**
	 * @Description 关闭生产指令
	 * @CreateTime 2018年5月8日 下午3:28:37
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	void doClose(String id) throws BusinessException;
	
	/**
	 * @Description 启用生产指令
	 * @CreateTime 2018年5月8日 下午3:28:44
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	void doOpen(String id) throws BusinessException;
}
