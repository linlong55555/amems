package com.eray.thjw.aerialmaterial.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OutstockDetailMapper;
import com.eray.thjw.aerialmaterial.dao.OutstockMapper;
import com.eray.thjw.aerialmaterial.po.CancelStock;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.CancelStockService;
import com.eray.thjw.aerialmaterial.service.ExpatriateDetailService;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionDetailService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.OutstockDetailService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutStockTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;

@Service
public class OutstockDetailServiceImpl implements OutstockDetailService {
	
	private static String SHOUGONG="手工制单";
	
	@Resource
	private OutstockDetailMapper outstockDetailMapper;
	
	@Resource
	private OutstockMapper outstockMapper;

	@Resource
	private MaterialRequisitionDetailService materialRequisitionDetailService;
	
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private CancelStockService cancelStockService;
	
	@Resource
	private OutFieldStockService outFieldStockService;
	
	@Resource
	private ExpatriateService expatriateService;
	
	@Resource
	private ExpatriateDetailService expatriateDetailService;
	
	@Resource
	private MaterialRecService materialRecService;
	
	
	@Override
	public void insert(OutstockDetail outstockDetail) {
		outstockDetailMapper.insertSelective(outstockDetail);
	}

	@Override
	public List<OutstockDetail> queryKey(String id) throws RuntimeException {
		return outstockDetailMapper.queryKey(id);
	}

	@Override
	public void update(OutstockDetail outstockDetail1) throws RuntimeException {
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail1);
	}

	@Override
	public List<OutstockDetail> queryKeyList(String id) throws RuntimeException {
		return outstockDetailMapper.queryKeyList(id);
	}

	@Override
	public Map<String, Object> cancellingStock(OutstockDetail outstockDetail)
			throws RuntimeException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//根据id查询出库明细表内容
		OutstockDetail list=outstockDetailMapper.selectByPrimaryKey(outstockDetail.getId());
		//根据id查询出库单信息
		Outstock outstock=outstockMapper.selectByPrimaryKey(list.getMainid());
		
		//出库类型=1，标识=0  领用退库
		if(outstock.getCklx()==1&&outstock.getSgbs()==0){
			receiveCancelling(list,outstockDetail.getTksl(),outstock);	//领用出库退库
		}
		//出库类型=2 送修退库
		if(outstock.getCklx()==2){
			giverepairCancelling(list,outstockDetail.getTksl(),outstock); //送修出库退库
		}
		
		//出库类型=1 ，标识=1   手动领用退库
		if(outstock.getCklx()==1&&outstock.getSgbs()==1){
			manualreceiveCancelling(list,outstockDetail.getTksl(),outstock);	//手动领用出库退库
		}
		
		//出库类型=4 借出退库
		if(outstock.getCklx()==4){
			secondCancelling(list,outstockDetail.getTksl(),outstock);
		}
		
		//出库类型=3 归还退库
		if(outstock.getCklx()==3){
			giveCancelling(list,outstockDetail.getTksl(),outstock);//归还出库退库
		}
		
		//出库类型=0 其它退库
		if(outstock.getCklx()==0){
			otherCancelling(list,outstockDetail.getTksl(),outstock); //其它出库退库
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}
	
	/**
	 * 领用退库
	 * @param outstockDetail
	 * @param tksl 
	 * @param outstock 
	 */
	private void receiveCancelling(OutstockDetail outstockDetail, BigDecimal tksl, Outstock outstock) {
		User user = ThreadVarUtil.getUser();
		
		//根据出库单明细id查询出库明细
		OutstockDetail outstockDetail1=outstockDetailMapper.selectByPrimaryKey(outstockDetail.getId());
		
		//根据领用申请单明细id查询领用申请明细
		MaterialRequisitionDetail materialRequisitionDetail=materialRequisitionDetailService.selectByPrimaryKey(outstockDetail1.getLydmxid());
		
		//修改领用明细退库数量
		materialRequisitionDetail.setTksl(materialRequisitionDetail.getTksl().add(tksl)); //退库数量+库存履历数量
		materialRequisitionDetailService.updateByPrimaryKeySelectives(materialRequisitionDetail);
		
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
			stock.setKcsl(stock2.getKcsl().add(tksl));
			stockSerivce.update(stock);
			//库存历史表-修改
			//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE);
			materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
		}else{
			Stock stock1 =new Stock(materialHistory);
			stockSerivce.insertSelective(stock1);
			//库存历史表-新增
			//commonRecService.write(materialHistory.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
			materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
		}	
		
		//出库明细的退库数量等于明细退库数量加上退库数量
		outstockDetail1.setTksl(outstockDetail1.getTksl().add(tksl));
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail1);
		
		//将出库明细状态改为撤销，填上出库数量
		CancelStock cancelStock=new CancelStock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		cancelStock.setId(uuid.toString());
		cancelStock.setDprtcode(user.getJgdm());
		cancelStock.setXgdjid(outstockDetail1.getId());
		cancelStock.setTksl(tksl);
		cancelStock.setZt(1);
		if(user.getBmdm()==null){
			cancelStock.setZdbmid("");
		}else{
			cancelStock.setZdbmid(user.getBmdm());
		}
		cancelStock.setZdrid(user.getId());
		cancelStock.setZdsj(new Date());
		cancelStockService.insertSelective(cancelStock);
		
		OutFieldStock outFieldStock=outFieldStockService.queryByKey(outstockDetail1.getId());
		if(outFieldStock.getKcsl().compareTo(tksl)==1){
			 //外场库存数减去退库数量
			 BigDecimal tuikus = outFieldStock.getKcsl().subtract(tksl);   
			 OutFieldStock outFieldStock1=new OutFieldStock();
			 outFieldStock1.setKcsl(tuikus);
			 outFieldStock1.setId(outFieldStock.getId());
			 outFieldStockService.update(outFieldStock1);
			 //新增外场虚拟库存表  -修改
			// commonRecService.write(outFieldStock.getId(), TableEnum.B_H_003, user.getId(), UpdateTypeEnum.UPDATE);
			 
			 materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE, OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
		 }
		
		 if(outFieldStock.getKcsl().compareTo(tksl)==0){
			 
			 outFieldStockService.delete(outFieldStock.getId());
			//新增外场虚拟库存表  -刪除
			//commonRecService.write(outFieldStock.getId(), TableEnum.B_H_003, user.getId(), UpdateTypeEnum.DELETE);
			materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE, OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
		 }
	}
	
	/**
	 * 送修退库
	 * @param outstockDetail
	 * @param tksl 
	 * @param outstock 
	 */
	private void giverepairCancelling(OutstockDetail outstockDetail, BigDecimal tksl, Outstock outstock) {
		User user = ThreadVarUtil.getUser();
		//根据库存明细的库存id查询库存履历
		MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(outstockDetail.getKcllid()); 
		
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
			materialRecService.writeStockRec(stock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.SAVE,OutStockTypeEnum.TYPE2.getName(),"撤销",outstock.getCkdh(),outstock.getXgdjid(),materialHistory.getKcsl());
			
		}	
		
		//出库明细的退库数量等于库存履历数量
		outstockDetail.setTksl(materialHistory.getKcsl());
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail);
		
		//添加b_h_019 退库明细
		CancelStock cancelStock=new CancelStock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		cancelStock.setId(uuid.toString());
		cancelStock.setDprtcode(user.getJgdm());
		cancelStock.setXgdjid(outstockDetail.getId());
		cancelStock.setTksl(materialHistory.getKcsl());
		cancelStock.setZt(1);
		if(user.getBmdm()==null){
			cancelStock.setZdbmid("");
		}else{
			cancelStock.setZdbmid(user.getBmdm());
		}
		cancelStock.setZdrid(user.getId());
		cancelStock.setZdsj(new Date());
		cancelStockService.insertSelective(cancelStock);
		
		//根据外派清单id将外派清单设为失效
		Expatriate expatriate= expatriateService.queryByKey(outstockDetail.getWpqdid());
		expatriate.setZt(0);
		expatriateService.update(expatriate);
		
		//根据单据id和部件履历id查询唯一外派附表
		ExpatriateDetail expatriateDetail=expatriateDetailService.queryByKey(outstockDetail.getMainid(), materialHistory.getId());
		expatriateDetail.setCxsl(expatriateDetail.getCxsl().add(tksl));
		expatriateDetailService.update(expatriateDetail);
		
	}
	
	/**
	 * 手动领用退库
	 * @param outstockDetail
	 * @param tksl 
	 * @param outstock 
	 */
	private void manualreceiveCancelling(OutstockDetail outstockDetail, BigDecimal tksl, Outstock outstock) {
		User user = ThreadVarUtil.getUser();
		
		//根据出库单明细id查询出库明细
		OutstockDetail outstockDetail1=outstockDetailMapper.selectByPrimaryKey(outstockDetail.getId());
		
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
			stock.setKcsl(stock2.getKcsl().add(tksl));
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
		outstockDetail1.setTksl(outstockDetail1.getTksl().add(tksl));
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail1);
		
		//将出库明细状态改为撤销，填上出库数量
		CancelStock cancelStock=new CancelStock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		cancelStock.setId(uuid.toString());
		cancelStock.setDprtcode(user.getJgdm());
		cancelStock.setXgdjid(outstockDetail1.getId());
		cancelStock.setTksl(tksl);
		cancelStock.setZt(1);
		if(user.getBmdm()==null){
			cancelStock.setZdbmid("");
		}else{
			cancelStock.setZdbmid(user.getBmdm());
		}
		cancelStock.setZdrid(user.getId());
		cancelStock.setZdsj(new Date());
		cancelStockService.insertSelective(cancelStock);
		
		OutFieldStock outFieldStock=outFieldStockService.queryByKey(outstockDetail1.getId());
		if(outFieldStock.getKcsl().compareTo(outstockDetail1.getTksl())==1){
			 //外场库存数减去退库数量
			 BigDecimal tuikus = outFieldStock.getKcsl().subtract(tksl);   
			 OutFieldStock outFieldStock1=new OutFieldStock();
			 outFieldStock1.setKcsl(tuikus);
			 outFieldStock1.setId(outFieldStock.getId());
			 outFieldStockService.update(outFieldStock1);
			 //新增外场虚拟库存表  -修改
			 //commonRecService.write(outFieldStock.getId(), TableEnum.B_H_003, user.getId(), UpdateTypeEnum.UPDATE);
			 materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE, OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
		 }
		
		 if(outFieldStock.getKcsl().compareTo(tksl)==0){
			 
			 outFieldStockService.delete(outFieldStock.getId());
			//新增外场虚拟库存表  -刪除
			//commonRecService.write(outFieldStock.getId(), TableEnum.B_H_003, user.getId(), UpdateTypeEnum.DELETE);
			materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(), outstock.getId(), outstock.getCkdh(), OutfieldRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE, OutStockTypeEnum.TYPE1.getName(),"撤销",outstock.getCkdh(),SHOUGONG,materialHistory.getKcsl());
		 }
	}
	
	/**
	 * 借出退库
	 * @param outstockDetail
	 * @param tksl 
	 * @param outstock 
	 */
	private void secondCancelling(OutstockDetail outstockDetail, BigDecimal tksl, Outstock outstock) {
		User user = ThreadVarUtil.getUser();
		
		//根据出库单明细id查询出库明细
		OutstockDetail outstockDetail1=outstockDetailMapper.selectByPrimaryKey(outstockDetail.getId());
		
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
			stock.setKcsl(stock2.getKcsl().add(tksl));
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
		outstockDetail1.setTksl(outstockDetail1.getTksl().add(tksl));
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail1);
		
		//将出库明细状态改为撤销，填上出库数量
		CancelStock cancelStock=new CancelStock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		cancelStock.setId(uuid.toString());
		cancelStock.setDprtcode(user.getJgdm());
		cancelStock.setXgdjid(outstockDetail.getId());
		cancelStock.setTksl(tksl);
		cancelStock.setZt(1);
		if(user.getBmdm()==null){
			cancelStock.setZdbmid("");
		}else{
			cancelStock.setZdbmid(user.getBmdm());
		}
		cancelStock.setZdrid(user.getId());
		cancelStock.setZdsj(new Date());
		cancelStockService.insertSelective(cancelStock);
		
		//根据外派清单id将外派清单设为失效
		Expatriate expatriate= expatriateService.queryByKey(outstockDetail1.getWpqdid());
		if(expatriate.getKcsl().compareTo(tksl)==0){
			expatriate.setZt(0);
			expatriate.setKcsl(expatriate.getKcsl().subtract(tksl));
		}else{
			expatriate.setKcsl(expatriate.getKcsl().subtract(tksl));
		}
		expatriateService.update(expatriate);
		
		//根据单据id和库存id查询唯一外派附表
		ExpatriateDetail expatriateDetail=expatriateDetailService.queryByKey(outstockDetail.getMainid(), materialHistory.getId());
		expatriateDetail.setCxsl(expatriateDetail.getCxsl().add(tksl));
		expatriateDetailService.update(expatriateDetail);
	}
	
	/**
	 * 归还退库
	 * @param outstockDetail
	 * @param tksl 
	 * @param outstock 
	 */
	private void giveCancelling(OutstockDetail outstockDetail, BigDecimal tksl, Outstock outstock) {
		
	User user = ThreadVarUtil.getUser();
		
		//根据出库单明细id查询出库明细
		OutstockDetail outstockDetail1=outstockDetailMapper.selectByPrimaryKey(outstockDetail.getId());
		
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
			stock.setKcsl(stock2.getKcsl().add(tksl));
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
		outstockDetail1.setTksl(outstockDetail1.getTksl().add(tksl));
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail1);
		
		//将出库明细状态改为撤销，填上出库数量
		CancelStock cancelStock=new CancelStock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		cancelStock.setId(uuid.toString());
		cancelStock.setDprtcode(user.getJgdm());
		cancelStock.setXgdjid(outstockDetail.getId());
		cancelStock.setTksl(tksl);
		cancelStock.setZt(1);
		if(user.getBmdm()==null){
			cancelStock.setZdbmid("");
		}else{
			cancelStock.setZdbmid(user.getBmdm());
		}
		cancelStock.setZdrid(user.getId());
		cancelStock.setZdsj(new Date());
		cancelStockService.insertSelective(cancelStock);
		
		//根据外派清单id将外派清单设为失效
		Expatriate expatriate= expatriateService.queryByKey(outstockDetail1.getWpqdid());
		expatriate.setGhsl(expatriate.getGhsl().subtract(tksl));
		expatriateService.update(expatriate);
		
		//根据单据id和库存id查询唯一外派附表
		ExpatriateDetail expatriateDetail=expatriateDetailService.queryByKey(outstockDetail.getMainid(), materialHistory.getId());
		expatriateDetail.setCxsl(expatriateDetail.getCxsl().add(tksl));
		expatriateDetailService.update(expatriateDetail);
		
	}
	
	/**
	 * 其它退库
	 * @param outstockDetail
	 * @param tksl 
	 * @param outstock 
	 */
	private void otherCancelling(OutstockDetail outstockDetail, BigDecimal tksl, Outstock outstock) {
		
		User user = ThreadVarUtil.getUser();
		
		//根据出库单明细id查询出库明细
		OutstockDetail outstockDetail1=outstockDetailMapper.selectByPrimaryKey(outstockDetail.getId());
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
			stock.setKcsl(stock2.getKcsl().add(tksl));
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
		outstockDetail1.setTksl(outstockDetail1.getTksl().add(tksl));
		outstockDetailMapper.updateByPrimaryKeySelective(outstockDetail1);
		
		//将出库明细状态改为撤销，填上出库数量
		CancelStock cancelStock=new CancelStock();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		cancelStock.setId(uuid.toString());
		cancelStock.setDprtcode(user.getJgdm());
		cancelStock.setXgdjid(outstockDetail1.getId());
		cancelStock.setTksl(tksl);
		cancelStock.setZt(1);
		if(user.getBmdm()==null){
			cancelStock.setZdbmid("");
		}else{
			cancelStock.setZdbmid(user.getBmdm());
		}
		cancelStock.setZdrid(user.getId());
		cancelStock.setZdsj(new Date());
		cancelStockService.insertSelective(cancelStock);
	}

	@Override
	public Map<String, Object> checkUpd(User userFromSession, String ids,BigDecimal tksl)
			throws RuntimeException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//根据id查询出库明细表内容
		OutstockDetail list=outstockDetailMapper.selectByPrimaryKey(ids);
		
		//根据id查询出库单信息
		Outstock outstock=outstockMapper.selectByPrimaryKey(list.getMainid());
		
		//出库类型=1，标识=0  领用出库
		if(outstock.getCklx()==1&&outstock.getSgbs()==0){
				
			//根据库存明细的库存id查询库存履历
			MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(list.getKcllid()); 
			OutFieldStock outFieldStock=new OutFieldStock();
			outFieldStock.setXgdjid(list.getId());
			outFieldStock.setKcid(materialHistory.getKcid());
			OutFieldStock list1=outFieldStockService.queryByParamKey(outFieldStock);
			//外场清单是否有 同时满足撤销数量(库存数量-退库数量)=外场数量
			if(list1==null||list1.getKcsl().compareTo(tksl)==-1){
				returnMap.put("state", "error");
				returnMap.put("message", "该出库单不可退库!");
				return returnMap;
			}
		}
			
		//出库类型=2 送修出库
		if(outstock.getCklx()==2){
			//外派清单借用数量-已归还数量 
			BigDecimal one =new BigDecimal(1);
			List<OutstockDetail> list1=outstockDetailMapper.queryKey(outstock.getId());
			Expatriate expatriate=expatriateService.queryByKey(list1.get(0).getWpqdid());
			if(tksl.compareTo(one)==1||tksl.compareTo(one)==-1||expatriate==null){
				returnMap.put("state", "error");
				returnMap.put("message", "该出库单不可退库!");
				return returnMap;
			}
		}
		
		//出库类型=1 ，标识=1   手动领用出库
		if(outstock.getCklx()==1&&outstock.getSgbs()==1){
				
				//根据库存明细的库存id查询库存履历
				MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(list.getKcllid()); 
				OutFieldStock outFieldStock=new OutFieldStock();
				outFieldStock.setXgdjid(list.getId());
				outFieldStock.setKcid(materialHistory.getKcid());
				OutFieldStock list1=outFieldStockService.queryByParamKey(outFieldStock);
				//外场清单是否有 同时满足撤销数量(库存数量-退库数量)=外场数量
					if(list1==null||list1.getKcsl().compareTo(tksl)==-1){
						returnMap.put("state", "error");
						returnMap.put("message", "该出库单不可退库!");
						return returnMap; 
					}
		}
		
		//出库类型=4 借出出库
		if(outstock.getCklx()==4){
				//外派清单已归还数量=0
				Expatriate expatriate=expatriateService.queryByKey(list.getWpqdid());
				//借用数量-已归还数量<本次退库数量
				if(expatriate.getKcsl().compareTo(expatriate.getGhsl())==0){
					returnMap.put("state", "error");
					returnMap.put("message", "该出库单不可退库!");
					return returnMap;
				}
		}
		
		//出库类型=3 借入归还出库
		if(outstock.getCklx()==3){
				//外派清单已归还数量=0
				Expatriate expatriate=expatriateService.queryByKey(list.getWpqdid());
				//借用数量-已归还数量<本次退库数量
				BigDecimal one =new BigDecimal(0);
				if(expatriate.getGhsl().compareTo(tksl)==-1||expatriate.getGhsl().compareTo(one)==0){
					returnMap.put("state", "error");
					returnMap.put("message", "该出库单不可退库!");
					return returnMap;
				}
		}
		
		//出库类型=0 其他出库
		if(outstock.getCklx()==0){
				//外派清单已归还数量=0
				//借用数量-已归还数量<本次退库数量
				BigDecimal one =new BigDecimal(0);
				//根据库存明细的库存id查询库存履历
				MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(list.getKcllid()); 
				if((materialHistory.getKcsl().subtract(list.getTksl())).compareTo(one)==0||(materialHistory.getKcsl().subtract(list.getTksl())).compareTo(tksl)==-1){
					returnMap.put("state", "error");
					returnMap.put("message", "该出库单不可退库!");
					return returnMap;
				}
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public OutstockDetail queryReserveDetailListByMainId(String ckdh)
			throws RuntimeException {
		return outstockDetailMapper.queryReserveDetailListByMainId(ckdh);
	}


}
