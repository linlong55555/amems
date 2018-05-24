package com.eray.thjw.aerialmaterial.dao;

import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;

/**
 * b_h_01201 外派部件对应库存信息
 * @author xu.yong
 *
 */
public interface ExpatriateDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpatriateDetail record);

    int insertSelective(ExpatriateDetail record);

    ExpatriateDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpatriateDetail record);

    int updateByPrimaryKey(ExpatriateDetail record);
    
	public ExpatriateDetail queryByKey(String id, String kcllid) throws RuntimeException ;
	
	/**
	 * 根据mainid以及相关单据ID进行删除
	 * @param mainId
	 * @param djid
	 * @return
	 */
	int deleteByMainIdAndDjid(String mainId, String djid);
}