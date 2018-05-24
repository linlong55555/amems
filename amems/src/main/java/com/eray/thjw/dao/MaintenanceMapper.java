package com.eray.thjw.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.Maintenance;

public interface MaintenanceMapper {
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询待生效或生效维修方案信息
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.09.23
	 */
	public List<Maintenance> queryAllPageListEff(Maintenance maintenance);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询维修方案信息
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	public List<Maintenance> queryAllPageList(Maintenance maintenance);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询版本
	 * @param wxfabh,dprtcode
	 * @return List<BigDecimal>
	 * @develop date 2016.08.24
	 */
	public List<BigDecimal> queryBbListByWxfabh(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询机型
	 * @param wxfabh,dprtcode
	 * @return String
	 * @develop date 2016.11.22
	 */
	public String getFjjxByWxfabh(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询维修方案信息
	 * @param wxfabh,dprtcode
	 * @return List<Maintenance>
	 * @develop date 2016.09.24
	 */
	public List<Maintenance> queryAllListByWxfabh(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询最大版本
	 * @param wxfabh,dprtcode
	 * @return BigDecimal
	 * @develop date 2016.08.26
	 */
	public BigDecimal getMaxBbByWxfabh(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询最大版本维修方案信息
	 * @param wxfabh,dprtcode
	 * @return Maintenance
	 * @develop date 2016.12.12
	 */
	public Maintenance queryMaxInfoByWxfabh(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询维修方案信息,去重
	 * @param map
	 * @return List<Maintenance>
	 * @develop date 2016.08.23
	 */
	public List<Maintenance> selectByDprtcodeList(Map<String, Object> map);
	
	/**
	 * @author liub
	 * @description 根据id删除维修方案信息
	 * @param id
	 * @return int
	 * @develop date 2016.08.19
	 */
    int deleteByPrimaryKey(String id);

    /**
	 * @author liub
	 * @description 插入维修方案信息
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.19
	 */
    int insert(Maintenance maintenance);
    /**
	 * @author liub
	 * @description 根据条件插入维修方案信息
	 * @param record
	 * @return int
	 * @develop date 2016.08.15
	 */
    int insertSelective(Maintenance record);

    /**
	 * @author liub
	 * @description 根据主键查询维修方案信息
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
    Maintenance selectByPrimaryKey(String id);
    
    /**
   	 * @author liub
   	 * @description 根据主键查询维修方案及用户信息
   	 * @param id
   	 * @return Maintenance
   	 * @develop date 2016.08.15
   	 */
       Maintenance selectById(String id);
    
    /**
	 * @author liub
	 * @description 根据维修方案编号、版本查询维修方案信息
	 * @param wxfabh,bb,dprtcode
	 * @return Maintenance
	 * @develop date 2016.09.24
	 */
    Maintenance selectByWxfabhBb(@Param("wxfabh") String wxfabh,@Param("bb") String bb,@Param("dprtcode") String dprtcode);

    /**
	 * @author liub
	 * @description 根据条件修改维修方案信息
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.19
	 */
    int updateByPrimaryKeySelective(Maintenance maintenance);

    /**
   	 * @author liub
   	 * @description 修改维修方案信息
   	 * @param record
   	 * @return int
   	 * @develop date 2016.08.19
   	 */
    int updateByPrimaryKey(Maintenance record);
    
    /**
   	 * @author liub
   	 * @description 修改维修方案生效
   	 * @param record
   	 * @return int
   	 * @develop date 2016.09.23
   	 */
    int updateEffective(Maintenance record);
    
    /**
   	 * @author liub
   	 * @description 小于当前版本的维修方案最新标识修改成老版本
   	 * @param map
   	 * @return int
   	 * @develop date 2016.09.24
   	 */
    int updateBbToOld(Map<String, Object> map);
    
    /**
   	 * @author hanwu
   	 * @description 根据飞机机型查询最新的定检项目和监控项目
   	 * @param paramMap
   	 * @return Maintenance
   	 * @develop date 2016.09.22
   	 */
    Maintenance selectByFjjx(Map<String, Object> paramMap);
    
    /**
	 * @author liub
	 * @description 根据查询条件分页查询维修方案信息（去重）
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	public List<Maintenance> queryAllPageDistincList(Maintenance maintenance);
	
	/**
	 * @author liub
	 * @description 检查维修方案机型是否存在
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.10.21
	 */
	public List<Maintenance> checkMaintenance(Maintenance maintenance);
	
	/**
   	 * @author hanwu
   	 * @description 查询未匹配的定检项目以及其维修方案
   	 * @param maintenance
   	 * @return int
   	 * @develop date 2016.11.22
   	 */
    Maintenance findUnmatchItem(Map<String, Object> plane);
    
    /**
   	 * @author sunji
   	 * @description 根据机型查询维修方案
   	 * @param maintenance
   	 * @return int
   	 * @develop date 2016.11.22
   	 */
    Maintenance selectMaintenanceByJx(Maintenance maintenance);
}