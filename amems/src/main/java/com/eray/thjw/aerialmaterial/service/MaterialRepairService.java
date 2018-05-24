package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialRepair;

public interface MaterialRepairService {

	/**
	 * 按条件查询一页送修出库
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaterialRepair> queryAllPageList(MaterialRepair materialRepair)  throws RuntimeException;
	 
	/**
	 * 按条件查询一页送修出库
	 * @param param
	 * @param pagination
	 * @return
	 */
	 MaterialRepair queryAllList(MaterialRepair materialRepair)  throws RuntimeException;
	
	 /**
	 * @author liub
	 * @description  根据查询条件分页查询航材送修单信息
	 * @param materialRepair
	 * @return List<MaterialRepair>
	 * @develop date 2016.11.01
	 */
	public Map<String, Object> queryRepairPageList(MaterialRepair materialRepair);
	
	 /**
		 * @author liub
		 * @description  根据查询条件分页查询审核航材送修单信息
		 * @param materialRepair
		 * @return List<MaterialRepair>
		 * @develop date 2016.11.01
		 */
		public Map<String, Object> queryApproveRepairPageList(MaterialRepair materialRepair);
		
		/**
		 * @author liub
		 * @description  根据查询条件分页查询送修航材信息(弹窗)
		 * @param reserveDetail
		 * @return Map<String, Object>
		 * @develop date 2016.11.07
		 */
		public List<Map<String, Object>> queryRepairMaterialPageList(MaterialRepair materialRepair);
		
}