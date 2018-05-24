package com.eray.thjw.project2.dao;

import com.eray.thjw.project2.po.ProjectRelationship;

/**
 * @Description 关联维修项目Mapper
 * @CreateTime 2017年8月16日 下午2:50:49
 * @CreateBy 韩武
 */
public interface ProjectRelationshipMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectRelationship record);

    int insertSelective(ProjectRelationship record);

    ProjectRelationship selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectRelationship record);

    int updateByPrimaryKey(ProjectRelationship record);
    
    /**
     * @Description 根据mainid删除关联维修项目
     * @CreateTime 2017年8月26日 下午5:53:06
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
}