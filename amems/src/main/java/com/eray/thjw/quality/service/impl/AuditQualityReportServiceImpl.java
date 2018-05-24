package com.eray.thjw.quality.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.QualityAuditReportMapper;
import com.eray.thjw.quality.po.AnnualAuditPlan;
import com.eray.thjw.quality.po.AuditMembers;
import com.eray.thjw.quality.po.DistributeDepartment;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.quality.po.QualityAuditReport;
import com.eray.thjw.quality.service.AuditQualityReportService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.quality.BusinessTypeEnum;
import enu.quality.QualityAuditReportEnum;

@Service
public class AuditQualityReportServiceImpl implements AuditQualityReportService{
	
@Autowired	
private QualityAuditReportMapper qualityAuditReportMapper;	

@Autowired
private AttachmentService attachmentService;

@Resource
private SaibongUtilService saibongUtilService;

	@Override
	public Map<String,Object> getList(QualityAuditReport report) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = report.getId();
		report.setId("");
		try {
			PageHelper.startPage(report.getPagination());
			List<QualityAuditReport> recordList = qualityAuditReportMapper.getQualityAuditReportList(report);
			        for(QualityAuditReport quality:recordList){
			        	quality.setAttachments(attachmentService.selectAttachments(quality.getId()));
			        }
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						QualityAuditReport newReport = new QualityAuditReport();
						newReport.setId(id);
						List<QualityAuditReport> newRecordList = qualityAuditReportMapper.getQualityAuditReportList(report);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, report.getPagination());
				return resultMap;

			} else {
				List<QualityAuditReport> newRecordList = new ArrayList<QualityAuditReport>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					QualityAuditReport newRecord = new QualityAuditReport();
					newRecord.setId(id);
					newRecordList = qualityAuditReportMapper.getQualityAuditReportList(report);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, report.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, report.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}

	@Override
	public List<String> validatShbhUnique(String zlshbh, String dprtcode,String id)
			throws BusinessException {		
		return qualityAuditReportMapper.validatShbhUnique(zlshbh, dprtcode,id);
	}
	
	@Override
	public int save(QualityAuditReport report) throws BusinessException, SaibongException {
		
		String id=report.getId();
		
		Map<String, Object> map = report.getParamsMap();

		String status = (String) map.get("saveorsubmit");

		User user = ThreadVarUtil.getUser();
		String uuid = UUID.randomUUID().toString();
		if(StringUtils.isBlank(id)){
			report.setId(uuid);
		}
		report.setDprtcode(user.getJgdm());
		report.setWhbmid(user.getBmdm());
		report.setWhrid(user.getId());
		report.setWhsj(new Date());
		String zlshbh=null;
		if(StringUtils.isBlank(report.getZlshbgbh())){
			try {
				 zlshbh=saibongUtilService.generate(user.getJgdm(), SaiBongEnum.QSB.getName());
				 report.setZlshbgbh(zlshbh);
			} catch (SaibongException e) {
				throw new SaibongException("塞班获取质量审核编号异常.....");
			}
		}

		if ("addSave".equals(status)) {
			report.setZt(QualityAuditReportEnum.SAVE.getId());
		} else if ("addSubmit".equals(status)) {
			report.setZt(QualityAuditReportEnum.SUBMIT.getId());
		} else if("editSubmit".equals(status)){
			report.setZt(QualityAuditReportEnum.SUBMIT.getId());
		} else if("auditRejectSave".equals(status)){
			report.setZt(QualityAuditReportEnum.AUDIT_BACK.getId());
		} else if("auditPassSave".equals(status)){
			report.setZt(QualityAuditReportEnum.AUDIT.getId());
		}else if("approveRejectSave".equals(status)){
			report.setZt(QualityAuditReportEnum.APPROVE_BACK.getId());
		}else if("approvePassSave".equals(status)){
			report.setZt(QualityAuditReportEnum.APPROVE.getId());
		}


		// 审核成员
		List<Map> shcy = (List<Map>) map.get("shcyArrays");
				
		List<AuditMembers> listShcy=mapToBean(shcy,AuditMembers.class);
		
		  for(AuditMembers auditMember:listShcy){
			  auditMember.setId(UUID.randomUUID().toString());
			  auditMember.setDprtcode(user.getJgdm());
			  auditMember.setZt(1);
			  auditMember.setWhbmid(user.getBmdm());
			  auditMember.setWhrid(user.getId());
			  auditMember.setWhsj(new Date());
			  auditMember.setYwid(report.getId());
			  auditMember.setYwlx(BusinessTypeEnum.ZLSHBG.getId());
		  }
		
		// 分发部门
		List<Map> ffbm = (List<Map>) map.get("ffbmArrays");
		 
		List<DistributeDepartment>  listDepartments=mapToBean(ffbm,DistributeDepartment.class);
		
		for(DistributeDepartment deprt:listDepartments){
			deprt.setId(UUID.randomUUID().toString());
			deprt.setDprtcode(user.getJgdm());
			deprt.setZt(1);
			deprt.setWhbmid(user.getBmdm());
			deprt.setWhrid(user.getId());
			deprt.setWhsj(new Date());
			deprt.setYwid(report.getId());
			deprt.setYwlx(BusinessTypeEnum.ZLSHBG.getId());
		}
		
		//保存质量审核报告
		if(!StringUtils.isBlank(id)){
			report.setId(id);
			qualityAuditReportMapper.updateAuditReportById(report);
		}else{		
			qualityAuditReportMapper.saveAuditReport(report);
		}
		
		//批量保存审核成员
		qualityAuditReportMapper.deleteAuditMember(report.getId());
		if(listShcy.size()>0){
			
			qualityAuditReportMapper.saveAuditMember(listShcy);
		}
		
		//批量保存分发部门
		
		qualityAuditReportMapper.deleteDistributeDepartment(report.getId());
		qualityAuditReportMapper.saveDistributeDepartment(listDepartments);
		
		
		//保存附件
		attachmentService.eidtAttachment(report.getAttachments(), report.getId(), 
				null, report.getId(), user.getJgdm(), LogOperationEnum.UPDATE_UPDATE);

		
		
		//处理流程记录
		saveFlowByZt(status,report.getId());
		
			
			return 0;
		}
	
	
	private void saveFlowByZt(String status,String mainId) {
		if("addSubmit".equals(status)||"editSubmit".equals(status)||"auditRejectSave".equals(status)||"auditPassSave".equals(status)||"approveRejectSave".equals(status)||"approvePassSave".equals(status)){
			User user = ThreadVarUtil.getUser();
			ProcessRecord record=new ProcessRecord();		
			record.setId(UUID.randomUUID().toString());
			record.setMainid(mainId);
			record.setDprtcode(user.getJgdm());
			record.setCzrmc(user.getRealname());
			record.setBmmc(user.getDepartmentName());
			record.setCzsj(new Date());
			if("addSubmit".equals(status)){
				record.setCzsm("提交质量审核报告");
			}else if("editSubmit".equals(status)){
				record.setCzsm("提交质量审核报告");
			}else if("auditRejectSave".equals(status)){
				record.setCzsm("审核驳回");
			}else if("auditPassSave".equals(status)){
				record.setCzsm("审核通过");
			}else if("approveRejectSave".equals(status)){
				record.setCzsm("审批驳回");
			}else if("approvePassSave".equals(status)){
				record.setCzsm("审批通过");
			}
			qualityAuditReportMapper.saveFlowRecord(record);
		
		}
		
	}

	private <T> List<T> mapToBean(List<Map> list,Class<T> clz) throws BusinessException{
		 Map map=null;
		 List<T> li=new ArrayList<T>();
		 try {			
			for(int i=0;i<list.size();i++){
				Object obj=clz.newInstance();
				  map=list.get(i);
				  BeanUtils.populate(obj, map);
				  li.add((T)obj);
			}
			
		} catch (InstantiationException e) {
			 throw new BusinessException("转换异常");
		} catch (IllegalAccessException e) {
			throw new BusinessException("转换异常");
		} catch (InvocationTargetException e) {
			throw new BusinessException("转换异常");
		}
	
		
		return li;	
	}

	@Override
	public int delete(String id) throws BusinessException {
		qualityAuditReportMapper.deleteAuditReport(id);
		qualityAuditReportMapper.deleteAuditMember(id);
		qualityAuditReportMapper.deleteDistributeDepartment(id);
		return 0;
	}

	@Override
	public QualityAuditReport selectBykey(String key) throws BusinessException {
		// TODO Auto-generated method stub
		return qualityAuditReportMapper.selectByKey(key);
	}

	@Override
	public QualityAuditReport selectByQualityNum(QualityAuditReport report)
			throws BusinessException {
		// TODO Auto-generated method stub
		return qualityAuditReportMapper.selectByQualityReportNum(report);
	}

}
