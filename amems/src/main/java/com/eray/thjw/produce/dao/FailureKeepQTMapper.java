package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.FailureKeepQT;

public interface FailureKeepQTMapper {
    int deleteByPrimaryKey(String id);

    int insert(FailureKeepQT record);

    int insertSelective(FailureKeepQT record);

    FailureKeepQT selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FailureKeepQT record);

    int updateByPrimaryKey(FailureKeepQT record);

	List<FailureKeepQT> selectByMainid(String mainid);

	void insert4Batch(List<FailureKeepQT> qtList);

	void deleteByMainid(String mainid);
}