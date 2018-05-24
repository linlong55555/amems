package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.Auditnotice;

public interface AuditnoticeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Auditnotice record);

    int insertSelective(Auditnotice record);

    Auditnotice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Auditnotice record);

    int updateByPrimaryKey(Auditnotice record);
    
    /**
     * 
     * @Description 查询所有
     * @CreateTime 2018-1-8 上午10:12:39
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    List<Auditnotice> queryAll(Auditnotice record);
    
    /**
     * 
     * @Description 根据条件查询数量
     * @CreateTime 2018-1-10 下午3:42:26
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    int queryCount(Auditnotice record);
}