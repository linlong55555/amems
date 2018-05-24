package com.eray.thjw.project2.dao;

import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.ProjectRelationshipEffective;

/**
 * @Description 维修方案生效区-相关维修项目关系Mapper
 * @CreateTime 2017年8月21日 下午7:24:46
 * @CreateBy 韩武
 */
public interface ProjectRelationshipEffectiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectRelationshipEffective record);

    int insertSelective(ProjectRelationshipEffective record);

    ProjectRelationshipEffectiveMapper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectRelationshipEffective record);

    int updateByPrimaryKey(ProjectRelationshipEffective record);
    
    /**
     * @Description 维修项目同步到生效区
     * @CreateTime 2017年8月21日 下午7:31:53
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    int synchronize(MaintenanceScheme scheme);
}