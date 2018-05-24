package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.DemandProtection;
/**
 * 
 * @Description 需求保障mapper
 * @CreateTime 2018年2月26日 上午10:04:07
 * @CreateBy 林龙
 */
public interface DemandProtectionMapper {
    int deleteByPrimaryKey(String id);

    int insert(DemandProtection record);

    int insertSelective(DemandProtection record);

    DemandProtection selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DemandProtection record);

    int updateByPrimaryKey(DemandProtection record);
}