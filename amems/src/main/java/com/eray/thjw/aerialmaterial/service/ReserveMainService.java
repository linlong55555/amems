package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ReserveDetail;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.po.ReserveWorkOrder;
import com.eray.thjw.exception.BusinessException;


public interface ReserveMainService {
	
	
	/**
	 * @author liub
	 * @description 增加提订单
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	public String add(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查提订单编辑权限
	 * @param id
	 * @develop date 2016.10.13
	 */
	public void checkEdit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 编辑提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	public void edit(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改提订详情
	 * @param reserveDetail
	 * @develop date 2016.12.16
	 * @throws BusinessException 
	 */
	public void updateReserveDetail(ReserveDetail reserveDetail) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 检查提订单审核权限
	 * @param id
	 * @develop date 2016.10.14
	 */
	public void checkAudit(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 审核提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	public void editAudit(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	public void cancel(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.10.12
	 */
	public Map<String, Object> queryAllPageList(ReserveMain reserve);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材提订单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.10.14
	 */
	public Map<String, Object> queryApprovePageList(ReserveMain reserve);
	
	/**
	 * @author liub
	 * @description  根据提订单id查询提订详情信息
	 * @param mainid
	 * @return List<ReserveDetail>
	 * @develop date 2016.10.12
	 */
	public List<ReserveDetail> queryReserveDetailListByMainId(String mainid);
	
	/**
	 * @author liub
	 * @description  根据提订单id集合查询提订详情信息
	 * @param mainidList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.12
	 */
	public List<Map<String, Object>> queryReserveDetailListByIds(List<String> mainidList);
	
	/**
	 * @author liub
	 * @description 根据主键id查询提订单信息
	 * @param id
	 * @return ReserveMain
	 * @develop date 2016.10.13
	 */
	public ReserveMain selectByPrimaryKey(String id);
	
	/**
	 * @author liub
	 * @description 根据主键id查询提订单信息(包含申请人、审核人)
	 * @param id
	 * @return ReserveMain
	 * @develop date 2016.12.19
	 */
	public ReserveMain selectById(String id);
	
	/**
	 * @author liub
	 * @description 编辑提订单信息
	 * @param reserve
	 * @develop date 2016.10.14
	 */
	public void updateByPrimaryKeySelective(ReserveMain reserve);
	
	/**
	 * @author liub
	 * @description 提订单指定结束
	 * @param reserve
	 * @develop date 2017.04.12
	 */
	public void updateShutDown(ReserveMain reserve) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订详情信息(弹窗)
	 * @param reserveDetail
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.07
	 */
	public List<Map<String, Object>> queryReserveDetailPageList(ReserveDetail reserveDetail);
	
	
	/**
	 * @author liub
	 * @description  根据mainid查询相关工单信息
	 * @param mainid
	 * @return List<ReserveWorkOrder>
	 * @develop date 2016.11.18
	 */
	public List<ReserveWorkOrder> queryWorkOrderListByMainId(String mainid);
}
