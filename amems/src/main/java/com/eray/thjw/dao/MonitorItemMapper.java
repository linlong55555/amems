package com.eray.thjw.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.MonitorItem;

public interface MonitorItemMapper {
	
	/**
	 * @author liub
	 * @description 根据定检项目id查询监控项目数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<MonitorItem> queryListByDjxmid(String djxmid);
	
	/**
	 * @author liub
	 * @description 根据定检项目id,监控项目编号,监控分类编号查询监控项目数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public MonitorItem selectByDjxmidAndJklAndJkf(@Param("djxmid") String djxmid,@Param("jklbh") String jklbh,@Param(value = "jkflbh")String jkflbh);
	
	int deleteByDjxmid(String djxmid);
	
    int deleteByPrimaryKey(String id);

    int insert(MonitorItem record);

    int insertSelective(MonitorItem record);

    MonitorItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitorItem record);

    int updateByPrimaryKey(MonitorItem record);
    
    /**
	 * @author liub
	 * @description 修改定检监控项目
	 * @param djxmid
	 * @develop date 2016.08.29
	 */
	public int updateByDjxmid(String djxmid);
	
	 /**
		 * @author liub
		 * @description 设置无效
		 * @param id
		 * @develop date 2017.02.28
		 */
		public int updateNotEffById(String id);

}