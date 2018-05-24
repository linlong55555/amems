package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.OutboundOrderDetails;
/**
 * 
 * @Description 出库明细mapper
 * @CreateTime 2018年3月15日 下午3:12:24
 * @CreateBy 林龙
 */
public interface OutboundOrderDetailsMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutboundOrderDetails record);

    int insertSelective(OutboundOrderDetails record);

    OutboundOrderDetails selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutboundOrderDetails record);

    int updateByPrimaryKey(OutboundOrderDetails record);

	List<OutboundOrderDetails> getDepartmentByYwid(String id);

	void deleteByMainid(String id);

	void delete4Batch(List<String> list);
}