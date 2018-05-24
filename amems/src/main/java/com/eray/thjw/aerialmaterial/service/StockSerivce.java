package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.StockHistory;


public interface StockSerivce {
	
	/**
	 * @author liub
	 * @description  根据条件分页查询送修航材列表
	 * @param stock
	 * @return Map<String, Object>
	 * @develop date 2016.10.27
	 */
	public List<Stock> queryRepairStockList(Stock stock) throws RuntimeException;
	
	/**
	 * 按条件查询一页库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageList(Stock stock)  throws RuntimeException;
	 
	 /**
	 * 按条件查询
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllList(Stock stock)  throws RuntimeException;
	
 	/**
	 * 按条件查询库存总数
	 * @param param
	 * @param pagination
	 * @return
	 */
	 int queryCount(Stock stock) throws RuntimeException;

	 List<Stock> queryList(Stock stock) throws RuntimeException;
	 
	 Stock queryKey(Stock stock) throws RuntimeException;
	 /**
	 * 删除库存 meizhiliang
	 * @param param
	 * @param pagination
	 * @return
	 */
	void delete(Stock stock);

	void update(Stock stock);
	
	/**
	 * 按条件查询一页库存正常
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageNormalList(Stock stock)  throws RuntimeException;
	 
	
	 	/**
		 * @author meizhiliang
		 * @description  更新航材库存表的检验结果
		 * @develop date 2016.11.01
		 */
	     int updateByPrimaryKeySelective(Stock stock)  throws RuntimeException;               

	     
	     public void insertSelective(Stock stock) throws RuntimeException;

		public Stock queryKeys(Stock stock1) throws RuntimeException;
		
	/**
 	 * @author meizhiliang
 	 * @description 查询已选择的移库航材
 	 * @param pagination
 	 * @return
 	 */
 	 List<Stock> queryChoseList(List<String> ids)  throws RuntimeException;	
 	/**
   	 * @author meizhiliang
   	 * @description 移库后更新航材原始的库存
   	 * @param pagination
   	 * @return
   	 */
  	 int updateStockKc(Stock stock)throws RuntimeException;
  	 /**
 	 * Meizhiliang 
 	 * 查询一条库存信息
 	 * @param param
 	 * @param pagination
 	 * @return
 	 */
 	 Stock queryById(String id);
 	 
 	/**
 	 * @author liub
 	 * @description 根据盘点id、关键字查询库存信息
 	 * @param pdid,keyword
 	 * @return List<Stock>
 	 * @develop date 2016.11.22
 	 */
 	public List<Stock> queryStockListByPdid(String pdid,String keyword) throws RuntimeException;


	/**
	 * 查询可领用库存
	 * @param stock
	 * @return
	 */
	List<Stock> query4RequisitionPage(Stock stock);

 	 /**
 	  * meizhiliang
 	  * 查询可移库库存
 	  * @param stock
 	  * @return
 	  */
 	 List<Stock> queryTransferPage(Stock stock);
 	 
 	 
  	 /**
  	  * 查询工具设备借用情况
  	  * @param stock
  	  * @return
  	  */
  	 List<Stock> queryList4Tool(Stock stock);

	public List<Stock> queryAllPageList3(Stock stock);

	public int queryCountckls(Stock stock);

	public Stock queryKeys1(Stock stock1);

	public List<Stock> queryAllPageNormalListkc(Stock stock);

	/**
	 * 库存信息查（包含仓库和外场）
	 * @param stock
	 * @return
	 */
	public List<Stock> queryPage4Edit(Stock stock);

	/**
	 * 查询一个库存
	 * @param stock
	 * @return
	 */
	public Stock load(Stock stock);

	/**
	 * 修改库存信息
	 * @param stock
	 */
	public void edit(Stock stock);
    
	 
	
	 /**
 	  * 查询所有报废库数据
 	  * @param stock
 	  * @return
 	  */
 	 List<Stock> queryByCklb(Stock stock);
 	 /**
 	  * 根据多个id查询所有报废库数据
 	  * @param stock
 	  * @return
 	  */
 	 List<Stock> queryByIds(List<String> idList);
 	 
 	/**
 	 * @author liub
 	 * @description 根据部件号、机构代码查询库存
 	 * @param stock
 	 * @return List<Stock>
 	 */
 	 List<Stock> selectBjhAndDprt(Stock stock);
 	 
 	/**
  	 * @author liub
  	 * @description 在库航材列表(弹窗)
  	 * @param stock
  	 * @return List<Stock>
  	 */
 	 List<Stock> queryAllPageListWin(Stock stock);
 	 
 	 /**
 	  * 查询可报废的库存
 	  * @param stock
 	  * @return
 	  */
 	List<Stock> selectScrapableStock(Stock stock);

	Stock queryKeytuihuo(Stock stock) throws RuntimeException;
	
	/**
	 * @Description 查询库存分布详情
	 * @CreateTime 2017-10-30 下午3:12:45
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return List<Stock> 库存集合
	 */
	List<Stock> queryInventoryDetailList(Stock stock);
	/**
	 * 
	 * @Description 验证部件序列号唯一性
	 * @CreateTime 2017年11月21日 下午6:35:20
	 * @CreateBy 岳彬彬
	 * @param id 单据id
	 * @param bjh 部件号
	 * @param sn 序列号
	 * @param dprtcode 组织机构
	 * @param type 类型 
	 * @throws BusinessException
	 */
	void getCount4ValidationBjAndXlh(String id,String bjh,String sn,String dprtcode,Integer type) throws BusinessException;
	/**
	 * 
	 * @Description 验证部件序列号的集合唯一性
	 * @CreateTime 2017年11月23日 下午5:21:09
	 * @CreateBy 岳彬彬
	 * @param stockList
	 * @param type
	 * @throws BusinessException
	 */
	void getCount4ValidationBjAndXlh(List<Stock> stockList,Integer type) throws BusinessException;

	public Map<String, Object> queryAllPageToolsList(Stock stock);

	/**
	 * 
	 * @Description 出库-选择出库物料-库存信息
	 * @CreateTime 2018年3月14日 下午2:09:59
	 * @CreateBy 林龙
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryOutinAllPageList(Stock stock)throws BusinessException;
	/**
	 * 
	 * @Description 根据条件查询移库列表
	 * @CreateTime 2018-3-22 下午2:23:49
	 * @CreateBy 孙霁
	 * @param record
	 * @return
	 */
	public Map<String, Object> queryAllByYk(Stock stock)throws BusinessException;
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-22 下午4:17:02
	 * @CreateBy 孙霁
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	public List<Stock> selectByIds(List<String> ids)throws BusinessException;
	
	/**
	 * 
	 * @Description 添加移库信息
	 * @CreateTime 2018-3-23 上午10:17:38
	 * @CreateBy 孙霁
	 * @param stockHistory
	 * @throws BusinessException
	 */
	public void saveYk(StockHistory stockHistory)throws BusinessException;

	/**
	 * 
	 * @Description 根据检验单id查询库存
	 * @CreateTime 2018年3月27日 下午3:12:43
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 */
	public List<Stock> queryjydid(String id);
	
}
