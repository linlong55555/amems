package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.ProductionOrder;

/**
 * @Description 生产指令B_G2_014
 * @CreateTime 2018年4月28日 上午9:57:09
 * @CreateBy 徐勇
 */
public interface ProductionOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductionOrder record);

    int insertSelective(ProductionOrder record);

    ProductionOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductionOrder record);

    int updateByPrimaryKey(ProductionOrder record);
    
    /**
     * @Description 生产指令页面更新
     * @CreateTime 2018年5月3日 下午4:29:01
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByProductionOrder(ProductionOrder record);
    
    /**
     * @Description 查询生产指令集合
     * @CreateTime 2018年5月4日 上午10:28:24
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<ProductionOrder> queryList(ProductionOrder record);
    
    /**
     * @Description 查询生产指令详情
     * @CreateTime 2018年5月7日 上午9:58:08
     * @CreateBy 韩武
     * @param id
     * @return
     */
    ProductionOrder queryDetail(String id);
    
    /**
     * @Description 根据指令编号、版本、组织机构获取数量
     * @CreateTime 2018年5月7日 下午2:14:15
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int getCountByZlbhAndBbAndDprtcode(ProductionOrder record);
    
    /**
     * @Description 根据主键更新状态
     * @CreateTime 2018年5月7日 下午3:55:34
     * @CreateBy 韩武
     * @param id
     * @param zt
     * @return
     */
    int updateZtByPrimaryKey(@Param("id")String id, @Param("zt")Integer zt);
    
    /**
     * @Description 更新生产指令后版本id
     * @CreateTime 2018年5月8日 上午10:16:03
     * @CreateBy 韩武
     * @param id
     * @param bBbid
     * @return
     */
    int updateBBbidByPrimaryKey(@Param("id")String id, @Param("bBbid")String bBbid);
    
    /**
     * @Description 查询生产指令版本历史
     * @CreateTime 2018年5月8日 上午11:46:48
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<ProductionOrder> queryVersionList(ProductionOrder record);
    
    /**
     * @Description 查询生产指令历史版本
     * @CreateTime 2018年5月8日 下午1:26:14
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<ProductionOrder> queryHistoryList(ProductionOrder record);
}