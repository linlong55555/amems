package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.productionplan.po.MaintenanceFailureSummary;



public interface MaintenanceFailureSummarySerivce {
	
	/**
	 * @author liub
	 * @description 增加维修故障总结
	 * @param summary
	 * @return String
	 * @develop date 2017.01.04
	 */
	public String add(MaintenanceFailureSummary summary);
	
	/**
	 * @author liub
	 * @description 修改维修故障总结
	 * @param summary
	 * @develop date 2017.01.04
	 */
	public void edit(MaintenanceFailureSummary summary);
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,id
	 * @develop date 2017.01.04
	 */
	public void deleteSummary(String id);
	
	/**
	 * @author liub
	 * @description 根据条件分页查询维修故障总结列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.04
	 */
	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity);
	
	/**
	 * @author liub
	 * @description 根据航材id查询维修故障总结及对应详情信息
	 * @param id
	 * @return MaintenanceFailureSummary
	 * @develop date 2017.01.04
	 */
	public MaintenanceFailureSummary selectById(String id);
	
	/**
	 * @author liub
	 * @description  根据条件分页查询维修故障总结列表(lucene)
	 * @param baseEntity,dprtList
	 * @return Map<String,Object>
	 * @develop date 2017.01.06
	 */
	public Map<String,Object> queryLucenePageList(BaseEntity baseEntity, List<String> dprtList);
	
}
