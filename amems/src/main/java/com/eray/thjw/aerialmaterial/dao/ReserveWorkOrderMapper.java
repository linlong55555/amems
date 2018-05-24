package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ReserveWorkOrder;

public interface ReserveWorkOrderMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteByMainId(String mainid);

    int insert(ReserveWorkOrder record);

    int insertSelective(ReserveWorkOrder record);

    ReserveWorkOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReserveWorkOrder record);

    int updateByPrimaryKey(ReserveWorkOrder record);
    
    /**
	 * @author liub
	 * @description  根据mainid查询相关工单信息
	 * @param mainid
	 * @return List<ReserveWorkOrder>
	 * @develop date 2016.11.18
	 */
	public List<ReserveWorkOrder> queryWorkOrderListByMainId(String mainid);
}