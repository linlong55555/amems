package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.Workorder;

/** 
 * @Description 
 * @CreateTime 2017-10-9 上午11:29:16
 * @CreateBy 孙霁	
 */
public interface TaskhistoryService {

	/**
	 * 
	 * @Description 条件查询历史任务主列表
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryAll(Workorder workorder);
	
	/**
	 * 
	 * @Description 根据工单id获取拆换件记录
	 * @CreateTime 2017-10-10 下午3:50:04
	 * @CreateBy 孙霁
	 * @param mainid
	 * @return
	 */
	List<WorkOrderIORecord> queryAllRecordByGdid(String mainid);
	
	/**
	 * 
	 * @Description 根据gdid获取工单主数据
	 * @CreateTime 2017-10-10 下午5:49:09
	 * @CreateBy 孙霁
	 * @param mainid
	 * @return
	 */
	Workorder selectGdBygdid(String gdid);

	/**
	 * @Description 根据145工单id获取拆换件记录
	 * @CreateTime 2017-10-10 下午3:50:04
	 * @CreateBy 雷伟
	 * @param gdid 145工单ID
	 * @return
	 */
	public List<WorkOrderIORecord> queryAllRecord145ByGdid(String gdid);
	
	/**
	 * 
	 * @Description 获取导出数据
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	public List<Workorder> getList(Workorder workorder);
}
