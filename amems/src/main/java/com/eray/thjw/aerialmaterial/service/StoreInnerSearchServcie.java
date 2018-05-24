package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.InstockDetail;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolBorrowRecord;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.FrozenHistory;
import com.eray.thjw.material2.po.StockHistory;

public interface StoreInnerSearchServcie {
	


	/**
	 * 库内查询列表
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryInnerStorePageList(Stock stock) throws BusinessException;
	
	
	
	
	/**
	 * 根据库存id查询库存履历
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public List<StockHistory> queryStoreHistoryByKcid(String id) throws BusinessException;
	
	
	/**
	 * 根据库存id查询库存详情
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Stock queryKcByKcid(String id) throws BusinessException;
	
	
	
	/**
	 * 库存查询编辑页面保存方法
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	public int save(Stock stock) throws BusinessException;
	
	
	
	
	/**
	 * 根据库存id查询工具借还记录
	 * @param kcid
	 * @return
	 * @throws BusinessException
	 */
	public List<ToolBorrowRecord> getListBykcid(String kcid) throws BusinessException;
	
	
	
	
	/**
	 * 根据库存id查询库存冻结信息
	 * @param frozenHistory
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> queryFrozenHistoryBykcid(FrozenHistory frozenHistory) throws BusinessException;
	
	
	
	/**
	 * 根据库存id查询工具工具借用归还记录
	 * @param kcid
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> getListAllBykcid(ToolBorrowRecord toolBorrowRecord) throws BusinessException;
	
	/**
	 * 
	 * @Description 根据仓库id更新uuid短码
	 * @CreateTime 2018年3月16日 下午3:05:15
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	void updateUuiddm4Print(String id);
	/**
	 * 
	 * @Description 根据id获取数据用于打印二维码
	 * @CreateTime 2018年3月16日 下午3:35:27
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	Map<String,Object> getData4Print(String id);
	
	/**
	 * @Description 查询未维护数据
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	public Map<String,Object> queryNoMaintenanceData(Stock stock) throws BusinessException;

}
