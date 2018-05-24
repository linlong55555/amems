package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;

import enu.LogOperationEnum;

public interface LoadingListService {

	List<LoadingList> selectLoadingList(LoadingList ll) throws RuntimeException; // 根据指定条件查询件号和SN序列号

	LoadingList selectEditableByPrimaryKey(LoadingList ll) throws RuntimeException; // 根据主键查询-编辑区

	void insertPlaneEditable(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException; // 插入编辑区的飞机装机清单

	void editPlaneEditable(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException; // 插入编辑区的飞机装机清单

	List<LoadingList> selectEditableInTree(LoadingList ll) throws RuntimeException; // 飞机装机清单查询-树状

	Map<String, Object> selectEditableInTable(LoadingList ll) throws RuntimeException; // 飞机装机清单查询-表状

	Map<String, Object> selectInTable(LoadingList ll) throws RuntimeException; // 飞机装机清单查询-表状-生效区

	Map<String, Object> queryNoChildList(LoadingList ll) throws RuntimeException; // 查询指定飞机无且无子部件的零件清单
	
	Map<String, Object> queryEffectiveNoChildList(LoadingList ll) throws RuntimeException; // 查询指定飞机无且无子部件的零件清单

	void doChangeParent(List<LoadingList> list) throws RuntimeException; // 改变父节点（维护子部件关系）

	String doInsertOrUpdateEditable(LoadingList ll) throws RuntimeException; // 新增或修改编辑区的装机清单

	void doScrapEditable(LoadingList ll) throws RuntimeException; // 作废部件

	void doCascadeScrapEditable(LoadingList ll) throws RuntimeException; // 作废部件

	LoadingList selectConutKey(LoadingList ll);// 查询

	List<LoadingList> queryEditableTimeMonitor(LoadingList ll) throws RuntimeException; // 查询时控件-编辑区

	void modifyTimeMonitorEditable(LoadingList ll) throws RuntimeException; // 修改时控件设置

	List<LoadingList> queryEditableFixedMonitor(LoadingList ll) throws RuntimeException; // 查询定检件-编辑区

	List<TimeMonitorSetting> getTcAndTv(LoadingList ll) throws RuntimeException; // 根据时控件表和定检件表查询tc和tv

	void saveFixMonitorEditable(ScheduledCheckItem item) throws RuntimeException; // 保存定检监控项目

	void deleteFixMonitorEditable(ScheduledCheckItem item) throws RuntimeException; // 删除定检监控项目

	void updateFixMonitorEditable(ScheduledCheckItem item) throws RuntimeException; // 修改定检监控项目

	List<LoadingList> findByJhAndXlh(LoadingList ll) throws RuntimeException; // 根据件号和序列号查询装机清单数据

	void updatePlaneEditable(LoadingList loadingList) throws RuntimeException; // 修改生效区的飞机装机清单

	void updatePlaneEditables(LoadingList loadingList) throws RuntimeException; // 修改生效区的飞机装机清单

	List<LoadingList> queryList(LoadingList loadingList); // 根据条件查询
	
	List<LoadingList> selectEffectiveInTree(LoadingList ll) throws RuntimeException; // 飞机装机清单查询-树状
	
	Map<String, Object> sumComponentUsageIfEverDisassembled(LoadingList loadingList);	// 如果曾经拆下则汇总部件使用情况
	
	List<LoadingList> queryByParam(LoadingList loadingList);	// 根据条件查询
	
	List<LoadingList> queryEditableParentTree(LoadingList ll);	// 飞机装机清单父节点查询-树状
	
	/**
	 * 删除飞机的装机清单数据
	 * @param pd
	 * @param czls
	 * @param logOperationEnum
	 */
	void deletePlaneEditable(PlaneData pd, String czls, LogOperationEnum logOperationEnum);
	
	List<LoadingList> getPart(LoadingList ll);
}
