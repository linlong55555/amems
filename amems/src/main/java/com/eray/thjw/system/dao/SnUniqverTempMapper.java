package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.system.po.SnUniqverTemp;

public interface SnUniqverTempMapper {
    int insert(SnUniqverTemp record);

    int insertSelective(SnUniqverTemp record);
    /**
     * 
     * @Description 批量新增
     * @CreateTime 2017年12月29日 下午12:31:29
     * @CreateBy 岳彬彬
     * @param record
     */
    void insertBatch(List<SnUniqverTemp> list);
    /**
     * 
     * @Description 根据流水号删除数据
     * @CreateTime 2017年12月29日 下午12:32:51
     * @CreateBy 岳彬彬
     * @param lsh
     */
    void deleteByLsh(String lsh);
}