package com.eray.thjw.aerialmaterial.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.OutstockDetailMapper;
import com.eray.thjw.aerialmaterial.dao.OutstockMapper;
import com.eray.thjw.aerialmaterial.dao.SecondmentMapper;
import com.eray.thjw.aerialmaterial.dao.StockFreezeHistoryMapper;
import com.eray.thjw.aerialmaterial.po.CancelStock;
import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;
import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.MaterialRequisition;
import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.po.OverseasListing;
import com.eray.thjw.aerialmaterial.po.Piecenumber;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.po.ScrapList;
import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.CancelStockService;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;
import com.eray.thjw.aerialmaterial.service.ExpatriateDetailService;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.aerialmaterial.service.HasScrappedListService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.MaterialRepairService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionDetailService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.OutstockDetailService;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.aerialmaterial.service.OverseasListingService;
import com.eray.thjw.aerialmaterial.service.ReserveMainService;
import com.eray.thjw.aerialmaterial.service.ScrapListService;
import com.eray.thjw.aerialmaterial.service.StockFreezeHistoryService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.OutStockTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StatusEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.common.EffectiveEnum;

@Service
public class OutstockServiceImpl implements OutstockService {
	
	private static String SHOUGONG="手工制单";
	
	@Resource
	private OutstockMapper outstockMapper;
	
	@Resource
	private ReserveMainService reserveMainService;
	@Resource
	private SaibongUtilService saibongUtilService;

	@Resource
	private OutstockDetailService outstockDetailService;
	
	@Resource
	private MaterialRequisitionService materialRequisitionService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private OutFieldStockService outFieldStockService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private MaterialRequisitionDetailService materialRequisitionDetailService;
	
	@Resource
	private MaterialRepairService materialRepairService;
	
	@Resource
	private OverseasListingService overseasListingService;
	
	@Resource
	private ExpatriateService expatriateService;
	
	@Resource
	private ExpatriateDetailService expatriateDetailService;
	
	@Resource
	private ScrapListService scrapListService;
	
	@Resource
	private HasScrappedListService hasScrappedListService;
	
	@Resource
	private CancelStockService cancelStockService;
	
	@Resource
	private ContractDetailService contractDetailService;
	
	@Resource
	private SecondmentMapper secondmentMapper;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private OutstockDetailMapper outstockDetailMapper;
	
	@Resource
	private StockFreezeHistoryMapper stockFreezeHistoryMapper;
	
	@Resource
	private StockFreezeHistoryService stockFreezeHistoryService;
	
	
	/**
	 *领用出库 
	 */
	@Override
	public Map<String, Object> save(Outstock outstock) throws BusinessException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//根据id查询领用申请表数据
		MaterialRequisition materialRequisition=materialRequisitionService.selectByPrimaryKey(outstock.getXgdjid());
		
		MaterialRequisition materialRequisition2=materialRequisitionService.selectByPrimaryKey(materialRequisition.getId());
		if(materialRequisition2.getZt()!=2){
			throw new BusinessException("当前领用申请单状态为"+ContractStatusEnum.getName(materialRequisition2.getZt())+",不可出库");
		}
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck;
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(id);
		outstock.setCklx(1);
		outstock.setCkdh(lyck);
		outstock.setFjzch(materialRequisition.getFjzch());
		outstock.setZdbmid(user.getBmdm());
		outstock.setSqrid(materialRequisition.getSqrid());
		outstock.setSqsj(materialRequisition.getSqrq());
		outstock.setDprtcode(user.getJgdm());
		outstock.setSgbs(0);
		outstock.setZt(2);
		outstock.setBz(outstock.getBz());
		outstock.setZdrid(user.getId());
		outstock.setSqbmid(materialRequisition.getSqbmid());
		outstock.setXgdjid(materialRequisition.getId());
		outstock.setZdsj(new Date());
		outstockMapper.insertSelective(outstock);
		 
		//更新领用申请单为完成
		MaterialRequisition materialRequisition1=new MaterialRequisition();
		materialRequisition1.setZt(10);
		materialRequisition1.setId(materialRequisition.getId());
		materialRequisitionService.updateByPrimaryKeySelective(materialRequisition1);
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			
					//获取库存表所有信息
					Stock stock1=new Stock();
					stock1.setId(materialRequisitionDetail.getKcid());
					Stock stock=stockSerivce.queryKeys(stock1);
			
					//保存部件履库存历表
					MaterialHistory materialHistory=new MaterialHistory(stock);
					UUID uuid3 = UUID.randomUUID();//获取随机的uuid
					String historyId = uuid3.toString();
					materialHistory.setId(historyId);
					BigDecimal cks=new BigDecimal(materialRequisitionDetail.getCksl());
					materialHistory.setKcsl(cks);
					materialHistoryService.insert(materialHistory);
					
					 //保存出库明细表
					OutstockDetail outstockDetail=new OutstockDetail();
					UUID uuid2 = UUID.randomUUID();//获取随机的uuid
					String detailId = uuid2.toString();
					outstockDetail.setId(detailId);
					outstockDetail.setMainid(id);
					outstockDetail.setLydid(materialRequisition.getId());
					outstockDetail.setLydmxid(materialRequisitionDetail.getId());
					outstockDetail.setKcllid(historyId);
					outstockDetail.setWhdwid(user.getBmdm());
					outstockDetail.setWhrid(user.getId());
					outstockDetail.setWhsj(new Date());
					outstockDetail.setZt(1);
					BigDecimal aDouble =new BigDecimal(0.00);
					outstockDetail.setTksl(aDouble);
					outstockDetailService.insert(outstockDetail); 
					
					// 保存b_h_003 外场虚拟库存表 -有rec表
					OutFieldStock outFieldStock=new OutFieldStock(stock);
					UUID uuid4 = UUID.randomUUID();//获取随机的uuid
					String outFieldStockId = uuid4.toString();
					outFieldStock.setId(outFieldStockId);
					outFieldStock.setKcsl(cks);
					outFieldStock.setXgdjid(detailId);
					outFieldStockService.insert(outFieldStock);
					//commonRecService.write(outFieldStockId, TableEnum.B_H_003, user.getId(), UpdateTypeEnum.SAVE);
					
					UUID czls = UUID.randomUUID();//获取随机的uuid
					materialRecService.writeOutfieldRec(uuid4.toString(), czls.toString(), id, lyck, OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE, OutStockTypeEnum.TYPE1.getName(),"",lyck,materialRequisition.getLysqdh(),cks);
					
					
					//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
					BigDecimal kcsl =stock.getKcsl();
					 if(kcsl.compareTo(cks)==-1){
							returnMap.put("state", "error");
							returnMap.put("message", "出库数量不能大于库存数量!");
						return returnMap;
					 }else if(kcsl.compareTo(cks)==0){
						 //当库存为0时删除
						 stockSerivce.delete(stock);
						// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE);
						 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE1.getName(),"",lyck,materialRequisition.getLysqdh(),cks);
					 }else{
						 //库存数减去出库数
						 BigDecimal kucuns = kcsl.subtract(cks);   	
						 stock.setKcsl(kucuns);
						 stockSerivce.update(stock);
						 //commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
						 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE1.getName(),"",lyck,materialRequisition.getLysqdh(),cks);
					 }
					 
					 //查询领用明细表信息
					 MaterialRequisitionDetail materialRequisitionDetail1=materialRequisitionDetailService.selectByPrimaryKey(materialRequisitionDetail.getId());
					// 当前完成数量加上出库数量
					 materialRequisitionDetail1.setYlysl(materialRequisitionDetail1.getYlysl().add(cks));
					 materialRequisitionDetail1.setId(materialRequisitionDetail1.getId());
					materialRequisitionDetailService.updateByPrimaryKey(materialRequisitionDetail1);
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 送修出库
	 */
	@Override
	public Map<String, Object> saveOutbound(MaterialRepair materialRepair)throws BusinessException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//查询送修单-航材表
		MaterialRepair list=materialRepairService.queryAllList(materialRepair);
		
		//查询送修申请单
		ReserveMain reserveMain=reserveMainService.selectById(list.getXgdjid());
		
		if(reserveMain.getZt()!=3){
			throw new BusinessException("当前送修申请单状态为"+ReserveStatusEnum.getName(reserveMain.getZt())+",不可出库");
		}
		
		User user = ThreadVarUtil.getUser();
		
		//保存出库单
		Outstock outstock=new Outstock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck ;	
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(id);
		outstock.setDprtcode(user.getJgdm());
		outstock.setCklx(2);
		outstock.setSgbs(0);
		outstock.setCkdh(lyck);
		outstock.setCksj(materialRepair.getCksj());
		outstock.setZt(2);
		outstock.setZdbmid(user.getBmdm());
		outstock.setZdrid(user.getId());
		outstock.setZdsj(new Date());
		outstock.setCkbmid(materialRepair.getCkbmid());
		outstock.setCukid(materialRepair.getCukid());
		outstock.setSqbmid(list.getSqbmid());
		outstock.setSqrid(list.getSqrid());
		outstock.setSqsj(list.getSqsj());
		outstock.setBz(materialRepair.getBz());
		outstock.setXgdjid(list.getXgdjid());
		outstockMapper.insertSelective(outstock);
		
		//获取出库航材信息
		MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(list.getKcllid());
		
		//获取库存表所有信息
		Stock stock1=new Stock();
		stock1.setId(list.getKcid());
		
		Stock stock=stockSerivce.queryKeys1(stock1);
		
		//保存部件履库存历表
		MaterialHistory materialHistory=new MaterialHistory(stock);
		UUID uuid3 = UUID.randomUUID();//获取随机的uuid
		String historyId = uuid3.toString();
		materialHistory.setId(historyId);
		//出库数转化为BigDecimal
		BigDecimal cks=new BigDecimal(1);
		materialHistory.setKcsl(cks);
		materialHistoryService.insert(materialHistory);
		
		//删除库存
		stockSerivce.delete(stock);
		UUID czls = UUID.randomUUID();//获取随机的uuid	
	    //commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE);
		 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE1.getName(),"",lyck,list.getXgdjid(),cks);
	    //根据tddhcid查询承销商id
	    ContractDetail ContractDetail=contractDetailService.queryKey(list.getId());
	    
		//保存外派清单
		OverseasListing overseasListing=new OverseasListing();
		UUID uuid4 = UUID.randomUUID();//获取随机的uuid
		String detailIds = uuid4.toString();
		overseasListing.setId(detailIds);
		overseasListing.setDprtcode(user.getJgdm());
		overseasListing.setJdlx(StatusEnum.SENDREPAIR.getId());
		if(ContractDetail!=null){
			overseasListing.setWpdxid(ContractDetail.getGysid());
		}else{
			overseasListing.setWpdxid("");
		}
		
		overseasListing.setBjid(materialHistory1.getBjid());
		overseasListing.setBjh(materialHistory1.getBjh());
		overseasListing.setPch("");
		overseasListing.setSn(materialHistory1.getSn());
		overseasListing.setKcsl(cks);
		BigDecimal aDouble =new BigDecimal(0.00);
		overseasListing.setGhsl(aDouble);
		overseasListing.setWpsj(new Date());
		overseasListing.setZt(EffectiveEnum.YES.getId());
		overseasListingService.insert(overseasListing);
		
		//添加外派清单附表
		ExpatriateDetail expatriateDetail=new ExpatriateDetail();
		UUID uuid5 = UUID.randomUUID();//获取随机的uuid
		String waipId = uuid5.toString();
		expatriateDetail.setId(waipId);
		expatriateDetail.setMainid(detailIds);
		expatriateDetail.setSjlx(1);
		expatriateDetail.setGldjlx(2);
		expatriateDetail.setDjid(id);
		expatriateDetail.setKcllid(historyId);
		expatriateDetail.setDprtcode(user.getJgdm());
		expatriateDetail.setCxsl(aDouble);
		expatriateDetailService.insertSelective(expatriateDetail);
		
		//保存出库明细表
		OutstockDetail outstockDetail=new OutstockDetail();
		UUID uuid2 = UUID.randomUUID();//获取随机的uuid
		String detailId = uuid2.toString();
		outstockDetail.setId(detailId);
		outstockDetail.setMainid(id);
		outstockDetail.setWpqdid(detailIds);
		outstockDetail.setKcllid(historyId);
		outstockDetail.setWhdwid(user.getBmdm());
		outstockDetail.setWhrid(user.getId());
		outstockDetail.setWhsj(new Date());
		outstockDetail.setZt(1);
		outstockDetail.setTksl(aDouble);
		outstockDetailService.insert(outstockDetail); 
		
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 手动领用出库
	 */
	@Override
	public Map<String, Object> manualsave(Outstock outstock)
			throws BusinessException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck ;	
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(id);
		outstock.setCklx(1);
		outstock.setCkdh(lyck);
		outstock.setFjzch(outstock.getFjzch());
		outstock.setZdbmid(user.getBmdm());
		outstock.setSqrid(outstock.getSqrid());
		outstock.setSqsj(outstock.getSqsj());
		outstock.setDprtcode(user.getJgdm());
		outstock.setSgbs(1);
		outstock.setZt(2);
		outstock.setBz(outstock.getBz());
		outstock.setZdrid(user.getId());
		outstock.setSqbmid(outstock.getSqbmid());
		outstock.setZdsj(new Date());
		outstockMapper.insertSelective(outstock);
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			
			//获取库存表所有信息
			Stock stock1=new Stock();
			stock1.setId(materialRequisitionDetail.getId());
			Stock stock=stockSerivce.queryKeys(stock1);
			
			//保存部件履库存历表
			MaterialHistory materialHistory=new MaterialHistory(stock);
			UUID uuid3 = UUID.randomUUID();//获取随机的uuid
			String historyId = uuid3.toString();
			materialHistory.setId(historyId);
			//出库数转化为BigDecimal
			BigDecimal cks=new BigDecimal(materialRequisitionDetail.getCksl());
			materialHistory.setKcsl(cks);
			materialHistoryService.insert(materialHistory);
					
			 //保存出库明细表
			OutstockDetail outstockDetail=new OutstockDetail();
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			outstockDetail.setId(detailId);
			outstockDetail.setMainid(id);
			outstockDetail.setKcllid(historyId);
			outstockDetail.setWhdwid(user.getBmdm());
			outstockDetail.setWhrid(user.getId());
			outstockDetail.setZt(1);
			BigDecimal aDouble =new BigDecimal(0.00);
			outstockDetail.setTksl(aDouble);
			outstockDetailService.insert(outstockDetail); 
					
			// 保存b_h_003 外场虚拟库存表 -有rec表
			OutFieldStock outFieldStock=new OutFieldStock(stock);
			UUID uuid4 = UUID.randomUUID();//获取随机的uuid
			String outFieldStockId = uuid4.toString();
			outFieldStock.setId(outFieldStockId);
			outFieldStock.setKcsl(cks);
			outFieldStock.setXgdjid(detailId);
			outFieldStockService.insert(outFieldStock);
			//commonRecService.write(outFieldStockId, TableEnum.B_H_003, user.getId(), UpdateTypeEnum.SAVE);
			
			UUID czls = UUID.randomUUID();//获取随机的uuid
			materialRecService.writeOutfieldRec(uuid4.toString(), czls.toString(), id, lyck, OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE, OutStockTypeEnum.TYPE1.getName(),"",lyck,SHOUGONG,cks);
			
			//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
			BigDecimal kcsl =stock.getKcsl();
			
			 if(kcsl.compareTo(cks)==-1){
					returnMap.put("state", "error");
					returnMap.put("message", "出库数量不能大于库存数量!");
				return returnMap;
			 }
			 
			 if(kcsl.compareTo(cks)==0){
				 //删除库存
				 stockSerivce.delete(stock);
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE1.getName(),"",lyck,SHOUGONG,cks);
			 }
			 
			 if(kcsl.compareTo(cks)==1){
				 //库存数减去出库数
				 BigDecimal kucuns = kcsl.subtract(cks);   	
				 stock.setKcsl(kucuns);
				 stockSerivce.update(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				 
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE1.getName(),"",lyck,SHOUGONG,cks);
			 }
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 借出出库
	 */
	@Override
	public Map<String, Object> checkoutsave(Outstock outstock)
			throws BusinessException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck;	
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(id);
		outstock.setDprtcode(user.getJgdm());
		outstock.setCklx(4);
		outstock.setCkdh(lyck);
		outstock.setCksj(outstock.getCksj());
		outstock.setZt(2);
		outstock.setSgbs(1);
		outstock.setZdbmid(user.getBmdm());
		outstock.setZdrid(user.getId());
		outstock.setZdsj(new Date());
		outstock.setCkbmid(outstock.getCkbmid());
		outstock.setCukid(outstock.getCukid());
		outstock.setSqbmid(outstock.getSqbmid());
		outstock.setSqrid(outstock.getSqrid());
		outstock.setJddxid(outstock.getJddxid());
		outstock.setJdfzr(outstock.getJdfzr());
		outstock.setBz(outstock.getBz());
		outstock.setSqsj(outstock.getSqsj());
		outstockMapper.insertSelective(outstock);
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			
			//获取库存表所有信息
			Stock stock1=new Stock();
			stock1.setId(materialRequisitionDetail.getId());
			Stock stock=stockSerivce.queryKeys(stock1);
			
			//保存部件履库存历表
			MaterialHistory materialHistory=new MaterialHistory(stock);
			UUID uuid3 = UUID.randomUUID();//获取随机的uuid
			String historyId = uuid3.toString();
			//出库数转化为BigDecimal
			BigDecimal cks=new BigDecimal(materialRequisitionDetail.getCksl());
			materialHistory.setId(historyId);
			materialHistory.setKcsl(cks);
			materialHistoryService.insert(materialHistory);
			
			//保存外派清单
			OverseasListing overseasListing=new OverseasListing();
			UUID uuid4 = UUID.randomUUID();//获取随机的uuid
			String detailIds = uuid4.toString();
			overseasListing.setId(detailIds);
			overseasListing.setDprtcode(user.getJgdm());
			overseasListing.setJdlx(StatusEnum.CHECKOUT.getId());
			overseasListing.setWpdxid(outstock.getJddxid());
			overseasListing.setBjid(materialRequisitionDetail.getBjid());
			overseasListing.setBjh(materialRequisitionDetail.getBjh());
			overseasListing.setPch(materialRequisitionDetail.getPch());
			overseasListing.setSn(materialRequisitionDetail.getSn());
			overseasListing.setKcsl(cks);
			BigDecimal aDouble =new BigDecimal(0.00);
			overseasListing.setGhsl(aDouble);
			overseasListing.setWpsj(new Date());
			overseasListing.setZt(EffectiveEnum.YES.getId());
			
			overseasListingService.insert(overseasListing);
			
			//添加外派清单附表
			ExpatriateDetail expatriateDetail=new ExpatriateDetail();
			UUID uuid5 = UUID.randomUUID();//获取随机的uuid
			String waipId = uuid5.toString();
			expatriateDetail.setId(waipId);
			expatriateDetail.setMainid(detailIds);
			expatriateDetail.setSjlx(1);
			expatriateDetail.setGldjlx(2);
			expatriateDetail.setDjid(id);
			expatriateDetail.setKcllid(historyId);
			expatriateDetail.setDprtcode(user.getJgdm());
			expatriateDetail.setCxsl(aDouble);
			expatriateDetailService.insertSelective(expatriateDetail);
			
			//保存出库明细表
			OutstockDetail outstockDetail=new OutstockDetail();
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			outstockDetail.setId(detailId);
			outstockDetail.setMainid(id);
			outstockDetail.setWpqdid(detailIds);
			outstockDetail.setKcllid(historyId);
			outstockDetail.setWhdwid(user.getBmdm());
			outstockDetail.setWhrid(user.getId());
			outstockDetail.setWhsj(new Date());
			outstockDetail.setZt(1);
			outstockDetail.setTksl(aDouble);
			outstockDetailService.insert(outstockDetail); 
			
			// 保存b_h_003 外场虚拟库存表 -有rec表
			OutFieldStock outFieldStock=new OutFieldStock(stock);
			UUID uuid6 = UUID.randomUUID();//获取随机的uuid
			String outFieldStockId = uuid6.toString();
			outFieldStock.setId(outFieldStockId);
			outFieldStock.setKcsl(cks);
			
			outFieldStockService.insert(outFieldStock);
			//commonRecService.write(outFieldStockId, TableEnum.B_H_003, user.getId(), UpdateTypeEnum.SAVE);
			
			UUID czls = UUID.randomUUID();//获取随机的uuid	

			materialRecService.writeOutfieldRec(uuid4.toString(), czls.toString(), id, lyck, OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE, OutStockTypeEnum.TYPE4.getName(),"",lyck,outstock.getJddxid(),cks);

			//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
			BigDecimal kcsl =stock.getKcsl();
			
			 if(kcsl.compareTo(cks)==-1){
					returnMap.put("state", "error");
					returnMap.put("message", "出库数量不能大于库存数量!");
				return returnMap;
			 }
			 
			 if(kcsl.compareTo(cks)==0){
				 stockSerivce.delete(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE);
				 
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE4.getName(),"",lyck,outstock.getJddxid(),cks);
			 }
			 
			 if(kcsl.compareTo(cks)==1){
				 //库存数减去出库数
				 BigDecimal kucuns = kcsl.subtract(cks);   	
				 
				 stock.setKcsl(kucuns);
				 stockSerivce.update(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE4.getName(),"",lyck,outstock.getJddxid(),cks);
			 }
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 返回分配
	 */
	@Override
	public Map<String, Object> checkoutstockassign(Outstock outstock)
			throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Expatriate> returnList = new ArrayList<Expatriate>();

		for (Piecenumber piecenumber : outstock.getPiecenumber()) {
			List<Expatriate> list = expatriateService.queryLendListchecka(outstock.getDprtcode(), outstock.getJddxid(), piecenumber.getBjid());
		
			if(null != list && !list.isEmpty()){
				for (Expatriate expatriate : list) {
					BigDecimal tempCount = new BigDecimal(0).setScale(2);
					tempCount = tempCount.add(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
					
					if (tempCount.compareTo(piecenumber.getCksl()) == 1) {
						expatriate.setGhsl(piecenumber.getCksl().setScale(2));
						returnList.add(expatriate);
						break;
					}else if(tempCount.compareTo(piecenumber.getCksl()) == 0) {
						expatriate.setGhsl(piecenumber.getCksl().setScale(2));
						returnList.add(expatriate);
						break;
					}else{
						expatriate.setGhsl(tempCount.setScale(2));
						piecenumber.setCksl(piecenumber.getCksl().setScale(2).subtract(tempCount.setScale(2)));
						returnList.add(expatriate);
					}
				}
			}

		}
		
		resultMap.put("rows",returnList);
		
		return resultMap;
	}

	/**
	 * 归还出库
	 */
	@Override
	public Map<String, Object> returntheoutbound(Outstock outstock) throws BusinessException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck ;	
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(id);
		outstock.setDprtcode(user.getJgdm());
		outstock.setCklx(3);
		outstock.setSgbs(1);
		outstock.setCkdh(lyck);
		outstock.setCksj(outstock.getCksj());
		outstock.setZt(2);
		outstock.setZdbmid(user.getBmdm());
		outstock.setZdrid(user.getId());
		outstock.setZdsj(new Date());
		outstock.setCkbmid(outstock.getCkbmid());
		outstock.setCukid(outstock.getCukid());
		outstock.setSqbmid(outstock.getSqbmid());
		outstock.setSqrid(outstock.getSqrid());
		outstock.setJddxid(outstock.getJddxid());
		outstock.setJdfzr(outstock.getJdfzr());
		outstock.setBz(outstock.getBz());
		outstock.setSqsj(outstock.getSqsj());
		outstockMapper.insertSelective(outstock);
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			BigDecimal ck = new BigDecimal(materialRequisitionDetail.getCksl()).setScale(2);
			
			List<Expatriate> list = expatriateService.queryLendListcheck(outstock.getDprtcode(), outstock.getJddxid(), materialRequisitionDetail.getBjid(),ck);
			
			 //更新外派清单表，并且保存外派清单附表
			for (Expatriate expatriate : list) {
				
				Stock stock1=new Stock();
				stock1.setId(materialRequisitionDetail.getId());
				
				//获取库存表所有信息
				Stock stock=stockSerivce.queryKeys(stock1);
				
				//保存部件履库存历表
				MaterialHistory materialHistory=new MaterialHistory(stock);
				UUID uuid3 = UUID.randomUUID();//获取随机的uuid
				String historyId = uuid3.toString();
				materialHistory.setId(historyId);
				materialHistory.setKcsl(expatriate.getDghsl()); //本次归还数量
				materialHistoryService.insert(materialHistory);
				
				 //保存出库明细表
				OutstockDetail outstockDetail=new OutstockDetail();
				UUID uuid2 = UUID.randomUUID();//获取随机的uuid
				String detailId = uuid2.toString();
				outstockDetail.setId(detailId);
				outstockDetail.setMainid(id);
				outstockDetail.setWpqdid(expatriate.getId());
				outstockDetail.setKcllid(historyId);
				outstockDetail.setWhdwid(user.getBmdm());
				outstockDetail.setWhrid(user.getId());
				outstockDetail.setWhsj(new Date());
				outstockDetail.setZt(1);
				BigDecimal aDouble =new BigDecimal(0.00);
				outstockDetail.setTksl(aDouble);
				outstockDetailService.insert(outstockDetail); 
				
				//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
				BigDecimal kcsl =stock.getKcsl();
				
				 if(kcsl.compareTo(expatriate.getDghsl())==-1){
						returnMap.put("state", "error");
						returnMap.put("message", "出库数量不能大于库存数量!");
					return returnMap;
				 }
				 
					UUID czls = UUID.randomUUID();//获取随机的uuid	
				 if(kcsl.compareTo(expatriate.getDghsl())==0){
					 stockSerivce.delete(stock);
					// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE);
					 
					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE3.getName(),"",lyck,SHOUGONG,expatriate.getDghsl());
				 }
				 
				 if(kcsl.compareTo(expatriate.getDghsl())==1){
					 //库存数减去出库数
					 BigDecimal kucuns = kcsl.subtract(expatriate.getDghsl());   	
					 
					 stock.setKcsl(kucuns);
					 stockSerivce.update(stock);
					// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE3.getName(),"",lyck,SHOUGONG,expatriate.getDghsl());
				 }
				 
				//跟新外派清单已归还数量
			    Expatriate expatriate1=new Expatriate(); 
			    expatriate1.setId(expatriate.getId());
				expatriate1.setGhsl(expatriate.getDghsl().setScale(2).add(expatriate.getGhsl().setScale(2)));
				expatriateService.update(expatriate1);
				
				//添加外派清单附表
				ExpatriateDetail expatriateDetail=new ExpatriateDetail();
				UUID uuid4 = UUID.randomUUID();//获取随机的uuid
				String waipId = uuid4.toString();
				expatriateDetail.setId(waipId);
				expatriateDetail.setMainid(expatriate.getId());
				expatriateDetail.setSjlx(2);
				expatriateDetail.setGldjlx(2);
				expatriateDetail.setDjid(id);
				expatriateDetail.setKcllid(historyId);
				expatriateDetail.setDprtcode(user.getJgdm());
				expatriateDetail.setCxsl(aDouble);
				expatriateDetailService.insertSelective(expatriateDetail);

			}
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 报废出库
	 * @throws SaibongException 
	 */
	@Override
	public Map<String, Object> doManualstockdumping(Outstock outstock)
			throws BusinessException, SaibongException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		
		outstock.setId(id);
		outstock.setDprtcode(user.getJgdm());
		outstock.setCklx(5);
		outstock.setSgbs(1);
		outstock.setCkdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName()));
		outstock.setCksj(outstock.getCksj());
		outstock.setZt(2);
		outstock.setZdbmid(user.getBmdm());
		outstock.setZdrid(user.getId());
		outstock.setZdsj(new Date());
		outstock.setCkbmid(outstock.getCkbmid());
		outstock.setCukid(outstock.getCukid());
		outstock.setSqbmid(outstock.getSqbmid());
		outstock.setSqrid(outstock.getSqrid());
		outstock.setBz(outstock.getBz());
		outstock.setSqsj(outstock.getSqsj());
		outstockMapper.insertSelective(outstock);
		
		//保存报废单
		ScrapList scrapList=new ScrapList();
		UUID uuid4 = UUID.randomUUID();//获取随机的uuid
		String scrapListId = uuid4.toString();
		scrapList.setId(scrapListId);
		scrapList.setDprtcode(user.getJgdm());
		scrapList.setBfdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.BFD.getName()));
		scrapList.setBfyy(outstock.getBz());
		scrapList.setBz(outstock.getBz());
		scrapList.setZt(1);
		scrapList.setBfbmid(outstock.getCkbmid());
		scrapList.setBfrid(outstock.getCukid());
		scrapList.setBfsj(outstock.getCksj());
		scrapList.setBflx(1);
		scrapList.setZdbmid(user.getJgdm());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapList.setBfsj(new Date());
		scrapListService.save(scrapList);
		
		String czls = UUID.randomUUID().toString();
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			
			BigDecimal cks = new BigDecimal(materialRequisitionDetail.getCksl());
			
			Stock stock = new Stock();
			stock.setId(materialRequisitionDetail.getId());
			//获取库存表所有信息
			stock=stockSerivce.queryKeys(stock);
		
			//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
			BigDecimal kcsl =stock.getKcsl();
		
			if(kcsl.compareTo(cks)==-1){
				throw new BusinessException("出库数量不能大于库存数量!");
			}else{
				// 可用数量
				BigDecimal kysl = stock.getKcsl().subtract(stock.getDjsl() != null ? stock.getDjsl() : BigDecimal.ZERO);
				// 已报废数量
				BigDecimal ybfsl = outstockDetailMapper.sumCountByScrap(stock.getId());
				// 可用数量 = 实际库存 - 冻结数量- 已报废数量
				kysl = kysl.subtract(ybfsl != null ? ybfsl : BigDecimal.ZERO);
				if(kysl.compareTo(cks) == -1){
					throw new BusinessException("该库存已进行报废出库!");
				}
			}
			 
			if(kcsl.compareTo(cks)>=0){
				
				// 添加部件库存履历表：库存id=外场虚拟库存id
				MaterialHistory materialHistory = new MaterialHistory(stock);
				materialHistory.setId(UUID.randomUUID().toString());
				materialHistory.setKcsl(kcsl);	
				materialHistory.setWhrid(user.getId());
				materialHistory.setWhsj(new Date());
				materialHistoryService.insertSelective(materialHistory);
			
				// 保存出库明细表
				OutstockDetail outstockDetail=new OutstockDetail();
				UUID uuid2 = UUID.randomUUID();//获取随机的uuid
				String detailId = uuid2.toString();
				outstockDetail.setId(detailId);
				outstockDetail.setMainid(id);
				outstockDetail.setKcllid(materialHistory.getId());
				outstockDetail.setWhdwid(user.getBmdm());
				outstockDetail.setWhrid(user.getId());
				outstockDetail.setWhsj(new Date());
				outstockDetail.setZt(1);
				BigDecimal aDouble =new BigDecimal(0.00);
				outstockDetail.setTksl(aDouble);
				outstockDetailService.insert(outstockDetail); 
			
				// 保存已报废部件清单
				HasScrappedList hasScrappedList=new HasScrappedList();
				hasScrappedList.setId(UUID.randomUUID().toString());
				hasScrappedList.setMainid(scrapListId);
				hasScrappedList.setKcid(stock.getId());
				hasScrappedList.setKcllid(materialHistory.getId());
				hasScrappedList.setWhdwid(user.getJgdm());
				hasScrappedList.setWhrid(user.getId());
				hasScrappedList.setWhsj(new Date());
				hasScrappedList.setZt(1);
				hasScrappedListService.save(hasScrappedList);
				
//				//库存冻结履历中添加冻结库存
//				StockFreezeHistory stockFreezeHistory = new StockFreezeHistory();
//				stockFreezeHistory.setId(UUID.randomUUID().toString());
//				stockFreezeHistory.setKclx(StockTypeEnum.INNER_STOCK.getId());
//				stockFreezeHistory.setKcid(stock.getId());
//				stockFreezeHistory.setYwlx(FreezeBusinessTypeEnum.SCRAP.getId());
//				stockFreezeHistory.setYwid(hasScrappedList.getId());
//				stockFreezeHistory.setDjsl(cks);
//				stockFreezeHistoryMapper.insertSelective(stockFreezeHistory);
//				
//				//刷新库存冻结数量
//				stockFreezeHistoryService.refreshStockFreezeCount(stock.getId(), StockTypeEnum.INNER_STOCK);
				//写入报废日志
				materialRecService.writeStockRec(stock.getId(), czls, "", "", StockRecBizTypeEnum.TYPE1, 
						UpdateTypeEnum.UPDATE, "报废出库", "", outstock.getCkdh(), scrapList.getBfdh(), cks);
			}
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 其它出库
	 */
	@Override
	public Map<String, Object> manualotherbackout(Outstock outstock)
			throws BusinessException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck;	
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(id);
		outstock.setDprtcode(user.getJgdm());
		outstock.setCklx(0);
		outstock.setSgbs(1);
		outstock.setCkdh(lyck);
		outstock.setCksj(outstock.getCksj());
		outstock.setZt(2);
		outstock.setZdbmid(user.getBmdm());
		outstock.setZdrid(user.getId());
		outstock.setZdsj(new Date());
		outstock.setCkbmid(outstock.getCkbmid());
		outstock.setCukid(outstock.getCukid());
		outstock.setSqbmid(outstock.getSqbmid());
		outstock.setSqrid(outstock.getSqrid());
		outstock.setBz(outstock.getBz());
		outstock.setSqsj(outstock.getSqsj());
		outstockMapper.insertSelective(outstock);
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			
			Stock stock1=new Stock();
			stock1.setId(materialRequisitionDetail.getId());
			//获取库存表所有信息
			Stock stock=stockSerivce.queryKeys(stock1);
			
			//保存部件履库存历表
			MaterialHistory materialHistory=new MaterialHistory(stock);
			UUID uuid3 = UUID.randomUUID();//获取随机的uuid
			String historyId = uuid3.toString();
			materialHistory.setId(historyId);
			//出库数转化为BigDecimal
			BigDecimal cks=new BigDecimal(materialRequisitionDetail.getCksl());
			materialHistory.setKcsl(cks); //本次归还数量
			materialHistoryService.insert(materialHistory);
			
			 //保存出库明细表
			OutstockDetail outstockDetail=new OutstockDetail();
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			outstockDetail.setId(detailId);
			outstockDetail.setMainid(id);
			outstockDetail.setKcllid(historyId);
			outstockDetail.setWhdwid(user.getBmdm());
			outstockDetail.setWhrid(user.getId());
			outstockDetail.setWhsj(new Date());
			outstockDetail.setZt(1);
			BigDecimal aDouble =new BigDecimal(0.00);
			outstockDetail.setTksl(aDouble);
			outstockDetailService.insert(outstockDetail); 
			
			//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
			BigDecimal kcsl =stock.getKcsl();
			
			 if(kcsl.compareTo(cks)==-1){
					returnMap.put("state", "error");
					returnMap.put("message", "出库数量不能大于库存数量!");
				return returnMap;
			 }
				UUID czls = UUID.randomUUID();//获取随机的uuid	
			 if(kcsl.compareTo(cks)==0){
				 stockSerivce.delete(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE);
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE0.getName(),"",lyck,SHOUGONG,cks);
			 }
			 
			 if(kcsl.compareTo(cks)==1){
				 //库存数减去出库数
				 BigDecimal kucuns = kcsl.subtract(cks);   	
				 
				 stock.setKcsl(kucuns);
				 stockSerivce.update(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE0.getName(),"",lyck,SHOUGONG,cks);
			 }
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}

	
	/**
	 * 其它飞行队借入申请单出库
	 */
	@Override
	public Map<String, Object> stockRemoval(Outstock outstock)throws RuntimeException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		//根据飞行队code获取借调对象id
		Secondment secondment=secondmentMapper.selectByIds(outstock.getFxd());
		
		if(secondment==null){
			returnMap.put("state", "error");
			returnMap.put("message", "该借调对象不存在,不能出库!");
			return returnMap;
		}
		
		//保存出库单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String lyck;	
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}	
		outstock.setId(id);
		outstock.setDprtcode(user.getJgdm());
		outstock.setCklx(4);
		outstock.setSgbs(0);
		outstock.setCkdh(lyck);
		outstock.setCksj(outstock.getCksj());
		outstock.setZt(2);
		outstock.setZdbmid(user.getBmdm());
		outstock.setZdrid(user.getId());
		outstock.setZdsj(new Date());
		outstock.setCkbmid(outstock.getCkbmid());
		outstock.setCukid(outstock.getCukid());
		outstock.setBz(outstock.getBz());
		outstock.setXgdjid(outstock.getOtheraerocadeid());
		outstock.setSqbmid(outstock.getSqbmid());
		outstock.setSqrid(outstock.getSqrid());
		outstock.setSqsj(outstock.getSqsj());
		outstock.setJddxid(secondment.getId());
		outstockMapper.insertSelective(outstock);
		
		/*
		 *更新领用数量 
		 */
		for (MaterialRequisitionDetail materialRequisitionDetail : outstock.getMaterialRequisitionDetail()) {
			
			//获取库存表所有信息
			Stock stock1=new Stock();
			stock1.setId(materialRequisitionDetail.getId());
			Stock stock=stockSerivce.queryKeys(stock1);
			
			//保存部件履库存历表
			MaterialHistory materialHistory=new MaterialHistory(stock);
			UUID uuid3 = UUID.randomUUID();//获取随机的uuid
			String historyId = uuid3.toString();
			//出库数转化为BigDecimal
			BigDecimal cks=new BigDecimal(materialRequisitionDetail.getCksl());
			materialHistory.setId(historyId);
			materialHistory.setKcsl(cks);
			materialHistoryService.insert(materialHistory);
			
			//保存外派清单
			OverseasListing overseasListing=new OverseasListing();
			UUID uuid4 = UUID.randomUUID();//获取随机的uuid
			String detailIds = uuid4.toString();
			overseasListing.setId(detailIds);
			overseasListing.setDprtcode(user.getJgdm());
			overseasListing.setJdlx(StatusEnum.CHECKOUT.getId());
			overseasListing.setWpdxid(outstock.getJddxid());
			overseasListing.setBjid(materialRequisitionDetail.getBjid());
			overseasListing.setBjh(materialRequisitionDetail.getBjh());
			if(materialRequisitionDetail.getSn()==null){
				overseasListing.setPch(materialRequisitionDetail.getPch());
			}else{
				overseasListing.setSn(materialRequisitionDetail.getSn());
			}
			overseasListing.setKcsl(cks);
			BigDecimal aDouble =new BigDecimal(0.00);
			overseasListing.setGhsl(aDouble);
			overseasListing.setWpsj(new Date());
			overseasListing.setZt(EffectiveEnum.YES.getId());
			overseasListingService.insert(overseasListing);
			
			//添加外派清单附表
			ExpatriateDetail expatriateDetail=new ExpatriateDetail();
			UUID uuid5 = UUID.randomUUID();//获取随机的uuid
			String waipId = uuid5.toString();
			expatriateDetail.setId(waipId);
			expatriateDetail.setMainid(detailIds);
			expatriateDetail.setSjlx(1);
			expatriateDetail.setGldjlx(2);
			expatriateDetail.setDjid(id);
			expatriateDetail.setKcllid(historyId);
			expatriateDetail.setDprtcode(user.getJgdm());
			expatriateDetail.setCxsl(aDouble);
			expatriateDetailService.insertSelective(expatriateDetail);
			
			//保存出库明细表
			OutstockDetail outstockDetail=new OutstockDetail();
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			outstockDetail.setId(detailId);
			outstockDetail.setMainid(id);
			outstockDetail.setWpqdid(detailIds);
			outstockDetail.setKcllid(historyId);
			outstockDetail.setWhdwid(user.getBmdm());
			outstockDetail.setWhrid(user.getId());
			outstockDetail.setWhsj(new Date());
			outstockDetail.setZt(1);
			outstockDetail.setTksl(aDouble);
			outstockDetailService.insert(outstockDetail); 
			
			// 保存b_h_003 外场虚拟库存表 -有rec表
			OutFieldStock outFieldStock=new OutFieldStock(stock);
			UUID uuid6 = UUID.randomUUID();//获取随机的uuid
			String outFieldStockId = uuid6.toString();
			outFieldStock.setId(outFieldStockId);
			outFieldStock.setKcsl(cks);
			outFieldStockService.insert(outFieldStock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			//commonRecService.write(outFieldStockId, TableEnum.B_H_003, user.getId(), UpdateTypeEnum.SAVE);
			
			materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(),id, lyck, OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE4 ,"",lyck,outstock.getOtheraerocadeid(),cks);
			
			//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
			BigDecimal kcsl =stock.getKcsl();
			
			 if(kcsl.compareTo(cks)==-1){
					returnMap.put("state", "error");
					returnMap.put("message", "出库数量不能大于库存数量!");
				return returnMap;
			 }
			 
			 if(kcsl.compareTo(cks)==0){
				 stockSerivce.delete(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(),id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE4,"",lyck,outstock.getOtheraerocadeid(),cks);
			 }
			 
			 if(kcsl.compareTo(cks)==1){
				 //库存数减去出库数
				 BigDecimal kucuns = kcsl.subtract(cks);   	
				 
				 stock.setKcsl(kucuns);
				 stockSerivce.update(stock);
				// commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				 materialRecService.writeStockRec(stock.getId(), czls.toString(),id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE4,"",lyck,outstock.getOtheraerocadeid(),cks);
			 }
		}
		
		returnMap.put("id", outstock.getOtheraerocadeid());
		returnMap.put("state", "success");
		returnMap.put("message", "出库成功!");
		return returnMap;
	}
	
	
	@Override
	public List<Outstock> queryAllPageList(Outstock outstock)
			throws RuntimeException {
		return outstockMapper.queryAllPageList(outstock);
	}

	/**
	 * 出库撤销
	 */
	@Override
	public Map<String, Object> backout(String id) throws BusinessException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//根据id查询出库单信息
		Outstock outstock=outstockMapper.selectByPrimaryKey(id);
		
		//出库类型=1，标识=0  领用出库
		if(outstock.getCklx()==1&&outstock.getSgbs()==0){
			receivebackout(outstock);	//领用出库撤销
		}
		//出库类型=2 送修出库
		if(outstock.getCklx()==2){
			giverepair(outstock); //送修出库撤销
		}
		//出库类型=1 ，标识=1   手动领用出库
		if(outstock.getCklx()==1&&outstock.getSgbs()==1){
			manualreceivebackout(outstock);	//手动领用出库撤销
		}
		
		//出库类型=4 借调出库
		if(outstock.getCklx()==4){
			secondbackout(outstock);
		}
		//出库类型=3 归还出库
		if(outstock.getCklx()==3){
			givebackout(outstock);//归还出库撤销
		}
		
		//出库类型=6 退货出库
		if(outstock.getCklx()==6){
			throw new BusinessException("退货出库不能撤销!");
		}
		
		//出库类型=0 其它
		if(outstock.getCklx()==0){
			otherbackout(outstock); //其它出库撤销
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}
	
	/**
	 * 归还出库撤销
	 * @param outstock
	 */
	public void givebackout(Outstock outstock){
		User user = ThreadVarUtil.getUser();
		
		//根据出库单id查询出库明细
		List<OutstockDetail> outstockDetail=outstockDetailService.queryKey(outstock.getId());
		
		for (OutstockDetail outstockDetail1 : outstockDetail) {
			
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail1.getKcllid()); 
			
			//根据库存履历的库存id查询库存 有就加 没有就新增 
			Stock stock =new Stock();
			stock.setId(materialHistory.getKcid());
			int num=stockSerivce.queryCountckls(stock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			if(num>0){
				Stock stock1=new Stock();
				stock1.setId(materialHistory.getKcid());
				Stock stock2 =stockSerivce.queryKey(stock1);
				stock.setKcsl(stock2.getKcsl().add(materialHistory.getKcsl()));
				stockSerivce.update(stock);
				//库存历史表-修改
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE3.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}else{
				Stock stock1 =new Stock(materialHistory);
				stockSerivce.insertSelective(stock1);
				//库存历史表-新增
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
				
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE3.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}	
			
			//出库明细的退库数量等于库存履历数量
			outstockDetail1.setTksl(materialHistory.getKcsl());
			outstockDetailService.update(outstockDetail1);
			
			//将出库明细状态改为撤销，填上出库数量
			CancelStock cancelStock=new CancelStock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			cancelStock.setId(uuid.toString());
			cancelStock.setDprtcode(user.getJgdm());
			cancelStock.setXgdjid(outstockDetail1.getId());
			cancelStock.setTksl(materialHistory.getKcsl());
			cancelStock.setZt(1);
			cancelStock.setZdbmid(user.getBmdm());
			cancelStock.setZdrid(user.getId());
			cancelStock.setZdsj(new Date());
			cancelStockService.insertSelective(cancelStock);
			
			//根据外派清单id将外派清单设为失效
			Expatriate expatriate= expatriateService.queryByKey(outstockDetail1.getWpqdid());
			expatriate.setGhsl(expatriate.getGhsl().subtract(materialHistory.getKcsl()));
			expatriateService.update(expatriate);
			
			//根据部件履历id和外派清单id删除 外派部件对应库存信息
			ExpatriateDetail expatriateDetail=expatriateDetailService.queryByKey(outstockDetail1.getMainid(), materialHistory.getId());
			expatriateDetailService.delete(expatriateDetail.getId());
		}
		
		//将出库单状态改为撤销
		Outstock outstock1=new Outstock();
		outstock1.setZt(11);
		outstock1.setId(outstock.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock1);
	}
	
	/**
	 * 借调出库撤销
	 * @param outstock
	 */
	public void secondbackout(Outstock outstock){
		
		User user = ThreadVarUtil.getUser();
		
		//根据出库单id查询出库明细
		List<OutstockDetail> outstockDetail=outstockDetailService.queryKey(outstock.getId());
		
		for (OutstockDetail outstockDetail1 : outstockDetail) {
			
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail1.getKcllid()); 
			
			//根据库存履历的库存id查询库存 有就加 没有就新增 
			Stock stock =new Stock();
			stock.setId(materialHistory.getKcid());
			int num=stockSerivce.queryCountckls(stock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			if(num>0){
				Stock stock1=new Stock();
				stock1.setId(materialHistory.getKcid());
				Stock stock2 =stockSerivce.queryKey(stock1);
				stock.setKcsl(stock2.getKcsl().add(materialHistory.getKcsl()));
				stockSerivce.update(stock);
				//库存历史表-修改
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE4.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}else{
				Stock stock1 =new Stock(materialHistory);
				stockSerivce.insertSelective(stock1);
				//库存历史表-新增
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE4.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}	
			
			//出库明细的退库数量等于库存履历数量
			outstockDetail1.setTksl(materialHistory.getKcsl());
			outstockDetailService.update(outstockDetail1);
			
			//将出库明细状态改为撤销，填上出库数量
			CancelStock cancelStock=new CancelStock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			cancelStock.setId(uuid.toString());
			cancelStock.setDprtcode(user.getJgdm());
			cancelStock.setXgdjid(outstockDetail1.getId());
			cancelStock.setTksl(materialHistory.getKcsl());
			cancelStock.setZt(1);
			cancelStock.setZdbmid(user.getBmdm());
			cancelStock.setZdrid(user.getId());
			cancelStock.setZdsj(new Date());
			cancelStockService.insertSelective(cancelStock);
			
			//根据外派清单id将外派清单设为失效
			Expatriate expatriate= expatriateService.queryByKey(outstockDetail1.getWpqdid());
			expatriate.setZt(0);
			expatriateService.update(expatriate);
		}
		
		//将出库单状态改为撤销
		Outstock outstock1=new Outstock();
		outstock1.setZt(11);
		outstock1.setId(outstock.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock1);
	}
	
	
	
	/**
	 * 送修出库撤销
	 * @param outstock
	 */
	public void giverepair(Outstock outstock){
		
		User user = ThreadVarUtil.getUser();
		
		//根据出库单id查询出库明细
		List<OutstockDetail> outstockDetail=outstockDetailService.queryKey(outstock.getId());
		
		for (OutstockDetail outstockDetail1 : outstockDetail) {
			
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail1.getKcllid()); 
			
			//根据库存履历的库存id查询库存 有就加 没有就新增 
			Stock stock =new Stock();
			stock.setId(materialHistory.getKcid());
			int num=stockSerivce.queryCountckls(stock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			
			if(num>0){
				Stock stock1=new Stock();
				stock1.setId(materialHistory.getKcid());
				Stock stock2 =stockSerivce.queryKey(stock1);
				stock.setKcsl(stock2.getKcsl().add(materialHistory.getKcsl()));
				stockSerivce.update(stock);
				//库存历史表-修改
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE2.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
			}else{
				Stock stock1 =new Stock(materialHistory);
				stockSerivce.insertSelective(stock1);
				//库存历史表-新增
			//	commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE2.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
			}	
			
			//出库明细的退库数量等于库存履历数量
			outstockDetail1.setTksl(materialHistory.getKcsl());
			outstockDetailService.update(outstockDetail1);
			
			//将出库明细状态改为撤销，填上出库数量
			CancelStock cancelStock=new CancelStock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			cancelStock.setId(uuid.toString());
			cancelStock.setDprtcode(user.getJgdm());
			cancelStock.setXgdjid(outstockDetail1.getId());
			cancelStock.setTksl(materialHistory.getKcsl());
			cancelStock.setZt(1);
			cancelStock.setZdbmid(user.getBmdm());
			cancelStock.setZdrid(user.getId());
			cancelStock.setZdsj(new Date());
			cancelStockService.insertSelective(cancelStock);
			
			//根据外派清单id将外派清单设为失效
			Expatriate expatriate= expatriateService.queryByKey(outstockDetail1.getWpqdid());
			expatriate.setZt(0);
			expatriateService.update(expatriate);
		
		}
	
		//将出库单状态改为撤销
		Outstock outstock1=new Outstock();
		outstock1.setZt(11);
		outstock1.setId(outstock.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock1);
	}
	
	/**
	 * 领用出库撤销
	 * @param outstock
	 */
	public void receivebackout(Outstock outstock){
		
		User user = ThreadVarUtil.getUser();
		//根据出库单id查询出库明细
		List<OutstockDetail> outstockDetail=outstockDetailService.queryKey(outstock.getId());
		
		for (OutstockDetail outstockDetail1 : outstockDetail) {
			
			//将领用申请单状态改回已提交
			MaterialRequisition materialRequisition=materialRequisitionService.queryById(outstockDetail1.getLydid());
			if(materialRequisition.getZt()==10){
				materialRequisition.setZt(2);
				materialRequisitionService.updateByPrimaryKeySelective(materialRequisition);
			}
			
			//根据领用申请单明细id查询领用申请明细
			MaterialRequisitionDetail materialRequisitionDetail=materialRequisitionDetailService.selectByPrimaryKey(outstockDetail1.getLydmxid());
			
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail1.getKcllid()); 
			
			materialRequisitionDetail.setYlysl(materialRequisitionDetail.getYlysl().subtract(materialHistory.getKcsl()));//将领用单明细表的完成数量-履历数量，
			materialRequisitionDetail.setTksl(materialRequisitionDetail.getTksl().add(materialHistory.getKcsl())); //退库数量+库存履历数量
			materialRequisitionDetailService.updateByPrimaryKey(materialRequisitionDetail);
			
			//根据库存履历的库存id查询库存 有就加 没有就新增 
			Stock stock =new Stock();
			stock.setId(materialHistory.getKcid());
			int num=stockSerivce.queryCountckls(stock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			if(num>0){
				Stock stock1=new Stock();
				stock1.setId(materialHistory.getKcid());
				Stock stock2 =stockSerivce.queryKey(stock1);
				stock.setKcsl(stock2.getKcsl().add(materialHistory.getKcsl()));
				stockSerivce.update(stock);
				//库存历史表-修改
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),materialRequisition.getLysqdh(),materialHistory.getKcsl());
			}else{
				Stock stock1 =new Stock(materialHistory);
				stockSerivce.insertSelective(stock1);
				//库存历史表-新增
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),materialRequisition.getLysqdh(),materialHistory.getKcsl());
			}	
			
			//出库明细的退库数量等于库存履历数量
			outstockDetail1.setTksl(materialHistory.getKcsl());
			outstockDetailService.update(outstockDetail1);
			
			//将出库明细状态改为撤销，填上出库数量
			CancelStock cancelStock=new CancelStock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			cancelStock.setId(uuid.toString());
			cancelStock.setDprtcode(user.getJgdm());
			cancelStock.setXgdjid(outstockDetail1.getId());
			cancelStock.setTksl(materialHistory.getKcsl());
			cancelStock.setZt(1);
			cancelStock.setZdbmid(user.getBmdm());
			cancelStock.setZdrid(user.getId());
			cancelStock.setZdsj(new Date());
			cancelStockService.insertSelective(cancelStock);
			
			//将外场中的记录删除
			OutFieldStock outFieldStock=outFieldStockService.queryByKey(outstockDetail1.getId());
			outFieldStockService.delete(outFieldStock.getId());
			//新增外场虚拟库存表  -刪除
			//commonRecService.write(outFieldStock.getId(), TableEnum.B_H_003, user.getId(), UpdateTypeEnum.DELETE);
			
			materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE, OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),materialRequisition.getLysqdh(),materialHistory.getKcsl());
		}
		
		//将出库单状态改为撤销
		Outstock outstock1=new Outstock();
		outstock1.setZt(11);
		outstock1.setId(outstock.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock1);
	}
	
	/**
	 * 手动领用出库撤销
	 * @param outstock
	 */
	public void manualreceivebackout(Outstock outstock){
		
		User user = ThreadVarUtil.getUser();
		//根据出库单id查询出库明细
		List<OutstockDetail> outstockDetail=outstockDetailService.queryKey(outstock.getId());
		
		for (OutstockDetail outstockDetail1 : outstockDetail) {
			
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail1.getKcllid()); 
			
			//根据库存履历的库存id查询库存 有就加 没有就新增 
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			Stock stock =new Stock();
			paramsMap.put("userId", user.getId());
			stock.setParamsMap(paramsMap);
			stock.setId(materialHistory.getKcid());
			int num=stockSerivce.queryCount(stock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			if(num>0){
				Stock stock1=new Stock();
				stock1.setId(materialHistory.getKcid());
				Stock stock2 =stockSerivce.queryKey(stock1);
				stock.setKcsl(stock2.getKcsl().add(materialHistory.getKcsl()));
				stockSerivce.update(stock);
				//库存历史表-修改
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}else{
				Stock stock1 =new Stock(materialHistory);
				stockSerivce.insertSelective(stock1);
				//库存历史表-新增
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
				
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}	
			
			//出库明细的退库数量等于库存履历数量
			outstockDetail1.setTksl(materialHistory.getKcsl());
			outstockDetailService.update(outstockDetail1);
			
			//将出库明细状态改为撤销，填上出库数量
			CancelStock cancelStock=new CancelStock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			cancelStock.setId(uuid.toString());
			cancelStock.setDprtcode(user.getJgdm());
			cancelStock.setXgdjid(outstockDetail1.getId());
			cancelStock.setTksl(materialHistory.getKcsl());
			cancelStock.setZt(1);
			cancelStock.setZdbmid(user.getBmdm());
			cancelStock.setZdrid(user.getId());
			cancelStock.setZdsj(new Date());
			cancelStockService.insertSelective(cancelStock);
			
			//将外场虚拟表中的记录删除
			OutFieldStock outFieldStock=outFieldStockService.queryByKey(outstockDetail1.getId());
			outFieldStockService.delete(outFieldStock.getId());
			//新增外场虚拟库存表  -刪除
			materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE, OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			
		}
		
		//将出库单状态改为撤销
		Outstock outstock1=new Outstock();
		outstock1.setZt(11);
		outstock1.setId(outstock.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock1);
	}
	
	/**
	 * 其它出库撤销
	 * @param outstock
	 */
	public void otherbackout(Outstock outstock){
		User user = ThreadVarUtil.getUser();
		
		//根据出库单id查询出库明细
		List<OutstockDetail> outstockDetail=outstockDetailService.queryKey(outstock.getId());
		
		for (OutstockDetail outstockDetail1 : outstockDetail) {
			
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail1.getKcllid()); 
			
			//根据库存履历的库存id查询库存 有就加 没有就新增 
			Stock stock =new Stock();
			stock.setId(materialHistory.getKcid());
			int num=stockSerivce.queryCountckls(stock);
			UUID czls = UUID.randomUUID();//获取随机的uuid	
			if(num>0){
				Stock stock1=new Stock();
				stock1.setId(materialHistory.getKcid());
				Stock stock2 =stockSerivce.queryKey(stock1);
				stock.setKcsl(stock2.getKcsl().add(materialHistory.getKcsl()));
				stockSerivce.update(stock);
				//库存历史表-修改
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE0.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}else{
				Stock stock1 =new Stock(materialHistory);
				stockSerivce.insertSelective(stock1);
				//库存历史表-新增
				//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
				materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE0.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
			}		
			
			//出库明细的退库数量等于库存履历数量
			outstockDetail1.setTksl(materialHistory.getKcsl());
			outstockDetailService.update(outstockDetail1);
			
			//将出库明细状态改为撤销，填上出库数量
			CancelStock cancelStock=new CancelStock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			cancelStock.setId(uuid.toString());
			cancelStock.setDprtcode(user.getJgdm());
			cancelStock.setXgdjid(outstockDetail1.getId());
			cancelStock.setTksl(materialHistory.getKcsl());
			cancelStock.setZt(1);
			cancelStock.setZdbmid(user.getBmdm());
			cancelStock.setZdrid(user.getId());
			cancelStock.setZdsj(new Date());
			cancelStockService.insertSelective(cancelStock);
			
		}
		
		//将出库单状态改为撤销
		Outstock outstock1=new Outstock();
		outstock1.setZt(11);
		outstock1.setId(outstock.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock1);
	}

	@Override
	public Map<String, Object> checkUpdMt(User userFromSession, String ids)
			throws RuntimeException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//根据id查询出库单信息
		Outstock outstock=outstockMapper.selectByPrimaryKey(ids);
		
		//出库类型=1，标识=0  领用出库
		if(outstock.getCklx()==1&&outstock.getSgbs()==0){
			List<OutstockDetail> list=outstockDetailService.queryKey(outstock.getId());
			for (OutstockDetail outstockDetail : list) {
				
				//根据库存明细的库存id查询库存履历
				MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail.getKcllid()); 
				OutFieldStock outFieldStock=new OutFieldStock();
				outFieldStock.setXgdjid(outstockDetail.getId());
				outFieldStock.setKcid(materialHistory.getKcid());
				OutFieldStock list1=outFieldStockService.queryByParamKey(outFieldStock);
				//外场清单是否有 同时满足撤销数量(库存数量-退库数量)=外场数量
					if(list1==null||materialHistory.getKcsl().compareTo(list1.getKcsl())==-1||materialHistory.getKcsl().compareTo(list1.getKcsl())==1){
						returnMap.put("state", "error");
						returnMap.put("message", "该出库单不可撤销!");
						return returnMap;
					}
			}
		}
		
		//出库类型=2 送修出库
		if(outstock.getCklx()==2){
			BigDecimal one =new BigDecimal(1.00);
			//外派清单借用数量-已归还数量 
			List<OutstockDetail> list=outstockDetailService.queryKey(outstock.getId());
			Expatriate expatriate=expatriateService.queryByKey(list.get(0).getWpqdid());
			if(list.get(0).getTksl().compareTo(one)==0||expatriate.getKcsl().compareTo(expatriate.getGhsl())==0){
				returnMap.put("state", "error");
				returnMap.put("message", "该出库单不可撤销!");
				return returnMap;
			}
		}
		
		//出库类型=1 ，标识=1   手动领用出库
		if(outstock.getCklx()==1&&outstock.getSgbs()==1){
			List<OutstockDetail> list=outstockDetailService.queryKey(outstock.getId());
			for (OutstockDetail outstockDetail : list) {
				
				//根据库存明细的库存id查询库存履历
				MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail.getKcllid()); 
				OutFieldStock outFieldStock=new OutFieldStock();
				outFieldStock.setXgdjid(outstockDetail.getId());
				outFieldStock.setKcid(materialHistory.getKcid());
				OutFieldStock list1=outFieldStockService.queryByParamKey(outFieldStock);
				//外场清单是否有 同时满足撤销数量(库存数量-退库数量)=外场数量
				if(list1==null||materialHistory.getKcsl().compareTo(list1.getKcsl())==-1||materialHistory.getKcsl().compareTo(list1.getKcsl())==1){
						returnMap.put("state", "error");
						returnMap.put("message", "该出库单不可撤销!");
						return returnMap;
					}
			}
		}
		
		//出库类型=4 借出出库
		if(outstock.getCklx()==4){
			List<OutstockDetail> list=outstockDetailService.queryKey(outstock.getId());
			for (OutstockDetail outstockDetail : list) {
				//外派清单已归还数量=0
				Expatriate expatriate=expatriateService.queryByKey(outstockDetail.getWpqdid());
				BigDecimal aDouble =new BigDecimal(0.00);
				if(expatriate.getGhsl().compareTo(aDouble)==1||expatriate.getGhsl().compareTo(aDouble)==-1||outstockDetail.getTksl().compareTo(aDouble)==1){
					returnMap.put("state", "error");
					returnMap.put("message", "该出库单不可撤销!");
					return returnMap;
				}
			}
		}
		
		//出库类型=3 借入归还出库
		if(outstock.getCklx()==3){
			List<OutstockDetail> list=outstockDetailService.queryKey(outstock.getId());
			for (OutstockDetail outstockDetail : list) {
				BigDecimal aDouble =new BigDecimal(0.00);
				if(outstockDetail.getTksl().compareTo(aDouble)==1){
					returnMap.put("state", "error");
					returnMap.put("message", "该出库单不可撤销!");
					return returnMap;
				}
			}
		}
		
		//出库类型=0 其它出库
		if(outstock.getCklx()==0){
			List<OutstockDetail> list=outstockDetailService.queryKey(outstock.getId());
			for (OutstockDetail outstockDetail : list) {
				BigDecimal aDouble =new BigDecimal(0.00);
				if(outstockDetail.getTksl().compareTo(aDouble)==1){
					returnMap.put("state", "error");
					returnMap.put("message", "该出库单不可撤销!");
					return returnMap;
				}
			}
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public List<Outstock> selectByAll(List<String>   xgdjids)
			throws RuntimeException {
	
		return outstockMapper.selectByAll(xgdjids);
	}

	@Override
	public Outstock selectById(String ckdh, String string) throws RuntimeException {
		return outstockMapper.selectById(ckdh,string);
	}

	@Override
	public Map<String, Object> queryDetailPage(OutstockDetail record) throws RuntimeException {
		PageHelper.startPage(record.getPagination());
		List<OutstockDetail> list = this.outstockDetailMapper.queryPageList(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

}
