package com.eray.thjw.outfield.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.outfield.po.ToolsMonitorDetail;
import com.eray.thjw.po.BaseEntity;

public interface ToolsMonitorDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ToolsMonitorDetail record);

    int insertSelective(ToolsMonitorDetail record);

    ToolsMonitorDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ToolsMonitorDetail record);

    int updateByPrimaryKey(ToolsMonitorDetail record);
    
    /**
	 * @author liub
	 * @description  根据mainid、编号删除计量工具监控详情数据
	 * @param record
	 * @return int
	 * @develop date 2016.12.06
	 */
   	public int deleteDetailByMainIdAndBjxlh(ToolsMonitorDetail record);
   	
   	/**
	 * @author liub
	 * @description  根据id删除计量工具监控详情
	 * @param id
	 * @return int
	 * @develop date 2016.12.14
	 */
   	public int deleteDetail(String id);
    
    /**
	 * @author liub
	 * @description  根据mainid编号查询最大校验日期
	 * @param mainid,bjxlh
	 * @return ToolsMonitorDetail
	 * @develop date 2016.12.02
	 */
   	public ToolsMonitorDetail getByMainidAndBjxlh(@Param("mainid")String mainid,@Param("bjxlh")String bjxlh);
   	
   	/**
	 * @author liub
	 * @description  根据计量工具id查询详情信息(最新检查日期)
	 * @param mainid
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.06
	 */
   	public List<ToolsMonitorDetail> queryDetailByMainId(String mainid);
   	
   	/**
	 * @author liub
	 * @description  根据计量工具id,编号查询历史详情信息
	 * @param mainid,bjxlh
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.14
	 */
   	public List<ToolsMonitorDetail> queryDetailByMainIdAndBjxlh(@Param("mainid")String mainid,@Param("bjxlh")String bjxlh);
   	
   	/**
	 * @author liub
	 * @description  根据条件分页查询计量监控列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.15
	 */
   	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity);
   		
}