package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.aerialmaterial.po.ReceiptSheetDetail;

public interface ReceiptSheetDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReceiptSheetDetail record);

    int insertSelective(ReceiptSheetDetail record);

    ReceiptSheetDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceiptSheetDetail record);

    int updateByPrimaryKey(ReceiptSheetDetail record);
    
    int deleteNotExist(ReceiptSheet receipt);

	ReceiptSheetDetail selectByPrimary(ReceiptSheetDetail receiptSheetDetail);

	List<ReceiptSheetDetail> selectByMaind(String mainid);
}