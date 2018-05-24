package com.eray.thjw.project2.dao;

import com.eray.thjw.project2.po.ProjectMaterial;

/**
 * @Description 维修项目-关联部件关系Mapper
 * @CreateTime 2017年8月16日 下午2:49:28
 * @CreateBy 韩武
 */
public interface ProjectMaterialMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectMaterial record);

    int insertSelective(ProjectMaterial record);

    ProjectMaterial selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectMaterial record);

    int updateByPrimaryKey(ProjectMaterial record);
    
    /**
     * @Description 根据mainid删除维修项目-关联部件关系
     * @CreateTime 2017年8月26日 下午6:10:36
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
}