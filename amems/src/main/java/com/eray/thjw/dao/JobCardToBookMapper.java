package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.JobCardToBook;

public interface JobCardToBookMapper {
    int deleteByPrimaryKey(String id);

    int insert(JobCardToBook record);

    int insertSelective(JobCardToBook record);

    JobCardToBook selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JobCardToBook record);

    int updateByPrimaryKey(JobCardToBook record);
    
    void batchDelete(List<String> ids);
    
    void batchInsert(List<JobCardToBook> JobCardToBooks);
    
    List<JobCardToBook> queryAllByMainid(String mainid);
}
