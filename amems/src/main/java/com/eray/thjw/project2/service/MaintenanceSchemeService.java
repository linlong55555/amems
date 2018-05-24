package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaintenanceScheme;

/**
 * @Description 维修方案service
 * @CreateTime 2017年8月16日 下午2:35:22
 * @CreateBy 韩武
 */
public interface MaintenanceSchemeService {
	
	/**
	 * @Description 根据飞机机型查询维修方案版本
	 * @CreateTime 2017年8月16日 下午2:35:31
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	List<MaintenanceScheme> queryByFjjx(MaintenanceScheme scheme);
	
	/**
	 * @Description 保存维修方案
	 * @CreateTime 2017年8月16日 下午2:35:37
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 */
	void doSave(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 提交维修方案
	 * @CreateTime 2017年8月16日 下午2:35:42
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 */
	void doSubmit(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 改版维修方案
	 * @CreateTime 2017年8月16日 下午2:35:48
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 */
	void doRevision(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 提交生产维修方案
	 * @CreateTime 2017年8月16日 下午2:35:55
	 * @CreateBy 韩武
	 * @param scheme
	 */
	void doSubmitProduction(MaintenanceScheme scheme) throws BusinessException;
	
	/**
	 * @Description 维修方案审核通过
	 * @CreateTime 2017年8月21日 下午5:06:41
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	void doAuditAgree(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 维修方案审核驳回
	 * @CreateTime 2017年8月21日 下午5:06:41
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	void doAuditReject(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 维修方案审批通过
	 * @CreateTime 2017年8月21日 下午5:06:50
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	void doApproveAgree(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 维修方案审批驳回
	 * @CreateTime 2017年8月21日 下午5:06:50
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	void doApproveReject(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 生产确认维修方案
	 * @CreateTime 2017年8月21日 下午5:07:03
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	void doConfirmProduction(MaintenanceScheme scheme) throws SaibongException, BusinessException;
	
	/**
	 * @Description 查询待审核维修方案
	 * @CreateTime 2017年8月22日 上午10:58:17
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	List<MaintenanceScheme> queryAuditList(MaintenanceScheme scheme);
	
	/**
	 * @Description 查询待批准维修方案
	 * @CreateTime 2017年8月22日 上午10:58:29
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	List<MaintenanceScheme> queryApproveList(MaintenanceScheme scheme);
	
	/**
	 * @Description 查询待生效维修方案
	 * @CreateTime 2017年8月22日 上午10:58:37
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	List<MaintenanceScheme> queryEffectList(MaintenanceScheme scheme);

	/**
	 * 
	 * @Description 根据id查询维修方案
	 * @CreateTime 2017年8月25日 上午10:29:03
	 * @CreateBy 林龙
	 * @param zlid 维修方案id
	 * @return 维修方案
	 * @throws BusinessException
	 */
	public MaintenanceScheme selectByPrimaryKey(String zlid)throws BusinessException;
	
	/**
	 * @Description 查询差异数据
	 * @CreateTime 2017年8月28日 下午3:31:49
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	Map<String, MaintenanceScheme> queryDifferenceData(MaintenanceScheme scheme);
	
	/**
	 * @Description 查询维修方案版本历史版本
	 * @CreateTime 2017年8月30日 上午9:18:56
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	List<MaintenanceScheme> queryVersionList(MaintenanceScheme scheme);
	
	/**
	 * @Description 查询维修方案详情
	 * @CreateTime 2017年9月12日 下午5:07:35
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	MaintenanceScheme queryDetail(String id);
}
