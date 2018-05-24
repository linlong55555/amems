package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.OrderAttachment;

public interface OrderAttachmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderAttachment record);

    int insertSelective(OrderAttachment record);

    OrderAttachment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderAttachment record);

    int updateByPrimaryKey(OrderAttachment record);
    
    List<OrderAttachment> queryAll(String id);
    
    List<OrderAttachment> queryAllByMainids(List<String> ids);
}