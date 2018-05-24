package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;

import enu.LogOperationEnum;


public interface ScheduledCheckMonitorItemService {
	
	public List<ScheduledCheckMonitorItem> queryAllList(Map<String, Object> map) throws Exception;
	
	public List<ScheduledCheckMonitorItem> queryAllsj(Map<String, Object> map) throws Exception;
	
	//public int queryCount(ScheduledCheckItem scheduledCheckItem) throws Exception;
	
	void saveEditable(List<ScheduledCheckMonitorItem> list, String mainid, String zjqdid, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;		//编辑区-保存监控项目
	
	void deleteEditable(String mainid, String czls, LogOperationEnum logOperationEnum, String zjqdid, boolean writeRec) throws RuntimeException;		//编辑区-删除监控项目
	
	void deleteByZjqdid(String zjqdid, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;		//编辑区-根据装机清单id删除监控项目
	
	void cascadeDeleteByZjqdid(String zjqdid, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;		//编辑区-级联删除监控项目
	
}
