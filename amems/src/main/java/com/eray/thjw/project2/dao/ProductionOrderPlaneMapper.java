package com.eray.thjw.project2.dao;

import com.eray.thjw.project2.po.ProductionOrderPlane;

/**
 * @Description 生产指令飞机关系b_g2_01402
 * @CreateTime 2018年4月28日 上午9:58:21
 * @CreateBy 徐勇
 */
public interface ProductionOrderPlaneMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductionOrderPlane record);

    int insertSelective(ProductionOrderPlane record);

    ProductionOrderPlane selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductionOrderPlane record);

    int updateByPrimaryKey(ProductionOrderPlane record);
    
    /**
     * @Description 根据mainid删除 生产指令飞机关系
     * @CreateTime 2018年5月4日 上午10:06:31
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
}