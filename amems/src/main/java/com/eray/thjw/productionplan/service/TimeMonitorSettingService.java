package com.eray.thjw.productionplan.service;

import java.util.List;

import com.eray.thjw.productionplan.po.TimeMonitorSetting;

import enu.LogOperationEnum;

public interface TimeMonitorSettingService {

	 List<TimeMonitorSetting> selectEditable(TimeMonitorSetting setting)throws RuntimeException;             //查询时控件设置-编辑区
	 
	 void saveEditable(List<TimeMonitorSetting> settings, String zjqdid, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;             //保存时控件设置-编辑区
	 
	 /**
	  * 删除时控件设置-编辑区
	  * @param zjqdid
	  */
	 void deleteEditable(String zjqdid, String czls, LogOperationEnum logOperationEnum);
	 
	 /**
	  * 级联删除时控件设置-编辑区
	  * @param zjqdid
	  */
	 void cascadeDeleteEditable(String zjqdid, String czls, LogOperationEnum logOperationEnum);
	 
}
