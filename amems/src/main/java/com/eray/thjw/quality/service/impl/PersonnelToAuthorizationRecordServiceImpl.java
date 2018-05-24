package com.eray.thjw.quality.service.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.PersonnelToAuthorizationRecordMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAuthorizationRecord;
import com.eray.thjw.quality.service.PersonnelToAuthorizationRecordService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.BusinessMapper;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PersonnelToAuthorizationRecordServiceImpl implements PersonnelToAuthorizationRecordService {
	
	@Resource
	private PersonnelToAuthorizationRecordMapper personnelToAuthorizationRecordMapper;
	
	@Resource
	private BusinessMapper businessMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Resource
	private CommonMapper commonMapper;
	
	/** 授权 */
	private final static Integer TYPE_AUTHORIZATION_RECORD = 1;
	
	/** 维修执照 */
	private final static Integer TYPE_MAINTENANCE_LICENSE = 21;
	
	/** 机型执照 */
	private final static Integer TYPE_TYPE_LICENSE = 22;
	
	/**
	 * 保存授权
	 */
	@Override
	public void saveAuthorizationRecord(MaintenancePersonnel personnel) throws BusinessException {
		
	}

	/**
	 * 保存维修执照
	 */
	@Override
	public void saveMaintenanceLicense(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_MAINTENANCE_LICENSE);
		for (PersonnelToAuthorizationRecord record : personnel.getMaintenanceLicenses()) {
			record.setMainid(personnel.getId());
			record.setDprtcode(personnel.getDprtcode());
			record.setZt(1);
			record.setWhbmid(user.getBmdm());
			record.setWhrid(user.getId());
			record.setWhsj(new Date());
			record.setXxlx(TYPE_MAINTENANCE_LICENSE);
			record.setCzls(personnel.getCzls());
			record.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(record.getId())){	// 新增
				record.setId(UUID.randomUUID().toString());
				personnelToAuthorizationRecordMapper.insertSelective(record);
				// 写入日志
				commonRecService.write(record.getId(), TableEnum.B_Z_00101, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToAuthorizationRecordMapper.updateByPrimaryKey(record);	
				// 写入日志
				commonRecService.write(record.getId(), TableEnum.B_Z_00101, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(record);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getMaintenanceLicenses().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToAuthorizationRecord record : personnel.getMaintenanceLicenses()) {
				sql.append("'").append(record.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.xxlx = 21");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00101, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToAuthorizationRecordMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存机型执照
	 */
	@Override
	public void saveTypeLicense(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.getParamsMap().put("lx", TYPE_TYPE_LICENSE);
		for (PersonnelToAuthorizationRecord record : personnel.getTypeLicenses()) {
			record.setMainid(personnel.getId());
			record.setDprtcode(personnel.getDprtcode());
			record.setZt(1);
			record.setWhbmid(user.getBmdm());
			record.setWhrid(user.getId());
			record.setWhsj(new Date());
			record.setXxlx(TYPE_TYPE_LICENSE);
			record.setCzls(personnel.getCzls());
			record.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(record.getId())){	// 新增
				record.setId(UUID.randomUUID().toString());
				personnelToAuthorizationRecordMapper.insertSelective(record);
				// 写入日志
				commonRecService.write(record.getId(), TableEnum.B_Z_00101, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
			}else{	// 修改
				personnelToAuthorizationRecordMapper.updateByPrimaryKey(record);	
				// 写入日志
				commonRecService.write(record.getId(), TableEnum.B_Z_00101, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
			}
			// 保存附件
			saveAttachments(record);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+personnel.getId()+"' ");
		if(!personnel.getTypeLicenses().isEmpty()){
			sql.append("and b.id not in (");
			for (PersonnelToAuthorizationRecord record : personnel.getTypeLicenses()) {
				sql.append("'").append(record.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") ");
			sql.append("and b.xxlx = 22");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_Z_00101, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		personnelToAuthorizationRecordMapper.deleteNotExist(personnel);
	}
	
	/**
	 * 保存附件
	 * @param data
	 */
	private void saveAttachments(PersonnelToAuthorizationRecord data){
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
	 * 查询授权记录
	 */
	@Override
	public List<PersonnelToAuthorizationRecord> queryAuthorizationRecords(
			String id) {
		return null;
	}

	/**
	 * 查询维修执照
	 */
	@Override
	public List<PersonnelToAuthorizationRecord> queryMaintenanceLicenses(String id) {
		List<PersonnelToAuthorizationRecord> list = personnelToAuthorizationRecordMapper.queryMaintenanceLicenses(id);
		for (PersonnelToAuthorizationRecord p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

	/**
	 * 查询机型执照
	 */
	@Override
	public List<PersonnelToAuthorizationRecord> queryTypeLicenses(String id) {
		List<PersonnelToAuthorizationRecord> list = personnelToAuthorizationRecordMapper.queryTypeLicenses(id);
		for (PersonnelToAuthorizationRecord p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}

	@Override
	public Map<String, Object> page(PersonnelToAuthorizationRecord record) throws BusinessException {
		
		//查询档案-授权记录
		PageHelper.startPage(record.getPagination());
		List<PersonnelToAuthorizationRecord> rows = personnelToAuthorizationRecordMapper.queryList(record);
		
		if (rows!=null && !rows.isEmpty()) {
			//档案-授权记录非空，组装岗位列表。
			List<String>ids = new ArrayList<String>();
			Map<String, List<Business>> map = new HashMap<String, List<Business>>();
			for (PersonnelToAuthorizationRecord row : rows) {
				ids.add(row.getMainid());//维修人员ids
			}
			
			//根据维修人档案ids查询岗位列表，携带岗位-人员关系
			List<Business> businesses = businessMapper.queryList4mpids(ids);
			List<BusinessToMaintenancePersonnel> toMps = new ArrayList<BusinessToMaintenancePersonnel>();
			if (businesses!=null && !businesses.isEmpty()) {
				//岗位非空，将岗位和维修人员放到map中。
		
				for (Business business : businesses) {
					if (business.getBusinessToMaintenancePersonnels() != null) {
						toMps = business.getBusinessToMaintenancePersonnels();
						for (BusinessToMaintenancePersonnel mp : toMps) {
						
							
							//维修人员-岗位关系 
							if (StringUtils.isNotBlank(mp.getGwid()) && StringUtils.isNotBlank(mp.getWxrydaid())) {
								//映射中包含维修人员档案
								if (!map.containsKey(mp.getWxrydaid())) {
									map.put(mp.getWxrydaid(), new ArrayList<Business>() );
								}
								//向集合中加入岗位
								map.get(mp.getWxrydaid()).add(business);
								
							}
						}
					}
				
				}
				record.setBusinesses(businesses);
			}
			//遍历档案-授权记录，组装对应的岗位.
			for (PersonnelToAuthorizationRecord row : rows) {
				StringBuffer str=new StringBuffer();
				if(map.containsKey(row.getMainid())) {
					List<Business> list = new ArrayList<Business>();
					for (Business business : map.get(row.getMainid())) {
						str.append(business.getDgmc());
						str.append(",");
						list.add(business);
					}
					row.getParamsMap().put("dgmc", str.substring(0,str.length() - 1));
					row.setBusinesses(list);
				}
			}
		}
		
		return PageUtil.pack4PageHelper(rows, record.getPagination());
	}

	public BusinessMapper getBusinessMapper() {
		return businessMapper;
	}

	public void setBusinessMapper(BusinessMapper businessMapper) {
		this.businessMapper = businessMapper;
	}

}
