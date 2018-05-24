package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.ToolBorrowReturn;

/**
 * @Description 工具借用/归还状态mapper
 * @CreateTime 2018年3月28日 上午11:23:09
 * @CreateBy 韩武
 */
public interface ToolBorrowReturnMapper {
    int deleteByPrimaryKey(String id);

    int insert(ToolBorrowReturn record);

    int insertSelective(ToolBorrowReturn record);

    ToolBorrowReturn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ToolBorrowReturn record);

    int updateByPrimaryKey(ToolBorrowReturn record);
}