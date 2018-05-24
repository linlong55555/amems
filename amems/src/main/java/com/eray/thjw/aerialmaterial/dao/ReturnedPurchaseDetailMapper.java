package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ReturnedPurchaseDetail;

public interface ReturnedPurchaseDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReturnedPurchaseDetail record);

    int insertSelective(ReturnedPurchaseDetail record);

    ReturnedPurchaseDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReturnedPurchaseDetail record);

    int updateByPrimaryKey(ReturnedPurchaseDetail record);

	List<ReturnedPurchaseDetail> selectByMainId(String id);

}