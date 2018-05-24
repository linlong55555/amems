package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.DestructionDetail;

public interface DestructionDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(DestructionDetail record);

    int insertSelective(DestructionDetail record);

    DestructionDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DestructionDetail record);

    int updateByPrimaryKey(DestructionDetail record);

    List<DestructionDetail> selectByMainid(String id);
    
    public List<DestructionDetail> queryAll(DestructionDetail destructionDetail);
    
    public List<DestructionDetail> queryAllPageList(DestructionDetail destructionDetail);
    
}