package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.dao.MountLoadingListMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.MountLoadingList;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.produce.dao.InstallationListTempMapper;
import com.eray.thjw.produce.dao.PlaneFaultMonitoringInfoMapper;
import com.eray.thjw.produce.dao.PlaneFaultMonitoringInfoOrderMapper;
import com.eray.thjw.produce.dao.PlaneFaultMonitoringMapper;
import com.eray.thjw.produce.dao.WorkOrderIORecordMapper;
import com.eray.thjw.produce.po.InstallationListTemp;
import com.eray.thjw.produce.po.PlaneFaultMonitoring;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfoOrder;
import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.PlaneFaultMonitoringInfoService;
import com.eray.thjw.productionplan.dao.LoadingListMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.util.StringUtil;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PlaneFaultMonitoringInfoServiceImpl implements PlaneFaultMonitoringInfoService {
	
	@Resource
	private PlaneFaultMonitoringInfoMapper planeFaultMonitoringInfoMapper;
	@Resource
	private PlaneFaultMonitoringInfoOrderMapper planeFaultMonitoringInfoOrderMapper;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private PlaneFaultMonitoringMapper planeFaultMonitoringMapper;
	@Resource
	private MountLoadingListMapper mountLoadingListMapper;
	@Resource
	private LoadingListMapper loadingListMapper;
	@Resource
	private WorkOrderIORecordMapper workOrderIORecordMapper;
	@Resource
	private InstallationListTempMapper installationListTempMapper;
	
	@Override
	public List<PlaneFaultMonitoringInfo> getInfoList(String mainid) {
		
		return planeFaultMonitoringInfoMapper.getInfoList(mainid);
	}

	@Override
	public PlaneFaultMonitoringInfo getInfoById(String id) {
		PlaneFaultMonitoringInfo pInfo=planeFaultMonitoringInfoMapper.getInfoById(id);
		Attachment attachment=new Attachment();
		attachment.setMainid(id);
		List<Attachment> attachmentList=attachmentMapper.queryListAttachments(attachment);
		pInfo.setAttachmentList(attachmentList);
		List<PlaneFaultMonitoringInfoOrder> list=planeFaultMonitoringInfoOrderMapper.getorderIdByMainid(id);
		if(list.size()>0){
			StringBuffer sbf=new StringBuffer();
			for(int i=0;i<list.size();i++){
				if(i!=list.size()-1){
					sbf.append(list.get(i).getGdid()).append(",");
				}else{
					sbf.append(list.get(i).getGdid());
				}
			}
			pInfo.setGdid(sbf.toString());
		}
		return pInfo;
	}

	@Override
	public String updateById(PlaneFaultMonitoringInfo info) {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();

		planeFaultMonitoringInfoMapper.updateById(info);
		List<Attachment> attachments = info.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(info.getId());
					attachment.setDprtcode(info.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,info.getMainid());
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,info.getMainid());
				} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

				}
			}
		}
		if(!info.getGdid().equals("")){
			planeFaultMonitoringInfoOrderMapper.deleteByMainid(info.getId());
			List<String> orderList=new ArrayList<String>();
			PlaneFaultMonitoringInfoOrder pmio=new PlaneFaultMonitoringInfoOrder();
			pmio.setMainid(info.getId());
			pmio.setDprtcode(info.getDprtcode());
			pmio.setZt(1);
			pmio.setWhdwid(info.getWhdwid());
			pmio.setWhrid(info.getWhrid());
			String gdidArray[]=info.getGdid().split(",");
			for (String gdid : gdidArray) {
				String id=UUID.randomUUID().toString();
				pmio.setId(id);
				orderList.add(id);
				pmio.setGdid(gdid);			
				planeFaultMonitoringInfoOrderMapper.insertInfoOrder(pmio);
			}
			commonRecService.write("id", orderList, TableEnum.B_S_0190101, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.UPDATE,info.getMainid());
		}
		commonRecService.write(info.getId(), TableEnum.B_S_01901, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,info.getMainid());
		PlaneFaultMonitoring pm=new PlaneFaultMonitoring();
		pm.setId(info.getMainid());
		pm.setWhdwid(user.getBmdm());
		pm.setWhrid(user.getId());
		planeFaultMonitoringMapper.updatePlaneFault(pm);
		commonRecService.write(pm.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,pm.getId());
	return info.getId();
	}

	@Override
	public void deleteById(PlaneFaultMonitoringInfo info) {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		attachmentMapper.updateByMainid(info.getId());
		commonRecService.writeByWhere(" b.mainid = '".concat(info.getId()).concat("' ").concat("and b.yxzt=1"), TableEnum.D_011, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,info.getMainid());
		planeFaultMonitoringInfoMapper.deleteById(info);
		commonRecService.write(info.getId(), TableEnum.B_S_01901, user.getId(), czls, LogOperationEnum.ZUOFEI,
				UpdateTypeEnum.DELETE,info.getMainid());
		planeFaultMonitoringInfoOrderMapper.deleteByMainid(info.getId());
		commonRecService.writeByWhere(" b.mainid = '".concat(info.getId()).concat("' ").concat("and b.zt=0"), TableEnum.B_S_0190101, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,info.getMainid());
		PlaneFaultMonitoring pm=new PlaneFaultMonitoring();
		pm.setId(info.getMainid());
		pm.setWhdwid(user.getBmdm());
		pm.setWhrid(user.getId());
		planeFaultMonitoringMapper.updatePlaneFault(pm);
		commonRecService.write(pm.getId(), TableEnum.B_S_019, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,pm.getId());
	}

	@Override
	public Map<String, List<String>> getZsClxx(String fxjlgljlid) {
		Map<String,List<String>> map=new HashMap<String,List<String>>();
		List<LoadingList> cxjList=loadingListMapper.getCxj(fxjlgljlid);		
		if(cxjList.size()>0){
			List<String> cxj=new ArrayList<String>();
			for (LoadingList loadingList : cxjList) {
				cxj.add(loadingList.getJh()+"("+loadingList.getXlh()+")");
			}
			map.put("cx",cxj);
		}
		List<MountLoadingList> zsjList=mountLoadingListMapper.getZsjxx(fxjlgljlid);
		if(zsjList.size()>0){
			List<String> zsj=new ArrayList<String>();
			for (MountLoadingList mountLoadingList : zsjList) {
				zsj.add(mountLoadingList.getJh()+"("+mountLoadingList.getXlh()+")");
			}
			map.put("zs",zsj);
		}
		return map;
	}

	
	@Override
	public Map<String, List<String>> getZsCxInfo(Workorder data) {
		//将工单的值传给装机清单
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		InstallationListTemp record = new InstallationListTemp();
		record.getParamsMap().put("id", data.getId());
		//查询装上记录与拆下
		List<InstallationListTemp> cxList = installationListTempMapper.queryCxList(record);
		List<InstallationListTemp> zsList = installationListTempMapper.queryZsList(record);
		//获取装上拆下的编号序列号
		if(cxList!=null && cxList.size()>0) {
				List<String> list = new ArrayList<String>();
				for (InstallationListTemp obj : cxList) {
					list.add(obj.getBjh()+"("+obj.getXlh()+")");
				}
				returnMap.put("cx",list);
		}
		if(zsList!=null && zsList.size()>0) {
			List<String> list = new ArrayList<String>();
			for (InstallationListTemp obj : cxList) {
				list.add(obj.getBjh()+"("+obj.getXlh()+")");
			}
			returnMap.put("zs",list);
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> queryWorkOrderList(WorkOrder data) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	
}
