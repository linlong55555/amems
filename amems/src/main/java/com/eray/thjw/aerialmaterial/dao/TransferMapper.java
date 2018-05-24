package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Transfer;

public interface TransferMapper {
	
	int insertSelective(Transfer record);   //想移库单插入数据
    
	List<Transfer> selectTransferList(Transfer record);  //分页查询移库列表
	
	int deleteByPrimaryKey(String id);     //插销移库操作
	
	Transfer selectByid(String id);        //根据id查询一个移库记录
    
	List<Transfer> selectByKey(String id);  //根绝移库单号查询移库单集合
	
	int insert(Transfer record);
    int updateByPrimaryKeySelective(Transfer record);
    int updateByPrimaryKey(Transfer record);
}