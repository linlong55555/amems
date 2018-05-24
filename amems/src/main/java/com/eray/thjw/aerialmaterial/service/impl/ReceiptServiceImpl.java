package com.eray.thjw.aerialmaterial.service.impl;

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

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.GodownEntryDetailMapper;
import com.eray.thjw.aerialmaterial.dao.GodownEntryMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.InspectionMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.ReceiptSheetDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ReceiptSheetMapper;
import com.eray.thjw.aerialmaterial.dao.ReturnedPurchaseMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.po.Contract;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;
import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.aerialmaterial.po.ReceiptSheetDetail;
import com.eray.thjw.aerialmaterial.po.ReturnedPurchase;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.ReceiptService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.SaiBongEnum;
import enu.SupervisoryLevelEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.ReceivingTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.common.PartSnValidationEnum;

/**
 * 收货
 * @author hanwu
 *
 */
@Service("receiptService")
public class ReceiptServiceImpl implements ReceiptService {
	
	@Resource
	private ReceiptSheetMapper receiptSheetMapper;
	
	@Resource
	private ReceiptSheetDetailMapper receiptSheetDetailMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private StockSerivce stockService;
	
	@Resource
	private StorageMapper storageMapper;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private HCMainDataMapper hcMainDataMapper;
	
	@Resource
	private InspectionMapper inspectionMapper;
	
	@Resource
	private GodownEntryMapper godownEntryMapper;
	
	@Resource
	private GodownEntryDetailMapper godownEntryDetailMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private ReturnedPurchaseMapper returnedPurchaseMapper;
	
	/**
	 * 收货分页查询
	 */
	@Override
	public Map<String, Object> queryPage(ReceiptSheet receipt) {
		String id = receipt.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		receipt.setId(null);
		PageHelper.startPage(receipt.getPagination());
		List<ReceiptSheet> list = receiptSheetMapper.queryPage(receipt);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			//分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ReceiptSheet param = new ReceiptSheet();
					param.setId(id);
					List<ReceiptSheet> newRecordList = receiptSheetMapper.queryPage(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			setInspection(list);
			return PageUtil.pack4PageHelper(list, receipt.getPagination());
		}else{
			List<ReceiptSheet> newRecordList = new ArrayList<ReceiptSheet>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ReceiptSheet param = new ReceiptSheet();
				param.setId(id);
				newRecordList = receiptSheetMapper.queryPage(param);
				if(newRecordList != null && newRecordList.size() == 1){
					setInspection(list);
					return PageUtil.pack(1, newRecordList, receipt.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, receipt.getPagination());
		}
	}

	/**
	 * 收货页面部件分页查询
	 */
	@Override
	public Map<String, Object> queryComponentPage(BaseEntity baseEntity) {
		
		Integer type = Integer.parseInt(String.valueOf(baseEntity.getParamsMap().get("type")));	// 收货类型
		
		PageHelper.startPage(baseEntity.getPagination());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 采购
		if(ReceivingTypeEnum.PURCHASE.getId().equals(type)){
			list = receiptSheetMapper.queryPurchasePage(baseEntity);
		}
		// 送修
		else if(ReceivingTypeEnum.REPAIR.getId().equals(type)){
			list = receiptSheetMapper.queryRepairPage(baseEntity);
		}
		// 借入
		else if(ReceivingTypeEnum.BORROW.getId().equals(type)){
			list = receiptSheetMapper.queryBorrowPage(baseEntity);
		}
		return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
	}

	/**
	 * 保存收货单
	 * @throws SaibongException 
	 * @throws BusinessException 
	 */
	@Override
	public String save(ReceiptSheet receipt) throws SaibongException, BusinessException {
		User user = ThreadVarUtil.getUser();
		receipt.setZt(1);
		receipt.setZdrid(user.getId());
		receipt.setZdsj(new Date());
		
		Integer type = null;
		if(ReceivingTypeEnum.PURCHASE.getId().equals(receipt.getShlx())){
			type = PartSnValidationEnum.PURCHASE.getId();
		}else if(ReceivingTypeEnum.REPAIR.getId().equals(receipt.getShlx())){
			type = PartSnValidationEnum.REPAIR.getId();
		}else if(ReceivingTypeEnum.BORROW.getId().equals(receipt.getShlx())){
			type = PartSnValidationEnum.BORROW.getId();
		}else if(ReceivingTypeEnum.RETURN.getId().equals(receipt.getShlx())){
			type = PartSnValidationEnum.LENDRETURN.getId();
		}else if(ReceivingTypeEnum.OTHER.getId().equals(receipt.getShlx())){
			type = PartSnValidationEnum.OTHER.getId();
		}
		
		// 收货单
		if(StringUtils.isBlank(receipt.getId())){	// 新增收货单
			receipt.setId(UUID.randomUUID().toString());
			receipt.setShdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.SHD.getName()));
			receiptSheetMapper.insertSelective(receipt);
		}else{	// 修改收货单
			receiptSheetMapper.updateByPrimaryKeySelective(receipt);	
		}
		// 收货单详细
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			detail.setZt(1);
			detail.setWhrid(user.getId());
			detail.setWhsj(new Date());
			
			//验证序列号重复性
			HCMainData hcMainData = hcMainDataMapper.selectById(detail.getBjid());
			if(hcMainData.getGljb().intValue() == SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue()){
				if(StringUtils.isBlank(detail.getSn())){
					throw new BusinessException("序列号不能为空");
				}
				stockService.getCount4ValidationBjAndXlh(detail.getId(), hcMainData.getBjh(), detail.getSn(), receipt.getDprtcode(), type);
			}
			
			if(StringUtils.isBlank(detail.getId())){	// 新增收货单详细
				detail.setId(UUID.randomUUID().toString());
				detail.setMainid(receipt.getId());
				detail.setKcllid(UUID.randomUUID().toString());
				receiptSheetDetailMapper.insertSelective(detail);
			}else{	// 修改收货单详细
				receiptSheetDetailMapper.updateByPrimaryKeySelective(detail);
			}
		}
		// 删除收货单详细
		receiptSheetDetailMapper.deleteNotExist(receipt);
		return receipt.getId();
	}

	/**
	 * 根据id查询收货数据
	 */
	@Override
	public ReceiptSheet queryById(String id) {
		return receiptSheetMapper.queryById(id);
	}

	/**
	 * 根据id查询单表
	 */
	@Override
	public ReceiptSheet selectByPrimaryKey(String id) {
		return receiptSheetMapper.selectByPrimaryKey(id);
	}

	/**
	 * 提交收货单
	 * @throws SaibongException 
	 * @throws BusinessException 
	 */
	@Override
	public String doSubmit(ReceiptSheet receipt) throws SaibongException, BusinessException {
		// 保存收货单
		save(receipt);
		// 生效收货单
		takeEffect(receipt.getId());
		return receipt.getId();
	}
	
	/**
	 * 收货单生效
	 * @param receiptId
	 * @throws SaibongException 
	 * @throws BusinessException 
	 */
	private void takeEffect(String receiptId) throws SaibongException, BusinessException{
		// 1.获取收货单对象
		ReceiptSheet receipt = queryById(receiptId);
		// 2.更新收货单主单、详细单的状态
		updateReceiptStatus(receipt);
		// 3.新增、更新库存、库存履历数据
		updateStock(receipt);
		// 4.按照收货类型更新其他单据
		updateRelevantBill(receipt);
		// 5.新增质检单或入库单
		insertInspectionOrGodownEntry(receipt);
	}
	
	/**
	 * 更新收货单状态
	 * @param receipt
	 */
	private void updateReceiptStatus(ReceiptSheet receipt){
		// 更新收货单主单状态
		User user = ThreadVarUtil.getUser();
		receipt.setZt(2);
		receipt.setZdrid(user.getId());
		receipt.setZdsj(new Date());
		receipt.setCzls(UUID.randomUUID().toString());
		receiptSheetMapper.updateByPrimaryKeySelective(receipt);	
		
		// 更新收货单详细单状态
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			detail.setZt(2);
			detail.setWhrid(user.getId());
			detail.setWhsj(new Date());
			receiptSheetDetailMapper.updateByPrimaryKeySelective(detail);
		}
	}
	
	/**
	 * 更新收货单（撤销）状态
	 * @param receipt
	 */
	private void revokeReceiptStatus(ReceiptSheet receipt){
		// 更新收货单主单状态
		User user = ThreadVarUtil.getUser();
		if(receipt.getZt() == 1){
			receipt.setZt(8);
		}else if(receipt.getZt() == 2){
			receipt.setZt(11);
		}
		receipt.setZdrid(user.getId());
		receipt.setZdsj(new Date());
		receipt.setCzls(UUID.randomUUID().toString());
		receiptSheetMapper.updateByPrimaryKeySelective(receipt);	
		
		// 更新收货单详细单状态
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			detail.setZt(11);
			detail.setWhrid(user.getId());
			detail.setWhsj(new Date());
			receiptSheetDetailMapper.updateByPrimaryKeySelective(detail);
		}
	}
	
	/**
	 * 新增、更新库存、库存履历数据
	 * @param receipt
	 */
	private void updateStock(ReceiptSheet receipt){
		User user = ThreadVarUtil.getUser();
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			// 查询库存履历
			MaterialHistory history = materialHistoryMapper.selectByPrimaryKey(detail.getKcllid());
			if(history == null){	// 新增
				Stock stock = new Stock();
				stock.setId(UUID.randomUUID().toString());
				stock.setDprtcode(receipt.getDprtcode());
				
				Storage storage = storageMapper.selectByIdWithRel(detail.getKwid());
				stock.setCklb(storage.getCklb());
				stock.setCkh(storage.getCkh());
				stock.setCkid(storage.getStore().getId());
				stock.setCkmc(storage.getStore().getCkmc());
				stock.setKwid(storage.getId());
				stock.setKwh(storage.getKwh());
				
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
				stock.setKcsl(detail.getSl());
				stock.setDjsl(BigDecimal.ZERO);
				
				try {
					stock.setGrn(SNGeneratorFactory.generate(stock.getDprtcode(), SaiBongEnum.HC_GRN.getName(), stock));
				} catch (com.eray.framework.exception.SaibongException e) {
					e.printStackTrace();
				}
				
				if(ReceivingTypeEnum.PURCHASE.getId().equals(receipt.getShlx())){	// 采购
					stock.setCghtid(receipt.getXgdjid());
					stock.setHtbhCg(receipt.getXgdjbh());
				}else if(ReceivingTypeEnum.REPAIR.getId().equals(receipt.getShlx())){	// 送修
					stock.setSxhtid(receipt.getXgdjid());
					stock.setHtbhSx(receipt.getXgdjbh());
				}
				
				stock.setRksj(receipt.getShrq());
				stock.setRkbmid(receipt.getShbmid());
				stock.setRkrid(receipt.getShrid());
				stock.setZt(2);
				stock.setWhrid(user.getId());
				stock.setWhsj(new Date());
				// 新增库存
				stockMapper.insertSelective(stock);
				// 插入日志
				materialRecService.writeStockRec(stock.getId(), receipt.getCzls(), receipt.getId(), receipt.getShdh(),
						StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.SAVE, ReceivingTypeEnum.getName(receipt.getShlx()),"",
						receipt.getShdh(), receipt.getXgdjbh(), detail.getSl());
				// 新增库存履历
				materialHistoryMapper.insertSelective(convertBean(stock, detail.getKcllid()));
			}else{	// 修改
				Stock stock = stockMapper.selectById(history.getKcid());
				
				Storage storage = storageMapper.selectByIdWithRel(detail.getKwid());
				stock.setKwid(storage.getId());
				stock.setKwh(storage.getKwh());
				
				stock.setPch(detail.getPch());
				stock.setSn(detail.getSn());
				
				stock.setRksj(receipt.getShrq());
				stock.setRkbmid(receipt.getShbmid());
				stock.setRkrid(receipt.getShrid());
				stock.setWhrid(user.getId());
				stock.setWhsj(new Date());
				// 修改库存
				stockMapper.updateByPrimaryKeySelective(stock);
				// 插入日志
				materialRecService.writeStockRec(stock.getId(), receipt.getCzls(), receipt.getId(), receipt.getShdh(),
						StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.UPDATE, ReceivingTypeEnum.getName(receipt.getShlx()),"",
						receipt.getShdh(), receipt.getXgdjbh(), detail.getSl());
				// 修改库存履历
				materialHistoryMapper.updateByPrimaryKeySelective(convertBean(stock, history));
			}
		}
	}
	
	/**
	 * 撤销库存
	 * @param receipt
	 */
	private void revokeStock(ReceiptSheet receipt){
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			// 查询库存履历
			MaterialHistory history = materialHistoryMapper.selectByPrimaryKey(detail.getKcllid());
			if(history != null){
				// 插入日志
				materialRecService.writeStockRec(history.getKcid(), receipt.getCzls(), receipt.getId(), receipt.getShdh(),
						StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.DELETE, ReceivingTypeEnum.getName(receipt.getShlx()),"撤销",
						receipt.getShdh(), receipt.getXgdjbh(), detail.getSl());
				// 删除库存
				Stock stock = new Stock();
				stock.setId(history.getKcid());
				stockMapper.delete(stock);
				// 删除库存履历
				materialHistoryMapper.deleteByPrimaryKey(detail.getKcllid());
			}
		}
	}
	

	/**
	 * 库存对象转化为库存履历对象
	 * @param stock
	 * @param kcllid
	 * @return
	 */
	private MaterialHistory convertBean(Stock stock, String kcllid){
		MaterialHistory history = new MaterialHistory();
		history.setId(kcllid);
		history.setKcid(stock.getId());
		history.setDprtcode(stock.getDprtcode());
		
		history.setCkid(stock.getCkid());
		history.setCklb(stock.getCklb());
		history.setCkh(stock.getCkh());
		history.setCkmc(stock.getCkmc());
		history.setKwid(stock.getKwid());
		history.setKwh(stock.getKwh());
		
		history.setBjid(stock.getBjid());
		history.setBjh(stock.getBjh());
		history.setPch(stock.getPch());
		history.setSn(stock.getSn());
		history.setZwms(stock.getZwms());
		history.setYwms(stock.getYwms());
		history.setJldw(stock.getJldw());
		history.setXh(stock.getXh());
		history.setGg(stock.getGg());
		history.setKcsl(stock.getKcsl());
		history.setDjsl(stock.getDjsl());
		
		history.setCghtid(stock.getCghtid());
		history.setHtbhCg(stock.getHtbhCg());
		history.setSxhtid(stock.getSxhtid());
		history.setHtbhSx(stock.getHtbhSx());
		
		history.setRksj(stock.getRksj());
		history.setZt(stock.getZt());
		history.setRkbmid(stock.getRkbmid());
		history.setRkrid(stock.getRkrid());
		
		history.setWhsj(stock.getWhsj());
		history.setWhrid(stock.getWhrid());
		history.setGrn(stock.getGrn());
		return history;
	}
	
	/**
	 * 库存对象转化为库存履历对象
	 * @param stock
	 * @param history
	 * @return
	 */
	private MaterialHistory convertBean(Stock stock, MaterialHistory history){
		history.setKwid(stock.getKwid());
		history.setKwh(stock.getKwh());
		
		history.setPch(stock.getPch());
		history.setSn(stock.getSn());
		history.setDjsl(stock.getDjsl());
		
		history.setRksj(stock.getRksj());
		history.setRkbmid(stock.getRkbmid());
		history.setRkrid(stock.getRkrid());
		
		history.setWhsj(stock.getWhsj());
		history.setWhrid(stock.getWhrid());
		history.setGrn(stock.getGrn());
		return history;
	}
	
	/**
	 * 按照收货类型更新其他单据
	 * @throws BusinessException 
	 */
	private void updateRelevantBill(ReceiptSheet receipt) throws BusinessException{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			paramMap.put("xgdjid", detail.getXgdjid());
			paramMap.put("dprtcode", receipt.getDprtcode());
			paramMap.put("jddxid", receipt.getJddxid());
			paramMap.put("sl", detail.getSl());
			// 采购
			if(ReceivingTypeEnum.PURCHASE.getId().equals(receipt.getShlx())){
				paramMap.put("htid", receipt.getXgdjid());
				// 更新合同明细到货数量
				receiptSheetMapper.updateContractDhsl(paramMap);
				// 更新合同到货数量状态
				receiptSheetMapper.updateContractDhslZt(paramMap);
			}
			// 送修
			else if(ReceivingTypeEnum.REPAIR.getId().equals(receipt.getShlx())){
				paramMap.put("htid", receipt.getXgdjid());
				// 更新合同明细到货数量
				receiptSheetMapper.updateContractDhsl(paramMap);
				// 更新合同到货数量状态
				receiptSheetMapper.updateContractDhslZt(paramMap);
				// 找到外派清单
				paramMap.put("jdlx", 3);
				paramMap.put("bjid", detail.getBjid());
				paramMap.put("sn", detail.getSn());
				Expatriate expatriate = receiptSheetMapper.findExpatriateByXlh(paramMap);
				if(expatriate != null){
					// 更新外派清单数据
					paramMap.put("wpqdid", expatriate.getId());
					receiptSheetMapper.updateExpatriateGhsl(paramMap);
					// 新增外派清单对应明细
					insertExpatriateDetail(paramMap, detail, receipt, expatriate.getId(), 2);
				}else{
					throw new BusinessException("送修航材未出库，不可提交");
				}
			}
			// 借入
			else if(ReceivingTypeEnum.BORROW.getId().equals(receipt.getShlx())){
				if(StringUtils.isNotBlank(receipt.getXgdjid())){
					// 更新借入申请单状态
					receiptSheetMapper.updateBorrowApplySl(paramMap);
					// 新增外派清单
					String wpqdid = insertExpatriate(paramMap, detail, receipt, 2);
					// 新增外派清单详细
					insertExpatriateDetail(paramMap, detail, receipt, wpqdid, 1);
				}
			}
			// 借出归还
			else if(ReceivingTypeEnum.RETURN.getId().equals(receipt.getShlx())){
				// 找到外派清单
				paramMap.put("jdlx", 1);
				paramMap.put("bjid", detail.getBjid());
				List<Expatriate> expatriates = receiptSheetMapper.findExpatriateByJh(paramMap);
				
				BigDecimal total = detail.getSl();
				for (Expatriate expatriate : expatriates) {
					if(total.compareTo(BigDecimal.ZERO) > 0){
						paramMap.put("wpqdid", expatriate.getId());
						BigDecimal current = expatriate.getKcsl();
						BigDecimal substract = BigDecimal.ZERO;
						if(total.compareTo(current) >= 0){
							substract = current;
							total = total.subtract(current);
						}else{
							substract = total;
							total = BigDecimal.ZERO;
						}
						paramMap.put("sl", substract);
						// 更新外派清单数据
						receiptSheetMapper.updateExpatriateGhsl(paramMap);
						// 新增外派清单对应明细
						insertExpatriateDetail(paramMap, insertKcll(receipt, detail, substract), receipt, expatriate.getId(), 1);
					}
				}
			}
			// 检查单据的到货数量是否超出单据数量
			checkReceiptCount(receipt, paramMap);
		}
	}
	
	/**
	 * 插入库存履历（记录借出归还的数量，用于撤销）
	 * @param detail
	 * @return
	 */
	private ReceiptSheetDetail insertKcll(ReceiptSheet receipt, ReceiptSheetDetail detail, BigDecimal number){
		MaterialHistory history = new MaterialHistory();
		history.setId(UUID.randomUUID().toString());
		history.setDprtcode(receipt.getDprtcode());
		
		Storage storage = storageMapper.selectByIdWithRel(detail.getKwid());
		history.setCklb(storage.getCklb());
		history.setCkh(storage.getCkh());
		history.setCkid(storage.getStore().getId());
		history.setCkmc(storage.getStore().getCkmc());
		history.setKwid(storage.getId());
		history.setKwh(storage.getKwh());
		
		HCMainData hcMainData = hcMainDataMapper.selectById(detail.getBjid());
		history.setBjid(hcMainData.getId());
		history.setBjh(hcMainData.getBjh());
		history.setPch(detail.getPch());
		history.setSn(detail.getSn());
		history.setZwms(hcMainData.getZwms());
		history.setYwms(hcMainData.getYwms());
		history.setJldw(hcMainData.getJldw());
		history.setXh(hcMainData.getXingh());
		history.setGg(hcMainData.getGg());
		history.setKcsl(number);
		history.setDjsl(BigDecimal.ZERO);
		
		history.setRksj(receipt.getShrq());
		history.setRkbmid(receipt.getShbmid());
		history.setRkrid(receipt.getShrid());
		history.setZt(2);
		history.setWhrid(ThreadVarUtil.getUser().getId());
		history.setWhsj(new Date());
		
		materialHistoryMapper.insertSelective(history);
		
		detail.setKcllid(history.getId());
		return detail;
	}
	
	/**
	 * 检查单据的到货数量是否超出单据数量
	 * @param receipt
	 * @throws BusinessException
	 */
	private void checkReceiptCount(ReceiptSheet receipt, Map<String, Object> paramMap) throws BusinessException{
		// 采购或送修
		if(ReceivingTypeEnum.PURCHASE.getId().equals(receipt.getShlx()) || 
				ReceivingTypeEnum.REPAIR.getId().equals(receipt.getShlx())){
			Contract contract = receiptSheetMapper.checkContractDhsl(paramMap);
			if(contract != null){
				throw new BusinessException("合同"+contract.getHtlsh()+"的到货数量超出合同数量，请检查数据");
			}
		}
		// 借入
		else if(ReceivingTypeEnum.BORROW.getId().equals(receipt.getShlx())){
			if(StringUtils.isNotBlank(receipt.getXgdjid())){
				BorrowApply borrowApply = receiptSheetMapper.checkBorrowApplySl(paramMap);
				if(borrowApply != null){
					throw new BusinessException("借入申请"+borrowApply.getSqdh()+"的到货数量超出审核数量，请检查数据");
				}
			}
		}
	}
	
	/**
	 * 新增质检单或入库单
	 * @param receipt
	 * @throws SaibongException 
	 */
	private void insertInspectionOrGodownEntry(ReceiptSheet receipt) throws SaibongException{
		User user = ThreadVarUtil.getUser();
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			if(detail.getIsZj() == 1){
				Inspection ins = new Inspection();
				ins.setId(UUID.randomUUID().toString());
				ins.setDprtcode(receipt.getDprtcode());
				ins.setJydh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCJY.getName()));
				ins.setShdid(receipt.getId());
				ins.setShdmxid(detail.getId());
				ins.setKysl(detail.getSl());
				ins.setZt(1);
				ins.setZdbmid(receipt.getShbmid());
				ins.setZdrid(receipt.getShrid());
				ins.setZdsj(new Date());
				inspectionMapper.insertSelective(ins);
			}else{
				GodownEntry gd = new GodownEntry();
				gd.setId(UUID.randomUUID().toString());
				gd.setDprtcode(receipt.getDprtcode());
				gd.setRklx(receipt.getShlx());
				gd.setRkdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName()));
				gd.setRkbmid(receipt.getShbmid());
				gd.setRukid(receipt.getShrid());
				gd.setRkrq(receipt.getShrq());
				gd.setFhdw(receipt.getFhdw());
				gd.setZt(1);
				gd.setShdid(receipt.getId());
				gd.setShdmxid(detail.getId());
				gd.setZdbmid(receipt.getShbmid());
				gd.setZdrid(receipt.getShrid());
				gd.setZdsj(new Date());
				godownEntryMapper.insertSelective(gd);
				
				GodownEntryDetail gdDetail = new GodownEntryDetail();
				gdDetail.setId(UUID.randomUUID().toString());
				gdDetail.setMainid(gd.getId());
				gdDetail.setKcllid(detail.getKcllid());
				gdDetail.setZt(1);
				gdDetail.setWhdwid(user.getBmdm());
				gdDetail.setWhrid(user.getId());
				gdDetail.setWhsj(new Date());
				godownEntryDetailMapper.insertSelective(gdDetail);
			}
		}
	}
	
	/**
	 * 撤销质检单或入库单
	 * @param receipt
	 */
	private void revokeInspectionOrGodownEntry(ReceiptSheet receipt) {
		User user = ThreadVarUtil.getUser();
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			if(detail.getIsZj() == 1){
				Inspection ins = new Inspection();
				ins.setShdmxid(detail.getId());
				ins.setZdbmid(receipt.getShbmid());
				ins.setZdrid(receipt.getShrid());
				ins.setZdsj(new Date());
				receiptSheetMapper.revokeInspection(ins);
			}
			
			GodownEntry gd = new GodownEntry();
			gd.setShdmxid(detail.getId());
			gd.setZdbmid(receipt.getShbmid());
			gd.setZdrid(receipt.getShrid());
			gd.setZdsj(new Date());
			receiptSheetMapper.revokeGodownEntry(gd);
			
			
			GodownEntryDetail gdDetail = new GodownEntryDetail();
			gdDetail.setKcllid(detail.getKcllid());
			gdDetail.setWhdwid(user.getBmdm());
			gdDetail.setWhrid(user.getId());
			gdDetail.setWhsj(new Date());
			receiptSheetMapper.revokeGodownEntryDetail(gdDetail);
		}
	}
	
	/**
	 * 按照收货类型撤销其他单据数据
	 * @param receipt
	 */
	private void revokeRelevantBill(ReceiptSheet receipt){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("revoke", 1);
		for (ReceiptSheetDetail detail : receipt.getDetails()) {
			paramMap.put("xgdjid", detail.getXgdjid());
			paramMap.put("dprtcode", receipt.getDprtcode());
			paramMap.put("jddxid", receipt.getJddxid());
			paramMap.put("sl", detail.getSl());
			paramMap.put("djid", detail.getId());
			// 采购
			if(ReceivingTypeEnum.PURCHASE.getId().equals(receipt.getShlx())){
				paramMap.put("htid", receipt.getXgdjid());
				// 更新合同明细到货数量
				receiptSheetMapper.updateContractDhsl(paramMap);
				// 更新合同到货数量状态
				receiptSheetMapper.updateContractDhslZt(paramMap);
			}
			// 送修
			else if(ReceivingTypeEnum.REPAIR.getId().equals(receipt.getShlx())){
				paramMap.put("htid", receipt.getXgdjid());
				// 更新合同明细到货数量
				receiptSheetMapper.updateContractDhsl(paramMap);
				// 更新合同到货数量状态
				receiptSheetMapper.updateContractDhslZt(paramMap);
				// 找到外派清单
				paramMap.put("jdlx", 3);
				paramMap.put("bjid", detail.getBjid());
				paramMap.put("sn", detail.getSn());
				// 查询外派清单详细数据
				ExpatriateDetail expatriateDetail = receiptSheetMapper.findExpatriateDetail(paramMap);
				if(expatriateDetail != null){
					// 更新外派清单数据
					paramMap.put("wpqdid", expatriateDetail.getMainid());
					receiptSheetMapper.updateExpatriateGhsl(paramMap);
					// 删除外派清单对应明细
					deleteExpatriateDetail(paramMap, expatriateDetail.getId());
				}
			}
			// 借入
			else if(ReceivingTypeEnum.BORROW.getId().equals(receipt.getShlx())){
				if(StringUtils.isNotBlank(receipt.getXgdjid())){
					// 更新借入申请单状态
					receiptSheetMapper.updateBorrowApplySl(paramMap);
					// 查询外派清单详细数据
					ExpatriateDetail expatriateDetail = receiptSheetMapper.findExpatriateDetail(paramMap);
					if(expatriateDetail != null){
						// 删除外派清单
						deleteExpatriate(paramMap, expatriateDetail.getMainid());
						// 删除外派清单详细
						deleteExpatriateDetail(paramMap, expatriateDetail.getId());
					}
				}
			}
			// 借出归还
			else if(ReceivingTypeEnum.RETURN.getId().equals(receipt.getShlx())){
				// 找到外派清单
				paramMap.put("jdlx", 1);
				paramMap.put("bjid", detail.getBjid());
				// 查询外派清单详细数据
				List<ExpatriateDetail> expatriateDetails = receiptSheetMapper.findExpatriateDetailByDjid(paramMap);
				for (ExpatriateDetail expatriateDetail : expatriateDetails) {
					// 获取库存履历
					MaterialHistory history = materialHistoryMapper.selectByPrimaryKey(expatriateDetail.getKcllid());
					// 更新外派清单数据
					paramMap.put("wpqdid", expatriateDetail.getMainid());
					paramMap.put("sl", history.getKcsl());
					receiptSheetMapper.updateExpatriateGhsl(paramMap);
					// 删除外派清单对应明细
					deleteExpatriateDetail(paramMap, expatriateDetail.getId());
				}
			}
		}
	}
	
	/**
	 * 新增外派对象
	 * @param paramMap
	 * @param detail
	 * @param receipt
	 */
	private String insertExpatriate(Map<String, Object> paramMap, 
			ReceiptSheetDetail detail, ReceiptSheet receipt, int jdlx){
		String id = UUID.randomUUID().toString();
		paramMap.put("wpqdid", id);
		paramMap.put("jdlx", jdlx);
		paramMap.put("bjid", detail.getBjid());
		HCMainData hcMainData = hcMainDataMapper.selectById(detail.getBjid());
		paramMap.put("bjh", hcMainData.getBjh());
		paramMap.put("sn", detail.getSn());
		paramMap.put("pch", detail.getPch());
		paramMap.put("kcsl", detail.getSl());
		paramMap.put("wpsj", receipt.getShrq());
		paramMap.put("ghsl", 0);
		paramMap.put("zt", 1);
		receiptSheetMapper.insertExpatriate(paramMap);
		return id;
	}
	
	/**
	 * 新增外派对象详细
	 * @param paramMap
	 * @param detail
	 * @param receipt
	 */
	private void insertExpatriateDetail(Map<String, Object> paramMap, 
			ReceiptSheetDetail detail, ReceiptSheet receipt, String mainid, int sjlx){
		paramMap.put("wpqdxxid", UUID.randomUUID().toString());
		paramMap.put("mainid", mainid);
		paramMap.put("sjlx", sjlx);
		paramMap.put("gldjlx", 3);
		paramMap.put("djid", detail.getId());
		paramMap.put("kcllid", detail.getKcllid());
		receiptSheetMapper.insertExpatriateDetail(paramMap);
	}
	
	/**
	 * 删除外派对象
	 * @param paramMap
	 * @param detail
	 * @param receipt
	 */
	private void deleteExpatriate(Map<String, Object> paramMap, String id){
		paramMap.put("wpqdid", id);
		receiptSheetMapper.deleteExpatriate(paramMap);
	}
	
	/**
	 * 删除外派对象详细
	 * @param paramMap
	 * @param detail
	 * @param receipt
	 */
	private void deleteExpatriateDetail(Map<String, Object> paramMap, String id){
		paramMap.put("wpqdxxid", id);
		receiptSheetMapper.deleteExpatriateDetail(paramMap);
	}

	/**
	 * 撤销收货单
	 */
	@Override
	public void doRevoke(String receiptId) {
		// 1.获取收货单对象
		ReceiptSheet receipt = queryById(receiptId);
		// 2.验证是否可以撤销收货单
		revokeValidate(receipt);
		// 3.更新收货单主单、详细单的状态
		revokeReceiptStatus(receipt);
		// 4.删除库存、库存履历数据
		revokeStock(receipt);
		// 5.按照收货类型撤销其他单据数据
		revokeRelevantBill(receipt);
		// 6.撤销质检单或入库单
		revokeInspectionOrGodownEntry(receipt);
	}
	
	/**
	 * 作废收货单
	 */
	@Override
	public void doScrap(String receiptId) {
		// 1.获取收货单对象
		ReceiptSheet receipt = queryById(receiptId);
		// 2.更新收货单主单、详细单的状态
		revokeReceiptStatus(receipt);
	}
	
	/**
	 * 验证是否可以撤销收货单
	 * @param receipt
	 */
	private void revokeValidate(ReceiptSheet receipt){
		if(receiptSheetMapper.findInstockCount(receipt) > 0){
			throw new SystemException("收货单中的部件已入库，无法撤销！");
		}
		if(receiptSheetMapper.findIncompleteStockCount(receipt) > 0){
			throw new SystemException("收货单中的部件已不在库存中，无法撤销！");
		}
	}
	
	/**
	 * @author liub
	 * @description 设置检验单
	 */
	private void setInspection(List<ReceiptSheet> list){
		if(list == null || list.size() == 0){
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();//参数
		List<String> idList = new ArrayList<String>();
		for (ReceiptSheet rs : list) {
			idList.add(rs.getId());
		}
		paramMap.put("idList", idList);
		List<Inspection> inspectionList = inspectionMapper.queryByIdList(paramMap);
		List<ReturnedPurchase> returnList=returnedPurchaseMapper.queryByIds(paramMap);
		if(inspectionList.size()>0){
			Map<String, List<Inspection>> map=new HashMap<String, List<Inspection>>();
			for (Inspection i : inspectionList) {
				List<Inspection> resultList = map.get(i.getShdid());
				if(resultList == null){
					resultList = new ArrayList<Inspection>();
					map.put(i.getShdid(), resultList);
				}
				resultList.add(i);
			}
			for (ReceiptSheet rs : list) {
				rs.setInspections(map.get(rs.getId()));
			}
		}
		if(returnList.size()>0){
			Map<String, List<ReturnedPurchase>> map=new HashMap<String, List<ReturnedPurchase>>();
			for (ReturnedPurchase i : returnList) {
				List<ReturnedPurchase> resultList = map.get(i.getShdid());
				if(resultList == null){
					resultList = new ArrayList<ReturnedPurchase>();
					map.put(i.getShdid(), resultList);
				}
				resultList.add(i);
			}
			for (ReceiptSheet rs : list) {
				rs.setReturnedPurchaseList((map.get(rs.getId())));
			}
		}
	}

}
