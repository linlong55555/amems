package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.BorrowApplyDetailMapper;
import com.eray.thjw.aerialmaterial.dao.BorrowApplyMapper;
import com.eray.thjw.aerialmaterial.dao.ContractDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ExpatriateDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ExpatriateMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.InstockDetailMapper;
import com.eray.thjw.aerialmaterial.dao.InstockDetailStockMapper;
import com.eray.thjw.aerialmaterial.dao.InstockMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialCostMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.QualityCheckMapper;
import com.eray.thjw.aerialmaterial.dao.SecondmentMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.aerialmaterial.po.Contract;
import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.Instock;
import com.eray.thjw.aerialmaterial.po.InstockDetail;
import com.eray.thjw.aerialmaterial.po.InstockDetailStock;
import com.eray.thjw.aerialmaterial.po.MaterialCost;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.QualityCheck;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.aerialmaterial.service.InstockService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.MaterialUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.SupervisoryLevelEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.InstockStatusEnum;
import enu.aerialmaterial.InstockTypeEnum;
import enu.aerialmaterial.RequisitionStatusEnum;
import enu.aerialmaterial.StatusEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;

/**
 * 入库业务层
 * @author xu.yong
 */
@Service(value="instockService")
public class InstockServiceImpl implements InstockService{
	
	private  final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private InstockDetailMapper instockDetailMapper;
	
	@Resource
	private InstockDetailStockMapper instockDetailStockMapper;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private StorageMapper storageMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private InstockMapper instockMapper;
	
	@Resource
	private ExpatriateService expatriateService;
	
	@Resource
	private ExpatriateMapper expatriateMapper;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private ExpatriateDetailMapper expatriateDetailMapper;
	
	@Resource
	private MaterialCostMapper materialCostMapper;
	
	@Resource
	private ContractDetailMapper contractDetailMapper;
	
	@Resource
	private BorrowApplyMapper borrowApplyMapper;
	
	@Resource
	private BorrowApplyDetailMapper borrowApplyDetailMapper;
	
	@Resource
	private QualityCheckMapper qualityCheckMapper;
	
	@Resource
	private SecondmentMapper secondmentMapper;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private MaterialUtilService materialUtilService;
	
	/**
	 * 查询采购待入库列表
	 * @return 
	 */
	public Map<String, Object> queryPurchasePageList(InstockDetail instockDetail){
		PageHelper.startPage(instockDetail.getPagination());
		List<InstockDetail> list = this.instockDetailMapper.queryPurchasePage(instockDetail);
		return PageUtil.pack4PageHelper(list, instockDetail.getPagination());
	} 
	
	/**
	 * 查询采购航材入库单，采购入库单和入库航材是一对一，所以使用InstockDetail查询和返回
	 * @param id 入库航材ID
	 * @return
	 */
	public InstockDetail queryPurchaseById(String id){
		//查询入库单航材信息
		InstockDetail instockDetail = this.instockDetailMapper.selectByPrimaryKey(id);
		if(instockDetail == null){
			throw new RuntimeException("入库航材不存在");
		}
		//当入库人为空时，构造当前用户
		Instock instock = instockDetail.getInstock();
		if(instock == null){
			instock = new Instock();
			instockDetail.setInstock(instock);
		}
		User rkrUser = instockDetail.getInstock().getRkrUser();
		if(rkrUser == null){
			rkrUser = new User();
			User user = ThreadVarUtil.getUser();
			rkrUser.setId(user.getId());
			rkrUser.setRealname(user.getRealname());
			rkrUser.setUsername(user.getUsername());
			instock.setRkbmid(user.getBmdm());
			instock.setRkrUser(rkrUser);
		}
		if(instock.getRksj() == null){
			instock.setRksj(new Date());
		}
		
		//查询航材入库信息
		instockDetail.setDetailStockList(this.instockDetailStockMapper.selectByMainId(instockDetail.getId()));
		
		return instockDetail;
	}
	
	/**
	 * 保存采购入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public String savePurchase(Map<String, Object> map) throws BusinessException{
		//接收并验证参数
		String id = (String)map.get("id");
		if(StringUtils.isBlank(id)){
			logger.error("前台传递ID为空");
			throw new BusinessException("数据异常");
		}
		
		String spqx = (String)map.get("spqx");//索赔期限
		Date spqxDate = null;
		if(StringUtils.isNotBlank(spqx)){
			try {
				spqxDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, spqx);
			} catch (ParseException e) {
				logger.error("索赔日期转换错误");
				throw new BusinessException("数据异常");
			}
		}
		String kccb = (String)map.get("kccb");//库存成本
		BigDecimal kccbNumber = null;
		if(StringUtils.isNotBlank(kccb)){
			kccbNumber = new BigDecimal(kccb);
		}
		
		String rksj = (String)map.get("rksj");//入库时间
		Date rksjDate = null;
		if(StringUtils.isNotBlank(rksj)){
			try {
				rksjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, rksj);
			} catch (ParseException e) {
				logger.error("入库时间转换错误");
				throw new BusinessException("数据异常");
			}
		}
		String rkrid = (String)map.get("rkrid");//入库人ID
		String rkbmid = (String)map.get("rkbmid");//入库部门ID
		
		
		//删除入库库存信息
		@SuppressWarnings("unchecked")
		List<String> delList = (List<String>)map.get("delIds");
		if(delList != null && !delList.isEmpty()){
			//删除关联的库存履历
			this.instockDetailStockMapper.deleteKcHisByIds(delList);
			//删除入库库存
			this.instockDetailStockMapper.deleteByIds(delList);
		}
		
		InstockDetail instockDetail = this.instockDetailMapper.selectByPrimaryKey(id);
		if(instockDetail == null || instockDetail.getInstock() == null || EffectiveEnum.YES.getId().intValue() != instockDetail.getZt().intValue()){
			throw new BusinessException("入库航材不存在");
		}
		Instock instock = instockDetail.getInstock();
		
		//检验业务状态
		if(instock.getZt().intValue() != InstockStatusEnum.SAVED.getId().intValue()){
			throw new BusinessException("当前入库单状态为"+InstockStatusEnum.getName(instock.getZt())+"，不可进行该操作");
		}
		
		User user = ThreadVarUtil.getUser();
		
		HCMainData hcMainData = instockDetail.getHcMainData();
		ContractDetail contractDetail = instockDetail.getContractDetail();//合同航材
		Contract contract = contractDetail != null ? contractDetail.getContract(): new Contract();//合同
		ReserveMain reserveMain = contractDetail !=null ? contractDetail.getReserveMain(): new ReserveMain();//提订/送修单
		BigDecimal count = new BigDecimal(0);//航材入库数计数
		
		//保存入库 库存
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> detailStockList = (List<Map<String, Object>>)map.get("detailStock");
		if(detailStockList != null){
			for (Map<String, Object> detailStockMap : detailStockList) {
				
				String detailStockId = (String)detailStockMap.get("id");
				String kwid = (String)detailStockMap.get("kwid");
				String sn = (String)detailStockMap.get("sn");
				String pch = (String)detailStockMap.get("pch");
				BigDecimal kcsl = new BigDecimal((String)detailStockMap.get("kcsl"));
				count = count.add(kcsl);
				
				//验证序列号重复性
				if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
					if(StringUtils.isBlank(sn)){
						throw new BusinessException("序列号不能为空");
					}
					if(!this.materialUtilService.validateSnUniqueness(user.getJgdm(), hcMainData.getBjh(), sn)){
						throw new BusinessException("序列号"+sn+"已存在");
					}
				}
				
				Storage storage = null;
				if(StringUtils.isNotBlank(kwid)){
					storage = this.storageMapper.selectByIdWithRel(kwid);
				}
				if(storage == null){
					throw new BusinessException("库位不存在");
				}
				
				if(StringUtils.isBlank(detailStockId)){//新增
					//新增库存履历
					MaterialHistory materialHistory = new MaterialHistory();
					materialHistory.setId(UUID.randomUUID().toString());
					materialHistory.setKcid(UUID.randomUUID().toString());
					materialHistory.setCkid(storage.getMainid());
					materialHistory.setCklb(storage.getCklb());
					materialHistory.setCkh(storage.getCkh());
					materialHistory.setCkmc(storage.getStore().getCkmc());
					materialHistory.setKwid(storage.getId());
					materialHistory.setKwh(storage.getKwh());
					materialHistory.setSn(sn);
					materialHistory.setPch(pch);
					materialHistory.setKcsl(kcsl);
					materialHistory.setSpqx(spqxDate);
					materialHistory.setKccb(kccbNumber);
					materialHistory.setZt(StockStatusEnum.RECEIVED.getId());
					materialHistory.setDprtcode(user.getJgdm());
					materialHistory.setBjid(instockDetail.getBjid());
					materialHistory.setBjh(hcMainData.getBjh());
					materialHistory.setZwms(hcMainData.getZwms());
					materialHistory.setYwms(hcMainData.getYwms());
					materialHistory.setJldw(hcMainData.getJldw());
					materialHistory.setDjsl(new BigDecimal(0));
					materialHistory.setCghtid(contract.getId());
					materialHistory.setHtbhCg(contract.getHtlsh());
					materialHistory.setTddid(reserveMain.getId());
					materialHistory.setTddh(reserveMain.getSqdh());//TODO
					materialHistory.setRksj(rksjDate);
					materialHistory.setRkbmid(rkbmid);
					materialHistory.setRkrid(rkrid);
					materialHistory.setXh(hcMainData.getXingh());
					materialHistory.setGg(hcMainData.getGg());
					this.materialHistoryMapper.insertSelective(materialHistory);
					
					//新增入库库存
					InstockDetailStock instockDetailStock = new InstockDetailStock();
					instockDetailStock.setId(UUID.randomUUID().toString());
					instockDetailStock.setMainid(id);
					instockDetailStock.setKcllid(materialHistory.getId());
					instockDetailStock.setZt(EffectiveEnum.YES.getId());
					instockDetailStock.setWhdwid(user.getJgdm());
					instockDetailStock.setWhrid(user.getId());
					this.instockDetailStockMapper.insert(instockDetailStock);
					
				}else{//修改
					MaterialHistory materialHistory = new MaterialHistory();
					materialHistory.setId((String) detailStockMap.get("kcllid"));
					materialHistory.setCkid(storage.getMainid());
					materialHistory.setCklb(storage.getCklb());
					materialHistory.setCkh(storage.getCkh());
					materialHistory.setCkmc(storage.getStore().getCkmc());
					materialHistory.setKwid(storage.getId());
					materialHistory.setKwh(storage.getKwh());
					materialHistory.setSn(sn);
					materialHistory.setPch(pch);
					materialHistory.setKcsl(kcsl);
					materialHistory.setSpqx(spqxDate);
					materialHistory.setKccb(kccbNumber);
					materialHistory.setRksj(rksjDate);
					materialHistory.setRkbmid(rkbmid);
					materialHistory.setRkrid(rkrid);
					this.materialHistoryMapper.updateByPrimaryKeySelective(materialHistory);
				}
			}
		}
		
		BigDecimal rksl = instockDetail.getSl();
		if(rksl.compareTo(count) != 0){
			throw new BusinessException("实际入库数量与待入库数量不一致");
		}
		
		
//		InstockDetail newinstockDetail = new InstockDetail();
//		newinstockDetail.setId(instockDetail.getId());
//		newinstockDetail.setWhrid(user.getId());
//		newinstockDetail.setWhdwid(user.getBmdm());
//		this.instockDetailMapper.updateByPrimaryKeySelective(newinstockDetail);
//		
		//修改入库单
		if(StringUtils.isBlank(instock.getRkdh())){//入库单号为空生成入库单号
			try {
				instock.setRkdh(saibongUtilService.generate(user.getJgdm(),SaiBongEnum.CGRK.getName()));
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
		}
		instock.setRksj(rksjDate);
		instock.setRukid(rkrid);
		instock.setRkbmid(rkbmid);
		instock.setZdrid(user.getId());
		instock.setZdbmid(user.getBmdm());
		instock.setZdsj(rksjDate);
		instock.setBz((String)map.get("bz"));
		this.instockMapper.updateByPrimaryKeySelective(instock);
		
		return instock.getRkdh();
	}
	
	/**
	 * 提交采购入库，将航材入库信息变更到库存
	 * @param detailId 入库航材ID
	 */
	public void savePurchaseSubmit(String detailId){
		
		//将航材入库信息 插入到库存
		this.instockDetailStockMapper.insertToStock(detailId);
		//增加航材检验信息
		this.instockDetailStockMapper.insertToQualityCheck(detailId);
		//更改入库单状态到 提交状态
		this.instockMapper.updateStateByDetailId(detailId, RequisitionStatusEnum.SAVED.getId(), RequisitionStatusEnum.SUBMITED.getId());
	}
	
	/**
	 * 作废采购入库单
	 * @param detailId 入库航材ID
	 */
	public void savePurchaseDiscard(String detailId){
		
		InstockDetail instockDetail = this.instockDetailMapper.selectByPrimaryKey(detailId);
		if(instockDetail == null || instockDetail.getInstock() == null){
			throw new RuntimeException("当前入库单不存在");
		}
		Instock instock = instockDetail.getInstock();
		//检验业务状态
		if(instock.getZt().intValue() != InstockStatusEnum.SAVED.getId().intValue()){
			throw new RuntimeException("当前入库单状态为"+InstockStatusEnum.getName(instock.getZt())+"，不可进行该操作");
		}
		
		//还原合同到货数量
		this.instockDetailMapper.cancelContractDhsl(detailId);
		//更改合同状态
		this.instockDetailMapper.cancelContractZt(detailId);
		//作废入库单
		int count = this.instockMapper.updateStateByDetailId(detailId, InstockStatusEnum.SAVED.getId(), InstockStatusEnum.INVALID.getId());
		if(count == 0){
			throw new RuntimeException("作废失败");
		}
	}
	
	/**
	 * 保存借出归还入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public void saveLend(Map<String, Object> map) throws BusinessException{
		//接收并验证参数
		String kccb = (String)map.get("kccb");//库存成本
		BigDecimal kccbNumber = null;
		if(StringUtils.isNotBlank(kccb)){
			kccbNumber = new BigDecimal(kccb);
		}
		
		String rksj = (String)map.get("rksj");//入库时间
		Date rksjDate = null;
		if(StringUtils.isNotBlank(rksj)){
			try {
				rksjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, rksj);
			} catch (ParseException e) {
				throw new BusinessException("数据异常", e);
			}
		}
		String rkrid = (String)map.get("rkrid");//入库人ID
		String rkbmid = (String)map.get("rkbmid");//入库部门ID
		String bjid = (String)map.get("bjid");//待归还部件ID
		String sqrid = (String)map.get("sqrid");//归还人
		String sqbmid = (String)map.get("sqbmid");//归还人部门
		String sl = (String)map.get("sl");//归还数量
		String jdfzr = (String)map.get("jdfzr");//归还人，借调方负责人
		String wpdxid = (String)map.get("wpdxid");//外派对象
		
		User user = ThreadVarUtil.getUser();
		
		BigDecimal slDecimal = null;
		try{
			slDecimal = new BigDecimal(sl).setScale(2);
		}catch(Exception e){
			throw new BusinessException("归还数量格式不正确", e);
		}
		
		//查询借调对象 部件 未归还 信息
		Expatriate expatriateStatics = this.expatriateService.querySingleLend(user.getJgdm(), wpdxid, bjid);
		
		if(null == expatriateStatics || null == expatriateStatics.getDghsl() || expatriateStatics.getDghsl().compareTo(new BigDecimal(0)) == 0){
			throw new BusinessException("没有借出未归还信息");
		}
		
		if(expatriateStatics.getDghsl().setScale(2).compareTo(slDecimal) == -1){
			throw new BusinessException("归还数超出借出未归还数量");
		}
		
		List<Expatriate> list = this.expatriateService.queryListLend(user.getJgdm(), wpdxid, bjid, slDecimal);
		if(null == list || list.isEmpty()){
			throw new BusinessException("没有借出未归还信息");
		}
		
		//保存库存信息及检验单信息
		List<Map<String, Object>> detailStockList = (List<Map<String, Object>>)map.get("detailStock");
		if(detailStockList == null || detailStockList.isEmpty()){
			throw new BusinessException("入库航材信息错误"); 
		} 
		List<Stock> stockList = new ArrayList<Stock>();
		Stock stock = null;
		BigDecimal count = new BigDecimal(0).setScale(2);
		
		Map<String, Storage> storageMap = new HashMap<String, Storage>();//<kwid, Storage> 用于缓存仓库信息
		Storage storage = null;
		Map<String, HCMainData> hcMainDataMap = new HashMap<String, HCMainData>();//<bjid, Storage> 用于缓存部件信息
		HCMainData hcMainData = null;
		
		String instockId = UUID.randomUUID().toString();
		String rkdh;
		try {
			rkdh = this.saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e); 
		}
		
		for (Map<String, Object> stockMap : detailStockList) {
			
			String rkbjid = (String)stockMap.get("bjid");//入库部件ID
			String kwid = (String)stockMap.get("kwid");//库位ID
			String sn = (String)stockMap.get("sn");//序列号
			String pch = (String)stockMap.get("pch");//批次号
			BigDecimal kcsl = new BigDecimal((String)stockMap.get("kcsl")).setScale(2);//库存数量
			count = count.add(kcsl);
			
			//获取仓库库位信息
			if(StringUtils.isNotBlank(kwid)){
				if(storageMap.containsKey(kwid)){
					storage = storageMap.get(kwid);
				}else{
					storage = this.storageMapper.selectByIdWithRel(kwid);
					storageMap.put(kwid, storage);
				}
			}else{
				storage = null;
			}
			if(storage == null){
				throw new BusinessException("库位不存在");
			}
			
			//获取部件信息
			if(StringUtils.isNotBlank(rkbjid)){
				if(hcMainDataMap.containsKey(rkbjid)){
					hcMainData = hcMainDataMap.get(rkbjid);
				}else{
					hcMainData = this.hCMainDataMapper.selectById(rkbjid);
					hcMainDataMap.put(rkbjid, hcMainData);
				}
			}else{
				hcMainData = null;
			}
			if(hcMainData == null){
				throw new BusinessException("航材主数据不存在");
			}
			
			//验证序列号重复性
			if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
				if(StringUtils.isBlank(sn)){
					throw new BusinessException("序列号不能为空");
				}
				if(!this.materialUtilService.validateSnUniqueness(user.getJgdm(), hcMainData.getBjh(), sn)){
					throw new BusinessException("序列号"+sn+"已存在");
				}
			}
			
			//保存库存信息
			stock = new Stock();
			stock.setId(UUID.randomUUID().toString());
			stock.setCkid(storage.getMainid());
			stock.setCklb(storage.getCklb());
			stock.setCkh(storage.getCkh());
			stock.setCkmc(storage.getStore().getCkmc());
			stock.setKwid(storage.getId());
			stock.setKwh(storage.getKwh());
			stock.setSn(sn);
			stock.setPch(pch);
			stock.setKcsl(kcsl);
			stock.setKccb(kccbNumber);
			stock.setZt(StockStatusEnum.RECEIVED.getId());
			stock.setDprtcode(user.getJgdm());
			stock.setBjid(rkbjid);
			stock.setBjh(hcMainData.getBjh());
			stock.setZwms(hcMainData.getZwms());
			stock.setYwms(hcMainData.getYwms());
			stock.setJldw(hcMainData.getJldw());
			stock.setDjsl(new BigDecimal(0));
			stock.setRksj(rksjDate);
			stock.setRkbmid(rkbmid);
			stock.setRkrid(rkrid);
			stock.setXh(hcMainData.getXingh());
			stock.setGg(hcMainData.getGg());
			this.stockMapper.insert(stock);
			
			stockList.add(stock);
			//增加检验单
			QualityCheck qualityCheck = stock.toQualityCheck();
			qualityCheck.setId(UUID.randomUUID().toString());
			qualityCheck.setRkdid(instockId);
			qualityCheck.setRkdh(rkdh);
			this.qualityCheckMapper.insertSelective(qualityCheck);
		}
		
		if(count.compareTo(slDecimal) != 0){
			throw new BusinessException("入库航材数量和归还数量不一致");
		}
		
		//保存入库单
		Instock instock = new Instock();
		instock.setId(instockId);
		instock.setDprtcode(user.getJgdm());
		instock.setRklx(InstockTypeEnum.TYPE4.getId());
		instock.setRkdh(rkdh);
		instock.setRkbmid(rkbmid);
		instock.setRukid(rkrid);
		instock.setRksj(rksjDate);
		instock.setZt(RequisitionStatusEnum.SUBMITED.getId());
		instock.setZdbmid(user.getBmdm());
		instock.setZdrid(user.getId());
		instock.setSqbmid(sqbmid);
		instock.setSqrid(sqrid);
		instock.setJddxid(wpdxid);
		instock.setJdfzr(jdfzr);
		instock.setBz((String)map.get("bz"));
		instock.setSqsj2(rksjDate);
		this.instockMapper.insertSelective(instock);
		
		//处理航材归还以及入库单航材信息
		for (Expatriate expatriate : list) {
			BigDecimal dghsl = expatriate.getDghsl();
			//保存归还记录及入库从表记录
			this.saveLendDetail(expatriate, stockList, instock, user);
			//修改外派清单已归还数据
			expatriate.setGhsl(expatriate.getGhsl().add(dghsl).setScale(2));
			this.expatriateMapper.updateByPrimaryKeySelective(expatriate);
		}
	}
	
	//处理航材归还以及入库单航材信息
	private void saveLendDetail(Expatriate expatriate, List<Stock> stockList, Instock instock, User user) throws BusinessException{
		Stock stock = null;
		try{
			stock = stockList.get(0);
		}catch(Exception e){//防止数据异常
			throw new BusinessException("入库数据异常", e);
		}
		//新增库存履历
		MaterialHistory materialHistory = stock.toMaterialHistory();
		materialHistory.setId(UUID.randomUUID().toString());
		
		int compare = expatriate.getDghsl().setScale(2).compareTo(materialHistory.getKcsl().setScale(2));
		if(compare == 1){//待归还数量大于库存数，还需要再使用一条库存记录
			
			this.materialHistoryMapper.insertSelective(materialHistory);
			this.saveLendDetail(expatriate, materialHistory, instock, user);
			stockList.remove(0);
			//再次调用自己
			this.saveLendDetail(expatriate, stockList, instock, user);
			
		}else if(compare == 0){
			
			//修改履历库存数为待归还数
			materialHistory.setKcsl(expatriate.getDghsl());
			this.materialHistoryMapper.insertSelective(materialHistory);
			
			//移除内存中的库存记录
			stockList.remove(0);
			
			this.saveLendDetail(expatriate, materialHistory, instock, user);
			
			
		}else{//当前库存已经满足数量要求
			
			//修改履历库存数为待归还数
			materialHistory.setKcsl(expatriate.getDghsl());
			this.materialHistoryMapper.insertSelective(materialHistory);
			
			//将内存中库存数减少
			stock.setKcsl(stock.getKcsl().subtract(expatriate.getDghsl()));
			
			this.saveLendDetail(expatriate, materialHistory, instock, user);
		}
	}
	
	
	/**
	 * 保存外派清单对应库存 、新增入库单航材、新增入库单明细
	 * @param expatriate
	 * @param materialHistory
	 * @param instock
	 * @param user
	 */
	private void saveLendDetail(Expatriate expatriate, MaterialHistory materialHistory, Instock instock, User user){
		
		//新增外派清单对应库存
		ExpatriateDetail expatriateDetail = new ExpatriateDetail();
		expatriateDetail.setId(UUID.randomUUID().toString());
		expatriateDetail.setMainid(expatriate.getId());
		expatriateDetail.setKcllid(materialHistory.getId());
		expatriateDetail.setDprtcode(user.getJgdm());
		expatriateDetail.setSjlx(2);//TODO 改为枚举
		expatriateDetail.setGldjlx(1);//TODO 改为枚举
		expatriateDetail.setDjid(instock.getId());
		this.expatriateDetailMapper.insert(expatriateDetail);
		
		//新增入库单航材
		InstockDetail instockDetail = new InstockDetail();
		instockDetail.setId(UUID.randomUUID().toString());
		instockDetail.setMainid(instock.getId());
		instockDetail.setBjid(materialHistory.getBjid());
		instockDetail.setBjh(materialHistory.getBjh());
		instockDetail.setSl(materialHistory.getKcsl());
		instockDetail.setWpqdid(expatriate.getId());
		instockDetail.setZt(EffectiveEnum.YES.getId());
		instockDetail.setWhdwid(user.getJgdm());
		instockDetail.setWhrid(user.getId());
		this.instockDetailMapper.insert(instockDetail);
		
		//新增入库单明细
		InstockDetailStock instockDetailStock = new InstockDetailStock();
		instockDetailStock.setId(UUID.randomUUID().toString());
		instockDetailStock.setMainid(instockDetail.getId());
		instockDetailStock.setKcllid(materialHistory.getId());
		instockDetailStock.setZt(EffectiveEnum.YES.getId());
		instockDetailStock.setWhdwid(user.getJgdm());
		instockDetailStock.setWhrid(user.getId());
		this.instockDetailStockMapper.insert(instockDetailStock);
		
		
	}
	
	/**
	 * 入库时查询默认库存成本
	 * @param bjid 部件ID
	 * @param instockDetailId 入库航材ID 采购入库时传入用于查询合同价格
	 */
	public BigDecimal queryDefaultCost(String bjid, String bjh, String instockDetailId){
		
		if(StringUtils.isBlank(bjid)){
			if(StringUtils.isNotBlank(bjh)){
				//根据部件号查询bjid
				HCMainData entity = new HCMainData();
				
				User user = ThreadVarUtil.getUser();
				entity.setDprtcode(user.getJgdm());
				entity.setBjh(bjh);
				List<HCMainData> list = this.hCMainDataMapper.checkMaterial(entity);
				if(null == list || list.isEmpty()){
					return null;
				}
				bjid =  (String) list.get(0).getId();
			}else{
				return null;
			}
		}
		
		//查询航材成本信息
		MaterialCost materialCost = this.materialCostMapper.selectNewestByBjid(bjid);
		if(materialCost != null){
			if(materialCost.getJuescb() != null && materialCost.getJuescb().compareTo(new BigDecimal(0)) == 1){
				return materialCost.getJuescb();
			}
			if(materialCost.getJiescb() != null && materialCost.getJiescb().compareTo(new BigDecimal(0)) == 1){
				return materialCost.getJiescb();
			}
			
			//采购入库时查询不到成本信息时获取合同价格
			if(StringUtils.isNotBlank(instockDetailId)){
				ContractDetail contractDetail = this.contractDetailMapper.selectByInstockDetailId(instockDetailId);
				if(contractDetail != null && contractDetail.getHtClf() != null && contractDetail.getHtClf().compareTo(new BigDecimal(0)) == 1){
					return contractDetail.getHtClf();
				}
			}else{
				BaseEntity baseEntity = new BaseEntity();
				baseEntity.getParamsMap().put("bjid", bjid);
				User user = ThreadVarUtil.getUser();
				baseEntity.getParamsMap().put("dprtcode", user.getJgdm());
				
				Pagination pagination = new Pagination();
				pagination.setPage(1);
				pagination.setRows(1);
				pagination.setSort("auto");
				baseEntity.setPagination(pagination);
				List<ContractDetail> contractDetailList = contractDetailMapper.selectCostHisByBjidPage(baseEntity);
				if(contractDetailList != null && !contractDetailList.isEmpty()){
					return contractDetailList.get(0).getHtClf();
				}
			}
			
			if(materialCost.getGscb() != null && materialCost.getGscb().compareTo(new BigDecimal(0)) == 1){
				return materialCost.getGscb();
			}
		}
		
		//采购入库时查询不到成本信息时获取合同价格
		if(StringUtils.isNotBlank(instockDetailId)){
			ContractDetail contractDetail = this.contractDetailMapper.selectByInstockDetailId(instockDetailId);
			if(contractDetail != null && contractDetail.getHtClf() != null && contractDetail.getHtClf().compareTo(new BigDecimal(0)) == 1){
				return contractDetail.getHtClf();
			}
		}else{
			BaseEntity baseEntity = new BaseEntity();
			baseEntity.getParamsMap().put("bjid", bjid);
			User user = ThreadVarUtil.getUser();
			baseEntity.getParamsMap().put("dprtcode", user.getJgdm());
			
			Pagination pagination = new Pagination();
			pagination.setPage(1);
			pagination.setRows(1);
			pagination.setSort("auto");
			baseEntity.setPagination(pagination);
			List<ContractDetail> contractDetailList = contractDetailMapper.selectCostHisByBjidPage(baseEntity);
			if(contractDetailList != null && !contractDetailList.isEmpty()){
				return contractDetailList.get(0).getHtClf();
			}
		}
		
		
		return null;
	}

	/**
	 * 查询送修待入库列表
	 * @return 
	 */
	public Map<String, Object> queryRepairPageList(BaseEntity entity){
		PageHelper.startPage(entity.getPagination());
		List<InstockDetail> list = this.instockDetailMapper.queryRepairPage(entity);
		return PageUtil.pack4PageHelper(list, entity.getPagination());
	} 
	
	/**
	 * 作废送修入库单
	 * @param instockDetailId 入库航材ID
	 */
	public void saveRepairDiscard(String instockDetailId){
		
		InstockDetail instockDetail = this.instockDetailMapper.selectByPrimaryKey(instockDetailId);
		if(instockDetail == null || instockDetail.getInstock() == null){
			throw new RuntimeException("当前入库单不存在");
		}
		Instock instock = instockDetail.getInstock();
		//检验业务状态
		if(instock.getZt().intValue() != InstockStatusEnum.SAVED.getId().intValue()){
			throw new RuntimeException("当前入库单状态为"+InstockStatusEnum.getName(instock.getZt())+"，不可进行该操作");
		}
		
		//还原合同到货数量
		this.instockDetailMapper.cancelContractDhsl(instockDetailId);
		//更改合同状态
		this.instockDetailMapper.cancelContractZt(instockDetailId);
		//作废入库单
		int count = this.instockMapper.updateStateByDetailId(instockDetailId, InstockStatusEnum.SAVED.getId(), InstockStatusEnum.INVALID.getId());
		if(count == 0){
			throw new RuntimeException("作废失败");
		}
	}
	
	/**
	 * 查询送修航材入库单，采购入库单和入库航材是一对一，所以使用InstockDetail查询和返回
	 * @param instockDetailId 入库航材ID
	 * @return
	 */
	public InstockDetail queryRepairById(String instockDetailId){
		//查询入库单航材信息
		InstockDetail instockDetail = this.instockDetailMapper.selectByPrimaryKey(instockDetailId);
		if(instockDetail == null){
			throw new RuntimeException("入库航材不存在");
		}
		//查询航材入库信息
		instockDetail.setDetailStockList(this.instockDetailStockMapper.selectByMainId(instockDetail.getId()));
		
		return instockDetail;
	}
	
	/**
	 * 保存送修入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public void saveRepair(Map<String, Object> map) throws BusinessException{
		//接收并验证参数
		String instockDetailId = (String)map.get("instockDetailId");
		if(StringUtils.isBlank(instockDetailId)){
			logger.error("前台传递ID为空");
			throw new BusinessException("数据异常");
		}
		
		String kccb = (String)map.get("kccb");//库存成本
		BigDecimal kccbNumber = null;
		if(StringUtils.isNotBlank(kccb)){
			try{
				kccbNumber = new BigDecimal(kccb).setScale(2);
			}catch(Exception e){
				throw new BusinessException("库存成本格式转换错误");
			}
		}else{
			throw new BusinessException("库存成本不能为空");
		}
		
		String rksj = (String)map.get("rksj");//入库时间
		Date rksjDate = null;
		if(StringUtils.isNotBlank(rksj)){
			try {
				rksjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, rksj);
			} catch (ParseException e) {
				logger.error("入库日期格式转换错误", e);
				throw new BusinessException("入库日期格式转换错误");
			}
		}else{
			throw new BusinessException("入库日期格式不能为空");
		}
		String rkrid = (String)map.get("rkrid");//入库人ID
		String rkbmid = (String)map.get("rkbmid");//入库部门ID
		
		String bjid = (String)map.get("bjid");//部件ID
		String sn = (String)map.get("sn");//序列号
		String kwid = (String)map.get("kwid");
		
		
		InstockDetail instockDetail = this.instockDetailMapper.selectByPrimaryKey(instockDetailId);
		if(instockDetail == null || instockDetail.getInstock() == null || EffectiveEnum.YES.getId().intValue() != instockDetail.getZt().intValue()){
			throw new BusinessException("入库航材不存在");
		}
		Instock instock = instockDetail.getInstock();
		
		//检验业务状态
		if(instock.getZt().intValue() != InstockStatusEnum.SAVED.getId().intValue()){
			throw new RuntimeException("当前入库单状态为"+InstockStatusEnum.getName(instock.getZt())+"，不可进行该操作");
		}
		
		User user = ThreadVarUtil.getUser();
		
		ContractDetail contractDetail = instockDetail.getContractDetail();//合同航材
		Contract contract = contractDetail != null ? contractDetail.getContract(): new Contract();//合同
		
		
		Storage storage = null;
		if(StringUtils.isNotBlank(kwid)){
			storage = this.storageMapper.selectByIdWithRel(kwid);
		}
		if(storage == null){
			throw new BusinessException("库位不存在");
		}
		//查询入库航材库存数据
		List<InstockDetailStock> instockDetailStockList = this.instockDetailStockMapper.selectByMainId(instockDetailId);
		
		//查询入库航材主数据
		HCMainData hcMainData = this.hCMainDataMapper.selectById(bjid);
		
		
		//验证序列号重复性
		if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
			if(StringUtils.isBlank(sn)){
				throw new BusinessException("序列号不能为空");
			}
			if(!this.materialUtilService.validateSnUniqueness(user.getJgdm(), hcMainData.getBjh(), sn)){
				throw new BusinessException("序列号"+sn+"已存在");
			}
		}
		
		//保存履历以及入库航材库存
		MaterialHistory materialHistory = new MaterialHistory();
		materialHistory.setKcid(UUID.randomUUID().toString());
		materialHistory.setCkid(storage.getMainid());
		materialHistory.setCklb(storage.getCklb());
		materialHistory.setCkh(storage.getCkh());
		materialHistory.setCkmc(storage.getStore().getCkmc());
		materialHistory.setKwid(storage.getId());
		materialHistory.setKwh(storage.getKwh());
		materialHistory.setSn(sn);
		materialHistory.setKcsl(new BigDecimal(1).setScale(2));
		materialHistory.setKccb(kccbNumber);
		materialHistory.setZt(StockStatusEnum.RECEIVED.getId());
		materialHistory.setDprtcode(user.getJgdm());
		materialHistory.setBjid(hcMainData.getId());
		materialHistory.setBjh(hcMainData.getBjh());
		materialHistory.setZwms(hcMainData.getZwms());
		materialHistory.setYwms(hcMainData.getYwms());
		materialHistory.setJldw(hcMainData.getJldw());
		materialHistory.setDjsl(new BigDecimal(0).setScale(2));
		materialHistory.setCghtid(contract.getId());
		materialHistory.setHtbhCg(contract.getHtlsh());
		materialHistory.setRksj(rksjDate);
		materialHistory.setRkbmid(rkbmid);
		materialHistory.setRkrid(rkrid);
		materialHistory.setXh(hcMainData.getXingh());
		materialHistory.setGg(hcMainData.getGg());
		
		if(instockDetailStockList == null || instockDetailStockList.isEmpty()){
			//当入库航材库存 不存在时，新增航材履历和 入库航材库存 信息
			//新增航材履历
			materialHistory.setId(UUID.randomUUID().toString());
			this.materialHistoryMapper.insertSelective(materialHistory);
			
			//新增入库航材库存
			InstockDetailStock instockDetailStock = new InstockDetailStock();
			instockDetailStock.setId(UUID.randomUUID().toString());
			instockDetailStock.setMainid(instockDetailId);
			instockDetailStock.setKcllid(materialHistory.getId());
			instockDetailStock.setZt(EffectiveEnum.YES.getId());
			instockDetailStock.setWhdwid(user.getJgdm());
			instockDetailStock.setWhrid(user.getId());
			this.instockDetailStockMapper.insertSelective(instockDetailStock);
		}else{
			//当入库航材库存 不存在时，修改航材履历和 入库航材库存 信息
			InstockDetailStock instockDetailStock = instockDetailStockList.get(0);
			materialHistory.setId(instockDetailStock.getKcllid());
			this.materialHistoryMapper.updateByPrimaryKeySelective(materialHistory);
			
			instockDetailStock.setWhdwid(user.getJgdm());
			instockDetailStock.setWhrid(user.getId());
			this.instockDetailStockMapper.updateByPrimaryKeySelective(instockDetailStock);
		}
		
		//保存入库单
		if(StringUtils.isBlank(instock.getRkdh())){//入库单号为空生成入库单号
			try {
				instock.setRkdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName()));
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
		}
		instock.setRksj(rksjDate);
		instock.setRukid(rkrid);
		instock.setRkbmid(rkbmid);
		instock.setZdrid(user.getId());
		instock.setZdbmid(user.getBmdm());
		instock.setZdsj(rksjDate);
		instock.setBz((String)map.get("bz"));
		this.instockMapper.updateByPrimaryKeySelective(instock);
		
	}
	
	
	/**
	 * 提交保存送修入库
	 * @param map
	 * @return 入库单号
	 * @throws BusinessException 
	 */
	public void saveRepairSubmit(Map<String, Object> map) throws BusinessException{
		
		User user = ThreadVarUtil.getUser();
		
		String instockDetailId = (String)map.get("instockDetailId");
		InstockDetail instockDetail = this.instockDetailMapper.selectById(instockDetailId);
		
		List<InstockDetailStock> instockDetailStockList = this.instockDetailStockMapper.selectByMainId(instockDetailId);
		
		InstockDetailStock instockDetailStock = instockDetailStockList.get(0);
		
		//修改外派清单
		//新增外派清单详单
		ExpatriateDetail expatriateDetail = new ExpatriateDetail();
		expatriateDetail.setId(UUID.randomUUID().toString());
		expatriateDetail.setMainid(instockDetail.getWpqdid());
		expatriateDetail.setKcllid(instockDetailStock.getKcllid());
		expatriateDetail.setDprtcode(user.getJgdm());
		expatriateDetail.setSjlx(2);//TODO 改为枚举
		expatriateDetail.setGldjlx(1);//TODO 改为枚举
		expatriateDetail.setDjid(instockDetailStock.getId());
		expatriateDetail.setCxsl(new BigDecimal(0).setScale(2));
		this.expatriateDetailMapper.insert(expatriateDetail);
		//更改外派清单到货数
		Expatriate expatriate = new Expatriate();
		expatriate.setId(instockDetail.getWpqdid());
		expatriate.setGhsl(new BigDecimal(1));//送修的序列号管理直接给1
		this.expatriateMapper.updateByPrimaryKeySelective(expatriate);
		//将航材入库信息 插入到库存
		this.instockDetailStockMapper.insertToStock(instockDetailId);
		//更改入库单状态到 提交状态
		this.instockMapper.updateStateByDetailId(instockDetailId, RequisitionStatusEnum.SAVED.getId(), RequisitionStatusEnum.SUBMITED.getId());
		
		//增加航材检验信息
		this.instockDetailStockMapper.insertToQualityCheck(instockDetailId);
	}
	
	/**
	 * 查询借入入库单
	 * @param instockId 入库单ID
	 * @param borrowApplyId 借入申请ID
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryBorrow(String instockId, String borrowApplyId) throws BusinessException{
		if(StringUtils.isBlank(instockId) && StringUtils.isBlank(borrowApplyId)){
			logger.error("查询借入入库单异常：查询参数入库单ID及借入申请单ID均为空");
			throw new BusinessException("业务异常");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Instock instock = null;
		//查找入库单
		if(StringUtils.isNotBlank(instockId)){
			instock = this.instockMapper.selectById(instockId);
		}
//			else{
//			instock = this.instockMapper.selectByXgdjId(borrowApplyId);
//		}
		
		if(null != instock){
			map.put("instockDetailStockList", this.instockDetailStockMapper.selectByInstockId(instockId));
		}else{
			instock = new Instock();
			if(StringUtils.isNotBlank(borrowApplyId)){
				BorrowApply borrowApply = this.borrowApplyMapper.selectById(borrowApplyId);
				
				if(null != borrowApply){
					instock.setFjzch(borrowApply.getFjzch());
					instock.setJddxid(borrowApply.getJddxid());
					instock.setSecondment(borrowApply.getSecondment());
					instock.setSqrid(borrowApply.getSqrid());
					instock.setSqrUser(borrowApply.getSqrUser());
					instock.setSqbmid(borrowApply.getSqdwid());
					instock.setXgdjid(borrowApplyId);
					List<BorrowApplyDetail> borrowApplyDetailList = this.borrowApplyDetailMapper.selectByMainId(borrowApplyId);
					if(borrowApplyDetailList != null && !borrowApplyDetailList.isEmpty()){
						map.put("borrowApplyDetailList", borrowApplyDetailList);
					}
				}else{
					throw new BusinessException("借入申请不存在或状态异常");
				}
			}
		}
		map.put("instock", instock);
		return map;
	}
	
	
	/**
	 * 提交借入入库
	 * @param map
	 * @throws BusinessException 
	 */
	public void saveBorrowSubmit(Map<String, Object> map) throws BusinessException{
		
		String borrowApplyId = (String)map.get("borrowApplyId");
		String rukid = (String)map.get("rukid");
		String rkbmid = (String)map.get("rkbmid");
		String sqrid = (String)map.get("sqrid");
		String sqbmid = (String)map.get("sqbmid");
		String sqsj2 = (String)map.get("sqsj2");
		String rksj = (String)map.get("rksj");
		String jddx = (String)map.get("jddx");
		String jdfzr = (String)map.get("jdfzr");
		String fjzch = (String)map.get("fjzch");
		String bz = (String)map.get("bz");
		
		Date rksjDate = null;
		if(StringUtils.isNotBlank(rksj)){
			try {
				rksjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, rksj);
			} catch (ParseException e) {
				logger.error("入库日期格式转换错误", e);
				throw new BusinessException("入库日期格式转换错误");
			}
		}else{
			throw new BusinessException("入库日期格式不能为空");
		}
		
		Date sqsjDate = null;
		if(StringUtils.isNotBlank(sqsj2)){
			try {
				sqsjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, sqsj2);
			} catch (ParseException e) {
				logger.error("接收日期格式转换错误", e);
				throw new BusinessException("接收日期格式转换错误");
			}
		}else{
			throw new BusinessException("接收日期格式不能为空");
		}
		
		User user = ThreadVarUtil.getUser();
		
		//预生成入库单ID
		String instockId = UUID.randomUUID().toString();
		String rkdh;
		try {
			rkdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}//入库单号
		
		List<Map<String, Object>> detailList = (List<Map<String, Object>>)map.get("detail");
		
		//开始保存业务
		Map<String, Storage> storageMap = new HashMap<String, Storage>();//缓存库位<kwid, 仓库>
		Storage storage = null;
		Map<String, HCMainData> hcMainDataMap = new HashMap<String, HCMainData>();//缓存部件<bjid, 部件>
		HCMainData hcMainData = null;
		String czls = UUID.randomUUID().toString();//操作流水；
		if(StringUtils.isBlank(borrowApplyId)){//表示为手工借入
			//保存入库航材
			Map<String, InstockDetail> instockDetailMap = new HashMap<String, InstockDetail>();//借入明细ID和入库航材对应关系
			//保存入库库存
			if(null != detailList && !detailList.isEmpty()){
				InstockDetail instockDetail = null;
				Expatriate expatriate = null;
				for (Map<String, Object> detailMap : detailList) {
					
					String bjid = (String)detailMap.get("bjid");//序列号
					String sn = (String)detailMap.get("sn");//序列号
					String pch = (String)detailMap.get("pch");//批次号
					String kwid = (String)detailMap.get("kwid");
					
					String kccb = (String)detailMap.get("kccb");//库存成本
					BigDecimal kccbNumber = null;
					if(StringUtils.isNotBlank(kccb)){
						try{
							kccbNumber = new BigDecimal(kccb).setScale(2);
						}catch(Exception e){
							throw new BusinessException("库存成本格式转换错误");
						}
					}else{
						throw new BusinessException("库存成本不能为空");
					}
					String kcsl = (String)detailMap.get("kcsl");
					BigDecimal kcslNumber = null;
					if(StringUtils.isNotBlank(kcsl)){
						try{
							kcslNumber = new BigDecimal(kcsl).setScale(2);
						}catch(Exception e){
							throw new BusinessException("入库数量格式转换错误");
						}
					}else{
						throw new BusinessException("入库数量不能为空");
					}
					
					//获取仓库库位信息
					if(StringUtils.isNotBlank(kwid)){
						if(storageMap.containsKey(kwid)){
							storage = storageMap.get(kwid);
						}else{
							storage = this.storageMapper.selectByIdWithRel(kwid);
							storageMap.put(kwid, storage);
						}
					}else{
						storage = null;
					}
					if(storage == null){
						throw new BusinessException("库位不存在");
					}
					
					//获取部件信息
					if(hcMainDataMap.containsKey(bjid)){
						hcMainData = hcMainDataMap.get(bjid);
					}else{
						hcMainData = this.hCMainDataMapper.selectById(bjid);
						hcMainDataMap.put(bjid, hcMainData);
					}
					if(hcMainData == null){
						throw new BusinessException("航材主数据不存在");
					}
					
					//验证序列号重复性
					if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
						if(StringUtils.isBlank(sn)){
							throw new BusinessException("序列号不能为空");
						}
						if(!this.materialUtilService.validateSnUniqueness(user.getJgdm(), hcMainData.getBjh(), sn)){
							throw new BusinessException("序列号"+sn+"已存在");
						}
					}
					
					//保存入库航材
					instockDetail = new InstockDetail();
					instockDetail.setId(UUID.randomUUID().toString());
					instockDetail.setMainid(instockId);
					instockDetail.setBjid(hcMainData.getId());
					instockDetail.setBjh(hcMainData.getBjh());
					instockDetail.setSl(kcslNumber);
					instockDetail.setWpqdid(UUID.randomUUID().toString());
					instockDetail.setZt(EffectiveEnum.YES.getId());
					instockDetail.setWhdwid(user.getJgdm());
					instockDetail.setWhrid(user.getId());
					this.instockDetailMapper.insertSelective(instockDetail);
					//保存外派清单
					expatriate = new Expatriate();
					expatriate.setId(instockDetail.getWpqdid());
					expatriate.setDprtcode(user.getJgdm());
					expatriate.setJdlx(StatusEnum.CALLIN.getId().shortValue());
					expatriate.setWpdxid(jddx);
					expatriate.setBjid(hcMainData.getId());
					expatriate.setBjh(hcMainData.getBjh());
					expatriate.setSn(sn);
					expatriate.setPch(pch);
					expatriate.setKcsl(kcslNumber);
					expatriate.setGhsl(new BigDecimal(0).setScale(2));
					expatriate.setZt(EffectiveEnum.YES.getId());
					expatriate.setWpsj(new Date());
					this.expatriateMapper.insertSelective(expatriate);
					
					//保存履历
					MaterialHistory materialHistory = new MaterialHistory();
					materialHistory.setId(UUID.randomUUID().toString());
					materialHistory.setKcid(UUID.randomUUID().toString());
					materialHistory.setCkid(storage.getMainid());
					materialHistory.setCklb(storage.getCklb());
					materialHistory.setCkh(storage.getCkh());
					materialHistory.setCkmc(storage.getStore().getCkmc());
					materialHistory.setKwid(storage.getId());
					materialHistory.setKwh(storage.getKwh());
					materialHistory.setSn(sn);
					materialHistory.setPch(pch);
					if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
						materialHistory.setKcsl(new BigDecimal(1).setScale(2));
					}else{
						materialHistory.setKcsl(instockDetail.getSl());
					}
					materialHistory.setKccb(kccbNumber);
					materialHistory.setZt(StockStatusEnum.RECEIVED.getId());
					materialHistory.setDprtcode(user.getJgdm());
					materialHistory.setBjid(hcMainData.getId());
					materialHistory.setBjh(hcMainData.getBjh());
					materialHistory.setZwms(hcMainData.getZwms());
					materialHistory.setYwms(hcMainData.getYwms());
					materialHistory.setJldw(hcMainData.getJldw());
					materialHistory.setDjsl(new BigDecimal(0).setScale(2));
					materialHistory.setRksj(rksjDate);
					materialHistory.setRkbmid(rkbmid);
					materialHistory.setRkrid(rukid);
					materialHistory.setXh(hcMainData.getXingh());
					materialHistory.setGg(hcMainData.getGg());
					this.materialHistoryMapper.insertSelective(materialHistory);
					//保存入库库存
					InstockDetailStock instockDetailStock = new InstockDetailStock();
					instockDetailStock.setId(UUID.randomUUID().toString());
					instockDetailStock.setMainid(instockDetail.getId());
					instockDetailStock.setKcllid(materialHistory.getId());
					instockDetailStock.setZt(EffectiveEnum.YES.getId());
					instockDetailStock.setWhdwid(user.getJgdm());
					instockDetailStock.setWhrid(user.getId());
					this.instockDetailStockMapper.insertSelective(instockDetailStock);
					//保存外派清单从表
					ExpatriateDetail expatriateDetail = new ExpatriateDetail();
					expatriateDetail.setId(UUID.randomUUID().toString());
					expatriateDetail.setMainid(instockDetail.getWpqdid());
					expatriateDetail.setKcllid(materialHistory.getId());
					expatriateDetail.setDprtcode(user.getJgdm());
					expatriateDetail.setSjlx(1);//TODO 改为枚举
					expatriateDetail.setGldjlx(1);//TODO 改为枚举
					expatriateDetail.setDjid(instockDetailStock.getId());
					this.expatriateDetailMapper.insertSelective(expatriateDetail);
					
					//保存库存
					Stock stock = materialHistory.toStock();
					this.stockMapper.insertSelective(stock);
					
					this.materialRecService.writeStockRec(stock.getId(), czls, instockId, rkdh, StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE,
							InstockTypeEnum.TYPE3.getName(),"",rkdh, "手工制单", stock.getKcsl());
					//保存检验单
					QualityCheck qualityCheck = stock.toQualityCheck();
					qualityCheck.setId(UUID.randomUUID().toString());
					qualityCheck.setRkdid(instockId);
					qualityCheck.setRkdh(rkdh);
					this.qualityCheckMapper.insertSelective(qualityCheck);
				}
			}
			
		}else{//借入申请单借入
			
			BorrowApply borrowApply = this.borrowApplyMapper.selectById(borrowApplyId);
			if(borrowApply == null){
				throw new BusinessException("借入申请单不存在");
			}
			if(ReserveStatusEnum.SUBMIT.getId().intValue() != borrowApply.getZt().intValue() && ReserveStatusEnum.AUDIT_PASS.getId().intValue() != borrowApply.getZt().intValue()){
				throw new BusinessException("该借入申请单不能进行入库");
			}
			
			//保存入库航材
			InstockDetail instockDetail = null;
			Expatriate expatriate = null;

			//保存入库库存
			if(null != detailList && !detailList.isEmpty()){
				for (Map<String, Object> detailMap : detailList) {
					String bjid = (String)detailMap.get("bjid");//序列号
					String jdsqmxid = (String)detailMap.get("jdsqmxid");//借入申请明细ID
					String sn = (String)detailMap.get("sn");//序列号
					String pch = (String)detailMap.get("pch");//批次号
					String kwid = (String)detailMap.get("kwid");
					
					String kccb = (String)detailMap.get("kccb");//库存成本
					BigDecimal kccbNumber = null;
					if(StringUtils.isNotBlank(kccb)){
						try{
							kccbNumber = new BigDecimal(kccb).setScale(2);
						}catch(Exception e){
							throw new BusinessException("库存成本格式转换错误");
						}
					}else{
						throw new BusinessException("库存成本不能为空");
					}
					
					//获取仓库库位信息
					if(StringUtils.isNotBlank(kwid)){
						if(storageMap.containsKey(kwid)){
							storage = storageMap.get(kwid);
						}else{
							storage = this.storageMapper.selectByIdWithRel(kwid);
							storageMap.put(kwid, storage);
						}
					}else{
						storage = null;
					}
					if(storage == null){
						throw new BusinessException("库位不存在");
					}
					
					String kcsl = (String)detailMap.get("kcsl");
					BigDecimal kcslNumber = null;
					if(StringUtils.isNotBlank(kcsl)){
						try{
							kcslNumber = new BigDecimal(kcsl).setScale(2);
						}catch(Exception e){
							throw new BusinessException("入库数量格式转换错误");
						}
					}else{
						throw new BusinessException("入库数量不能为空");
					}
					
					//获取部件信息
					if(hcMainDataMap.containsKey(bjid)){
						hcMainData = hcMainDataMap.get(bjid);
					}else{
						hcMainData = this.hCMainDataMapper.selectById(bjid);
						hcMainDataMap.put(bjid, hcMainData);
					}
					if(hcMainData == null){
						throw new BusinessException("航材主数据不存在");
					}
					
					//验证序列号重复性
					if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
						if(StringUtils.isBlank(sn)){
							throw new BusinessException("序列号不能为空");
						}
						if(!this.materialUtilService.validateSnUniqueness(user.getJgdm(), hcMainData.getBjh(), sn)){
							throw new BusinessException("序列号"+sn+"已存在");
						}
					}
					
					
					instockDetail = new InstockDetail();
					instockDetail.setId(UUID.randomUUID().toString());
					instockDetail.setMainid(instockId);
					instockDetail.setBjid(hcMainData.getId());
					instockDetail.setBjh(hcMainData.getBjh());
					instockDetail.setSl(kcslNumber);
					instockDetail.setWpqdid(UUID.randomUUID().toString());
					instockDetail.setJdsqdid(borrowApplyId);
					instockDetail.setJdsqmxid(jdsqmxid);
					instockDetail.setZt(EffectiveEnum.YES.getId());
					instockDetail.setWhdwid(user.getJgdm());
					instockDetail.setWhrid(user.getId());
					this.instockDetailMapper.insertSelective(instockDetail);
					
					expatriate = new Expatriate();
					expatriate.setId(instockDetail.getWpqdid());
					expatriate.setDprtcode(user.getJgdm());
					expatriate.setJdlx(StatusEnum.CALLIN.getId().shortValue());
					expatriate.setWpdxid(jddx);
					expatriate.setBjid(hcMainData.getId());
					expatriate.setBjh(hcMainData.getBjh());
					expatriate.setSn(sn);
					expatriate.setPch(pch);
					expatriate.setKcsl(kcslNumber);
					expatriate.setGhsl(new BigDecimal(0).setScale(2));
					expatriate.setZt(EffectiveEnum.YES.getId());
					expatriate.setWpsj(new Date());
					
					this.expatriateMapper.insertSelective(expatriate);
					
					//保存履历
					MaterialHistory materialHistory = new MaterialHistory();
					materialHistory.setId(UUID.randomUUID().toString());
					materialHistory.setKcid(UUID.randomUUID().toString());
					materialHistory.setCkid(storage.getMainid());
					materialHistory.setCklb(storage.getCklb());
					materialHistory.setCkh(storage.getCkh());
					materialHistory.setCkmc(storage.getStore().getCkmc());
					materialHistory.setKwid(storage.getId());
					materialHistory.setKwh(storage.getKwh());
					materialHistory.setSn(sn);
					materialHistory.setPch(pch);
					materialHistory.setKcsl(kcslNumber);
					materialHistory.setKccb(kccbNumber);
					materialHistory.setZt(StockStatusEnum.RECEIVED.getId());
					materialHistory.setDprtcode(user.getJgdm());
					materialHistory.setBjid(hcMainData.getId());
					materialHistory.setBjh(hcMainData.getBjh());
					materialHistory.setZwms(hcMainData.getZwms());
					materialHistory.setYwms(hcMainData.getYwms());
					materialHistory.setJldw(hcMainData.getJldw());
					materialHistory.setDjsl(new BigDecimal(0).setScale(2));
					materialHistory.setRksj(rksjDate);
					materialHistory.setRkbmid(rkbmid);
					materialHistory.setRkrid(rukid);
					materialHistory.setXh(hcMainData.getXingh());
					materialHistory.setGg(hcMainData.getGg());
					this.materialHistoryMapper.insertSelective(materialHistory);
					//保存入库库存
					InstockDetailStock instockDetailStock = new InstockDetailStock();
					instockDetailStock.setId(UUID.randomUUID().toString());
					instockDetailStock.setMainid(instockDetail.getId());
					instockDetailStock.setKcllid(materialHistory.getId());
					instockDetailStock.setZt(EffectiveEnum.YES.getId());
					instockDetailStock.setWhdwid(user.getJgdm());
					instockDetailStock.setWhrid(user.getId());
					this.instockDetailStockMapper.insertSelective(instockDetailStock);
					//保存外派清单从表
					ExpatriateDetail expatriateDetail = new ExpatriateDetail();
					expatriateDetail.setId(UUID.randomUUID().toString());
					expatriateDetail.setMainid(instockDetail.getWpqdid());
					expatriateDetail.setKcllid(materialHistory.getId());
					expatriateDetail.setDprtcode(user.getJgdm());
					expatriateDetail.setSjlx(1);//TODO 改为枚举
					expatriateDetail.setGldjlx(1);//TODO 改为枚举
					expatriateDetail.setDjid(instockDetailStock.getId());
					this.expatriateDetailMapper.insertSelective(expatriateDetail);
					
					//保存库存
					Stock stock = materialHistory.toStock();
					this.stockMapper.insertSelective(stock);
					
					this.materialRecService.writeStockRec(stock.getId(), czls, instockId, rkdh, StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE,
							InstockTypeEnum.TYPE3.getName(),"",rkdh, borrowApply.getSqdh(), stock.getKcsl());
					//保存检验单
					QualityCheck qualityCheck = stock.toQualityCheck();
					qualityCheck.setId(UUID.randomUUID().toString());
					qualityCheck.setRkdid(instockId);
					qualityCheck.setRkdh(rkdh);
					this.qualityCheckMapper.insertSelective(qualityCheck);
				}
			}
			//将借入申请单改为完成
			BorrowApply newBorrowApply = new BorrowApply();
			newBorrowApply.setId(borrowApplyId);
			newBorrowApply.setZt(ReserveStatusEnum.FINISH.getId());
			this.borrowApplyMapper.updateByPrimaryKeySelective(newBorrowApply);
			
		}
		
		Instock instock = new Instock();
		instock.setId(instockId);
		instock.setDprtcode(user.getJgdm());
		instock.setRklx(InstockTypeEnum.TYPE3.getId());
		instock.setRkdh(rkdh);
		instock.setRukid(rukid);
		instock.setRkbmid(rkbmid);
		instock.setRksj(rksjDate);
		instock.setZt(RequisitionStatusEnum.SUBMITED.getId());
		instock.setZdrid(user.getId());
		instock.setZdbmid(user.getBmdm());
		instock.setFjzch(fjzch);
		instock.setSqrid(sqrid);
		instock.setSqbmid(sqbmid);
		instock.setSqsj2(sqsjDate);
		instock.setJddxid(jddx);
		instock.setJdfzr(jdfzr);
		instock.setBz(bz);
		instock.setXgdjid(borrowApplyId);
		this.instockMapper.insertSelective(instock);
		
	}
	
	/**
	 * 提交手工入库
	 * @param map
	 * @throws BusinessException 
	 */
	public void saveHandworkSubmit(Map<String, Object> map) throws BusinessException{
		
		String rukid = (String)map.get("rukid");
		String rkbmid = (String)map.get("rkbmid");
		String sqrid = (String)map.get("sqrid");
		String sqbmid = (String)map.get("sqbmid");
		String sqsj2 = (String)map.get("sqsj2");
		String rksj = (String)map.get("rksj");
		String fjzch = (String)map.get("fjzch");
		String bz = (String)map.get("bz");
		
		Date rksjDate = null;
		if(StringUtils.isNotBlank(rksj)){
			try {
				rksjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, rksj);
			} catch (ParseException e) {
				logger.error("入库日期格式转换错误", e);
				throw new BusinessException("入库日期格式转换错误");
			}
		}else{
			throw new BusinessException("入库日期格式不能为空");
		}
		
		Date sqsjDate = null;
		if(StringUtils.isNotBlank(sqsj2)){
			try {
				sqsjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, sqsj2);
			} catch (ParseException e) {
				logger.error("接收日期格式转换错误", e);
				throw new BusinessException("接收日期格式转换错误");
			}
		}else{
			throw new BusinessException("接收日期格式不能为空");
		}
		
		User user = ThreadVarUtil.getUser();
		
		//预生成入库单ID
		String instockId = UUID.randomUUID().toString();
		String rkdh;//入库单号
		try {
			rkdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		//业务操作流水号，用于写入业务历史记录
		String czls = UUID.randomUUID().toString();
		
		List<Map<String, Object>> detailList = (List<Map<String, Object>>)map.get("detail");
		
		//开始保存业务
		
		Map<String, Storage> storageMap = new HashMap<String, Storage>();//缓存库位<kwid, 仓库>
		Storage storage = null;
		Map<String, HCMainData> hcMainDataMap = new HashMap<String, HCMainData>();//缓存部件<bjid, 部件>
		HCMainData hcMainData = null;
		InstockDetail instockDetail = null;

		if(null != detailList && !detailList.isEmpty()){
			for (Map<String, Object> detailMap : detailList) {
				
				String bjid = (String)detailMap.get("bjid");//部件ID
				String sn = (String)detailMap.get("sn");//序列号
				String pch = (String)detailMap.get("pch");//批次号
				String kwid = (String)detailMap.get("kwid");
				String kccb = (String)detailMap.get("kccb");//库存成本
				BigDecimal kccbNumber = null;
				if(StringUtils.isNotBlank(kccb)){
					try{
						kccbNumber = new BigDecimal(kccb).setScale(2);
					}catch(Exception e){
						throw new BusinessException("库存成本格式转换错误");
					}
				}else{
					throw new BusinessException("库存成本不能为空");
				}
				String kcsl = (String)detailMap.get("kcsl");//入库数量
				BigDecimal kcslNumber = null;
				if(StringUtils.isNotBlank(kccb)){
					try{
						kcslNumber = new BigDecimal(kcsl).setScale(2);
					}catch(Exception e){
						throw new BusinessException("入库数量格式转换错误");
					}
				}else{
					throw new BusinessException("入库数量不能为空");
				}
				
				
				//获取部件信息
				if(hcMainDataMap.containsKey(bjid)){
					hcMainData = hcMainDataMap.get(bjid);
				}else{
					hcMainData = this.hCMainDataMapper.selectById(bjid);
					hcMainDataMap.put(bjid, hcMainData);
				}
				if(hcMainData == null){
					throw new BusinessException("航材主数据不存在");
				}
				
				//验证序列号重复性
				if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
					if(StringUtils.isBlank(sn)){
						throw new BusinessException("序列号不能为空");
					}
					if(!this.materialUtilService.validateSnUniqueness(user.getJgdm(), hcMainData.getBjh(), sn)){
						throw new BusinessException("序列号"+sn+"已存在");
					}
				}
				
				
				//保存入库航材
				instockDetail = new InstockDetail();
				instockDetail.setId(UUID.randomUUID().toString());
				instockDetail.setMainid(instockId);
				instockDetail.setBjid(hcMainData.getId());
				instockDetail.setBjh(hcMainData.getBjh());
				instockDetail.setSl(kcslNumber);
				instockDetail.setZt(EffectiveEnum.YES.getId());
				instockDetail.setWhdwid(user.getJgdm());
				instockDetail.setWhrid(user.getId());
				this.instockDetailMapper.insertSelective(instockDetail);
				
				//获取仓库库位信息
				if(StringUtils.isNotBlank(kwid)){
					if(storageMap.containsKey(kwid)){
						storage = storageMap.get(kwid);
					}else{
						storage = this.storageMapper.selectByIdWithRel(kwid);
						storageMap.put(kwid, storage);
					}
				}else{
					storage = null;
				}
				if(storage == null){
					throw new BusinessException("库位不存在");
				}
				
				//保存履历
				MaterialHistory materialHistory = new MaterialHistory();
				materialHistory.setId(UUID.randomUUID().toString());
				materialHistory.setKcid(UUID.randomUUID().toString());
				materialHistory.setCkid(storage.getMainid());
				materialHistory.setCklb(storage.getCklb());
				materialHistory.setCkh(storage.getCkh());
				materialHistory.setCkmc(storage.getStore().getCkmc());
				materialHistory.setKwid(storage.getId());
				materialHistory.setKwh(storage.getKwh());
				materialHistory.setSn(sn);
				materialHistory.setPch(pch);
				materialHistory.setKcsl(kcslNumber);
				materialHistory.setKccb(kccbNumber);
				materialHistory.setZt(StockStatusEnum.RECEIVED.getId());
				materialHistory.setDprtcode(user.getJgdm());
				materialHistory.setBjid(hcMainData.getId());
				materialHistory.setBjh(hcMainData.getBjh());
				materialHistory.setZwms(hcMainData.getZwms());
				materialHistory.setYwms(hcMainData.getYwms());
				materialHistory.setJldw(hcMainData.getJldw());
				materialHistory.setDjsl(new BigDecimal(0).setScale(2));
				materialHistory.setRksj(rksjDate);
				materialHistory.setRkbmid(rkbmid);
				materialHistory.setRkrid(rukid);
				materialHistory.setXh(hcMainData.getXingh());
				materialHistory.setGg(hcMainData.getGg());
				this.materialHistoryMapper.insertSelective(materialHistory);
				//保存入库库存
				InstockDetailStock instockDetailStock = new InstockDetailStock();
				instockDetailStock.setId(UUID.randomUUID().toString());
				instockDetailStock.setMainid(instockDetail.getId());
				instockDetailStock.setKcllid(materialHistory.getId());
				instockDetailStock.setZt(EffectiveEnum.YES.getId());
				instockDetailStock.setWhdwid(user.getJgdm());
				instockDetailStock.setWhrid(user.getId());
				this.instockDetailStockMapper.insertSelective(instockDetailStock);
				
				//保存库存
				Stock stock = materialHistory.toStock();
				this.stockMapper.insertSelective(stock);
				
				//保存库存表的修改历史记录
				this.materialRecService.writeStockRec(stock.getId(), czls, instockId, rkdh, StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, 
						InstockTypeEnum.TYPE0.getName(),"",rkdh,"手工制单",stock.getKcsl());
				
				//保存检验单
				QualityCheck qualityCheck = stock.toQualityCheck();
				qualityCheck.setId(UUID.randomUUID().toString());
				qualityCheck.setRkdid(instockId);
				qualityCheck.setRkdh(rkdh);
				this.qualityCheckMapper.insertSelective(qualityCheck);
			}
		}
		Instock instock = new Instock();
		instock.setId(instockId);
		instock.setDprtcode(user.getJgdm());
		instock.setRklx(InstockTypeEnum.TYPE0.getId());
		instock.setRkdh(rkdh);
		instock.setRukid(rukid);
		instock.setRkbmid(rkbmid);
		instock.setRksj(rksjDate);
		instock.setZt(RequisitionStatusEnum.SUBMITED.getId());
		instock.setZdrid(user.getId());
		instock.setZdbmid(user.getBmdm());
		instock.setFjzch(fjzch);
		instock.setSqrid(sqrid);
		instock.setSqbmid(sqbmid);
		instock.setSqsj2(sqsjDate);
		instock.setBz(bz);
		this.instockMapper.insertSelective(instock);
		
	}
	
	/**
	 * 分页查询 历史 入库单
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryPage(BaseEntity entity) throws BusinessException{
		
		String id = (String)entity.getParamsMap().get("id");
		
		entity.getParamsMap().remove("id");
		
		PageHelper.startPage(entity.getPagination());
		List<Instock> list = this.instockMapper.selectPage(entity);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BaseEntity entity1 = new BaseEntity();
					Map<String, Object> paramsMap = new HashMap<String, Object>(1);
					paramsMap.put("id", id);
					entity1.setParamsMap(paramsMap);
					List<Instock> newRecordList = this.instockMapper.selectPage(entity1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, entity.getPagination());
		}else{
			List<Instock> newRecordList = new ArrayList<Instock>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BaseEntity entity1 = new BaseEntity();
				Map<String, Object> paramsMap = new HashMap<String, Object>(1);
				paramsMap.put("id", id);
				entity1.setParamsMap(paramsMap);
				newRecordList = this.instockMapper.selectPage(entity1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, entity.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, entity.getPagination());
		}
		
	}
	
	/**
	 * 撤销入库单
	 * @param id 入库单ID
	 * @throws BusinessException 
	 */
	public void saveCancel(String id) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		
		//查询入库单
		Instock instock = this.instockMapper.selectByPrimaryKey(id);
		
		//检查入库单状态
		if(instock == null){
			throw new BusinessException("入库单不存在");
		}
		if(RequisitionStatusEnum.SUBMITED.getId().intValue() != instock.getZt().intValue()){
			throw new BusinessException("入库单当前状态不允许撤销");
		}
		if(!user.getJgdm().equals(instock.getDprtcode())){
			throw new BusinessException("用户所属组织机构不允许对此入库单进行撤销操作");
		}
		
		if(instock.getRklx().intValue() == InstockTypeEnum.TYPE1.getId().intValue()){
			this.cancelPurchase(instock);
		}else if(instock.getRklx().intValue() == InstockTypeEnum.TYPE2.getId().intValue()){
			this.cancelRepair(instock);
		}else if(instock.getRklx().intValue() == InstockTypeEnum.TYPE3.getId().intValue()){
			this.cancelBorrow(instock);
		}else if(instock.getRklx().intValue() == InstockTypeEnum.TYPE4.getId().intValue()){
			this.cancelLendReturn(instock);
		}else if(instock.getRklx().intValue() == InstockTypeEnum.TYPE0.getId().intValue()){
			this.cancelHandwork(instock);
		}else{
			throw new BusinessException("入库类型异常");
		}
		
		//作废检验单
		this.qualityCheckMapper.discardByInstockId(instock.getId());
		//更改入库单状态
		Instock instock1 = new Instock();
		instock1.setId(instock.getId());
		instock1.setZt(11);//TODO 枚举
		this.instockMapper.updateByPrimaryKeySelective(instock1);
	}
	
	/**
	 * 撤销采购入库
	 * @param instock
	 * @throws BusinessException 
	 */
	private void cancelPurchase(Instock instock) throws BusinessException{
		//查询入库航材库存
		List<InstockDetailStock> list = this.instockDetailStockMapper.selectByInstockId(instock.getId());
		if(null == list || list.isEmpty()){
			return;
		}
		ContractDetail contractDetail = null;
		//循环撤销入库信息
		for (InstockDetailStock instockDetailStock : list) {
			//查询库存
			Stock stock = this.stockMapper.queryById(instockDetailStock.getMaterialHistory().getKcid());
			//检验库存状态
			if(stock == null || stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == -1){
				throw new BusinessException("入库单入库航材库足不以进行撤销操作！");
			}
			//撤回库存
			if(stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == 0){
				this.stockMapper.delete(stock);
			}else{
				stock.setKcsl(stock.getKcsl().subtract(instockDetailStock.getMaterialHistory().getKcsl()).setScale(2));
				this.stockMapper.updateByPrimaryKeySelective(stock);
			}
			//TODO 写历史表
			contractDetail = new ContractDetail();
			contractDetail.setId(instockDetailStock.getInstockDetail().getHtmxid());
			contractDetail.setDhsl(instockDetailStock.getMaterialHistory().getKcsl());
			//回退合同到货数量
			this.contractDetailMapper.updateDhslSub(contractDetail);
			//更改合同到货状态
			this.instockDetailMapper.cancelContractZt(instockDetailStock.getMainid());

		}
	}
	
	/**
	 * 撤销送修入库
	 * @param instock
	 * @throws BusinessException 
	 */
	private void cancelRepair(Instock instock) throws BusinessException{
		//查询入库航材库存
		//查询入库航材库存
		List<InstockDetailStock> list = this.instockDetailStockMapper.selectByInstockId(instock.getId());
		if(null == list || list.isEmpty()){
			return;
		}
		ContractDetail contractDetail = null;
		Expatriate expatriate = null;
		//循环撤销入库信息
		for (InstockDetailStock instockDetailStock : list) {
			//查询库存
			Stock stock = this.stockMapper.queryById(instockDetailStock.getMaterialHistory().getKcid());
			//检验库存状态
			if(stock == null || stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == -1){
				throw new BusinessException("入库单入库航材库足不以进行撤销操作！");
			}
			//撤回库存
			if(stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == 0){
				this.stockMapper.delete(stock);
			}else{
				stock.setKcsl(stock.getKcsl().subtract(instockDetailStock.getMaterialHistory().getKcsl()).setScale(2));
				this.stockMapper.updateByPrimaryKeySelective(stock);
			}
			//TODO 写历史表
			//回退外派清单
			//删除外派清单对应的明细
			this.expatriateDetailMapper.deleteByMainIdAndDjid(instockDetailStock.getInstockDetail().getWpqdid(), instockDetailStock.getId());
			//回退外派清单归还数量
			expatriate = new Expatriate();
			expatriate.setId(instockDetailStock.getInstockDetail().getWpqdid());
			expatriate.setGhsl(instockDetailStock.getMaterialHistory().getKcsl());
			this.expatriateMapper.updateGhslSub(expatriate);
			
			contractDetail = new ContractDetail();
			contractDetail.setId(instockDetailStock.getInstockDetail().getHtmxid());
			contractDetail.setDhsl(instockDetailStock.getMaterialHistory().getKcsl());
			//回退合同到货数量
			this.contractDetailMapper.updateDhslSub(contractDetail);
			//更改合同到货状态
			this.instockDetailMapper.cancelContractZt(instockDetailStock.getMainid());
		}
	}
	
	/**
	 * 撤销借入入库
	 * @param instock
	 * @throws BusinessException 
	 */
	private void cancelBorrow(Instock instock) throws BusinessException{
		//查询入库航材库存
		List<InstockDetailStock> list = this.instockDetailStockMapper.selectByInstockId(instock.getId());
		if(null == list || list.isEmpty()){
			return;
		}
		String czls = UUID.randomUUID().toString();
		//循环撤销入库信息
		for (InstockDetailStock instockDetailStock : list) {
			//查询库存
			Stock stock = this.stockMapper.queryById(instockDetailStock.getMaterialHistory().getKcid());
			//检验库存状态
			if(stock == null || stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == -1){
				throw new BusinessException("入库单入库航材库足不以进行撤销操作！");
			}
			
			//回退外派清单
			
			//查询外派清单
			Expatriate expatriate = this.expatriateMapper.selectByPrimaryKey(instockDetailStock.getInstockDetail().getWpqdid());
			if(expatriate != null){
				//检验外派清单是否有归还记录
				if(expatriate.getGhsl().intValue() == 0){
					//将外派清单失效
					expatriate.setZt(EffectiveEnum.NO.getId());
					expatriateMapper.updateByPrimaryKeySelective(expatriate);
					
				}else{
					throw new BusinessException("该入库单已有归还记录不能进行撤销操作！");
				}
			}
			
			//撤回库存
			if(stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == 0){ 
				this.stockMapper.delete(stock);
				this.materialRecService.writeStockRec(stock.getId(), czls, instock.getId(), instock.getRkdh(), StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.DELETE, 
						InstockTypeEnum.TYPE3.getName(), "撤销", instock.getRkdh(),"", stock.getKcsl());
				
			}else{
				stock.setKcsl(stock.getKcsl().subtract(instockDetailStock.getMaterialHistory().getKcsl()).setScale(2));
				this.stockMapper.updateByPrimaryKeySelective(stock);
				this.materialRecService.writeStockRec(stock.getId(), czls, instock.getId(), instock.getRkdh(), StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.UPDATE, 
						InstockTypeEnum.TYPE3.getName(), "撤销", instock.getRkdh(),"", instockDetailStock.getMaterialHistory().getKcsl());
			}
			
			//将借入申请单改为提交
			if(StringUtils.isNotBlank(instock.getXgdjid())){
				BorrowApply newBorrowApply = new BorrowApply();
				newBorrowApply.setId(instock.getXgdjid());
				newBorrowApply.setZt(ReserveStatusEnum.SUBMIT.getId());
				this.borrowApplyMapper.updateByPrimaryKeySelective(newBorrowApply);
			}
			
		}
	}
	
	/**
	 * 撤销借出归还入库
	 * @param instock
	 * @throws BusinessException 
	 */
	private void cancelLendReturn(Instock instock) throws BusinessException{
		//查询入库航材库存
		List<InstockDetailStock> list = this.instockDetailStockMapper.selectByInstockId(instock.getId());
		if(null == list || list.isEmpty()){
			return;
		}
		Expatriate expatriate = null;
		//循环撤销入库信息
		for (InstockDetailStock instockDetailStock : list) {
			//查询库存
			Stock stock = this.stockMapper.queryById(instockDetailStock.getMaterialHistory().getKcid());
			//检验库存状态
			if(stock == null || stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == -1){
				throw new BusinessException("入库单入库航材库足不以进行撤销操作！");
			}
			
			//撤回库存
			if(stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == 0){
				this.stockMapper.delete(stock);
			}else{
				stock.setKcsl(stock.getKcsl().subtract(instockDetailStock.getMaterialHistory().getKcsl()).setScale(2));
				this.stockMapper.updateByPrimaryKeySelective(stock);
			}
			//TODO 写历史表
			//回退外派清单
			//删除外派清单对应的明细
			this.expatriateDetailMapper.deleteByMainIdAndDjid(instockDetailStock.getInstockDetail().getWpqdid(), instockDetailStock.getId());
			//回退外派清单归还数量
			expatriate = new Expatriate();
			expatriate.setId(instockDetailStock.getInstockDetail().getWpqdid());
			expatriate.setGhsl(instockDetailStock.getMaterialHistory().getKcsl());
			this.expatriateMapper.updateGhslSub(expatriate);
			
		}
	}
	
	/**
	 * 撤销手工入库
	 * @param instock
	 * @throws BusinessException 
	 */
	private void cancelHandwork(Instock instock) throws BusinessException{
		//查询入库航材库存
		List<InstockDetailStock> list = this.instockDetailStockMapper.selectByInstockId(instock.getId());
		if(null == list || list.isEmpty()){
			return;
		}
		//循环撤销入库信息
		for (InstockDetailStock instockDetailStock : list) {
			//查询库存
			Stock stock = this.stockMapper.queryById(instockDetailStock.getMaterialHistory().getKcid());
			//检验库存状态
			if(stock == null || stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == -1){
				throw new BusinessException("入库单入库航材库足不以进行撤销操作！");
			}
			//撤回库存
			if(stock.getKcsl().compareTo(instockDetailStock.getMaterialHistory().getKcsl()) == 0){
				this.stockMapper.delete(stock);
			}else{
				stock.setKcsl(stock.getKcsl().subtract(instockDetailStock.getMaterialHistory().getKcsl()).setScale(2));
				this.stockMapper.updateByPrimaryKeySelective(stock);
			}
			//TODO 写历史表
			
		}
	}
	
	
	/**
	 * 根据外飞行队出库单 保存借出归还入库
	 * @param map
	 * @return 
	 * @throws BusinessException 
	 */
	public void saveLendByOutstock(Map<String, Object> map) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		//接收并验证参数
		
		String rksj = (String)map.get("rksj");//入库时间
		Date rksjDate = null;
		if(StringUtils.isNotBlank(rksj)){
			try {
				rksjDate = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, rksj);
			} catch (ParseException e) {
				throw new BusinessException("数据异常", e);
			}
		}
		String rkrid = (String)map.get("rkrid");//入库人ID
		String rkbmid = (String)map.get("rkbmid");//入库部门ID
		String sqrid = (String)map.get("sqrid");//归还人
		String sqbmid = (String)map.get("sqbmid");//归还人部门
		String jdfzr = (String)map.get("jdfzr");//归还人，借调方负责人
		String fxdid = (String)map.get("jddxid");//外派对象
		String outstockId = (String)map.get("outstockId");//外飞行队出库单ID
		
		
		if(StringUtils.isBlank(fxdid)){
			throw new BusinessException("数据异常");
		}
		//根据借调对象编号查询借调对象
		Secondment secondment = this.secondmentMapper.selectByBH(user.getJgdm(), fxdid);
		if(secondment == null){
			throw new BusinessException("借调对象不存在");
		}
		List<Map<String, Object>> detailStockList = (List<Map<String, Object>>)map.get("detailStock");
		if(detailStockList == null || detailStockList.isEmpty()){
			throw new BusinessException("入库航材信息错误"); 
		} 
		
		List<String> bjIdList = new ArrayList<String>();
		List<String> bjhList = new ArrayList<String>();
		for (Map<String, Object> stockMap : detailStockList) {
			if(stockMap.containsKey("bjid")){
				bjIdList.add((String)stockMap.get("bjid"));
			}else if(stockMap.containsKey("bjh")){
				bjhList.add((String)stockMap.get("bjh"));
			}
		}
		if(bjIdList.isEmpty() && bjhList.isEmpty()){
			throw new BusinessException("部件不存在");
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if(!bjIdList.isEmpty()){
			paramsMap.put("bjIdList", bjIdList);
		}
		if(!bjhList.isEmpty()){
			paramsMap.put("bjhList", bjhList);
		}
		paramsMap.put("dprtcode", user.getJgdm());

		List<HCMainData> hCMainDataList = hCMainDataMapper.selectBybjs(paramsMap);
		
		if(hCMainDataList == null || hCMainDataList.isEmpty()){
			throw new BusinessException("部件不存在，请选择借出航材");
		}
		
		Map<String, HCMainData> bjhMap = new HashMap<String, HCMainData>(hCMainDataList.size());
		Map<String, HCMainData> bjIdMap = new HashMap<String, HCMainData>(hCMainDataList.size());
		for (HCMainData hcMainData : hCMainDataList) {
			bjhMap.put(hcMainData.getBjh(), hcMainData);
			bjIdMap.put(hcMainData.getId(), hcMainData);
		}
		
		List<Stock> stockList = null;
		Stock stock = null;
		
		Map<String, Storage> storageMap = new HashMap<String, Storage>();//<kwid, Storage> 用于缓存仓库信息
		Storage storage = null;
		HCMainData hcMainData = null;
		
		String instockId = UUID.randomUUID().toString();
		String rkdh;
		try {
			rkdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		Instock instock = new Instock();
		instock.setId(instockId);
		instock.setRkdh(rkdh);
		
		//保存库存信息及检验单信息
		for (Map<String, Object> stockMap : detailStockList) {
			stockList = new ArrayList<Stock>();
			if(stockMap.containsKey("bjid")){//有选择借出部件ID
				hcMainData = bjIdMap.get((String)stockMap.get("bjid"));
			}else{//未选择借出部件id，使用bjh
				hcMainData = bjhMap.get((String)stockMap.get("bjh"));
			}
			if(hcMainData == null){
				throw new BusinessException("部件号不存在");
			}
			
			String kwid = (String)stockMap.get("kwid");//库位ID
			String sn = (String)stockMap.get("sn");//序列号
			String pch = (String)stockMap.get("pch");//批次号
			
			BigDecimal kcsl = new BigDecimal((String)stockMap.get("kcsl")).setScale(2);//库存数量
			
			String kccb = (String)stockMap.get("kccb");//库存成本
			BigDecimal kccbNumber = null;
			if(StringUtils.isNotBlank(kccb)){
				kccbNumber = new BigDecimal(kccb);
			}
			
			//获取仓库库位信息
			if(StringUtils.isNotBlank(kwid)){
				if(storageMap.containsKey(kwid)){
					storage = storageMap.get(kwid);
				}else{
					storage = this.storageMapper.selectByIdWithRel(kwid);
					storageMap.put(kwid, storage);
				}
			}else{
				storage = null;
			}
			if(storage == null){
				throw new BusinessException("库位不存在");
			}
			
			//保存库存信息
			stock = new Stock();
			stock.setId(UUID.randomUUID().toString());
			stock.setCkid(storage.getMainid());
			stock.setCklb(storage.getCklb());
			stock.setCkh(storage.getCkh());
			stock.setCkmc(storage.getStore().getCkmc());
			stock.setKwid(storage.getId());
			stock.setKwh(storage.getKwh());
			stock.setSn(sn);
			stock.setPch(pch);
			stock.setKcsl(kcsl);
			stock.setKccb(kccbNumber);
			stock.setZt(StockStatusEnum.RECEIVED.getId());
			stock.setDprtcode(user.getJgdm());
			stock.setBjid(hcMainData.getId());
			stock.setBjh(hcMainData.getBjh());
			stock.setZwms(hcMainData.getZwms());
			stock.setYwms(hcMainData.getYwms());
			stock.setJldw(hcMainData.getJldw());
			stock.setDjsl(new BigDecimal(0));
			stock.setRksj(rksjDate);
			stock.setRkbmid(rkbmid);
			stock.setRkrid(rkrid);
			stock.setXh(hcMainData.getXingh());
			stock.setGg(hcMainData.getGg());
			this.stockMapper.insertSelective(stock);
			
			stockList.add(stock);
			//增加检验单
			QualityCheck qualityCheck = stock.toQualityCheck();
			qualityCheck.setId(UUID.randomUUID().toString());
			qualityCheck.setRkdid(instockId);
			qualityCheck.setRkdh(rkdh);
			this.qualityCheckMapper.insertSelective(qualityCheck);
			
			List<Expatriate> expatriateList = this.expatriateService.queryListLend(user.getJgdm(), secondment.getId(), hcMainData.getId(), stock.getKcsl());
			
			//处理航材归还以及入库单航材信息
			for (Expatriate expatriate : expatriateList) {
				BigDecimal dghsl = expatriate.getDghsl();
				//保存归还记录及入库从表记录
				this.saveLendDetail(expatriate, stockList, instock, user);
				//修改外派清单已归还数据
				expatriate.setGhsl(expatriate.getGhsl().add(dghsl).setScale(2));
				this.expatriateMapper.updateByPrimaryKeySelective(expatriate);
			}
		}
		
		//保存入库单
		instock.setDprtcode(user.getJgdm());
		instock.setRklx(InstockTypeEnum.TYPE4.getId());
		instock.setRkbmid(rkbmid);
		instock.setRukid(rkrid);
		instock.setRksj(rksjDate);
		instock.setZt(RequisitionStatusEnum.SUBMITED.getId());
		instock.setZdbmid(user.getBmdm());
		instock.setZdrid(user.getId());
		instock.setSqbmid(sqbmid);
		instock.setSqrid(sqrid);
		instock.setJddxid(secondment.getId());
		instock.setJdfzr(jdfzr);
		instock.setBz((String)map.get("bz"));
		instock.setSqsj2(rksjDate);
		instock.setXgdjid(outstockId);
		this.instockMapper.insertSelective(instock);
		
	}

	@Override
	public Map<String, Object> queryDetailPage(InstockDetailStock record) throws RuntimeException {
		
		PageHelper.startPage(record.getPagination());
		List<InstockDetailStock> list = this.instockDetailStockMapper.queryPageList(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
		
	}
	
	public Instock queryInstockById(String id){
		return this.instockMapper.selectById(id);
	}
	
	public List<InstockDetailStock> queryInstockStockById(String id){
		return this.instockDetailStockMapper.selectByInstockId(id);
	}
	
}
