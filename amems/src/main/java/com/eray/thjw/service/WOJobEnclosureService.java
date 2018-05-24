package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.po.WorkOrder;

public interface WOJobEnclosureService {
	int insertSelective(WorkOrder wrokorder);     // 对工单的附件表执行增加操作

	List<WOJobEnclosure> selectedFjList(WOJobEnclosure wOJobEnclosure);
	
    int doByWOJobEnclosure(WorkOrder workorder);
	     
    int updateByWOJobEnclosure(WOJobEnclosure record);
	     
	int deleteById(String id);
	
	WOJobEnclosure selectByPrimaryKey(String id);    //附件下载
}
