package com.eray.thjw.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Maintenance;


public interface MaintenanceService {
	
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
	 * @author sunji
	 * @description 根据查询条件分页查询维修方案信息(去重)
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	public List<Maintenance> queryAllPageDistincList(Maintenance maintenance);
	
	/**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询维修方案信息,去重
	 * @param
	 * @return List<Map<String, Object>
	 * @develop date 2016.08.23
	 */
	public List<Map<String, Object>> selectByDprtcodeList();
	
	/**
	 * @author liub
	 * @description 保存维修方案信息
	 * @param maintenance
	 * @return String
	 * @develop date 2016.08.15
	 */
	public String saveMaintenance(Maintenance maintenance) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改维修方案信息
	 * @param maintenance
	 * @develop date 2016.08.15
	 */
	public void edit(Maintenance maintenance) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 改版维修方案
	 * @param maintenance
	 * @return String
	 * @develop date 2016.08.17
	 */
	public String modifyMaintenance(Maintenance maintenance,String xdtzsidStr) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  提交生产
	 * @param maintenance
	 * @develop date 2016.08.18
	 */
	public void updateSbProdMaintenance(Maintenance maintenance) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @develop date 2016.08.18
	 */
	public void deleteMaintenance(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据主键查询维修方案信息
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public Maintenance getByPrimaryKey(String id);
	
	/**
	 * @author liub
	 * @description 根据主键查询维修方案及用户信息
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public Maintenance selectById(String id);
	
	/**
	 * @author liub
	 * @description 检查维修方案修改权限
	 * @param id
	 * @develop date 2016.08.17
	 */
	public void checkUpdMt(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查维修方案改版权限
	 * @param id
	 * @develop date 2016.08.17
	 */
	public void checkMdfMt(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查维修方案提交生产权限
	 * @param id
	 * @develop date 2016.08.18
	 */
	public void checkSbProdMt(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查维修方案生效状态
	 * @param wxfabh,bb,dprtcode
	 * @develop date 2016.08.18
	 */
	public void checkEffectiveState(String wxfabh,String bb,String dprtcode) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据维修方案、版本查询维修方案信息
	 * @param wxfabh,bb,dprtcode
	 * @return Maintenance
	 * @develop date 2016.12.12
	 */
	public Maintenance selectByWxfabhBb(String wxfabh,String bb,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询版本集合
	 * @param wxfabh,dprtcode
	 * @return List<BigDecimal>
	 * @develop date 2016.08.24
	 */
	public List<BigDecimal> queryBbListByWxfabh(String wxfabh,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询机型
	 * @param wxfabh,dprtcode
	 * @return String
	 * @develop date 2016.08.24
	 */
	public String getFjjxByWxfabh(String wxfabh,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询最大版本
	 * @param wxfabh,dprtcode
	 * @return BigDecimal
	 * @develop date 2016.08.26
	 */
	public BigDecimal getMaxBbByWxfabh(String wxfabh,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询最大版本维修方案信息
	 * @param wxfabh,dprtcode
	 * @return Maintenance
	 * @develop date 2016.12.12
	 */
	public Maintenance queryMaxInfoByWxfabh(String wxfabh,String dprtcode);
	
	/**
	 * @author hanwu
	 * @description 根据飞机机型查询最新的定检项目和监控项目
	 * @param paramMap
	 * @return Maintenance
	 * @develop date 2016.09.22
	 */
	public Maintenance selectByFjjx(Map<String, Object> paramMap);
	
	/**
	 * @author liub
	 * @description 检查维修方案改版生效权限
	 * @param id
	 * @develop date 2016.09.23
	 */
	public void checkEffective(String id)throws BusinessException;
	
	/**
	 * @author liub
	 * @description 维修方案生效
	 * @param wxfaId,sxjyrid
	 * @develop date 2016.09.23
	 */
	public void updateEffective(String wxfaId,String sxjyrid) throws BusinessException;
	
	/**
	 * @author hanwu
	 * @description 查询未匹配的定检项目以及其维修方案
	 * @param fjjx
	 * @return
	 * @throws
	 */
	public Maintenance findUnmatchItem(Map<String, Object> plane);
	/**
   	 * @author sunji
   	 * @description 根据机型查询维修方案
   	 * @param maintenance
   	 * @return int
   	 * @develop date 2016.11.22
   	 */
	public Maintenance selectMaintenanceByJx(Maintenance maintenance);
}
