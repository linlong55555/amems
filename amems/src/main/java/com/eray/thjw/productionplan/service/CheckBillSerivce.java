package com.eray.thjw.productionplan.service;

import com.eray.thjw.productionplan.po.ScheduledCheckItem;


public interface CheckBillSerivce {
	
	void save(String bz,String id,String ids,ScheduledCheckItem scheduledCheckItem, String czls) throws Exception;	// // 新增b_g_016 定检工单
	
}
