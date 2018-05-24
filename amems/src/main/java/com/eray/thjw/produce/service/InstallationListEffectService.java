package com.eray.thjw.produce.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.ComponentUse;
import com.eray.thjw.produce.po.InstallationListEditable;



/**
 * @Description 装机清单生效service
 * @CreateTime 2017年10月10日 下午4:15:49
 * @CreateBy 韩武
 */
public interface InstallationListEffectService {

	/**
	 * @Description 装机清单生效
	 * @CreateTime 2017年10月10日 下午4:15:35
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @throws BusinessException
	 */
	void doEffect(Aircraftinfo aircraftinfo)throws BusinessException ;
	
	
	/**
	 * @Description  装上件从临时区生效
	 * @CreateTime 2017年11月17日 上午10:56:35
	 * @CreateBy 徐勇
	 * @param install 装机清单信息
	 * @param componentUsage 部件使用情况
	 * @param workIOId 工单拆装记录id
	 * @throws BusinessException 
	 */
	public void doInstalledEffectFromTemp(InstallationListEditable install, ComponentUse componentUsage, String workIOId) throws BusinessException;
	
	/**
	 * @Description 拆下件生效
	 * @CreateTime 2017年11月17日 下午1:44:29
	 * @CreateBy 徐勇
	 * @param install 装机清单
	 * @param componentUsage 部件使用
	 * @workIOId 工单拆装记录id
	 * @throws BusinessException 
	 */
	public void doRemovedEffect(InstallationListEditable install, ComponentUse componentUsage, String workIOId) throws BusinessException;
	
	/**
	 * @Description  装上件从临时区生效
	 * @CreateTime 2017年11月17日 上午10:56:35
	 * @CreateBy 徐勇
	 * @param install 装机清单信息
	 * @param componentUsage 部件使用情况
	 * @throws BusinessException 
	 */
	public void doInstalledEffectFromTemp4Edit(InstallationListEditable install, ComponentUse componentUsage) throws BusinessException;
	
	/**
	 * @Description 拆下件修改
	 * @CreateTime 2017年11月17日 下午1:44:29
	 * @CreateBy 徐勇
	 * @param install 装机清单
	 * @param componentUsage 部件使用
	 * @throws BusinessException 
	 */
	public void doRemovedEdit(InstallationListEditable install, ComponentUse componentUsage) throws BusinessException;
	/**
	 * @Description 装上件撤销
	 * @CreateTime 2017年11月24日 下午3:29:57
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 * @param workIOId 工单拆装记录id
	 * @param kcid 装上时库存id
	 * @param kcllid 装上时库存履历id
	 * @throws BusinessException 
	 */
	public void doInstalledUndo(String zjqdid, String workIOId, String kcid, String kcllid) throws BusinessException;
	
	/**
	 * @Description 拆下件撤销
	 * @CreateTime 2017年11月24日 下午3:40:43
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 * @param workIOId 工单拆装记录id
	 * @param kcid 拆下时的库存id
	 * @param kcllid 拆下时的库存履历id
	 * @param componentUse部件使用
	 * @throws BusinessException
	 */
	public void doRemovedUndo(String zjqdid, String workIOId, String kcid, String kcllid, ComponentUse componentUse) throws BusinessException;
}
