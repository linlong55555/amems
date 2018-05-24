package com.eray.thjw.aerialmaterial.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.ComponentHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.OutFieldStockMapper;
import com.eray.thjw.aerialmaterial.dao.StockFreezeHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.po.ComponentHistory;
import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.QualityCheck;
import com.eray.thjw.aerialmaterial.po.ScrapList;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.StockFreezeHistory;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.po.ToolBorrowRecord;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.HasScrappedListService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.QualityCheckService;
import com.eray.thjw.aerialmaterial.service.ScrapListService;
import com.eray.thjw.aerialmaterial.service.StockFreezeHistoryService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.StoreInnerSearchServcie;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.ContractInfoMapper;
import com.eray.thjw.material2.dao.StockHistoryMapper;
import com.eray.thjw.material2.po.ContractInfo;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptInfo;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.service.InstallationListEffectService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.MaterialToolSecondTypeEnum;
import enu.MaterialType2Enum;
import enu.MaterialTypeEnum;
import enu.SaiBongEnum;
import enu.SupervisoryLevelEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.FreezeBusinessTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockHistoryOperationEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.aerialmaterial.StockTypeEnum;
import enu.material2.ContractMgntTypeEnum;
import enu.material2.ReceiptTypeEnum;
import enu.material2.StockHistoryTypeEnum;

@Service
public class OutFieldStockServiceImpl implements OutFieldStockService {
	private static String SHOUGONG="手工制单";
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private OutFieldStockMapper outFieldStockMapper;

	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private ScrapListService scrapListService;
	
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private HasScrappedListService hasScrappedListService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private QualityCheckService qualityCheckService;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private StockFreezeHistoryMapper StockFreezeHistoryMapper;
	
	@Resource
	private StockFreezeHistoryService stockFreezeHistoryService;
	
	@Resource
	private ContractInfoMapper contractInfoMapper;
	
	@Resource
	private StoreInnerSearchServcie storeInnerSearchService;
	
	@Resource
	private ComponentCertificateService componentCertificateService;
	
	@Resource
	private ComponentHistoryMapper componentHistoryMapper;
	
	@Resource
	private InstallationListEffectService installationListEffectService;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private StockHistoryMapper stockHistoryMapper;
	
	@Resource
	private ComponentMapper componentMapper;
	
	@Resource
	private MaterialBatchInfoService materialBatchInfoService;
	
	@Resource
	private StoreInnerSearchMapper storeInnerSearchMapper;
	
	@Override
	public void insert(OutFieldStock outFieldStock) {
		outFieldStockMapper.insertSelective(outFieldStock);
	}
	

	/**
	 * 根据条件查询外场库存
	 */
	@Override
	public Map<String, Object> queryPageByParam(OutFieldStock outFieldStock) {
		PageHelper.startPage(outFieldStock.getPagination());
		List<OutFieldStock> list = outFieldStockMapper.queryByParam(outFieldStock);
		return PageUtil.pack4PageHelper(list, outFieldStock.getPagination());
	}

	@Override
	public OutFieldStock queryByKey(String id) throws RuntimeException {
		return outFieldStockMapper.queryByKey(id);
	}

	@Override
	public void delete(String  id) throws RuntimeException {
		outFieldStockMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void update(OutFieldStock outFieldStock1) throws RuntimeException {
		outFieldStockMapper.updateByPrimaryKeySelective(outFieldStock1);
		
	}

	@Override
	public OutFieldStock queryByParamKey(OutFieldStock outFieldStock)
			throws RuntimeException {
		return outFieldStockMapper.queryByParamKey(outFieldStock);
	}

	@Override
	public List<OutFieldStock> queryAllPageList(OutFieldStock outFieldStock)
			throws RuntimeException {
		return outFieldStockMapper.queryAllPageList(outFieldStock);
	}

	/**
	 * 报废
	 */
	@Override
	public Map<String, Object> remove(OutFieldStock outFieldStock) throws RuntimeException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		BigDecimal kcs=outFieldStock.getKcsl();//报废数量
		
		//根据id查询外场表
		OutFieldStock outFieldStock1=outFieldStockMapper.selectByPrimaryKey(outFieldStock.getId());
		
		//添加报废表
		ScrapList scrapList=new ScrapList();
		UUID uuid2 = UUID.randomUUID();//获取随机的uuid
		String scrapId = uuid2.toString();
		scrapList.setId(scrapId);
		scrapList.setDprtcode(user.getJgdm());
		String lyck;
		try {
			lyck = saibongUtilService.generate(user.getJgdm(),SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}	
		scrapList.setBfdh(lyck);
		scrapList.setBfyy(outFieldStock.getBfyy());
		scrapList.setZt(2);
		scrapList.setBfbmid(user.getJgdm());
		scrapList.setBfrid(user.getId());
		scrapList.setBfsj(new Date());
		scrapList.setBflx(2);
		scrapList.setZdbmid(user.getJgdm());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		try {
			scrapListService.save(scrapList);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		

		UUID czls = UUID.randomUUID();//获取随机的uuid	
		//减去外场库存数，当减去外场库存后库存为0则删除该外场库存数据 -rec-验证库存
		BigDecimal kcsl =outFieldStock1.getKcsl();
		if(kcsl.compareTo(kcs)==-1){
			 returnMap.put("state", "error");
			 returnMap.put("message", "报废数量不能大于外场库存数量!");
			 return returnMap;
		}else{
			 
			 //添加部件库存履历表：库存id=外场虚拟库存id
			 MaterialHistory materialHistory=new MaterialHistory(outFieldStock1);
			 materialHistory.setId(UUID.randomUUID().toString());
			 materialHistory.setKcsl(kcs);	
			 materialHistory.setWhrid(user.getId());
			 materialHistory.setWhsj(new Date());
			 materialHistoryService.insertSelective(materialHistory);
				
			 //添加报废附表
			 HasScrappedList hasScrappedList=new HasScrappedList();
			 hasScrappedList.setId(UUID.randomUUID().toString());
			 hasScrappedList.setMainid(scrapId);
			 hasScrappedList.setKcllid(materialHistory.getId());
			 hasScrappedList.setCkid(outFieldStock.getCkid());
			 hasScrappedList.setKwid(outFieldStock.getKwid());
			 hasScrappedList.setWhdwid(user.getJgdm());
			 hasScrappedList.setWhrid(user.getId());
			 hasScrappedList.setWhsj(new Date());
			 hasScrappedList.setZt(1);
			 try {
				hasScrappedListService.save(hasScrappedList);
			 } catch (BusinessException e) {
				e.printStackTrace();
			 }
				
			 //库存冻结履历中添加冻结库存
			 StockFreezeHistory stockFreezeHistory = new StockFreezeHistory();
			 stockFreezeHistory.setId(UUID.randomUUID().toString());
			 stockFreezeHistory.setKclx(StockTypeEnum.OUTER_STOCK.getId());
			 stockFreezeHistory.setKcid(outFieldStock.getId());
			 stockFreezeHistory.setYwlx(FreezeBusinessTypeEnum.SCRAP.getId());
			 stockFreezeHistory.setYwid(hasScrappedList.getId());
			 stockFreezeHistory.setDjsl(kcs);
			 StockFreezeHistoryMapper.insertSelective(stockFreezeHistory);
			 
			 //刷新库存冻结数量
			 stockFreezeHistoryService.refreshStockFreezeCount(outFieldStock.getId(), StockTypeEnum.OUTER_STOCK);
			 //写入报废日志
			 materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(),"", "", OutfieldRecBizTypeEnum.TYPE3, UpdateTypeEnum.UPDATE, "拆下件报废","",kcs);
		 
		 }
		 
		 returnMap.put("state", "success");
		 return returnMap;
		
	}
	@Override
	public OutFieldStock queryById(String id) throws RuntimeException {
		return outFieldStockMapper.queryById(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OutFieldStock record) {
		return outFieldStockMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(OutFieldStock record) {
		return outFieldStockMapper.insertSelective(record);
	}

	/**
	 * 送修
	 */
	@Override
	public Map<String, Object> senda(OutFieldStock outFieldStock)
			throws RuntimeException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		//根据id查询外场表
		OutFieldStock outFieldStock1=outFieldStockMapper.selectByPrimaryKey(outFieldStock.getId());
		
		String ckid=outFieldStock.getCkid();//仓库id
		Store store=storeService.selectByPrimaryKey(ckid);//根据仓库id查询仓库信息
		String kwid=outFieldStock.getKwid();//库位id
		Storage storage=storeService.selectByIdWithRel(kwid);//根据库位id查询库位信息
		
		//新增库存
		Stock stock =new Stock(outFieldStock1);
		UUID uuid4 = UUID.randomUUID();//获取随机的uuid
		String kcId = uuid4.toString();
		stock.setId(kcId);
		//出库数转化为BigDecimal
		BigDecimal cks=new BigDecimal(1);
		stock.setKcsl(cks);
		stock.setCklb(3);
		stock.setCkid(ckid);
		stock.setCkh(store.getCkh());
		stock.setCkmc(store.getCkmc());
		stock.setKwid(kwid);
		stock.setKwh(storage.getKwh());
		stock.setRksj(outFieldStock.getRksj());
		stock.setRkbmid(user.getJgdm());
		stock.setRkrid(user.getId());
		stockSerivce.insertSelective(stock);
		UUID czls = UUID.randomUUID();//获取随机的uuid	
		//库存历史表-新增
		//commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
		
		
		materialRecService.writeStockRec(stock.getId(), czls.toString(), "", "", StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE,"拆下件送待修库","","",SHOUGONG,cks);
		//外场表信息删除
		outFieldStockMapper.deleteByPrimaryKey(outFieldStock1.getId());
		//commonRecService.write(outFieldStock1.getId(), TableEnum.B_H_003, user.getId(), UpdateTypeEnum.DELETE);
		materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "拆下件送待修库","","",SHOUGONG,cks);
		
		 returnMap.put("state", "success");
		 return returnMap;
	}

	/**
	 * 入库
	 */
	@Override
	public Map<String, Object> add(OutFieldStock outFieldStock)
			throws RuntimeException {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();
		
		BigDecimal kcs=outFieldStock.getKcsl();//报废数量
		String ckid=outFieldStock.getCkid();//仓库id
		Store store=storeService.selectByPrimaryKey(ckid);//根据仓库id查询仓库信息
		String kwid=outFieldStock.getKwid();//库位id
		Storage storage=storeService.selectByIdWithRel(kwid);//根据库位id查询库位信息
		//根据id查询外场表
		OutFieldStock outFieldStock1=outFieldStockMapper.selectByPrimaryKey(outFieldStock.getId());
		
		//减去外场库存数，当减去外场库存后库存为0则删除该外场库存数据 -rec-验证库存
		BigDecimal kcsl =outFieldStock1.getKcsl();
		UUID czls = UUID.randomUUID();//获取随机的uuid	
		 if(kcsl.compareTo(kcs)==-1){
				returnMap.put("state", "error");
				returnMap.put("message", "入库数量不能大于外场库存数量!");
			return returnMap;
		 }else if(kcsl.compareTo(kcs)==0){
			 //当外场库存为0时删除
			 outFieldStockMapper.deleteByPrimaryKey(outFieldStock1.getId());
			 materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "拆下件入库","","",SHOUGONG,kcs);
		 }else{
			 //外场库存数减去报废数
			 BigDecimal kucuns = kcsl.subtract(kcs);   	
			 outFieldStock1.setKcsl(kucuns);
			 outFieldStockMapper.updateByPrimaryKeySelective(outFieldStock1);
			 materialRecService.writeOutfieldRec(outFieldStock.getId(), czls.toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "拆下件入库","","",SHOUGONG,kcs);
		 }
		 
		//新增库存
		Stock stock1 =new Stock(outFieldStock1);
		UUID uuid4 = UUID.randomUUID();//获取随机的uuid
		String kcId = uuid4.toString();
		stock1.setId(kcId);
		stock1.setCkid(ckid);
		stock1.setCklb(store.getCklb());
		stock1.setCkh(store.getCkh());
		stock1.setCkmc(store.getCkmc());
		stock1.setKwid(kwid);
		stock1.setKwh(storage.getKwh());
		stock1.setRksj(outFieldStock.getRksj());
		stock1.setRkbmid(user.getJgdm());
		stock1.setRkrid(user.getId());
		stock1.setZt(2);
		stock1.setKcsl(kcs);
		stockSerivce.insertSelective(stock1);
		//库存历史表-新增
		//commonRecService.write(outFieldStock1.getKcid(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE);
		
		//添加检验单
	    QualityCheck qualityCheck=new QualityCheck(outFieldStock1);
	    UUID uuid5 = UUID.randomUUID();//获取随机的uuid
		String jyId = uuid5.toString();
		qualityCheck.setId(jyId);
		qualityCheck.setKcid(kcId);
		qualityCheck.setCklb(store.getCklb());
		qualityCheck.setCkh(store.getCkh());
		qualityCheck.setCkmc(store.getCkmc());
		qualityCheck.setKwid(kwid);
		qualityCheck.setZt(1);
		qualityCheck.setKcsl(kcs);
		qualityCheck.setKwid(storage.getKwh());
		qualityCheckService.save(qualityCheck);
		
	    returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public void checkEdit(String id) throws RuntimeException {
	
		//根据id查询外场表
		OutFieldStock outFieldStock1=outFieldStockMapper.selectByPrimaryKey(id);
		//验证管理级别
		Integer gljb = outFieldStock1.getGljb();
		if(null == gljb || 3 != gljb){
			throw new RuntimeException("对不起,只有序列号管理下的库存才可以送修!");
		}
	}

	/**
	 * @Description 装上件时 更新外场库存
	 * @CreateTime 2017年10月31日 下午1:40:51
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param bjh 部件号
	 * @param xlh 序列号
	 * @param pch 批次号
	 * @param azsl 安装数量
	 * @return //0：库存id, 1:库存履历
	 */
	public String[] update4InstallComponent(String dprtcode, String bjh, String xlh, String pch, BigDecimal azsl){
		
		String[] kcArray = new String[2];//0：库存id, 1:库存履历
		//查询外场库存
		List<OutFieldStock> list = this.outFieldStockMapper.selectStockByComponent(dprtcode, bjh, xlh, pch);
		//扣除外场库存
		for (OutFieldStock outFieldStock : list) {
			if(azsl.compareTo(BigDecimal.ZERO) == 1){
				BigDecimal kcsl = outFieldStock.getKcsl();//库存数量
				BigDecimal djsl = outFieldStock.getDjsl();//冻结数量
				if(djsl == null){
					djsl = BigDecimal.ZERO;
				}
				kcsl = kcsl.subtract(djsl);//可用数量
				if(kcsl.compareTo(azsl) <= 0){
					if(djsl.compareTo(BigDecimal.ZERO) == 0){
						//删除库存
						this.outFieldStockMapper.deleteByPrimaryKey(outFieldStock.getId());
						MaterialHistory materialHistory = new MaterialHistory(outFieldStock);
						materialHistory.setId(UUID.randomUUID().toString());
						materialHistoryService.insertSelective(materialHistory);
						kcArray[0] = outFieldStock.getId();
						kcArray[1] = materialHistory.getId();
					}else{
						//更新库存为冻结数量
						this.outFieldStockMapper.updateKcslById(outFieldStock.getId(), djsl);
						MaterialHistory materialHistory = new MaterialHistory(outFieldStock);
						materialHistory.setId(UUID.randomUUID().toString());
						materialHistoryService.insertSelective(materialHistory);
						kcArray[0] = outFieldStock.getId();
						kcArray[1] = materialHistory.getId();
					}
					azsl = azsl.subtract(kcsl);
				}else{
					//库存 - 待安装数量
					this.outFieldStockMapper.updateKcslById(outFieldStock.getId(), kcsl.add(djsl).subtract(azsl));
					MaterialHistory materialHistory = new MaterialHistory(outFieldStock);
					materialHistory.setId(UUID.randomUUID().toString());
					materialHistoryService.insertSelective(materialHistory);
					kcArray[0] = outFieldStock.getId();
					kcArray[1] = materialHistory.getId();
					break;
				}
			}
		}
		return kcArray;
	}

	/**
	 * 
	 * @Description 获取库外列表
	 * @CreateTime 2018-3-12 下午4:22:24
	 * @CreateBy 孙霁
	 * @param outFieldStock
	 * @return
	 */
	@Override
	public  Map<String, Object> queryAll(OutFieldStock outFieldStock) {
		PageHelper.startPage(outFieldStock.getPagination());
		List<OutFieldStock> list = outFieldStockMapper.queryAll(outFieldStock);
		return PageUtil.pack4PageHelper(list, outFieldStock.getPagination());
	}

	 /**
	  * 
	  * @Description 导出
	  * @CreateTime 2018-3-15 上午10:59:08
	  * @CreateBy 孙霁
	  * @param outFieldStock
	  * @return
	  */
	@Override
	public List<OutFieldStock> outfieldExportExcel(OutFieldStock outFieldStock) {
		List<OutFieldStock> list = outFieldStockMapper.queryAll(outFieldStock);
		String isTool=outFieldStock.getParamsMap().get("isTool").toString();
		String hclxText="";
		String gljbText="";
		for (OutFieldStock ofs : list) {
			if("true".equals(isTool)&&ofs.getParamsMap().get("hclx_ej")!=null&&ofs.getParamsMap().get("hclx_ej")!=""){
				hclxText=MaterialToolSecondTypeEnum.getName(Integer.valueOf(ofs.getParamsMap().get("hclx_ej").toString()));
			}
			if("false".equals(isTool)&&ofs.getParamsMap().get("hclx")!=null&&ofs.getParamsMap().get("hclx")!=""){
				hclxText=MaterialTypeEnum.getName(Integer.valueOf(ofs.getParamsMap().get("hclx").toString()));

			}
			if(ofs.getParamsMap().get("gljb")!=null&&ofs.getParamsMap().get("gljb")!=""){
				gljbText=SupervisoryLevelEnum.getName(Integer.valueOf(ofs.getParamsMap().get("gljb").toString()));			
			}
			ofs.getParamsMap().put("hclxText", hclxText);
			ofs.getParamsMap().put("gljbText", gljbText);
		}
		return list;
	}

	 /**
	  * 
	  * @Description 在途部件清单导出
	  * @CreateTime 2018-3-15 上午10:59:08
	  * @CreateBy 孙霁
	  * @param outFieldStock
	  * @return
	  */
	public List<ContractInfo> contractInfoExportExcel(ContractInfo contractInfo) {
		List<ContractInfo> list = contractInfoMapper.queryAll(contractInfo);
		for (ContractInfo ci : list) {
			ci.getParamsMap().put("hclxText", MaterialTypeEnum.getName(Integer.valueOf(ci.getParamsMap().get("hclx").toString())));
			ci.getParamsMap().put("htlxText", ContractMgntTypeEnum.getName(Integer.valueOf(ci.getParamsMap().get("htlx").toString())));
		}
		return list;
	}

	 /**
	  * @Description 
	  * @CreateTime 2018-3-19 上午10:44:33
	  * @CreateBy 孙霁
	  * @param id
	  * @return
	  */
	@Override
	public OutFieldStock selectById(String id)throws BusinessException {
		return outFieldStockMapper.selectById(id);
	}

	 /**
	  * 
	  * @Description 根据id查询库存信息
	  * @CreateTime 2018-3-20 下午2:56:13
	  * @CreateBy 孙霁
	  * @param id
	  * @return
	  * @throws BusinessException
	  */
	@Override
	public Map<String, Object> selectKcxqById(String id)
			throws BusinessException {
		Map<String, Object> map= new HashMap<String, Object>();
		//查询库存数据
		OutFieldStock outFieldStock = outFieldStockMapper.selectById(id);
		if(outFieldStock != null){
			map.put("outFieldStock", outFieldStock);
			
			//查询库存履历
			List<StockHistory> stockHistoryList = storeInnerSearchService.queryStoreHistoryByKcid(outFieldStock.getKcid());
			map.put("stockHistoryList", stockHistoryList);
			//如果状态为工具，查询借用归还
			if (outFieldStock.getParamsMap() != null&& MaterialTypeEnum.APPOINTED.getId() == outFieldStock.getParamsMap().get("hclx")) {// 如果是工具
				List<ToolBorrowRecord> toolBorrowRecordList = storeInnerSearchService.getListBykcid(id);
				map.put("toolBorrowRecordList", toolBorrowRecordList);
			}
		}else{
			throw new BusinessException("当前数据不存在，请刷新后再进行操作！");
		}
		return map;
	}

	/**
	  * 
	  * @Description 修改外场库存数据
	  * @CreateTime 2018-3-21 上午11:28:52
	  * @CreateBy 孙霁
	  * @param outFieldStock
	  * @throws BusinessException
	  */
	@Override
	public void updateByWc(OutFieldStock outFieldStock)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		outFieldStock.setWhrid(user.getId());
		outFieldStock.setWhsj(new Date());
		String czls = UUID.randomUUID().toString();
		LogOperationEnum cz = LogOperationEnum.EDIT;
		OutFieldStock oldOutFieldStock = outFieldStockMapper.selectById(outFieldStock.getId());
		if(oldOutFieldStock==null)
			throw new BusinessException("该数据已经被更改,请刷新后再操作");
		//修改b_h_003数据
		outFieldStockMapper.updateByPrimaryKeySelective(outFieldStock);
		//添加历史数据表
		materialRecService.writeOutfieldRec(outFieldStock.getId(), czls, outFieldStock.getId(), null, OutfieldRecBizTypeEnum.TYPE7, UpdateTypeEnum.UPDATE, null);
		 //添加成本价值表数据
		materialBatchInfoService.insertOrUpdate(outFieldStock.getDprtcode(), outFieldStock.getBjh(), 
				outFieldStock.getSn(), outFieldStock.getPch(), outFieldStock.getKccb(), outFieldStock.getBiz(),
				outFieldStock.getJz(), outFieldStock.getJzbz());
		//添加证书信息
		InstallationListEditable record=new InstallationListEditable();
		record.setBjh(outFieldStock.getBjh());
		record.setXlh(outFieldStock.getSn());
		record.setPch(outFieldStock.getPch());
		record.setDprtcode(outFieldStock.getDprtcode());
		record.setCertificates(outFieldStock.getCertificates());
		record.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		componentCertificateService.save(record);
		//根据库存id=b_h2_025.装机清单id来增量处理
		Stock stock=new Stock(outFieldStock);
		storeInnerSearchMapper.batchInsertOrUpdateBj(stock);
		//刷新b_h_010数据 
		InstallationListEditable installationListEditable=new InstallationListEditable();
		installationListEditable.setDprtcode(stock.getDprtcode());
		installationListEditable.setBjh(stock.getBjh());
		installationListEditable.setXlh(stock.getSn());
		installationListEditable.setLlklx(stock.getLlklx());
		installationListEditable.setLlkbh(stock.getLlkbh());
		installationListEditable.setBjgzjl(stock.getBjgzjl());
		installationListEditable.setChucrq(stock.getScrq());
		installationListEditable.setTsn(stock.getTsn());
		installationListEditable.setTso(stock.getTso());
		installationListEditable.setCsn(StringUtils.isEmpty(stock.getCsn())?null:Integer.valueOf(stock.getCsn()));
		installationListEditable.setCso(StringUtils.isEmpty(stock.getCso())?null:Integer.valueOf(stock.getCso()));
		installationListEditable.setCcsj(new Date());
		int updateCount = componentMapper.updateCompnent2Newest(installationListEditable);
		if(updateCount == 0){
			this.componentMapper.insertByInstallList(installationListEditable);
		}
		//更新b_H_017
		MaterialHistory materialHistory = new MaterialHistory(oldOutFieldStock);
		String materialHistoryId = UUID.randomUUID().toString();
		materialHistory.setId(materialHistoryId);
		materialHistoryMapper.insertSelective(materialHistory);
		
		//写入b_h_025  //库存履历
		StockHistory  history=new StockHistory();
		history.setId(UUID.randomUUID().toString());
		history.setDprtcode(stock.getDprtcode());
		history.setCzrbmid(user.getBmdm());
	    history.setCzrid(user.getId());
		history.setCzr(user.getRealname());
		history.setCzzlx(null);
		history.setCzlx(StockHistoryOperationEnum.kcxg.getId());
	    history.setCzsm(StockHistoryOperationEnum.kcxg.getName());
		history.setKcid(stock.getId());
		storeInnerSearchMapper.saveStoreHistory(history);
	}
	//更新b_H_010
	private void insertComponentInfoIfNotExist(OutFieldStock outFieldStock){
		if(StringUtils.isNotBlank(outFieldStock.getSn())){
			
			Component param = new Component();
			param.setJh(outFieldStock.getBjh());
			param.setXlh(outFieldStock.getSn());
			param.setDprtcode(outFieldStock.getDprtcode());
			param.setChucrq(outFieldStock.getScrq());
			param.setTsn(outFieldStock.getTsn());
			param.setTso(outFieldStock.getTso());
			if(outFieldStock.getCsn() != null && !"".equals(outFieldStock.getCsn())){
				param.setCsn(outFieldStock.getCsn() != null?Integer.valueOf(outFieldStock.getCsn()):null);
			}
			if(outFieldStock.getCso() != null && !"".equals(outFieldStock.getCso())){
				param.setCso(outFieldStock.getCso() != null?Integer.valueOf(outFieldStock.getCso()):null);
			}
			param.setWhsj(outFieldStock.getWhsj());
			Component result = componentMapper.findByJhAndXlh(param);
			
			if(result == null){
				param.setId(UUID.randomUUID().toString());
				componentMapper.insertSelective(param);
			}else{
				componentMapper.updateByPrimaryKeySelective(param);
			}
		}
	}

	@Override
	public OutFieldStock queryByKeywc(String kcid) {
		return outFieldStockMapper.queryByKeywc(kcid);
	}

	/**
	 * 
	 * @Description 根据检验单id查询外场库存
	 * @CreateTime 2018年3月27日 下午3:17:21
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 */
	@Override
	public List<OutFieldStock> queryjydid(String id) {
		return outFieldStockMapper.queryjydid(id);
	}
	
}
