package com.eray.thjw.quality.dao;

import com.eray.thjw.quality.po.ProjectAndProblem;

public interface ProjectAndProblemMapper {
    int insert(ProjectAndProblem record);

    int insertSelective(ProjectAndProblem record);
    /**
     * 
     * @Description 根据审核问题单id删除记录
     * @CreateTime 2018年1月11日 下午2:34:24
     * @CreateBy 岳彬彬
     * @param shwtdid
     */
    void deleteByShwtdid(String shwtdid);
    /**
     * 
     * @Description 根据审核项目单id删除记录
     * @CreateTime 2018年1月11日 下午2:34:24
     * @CreateBy sunji
     * @param shxmdid
     */
    void deleteByShxmdid(String shxmdid);
}