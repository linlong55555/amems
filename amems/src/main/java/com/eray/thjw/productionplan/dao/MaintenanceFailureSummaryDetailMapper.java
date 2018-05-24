package com.eray.thjw.productionplan.dao;

import com.eray.thjw.productionplan.po.MaintenanceFailureSummaryDetail;

public interface MaintenanceFailureSummaryDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceFailureSummaryDetail record);

    int insertSelective(MaintenanceFailureSummaryDetail record);

    MaintenanceFailureSummaryDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceFailureSummaryDetail record);

    int updateByPrimaryKey(MaintenanceFailureSummaryDetail record);
}