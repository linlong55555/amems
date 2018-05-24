package com.eray.thjw.project.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project.po.MinimumEquipmentList;



/**
 * @author liub
 * @description 
 */
public interface MelDetailService {
	
	public String save(MinimumEquipmentList minimumEquipmentList) throws BusinessException;
	
	public String edit(MinimumEquipmentList minimumEquipmentList) throws BusinessException;
	
	public List<MinimumEquipmentList> queryAllPageList(MinimumEquipmentList minimumEquipmentList);
	
	public MinimumEquipmentList selectById(String id);
	
}
