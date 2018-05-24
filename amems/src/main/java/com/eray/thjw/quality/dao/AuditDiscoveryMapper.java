package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.AuditDiscovery;

/**
 * @Description 审核问题清单Mapper
 * @CreateTime 2018年1月8日 下午1:46:20
 * @CreateBy 韩武
 */
public interface AuditDiscoveryMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditDiscovery record);

    int insertSelective(AuditDiscovery record);

    AuditDiscovery selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditDiscovery record);

    int updateByPrimaryKey(AuditDiscovery record);
    
    int getCount(String shwtdbh,String dprtcode);
    /**
     * 
     * @Description 问题通知单列表
     * @CreateTime 2018年1月9日 下午5:35:01
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<AuditDiscovery> queryList(AuditDiscovery record);
}