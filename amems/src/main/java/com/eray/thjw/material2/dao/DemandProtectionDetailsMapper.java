package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.DemandProtectionDetails;
/**
 * 
 * @Description 需求保障明细mapper
 * @CreateTime 2018年2月26日 上午10:04:26
 * @CreateBy 林龙
 */
public interface DemandProtectionDetailsMapper {
    int deleteByPrimaryKey(String id);

    int insert(DemandProtectionDetails record);

    int insertSelective(DemandProtectionDetails record);

    DemandProtectionDetails selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DemandProtectionDetails record);

    int updateByPrimaryKey(DemandProtectionDetails record);
}