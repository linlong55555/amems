package com.eray.thjw.aerialmaterial.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.HasScrappedListMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.OutFieldStockMapper;
import com.eray.thjw.aerialmaterial.dao.ScrapListMapper;
import com.eray.thjw.aerialmaterial.dao.StockFreezeHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.TransferMapper;
import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.ScrapList;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.StockFreezeHistory;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Transfer;
import com.eray.thjw.aerialmaterial.service.ComponentScrapService;
import com.eray.thjw.aerialmaterial.service.ComponentUsageService;
import com.eray.thjw.aerialmaterial.service.HasScrappedListService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.ScrapListService;
import com.eray.thjw.aerialmaterial.service.StockFreezeHistoryService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.FreezeBusinessTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.ScrapStatusEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.aerialmaterial.StockTypeEnum;

@Service
public class ScrapListServiceImpl implements ScrapListService {
	
	@Resource
	private ScrapListMapper scrapListMapper;
	@Resource
	private HasScrappedListMapper hasScrappedListMapper;
	@Resource
	private HasScrappedListService hasScrappedListService;
	@Resource
	private StockMapper stockMapper;
	@Resource
	private OutFieldStockMapper outFieldStockMapper;
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	@Resource
	private TransferMapper transferMapper;
	@Autowired
	private SaibongUtilService saibongUtilService;
	@Resource
	private MaterialRecService materialRecService;
	@Resource
	private MaterialHistoryService materialHistoryService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private StockFreezeHistoryMapper stockFreezeHistoryMapper;
	@Resource
	private StockFreezeHistoryService stockFreezeHistoryService;
	@Resource
	private StorageMapper storageMapper;
	@Resource
	private ComponentUsageService componentUsageService;
	@Resource
	private ComponentScrapService componentScrapService;
	
	@Override
	public void save(ScrapList scrapList)
			throws BusinessException {
		 scrapListMapper.insertSelective(scrapList);
	}

	@Override
	public List<ScrapList> selectScrapList(ScrapList record) {

		return scrapListMapper.selectScrapList(record);
	}

	@Override
	public ScrapList selectByPrimaryKey(String id) {
		return scrapListMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(ScrapList record) throws Exception {
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		List<HasScrappedList> hasScrappedList=record.getHasScrappedList();
		String ykdh=saibongUtilService.generate(ThreadVarUtil.getUser().getJgdm(), SaiBongEnum.HCYK.getName());//生成移库单号
		for (HasScrappedList hasScrappedList2 : hasScrappedList) {
			hasScrappedListMapper.updateByPrimaryKeySelective(hasScrappedList2);			
			if(record.getZt()==10){			//提交
				MaterialHistory materialHistory=materialHistoryMapper.selectByPrimaryKey(hasScrappedList2.getKcllid());//为了方便日志提前查询
				if(hasScrappedList2.getZt()!=2){//不通过
					if(record.getBflx()==1){
						stockMapper.updateZTById(materialHistory.getKcid());
						materialRecService.writeStockRec(materialHistory.getKcid(), czls.toString(), materialHistory.getKcid(), "", StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.UPDATE,"审核",record.getBfdh(),materialHistory.getKcsl());
					}else{
						outFieldStockMapper.updateZTById(materialHistory.getKcid());
						materialRecService.writeStockRec(materialHistory.getKcid(), czls.toString(), materialHistory.getKcid(), "", StockRecBizTypeEnum.TYPE2, UpdateTypeEnum.UPDATE,"审核",record.getBfdh(),materialHistory.getKcsl());
					}
				}else{//通过
					if(record.getBflx()==1){    //生成移库单
						Transfer t=new Transfer();
						t.setId(materialHistory.getKcid());
						t.setDprtcode(materialHistory.getDprtcode());
						t.setYkdh(ykdh);
						t.setBjid(materialHistory.getBjid());
						t.setBjh(materialHistory.getBjh());
						t.setPch(materialHistory.getPch());
						t.setSn(materialHistory.getSn());
						t.setZwms(materialHistory.getZwms());
						t.setYwms(materialHistory.getYwms());
						t.setYsCklb(materialHistory.getCklb());
						t.setYsCkid(materialHistory.getCkid());
						t.setYsCkh(materialHistory.getCkh());
						t.setYsCkmc(materialHistory.getCkmc());
						t.setYsKwid(materialHistory.getKwid());
						t.setYsKwh(materialHistory.getKwh());
						t.setYsSl(materialHistory.getKcsl());
						t.setZdrid(user.getId());
						t.setZdbmid(user.getBmdm());
						t.setZt("1");
						transferMapper.insertSelective(t);
					}else{
						materialRecService.writeOutfieldRec(materialHistory.getKcid(), czls.toString(), materialHistory.getKcid(), "", OutfieldRecBizTypeEnum.TYPE3, UpdateTypeEnum.DELETE,"审核",record.getBfdh(),materialHistory.getKcsl());
						outFieldStockMapper.deleteOutFieldStockById(materialHistory.getKcid());//删除b_h_003中数据
						stockMapper.insertStockFromMaterialHistory(user.getId(),materialHistory.getId());//新增一条到b_h_001中
						materialRecService.writeStockRec(materialHistory.getKcid(), czls.toString(), materialHistory.getKcid(), "", StockRecBizTypeEnum.TYPE6, UpdateTypeEnum.SAVE,"审核",record.getBfdh(),materialHistory.getKcsl());
					}
				}
			}
		}
		scrapListMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 查询-报废申请
	 */
	@Override
	public Map<String, Object> queryapplypage(ScrapList scraplist) {
		//用户刚编辑过的记录 ID
		String id = scraplist.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		scraplist.setId(null);
		
		PageHelper.startPage(scraplist.getPagination());
		List<ScrapList> list = scrapListMapper.queryapplypage(scraplist);
		if(((Page)list).getTotal() > 0){
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ScrapList param = new ScrapList();
					param.setId(id);
					List<ScrapList> newRecordList = scrapListMapper.queryapplypage(param);;
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, scraplist.getPagination());
		}else{
			List<ScrapList> newRecordList = new ArrayList<ScrapList>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ScrapList param = new ScrapList();
				param.setId(id);
				newRecordList = scrapListMapper.queryapplypage(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, scraplist.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, scraplist.getPagination());
		}
	}

	/**
	 * 查询报废单详情
	 */
	@Override
	public List<HasScrappedList> queryscrapdetail(ScrapList scraplist) {
		return hasScrappedListMapper.queryscrapdetail(scraplist.getId());
	}

	/**
	 * 保存报废单
	 * @throws SaibongException 
	 */
	@Override
	public String doSave(ScrapList scrapList) throws SaibongException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		scrapList.setBfsj(new Date());
		scrapList.setZdrid(user.getId());
		scrapList.setZdbmid(user.getBmdm());
		scrapList.setZdsj(new Date());
		scrapList.setBflx(StockTypeEnum.INNER_STOCK.getId());
		
		// 报废单
		if(StringUtils.isBlank(scrapList.getId())){	// 新增报废单
			scrapList.setId(UUID.randomUUID().toString());
			scrapList.setBfdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.BFD.getName()));
			scrapList.setZt(1);
			scrapListMapper.insertSelective(scrapList);
		}else{	// 修改报废单
			scrapListMapper.updateByPrimaryKeySelective(scrapList);	
		}
		// 报废单详细
		for (HasScrappedList hasScrappedList : scrapList.getHasScrappedList()) {
			hasScrappedList.setZt(1);
			hasScrappedList.setWhrid(user.getId());
			hasScrappedList.setWhsj(new Date());
			hasScrappedList.setWhdwid(user.getBmdm());
			
			if(StringUtils.isBlank(hasScrappedList.getId())){	// 新增报废单详细
				
				// 添加库存履历
				Stock stock = stockMapper.selectById(hasScrappedList.getKcid());
				MaterialHistory materialHistory=new MaterialHistory(stock);
				materialHistory.setId(UUID.randomUUID().toString());
				materialHistory.setKcsl(hasScrappedList.getBfsl());	
				materialHistory.setWhrid(user.getId());
				materialHistory.setWhsj(new Date());
				materialHistoryService.insertSelective(materialHistory);
				 
				hasScrappedList.setId(UUID.randomUUID().toString());
				hasScrappedList.setMainid(scrapList.getId());
				hasScrappedList.setKcllid(materialHistory.getId());
				hasScrappedListMapper.insertSelective(hasScrappedList);
			}else{	// 修改报废单详细
				HasScrappedList dbData = hasScrappedListMapper.selectByPrimaryKey(hasScrappedList.getId());
				MaterialHistory materialHistory = materialHistoryMapper.selectByPrimaryKey(dbData.getKcllid());
				materialHistory.setKcsl(hasScrappedList.getBfsl());	
				materialHistory.setWhrid(user.getId());
				materialHistory.setWhsj(new Date());
				materialHistoryService.updateByPrimaryKeySelective(materialHistory);
				hasScrappedListMapper.updateByPrimaryKeySelective(hasScrappedList);
			}
		}
		// 删除收货单详细
		hasScrappedListMapper.deleteNotExist(scrapList);
		
		// 保存附件
		List<Attachment> attachments = scrapList.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(scrapList.getId());
					attachment.setDprtcode(scrapList.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
				} 
			}
		}
		List<String> ids = new ArrayList<String>();
		ids.add(scrapList.getId());
		commonRecService.write("mainid", ids, TableEnum.D_011, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, scrapList.getId());
		return scrapList.getId();
	}

	/**
	 * 提交报废单
	 * @throws BusinessException 
	 */
	@Override
	public String doSubmit(ScrapList scrapList) throws SaibongException, BusinessException {
		// 保存报废单
		doSave(scrapList);
		// 验证报废单
		validate(scrapList);
		// 更新报废单状态
		updateScrapStatus(scrapList);
		// 冻结库存
		freezeStock(scrapList);
		return scrapList.getId();
	}
	
	/**
	 * 更新报废单状态
	 * @param receipt
	 */
	private void updateScrapStatus(ScrapList scrapList){
		// 更新报废单主单状态
		User user = ThreadVarUtil.getUser();
		scrapList.setZt(2);
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapListMapper.updateByPrimaryKeySelective(scrapList);	
	}
	
	/**
	 * 冻结库存
	 * @param scrapList
	 */
	private void freezeStock(ScrapList scrapList){
		List<HasScrappedList> list= hasScrappedListMapper.queryAllData(scrapList.getId());
		for (HasScrappedList hasScrappedList : list) {
			StockFreezeHistory param = new StockFreezeHistory();
			param.setKcid(hasScrappedList.getKcid());
			param.setYwid(hasScrappedList.getId());
			// 冻结库存
			if(hasScrappedList.getZt() == 1){
				//库存冻结履历中添加冻结库存
				StockFreezeHistory stockFreezeHistory = stockFreezeHistoryMapper.queryByYwid(param);
				if(stockFreezeHistory == null){	// 新增
					stockFreezeHistory = new StockFreezeHistory();
					stockFreezeHistory.setId(UUID.randomUUID().toString());
					stockFreezeHistory.setKclx(StockTypeEnum.INNER_STOCK.getId());
					stockFreezeHistory.setKcid(hasScrappedList.getKcid());
					stockFreezeHistory.setYwlx(FreezeBusinessTypeEnum.SCRAP.getId());
					stockFreezeHistory.setYwid(hasScrappedList.getId());
					stockFreezeHistory.setDjsl(hasScrappedList.getBfsl());
					stockFreezeHistoryMapper.insertSelective(stockFreezeHistory);
				}else{	// 修改
					stockFreezeHistory.setDjsl(hasScrappedList.getBfsl());
					stockFreezeHistoryMapper.updateByPrimaryKeySelective(stockFreezeHistory);
				}
			}
			// 解冻库存
			else{
				// 删除冻结履历
				stockFreezeHistoryMapper.deleteByYwid(param);
			}
			// 刷新库存冻结数量
			stockFreezeHistoryService.refreshStockFreezeCount(hasScrappedList.getKcid(), StockTypeEnum.INNER_STOCK);
		}
		// 删除明细表中无效的数据
		hasScrappedListMapper.deleteInvalidData(scrapList.getId());
	}
	
	/**
	 * 验证报废单
	 * @param scrapList
	 * @throws BusinessException 
	 */
	private void validate(ScrapList scrapList) throws BusinessException{
		ScrapList dbData = scrapListMapper.selectByPrimaryKey(scrapList.getId());
		// 验证单据状态为保存或审批驳回
		if(!dbData.getZt().equals(ScrapStatusEnum.SAVE.getId()) &&
				!dbData.getZt().equals(ScrapStatusEnum.AUDIT_REJEC.getId())){
			throw new BusinessException("单据状态为"+
				ScrapStatusEnum.getName(dbData.getZt())+"不可提交");
		}
		
		// 验证库存可用数量大于报废数量
		for (HasScrappedList hasScrappedList : scrapList.getHasScrappedList()) {
			Stock stock = stockMapper.selectById(hasScrappedList.getKcid());
			if(stock == null){
				throw new BusinessException("当前部件在库存不存在，请重新选择部件！");
			}
			// 报废数量
			BigDecimal bfsl = hasScrappedList.getBfsl();
			// 库存数量
			BigDecimal kcsl = stock.getKcsl() == null ? BigDecimal.ZERO : stock.getKcsl();
			// 冻结数量
			BigDecimal djsl = stock.getDjsl() == null ? BigDecimal.ZERO : stock.getDjsl();
			// 可用数量 = 库存数量 - 冻结数量
			BigDecimal kysl = kcsl.subtract(djsl);
			if(dbData.getZt().equals(ScrapStatusEnum.SAVE.getId()) && bfsl.compareTo(kysl) > 0){
				throw new BusinessException("部件"+stock.getBjh()+"的报废数量大于可用数量");
			}
			if(dbData.getZt().equals(ScrapStatusEnum.AUDIT_REJEC.getId()) && bfsl.add(kysl).compareTo(kcsl) > 0){
				throw new BusinessException("部件"+stock.getBjh()+"的报废数量+可用数量不可大于库存数量");
			}
		}
	}

	/**
	 * 作废报废单
	 */
	@Override
	public void doScrap(ScrapList scrapList) {
		User user = ThreadVarUtil.getUser();
		scrapList.setZt(ScrapStatusEnum.SCRAP.getId());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapListMapper.updateByPrimaryKeySelective(scrapList);	
	}

	/**
	 * 指定结束报废单
	 * @throws BusinessException 
	 */
	@Override
	public void doClose(ScrapList scrapList) throws BusinessException {
		ScrapList dbData = scrapListMapper.selectByPrimaryKey(scrapList.getId());
		User user = ThreadVarUtil.getUser();
		// 验证单据状态为已审核状态
		if(!dbData.getZt().equals(ScrapStatusEnum.SUBMIT.getId()) && 
				!dbData.getZt().equals(ScrapStatusEnum.AUDIT_REJEC.getId())){
			throw new BusinessException("单据状态为"+
				ScrapStatusEnum.getName(dbData.getZt())+"不可指定结束");
		}
		// 循环报废单详细
		List<HasScrappedList> list= hasScrappedListMapper.queryAllData(scrapList.getId());
		for (HasScrappedList hasScrappedList : list) {
			StockFreezeHistory param = new StockFreezeHistory();
			param.setKcid(hasScrappedList.getKcid());
			param.setYwid(hasScrappedList.getId());
			// 删除冻结履历
			stockFreezeHistoryMapper.deleteByYwid(param);
			// 刷新库存冻结数量
			if(dbData.getBflx().equals(StockTypeEnum.INNER_STOCK.getId())){
				stockFreezeHistoryService.refreshStockFreezeCount(hasScrappedList.getKcid(), StockTypeEnum.INNER_STOCK);
			}else if(dbData.getBflx().equals(StockTypeEnum.OUTER_STOCK.getId())){
				stockFreezeHistoryService.refreshStockFreezeCount(hasScrappedList.getKcid(), StockTypeEnum.OUTER_STOCK);
			}
		}
		
		// 更新报废单主单数据
		scrapList.setZt(ScrapStatusEnum.CLOSED.getId());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapList.setZdjsrid(user.getId());
		scrapList.setZdjsrq(new Date());
		scrapListMapper.updateByPrimaryKeySelective(scrapList);	
	}

	/**
	 * 撤销报废单
	 * @throws BusinessException 
	 */
	@Override
	public void doRevoke(ScrapList scrapList) throws BusinessException {
		ScrapList dbData = scrapListMapper.selectByPrimaryKey(scrapList.getId());
		User user = ThreadVarUtil.getUser();
		// 验证单据状态为已审核状态
		if(!dbData.getZt().equals(ScrapStatusEnum.AUDITED.getId())){
			throw new BusinessException("单据状态为"+
				ScrapStatusEnum.getName(dbData.getZt())+"不可撤销");
		}
		
		// 循环报废单详细
		List<HasScrappedList> list= hasScrappedListMapper.queryAllData(scrapList.getId());
		for (HasScrappedList hasScrappedList : list) {
			if(hasScrappedList.getZt() == 1){
				// 库内报废
				if(dbData.getBflx().equals(StockTypeEnum.INNER_STOCK.getId())){
					Stock stock = stockMapper.selectById(hasScrappedList.getSjkc());
					stock.setZt(StockStatusEnum.NORMAL.getId());
					stock.setWhrid(user.getId());
					stock.setWhsj(new Date());
					// 更新库存
					stockMapper.updateByPrimaryKeySelective(stock);
					// 撤销部件使用情况
					revokeComponentUsage(stock);
				}
				// 库外报废
				else if(dbData.getBflx().equals(StockTypeEnum.INNER_STOCK.getId())){
					OutFieldStock outFieldStock = outFieldStockMapper.selectByPrimaryKey(hasScrappedList.getSjkc());
					outFieldStock.setZt(StockStatusEnum.NORMAL.getId());
					outFieldStock.setWhrid(user.getId());
					outFieldStock.setWhsj(new Date());
					// 更新库存
					outFieldStockMapper.updateByPrimaryKeySelective(outFieldStock);
					// 撤销部件使用情况
					revokeComponentUsage(new Stock(outFieldStock));
				}
			}
		}
		
		// 更新报废单主单数据
		scrapList.setZt(ScrapStatusEnum.REVOKE.getId());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapListMapper.updateByPrimaryKeySelective(scrapList);	
	}

	/**
	 * 审核通过报废单
	 * @throws BusinessException 
	 */
	@Override
	public String doAuditApprove(ScrapList scrapList) throws BusinessException {
		ScrapList dbData = scrapListMapper.selectByPrimaryKey(scrapList.getId());
		User user = ThreadVarUtil.getUser();
		// 验证单据状态为提交状态
		if(!dbData.getZt().equals(ScrapStatusEnum.SUBMIT.getId())){
			throw new BusinessException("单据状态为"+
				ScrapStatusEnum.getName(dbData.getZt())+"不可审核");
		}
		
		// 循环报废单详细
		List<HasScrappedList> list= hasScrappedListMapper.queryAllData(scrapList.getId());
		for (HasScrappedList hasScrappedList : list) {
			if(hasScrappedList.getZt() == 1){
				// 库存冻结履历
				StockFreezeHistory param = new StockFreezeHistory();
				param.setKcid(hasScrappedList.getKcid());
				param.setYwid(hasScrappedList.getId());
				StockFreezeHistory stockFreezeHistory = stockFreezeHistoryMapper.queryByYwid(param);
				// 删除冻结履历
				stockFreezeHistoryMapper.deleteByPrimaryKey(stockFreezeHistory.getId());
				
				/*
				 * 库内报废
				 */
				if(dbData.getBflx().equals(StockTypeEnum.INNER_STOCK.getId())){
					// 库存
					Stock stock = stockMapper.selectById(hasScrappedList.getKcid());
					// 库存数量 = 本次冻结数量
					if(stock.getKcsl().compareTo(stockFreezeHistory.getDjsl()) == 0){
						// 更新库存仓库
						Storage storage = storageMapper.selectByIdWithRel(hasScrappedList.getKwid());
						stock.setCklb(storage.getCklb());
						stock.setCkh(storage.getCkh());
						stock.setCkid(storage.getStore().getId());
						stock.setCkmc(storage.getStore().getCkmc());
						stock.setKwid(storage.getId());
						stock.setKwh(storage.getKwh());
						// 重置冻结数量为0
						stock.setDjsl(BigDecimal.ZERO);
						// 更新库存状态 = 报废
						stock.setZt(StockStatusEnum.SCRAP.getId());
						stock.setWhrid(user.getId());
						stock.setWhsj(new Date());
						stockMapper.updateByPrimaryKeySelective(stock);
						hasScrappedList.setKcid(stock.getId());
						// 作废部件使用情况b_h_01001-->b_h_01004
						updateComponentUsage(stock);
					}
					// 库存数量 > 本次冻结数量
					else if(stock.getKcsl().compareTo(stockFreezeHistory.getDjsl()) == 1){
						
						// 报废拆分出的新库存（新增）
						Stock stock_new = stock.deepClone(Stock.class);
						stock_new.setId(UUID.randomUUID().toString());
						Storage storage = storageMapper.selectByIdWithRel(hasScrappedList.getKwid());
						stock_new.setCklb(storage.getCklb());
						stock_new.setCkh(storage.getCkh());
						stock_new.setCkid(storage.getStore().getId());
						stock_new.setCkmc(storage.getStore().getCkmc());
						stock_new.setKwid(storage.getId());
						stock_new.setKwh(storage.getKwh());
						stock_new.setKcsl(stockFreezeHistory.getDjsl());
						stock_new.setDjsl(BigDecimal.ZERO);
						stock_new.setZt(StockStatusEnum.SCRAP.getId());
						stock_new.setWhrid(user.getId());
						stock_new.setWhsj(new Date());
						stockMapper.insertSelective(stock_new);
						hasScrappedList.setKcid(stock_new.getId());
						
						// 本次库存（修改）
						stock.setKcsl(stock.getKcsl().subtract(stockFreezeHistory.getDjsl()));
						stock.setDjsl(stock.getDjsl().subtract(stockFreezeHistory.getDjsl()));
						stock.setWhrid(user.getId());
						stock.setWhsj(new Date());
						stockMapper.updateByPrimaryKeySelective(stock);
					}
				}
				
				/*
				 * 库外报废
				 */
				else if(dbData.getBflx().equals(StockTypeEnum.OUTER_STOCK.getId())){
					// 库存
					OutFieldStock outFieldStock = outFieldStockMapper.selectByPrimaryKey(hasScrappedList.getKcid());
					// 库存数量 = 本次冻结数量
					if(outFieldStock.getKcsl().compareTo(stockFreezeHistory.getDjsl()) == 0){
						// 更新库存仓库
						Storage storage = storageMapper.selectByIdWithRel(hasScrappedList.getKwid());
						Stock stock_new = new Stock(outFieldStock);
						stock_new.setCklb(storage.getCklb());
						stock_new.setCkh(storage.getCkh());
						stock_new.setCkid(storage.getStore().getId());
						stock_new.setCkmc(storage.getStore().getCkmc());
						stock_new.setKwid(storage.getId());
						stock_new.setKwh(storage.getKwh());
						// 重置冻结数量为0
						stock_new.setDjsl(BigDecimal.ZERO);
						// 更新库存状态 = 报废
						stock_new.setZt(StockStatusEnum.SCRAP.getId());
						stock_new.setWhrid(user.getId());
						stock_new.setWhsj(new Date());
						hasScrappedList.setKcid(outFieldStock.getId());
						// 作废部件使用情况b_h_01001-->b_h_01004
						updateComponentUsage(new Stock(outFieldStock));
						stock_new.setId(UUID.randomUUID().toString());
						stockMapper.insertSelective(stock_new);
						outFieldStockMapper.deleteByPrimaryKey(outFieldStock.getId());
					}
					// 库存数量 > 本次冻结数量
					else if(outFieldStock.getKcsl().compareTo(stockFreezeHistory.getDjsl()) == 1){
						
						// 报废拆分出的新库存（新增）
						Stock stock_new = new Stock(outFieldStock);
						stock_new.setId(UUID.randomUUID().toString());
						Storage storage = storageMapper.selectByIdWithRel(hasScrappedList.getKwid());
						stock_new.setCklb(storage.getCklb());
						stock_new.setCkh(storage.getCkh());
						stock_new.setCkid(storage.getStore().getId());
						stock_new.setCkmc(storage.getStore().getCkmc());
						stock_new.setKwid(storage.getId());
						stock_new.setKwh(storage.getKwh());
						stock_new.setKcsl(stockFreezeHistory.getDjsl());
						stock_new.setDjsl(BigDecimal.ZERO);
						stock_new.setZt(StockStatusEnum.SCRAP.getId());
						stock_new.setWhrid(user.getId());
						stock_new.setWhsj(new Date());
						stockMapper.insertSelective(stock_new);
						hasScrappedList.setKcid(stock_new.getId());
						
						// 本次库存（修改）
						outFieldStock.setKcsl(outFieldStock.getKcsl().subtract(stockFreezeHistory.getDjsl()));
						outFieldStock.setDjsl(outFieldStock.getDjsl().subtract(stockFreezeHistory.getDjsl()));
						outFieldStock.setWhrid(user.getId());
						outFieldStock.setWhsj(new Date());
						outFieldStockMapper.updateByPrimaryKeySelective(outFieldStock);
					}
				}
				// 更新报废单明细
				hasScrappedList.setWhdwid(user.getBmdm());
				hasScrappedList.setWhsj(new Date());
				hasScrappedList.setWhrid(user.getId());
				hasScrappedListMapper.updateByPrimaryKeySelective(hasScrappedList);
			}
		}
		
		// 更新报废单主单数据
		scrapList.setZt(ScrapStatusEnum.AUDITED.getId());
		scrapList.setSprid(user.getId());
		scrapList.setSpsj(new Date());
		scrapList.setSpbmid(user.getBmdm());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapListMapper.updateByPrimaryKeySelective(scrapList);	
		return scrapList.getId();
	}
	
	/**
	 * 作废部件使用情况b_h_01001-->b_h_01004
	 * @param stock
	 */
	private void updateComponentUsage(Stock stock){
		if(StringUtils.isNotBlank(stock.getSn())){
			componentScrapService.insertComponentScrap(stock.getBjh(), stock.getSn(), stock.getDprtcode());
			componentUsageService.deleteComponentUsage(stock.getBjh(), stock.getSn(), stock.getDprtcode());
		}
	}
	
	/**
	 * 撤销作废部件使用情况b_h_01004-->b_h_01001
	 * @param stock
	 */
	private void revokeComponentUsage(Stock stock){
		if(StringUtils.isNotBlank(stock.getSn())){
			componentUsageService.insertComponentUsage(stock.getBjh(), stock.getSn(), stock.getDprtcode());
			componentScrapService.deleteComponentScrap(stock.getBjh(), stock.getSn(), stock.getDprtcode());
		}
	}

	/**
	 * 审核拒绝报废单
	 */
	@Override
	public String doAuditReject(ScrapList scrapList) throws BusinessException {
		ScrapList dbData = scrapListMapper.selectByPrimaryKey(scrapList.getId());
		User user = ThreadVarUtil.getUser();
		// 验证单据状态为已审核状态
		if(!dbData.getZt().equals(ScrapStatusEnum.SUBMIT.getId())){
			throw new BusinessException("单据状态为"+
				ScrapStatusEnum.getName(dbData.getZt())+"不可审核拒绝");
		}
		
		// 更新报废单主单数据
		scrapList.setZt(ScrapStatusEnum.AUDIT_REJEC.getId());
		scrapList.setSprid(user.getId());
		scrapList.setSpsj(new Date());
		scrapList.setSpbmid(user.getBmdm());
		scrapList.setZdrid(user.getId());
		scrapList.setZdsj(new Date());
		scrapListMapper.updateByPrimaryKeySelective(scrapList);	
		return scrapList.getId();
	}
}
