package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.GodownEntryMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.OutstockMapper;
import com.eray.thjw.aerialmaterial.dao.ReceiptSheetDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ReceiptSheetMapper;
import com.eray.thjw.aerialmaterial.dao.ReturnedPurchaseDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ReturnedPurchaseMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.aerialmaterial.po.ReceiptSheetDetail;
import com.eray.thjw.aerialmaterial.po.ReturnedPurchase;
import com.eray.thjw.aerialmaterial.po.ReturnedPurchaseDetail;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.GodownEntryDetailService;
import com.eray.thjw.aerialmaterial.service.GodownEntryService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.OutstockDetailService;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.aerialmaterial.service.ReturnedPurchaseService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.ReceivingTypeEnum;

/**
 * 退货
 * @author ll
 *
 */
@Service("returnedPurchaseService")
public class ReturnedPurchaseServiceImpl implements ReturnedPurchaseService {
	
	@Resource
	private ReturnedPurchaseMapper returnedPurchaseMapper;
	@Resource
	private GodownEntryMapper godownEntryMapper;
	
	@Resource
	private ReturnedPurchaseDetailMapper returnedPurchaseDetailMapper;
	
	@Resource
	private OutstockDetailService outstockDetailService;
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private OutstockService outstockService;
	@Resource
	private GodownEntryService godownEntryService;
	
	@Resource
	private OutstockMapper outstockMapper;
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private ReceiptSheetDetailMapper receiptSheetDetailMapper;
	
	@Resource
	private ReceiptSheetMapper receiptSheetMapper;
	@Resource
	private StorageMapper storageMapper;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private HCMainDataMapper hcMainDataMapper;
	@Resource
	private StockMapper stockMapper;
	@Resource
	private GodownEntryDetailService godownEntryDetailService;
	@Override
	public List<ReturnedPurchase> queryAllPageList(ReturnedPurchase returnedPurchase) throws RuntimeException{
		return returnedPurchaseMapper.queryAllPageList(returnedPurchase);
	}

	@Override
	public ReturnedPurchase selectByPrimaryKey(String id) throws RuntimeException {
		return returnedPurchaseMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @author ll
	 * @description 验证借入单操作权限
	 * @param id
	 * @return 
	 * @develop date 2016.09.20
	 */
	private void validatePri(String id) throws RuntimeException{
		User user = ThreadVarUtil.getUser();
		ReturnedPurchase obj = returnedPurchaseMapper.selectByPrimaryKey(id);
		//判空
		if(null == obj){
			throw new RuntimeException("对不起,表单数据不存在!");
		}
	}
	

	@Override
	public void cancel(ReturnedPurchase returnedPurchase) throws RuntimeException {
		returnedPurchaseMapper.updateByPrimaryKeySelective(returnedPurchase);
		
	}

	@Override
	public void updateByPrimaryKeySelective(ReturnedPurchase returnedPurchase)
			throws RuntimeException {
		returnedPurchaseMapper.updateByPrimaryKeySelective(returnedPurchase);
	}

	@Override
	public ReturnedPurchase selectById(String id) throws RuntimeException {
		return returnedPurchaseMapper.selectByPrimaryKey(id);
	}

	@Override
	public String save(ReturnedPurchase returnedPurchase) throws RuntimeException, BusinessException {
		
		User user = ThreadVarUtil.getUser();//当前用户明细
		
		String id = UUID.randomUUID().toString();//退货单id
		
		String ckid= UUID.randomUUID().toString();//出库单id
		
		//保存退货单数据,状态为提交
		String thdh=null;
		try {
			thdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.THD.getName());
		} catch (SaibongException e) {
			e.printStackTrace();
		}
		returnedPurchase.setId(id);
		returnedPurchase.setZt(ReserveStatusEnum.SAVE.getId());
		returnedPurchase.setZdsj(new Date());
		returnedPurchase.setZdrid(user.getId());
		returnedPurchase.setZdbmid(user.getBmdm());
		returnedPurchase.setDprtcode(user.getJgdm());
		returnedPurchase.setCkdid(ckid);
		returnedPurchase.setThdh(thdh);
		returnedPurchaseMapper.insertSelective(returnedPurchase);
		
				//新增或编辑详情信息
				for(ReturnedPurchaseDetail returnedPurchaseDetail: returnedPurchase.getReturnedPurchaseDetailList()){
					
					if(null != returnedPurchaseDetail.getId() && !"".equals(returnedPurchaseDetail.getId())){
						
					}else{
						
						//根据前台存储的库存id操作库存表
						Stock stock=stockSerivce.queryById(returnedPurchaseDetail.getMaterialHistory().getKcid());
				
						//新增库存履历
						String historyId=updateMaterialHistory(stock,returnedPurchaseDetail.getSl());//返回库存履历id
						
						//新增退货表详细
						String thxqid = UUID.randomUUID().toString();//退货详情id
						returnedPurchaseDetail.setId(thxqid);
						returnedPurchaseDetail.setMainid(id);
						returnedPurchaseDetail.setKcllid(historyId);
						returnedPurchaseDetail.setSl(returnedPurchaseDetail.getSl());
						returnedPurchaseDetail.setZt(1);
						returnedPurchaseDetail.setWhrid(user.getId());
						returnedPurchaseDetail.setWhsj(new Date());
						returnedPurchaseDetailMapper.insertSelective(returnedPurchaseDetail);
					}
				}
				
		return id;
	}

	@Override
	public String submit(ReturnedPurchase returnedPurchase) throws RuntimeException, BusinessException {
		
		User user = ThreadVarUtil.getUser();//当前用户明细
		
		String id = UUID.randomUUID().toString();//退货单id
		
		String ckid= UUID.randomUUID().toString();//出库单id
		
		//保存退货单数据,状态为提交
		String thdh=null;
		try {
			thdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.THD.getName());
		} catch (SaibongException e) {
			e.printStackTrace();
		}
		returnedPurchase.setId(id);
		returnedPurchase.setZt(ReserveStatusEnum.SUBMIT.getId());
		returnedPurchase.setZdsj(new Date());
		returnedPurchase.setZdrid(user.getId());
		returnedPurchase.setZdbmid(user.getBmdm());
		returnedPurchase.setDprtcode(user.getJgdm());
		returnedPurchase.setCkdid(ckid);
		returnedPurchase.setThdh(thdh);
		returnedPurchaseMapper.insertSelective(returnedPurchase);
		
				for(ReturnedPurchaseDetail returnedPurchaseDetail: returnedPurchase.getReturnedPurchaseDetailList()){
					
					if(null != returnedPurchaseDetail.getId() && !"".equals(returnedPurchaseDetail.getId())){
						
					}else{
						
						//根据前台存储的库存id操作库存表
						Stock stock=stockSerivce.queryById(returnedPurchaseDetail.getMaterialHistory().getKcid());
						
						//根据库存id和库存类型  查询库存履历id
						List<MaterialHistory> materialHistory1=materialHistoryService.selectKey(stock.getId(),user.getJgdm());
						
						String godownEntryDetailId="";
						String receiptSheetDetailId="";
						if(materialHistory1!=null){
							//根据多个库存履历id和多个收货单里匹配
							for (MaterialHistory materialHistory : materialHistory1) {
							
								if("2".equals(stock.getCklb())){
									throw new BusinessException("当前仓库不能是报废仓库");
								}
								
								//1.根据库存履历id查询收货单信息 
								ReceiptSheetDetail receiptSheetDetail=new ReceiptSheetDetail();
								receiptSheetDetail.setKcllid(materialHistory.getId());
								ReceiptSheetDetail receiptSheetDetail1=receiptSheetDetailMapper.selectByPrimary(receiptSheetDetail);
								
								if(receiptSheetDetail1!=null){
									
									List<ReceiptSheetDetail> receiptSheetDetaillist =receiptSheetDetailMapper.selectByMaind(receiptSheetDetail1.getMainid());
									// 1.获取第一条收货单信息
									ReceiptSheet receiptNum = receiptSheetMapper.queryById(receiptSheetDetaillist.get(0).getMainid());
									//只查询状态为提交的入库单
									if(2==receiptNum.getZt()){//收货已入库
										// 更新收货单详细单状态为保存，并更新退货数量
										receiptSheetDetail1.setWhrid(user.getId());
										receiptSheetDetail1.setWhsj(new Date());
										receiptSheetDetail1.setThsl(returnedPurchaseDetail.getSl());
										receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
										
										receiptSheetDetailId=receiptSheetDetail1.getId();//给收库明细id复制
										
										Map<String, Object> paramMap = new HashMap<String, Object>();
										
										paramMap.put("xgdjid", receiptSheetDetaillist.get(0).getXgdjid());
										paramMap.put("dprtcode", receiptNum.getDprtcode());
										paramMap.put("jddxid", receiptNum.getJddxid());
										paramMap.put("sl", returnedPurchaseDetail.getSl());
										// 采购
										if(ReceivingTypeEnum.PURCHASE.getId().equals(receiptNum.getShlx())){
											paramMap.put("htid", receiptNum.getXgdjid());
											// 更新合同明细到货数量
											receiptSheetMapper.updateContractRksl(paramMap);
											// 更新合同到货数量状态
											receiptSheetMapper.updateContractDhslZt(paramMap);
										}else if(ReceivingTypeEnum.REPAIR.getId().equals(receiptNum.getShlx())){
											// 送修
											paramMap.put("htid", receiptNum.getXgdjid());
											// 更新合同明细到货数量
											receiptSheetMapper.updateContractRksl(paramMap);
											// 更新合同到货数量状态
											receiptSheetMapper.updateContractDhslZt(paramMap);
											// 找到外派清单
											paramMap.put("jdlx", 3);
											paramMap.put("bjid", receiptSheetDetaillist.get(0).getBjid());
											paramMap.put("sn", receiptSheetDetaillist.get(0).getSn());
											Expatriate expatriate = receiptSheetMapper.findExpatriateByXlh(paramMap);
											if(expatriate != null){
												// 更新外派清单数据
												paramMap.put("wpqdid", expatriate.getId());
												receiptSheetMapper.updateExpatriateGhsl(paramMap);
												// 新增外派清单对应明细
												insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, expatriate.getId(), 2);
											}
										}else if(ReceivingTypeEnum.BORROW.getId().equals(receiptNum.getShlx())){
											// 借入
											if(StringUtils.isNotBlank(receiptNum.getXgdjid())){
												// 更新借入申请单状态
												receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
												// 新增外派清单
												String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 2);
												// 新增外派清单详细
												insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
											}
										}else if(ReceivingTypeEnum.RETURN.getId().equals(receiptNum.getShlx())){
											// 借出归还
											// 更新外派清单状态
											String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 1);
											// 新增外派明细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
										}
									}else{
										//收货未入库
										// 更新收货单详细单状态为保存，并更新退货数量
										receiptSheetDetail1.setWhrid(user.getId());
										receiptSheetDetail1.setWhsj(new Date());
										receiptSheetDetail1.setThsl(returnedPurchaseDetail.getSl());
										receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
										
										receiptSheetDetailId=receiptSheetDetail1.getId();//给收库明细id复制
										
										Map<String, Object> paramMap = new HashMap<String, Object>();
										
										paramMap.put("xgdjid", receiptSheetDetaillist.get(0).getXgdjid());
										paramMap.put("dprtcode", receiptNum.getDprtcode());
										paramMap.put("jddxid", receiptNum.getJddxid());
										paramMap.put("sl", returnedPurchaseDetail.getSl());
										if(ReceivingTypeEnum.BORROW.getId().equals(receiptNum.getShlx())){
											// 借入
											if(StringUtils.isNotBlank(receiptNum.getXgdjid())){
												// 更新借入申请单状态
												receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
												// 新增外派清单
												String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 2);
												// 新增外派清单详细
												insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
											}
										}else if(ReceivingTypeEnum.RETURN.getId().equals(receiptNum.getShlx())){
											// 借出归还
											// 更新外派清单状态
											String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 1);
											// 新增外派明细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
										}
									}
									
								}
							}
						}
				
						//减库存 或者删除、新增库存履历
						String historyId=updateStock(stock,returnedPurchaseDetail.getSl());//返回库存履历id
						
						//保存出库明细表
						String detailId = UUID.randomUUID().toString();//出库明细id
						OutstockDetail outstockDetail=new OutstockDetail();
						outstockDetail.setId(detailId);
						outstockDetail.setMainid(ckid);
						outstockDetail.setKcllid(historyId);
						outstockDetail.setWhdwid(user.getBmdm());
						outstockDetail.setWhrid(user.getId());
						outstockDetail.setWhsj(new Date());
						outstockDetail.setZt(1);
						BigDecimal aDouble =new BigDecimal(0.00);
						outstockDetail.setTksl(aDouble);
						outstockDetailService.insert(outstockDetail); 
						
						//新增退货表详细
						String thxqid = UUID.randomUUID().toString();//退货详情id
						returnedPurchaseDetail.setId(thxqid);
						returnedPurchaseDetail.setMainid(id);
						returnedPurchaseDetail.setKcllid(historyId);
						returnedPurchaseDetail.setShdmxid(receiptSheetDetailId);
						returnedPurchaseDetail.setRkmxid(godownEntryDetailId);
						returnedPurchaseDetail.setCkdmxid(detailId);
						returnedPurchaseDetail.setSl(returnedPurchaseDetail.getSl());
						returnedPurchaseDetail.setZt(1);
						returnedPurchaseDetail.setWhrid(user.getId());
						returnedPurchaseDetail.setWhsj(new Date());
						returnedPurchaseDetailMapper.insertSelective(returnedPurchaseDetail);
					}
				}
				//新增出库单
				Outstock outstock=new Outstock();
				String lyck;
				try {
					lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
				} catch (SaibongException e) {
					throw new BusinessException(e);
				}	
				outstock.setId(ckid);
				outstock.setCklx(6);//出库类型,退货
				outstock.setCkdh(lyck);
				outstock.setZdbmid(user.getBmdm());
				outstock.setSqrid(returnedPurchase.getThrid());
				outstock.setSqsj(returnedPurchase.getThrq());
				outstock.setDprtcode(user.getJgdm());
				outstock.setSgbs(0);
				outstock.setZt(2);
				outstock.setBz(outstock.getBz());
				outstock.setZdrid(user.getId());
				outstock.setCukid(user.getId());
				outstock.setCksj(new Date());
				outstock.setSqbmid(returnedPurchase.getZdbmid());
				outstock.setZdsj(new Date());
				outstockMapper.insertSelective(outstock);
				
		return id;
	}
	

	@Override
	public String updatesave(ReturnedPurchase returnedPurchase) {
		
		List<String> list1 = new ArrayList<String>(); 
		List<String> list2 = new ArrayList<String>(); 
		
		User user = ThreadVarUtil.getUser();
		String ckid= UUID.randomUUID().toString();//出库单id
		
		//修改退货单数据,状态为提交
		returnedPurchase.setZt(ReserveStatusEnum.SAVE.getId());
		returnedPurchase.setCkdid(ckid);
		returnedPurchaseMapper.updateByPrimaryKeySelective(returnedPurchase);
		
		//根据id查询详情数据
		List<ReturnedPurchaseDetail> returnedPurchaseDetailList=returnedPurchaseDetailMapper.selectByMainId(returnedPurchase.getId());
		
		for (ReturnedPurchaseDetail returnedPurchaseDetail : returnedPurchaseDetailList) {
			list1.add(returnedPurchaseDetail.getId());
		}
		
		for (ReturnedPurchaseDetail returnedPurchaseDetail : returnedPurchase.getReturnedPurchaseDetailList()) {
			list2.add(returnedPurchaseDetail.getId());
		}
		
		for (String  id : list1) {
			if(!list2.contains(id)){//修改
				for (ReturnedPurchaseDetail returnedPurchaseDetail : returnedPurchaseDetailList) {
					
					MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(returnedPurchaseDetail.getKcllid());
					
					//修改库存履历
					updateMaterialHistory(returnedPurchaseDetail.getSl(),materialHistory);//返回库存履历id
					
					//新增退货表详细
					returnedPurchaseDetail.setWhrid(user.getId());
					returnedPurchaseDetail.setWhsj(new Date());
					returnedPurchaseDetailMapper.updateByPrimaryKeySelective(returnedPurchaseDetail);
				}
			}else{//新增
				
				for(ReturnedPurchaseDetail returnedPurchaseDetail: returnedPurchase.getReturnedPurchaseDetailList()){
								
					if(null == returnedPurchaseDetail.getId() && "".equals(returnedPurchaseDetail.getId())){
						
						//根据前台存储的库存id操作库存表
						Stock stock=stockSerivce.queryById(returnedPurchaseDetail.getMaterialHistory().getKcid());
				
						//新增库存履历
						String historyId=updateMaterialHistory(stock,returnedPurchaseDetail.getSl());//返回库存履历id
						
						//新增退货表详细
						String thxqid = UUID.randomUUID().toString();//退货详情id
						returnedPurchaseDetail.setId(thxqid);
						returnedPurchaseDetail.setMainid(id);
						returnedPurchaseDetail.setKcllid(historyId);
						returnedPurchaseDetail.setSl(returnedPurchaseDetail.getSl());
						returnedPurchaseDetail.setZt(1);
						returnedPurchaseDetail.setWhrid(user.getId());
						returnedPurchaseDetail.setWhsj(new Date());
						returnedPurchaseDetailMapper.insertSelective(returnedPurchaseDetail);
					}
				}
				
			}
		}
		return returnedPurchase.getId();
	}
	
	@Override
	public String updatesubmit(ReturnedPurchase returnedPurchase) throws RuntimeException, BusinessException {
		
		Map<String, ReturnedPurchaseDetail> dbMap = new TreeMap<String, ReturnedPurchaseDetail>(); 
		Map<String, ReturnedPurchaseDetail> formMap = new TreeMap<String, ReturnedPurchaseDetail>(); 
		
		User user = ThreadVarUtil.getUser();
		String ckid= UUID.randomUUID().toString();//出库单id
		
		//修改退货单数据,状态为提交
		returnedPurchase.setZt(ReserveStatusEnum.SUBMIT.getId());
		returnedPurchase.setCkdid(ckid);
		returnedPurchaseMapper.updateByPrimaryKeySelective(returnedPurchase);
		
		//根据id查询详情数据
		List<ReturnedPurchaseDetail> returnedPurchaseDetailList=returnedPurchaseDetailMapper.selectByMainId(returnedPurchase.getId());
		
		for (ReturnedPurchaseDetail returnedPurchaseDetail : returnedPurchaseDetailList) {
			dbMap.put(returnedPurchaseDetail.getId(), returnedPurchaseDetail);
		}
		
		for (ReturnedPurchaseDetail returnedPurchaseDetail : returnedPurchase.getReturnedPurchaseDetailList()) {
			formMap.put(returnedPurchaseDetail.getId(), returnedPurchaseDetail);
		}
		
		for (String  id : formMap.keySet()) {
			
			if(dbMap.keySet().contains(id)){//修改
				
					ReturnedPurchaseDetail returnedPurchaseDetail = dbMap.get(id);
					BigDecimal thsl = formMap.get(id).getSl();
					
					MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(returnedPurchaseDetail.getKcllid());
					
					//根据前台存储的库存id操作库存表
					Stock stock=stockSerivce.queryById(returnedPurchaseDetail.getMaterialHistory().getKcid());
					
					String godownEntryDetailId="";
					String receiptSheetDetailId="";
						
							if("2".equals(stock.getCklb())){
								throw new BusinessException("当前仓库不能是报废仓库");
							}
							
							//1.根据库存履历id查询收货单信息 
							ReceiptSheetDetail receiptSheetDetail=new ReceiptSheetDetail();
							receiptSheetDetail.setKcllid(materialHistory.getId());
							ReceiptSheetDetail receiptSheetDetail1=receiptSheetDetailMapper.selectByPrimary(receiptSheetDetail);
							
							if(receiptSheetDetail1!=null){
								
								List<ReceiptSheetDetail> receiptSheetDetaillist =receiptSheetDetailMapper.selectByMaind(receiptSheetDetail1.getMainid());
								// 1.获取第一条收货单信息
								ReceiptSheet receiptNum = receiptSheetMapper.queryById(receiptSheetDetaillist.get(0).getMainid());
								//只查询状态为提交的入库单
								if("2".equals(receiptNum.getZt())){//收货已入库
									// 更新收货单详细单状态为保存，并更新退货数量
									receiptSheetDetail1.setWhrid(user.getId());
									receiptSheetDetail1.setWhsj(new Date());
									receiptSheetDetail1.setThsl(returnedPurchaseDetail.getSl());
									receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
									
									receiptSheetDetailId=receiptSheetDetail1.getId();//给收库明细id复制
									
									//按照收货类型更新其他单据
									//updateRelevantBill(receiptNum,receiptSheetDetaillist.get(0),returnedPurchaseDetail.getSl());
								
									//ReceiptSheet receipt,ReceiptSheetDetail detail,BigDecimal sl
									
									Map<String, Object> paramMap = new HashMap<String, Object>();
									
									paramMap.put("xgdjid", receiptSheetDetaillist.get(0).getXgdjid());
									paramMap.put("dprtcode", receiptNum.getDprtcode());
									paramMap.put("jddxid", receiptNum.getJddxid());
									paramMap.put("sl", returnedPurchaseDetail.getSl());
									// 采购
									if(ReceivingTypeEnum.PURCHASE.getId().equals(receiptNum.getShlx())){
										paramMap.put("htid", receiptNum.getXgdjid());
										// 更新合同明细到货数量
										receiptSheetMapper.updateContractRksl(paramMap);
										// 更新合同到货数量状态
										receiptSheetMapper.updateContractDhslZt(paramMap);
									}else if(ReceivingTypeEnum.REPAIR.getId().equals(receiptNum.getShlx())){
										// 送修
										paramMap.put("htid", receiptNum.getXgdjid());
										// 更新合同明细到货数量
										receiptSheetMapper.updateContractRksl(paramMap);
										// 更新合同到货数量状态
										receiptSheetMapper.updateContractDhslZt(paramMap);
										// 找到外派清单
										paramMap.put("jdlx", 3);
										paramMap.put("bjid", receiptSheetDetaillist.get(0).getBjid());
										paramMap.put("sn", receiptSheetDetaillist.get(0).getSn());
										Expatriate expatriate = receiptSheetMapper.findExpatriateByXlh(paramMap);
										if(expatriate != null){
											// 更新外派清单数据
											paramMap.put("wpqdid", expatriate.getId());
											receiptSheetMapper.updateExpatriateGhsl(paramMap);
											// 新增外派清单对应明细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, expatriate.getId(), 2);
										}
									}else if(ReceivingTypeEnum.BORROW.getId().equals(receiptNum.getShlx())){
										// 借入
										if(StringUtils.isNotBlank(receiptNum.getXgdjid())){
											// 更新借入申请单状态
											receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
											// 新增外派清单
											String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 2);
											// 新增外派清单详细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
										}
									}else if(ReceivingTypeEnum.RETURN.getId().equals(receiptNum.getShlx())){
										// 借出归还
										// 更新外派清单状态
										String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 1);
										// 新增外派明细
										insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
									}
								}else{
									//收货未入库
									// 更新收货单详细单状态为保存，并更新退货数量
									receiptSheetDetail1.setWhrid(user.getId());
									receiptSheetDetail1.setWhsj(new Date());
									receiptSheetDetail1.setThsl(returnedPurchaseDetail.getSl());
									receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
									
									receiptSheetDetailId=receiptSheetDetail1.getId();//给收库明细id复制
									
									Map<String, Object> paramMap = new HashMap<String, Object>();
									
									paramMap.put("xgdjid", receiptSheetDetaillist.get(0).getXgdjid());
									paramMap.put("dprtcode", receiptNum.getDprtcode());
									paramMap.put("jddxid", receiptNum.getJddxid());
									paramMap.put("sl", returnedPurchaseDetail.getSl());
									if(ReceivingTypeEnum.BORROW.getId().equals(receiptNum.getShlx())){
										// 借入
										if(StringUtils.isNotBlank(receiptNum.getXgdjid())){
											// 更新借入申请单状态
											receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
											// 新增外派清单
											String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 2);
											// 新增外派清单详细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
										}
									}else if(ReceivingTypeEnum.RETURN.getId().equals(receiptNum.getShlx())){
										// 借出归还
										// 更新外派清单状态
										String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 1);
										// 新增外派明细
										insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
									}
								}
								
							}
			
					//减库存 或者删除、修改库存履历
					updateStock(stock,thsl,materialHistory);//返回库存履历id
					
					//保存出库明细表
					String detailId = UUID.randomUUID().toString();//出库明细id
					OutstockDetail outstockDetail=new OutstockDetail();
					outstockDetail.setId(detailId);
					outstockDetail.setMainid(ckid);
					outstockDetail.setKcllid(materialHistory.getId());
					outstockDetail.setWhdwid(user.getBmdm());
					outstockDetail.setWhrid(user.getId());
					outstockDetail.setWhsj(new Date());
					outstockDetail.setZt(1);
					BigDecimal aDouble =new BigDecimal(0.00);
					outstockDetail.setTksl(aDouble);
					outstockDetailService.insert(outstockDetail); 
					
					//新增退货表详细
					String thxqid = UUID.randomUUID().toString();//退货详情id
					returnedPurchaseDetail.setId(thxqid);
					returnedPurchaseDetail.setMainid(id);
					returnedPurchaseDetail.setKcllid(materialHistory.getId());
					returnedPurchaseDetail.setShdmxid(receiptSheetDetailId);
					returnedPurchaseDetail.setRkmxid(godownEntryDetailId);
					returnedPurchaseDetail.setCkdmxid(detailId);
					returnedPurchaseDetail.setSl(returnedPurchaseDetail.getSl());
					returnedPurchaseDetail.setZt(1);
					returnedPurchaseDetail.setWhrid(user.getId());
					returnedPurchaseDetail.setWhsj(new Date());
					returnedPurchaseDetailMapper.insertSelective(returnedPurchaseDetail);
				
			}else{//新增
				
					ReturnedPurchaseDetail returnedPurchaseDetail = dbMap.get(id);
					BigDecimal thsl = formMap.get(id).getSl();
								
					if(null == returnedPurchaseDetail.getId() && "".equals(returnedPurchaseDetail.getId())){//排除有退货明细id的数据
						
						//根据前台存储的库存id操作库存表
						Stock stock=stockSerivce.queryById(returnedPurchaseDetail.getMaterialHistory().getKcid());
						
						//根据库存id和库存类型  查询库存履历id
						List<MaterialHistory> materialHistory1=materialHistoryService.selectKey(stock.getId(),user.getJgdm());
						
						String godownEntryDetailId="";
						String receiptSheetDetailId="";
						if(materialHistory1!=null){
							//根据多个库存履历id和多个收货单里匹配
							for (MaterialHistory materialHistory : materialHistory1) {
							
								if("2".equals(stock.getCklb())){
									throw new BusinessException("当前仓库不能是报废仓库");
								}
								
								//1.根据库存履历id查询收货单信息 
								ReceiptSheetDetail receiptSheetDetail=new ReceiptSheetDetail();
								receiptSheetDetail.setKcllid(materialHistory.getId());
								ReceiptSheetDetail receiptSheetDetail1=receiptSheetDetailMapper.selectByPrimary(receiptSheetDetail);
								
								if(receiptSheetDetail1!=null){
									
									List<ReceiptSheetDetail> receiptSheetDetaillist =receiptSheetDetailMapper.selectByMaind(receiptSheetDetail1.getMainid());
									// 1.获取第一条收货单信息
									ReceiptSheet receiptNum = receiptSheetMapper.queryById(receiptSheetDetaillist.get(0).getMainid());
									//只查询状态为提交的入库单
									if("2".equals(receiptNum.getZt())){//收货已入库
										// 更新收货单详细单状态为保存，并更新退货数量
										receiptSheetDetail1.setWhrid(user.getId());
										receiptSheetDetail1.setWhsj(new Date());
										receiptSheetDetail1.setThsl(returnedPurchaseDetail.getSl());
										receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
										
										receiptSheetDetailId=receiptSheetDetail1.getId();//给收库明细id复制
										
										Map<String, Object> paramMap = new HashMap<String, Object>();
										
										paramMap.put("xgdjid", receiptSheetDetaillist.get(0).getXgdjid());
										paramMap.put("dprtcode", receiptNum.getDprtcode());
										paramMap.put("jddxid", receiptNum.getJddxid());
										paramMap.put("sl", returnedPurchaseDetail.getSl());
										// 采购
										if(ReceivingTypeEnum.PURCHASE.getId().equals(receiptNum.getShlx())){
											paramMap.put("htid", receiptNum.getXgdjid());
											// 更新合同明细到货数量
											receiptSheetMapper.updateContractRksl(paramMap);
											// 更新合同到货数量状态
											receiptSheetMapper.updateContractDhslZt(paramMap);
										}else if(ReceivingTypeEnum.REPAIR.getId().equals(receiptNum.getShlx())){
											// 送修
											paramMap.put("htid", receiptNum.getXgdjid());
											// 更新合同明细到货数量
											receiptSheetMapper.updateContractRksl(paramMap);
											// 更新合同到货数量状态
											receiptSheetMapper.updateContractDhslZt(paramMap);
											// 找到外派清单
											paramMap.put("jdlx", 3);
											paramMap.put("bjid", receiptSheetDetaillist.get(0).getBjid());
											paramMap.put("sn", receiptSheetDetaillist.get(0).getSn());
											Expatriate expatriate = receiptSheetMapper.findExpatriateByXlh(paramMap);
											if(expatriate != null){
												// 更新外派清单数据
												paramMap.put("wpqdid", expatriate.getId());
												receiptSheetMapper.updateExpatriateGhsl(paramMap);
												// 新增外派清单对应明细
												insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, expatriate.getId(), 2);
											}
										}else if(ReceivingTypeEnum.BORROW.getId().equals(receiptNum.getShlx())){
											// 借入
											if(StringUtils.isNotBlank(receiptNum.getXgdjid())){
												// 更新借入申请单状态
												receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
												// 新增外派清单
												String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 2);
												// 新增外派清单详细
												insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
											}
										}else if(ReceivingTypeEnum.RETURN.getId().equals(receiptNum.getShlx())){
											// 借出归还
											// 更新外派清单状态
											String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 1);
											// 新增外派明细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
										}
									}else{
										//收货未入库
										// 更新收货单详细单状态为保存，并更新退货数量
										receiptSheetDetail1.setWhrid(user.getId());
										receiptSheetDetail1.setWhsj(new Date());
										receiptSheetDetail1.setThsl(returnedPurchaseDetail.getSl());
										receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
										
										receiptSheetDetailId=receiptSheetDetail1.getId();//给收库明细id复制
										
										Map<String, Object> paramMap = new HashMap<String, Object>();
										
										paramMap.put("xgdjid", receiptSheetDetaillist.get(0).getXgdjid());
										paramMap.put("dprtcode", receiptNum.getDprtcode());
										paramMap.put("jddxid", receiptNum.getJddxid());
										paramMap.put("sl", returnedPurchaseDetail.getSl());
										if(ReceivingTypeEnum.BORROW.getId().equals(receiptNum.getShlx())){
											// 借入
											if(StringUtils.isNotBlank(receiptNum.getXgdjid())){
												// 更新借入申请单状态
												receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
												// 新增外派清单
												String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 2);
												// 新增外派清单详细
												insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
											}
										}else if(ReceivingTypeEnum.RETURN.getId().equals(receiptNum.getShlx())){
											// 借出归还
											// 更新外派清单状态
											String wpqdid = insertExpatriate(paramMap, receiptSheetDetaillist.get(0), receiptNum, 1);
											// 新增外派明细
											insertExpatriateDetail(paramMap, receiptSheetDetaillist.get(0), receiptNum, wpqdid, 1);
										}
									}
									
								}
							}
						}
				
						//减库存 或者删除、新增库存履历
						String historyId=updateStock(stock,thsl);//返回库存履历id
						
						
						//保存出库明细表
						String detailId = UUID.randomUUID().toString();//出库明细id
						OutstockDetail outstockDetail=new OutstockDetail();
						outstockDetail.setId(detailId);
						outstockDetail.setMainid(ckid);
						outstockDetail.setKcllid(historyId);
						outstockDetail.setWhdwid(user.getBmdm());
						outstockDetail.setWhrid(user.getId());
						outstockDetail.setWhsj(new Date());
						outstockDetail.setZt(1);
						BigDecimal aDouble =new BigDecimal(0.00);
						outstockDetail.setTksl(aDouble);
						outstockDetailService.insert(outstockDetail); 
						
						//新增退货表详细
						String thxqid = UUID.randomUUID().toString();//退货详情id
						returnedPurchaseDetail.setId(thxqid);
						returnedPurchaseDetail.setMainid(id);
						returnedPurchaseDetail.setKcllid(historyId);
						returnedPurchaseDetail.setShdmxid(receiptSheetDetailId);
						returnedPurchaseDetail.setRkmxid(godownEntryDetailId);
						returnedPurchaseDetail.setCkdmxid(detailId);
						returnedPurchaseDetail.setSl(thsl);
						returnedPurchaseDetail.setZt(1);
						returnedPurchaseDetail.setWhrid(user.getId());
						returnedPurchaseDetail.setWhsj(new Date());
						returnedPurchaseDetailMapper.insertSelective(returnedPurchaseDetail);
					}
				
			}
		}
		//新增出库单
		Outstock outstock=new Outstock();
		String lyck;
		try {
			lyck = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LYCK.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}	
		outstock.setId(ckid);
		outstock.setCklx(6);//出库类型,退货
		outstock.setCkdh(lyck);
		outstock.setZdbmid(user.getBmdm());
		outstock.setSqrid(returnedPurchase.getThrid());
		outstock.setSqsj(returnedPurchase.getThrq());
		outstock.setDprtcode(user.getJgdm());
		outstock.setSgbs(0);
		outstock.setZt(2);
		outstock.setBz(outstock.getBz());
		outstock.setZdrid(user.getId());
		outstock.setSqbmid(returnedPurchase.getZdbmid());
		outstock.setZdsj(new Date());
		outstockMapper.insertSelective(outstock);
		return returnedPurchase.getId();
	}
	
	
	private void updateStock(Stock stock, BigDecimal sl,MaterialHistory materialHistory) {
		User user = ThreadVarUtil.getUser();
		
		//减去或者删除库存
		 if(sl.compareTo(stock.getKcsl())==-1){
			 //库存数减去出库数
			 BigDecimal kucuns = stock.getKcsl().subtract(sl);   	
			 
			 stock.setKcsl(kucuns);
			 stockSerivce.update(stock);
//					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE3.getName(),"",lyck,SHOUGONG,expatriate.getDghsl());
		 }
		
		 if(sl.compareTo(stock.getKcsl())==0){
			 stockSerivce.delete(stock);
			 
//					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE3.getName(),"",lyck,SHOUGONG,expatriate.getDghsl());
		 }
				 
		// 新增库存履历
		materialHistory.setKcsl(sl);
		materialHistoryService.updateByPrimaryKeySelective(materialHistory);
	}

	/**
	 *修改库存履历数据
	 * @param receipt
	 */
	private void  updateMaterialHistory(BigDecimal sl,MaterialHistory materialHistory){
			
		// 新增库存履历
		materialHistory.setKcsl(sl);
		materialHistoryService.updateByPrimaryKeySelective(materialHistory);
	}
	
	/**
	 *新增库存履历数据
	 * @param receipt
	 */
	private String  updateMaterialHistory(Stock stock,BigDecimal sl){
			
		// 新增库存履历
	    MaterialHistory materialHistory=new MaterialHistory(stock);
		UUID uuid3 = UUID.randomUUID();//获取随机的uuid
		String historyId = uuid3.toString();
		materialHistory.setId(historyId);
		materialHistory.setKcsl(sl);
		materialHistoryService.insert(materialHistory);
		
		return historyId;
	}

	
	
	/**
	 * 删除，减去库存、新增库存履历数据
	 * @param receipt
	 */
	private String  updateStock(Stock stock,BigDecimal sl){
		User user = ThreadVarUtil.getUser();
			
		//减去或者删除库存
		 if(sl.compareTo(stock.getKcsl())==-1){
			 //库存数减去出库数
			 BigDecimal kucuns = stock.getKcsl().subtract(sl);   	
			 
			 stock.setKcsl(kucuns);
			 stockSerivce.update(stock);
//					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE3.getName(),"",lyck,SHOUGONG,expatriate.getDghsl());
		 }
		
		 if(sl.compareTo(stock.getKcsl())==0){
			 stockSerivce.delete(stock);
			 
//					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, lyck, StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE3.getName(),"",lyck,SHOUGONG,expatriate.getDghsl());
		 }
				 
		// 新增库存履历
	    MaterialHistory materialHistory=new MaterialHistory(stock);
		UUID uuid3 = UUID.randomUUID();//获取随机的uuid
		String historyId = uuid3.toString();
		materialHistory.setId(historyId);
		materialHistory.setKcsl(sl);
		materialHistoryService.insert(materialHistory);
		
		
		return historyId;
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
		
		history.setRksj(stock.getRksj());
		history.setRkbmid(stock.getRkbmid());
		history.setRkrid(stock.getRkrid());
		return history;
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
		
		history.setCghtid(stock.getCghtid());
		history.setHtbhCg(stock.getHtbhCg());
		history.setSxhtid(stock.getSxhtid());
		history.setHtbhSx(stock.getHtbhSx());
		
		history.setRksj(stock.getRksj());
		history.setZt(stock.getZt());
		history.setRkbmid(stock.getRkbmid());
		history.setRkrid(stock.getRkrid());
		return history;
	}
	
	
	/**
	 * 按照收货类型更新其他单据
	 */
	private void updateRelevantBill(ReceiptSheet receipt,ReceiptSheetDetail detail,BigDecimal sl){
		Map<String, Object> paramMap = new HashMap<String, Object>();
	
			paramMap.put("xgdjid", detail.getXgdjid());
			paramMap.put("dprtcode", receipt.getDprtcode());
			paramMap.put("jddxid", receipt.getJddxid());
			paramMap.put("sl", sl);
			// 采购
			if(ReceivingTypeEnum.PURCHASE.getId().equals(receipt.getShlx())){
				paramMap.put("htid", receipt.getXgdjid());
				// 更新合同明细到货数量
				receiptSheetMapper.updateContractRksl(paramMap);
				// 更新合同到货数量状态
				receiptSheetMapper.updateContractDhslZt(paramMap);
			}
			// 送修
			else if(ReceivingTypeEnum.REPAIR.getId().equals(receipt.getShlx())){
				paramMap.put("htid", receipt.getXgdjid());
				// 更新合同明细到货数量
				receiptSheetMapper.updateContractRksl(paramMap);
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
				}
			}
			// 借入
			else if(ReceivingTypeEnum.BORROW.getId().equals(receipt.getShlx())){
				if(StringUtils.isNotBlank(receipt.getXgdjid())){
					// 更新借入申请单状态
					receiptSheetMapper.updateBorrowApplyrkSl(paramMap);
					// 新增外派清单
					String wpqdid = insertExpatriate(paramMap, detail, receipt, 2);
					// 新增外派清单详细
					insertExpatriateDetail(paramMap, detail, receipt, wpqdid, 1);
				}
			}
			// 借出归还
			else if(ReceivingTypeEnum.RETURN.getId().equals(receipt.getShlx())){
				// 更新外派清单状态
				String wpqdid = insertExpatriate(paramMap, detail, receipt, 1);
				// 新增外派明细
				insertExpatriateDetail(paramMap, detail, receipt, wpqdid, 1);
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

	@Override
	public void cancel(String id) throws BusinessException {
		validatePri(id);
		ReturnedPurchase returnedPurchase1=returnedPurchaseMapper.selectByPrimaryKey(id);
		if(returnedPurchase1.getZt()!=1){
			throw new BusinessException("当前申请单状态为"+ContractStatusEnum.getName(returnedPurchase1.getZt())+",不可作废");
		}
	
		ReturnedPurchase returnedPurchase = new ReturnedPurchase();
		returnedPurchase.setId(id);
		returnedPurchase.setZt(ReserveStatusEnum.CANCEL.getId());
		returnedPurchaseMapper.updateByPrimaryKeySelective(returnedPurchase);
	}

	@Override
	public void revoked(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		validatePri(id);
		ReturnedPurchase returnedPurchase1=returnedPurchaseMapper.selectByPrimaryKey(id);
		if(returnedPurchase1.getZt()!=2){
			throw new BusinessException("当前申请单状态为"+ContractStatusEnum.getName(returnedPurchase1.getZt())+",不可撤销");
		}
		
		ReturnedPurchase returnedPurchase = new ReturnedPurchase();
		returnedPurchase.setId(id);
		returnedPurchase.setZt(ReserveStatusEnum.REVOKED.getId());
		returnedPurchaseMapper.updateByPrimaryKeySelective(returnedPurchase);
		
		List<ReturnedPurchaseDetail> returnedPurchaseDetail1=	returnedPurchaseDetailMapper.selectByMainId(id);
		
		for(ReturnedPurchaseDetail returnedPurchaseDetail: returnedPurchaseDetail1){
			
			if(null != returnedPurchaseDetail.getId() && !"".equals(returnedPurchaseDetail.getId())){
					
					MaterialHistory materialHistory=materialHistoryService.selectByPrimaryKey(returnedPurchaseDetail.getKcllid());
					
					//根据前台存储的库存id操作库存表
					//Stock stock=stockSerivce.queryById(returnedPurchaseDetail.getMaterialHistory().getKcid());
					
							if("2".equals(materialHistory.getCklb())){
								throw new BusinessException("当前仓库不能是报废仓库");
							}
							
							//根据退货详情里的收货单明细id查询收货单明细信息
							ReceiptSheetDetail receiptSheetDetail1=receiptSheetDetailMapper.selectByPrimaryKey(returnedPurchaseDetail.getShdmxid());
							
							if(receiptSheetDetail1!=null){
								
								// 更新收货单详细单状态为保存，并更新退货数量
								receiptSheetDetail1.setWhrid(user.getId());
								receiptSheetDetail1.setWhsj(new Date());
								
								//收货退货数量
								BigDecimal kucuns = returnedPurchaseDetail.getSl().subtract(receiptSheetDetail1.getSl());   	
								receiptSheetDetail1.setThsl(kucuns);
								receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail1);
							}
							
							//2.根据入库明细单入库单明细id查询入库明细表信息
							GodownEntryDetail godownEntryDetail=godownEntryDetailService.selectByPrimaryKey(returnedPurchaseDetail.getRkmxid());
							
							if(godownEntryDetail!=null){
								//根据入库单明细表的mainid查询入库单信息
								GodownEntry godownEntry = godownEntryService.queryGetByid(godownEntryDetail.getMainid());
								
								//根据入库单的收货单明细id查询收货单明细信息
								ReceiptSheetDetail receiptSheetDetail2=receiptSheetDetailMapper.selectByPrimaryKey(godownEntry.getShdmxid());
								
								//收货退货数量
								BigDecimal kucuns = returnedPurchaseDetail.getSl().subtract(receiptSheetDetail2.getSl());   	
								receiptSheetDetail2.setThsl(kucuns);
								receiptSheetDetailMapper.updateByPrimaryKeySelective(receiptSheetDetail2);
							}
			
							Stock stock =new Stock();
							stock.setId(materialHistory.getKcid());
							int num=stockSerivce.queryCountckls(stock);
							
							//加库存 或者新增、新增库存履历
							addStock(num,returnedPurchaseDetail.getSl(),materialHistory);//返回库存履历id
				}
		}
		//修改出库单
		Outstock outstock=new Outstock();
		outstock.setId(returnedPurchase1.getCkdid());
		outstock.setZt(ReserveStatusEnum.REVOKED.getId());
		outstockMapper.updateByPrimaryKeySelective(outstock);
	}
	
	//加库存 或者新增、新增库存履历
	private void addStock(int num, BigDecimal sl,MaterialHistory materialHistory) {
		User user = ThreadVarUtil.getUser();
		if(num>0){
			Stock stock=new Stock();
			stock.setId(materialHistory.getKcid());
			
			Stock stock2 =stockSerivce.queryKeytuihuo(stock);
			
			stock.setKcsl(stock2.getKcsl().add(sl));
			stockSerivce.update(stock);
		}else{
			Stock stock1 =new Stock(materialHistory);
			String kcid= UUID.randomUUID().toString();//出库单id
			stock1.setKcsl(sl);
			stock1.setId(kcid);
			stockSerivce.insertSelective(stock1);
		}
				 
		// 新增库存履历
		materialHistory.setKcsl(sl);
		materialHistoryService.updateByPrimaryKeySelective(materialHistory);
	}
	
}
