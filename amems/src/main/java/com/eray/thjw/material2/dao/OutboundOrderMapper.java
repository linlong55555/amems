package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.OutboundOrder;
/**
 * 
 * @Description 出库单mapper
 * @CreateTime 2018年3月15日 下午3:12:40
 * @CreateBy 林龙
 */
public interface OutboundOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutboundOrder record);

    int insertSelective(OutboundOrder record);

    OutboundOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutboundOrder record);

    int updateByPrimaryKey(OutboundOrder record);

	int queryCount(OutboundOrder tec);

	int getCount4Validation(OutboundOrder outboundOrder);

	List<OutboundOrder> queryAllPageList(OutboundOrder outboundOrder);

	OutboundOrder getByStockoutId(OutboundOrder outboundOrder);
}