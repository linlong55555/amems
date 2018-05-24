package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ToolsUse;

public interface ToolsUseMapper {
    int deleteByPrimaryKey(String id);

    int insert(ToolsUse record);

    int insertSelective(ToolsUse record);

    ToolsUse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ToolsUse record);

    int updateByPrimaryKey(ToolsUse record);

	List<ToolsUse> queryList(ToolsUse record);
}