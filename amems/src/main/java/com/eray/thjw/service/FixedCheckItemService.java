package com.eray.thjw.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FixedCheckItem;


public interface FixedCheckItemService {
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询定检项目排序数据
	 * @param wxfabh,dprtcode
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.25
	 */
	public List<Map<String, Object>> queryOrderList(String wxfabh,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询定检项目信息
	 * @param fixedCheckItem
	 * @return Map<String, Object>
	 * @develop date 2016.08.20
	 */
	public Map<String, Object> queryAllPageList(FixedCheckItem fixedCheckItem);
	
	/**
	 * @author liub
	 * @description 获取最大排序号
	 * @return int
	 * @develop date 2016.08.25
	 */
	public int getMaxPxh();
	
	/**
	 * @author liub
	 * @description 根据id查询定检项目
	 * @param id
	 * @return FixedCheckItem
	 * @develop date 2016.08.24
	 */
	public FixedCheckItem selectByPrimaryKey(String id);
	
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
	 * @description 根据维修方案编号查询定检项目信息,字段排序F_BBID desc,ZWMS desc
	 * @param wxfabh
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.28
	 */
	public List<FixedCheckItem> queryByWxfabh(String wxfabh,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据条件查询有差异维修方案的定检项目信息,字段排序djbh desc,ZWMS desc
	 * @param map
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.28
	 */
	public List<FixedCheckItem> queryByMap(Map<String, Object> map);
	
	
	/**
	 * @author liub
	 * @description 根据条件新增定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	public String add(FixedCheckItem fixedCheckItem) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查定检项目修改权限
	 * @param id
	 * @develop date 2016.08.31
	 */
	public void checkEdit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	public void edit(FixedCheckItem fixedCheckItem) throws BusinessException;
	

	/**
	 * @author liub
	 * @description 检查定检项目改版权限
	 * @param id
	 * @develop date 2016.08.26
	 */
	public void checkModify(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 改版定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.26
	 */
	public String modify(FixedCheckItem fixedCheckItem) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查定检项目审核权限
	 * @param id
	 * @develop date 2016.09.28
	 */
	public void checkAudit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 审核
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	public void audit(FixedCheckItem fixedCheckItem) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查定检项目审批权限
	 * @param id
	 * @develop date 2016.09.28
	 */
	public void checkApprove(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList,yj
	 * @develop date 2016.12.28
	 */
	public String updateBatchAudit(List<String> idList,String yj) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList,yj
	 * @develop date 2016.12.28
	 */
	public String updateBatchApprove(List<String> idList,String yj) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 审批
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	public void approve(FixedCheckItem fixedCheckItem) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 调整排序
	 * @param fixedCheckItemList
	 * @develop date 2016.08.24
	 */
	public void order(List<FixedCheckItem> fixedCheckItemList);
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @develop date 2016.08.25
	 */
	public void cancel(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  根据维修方案编号、版本作废
	 * @param wxfabh维修方案编号,wxfabb维修方案版本,dprtcode
	 * @develop date 2016.12.12
	 */
	public void cancelByWxfabhAndBb(String wxfabh,BigDecimal wxfabb,String dprtcode);
	
	/**
	 * @author liub
	 * @description 生效定检项目
	 * @param wxfabh维修方案编号,wxfabb维修方案版本
	 * @develop date 2016.09.24
	 */
	public void sbEffective(String wxfabh,BigDecimal wxfabb,String czls,String dprtcode)throws BusinessException;
	
	/**
	 * @author liub
	 * @description  将定检项目修改成待生效
	 * @param idList
	 * @develop date 2016.09.29
	 */
	public void updateWaitEffective(List<String> idList,String czls);
	/**
	 * @author sunji
	 * @description  根据编号和最新标识查询数据
	 * @param idList
	 * @develop date 2016.09.29
	 */
	public FixedCheckItem selectByZxbsAndBh(FixedCheckItem fixedCheckItem);
}
