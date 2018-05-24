package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.ProcessRecord;
/**
 * 
 * @Description 流程记录 Mapper
 * @CreateTime 2018年1月4日 上午11:03:03
 * @CreateBy 林龙
 */
public interface ProcessRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProcessRecord record);

    int insertSelective(ProcessRecord record);

    ProcessRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProcessRecord record);

    int updateByPrimaryKey(ProcessRecord record);
    
    /**
     * @Description 查询流程记录合集
     * @CreateTime 2018年1月11日 下午2:25:54
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<ProcessRecord> queryList(ProcessRecord record);
    /**
     * 
     * @Description 批量新增流程记录
     * @CreateTime 2018年1月15日 上午11:36:19
     * @CreateBy 岳彬彬
     * @param record
     */
    void batchInsert(ProcessRecord record);
    /**
     * 
     * @Description 根据mainid 查询数据
     * @CreateTime 2018-2-28 下午2:35:03
     * @CreateBy 孙霁
     * @param mainid
     * @return
     */
    List<ProcessRecord> selectByMainid(String mainid);
    /**
     * 
     * @Description 根据mainid删除流程记录
     * @CreateTime 2018年3月23日 下午6:01:23
     * @CreateBy 岳彬彬
     * @param mainid
     */
    void deleteByMainid(String mainid);
}