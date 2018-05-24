package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.ComponentCertificateMapper;
import com.eray.thjw.aerialmaterial.dao.ComponentHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.dao.GodownEntryDetailMapper;
import com.eray.thjw.aerialmaterial.dao.GodownEntryMapper;
import com.eray.thjw.aerialmaterial.dao.InspectionMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.po.ComponentHistory;
import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.InspectionService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.MaterialTypeEnum;
import enu.OperateEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutStockTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.common.EffectiveEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoEnum;
import enu.project2.TodoStatusEnum;

@Service
public class InspectionServiceImpl implements InspectionService{
	
	@Resource
	private InspectionMapper inspectionMapper;
	@Resource
	private MaterialRecService materialRecService;
	@Resource
	private CommonService commonService;
	@Resource
	private StoreInnerSearchMapper storeInnerSearchMapper;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private GodownEntryMapper godownEntryMapper;
	@Autowired
	private MaterialHistoryMapper materialHistoryMapper; 
	@Autowired
	private GodownEntryDetailMapper godownEntryDetailMapper; 
	@Autowired
	private ComponentCertificateService componentCertificateService; 
	@Autowired
	private ComponentCertificateMapper componentCertificateMapper; 
	@Autowired
	private AttachmentService attachmentService; 
	@Autowired
	private CommonRecService commonRecService; 
	@Autowired
	private ComponentHistoryMapper componentHistoryMapper; 
	@Autowired
	private ComponentMapper componentMapper; 
	@Autowired
	private StockSerivce stockSerivce; 
	@Autowired
	private OutFieldStockService outFieldStockService; 
	@Autowired
	private TodorsService todorsService;
	@Autowired
	private TodoService todoService;

	@Override
	public int deleteByPrimaryKey(String id) {
		
		return inspectionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Inspection record) {
		
		return inspectionMapper.insert(record);
	}

	@Override
	public int insertSelective(Inspection record) {
		
		return inspectionMapper.insertSelective(record);
	}

	@Override
	public Inspection selectByPrimaryKey(String id) {
		
		return inspectionMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public Inspection getById(String id) {
		
		return inspectionMapper.getById(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Inspection record) throws RuntimeException, SaibongException {
		Date currentDate = commonService.getSysdate();//当前时间
		User user=ThreadVarUtil.getUser();
		if(inspectionMapper.getCount4validation(record.getId())==0){
			throw new RuntimeException("数据已更新,请刷新页面！");
		}
		if(record.getGodownEntry()!=null){
			GodownEntry godownEntry=record.getGodownEntry();
			String id=UUID.randomUUID().toString();
			godownEntry.setId(id);
			String	rkdh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_ZJD.getName(),record);
			godownEntry.setRkdh(rkdh);
			godownEntryMapper.insertSelective(godownEntry);
			MaterialHistory materialHistory=record.getMaterialHistory();
			String materialId=UUID.randomUUID().toString();
			materialHistory.setId(materialId);
			materialHistoryMapper.insertSelective(materialHistory);
			GodownEntryDetail godownEntryDetail=new GodownEntryDetail();
			godownEntryDetail.setId(UUID.randomUUID().toString());
			godownEntryDetail.setMainid(id);
			godownEntryDetail.setKcllid(materialId);
			godownEntryDetail.setZt(1);
			godownEntryDetail.setWhdwid(record.getZdbmid());
			godownEntryDetail.setWhrid(record.getZdrid());
			godownEntryDetailMapper.insertSelective(godownEntryDetail);
			
			if(materialHistory.getSn()!=null){
				ComponentHistory componentHistory=new ComponentHistory();
				componentHistory.setId(UUID.randomUUID().toString());
				componentHistory.setDprtcode(record.getDprtcode());
				componentHistory.setJh(materialHistory.getBjh());
				componentHistory.setXlh(materialHistory.getSn());
				componentHistory.setChucrq(materialHistory.getScrq());
				componentHistory.setTsn(record.getTsn());
				componentHistory.setTso(record.getTso());
				if(record.getCsn().equals("")){
					componentHistory.setCsn(null);
				}else{
					componentHistory.setCsn(Integer.valueOf(record.getCsn()));
				}
				if(record.getCso().equals("")){
					componentHistory.setCso(null);
				}else{
					componentHistory.setCso(Integer.valueOf(record.getCso()));
				}
				componentHistory.setWhsj(record.getJyrq());
				componentHistoryMapper.insertSelective(componentHistory);
				
				//查询维护时间最大那条b_h2_025 部件数据更新
				ComponentHistory componentHistory1=componentHistoryMapper.selectByPrimaryComponentHistory(componentHistory);
				
				if(componentHistory1!=null){
					Component componentOld=componentMapper.selectByPrimaryKey(materialId);
					Component component=new Component();
					component.setId(componentHistory1.getId());
					component.setDprtcode(componentHistory1.getDprtcode());
					component.setJh(componentHistory1.getJh()); 
					component.setXlh(componentHistory1.getXlh());
					component.setChucrq(componentHistory1.getChucrq());
					component.setTsn(componentHistory1.getTsn());
					component.setTso(componentHistory1.getTso());
					component.setCsn(componentHistory1.getCsn());
					component.setCso(componentHistory1.getCso());
					component.setWhsj(componentHistory1.getWhsj());
					//新增或者修改b_h_010 部件
					if(componentOld==null){
						componentMapper.insertSelective(component);
					}else{
						componentMapper.updateByPrimaryKeySelective(component);
					}
				}
		
			}
			
		}
		List<Attachment> list = record.getAttachments();
		if (list != null && !list.isEmpty()) {
			for (Attachment attachment : list) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(record.getId());
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
				} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

				}
			}
		}
		// 保存证书
		saveComponentCertificate(record);
		
		record.setZdsj(currentDate);
		record.setJyrid(user.getId());
		return inspectionMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * @Description 保存证书
	 * @CreateTime 2017年10月26日 下午3:34:25
	 * @CreateBy 韩武
	 * @param record
	 */
	private void saveComponentCertificate(Inspection record){
		record.setCzls(UUID.randomUUID().toString());
		record.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		User user = ThreadVarUtil.getUser();
		
		// 删除部件证书
		delete(record);		
		
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
	public void delete(Inspection record){
		
		if(StringUtils.isNotBlank(record.getParamsMap().get("sn").toString())){
			record.getParamsMap().put("pch", "-");
		}else{
			record.getParamsMap().put("sn", "-");
			if(StringUtils.isBlank(record.getMaterialHistory().getPch())){
				record.getParamsMap().put("pch", "-");
			}
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

	@Override
	public int updateByPrimaryKey(Inspection record) {
		
		return inspectionMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Inspection> selectInspectionList(Inspection record) {
		
		return inspectionMapper.selectInspectionList(record);
	}

	@Override
	public Map<String, Object> queryAllPageList(Inspection inspection)throws Exception {
		//用户刚编辑过的记录 ID
				String id = inspection.getId();
				//清除查询条件中的ID，保证列表查询结果集的正确性
				inspection.setId(null);
				PageHelper.startPage(inspection.getPagination());
				List<Inspection> list = inspectionMapper.queryAllPageList(inspection);
				if(((Page)list).getTotal() > 0){
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						if(!PageUtil.hasRecord(list, id)){
							Inspection param = new Inspection();
							param.setId(id);
							List<Inspection> newRecordList = inspectionMapper.queryAllPageList(param);
							if(newRecordList != null && newRecordList.size() == 1){
								list.add(0, newRecordList.get(0));
							}
						}
					}
					return PageUtil.pack4PageHelper(list, inspection.getPagination());
				}else{
					List<Inspection> newRecordList = new ArrayList<Inspection>(1);
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						Inspection param = new Inspection();
						param.setId(id);
						newRecordList = inspectionMapper.queryAllPageList(param);
						if(newRecordList != null && newRecordList.size() == 1){
							return PageUtil.pack(1, newRecordList, inspection.getPagination());
						}
					}
					return PageUtil.pack(0, newRecordList, inspection.getPagination());
				}
		
	}

	 /**
	  * 
	  * @Description 查询航材工具检验
	  * @CreateTime 2018年3月26日 下午5:10:19
	  * @CreateBy 林龙
	  * @param inspection
	  * @return
	  * @throws Exception
	  */
	@Override
	public Inspection getByInspectionId(Inspection inspection) throws Exception {
		
		return inspectionMapper.getByInspectionId(inspection);
	}
	
	/**
	 * 
	 * @Description 保存航材工具检验
	 * @CreateTime 2018年3月27日 下午1:57:15
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public int updateByPrimary(Inspection inspection) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		//修改
		return uodateinspection(inspection,czls);
	}

	private int uodateinspection(Inspection inspection, String czls) throws BusinessException {
	
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		//新增附件
		attachmentService.eidtAttachment(inspection.getAttachmentList(), inspection.getId(), czls, inspection.getId(), user.getJgdm(), LogOperationEnum.EDIT);
		
		// 保存证书
		saveComponentCertificateNEW(inspection);
		
		inspection.setZdsj(currentDate);
		inspection.setJyrid(user.getId());
		
		
		//当页面没有填写单编号时
		if(inspection.getJydh() == null || "".equals(inspection.getJydh())){ 
			String shdh=this.setDh(inspection,user); //根据对象获取最新编号
			inspection.setJydh(shdh);
		}
		
		//修改
		return inspectionMapper.updateByPrimaryKeySelective(inspection);
	}

	/**
	 * 
	 * @param user 
	 * @Description  根据对象获取最新编号
	 * @CreateTime 2017年8月17日 下午4:32:44
	 * @CreateBy 林龙
	 * @param technical
	 * @return pgdbhNew 编号
	 */
	private String setDh(Inspection inspection, User user) throws BusinessException{
		String jydh = null;
		Inspection tec = new Inspection(); //new 对象
		boolean b = true;
		while(b){
			try {
				jydh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_ZJD.getName(),inspection); 
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			tec.setJydh(jydh);
			tec.setDprtcode(user.getJgdm());
			//根据对象查询数量
			int i = inspectionMapper.queryCount(tec); 
			if(i <= 0){
				b = false;
			}
		}
		return jydh;
	}

	/**
	 * 
	 * @Description 提交航材工具检验
	 * @CreateTime 2018年3月27日 下午1:57:15
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public int updateByPrimarySubmit(Inspection inspection) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();//流水号
		Date currentDate = commonService.getSysdate();//当前时间
		//根据检验单id查询很bh001库存数据
		List<Stock> stocklist=stockSerivce.queryjydid(inspection.getId());
		for (Stock stock : stocklist) {
			Stock stockNew=new Stock();
			stockNew.setId(stock.getId());
			stockNew.setHcly(inspection.getHcly());
			stockNew.setScrq(inspection.getScrq());
			stockNew.setHjsm(inspection.getHjsm());
			stockNew.setTsn(inspection.getTsn());
			stockNew.setCsn(inspection.getCsn());
			stockNew.setTso(inspection.getTso());
			stockNew.setCso(inspection.getCso());
			stockNew.setGrn(inspection.getGrn());
			stockNew.setCfyq(inspection.getCfyq());
			stockSerivce.updateByPrimaryKeySelective(stockNew);
			materialRecService.writeStockRec(stock.getId(), czls.toString(), inspection.getId(), inspection.getJydh(), StockRecBizTypeEnum.TYPE5, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE0.getName(),"",inspection.getJydh(),inspection.getJydh(),inspection.getKysl());
			
		}
		

		
		//根据检验单id查询bh003库存数据
		List<OutFieldStock> outFieldStocklist=outFieldStockService.queryjydid(inspection.getId());
		for (OutFieldStock outFieldStock : outFieldStocklist) {
			outFieldStock.setHcly(inspection.getHcly());
			outFieldStock.setScrq(inspection.getScrq());
			outFieldStock.setHjsm(inspection.getHjsm());
			outFieldStock.setTsn(inspection.getTsn());
			outFieldStock.setCsn(inspection.getCsn());
			outFieldStock.setTso(inspection.getTso());
			outFieldStock.setGrn(inspection.getGrn());
			outFieldStock.setCso(inspection.getCso());
			outFieldStock.setCfyq(inspection.getCfyq());
			outFieldStockService.updateByPrimaryKeySelective(outFieldStock);
			materialRecService.writeStockRec(outFieldStock.getId(), czls.toString(), inspection.getId(), inspection.getJydh(), StockRecBizTypeEnum.TYPE5, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE0.getName(),"",inspection.getJydh(),inspection.getJydh(),inspection.getKysl());
			
		}
		
		if(StringUtils.isNotBlank(inspection.getParamsMap().get("gljb").toString())  && Integer.valueOf((String) inspection.getParamsMap().get("gljb")) == 3){//当部件为序列号件时
			//根据检验单id=b_h2_025.装机清单id来增量处理
			inspection.setSn(inspection.getParamsMap().get("sn").toString()); 
			inspection.setBjh(inspection.getParamsMap().get("bjh").toString());
			storeInnerSearchMapper.batchInsertOrUpdateBjInspection(inspection);
			
			//汇总b_h2_025取最新时间记录更新到b_h_010部件数据中
			InstallationListEditable installationListEditable =new InstallationListEditable();
			installationListEditable.setBjh(inspection.getParamsMap().get("bjh").toString());
			installationListEditable.setXlh(inspection.getParamsMap().get("sn").toString());
			installationListEditable.setDprtcode(inspection.getDprtcode());
			int updateCount = this.componentMapper.updateCompnent2Newest(installationListEditable);
			if(updateCount == 0){
				installationListEditable.setChucrq(inspection.getScrq());
				installationListEditable.setTsn(inspection.getTsn());
				installationListEditable.setTso(inspection.getTso());
				installationListEditable.setCsn(Integer.valueOf(inspection.getCsn()));
				installationListEditable.setCso(Integer.valueOf(inspection.getCso()));
				installationListEditable.setZjzt(1);
				installationListEditable.setAzsj(currentDate);
				this.componentMapper.insertByInstallList(installationListEditable);
			}
		}
		
		
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(inspection.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(33);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		Stock stock=null;
		if(stocklist.size()>0){
		      stock=stocklist.get(0);
		}
		String gnbm="";
		Integer dbgzlx=null;
		String id=null;
		String ywms=null;
		String bjh=null;
		String sn=null;
		String pch=null;
		Integer hclx=null;
		if(stock!=null){
			id=stock.getId();
			bjh=stock.getBjh();
			sn=stock.getSn();
			pch=stock.getPch();
			if(stock.gethCMainData()!=null){
				ywms=stock.gethCMainData().getYwms();
				hclx=stock.gethCMainData().getHclx();
			}
		}
		if(hclx!=null){
			if(!MaterialTypeEnum.APPOINTED.getId().equals(hclx)){//如果不是工具
				dbgzlx=UndoEnum.HCSJ.getId();	
				gnbm="material:outin:materialstockin";
			}else if(MaterialTypeEnum.APPOINTED.getId().equals(hclx)){//如果是工具
				dbgzlx=UndoEnum.GJSJ.getId();
				gnbm="material:outin:toolstockin";
			}
		}
		//质检提交,写入待办
		StringBuilder sm=new StringBuilder();
		sm.append("待上架: ");
		if(StringUtils.isNotBlank(ywms))
			sm.append(ywms);
		if(StringUtils.isNotBlank(bjh))
			sm.append(" /").append(bjh);
		if(StringUtils.isNotBlank(sn))
			sm.append(" /").append(sn);
		if(StringUtils.isNotBlank(pch))
			sm.append(" /").append(pch);
			
		todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), TodoStatusEnum.DCL.getId(), id, null, null, 
				null, dbgzlx, NodeEnum.NODE31.getId(), sm.toString(),null, null, gnbm, id, null);
		
		//修改
		return uodateinspection(inspection,czls);
		
		
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
