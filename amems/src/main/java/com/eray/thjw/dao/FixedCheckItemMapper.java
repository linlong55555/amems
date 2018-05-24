package com.eray.thjw.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.FixedCheckItem;

public interface FixedCheckItemMapper {
	
	/**
	 * @author liub
	 * @description 根据定检项目id集合级联查询监控项目
	 * @param map
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.22
	 */
	public List<FixedCheckItem> findJoinMonitorByIds(Map<String, Object> map);
	
	/**
	 * @author liub
	 * @description 查询未作废、未关闭的定检项目信息
	 * @return List<FixedCheckItem>
	 * @develop date 2016.08.25
	 */
	public List<FixedCheckItem> findAllSelective();
	
	/**
	 * @author liub
	 * @description 查询排序定检项目信息
	 * @param wxfabh,dprtcode
	 * @return List<FixedCheckItem>
	 * @develop date 2016.08.25
	 */
	public List<FixedCheckItem> queryOrderList(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询定检项目信息
	 * @param fixedCheckItem
	 * @return List<Maintenance>
	 * @develop date 2016.08.20
	 */
	public List<FixedCheckItem> queryAllPageList(FixedCheckItem fixedCheckItem);
	
	/**
	 * @author liub
	 * @description 获取最大排序号
	 * @return int
	 * @develop date 2016.08.25
	 */
	public BigDecimal getMaxPxh();
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询定检项目
	 * @param wxfabh,dprtcode
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.24
	 */
	public List<FixedCheckItem> queryByWxfabh(@Param("wxfabh")String wxfabh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据条件查询有差异维修方案的定检项目信息
	 * @param map
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.28
	 */
	public List<FixedCheckItem> queryByMap(Map<String, Object> map);
	
	/**
	 * @author liub
	 * @description 根据id查询定检项目,包含制单人,审核人,批准人
	 * @param id
	 * @return FixedCheckItem
	 * @develop date 2016.09.29
	 */
	public FixedCheckItem selectById(String id);
	
	/**
	 * @author liub
	 * @description 根据定检项目id集合定检项目信息
	 * @param idList
	 * @return List<FixedCheckItem>
	 * @develop date 2017.04.11
	 */
	public List<FixedCheckItem> queryByIdList(List<String> idList);
	
	/**
   	 * @author liub
   	 * @description 修改定检项目生效
   	 * @param map
   	 * @return int
   	 * @develop date 2016.09.25
   	 */
    int updateEffective(Map<String, Object> map);
    
    /**
   	 * @author liub
   	 * @description 将定检项目修改成待生效
   	 * @param map
   	 * @return int
   	 * @develop date 2016.09.29
   	 */
    int updateWaitEffective(Map<String, Object> map);
    
    /**
   	 * @author liub
   	 * @description 小于当前版本的定检项目最新标识修改成老版本
   	 * @param map
   	 * @return int
   	 * @develop date 2016.09.25
   	 */
    int updateBbToOld(Map<String, Object> map);
    
    /**
	 * @author liub
	 * @description  根据维修方案编号、版本作废
	 * @param wxfabh维修方案编号,wxfabb维修方案版本,dprtcode
	 * @return int
	 * @develop date 2016.12.12
	 */
    int cancelByWxfabhAndBb(@Param("wxfabh")String wxfabh,@Param("wxfabb")String wxfabb,@Param("dprtcode")String dprtcode);
    
    /**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
    int updateBatchAudit(Map<String, Object> map);
    
    /**
	 * @author liub
	 * @description 批量批准
	 * @param idList
	 * @develop date 2016.12.28
	 */
    int updateBatchApprove(Map<String, Object> map);
	
    int deleteByPrimaryKey(String id);

    int insert(FixedCheckItem record);

    int insertSelective(FixedCheckItem record);

    FixedCheckItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FixedCheckItem record);

    int updateByPrimaryKey(FixedCheckItem record);
    
    FixedCheckItem selectByZxbsAndBh(FixedCheckItem record);
    
}