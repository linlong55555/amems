package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ShapeStructureRepairMapper;
import com.eray.thjw.project2.po.ShapeStructureRepair;
import com.eray.thjw.project2.service.ShapeStructureRepairService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class ShapeStructureRepairServiceImpl implements ShapeStructureRepairService{

	@Autowired
	private ShapeStructureRepairMapper shapeStructureRepairMapper;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private PlaneModelDataService planeModelDataService;

	@Override
	public List<ShapeStructureRepair> getList(ShapeStructureRepair record) {
		List<ShapeStructureRepair> list=shapeStructureRepairMapper.getList(record);
		if(list.size()>0){
			List<String> ids=new ArrayList<String>();
			for (ShapeStructureRepair shapeStructureRepair : list) {
				if(shapeStructureRepair.getXlhfj()!=null&&!shapeStructureRepair.getXlhfj().equals("")){
					ids.add(shapeStructureRepair.getXlhfj());				
				}
				if(shapeStructureRepair.getXlqfj()!=null&&!shapeStructureRepair.getXlqfj().equals("")){
					ids.add(shapeStructureRepair.getXlqfj());				
				}
				if(shapeStructureRepair.getXlyjfj()!=null&&!shapeStructureRepair.getXlyjfj().equals("")){
					ids.add(shapeStructureRepair.getXlyjfj());				
				}
			}
			if(ids.size()>0){
				List<Attachment> attList=attachmentMapper.queryPlaneDataListAttachmentsByMainIds(ids);
				if(attList.size()>0){
					for (ShapeStructureRepair shapeStructureRepair : list) {
						List<Attachment> attachments=new ArrayList<Attachment>();
						List<Attachment> xlhAttachment=new ArrayList<Attachment>();
						List<Attachment> xlyjAttachment=new ArrayList<Attachment>();
						for (Attachment attachment : attList) {
							if(shapeStructureRepair.getXlqfj()!=null&&shapeStructureRepair.getXlqfj().equals(attachment.getMainid())){
								attachments.add(attachment);
							}
							if(shapeStructureRepair.getXlhfj()!=null&&shapeStructureRepair.getXlhfj().equals(attachment.getMainid())){
								xlhAttachment.add(attachment);
							}
							if(shapeStructureRepair.getXlyjfj()!=null&&shapeStructureRepair.getXlyjfj().equals(attachment.getMainid())){
								xlyjAttachment.add(attachment);
							}
						}
						shapeStructureRepair.setAttachments(attachments);
						shapeStructureRepair.setXlhAttachment(xlhAttachment);
						shapeStructureRepair.setXlyjAttachment(xlyjAttachment);
					}
					
				}
			}
		}
		return list;
	}

	@Override
	public void insertRecord(ShapeStructureRepair record) throws BusinessException {
		
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		List<String> fjzchList=new ArrayList<String>();
		fjzchList.add(record.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), user.getJgdm(), fjzchList);
		List<Attachment> list = record.getAttachments();
		if (list != null && !list.isEmpty()) {
			String xlqfj=UUID.randomUUID().toString();
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : list) {
				attachment.setMainid(xlqfj);
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,record.getId());
			record.setXlqfj(xlqfj);
		}
		List<Attachment> xlhlist = record.getXlhAttachment();
		if (xlhlist != null && !xlhlist.isEmpty()) {
			String xlhfj=UUID.randomUUID().toString();
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : xlhlist) {
				attachment.setMainid(xlhfj);
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,record.getId());
			record.setXlhfj(xlhfj);
		}
		
		List<Attachment> xlyjlist = record.getXlyjAttachment();
		if (xlyjlist != null && !xlyjlist.isEmpty()) {
			String xlhfj=UUID.randomUUID().toString();
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : xlyjlist) {
				attachment.setMainid(xlhfj);
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,record.getId());
			record.setXlyjfj(xlhfj);
		}
		shapeStructureRepairMapper.insertRecord(record);
		commonRecService.write(record.getId(), TableEnum.B_G_015, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE,record.getId());
		
	}

	@Override
	public void updateRecordById(ShapeStructureRepair record) throws BusinessException {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		List<String> fjzchList=new ArrayList<String>();
		fjzchList.add(record.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), record.getDprtcode(), fjzchList);
		List<Attachment> attachments = record.getAttachments();
		ShapeStructureRepair ssRepair=shapeStructureRepairMapper.getRecordById(record.getId());
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					if(ssRepair.getXlqfj()!=null){
						attachment.setMainid(ssRepair.getXlqfj());						
					}else{
						String xlqfj=UUID.randomUUID().toString();
						attachment.setMainid(xlqfj);
						record.setXlqfj(xlqfj);
						ssRepair.setXlqfj(xlqfj);
					}
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,record.getId());					
				} 
			}
		}
		
		List<Attachment> xlhAttachments = record.getXlhAttachment();
		if (xlhAttachments != null && !xlhAttachments.isEmpty()) {
			for (Attachment attachment : xlhAttachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					if(ssRepair.getXlhfj()!=null){
						attachment.setMainid(ssRepair.getXlhfj());						
					}else{
						String xlhfj=UUID.randomUUID().toString();
						attachment.setMainid(xlhfj);
						record.setXlhfj(xlhfj);
						ssRepair.setXlhfj(xlhfj);
					}
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,record.getId());
				} 
			}
		}
		
		List<Attachment> xlyjAttachments = record.getXlyjAttachment();
		if (xlyjAttachments != null && !xlyjAttachments.isEmpty()) {
			for (Attachment attachment : xlyjAttachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					if(ssRepair.getXlyjfj()!=null){
						attachment.setMainid(ssRepair.getXlyjfj());						
					}else{
						String xlyjfj=UUID.randomUUID().toString();
						attachment.setMainid(xlyjfj);
						record.setXlyjfj(xlyjfj);
						ssRepair.setXlyjfj(xlyjfj);
					}
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,record.getId());
				} 
			}
		}
		
		//需要删除的附件
		List<String> delAttachements = record.getDelAttachements();
		if(delAttachements != null && delAttachements.size() > 0){
			for (String id : delAttachements) {
				attachmentMapper.updateByPrimaryKey(id);
				commonRecService.write(id, TableEnum.D_011, user.getId(), czls,
						LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,record.getId());
			}
		}
		
		shapeStructureRepairMapper.updateRecordById(record);
		commonRecService.write(record.getId(), TableEnum.B_G_015, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,record.getId());
		
	}


	@Override
	public ShapeStructureRepair getRecordById(String id)throws BusinessException {
		ShapeStructureRepair sr=shapeStructureRepairMapper.getRecordById(id);
		Attachment attachment=new Attachment();
		if(sr.getXlqfj()!=null && !"".equals(sr.getXlqfj())){
			attachment.setMainid(sr.getXlqfj());
			List<Attachment> attachmentList=attachmentMapper.queryListAttachments(attachment);
			sr.setAttachments(attachmentList);
		}
		if(sr.getXlhfj()!=null && !"".equals(sr.getXlhfj())){
			attachment.setMainid(sr.getXlhfj());
			List<Attachment> attachmentList=attachmentMapper.queryListAttachments(attachment);
			sr.setXlhAttachment(attachmentList);
		}
		if(sr.getXlyjfj()!=null && !"".equals(sr.getXlyjfj())){
			attachment.setMainid(sr.getXlyjfj());
			List<Attachment> attachmentList=attachmentMapper.queryListAttachments(attachment);
			sr.setXlyjAttachment(attachmentList);
		}
		return sr;
	}

	@Override
	public void deleteById(String id) throws BusinessException {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();	
		ShapeStructureRepair ssRepair=shapeStructureRepairMapper.getRecordById(id);
		List<String> fjzchList=new ArrayList<String>();
		fjzchList.add(ssRepair.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), ssRepair.getDprtcode(), fjzchList);
		commonRecService.write(id, TableEnum.B_G_015, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,id);
		if(ssRepair.getXlqfj()!=null){
		commonRecService.writeByWhere(" b.mainid = '".concat(ssRepair.getXlqfj()).concat("' ").concat("and b.yxzt=1"), TableEnum.D_011, user.getId(),czls,LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,id);
		attachmentMapper.updateByMainid(ssRepair.getXlqfj());
		}
		if(ssRepair.getXlhfj()!=null){
		commonRecService.writeByWhere(" b.mainid = '".concat(ssRepair.getXlhfj()).concat("' ").concat("and b.yxzt=1"), TableEnum.D_011, user.getId(),czls,LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,id);
		attachmentMapper.updateByMainid(ssRepair.getXlhfj());
		}
		if(ssRepair.getXlyjfj()!=null){
			commonRecService.writeByWhere(" b.mainid = '".concat(ssRepair.getXlyjfj()).concat("' ").concat("and b.yxzt=1"), TableEnum.D_011, user.getId(),czls,LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,id);
			attachmentMapper.updateByMainid(ssRepair.getXlyjfj());
		}
		shapeStructureRepairMapper.deleteById(id);
		
	}

	
   
	
}
