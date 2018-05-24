package com.eray.thjw.quality.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.PersonnelToTechnicalGradeRecordMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToTechnicalGradeRecord;
import com.eray.thjw.quality.service.PersonnelToTechnicalGradeRecordService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PersonnelToTechnicalGradeRecordServiceImpl implements PersonnelToTechnicalGradeRecordService {
	
	@Resource
	private PersonnelToTechnicalGradeRecordMapper personnelToTechnicalGradeRecordMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;

	/**
	 * 保存
	 */
	@Override
	public void save(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		for (PersonnelToTechnicalGradeRecord grade : personnel.getGrades()) {
			grade.setMainid(personnel.getId());
			grade.setDprtcode(personnel.getDprtcode());
			grade.setZt(1);
			grade.setWhbmid(user.getBmdm());
			grade.setWhrid(user.getId());
			grade.setWhsj(new Date());
			grade.setCzls(personnel.getCzls());
			grade.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(grade.getId())){	// 新增
				grade.setId(UUID.randomUUID().toString());
				personnelToTechnicalGradeRecordMapper.insertSelective(grade);
				// 写入日志
				commonRecService.write(grade.getId(), TableEnum.B_Z_00111, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToTechnicalGradeRecordMapper.updateByPrimaryKeySelective(grade);	
				// 写入日志
				commonRecService.write(grade.getId(), TableEnum.B_Z_00111, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(grade);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getGrades().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToTechnicalGradeRecord grade : personnel.getGrades()) {
				sql.append("'").append(grade.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00111, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToTechnicalGradeRecordMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存附件
	 * @param data
	 */
	private void saveAttachments(PersonnelToTechnicalGradeRecord data){
		List<String> ids = new ArrayList<String>();
		List<Attachment> attachments = data.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachments) {
				if(StringUtils.isBlank(attachment.getId())){
					// 后缀名截取10位
					if(StringUtils.isNotBlank(attachment.getHzm()) && attachment.getHzm().length() > 10){
						attachment.setHzm(attachment.getHzm().substring(0, 10));
					}
					attachment.setMainid(data.getId());
					attachment.setDprtcode(data.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					ids.add(attachment.getId());
				}
			}
		}
		if(!ids.isEmpty()){
			commonRecService.write("id", ids, TableEnum.D_011, ThreadVarUtil.getUser().getId(), data.getCzls(),
					data.getLogOperationEnum(), UpdateTypeEnum.SAVE, data.getMainid());
		}
	}

	/**
	 * 查询
	 */
	@Override
	public List<PersonnelToTechnicalGradeRecord> query(String id) {
		List<PersonnelToTechnicalGradeRecord> list = personnelToTechnicalGradeRecordMapper.query(id);
		for (PersonnelToTechnicalGradeRecord p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

}
