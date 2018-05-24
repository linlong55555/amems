package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialToPlaneModel;

public interface MaterialToPlaneModelMapper {
	
	/**
	 * @author liub
	 * @description  根据航材id查询航材机型关系表
	 * @param mainId
	 * @return List<MaterialToPlaneModel>
	 * @develop date 2016.09.20
	 */
	public List<MaterialToPlaneModel> queryByMainId(String mainId);
	
	/**
	 * @author liub
	 * @description  根据航材id集合查询航材机型关系表
	 * @param map
	 * @return List<MaterialToPlaneModel>
	 * @develop date 2016.09.20
	 */
	public List<MaterialToPlaneModel> queryByMainIdList(Map<String, Object> map);
	
    int deleteByPrimaryKey(String id);

    int insert(MaterialToPlaneModel record);

    int insertSelective(MaterialToPlaneModel record);

    MaterialToPlaneModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialToPlaneModel record);

    int updateByPrimaryKey(MaterialToPlaneModel record);
      
    void updateByMainid(MaterialToPlaneModel record);
    
    void deleteByMainid(String mainid);
}