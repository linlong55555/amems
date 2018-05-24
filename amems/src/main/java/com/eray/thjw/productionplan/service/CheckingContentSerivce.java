package com.eray.thjw.productionplan.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.productionplan.po.CheckingContent;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;

public interface CheckingContentSerivce {
	/**
	 * @author 梅志亮
	 * @param czls 
	 * @return 
	 * @description 新增b_g_01402  定检任务工作内容
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	void save(String bz,String id,ScheduledCheckItem scheduledCheckItem, String czls) throws Exception;
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 查询定检任务单的多个工作内容
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	List <CheckingContent> selectByPrimaryKey(String mainid);  
}
