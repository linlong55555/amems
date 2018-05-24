package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.Inspection;

public interface GodownEntryMapper {
    int deleteByPrimaryKey(String id);

    int insert(GodownEntry record);

    int insertSelective(GodownEntry record);

    GodownEntry selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GodownEntry record);

    int updateByPrimaryKey(GodownEntry record);

	List<GodownEntry> queryAllPageList(GodownEntry godownEntry)throws RuntimeException;

}