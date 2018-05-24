package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.PlaneFaultMonitoringInfoMapper;
import com.eray.thjw.produce.dao.PlaneFaultMonitoringInfoOrderMapper;
import com.eray.thjw.produce.dao.PlaneFaultMonitoringMapper;
import com.eray.thjw.produce.po.PlaneFaultMonitoring;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfoOrder;
import com.eray.thjw.produce.service.PlaneFaultMonitoringService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PlaneFaultMonitoringServiceImpl implements PlaneFaultMonitoringService {
	
	@Resource
	private PlaneFaultMonitoringMapper planeFaultMonitoringMapper;
	@Resource
	private CommonRecService commonRecService; 
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private PlaneFaultMonitoringInfoMapper planeFaultMonitoringInfoMapper;
	@Resource
	private FlightRecordSheetMapper flightRecordSheetMapper;
	@Resource
	private PlaneFaultMonitoringInfoOrderMapper planeFaultMonitoringInfoOrderMapper;
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Override
	public Map<String, Object> getList(PlaneFaultMonitoring record) {
		String id = record.getId();// 用户刚编辑过的记录 ID
		// 清除查询条件中的ID，保证列表查询结果集的正确性
		record.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(record.getPagination());
		List<PlaneFaultMonitoring> recordList = planeFaultMonitoringMapper.getList(record);
		if (((Page) recordList).getTotal() > 0) {
			if (StringUtils.isNotBlank(id)) {
				if (!PageUtil.hasRecord(recordList, id)) {
					PlaneFaultMonitoring record1 = new PlaneFaultMonitoring();
					record1.setId(id);
					List<PlaneFaultMonitoring> newList = planeFaultMonitoringMapper.getList(record1);
					if (newList != null && newList.size() == 1) {
						recordList.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(recordList, record.getPagination());
		} else {
			List<PlaneFaultMonitoring> newRecordList = new ArrayList<PlaneFaultMonitoring>(1);
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				PlaneFaultMonitoring record2 = new PlaneFaultMonitoring();
				record2.setId(id);
				newRecordList = planeFaultMonitoringMapper.getList(record2);
				if (newRecordList != null && newRecordList.size() == 1) {
					return PageUtil.pack(1, newRecordList, record.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, record.getPagination());
		}
	}
	
	 /**
	  * 
	  * @Description 新增飞机故障监控
	  * @CreateTime 2017年9月15日 上午9:53:06
	  * @CreateBy 林龙
	  * @param planeFaultMonitoring 飞机缺陷
	  * @return id
	  * @throws BusinessException
	  */
	@Override
	public String insertPlaneFaultMonitoring(PlaneFaultMonitoring record) throws BusinessException {
		String id = UUID.randomUUID().toString();
		record.setId(id);
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		record.setDprtcode(user.getJgdm());
		List<String> fjzchList=new ArrayList<String>();
		fjzchList.add(record.getFjzch());
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setGbzt(0);
		record.setWhdwid(user.getBmdm());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), record.getDprtcode(), fjzchList);
		planeFaultMonitoringMapper.insertPlaneFaultMonitoring(record);
		List<Attachment> list = record.getAttachments();
		if (list != null && !list.isEmpty()) {
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : list) {
				attachment.setMainid(record.getId());
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
		commonRecService.write(record.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE,record.getId());
		return id;
	}
	@Override
	public PlaneFaultMonitoring getGbyy(PlaneFaultMonitoring record) {
		
		return planeFaultMonitoringMapper.getGbyy(record);
	}
	@Override
	public void insertGbyy(PlaneFaultMonitoring record) {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		record.setGbrid(user.getId());
		planeFaultMonitoringMapper.insertGbyy(record);
		commonRecService.write(record.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,record.getId());
	}
	
	@Override
	public PlaneFaultMonitoring getPlaneFaultMonitoringById(String id) {
		PlaneFaultMonitoring planeFaultMonitoring=planeFaultMonitoringMapper.getPlaneFaultMonitoringById(id);
//		planeFaultMonitoring.setGzcs(planeFaultMonitoringInfoMapper.getCountByMainidAndZt(planeFaultMonitoring.getId()));
//		planeFaultMonitoring.setZjfsrq(planeFaultMonitoringInfoMapper.getNearTime(planeFaultMonitoring.getId()));
		return planeFaultMonitoring;
	}
	
	@Override
	public String updatePlaneFaultMonitoringById(PlaneFaultMonitoring record) throws BusinessException {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		List<String> fjzchList=new ArrayList<String>();
		fjzchList.add(record.getFjzch());
		record.setWhrid(user.getId());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), record.getDprtcode(), fjzchList);
		planeFaultMonitoringMapper.updatePlaneFaultMonitoringById(record);
		List<Attachment> attachments = record.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(record.getId());
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,record.getId());
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,record.getId());
				} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

				}
			}
		}
		commonRecService.write(record.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,record.getId());
		return record.getId();
	}

/**
 * 
 * @Description 保存故障处理记录
 * @CreateTime 2017年9月14日 下午12:02:21
 * @CreateBy 林龙
 * @param record
 * @param request
 * @return
 * @throws Exception
 */	
public String addGzcl(PlaneFaultMonitoringInfo record) throws BusinessException{
	String uuid = UUID.randomUUID().toString();//uuid-主单id
	String czls = UUID.randomUUID().toString();
	User user = ThreadVarUtil.getUser();	
	record.setId(uuid);
	record.setZt(1);
	planeFaultMonitoringInfoMapper.insertInfo(record);
	List<Attachment> list = record.getAttachments();
	if (list != null && !list.isEmpty()) {
		List<String> columnValueList = new ArrayList<String>();
		for (Attachment attachment : list) {
			attachment.setMainid(record.getId());
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
	if(!record.getGdid().equals("")){
		List<String> orderList=new ArrayList<String>();
		PlaneFaultMonitoringInfoOrder pmio=new PlaneFaultMonitoringInfoOrder();
		pmio.setMainid(record.getId());
		pmio.setDprtcode(record.getDprtcode());
		pmio.setZt(1);
		pmio.setWhdwid(record.getWhdwid());
		pmio.setWhrid(record.getWhrid());
		String gdidArray[]=record.getGdid().split(",");
		for (String gdid : gdidArray) {
			String id=UUID.randomUUID().toString();
			pmio.setId(id);
			orderList.add(id);
			pmio.setGdid(gdid);			
			planeFaultMonitoringInfoOrderMapper.insertInfoOrder(pmio);
		}
		commonRecService.write("id", orderList, TableEnum.B_S_0190101, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE,record.getMainid());
	}
	commonRecService.write(record.getId(), TableEnum.B_S_01901, user.getId(), czls, LogOperationEnum.EDIT,
			UpdateTypeEnum.SAVE,record.getMainid());
	PlaneFaultMonitoring pm=new PlaneFaultMonitoring();
	pm.setId(record.getMainid());
	pm.setWhdwid(user.getBmdm());
	pm.setWhrid(user.getId());
	planeFaultMonitoringMapper.updatePlaneFault(pm);
	commonRecService.write(pm.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.EDIT,
			UpdateTypeEnum.UPDATE,pm.getId());
	return record.getId();
	}

@Override
public void delete(PlaneFaultMonitoringInfo record) throws BusinessException {
	User user = ThreadVarUtil.getUser();	
	String czls = UUID.randomUUID().toString();
	commonRecService.write(record.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.DELETE,
			UpdateTypeEnum.DELETE,record.getId());
	planeFaultMonitoringMapper.deleteByPrimaryKey(record);
}

}
