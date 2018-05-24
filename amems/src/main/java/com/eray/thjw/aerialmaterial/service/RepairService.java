package com.eray.thjw.aerialmaterial.service;

import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.exception.BusinessException;


public interface RepairService {
	
	
	/**
	 * @author liub
	 * @description 新增送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	public String add(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查送修单编辑权限
	 * @param id
	 * @develop date 2016.11.01
	 */
	public void checkEdit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 编辑送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	public void edit(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 编辑送修详情
	 * @param materialRepair
	 * @develop date 2016.12.16
	 * @throws BusinessException 
	 */
	public void updateMaterialRepair(MaterialRepair materialRepair) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查送修单审核权限
	 * @param id
	 * @develop date 2016.11.02
	 */
	public void checkAudit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 审核送修单
	 * @param reserve
	 * @develop date 2016.11.02
	 * @throws BusinessException 
	 */
	public void editAudit(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @return
	 * @develop date 2016.11.03
	 */
	public void cancel(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  根据id查询送修单、送修航材信息
	 * @param id
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	public Map<String, Object> selectMapById(String id);
	
	/**
	 * @author liub
	 * @description 指定结束送修单信息
	 * @param reserve
	 * @return
	 * @develop date 2016.11.03
	 */
	public void updateShutDown(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 编辑送修单信息
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 */
	public void updateByPrimaryKeySelective(ReserveMain reserve) throws BusinessException;
	
}
