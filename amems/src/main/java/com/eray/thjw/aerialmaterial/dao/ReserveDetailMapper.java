package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ReserveDetail;

public interface ReserveDetailMapper {
	
	/**
	 * @author liub
	 * @description  根据提订单id集合查询提订详情信息
	 * @param mainid
	 * @return List<ReserveDetail>
	 * @develop date 2016.10.13
	 */
	public List<ReserveDetail> queryReserveDetailListByMainId(String mainid);
	
	/**
	 * @author liub
	 * @description  根据提订单id集合查询提订详情信息
	 * @param mainidList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	public List<Map<String, Object>> queryReserveDetailListByIds(List<String> mainidList);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订详情信息(弹窗)
	 * @param reserveDetail
	 * @return Map<String, Object>
	 * @develop date 2016.11.07
	 */
	public List<Map<String, Object>> queryReserveDetailPageList(ReserveDetail reserveDetail);
	
    int deleteByPrimaryKey(String id);

    int insert(ReserveDetail record);

    int insertSelective(ReserveDetail record);

    ReserveDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReserveDetail record);

    int updateByPrimaryKey(ReserveDetail record);
    
    /**
	 * @author liub
	 * @description  根据提订单详情id集合查询提订详情信息
	 * @param idList
	 * @return List<ReserveDetail>
	 */
	public List<ReserveDetail> queryByIds(List<String> idList);
	
	/**
	 * @author liub
	 * @description 修改已采购数量
	 * @param record
	 */
	public int updateYcgsl(ReserveDetail record);
}