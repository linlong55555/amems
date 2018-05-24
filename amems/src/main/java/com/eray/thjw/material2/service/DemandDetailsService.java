package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandDetails;

/**
 * 
 * @Description 需求明细service
 * @CreateTime 2018年2月26日 下午3:23:48
 * @CreateBy 林龙
 */
public interface DemandDetailsService {

	/**
	 * 
	 * @Description 需求跟踪-查询需求信息
	 * @CreateTime 2018年2月27日 下午2:37:00
	 * @CreateBy 林龙
	 * @param demandDetails
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> queryAllPageList(DemandDetails demandDetails)throws BusinessException;

	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年2月28日 上午11:04:11
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	void updateClose(DemandDetails demandDetails)throws BusinessException;

	List<DemandDetails> queryDemandInfoList(DemandDetails demandDetails)throws BusinessException;

	List<DemandDetails> queryDemandProtectionInfoList(DemandDetails demandDetails)throws BusinessException;

}
