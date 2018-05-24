package com.eray.thjw.flightdata.dao;
import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.flightdata.po.ReturnInstruction;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.productionplan.po.CheckingMonitoring;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlanTask;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.po.ScheduledCheckPlan;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;

public interface FlightRecordSheetEffectiveMapper {
	
	/**
	 * 对比差异-完成计划
	 * @param ids
	 * @return
	 */
	List<String> compareDifferenceInFinishedPlan(Map<String, Object> paramMap);
	
	/**
	 * 对比差异-拆下件
	 * @param ids
	 * @return
	 */
	List<Map<String, String>> compareDifferenceInDisassemblyComponent(Map<String, Object> paramMap);
	
	/**
	 * 对比差异-装上件
	 * @param ids
	 * @return
	 */
	List<Map<String, String>> compareDifferenceInMountComponent(Map<String, Object> paramMap);
	
	/**
	 * 根据飞行记录单id删除还原指令
	 * @param fxjldid
	 * @return
	 */
	int deleteReturnInstruction(String fxjldid);
	
	/**
	 * 批量插入还原指令
	 * @param list
	 * @return
	 */
	int insertReturnInstruction(List<ReturnInstruction> list);
	
	/**
	 * 根据飞行记录单id找到还原指令
	 * @param fxjldid
	 * @return
	 */
	List<ReturnInstruction> findReturnInstruction(String fxjldid);
	
	/**
	 * 验证拆下件
	 * @param zjqdid
	 * @return
	 */
	boolean validateCxj(String zjqdid);
	
	/**
	 * 验证装上件
	 * @param zjqdid
	 * @return
	 */
	boolean validateZsj(String zjqdid);
	
	/**
	 * 还原计划任务
	 * @param planTask
	 * @return
	 */
	int restorePlanTask(PlanTask planTask);
	
	/**
	 * 还原定检任务主表
	 * @param xggdid
	 * @return
	 */
	int restoreFixedCheckMain(String xggdid);
	
	/**
	 * 还原定检任务工单
	 * @param xggdid
	 * @return
	 */
	int restoreFixedCheckWorkOrder(String xggdid);
	
	/**
	 * 还原非例行任务
	 * @param xggdid
	 * @return
	 */
	int restoreNonRoutineTask(String xggdid);
	
	/**
	 * 还原EO任务
	 * @param xggdid
	 * @return
	 */
	int restoreEOTask(String xggdid);
	
	/**
	 * 还原生效区-装机清单的任务
	 * @param xggdid
	 * @return
	 */
	int restorePlanInLoadingList(PlanTask planTask);
	
	/**
	 * 还原生效区-定检件定检项目的任务
	 * @param xggdid
	 * @return
	 */
	int restorePlanInFixedCheck(PlanTask planTask);
	
	/**
	 * 删除计划任务监控数据快照
	 * @param xggdid
	 * @return
	 */
	int deletePlanTaskSnapshot(String mainid);
	
	/**
	 * 获取定检监控计划数据
	 * @param map
	 * @return
	 */
	ScheduledCheckPlan findScheduledCheckPlan(Map<String, Object> map);
	
	/**
	 * 更新定检监控计划数据-不参与计算
	 * @param scheduledCheckPlan
	 * @return
	 */
	int updateScheduledCheckPlanWhenNotCyjs(Map<String, Object> map);
	
	/**
	 * 找到上一次的定检监控计划数据
	 * @param scheduledCheckPlan
	 * @return
	 */
	List<String> findBeforeScheduledCheckPlan(ScheduledCheckPlan plan);
	
	/**
	 * 更新上一次的监控数据为参与计算
	 * @param scheduledCheckPlan
	 * @return
	 */
	int updatePreviousScheduledCheckPlan(String id);
	
	/**
	 * 更新初始的监控数据为参与计算
	 * @param scheduledCheckPlan
	 * @return
	 */
	int updateInitScheduledCheckPlan(ScheduledCheckPlan plan);
	
	/**
	 * 还原编辑区-装上件-装上件、拆下件都存在
	 * @return
	 */
	int restoreLoadingList1(Map<String, Object> paramMap);
	
	/**
	 * 还原编辑区-拆下件-装上件、拆下件都存在
	 * @return
	 */
	int restoreLoadingList2(Map<String, Object> paramMap);
	

	/**
	 * 还原编辑区-装上件子节点-装上件、拆下件都存在
	 * @return
	 */
	int restoreLoadingList3(Map<String, Object> paramMap);
	
	/**
	 * 还原生效区-装上件-装上件、拆下件都存在
	 * @return
	 */
	int restoreLoadingList4(Map<String, Object> paramMap);
	
	/**
	 * 还原生效区-拆下件-装上件、拆下件都存在
	 * @return
	 */
	int restoreLoadingList5(Map<String, Object> paramMap);
	

	/**
	 * 还原生效区-装上件子节点-装上件、拆下件都存在
	 * @return
	 */
	int restoreLoadingList6(Map<String, Object> paramMap);
	
	/**
	 * 还原编辑区-装上件-装上件存在、拆下件不存在
	 * @return
	 */
	int restoreLoadingList7(Map<String, Object> paramMap);
	

	/**
	 * 还原编辑区-装上件子节点-装上件存在、拆下件不存在
	 * @return
	 */
	int restoreLoadingList8(Map<String, Object> paramMap);
	
	/**
	 * 还原生效区-装上件-装上件存在、拆下件不存在
	 * @return
	 */
	int restoreLoadingList9(Map<String, Object> paramMap);
	

	/**
	 * 还原生效区-装上件子节点-装上件存在、拆下件不存在
	 * @return
	 */
	int restoreLoadingList10(Map<String, Object> paramMap);
	
	/**
	 * 还原编辑区-拆下件-装上件不存在、拆下件存在
	 * @return
	 */
	int restoreLoadingList11(Map<String, Object> paramMap);
	
	/**
	 * 还原生效区-拆下件-装上件不存在、拆下件存在
	 * @return
	 */
	int restoreLoadingList12(Map<String, Object> paramMap);
	
	/**
	 * 还原装上件定检监控计划
	 * @param zsjid
	 * @return
	 */
	int restoreZsjMonitorPlan(String zsjid);
	
	/**
	 * 还原拆下件定检监控计划
	 * @param cxjid
	 * @return
	 */
	int restoreCxjMonitorPlan(String cxjid);
	
	/**
	 * 还原装上件拆解记录
	 * @param zsjid
	 * @return
	 */
	int restoreZsjDissDisassemblyRecord(String zsjid);
	
	/**
	 * 还原拆下件拆解记录
	 * @param zsjid
	 * @return
	 */
	int restoreCxjDissDisassemblyRecord(Map<String, Object> map);
	
	/**
	 * 还原装上件外场库存
	 * @param zsjid
	 * @return
	 */
	int restoreZsjStock(Map<String, Object> map);
	
	/**
	 * 删除库存履历
	 * @param zsjid
	 * @return
	 */
	int deleteStockRecord(String kclvid);
	
	/**
	 * 清空装上件拆解记录-b_s_0060201
	 * @param cjjlid
	 * @return
	 */
	int clearZsjDissDisassemblyRecord(String cjjlid);
	
	/**
	 * 还原拆下件外场库存
	 * @param cxjid
	 * @return
	 */
	int restoreCxjStock(String wckcid);
	
	/**
	 * 清空拆下件拆解记录-b_s_0060201
	 * @param cjjlid
	 * @return
	 */
	int clearCxjDissDisassemblyRecord(String cjjlid);
	
	/**
	 * 根据飞行记录单删除部件使用情况
	 * @param fxjldid
	 * @return
	 */
	int deleteComponentUsage(String fxjldid);
	
	/**
	 * 根据飞行记录单航段查询对应的完成任务
	 * @return
	 */
	List<FlightRecordSheetToPlan> findFinishedTaskByHd(Map<String, Object> map);
	
	/**
	 * 完成计划任务
	 * @param planTask
	 * @return
	 */
	int finishPlanTask(PlanTask planTask);
	
	/**
	 * 完成定检任务主表
	 * @param xggdid
	 * @return
	 */
	int finishFixedCheckMain(Map<String, Object> paramMap);
	
	/**
	 * 完成定检任务工单
	 * @param xggdid
	 * @return
	 */
	int finishFixedCheckWorkOrder(Map<String, Object> paramMap);
	
	/**
	 * 完成非例行任务
	 * @param xggdid
	 * @return
	 */
	int finishNonRoutineTask(Map<String, Object> paramMap);
	
	/**
	 * 完成EO任务
	 * @param xggdid
	 * @return
	 */
	int finishEOTask(Map<String, Object> paramMap);
	
	/**
	 * 完成生效区-装机清单的任务
	 * @param xggdid
	 * @return
	 */
	int finishPlanInLoadingList(PlanTask planTask);
	
	/**
	 * 完成生效区-定检件定检项目的任务
	 * @param xggdid
	 * @return
	 */
	int finishPlanInFixedCheck(String xggdid);
	
	/**
	 * 获取飞行记录单相关信息
	 * @param rwid
	 * @param fxjldid
	 * @return
	 */
	Map<String, Object> getFlightRecordInfo(String rwid, String fxjldid);
	
	/**
	 * 获取监控计划值和实际值
	 * @param map
	 * @return
	 */
	Map<String, Object> getMonitorActualValue(Map<String, Object> map);
	
	/**
	 * 新增计划任务监控数据快照
	 * @param plantask
	 * @return
	 */
	int insertPlanTaskSnapshot(Map<String, Object> monitorData);
	
	/**
	 * 失效定检件监控计划
	 * @return
	 */
	int invalidScheduledCheckPlan(Map<String, Object> monitorData);
	
	/**
	 * 获取当前飞行记录单之后的飞行记录单-判断参与计算标识
	 * @param monitorData
	 * @return
	 */
	List<String> judgeCyjs(Map<String, Object> monitorData);
	
	/**
	 * 插入定检监控计划
	 * @param scheduledCheckPlan
	 * @return
	 */
	int insertScheduledCheckPlan(ScheduledCheckPlan scheduledCheckPlan);
	
	/**
	 * 找到飞行记录单航段对应的拆解记录
	 * @return
	 */
	List<Map<String, Object>> findFlightRecordSheetToDisassembly(Map<String, Object> paramMap);
	
	/**
	 * 失效编辑区拆下件
	 * @param cx_zjqdid
	 * @return
	 */
	int invalidLoadingListEditable(Map<String, Object> dataInfo);
	
	/**
	 * 失效生效区拆下件
	 * @param cx_zjqdid
	 * @return
	 */
	int invalidLoadingListEffective(Map<String, Object> dataInfo);
	
	/**
	 * 清除编辑区中拆下件的子节点的父节点id
	 * @param cx_zjqdid
	 * @return
	 */
	int clearFjdidEditable(Map<String, Object> dataInfo);
	
	/**
	 * 清除生效区中拆下件的子节点的父节点id
	 * @param cx_zjqdid
	 * @return
	 */
	int clearFjdidEffective(Map<String, Object> dataInfo);
	
	/**
	 * 将拆下件的定检监控计划的计算标识改为0
	 * @param cx_zjqdid
	 * @return
	 */
	int invalidScheduledCheckPlanByZjqdid(Map<String, Object> dataInfo);
	
	/**
	 * 判断是否存在该飞行记录单下的拆解记录
	 * @param paramMap
	 * @return
	 */
	boolean isExistDisassemblyRecord(Map<String, Object> paramMap);
	
	/**
	 * 更新拆下件的部件拆解记录-已存该飞行记录单下的记录时
	 * @param paramMap
	 * @return
	 */
	int updateDisassemblyRecordWhenExist(Map<String, Object> paramMap);
	
	/**
	 * 更新拆下件的部件拆解记录-不存该飞行记录单下的记录时
	 * @param paramMap
	 * @return
	 */
	int updateDisassemblyRecordWhenNotExist(Map<String, Object> paramMap);
	
	/**
	 * 插入拆下件虚拟库存
	 * @param paramMap
	 * @return
	 */
	int insertCxjStock(Map<String, Object> paramMap);
	
	/**
	 * 更新拆下件虚拟库存
	 * @param paramMap
	 * @return
	 */
	int updateCxjStock(Map<String, Object> paramMap);
	
	/**
	 * 新增库存履历
	 * @param paramMap
	 * @return
	 */
	int insertCxjStockRecord(Map<String, Object> paramMap);
	
	/**
	 * 更新拆下件库存履历
	 * @param paramMap
	 * @return
	 */
	int updateCxjStockRecord(Map<String, Object> paramMap);
	
	/**
	 * 回填b_s_0060201拆下件库存id
	 * @param paramMap
	 * @return
	 */
	int backfillCxjStockRecordId(Map<String, Object> paramMap);
	
	/**
	 * 同步装机清单编辑区数据
	 * @param dataInfo
	 * @return
	 */
	int synchronizeLoadingListEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步装机清单生效区数据
	 * @param dataInfo
	 * @return
	 */
	int synchronizeLoadingListEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步航材主数据
	 * @param paramMap
	 * @return
	 */
	int synchronizeAerialMaterialMainData(Map<String, Object> paramMap);
	
	/**
	 * 同步时控件监控设置到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeTimeMonitorEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步时控件监控删除数据到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeTimeMonitorDeleteDataEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步时控件监控设置到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeTimeMonitorEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步时控件监控删除数据到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeTimeMonitorDeleteDataEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步特殊飞行设置数据到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeSpecialFlightSettingEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步特殊飞行设置中的删除数据到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeSpecialFlightSettingDeleteDataEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步特殊飞行设置数据到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeSpecialFlightSettingEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步特殊飞行设置中的删除数据到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeSpecialFlightSettingDeleteDataEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检项目数据到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedItemEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检项目数据到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedItemEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检监控项数据到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedMonitorEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检监控项数据中的删除数据到编辑区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedMonitorDeleteDataEditable(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检监控项数据到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedMonitorEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检监控项数据中的删除数据到生效区
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedMonitorDeleteDataEffective(Map<String, Object> dataInfo);
	
	/**
	 * 同步定检件监控计划数据
	 * @param paramMap
	 * @return
	 */
	int synchronizeFixedMonitorPlan(Map<String, Object> paramMap);
	
	/**
	 * 同步定检件监控计划数据中的删除数据
	 * @param zjqdid
	 * @return
	 */
	int synchronizeFixedMonitorPlanDeleteData(String zjqdid);
	
	/**
	 * 同步部件数据
	 * @param zjqdid
	 * @return
	 */
	int synchronizeComponent(String zjqdid);
	
	/**
	 * 同步部件拆解记录
	 * @param zjqdid
	 * @return
	 */
	int synchronizeComponentDisassembleRecord(Map<String, Object> dataInfo);
	
	/**
	 * 获取某次飞行记录单之前的部件累积使用量
	 * @param paramMap
	 * @return
	 */
	ComponentUsage getComponentUsage(Map<String, Object> paramMap);
	
	/**
	 * 同步部件使用情况
	 * @param fjzch
	 * @return
	 */
	int synchronizeComponentUsage(Map<String, Object> paramMap);
	
	/**
	 * 更新装上件虚拟库存
	 * @param paramMap
	 * @return
	 */
	int updateZsjStock(Map<String, Object> paramMap);
	
	/**
	 * 当库存数量为0时，删除虚拟库存
	 * @param paramMap
	 * @return
	 */
	int deleteStockWhenKcslIsZero(Map<String, Object> paramMap);
	
	/**
	 * 新增装上件库存履历
	 * @param paramMap
	 * @return
	 */
	int insertZsjStockRecord(Map<String, Object> paramMap);
	
	/**
	 * 更新装上件库存履历
	 * @param paramMap
	 * @return
	 */
	int updateZsjStockRecord(Map<String, Object> paramMap);
	
	/**
	 * 回填b_s_0060201装上件库存id
	 * @param paramMap
	 * @return
	 */
	int backfillZsjStockRecordId(Map<String, Object> paramMap);
	
	/**
	 * 根据飞行记录单和航次，找到对应的飞行数据
	 * @param paramMap
	 * @return
	 */
	FlightRecord findFlightRecordByHc(Map<String, Object> paramMap);
	
	/**
	 * 找到飞机下所有装机部件
	 * @param flightRecord
	 * @return
	 */
	List<Map<String, Object>> findPlaneComponent(FlightRecord flightRecord);
	
	/**
	 * 插入或更新部件使用情况
	 * @param dataBean
	 * @return
	 */
	int insertComponentUsage(Map<String, Object> saveList);
	
	/**
	 * 删除b_s_00601无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00601InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_00603无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00603InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_00602无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00602InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_0060201无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS0060201InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_006020101无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS006020101InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_00602010104无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00602010104InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_00602010101无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00602010101InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_00602010102无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00602010102InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_00602010103无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS00602010103InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除b_s_0060201010301无效数据
	 * @param fxjldid
	 * @return
	 */
	int deleteBS0060201010301InvalidData(Map<String, Object> paramMap);
	
	/**
	 * 删除无效的定检项目
	 * @param zjqdid
	 */
	void deleteInvalidFixedItemEffective(Map<String, Object> dataInfo);
	
	/**
	 * 查询航材主数据
	 * @param paramMap
	 * @return
	 */
	String queryHCMainData(Map<String, Object> paramMap);
	
	/**
	 * 更新装上件父节点id-编辑区
	 * @param paramMap
	 * @return
	 */
	int updateFjdidEditable(Map<String, Object> paramMap);
	
	/**
	 * 更新装上件父节点id-生效区
	 * @param paramMap
	 * @return
	 */
	int updateFjdidEffective(Map<String, Object> paramMap);
	
	/**
	 * 根据部件拆解记录，判断该部件是否曾经装过飞机
	 * @param usage
	 * @return
	 */
	boolean isEverDisassembled(ComponentUsage usage);
	
	/**
	 * 失效参与计算的定检件监控计划
	 * @return
	 */
	int invalidScheduledCheckPlanThatCalculated(Map<String, Object> monitorData);
	
	/**
	 * 根据工单号查询工单id
	 * @param gdid
	 * @return
	 */
	String queryGdhByGdid(String gdid);
	
	/**
	 * 查询外场库存
	 * @param kcid
	 * @return
	 */
	OutFieldStock queryOutFieldData(String kcid);
	
	/**
	 * 获取所有拆解的装机清单数据
	 * @param fxjldid
	 * @return
	 */
	List<Map<String, String>> getAllDisassemblyLoadingList(String fxjldid);
	
	/**
	 * 获取非例行工单的时控件设置
	 * @param xggdid
	 * @return
	 */
	WOActionObj findWOTimeMonitorSetting(String xggdid);
	
	/**
	 * 获取装机清单生效区的时控件设置
	 * @param zjqdid
	 * @return
	 */
	List<TimeMonitorSetting> findLoadingListTimeMonitorSetting(String zjqdid);
	
	/**
	 * 获取定检工单的定检项设置
	 * @param xggdid
	 * @return
	 */
	CheckingMonitoring findWOFixedMonitorSetting(String xggdid);
	
	/**
	 * 获取装机清单生效区的定检件设置
	 * @param zjqdid
	 * @return
	 */
	List<ScheduledCheckMonitorItem> findLoadingListFixedMonitorSetting(String zjqdid, String djbh);
	
	/**
	 * 根据装机清单id找到装机清单数据
	 * @param zjqdid
	 * @return
	 */
	LoadingList findLoadingList(String zjqdid);
	
	/**
	 * 获取该飞行记录单之后的所有飞行记录
	 * @param paramMap
	 * @return
	 */
	List<FlightRecord> getAllAfterFlightRecord(String fxjldid);
	
	/**
	 * 更新飞行记录的左发件号序列号
	 * @param list
	 * @return
	 */
	int updateFlightRecordZF(FlightRecord record);
	
	/**
	 * 更新飞行记录的右发件号序列号
	 * @param list
	 * @return
	 */
	int updateFlightRecordYF(FlightRecord record);
	
	/**
	 * 更新飞行记录的搜索灯件号序列号
	 * @param list
	 * @return
	 */
	int updateFlightRecordSSD(FlightRecord record);
	
	/**
	 * 更新飞行记录的绞车件号序列号
	 * @param list
	 * @return
	 */
	int updateFlightRecordJC(FlightRecord record);
	
	/**
	 * 更新飞行记录的外吊挂件号序列号
	 * @param list
	 * @return
	 */
	int updateFlightRecordWDG(FlightRecord record);
	
	/**
	 * 获取飞机一级部件的数量
	 * @param fxjldid
	 * @return
	 */
	List<Map<String, Object>> getLevelOneComponentCount(String fxjldid);
	
	/**
	 * 插入监控项目快照
	 * @param paramMap
	 * @return
	 */
	int insertMonitorItemSnapshot(Map<String, Object> paramMap);
	
	/**
	 * 还原监控项目快照
	 * @param paramMap
	 * @return
	 */
	int restoreMonitorItemSnapshot(Map<String, Object> paramMap);
	
	/**
	 * 还原时控件设置-编辑区
	 * @param paramMap
	 * @return
	 */
	int restoreTimeMonitorEditable(Map<String, Object> paramMap);
	
	/**
	 * 还原定检件监控-编辑区
	 * @param paramMap
	 * @return
	 */
	int resotreFixedMonitorEditable(Map<String, Object> paramMap);
	
	/**
	 * 还原定检件监控项目-编辑区
	 * @param paramMap
	 * @return
	 */
	int resotreFixedMonitorItemEditable(Map<String, Object> paramMap);
	
	/**
	 * 还原时控件设置-生效区
	 * @param paramMap
	 * @return
	 */
	int restoreTimeMonitorEffective(Map<String, Object> paramMap);
	
	/**
	 * 还原定检件监控-生效区
	 * @param paramMap
	 * @return
	 */
	int resotreFixedMonitorEffective(Map<String, Object> paramMap);
	
	/**
	 * 还原定检件监控项目-生效区
	 * @param paramMap
	 * @return
	 */
	int resotreFixedMonitorItemEffective(Map<String, Object> paramMap);
	
	/**
	 * 之后的飞行记录单是否有拆下的记录
	 * @param paramMap
	 * @return
	 */
	int hasAfterDisassembleRecord(Map<String, Object> paramMap);
	
	/**
	 * 之后的飞行记录单是否有装上的记录
	 * @param paramMap
	 * @return
	 */
	int hasAfterAmountRecord(Map<String, Object> paramMap);
} 