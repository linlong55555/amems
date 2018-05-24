package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixedCheckWorkContent;

public interface FixedCheckWorkContentMapper {
	
	/**
	 * @author liub
	 * @description 根据定检项目id工作内容数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<FixedCheckWorkContent> queryListByDjxmid(String djxmid);
	
	/**
	 * @author liub
	 * @description 根据定检项目id查询内部序号最大值
	 * @param 
	 * @return String
	 * @develop date 2016.08.29
	 */
	public String queryNbxhByDjxmid(String djxmid);
	
	/**
	 * @author liub
	 * @description 根据定检项目id查询所有有效的工作内容
	 * @param djxmid
	 * @return List<FixedCheckWorkContent>
	 * @develop date 2017.02.14
	 */
	public List<FixedCheckWorkContent> queryListSelective(String djxmid);
	
	/**
	 * @author liub
	 * @description 根据条件分页查询差异列表
	 * @param baseEntity
	 * @return List<Map<String, Object>> 
	 * @develop date 2017.02.09
	 */
	public List<Map<String, Object>> queryDiffPageList(BaseEntity baseEntity);
	
	int deleteByDjxmid(String djxmid);
	
    int deleteByPrimaryKey(String id);

    int insert(FixedCheckWorkContent record);

    int insertSelective(FixedCheckWorkContent record);

    FixedCheckWorkContent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FixedCheckWorkContent record);

    int updateByPrimaryKey(FixedCheckWorkContent record);
}