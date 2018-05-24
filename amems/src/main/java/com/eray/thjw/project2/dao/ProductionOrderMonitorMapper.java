package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.ProductionOrderMonitor;

/**
 * @Description 生产指令监控项 b_g2_01401
 * @CreateTime 2018年4月28日 上午9:57:41
 * @CreateBy 徐勇
 */
public interface ProductionOrderMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductionOrderMonitor record);

    int insertSelective(ProductionOrderMonitor record);

    ProductionOrderMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductionOrderMonitor record);

    int updateByPrimaryKey(ProductionOrderMonitor record);
    
    /**
     * @Description 查询多个生产指令的监控项目
     * @CreateTime 2018年5月2日 上午11:20:57
     * @CreateBy 徐勇
     * @param list 生产指令IDs
     * @return
     */
    List<ProductionOrderMonitor> selectMonitorByMainIds(@Param("list")List<String> list);
    
    /**
     * @Description 查询生产指令的监控项目
     * @CreateTime 2018年5月2日 上午11:20:57
     * @CreateBy 徐勇
     * @param mainid 生产指令ID
     * @return
     */
    List<ProductionOrderMonitor> selectMonitorByMainId(@Param("mainid")String mainid);
    
    
    /**
     * @Description 根据mainid删除生产指令监控项
     * @CreateTime 2018年5月4日 上午10:06:31
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
}