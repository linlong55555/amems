package com.eray.thjw.productionplan.dao;

import com.eray.thjw.productionplan.po.ScheduledCheckPlan;


/**
 * b_s_008 定检件监控计划表 dao
 * @author zhuchao
 *
 */
public interface ScheduledCheckPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(ScheduledCheckPlan record);

    int insertSelective(ScheduledCheckPlan record);

    ScheduledCheckPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ScheduledCheckPlan record);

    int updateByPrimaryKey(ScheduledCheckPlan record);
}