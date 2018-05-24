package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolBorrowRecord;
import com.eray.thjw.material2.po.FrozenHistory;
import com.eray.thjw.material2.po.StockHistory;

public interface StoreInnerSearchMapper {
	
	
	/**
	 * @description 根据当前用户和机构查询由仓库权限的仓库数据
	 * @param stock
	 * @return
	 */
	public List<Stock> queryAll(Stock stock);

	
	/**
	 * 根据库存id查询库存履历
	 * @param kcid
	 * @return
	 */
	public List<StockHistory> querByKcid(String kcid);
	
	
	/**
	 * 根据库存id,当前用户id,当前用户组织机构查询库存详情
	 * @param kcid
	 * @return
	 */
	
	public Stock queryKcByKcid(String kcid,String userId,String dprtcode);
	
	
	
	/**
	 * 保存库存履历
	 * @param storeHistory
	 * @return
	 */
	public int saveStoreHistory(StockHistory  storeHistory);
	
	
	
	/**
	 *  根据id修改库存信息
	 * @param stock
	 * @return
	 */
	public int updateStoreById(Stock stock);
	
	
	
	/**
	 * 根据库存ID查询工具借还记录(前十条)
	 * @param kcid
	 * @return
	 */
	public List<ToolBorrowRecord> getListBykcid(String kcid);
	
	
	/**
	 * 根据库存id增量处理部件数据
	 * @param kcid
	 * @return
	 */
	public int batchInsertOrUpdateBj(Stock stock);
	
	
	
	/**
	 * 根据库存id查询库存冻结履历信息
	 * @param kcid
	 * @return
	 */
	public List<FrozenHistory> queryFrozenHistoryBykcid(String kcid);
	
	/**
	 * 
	 * @Description 根据仓库id该数据不存在写入uuid短码
	 * @CreateTime 2018年3月16日 下午3:02:56
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	void updateUuiddm4Print(String id);
	/**
	 * 
	 * @Description 根据id获取数据用于打印
	 * @CreateTime 2018年3月16日 下午3:35:27
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	Map<String,Object> getData4Print(String id);

	/**
	 * 根据库存id查询工具借用归还记录
	 * @param kcid
	 * @return
	 */
	public List<ToolBorrowRecord> getListAllBykcid(String kcid);
	
	
	
	/**
	 * 批量修改库存状态
	 * @param stock
	 * @return
	 */
	public int updateStatus(Stock stock);


	public void batchInsertOrUpdateBjInspection(Inspection inspection);
	
	
	
	/**
	 * @Description 查询未维护数据
	 * @param stock
	 * @return
	 */
	public List<Stock> queryNoMaintenanceData(Stock stock);
	
}
