package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;

public interface CheckTaskBillService {
	/**
	 * @author meizhiliang
	 * @description 指定结束定检任务单
	 * @develop date 2016.11.01
    */ 
	void doEnd(CheckTaskBill record) throws RuntimeException;
	/**
	 * @author meizhiliang
	 * @description 指定结束计划任务
	 * @develop date 2016.11.01
    */ 
	void doEndByPlanTask(CheckTaskBill record) throws RuntimeException;
	
	/**
	 * 1、新增b_g_014 定检任务主表
	 * @param record
	 * @return+
	 * 
	 * @throws Exception
	 */
	public void save(ScheduledCheckItem scheduledCheckItem) throws Exception;
	
	/**
	 * @author liub
	 * @description 检查定检任务主表是否存在数据
	 * @param Map<String, Object>
	 * @return
	 * @develop date 2016.09.26
	 */
	public List<CheckTaskBill> checkIsExist(Map<String, Object> paramMap) throws RuntimeException;
    
	/**
	 * @author meizhiliang
	 * @description 分页查询定检任务单列表
	 * @develop date 2016.09.26
	 */
	 List<CheckTaskBill> selectCheckTaskBill(CheckTaskBill record);         
	 
     /**
		 * @author meizhiliang
		 * @description 查询定检任务单
		 * @develop date 2016.11.01
	*/ 
    public CheckTaskBill selectByPrimaryKey(String id);

    void updateByPrimaryKeySelective(CheckTaskBill checkTaskBill);

	void updateByPrimaryKeySelective1(CheckTaskBill checkTaskBill);
	
}
