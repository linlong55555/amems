package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Demand;


/**
 * 
 * @Description 需求service
 * @CreateTime 2018年2月26日 下午3:23:48
 * @CreateBy 林龙
 */
public interface DemandService {

	/**
	 * 
	 * @Description 需求跟踪-需求列表
	 * @CreateTime 2018年2月26日 下午3:27:57
	 * @CreateBy 林龙
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	public List<Demand> queryAllList(Demand demand)throws BusinessException;

	/**
	 * 
	 * @Description 需求跟踪-已关闭需求列表
	 * @CreateTime 2018年2月28日 上午9:49:14
	 * @CreateBy 林龙
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllClosePageList(Demand demand)throws BusinessException;

	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018年2月28日 上午10:04:43
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	public void delete(Demand demand)throws BusinessException;

	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年2月28日 上午10:19:46
	 * @CreateBy 林龙
	 * @param demand
	 */
	public void updateClose(Demand demand)throws BusinessException;

	/**
	 * 
	 * @Description 审批通过/审批驳回
	 * @CreateTime 2018年3月2日 上午10:36:45
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	public void updateSubApprove(Demand demand)throws BusinessException;

	/**
	 * 
	 * @Description 出库-需求保障信息
	 * @CreateTime 2018年3月19日 下午5:07:19
	 * @CreateBy 林龙
	 * @param demand
	 * @return
	 */
	public Map<String, Object> queryAllDemandpprotectionPageList(Demand demand);


}
