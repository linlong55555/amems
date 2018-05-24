package com.eray.thjw.service;

import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.WorkOrder;

public interface BaseWorkOrderService {
    /**
     * @author meizhiliang
     * @discription 增加基础工单
     */
	int insertSelective(WorkOrder workorder)throws RuntimeException;
	
	  /**
     * @author linlong
     * @discription 根据工单编号查询基础工单
     */
	BaseWorkOrder selectKey(String gdbh)throws RuntimeException;
}
