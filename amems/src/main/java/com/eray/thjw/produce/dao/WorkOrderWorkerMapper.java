package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.WorkOrderWorker;
import com.eray.thjw.produce.po.Workorder;

/**
 * @Description 135工单信息-工作者mapper
 * @CreateTime 2018年1月25日 上午10:15:13
 * @CreateBy 韩武
 */
public interface WorkOrderWorkerMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkOrderWorker record);

    int insertSelective(WorkOrderWorker record);

    WorkOrderWorker selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkOrderWorker record);

    int updateByPrimaryKey(WorkOrderWorker record);
    
    /**
     * @Description 删除工作者
     * @CreateTime 2018年1月24日 上午10:23:12
     * @CreateBy 韩武
     * @param workorder
     * @return
     */
	int deleteNotExist(Workorder workorder);
	
	/**
	 * @Description 根据工单id删除flb工作者
	 * @CreateTime 2018年1月31日 上午10:07:02
	 * @CreateBy 韩武
	 * @param gdid
	 * @return
	 */
	int deleteFlbWorkerByGdid(String gdid);
	
	/**
	 * @Description 根据工单id复制135工单的工作者到flb
	 * @CreateTime 2018年1月31日 上午10:10:18
	 * @CreateBy 韩武
	 * @param gdid
	 * @return
	 */
	int copyWorkOrderWorkerToFlb(String gdid);
}