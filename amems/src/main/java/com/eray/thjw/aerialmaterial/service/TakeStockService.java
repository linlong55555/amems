package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.TakeStock;
import com.eray.thjw.aerialmaterial.po.TakeStockDetail;
import com.eray.thjw.aerialmaterial.po.TakeStockScope;
import com.eray.thjw.exception.BusinessException;


public interface TakeStockService {
	
	/**
	 * @author liub
	 * @description 新增盘点单
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	public String addTakeStock(TakeStock takeStock) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改盘点单
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	public void editTakeStock(TakeStock takeStock) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 新增盘点范围
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	public void addTakeScope(TakeStock takeStock) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 新增盘点详情根据库存
	 * @param takeStockDetail
	 * @develop date 2016.11.24
	 * @throws BusinessException 
	 */
	public void addDetailFromStock(TakeStockDetail takeStockDetail) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 新增盘点详情根据航材
	 * @param takeStockDetail
	 * @develop date 2016.11.24
	 * @throws BusinessException 
	 */
	public void addDetailFromMaterial(TakeStockDetail takeStockDetail) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改盘点详情,及部件履历
	 * @param takeStockDetail
	 * @develop date 2016.11.25
	 * @throws BusinessException 
	 */
	public void eidtTakeStockDetail(TakeStockDetail takeStockDetail) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 删除盘点范围
	 * @param id
	 * @develop date 2016.11.23
	 * @throws BusinessException 
	 */
	public void delTakeScope(String takeScopeId,String mainId) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 审核
	 * @param takeStock
	 * @develop date 2016.11.29
	 * @throws BusinessException 
	 */
	public void editAudit(TakeStock takeStock) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 撤销
	 * @param takeStock
	 * @develop date 2016.11.29
	 * @throws BusinessException 
	 */
	public void editRecall(TakeStock takeStock) throws BusinessException;
	
	 /**
	 * @author liub
	 * @description  根据id查询盘点单
	 * @param id
	 * @return TakeStock
	 * @develop date 2016.11.22
	 */
	public TakeStock getById(String id);
	
	/**
	 * @author liub
	 * @description  根据仓库id查询盘点单列表
	 * @param ckid
	 * @return List<TakeStock>
	 * @develop date 2016.11.22
	 */
	public List<TakeStock> queryListByCkid(String ckid);
	
	/**
	 * @author liub
	 * @description  根据盘点id查询盘点范围列表
	 * @param ckid
	 * @return List<TakeStockScope>
	 * @develop date 2016.11.22
	 */
	public List<TakeStockScope> queryTakeScopeListByMainId(String mainid);
	
	/**
	 * @author liub
	 * @description  根据盘点差异id查询盘点差异及履历信息
	 * @param id
	 * @return TakeStockDetail
	 * @develop date 2016.11.25
	 */
	public TakeStockDetail queryByDetailId(String id);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询盘点单信息
	 * @param takeStock
	 * @return List<TakeStock>
	 * @develop date 2016.11.21
	 */
	public List<TakeStock> queryAllPageList(TakeStock takeStock);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.23
	 */
	public List<TakeStockDetail> queryAllDetailPageList(TakeStockDetail takeStockDetail);
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询库存及盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.23
	 */
	public List<TakeStockDetail> queryStockDetailPageList(TakeStockDetail takeStockDetail);
	
}
