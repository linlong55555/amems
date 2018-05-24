package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.WOJobContent;
import com.eray.thjw.po.WorkOrder;

public interface WOJobContentService {

	int insertSelective(WorkOrder wrokorder)throws RuntimeException;    //对工单的工作内容执行增加操作

	  List<WOJobContent> selectByWOJobContentList(WOJobContent record);        //查询工队的所有工作内容
	  /**
	   	 * @author sunji
	   	 * @description 根据基础id查询工作内容
	   	 * @develop date 2016.08.15
	   	 */
	 public List<WOJobContent> selectedGznrList(WOJobContent wOJobContent)throws RuntimeException;
	 
     int doByJobContent(WorkOrder workorder);
     
     int deleteById(String id);
     
     int updateByJobContent(WOJobContent wOJobContent);
}
