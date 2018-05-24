package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.productionplan.po.PlanTask;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.po.PlanTaskHistory;

/**
 * 计划任务服务接口
 * @author zhuchao
 *
 */
public interface PlanTaskService {
	 
	/**
	 * 查询一个计划任务
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public PlanTask selectByPrimaryKey(String id) throws BusinessException;
	
	/**
	 * 指定结束
	 * @param planTask
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> doEnd(PlanTaskExt planTask)throws  BusinessException;
	
	/**
	 * 获取所有计划任务看板
	 * @param planTask
	 * @return
	 */
    public Map<String,List<PlanTask>> queryListPanel(PlanTask planTask)throws RuntimeException;

    /**
     * 获取非装机件与装机件的数量
     * @param planTask
     * @return
     * @throws RuntimeException
     */
	public Map<String, String> queryTotalByGroup(PlanTask planTask)throws RuntimeException;

	/**
	 * 计划任务完成
	 * @param planTask
	 * @return 
	 * @throws RuntimeException
	 */
	public Map<String, Object> doComplete(PlanTask planTask)throws BusinessException;

	/**
	 * 计划任务列表
	 * @param planTask
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public Map<String, Object> queryPlanTaskList(PlanTaskExt planTask) throws BusinessException;

	/**
	 * 计划任务历史列表
	 * @param planTask
	 * @return
	 */
	public Map<String, Object> queryHistoryList(PlanTaskHistory planTask) throws BusinessException;

	/**
	 * 查询一页航材缺件
	 * @param record
	 * @return
	 */
	public Map<String, Object> hcStatistics(HCMainData record)throws BusinessException;

	/**
	 * 查询一页工具缺件
	 * @param record
	 * @return
	 */
	public Map<String, Object> toolStatistics(HCMainData hcMainData)throws BusinessException;

	/**
	 * 查询计划任务
	 * @param planTask
	 * @return
	 */
	public List<PlanTask> queryAllList(PlanTask planTask)throws BusinessException;

	/**
	 * 修改监控备注
	 * @param planTask
	 */
	public void editJkRemark(PlanTask planTask) throws BusinessException;

	/**
	 * 工单指定结束同步结束计划任务
	 * @param planTask
	 * @throws Exception
	 * return String 00:成功，01:未生成计划任务
	 */
	public String doEndByWorkOrder(PlanTaskExt planTask) throws Exception;
	
	/**
	 * 飞行记录单查询待完成的计划任务
	 * @param planTask
	 * @throws Exception
	 */
	public Map<String, Object> queryByFlightRecord(PlanTask planTask);

	/**
	 * 
	 * 检查计划任务显示状态是否已完成
	 * @param id  
	 * @param idSource id来源 (1定检工单、2非例行工单任务、3EO工单任务 4、定检执行任务、5计划任务)
	 * @return
	 * @throws BusinessException 
	 */
	public boolean alreadyCompleted(String id, String idSource) throws BusinessException;

	/**
	 * 查询计划看板和汇总数据
	 * @param planTask
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryPanelSummary(PlanTask planTask) throws BusinessException;
	
}
