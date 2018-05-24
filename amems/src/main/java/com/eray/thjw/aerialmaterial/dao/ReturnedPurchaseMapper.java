package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ReturnedPurchase;
import com.eray.thjw.aerialmaterial.po.ReturnedPurchaseDetail;

public interface ReturnedPurchaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReturnedPurchase record);

    int insertSelective(ReturnedPurchase record);

    ReturnedPurchase selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReturnedPurchase record);

    int updateByPrimaryKey(ReturnedPurchase record);

	List<ReturnedPurchase> queryAllPageList(ReturnedPurchase returnedPurchase) throws RuntimeException;

	List<ReturnedPurchaseDetail> selectByMainId(String id);
	
	List<ReturnedPurchase> queryByIds(Map<String, Object> map);
}