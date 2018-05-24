package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.project2.po.WorkHour;

import enu.LogOperationEnum;

/**
 * @Description 工种工时service
 * @CreateTime 2017-8-19 下午2:57:09
 * @CreateBy 刘兵
 */
public interface WorkHourService {
	
	/**
	 * @Description 保存多个工种工时
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param workHourList 工种工时集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void saveWorkHourList(List<WorkHour> workHourList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 编辑多个工种工时
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param workHourList 工种工时集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void updateWorkHourList(List<WorkHour> workHourList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 根据ywid删除工种工时
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 根据条件查询工种工时列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workHour 工种工时
	 * @return List<WorkHour> 工种工时集合
	 */
	List<WorkHour> queryAllList(WorkHour workHour);
}
