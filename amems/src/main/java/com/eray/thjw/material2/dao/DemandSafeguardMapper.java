package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.DemandSafeguard;
/**
 * 
 * @Description 需求保障
 * @CreateTime 2018-2-26 下午2:59:18
 * @CreateBy 孙霁
 */
public interface DemandSafeguardMapper {
    int deleteByPrimaryKey(String id);

    int insert(DemandSafeguard record);

    int insertSelective(DemandSafeguard record);

    DemandSafeguard selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DemandSafeguard record);

    int updateByPrimaryKey(DemandSafeguard record);
    
    /**
     * 
     * @Description 根据查询条件查询需求清单
     * @CreateTime 2018-2-28 上午11:23:38
     * @CreateBy 孙霁
     * @param id
     * @return
     */
    DemandSafeguard selectDetail(String id);
}