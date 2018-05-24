package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.FailureKeepJK;

public interface FailureKeepJKMapper {
    int deleteByPrimaryKey(String id);

    int insert(FailureKeepJK record);

    int insertSelective(FailureKeepJK record);

    FailureKeepJK selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FailureKeepJK record);

    int updateByPrimaryKey(FailureKeepJK record);

	void insert4Batch(List<FailureKeepJK> jkList);

	void deleteByMainid(String mainid);

	void updateJkBsByMainidAndJD(@Param("mainid")String mainid, @Param("jd")String jd, @Param("jkbs")String jkbs);

	void deleteByMainidAndJD(@Param("mainid")String mainid, @Param("jd")String jd);

	List<FailureKeepJK> selectByMainidAndJd(@Param("mainid")String mainid, @Param("jd")String jd);

}