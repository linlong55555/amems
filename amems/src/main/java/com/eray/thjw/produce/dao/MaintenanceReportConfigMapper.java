package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.MaintenanceReportConfig;

/**
 * @Description 维修执管月报配置mapper
 * @CreateTime 2018年4月24日 下午3:58:16
 * @CreateBy 韩武
 */
public interface MaintenanceReportConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceReportConfig record);

    int insertSelective(MaintenanceReportConfig record);

    MaintenanceReportConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceReportConfig record);

    int updateByPrimaryKey(MaintenanceReportConfig record);
    
    /**
     * @Description 根据业务主键更新
     * @CreateTime 2018年4月26日 上午11:10:09
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByBusinessKey(MaintenanceReportConfig record);
    
    /**
     * @Description 根据业务主键查询
     * @CreateTime 2018年4月26日 下午1:44:03
     * @CreateBy 韩武
     * @param record
     * @return
     */
    MaintenanceReportConfig queryByBusinessKey(MaintenanceReportConfig record);
    
    /**
     * @Description 查询最新的工时费用设置
     * @CreateTime 2018年4月26日 下午1:56:33
     * @CreateBy 韩武
     * @param record
     * @return
     */
    MaintenanceReportConfig queryLatestConfig(MaintenanceReportConfig record);
}