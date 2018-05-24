package com.eray.thjw.flightdata.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.dao.LegacyTrouble2WorkOrderMapper;
import com.eray.thjw.flightdata.dao.LegacyTroubleMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.LegacyTrouble;
import com.eray.thjw.flightdata.po.LegacyTrouble2WorkOrder;
import com.eray.thjw.flightdata.service.LegacyTroubleService;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.MaintenancePackageService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.BinaryEnum;
import enu.LegacyTroubleStatusEnum;
import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.PlanTaskSubType;
import enu.TableEnum;
import enu.ThresholdEnum;
import enu.UpdateTypeEnum;

@Service
public class LegacyTroubleServiceImpl implements LegacyTroubleService{

	@Resource
	private LegacyTroubleMapper legacyTroubleMapper;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Resource
	private LegacyTrouble2WorkOrderMapper legacyTrouble2WorkOrderMapper;
	
	@Resource
	private MaintenancePackageService maintenancePackageService;
	
	@Resource
	private WorkOrderService workOrderService;
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private CommonRecService commonRecService; 
	
	@Resource
	private MonitorsettingsService monitorsettingsService; 
	
	
	@Override
	public Map<String, Object> queryList(LegacyTrouble legacytrouble) throws BusinessException {
		try {
			List<LegacyTrouble> rows = null ; 
			String id = legacytrouble.getId();//用户刚编辑过的记录 ID
			legacytrouble.setId(null);//清除查询条件中的ID，保证列表查询结果集的正确性
			Monitorsettings threshold = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GZBLD.getName(), legacytrouble.getDprtcode());
			PageHelper.startPage(legacytrouble.getPagination());
			
			rows = legacyTroubleMapper.queryList(legacytrouble);
			appendCurrentRow(legacytrouble, rows, id);
			renderColor(rows, threshold);
			return PageUtil.pack4PageHelper(rows, legacytrouble.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询部件履历失败",e);
		}
	}

	private void appendCurrentRow(LegacyTrouble legacytrouble, List<LegacyTrouble> rows, String id)
			throws BusinessException {
		if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if(!PageUtil.hasRecord(rows, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				//在查询条件中增加ID
				legacytrouble.setId(id);
				int page = legacytrouble.getPagination().getPage();
				legacytrouble.getPagination().setPage(1);
				List<LegacyTrouble> newRecordList = legacyTroubleMapper.queryList(legacytrouble);
				legacytrouble.getPagination().setPage(page);
				if(newRecordList != null && newRecordList.size() == 1){
					//将记录放入结果集第一条
					rows.add(0, newRecordList.get(0));
				}
			}
		}
	}

	private void renderColor(List<LegacyTrouble> rows, Monitorsettings threshold) throws ParseException {
		int surplus = 0;
		for (LegacyTrouble planTask : rows) {
			 
			if (planTask.getDqrq()!=null && planTask.getZt()!=null 
					&& (planTask.getZt().trim().equalsIgnoreCase(LegacyTroubleStatusEnum.SAVED.getId().toString())
							||planTask.getZt().trim().equalsIgnoreCase(LegacyTroubleStatusEnum.COMMITTED.getId().toString())
							)) {
				surplus = DateUtil.getConsumeDay(Calendar.getInstance().getTime(),planTask.getDqrq());
				 if (surplus >= threshold.getYjtsJb1()&&surplus<threshold.getYjtsJb2()) {
					 planTask.setBgcolor("#fefe95");//level1  
				 }
				 else if (surplus <= threshold.getYjtsJb1()) {
					 planTask.setBgcolor("#ff9c9c");//level2 
				 }
				 else {
					 
				 }
			}
		}
	}

	@Override
	public void add(LegacyTrouble record) throws BusinessException {
		try {
			LogOperationEnum active = null;
			active = StringUtils.isNotBlank(record.getZt()) && LogOperationEnum.SUBMIT_WO.getId().equals(Integer.valueOf(record.getZt()))
					?LogOperationEnum.SUBMIT_WO
					:LogOperationEnum.SAVE_WO;
			
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setId(UUID.randomUUID().toString());
		record.setDprtcode(user.getJgdm());
		record.setZt(StringUtils.isNotBlank(record.getZt())?record.getZt():LegacyTroubleStatusEnum.SAVED.getId().toString());
		legacyTroubleMapper.insertSelective(record);
		List<Attachment> list = record.getAttachments();
		if (list!= null && !list.isEmpty()) {
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
			
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(),czls,active, UpdateTypeEnum.SAVE,record.getId());
		}
		commonRecService.write(record.getId(), TableEnum.B_S_013, user.getId(),czls ,active, UpdateTypeEnum.SAVE,record.getId());
		} catch (Exception e) {
			throw new BusinessException("新增故障保留单失败",e);
		}
	}

	@Override
	public void edit(LegacyTrouble record) throws BusinessException {
		try {
			LogOperationEnum active = null;
			active = StringUtils.isNotBlank(record.getZt()) && LogOperationEnum.SUBMIT_WO.getId().equals(Integer.valueOf(record.getZt()))
					?LogOperationEnum.SUBMIT_WO
					:LogOperationEnum.EDIT;
					
			String czls = UUID.randomUUID().toString();
			User user = ThreadVarUtil.getUser();
			record.setWhrid(user.getId());
			record.setWhdwid(user.getBmdm());
			legacyTroubleMapper.updateByPrimaryKeySelective(record);
			
			List<Attachment> attachments= record.getAttachments();
			if (attachments!=null && !attachments.isEmpty()) {
				 
				for (Attachment attachment : attachments) {
					if (attachment.getOperate()==null || attachment.getOperate().equals(OperateEnum.ADD)) {
						attachment.setMainid(record.getId());
						attachment.setDprtcode(user.getJgdm());
						attachment.setId(UUID.randomUUID().toString());
						attachment.setCzrid(user.getId());
						attachment.setCzbmid(user.getBmdm());
						attachmentMapper.addFile(attachment);
						commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(),czls,active, UpdateTypeEnum.SAVE,record.getId());
					}
					else if(attachment.getOperate().equals(OperateEnum.DEL)){
						attachmentMapper.delFile(attachment);
						commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(),czls,active, UpdateTypeEnum.DELETE,record.getId());
					}
					else if(attachment.getOperate().equals(OperateEnum.EDIT)){
						
					}
				}
			}
			commonRecService.write(record.getId(), TableEnum.B_S_013, user.getId(),czls,active, UpdateTypeEnum.UPDATE,record.getId());
		} catch (Exception e) {
			throw new BusinessException("故障保留单再次保留失败", e);
		}
		
	}

	@Override
	public Map<String,Object> doCancel(LegacyTrouble record) throws BusinessException {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			Boolean isLegitimate = Boolean.FALSE;
			LegacyTrouble row = this.legacyTroubleMapper.selectByPrimaryKey(record.getId());
			//保存状态可以作废
			if(row.getZt().equals(LegacyTroubleStatusEnum.SAVED.getId().toString())){
				isLegitimate = Boolean.TRUE;
				result.put("isLegitimate", isLegitimate);
				result.put("message", "保存状态可以作废");
				
				User user = ThreadVarUtil.getUser();
				record.setWhrid(user.getId());
				record.setWhdwid(user.getBmdm());
				record.setZt(LegacyTroubleStatusEnum.CANCEL.getId().toString());
				legacyTroubleMapper.updateByPrimaryKeySelective(record);
				String czls = UUID.randomUUID().toString();
				commonRecService.write(record.getId(), TableEnum.B_S_013, user.getId(),czls  ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,record.getId());
				Attachment attachment = new Attachment();
				attachment.setMainid(record.getId());
				attachmentMapper.delFiles(attachment );
				commonRecService.writeByWhere(" b.mainid = '".concat(record.getId()).concat("' "), TableEnum.D_011, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,record.getId());
			}
			return result;
		} catch (Exception e) {
			throw new BusinessException("故障保留单作废失败");
		}
	}

	@Override
	public void doEnd(LegacyTrouble record)throws BusinessException {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			Boolean isLegitimate = Boolean.FALSE;
			LegacyTrouble row = this.legacyTroubleMapper.selectByPrimaryKey(record.getId());
			//提交状态可以作废
			if(row.getZt().equals(LegacyTroubleStatusEnum.COMMITTED.getId().toString())){
				isLegitimate = Boolean.TRUE;
				result.put("isLegitimate", isLegitimate);
				result.put("message", "提交状态可以指定结束");
				
				String czls = UUID.randomUUID().toString();
				User user = ThreadVarUtil.getUser();
				record.setWhrid(user.getId());
				record.setWhdwid(user.getBmdm());
				record.setZt(LegacyTroubleStatusEnum.CLOSE.getId().toString());
				legacyTroubleMapper.end(record);
				commonRecService.write(record.getId(), TableEnum.B_S_013, user.getId(),czls ,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,record.getId());
			}	
		} catch (Exception e) {
			throw new BusinessException("故障保留单指定结束失败");
		}
	}

	@Override
	public LegacyTrouble load(String id) throws BusinessException {
		try {
			 return legacyTroubleMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new BusinessException("查询故障保留单失败",e);
		}
		
	}

	@Override
	public void generateOrder(LegacyTrouble legacyTrouble) throws BusinessException {
		try {
			User user = ThreadVarUtil.getUser();
			LegacyTrouble legacyTroubleDb = legacyTroubleMapper.selectByPrimaryKey(legacyTrouble.getId());
			
			//根据工单类型选择不同的数据表进行新增操作：非例行工单对应b_g_007 非例行工单表；EO工单对应b_g_010 EO工单表
			WorkOrder record = new WorkOrder();
			record.setXfgdyy("由故障保留单生成，故障保留单号：".concat(legacyTroubleDb.getGzbldh()));
			record.setBz(legacyTroubleDb.getBz());
			record.setZt(0);
			
			BaseWorkOrder baseWorkOrder = new BaseWorkOrder();
			WOActionObj wOActionObj = new WOActionObj();
			wOActionObj.setFjzch(legacyTroubleDb.getFjzch());
			baseWorkOrder.setwOActionObj(wOActionObj );
			record.setBaseWorkOrder(baseWorkOrder);
			if (PlanTaskSubType.isNonRoutine(legacyTrouble.getGdlx())) {
				record.setGddlx(2);
				record.setGdlx(PlanTaskSubType.str2Id(legacyTrouble.getGdlx()));
			}
			/*else if (PlanTaskType.EO_BILL.toString().equals(legacyTrouble.getGdlx())) {
				record.setGddlx(3);
			}*/
			
			//设置基地飞机注册号
			record.setZjh(legacyTroubleDb.getZjh());
			if (StringUtils.isNotBlank(legacyTroubleDb.getFjzch())) {
				PlaneData planeData=new PlaneData();
				planeData.setFjzch(legacyTroubleDb.getFjzch());
				planeData.setDprtcode(user.getJgdm());
				planeData = planeDataService.selectByPrimaryKey(planeData);
				record.setJd(planeData.getJd());
				
			}
			
			String czls = UUID.randomUUID().toString();
			record.setCzls(czls);
			String xggdid = workOrderService.insertSelective(record);
			
			//3、b_s_01301 故障保留单-工单对应关系表字段处理
			LegacyTrouble2WorkOrder legacyTrouble2WorkOrder = new LegacyTrouble2WorkOrder();
			legacyTrouble2WorkOrder.setId(UUID.randomUUID().toString());
			legacyTrouble2WorkOrder.setMainid(legacyTrouble.getId());
			legacyTrouble2WorkOrder.setGdlx(record.getGddlx().toString());
			legacyTrouble2WorkOrder.setGdzlx(record.getGdzlx()!=null?record.getGdzlx().toString():null);
			legacyTrouble2WorkOrder.setZt(BinaryEnum.YES.getId().toString());
			legacyTrouble2WorkOrder.setWhdwid(user.getId());
			legacyTrouble2WorkOrder.setWhrid(user.getBmdm());
			legacyTrouble2WorkOrder.setXggdid(xggdid);
		 
			legacyTrouble2WorkOrderMapper.insertSelective(legacyTrouble2WorkOrder);
			
			commonRecService.write(legacyTroubleDb.getId(), TableEnum.B_S_013, user.getId(),czls  ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,legacyTroubleDb.getId());
			commonRecService.write(legacyTrouble2WorkOrder.getId(), TableEnum.B_S_01301, user.getId(),czls  ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,legacyTroubleDb.getId());
			
		} catch (Exception e) {
			throw new BusinessException("生成工单失败", e);
		}
	}

	@Override
	public List<Attachment> queryListAttachments(Attachment attachment) {
		return attachmentMapper.queryListAttachments(attachment);
	}

	/**
	 * 根据飞行记录单查询故障保留单
	 */
	@Override
	public List<LegacyTrouble> queryListByFlightRecord(FlightRecordSheet sheet) {
		return legacyTroubleMapper.queryListByFlightRecord(sheet);
	}

	@Override
	public Boolean validatorGzbldh(LegacyTrouble legacyTrouble) throws BusinessException {
		try {
			Boolean isLegitimate = Boolean.FALSE;
			int count = 0;
			//新增
			if(StringUtils.isBlank(legacyTrouble.getId())){
				LegacyTrouble param = new LegacyTrouble ();
				param.setGzbldh(legacyTrouble.getGzbldh());
				param.setDprtcode(legacyTrouble.getDprtcode());
				count = legacyTroubleMapper.queryListCount(param);
			}
			else{
				//编辑
				LegacyTrouble bean = new LegacyTrouble();
				bean.getParamsMap().put("idneq", legacyTrouble.getId());
				bean.setDprtcode(legacyTrouble.getDprtcode());
				bean.setGzbldh(legacyTrouble.getGzbldh());
				count = legacyTroubleMapper.queryListCount(bean);
			}
			isLegitimate = count==0?Boolean.TRUE:Boolean.FALSE;
			return isLegitimate;
		} catch (Exception e) {
			throw new BusinessException("检查故障保留单失败",e);
		}
	}

	@Override
	public Map<String, Object> addValidate(LegacyTrouble legacyTrouble) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = validatorGzbldh(legacyTrouble);
		if (isLegitimate) {
			this.add(legacyTrouble);
		}
		else{
			result.put("message", "故障保留单号已经被使用。");
		}
		result.put("isLegitimate", isLegitimate);
		return result;
	}

	@Override
	public Map<String, Object> editValidate(LegacyTrouble legacyTrouble)throws BusinessException {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			//验证故障保留单是否重复(false)
			Boolean isLegitimate = validatorGzbldh(legacyTrouble);
			if (!isLegitimate) {
				result.put("message", "故障保留单号已经被使用。");
			}
			else{
				LegacyTrouble row = this.legacyTroubleMapper.selectByPrimaryKey(legacyTrouble.getId());
				String action = legacyTrouble.getParamsMap().get("action")==null?"":legacyTrouble.getParamsMap().get("action").toString();
//				legacyTrouble.setZt(row.getZt());
				//编辑保存（保存状态可以执行）
				if (LegacyTroubleStatusEnum.SAVED.toString().equals(action)) {
					if (row.getZt().equals(LegacyTroubleStatusEnum.SAVED.getId().toString()) ) {
						isLegitimate = Boolean.TRUE;
					}
					else{
						isLegitimate = Boolean.FALSE;
						result.put("message", "改数据状态已变更，不能进行保存。");
					}
				}//编辑提交(保存，提交状态可以执行)
				else if(LegacyTroubleStatusEnum.COMMITTED.toString().equals(action)) {
					if (row.getZt().equals(LegacyTroubleStatusEnum.SAVED.getId().toString()) 
							|| row.getZt().equals(LegacyTroubleStatusEnum.COMMITTED.getId().toString())) {
						isLegitimate = Boolean.TRUE;
					}
					else{
						isLegitimate = Boolean.FALSE;
						result.put("message", "改数据状态已变更，不能进行提交。");
					}
				}
			}
			
			if (isLegitimate) {
				this.edit(legacyTrouble);
			}
			result.put("isLegitimate", isLegitimate);
			return result;
		} catch (Exception e) {
			throw new BusinessException("修改故障保留单失败");
		}
	}

}
