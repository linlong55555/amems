package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.ProjectKeep;

/**
 * 
 * @Description  项目保留mapper 
 * @CreateTime 2017年10月27日 下午3:59:34
 * @CreateBy 林龙
 */
public interface ProjectKeepMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectKeep record);

    int insertSelective(ProjectKeep record);

    ProjectKeep selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectKeep record);

    int updateByPrimaryKey(ProjectKeep record);

	List<ProjectKeep> queryAllPageList(ProjectKeep projectKeep);

	int queryCount(ProjectKeep fai);

	ProjectKeep getInfoById(ProjectKeep projectKeep);

	int getCurrentZt4Validation(String id);

	ProjectKeep selectGetBygdId(ProjectKeep projectKeep);

	ProjectKeep selectGetBygdId135(ProjectKeep projectKeep);

	ProjectKeep selectGetBygdId145(ProjectKeep projectKeep);

	List<ProjectKeep> queryMonitorAllPageList(ProjectKeep param);

}