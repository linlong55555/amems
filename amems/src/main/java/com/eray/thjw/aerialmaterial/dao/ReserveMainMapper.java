package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ReserveMain;

public interface ReserveMainMapper {
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订单信息
	 * @param reserve
	 * @return List<ReserveMain>
	 * @develop date 2016.10.12
	 */
	public List<ReserveMain> queryAllPageList(ReserveMain record);
		
	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材提订单信息
	 * @param reserve
	 * @return List<ReserveMain>
	 * @develop date 2016.10.14
	 */
	public List<ReserveMain> queryApprovePageList(ReserveMain record);
		
	/**
	 * @author liub
	 * @description  根据id查询送修单、送修航材信息
	 * @param id
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	public Map<String, Object> selectMapById(String id);
	
	/**
	 * @author liub
	 * @description  根据id查询提订单信息
	 * @param id
	 * @return ReserveMain
	 * @develop date 2016.12.19
	 */
	public ReserveMain selectById(String id);
	
    int deleteByPrimaryKey(String id);

    int insert(ReserveMain record);

    int insertSelective(ReserveMain record);

    ReserveMain selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReserveMain record);

    int updateByPrimaryKey(ReserveMain record);
}