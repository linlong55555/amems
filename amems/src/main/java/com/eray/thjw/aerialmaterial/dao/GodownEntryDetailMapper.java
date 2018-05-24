package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;

public interface GodownEntryDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(GodownEntryDetail record);

    int insertSelective(GodownEntryDetail record);

    GodownEntryDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GodownEntryDetail record);

    int updateByPrimaryKey(GodownEntryDetail record);

	List<GodownEntryDetail> queryGetreceiptSheetDetails(String id)throws RuntimeException;

	GodownEntryDetail selectByPrimary(GodownEntryDetail godownEntryDetail1);
}