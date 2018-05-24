package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetToPlanMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.flightdata.service.FlightRecordSheetToDisassemblyService;
import com.eray.thjw.flightdata.service.FlightRecordSheetToPlanService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.OperateEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 飞行记录关联计划表实现
 * @author hanwu
 *
 */
@Service
public class FlightRecordSheetToPlanServiceImpl implements FlightRecordSheetToPlanService {

	@Resource
	private FlightRecordSheetToPlanMapper flightRecordSheetToPlanMapper;
	
	@Resource
	private WorkorderMapper workorderMapper;
	
	@Resource
	FlightRecordSheetToDisassemblyService flightRecordSheetToDisassemblyService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;


	/**
	 * 保存飞行记录关联计划表
	 */
	@Override
	public void save(FlightRecordSheetToPlan data) {
		// 当前操作   1：新增   2.修改 
		UpdateTypeEnum operation = UpdateTypeEnum.UPDATE;
		// 生成id
		if(StringUtils.isBlank(data.getId())){
			data.setId(UUID.randomUUID().toString());
			operation = UpdateTypeEnum.SAVE;
		}
		// 设置默认值
		setDefaultValue(data);
		// 保存记录
		flightRecordSheetToPlanMapper.save(data);
		// 插入日志
		commonRecService.write(data.getId(), TableEnum.B_S_00602, ThreadVarUtil.getUser().getId(), data.getCzls(),
				data.getLogOperationEnum(), operation, data.getFxjldid());
		// 保存完成任务附件
		saveAttachments(data);
		// 保存拆解记录
		saveDismountRecord(data);
	}


	@Override
	public void deleteNotExist(FlightRecordSheet flightRecordSheet) {
		FlightRecordSheetToPlan param = new FlightRecordSheetToPlan();
		setDefaultValue(param);
		param.setFxjldid(flightRecordSheet.getId());
		param.setZt(EffectiveEnum.NO.getId());
		List<String> ids = new ArrayList<String>();
		for (FlightRecordSheetToPlan temp : flightRecordSheet.getFinishedWork()) {
			ids.add(temp.getId());
		}
		param.setIds(ids);
		// 删除对应附件
		deleteAttachments(param, flightRecordSheet);
		flightRecordSheetToPlanMapper.deleteNotExist(param);
		// 插入日志
		StringBuilder sql = new StringBuilder("b.fxjldid = '"+flightRecordSheet.getId()+"' ");
		if(!ids.isEmpty()){
			sql.append("and b.id not in (");
			for (String id : ids) {
				sql.append("'").append(id).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_S_00602, ThreadVarUtil.getUser().getId(), flightRecordSheet.getCzls(),
				flightRecordSheet.getLogOperationEnum(), UpdateTypeEnum.DELETE, flightRecordSheet.getId());
		
	}
	
	/**
	 * 保存拆解记录
	 * @param data
	 */
	private void saveDismountRecord(FlightRecordSheetToPlan data){
		for (FlightRecordSheetToDisassembly dismountRecord : data.getDismountRecord()) {
			dismountRecord.setFxjldid(data.getFxjldid());
			dismountRecord.setFxjlgljlid(data.getId());
			dismountRecord.setCzls(data.getCzls());
			dismountRecord.setLogOperationEnum(data.getLogOperationEnum());
			dismountRecord.setDprtcode(data.getDprtcode());
			flightRecordSheetToDisassemblyService.save(dismountRecord);
		}
		flightRecordSheetToDisassemblyService.deleteNotExist(data);
	}
	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(FlightRecordSheetToPlan data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}


	/**
	 * 根据飞行记录单查找
	 */
	@Override
	public List<FlightRecordSheetToPlan> findByFxjldid(String fxjldid) {
		//TODO 任务单号和任务信息使用b_s_00602中的字段，b_s_00602需要增加任务单号字段 
		//FIXME 20170522 徐勇
		return flightRecordSheetToPlanMapper.findByFxjldid(fxjldid);
	}
	
	/**
	 * 保存完成任务附件
	 * @param data
	 */
	private void saveAttachments(FlightRecordSheetToPlan data){
		List<Attachment> attachments = data.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(data.getRwdid());
					attachment.setDprtcode(data.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
				} 
			}
		}
		List<String> ids = new ArrayList<String>();
		ids.add(data.getRwdid());
		commonRecService.write("mainid", ids, TableEnum.D_011, ThreadVarUtil.getUser().getId(), data.getCzls(),
				data.getLogOperationEnum(), UpdateTypeEnum.SAVE, data.getFxjldid());
	}
	
	/**
	 * 删除任务下所有附件
	 * @param rwdid
	 */
	private void deleteAttachments(FlightRecordSheetToPlan param, FlightRecordSheet data){
		// 获取删除的任务
		List<FlightRecordSheetToPlan> list = flightRecordSheetToPlanMapper.queryDelete(param);
		List<String> mainids = new ArrayList<String>();
		if(!list.isEmpty()){
			for (FlightRecordSheetToPlan plan : list) {
				Attachment attachment = new Attachment();
				attachment.setMainid(plan.getRwdid());
				attachmentMapper.delFiles(attachment);
				mainids.add(attachment.getMainid());
			}
			commonRecService.write("mainid", mainids , TableEnum.D_011, ThreadVarUtil.getUser().getId(), data.getCzls(),
					data.getLogOperationEnum(), UpdateTypeEnum.DELETE, data.getId());
		}
	}


	@Override
	public List<FlightRecordSheetToPlan> getZlhAndId(FlightRecordSheetToPlan param) {
		
		return flightRecordSheetToPlanMapper.getZlhAndId(param);
	}
	
	@Override
	public List<Workorder> getWorkorderList(Workorder data) {
		return workorderMapper.queryListByTitle(data);
	}
}
