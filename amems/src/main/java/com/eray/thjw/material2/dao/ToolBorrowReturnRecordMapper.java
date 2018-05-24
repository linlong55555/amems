package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.ToolBorrowReturnRecord;

/**
 * @Description 工具借用/归还记录mapper
 * @CreateTime 2018年3月28日 上午11:23:28
 * @CreateBy 韩武
 */
public interface ToolBorrowReturnRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ToolBorrowReturnRecord record);

    int insertSelective(ToolBorrowReturnRecord record);

    ToolBorrowReturnRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ToolBorrowReturnRecord record);

    int updateByPrimaryKey(ToolBorrowReturnRecord record);
    
    /**
     * @Description 查询工具借用/归还记录集合
     * @CreateTime 2018年4月3日 下午5:48:14
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<ToolBorrowReturnRecord> queryList(ToolBorrowReturnRecord record);
}