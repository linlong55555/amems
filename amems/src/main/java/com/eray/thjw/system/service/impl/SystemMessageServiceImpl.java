package com.eray.thjw.system.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.system.dao.SystemMessageMapper;
import com.eray.thjw.system.po.Message;
import com.eray.thjw.system.service.SystemMessageService;

import enu.OperateEnum;


@Service
public class SystemMessageServiceImpl implements SystemMessageService {

	@Resource
	private SystemMessageMapper systemMessageMapper;
	@Resource
	private AttachmentMapper attachmentMapper;

	@Override
	public int deleteByPrimaryKey(String id) {

		return systemMessageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Message record) {
		
		return systemMessageMapper.insert(record);
	}

	@Override
	public int insertSelective(Message record) {

		return systemMessageMapper.insertSelective(record);
	}

	@Override
	public Message selectByPrimaryKey(String id) {
		Message message=systemMessageMapper.selectByPrimaryKey(id);
		Attachment attachment=new Attachment();
		attachment.setMainid(id);
		List<Attachment> attachmentList=attachmentMapper.queryListAttachments(attachment);
		if(attachmentList.size()>0){
			message.setAttachments(attachmentList);
		}
		return message;
	}

	@Override
	public int updateByPrimaryKeySelective(Message record) {

		return systemMessageMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Message record) {
		
		return systemMessageMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Message> selectEffective(String dprtcode) {

		return systemMessageMapper.selectEffective(dprtcode);
	}

	@Override
	public List<Message> selectMessageList(Message record) {
		return systemMessageMapper.selectMessageList(record);
	}

	@Override
	public void insertMessage(Message record) {
		List<Attachment> list = record.getAttachments();
		if (list != null && !list.isEmpty()) {
			for (Attachment attachment : list) {
				attachment.setMainid(record.getId());
				attachment.setDprtcode(record.getDprtcode());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(record.getWhrid());
				attachment.setCzbmid(record.getWhbmid());
				attachmentMapper.addFile(attachment);
			}
		}
		Date d=new Date();
		if(record.getZt()==1){
			record.setFbsj(d);
			if(record.getYxqKs()==null){
			d.setHours(8);
			d.setMinutes(0);
			d.setSeconds(0);
			record.setYxqKs(d);
			if(record.getYxqJs()!=null){
				DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				String first=sdf.format(d);
				String end=sdf.format(record.getYxqJs());
				if(java.sql.Date.valueOf(first).after(java.sql.Date.valueOf(end))){
					throw new RuntimeException("开始日期不能大于结束日期");
				}
			}
			}
		}
		systemMessageMapper.insertMessage(record);
	}

	@Override
	public void updateMessageByPrimaryKey(Message record) {
		List<Attachment> attachments = record.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(record.getId());
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(record.getWhrid());
					attachment.setCzbmid(record.getWhbmid());
					attachmentMapper.addFile(attachment);
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
				} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

				}
			}
		}
		Date d=new Date();
		if(record.getZt()==1){
			record.setFbsj(d);
			if(record.getYxqKs()==null){
				Date d1=new Date();
				d1.setHours(8);
				d1.setMinutes(0);
				d1.setSeconds(0);
				record.setYxqKs(d1);
				if(record.getYxqJs()!=null){
					DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
					String first=sdf.format(d);
					String end=sdf.format(record.getYxqJs());
					if(java.sql.Date.valueOf(first).after(java.sql.Date.valueOf(end))){
						throw new RuntimeException("开始日期不能大于结束日期");
					}
				}
			}
		}
		systemMessageMapper.updateMessageByPrimaryKey(record);
	}

	@Override
	public void updateZtByPrimaryKey(Message record) {
		Date d=new Date();
		record.setFbsj(d);
		Message message=systemMessageMapper.selectByPrimaryKey(record.getId());
		if(message.getYxqKs()==null){
			d.setHours(8);
			d.setMinutes(0);
			d.setSeconds(0);
			record.setYxqKs(d);
			if(message.getYxqJs()!=null){
				DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				String first=sdf.format(d);
				String end=sdf.format(message.getYxqJs());
				if(java.sql.Date.valueOf(first).after(java.sql.Date.valueOf(end))){
					throw new RuntimeException("开始日期不能大于结束日期");
				}
			}
		}
		systemMessageMapper.updateZtByPrimaryKey(record);
	}

	@Override
	public void updateToClose(Message record) {
		systemMessageMapper.updateToClose(record);
	}

}
