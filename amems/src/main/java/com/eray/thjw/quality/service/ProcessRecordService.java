package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.quality.po.ProcessRecord;

/**
 * 
 * @Description 流程记录service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 岳彬彬
 */
public interface ProcessRecordService {

	/**
	 * @Description 查询流程记录集合
	 * @CreateTime 2018年1月11日 下午2:23:49
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<ProcessRecord> queryList(ProcessRecord record);
	
	/**
	 * 
	 * @Description 新增流程记录
	 * @CreateTime 2018年1月11日 上午10:09:17
	 * @CreateBy 岳彬彬
	 * @param mainid
	 * @param dprtcode
	 */
	void addRecord(String mainid,String dprtcode,String message);
	/**
	 * 
	 * @Description 批量新增
	 * @CreateTime 2018年1月15日 上午11:28:24
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param dprtcode
	 * @param message
	 */
	void addBatchRecord(List<String> idList, String dprtcode,String message);
	
	/**
	 * 
	 * @Description 根据mainid获取数据
	 * @CreateTime 2018-2-28 下午2:36:27
	 * @CreateBy 孙霁
	 * @param mainid
	 * @return
	 */
	List<ProcessRecord> selectByMainid(String mainid);
	/**
	 * 
	 * @Description 根据maind删除数据
	 * @CreateTime 2018年3月23日 下午5:59:56
	 * @CreateBy 岳彬彬
	 * @param mainid
	 */
	void deleteRecord(String mainid);
}
