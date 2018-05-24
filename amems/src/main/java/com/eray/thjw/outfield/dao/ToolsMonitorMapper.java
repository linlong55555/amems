package com.eray.thjw.outfield.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.outfield.po.ToolsMonitor;

public interface ToolsMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(ToolsMonitor record);

    int insertSelective(ToolsMonitor record);

    ToolsMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ToolsMonitor record);

    int updateByPrimaryKey(ToolsMonitor record);
    
    /**
	 * @author liub
	 * @description  修改计量工具监控为计量或非计量
	 * @param record
	 * @return int
	 * @develop date 2016.12.02
	 */
   	public int updateByIdAndIsJl(ToolsMonitor record);
    
    /**
	 * @author liub
	 * @description  根据id获取计量工具监控map
	 * @param id
	 * @return Map<String, Object>
	 * @develop date 2016.12.02
	 */
   	public Map<String, Object> getToolsMonitorMap(String id);
    
    /**
	 * @author liub
	 * @description  根据条件部件id和序列号获取数据
	 * @param bjid,bjxlh
	 * @return ToolsMonitor
	 * @develop date 2016.12.01
	 */
   	public ToolsMonitor getByBjidAndBjxlh(@Param("bjid")String bjid,@Param("bjxlh")String bjxlh);
    
    /**
	 * @author liub
	 * @description  根据条件分页查询库存及外场虚拟库存列表
	 * @param toolsMonitor
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.30
	 */
   	public List<Map<String, Object>> queryAllStockPageList(ToolsMonitor record);
   		
}