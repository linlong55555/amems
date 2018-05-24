package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.po.SnUniqverTemp;


public interface StockMapper {
	
	/**
	 * @author liub
	 * @description  根据条件分页查询送修航材列表
	 * @param stock
	 * @return Map<String, Object>
	 * @develop date 2016.10.27
	 */
	public List<Stock> queryRepairStockList(Stock stock);
	
	/**
	 * 按条件查询一页库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageList(Stock stock)  throws RuntimeException;
	 
 	/**
	 * 按条件查询库存总数
	 * @param param
	 * @param pagination
	 * @return
	 */
	 int queryCount(Stock stock) throws RuntimeException;
	 
	 List<Stock> queryList(Stock stock) throws RuntimeException;
	 
	 
	 /**
	 * Meizhiliang 
	 * 查询一条库存信息
	 * @param param
	 * @param pagination
	 * @return
	 */
	 Stock queryById(String id);
	 
	 /**
	 * 按条件查询
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllList(Stock stock)  throws RuntimeException;

	void delete(Stock stock);
	
	/**
	 * @author liub
	 * @description  修改库存表状态
	 * @param id,zt
	 */
	public void updateLocked(@Param("id")String id,@Param("zt")String zt);
	
	void update(Stock stock);
	
	/**
	 * 按条件查询一页库存正常
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Stock> queryAllPageNormalList(Stock stock)  throws RuntimeException;
	 
	 public Stock queryKey(Stock stock) throws RuntimeException;
	 
	 int insert(Stock record);

	 int insertMater(MaterialHistory materialHistory);
	 
     int insertSelective(Stock record);
     
 	/**
	 * @author meizhiliang
	 * @description  更新航材库存表的检验结果
	 * @develop date 2016.11.01
	 */
     int updateByPrimaryKeySelective(Stock stock);               
     
     public Stock queryKeys(Stock stock1) throws RuntimeException;
     
     public Stock getByUuiddm(@Param("uuiddm")String uuiddm, @Param("dprtcode")String dprtcode);
     
     public Stock getById(@Param("id")String id, @Param("dprtcode")String dprtcode);
     
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
  	 * @author liub
  	 * @description 根据盘点id、关键字查询库存信息
  	 * @param pdid,keyword
  	 * @return List<Stock>
  	 * @develop date 2016.11.22
  	 */
  	 List<Stock> queryStockListByPdid(@Param("pdid")String pdid,@Param("keyword")String keyword);
  	 
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
  	
  	 /**
  	  * 查询库存
  	  * @param id
  	  * @return
  	  */
  	 Stock selectById(String id);

	public List<Stock> queryAllPageList3(Stock stock);
	
	/**
	 * 全业务 查询部件序列号 统计数量
	 * @param dprtcode 组织机构ID
	 * @param bjh 部件号
	 * @param sn 序列号
	 * @return
	 */
	int selectCountBjSn(String dprtcode, String bjh, String sn);

	public int queryCountnum(Stock stock);

	public void updateByPrimaryKeySelectives(Stock stock);

	public int queryCountckls(Stock stock);

	public Stock queryKeys1(Stock stock1);
	

	public List<Stock> queryAllPageNormalListkc(Stock stock);

	public List<Stock> queryCountnums(Stock stock);
	 /**
	  * 查询所有报废库数据
	  * @param stock
	  * @return
	  */
	public List<Stock> queryByCklb(Stock stock);
	/**
	 * 库存信息查（包含仓库和外场）
	 * @param stock
	 * @return
	 * @throws RuntimeException
	 */
	public List<Stock> queryPage4Edit(Stock stock)  throws RuntimeException;

	/**
	 * 查询一个库存（仓库）
	 * @param stock
	 * @return
	 */
	public Stock selectOne4Wh(Stock stock);

	/**
	 * 查询一个库存（外场）
	 * @param stock
	 * @return
	 */
	public Stock selectOne4Out(Stock stock);

	/**
	 * 修改仓库库存
	 * @param stock
	 */
	public void update4Wh(Stock stock);

	/**
	 * 修改外场库存
	 * @param stock
	 */
	public void update4Out(Stock stock);
	
	/**
	 * 查询所有报废库数据
	 * @param stock
	 * @return
	 */
	public List<Stock> queryByIds(List<String> idList);
	/**
	 * 根据idList物理删除数据
	 * @param stock
	 * @return
	 */
	public int deleteids(List<String> idList);
	
	void updateZTById(String id);
	
	void insertStockFromMaterialHistory(String whrid,String id);
	
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

	public Stock queryKeytuihuo(Stock stock);
	
	int selectCount4exit(List<String> idList);
	/**
	 * 
	 * @Description 获取根据部件id和组织机构获取序列号不为空的库存数据
	 * @CreateTime 2017年8月24日 上午10:48:57
	 * @CreateBy 岳彬彬
	 * @param bjid
	 * @param dprtcode
	 * @return
	 */
	List<Stock> getStockList(String bjid,String dprtcode);
	
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
	 * @param stock
	 */
	List<Map<String,Object>> getCount4ValidationBjAndXlh(Stock stock);
	List<Map<String,Object>> getCount4ValidationList(List<Stock> list);
	/**
	 * 
	 * @Description 验证部件序列号唯一性
	 * @CreateTime 2017年12月29日 下午12:53:48
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	List<Map<String,Object>> queryCount4Validation(SnUniqverTemp record);

	public List<Stock> queryAllPageToolsList(Stock stock);

	public List<Stock> queryOutinAllPageList(Stock stock);
	
	/**
	 * @Description 根据收货单查询库存
	 * @CreateTime 2018年3月20日 下午2:19:54
	 * @CreateBy 韩武
	 * @param shdid
	 * @return
	 */
	List<Stock> queryStockByShdid(String shdid);
	
	/**
	 * @Description 航材入库上架查询库存
	 * @CreateTime 2018年3月21日 上午9:58:34
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<Stock> queryStockByMaterialStockIn(Stock record);
	
	/**
	 * @Description 航材入库上架id查询库存
	 * @CreateTime 2018年3月22日 下午5:12:34
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	Stock queryStockByMaterialStockInId(String id);
	
	/**
	 * @Description 根据id获取uuid短码
	 * @CreateTime 2018年3月21日 上午11:19:34
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	String selectUUIDDMbyId(String id);
	
	/**
	 * @Description 根据id查询
	 * @CreateTime 2018年3月23日 下午2:39:38
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	Stock selectByPrimaryKey(String id);
	
	/**
	 * 
	 * @Description 根据条件查询移库列表
	 * @CreateTime 2018-3-22 下午2:23:49
	 * @CreateBy 孙霁
	 * @param record
	 * @return
	 */
	List<Stock> queryAllByYk(Stock record);
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-22 下午4:17:02
	 * @CreateBy 孙霁
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	List<Stock> selectByIds(List<String> ids);
	/**
	 * 
	 * @Description 根据id集合获取库存是否存在
	 * @CreateTime 2018年3月22日 下午5:58:05
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	List<Stock> getList4ExistByIdlist(List<String> list);
	/**
	 * 
	 * @Description 修改库存冻结数量
	 * @CreateTime 2018年3月23日 下午5:00:02
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	void update4Djsl(List<String> list);
	/**
	 * 
	 * @Description 销毁时更新库存数量和冻结数量
	 * @CreateTime 2018年3月28日 上午11:12:36
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4Destroy(Stock record);


	public List<Stock> queryjydid(String id);
	
	/**
	 * @Description 工具/设备借归查询库存
	 * @CreateTime 2018年3月28日 下午1:46:57
	 * @CreateBy 韩武
	 * @param param
	 * @return
	 */
	List<Stock> queryByToolBorrowReturn(Map<String, String> param);
}