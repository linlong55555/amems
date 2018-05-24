package com.eray.thjw.basic.dao;

import java.util.List;

import com.eray.thjw.basic.po.PriceLadder;
/**
 * 
 * @Description 工时-阶梯单价Mapper
 * @CreateTime 2018年4月2日 上午10:08:16
 * @CreateBy 岳彬彬
 */
public interface PriceLadderMapper {
    int deleteByPrimaryKey(String id);

    int insert(PriceLadder record);

    int insertSelective(PriceLadder record);

    PriceLadder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PriceLadder record);

    int updateByPrimaryKey(PriceLadder record);
    /**
     * 
     * @Description 工时阶梯单价集合
     * @CreateTime 2018年4月2日 上午10:55:29
     * @CreateBy 岳彬彬
     * @return
     */
    List<PriceLadder> getRecord();
    /**
     * 
     * @Description 删除所有
     * @CreateTime 2018年4月3日 下午6:37:28
     * @CreateBy 岳彬彬
     */
    void deleteAll();
}