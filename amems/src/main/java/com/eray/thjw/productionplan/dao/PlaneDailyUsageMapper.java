package com.eray.thjw.productionplan.dao;

import com.eray.thjw.productionplan.po.PlaneDailyUsage;
import com.eray.thjw.productionplan.po.PlaneData;

public interface PlaneDailyUsageMapper {
    int deleteByPrimaryKey(PlaneData pd);

    int insert(PlaneDailyUsage record);

    int insertSelective(PlaneDailyUsage record);

    PlaneDailyUsage selectByPrimaryKey(String fjzch);

    int updateByPrimaryKeySelective(PlaneDailyUsage record);

    int updateByPrimaryKey(PlaneDailyUsage record);
}