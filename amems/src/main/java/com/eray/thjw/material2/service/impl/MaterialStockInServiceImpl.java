package com.eray.thjw.material2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.StockHistoryMapper;
import com.eray.thjw.material2.po.OutinReceiptShelves;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.po.StockInShelf;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.material2.service.MaterialStockInService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.material2.StockHistorySubtypeEnum;
import enu.material2.StockHistoryTypeEnum;
import enu.produce.NodeEnum;
import enu.project2.TodoStatusEnum;

/**
 * @Description 航材入库上架service实现类
 * @CreateTime 2018年3月21日 上午9:52:03
 * @CreateBy 韩武
 */
@Service("materialStockInService")
public class MaterialStockInServiceImpl implements MaterialStockInService {
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private MaterialBatchInfoService materialBatchInfoService;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private StockHistoryMapper stockHistoryMapper;
	
	@Resource
	private ComponentCertificateService componentCertificateService;
	
	@Resource
	private StoreInnerSearchMapper storeInnerSearchMapper;
	
	@Resource
	private ComponentMapper componentMapper;
	
	@Resource
	private TodorsService todorsService;

	/**
	 * @Description 查询航材入库上架列表
	 * @CreateTime 2018年3月21日 上午9:50:25
	 * @CreateBy 韩武
	 * @param param
	 * @return
	 */
	@Override
	public Map<String, Object> queryMaterialStockInList(Stock record) {
		
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		
		PageHelper.startPage(record.getPagination());
		List<Stock> list = stockMapper.queryStockByMaterialStockIn(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	/**
	 * @Description 查询上架详情
	 * @CreateTime 2018年3月22日 下午5:03:56
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Stock queryShelfDetail(String id) {
		return stockMapper.queryStockByMaterialStockInId(id);
	}

	/**
	 * @Description 入库上架
	 * @CreateTime 2018年3月23日 上午11:44:23
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void doPutOnShelf(StockInShelf record) throws BusinessException{
		
		User user=ThreadVarUtil.getUser();
		
		// 保存库存和履历
		saveStockAndRec(record);
		
		// 保存证书
		saveCertificates(record);
		
		//添加待办
		todorsService.updateToDo(record.getKcid(), NodeEnum.NODE31.getId(), TodoStatusEnum.DCL.getId(), user.getId());
	}
	
	/**
	 * @Description 保存证书
	 * @CreateTime 2018年3月26日 上午10:52:47
	 * @CreateBy 韩武
	 * @param record
	 */
	private void saveCertificates(StockInShelf shelf){
		
		InstallationListEditable record = new InstallationListEditable();
		record.setBjh(shelf.getBjh());
		record.setXlh(shelf.getXlh());
		record.setPch(shelf.getPch());
		record.setDprtcode(shelf.getDprtcode());
		record.setCertificates(shelf.getCertificates());
		record.setCzls(UUID.randomUUID().toString());
		record.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		componentCertificateService.save(record);
	}
	
	/**
	 * @Description 保存库存和履历
	 * @CreateTime 2018年3月23日 下午1:49:28
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	private void saveStockAndRec(StockInShelf record) throws BusinessException{
		if(record.getShelves() != null && !record.getShelves().isEmpty()){
			Stock stock = null;
			for (int i = 0; i < record.getShelves().size(); i++) {
				// 上架信息
				OutinReceiptShelves shelf = record.getShelves().get(i);
				// 第一个修改原有库存信息
				if(i == 0){
					// 查询原有的库存信息
					stock = stockMapper.selectByPrimaryKey(record.getKcid());
					if(!StockStatusEnum.RECEIVED.getId().equals(stock.getZt())){
						throw new BusinessException("该数据已更新，请刷新后再进行操作!");
					}
					
					// 实体转换（上架-->库存）
					convertShelfToStock(record, shelf, stock);
					
					// 更新原有的上架信息
					stockMapper.updateByPrimaryKeySelective(stock);
				}
				// 新增库存
				else{
					// 实体转换（上架-->库存）
					convertShelfToStock(record, shelf, stock);
					
					// 新增库存
					stock.setId(UUID.randomUUID().toString());
					stockMapper.insertSelective(stock);
				}
				
				// 保存部件履历和库存履历
				saveComponentHistoryAndStockHistory(stock);
				
				// 插入或更新物料批次信息
				materialBatchInfoService.insertOrUpdate(stock.getDprtcode(), stock.getBjh(), 
						stock.getSn(), stock.getPch(), stock.getKccb(), stock.getBiz(), 
						stock.getJz(), stock.getJzbz());
				
				// 新增或更新部件信息
				insertOrUpdateComponentInfo(stock);
			}
		}
	}
	
	/**
	 * @Description 新增或更新部件信息
	 * @CreateTime 2018年3月19日 上午11:22:20
	 * @CreateBy 韩武
	 * @param shelf
	 */
	private void insertOrUpdateComponentInfo(Stock record){
		if(StringUtils.isNotBlank(record.getSn())){
			
			// 根据部件号+序列号+组织机构更新b_h_010
			Component param = new Component();
			param.setJh(record.getBjh());
			param.setXlh(record.getSn());
			param.setDprtcode(record.getDprtcode());
			param.setChucrq(record.getScrq());
			param.setTsn(record.getTsn());
			param.setTso(record.getTso());
			param.setCsn(StringUtils.isBlank(record.getCsn()) ? null : Integer.parseInt(record.getCsn()));
			param.setCso(StringUtils.isBlank(record.getCso()) ? null : Integer.parseInt(record.getCso()));
			Component result = componentMapper.findByJhAndXlh(param);
			
			if(result == null){
				param.setId(UUID.randomUUID().toString());
				componentMapper.insertSelective(param);
			}
			
			//根据库存id更新b_h2_025
			storeInnerSearchMapper.batchInsertOrUpdateBj(record);
		}
	}
	
	/**
	 * @Description 实体转换（上架-->库存）
	 * @CreateTime 2018年3月26日 上午9:45:58
	 * @CreateBy 韩武
	 * @param record
	 * @param shelf
	 * @param stock
	 */
	private void convertShelfToStock(StockInShelf record, OutinReceiptShelves shelf, Stock stock){
		stock.setCkh(shelf.getCkh());
		stock.setCkid(shelf.getCkid());
		stock.setCklb(shelf.getCklb());
		stock.setCkmc(shelf.getCkmc());
		stock.setKwh(shelf.getKwh());
		stock.setKwid(shelf.getKwid());
		stock.setKcsl(shelf.getSjsl());
		stock.setJldw(shelf.getJldw());
		stock.setKccb(shelf.getKccb());
		stock.setBiz(shelf.getBiz());
		stock.setJz(shelf.getJz());
		stock.setJzbz(shelf.getJzbz());
		stock.setHcly(record.getHcly());
		stock.setGrn(record.getGrn());
		stock.setScrq(record.getScrq());
		stock.setHjsm(record.getHjsm());
		stock.setTsn(record.getTsn());
		stock.setTso(record.getTso());
		stock.setCsn(record.getCsn());
		stock.setCso(record.getCso());
		stock.setCfyq(record.getCfyq());
		stock.setQczt(record.getQczt());
		stock.setZt(StockStatusEnum.NORMAL.getId());
		
		User user = ThreadVarUtil.getUser();
		stock.setWhrid(user.getId());
		stock.setWhsj(new Date());
	}
	
	/**
	 * @Description 保存部件履历和库存履历
	 * @CreateTime 2018年3月26日 上午10:44:09
	 * @CreateBy 韩武
	 * @param stock
	 */
	private void saveComponentHistoryAndStockHistory(Stock stock){
		
		// 写部件履历
		stock.setUuiddm(stockMapper.selectUUIDDMbyId(stock.getId()));
		MaterialHistory materialHistory = new MaterialHistory(stock);
		materialHistory.setId(UUID.randomUUID().toString());
		materialHistoryMapper.insertSelective(materialHistory);
		
		// 写库存履历
		User user = ThreadVarUtil.getUser();
		StockHistory stockHistory = new StockHistory();
		stockHistory.setId(materialHistory.getId());
		stockHistory.setDprtcode(materialHistory.getDprtcode());
		stockHistory.setCzsj(new Date());
		stockHistory.setCzrbmid(user.getBmdm());
		stockHistory.setCzrid(user.getId());
		stockHistory.setCzr(user.getDisplayName());
		stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_RECEIPT.getId());
		stockHistory.setCzzlx(StockHistorySubtypeEnum.STOCK_RECEIPT_ON_SHELF.getId());
		stockHistory.setCzsm("收货-入库上架");
		stockHistory.setKcid(materialHistory.getKcid());
		stockHistoryMapper.insertSelective(stockHistory);
	}
}