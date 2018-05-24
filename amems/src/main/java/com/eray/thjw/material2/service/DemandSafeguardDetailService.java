package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.DemandSafeguardDetail;

/** 
 * @Description 
 * @CreateTime 2018-2-26 下午3:19:37
 * @CreateBy 孙霁	
 */
public interface DemandSafeguardDetailService {

	/**
	 * 
	 * @Description 根据查询条件查询需求清单
	 * @CreateTime 2018-2-27 下午3:10:33
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @return
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAll(DemandSafeguardDetail demandSafeguardDetail)throws BusinessException ;
	
	/**
	 * @Description 查询需求统计分析
	 * @CreateTime 2018-4-3 下午5:10:16
	 * @CreateBy 刘兵
	 * @param demandSafeguardDetail
	 * @return
	 * @throws BusinessException
	 */
	public List<DemandSafeguardDetail> queryAnalysisList(DemandSafeguardDetail demandSafeguardDetail)throws BusinessException ;
	
	/**
	 * 
	 * @Description 批量处理
	 * @CreateTime 2018-2-28 下午5:05:41
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @throws BusinessException
	 */
	public void updateBatch(DemandSafeguardDetail demandSafeguardDetail)throws BusinessException ;
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2018-3-1 上午10:39:06
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @throws BusinessException
	 */
	public List<DemandSafeguardDetail> getDemandSafeguardDetailList(DemandSafeguardDetail demandSafeguardDetail)throws BusinessException ;
}
