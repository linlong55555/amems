package com.eray.thjw.productionplan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.productionplan.dao.PlaneLoadInfoMapper;
import com.eray.thjw.productionplan.po.PlaneLoadInfo;
import com.eray.thjw.productionplan.service.PlaneLoadInfoService;
/**
 * 飞机震动service
 * @author sunji
 *
 */
@Service
public class PlaneLoadInfoServiceImpl implements PlaneLoadInfoService{

	@Resource
	private PlaneLoadInfoMapper planeLoadInfoMapper;
	
	/***sunji
	 * 根据mianid查询数据
	 * @param mainid
	 */
	public List<PlaneLoadInfo> getInfoByMainid(String mainid) {
		return planeLoadInfoMapper.getInfoByMainid(mainid);
	}

	/***sunji
	 * 根据List<String> id删除数据
	 * @param ids
	 */
	public void deleteIds(Map<String, Object> paramMap) {
		planeLoadInfoMapper.deleteIds(paramMap);
		
	}

}
