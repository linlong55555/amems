package com.eray.thjw.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project.po.MinimumEquipmentList;

public interface MinimumEquipmentListMapper {
	
	int deleteByPrimaryKey(String id);

	int insert(MinimumEquipmentList record);

	int insertSelective(MinimumEquipmentList record);

	MinimumEquipmentList selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MinimumEquipmentList record);

	int updateByPrimaryKey(MinimumEquipmentList record);
	
	int updateByNotEffective(@Param("jx")String jx,@Param("dprtcode")String dprtcode);
	
	public List<MinimumEquipmentList> queryAllPageList(MinimumEquipmentList record);
	
	public MinimumEquipmentList selectById(String id);
	
	public List<MinimumEquipmentList> checkExists(MinimumEquipmentList record);
}