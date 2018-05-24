package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.productionplan.po.TimeControlOptions;


/**
 * b_s_00304 生效区-定检监控项目 dao
 * @author zhuchao
 *
 */
public interface TimeControlOptionsMapper {
	
	public List<TimeControlOptions> queryAllList(TimeControlOptions timeControlOptions)throws BusinessException;
	
	public List<TimeControlOptions> queryAllsj() throws BusinessException;
}