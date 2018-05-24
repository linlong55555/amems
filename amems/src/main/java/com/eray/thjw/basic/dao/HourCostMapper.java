package com.eray.thjw.basic.dao;

import java.util.List;

import com.eray.thjw.basic.po.HourCost;
/**
 * 
 * @Description 工时单价Mapper
 * @CreateTime 2018年4月2日 上午10:05:45
 * @CreateBy 岳彬彬
 */
public interface HourCostMapper {
    int deleteByPrimaryKey(String id);

    int insert(HourCost record);

    int insertSelective(HourCost record);

    HourCost selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HourCost record);

    int updateByPrimaryKey(HourCost record);
    /**
     * 
     * @Description 获取工时单价
     * @CreateTime 2018年4月2日 上午10:52:59
     * @CreateBy 岳彬彬
     * @return
     */
    List<HourCost> getRecord();
    /**
     * 
     * @Description 删除所有
     * @CreateTime 2018年4月3日 下午6:37:28
     * @CreateBy 岳彬彬
     */
    void deleteAll();
}