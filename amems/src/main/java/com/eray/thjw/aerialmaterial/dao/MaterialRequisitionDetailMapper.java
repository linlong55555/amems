package com.eray.thjw.aerialmaterial.dao;

import java.math.BigDecimal;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;

/**
 * 航材领用明细DAO
 * @author xu.yong
 *
 */
public interface MaterialRequisitionDetailMapper {
    int deleteByPrimaryKey(String id);
    
    /**
     * 根据多个ID删除数据
     * @param idList
     * @return
     */
    int deleteByIds(List<String> idList);

    int insert(MaterialRequisitionDetail record);

    int insertSelective(MaterialRequisitionDetail record);

    MaterialRequisitionDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialRequisitionDetail record);

    int updateByPrimaryKey(MaterialRequisitionDetail record);
    
    /**
     * 根据领用单ID和库存ID查询有效明细数
     * @param mainId 领用单ID
     * @param bjid 部件ID
     * @return 
     */
    int selectByMainIdKcIdCount(String mainId, String kcid);
    
    /**
     * 根据mainId查询领用航材信息
     * @param mainId
     * @return
     */
    List<MaterialRequisitionDetail> selectByMainId(String mainId);
    
    /**
     * 更新申请领用数量
     * @param modifyList
     */
    void updateNumber(String id, BigDecimal sqlysl);
    
    
	/**
	 * 按条件查询一页领用申请明细
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaterialRequisitionDetail> queryAllPageList(MaterialRequisitionDetail materialRequisitionDetail)  throws RuntimeException;
	
 	/**
	 * 按条件查询领用申请明细总数
	 * @param param
	 * @param pagination
	 * @return
	 */
	 int queryCount(MaterialRequisitionDetail materialRequisitionDetail) throws RuntimeException;
	 
	 public void updateByPrimaryKeySelectives(
				MaterialRequisitionDetail materialRequisitionDetail)
				throws RuntimeException;
    
}