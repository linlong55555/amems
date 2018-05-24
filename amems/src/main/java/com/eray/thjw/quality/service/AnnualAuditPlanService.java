package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AnnualAuditPlan;

/**
 * 
 * @Description 年度审核计划service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface AnnualAuditPlanService {
	
	/**
	 * 
	 * @Description 年度审核计划分页查询列表
	 * @CreateTime 2018年1月8日 下午2:14:29
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(AnnualAuditPlan annualAuditPlan)throws BusinessException ;

	/**
	 * 
	 * @Description 新增年度审核计划
	 * @CreateTime 2018年1月8日 下午3:56:04
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	public String save(AnnualAuditPlan annualAuditPlan)throws BusinessException;

	/**
	 * 
	 * @Description 根据id查询年度审核计划数据
	 * @CreateTime 2018年1月9日 下午2:14:38
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	public AnnualAuditPlan getById(AnnualAuditPlan annualAuditPlan)throws BusinessException;

	/**
	 * 
	 * @Description 修改年度审核计划
	 * @CreateTime 2018年1月9日 下午2:35:48
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	public String update(AnnualAuditPlan annualAuditPlan)throws BusinessException;

	public String delete(AnnualAuditPlan annualAuditPlan)throws BusinessException;
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-1-22 下午2:33:52
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<AnnualAuditPlan>
	 */
	List<AnnualAuditPlan> doExportExcel(AnnualAuditPlan paramObj);
	
	
}
