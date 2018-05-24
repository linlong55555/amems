package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.PlaneBase;

public interface PlaneBaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlaneBase record);

    int insertSelective(PlaneBase record);

    PlaneBase selectByPrimaryKey(String id);

    PlaneBase selectByPrimary(String jdms);
    
    int updateByPrimaryKeySelective(PlaneBase record);

    int updateByPrimaryKey(PlaneBase record);
    
    List<PlaneBase> findAllBase(PlaneBase planeBase);
    
    List<PlaneBase> findBaseByDprtcode(String dprtcode);
    
	PlaneBase selectByPrimary1(PlaneBase planeBase1);
}