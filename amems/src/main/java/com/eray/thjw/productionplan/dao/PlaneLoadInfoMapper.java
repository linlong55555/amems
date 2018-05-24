package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneLoadInfo;

public interface PlaneLoadInfoMapper {
   
	void insertPlaneLoadInfo(PlaneLoadInfo record);
	
	List<PlaneLoadInfo> getInfoByMainid(String mainid);
	
	int deleteIds(Map<String, Object> map);
	
	int deleteByMainid(String mainid);

	void updateByPrimaryKeySelective(PlaneLoadInfo record);
	
	//根据idList查询数据
	List<PlaneLoadInfo> getInfoByids(List<String> idList);
	
}