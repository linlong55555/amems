package com.eray.thjw.training.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.YearPlan;


public interface YearPlanService {
	 
	/**
	 * @author liub
	 * @description 新增或修改年度计划
	 * @param yearPlan
	 * @return id
	 * @throws BusinessException 
	 */
	public String addOrUpdate(YearPlan yearPlan);
	
	/**
	 * @author liub
	 * @description 根据年度、机构代码查询年度计划、附件数
	 * @param nd、dprtcode
	 * @return YearPlan
	 */
	public YearPlan selectByNdAndDprt(Integer nd,String dprtcode);
}
