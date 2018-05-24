package com.eray.thjw.productionplan.service;

import java.util.List;

import com.eray.thjw.productionplan.po.TimeControlOptions;


/**
 * b_s_00304 生效区-定检监控项目 dao
 * @author zhuchao
 *
 */
public interface TimeControlOptionsService {
	
	public List<TimeControlOptions> queryAllList(TimeControlOptions timeControlOptions) throws Exception;
	
	public List<TimeControlOptions> queryAllsj() throws Exception;
}