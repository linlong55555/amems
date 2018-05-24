package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.dao.UpMapper;

public interface OtherBorrowApplyDetailMapper extends UpMapper{
    int deleteByPrimaryKey(String id);

    int insert(BorrowApplyDetail record);

    int insertSelective(BorrowApplyDetail record);

    BorrowApplyDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BorrowApplyDetail record);

    int updateByPrimaryKey(BorrowApplyDetail record);
    
    List<BorrowApplyDetail> selectByMainId(String mainid);
}