package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.ProjectModel;

/**
 * @Description 维修项目对应飞机关系Mapper
 * @CreateTime 2017年8月16日 下午2:50:09
 * @CreateBy 韩武
 */
public interface ProjectModelMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectModel record);

    int insertSelective(ProjectModel record);

    ProjectModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectModel record);

    int updateByPrimaryKey(ProjectModel record);
    
    /**
     * @Description 根据mainid删除维修项目对应飞机关系
     * @CreateTime 2017年8月26日 下午6:05:35
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
    
    /**
     * @Description 根据mainid集合获取维修项目对应飞机关系
     * @CreateTime 2017-8-28 上午11:30:15
     * @CreateBy 刘兵
     * @param mainidList mainid集合 
     * @return List<ProjectModel> 维修项目对应飞机关系集合 
     */
	List<ProjectModel> queryByMainidList(List<String> mainidList);

	/**
	 * @Description 根据mainid批量删除维修项目对应飞机关系：导入专用
	 * @CreateTime 2017-12-15 下午2:17:23
	 * @CreateBy 雷伟
	 * @param delModelIds
	 */
	void delete4BatchByMainids(List<String> delModelIds);

	/**
	 * @Description 批量插入：导入专用
	 * @CreateTime 2017-12-15 下午2:20:42
	 * @CreateBy 雷伟
	 * @param addModels
	 */
	void insert4BatchImpl(List<ProjectModel> addModels);
}