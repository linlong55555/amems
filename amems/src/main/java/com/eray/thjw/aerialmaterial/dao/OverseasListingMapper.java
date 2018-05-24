package com.eray.thjw.aerialmaterial.dao;

import com.eray.thjw.aerialmaterial.po.OverseasListing;


public interface OverseasListingMapper {
    int deleteByPrimaryKey(String id);

    int insert(OverseasListing record);

    int insertSelective(OverseasListing record);

    OverseasListing selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OverseasListing record);

    int updateByPrimaryKey(OverseasListing record);
}