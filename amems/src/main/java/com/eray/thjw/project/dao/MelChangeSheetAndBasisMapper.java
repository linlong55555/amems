package com.eray.thjw.project.dao;

import java.util.List;

import com.eray.thjw.project.po.MelChangeSheetAndBasis;

public interface MelChangeSheetAndBasisMapper {
    int deleteByPrimaryKey(String id);

    int insert(MelChangeSheetAndBasis record);

    int insertSelective(MelChangeSheetAndBasis record);

    MelChangeSheetAndBasis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MelChangeSheetAndBasis record);

    int updateByPrimaryKey(MelChangeSheetAndBasis record);
    
    int insertBatch(List<MelChangeSheetAndBasis> list);
    
    List<MelChangeSheetAndBasis> queryByMainid(String mainid);
    
}