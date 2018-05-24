package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.FixedCheckWorkPlane;

public interface FixedCheckWorkPlaneMapper {
	
	/**
	 * @author liub
	 * @description 根据mainid删除定检工作对应飞机关系
	 * @param mainid
	 * @develop date 2016.10.24
	 */
	int deleteByMainId(String mainid);
	
    int deleteByPrimaryKey(String id);

    int insert(FixedCheckWorkPlane record);

    int insertSelective(FixedCheckWorkPlane record);

    FixedCheckWorkPlane selectByPrimaryKey(String id);
    
    /**
	 * @author liub
	 * @description 根据mainid查询定检工作对应飞机关系
	 * @param mainid
	 * @develop date 2017.3.01
	 */
    List<FixedCheckWorkPlane> selectByMainid(String mainid);

    int updateByPrimaryKeySelective(FixedCheckWorkPlane record);

    int updateByPrimaryKey(FixedCheckWorkPlane record);
}