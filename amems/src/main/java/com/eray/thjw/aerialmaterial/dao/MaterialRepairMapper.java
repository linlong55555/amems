package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialRepair;

public interface MaterialRepairMapper {
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材送修单信息
	 * @param record
	 * @return List<MaterialRepair>
	 * @develop date 2016.11.01
	 */
	public List<MaterialRepair> queryRepairPageList(MaterialRepair record);
		
	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材送修单信息
	 * @param record
	 * @return List<MaterialRepair>
	 * @develop date 2016.11.02
	 */
	public List<MaterialRepair> queryApproveRepairPageList(MaterialRepair record);
		
	/**
	 * @author liub
	 * @description  根据查询条件分页查询送修航材信息(弹窗)
	 * @param materialRepair
	 * @return Map<String, Object>
	 * @develop date 2016.11.07
	 */
	public List<Map<String, Object>> queryRepairMaterialPageList(MaterialRepair record);
		
    int deleteByPrimaryKey(String id);

    int insert(MaterialRepair record);

    int insertSelective(MaterialRepair record);

    MaterialRepair selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialRepair record);

    int updateByPrimaryKey(MaterialRepair record);
    
    /**
	 * @author liub
	 * @description  根据mainid修改送修航材询价状态为待询价
	 * @param mainid
	 * @return int
	 * @develop date 2016.11.02
	 */
    int updateXjztByMainid(String mainid);
    
    /**
	 * @author liub
	 * @description  根据mainid修改送修航材
	 * @param record
	 * @return int
	 * @develop date 2016.11.03
	 */
    int updateByMainid(MaterialRepair record);
    
    /**
	 * 按条件查询一页送修出库
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaterialRepair> queryAllPageList(MaterialRepair materialRepair)  throws RuntimeException;
	
	 public MaterialRepair queryAllList(MaterialRepair materialRepair)
				throws RuntimeException;
}