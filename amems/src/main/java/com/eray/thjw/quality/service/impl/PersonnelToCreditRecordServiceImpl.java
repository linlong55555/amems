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
import com.eray.thjw.quality.dao.PersonnelToCreditRecordMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToCreditRecord;
import com.eray.thjw.quality.service.PersonnelToCreditRecordService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PersonnelToCreditRecordServiceImpl implements PersonnelToCreditRecordService {
	
	@Resource
	private PersonnelToCreditRecordMapper personnelToCreditRecordMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	/** 负有责任的不安全事件 */
	private final static Integer TYPE_UNSAFE_INCIDENT = 1;
	
	/** 不诚信行为 */
	private final static Integer TYPE_DISHONEST_BEHAVIOR = 2;
	
	/** 事故征候情况 */
	private final static Integer TYPE_INCIDENT_SITUATION = 3;
	
	
	/**
	 * 保存负有责任的不安全事件
	 */
	@Override
	public void saveUnsafeIncident(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_UNSAFE_INCIDENT);
		for (PersonnelToCreditRecord unsafeIncident : personnel.getUnsafeIncidents()) {
			unsafeIncident.setMainid(personnel.getId());
			unsafeIncident.setDprtcode(personnel.getDprtcode());
			unsafeIncident.setZt(1);
			unsafeIncident.setWhbmid(user.getBmdm());
			unsafeIncident.setWhrid(user.getId());
			unsafeIncident.setWhsj(new Date());
			unsafeIncident.setLx(TYPE_UNSAFE_INCIDENT);
			unsafeIncident.setCzls(personnel.getCzls());
			unsafeIncident.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(unsafeIncident.getId())){	// 新增
				unsafeIncident.setId(UUID.randomUUID().toString());
				personnelToCreditRecordMapper.insertSelective(unsafeIncident);
				// 写入日志
				commonRecService.write(unsafeIncident.getId(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToCreditRecordMapper.updateByPrimaryKeySelective(unsafeIncident);	
				// 写入日志
				commonRecService.write(unsafeIncident.getId(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(unsafeIncident);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getUnsafeIncidents().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToCreditRecord unsafeIncident : personnel.getUnsafeIncidents()) {
				sql.append("'").append(unsafeIncident.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.lx = 1");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToCreditRecordMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存不诚信行为
	 */
	@Override
	public void saveDishonestBehaviors(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_DISHONEST_BEHAVIOR);
		for (PersonnelToCreditRecord dishonestBehavior : personnel.getDishonestBehaviors()) {
			dishonestBehavior.setMainid(personnel.getId());
			dishonestBehavior.setDprtcode(personnel.getDprtcode());
			dishonestBehavior.setZt(1);
			dishonestBehavior.setWhbmid(user.getBmdm());
			dishonestBehavior.setWhrid(user.getId());
			dishonestBehavior.setWhsj(new Date());
			dishonestBehavior.setLx(TYPE_DISHONEST_BEHAVIOR);
			dishonestBehavior.setCzls(personnel.getCzls());
			dishonestBehavior.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(dishonestBehavior.getId())){	// 新增
				dishonestBehavior.setId(UUID.randomUUID().toString());
				personnelToCreditRecordMapper.insertSelective(dishonestBehavior);
				// 写入日志
				commonRecService.write(dishonestBehavior.getId(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToCreditRecordMapper.updateByPrimaryKeySelective(dishonestBehavior);	
				// 写入日志
				commonRecService.write(dishonestBehavior.getId(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(dishonestBehavior);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getDishonestBehaviors().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToCreditRecord dishonestBehavior : personnel.getDishonestBehaviors()) {
				sql.append("'").append(dishonestBehavior.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.lx = 2");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToCreditRecordMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存事故征候情况
	 */
	@Override
	public void saveIncidentSituation(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_INCIDENT_SITUATION);
		for (PersonnelToCreditRecord incidentSituation : personnel.getIncidentSituations()) {
			incidentSituation.setMainid(personnel.getId());
			incidentSituation.setDprtcode(personnel.getDprtcode());
			incidentSituation.setZt(1);
			incidentSituation.setWhbmid(user.getBmdm());
			incidentSituation.setWhrid(user.getId());
			incidentSituation.setWhsj(new Date());
			incidentSituation.setLx(TYPE_INCIDENT_SITUATION);
			incidentSituation.setCzls(personnel.getCzls());
			incidentSituation.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(incidentSituation.getId())){	// 新增
				incidentSituation.setId(UUID.randomUUID().toString());
				personnelToCreditRecordMapper.insertSelective(incidentSituation);
				// 写入日志
				commonRecService.write(incidentSituation.getId(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToCreditRecordMapper.updateByPrimaryKeySelective(incidentSituation);	
				// 写入日志
				commonRecService.write(incidentSituation.getId(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(incidentSituation);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getIncidentSituations().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToCreditRecord incidentSituation : personnel.getIncidentSituations()) {
				sql.append("'").append(incidentSituation.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.lx = 3");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00104, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToCreditRecordMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存附件
	 * @param data
	 */
	private void saveAttachments(PersonnelToCreditRecord data){
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
	 * 查询事故征候情况
	 */
	@Override
	public List<PersonnelToCreditRecord> queryIncidentSituations(String id) {
		List<PersonnelToCreditRecord> list = personnelToCreditRecordMapper.queryIncidentSituations(id);
		for (PersonnelToCreditRecord p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

	/**
	 * 查询负有责任的不安全事件
	 */
	@Override
	public List<PersonnelToCreditRecord> queryUnsafeIncidents(String id) {
		List<PersonnelToCreditRecord> list = personnelToCreditRecordMapper.queryUnsafeIncidents(id);
		for (PersonnelToCreditRecord p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

	/**
	 * 查询不诚信行为
	 */
	@Override
	public List<PersonnelToCreditRecord> queryDishonestBehaviors(String id) {
		List<PersonnelToCreditRecord> list = personnelToCreditRecordMapper.queryDishonestBehaviors(id);
		for (PersonnelToCreditRecord p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

}
