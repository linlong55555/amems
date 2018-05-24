package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.InstockDetailStock;

/**
 * 入库单库存
 * @author xu.yong
 *
 */
public interface InstockDetailStockMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstockDetailStock record);

    int insertSelective(InstockDetailStock record);

    InstockDetailStock selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstockDetailStock record);

    int updateByPrimaryKey(InstockDetailStock record);
    
    /**
     * 根据入库单航材查询航材的库存
     * @param mainId
     * @return
     */
    List<InstockDetailStock> selectByMainId(String mainId);
    
    /**
     * 根据 入库单库存 批量删除库存履历
     * @param delList
     * @return
     */
    int deleteKcHisByIds(List<String> delList);
    /**
     * 根据 入库单库存 批量删除
     * @param delList
     * @return
     */
    int deleteByIds(List<String> delList);
    
    /**
     * 将入库信息插入到库存中
     * @param id 入库单航材ID  InstockDetail ID
     * @return
     */
    int insertToStock(String id);
    
    /**
     * 将入库信息插入到检验中
     * @param id 入库单航材ID  InstockDetail ID
     * @return
     */
    int insertToQualityCheck(String id);
    
    /**
     * 根据入库单号查询借入入库明细
     * @param instockId 入库单ID
     * @return
     */
    List<InstockDetailStock> selectByInstockId(String instockId);
    
	/**
	 * 查询入库明细列表
	 * @param record
	 * @return
	 */
	List<InstockDetailStock> queryPageList(InstockDetailStock record);
}