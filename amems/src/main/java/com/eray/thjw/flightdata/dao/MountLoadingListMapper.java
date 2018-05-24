package com.eray.thjw.flightdata.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.flightdata.po.MountLoadingList;

public interface MountLoadingListMapper {
    int deleteByPrimaryKey(String id);

    int insert(MountLoadingList record);

    int insertSelective(MountLoadingList record);

    MountLoadingList selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MountLoadingList record);

    int updateByPrimaryKey(MountLoadingList record);
    
    int save(MountLoadingList record);
    
    int deleteInvalid(Map<String, Object> paramMap);
    
    int delete(MountLoadingList record);
    
    List<MountLoadingList> getZsjxx(String fxjlgljlid);
}