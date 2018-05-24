package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.project2.po.ApplicableUnit;

import enu.LogOperationEnum;

/**
 * @Description 工卡-适用单位service
 * @CreateTime 2017-11-8 下午3:16:18
 * @CreateBy 刘兵
 */
public interface ApplicableUnitService {
	
	/**
	 * @Description 保存多个工卡-适用单位
	 * @CreateTime 2017-11-8 下午3:16:18
	 * @CreateBy 刘兵
	 * @param ApplicableUnitList 工卡-适用单位集合
	 * @param mainid 父ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	void saveApplicableUnitList(List<ApplicableUnit> applicableUnitList, String mainid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 编辑多个工卡-适用单位
	 * @CreateTime 2017-11-8 下午3:16:18
	 * @CreateBy 刘兵
	 * @param ApplicableUnitList 工卡-适用单位集合
	 * @param mainid 父ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	void updateApplicableUnitList(List<ApplicableUnit> applicableUnitList, String mainid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 根据mainid删除工卡-适用单位
	 * @CreateTime 2017-11-8 下午3:16:18
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	void deleteByMainid(String mainid, String czls, String zdid, LogOperationEnum logopration);
}
