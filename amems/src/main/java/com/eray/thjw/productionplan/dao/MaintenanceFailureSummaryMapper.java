package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.productionplan.po.MaintenanceFailureSummary;

public interface MaintenanceFailureSummaryMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceFailureSummary record);

    int insertSelective(MaintenanceFailureSummary record);

    MaintenanceFailureSummary selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceFailureSummary record);

    int updateByPrimaryKey(MaintenanceFailureSummary record);
    
    /**
	 * @author liub
	 * @description  根据条件分页查询维修故障总结列表
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
}