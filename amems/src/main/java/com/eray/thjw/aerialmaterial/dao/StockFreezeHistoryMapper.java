package com.eray.thjw.aerialmaterial.dao;

import java.math.BigDecimal;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.StockFreezeHistory;

public interface StockFreezeHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(StockFreezeHistory record);

    int insertSelective(StockFreezeHistory record);

    StockFreezeHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StockFreezeHistory record);

    int updateByPrimaryKey(StockFreezeHistory record);
    
    /**
     * 获取该库存的所有冻结数量之和
     * @param kcid
     * @return
     */
    BigDecimal sumFreezeCount(String kcid);
    
    /**
     * 根据业务id和库存id查询
     * @param record
     * @return
     */
    StockFreezeHistory queryByYwid(StockFreezeHistory record);
    
    /**
     * 根据业务id和库存id刪除
     * @param record
     * @return
     */
    int deleteByYwid(StockFreezeHistory record);
    /**
     * 
     * @Description 批量新增
     * @CreateTime 2018年3月23日 下午4:56:30
     * @CreateBy 岳彬彬
     * @param list
     */
    void insertBatchRecord(List<StockFreezeHistory> list);
    /**
     * 
     * @Description 根据ywid批量删除
     * @CreateTime 2018年3月26日 下午1:42:14
     * @CreateBy 岳彬彬
     * @param list
     */
    void deleteByYwidList(List<String> list);
    /**
     * 
     * @Description 通过业务id删除库存履历
     * @CreateTime 2018年3月28日 上午11:03:09
     * @CreateBy 岳彬彬
     * @param ywid
     */
    void deleteYwid(String ywid);
}