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

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.ComponentCertificateMapper;
import com.eray.thjw.aerialmaterial.dao.ContractDetailMapper;
import com.eray.thjw.aerialmaterial.dao.GodownEntryMapper;
import com.eray.thjw.aerialmaterial.dao.InspectionMapper;
import com.eray.thjw.aerialmaterial.dao.ReceiptSheetDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ReceiptSheetMapper;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.aerialmaterial.po.ReceiptSheetDetail;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.service.GodownEntryDetailService;
import com.eray.thjw.aerialmaterial.service.GodownEntryService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 入库
 * @author ll
 *
 */
@Service("godownEntryService")
public class GodownEntryServiceImpl implements GodownEntryService {
	
	@Resource
	private GodownEntryMapper godownEntryMapper;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private GodownEntryDetailService godownEntryDetailService;
	
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private ReceiptSheetDetailMapper receiptSheetDetailMapper;
	
	@Resource
	private ContractDetailMapper contractDetailMapper;
	
	@Resource
	private ReceiptSheetMapper receiptSheetMapper;
	
	@Resource
	private StorageMapper storageMapper;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private InspectionMapper inspectionMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private ComponentCertificateMapper componentCertificateMapper;
	
	
	@Override
	public List<GodownEntry> queryAllPageList(GodownEntry godownEntry) throws RuntimeException{
		return godownEntryMapper.queryAllPageList(godownEntry);
	}

	@Override
	public GodownEntry queryGetByid(String id) throws RuntimeException {
		return godownEntryMapper.selectByPrimaryKey(id);
	}

	@Override
	public String editSave(GodownEntry godownEntry) throws RuntimeException {
		
		List<GodownEntryDetail> GodownEntryDetailList=godownEntry.getGodownEntryDetailList();
		return this.edit(godownEntry, GodownEntryDetailList);
	}
	@Override
	public String edit(GodownEntry godownEntry, List<GodownEntryDetail> GodownEntryDetailList) throws RuntimeException {
	
	List<String> newReserveDetailIdList = new ArrayList<String>();
		
		List<String> list1 = new ArrayList<String>(); //修改之前分配的菜单id
		List<String> list2 = new ArrayList<String>(); //修改之前分配的菜单id
		//编辑借入单
		User user = ThreadVarUtil.getUser();
		
		//修改入库单信息
		godownEntry.setRkrq(new Date());
		godownEntry.setZdsj(new Date());
		godownEntryMapper.updateByPrimaryKeySelective(godownEntry);
		
		List<GodownEntryDetail> godownEntryDetaillist=godownEntryDetailService.queryGetreceiptSheetDetails(godownEntry.getId());
		
		for (GodownEntryDetail godownEntryDetail : godownEntryDetaillist) {
			list1.add(godownEntryDetail.getId());
		}
		
		for (GodownEntryDetail godownEntryDetail : GodownEntryDetailList) {
			list2.add(godownEntryDetail.getId());
		}
		
			//新增或编辑借入详情信息
			for(GodownEntryDetail godownEntryDetail : GodownEntryDetailList){
				
				if(list1.contains(godownEntryDetail.getId())){
					//修改入库附表数据
					GodownEntryDetail godownEntryDetail1=new GodownEntryDetail();
					godownEntryDetail1.setId(godownEntryDetail.getId());
					godownEntryDetail1.setWhsj(new Date());
					godownEntryDetail1.setKcllid(godownEntryDetail.getMaterialHistory().getId());
					godownEntryDetailService.updateByPrimaryKeySelective(godownEntryDetail1);
					
					//修改库存履历
					MaterialHistory materialHistory=new MaterialHistory();
					materialHistory.setId(godownEntryDetail.getMaterialHistory().getId());
					materialHistory.setCkid(godownEntryDetail.getMaterialHistory().getCkid());
					materialHistory.setKwid(godownEntryDetail.getMaterialHistory().getKwid());
					materialHistory.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
					materialHistory.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
					materialHistory.setXkzh(godownEntryDetail.getMaterialHistory().getXkzh());
					materialHistory.setPch(godownEntryDetail.getMaterialHistory().getPch());
					materialHistory.setSn(godownEntryDetail.getMaterialHistory().getSn());
					
					materialHistory.setHcly(godownEntryDetail.getMaterialHistory().getHcly());
					if(godownEntryDetail.getMaterialHistory().getGrn() != null && !"".equals(godownEntryDetail.getMaterialHistory().getGrn())){
						materialHistory.setGrn(godownEntryDetail.getMaterialHistory().getGrn());
					}else{
						try {
							materialHistory.setGrn(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_GRN.getName()));
						} catch (SaibongException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if("#_#".equals(godownEntryDetail.getMaterialHistory().getGrn())){
						materialHistory.setGrn(null);
					}
					materialHistory.setHjsm(godownEntryDetail.getMaterialHistory().getHjsm());
					materialHistory.setTsn(godownEntryDetail.getMaterialHistory().getTsn());
					materialHistory.setCsn(godownEntryDetail.getMaterialHistory().getCsn());
					materialHistory.setTso(godownEntryDetail.getMaterialHistory().getTso());
					materialHistory.setCso(godownEntryDetail.getMaterialHistory().getCso());
					materialHistory.setCfyq(godownEntryDetail.getMaterialHistory().getCfyq());
					materialHistory.setBz(godownEntryDetail.getMaterialHistory().getBz());
					materialHistoryService.updateByPrimaryKeySelective(materialHistory);
					
					// 保存证书
					Inspection inspection = new Inspection();
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("bjh",godownEntryDetail.getMaterialHistory().getBjh());
					paramsMap.put("sn",godownEntryDetail.getMaterialHistory().getSn());
					paramsMap.put("pch",godownEntryDetail.getMaterialHistory().getPch());
					inspection.setParamsMap(paramsMap);
					inspection.setCertificates(godownEntryDetail.getMaterialHistory().getCertificates());
					inspection.setDprtcode(godownEntryDetail.getMaterialHistory().getDprtcode());
					inspection.setId(godownEntryDetail.getMaterialHistory().getId());
					saveComponentCertificateNEW(inspection);
					
					GodownEntryDetail godownEntryDetail2=godownEntryDetailService.selectByPrimaryKey(godownEntryDetail.getId());
					//修改库存
					MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(godownEntryDetail2.getKcllid());
					Stock stock1=new Stock(materialHistory1);
					stock1.setRksj(new Date());
					stock1.setRkrid(godownEntry.getRukid());
					stock1.setWhrid(godownEntry.getRukid());
					stock1.setWhsj(new Date());
					stockSerivce.updateByPrimaryKeySelective(stock1);
				
		}else{
			
				newReserveDetailIdList.add(godownEntryDetail.getId());
				if(null != godownEntryDetail.getId() && !"".equals(godownEntryDetail.getId())){
					
					//修改入库附表
					GodownEntryDetail godownEntryDetail1=godownEntryDetailService.selectByPrimaryKey(godownEntryDetail.getId());
					godownEntryDetail.setWhsj(new Date());
					godownEntryDetail.setKcllid(godownEntryDetail.getMaterialHistory().getId());
					godownEntryDetailService.updateByPrimaryKeySelective(godownEntryDetail1);
					
					//修改库存履历
					MaterialHistory materialHistory=new MaterialHistory();
					materialHistory.setId(godownEntryDetail.getMaterialHistory().getId());
					materialHistory.setCkid(godownEntryDetail.getMaterialHistory().getCkid());
					materialHistory.setKwid(godownEntryDetail.getMaterialHistory().getKwid());
					materialHistory.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
					materialHistory.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
					materialHistory.setXkzh(godownEntryDetail.getMaterialHistory().getXkzh());
					materialHistory.setPch(godownEntryDetail.getMaterialHistory().getPch());
					materialHistory.setSn(godownEntryDetail.getMaterialHistory().getSn());
					materialHistory.setHcly(godownEntryDetail.getMaterialHistory().getHcly());
					if(godownEntryDetail.getMaterialHistory().getGrn() != null && !"".equals(godownEntryDetail.getMaterialHistory().getGrn())){
						materialHistory.setGrn(godownEntryDetail.getMaterialHistory().getGrn());
					}else{
						try {
							materialHistory.setGrn(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_GRN.getName()));
						} catch (SaibongException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if("#_#".equals(godownEntryDetail.getMaterialHistory().getGrn())){
						materialHistory.setGrn(null);
					}
					materialHistory.setHjsm(godownEntryDetail.getMaterialHistory().getHjsm());
					materialHistory.setTsn(godownEntryDetail.getMaterialHistory().getTsn());
					materialHistory.setCsn(godownEntryDetail.getMaterialHistory().getCsn());
					materialHistory.setTso(godownEntryDetail.getMaterialHistory().getTso());
					materialHistory.setCso(godownEntryDetail.getMaterialHistory().getCso());
					materialHistory.setCfyq(godownEntryDetail.getMaterialHistory().getCfyq());
					materialHistory.setBz(godownEntryDetail.getMaterialHistory().getBz());
					materialHistoryService.updateByPrimaryKeySelective(materialHistory);
					// 保存证书
					Inspection inspection = new Inspection();
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					paramsMap.put("bjh",godownEntryDetail.getMaterialHistory().getBjh());
					paramsMap.put("sn",godownEntryDetail.getMaterialHistory().getSn());
					paramsMap.put("pch",godownEntryDetail.getMaterialHistory().getPch());
					inspection.setParamsMap(paramsMap);
					inspection.setCertificates(godownEntryDetail.getMaterialHistory().getCertificates());
					inspection.setDprtcode(godownEntryDetail.getMaterialHistory().getDprtcode());
					inspection.setId(godownEntryDetail.getMaterialHistory().getId());
					saveComponentCertificateNEW(inspection);
					//修改库存
					MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(godownEntryDetail1.getKcllid());
					Stock stock1=new Stock(materialHistory1);
					stock1.setRksj(new Date());
					stock1.setRkrid(godownEntry.getRukid());
					stockSerivce.updateByPrimaryKeySelective(stock1);
					
				}else{
					
					UUID kcllidss = UUID.randomUUID();//获取随机的uuid
					String kcllids = kcllidss.toString();
					
					UUID kcidss = UUID.randomUUID();//获取随机的uuid
					String kcids = kcidss.toString();
					
					//添加入库附表
					UUID uuid2 = UUID.randomUUID();//获取随机的uuid
					String detailId = uuid2.toString();
					godownEntryDetail.setId(detailId);
					godownEntryDetail.setMainid(godownEntry.getId());
					godownEntryDetail.setKcllid(kcllids);
					godownEntryDetail.setZt(EffectiveEnum.YES.getId());
					godownEntryDetail.setWhrid(user.getId());
					godownEntryDetail.setWhdwid(user.getBmdm());
					godownEntryDetail.setWhsj(new Date());
					godownEntryDetailService.insertSelective(godownEntryDetail);
					
					//添加库存履历
					MaterialHistory materialHistory=new MaterialHistory();
					materialHistory.setId(kcllids);
					materialHistory.setKcid(kcids);
					materialHistory.setCkid(godownEntryDetail.getMaterialHistory().getCkid());
					materialHistory.setKwid(godownEntryDetail.getMaterialHistory().getKwid());
					materialHistory.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
					materialHistory.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
					materialHistory.setHcly(godownEntryDetail.getMaterialHistory().getHcly());
					if(godownEntryDetail.getMaterialHistory().getGrn() != null && !"".equals(godownEntryDetail.getMaterialHistory().getGrn())){
						materialHistory.setGrn(godownEntryDetail.getMaterialHistory().getGrn());
					}else{
						try {
							materialHistory.setGrn(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_GRN.getName()));
						} catch (SaibongException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if("#_#".equals(godownEntryDetail.getMaterialHistory().getGrn())){
						materialHistory.setGrn(null);
					}
					materialHistory.setHjsm(godownEntryDetail.getMaterialHistory().getHjsm());
					materialHistory.setTsn(godownEntryDetail.getMaterialHistory().getTsn());
					materialHistory.setCsn(godownEntryDetail.getMaterialHistory().getCsn());
					materialHistory.setTso(godownEntryDetail.getMaterialHistory().getTso());
					materialHistory.setCso(godownEntryDetail.getMaterialHistory().getCso());
					materialHistory.setCfyq(godownEntryDetail.getMaterialHistory().getCfyq());
					materialHistory.setBz(godownEntryDetail.getMaterialHistory().getBz());
					materialHistory.setDprtcode(godownEntry.getDprtcode());
					materialHistory.setCklb(Integer.valueOf(godownEntry.getParamsMap().get("cklb").toString()));
					materialHistory.setBjid(godownEntry.getParamsMap().get("bjid").toString());
					materialHistory.setBjh(godownEntry.getParamsMap().get("bjh").toString());
					materialHistory.setPch(godownEntryDetail.getMaterialHistory().getPch());
					materialHistory.setSn(godownEntryDetail.getMaterialHistory().getSn());
					materialHistory.setZwms(godownEntry.getParamsMap().get("zwms").toString());
					materialHistory.setYwms(godownEntry.getParamsMap().get("ywms").toString());
					materialHistory.setJldw(godownEntry.getParamsMap().get("jldw").toString());
					materialHistory.setZt(1);
					materialHistory.setJydid(godownEntry.getParamsMap().get("jydid").toString());
					materialHistory.setXh(godownEntry.getParamsMap().get("xh").toString());
					materialHistory.setGg(godownEntry.getParamsMap().get("gg").toString());
					materialHistoryService.insertSelective(materialHistory);
					
					//新增库存
					Stock stock=new Stock(materialHistory);
					stock.setId(kcids);
					stock.setWhsj(new Date());
					stock.setWhrid(user.getId());
					stockSerivce.insertSelective(stock);
					materialRecService.writeOutfieldRec(stock.getId(), UUID.randomUUID().toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "入库","",stock.getKcsl());
				}
				
		}
				
			}
	
		return "";
	}
	
	@Override
	public void editSubmit(GodownEntry godownEntry) throws RuntimeException {
		
		List<GodownEntryDetail> GodownEntryDetailList=godownEntry.getGodownEntryDetailList();
		this.editSubmit(godownEntry, GodownEntryDetailList);
	}
	
	@Override
	public void editSubmit(GodownEntry godownEntry, List<GodownEntryDetail> GodownEntryDetailList) throws RuntimeException {
		List<String> newReserveDetailIdList = new ArrayList<String>();
		
		List<String> list1 = new ArrayList<String>(); //修改之前分配的菜单id
		List<String> list2 = new ArrayList<String>(); //修改之前分配的菜单id
		//编辑借入单
		User user = ThreadVarUtil.getUser();
		
		//修改入库单信息
		godownEntry.setRkrq(new Date());
		godownEntry.setZdsj(new Date());
		godownEntryMapper.updateByPrimaryKeySelective(godownEntry);
		
		GodownEntry godownEntry1=godownEntryMapper.selectByPrimaryKey(godownEntry.getId());
		
		//当入库类型为提订或者送修时，
		ReceiptSheetDetail receiptSheetDetail=receiptSheetDetailMapper.selectByPrimaryKey(godownEntry1.getShdmxid());
		
		ReceiptSheet receiptSheet=receiptSheetMapper.selectByPrimaryKey(godownEntry1.getShdid());
		
		if(receiptSheet.getShlx()==1||receiptSheet.getShlx()==2){
			ContractDetail contractDetail1=contractDetailMapper.selectByPrimaryKey(receiptSheetDetail.getXgdjid());
			ContractDetail contractDetail=new ContractDetail();
			contractDetail.setId(receiptSheetDetail.getXgdjid());
			contractDetail.setRksl(contractDetail1.getRksl().add(receiptSheetDetail.getSl()));
			contractDetailMapper.updateByPrimaryKeySelective(contractDetail);
		}
		
		
		List<GodownEntryDetail> godownEntryDetaillist=godownEntryDetailService.queryGetreceiptSheetDetails(godownEntry.getId());
		
		for (GodownEntryDetail godownEntryDetail : godownEntryDetaillist) {
			list1.add(godownEntryDetail.getId());
		}
		
		for (GodownEntryDetail godownEntryDetail : GodownEntryDetailList) {
			list2.add(godownEntryDetail.getId());
		}
		
		for (String  id : list1) {
					
					//新增或编辑借入详情信息
					for(GodownEntryDetail godownEntryDetail : GodownEntryDetailList){
						if(!list2.contains(id)){
						
						//修改入库附表数据
						GodownEntryDetail godownEntryDetail1=new GodownEntryDetail();
						godownEntryDetail1.setId(id);
						godownEntryDetail1.setWhsj(new Date());
						godownEntryDetail1.setKcllid(godownEntryDetail.getMaterialHistory().getId());
						godownEntryDetailService.updateByPrimaryKeySelective(godownEntryDetail1);
						
						Storage storage = storageMapper.selectByIdWithRel(godownEntryDetail.getMaterialHistory().getKwid());
						//修改库存履历
						MaterialHistory materialHistory=new MaterialHistory();
						materialHistory.setId(godownEntryDetail.getMaterialHistory().getId());
						materialHistory.setCklb(storage.getCklb());
						materialHistory.setCkh(storage.getCkh());
						materialHistory.setCkid(storage.getStore().getId());
						materialHistory.setCkmc(storage.getStore().getCkmc());
						materialHistory.setKwid(storage.getId());
						materialHistory.setKwh(storage.getKwh());
						materialHistory.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
						materialHistory.setDjsl(BigDecimal.ZERO);
						materialHistory.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
						materialHistory.setXkzh(godownEntryDetail.getMaterialHistory().getXkzh());
						materialHistory.setPch(godownEntryDetail.getMaterialHistory().getPch());
						materialHistory.setSn(godownEntryDetail.getMaterialHistory().getSn());
						materialHistory.setHcly(godownEntryDetail.getMaterialHistory().getHcly());
						if(godownEntryDetail.getMaterialHistory().getGrn() != null && !"".equals(godownEntryDetail.getMaterialHistory().getGrn())){
							materialHistory.setGrn(godownEntryDetail.getMaterialHistory().getGrn());
						}else{
							try {
								materialHistory.setGrn(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_GRN.getName()));
							} catch (SaibongException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if("#_#".equals(godownEntryDetail.getMaterialHistory().getGrn())){
							materialHistory.setGrn(null);
						}
						materialHistory.setHjsm(godownEntryDetail.getMaterialHistory().getHjsm());
						materialHistory.setTsn(godownEntryDetail.getMaterialHistory().getTsn());
						materialHistory.setCsn(godownEntryDetail.getMaterialHistory().getCsn());
						materialHistory.setTso(godownEntryDetail.getMaterialHistory().getTso());
						materialHistory.setCso(godownEntryDetail.getMaterialHistory().getCso());
						materialHistory.setCfyq(godownEntryDetail.getMaterialHistory().getCfyq());
						materialHistory.setBz(godownEntryDetail.getMaterialHistory().getBz());
						materialHistory.setWhsj(new Date());
						materialHistory.setWhrid(user.getId());
						materialHistoryService.updateByPrimaryKeySelective(materialHistory);
						// 保存证书
						Inspection inspection = new Inspection();
						Map<String, Object> paramsMap = new HashMap<String, Object>();
						paramsMap.put("bjh",godownEntryDetail.getMaterialHistory().getBjh());
						paramsMap.put("sn",godownEntryDetail.getMaterialHistory().getSn());
						paramsMap.put("pch",godownEntryDetail.getMaterialHistory().getPch());
						inspection.setParamsMap(paramsMap);
						inspection.setCertificates(godownEntryDetail.getMaterialHistory().getCertificates());
						inspection.setDprtcode(godownEntryDetail.getMaterialHistory().getDprtcode());
						inspection.setId(godownEntryDetail.getMaterialHistory().getId());
						saveComponentCertificateNEW(inspection);
						GodownEntryDetail godownEntryDetail2=godownEntryDetailService.selectByPrimaryKey(id);
						//修改库存
						MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(godownEntryDetail2.getKcllid());
						Stock stock1=new Stock(materialHistory1);
						
						stock1.setRksj(new Date());
						stock1.setRkrid(godownEntry.getRukid());
						stock1.setCklb(storage.getCklb());
						stock1.setCkh(storage.getCkh());
						stock1.setCkid(storage.getStore().getId());
						stock1.setCkmc(storage.getStore().getCkmc());
						stock1.setKwid(storage.getId());
						stock1.setKwh(storage.getKwh());
						stock1.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
						stock1.setDjsl(BigDecimal.ZERO);
						stock1.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
						stock1.setWhrid(user.getId());
						stock1.setWhsj(new Date());
						stockSerivce.updateByPrimaryKeySelective(stock1);
						
						}else{								
								newReserveDetailIdList.add(godownEntryDetail.getId());
								
								if(null != godownEntryDetail.getId() && !"".equals(godownEntryDetail.getId())){
									
									//修改入库附表
									GodownEntryDetail godownEntryDetail1=godownEntryDetailService.selectByPrimaryKey(godownEntryDetail.getId());
									godownEntryDetail.setWhsj(new Date());
									godownEntryDetail.setKcllid(godownEntryDetail.getMaterialHistory().getId());
									godownEntryDetailService.updateByPrimaryKeySelective(godownEntryDetail1);
									
									Storage storage = storageMapper.selectByIdWithRel(godownEntryDetail.getMaterialHistory().getKwid());
									//修改库存履历
									MaterialHistory materialHistory=new MaterialHistory();
									materialHistory.setId(godownEntryDetail.getMaterialHistory().getId());
									materialHistory.setCklb(storage.getCklb());
									materialHistory.setCkh(storage.getCkh());
									materialHistory.setCkid(storage.getStore().getId());
									materialHistory.setCkmc(storage.getStore().getCkmc());
									materialHistory.setKwid(storage.getId());
									materialHistory.setKwh(storage.getKwh());
									materialHistory.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
									materialHistory.setDjsl(BigDecimal.ZERO);
									materialHistory.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
									materialHistory.setXkzh(godownEntryDetail.getMaterialHistory().getXkzh());
									materialHistory.setPch(godownEntryDetail.getMaterialHistory().getPch());
									materialHistory.setSn(godownEntryDetail.getMaterialHistory().getSn());
									materialHistory.setHcly(godownEntryDetail.getMaterialHistory().getHcly());
									if(godownEntryDetail.getMaterialHistory().getGrn() != null && !"".equals(godownEntryDetail.getMaterialHistory().getGrn())){
										materialHistory.setGrn(godownEntryDetail.getMaterialHistory().getGrn());
									}else{
										try {
											materialHistory.setGrn(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_GRN.getName()));
										} catch (SaibongException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									if("#_#".equals(godownEntryDetail.getMaterialHistory().getGrn())){
										materialHistory.setGrn(null);
									}
									materialHistory.setHjsm(godownEntryDetail.getMaterialHistory().getHjsm());
									materialHistory.setTsn(godownEntryDetail.getMaterialHistory().getTsn());
									materialHistory.setCsn(godownEntryDetail.getMaterialHistory().getCsn());
									materialHistory.setTso(godownEntryDetail.getMaterialHistory().getTso());
									materialHistory.setCso(godownEntryDetail.getMaterialHistory().getCso());
									materialHistory.setCfyq(godownEntryDetail.getMaterialHistory().getCfyq());
									materialHistory.setBz(godownEntryDetail.getMaterialHistory().getBz());
									materialHistory.setWhsj(new Date());
									materialHistory.setWhrid(user.getId());
									materialHistoryService.updateByPrimaryKeySelective(materialHistory);
									// 保存证书
									Inspection inspection = new Inspection();
									Map<String, Object> paramsMap = new HashMap<String, Object>();
									paramsMap.put("bjh",godownEntryDetail.getMaterialHistory().getBjh());
									paramsMap.put("sn",godownEntryDetail.getMaterialHistory().getSn());
									paramsMap.put("pch",godownEntryDetail.getMaterialHistory().getPch());
									inspection.setParamsMap(paramsMap);
									inspection.setCertificates(godownEntryDetail.getMaterialHistory().getCertificates());
									inspection.setDprtcode(godownEntryDetail.getMaterialHistory().getDprtcode());
									inspection.setId(godownEntryDetail.getMaterialHistory().getId());
									saveComponentCertificateNEW(inspection);
									//修改库存
									MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(godownEntryDetail1.getKcllid());
									Stock stock1=new Stock(materialHistory1);
									
									stock1.setRksj(new Date());
									stock1.setRkrid(godownEntry.getRukid());
									stock1.setCklb(storage.getCklb());
									stock1.setCkh(storage.getCkh());
									stock1.setCkid(storage.getStore().getId());
									stock1.setCkmc(storage.getStore().getCkmc());
									stock1.setKwid(storage.getId());
									stock1.setKwh(storage.getKwh());
									stock1.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
									stock1.setDjsl(BigDecimal.ZERO);
									stock1.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
									stock1.setWhrid(user.getId());
									stock1.setWhsj(new Date());
									stockSerivce.updateByPrimaryKeySelective(stock1);
									
								}else{
									
									UUID kcllidss = UUID.randomUUID();//获取随机的uuid
									String kcllids = kcllidss.toString();
									
									UUID kcidss = UUID.randomUUID();//获取随机的uuid
									String kcids = kcidss.toString();
									
									//添加入库附表
									UUID uuid2 = UUID.randomUUID();//获取随机的uuid
									String detailId = uuid2.toString();
									godownEntryDetail.setId(detailId);
									godownEntryDetail.setMainid(godownEntry.getId());
									godownEntryDetail.setKcllid(kcllids);
									godownEntryDetail.setZt(EffectiveEnum.YES.getId());
									godownEntryDetail.setWhrid(user.getId());
									godownEntryDetail.setWhdwid(user.getBmdm());
									godownEntryDetail.setWhsj(new Date());
									godownEntryDetailService.insertSelective(godownEntryDetail);
									
									Storage storage = storageMapper.selectByIdWithRel(godownEntryDetail.getMaterialHistory().getKwid());
									//添加库存履历
									MaterialHistory materialHistory=new MaterialHistory();
									materialHistory.setId(kcllids);
									materialHistory.setKcid(kcids);
									materialHistory.setCklb(storage.getCklb());
									materialHistory.setCkh(storage.getCkh());
									materialHistory.setCkid(storage.getStore().getId());
									materialHistory.setCkmc(storage.getStore().getCkmc());
									materialHistory.setKwid(storage.getId());
									materialHistory.setKwh(storage.getKwh());
									materialHistory.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
									materialHistory.setDjsl(BigDecimal.ZERO);
									materialHistory.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
									materialHistory.setXkzh(godownEntryDetail.getMaterialHistory().getXkzh());
									materialHistory.setHcly(godownEntryDetail.getMaterialHistory().getHcly());
									if(godownEntryDetail.getMaterialHistory().getGrn() != null && !"".equals(godownEntryDetail.getMaterialHistory().getGrn())){
										materialHistory.setGrn(godownEntryDetail.getMaterialHistory().getGrn());
									}else{
										try {
											materialHistory.setGrn(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_GRN.getName()));
										} catch (SaibongException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									if("#_#".equals(godownEntryDetail.getMaterialHistory().getGrn())){
										materialHistory.setGrn(null);
									}
									materialHistory.setHjsm(godownEntryDetail.getMaterialHistory().getHjsm());
									materialHistory.setTsn(godownEntryDetail.getMaterialHistory().getTsn());
									materialHistory.setCsn(godownEntryDetail.getMaterialHistory().getCsn());
									materialHistory.setTso(godownEntryDetail.getMaterialHistory().getTso());
									materialHistory.setCso(godownEntryDetail.getMaterialHistory().getCso());
									materialHistory.setCfyq(godownEntryDetail.getMaterialHistory().getCfyq());
									materialHistory.setBz(godownEntryDetail.getMaterialHistory().getBz());
									materialHistory.setDprtcode(godownEntry.getDprtcode());
									materialHistory.setCklb(Integer.valueOf(godownEntry.getParamsMap().get("cklb").toString()));
									materialHistory.setBjid(godownEntry.getParamsMap().get("bjid").toString());
									materialHistory.setBjh(godownEntry.getParamsMap().get("bjh").toString());
									materialHistory.setPch(godownEntryDetail.getMaterialHistory().getPch());
									materialHistory.setSn(godownEntryDetail.getMaterialHistory().getSn());
									materialHistory.setZwms(godownEntry.getParamsMap().get("zwms").toString());
									materialHistory.setYwms(godownEntry.getParamsMap().get("ywms").toString());
									materialHistory.setJldw(godownEntry.getParamsMap().get("jldw").toString());
									materialHistory.setZt(1);
									materialHistory.setJydid(godownEntry.getParamsMap().get("jydid").toString());
									materialHistory.setXh(godownEntry.getParamsMap().get("xh").toString());
									materialHistory.setGg(godownEntry.getParamsMap().get("gg").toString());
									materialHistory.setWhsj(new Date());
									materialHistory.setWhrid(user.getId());
									materialHistoryService.insertSelective(materialHistory);
									
									//新增库存
									Stock stock=new Stock(materialHistory);
									stock.setId(kcids);
									stock.setCklb(storage.getCklb());
									stock.setCkh(storage.getCkh());
									stock.setCkid(storage.getStore().getId());
									stock.setCkmc(storage.getStore().getCkmc());
									stock.setKwid(storage.getId());
									stock.setKwh(storage.getKwh());
									stock.setKcsl(godownEntryDetail.getMaterialHistory().getKcsl());
									stock.setDjsl(BigDecimal.ZERO);
									stock.setKccb(godownEntryDetail.getMaterialHistory().getKccb());
									stock.setWhrid(user.getId());
									stock.setWhsj(new Date());
									stockSerivce.insertSelective(stock);
									
									materialRecService.writeOutfieldRec(stock.getId(), UUID.randomUUID().toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "入库","",stock.getKcsl());
								}
							
						}
					}
		}
	}

	@Override
	public void cancel(String id) throws BusinessException {
		
		//修改入库单状态
		GodownEntry godownEntry=new GodownEntry();
		godownEntry.setId(id);
		godownEntry.setZt(1);
		godownEntryMapper.updateByPrimaryKeySelective(godownEntry);
		
		//查询入库单相关信息
		GodownEntry godownEntry1 =godownEntryMapper.selectByPrimaryKey(id);
		
		//当入库类型为提订或者送修时，
		ReceiptSheetDetail receiptSheetDetail=receiptSheetDetailMapper.selectByPrimaryKey(godownEntry1.getShdmxid());
		
		ReceiptSheet receiptSheet=receiptSheetMapper.selectByPrimaryKey(godownEntry1.getShdid());
		
		if(receiptSheet.getShlx()==1||receiptSheet.getShlx()==2){
			ContractDetail contractDetail1=contractDetailMapper.selectByPrimaryKey(receiptSheetDetail.getXgdjid());
			ContractDetail contractDetail=new ContractDetail();
			contractDetail.setId(receiptSheetDetail.getXgdjid());
			contractDetail.setRksl(contractDetail1.getRksl().subtract(receiptSheetDetail.getSl()));
			contractDetailMapper.updateByPrimaryKeySelective(contractDetail);
		}
		
		//查询入库单附表
		List<GodownEntryDetail> borrowApplyDetaillist=godownEntryDetailService.queryGetreceiptSheetDetails(id);
		
		for (GodownEntryDetail godownEntryDetail : borrowApplyDetaillist) {
			
			//添加履历
			materialHistoryService.updateByPrimaryKeySelective(godownEntryDetail.getMaterialHistory());
			
			//新增或者增加检验状态的库存
			if(godownEntry1.getReceiptSheetDetail().getKcllid()!=null){
				MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(godownEntry.getReceiptSheetDetail().getKcllid());
				Stock stock1=new Stock();
				stock1.setId(materialHistory1.getKcid());
				Stock stock=stockSerivce.queryKeys(stock1);
				BigDecimal r=BigDecimal.ZERO; 
				
				 if(stock!=null&&stock.getKcsl().compareTo(r)==0){
					 //新增库存
					 stock.setZt(2);
					 stockSerivce.insertSelective(stock);
					 materialRecService.writeOutfieldRec(stock.getId(), UUID.randomUUID().toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "入库撤销","",stock.getKcsl());
				 }
				 
				 if(stock!=null&&stock.getKcsl().compareTo(r)==1){
					 //库存数增加入库数
					 BigDecimal kucuns = stock.getKcsl().subtract(godownEntryDetail.getMaterialHistory().getKcsl());   	
					 stock.setKcsl(kucuns);
					 stock.setZt(2);
					 stockSerivce.updateByPrimaryKeySelective(stock);
					 materialRecService.writeOutfieldRec(stock.getId(), UUID.randomUUID().toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.UPDATE, "入库撤销","",kucuns);
				 }
			}
			
			//删除或者减去库存
			if(godownEntry1.getReceiptSheetDetail().getKcllid()!=null){
				MaterialHistory materialHistory1=materialHistoryService.selectByPrimaryKey(godownEntry.getReceiptSheetDetail().getKcllid());
				Stock stock1=new Stock();
				stock1.setId(materialHistory1.getKcid());
				Stock stock=stockSerivce.queryKeys(stock1);
				BigDecimal r=BigDecimal.ZERO; 
				
				 if(stock!=null&&stock.getKcsl().compareTo(r)==0){
					 //删除库存
					 stock.setZt(2);
					 stockSerivce.delete(stock);
					 materialRecService.writeOutfieldRec(stock.getId(), UUID.randomUUID().toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "入库撤销","",stock.getKcsl());
				 }
				 
				 if(stock!=null&&stock.getKcsl().compareTo(r)==1){
					 //库存数减去入库数
					 BigDecimal kucuns = stock.getKcsl().subtract(godownEntryDetail.getMaterialHistory().getKcsl());   	
					 stock.setKcsl(kucuns);
					 stock.setZt(2);
					 stockSerivce.updateByPrimaryKeySelective(stock);
					 materialRecService.writeOutfieldRec(stock.getId(), UUID.randomUUID().toString(),"", "", OutfieldRecBizTypeEnum.TYPE2, UpdateTypeEnum.SAVE, "入库撤销","",kucuns);
				 }
			}
			
		}
	}
	
	/**
	 * @Description 保存证书
	 * @CreateTime 2017年10月26日 下午3:34:25
	 * @CreateBy 韩武
	 * @param record
	 */
	private void saveComponentCertificateNEW(Inspection record){
		record.setCzls(UUID.randomUUID().toString());
		record.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		User user = ThreadVarUtil.getUser();
		
		// 删除部件证书
		deleteNew(record);		
		
		// 保存部件证书
		if (record.getCertificates() != null 
				&& !record.getCertificates().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ComponentCertificate cert : record.getCertificates()) {
				
				cert.setId(UUID.randomUUID().toString());
				cert.setJh(record.getParamsMap().get("bjh").toString());
				cert.setXlh(record.getParamsMap().get("sn").toString());
				cert.setPch(record.getParamsMap().get("pch").toString());
				cert.setZt(EffectiveEnum.YES.getId());
				cert.setWhrid(user.getId());
				cert.setWhsj(new Date());
				cert.setWhbmid(user.getBmdm());
				cert.setDprtcode(record.getDprtcode());
				
				if(StringUtils.isNotBlank(cert.getJh())){
					idList.add(cert.getId());
					componentCertificateMapper.insertSelective(cert);
					
					// 保存附件
					attachmentService.eidtAttachment(cert.getAttachments(), cert.getId(), record.getCzls(), record.getId(), record.getDprtcode(), record.getLogOperationEnum());
				}
			}
			
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_H2_024, user.getId(), 
						record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getId());
			}
		}
	}
	
	/**
	 * @Description 删除部件证书
	 * @CreateTime 2017年9月28日 下午5:04:34
	 * @CreateBy 韩武
	 * @param record
	 */
	public void deleteNew(Inspection record){
		
		if(record.getParamsMap().get("sn") != null && StringUtils.isNotBlank(record.getParamsMap().get("sn").toString())){
			record.getParamsMap().put("pch", "-");
		}else{
			record.getParamsMap().put("sn", "-");
		}
		
		User user = ThreadVarUtil.getUser();
		List<String> delList = inspectionMapper.selectByKeyInspection(record);
		
		if(!delList.isEmpty()){
			// 删除附件
			for (String mainid : delList) {
				attachmentService.delFiles(mainid, record.getCzls(), record.getId(), record.getLogOperationEnum());
			}
			
			// 保存历史记录信息
			commonRecService.write("id", delList, TableEnum.B_H2_024, user.getId(), 
					record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getId());
			
			// 删除部件证书
			inspectionMapper.deleteByKeyInspection(record);
		}
	}
}
