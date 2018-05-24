package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.InstockDetail;
import com.eray.thjw.po.BaseEntity;

/**
 * 入库单航材
 * @author xu.yong
 *
 */
public interface InstockDetailMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(InstockDetail record);

    int insertSelective(InstockDetail record);

    InstockDetail selectByPrimaryKey(String id);
    
    InstockDetail selectById(String id);

    int updateByPrimaryKeySelective(InstockDetail record);

    int updateByPrimaryKey(InstockDetail record);
    
    /**
     * 分页查询采购待入库 航材 
     * @param record
     * @return
     */
    List<InstockDetail> queryPurchasePage(InstockDetail record);
    
    /**
     * 撤销合同到货数量
     * @param id
     * @return
     */
    int cancelContractDhsl(String id);
    /**
     * 作废时更改合同状态
     * @param id
     * @return
     */
    int cancelContractZt(String id);
    
    
    /**
     * 分页送修采购待入库 航材 
     * @param record
     * @return
     */
    List<InstockDetail> queryRepairPage(BaseEntity record);

    /**
     * 查询入库明细总数
     * @param record
     * @return
     */
	int queryPageCount(InstockDetail record);

	/**
     * 查询入库明细列表
     * @param record
     * @return
     */
	List<InstockDetail> queryPageList(InstockDetail record);
    
    
}