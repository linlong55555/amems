package com.eray.thjw.project2.dao;

import com.eray.thjw.project2.po.ProjectMonitor;

/**
 * @Description 维修项目-监控项目Mapper
 * @CreateTime 2017年8月16日 下午2:50:31
 * @CreateBy 韩武
 */
public interface ProjectMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectMonitor record);

    int insertSelective(ProjectMonitor record);

    ProjectMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectMonitor record);

    int updateByPrimaryKey(ProjectMonitor record);
    
    /**
     * @Description 根据mainid删除维修项目-监控项目
     * @CreateTime 2017年8月26日 下午6:18:19
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
    
    /**
     * @Description 根据jkdxid删除维修项目-监控项目
     * @CreateTime 2017年8月26日 下午6:25:44
     * @CreateBy 韩武
     * @param jkdxid
     * @return
     */
    int deleteByJkdxid(String jkdxid);
}