package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.QualityReviewMapper;
import com.eray.thjw.aerialmaterial.dao.QualityReviewRecordMapper;
import com.eray.thjw.aerialmaterial.po.QualityReview;
import com.eray.thjw.aerialmaterial.po.QualityReviewRecord;
import com.eray.thjw.aerialmaterial.service.QualityReviewService;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class QualityReviewServiceImpl implements QualityReviewService {
	@Autowired
	private QualityReviewMapper qualityReviewMapper;
	@Autowired
	private QualityReviewRecordMapper qualityReviewRecordMapper;
	@Resource
	private CommonRecService commonRecService;
	@Autowired
	private SaibongUtilService saibongUtilService;     
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	
	@Override
	public void insertRecord(QualityReview record) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		List<Attachment> list = record.getAttachments();
		String rkdh=saibongUtilService.generate(ThreadVarUtil.getUser().getJgdm(), SaiBongEnum.ZWZLSC.getName());//单号
		record.setDh(rkdh);
		String mainid = UUID.randomUUID().toString();//附件mainid
		if (list != null && !list.isEmpty()) {
			record.setXffjid(mainid);
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : list) {
				attachment.setMainid(mainid);
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,record.getId());
		}
		if(record.getZt()==2){
		QualityReviewRecord reviewRecord=new QualityReviewRecord();
		String id=UUID.randomUUID().toString();
		reviewRecord.setId(id);
		reviewRecord.setCzrid(record.getXfrid());
		reviewRecord.setDprtcode(record.getDprtcode());
		reviewRecord.setMainid(record.getId());
		reviewRecord.setCzsm(record.getXfyy());
		reviewRecord.setHd("下发");
		if(list.size()>0){
			reviewRecord.setFjid(mainid);
		}
		qualityReviewRecordMapper.insertRecord(reviewRecord);
		}
		qualityReviewMapper.insertRecord(record);
		commonRecService.write(record.getId(), TableEnum.B_Z_002, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE,record.getId());
	}
	@Override
	public List<QualityReview> selectQualityReviewList(QualityReview record) {
		
		List<String> fjList=new ArrayList<String>();
		if(record.getParamsMap()!=null&&record.getParamsMap().get("lx")!=null&&record.getParamsMap().get("lx").equals("2")){
			List<QualityReview> list=qualityReviewMapper.selectQualityReviewList(record);
			for (QualityReview qualityReview : list) {
				if(qualityReview.getXffjid()!=null){
					fjList.add(qualityReview.getXffjid());
				}
				if(qualityReview.getZgfjid()!=null){
					fjList.add(qualityReview.getZgfjid());
				}
				if(qualityReview.getShfjid()!=null){
					fjList.add(qualityReview.getShfjid());
				}
				if(qualityReview.getGbfjid()!=null){				
					fjList.add(qualityReview.getGbfjid());
				}
			}
			if(fjList.size()>0){
				List<Attachment> attList=attachmentMapper.queryPlaneDataListAttachmentsByMainIds(fjList);
				return getList(list,attList);
			}else{
				return list;
			}
		}
		else{
			List<QualityReview> list=qualityReviewMapper.selectQualityReviewList(record);
			for (QualityReview qualityReview : list) {
				if(qualityReview.getXffjid()!=null){
					fjList.add(qualityReview.getXffjid());
				}
				if(qualityReview.getZgfjid()!=null){
					fjList.add(qualityReview.getZgfjid());
				}
				if(qualityReview.getShfjid()!=null){
					fjList.add(qualityReview.getShfjid());
				}
				if(qualityReview.getGbfjid()!=null){				
					fjList.add(qualityReview.getGbfjid());
				}
			}
			if(fjList.size()>0){
				List<Attachment> attList=attachmentMapper.queryPlaneDataListAttachmentsByMainIds(fjList);
			return getList(list,attList);
			}else{
				return list;
			}
		}
	}
	@Override
	public QualityReview selectQualityReviewById(String id) {
		
			return qualityReviewMapper.selectQualityReviewById(id);
	}
	@Override
	public void updateQualityReviewById(QualityReview record) {
		QualityReview qr=qualityReviewMapper.selectQualityReviewById(record.getId());
		if(record.getZt()==3&&qr.getZt()==9){
			throw new RuntimeException("该整理信息已经关闭,不能反馈了!");
		}
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		List<Attachment> list = record.getAttachments();
		String mainid = UUID.randomUUID().toString();
		if (list !=null&&list.size()>0) {
			record=getAttachmentMainid(mainid,record,user,czls);			
		}else{
			if(record.getZt()==2||record.getZt()==3||record.getZt()==9||record.getZt()==10){
				addRecord(record,"");
			}
		}
		qualityReviewMapper.updateQualityReviewById(record);
		commonRecService.write(record.getId(), TableEnum.B_Z_002, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,record.getId());
	}
	
	private String getHd(int zt,String shrid){
		if(zt==2&&shrid==null){
			return "下发";
		}else if(zt==3){
			return "处理";
		}else if(zt==9){
			return "关闭";
		}else if(zt==10){
			return "完成";
		}else if(zt==2&&shrid!=null){
			return "审核退回";
		}else{
			return "";
		}
	}
	
	private QualityReview getAttachmentMainid(String mainid,QualityReview record,User user,String czls){
		//下发
		if(record.getZt()==2&&record.getShrid()==null){			
			record.setXffjid(mainid);
			addRecord(record,mainid);		
			addAttachment(record,user,czls);
		}
		//反馈整改
		if(record.getZt()==3){
			record.setZgfjid(mainid);
			addRecord(record,mainid);
			addAttachment(record,user,czls);
		}
		//关闭
		if(record.getZt()==9){
			record.setGbfjid(mainid);
			addRecord(record,mainid);
			addAttachment(record,user,czls);
		}
		//审核完成
		if(record.getZt()==10){
			record.setShfjid(mainid);
			addRecord(record,mainid);
			addAttachment(record,user,czls);
		}
		//审核驳回
		if(record.getZt()==2&&record.getShrid()!=null){			
			record.setShfjid(mainid);
			addRecord(record,mainid);
			addAttachment(record,user,czls);
		}
		//保存
		if(record.getZt()==1){		
			record.setXffjid(mainid);
			addAttachment(record,user,czls);
		}
		return record;
	}
	
	/**
	 * 新增b_z_00201记录
	 * @param record
	 * @param mainid
	 */
	private void addRecord(QualityReview record,String mainid){
		QualityReviewRecord reviewRecord=new QualityReviewRecord();
		String id=UUID.randomUUID().toString();
		reviewRecord.setId(id);
		reviewRecord.setCzrid(record.getWhrid());
		reviewRecord.setDprtcode(record.getDprtcode());
		reviewRecord.setMainid(record.getId());
		if(record.getZt()==2&&record.getShrid()==null){
			QualityReview qr=qualityReviewMapper.selectQualityReviewById(record.getId());
			if(qr.getXffjid()!=null&&!qr.getXffjid().equals("")&&mainid.equals("")){
				reviewRecord.setFjid(qr.getXffjid());
			}else{
				reviewRecord.setFjid(mainid);
			}
			reviewRecord.setCzsm(record.getXfyy());						
		}
		if(record.getZt()==3){
			reviewRecord.setCzsm(record.getZgsm());			
			reviewRecord.setFjid(mainid);				
		}
		if(record.getZt()==9){
			reviewRecord.setCzsm(record.getGbyy());			
			reviewRecord.setFjid(mainid);
		}
		if(record.getZt()==10){
			reviewRecord.setCzsm(record.getShsm());			
			reviewRecord.setFjid(mainid);
		}
		if(record.getZt()==2&&record.getShrid()!=null){
			reviewRecord.setCzsm(record.getShsm());			
			reviewRecord.setFjid(mainid);
		}
		reviewRecord.setHd(getHd(record.getZt(),record.getShrid()));			
		qualityReviewRecordMapper.insertRecord(reviewRecord);
	}
	
	/**
	 * 处理附件
	 * @param record
	 * @param user
	 * @param czls
	 */
	private void addAttachment(QualityReview record,User user,String czls){
		List<Attachment> list = record.getAttachments();
		for (Attachment attachment : list) {
			if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
			if(record.getZt()==1){
				attachment.setMainid(record.getXffjid());
			}
			if(record.getZt()==2&&record.getShrid()==null){
				attachment.setMainid(record.getXffjid());
			}
			if(record.getZt()==3){
				attachment.setMainid(record.getZgfjid());
			}
			if(record.getZt()==9){
				attachment.setMainid(record.getGbfjid());
			}
			if(record.getZt()==10){
				attachment.setMainid(record.getShfjid());
			}
			if(record.getZt()==2&&record.getShrid()!=null){
				attachment.setMainid(record.getShfjid());
			}
			attachment.setDprtcode(user.getJgdm());
			attachment.setId(UUID.randomUUID().toString());
			attachment.setCzrid(user.getId());
			attachment.setCzbmid(user.getBmdm());
			attachmentMapper.addFile(attachment);
			commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
					LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,record.getId());
			}else if (attachment.getOperate().equals(OperateEnum.DEL)) {
				attachmentMapper.updateByPrimaryKey(attachment.getId());
				commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
						LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,record.getId());
			} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

			}
		}
	
	}
	
	private List<QualityReview> getList(List<QualityReview> list,List<Attachment> attList){		
		for (QualityReview qualityReview : list) {
			List<Attachment> xfattachments=new ArrayList<Attachment>();	
			List<Attachment> zgattachments=new ArrayList<Attachment>();
			List<Attachment> shattachments=new ArrayList<Attachment>();	
			List<Attachment> gbattachments=new ArrayList<Attachment>();	
			for (Attachment attachment : attList) {
			if(qualityReview.getXffjid()!=null&&!qualityReview.getXffjid().equals("")){
				if(qualityReview.getXffjid().equals(attachment.getMainid())){
					xfattachments.add(attachment);
				}
			}
				if(qualityReview.getZgfjid()!=null&&!qualityReview.getZgfjid().equals("")){
					
					if(qualityReview.getZgfjid().equals(attachment.getMainid())){
						zgattachments.add(attachment);
				}					
			}
				if(qualityReview.getShfjid()!=null&&!qualityReview.getShfjid().equals("")){
					
					if(qualityReview.getShfjid().equals(attachment.getMainid())){
						shattachments.add(attachment);
				}					
			}
				if(qualityReview.getGbfjid()!=null&&!qualityReview.getGbfjid().equals("")){
					
					if(qualityReview.getGbfjid().equals(attachment.getMainid())){
						gbattachments.add(attachment);
				}					
			}
		}
			if(xfattachments.size()>0){
				qualityReview.setXfattachments(xfattachments);
			}
			if(zgattachments.size()>0){
				qualityReview.setZgattachments(zgattachments);
			}
			if(shattachments.size()>0){
				qualityReview.setShattachments(shattachments);
			}
			if(gbattachments.size()>0){
				qualityReview.setGbattachments(gbattachments);
			}				
		}
		return list;
	}
}
