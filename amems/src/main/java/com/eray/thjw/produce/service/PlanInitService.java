package com.eray.thjw.produce.service;

import java.util.List;

import com.eray.thjw.produce.po.PlanInit;

import enu.LogOperationEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-21 上午11:57:58
 * @CreateBy 孙霁	
 */
public interface PlanInitService {

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 下午12:01:59
	 * @CreateBy 孙霁
	 * @param planInitList
	 * @param fjzch
	 * @param jgdm
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 */
	public void add(List<PlanInit> planInitList, String fjzch, String jgdm,UpdateTypeEnum type, String czls,
			LogOperationEnum logOperationEnum);
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-21 下午12:01:59
	 * @CreateBy 孙霁
	 * @param planInitList
	 * @param fjzch
	 * @param jgdm
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 */
	public void edit(List<PlanInit> planInitList, String fjzch, String jgdm,UpdateTypeEnum type, String czls,
			LogOperationEnum logOperationEnum);
}
