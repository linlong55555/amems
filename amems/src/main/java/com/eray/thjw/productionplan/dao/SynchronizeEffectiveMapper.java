package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;


public interface SynchronizeEffectiveMapper {
	
	/**
	 * 获取未完成的时控件数据
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findUnfinishedTimeMonitorData(PlaneData pd);
	
	/**
	 * 获取未完成的定检件数据
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findUnfinishedFixedMonitorData(PlaneData pd);
	
	/**
	 * 获取未完成的时控件计划
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findUnfinishedTimeMonitorPlan(PlaneData pd);
	
	/**
	 * 获取未完成的定检件计划
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findUnfinishedFixedMonitorPlan(PlaneData pd);
	
	/**
	 * 获取废弃的定检监控项目
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findScrapFixedMonitor(PlaneData pd);
	
	/**
	 * 获取未配置完全的定检监控项目（由于升版导致）
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findNotFinishedFixedMonitor(PlaneData pd);
	
	/**
	 * 获取未关联部件
	 * @param fjzch
	 * @return
	 */
	List<LoadingList> findNoParentData(PlaneData pd);
	
	/**
	 * 同步装机清单数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeLoadingList(PlaneData pd);
	
	/**
	 * 更新装机清单编辑区的同步标识
	 * @param fjzch
	 * @return
	 */
	int updateLoadingListTbbs(PlaneData pd);
	
	/**
	 * 同步时控件监控设置数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeTimeMonitor(PlaneData pd);
	
	/**
	 * 同步时控件监控设置中的删除数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeTimeMonitorDeleteData(PlaneData pd);
	
	/**
	 * 更新时控件监控设置编辑区的同步标识
	 * @param fjzch
	 * @return
	 */
	int updateTimeMonitorTbbs(PlaneData pd);
	
	/**
	 * 同步特殊飞行设置数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeSpecialFlightSetting(PlaneData pd);
	
	/**
	 * 同步特殊飞行设置中的删除数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeSpecialFlightSettingDeleteData(PlaneData pd);
	
	/**
	 * 更新特殊飞行设置编辑区的同步标识
	 * @param fjzch
	 * @return
	 */
	int updateSpecialFlightSettingTbbs(PlaneData pd);
	
	/**
	 * 同步定检项目数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeFixedItem(PlaneData pd);
	
	/**
	 * 更新定检项目编辑区的同步标识
	 * @param fjzch
	 * @return
	 */
	int updateFixedItemTbbs(PlaneData pd);
	
	/**
	 * 同步定检监控项数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeFixedMonitor(PlaneData pd);
	
	/**
	 * 同步定检监控项数据中的删除数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeFixedMonitorDeleteData(PlaneData pd);
	
	/**
	 * 更新定检监控项数据编辑区的同步标识
	 * @param fjzch
	 * @return
	 */
	int updateFixedMonitorTbbs(PlaneData pd);
	
	/**
	 * 同步定检件监控计划数据
	 * @param paramMap
	 * @return
	 */
	int synchronizeFixedMonitorPlan(Map<String, Object> paramMap);
	
	/**
	 * 同步定检件监控计划数据中的删除数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeFixedMonitorPlanDeleteData(PlaneData pd);
	
	/**
	 * 同步航材主数据
	 * @param paramMap
	 * @return
	 */
	int synchronizeAerialMaterialMainData(Map<String, Object> paramMap);
	
	/**
	 * 同步部件数据
	 * @param fjzch
	 * @return
	 */
	int synchronizeComponent(PlaneData pd);		
	
	/**
	 * 同步部件拆解记录
	 * @param fjzch
	 * @return
	 */
	int synchronizeComponentDisassembleRecord(PlaneData pd);
	
	/**
	 * 同步部件使用情况
	 * @param fjzch
	 * @return
	 */
	int synchronizeComponentUsage(Map<String, Object> paramMap);
	
	/**
	 * 删除无效的定检项目
	 * @param fjzch
	 */
	void deleteInvalidFixedItem(PlaneData pd);
	
	/**
	 * 所有部件的使用情况
	 * @param fjzch
	 * @return
	 */
	List<ComponentUsage> getComponentUsage(PlaneData pd);
	
	/**
	 * 根据部件拆解记录，判断该部件是否曾经装过飞机
	 * @param usage
	 * @return
	 */
	boolean isEverDisassembled(ComponentUsage usage);
	
}