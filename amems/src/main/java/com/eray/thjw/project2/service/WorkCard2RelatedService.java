package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.project2.po.WorkCard2Related;

import enu.LogOperationEnum;

/**
 * @Description 工卡-关联工卡service
 * @CreateTime 2017-8-25 下午5:34:05
 * @CreateBy 刘兵
 */
public interface WorkCard2RelatedService {
	
	/**
	 * @Description 保存多个工卡-关联工卡
	 * @CreateTime 2017-8-25 下午5:41:24
	 * @CreateBy 刘兵
	 * @param workCard2RelatedList 工卡-关联工卡集合
	 * @param mainid 父表单id
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	void saveWorkCard2RelatedList(List<WorkCard2Related> workCard2RelatedList, String mainid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 编辑多个工卡-关联工卡
	 * @CreateTime 2017-8-25 下午5:41:24
	 * @CreateBy 刘兵
	 * @param workCard2RelatedList 工卡-关联工卡集合
	 * @param mainid 父表单id
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	void updateWorkCard2RelatedList(List<WorkCard2Related> workCard2RelatedList, String mainid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 根据mainid删除工卡-关联工卡
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	void deleteByMainid(String mainid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 根据条件查询工卡-关联工卡列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param WorkCard2Related 工卡-关联工卡
	 * @return List<WorkCard2Related> 工卡-关联工卡集合
	 */
	List<WorkCard2Related> queryAllList(WorkCard2Related workCard2Related);
	
}
