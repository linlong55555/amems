package com.eray.thjw.outfield.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.outfield.po.ToolsMonitor;
import com.eray.thjw.outfield.po.ToolsMonitorDetail;
import com.eray.thjw.po.BaseEntity;



public interface ToolsMonitorService {
	
	/**
	 * @author liub
	 * @description  新增计量工具监控数据
	 * @param toolsMonitorDetail
	 * @return Map<String, Object>
	 * @develop date 2016.12.01
	 */
	public Map<String, Object> addToolsMonitor(ToolsMonitorDetail toolsMonitorDetail)throws BusinessException;
	
	/**
	 * @author liub
	 * @description  新增计量工具监控详情数据
	 * @param toolsMonitorDetail
	 * @return Map<String, Object>
	 * @develop date 2016.12.06
	 */
	public Map<String, Object> addToolsMonitorDetail(ToolsMonitorDetail toolsMonitorDetail) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  批量新增计量工具监控详情数据
	 * @param toolsMonitor
	 * @return Map<String, Object>
	 * @develop date 2016.12.15
	 */
	public Map<String, Object> addToolsMonitorDetailList(ToolsMonitor toolsMonitor) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  根据mainid、编号删除计量工具监控详情数据
	 * @param toolsMonitorDetail
	 * @return Map<String, Object>
	 * @develop date 2016.12.06
	 */
	public Map<String, Object> deleteDetailByMainIdAndBjxlh(ToolsMonitorDetail toolsMonitorDetail);
	
	/**
	 * @author liub
	 * @description  根据detailId删除计量工具监控详情
	 * @param detailId
	 * @return Map<String, Object>
	 * @develop date 2016.12.14
	 */
	public Map<String, Object> deleteDetail(String detailId,String mainid);
	
	/**
	 * @author liub
	 * @description  根据条件分页查询库存及外场虚拟库存列表
	 * @param toolsMonitor
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.30
	 */
	public List<Map<String, Object>> queryAllStockPageList(ToolsMonitor toolsMonitor);
	
	/**
	 * @author liub
	 * @description  根据条件分页查询计量监控列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.15
	 */
	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity);
	
	/**
	 * @author liub
	 * @description  根据计量工具id查询详情信息(最新检查日期)
	 * @param mainid
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.06
	 */
	public List<ToolsMonitorDetail> queryDetailByMainId(String mainid);
	
	/**
	 * @author liub
	 * @description  根据计量工具id,编号查询历史详情信息
	 * @param mainid,bjxlh
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.14
	 */
	public List<ToolsMonitorDetail> queryDetailByMainIdAndBjxlh(String mainid,String bjxlh);
}
