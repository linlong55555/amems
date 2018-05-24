package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.material2.po.ToolBorrowReturnRecord;


/**
 * @Description 工具借用/归还记录service
 * @CreateTime 2018年4月3日 下午5:38:03
 * @CreateBy 韩武
 */
public interface ToolBorrowReturnRecordService {
	
	/**
	 * @Description 查询工具/设备借归记录分页列表
	 * @CreateTime 2018年4月3日 下午5:39:07
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryPageableList(ToolBorrowReturnRecord record);
	
	/**
	 * @Description 查询工具/设备借归记录列表
	 * @CreateTime 2018年4月3日 下午5:39:07
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<ToolBorrowReturnRecord> queryList(ToolBorrowReturnRecord record);
}
