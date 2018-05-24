package com.eray.thjw.aerialmaterial.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.FreezResumeMapper;
import com.eray.thjw.material2.dao.StockHistoryMapper;
import com.eray.thjw.material2.po.FreezResume;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.SnUniqverTempMapper;
import com.eray.thjw.system.po.SnUniqverTemp;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.KclyEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.material2.StockHistoryTypeEnum;

@Service
public class StockSerivceImpl implements StockSerivce {
	
	@Resource
	private StockMapper stockMapper;
	@Resource
	private SnUniqverTempMapper snUniqverTempMapper;
	
	@Resource
	private TimeControllWareItemService timeControllWareItemService;
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private StockHistoryMapper stockHistoryMapper;
	
	@Resource
	private FreezResumeMapper freezResumeMapper;
	
	/**
	 * @author liub
	 * @description  根据条件分页查询送修航材列表
	 * @param stock
	 * @return Map<String, Object>
	 * @develop date 2016.10.27
	 */
	@Override
	public List<Stock> queryRepairStockList(Stock stock) throws RuntimeException {
		return stockMapper.queryRepairStockList(stock);
	}
	
	@Override
	public List<Stock> queryAllPageList(Stock stock)
			throws RuntimeException {
		
		return stockMapper.queryAllPageList(stock);
	}

	@Override
	public int queryCount(Stock stock) throws RuntimeException {
		
		return stockMapper.queryCount(stock);
	}

	@Override
	public List<Stock> queryList(Stock stock) throws RuntimeException {
		
		return stockMapper.queryList(stock);
	}

	@Override
	public List<Stock> queryAllList(Stock stock) throws RuntimeException {
		return stockMapper.queryAllList(stock);
	}

	@Override
	public void delete(Stock stock) {
		stockMapper.delete(stock);
	}

	@Override
	public void update(Stock stock) {
		stockMapper.update(stock);
	}

	@Override
	public List<Stock> queryAllPageNormalList(Stock stock)
			throws RuntimeException {
		return stockMapper.queryAllPageNormalList(stock);
	}

	@Override
	public Stock queryKey(Stock stock) throws RuntimeException {
		return stockMapper.queryKey(stock);
	}
	@Override
	public Stock queryKeytuihuo(Stock stock) throws RuntimeException {
		return stockMapper.queryKeytuihuo(stock);
	}
    
	
	@Override
	public int updateByPrimaryKeySelective(Stock stock) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		int count=stockMapper.updateByPrimaryKeySelective(stock);
		//commonRecService.write(stock.getJydid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
		return count;
	}

	@Override
	public void insertSelective(Stock stock) throws RuntimeException {
		stockMapper.insertSelective(stock);
	}

	@Override
	public Stock queryKeys(Stock stock1) throws RuntimeException {
		return stockMapper.queryKeys(stock1);
	}
	
	/**
 	 * @author meizhiliang
 	 * @description 查询已选择的移库航材
 	 * @param pagination
 	 * @return
 	 */
	@Override
	public List<Stock> queryChoseList(List<String> ids) throws RuntimeException {
		return stockMapper.queryChoseList(ids);
	}
	/**
  	 * @author meizhiliang
  	 * @description 移库后更新航材原始的库存
  	 * @param pagination
  	 * @return
  	 */
	@Override
	public int updateStockKc(Stock stock) throws RuntimeException {
		return stockMapper.updateStockKc(stock);
	}

	@Override
	public Stock queryById(String id) {
		return stockMapper.queryById(id);
	}

	/**
 	 * @author liub
 	 * @description 根据盘点id、关键字查询库存信息
 	 * @param pdid,keyword
 	 * @return List<Stock>
 	 * @develop date 2016.11.22
 	 */
	@Override
 	public List<Stock> queryStockListByPdid(String pdid,String keyword) throws RuntimeException{
		return stockMapper.queryStockListByPdid(pdid,keyword);
	}

	@Override
	public List<Stock> query4RequisitionPage(Stock stock){
		
		return stockMapper.query4RequisitionPage(stock);
	}

	@Override
	public List<Stock> queryTransferPage(Stock stock) {
		return stockMapper.queryTransferPage(stock);
	}

	@Override
	public List<Stock> queryList4Tool(Stock stock) {
		return stockMapper.queryList4Tool(stock);
	}

	@Override
	public List<Stock> queryAllPageList3(Stock stock) {
		return stockMapper.queryAllPageList3(stock);
	}

	@Override
	public int queryCountckls(Stock stock) {
		return stockMapper.queryCountckls(stock);
	}

	@Override
	public Stock queryKeys1(Stock stock1) {
		return stockMapper.queryKeys1(stock1);
	}

	@Override
	public List<Stock> queryAllPageNormalListkc(Stock stock) {
		return stockMapper.queryAllPageNormalListkc(stock);
	}

	@Override
	public List<Stock> queryPage4Edit(Stock stock) {
		return stockMapper.queryPage4Edit(stock);
	}

	@Override
	public Stock load(Stock stock) {
		Stock result = null;
		//库存来源：仓库
		if (KclyEnum.isWarehouse(stock.getKcly())) {
			result = stockMapper.selectOne4Wh(stock);
		}
		else{
			//库存来源：场外
			result = stockMapper.selectOne4Out(stock);
		}
		return result;
	}

	@Override
	public void edit(Stock stock) {
		String czls = UUID.randomUUID().toString();
		String bizNo = null;
		String bizid = null;
		User user = ThreadVarUtil.getUser();
		stock.setRkrid(user.getId());
		//库存来源：仓库
		if (KclyEnum.isWarehouse(stock.getKcly())) {
			  stockMapper.update4Wh(stock);
			  materialRecService.writeStockRec(stock.getId(), czls, bizid, bizNo, StockRecBizTypeEnum.TYPE7, UpdateTypeEnum.UPDATE);
		}
		else{
			//库存来源：场外
			 stockMapper.update4Out(stock);
			 stock.setWhrid(user.getId());
			 materialRecService.writeOutfieldRec(stock.getId(), czls, bizid, bizNo, OutfieldRecBizTypeEnum.TYPE7, UpdateTypeEnum.UPDATE);
		}
		
	}

	 /**
	  * 查询所有报废库数据
	  * @param stock
	  * @return
	  */
	public List<Stock> queryByCklb(Stock stock) {
		return stockMapper.queryByCklb(stock);
	}
	 /**
	  * 根据多个id查询所有报废库数据
	  * @param stock
	  * @return
	  */
	public List<Stock> queryByIds(List<String> ids) {
		return stockMapper.queryByIds(ids);
	}
	
	/**
	 * @author liub
	 * @description 根据部件号、机构代码查询库存
	 * @param stock
	 * @return List<Stock>
	 */
	@Override
	public List<Stock> selectBjhAndDprt(Stock stock){
		return stockMapper.selectBjhAndDprt(stock);
	}
	/**
  	 * @author liub
  	 * @description 在库航材列表(弹窗)
  	 * @param stock
  	 * @return List<Stock>
  	 */
	@Override
	public List<Stock> queryAllPageListWin(Stock stock){
		return stockMapper.queryAllPageListWin(stock);
	}

	/**
	 * 查询可报废的库存
	 */
	@Override
	public List<Stock> selectScrapableStock(Stock stock) {
		return stockMapper.selectScrapableStock(stock);
	}
	
	/**
	 * @Description 查询库存分布详情
	 * @CreateTime 2017-10-30 下午3:12:45
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return List<Stock> 库存集合
	 */
	@Override
	public List<Stock> queryInventoryDetailList(Stock stock){
		return stockMapper.queryInventoryDetailList(stock);
	}
	
	/**
	 * 
	 * @Description 验证部件序列号唯一性
	 * @CreateTime 2017年11月21日 下午6:35:20
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param bjh
	 * @param sn
	 * @param dprtcode
	 * @param gype
	 * @throws BusinessException
	 */
	@Override
	public void getCount4ValidationBjAndXlh(String id,String bjh, String sn, String dprtcode,
			Integer type) throws BusinessException {
		Stock stock = new Stock();
		stock.setBjh(bjh);
		stock.setSn(sn);
		stock.setDprtcode(dprtcode);
		stock.getParamsMap().put("lxList", getLxList(type));
		List<Map<String,Object>> list = stockMapper.getCount4ValidationBjAndXlh(stock);
		boolean flag = false;
		StringBuffer sbf = new StringBuffer();
		if(list.size()>0){
			for (Map<String, Object> map : list) {
				if(map.get("ID").equals(id)){
					continue;
				}else{
					flag = true;
					sbf.append(map.get("WZ"));
				}
			}
		}
		if(flag){
			throw new BusinessException("部件["+bjh+"]序列号["+sn+"]在"+sbf.toString()+"已存在!");
		}
	}
	/**
	 * 
	 * @Description 验证部件序列号的集合唯一性
	 * @CreateTime 2017年11月23日 下午5:21:09
	 * @CreateBy 岳彬彬
	 * @param stockList
	 * @param type
	 * @throws BusinessException
	 */
	@Override
	public void getCount4ValidationBjAndXlh(List<Stock> stockList, Integer type) throws BusinessException {	
		if(stockList.size()>0){
			String lsh = UUID.randomUUID().toString();
			SnUniqverTemp temp ;
			List<SnUniqverTemp> snList =new ArrayList<SnUniqverTemp>();
			for (int i = 0; i < stockList.size(); i++) {
				temp = new SnUniqverTemp();
				temp.setLsh(lsh);
				temp.setBjh(stockList.get(i).getBjh());
				temp.setXlh(stockList.get(i).getSn());
				temp.setDprtcode(stockList.get(i).getDprtcode());
				snList.add(temp);
				if(snList.size() >= 100 || i == stockList.size() - 1){
					snUniqverTempMapper.insertBatch(snList);
					snList.clear();
				}
			}
			//关联查询
			temp = new SnUniqverTemp();
			temp.setLsh(lsh);
			temp.getParamsMap().put("lxList", getLxList(type));
			List<Map<String,Object>> list = stockMapper.queryCount4Validation(temp);
			//删除零时表数据
			snUniqverTempMapper.deleteByLsh(lsh);
			//做比较
			doCompare(stockList,list);
		}
		
	}
	/**
	 * 
	 * @Description 获取需要验证范围
	 * @CreateTime 2017年11月23日 下午5:21:46
	 * @CreateBy 岳彬彬
	 * @param type
	 * @return
	 */
	private List<String> getLxList(Integer type){
		List<String> lxList = new ArrayList<String>();
		//1、仓库 2、外场  3、销毁单  4、外派清单（借出未归还）5、外派清单（送修未归还）6、装机清单生效区装上件
		lxList.add("1");
		lxList.add("3");
		lxList.add("6");
		if (type == 1 || type == 2 || type == 3 || type == 4) {
			lxList.add("4");
			lxList.add("5");
		}else if (type == 5 || type == 7 || type == 9){
			lxList.add("2");
			lxList.add("4");
			lxList.add("5");
		}else if (type ==6){
			lxList.add("2");
			lxList.add("4");
		}else if(type == 8){
			lxList.add("2");
			lxList.add("5");
		}else if(type == 11){
			lxList.clear();
			lxList.add("1");
			lxList.add("6");
		}else if(type == 12){
			lxList.clear();
			lxList.add("1");
			lxList.add("2");
			lxList.add("6");
		}else if(type == 13){
			lxList.clear();
			lxList.add("1");
		}
		return lxList;
	}
	/**
	 * 
	 * @Description 比较部件号和序列号是否已存在
	 * @CreateTime 2017年11月23日 下午5:22:16
	 * @CreateBy 岳彬彬
	 * @param stockList
	 * @param list
	 * @throws BusinessException
	 */
	private void doCompare(List<Stock> stockList,List<Map<String,Object>> list) throws BusinessException{
		boolean flag = false;
		StringBuffer sbf = new StringBuffer();
		for (Map<String, Object> map : list) {	
			for (int i = 0; i < stockList.size(); i++) {
				if(map.get("SN").equals(stockList.get(i).getSn())){
					if(map.get("ID").equals(stockList.get(i).getId())){
						flag = false;
						break;
					}else{
						flag = true;
						sbf.append("部件["+stockList.get(i).getBjh()+"]序列号["+stockList.get(i).getSn()+"]在"+map.get("WZ")+"已存在!");
					}				
				}
			}
		}
		if(flag){
			throw new BusinessException(sbf.toString());
		}
	}

	@Override
	public Map<String, Object> queryAllPageToolsList(Stock stock) {
		PageHelper.startPage(stock.getPagination());
		List<Stock> newRecordList =stockMapper.queryAllPageToolsList(stock);
		return PageUtil.pack4PageHelper(newRecordList, stock.getPagination());
	}
	
	/**
	 * 
	 * @Description 出库-选择出库物料-库存信息
	 * @CreateTime 2018年3月14日 下午2:09:59
	 * @CreateBy 林龙
	 * @param stock
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryOutinAllPageList(Stock stock) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		stock.getParamsMap().put("userId", user.getId());
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = stockMapper.queryOutinAllPageList(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}

	/**
	 * 
	 * @Description 根据条件查询移库列表
	 * @CreateTime 2018-3-22 下午2:23:49
	 * @CreateBy 孙霁
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllByYk(Stock stock)
			throws BusinessException {
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = stockMapper.queryAllByYk(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}

	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-22 下午4:17:02
	 * @CreateBy 孙霁
	 * @param ids
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Stock> selectByIds(List<String> ids)
			throws BusinessException {
		List<Stock> list =stockMapper.selectByIds(ids);
		if(list.size() != ids.size()){
			throw new BusinessException("当前选择数据已更新，请刷新后再进行操作！");
		}
		return list;
	}

	/**
	 * 
	 * @Description 添加移库信息
	 * @CreateTime 2018-3-23 上午10:17:38
	 * @CreateBy 孙霁
	 * @param stockHistory
	 * @throws BusinessException
	 */
	@Override
	public void saveYk(StockHistory stockHistory) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		List<Stock> stockList = stockHistory.getStockList();
		//获取所有stockList 的id
		List<String> ids = new ArrayList<String>();
		for (Stock stock : stockList) {
			ids.add(stock.getId());
		}
		//根据ids获取数据
		List<Stock> oldStockList =this.selectByIds(ids);
		//拼接字段
		StringBuffer str = new StringBuffer();
		//对比是否数据是全部移库  还是部分移库
		for (Stock oldStock : oldStockList) {
			//查询刚插入的数据
			String uuiddm = stockMapper.selectUUIDDMbyId(oldStock.getId());
			for (Stock newStock : stockList) {
				//清空拼接字段
				if(oldStock.getId().equals(newStock.getId())){
					//全部移库
					String id = UUID.randomUUID().toString();
					if(newStock.getKcsl().compareTo(oldStock.getKcsl()) == 0){
						str.setLength(0);
						//str.append(stockHistory.getCzsm());
						str.append("从").append(oldStock.getCkh()).append(oldStock.getCkmc()==null?"":oldStock.getCkmc()).append(oldStock.getKwh()==null?"":oldStock.getKwh())
						.append("移动到").append(newStock.getCkh()).append(newStock.getCkmc()==null?"":newStock.getCkmc()).append(newStock.getKwh()==null?"":newStock.getKwh());
						oldStock.setCkid(newStock.getCkid());
						oldStock.setCkh(newStock.getCkh());
						oldStock.setCkmc(newStock.getCkmc());
						oldStock.setKwid(newStock.getKwid());
						oldStock.setKwh(newStock.getKwh());
						oldStock.setWhrid(user.getId());
						oldStock.setWhsj(new Date());
						//修改库存数据
						stockMapper.updateByPrimaryKeySelective(oldStock);
						oldStock.setUuiddm(uuiddm);
						//添加b_h_017
						MaterialHistory materialHistory = new MaterialHistory(oldStock);
						materialHistory.setId(id);
						materialHistoryMapper.insertSelective(materialHistory);
						//添加b_h_025
						stockHistory.setId(id);
						stockHistory.setDprtcode(oldStock.getDprtcode());
						stockHistory.setCzsj(new Date());
						stockHistory.setCzrid(user.getId());
						stockHistory.setCzrbmid(user.getBmdm());
						stockHistory.setCzr(user.getUsername() +" " + user.getRealname());
						stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_TRANSFER.getId());
						stockHistory.setCzsm(str.toString());
						stockHistory.setKcid(materialHistory.getKcid());		
						stockHistoryMapper.insertSelective(stockHistory);
					}else{
						//部分移库
						//修改原库存数据
						str.setLength(0);
						//str.append(stockHistory.getCzsm());
						str.append("移出到").append(newStock.getCkh()).append(newStock.getCkmc()==null?"":newStock.getCkmc()).append(newStock.getKwh()==null?"":newStock.getKwh())
						.append(",数量").append(newStock.getKcsl());
						
						oldStock.setKcsl(oldStock.getKcsl().subtract(newStock.getKcsl()));
						if(newStock.getZt() ==1){
							oldStock.setDjsl(new BigDecimal(0));
						}
						
						//修改库存数据
						stockMapper.updateByPrimaryKeySelective(oldStock);
						oldStock.setUuiddm(uuiddm);
						//添加b_h_017
						MaterialHistory materialHistory = new MaterialHistory(oldStock);
						String materialHistoryId = UUID.randomUUID().toString();
						materialHistory.setId(materialHistoryId);
						materialHistoryMapper.insertSelective(materialHistory);
						//添加b_h_025
						stockHistory.setId(materialHistoryId);
						stockHistory.setDprtcode(oldStock.getDprtcode());
						stockHistory.setCzsj(new Date());
						stockHistory.setCzrid(user.getId());
						stockHistory.setCzrbmid(user.getBmdm());
						stockHistory.setCzr(user.getUsername() +" " + user.getRealname());
						stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_TRANSFER.getId());
						stockHistory.setCzzlx(32);
						stockHistory.setCzsm(str.toString());
						stockHistory.setKcid(materialHistory.getKcid());		
						stockHistoryMapper.insertSelective(stockHistory);
						
						//添加移出数据
						str.setLength(0);
						//str.append(stockHistory.getCzsm());
						str.append("从").append(oldStock.getCkh()).append(oldStock.getCkmc()==null?"":oldStock.getCkmc()).append(oldStock.getKwh()==null?"":oldStock.getKwh())
						.append("移动到").append(newStock.getCkh()).append(newStock.getCkmc()==null?"":newStock.getCkmc()).append(newStock.getKwh()==null?"":newStock.getKwh());
						oldStock.setId(id);
						oldStock.setKcsl(newStock.getKcsl());
						oldStock.setCkid(newStock.getCkid());
						oldStock.setCkh(newStock.getCkh());
						oldStock.setCkmc(newStock.getCkmc());
						oldStock.setKwid(newStock.getKwid());
						oldStock.setKwh(newStock.getKwh());
						oldStock.setWhrid(user.getId());
						oldStock.setWhsj(new Date());
						oldStock.setKwh(newStock.getKwh());
						if(newStock.getZt() ==1){
							oldStock.setDjsl(newStock.getKcsl());
						}else{
							oldStock.setDjsl(new BigDecimal(0));
						}
						//添加库存数据
						stockMapper.insertSelective(oldStock);
						oldStock.setUuiddm(uuiddm);
						//添加b_h_017
						materialHistory = new MaterialHistory(oldStock);
						materialHistoryId = UUID.randomUUID().toString();
						materialHistory.setId(materialHistoryId);
						materialHistoryMapper.insertSelective(materialHistory);
						//添加b_h_025
						stockHistory.setId(materialHistoryId);
						stockHistory.setDprtcode(oldStock.getDprtcode());
						stockHistory.setCzsj(new Date());
						stockHistory.setCzrid(user.getId());
						stockHistory.setCzrbmid(user.getBmdm());
						stockHistory.setCzr(user.getUsername() +" " + user.getRealname());
						stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_TRANSFER.getId());
						stockHistory.setCzzlx(31);
						stockHistory.setCzsm(str.toString());
						stockHistory.setKcid(materialHistory.getKcid());		
						stockHistoryMapper.insertSelective(stockHistory);
						
					}
					//根据原库存id批量修改冻结履历的库存id=新库存id
					if(newStock.getZt() ==1){
						List<FreezResume> freezResumeList =  freezResumeMapper.selectByKcid(newStock.getId());
						for (FreezResume freezResume : freezResumeList) {
							freezResume.setId(id);
							freezResumeMapper.updateByPrimaryKeySelective(freezResume);
						}
					}
				}
			}
		}
		
		
	}
	
	/**
	 * 
	 * @Description 根据检验单id查询库存
	 * @CreateTime 2018年3月27日 下午3:12:43
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 */
	@Override
	public List<Stock> queryjydid(String id) {
		return stockMapper.queryjydid(id);
	}
}
