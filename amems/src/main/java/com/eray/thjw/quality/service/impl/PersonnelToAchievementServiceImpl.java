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
import com.eray.thjw.quality.dao.PersonnelToAchievementMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAchievement;
import com.eray.thjw.quality.service.PersonnelToAchievementService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PersonnelToAchievementServiceImpl implements PersonnelToAchievementService {
	
	@Resource
	private PersonnelToAchievementMapper personnelToAchievementMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	/** 成就 */
	private final static Integer TYPE_SCHOLARSHIP = 1;
	
	/** 奖励 */
	private final static Integer TYPE_CITATION = 2;
	
	
	/**
	 * 保存学术成就
	 */
	@Override
	public void saveScholarship(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_SCHOLARSHIP);
		for (PersonnelToAchievement achievement : personnel.getScholarships()) {
			achievement.setMainid(personnel.getId());
			achievement.setDprtcode(personnel.getDprtcode());
			achievement.setZt(1);
			achievement.setWhbmid(user.getBmdm());
			achievement.setWhrid(user.getId());
			achievement.setWhsj(new Date());
			achievement.setLx(TYPE_SCHOLARSHIP);
			achievement.setCzls(personnel.getCzls());
			achievement.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(achievement.getId())){	// 新增
				achievement.setId(UUID.randomUUID().toString());
				personnelToAchievementMapper.insertSelective(achievement);
				// 写入日志
				commonRecService.write(achievement.getId(), TableEnum.B_Z_00102, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToAchievementMapper.updateByPrimaryKeySelective(achievement);	
				// 写入日志
				commonRecService.write(achievement.getId(), TableEnum.B_Z_00102, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(achievement);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getScholarships().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToAchievement achievement : personnel.getScholarships()) {
				sql.append("'").append(achievement.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.lx = 1");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00102, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToAchievementMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存嘉奖记录
	 */
	@Override
	public void saveCitation(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_CITATION);
		for (PersonnelToAchievement achievement : personnel.getCitationRecords()) {
			achievement.setMainid(personnel.getId());
			achievement.setDprtcode(personnel.getDprtcode());
			achievement.setZt(1);
			achievement.setWhbmid(user.getBmdm());
			achievement.setWhrid(user.getId());
			achievement.setWhsj(new Date());
			achievement.setLx(TYPE_CITATION);
			achievement.setCzls(personnel.getCzls());
			achievement.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(achievement.getId())){	// 新增
				achievement.setId(UUID.randomUUID().toString());
				personnelToAchievementMapper.insertSelective(achievement);
				// 写入日志
				commonRecService.write(achievement.getId(), TableEnum.B_Z_00102, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToAchievementMapper.updateByPrimaryKeySelective(achievement);	
				// 写入日志
				commonRecService.write(achievement.getId(), TableEnum.B_Z_00102, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(achievement);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getCitationRecords().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToAchievement achievement : personnel.getCitationRecords()) {
				sql.append("'").append(achievement.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.lx = 2");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00102, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToAchievementMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存附件
	 * @param data
	 */
	private void saveAttachments(PersonnelToAchievement data){
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
	 * 查询学术成就
	 */
	@Override
	public List<PersonnelToAchievement> queryScholarships(String id) {
		List<PersonnelToAchievement> list = personnelToAchievementMapper.queryScholarships(id);
		for (PersonnelToAchievement p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

	/**
	 * 查询嘉奖记录
	 */
	@Override
	public List<PersonnelToAchievement> queryCitations(String id) {
		List<PersonnelToAchievement> list = personnelToAchievementMapper.queryCitations(id);
		for (PersonnelToAchievement p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

}
