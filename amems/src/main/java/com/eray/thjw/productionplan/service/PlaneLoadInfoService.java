package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneLoadInfo;

public interface PlaneLoadInfoService {
	
	/***sunji
	 * 根据mianid查询数据
	 * @param mainid
	 */
	public List<PlaneLoadInfo> getInfoByMainid(String mainid);
	/***sunji
	 * 根据List<String> id删除数据
	 * @param ids
	 */
	public void deleteIds(Map<String,Object> paramMap);
	
	
}
