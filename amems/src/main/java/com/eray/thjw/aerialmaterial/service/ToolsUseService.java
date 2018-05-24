package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ToolsUse;

/**
 * 工具，设备借用服务接口
 * @author zhuchao
 *
 */
public interface ToolsUseService {
	
	/**
	 * 借出申请
	 * @param record
	 */
	public Map<String, Object> doApply(ToolsUse record);
	
	/**
	 * 借出确认
	 * @param record
	 */
	public Map<String, Object> doConfirm(ToolsUse record);
	
	/**
	 * 归还
	 * @param record
	 */
	public Map<String, Object> doReturn(ToolsUse record);

	/**
	 * 指定结束
	 * @param record
	 */
	public Map<String, Object> doEnd(ToolsUse record);

	/**
	 * 查询工具，设备借出列表
	 * @param record
	 * @return
	 */
	public List<ToolsUse> queryList(ToolsUse record);

	/**
	 * 快速借出确认
	 * @param record
	 */
	public Map<String, Object> doConfirmFast(ToolsUse record);
}
