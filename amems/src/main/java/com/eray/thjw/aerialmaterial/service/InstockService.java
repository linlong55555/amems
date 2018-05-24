package com.eray.thjw.aerialmaterial.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Instock;
import com.eray.thjw.aerialmaterial.po.InstockDetail;
import com.eray.thjw.aerialmaterial.po.InstockDetailStock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;

/**
 * 入库业务层
 * @author xu.yong
 */
public interface InstockService {
	
	/**
	 * 查询采购待入库列表
	 * @return 
	 */
	public Map<String, Object> queryPurchasePageList(InstockDetail instockDetail);
	
	
	/**
	 * 查询采购航材入库单，采购入库单和入库航材是一对一，所以使用InstockDetail查询和返回
	 * @param id 入库航材ID
	 * @return
	 */
	public InstockDetail queryPurchaseById(String id);
	
	/**
	 * 保存采购入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public String savePurchase(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 提交采购入库，将航材入库信息变更到库存
	 * @param detailId 入库航材ID
	 */
	public void savePurchaseSubmit(String detailId);
	
	/**
	 * 作废采购入库单
	 * @param detailId 入库航材ID
	 */
	public void savePurchaseDiscard(String detailId);
	
	
	/**
	 * 保存借出归还入库
	 * @param map
	 */
	public void saveLend(Map<String, Object> map)throws BusinessException;
	
	/**
	 * 入库时查询默认库存成本
	 * @param bjid 部件ID
	 * @param instockDetailId 入库航材ID 采购入库时传入用于查询合同价格
	 */
	public BigDecimal queryDefaultCost(String bjid, String bjh, String instockDetailId);
	
	/**
	 * 查询送修待入库列表
	 * @return 
	 */
	public Map<String, Object> queryRepairPageList(BaseEntity entity);
	
	/**
	 * 作废送修入库单
	 * @param instockDetailId 入库航材ID
	 */
	public void saveRepairDiscard(String instockDetailId);
	
	/**
	 * 查询送修航材入库单，采购入库单和入库航材是一对一，所以使用InstockDetail查询和返回
	 * @param instockDetailId 入库航材ID
	 * @return
	 */
	public InstockDetail queryRepairById(String instockDetailId);
	
	/**
	 * 保存送修入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public void saveRepair(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 提交保存送修入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public void saveRepairSubmit(Map<String, Object> map) throws BusinessException;
	
	
	/**
	 * 查询借入入库单
	 * @param instockId 入库单ID
	 * @param borrowApplyId 借入申请ID
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryBorrow(String instockId, String borrowApplyId) throws BusinessException;
	
	/**
	 * 提交借入入库
	 * @param map
	 * @throws BusinessException 
	 */
	public void saveBorrowSubmit(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 提交手工入库
	 * @param map
	 * @throws BusinessException 
	 */
	public void saveHandworkSubmit(Map<String, Object> map) throws BusinessException;
	
	/**
	 * 分页查询 历史 入库单
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryPage(BaseEntity entity) throws BusinessException;
	
	/**
	 * 撤销入库单
	 * @param id 入库单ID
	 * @throws BusinessException 
	 */
	public void saveCancel(String id) throws BusinessException;
	
	/**
	 * 根据外飞行队出库单 保存借出归还入库
	 * @param map
	 * @return 
	 * @throws BusinessException 
	 */
	public void saveLendByOutstock(Map<String, Object> map) throws BusinessException;

	/**
	 * 查询入库明细
	 * @param record
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> queryDetailPage(InstockDetailStock record)throws RuntimeException;
	
	public Instock queryInstockById(String id);
	public List<InstockDetailStock> queryInstockStockById(String id);
	
}
