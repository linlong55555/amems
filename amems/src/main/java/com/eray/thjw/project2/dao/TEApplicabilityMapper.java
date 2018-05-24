package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.TEApplicability;

/**
 * 
 * @Description 技术评估单-适用性mapper
 * @CreateTime 2018年3月29日 下午3:02:45
 * @CreateBy 林龙
 */
public interface TEApplicabilityMapper {
    int deleteByPrimaryKey(String id);

    int insert(TEApplicability record);

    int insertSelective(TEApplicability record);

    TEApplicability selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TEApplicability record);

    int updateByPrimaryKey(TEApplicability record);

	void insert4Batch(List<TEApplicability> syxszList);

	void deleteByMainid(String id);
}