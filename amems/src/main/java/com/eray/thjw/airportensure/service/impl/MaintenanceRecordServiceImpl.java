package com.eray.thjw.airportensure.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.airportensure.dao.MaintenanceRecordMapper;
import com.eray.thjw.airportensure.po.MaintenanceRecord;
import com.eray.thjw.airportensure.service.MaintenanceRecordService;
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
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {
	@Autowired
	private MaintenanceRecordMapper maintenanceRecordMapper;
	@Resource
	private CommonRecService commonRecService;
	@Autowired
	private SaibongUtilService saibongUtilService;     
	@Resource
	private AttachmentMapper attachmentMapper;

	@Override
	public void insertMaintenanceRecord(MaintenanceRecord maintenanceRecord) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
//		String id = UUID.randomUUID().toString();
//		maintenanceRecord.setId(id);
		String wxdbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JCBZWX.getName());
		maintenanceRecord.setWxdbh(wxdbh);
		maintenanceRecordMapper.insertMaintenanceRecord(maintenanceRecord);
		List<Attachment> list = maintenanceRecord.getAttachments();
		if (list != null && !list.isEmpty()) {
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : list) {
				attachment.setMainid(maintenanceRecord.getId());
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,maintenanceRecord.getId());
		}
		commonRecService.write(maintenanceRecord.getId(), TableEnum.B_J_001, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE,maintenanceRecord.getId());
	}

	@Override
	public List<MaintenanceRecord> selectMaintenanceRecordList(MaintenanceRecord maintenanceRecord) throws Exception {

		return maintenanceRecordMapper.selectMaintenanceRecordList(maintenanceRecord);
	}

	@Override
	public void updateZtById(String id) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		commonRecService.write(id, TableEnum.B_J_001, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
		commonRecService.writeByWhere(" b.mainid = '".concat(id).concat("' ").concat("and b.yxzt=1"), TableEnum.D_011, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
		maintenanceRecordMapper.updateZtById(id);
		attachmentMapper.updateByMainid(id);
	}

	@Override
	public MaintenanceRecord getMaintenanceRecordById(MaintenanceRecord maintenanceRecord) throws Exception {
		MaintenanceRecord mr=maintenanceRecordMapper.getMaintenanceRecordById(maintenanceRecord);
		Attachment attachment=new Attachment();
		attachment.setMainid(mr.getId());
		List<Attachment> attachmentList=attachmentMapper.queryListAttachments(attachment);
		if(attachmentList.size()>0){
			mr.setAttachments(attachmentList);
		}
		return mr;
	}

	@Override
	public void updateMaintenanceRecordById(MaintenanceRecord maintenanceRecord) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();

		maintenanceRecordMapper.updateMaintenanceRecordById(maintenanceRecord);
		List<Attachment> attachments = maintenanceRecord.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(maintenanceRecord.getId());
					attachment.setDprtcode(maintenanceRecord.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,maintenanceRecord.getId());
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,maintenanceRecord.getId());
				} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

				}
			}
		}
		commonRecService.write(maintenanceRecord.getId(), TableEnum.B_J_001, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,maintenanceRecord.getId());
	}

}
