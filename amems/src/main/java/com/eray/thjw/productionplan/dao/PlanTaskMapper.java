package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlanTask;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.po.PlanTaskHistory;

public interface PlanTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlanTask record);

    int insertSelective(PlanTask record);

    PlanTask selectByPrimaryKey(String id);
    
    /**
     * 通过相关工单ID查询计划任务
     * @param xggdid
     * @return
     */
    PlanTask selectByXggdid(String xggdid);

    int updateByPrimaryKeySelective(PlanTask record);

    int updateByPrimaryKey(PlanTask record);

	int queryListCount(PlanTask planTask);
	
	List<PlanTask> queryList(PlanTask planTask);
	
	List<PlanTask> queryLoadedParts(PlanTask planTask);
	
	List<PlanTask> queryNotLoadedParts(PlanTask planTask);
	
	List<PlanTask> queryAllList(PlanTask planTask);

	/**
	 * 计划看板结果列表
	 * @param planTask
	 * @return
	 */
	List<PlanTask> queryListPanel(PlanTask planTask);
	
	/**
	 * 计划任务列表
	 * @param planTask
	 * @return
	 */
	List<PlanTaskExt> queryPlanTaskList(PlanTask planTask);

	/**
	 * 计划任务历史列表
	 * @param planTask
	 * @return
	 */
	List<PlanTaskHistory> queryHistoryList(PlanTask planTask);

	/**
	 * 计划任务历史总数
	 * @param planTask
	 * @return
	 */
	int queryHistoryCount(PlanTask planTask);
	
	/**
	 * 时控件计划任务指定结束，清除装机清单表任务ID,工单ID
	 * @param id
	 */
	public void doEndByTimecontrolpart(String id);

	/**
	 * 定检件计划任务指定结束，清除生效区-定检件定检项目任务ID
	 * @param id
	 */
	public void doEndByCheckbill(String id);

	/**
	 * 指定结束计划任务
	 * @param planTask
	 * @return
	 */
	public int doEnd(PlanTaskExt planTask);
	
	/**
	 * 生成快照
	 * @param planTaskDb
	 * @return
	 */
	PlanTaskExt genPlanTaskSnap(PlanTask planTaskDb);
	
	/**
	 * 查询定检工单
	 * @return
	 */
	public Map<String, Object> selectDjWorkOrder(String id);
	
}