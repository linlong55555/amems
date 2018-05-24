package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.MonitorItem;


public interface MonitorItemService {
	
	/**
	 * @author liub
	 * @description 根据定检项目id查询监控项目数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<Map<String, Object>> queryListByDjxmid(String djxmid) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 新增定检监控项目集合
	 * @param monitorItemList,djxmid
	 * @develop date 2016.08.24
	 */
	public void addMonitorItemList(List<MonitorItem> monitorItemList , String djxmid,String czls, String zdid, String dprtcode) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 修改定检监控项目集合
	 * @param monitorItemList,djxmid
	 * @develop date 2016.08.29
	 */
	public void editMonitorItemList(List<MonitorItem> monitorItemList , String djxmid,String czls,String zdid, String dprtcode) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 根据定检编号删除监控项目
	 * @param 定检编号djbh
	 * @develop date 2016.08.24
	 */
	public void deleteByDjxmid(String djxmid) throws RuntimeException;
	
}
