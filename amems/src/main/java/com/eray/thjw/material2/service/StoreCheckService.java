package com.eray.thjw.material2.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.StoreCheck;


/**
 * @Description 盘点记录service 
 * @CreateTime 2018-3-20 下午2:12:17
 * @CreateBy 刘兵
 */
public interface StoreCheckService {

	/**
	 * @Description 新增库存
	 * @CreateTime 2018-3-21 下午5:55:37
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return String 库存id
	 * @throws BusinessException
	 */
	public String save(Stock stock) throws BusinessException;
	
	/**
	 * @Description 修改库存
	 * @CreateTime 2018-3-21 下午5:55:37
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return String 库存id
	 * @throws BusinessException
	 */
	public String update(Stock stock) throws BusinessException;
	
	/**
	 * @Description 盈亏历史分页列表查询
	 * @CreateTime 2018-3-22 上午11:55:12
	 * @CreateBy 刘兵
	 * @param storeCheck 盘点记录
	 * @return List<StoreCheck> 盘点记录集合
	 */
	List<StoreCheck> queryAllPageList(StoreCheck storeCheck);
	
	/**
	 * @Description 获取库存数据
	 * @CreateTime 2018-3-23 上午11:13:58
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return Stock 库存
	 * @throws BusinessException
	 */
	Stock getByStock(Stock stock) throws BusinessException;
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-3-26 上午10:13:59
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<StoreCheck>
	 */
	List<StoreCheck> doExportExcel(StoreCheck paramObj);
	
}
