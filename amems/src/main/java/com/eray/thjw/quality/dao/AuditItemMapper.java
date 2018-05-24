package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.AuditItem;

public interface AuditItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditItem record);

    int insertSelective(AuditItem record);

    AuditItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditItem record);

    int updateByPrimaryKey(AuditItem record);
    
    /**
     * 
     * @Description 根据条件查询列表数据
     * @CreateTime 2018-1-16 下午3:02:05
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    List<AuditItem> queryAll(AuditItem record);
    
    /**
     * 
     * @Description 根据条件查询数量
     * @CreateTime 2018-1-16 下午3:08:10
     * @CreateBy 孙霁
     * @param record
     */
    int queryCount(AuditItem record);
}