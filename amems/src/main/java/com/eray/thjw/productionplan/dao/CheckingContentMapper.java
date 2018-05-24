package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.productionplan.po.CheckingContent;

public interface CheckingContentMapper {
	
	void save(CheckingContent checkingContent) throws Exception;	// 新增b_g_01402   定检任务工作内容
	
	List <CheckingContent> selectByPrimaryKey(String mainid);           //查询定检任务单的多个工作内容
	
}
