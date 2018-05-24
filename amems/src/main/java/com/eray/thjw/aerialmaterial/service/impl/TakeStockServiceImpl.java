package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.dao.TakeStockDetailMapper;
import com.eray.thjw.aerialmaterial.dao.TakeStockMapper;
import com.eray.thjw.aerialmaterial.dao.TakeStockScopeMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.po.TakeStock;
import com.eray.thjw.aerialmaterial.po.TakeStockDetail;
import com.eray.thjw.aerialmaterial.po.TakeStockScope;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.TakeStockService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SaiBongEnum;
import enu.aerialmaterial.TakeStockStatusEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 盘点service,用于业务逻辑处理
 * @develop date 2016.11.21
 */
@Service
public class TakeStockServiceImpl implements TakeStockService {
	
	/**
	 * @author liub
	 * @description 盘点Mapper
	 * @develop date 2016.11.21
	 */
    @Resource
    private TakeStockMapper takeStockMapper;
    
    /**
	 * @author liub
	 * @description 盘点范围Mapper
	 * @develop date 2016.11.22
	 */
    @Resource
    private TakeStockScopeMapper takeStockScopeMapper;
    
    /**
	 * @author liub
	 * @description 盘点明细Mapper
	 * @develop date 2016.11.23
	 */
    @Resource
    private TakeStockDetailMapper takeStockDetailMapper;
    
    /**
	 * @author liub
	 * @description 采番service
	 * @develop date 2016.11.22
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	/**
	 * @author liub
	 * @description 仓库Mapper
	 * @develop date 2016.11.22
	 */
    @Resource
    private StoreMapper storeMapper;
    
    /**
	 * @author liub
	 * @description 库存Mapper
	 * @develop date 2016.11.01
	 */
    @Resource
	private StockMapper stockMapper;
    
    /**
	 * @author liub
	 * @description 部件履历service
	 * @develop date 2016.11.01
	 */
	@Autowired
	private MaterialHistoryService materialHistoryService;
	
    /**
	 * @author liub
	 * @description 新增盘点单
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	@Override
	public String addTakeStock(TakeStock takeStock) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String pddh;
		try {
			pddh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.YPKD.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		Store store = storeMapper.selectByPrimaryKey(takeStock.getCkid());
		takeStock.setId(id);
		takeStock.setPddh(pddh);
		takeStock.setCklb(store.getCklb());
		takeStock.setCkh(store.getCkh());
		takeStock.setCkmc(store.getCkmc());
		takeStock.setZt(TakeStockStatusEnum.SAVE.getId());
		takeStock.setZdrid(user.getId());
		takeStock.setZdbmid(user.getBmdm());
		takeStockMapper.insertSelective(takeStock);
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改盘点单
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	@Override
	public void editTakeStock(TakeStock takeStock) throws BusinessException{
		TakeStock obj = takeStockMapper.selectByPrimaryKey(takeStock.getId());
		verification(new Integer[]{TakeStockStatusEnum.SAVE.getId()},obj.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		takeStockMapper.updateByPrimaryKeySelective(takeStock);
	}
	
	/**
	 * @author liub
	 * @description 新增盘点范围
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	@Override
	public void addTakeScope(TakeStock takeStock) throws BusinessException{
		TakeStock obj = takeStockMapper.selectByPrimaryKey(takeStock.getId());
		verification(new Integer[]{TakeStockStatusEnum.SAVE.getId()},obj.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		if(null != takeStock.getTakeStockScopeList()){
			for (TakeStockScope takeStockScope : takeStock.getTakeStockScopeList()) {
				User user = ThreadVarUtil.getUser();
				UUID uuid = UUID.randomUUID();//获取随机的uuid
				String id = uuid.toString();
				takeStockScope.setId(id);
				takeStockScope.setZt(EffectiveEnum.YES.getId());
				takeStockScope.setWhrid(user.getId());
				takeStockScope.setWhdwid(user.getBmdm());
				takeStockScopeMapper.insertSelective(takeStockScope);
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 新增盘点详情根据库存
	 * @param takeStockDetail
	 * @develop date 2016.11.24
	 * @throws BusinessException 
	 */
	@Override
	public void addDetailFromStock(TakeStockDetail takeStockDetail) throws BusinessException{
		TakeStock obj = takeStockMapper.selectByPrimaryKey(takeStockDetail.getMainid());
		verification(new Integer[]{TakeStockStatusEnum.SAVE.getId()},obj.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		//新增部件履历
		Stock stock = stockMapper.queryById(takeStockDetail.getStock().getId());
		MaterialHistory materialHistory = new MaterialHistory(stock);
		UUID materialHistoryUuid = UUID.randomUUID();//获取随机的uuid
		String materialHistoryId = materialHistoryUuid.toString();
		materialHistory.setId(materialHistoryId);
		materialHistoryService.insert(materialHistory);
		//新增盘点详情
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		takeStockDetail.setId(id);
		takeStockDetail.setKcllid(materialHistoryId);
		takeStockDetail.setKcsl(stock.getKcsl());
		takeStockDetail.setWhrid(user.getId());
		takeStockDetail.setWhdwid(user.getBmdm());
		takeStockDetailMapper.insertSelective(takeStockDetail);
	}
	
	/**
	 * @author liub
	 * @description 新增盘点详情根据航材
	 * @param takeStockDetail
	 * @develop date 2016.11.24
	 * @throws BusinessException 
	 */
	@Override
	public void addDetailFromMaterial(TakeStockDetail takeStockDetail) throws BusinessException{
		TakeStock obj = takeStockMapper.selectByPrimaryKey(takeStockDetail.getMainid());
		verification(new Integer[]{TakeStockStatusEnum.SAVE.getId()},obj.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		//新增部件履历
		MaterialHistory materialHistory = takeStockDetail.getMaterialHistory();
		Store store = storeMapper.selectByPrimaryKey(materialHistory.getCkid());
		UUID materialHistoryUuid = UUID.randomUUID();//获取随机的uuid
		String materialHistoryId = materialHistoryUuid.toString();
		materialHistory.setId(materialHistoryId);
		materialHistory.setCklb(store.getCklb());
		materialHistory.setCkh(store.getCkh());
		materialHistory.setCkmc(store.getCkmc());
		materialHistory.setKcsl(BigDecimal.ZERO);
		materialHistory.setDprtcode(takeStockDetail.getDprtcode());
		materialHistoryService.insertSelective(materialHistory);
		//新增盘点详情
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		takeStockDetail.setId(id);
		takeStockDetail.setKcllid(materialHistoryId);
		takeStockDetail.setKcsl(new BigDecimal("0"));
		takeStockDetail.setWhrid(user.getId());
		takeStockDetail.setWhdwid(user.getBmdm());
		takeStockDetailMapper.insertSelective(takeStockDetail);
	}
	
	/**
	 * @author liub
	 * @description 修改盘点详情,及部件履历
	 * @param takeStockDetail
	 * @develop date 2016.11.25
	 * @throws BusinessException 
	 */
	@Override
	public void eidtTakeStockDetail(TakeStockDetail takeStockDetail) throws BusinessException{
		TakeStock obj = takeStockMapper.selectByPrimaryKey(takeStockDetail.getMainid());
		verification(new Integer[]{TakeStockStatusEnum.SAVE.getId()},obj.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		//修改盘点详情
		takeStockDetail.setWhrid(user.getId());
		takeStockDetail.setWhdwid(user.getBmdm());
		takeStockDetailMapper.updateByPrimaryKeySelective(takeStockDetail);
		//修改部件履历
		MaterialHistory materialHistory = takeStockDetail.getMaterialHistory();
		if(null != takeStockDetail.getKcllid() && null != materialHistory){
			materialHistoryService.updateByPrimaryKeySelective(materialHistory);
		}
	}
	
	/**
	 * @author liub
	 * @description 删除盘点范围
	 * @param id
	 * @develop date 2016.11.23
	 * @throws BusinessException 
	 */
	@Override
	public void delTakeScope(String takeScopeId,String mainId) throws BusinessException{
		TakeStock obj = takeStockMapper.selectByPrimaryKey(mainId);
		verification(new Integer[]{TakeStockStatusEnum.SAVE.getId()},obj.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		takeStockScopeMapper.deleteByPrimaryKey(takeScopeId);
	}
	
	/**
	 * @author liub
	 * @description 审核
	 * @param takeStock
	 * @develop date 2016.11.29
	 * @throws BusinessException 
	 */
	@Override
	public void editAudit(TakeStock takeStock) throws BusinessException{
		TakeStock oldTakeStock = takeStockMapper.selectByPrimaryKey(takeStock.getId());
		verification(new Integer[]{TakeStockStatusEnum.SUBMIT.getId()},oldTakeStock.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		takeStock.setZt(TakeStockStatusEnum.APPROVED.getId());
		takeStock.setShrid(user.getId());
		takeStock.setShbmid(user.getBmdm());
		takeStockMapper.updateByPrimaryKeySelective(takeStock);
		//获取盘点差异信息,修改库存履历信息
		List<TakeStockDetail> takeStockDetailList = takeStockDetailMapper.queryByMainId(takeStock.getId());
		for (TakeStockDetail takeStockDetail : takeStockDetailList) {
			BigDecimal cksl = takeStockDetail.getCksl();//盘点数量
			if(null != takeStockDetail.getStock()){
				//将库存信息保存到履历表
				Stock stock = stockMapper.queryById(takeStockDetail.getStock().getId());
				MaterialHistory materialHistory = new MaterialHistory(stock);
				materialHistory.setId(takeStockDetail.getKcllid());
				materialHistoryService.updateByPrimaryKeySelective(materialHistory);
				//修改库存信息
				if(null != cksl && cksl.compareTo(BigDecimal.ZERO) == 0){
					stockMapper.delete(stock);
				}else{
					stock.setKcsl(cksl);
					stockMapper.update(stock);
				}
			}else{
				//如果库存不存在
				MaterialHistory materialHistory = materialHistoryService.selectByPrimaryKey(takeStockDetail.getKcllid());
				//新增库存信息
				if(null != cksl && cksl.compareTo(BigDecimal.ZERO) == 1){
					UUID uuid = UUID.randomUUID();//获取随机的uuid
					String kcid = uuid.toString();
					materialHistory.setKcid(kcid);
					Stock stock = new Stock(materialHistory);
					stock.setKcsl(cksl);
					stockMapper.insertSelective(stock);
				}
				materialHistory.setKcsl(BigDecimal.ZERO);
				materialHistoryService.updateByPrimaryKeySelective(materialHistory);
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 撤销
	 * @param takeStock
	 * @develop date 2016.11.29
	 * @throws BusinessException 
	 */
	@Override
	public void editRecall(TakeStock takeStock) throws BusinessException{
		TakeStock oldTakeStock = takeStockMapper.selectByPrimaryKey(takeStock.getId());
		verification(new Integer[]{TakeStockStatusEnum.APPROVED.getId()},oldTakeStock.getZt(),"该盘点单已更新，请刷新后再进行操作!");
		takeStock.setZt(TakeStockStatusEnum.RECALL.getId());
		takeStockMapper.updateByPrimaryKeySelective(takeStock);
		//获取盘点差异信息,修改库存信息
		List<TakeStockDetail> takeStockDetailList = takeStockDetailMapper.queryByMainId(takeStock.getId());
		for (TakeStockDetail takeStockDetail : takeStockDetailList) {
			MaterialHistory materialHistory = takeStockDetail.getMaterialHistory();
			BigDecimal kcsl = materialHistory.getKcsl();//库存数量
			if(null != takeStockDetail.getStock()){				
				if(null != kcsl && kcsl.compareTo(BigDecimal.ZERO) == 0){
					stockMapper.delete(takeStockDetail.getStock());
				}else{
					MaterialHistory oldMaterialHistory = materialHistoryService.selectByPrimaryKey(takeStockDetail.getKcllid());
					Stock stock = new Stock(oldMaterialHistory);
					stockMapper.update(stock);
				}
			}
			//新增库存信息
			if(null == takeStockDetail.getStock() && null != kcsl && kcsl.compareTo(BigDecimal.ZERO) == 1){
				MaterialHistory oldMaterialHistory = materialHistoryService.selectByPrimaryKey(takeStockDetail.getKcllid());
				Stock stock = new Stock(oldMaterialHistory);
				stockMapper.insertSelective(stock);
			}
		}
	}
    
    /**
	 * @author liub
	 * @description 根据仓库id查询盘点单列表
	 * @param ckid
	 * @return>
	 * @develop date 2016.11.21
	 */
	public void getTakeStockListByCkid(String ckid){
		takeStockMapper.selectByPrimaryKey(ckid);
	}
	
	 /**
	 * @author liub
	 * @description  根据id查询盘点单
	 * @param id
	 * @return TakeStock
	 * @develop date 2016.11.22
	 */
	@Override
	public TakeStock getById(String id){
		return takeStockMapper.getById(id);
	}
	
	/**
	 * @author liub
	 * @description  根据仓库id查询盘点单列表
	 * @param ckid
	 * @return List<TakeStock>
	 * @develop date 2016.11.22
	 */
	@Override
	public List<TakeStock> queryListByCkid(String ckid){
		return takeStockMapper.queryListByCkid(ckid);
	}
	
	/**
	 * @author liub
	 * @description  根据盘点id查询盘点范围列表
	 * @param ckid
	 * @return List<TakeStockScope>
	 * @develop date 2016.11.22
	 */
	@Override
	public List<TakeStockScope> queryTakeScopeListByMainId(String mainid){
		return takeStockScopeMapper.queryTakeScopeListByMainId(mainid);
	}
	
	/**
	 * @author liub
	 * @description  根据盘点差异id查询盘点差异及履历信息
	 * @param id
	 * @return TakeStockDetail
	 * @develop date 2016.11.25
	 */
	public TakeStockDetail queryByDetailId(String id){
		return takeStockDetailMapper.queryByDetailId(id);
	}
	
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询盘点单信息
	 * @param takeStock
	 * @return List<TakeStock>
	 * @develop date 2016.11.21
	 */
	@Override
	public List<TakeStock> queryAllPageList(TakeStock takeStock){
		return takeStockMapper.queryAllPageList(takeStock);
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.23
	 */
	@Override
	public List<TakeStockDetail> queryAllDetailPageList(TakeStockDetail takeStockDetail){
		return takeStockDetailMapper.queryAllDetailPageList(takeStockDetail);
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询库存及盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return List<TakeStockDetail>
	 * @develop date 2016.11.23
	 */
	@Override
	public List<TakeStockDetail> queryStockDetailPageList(TakeStockDetail takeStockDetail){
		return takeStockDetailMapper.queryStockDetailPageList(takeStockDetail);
	}
	
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}
	
}
