package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.ComponentUseCount;

/**
 * @Description B_S2_913 部件使用汇总 DAO
 * @CreateTime 2017年10月25日 下午5:07:22
 * @CreateBy 徐勇
 */
public interface ComponentUseCountMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComponentUseCount record);

    int insertSelective(ComponentUseCount record);

    ComponentUseCount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComponentUseCount record);

    int updateByPrimaryKey(ComponentUseCount record);
    
    int deleteByZjqdid(@Param("zjqdid")String zjqdid);
    
    /**
     * @Description 批量新增
     * @CreateTime 2017年10月25日 下午5:09:45
     * @CreateBy 徐勇
     * @param list 部件使用汇总
     * @return
     */
    int insert4Batch(@Param("list")List<ComponentUseCount> list);
    
    /**
     * @Description 批量修改
     * @CreateTime 2017年10月25日 下午5:24:16
     * @CreateBy 徐勇
     * @param list 部件使用汇总
     * @return
     */
    int update4Batch(@Param("list")List<ComponentUseCount> list);
    
    /**
     * @Description 批量修改累计值（实际值）
     * @CreateTime 2017年10月25日 下午5:24:16
     * @CreateBy 徐勇
     * @param list 部件使用汇总
     * @return
     */
    int updateSJ4Batch(@Param("list")List<ComponentUseCount> list);
}