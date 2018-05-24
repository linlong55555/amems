package com.eray.thjw.produce.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.PlanInit;
import com.eray.thjw.produce.po.PlanUsage;

import enu.LogOperationEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-21 上午11:57:58
 * @CreateBy 孙霁	
 */
public interface PlanUsageService{

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 下午2:54:21
	 * @CreateBy 孙霁
	 * @param PlanUsageList
	 * @param fjzch
	 * @param dprtcode
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 * @throws BusinessException
	 */
	public void add(List<PlanUsage> planUsageList, String fjzch, String dprtcode,
			UpdateTypeEnum type, String czls, LogOperationEnum logOperationEnum) throws BusinessException ;
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-21 下午2:54:21
	 * @CreateBy 孙霁
	 * @param PlanUsageList
	 * @param fjzch
	 * @param dprtcode
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 * @throws BusinessException
	 */
	public void edit(List<PlanUsage> planUsageList, String fjzch, String dprtcode,
			UpdateTypeEnum type, String czls, LogOperationEnum logOperationEnum) throws BusinessException ;
}
