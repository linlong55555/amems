package com.eray.thjw.material2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.InspectionMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.OutFieldStockMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.BackRegisterMapper;
import com.eray.thjw.material2.dao.ContractMgntMapper;
import com.eray.thjw.material2.dao.OutinReceiptInfoMapper;
import com.eray.thjw.material2.dao.OutinReceiptMapper;
import com.eray.thjw.material2.dao.OutinReceiptShelvesMapper;
import com.eray.thjw.material2.dao.ReceivingRelationshipMapper;
import com.eray.thjw.material2.dao.StockHistoryMapper;
import com.eray.thjw.material2.po.BackRegister;
import com.eray.thjw.material2.po.ContractMgnt;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptInfo;
import com.eray.thjw.material2.po.OutinReceiptShelves;
import com.eray.thjw.material2.po.OutinReceiptSource;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.material2.service.OutinReceiptInfoService;
import com.eray.thjw.material2.service.OutinReceiptService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.SaiBongEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;
import enu.common.PartSnValidationEnum;
import enu.common.WhetherEnum;
import enu.material2.ReceiptSourceTypeEnum;
import enu.material2.ReceiptStatusEnum;
import enu.material2.ReceiptTypeEnum;
import enu.material2.StockHistorySubtypeEnum;
import enu.material2.StockHistoryTypeEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
import enu.quality.InspectionStatusEnum;

/**
 * @Description 航材收货serivce实现类
 * @CreateTime 2018年2月26日 上午11:45:34
 * @CreateBy 韩武
 */
@Service("outinReceiptService")
public class OutinReceiptServiceImpl implements OutinReceiptService {
	
	@Resource
	private OutinReceiptMapper outinReceiptMapper;
	
	@Resource
	private ContractMgntMapper contractMgntMapper;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private OutinReceiptInfoService outinReceiptInfoService;
	
	@Resource
	private StockSerivce stockService;
	
	@Resource
	private BackRegisterMapper backRegisterMapper;
	
	@Resource
	private OutFieldStockMapper outFieldStockMapper;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private OutinReceiptInfoMapper outinReceiptInfoMapper;
	
	@Resource
	private InspectionMapper inspectionMapper;
	
	@Resource
	private HCMainDataMapper hcMainDataMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private ComponentMapper componentMapper;
	
	@Resource
	private StockHistoryMapper stockHistoryMapper;
	
	@Resource
	private OutinReceiptShelvesMapper outinReceiptShelvesMapper;
	
	@Resource
	private ReceivingRelationshipMapper receivingRelationshipMapper;
	
	@Resource
	private MaterialBatchInfoService materialBatchInfoService;
	
	@Resource
	private TodorsService todorsService;
	@Resource
	private TodoService todoService;

	/**
	 * @Description 查询收货来源-合同列表
	 * @CreateTime 2018年3月5日 下午2:54:28
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryContractList(ContractMgnt record) {
		PageHelper.startPage(record.getPagination());
		List<ContractMgnt> list = contractMgntMapper.queryAllPageList(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	/**
	 * @Description 查询收货来源详细-合同详细/退料详细列表
	 * @CreateTime 2018年3月7日 上午9:54:11
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryOriginList(OutinReceipt record) {
		
		PageHelper.startPage(record.getPagination());
		
		// 明细集合
		List<OutinReceiptSource> list = new ArrayList<OutinReceiptSource>();
		// 收货类型=采购/送修/租入/租出/交换
		if(ReceiptTypeEnum.PURCHASE.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.REPAIR.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.RENT_IN.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.RENT_OUT.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.EXCHANGE.getId().equals(record.getShlx())){
			list = outinReceiptMapper.queryContractDetailList(record);
		}
		// 收货类型=退料
		else if(ReceiptTypeEnum.RETURN_MATERIAL.getId().equals(record.getShlx())){
			list = outinReceiptMapper.queryReturnMaterialList(record);
		}
		
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	/**
	 * @Description 保存收货单
	 * @CreateTime 2018年3月9日 下午4:26:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public OutinReceipt doSave(OutinReceipt record) throws BusinessException, SaibongException{
		// 保存时验证
		validateForSave(record);
		// 保存收货单主单
		saveReceipt(record);
		// 保存收货单明细
		saveReceiptDetails(record);
		return record;
	}
	
	/**
	 * @Description 提交收货单
	 * @CreateTime 2018年3月9日 下午4:26:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public OutinReceipt doSubmit(OutinReceipt record) throws BusinessException,
			SaibongException {
		// 提交时验证
		validateForSubmit(record);
		// 保存收货单主单
		saveReceipt(record);
		// 保存收货单明细
		saveReceiptDetails(record);
		// 退料扣减库存
		deductionStockIfReturnMaterial(record);
		// 勾选质检选项的生成质检单
		buildInspectionSheet(record);
		// 生成库存/库存履历
		addToStockAndRecordStockHistory(record);
		
		
		return record;
	}
	
	/**
	 * @Description 生成库存/库存履历
	 * @CreateTime 2018年3月13日 下午2:38:52
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	private void addToStockAndRecordStockHistory(OutinReceipt record) throws BusinessException{
		
		if(record.getDetails() != null && !record.getDetails().isEmpty()){
			for (OutinReceiptInfo detail : record.getDetails()) {
				// 有上架信息
				if(detail.getShelves() != null && !detail.getShelves().isEmpty()){
					for (OutinReceiptShelves shelf : detail.getShelves()) {
						
						if(WhetherEnum.YES.getId().equals(detail.getIsZj())){
							// 保存收货单-上架的库存和库存履历
							saveShelfStockAndRec(shelf, detail, record, StockStatusEnum.RECEIVED);
						}else{
							// 保存收货单-上架的库存和库存履历
							StockStatusEnum stockStatusEnum = StringUtils.isNotBlank(shelf.getKwid()) 
									&& detail.getIsZj().equals(WhetherEnum.NO.getId()) 
									? StockStatusEnum.NORMAL : StockStatusEnum.RECEIVED;
							saveShelfStockAndRec(shelf, detail, record, stockStatusEnum);
						}
						
						// 保存收货单详情的部件履历和库存履历主信息
						saveReceiptMaterialHistoryAndStockHistory(shelf, detail, record);
					}
				}
				
				// 如部件信息不存在，则插入部件信息
				insertComponentInfoIfNotExist(detail, record);
			}
		}
	}
	
	/**
	 * @Description 如部件信息不存在，则插入部件信息
	 * @CreateTime 2018年3月19日 上午11:22:20
	 * @CreateBy 韩武
	 * @param shelf
	 */
	private void insertComponentInfoIfNotExist(OutinReceiptInfo detail, OutinReceipt record){
		if(StringUtils.isNotBlank(detail.getSn())){
			
			Component param = new Component();
			param.setJh(detail.getBjh());
			param.setXlh(detail.getSn());
			param.setDprtcode(record.getDprtcode());
			Component result = componentMapper.findByJhAndXlh(param);
			
			if(result == null){
				param.setId(UUID.randomUUID().toString());
				componentMapper.insertSelective(param);
			}
		}
	}
	
	/**
	 * @Description 转换库存实体
	 * @CreateTime 2018年3月13日 下午4:21:11
	 * @CreateBy 韩武
	 * @param shelf
	 * @param detail
	 * @param record
	 * @param stockStatusEnum
	 * @return
	 */
	private Stock convertStockBean(OutinReceiptShelves shelf, OutinReceiptInfo detail, 
			OutinReceipt record, StockStatusEnum stockStatusEnum){
		User user = ThreadVarUtil.getUser();
		
		Stock stock = new Stock();
		stock.setId(UUID.randomUUID().toString());
		stock.setDprtcode(record.getDprtcode());
		
		// 查询库位信息
		stock.setCklb(shelf.getCklb());
		stock.setCkh(shelf.getCkh());
		stock.setCkid(shelf.getCkid());
		stock.setCkmc(shelf.getCkmc());
		stock.setKwid(shelf.getKwid());
		stock.setKwh(shelf.getKwh());
		
		// 查询航材信息
		if(!StringUtils.isBlank(detail.getBjid())){
			HCMainData hcMainData = hcMainDataMapper.selectById(detail.getBjid());
			stock.setBjid(hcMainData.getId());
			stock.setBjh(hcMainData.getBjh());
			stock.setPch(detail.getPch());
			stock.setSn(detail.getSn());
			stock.setZwms(hcMainData.getZwms());
			stock.setYwms(hcMainData.getYwms());
			stock.setJldw(hcMainData.getJldw());
			stock.setXh(hcMainData.getXingh());
			stock.setGg(hcMainData.getGg());
			stock.setKcsl(shelf.getSjsl());
			stock.setDjsl(BigDecimal.ZERO);
			stock.sethCMainData(hcMainData);
		}	
		// 合同信息
		if(ReceiptTypeEnum.PURCHASE.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.REPAIR.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.RENT_IN.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.RENT_OUT.getId().equals(record.getShlx())
				|| ReceiptTypeEnum.EXCHANGE.getId().equals(record.getShlx())){	// 收货类型=采购、修理、租进、租出、交换
			if(StringUtils.isNotBlank(record.getLyid())){
				stock.setCghtid(record.getLyid());
				stock.setHtbhCg(record.getLybh());
			}else if(StringUtils.isNotBlank(detail.getLymxid())){
				ContractMgnt contract = contractMgntMapper.findByChildId(detail.getLymxid());
				stock.setCghtid(contract.getId());
				stock.setHtbhCg(contract.getHth());
			}
		}
		
		stock.setRksj(record.getShrq());
		stock.setRkbmid(user.getBmdm());
		stock.setRkrid(user.getId());
		stock.setZt(stockStatusEnum.getId());
		stock.setKccb(shelf.getKccb());
		stock.setBiz(shelf.getBiz());
		stock.setJz(shelf.getJz());
		stock.setJzbz(shelf.getJzbz());
		stock.setShdmxid(shelf.getId());
		stock.setCqid(detail.getCqid());
		stock.setWhrid(user.getId());
		stock.setWhsj(new Date());
		stock.setJydid(detail.getJydid());
		
		return stock;
	}
	
	/**
	 * @Description 保存收货单的部件履历和库存履历主信息
	 * @CreateTime 2018年3月19日 上午10:54:35
	 * @CreateBy 韩武
	 * @param detail
	 * @param record
	 */
	private void saveReceiptMaterialHistoryAndStockHistory(OutinReceiptShelves shelf, 
			OutinReceiptInfo detail, OutinReceipt record){
		
		User user = ThreadVarUtil.getUser();
		
		// 写收货履历
		Stock stock = shelf.getStock();
		MaterialHistory materialHistory = new MaterialHistory(stock);
		materialHistory.setId(UUID.randomUUID().toString());
		materialHistoryMapper.insertSelective(materialHistory);
		
		// 写部件履历
		StockHistory stockHistory = new StockHistory();
		stockHistory.setId(materialHistory.getId());
		stockHistory.setDprtcode(materialHistory.getDprtcode());
		stockHistory.setCzsj(new Date());
		stockHistory.setCzrbmid(user.getBmdm());
		stockHistory.setCzrid(user.getId());
		stockHistory.setCzr(user.getDisplayName());
		stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_RECEIPT.getId());
		stockHistory.setCzzlx(StockHistorySubtypeEnum.getIdByReceiptType(record.getShlx(), detail.getLymxlx()));
		stockHistory.setCzsm("收货");
		stockHistory.setKcid(materialHistory.getKcid());
		stockHistory.setYwid(record.getId());
		stockHistory.setYwbh(record.getShdh());
		stockHistory.setYwmxid(shelf.getId());
		stockHistoryMapper.insertSelective(stockHistory);
		
		// 更新收货单-上架的库存履历id
		OutinReceiptShelves param = new OutinReceiptShelves();
		param.setId(shelf.getId());
		param.setKcllid(materialHistory.getId());
		outinReceiptShelvesMapper.updateByPrimaryKeySelective(param);
	}
	
	/**
	 * @throws BusinessException 
	 * @Description 保存收货单-上架的库存和库存履历
	 * @CreateTime 2018年3月13日 下午3:44:16
	 * @CreateBy 韩武
	 * @param shelf
	 * @param detail
	 * @param record
	 * @param stockStatusEnum
	 * @throws  
	 */
	private void saveShelfStockAndRec(OutinReceiptShelves shelf, OutinReceiptInfo detail, 
			OutinReceipt record, StockStatusEnum stockStatusEnum) throws BusinessException{
		
		User user = ThreadVarUtil.getUser();
		
		// 转换库存实体
		Stock stock = convertStockBean(shelf, detail, record, stockStatusEnum);
		
		// 保存库存
		stockMapper.insertSelective(stock);
		
		//收货单提交,当写入库存为收货状态的库存时写待办
		if(Integer.valueOf(0).equals(detail.getIsZj())){
			if(StockStatusEnum.RECEIVED.getId().equals(stock.getZt())){
				String gnbm="";
				Integer dbgzlx=null;
				if(!MaterialTypeEnum.APPOINTED.getId().equals(detail.getHclx())){//如果不是工具
					dbgzlx=UndoEnum.HCSJ.getId();	
					gnbm="material:outin:materialstockin";
				}else{//如果是工具
					dbgzlx=UndoEnum.GJSJ.getId();
					gnbm="material:outin:toolstockin";
				}
				//质检提交,写入待办
				StringBuilder sm=new StringBuilder();
				sm.append("待上架: ");
				if(StringUtils.isNotBlank(stock.getYwms()))
					sm.append(stock.getYwms());
				if(StringUtils.isNotBlank(stock.getBjh()))
					sm.append(" /").append(stock.getBjh());
				if(StringUtils.isNotBlank(stock.getSn()))
					sm.append(" /").append(stock.getSn());
				if(StringUtils.isNotBlank(stock.getPch()))
					sm.append(" /").append(stock.getPch());
				todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), TodoStatusEnum.DCL.getId(), stock.getId(), null, null, 
						null, dbgzlx, NodeEnum.NODE31.getId(), sm.toString(),null, null, gnbm, stock.getId(), null);
				
			}			
		}
		// 插入或更新物料批次信息
		if(StockStatusEnum.NORMAL.equals(stockStatusEnum)){
			materialBatchInfoService.insertOrUpdate(stock.getDprtcode(), stock.getBjh(), 
					stock.getSn(), stock.getPch(), stock.getKccb(), stock.getBiz(), 
					stock.getJz(), stock.getJzbz());
		}
		
		// 获取uuid短码
		stock.setUuiddm(stockMapper.selectUUIDDMbyId(stock.getId()));
		
		// 保存库存履历
		materialRecService.writeStockRec(stock.getId(), record.getCzls(), record.getId(), record.getShdh(),
				StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.SAVE, ReceiptTypeEnum.getName(record.getShlx()),"",
				record.getShdh(), record.getLybh(), detail.getSl());
		
		// 有仓库库位信息且不质检
		if(StringUtils.isNotBlank(shelf.getKwid()) && WhetherEnum.NO.getId().equals(detail.getIsZj())){
			// 写部件履历
			MaterialHistory materialHistory = new MaterialHistory(stock);
			materialHistory.setId(UUID.randomUUID().toString());
			materialHistoryMapper.insertSelective(materialHistory);
			
			// 写库存履历
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
			stockHistory.setYwid(record.getId());
			stockHistory.setYwbh(record.getShdh());
			stockHistory.setYwmxid(shelf.getId());
			stockHistoryMapper.insertSelective(stockHistory);
		}
		
		shelf.setStock(stock);
	}
	
	/**
	 * @Description 生成质检单
	 * @CreateTime 2018年3月13日 下午2:27:03
	 * @CreateBy 韩武
	 * @param record
	 * @throws SaibongException 
	 */
	private void buildInspectionSheet(OutinReceipt record) throws SaibongException{
		User user = ThreadVarUtil.getUser();
		if(record.getDetails() != null && !record.getDetails().isEmpty()){
			for (OutinReceiptInfo detail : record.getDetails()) {
				if(WhetherEnum.YES.getId().equals(detail.getIsZj())){
					Inspection ins = new Inspection();
					ins.setId(UUID.randomUUID().toString());
					ins.setDprtcode(record.getDprtcode());
					ins.setJydh(SNGeneratorFactory.generate(record.getDprtcode(), SaiBongEnum.HC_ZJD.getName(), record));
					ins.setShdid(record.getId());
					ins.setShdmxid(detail.getId());
					ins.setKysl(detail.getSl());
					ins.setZt(InspectionStatusEnum.INITIAL.getId());
					ins.setZdbmid(user.getBmdm());
					ins.setZdrid(user.getId());
					ins.setZdsj(new Date());
					detail.setJydid(ins.getId());
					inspectionMapper.insertSelective(ins);
					
					//收货单提交写待办
					StringBuffer strSm = new StringBuffer();
					strSm.append("请质检部件");
					strSm.append(detail.getBjh());
					if(StringUtils.isNotBlank(detail.getSn())){
						strSm.append("序列号");
						strSm.append(detail.getSn());
					}
					if(StringUtils.isNotBlank((detail.getPch()))){
						strSm.append("批次号");
						strSm.append(detail.getPch());
					}
					todoService.insertSelectiveTechnical(ins,strSm.toString(),"material:inspection:main",NodeEnum.NODE33.getId(),null,TodoEnum.ZJ.getId());
				}
			}
		}
	}
	
	/**
	 * @Description 退料扣减库存
	 * @CreateTime 2018年3月13日 上午10:30:48
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	private void deductionStockIfReturnMaterial(OutinReceipt record) throws BusinessException{
		if(record.getDetails() != null && !record.getDetails().isEmpty()){
			for (OutinReceiptInfo detail : record.getDetails()) {
				// 明细类型 = 退料
				if(ReceiptSourceTypeEnum.RETURN_MATERIAL.getId().equals(detail.getLymxlx())){
					// 退料单
					BackRegister back = backRegisterMapper.selectByPrimaryKey(detail.getLymxid());
					// 如果外场库存id为空，表示退料申请时没有关联外场库存，是手工输入的件号序列号，无需处理退料扣减库存
					if (null == back || StringUtils.isBlank(back.getWckcid())) {
						continue;
					}
					// 外场库存
					OutFieldStock out = outFieldStockMapper.selectByPrimaryKey(back.getWckcid());
					if(out != null){
						BigDecimal kysl = (out.getKcsl() != null ? out.getKcsl() : BigDecimal.ZERO)
								.subtract(out.getDjsl() != null ? out.getDjsl() : BigDecimal.ZERO);
						// 判断外场库存数量是否能足够扣减
						if(detail.getSl().compareTo(kysl) <= 0){
							
							// 插入日志
							materialRecService.writeOutfieldRec(out.getId(), record.getCzls(), record.getId(), record.getShdh(),
									OutfieldRecBizTypeEnum.TYPE9, UpdateTypeEnum.SAVE, ReceiptTypeEnum.RETURN_MATERIAL.getName(),
									"", record.getShdh(), "", detail.getSl());
							
							// 更新外场库存数量
							if(detail.getSl().compareTo(kysl) == 0){
								outFieldStockMapper.deleteByPrimaryKey(out.getId());
							}else{
								outFieldStockMapper.updateKcslById(out.getId(), out.getKcsl().subtract(detail.getSl()));
							}
							
							// 保存库存履历
							MaterialHistory materialHistory = new MaterialHistory(out);
							materialHistory.setId(UUID.randomUUID().toString());
							materialHistoryService.insertSelective(materialHistory);
							
							// 将b_h_017的id（库存履历id）回填到b_h2_02001 收货单-信息表的外场库存履历id字段
							OutinReceiptInfo param = new OutinReceiptInfo();
							param.setId(detail.getId());
							param.setWckcllid(materialHistory.getId());
							outinReceiptInfoMapper.updateByPrimaryKeySelective(param);
						}else{
							throw new BusinessException("部件：" + detail.getBjh() + "，收货数量不能大于外场库存数量！");
						}
					}else{
						throw new BusinessException("部件：" + detail.getBjh() + "，收货数量不能大于外场库存数量！");
					}
				}
			}
		}
	}
	
	/**
	 * @Description 保存时验证
	 * @CreateTime 2018年3月9日 下午4:52:53
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	private void validateForSave(OutinReceipt record) throws BusinessException{
		if(StringUtils.isNotBlank(record.getId())){
			OutinReceipt dbData = outinReceiptMapper.selectByPrimaryKey(record.getId());
			if(dbData != null && !ReceiptStatusEnum.SVAE.getId().equals(dbData.getZt())){
				throw new BusinessException("该数据已更新，请刷新后再进行操作！");
			}
		}
		
		if(record.getDetails() != null && !record.getDetails().isEmpty()){
			for (OutinReceiptInfo detail : record.getDetails()) {
				HCMainData param = new HCMainData();
				param.setDprtcode(record.getDprtcode());
				param.setBjh(detail.getBjh());
				if(hCMainDataMapper.selectByBjhAndDprcode(param) == null){
					throw new BusinessException("部件号："+ detail.getBjh() + "在航材主数据中不存在！");
				}
			}
		}
	}
	
	/**
	 * @Description 提交时验证
	 * @CreateTime 2018年3月13日 上午10:11:01
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	private void validateForSubmit(OutinReceipt record) throws BusinessException{
		if(StringUtils.isNotBlank(record.getId())){
			OutinReceipt dbData = outinReceiptMapper.selectByPrimaryKey(record.getId());
			if(dbData != null && !ReceiptStatusEnum.SVAE.getId().equals(dbData.getZt())){
				throw new BusinessException("该数据已更新，请刷新后再进行操作！");
			}
		}
		
		if(record.getDetails() != null && !record.getDetails().isEmpty()){
			Map<String, OutinReceiptInfo> jhxlhMap = new HashMap<String, OutinReceiptInfo>();
			for (OutinReceiptInfo detail : record.getDetails()) {
				
				// 验证收货单明细是否在部件主数据中存在
				HCMainData param = new HCMainData();
				param.setDprtcode(record.getDprtcode());
				param.setBjh(detail.getBjh());
				if(hCMainDataMapper.selectByBjhAndDprcode(param) == null){
					throw new BusinessException("部件号："+ detail.getBjh() + "在航材主数据中不存在！");
				}
				
				if(StringUtils.isNotBlank(detail.getSn())){
					
					// 工具
					if(MaterialTypeEnum.APPOINTED.getId().equals(detail.getHclx())){
						// 判断存在的范围：库存
						stockService.getCount4ValidationBjAndXlh(detail.getId(), detail.getBjh(), 
								detail.getSn(), record.getDprtcode(), PartSnValidationEnum.TOOL.getId());
					}
					// 退料
					else if(ReceiptSourceTypeEnum.RETURN_MATERIAL.getId().equals(detail.getLymxlx())){
						// 退料单
						BackRegister back = backRegisterMapper.selectByPrimaryKey(detail.getLymxid());
						// 判断存在的范围：装机清单+外场库存+库存，排除外场库存id
						stockService.getCount4ValidationBjAndXlh(back.getWckcid(), detail.getBjh(), 
								detail.getSn(), record.getDprtcode(), PartSnValidationEnum.MATERIAL.getId());
					}
					// 其他
					else{
						// 判断存在的范围：装机清单+外场库存+库存
						stockService.getCount4ValidationBjAndXlh(detail.getId(), detail.getBjh(), 
								detail.getSn(), record.getDprtcode(), PartSnValidationEnum.MATERIAL.getId());
					}
					
					// 验证件号序列号在收货单中重复
					String key = detail.getBjh() + "_" + detail.getSn();
					if(jhxlhMap.containsKey(key)){
						throw new BusinessException("部件号："+ detail.getBjh() + "，序列号：" + detail.getSn() + "在收货单中重复！");
					}else{
						jhxlhMap.put(key, detail);
					}
				}
			}
		}
	}
	
	/**
	 * @Description 删除时验证
	 * @CreateTime 2018年3月20日 上午9:57:08
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	private void validateForDelete(String id) throws BusinessException{
		OutinReceipt dbData = outinReceiptMapper.selectByPrimaryKey(id);
		if(dbData == null || !ReceiptStatusEnum.SVAE.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
	}
	
	/**
	 * @Description 撤销时验证
	 * @CreateTime 2018年3月20日 上午9:57:08
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	private void validateForRevoke(String id) throws BusinessException{
		OutinReceipt dbData = outinReceiptMapper.selectByPrimaryKey(id);
		if(dbData == null || !ReceiptStatusEnum.SUBMIT.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		
		if(outinReceiptShelvesMapper.validateStockCountByShdid(id) > 0){
			throw new BusinessException("收货数量与库存数量不一致，撤销失败！");
		}
	}
	
	/**
	 * @Description 保存收货单主单
	 * @CreateTime 2018年3月9日 下午4:32:21
	 * @CreateBy 韩武
	 * @param record
	 * @throws SaibongException 
	 */
	private void saveReceipt(OutinReceipt record) throws SaibongException{
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setWhbmid(user.getBmdm());
		record.setCzls(UUID.randomUUID().toString());
		
		if (StringUtils.isBlank(record.getId())){	// 新增收货单主单
			record.setId(UUID.randomUUID().toString());
			// 生成收货单号
			record.setShdh(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_SHD.getName(), record));
			record.setDprtcode(user.getJgdm());
			record.setShrid(user.getId());
			record.setShbmid(user.getBmdm());
			outinReceiptMapper.insertSelective(record);
			
		} else {	// 修改收货单主单
			outinReceiptMapper.updateByReceipt(record);
		}
	}
	
	/**
	 * @Description 保存收货单明细
	 * @CreateTime 2018年3月9日 下午5:10:26
	 * @CreateBy 韩武
	 * @param record
	 * @throws SaibongException 
	 */
	private void saveReceiptDetails(OutinReceipt record) throws SaibongException{
		// 根据收货单id删除收货单明细
		outinReceiptInfoService.deleteNotExistByShdid(record.getId(), record.getDetails());
		// 保存收货单明细
		outinReceiptInfoService.save(record);
	}

	/**
	 * @Description 查询收货单详情
	 * @CreateTime 2018年3月12日 下午2:29:27
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public OutinReceipt queryDetail(String id) {
		return outinReceiptMapper.queryDetail(id);
	}

	/**
	 * @Description 查询当前人的收货单列表
	 * @CreateTime 2018年3月19日 下午2:46:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> querySelfList(OutinReceipt record) {
		PageHelper.startPage(record.getPagination());
		List<OutinReceipt> list = outinReceiptMapper.querySelfList(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	/**
	 * @Description 删除收货单
	 * @CreateTime 2018年3月19日 下午5:22:26
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException 
	 */
	@Override
	public void doDelete(String id) throws BusinessException {
		// 删除时验证
		validateForDelete(id);
		// 删除收货单上架信息
		outinReceiptShelvesMapper.deleteByShdid(id);
		// 删除收货单详情
		outinReceiptInfoMapper.deleteByShdid(id);
		// 删除收货单
		outinReceiptMapper.deleteByPrimaryKey(id);
		// 删除收货关系
		receivingRelationshipMapper.deleteByShdid(id);
	}

	/**
	 * @Description 撤销收货单
	 * @CreateTime 2018年3月19日 下午5:22:43
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException 
	 */
	@Override
	public void doRevoke(String id) throws BusinessException {
		// 撤销时验证
		validateForRevoke(id);
		
		// 查询收货单详情
		OutinReceipt receipt = queryDetail(id);
		
		// 删除库存并记录库存履历
		deleteStock(receipt);
		
		// 退料扣减的库存返还
		revokeStockIfReturnMaterial(receipt);
		
		// 删除质检单
		deleteInspectionSheet(id);
		
		// 处理收货单数据
		revokeReceipt(receipt);
		
	}
	
	/**
	 * @Description 删除质检单
	 * @CreateTime 2018年3月29日 下午3:29:36
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException 
	 */
	private void deleteInspectionSheet(String id) throws BusinessException{
		
		
		List<Inspection> list=inspectionMapper.selectByKeyByShdid(id);
		for (Inspection inspection : list) {
			//删除待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(inspection.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(33);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			todoService.deletedbyw(tododbyw);
		}
		
		inspectionMapper.deleteByShdid(id);
	}
	
	/**
	 * @Description 删除库存并记录库存履历
	 * @CreateTime 2018年3月20日 下午2:07:31
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException 
	 */
	private void deleteStock(OutinReceipt receipt) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		List<Stock> stocks = stockMapper.queryStockByShdid(receipt.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE31.getId());
		for (Stock stock : stocks) {
			// 删除库存
			stockMapper.delete(stock);
			
			//收货单撤销根据库存id删除待办
			todorsService.deleteToDo(stock.getId(), UndoStatusEnum.DCL.getId(),jdlist);			
			// 写入b_h_017 部件库存履历
			MaterialHistory materialHistory = new MaterialHistory(stock);
			materialHistory.setId(UUID.randomUUID().toString());
			materialHistoryMapper.insertSelective(materialHistory);
			
			// 写入b_h_025 库存履历主信息
			StockHistory stockHistory = new StockHistory();
			stockHistory.setId(materialHistory.getId());
			stockHistory.setDprtcode(materialHistory.getDprtcode());
			stockHistory.setCzsj(new Date());
			stockHistory.setCzrbmid(user.getBmdm());
			stockHistory.setCzrid(user.getId());
			stockHistory.setCzr(user.getDisplayName());
			stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_RECEIPT.getId());
			stockHistory.setCzzlx(StockHistorySubtypeEnum.STOCK_RECEIPT_CANCEL.getId());
			stockHistory.setCzsm("收货-撤销");
			stockHistory.setKcid(materialHistory.getKcid());
			stockHistory.setYwid(receipt.getId());
			stockHistory.setYwbh(receipt.getShdh());
			stockHistory.setYwmxid(stock.getShdmxid());
			stockHistoryMapper.insertSelective(stockHistory);
		}
	}

	/**
	 * @Description 退料返还库存
	 * @CreateTime 2018年3月20日 下午2:37:57
	 * @CreateBy 韩武
	 * @param receipt
	 */
	private void revokeStockIfReturnMaterial(OutinReceipt receipt){
		User user = ThreadVarUtil.getUser();
		for (OutinReceiptInfo detail : receipt.getDetails()) {
			// 如果收货单明细的外场库存履历id不为空
			if(StringUtils.isNotBlank(detail.getWckcllid())){
				MaterialHistory materialHistory = materialHistoryMapper.selectByPrimaryKey(detail.getWckcllid());
				OutFieldStock outFieldStock = outFieldStockMapper.selectById(materialHistory.getKcid());
				if(outFieldStock == null){
					// 如果不存在，则根据库存履历数据新增外场库存数据，库存数量=收货数量
					outFieldStockMapper.insertOutFieldStockFromMaterialHistory(user.getId(), materialHistory.getId(), materialHistory.getKfkcid());
				}else{
					// 如果存在则库存数量=库存数量+收货数量
					outFieldStock.setKcsl(outFieldStock.getKcsl().add(detail.getSl()));
					outFieldStockMapper.updateKcslById(outFieldStock.getId(), outFieldStock.getKcsl());
				}
			}
		}
	}
	
	/**
	 * @Description 撤销收货单
	 * @CreateTime 2018年3月20日 下午2:55:51
	 * @CreateBy 韩武
	 * @param receipt
	 */
	private void revokeReceipt(OutinReceipt receipt){
		
		// 修改收货单主表状态=11撤销，记录维护人和时间信息
		User user = ThreadVarUtil.getUser();
		receipt.setWhrid(user.getId());
		receipt.setWhsj(new Date());
		receipt.setWhbmid(user.getBmdm());
		receipt.setZt(ReceiptStatusEnum.CANCEL.getId());
		outinReceiptMapper.updateByPrimaryKeySelective(receipt);
		
		// 根据收货单id修改b_h_027 收货关系表状态=0无效
		receivingRelationshipMapper.invalidateByshdid(receipt.getId());
		
	
	}

	@Override
	public List<OutinReceiptSource> queryReturnMaterialList(OutinReceipt record) throws BusinessException {
		return  outinReceiptMapper.queryReturnMaterialList(record);
	}
}