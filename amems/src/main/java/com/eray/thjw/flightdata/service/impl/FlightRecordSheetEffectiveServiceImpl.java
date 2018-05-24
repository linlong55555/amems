package com.eray.thjw.flightdata.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetEffectiveMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetToDisassemblyMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetToPlanMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.flightdata.po.MountLoadingList;
import com.eray.thjw.flightdata.po.ReturnInstruction;
import com.eray.thjw.flightdata.service.FlightRecordSheetEffiectiveService;
import com.eray.thjw.flightdata.service.FlightRecordSheetService;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.productionplan.dao.LoadingListEditableMapper;
import com.eray.thjw.productionplan.dao.LoadingListMapper;
import com.eray.thjw.productionplan.dao.PlanTaskMapper;
import com.eray.thjw.productionplan.po.CheckingMonitoring;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlanTask;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.po.ScheduledCheckPlan;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.EngineeringService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enu.FlightRecordSheetStatusEnum;
import enu.LogOperationEnum;
import enu.MonitorItemEnum;
import enu.PlanTaskSubType;
import enu.PlanTaskType;
import enu.PlaneComponentPositionEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;

/**
 * 飞行记录单生效service
 * @author hanwu
 *
 */
@Service
public class FlightRecordSheetEffectiveServiceImpl implements FlightRecordSheetEffiectiveService{

	@Resource
	private FlightRecordSheetEffectiveMapper flightRecordSheetEffectiveMapper;
	
	@Resource
	private PlanTaskMapper planTaskMapper;
	
	@Resource
	private FlightRecordSheetToDisassemblyMapper flightRecordSheetToDisassemblyMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private FlightRecordSheetService flightRecordSheetService;
	
	@Resource
	private FlightRecordSheetMapper flightRecordSheetMapper;
	
	@Resource
	private FlightRecordSheetToPlanMapper flightRecordSheetToPlanMapper;
	
	@Resource
	private EngineeringService engineeringService;
	
	@Resource
	private LoadingListMapper loadingListMapper;
	
	@Resource
	private LoadingListEditableMapper loadingListEditableMapper;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	/** 对象类型-任务 */
	private final static int DXLX_PLAN = 1;
	
	/** 对象类型-拆解件 */
	private final static int DXLX_COMPONENT = 2;
	
	/** 航前 */
	private final static int LEG_BEFORE = 1;
	
	/** 航间一 */
	private final static int LEG_ONE = 3;
	
	/** 航间二 */
	private final static int LEG_TWO = 5;
	
	/** 航间三 */
	private final static int LEG_THREE = 7;
	
	/** 航后 */
	private final static int LEG_AFTER = 99;
	
	/** 航次一 */
	private final static int VOYAGE_ONE = 2;
	
	/** 航次二 */
	private final static int VOYAGE_TWO = 4;
	
	/** 航次三 */
	private final static int VOYAGE_THREE = 6;
	
	/** 航次四*/
	private final static int VOYAGE_FOUR = 8;
	
	/** 初始标记，部件已用 */
	private final static String CSBJ_BJYY_CODE = "bjyy";
	
	/** 初始标记-部件已用  */
	private final static Integer CSBJ_BJYY_VALUE = 1;
	
	/** 初始标记-装机后已用  */
	private final static String CSBJ_ZJHYY_CODE = "zjhyy";
	
	/** 初始标记-装机后已用  */
	private final static Integer CSBJ_ZJHYY_VALUE = 2;
	
	/** 初始标记-调整值  */
	private final static Integer CSBJ_ADJUST_VALUE = 3;
	
	
	Gson gson = new Gson();

	/**
	 * 飞行记录单生效
	 * @throws BusinessException 
	 */
	@Override
	public String doTakeEffect(FlightRecordSheet flightRecordSheet) throws BusinessException {
		// 设置日志参数
		setRecValue(flightRecordSheet, UpdateTypeEnum.UPDATE);
		// 参数验证
		this.validate(flightRecordSheet);
		// 对比差异，保存还原指令
		this.compareDifference(flightRecordSheet);
		// 保存草稿
		flightRecordSheetService.save(flightRecordSheet);
		// 根据指令，进行还原
		this.restore(flightRecordSheet.getId(), flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 飞行记录单生效操作
		this.takeEffect(flightRecordSheet);
		// 删除飞行记录单中无效的数据
		this.deleteInvalid(flightRecordSheet.getId(), true);
		// 更新修订提醒标识
		updateNoticeFlag(flightRecordSheet.getId());
		// 验证飞机中一级部件只能存在一个
		validateLevelOneComponent(flightRecordSheet.getId());
		return flightRecordSheet.getId();
	}
	
	/**
	 * 参数验证
	 * @param flightRecordSheet
	 */
	private void validate(FlightRecordSheet flightRecordSheet){
		if(flightRecordSheet.getId() != null){
			FlightRecordSheet dbData = flightRecordSheetService.findById(flightRecordSheet.getId());
			// 状态为：8作废、9关闭、10完成、11修改作废，不允许提交
			if(FlightRecordSheetStatusEnum.INVALID.getId().equals(dbData.getZt()) || 
					FlightRecordSheetStatusEnum.CLOSE.getId().equals(dbData.getZt()) ||
					FlightRecordSheetStatusEnum.FINISH.getId().equals(dbData.getZt()) ||
					FlightRecordSheetStatusEnum.AMENDMENTVOID.getId().equals(dbData.getZt())){
				throw new SystemException("该飞行记录单状态已经变成"+FlightRecordSheetStatusEnum.getName(dbData.getZt())+"，不能完成当前操作。");
			}
			// 飞机的组织机构代码需要和操作人的机构代码一致
			if(!ThreadVarUtil.getUser().getJgdm().equals(dbData.getDprtcode())){
				throw new SystemException("飞机的组织机构代码和当前登录用户的机构代码不一致，不能完成当前操作。");
			}
			
			// 验证是否有飞机权限
			List<String> list = new ArrayList<String>();
			list.add(dbData.getFjzch());
			if(!planeModelDataService.existsAircraft(ThreadVarUtil.getUser().getId(),ThreadVarUtil.getUser().getUserType(), 
					ThreadVarUtil.getUser().getJgdm(), list)){
				throw new SystemException("无该飞机权限！");
			}
			
		}
	}
	
	/**
	 * 验证飞机中一级部件只能存在一个
	 * @param fxjldid
	 */
	private void validateLevelOneComponent(String fxjldid){
		// 获取飞机一级部件的数量
		List<Map<String, Object>> countMap = flightRecordSheetEffectiveMapper.getLevelOneComponentCount(fxjldid);
		
		for (Map<String, Object> map : countMap) {
			Integer wz = Integer.parseInt(map.get("WZ").toString());
			Integer count = Integer.parseInt(map.get("COUNT").toString());
			// 除机身外数量大于1
			if(wz > 0 && count > 1){
				throw new SystemException("该飞机下位置为："+PlaneComponentPositionEnum.getName(wz)+"的一级部件已存在，不能完成当前操作。");
			}
		}
	}
	
	
	
	/**
	 * 对比差异，保存还原指令
	 * @param flightRecordSheet
	 */
	private void compareDifference(FlightRecordSheet flightRecordSheet){
		// 根据状态判断是否需要对比差异
		if(flightRecordSheet.getId() == null){	// id为空——>新增，无需对比差异
			return;
		} else {	
			FlightRecordSheet dbData = flightRecordSheetService.findById(flightRecordSheet.getId());
			if(!FlightRecordSheetStatusEnum.SUBMIT.getId().equals(dbData.getZt()) &&	// 只有状态为：2提交、12修订，才需要对比差异
					 !FlightRecordSheetStatusEnum.REVISE.getId().equals(dbData.getZt())){
				return;
			}
		}
		// 删除该飞行记录单下原有的还原指令
		flightRecordSheetEffectiveMapper.deleteReturnInstruction(flightRecordSheet.getId());
		User user = ThreadVarUtil.getUser();
		
		// 参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 完成任务id
		List<String> finishedWorkIds = new ArrayList<String>();
		// 装上件-装机清单id
		List<String> onIds = new ArrayList<String>();
		// 拆下件-装机清单id
		List<String> offIds = new ArrayList<String>();
		//还原指令
		List<ReturnInstruction> instructions = new ArrayList<ReturnInstruction>();
		// 汇总id
		if(flightRecordSheet.getFinishedWork() != null){
			for (FlightRecordSheetToPlan plan : flightRecordSheet.getFinishedWork()) {
				finishedWorkIds.add(plan.getRwdid());
				List<FlightRecordSheetToDisassembly> dismountRecords = plan.getDismountRecord();
				if(dismountRecords != null){
					for (FlightRecordSheetToDisassembly dismountRecord : dismountRecords) {
						if(StringUtils.isNotBlank(dismountRecord.getCxZjqdid())){
							offIds.add(dismountRecord.getCxZjqdid());
						}
						MountLoadingList on = dismountRecord.getOn();
						if(on != null && StringUtils.isNotBlank(on.getZsZjqdid())){
							onIds.add(on.getZsZjqdid());
						}
					}
				}
			}
		}
		paramMap.put("fxjldid", flightRecordSheet.getId());
		paramMap.put("finishedWorkIds", finishedWorkIds);
		paramMap.put("onIds", onIds);
		paramMap.put("offIds", offIds);
		// 对比差异-完成计划
		for (String dxid : flightRecordSheetEffectiveMapper.compareDifferenceInFinishedPlan(paramMap)) {
			ReturnInstruction ri = new ReturnInstruction();
			ri.setId(UUID.randomUUID().toString());
			ri.setFxjldh(flightRecordSheet.getId());
			ri.setDxlx(DXLX_PLAN);
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("RWID", dxid);
			ri.setDxid(gson.toJson(temp));
			ri.setDprtCode(flightRecordSheet.getDprtcode());
			ri.setWhrid(user.getId());
			ri.setWhsj(new Date());
			instructions.add(ri);
		}
		// 对比差异-拆下件、装上件
		Map<String, Map<String, String>> unionMap = new HashMap<String, Map<String, String>>();
		List<Map<String, String>> offMap = flightRecordSheetEffectiveMapper.compareDifferenceInDisassemblyComponent(paramMap);
		for (Map<String, String> obj : offMap) {
			unionMap.put(obj.get("CJJLID"), obj);
		}
		List<Map<String, String>> onMap = flightRecordSheetEffectiveMapper.compareDifferenceInMountComponent(paramMap);
		for (Map<String, String> obj : onMap) {
			if(unionMap.containsKey(obj.get("CJJLID"))){
				unionMap.get(obj.get("CJJLID")).put("ZSJID", obj.get("ZSJID"));
			} else {
				unionMap.put(obj.get("CJJLID"), obj);
			}
		}
		for (String cjjlid : unionMap.keySet()) {
			Map<String, String> map = unionMap.get(cjjlid);
			ReturnInstruction ri = new ReturnInstruction();
			ri.setId(UUID.randomUUID().toString());
			ri.setFxjldh(flightRecordSheet.getId());
			ri.setDxlx(DXLX_COMPONENT);
			ri.setDxid(gson.toJson(map));
			ri.setDprtCode(flightRecordSheet.getDprtcode());
			ri.setWhrid(user.getId());
			ri.setWhsj(new Date());
			instructions.add(ri);
		}
		// 插入还原指令
		if(!instructions.isEmpty()){
			flightRecordSheetEffectiveMapper.insertReturnInstruction(instructions);
		}
	}
	
	/**
	 * 根据指令，进行还原
	 * @param fxjldid
	 */
	private void restore(String fxjldid, String czls, LogOperationEnum logOperationEnum){
		List<ReturnInstruction> instructions = flightRecordSheetEffectiveMapper.findReturnInstruction(fxjldid);
		for (ReturnInstruction instruction : instructions) {
			if(instruction.getDxlx() == DXLX_PLAN){	// 对象类型-任务
				Map<String, String> map = gson.fromJson(instruction.getDxid(), new TypeToken<Map<String, String>>(){}.getType());
				String rwid = map.get("RWID");
				// 还原任务
				restorePlan(rwid, fxjldid, czls, logOperationEnum);
			} else if(instruction.getDxlx() == DXLX_COMPONENT){	// 对象类型-拆解件
				Map<String, String> map = gson.fromJson(instruction.getDxid(), new TypeToken<Map<String, String>>(){}.getType());
				String cxjid = map.get("CXJID");
				String zsjid = map.get("ZSJID");
				String cjjlid = map.get("CJJLID");
				// 还原拆解件
				resotreComponent(fxjldid, cxjid, zsjid, cjjlid, czls, logOperationEnum );
			}
		}
	}
	
	/**
	 * 还原拆解件
	 * @param fxjldid
	 * @param cxjid
	 * @param zsjid
	 */
	private void resotreComponent(String fxjldid, String cxjid, String zsjid, String cjjlid
			, String czls, LogOperationEnum logOperationEnum){
		User user = ThreadVarUtil.getUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fxjldid", fxjldid);
		paramMap.put("cxjid", cxjid);
		paramMap.put("zsjid", zsjid);
		paramMap.put("whdwid", user.getBmdm());
		paramMap.put("whrid", user.getId());
		paramMap.put("whsj", new Date());
		
		if(!Utils.Str.isEmpty(zsjid)){
			// 验证该部件是否已经被拆下
			if(!flightRecordSheetEffectiveMapper.validateZsj(zsjid)){
				throw new SystemException("装上件已被拆下或作废，无法还原！");
			}
			// 还原时控件和定检件设置
			restoreTimeMonitorAndFixedMonitor(fxjldid, zsjid, czls, logOperationEnum);
		}
		if(!Utils.Str.isEmpty(cxjid)){
			// 验证该部件是否已经被装上
			if(!flightRecordSheetEffectiveMapper.validateCxj(cxjid)){
				throw new SystemException("拆下件已被装上或作废，无法还原！");
			}
		}
		
		// 还原装机清单数据
		resotreLoadingList(fxjldid, cxjid, zsjid, cjjlid, czls, logOperationEnum, paramMap, true);
		// 拆解记录
		FlightRecordSheetToDisassembly disassemble = flightRecordSheetToDisassemblyMapper.selectByPrimaryKey(cjjlid);
		// 飞行记录单
		FlightRecordSheet sheet = flightRecordSheetMapper.selectByPrimaryKey(fxjldid);
		// 完成任务
		FlightRecordSheetToPlan plan = flightRecordSheetToPlanMapper.selectByPrimaryKey(disassemble.getFxjlgljlid());
		// 工单编号
		String gdbh = flightRecordSheetEffectiveMapper.queryGdhByGdid(plan.getXgdjid());
		if(StringUtils.isBlank(gdbh)){
			gdbh = "无";
		}
		
		if(!Utils.Str.isEmpty(zsjid)){	// 装上件存在
			// 还原定检监控计划
			flightRecordSheetEffectiveMapper.restoreZsjMonitorPlan(zsjid);
			// 还原拆解记录
			flightRecordSheetEffectiveMapper.restoreZsjDissDisassemblyRecord(zsjid);
			if(!Utils.Str.isEmpty(disassemble.getZsWckcid())){
				// 还原装上件外场库存
				flightRecordSheetEffectiveMapper.restoreZsjStock(paramMap);
				// 外场库存
				OutFieldStock out = flightRecordSheetEffectiveMapper.queryOutFieldData(disassemble.getZsWckcid());
				if(out != null){
					// 插入日志
					materialRecService.writeOutfieldRec(disassemble.getZsWckcid(), czls, cjjlid, disassemble.getCjjldh(),
							OutfieldRecBizTypeEnum.TYPE5, UpdateTypeEnum.SAVE, "撤销", sheet.getFxjldh(), assembleRwlx(plan),
							plan.getRwdh(), gdbh, out.getKcsl());
				}
			}
			if(!Utils.Str.isEmpty(disassemble.getZsKclvid())){
				// 删除装上件库存履历
				flightRecordSheetEffectiveMapper.deleteStockRecord(disassemble.getZsKclvid());
			}
			// 清空装上件拆解记录-b_s_0060201
			flightRecordSheetEffectiveMapper.clearZsjDissDisassemblyRecord(cjjlid);
		}
		
		if(!Utils.Str.isEmpty(cxjid)){	// 拆下件存在
			// 还原定检监控计划
			flightRecordSheetEffectiveMapper.restoreCxjMonitorPlan(cxjid);
			paramMap.clear();
			paramMap.put("cxjid", cxjid);
			paramMap.put("fxjldid", fxjldid);
			paramMap.put("whdwid", user.getBmdm());
			paramMap.put("whrid", user.getId());
			// 还原监控项目快照
			flightRecordSheetEffectiveMapper.restoreMonitorItemSnapshot(paramMap);
			// 还原拆解记录
			flightRecordSheetEffectiveMapper.restoreCxjDissDisassemblyRecord(paramMap);
			// 拆解记录
			if(!Utils.Str.isEmpty(disassemble.getCxWckcid())){
				// 外场库存
				OutFieldStock out = flightRecordSheetEffectiveMapper.queryOutFieldData(disassemble.getCxWckcid());
				if(out == null){
					throw new SystemException("拆下件已被送修、入库或报废，无法还原！");
				}
				// 插入日志
				materialRecService.writeOutfieldRec(disassemble.getCxWckcid(), czls, cjjlid, disassemble.getCjjldh(),
						OutfieldRecBizTypeEnum.TYPE4, UpdateTypeEnum.SAVE, "撤销", sheet.getFxjldh(), assembleRwlx(plan),
						plan.getRwdh(), gdbh, out.getKcsl());
				// 还原拆下件外场库存
				flightRecordSheetEffectiveMapper.restoreCxjStock(disassemble.getCxWckcid());
			}
			if(!Utils.Str.isEmpty(disassemble.getCxKclvid())){
				// 删除拆下件库存履历
				flightRecordSheetEffectiveMapper.deleteStockRecord(disassemble.getCxKclvid());
			}
			// 清空装上件拆解记录-b_s_0060201
			flightRecordSheetEffectiveMapper.clearCxjDissDisassemblyRecord(cjjlid);
		}
		// 如果拆解件为一级部件，则还原该拆解件后，要重置之后的飞行记录单的件号、序列号
		resetAfterFlightReocrd(cxjid, zsjid, fxjldid);
	}
	
	/**
	 * 还原时控件和定检件设置
	 * @param zsjid
	 * @param czls
	 * @param logOperationEnum
	 */
	private void restoreTimeMonitorAndFixedMonitor(String fxjldid, String zsjid, String czls, 
			LogOperationEnum logOperationEnum){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramMap.put("zjqdid", zsjid);
		paramMap.put("whrid", user.getId());
		paramMap.put("whdwid", user.getBmdm());
		paramMap.put("whsj", new Date());
		
		// 还原时控件设置-编辑区
		flightRecordSheetEffectiveMapper.restoreTimeMonitorEditable(paramMap);
		// 还原定检件监控-编辑区
		flightRecordSheetEffectiveMapper.resotreFixedMonitorEditable(paramMap);
		// 还原定检件监控项目-编辑区
		flightRecordSheetEffectiveMapper.resotreFixedMonitorItemEditable(paramMap);
		// 还原时控件设置-生效区
		flightRecordSheetEffectiveMapper.restoreTimeMonitorEffective(paramMap);
		// 还原定检件监控-生效区
		flightRecordSheetEffectiveMapper.resotreFixedMonitorEffective(paramMap);
		// 还原定检件监控项目-生效区
		flightRecordSheetEffectiveMapper.resotreFixedMonitorItemEffective(paramMap);
		
		// 写入日志
		List<String> zjqdids = new ArrayList<String>();
		zjqdids.add(zsjid);
		commonRecService.write("zjqdid", zjqdids, TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
		commonRecService.write("zjqdid", zjqdids, TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
		commonRecService.write("zjqdid", zjqdids, TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
	}
	
	/**
	 * 如果拆解件为一级部件，则还原该拆解件后，要重置之后的飞行记录单的件号、序列号
	 * @param cxjid
	 * @param zsjid
	 */
	private void resetAfterFlightReocrd(String cxjid, String zsjid, String fxjldid){
		// 获取装机清单对象
		LoadingList cxj = null;
		if(!Utils.Str.isEmpty(cxjid)){
			cxj = flightRecordSheetEffectiveMapper.findLoadingList(cxjid);
		}
		LoadingList zsj = null;
		if(!Utils.Str.isEmpty(zsjid)){
			zsj = flightRecordSheetEffectiveMapper.findLoadingList(zsjid);
		}
		
		// 计算还原前、后的件号、序列号
		String jh_before = "";
		String xlh_before = "";
		String jh_after = "";
		String xlh_after = "";
		Integer wz = 0;
		if(!Utils.Str.isEmpty(cxjid)){
			jh_after = cxj.getJh();
			xlh_after = cxj.getXlh();
			wz = cxj.getWz();
			if(cxj.getCj() != 1){
				return;
			}
		}
		if(!Utils.Str.isEmpty(zsjid)){
			jh_before = zsj.getJh();
			xlh_before = zsj.getXlh();
			wz = zsj.getWz();
			if(zsj.getCj() != 1){
				return;
			}
		}
		
		// 找到该飞行记录单之后的记录单，如果件号、序列号和还原前的匹配，则重置为还原后的件号、序列号
		List<FlightRecord> records = flightRecordSheetEffectiveMapper.getAllAfterFlightRecord(fxjldid);
		for (FlightRecord record : records) {
			if(wz == 1){
				if(jh_before.equals(record.getJhF1() == null ? "" : record.getJhF1()) 
						&& xlh_before.equals(record.getXlhF1() == null ? "" : record.getXlhF1())){
					FlightRecord fr = new FlightRecord();
					fr.setId(record.getFxjldid());
					fr.setJhF1(jh_after);
					fr.setXlhF1(xlh_after);
					flightRecordSheetEffectiveMapper.updateFlightRecordZF(fr);
				}else{
					break;
				}
			}else if(wz == 2){
				if(jh_before.equals(record.getJhF2() == null ? "" : record.getJhF2()) 
						&& xlh_before.equals(record.getXlhF2() == null ? "" : record.getXlhF2())){
					FlightRecord fr = new FlightRecord();
					fr.setId(record.getFxjldid());
					fr.setJhF2(jh_after);
					fr.setXlhF2(xlh_after);
					flightRecordSheetEffectiveMapper.updateFlightRecordYF(fr);
				}else{
					break;
				}
			}else if(wz == 3){
				if(jh_before.equals(record.getJhJc() == null ? "" : record.getJhJc()) 
						&& xlh_before.equals(record.getXlhJc() == null ? "" : record.getXlhJc())){
					FlightRecord fr = new FlightRecord();
					fr.setId(record.getFxjldid());
					fr.setJhJc(jh_after);
					fr.setXlhJc(xlh_after);
					flightRecordSheetEffectiveMapper.updateFlightRecordJC(fr);
				}else{
					break;
				}
			}else if(wz == 4){
				if(jh_before.equals(record.getJhSsd() == null ? "" : record.getJhSsd()) 
						&& xlh_before.equals(record.getXlhSsd() == null ? "" : record.getXlhSsd())){
					FlightRecord fr = new FlightRecord();
					fr.setId(record.getFxjldid());
					fr.setJhSsd(jh_after);
					fr.setXlhSsd(xlh_after);
					flightRecordSheetEffectiveMapper.updateFlightRecordSSD(fr);
				}else{
					break;
				}
			}else if(wz == 5){
				if(jh_before.equals(record.getJhWdg() == null ? "" : record.getJhWdg()) 
						&& xlh_before.equals(record.getXlhWdg() == null ? "" : record.getXlhWdg())){
					FlightRecord fr = new FlightRecord();
					fr.setId(record.getFxjldid());
					fr.setJhWdg(jh_after);
					fr.setXlhWdg(xlh_after);
					flightRecordSheetEffectiveMapper.updateFlightRecordWDG(fr);
				}else{
					break;
				}
			}
		}
		
	}
	
	/**
	 * 还原装机清单数据
	 * @param fxjldid
	 * @param cxjid
	 * @param zsjid
	 * @param cjjlid
	 * @param czls
	 * @param logOperationEnum
	 */
	private void resotreLoadingList(String fxjldid, String cxjid, String zsjid, String cjjlid
			, String czls, LogOperationEnum logOperationEnum, Map<String, Object> paramMap, boolean writeRec){
		if(!Utils.Str.isEmpty(zsjid)){	// 装上件存在
			if(!Utils.Str.isEmpty(cxjid)){	// 拆下件存在
				
				
				if(writeRec){
					// 插入日志
					List<String> list1 = new ArrayList<String>();
					list1.add(zsjid);
					list1.add(cxjid);
					commonRecService.write("id", list1, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);
					
					List<String> list2 = new ArrayList<String>();
					list2.add(zsjid);
					commonRecService.write("fjdid", list2, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);
				}
				
				// 还原编辑区-拆下件-装上件、拆下件都存在
				flightRecordSheetEffectiveMapper.restoreLoadingList2(paramMap);
				// 还原生效区-拆下件-装上件、拆下件都存在
				flightRecordSheetEffectiveMapper.restoreLoadingList5(paramMap);
				
				// 还原编辑区-装上件-装上件、拆下件都存在
				flightRecordSheetEffectiveMapper.restoreLoadingList1(paramMap);
				
				// 还原编辑区-装上件子节点-装上件、拆下件都存在
				flightRecordSheetEffectiveMapper.restoreLoadingList3(paramMap);
				// 还原生效区-装上件-装上件、拆下件都存在
				flightRecordSheetEffectiveMapper.restoreLoadingList4(paramMap);
				// 还原生效区-装上件子节点-装上件、拆下件都存在
				flightRecordSheetEffectiveMapper.restoreLoadingList6(paramMap);
				
				// 删除装机清单拆下历史
				LoadingList cxj = loadingListEditableMapper.selectByPrimaryKey(cxjid);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("fxjldid", fxjldid);
				param.put("jh", cxj.getJh());
				param.put("xlh", cxj.getXlh());
				loadingListEditableMapper.deleteLoadingListHistory(param);
			} else{
				if(writeRec){
					// 插入日志
					commonRecService.write(zsjid, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
							logOperationEnum, UpdateTypeEnum.UPDATE, fxjldid);
					
					List<String> list2 = new ArrayList<String>();
					list2.add(zsjid);
					commonRecService.write("fjdid", list2, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
							logOperationEnum, UpdateTypeEnum.UPDATE, null);
				}
				
				// 还原编辑区-装上件-装上件存在、拆下件不存在
				flightRecordSheetEffectiveMapper.restoreLoadingList7(paramMap);
				// 还原编辑区-装上件子节点-装上件存在、拆下件不存在
				flightRecordSheetEffectiveMapper.restoreLoadingList8(paramMap);
				// 还原生效区-装上件-装上件存在、拆下件不存在
				flightRecordSheetEffectiveMapper.restoreLoadingList9(paramMap);
				// 还原生效区-装上件子节点-装上件存在、拆下件不存在
				flightRecordSheetEffectiveMapper.restoreLoadingList10(paramMap);
			}
		} else if(!Utils.Str.isEmpty(cxjid)){
			if(writeRec){
				// 插入日志
				commonRecService.write(cxjid, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
						logOperationEnum, UpdateTypeEnum.UPDATE, fxjldid);
			}
			
			// 还原编辑区-拆下件-装上件不存在、拆下件存在
			flightRecordSheetEffectiveMapper.restoreLoadingList11(paramMap);
			// 还原生效区-拆下件-装上件不存在、拆下件存在
			flightRecordSheetEffectiveMapper.restoreLoadingList12(paramMap);
			// 删除装机清单拆下历史
			LoadingList cxj = loadingListEditableMapper.selectByPrimaryKey(cxjid);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fxjldid", fxjldid);
			param.put("jh", cxj.getJh());
			param.put("xlh", cxj.getXlh());
			loadingListEditableMapper.deleteLoadingListHistory(param);
		}
	}
	
	/**
	 * 还原任务
	 * @param rwid
	 */
	private void restorePlan(String rwid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		// 获取计划任务对象
		PlanTask planTask = planTaskMapper.selectByPrimaryKey(rwid);
		// 还原b_s_009 计划任务
		restorePlanTask(rwid, fxjldid, czls, logOperationEnum);
		if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.CHECK_BILL.getId()){
			// 还原定检执行任务
			restoreFixedCheckTask(planTask.getXggdid(), fxjldid, czls, logOperationEnum);
		} else if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.NON_ROUTINE.getId()){
			// 还原非例行工单任务
			restoreNonRoutineTask(planTask.getXggdid(), fxjldid, czls, logOperationEnum);
		} else if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.EO_BILL.getId()){
			// 还原EO工单任务
			restoreEOTask(planTask.getXggdid(), fxjldid, czls, logOperationEnum);
		}
		// 还原生效区-飞机装机清单
		restorePlanInLoadingList(planTask, czls, logOperationEnum);
		// 还原监控数据
		resotreMonitorData(planTask, fxjldid, czls, logOperationEnum);
		// 还原附件
		restoreAttachment(planTask, fxjldid, czls, logOperationEnum);
	}
	
	/**
	 * 还原附件
	 * @param planTask
	 * @param czls
	 * @param logOperationEnum
	 */
	private void restoreAttachment(PlanTask planTask, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		List<String> mainids = new ArrayList<String>();
		
		Attachment attachment = new Attachment();
		attachment.setMainid(planTask.getId());
		attachmentMapper.delFiles(attachment);
		mainids.add(attachment.getMainid());
		
		commonRecService.write("mainid", mainids , TableEnum.D_011, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
	}
	
	/**
	 * 还原计划任务
	 * @param rwid
	 * @return
	 */
	private void restorePlanTask(String rwid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		User user = ThreadVarUtil.getUser();
		PlanTask param = new PlanTask();
		param.setId(rwid);
		param.setWhrid(user.getId());
		param.setWhdwid(user.getBmdm());
		param.setWhsj(new Date());
		flightRecordSheetEffectiveMapper.restorePlanTask(param);
		// 插入日志
		commonRecService.write(rwid, TableEnum.B_S_009, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.REVOKE, UpdateTypeEnum.UPDATE, rwid);
	}
	
	/**
	 * 完成计划任务
	 * @param plan
	 */
	private void finishPlanTask(FlightRecordSheetToPlan plan, String fxjldid, 
			String czls, LogOperationEnum logOperationEnum, PlanTask planTask){
		
		FlightRecordSheet sheet = flightRecordSheetMapper.selectByPrimaryKey(fxjldid);
		if(sheet.getZt() == 2 && planTask.getZt() != 2){
			throw new SystemException("计划任务:"+planTask.getRwdh()+"已被完成或指定结束，操作无法完成。");
		}
		
		User user = ThreadVarUtil.getUser();
		PlanTask param = new PlanTask();
		param.setId(plan.getRwdid());
		param.setSjgsRs(plan.getSjgsRs());
		param.setSjgsXs(plan.getSjgsXss());
		param.setZdjsrid(plan.getZrrid());
		param.setZdjsrq(sheet.getFxrq());
		param.setWhrid(user.getId());
		param.setWhdwid(user.getBmdm());
		param.setWhsj(new Date());
		param.setFxjldid(fxjldid);
		param.setGzxx(plan.getGzxx());
		param.setClcs(plan.getClcs());
		flightRecordSheetEffectiveMapper.finishPlanTask(param);
		// 插入日志
		commonRecService.write(plan.getRwdid(), TableEnum.B_S_009, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.WANCHEN, UpdateTypeEnum.UPDATE, plan.getRwdid());
	}
	
	/**
	 * 还原定检任务
	 * @param rwid
	 */
	private void restoreFixedCheckTask(String xggdid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		// 还原定检任务主表
		flightRecordSheetEffectiveMapper.restoreFixedCheckMain(xggdid);
		// 插入日志
		commonRecService.write(xggdid, TableEnum.B_G_014, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.UPDATE, xggdid);
		// 还原定检任务工单
		flightRecordSheetEffectiveMapper.restoreFixedCheckWorkOrder(xggdid);
		// 插入日志
		List<String> list = new ArrayList<String>();
		list.add(xggdid);
		commonRecService.write("djrwdid", list, TableEnum.B_G_016, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, fxjldid);
	}
	
	/**
	 * 完成定检任务
	 * @param rwid
	 */
	private void finishFixedCheckTask(String xggdid, String zrrid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		
		// 验证完成工作对应的工单监控项是否与生效区的监控项一致
		validateFixedMonitorSetting(xggdid);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("xggdid", xggdid);
		paramMap.put("zdjsrid", zrrid);
		paramMap.put("zdjsrq", new Date());
		// 完成定检任务主表
		flightRecordSheetEffectiveMapper.finishFixedCheckMain(paramMap);
		// 插入日志
		commonRecService.write(xggdid, TableEnum.B_G_014, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.UPDATE, fxjldid);
		
		// 完成定检任务工单
		flightRecordSheetEffectiveMapper.finishFixedCheckWorkOrder(paramMap);
		// 插入日志
		List<String> ids = new ArrayList<String>();
		ids.add(xggdid);
		commonRecService.write("djrwdid", ids, TableEnum.B_G_016, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.UPDATE, fxjldid);
	}
	
	/**
	 * 还原非例行任务
	 * @param rwid
	 */
	private void restoreNonRoutineTask(String xggdid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		// 还原非例行任务
		flightRecordSheetEffectiveMapper.restoreNonRoutineTask(xggdid);
		// 插入日志
		commonRecService.write(xggdid, TableEnum.B_G_007, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, fxjldid);
	}
	
	/**
	 * 完成非例行任务
	 * @param rwid
	 */
	private void finishNonRoutineTask(String xggdid, String zrrid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		
		// 验证完成工作对应的工单监控项是否与生效区的监控项一致
		validateTimeMonitorSetting(xggdid);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("xggdid", xggdid);
		paramMap.put("zdjsrid", zrrid);
		paramMap.put("zdjsrq", new Date());
		// 还原非例行任务
		flightRecordSheetEffectiveMapper.finishNonRoutineTask(paramMap);
		
		// 插入日志
		commonRecService.write(xggdid, TableEnum.B_G_007, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.UPDATE, fxjldid);
	}
	
	/**
	 * 验证完成工作对应的时控件工单监控项是否与生效区的监控项一致
	 * @param xggdid
	 */
	private void validateTimeMonitorSetting(String xggdid){
		WOActionObj woActionObj = flightRecordSheetEffectiveMapper.findWOTimeMonitorSetting(xggdid);
		// 时控件工单
		if(woActionObj != null && PlanTaskSubType.TIME_CONTROL_PART.getId().equals(woActionObj.getGdlx())){
			List<TimeMonitorSetting> list = flightRecordSheetEffectiveMapper.findLoadingListTimeMonitorSetting(woActionObj.getZjqdid());
			// 工单对应的时控件监控项目
			List<String> strList1 = new ArrayList<String>();
			// 装机清单对应的时控件监控项目
			List<String> strList2 = new ArrayList<String>();
			if(StringUtils.isNotBlank(woActionObj.getJkxmbhF())){
				strList1.add(woActionObj.getJkxmbhF());
			}
			if(StringUtils.isNotBlank(woActionObj.getJkxmbhS())){
				strList1.add(woActionObj.getJkxmbhS());
			}
			if(StringUtils.isNotBlank(woActionObj.getJkxmbhT())){
				strList1.add(woActionObj.getJkxmbhT());
			}
			
			for (TimeMonitorSetting setting : list) {
				strList2.add(setting.getJklbh());
			}
			Set<String> strList3 = new HashSet<String>();
			strList3.addAll(strList1);
			strList3.addAll(strList2);
			if(strList1.size() != strList2.size() 
					|| strList3.size() != strList1.size() 
					|| strList3.size() != strList2.size()){
				throw new SystemException("单号为："+woActionObj.getBjName()+"的非例行-时控件工单对应的监控项已经过时！");
			}
		}
	}
	
	/**
	 * 验证完成工作对应的定检件工单监控项是否与生效区的监控项一致
	 * @param xggdid
	 */
	private void validateFixedMonitorSetting(String xggdid){
		CheckingMonitoring checkingMonitoring = flightRecordSheetEffectiveMapper.findWOFixedMonitorSetting(xggdid);
		if(checkingMonitoring != null){
			List<ScheduledCheckMonitorItem> list = 
					flightRecordSheetEffectiveMapper.findLoadingListFixedMonitorSetting(checkingMonitoring.getZjqdid()
							, checkingMonitoring.getDjbh());
			// 工单对应的定检件监控项目
			List<String> strList1 = new ArrayList<String>();
			// 装机清单对应的定检件监控项目
			List<String> strList2 = new ArrayList<String>();
			if(StringUtils.isNotBlank(checkingMonitoring.getJkxmbhF())){
				strList1.add(checkingMonitoring.getJkxmbhF());
			}
			if(StringUtils.isNotBlank(checkingMonitoring.getJkxmbhS())){
				strList1.add(checkingMonitoring.getJkxmbhS());
			}
			if(StringUtils.isNotBlank(checkingMonitoring.getJkxmbhT())){
				strList1.add(checkingMonitoring.getJkxmbhT());
			}
			
			for (ScheduledCheckMonitorItem setting : list) {
				strList2.add(setting.getJklbh());
			}
			Set<String> strList3 = new HashSet<String>();
			strList3.addAll(strList1);
			strList3.addAll(strList2);
			if(strList1.size() != strList2.size() 
					|| strList3.size() != strList1.size() 
					|| strList3.size() != strList2.size()){
				throw new SystemException("单号为："+checkingMonitoring.getDjrwbh()+"的定检工单对应的监控项已经过时！");
			}
		}
	}
	
	/**
	 * 还原EO任务
	 * @param rwid
	 */
	private void restoreEOTask(String xggdid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		// 还原EO任务
		flightRecordSheetEffectiveMapper.restoreEOTask(xggdid);
		// 插入日志
		commonRecService.write(xggdid, TableEnum.B_G_010, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, fxjldid);
		
		// 获取对应工程指令
		Engineering engineering = engineeringService.findByEOId(xggdid);
		// 更新工程指令状态
		engineeringService.updateStatus(engineering.getId(), czls, LogOperationEnum.EDIT);
	}
	
	/**
	 * 完成EO任务
	 * @param rwid
	 */
	private void finishEOTask(String xggdid, String zrrid, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("xggdid", xggdid);
		paramMap.put("zdjsrid", zrrid);
		paramMap.put("zdjsrq", new Date());
		// 完成EO任务
		flightRecordSheetEffectiveMapper.finishEOTask(paramMap);
		// 插入日志
		commonRecService.write(xggdid, TableEnum.B_G_010, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.UPDATE, fxjldid);
		// 获取对应工程指令
		Engineering engineering = engineeringService.findByEOId(xggdid);
		// 更新工程指令状态
		engineeringService.updateStatus(engineering.getId(), czls, logOperationEnum);
		
		
	}
	
	/**
	 * 还原生效区-装机清单的任务
	 * @param planTask
	 */
	private void restorePlanInLoadingList(PlanTask planTask, String czls, LogOperationEnum logOperationEnum){
		// 还原生效区-装机清单的任务
		flightRecordSheetEffectiveMapper.restorePlanInLoadingList(planTask);
		// 还原生效区-定检件定检项目的任务
		flightRecordSheetEffectiveMapper.restorePlanInFixedCheck(planTask);
	}
	
	/**
	 * 完成生效区-装机清单的任务
	 * @param planTask
	 */
	private void finishPlanInLoadingList(PlanTask planTask, String czls, LogOperationEnum logOperationEnum){
		// 完成生效区-装机清单的任务
		flightRecordSheetEffectiveMapper.finishPlanInLoadingList(planTask);
		// 完成生效区-定检件定检项目的任务
		flightRecordSheetEffectiveMapper.finishPlanInFixedCheck(planTask.getId());
	}
	
	/**
	 * 还原监控数据
	 * @param xggdid
	 * @param fxjldid
	 */
	private void resotreMonitorData(PlanTask planTask, String fxjldid, String czls, LogOperationEnum logOperationEnum){
		// 插入日志
		List<String> ids = new ArrayList<String>();
		ids.add(planTask.getId());
		commonRecService.write("mainid", ids, TableEnum.B_S_00901, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, planTask.getId());
		// 清空b_s_00901 计划任务监控数据快照
		flightRecordSheetEffectiveMapper.deletePlanTaskSnapshot(planTask.getId());
		// 还原b_s_008 定检件监控计划表
		if(String.valueOf(PlanTaskType.CHECK_BILL.getId()).equals(planTask.getRwlx())){	// 定检任务
			// 获取定检监控计划数据
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("djrwdh", planTask.getId());
			paramMap.put("fxjldid", fxjldid);
			ScheduledCheckPlan plan = flightRecordSheetEffectiveMapper.findScheduledCheckPlan(paramMap);
			// 失效该数据
			flightRecordSheetEffectiveMapper.updateScheduledCheckPlanWhenNotCyjs(paramMap);
			if(plan == null || plan.getIsCyjs() == 1){ // 当前飞行记录单对应的监控计划数据参与计算（当前飞行记录单最新，后面无新增的飞行记录单）
				// 找到之前的监控数据
				List<String> list = flightRecordSheetEffectiveMapper.findBeforeScheduledCheckPlan(plan);
				if(list.size() >= 1){	// 有之前的监控数据
					// 更新上一次的监控数据为参与计算
					flightRecordSheetEffectiveMapper.updatePreviousScheduledCheckPlan(list.get(0));
				} else{	// 无之间的监控数据
					// 更新初始数据为参与计算
					flightRecordSheetEffectiveMapper.updateInitScheduledCheckPlan(plan);
				}
			} 
		}
	}
	
	/**
	 * 飞行记录单生效
	 * @param fxjldid
	 */
	private void takeEffect(FlightRecordSheet flightRecordSheet){
		// 还原所有装机清单表数据
		restoreAllLoadingList(flightRecordSheet.getId(), flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 删除该飞行记录单对应的部件使用情况
		flightRecordSheetEffectiveMapper.deleteComponentUsage(flightRecordSheet.getId());
		// 更新飞行记录单状态
		updateFlightRecordSheetStatus(flightRecordSheet.getId(), flightRecordSheet.getOperation(), null, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航前
		dealWithHd(flightRecordSheet.getId(), LEG_BEFORE, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航次一
		dealWithHc(flightRecordSheet.getId(), VOYAGE_ONE, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航间一
		dealWithHd(flightRecordSheet.getId(), LEG_ONE, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航次二
		dealWithHc(flightRecordSheet.getId(), VOYAGE_TWO, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航间二
		dealWithHd(flightRecordSheet.getId(), LEG_TWO, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航次三
		dealWithHc(flightRecordSheet.getId(), VOYAGE_THREE, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航间三
		dealWithHd(flightRecordSheet.getId(), LEG_THREE, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航次四
		dealWithHc(flightRecordSheet.getId(), VOYAGE_FOUR, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航后
		dealWithHd(flightRecordSheet.getId(), LEG_AFTER, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 处理航次调整值
		dealWithHc(flightRecordSheet.getId(), null, 
				flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
	}
	
	/**
	 * 还原所有装机清单表数据
	 * @param fxjldid
	 * @param czls
	 * @param logOperationEnum
	 */
	private void restoreAllLoadingList(String fxjldid, String czls, LogOperationEnum logOperationEnum){
		// 获取所有飞行记录单中的装上件、拆下件
		List<Map<String, String>> all = flightRecordSheetEffectiveMapper.getAllDisassemblyLoadingList(fxjldid);
		// 还原指令
		List<ReturnInstruction> instructions = flightRecordSheetEffectiveMapper.findReturnInstruction(fxjldid);
		for (ReturnInstruction instruction : instructions) {
			if(instruction.getDxlx() == DXLX_COMPONENT){	// 对象类型-拆解件
				Map<String, String> map = gson.fromJson(instruction.getDxid(), new TypeToken<Map<String, String>>(){}.getType());
				String cxjid = map.get("CXJID");
				String zsjid = map.get("ZSJID");
				String cjjlid = map.get("CJJLID");
				
				// 对比移除出还原指令中包含的拆解件
				for (Map<String, String> one : all) {
					String cxjid_all = one.get("CXJID");
					String zsjid_all = one.get("ZSJID");
					String cjjlid_all = one.get("CJJLID");
					if(cjjlid_all.equals(cjjlid)){
						if(!Utils.Str.isEmpty(zsjid_all) && zsjid_all.equals(zsjid)){
							one.remove("ZSJID");
						}
						if(!Utils.Str.isEmpty(cxjid_all) && cxjid_all.equals(cxjid)){
							one.remove("CXJID");
						}
					}
				}
			}
		}
		
		//还原装机清单数据
		for (Map<String, String> one : all) {
			String cxjid = one.get("CXJID");
			String zsjid = one.get("ZSJID");
			String cjjlid = one.get("CJJLID");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fxjldid", fxjldid);
			paramMap.put("cxjid", cxjid);
			paramMap.put("zsjid", zsjid);
			paramMap.put("whdwid", ThreadVarUtil.getUser().getBmdm());
			paramMap.put("whrid", ThreadVarUtil.getUser().getId());
			paramMap.put("whsj", new Date());
			resotreLoadingList(fxjldid, cxjid, zsjid, cjjlid, czls, logOperationEnum, paramMap, false);
		}
		
	}
	
	/**
	 * 处理航次
	 * @param fxjldid	飞行记录单id
	 * @param hc	航次
	 */
	private void dealWithHc(String fxjldid, Integer hc, String czls, LogOperationEnum logOperationEnum){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fxjldid", fxjldid);
		paramMap.put("hc", hc);
		// 航次对应的飞行记录
		FlightRecord flightRecord = flightRecordSheetEffectiveMapper.findFlightRecordByHc(paramMap);
		// 找到飞机下所有装机部件
		List<Map<String, Object>> lists = flightRecordSheetEffectiveMapper.findPlaneComponent(flightRecord);
		// 待保存集合
		List<Map<String, Object>> saveList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> loadingList : lists) {
			// 生成部件使用情况实体
			saveList.add(generateComponentUsageBean(loadingList, flightRecord, hc));
		}
		// 保存部件使用情况
		if(!saveList.isEmpty()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("saveList", saveList);
			flightRecordSheetEffectiveMapper.insertComponentUsage(map);
		}
	}
	
	/**
	 * 生成部件使用情况实体
	 * @param loadingList
	 * @param flightRecord
	 * @param hc
	 * @return
	 */
	private Map<String, Object> generateComponentUsageBean(Map<String, Object> loadingList,
			FlightRecord flightRecord,  Integer hc){
		Map<String, Object> dataBean = new HashMap<String, Object>();
		dataBean.put("id", UUID.randomUUID().toString());
		dataBean.put("mainid", loadingList.get("bjid"));
		dataBean.put("dprtcode", flightRecord.getDprtcode());
		dataBean.put("jh", loadingList.get("jh"));
		dataBean.put("xlh", loadingList.get("xlh"));
		dataBean.put("zt", 1);
		dataBean.put("is_cyjs", 1);
		dataBean.put("csbj", 0);
		dataBean.put("whsj", new Date());
		dataBean.put("xdbs", hc == null ? 1 : 0);
		dataBean.put("fjzch", flightRecord.getFjzch());
		dataBean.put("fxjldid", flightRecord.getFxjldid());
		dataBean.put("fxjldh", flightRecord.getFxjldh());
		dataBean.put("fxrq", formatDate(flightRecord.getFxrq(), "yyyy-MM-dd"));
		dataBean.put("fxjldcjsj", flightRecord.getZdsj());
		dataBean.put("hc", hc);
		dataBean.put("tsfxpzid", flightRecord.getTsfxpzid());
		dataBean.put("xsz", loadingList.get("xsz"));
		// 部件位置
		int wz = Integer.parseInt(String.valueOf(loadingList.get("wz")));
		// 通用属性
		dataBean.put("fxsj_xs", flightRecord.getFxsjXs());
		dataBean.put("fxsj_fz", flightRecord.getFxsjFz());
		dataBean.put("fxsj_xs_skj", flightRecord.getFxsjXs());
		dataBean.put("fxsj_fz_skj", flightRecord.getFxsjFz());
		dataBean.put("qljxh", flightRecord.getQljxh());
		dataBean.put("qljxh_skj", flightRecord.getQljxh());
		dataBean.put("ts1", flightRecord.getTs1());
		dataBean.put("ts2", flightRecord.getTs2());
		if(wz == PlaneComponentPositionEnum.ZF.getCode()){	// 左发
			
			dataBean.put("fsj_xs", flightRecord.getF1sjXs());
			dataBean.put("fsj_fz", flightRecord.getF1sjFz());
			dataBean.put("fdj_n1", flightRecord.getF1N1());
			dataBean.put("fdj_n2", flightRecord.getF1N2());
			dataBean.put("fdj_n3", flightRecord.getF1N3());
			dataBean.put("fdj_n4", flightRecord.getF1N4());
			dataBean.put("fdj_n5", flightRecord.getF1N5());
			dataBean.put("fdj_n6", flightRecord.getF1N6());
		} else if(wz == PlaneComponentPositionEnum.YF.getCode()){ // 右发
			
			dataBean.put("fsj_xs", flightRecord.getF2sjXs());
			dataBean.put("fsj_fz", flightRecord.getF2sjFz());
			dataBean.put("fdj_n1", flightRecord.getF2N1());
			dataBean.put("fdj_n2", flightRecord.getF2N2());
			dataBean.put("fdj_n3", flightRecord.getF2N3());
			dataBean.put("fdj_n4", flightRecord.getF2N4());
			dataBean.put("fdj_n5", flightRecord.getF2N5());
			dataBean.put("fdj_n6", flightRecord.getF2N6());
		} else if(wz == PlaneComponentPositionEnum.JC.getCode()){	// 绞车
			
			dataBean.put("jcsj_xs", flightRecord.getJcsjXs());
			dataBean.put("jcsj_fz", flightRecord.getJcsjFz());
			dataBean.put("jcxh", flightRecord.getJcxh());
		} else if(wz == PlaneComponentPositionEnum.SSD.getCode()){	// 搜索灯
			
			dataBean.put("ssdsj_xs", flightRecord.getSsdsjXs());
			dataBean.put("ssdsj_fz", flightRecord.getSsdsjFz());
		} else if(wz == PlaneComponentPositionEnum.WDG.getCode()){	// 外吊挂
			
			dataBean.put("dgxh", flightRecord.getDgxh());
		}
		// 设置部件使用情况-时控件值
		setTimeMonitorValue(dataBean, loadingList, flightRecord);
		return dataBean;
	}
	
	/**
	 * 设置部件使用情况-时控件值
	 * @param dataBean
	 * @param loadingList
	 */
	private void setTimeMonitorValue(Map<String, Object> dataBean, 
			Map<String, Object> loadingList, FlightRecord flightRecord){
		String tsbh = String.valueOf(loadingList.get("bh"));
		Double xsz = loadingList.get("xsz") == null ? null : Double.parseDouble(String.valueOf(loadingList.get("xsz")));
		if(!Utils.Str.isEmpty(tsbh)){
			// 起落架循环
			BigDecimal qljxh = flightRecord.getQljxh();
			// 飞行时间-小时
			int fxsjXs = flightRecord.getFxsjXs();
			// 飞行时间-分钟
			int fxsjFz = flightRecord.getFxsjFz();
			// 总飞行时间（分钟）
			double fxsjTotal = fxsjXs * 60 + fxsjFz;
			if(tsbh.equals("TS001")){
				// 系数值默认为0
				xsz = xsz == null ? 0 : xsz;
				// 起落架循环-时控件  = 起落架循环 + 吊挂循环 * 系数值
				qljxh = qljxh.add(flightRecord.getDgxh().multiply(new BigDecimal(xsz))).setScale(2, BigDecimal.ROUND_HALF_UP);
				dataBean.put("qljxh_skj", qljxh);
			} else if(tsbh.equals("TS002")){
				// 系数值默认为1
				xsz = xsz == null ? 1 : xsz;
				// 飞行时间-时控件 = 飞行时间 * 系数值
				fxsjTotal = fxsjTotal * xsz;
				fxsjTotal = formatDouble2(fxsjTotal, 0);
				dataBean.put("fxsj_xs_skj", (int)(fxsjTotal / 60));
				dataBean.put("fxsj_fz_skj", formatDouble2(fxsjTotal % 60, 0));
			} else if(tsbh.equals("TS003")){
				// 系数值默认为1
				xsz = xsz == null ? 1 : xsz;
				// 起落架循环-时控件  = 起落架循环 + 飞行时间（小时） * 8
				BigDecimal fxsjTotalXs = new BigDecimal(fxsjTotal).multiply(new BigDecimal(8))
						.divide(new BigDecimal(60), 0, BigDecimal.ROUND_HALF_UP);
				dataBean.put("qljxh_skj", qljxh.add(fxsjTotalXs).setScale(2));
				// 飞行时间-时控件 = 飞行时间 * 系数值
				fxsjTotal = fxsjTotal * xsz;
				fxsjTotal = formatDouble2(fxsjTotal, 0);
				dataBean.put("fxsj_xs_skj", (int)(fxsjTotal / 60));
				dataBean.put("fxsj_fz_skj", formatDouble2(fxsjTotal % 60, 0));
			} else if(tsbh.equals("TS004")){
				// 系数值默认为1
				xsz = xsz == null ? 1 : xsz;
				// 飞行时间-时控件 = 飞行时间 * 系数值
				fxsjTotal = fxsjTotal * xsz;
				fxsjTotal = formatDouble2(fxsjTotal, 0);
				dataBean.put("fxsj_xs_skj", (int)(fxsjTotal / 60));
				dataBean.put("fxsj_fz_skj", formatDouble2(fxsjTotal % 60, 0));
			}
		}
	}
	
	/**
	 * 处理航段
	 * @param fxjldid	飞行记录单id
	 * @param hd	航段
	 */
	private void dealWithHd(String fxjldid, Integer hd, String czls, LogOperationEnum logOperationEnum){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fxjldid", fxjldid);
		paramMap.put("hd", hd);
		// 本航段完成的任务
		List<FlightRecordSheetToPlan> plans = flightRecordSheetEffectiveMapper.findFinishedTaskByHd(paramMap);
		PlanTask planTask = null;
		for (FlightRecordSheetToPlan plan : plans) {
			//FIXME 徐勇 20170522 手工完成任务
			//相关单据ID为空时表示是手工完成的任务，没有上游的任务单，不需要处理任务单的相关信息
			if(StringUtils.isNotBlank(plan.getXgdjid())){
				// 获取计划任务对象
				planTask = planTaskMapper.selectByPrimaryKey(plan.getRwdid());
				// 完成计划任务
				finishPlanTask(plan, fxjldid, czls, logOperationEnum, planTask);
				if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.CHECK_BILL.getId()){
					// 完成定检执行任务
					finishFixedCheckTask(planTask.getXggdid(), plan.getZrrid(), fxjldid, czls, logOperationEnum);
				} else if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.NON_ROUTINE.getId()){
					// 完成非例行工单任务
					finishNonRoutineTask(planTask.getXggdid(), plan.getZrrid(), fxjldid, czls, logOperationEnum);
				} else if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.EO_BILL.getId()){
					// 完成EO工单任务
					finishEOTask(planTask.getXggdid(), plan.getZrrid(), fxjldid, czls, logOperationEnum);
				}
				// 完成生效区-装机清单的任务
				finishPlanInLoadingList(planTask, czls, logOperationEnum);
				// 更新定检监控数据
				updateMonitorData(planTask, fxjldid, hd, czls, logOperationEnum);
			}
			//FIXME 徐勇 20170522 手工完成的任务没有planTask, 传参由 planTask改为rwdid
			// 处理装机件
			dealWithComponent(plan.getRwdid(), fxjldid, hd, czls, logOperationEnum);
		}
	}
	
	/**
	 * 处理装机件
	 * @param rwdid
	 * @param fxjldid
	 * @param hd
	 * @FIXME 徐勇 20170522 传参由planTash改为rwdid
	 */
	private void dealWithComponent(String rwdid, String fxjldid, Integer hd, 
			String czls, LogOperationEnum logOperationEnum){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fxjldid", fxjldid);
		paramMap.put("hd", hd);
		paramMap.put("rwdid", rwdid);
		// 航段对应的拆解记录
		//TODO 手工完成的任务对应的装上拆下件对于现有逻辑是否需要调整 
		//FIXME 20170522 徐勇
		List<Map<String, Object>> dataList = flightRecordSheetEffectiveMapper.findFlightRecordSheetToDisassembly(paramMap);
		for (Map<String, Object> dataInfo : dataList) {
			dataInfo.put("dprtcode", dataInfo.get("dprtcode"));
			dataInfo.put("bmid", ThreadVarUtil.getUser().getBmdm());
			dataInfo.put("userid", ThreadVarUtil.getUser().getId());
			// 拆下件
			if(dataInfo.get("cx_zjqdid") != null){
				// 同步拆下件
				synchronizeCxj(dataInfo, czls, logOperationEnum);
			}
			// 装上件
			if(dataInfo.get("zs_zjqdid") != null){
				// 同步装上件
				synchronizeZsj(dataInfo, czls, logOperationEnum);
			}
		}
	}
	
	/**
	 * 同步拆下件
	 * @param dataInfo
	 */
	private void synchronizeCxj(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 失效编辑区拆下件
		flightRecordSheetEffectiveMapper.invalidLoadingListEditable(dataInfo);
		// 插入日志
		commonRecService.write(String.valueOf(dataInfo.get("cx_zjqdid")), TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.EDIT, UpdateTypeEnum.DELETE, String.valueOf(dataInfo.get("fxjldid")));
		// 写入装机清单拆下历史
		loadingListEditableMapper.writeToLoadingListHistory(String.valueOf(dataInfo.get("cx_zjqdid"))
				, String.valueOf(dataInfo.get("fxjldid")));
		// 失效生效区拆下件
		flightRecordSheetEffectiveMapper.invalidLoadingListEffective(dataInfo);
		// 清除编辑区中拆下件的子节点的父节点id
		flightRecordSheetEffectiveMapper.clearFjdidEditable(dataInfo);
		// 插入日志
		List<String> ids = new ArrayList<String>();
		ids.add(String.valueOf(dataInfo.get("cx_zjqdid")));
		commonRecService.write("fjdid", ids, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, String.valueOf(dataInfo.get("fxjldid")));
		// 清除生效区中拆下件的子节点的父节点id
		flightRecordSheetEffectiveMapper.clearFjdidEffective(dataInfo);
		// 将拆下件的定检监控计划的计算标识改为0
		flightRecordSheetEffectiveMapper.invalidScheduledCheckPlanByZjqdid(dataInfo);
		if(flightRecordSheetEffectiveMapper.isExistDisassemblyRecord(dataInfo)){	// 存在该飞行记录单下的拆解记录
			// 更新拆下件的部件拆解记录-已存该飞行记录单下的记录时
			flightRecordSheetEffectiveMapper.updateDisassemblyRecordWhenExist(dataInfo);
		}else{
			// 更新拆下件的部件拆解记录-不存该飞行记录单下的记录时
			flightRecordSheetEffectiveMapper.updateDisassemblyRecordWhenNotExist(dataInfo);
			// 插入监控项目快照
			flightRecordSheetEffectiveMapper.insertMonitorItemSnapshot(dataInfo);
		}
		
		// 拆解记录
		FlightRecordSheetToDisassembly disassemble = flightRecordSheetToDisassemblyMapper.selectByPrimaryKey(String.valueOf(dataInfo.get("cjjlid")));
		// 飞行记录单
		FlightRecordSheet sheet = flightRecordSheetMapper.selectByPrimaryKey(String.valueOf(dataInfo.get("fxjldid")));
		// 完成任务
		FlightRecordSheetToPlan plan = flightRecordSheetToPlanMapper.selectByPrimaryKey(disassemble.getFxjlgljlid());
		// 工单编号
		String gdbh = flightRecordSheetEffectiveMapper.queryGdhByGdid(plan.getXgdjid());
		if(StringUtils.isBlank(gdbh)){
			gdbh = "无";
		}
		
		if(dataInfo.get("cx_wckcid") != null){
			// 更新外场库存
			flightRecordSheetEffectiveMapper.updateCxjStock(dataInfo);
			// 外场库存
			OutFieldStock out = flightRecordSheetEffectiveMapper.queryOutFieldData(disassemble.getCxWckcid());
			if(out != null){
				// 插入日志
				materialRecService.writeOutfieldRec(disassemble.getCxWckcid(), czls, String.valueOf(dataInfo.get("cjjlid")), disassemble.getCjjldh(),
						OutfieldRecBizTypeEnum.TYPE4, UpdateTypeEnum.UPDATE, "", sheet.getFxjldh(), assembleRwlx(plan),
						plan.getRwdh(), gdbh, out.getKcsl());
			}
			// 更新拆下件库存履历
			flightRecordSheetEffectiveMapper.updateCxjStockRecord(dataInfo);
		}else{
			// 新增外场库存
			dataInfo.put("cx_wckcid", UUID.randomUUID().toString());
			flightRecordSheetEffectiveMapper.insertCxjStock(dataInfo);
			// 外场库存
			OutFieldStock out = flightRecordSheetEffectiveMapper.queryOutFieldData(String.valueOf(dataInfo.get("cx_wckcid")));
			if(out != null){
				// 插入日志
				materialRecService.writeOutfieldRec(String.valueOf(dataInfo.get("cx_wckcid")), czls, 
						String.valueOf(dataInfo.get("cjjlid")), disassemble.getCjjldh(),
						OutfieldRecBizTypeEnum.TYPE4, UpdateTypeEnum.SAVE, "", sheet.getFxjldh(), assembleRwlx(plan),
						plan.getRwdh(), gdbh, out.getKcsl());
			}
			// 新增库存履历
			dataInfo.put("cx_kclvid", UUID.randomUUID().toString());
			flightRecordSheetEffectiveMapper.insertCxjStockRecord(dataInfo);
			// 回填b_s_0060201拆下件库存id
			flightRecordSheetEffectiveMapper.backfillCxjStockRecordId(dataInfo);
		}
	}
	
	/**
	 * 之后的飞行记录单是否有装上的记录
	 * @param dataInfo
	 * @return
	 */
	private int hasAfterAmountRecord(Map<String, Object> dataInfo){
		if(dataInfo.get("cx_xlh") == null || StringUtils.isBlank(String.valueOf(dataInfo.get("cx_xlh")))){
			return 0;
		}
		return flightRecordSheetEffectiveMapper.hasAfterAmountRecord(dataInfo);
	}
	
	/**
	 * 同步装上件
	 * @param paramMap
	 */
	private void synchronizeZsj(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 同步装机清单数据(b_s_001 & b_s_003)
		this.synchronizeLoadingList(dataInfo, czls, logOperationEnum);
		// 同步时控件监控设置(b_s_00101 & b_s_00301)
		this.synchronizeTimeMonitor(dataInfo, czls, logOperationEnum);
		// 同步特殊飞行情况(b_s_00102 & b_s_00302)
		this.synchronizeSpecialFlightSetting(dataInfo, czls, logOperationEnum);
		// 同步定检项目数据(b_s_00103 & b_s_00303)
		this.synchronizeFixedItem(dataInfo, czls, logOperationEnum);
		// 同步定检监控数据(b_s_00304)
		this.synchronizeFixedMonitor(dataInfo, czls, logOperationEnum);
		// 同步定检件监控计划(b_s_008)
		this.synchronizeFixedMonitorPlan(dataInfo);
		// 同步部件(b_h_010)
		this.synchronizeComponent(dataInfo);
		// 同步部件拆解记录(b_h_01002)
		this.synchronizeComponentDisassembleRecord(dataInfo);
		// 同步部件使用情况(b_h_01001)
		this.synchronizeComponentUsage(dataInfo);
		// 同步父节点id(b_s_001 & b_s_003)
		this.synchronizeFjdid(dataInfo, czls, logOperationEnum);
		// 同步库存
		this.synchronizeStock(dataInfo, czls, logOperationEnum);
	}
	
	/**
	 * 同步装上件父节点id
	 * @param dataInfo
	 */
	private void synchronizeFjdid(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 同步父节点id-编辑区
		flightRecordSheetEffectiveMapper.updateFjdidEditable(dataInfo);
		// 同步父节点id-生效区
		flightRecordSheetEffectiveMapper.updateFjdidEffective(dataInfo);
		// 插入日志
		commonRecService.writeByWhere("EXISTS (SELECT 1 FROM B_S_00602010104 B1 LEFT JOIN "
				+ "B_S_006020101 B2 ON B1.MAINID = B2.ID WHERE B2.ZS_ZJQDID = '"+String.valueOf(dataInfo.get("zs_zjqdid"))+"' "
				+ "AND b.ID = B1.ZBJ_ZJQDID AND B1.ZT = 1)", 
				TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, String.valueOf(dataInfo.get("fxjldid")));
	}
	
	/**
	 * 同步库存
	 * @param dataInfo
	 */
	private void synchronizeStock(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		String zsWckcid = String.valueOf(dataInfo.get("zs_wckcid"));
		if(!Utils.Str.isEmpty(zsWckcid)){
			// 更新装上件虚拟库存
			flightRecordSheetEffectiveMapper.updateZsjStock(dataInfo);
			// 拆解记录
			FlightRecordSheetToDisassembly disassemble = flightRecordSheetToDisassemblyMapper.selectByPrimaryKey(String.valueOf(dataInfo.get("cjjlid")));
			// 飞行记录单
			FlightRecordSheet sheet = flightRecordSheetMapper.selectByPrimaryKey(String.valueOf(dataInfo.get("fxjldid")));
			// 完成任务
			FlightRecordSheetToPlan plan = flightRecordSheetToPlanMapper.selectByPrimaryKey(disassemble.getFxjlgljlid());
			// 工单编号
			String gdbh = flightRecordSheetEffectiveMapper.queryGdhByGdid(plan.getXgdjid());
			if(StringUtils.isBlank(gdbh)){
				gdbh = "无";
			}
			// 外场库存
			OutFieldStock out = flightRecordSheetEffectiveMapper.queryOutFieldData(disassemble.getZsWckcid());
			if(out != null){
				// 插入日志
				materialRecService.writeOutfieldRec(disassemble.getZsWckcid(), czls, String.valueOf(dataInfo.get("cjjlid")), disassemble.getCjjldh(),
						OutfieldRecBizTypeEnum.TYPE5, UpdateTypeEnum.UPDATE, "", sheet.getFxjldh(), assembleRwlx(plan),
						plan.getRwdh(), gdbh, out.getKcsl());
			}
			// 当库存数量为0时，删除虚拟库存
			flightRecordSheetEffectiveMapper.deleteStockWhenKcslIsZero(dataInfo);
		}
		
		if(dataInfo.get("zs_kclvid") != null){
			// 更新装上件库存履历
			flightRecordSheetEffectiveMapper.updateZsjStockRecord(dataInfo);
		}else{
			// 新增库存履历
			dataInfo.put("zs_kclvid", UUID.randomUUID().toString());
			dataInfo.put("zs_bjid", flightRecordSheetEffectiveMapper.queryHCMainData(dataInfo));
			flightRecordSheetEffectiveMapper.insertZsjStockRecord(dataInfo);
			// 回填b_s_0060201装上件库存id
			flightRecordSheetEffectiveMapper.backfillZsjStockRecordId(dataInfo);
		}
	}
	
	/**
	 * 同步部件使用情况(b_s_001----b_h_01001)
	 * @param fjzch
	 */
	private void synchronizeComponentUsage(Map<String, Object> dataInfo){
		// 该部件的累积部件使用情况
		ComponentUsage usage = flightRecordSheetEffectiveMapper.getComponentUsage(dataInfo);
		// 该部件曾经装上过飞机
		if(isUsed(usage, Integer.parseInt(String.valueOf(dataInfo.get("zs_wz"))))  && isEverDisassembled(usage)){
			//初始标记-调整值
			dataInfo.put("csbj", CSBJ_ADJUST_VALUE);
			dataInfo.put("type", CSBJ_ZJHYY_CODE);
			// 同步部件已用使用情况
			flightRecordSheetEffectiveMapper.synchronizeComponentUsage(dataInfo);
		} else{	// 第一次装机
			//初始标记-部件已用
			dataInfo.put("csbj", CSBJ_BJYY_VALUE);
			dataInfo.put("type", CSBJ_BJYY_CODE);
			// 同步部件已用使用情况
			flightRecordSheetEffectiveMapper.synchronizeComponentUsage(dataInfo);
			
			//初始标记-装机后已用
			dataInfo.put("csbj", CSBJ_ZJHYY_VALUE);
			dataInfo.put("type", CSBJ_ZJHYY_CODE);
			// 同步装机后-->进入系统前情况
			flightRecordSheetEffectiveMapper.synchronizeComponentUsage(dataInfo);
		}
	}
	
	/**
	 * 根据部件拆解记录，判断该部件是否曾经装过飞机
	 * @param usage
	 * @return
	 */
	private boolean isEverDisassembled(ComponentUsage usage){
		return flightRecordSheetEffectiveMapper.isEverDisassembled(usage);
	}
	
	/**
	 * 根据部件使用情况和部件位置，判断部件是否已经使用
	 * @param usage
	 * @param wz
	 * @return
	 */
	private boolean isUsed(ComponentUsage usage, int wz){
		if(usage == null) {
			return false;
		}
		if(wz == PlaneComponentPositionEnum.JS.getCode()){	// 机身
			
			return isNotZero(usage.getFxsjFz()) || isNotZero(usage.getFxsjXs()) || isNotZero(usage.getQljxh()) 
					|| isNotZero(usage.getTs1()) || isNotZero(usage.getTs2());
		} else if(wz == PlaneComponentPositionEnum.ZF.getCode() || wz == PlaneComponentPositionEnum.YF.getCode()){ // 发动机
			
			return isNotZero(usage.getFdjN1()) || isNotZero(usage.getFdjN2()) || isNotZero(usage.getFdjN3()) ||
						isNotZero(usage.getFdjN4()) || isNotZero(usage.getFdjN5()) || isNotZero(usage.getFdjN6()) ||
						isNotZero(usage.getFsjFz()) || isNotZero(usage.getFsjXs());
		} else if(wz == PlaneComponentPositionEnum.JC.getCode()){	// 绞车
			
			return isNotZero(usage.getJcxh()) || isNotZero(usage.getJcsjFz()) || isNotZero(usage.getJcsjXs());
		} else if(wz == PlaneComponentPositionEnum.SSD.getCode()){	// 搜索灯
			
			return isNotZero(usage.getSsdsjFz()) || isNotZero(usage.getSsdsjXs());
		} else if(wz == PlaneComponentPositionEnum.WDG.getCode()){	// 外吊挂
			
			return isNotZero(usage.getDgxh());
		}
		return false;
	}
	
	/**
	 * 判断数字不为空
	 * @param num
	 * @return
	 */
	private boolean isNotZero(BigDecimal num){
		return num != null && num.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * 判断数字不为空
	 * @param num
	 * @return
	 */
	private boolean isNotZero(Integer num){
		return num != null && num > 0;
	}
	
	/**
	 * 同步部件拆解记录(b_s_001----b_h_01002)
	 * @param fjzch
	 */
	private void synchronizeComponentDisassembleRecord(Map<String, Object> dataInfo){
		// 同步部件拆解记录
		flightRecordSheetEffectiveMapper.synchronizeComponentDisassembleRecord(dataInfo);
	}
	
	/**
	 * 同步部件(b_s_001----b_h_010)
	 * @param fjzch
	 */
	private void synchronizeComponent(Map<String, Object> dataInfo){
		// 装上件id
		String zjqdid = String.valueOf(dataInfo.get("zs_zjqdid"));
		// 同步部件
		flightRecordSheetEffectiveMapper.synchronizeComponent(zjqdid);
	}
	
	/**
	 * 同步定检件监控计划(b_s_00304---->b_s_008)
	 * @param fjzch
	 */
	private void synchronizeFixedMonitorPlan(Map<String, Object> dataInfo){
		// 装上件id
		String zjqdid = String.valueOf(dataInfo.get("zs_zjqdid"));
		// 参数map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 装机清单id
		paramMap.put("zjqdid", zjqdid);
		// 制单人id
		paramMap.put("zdrid", ThreadVarUtil.getUser().getId());
		// 同步定检件监控计划数据（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeFixedMonitorPlan(paramMap);
		// 同步定检件监控计划数据中的删除数据（删除）
		flightRecordSheetEffectiveMapper.synchronizeFixedMonitorPlanDeleteData(zjqdid);
	}
	
	/**
	 * 同步定检监控数据(b_s_0060201010301---->b_s_0010301 & b_s_00304)
	 * @param fjzch
	 */
	private void synchronizeFixedMonitor(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 同步定检监控项数据到编辑区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeFixedMonitorEditable(dataInfo);
		// 同步定检监控项数据中的删除数据到编辑区（删除）
		flightRecordSheetEffectiveMapper.synchronizeFixedMonitorDeleteDataEditable(dataInfo);
		// 同步定检监控项数据到生效区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeFixedMonitorEffective(dataInfo);
		// 同步定检监控项数据中的删除数据到生效区（删除）
		flightRecordSheetEffectiveMapper.synchronizeFixedMonitorDeleteDataEffective(dataInfo);
		
		// 装上件id
		String zjqdid = String.valueOf(dataInfo.get("zs_zjqdid"));
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' and NOT EXISTS "
				+ "(SELECT 1 FROM B_S_0060201010301 B1 INNER JOIN B_S_00602010103 B3 "
				+ "ON B1.MAINID = B3.ID INNER JOIN B_S_006020101 T2 ON B3.MAINID = T2.ID "
				+ "WHERE B1.MAINID = B1.MAINID AND B1.JKLBH = b.JKLBH AND B1.JKFLBH = b.JKFLBH "
				+ "AND T2.ZS_ZJQDID = b.ZJQDID AND B1.DJBH = b.DJBH AND B1.ZT = 1)", 
				TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}
	
	/**
	 * 同步定检项目数据(b_s_00602010103---->b_s_00103 & b_s_00303)
	 * @param fjzch
	 */
	private void synchronizeFixedItem(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 同步定检项目数据到编辑区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeFixedItemEditable(dataInfo);
		// 同步定检项目数据到生效区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeFixedItemEffective(dataInfo);
		// 删除生效区无效的定检项目数据
		flightRecordSheetEffectiveMapper.deleteInvalidFixedItemEffective(dataInfo);
		
		// 装上件id
		String zjqdid = String.valueOf(dataInfo.get("zs_zjqdid"));
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_00103, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
	}
	
	/**
	 * 同步特殊飞行情况(b_s_00602010102---->b_s_00102 & b_s_00302)
	 * @param zjqdid
	 */
	private void synchronizeSpecialFlightSetting(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 同步特殊飞行情况到编辑区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeSpecialFlightSettingEditable(dataInfo);
		// 同步特殊飞行情况到编辑区（删除）
		flightRecordSheetEffectiveMapper.synchronizeSpecialFlightSettingDeleteDataEditable(dataInfo);
		// 同步特殊飞行情况到生效区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeSpecialFlightSettingEffective(dataInfo);
		// 同步特殊飞行情况到生效区（删除）
		flightRecordSheetEffectiveMapper.synchronizeSpecialFlightSettingDeleteDataEffective(dataInfo);
		
		// 装上件id
		String zjqdid = String.valueOf(dataInfo.get("zs_zjqdid"));
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_00102, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' AND NOT EXISTS "
				+ "(SELECT 1 FROM B_S_00602010102 B1 INNER JOIN B_S_006020101 T2"
				+ " ON B1.MAINID = T2.ID WHERE T2.ZS_ZJQDID = b.ZJQDID "
				+ "AND B1.TSFXPZID = b.TSFXPZID AND B1.ZT = 1)", 
				TableEnum.B_S_00102, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}
	
	/**
	 * 同步时控件监控设置(b_s_00602010101---->b_s_00101 & b_s_00301)
	 * @param zjqdid
	 */
	private void synchronizeTimeMonitor(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		
		// 同步时控件监控设置到编辑区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeTimeMonitorEditable(dataInfo);
		// 同步时控件监控设置到编辑区（删除）
		flightRecordSheetEffectiveMapper.synchronizeTimeMonitorDeleteDataEditable(dataInfo);
		// 同步时控件监控设置到生效区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeTimeMonitorEffective(dataInfo);
		// 同步时控件监控设置到生效区（删除）
		flightRecordSheetEffectiveMapper.synchronizeTimeMonitorDeleteDataEffective(dataInfo);
		// 装上件id
		String zjqdid = String.valueOf(dataInfo.get("zs_zjqdid"));
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		
		// 插入日志
		commonRecService.writeByWhere("b.zjqdid = '"+zjqdid+"' and NOT EXISTS "
				+ "(SELECT 1 FROM B_S_00602010101 B1 INNER JOIN B_S_006020101 T2 "
				+ "ON B1.MAINID = T2.ID WHERE T2.ZS_ZJQDID = b.ZJQDID AND "
				+ "B1.JKLBH = b.JKLBH AND B1.JKFLBH = b.JKFLBH AND B1.ZT = 1)", 
				TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}
	
	/**
	 * 同步装机清单数据(b_s_006020101---->b_s_001 & b_s_003)
	 * @param zjqdid
	 */
	private void synchronizeLoadingList(Map<String, Object> dataInfo, String czls, LogOperationEnum logOperationEnum){
		// 之后的飞行记录单是否有拆下的记录
		dataInfo.put("hasAfterDisassembleRecord", hasAfterDisassembleRecord(dataInfo));
		// 同步装机清单数据到编辑区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeLoadingListEditable(dataInfo);
		// 同步装机清单数据到生效区（新增、修改）
		flightRecordSheetEffectiveMapper.synchronizeLoadingListEffective(dataInfo);
		// 参数map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 飞行记录单id
		paramMap.put("fxjldid", String.valueOf(dataInfo.get("fxjldid")));
		// 飞机注册号
		paramMap.put("zjqdid", String.valueOf(dataInfo.get("zs_zjqdid")));
		// 部门id
		paramMap.put("bmid", ThreadVarUtil.getUser().getBmdm());
		// 创建人id
		paramMap.put("cjrid", ThreadVarUtil.getUser().getId());
		// 同步航材主数据
		flightRecordSheetEffectiveMapper.synchronizeAerialMaterialMainData(paramMap);
		// 插入日志
		commonRecService.write(String.valueOf(dataInfo.get("zs_zjqdid")), TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
				LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, String.valueOf(dataInfo.get("zs_zjqdid")));
		
	}
	
	/**
	 * 之后的飞行记录单是否有拆下的记录
	 * @param dataInfo
	 * @return
	 */
	private int hasAfterDisassembleRecord(Map<String, Object> dataInfo){
		return flightRecordSheetEffectiveMapper.hasAfterDisassembleRecord(dataInfo);
	}
	
	/**
	 * 更新定检监控数据
	 * @param rwid
	 * @param fxjldid
	 * @param hd
	 */
	private void updateMonitorData(PlanTask planTask, String fxjldid, Integer hd, 
			String czls, LogOperationEnum logOperationEnum){
		User user = ThreadVarUtil.getUser();
		// 获取飞行记录单相关信息
		Map<String, Object> flightData = flightRecordSheetEffectiveMapper.getFlightRecordInfo(planTask.getId(), fxjldid);
		flightData.put("hd", hd);
		// 获取监控计划和实际值
		Map<String, Object> monitorData = flightRecordSheetEffectiveMapper.getMonitorActualValue(flightData);
		// 插入日志
		List<String> ids = new ArrayList<String>();
		ids.add(planTask.getId());
		commonRecService.write("mainid", ids, TableEnum.B_S_00901, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, planTask.getId());
		// 删除计划任务监控数据快照
		flightRecordSheetEffectiveMapper.deletePlanTaskSnapshot(planTask.getId());
		// 时间进位
		carryIfItsTime(monitorData);
		// 新增计划任务监控数据快照
		monitorData.put("id", UUID.randomUUID().toString());
		monitorData.put("fxjldid", fxjldid);
		monitorData.put("fxjldzdsj", flightData.get("fxjldzdsj"));
		monitorData.put("fxrq", flightData.get("fxrq"));
		monitorData.put("mainid", planTask.getId());
		monitorData.put("whdwid", user.getBmdm());
		monitorData.put("whrid", user.getId());
		monitorData.put("whsj", new Date());
		flightRecordSheetEffectiveMapper.insertPlanTaskSnapshot(monitorData);
		// 插入日志
		commonRecService.write(String.valueOf(monitorData.get("id")), TableEnum.B_S_00901, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.SAVE, fxjldid);
		// 更新b_s_008 定检件监控计划表
		if(Integer.parseInt(planTask.getRwlx()) == PlanTaskType.CHECK_BILL.getId()){
			// 失效定检件监控计划
			flightRecordSheetEffectiveMapper.invalidScheduledCheckPlan(monitorData);
			// 参与计算标识
			Integer jsbs = flightRecordSheetEffectiveMapper.judgeCyjs(monitorData).isEmpty() ? 1 : 0;
			if(jsbs == 1){
				flightRecordSheetEffectiveMapper.invalidScheduledCheckPlanThatCalculated(monitorData);
			}
			// 保存第一次定检监控计划
			saveMonitorData(flightData, monitorData, planTask, fxjldid, jsbs, "_f");
			// 保存第二次定检监控计划
			saveMonitorData(flightData, monitorData, planTask, fxjldid, jsbs, "_s");
			// 保存第三次定检监控计划
			saveMonitorData(flightData, monitorData, planTask, fxjldid, jsbs, "_t");
		}
	}
	
	/**
	 * 时间进位
	 */
	private void carryIfItsTime(Map<String, Object> monitorData){
		String jkxmbh_f = String.valueOf(monitorData.get("jkxmbh_f"));	// 第一次监控项目编号
		String jkxmbh_s = String.valueOf(monitorData.get("jkxmbh_s"));	// 第二次监控项目编号
		String jkxmbh_t = String.valueOf(monitorData.get("jkxmbh_t"));	// 第三次监控项目编号
		String sj_f = String.valueOf(monitorData.get("sj_f"));	// 第一次实际值
		String sj_s = String.valueOf(monitorData.get("sj_s"));	// 第二次实际值
		String sj_t = String.valueOf(monitorData.get("sj_t"));	// 第三次实际值
		
		// 进位
		if(!Utils.Str.isEmpty(jkxmbh_f) && !Utils.Str.isEmpty(sj_f) && MonitorItemEnum.isTime(jkxmbh_f)){
			monitorData.put("sj_f", 
					StringAndDate_Util.operateTime(sj_f, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD));
		}
		if(!Utils.Str.isEmpty(jkxmbh_s) && !Utils.Str.isEmpty(sj_s) && MonitorItemEnum.isTime(jkxmbh_s)){	// 第二次
			monitorData.put("sj_s", 
					StringAndDate_Util.operateTime(sj_s, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD));
		}
		if(!Utils.Str.isEmpty(jkxmbh_t) && !Utils.Str.isEmpty(sj_t) && MonitorItemEnum.isTime(jkxmbh_t)){	// 第三次
			monitorData.put("sj_t", 
					StringAndDate_Util.operateTime(sj_t, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD));
		}
	}
	
	/**
	 * 保存定检监控计划
	 * @param flightData
	 * @param monitorData
	 * @param planTask
	 * @param fxjldid
	 * @param jsbs
	 * @param suffix
	 */
	private void saveMonitorData(Map<String, Object> flightData, Map<String, Object> monitorData, 
			PlanTask planTask, String fxjldid, Integer jsbs, String suffix){
		User user = ThreadVarUtil.getUser();
		if(monitorData.get("jkflbh"+suffix) != null){
			ScheduledCheckPlan first = new ScheduledCheckPlan();
			first.setId(UUID.randomUUID().toString());
			first.setZjqdid(String.valueOf(monitorData.get("zjqdid")));
			first.setDjxmid(String.valueOf(monitorData.get("djxmid")));
			first.setDjxmbh(String.valueOf(monitorData.get("djbh")));
			first.setJkflbh(String.valueOf(monitorData.get("jkflbh"+suffix)));
			first.setJklbh(String.valueOf(monitorData.get("jkxmbh"+suffix)));
			first.setJkjhz(String.valueOf(monitorData.get("jkz"+suffix)));
			first.setJksjz(String.valueOf(monitorData.get("sj"+suffix)));
			first.setDw(matchUnit(first.getJklbh()).shortValue());
			first.setFxjldid(fxjldid);
			first.setFxjldh(String.valueOf(flightData.get("fxjldh")));
			first.setFxrq(formatDate(String.valueOf(flightData.get("fxrq")), "yyyy-MM-dd"));
			first.setFxjltbsj(formatDate(String.valueOf(flightData.get("fxjldzdsj")), "yyyy-MM-dd HH:mm:ss"));
			first.setDjrwdh(planTask.getId());
			first.setIsCyjs(jsbs.shortValue());
			first.setCsbj(0);
			first.setZdrid(user.getId());
			first.setZdsj(new Date());
			first.setZt((short) 1);
			first.setDprtcode(String.valueOf(flightData.get("dprtcode")));
			flightRecordSheetEffectiveMapper.insertScheduledCheckPlan(first);
		}
	}
	
	/**
	 * 根据监控类编号匹配适应单位
	 * @param jklbh
	 * @return
	 */
	private Integer matchUnit(String jklbh){
		if(MonitorItemEnum.isCalendar(jklbh)){	// 日历
			return 10;
		}else if(MonitorItemEnum.isTime(jklbh)){	// 时间
			return 20;
		}else if(MonitorItemEnum.isLoop(jklbh)){	//循环
			return 30;
		}
		return null;
	}
	
	/**
	 * 删除飞行记录单中无效的数据
	 * @param fxjldid	飞行记录单号
	 * @param onlyInvalid	是否只删除失效数据，否则删除该飞行记录单的全部数据
	 */
	private void deleteInvalid(String fxjldid, boolean onlyInvalid){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fxjldid", fxjldid);
		if(onlyInvalid){
			paramMap.put("zt", 0);
		}
		// 删除b_s_00601无效数据
		flightRecordSheetEffectiveMapper.deleteBS00601InvalidData(paramMap);
		// 删除b_s_00603无效数据
		flightRecordSheetEffectiveMapper.deleteBS00603InvalidData(paramMap);
		// 删除b_s_00602无效数据
		flightRecordSheetEffectiveMapper.deleteBS00602InvalidData(paramMap);
		// 删除b_s_00602010104无效数据
		flightRecordSheetEffectiveMapper.deleteBS00602010104InvalidData(paramMap);
		// 删除b_s_00602010101无效数据
		flightRecordSheetEffectiveMapper.deleteBS00602010101InvalidData(paramMap);
		// 删除b_s_00602010102无效数据
		flightRecordSheetEffectiveMapper.deleteBS00602010102InvalidData(paramMap);
		// 删除b_s_0060201010301无效数据
		flightRecordSheetEffectiveMapper.deleteBS0060201010301InvalidData(paramMap);
		// 删除b_s_00602010103无效数据
		flightRecordSheetEffectiveMapper.deleteBS00602010103InvalidData(paramMap);
		// 删除b_s_006020101无效数据
		flightRecordSheetEffectiveMapper.deleteBS006020101InvalidData(paramMap);
		// 删除b_s_0060201无效数据
		flightRecordSheetEffectiveMapper.deleteBS0060201InvalidData(paramMap);
	}

	/**
	 * 将字符串类型转换为日期
	 * @param date
	 * @param format
	 * @return
	 */
	private Date formatDate(String date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 保留2位小数
	 * @param d
	 * @return
	 */
	private double formatDouble2(double d, int scale) {
        BigDecimal bg = new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bg.doubleValue();
    }

	/**
	 * 飞行记录单撤销
	 */
	@Override
	public void doCancel(FlightRecordSheet flightRecordSheet) {
		// 设置日志参数
		setRecValue(flightRecordSheet, UpdateTypeEnum.DELETE);
		// 参数验证
		this.validate(flightRecordSheet);
		// 对比差异，保存还原指令
		this.compareDifference(flightRecordSheet);
		// 根据指令，进行还原
		this.restore(flightRecordSheet.getId(), flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 删除飞行记录单中无效的数据。
		// 不删除，保留。
		//this.deleteInvalid(flightRecordSheet.getId(), false);
		// 删除该飞行记录单对应的部件使用情况
		flightRecordSheetEffectiveMapper.deleteComponentUsage(flightRecordSheet.getId());
		// 更新飞行记录单状态
		updateFlightRecordSheetStatus(flightRecordSheet.getId(), flightRecordSheet.getOperation(), 
				flightRecordSheet.getZdjsyy(), flightRecordSheet.getCzls(), flightRecordSheet.getLogOperationEnum());
		// 更新修订提醒标识
		updateNoticeFlag(flightRecordSheet.getId());
		// 验证飞机中一级部件只能存在一个
		validateLevelOneComponent(flightRecordSheet.getId());
	}
	
	/**
	 * 更新飞行记录单状态
	 * @param fxjldid
	 * @param type
	 */
	private void updateFlightRecordSheetStatus(String fxjldid, Integer opeartion, 
			String zdjsyy, String czls, LogOperationEnum logOperationEnum){
		User user = ThreadVarUtil.getUser();
		// 参数bean
		FlightRecordSheet param = new FlightRecordSheet();
		param.setId(fxjldid);
		param.setXdrid(user.getId());
		param.setXdsj(new Date());
		param.setTbbs(SynchronzeEnum.NOT_NEED.getCode());
		// 原数据
		FlightRecordSheet frs = flightRecordSheetMapper.selectByPrimaryKey(fxjldid);
		if(opeartion == 1 || opeartion == 2){	// 新增或修改
			if(FlightRecordSheetStatusEnum.SAVE.getId().equals(frs.getZt())){
				// 保存-->提交
				param.setZt(FlightRecordSheetStatusEnum.SUBMIT.getId());
			}else if(FlightRecordSheetStatusEnum.SUBMIT.getId().equals(frs.getZt())){
				// 提交-->修订
				param.setZt(FlightRecordSheetStatusEnum.REVISE.getId());
			}else if(FlightRecordSheetStatusEnum.REVISE.getId().equals(frs.getZt())){
				// 修订-->修订
				param.setZt(FlightRecordSheetStatusEnum.REVISE.getId());
			}
		} else if(opeartion == 3){	// 撤销
			param.setZdjsyy(zdjsyy);
			if(FlightRecordSheetStatusEnum.SAVE.getId().equals(frs.getZt()) || 
					FlightRecordSheetStatusEnum.SUBMIT.getId().equals(frs.getZt())){
				// 保存、提交-->作废
				param.setZt(FlightRecordSheetStatusEnum.INVALID.getId());
			}else if(FlightRecordSheetStatusEnum.REVISE.getId().equals(frs.getZt())){
				// 修订-->修订作废
				param.setZt(FlightRecordSheetStatusEnum.AMENDMENTVOID.getId());
			}
		}
		// 更新
		flightRecordSheetMapper.updateByPrimaryKeySelective(param);
		// 插入日志
		commonRecService.write(fxjldid, TableEnum.B_S_006, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, opeartion == 1 ? UpdateTypeEnum.SAVE : UpdateTypeEnum.UPDATE, fxjldid);
	}
	
	/**
	 * 设置日志参数
	 * @param fxjldid
	 * @param type
	 * @return
	 */
	private void setRecValue(FlightRecordSheet flightRecordSheet, UpdateTypeEnum type){
		// 操作流水
		String czls = UUID.randomUUID().toString();
		// 日志类型
		LogOperationEnum logOperationEnum = null;
		// 操作类型
		Integer operation = null;
		// 原数据
		FlightRecordSheet frs = flightRecordSheetMapper.selectByPrimaryKey(flightRecordSheet.getId());
		if(frs == null){	//新增
			operation = 1;
			logOperationEnum = LogOperationEnum.SUBMIT_WO;
		}else if(type == UpdateTypeEnum.UPDATE){	// 修改
			operation = 2;
			if(FlightRecordSheetStatusEnum.SAVE.getId().equals(frs.getZt())){
				// 保存-->提交
				logOperationEnum = LogOperationEnum.SUBMIT_WO;
			}else if(FlightRecordSheetStatusEnum.SUBMIT.getId().equals(frs.getZt())){
				// 提交-->修订
				logOperationEnum = LogOperationEnum.EDIT;
			}else if(FlightRecordSheetStatusEnum.REVISE.getId().equals(frs.getZt())){
				// 修订-->修订
				logOperationEnum = LogOperationEnum.EDIT;
			}
		}else if(type == UpdateTypeEnum.DELETE){	// 撤销
			operation = 3;
			if(FlightRecordSheetStatusEnum.SAVE.getId().equals(frs.getZt()) || 
					FlightRecordSheetStatusEnum.SUBMIT.getId().equals(frs.getZt())){
				// 保存、提交-->作废
				logOperationEnum = LogOperationEnum.ZUOFEI;
			}else if(FlightRecordSheetStatusEnum.REVISE.getId().equals(frs.getZt())){
				// 修订-->修订作废
				logOperationEnum = LogOperationEnum.REVOKE;
			}
		}
		flightRecordSheet.setCzls(czls);
		flightRecordSheet.setLogOperationEnum(logOperationEnum);
		flightRecordSheet.setOperation(operation);
	}
	
	/**
	 * 获取计划任务任务类型描述
	 * @param planTask
	 * @return
	 */
	private String assembleRwlx(FlightRecordSheetToPlan plan){
		if(plan != null){
			// 任务类型
			Integer rwlx = plan.getRwlx();
			// 任务子类型
			Integer rwzlx = plan.getRwzlx();
			if(rwlx != null && rwzlx != null){
				if(rwlx == 1){
					 return "定检执行任务";
				 }else if(rwlx == 2){
					 if(rwzlx == 1){
						 return "非例行-时控件工单";
					 }else if(rwzlx == 2){
						 return "非例行-附加工单";
					 }else if(rwzlx == 3){
						 return "非例行-排故工单";
					 }
				 }else if(rwlx == 3){
					 return "EO工单任务";
				 }
			}
		}
		return "";
	}
	
	/**
	 * 更新修订提醒标识
	 * @param fxjldid
	 */
	private void updateNoticeFlag(String fxjldid){
		// 前一个飞行记录单
		FlightRecordSheet previous = flightRecordSheetMapper.getPreviousRecord(fxjldid);
		if(previous == null || previous.getIsXdtx() == 0){
			// 当前飞行记录单
			FlightRecordSheet current = flightRecordSheetMapper.selectByPrimaryKey(fxjldid);
			if(current.getIsXdtx() == 1){
				current.setIsXdtx(0);
				flightRecordSheetMapper.updateByPrimaryKeySelective(current);
			}
			// 更新后面的飞行记录单的提醒标识
			flightRecordSheetMapper.updateAfterNoticeFlag(fxjldid);
		}
	}
	
}
