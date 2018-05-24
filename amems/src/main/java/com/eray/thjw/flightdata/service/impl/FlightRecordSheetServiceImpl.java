package com.eray.thjw.flightdata.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.aerialmaterial.service.ComponentUsageService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetMapper;
import com.eray.thjw.flightdata.dao.FlightRecordSheetToDisassemblyMapper;
import com.eray.thjw.flightdata.dao.MountLoadingListMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.flightdata.po.InspectionRecord;
import com.eray.thjw.flightdata.po.MountTimeMonitorTotal;
import com.eray.thjw.flightdata.po.PreflightData;
import com.eray.thjw.flightdata.service.FlightRecordService;
import com.eray.thjw.flightdata.service.FlightRecordSheetService;
import com.eray.thjw.flightdata.service.FlightRecordSheetToPlanService;
import com.eray.thjw.flightdata.service.InspectionRecordService;
import com.eray.thjw.flightdata.service.MountFixedMonitorService;
import com.eray.thjw.flightdata.service.MountSpecialConditionService;
import com.eray.thjw.flightdata.service.MountSubcomponentService;
import com.eray.thjw.flightdata.service.MountTimeMonitorService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetMapper;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.service.PlaneInitDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.FlightRecordSheetStatusEnum;
import enu.LogOperationEnum;
import enu.MonitorItemEnum;
import enu.OperateEnum;
import enu.PlaneComponentPositionEnum;
import enu.PlaneInitDataEnum;
import enu.SaiBongEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;

/**
 * 飞行记录单服务实现
 * @author hanwu
 *
 */
@Service
public class FlightRecordSheetServiceImpl implements FlightRecordSheetService {

	@Resource
	private FlightSheetMapper flightSheetMapper;
	
	@Resource
	private FlightRecordSheetMapper flightRecordSheetMapper;
	
	@Resource
	private FlightRecordSheetToDisassemblyMapper flightRecordSheetToDisassemblyMapper;
	
	@Resource
	private MountLoadingListMapper mountLoadingListMapper;
	
	@Resource
	private PlaneInitDataService PlaneInitDataService;
	
	@Resource
	private ComponentUsageService componentUsageService;
	
	@Resource
	private FlightRecordService flightRecordService;
	
	@Resource
	private InspectionRecordService inspectionRecordService;
	
	@Resource
	private FlightRecordSheetToPlanService flightRecordSheetToPlanService;
	
	@Resource
	private MountSubcomponentService mountSubcomponentService;
	
	@Resource
	private MountTimeMonitorService mountTimeMonitorService;
	
	@Resource
	private MountSpecialConditionService mountSpecialConditionService;
	
	@Resource
	private MountFixedMonitorService mountFixedMonitorService;
	
	@Resource
	private SaibongUtilService saibongUtilService;     
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	/** 上一页 */
	private static final Integer PREVIOUS_PAGE = -1;
	
	/** 下一页 */
	private static final Integer NEXT_PAGE = 1;
	
	/**
	 * 飞行记录单分页查询
	 * @throws BusinessException 
	 */
	@Override
	public Map<String, Object> queryByPage(FlightRecordSheet sheet) throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = sheet.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		sheet.setId(null);
		PageHelper.startPage(sheet.getPagination());
		List<FlightRecordSheet> list = flightRecordSheetMapper.queryByPage(sheet);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					FlightRecordSheet param = new FlightRecordSheet();
					param.setId(id);
					List<FlightRecordSheet> newRecordList = flightRecordSheetMapper.queryByPage(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, sheet.getPagination());
		}else{
			List<FlightRecordSheet> newRecordList = new ArrayList<FlightRecordSheet>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				FlightRecordSheet param = new FlightRecordSheet();
				param.setId(id);
				newRecordList = flightRecordSheetMapper.queryByPage(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, sheet.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, sheet.getPagination());
		}
	}

	/**
	 * 加载飞行前数据
	 */
	@Override
	public List<FlightRecord> loadPreflightData(FlightRecordSheet sheet) {
		// 初始化4个飞行前数据
		List<FlightRecord> result = initPreflightData();
		// 统计跟飞机走的飞行前数据：飞行小时、起落架循环、ts1、ts2---->飞机初始化数据+飞行记录单数据
		findWithPlaneData(sheet, result);
		// 统计跟部件走的飞行前数据：其他---->部件使用情况
		findWithComponentData(sheet, result);
		return result;
	}
	
	/**
	 * 初始化4个飞行前数据
	 * @return
	 */
	private List<FlightRecord> initPreflightData(){
		List<FlightRecord> result = new ArrayList<FlightRecord>();
		FlightRecord hc1 = new FlightRecord();
		hc1.setHc(2);
		result.add(hc1);
		FlightRecord hc2 = new FlightRecord();
		hc2.setHc(4);
		result.add(hc2);
		FlightRecord hc3 = new FlightRecord();
		hc3.setHc(6);
		result.add(hc3);
		FlightRecord hc4 = new FlightRecord();
		hc4.setHc(8);
		result.add(hc4);
		return result;
	}
	
	/**
	 * 统计跟飞机走的飞行前数据：飞行小时、起落架循环、ts1、ts2---->飞机初始化数据+飞行记录单数据
	 * @param fjzch
	 * @param resultMap key:监控项 		value:对应的件号、序列号、飞行前数据
	 */
	private void findWithPlaneData(FlightRecordSheet sheet, List<FlightRecord> result){
		
		// 飞机初始化数据
		Map<String, Object> initDatasMap = PlaneInitDataService.findByFjzch(sheet.getFjzch(), sheet.getDprtcode());
		
		if(initDatasMap != null){
			
			for (String key : initDatasMap.keySet()) {
				if (key.equalsIgnoreCase(PlaneInitDataEnum.INIT_TIME_JSFX.getCode()) && initDatasMap.get(key) != null) {	// 飞行时间
					
					addToResult(MonitorItemEnum.FUSELAGE_FLIGHT_TIME, initDatasMap.get(key).toString(), result);
					
				} else if (key.equalsIgnoreCase(PlaneInitDataEnum.INIT_LOOP_QLJ.getCode()) && initDatasMap.get(key) != null){	// 起落架循环
					
					addToResult(MonitorItemEnum.LANDING_GEAR_CYCLE, initDatasMap.get(key).toString(), result);
					
				} else if (key.equalsIgnoreCase(PlaneInitDataEnum.INIT_LOOP_TS1.getCode()) && initDatasMap.get(key) != null){	// ts1
					
					addToResult(MonitorItemEnum.SPECIAL_FIRST, initDatasMap.get(key).toString(), result);
					
				} else if (key.equalsIgnoreCase(PlaneInitDataEnum.INIT_LOOP_TS2.getCode()) && initDatasMap.get(key) != null){	// ts2
					
					addToResult(MonitorItemEnum.SPECIAL_SECOND, initDatasMap.get(key).toString(), result);
					
				}
			}
			
		}
		
		
		// 飞行记录单数据
		FlightRecord flightRecord = flightRecordSheetMapper.countUsageByFjzch(sheet);
		
		if (flightRecord != null) {
			
			// 飞机初始化的飞行时间   + 飞行记录单的飞行时间
			addToResult(MonitorItemEnum.FUSELAGE_FLIGHT_TIME, flightRecord.getFxsj(), result);
			
			// 飞机初始化的起落架循环  + 飞行记录单的起落架循环
			addToResult(MonitorItemEnum.LANDING_GEAR_CYCLE, flightRecord.getQljxh(), result);
			
			// 飞机初始化的ts1 + 飞行记录单的ts1
			addToResult(MonitorItemEnum.SPECIAL_FIRST, flightRecord.getTs1(), result);
			
			// 飞机初始化的ts2 + 飞行记录单的ts2
			addToResult(MonitorItemEnum.SPECIAL_SECOND, flightRecord.getTs2(), result);
			
		}
		
	}
	
	/**
	 * 将指定监控项的值加到结果集中
	 * @param monitorItem 监控项编号
	 * @param value	监控项累计的值
	 * @param resultMap 结果集
	 */
	private void addToResult(MonitorItemEnum monitorItem, Object value, List<FlightRecord> result){
		// obj转string
		String value_str = value == null ? "" : value.toString();
		
		// 循环4个航次
		for (FlightRecord rec : result) {

			if (monitorItem.equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME)) {	// 飞行时间
				
				rec.setFxsj(addOperation(value_str, rec.getFxsj(), monitorItem));
				
			} else if (monitorItem.equals(MonitorItemEnum.LANDING_GEAR_CYCLE)) {	// 起落架循环
				
				rec.setQljxh(new BigDecimal(addOperation(value_str, rec.getQljxh(), monitorItem)));
				
			} else if (monitorItem.equals(MonitorItemEnum.SPECIAL_FIRST)) {	// ts1
				
				rec.setTs1(new BigDecimal(addOperation(value_str, rec.getTs1(), monitorItem)));
				
			} else if (monitorItem.equals(MonitorItemEnum.SPECIAL_SECOND)) {	//ts2
				
				rec.setTs2(new BigDecimal(addOperation(value_str, rec.getTs2(), monitorItem)));
				
			}
		}
	}
	
	/**
	 * 时间、循环、日历的加法计算
	 * @param param1
	 * @param param2
	 * @param monitorItem
	 * @return
	 */
	private String addOperation(Object param1, Object param2, MonitorItemEnum monitorItem){
		param1 = param1 == null ? "0" : param1;
		param2 = param2 == null ? "0" : param2;
		if (MonitorItemEnum.isTime(monitorItem.name())) { // 时间
			
			return StringAndDate_Util.operateTime(param1.toString(), param2.toString(), TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);
			
		} else if (MonitorItemEnum.isLoop(monitorItem.name())) {	// 循环
			
			return new BigDecimal(param1.toString()).add(new BigDecimal(param2.toString())).toString();
			
		} else {	// 日历
			throw new SystemException("暂不支持该格式的计算!");
		}
	}
	
	/**
	 * 统计跟部件走的飞行前数据：除了飞行小时、起落架循环、ts1、ts2之外的其他数据---->部件使用情况
	 * @param fjzch
	 * @param resultMap
	 */
	private void findWithComponentData(FlightRecordSheet sheet, List<FlightRecord> result){
		// 所有一级子部件的部件使用情况
		List<ComponentUsage> usages = new ArrayList<ComponentUsage>();
		if(Utils.Str.isEmpty(sheet.getId())){
			// 根据飞机注册号统计所有一级子部件的部件使用情况
			usages = componentUsageService.sumComponentUsageByPlane(sheet);
			
		}else {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", sheet.getId());
			// 统计航次一对应的一级子部件的部件使用情况
			paramMap.put("hc", 2);
			usages.addAll(componentUsageService.sumComponentUsageByFxjldhHc(paramMap));
			// 统计航次二对应的一级子部件的部件使用情况
			paramMap.put("hc", 4);
			usages.addAll(componentUsageService.sumComponentUsageByFxjldhHc(paramMap));
			// 统计航次三对应的一级子部件的部件使用情况
			paramMap.put("hc", 6);
			usages.addAll(componentUsageService.sumComponentUsageByFxjldhHc(paramMap));
			// 统计航次四对应的一级子部件的部件使用情况
			paramMap.put("hc", 8);
			usages.addAll(componentUsageService.sumComponentUsageByFxjldhHc(paramMap));
		}
		// 组装随飞机的数据
		assembleComponentData(usages, result);

	}
	
	/**
	 * 组装随飞机的数据
	 * @param usages
	 * @param result
	 */
	private void assembleComponentData(List<ComponentUsage> usages, List<FlightRecord> result){
		for (ComponentUsage usage : usages) {
			
			if (PlaneComponentPositionEnum.ZF.getCode().equals(usage.getWz())) {	// 1#左发
				
				for (FlightRecord rec : result) {
					
					if(usage.getHc() <= rec.getHc()){
						
						// 1#发时间
						rec.setF1SjN16Z(addOperation(usage.getFsj(), null, MonitorItemEnum.FUSELAGE_FLIGHT_TIME));
						assemblePreflightData("f1sj", rec.getF1SjN16Z(), usage, rec);
						// 1#发N1
						rec.setF1N1(usage.getFdjN1());
						assemblePreflightData("f1n1", rec.getF1N1() == null ? null : rec.getF1N1().toString(), usage, rec);
						// 1#发N2
						rec.setF1N2(usage.getFdjN2());
						assemblePreflightData("f1n2", rec.getF1N2() == null ? null : rec.getF1N2().toString(), usage, rec);
						// 1#发N3
						rec.setF1N3(usage.getFdjN3());
						assemblePreflightData("f1n3", rec.getF1N3() == null ? null : rec.getF1N3().toString(), usage, rec);
						// 1#发N4
						rec.setF1N4(usage.getFdjN4());
						assemblePreflightData("f1n4", rec.getF1N4() == null ? null : rec.getF1N4().toString(), usage, rec);
						// 1#发N5
						rec.setF1N5(usage.getFdjN5());
						assemblePreflightData("f1n5", rec.getF1N5() == null ? null : rec.getF1N5().toString(), usage, rec);
						// 1#发N6
						rec.setF1N6(usage.getFdjN6());
						assemblePreflightData("f1n6", rec.getF1N6() == null ? null : rec.getF1N6().toString(), usage, rec);
						
					}
					
					
				}
				
			} else if (PlaneComponentPositionEnum.YF.getCode().equals(usage.getWz())) {	// 2#右发
				
				for (FlightRecord rec : result) {
					
					if(usage.getHc() <= rec.getHc()){
						
						// 2#发时间
						rec.setF2SjN16Z(addOperation(usage.getFsj(), null, MonitorItemEnum.FUSELAGE_FLIGHT_TIME));
						assemblePreflightData("f2sj", rec.getF2SjN16Z(), usage, rec);
						// 2#发N1
						rec.setF2N1(usage.getFdjN1());
						assemblePreflightData("f2n1", rec.getF2N1() == null ? null : rec.getF2N1().toString(), usage, rec);
						// 2#发N2
						rec.setF2N2(usage.getFdjN2());
						assemblePreflightData("f2n2", rec.getF2N2() == null ? null : rec.getF2N2().toString(), usage, rec);
						// 2#发N3
						rec.setF2N3(usage.getFdjN3());
						assemblePreflightData("f2n3", rec.getF2N3() == null ? null : rec.getF2N3().toString(), usage, rec);
						// 2#发N4
						rec.setF2N4(usage.getFdjN4());
						assemblePreflightData("f2n4", rec.getF2N4() == null ? null : rec.getF2N4().toString(), usage, rec);
						// 2#发N5
						rec.setF2N5(usage.getFdjN5());
						assemblePreflightData("f2n5", rec.getF2N5() == null ? null : rec.getF2N5().toString(), usage, rec);
						// 2#发N6
						rec.setF2N6(usage.getFdjN6());
						assemblePreflightData("f2n6", rec.getF2N6() == null ? null : rec.getF2N6().toString(), usage, rec);
						
					}
				}
				
			} else if (PlaneComponentPositionEnum.JC.getCode().equals(usage.getWz())) {	// 绞车
				
				for (FlightRecord rec : result) {
					
					if(usage.getHc() <= rec.getHc()){
						
						// 绞车时间
						rec.setJcsjZ(addOperation(usage.getJcsj(), null, MonitorItemEnum.WINCH_TIME));
						assemblePreflightData("jcsj", rec.getJcsjZ(), usage, rec);
						// 绞车循环
						rec.setJcxhZ(usage.getJcxh() == null ? null : usage.getJcxh().toString());
						assemblePreflightData("jcxh", rec.getJcxhZ(), usage, rec);
						
					}
					
				}
				
				
			} else if (PlaneComponentPositionEnum.SSD.getCode().equals(usage.getWz())) {	// 搜索灯
				
				for (FlightRecord rec : result) {
					
					if(usage.getHc() <= rec.getHc()){
						
						// 搜索灯时间
						rec.setSsdsjZ(addOperation(usage.getSsdsj(), null, MonitorItemEnum.SEARCH_LIGHT_TIME));
						assemblePreflightData("ssdsj", rec.getSsdsjZ(), usage, rec);
						
					}
				}
				
				
			} else if (PlaneComponentPositionEnum.WDG.getCode().equals(usage.getWz())) {	// 外吊挂
				
				for (FlightRecord rec : result) {
					
					if(usage.getHc() <= rec.getHc()){
						
						// 外吊挂循环
						rec.setDgxhZ(usage.getDgxh() == null ? null : usage.getDgxh().toString());
						assemblePreflightData("dgxh", rec.getDgxhZ(), usage, rec);
						
					}
				}
				
			}
		}
	}
	
	/**
	 * 拼接飞行前数据对象
	 * @param item
	 * @param value
	 * @param usage
	 * @param rec
	 */
	private void assemblePreflightData(String item, String value, ComponentUsage usage, FlightRecord rec){
		// 航次对应的飞行前数据
		List<PreflightData>  preflightDatas = rec.getPreflightDatas();
		PreflightData data = new PreflightData();
		// 项目
		data.setItem(item);
		// 件号
		data.setJh(usage.getJh());
		// 序列号
		data.setXlh(usage.getXlh());
		// 飞行前数据
		data.setValue(value);
		preflightDatas.add(data);
	}

	/**
	 * 根据条件查询飞行记录单
	 */
	@Override
	public List<FlightRecordSheet> queryList(FlightRecordSheet sheet) {
		return flightRecordSheetMapper.queryList(sheet);
	}

	/**
	 * 根据飞机注册号查询记录本页码
	 */
	@Override
	public List<String> queryRecordNumByFjzch(String fjzch) {
		return flightRecordSheetMapper.queryRecordNumByFjzch(fjzch);
	}

	/**
	 * 保存飞行记录单数据
	 * @throws BusinessException 
	 */
	@Override
	public String save(FlightRecordSheet data) throws BusinessException {
		
		// 验证是否有飞机权限
		List<String> list = new ArrayList<String>();
		list.add(data.getFjzch());
		planeModelDataService.existsAircraft4Expt(ThreadVarUtil.getUser().getId(), 
				ThreadVarUtil.getUser().getUserType(), ThreadVarUtil.getUser().getJgdm(), list);
				
		// 操作流水
		if(StringUtils.isBlank(data.getCzls())){
			data.setCzls(UUID.randomUUID().toString());
			data.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		}
		// 保存飞行记录单数据
		saveFlightRecordSheet(data);
		// 保存飞行记录单明细
		flightRecordService.deleteNotExist(data);
		for (FlightRecord flightData : data.getFlightData()) {
			flightRecordService.save(flightData);
		}
		// 保存航间检查记录
		for (InspectionRecord jcr : data.getJcr()) {
			inspectionRecordService.save(jcr);
		}
		inspectionRecordService.deleteNotExist(data);
		// 保存飞行记录关联
		for (FlightRecordSheetToPlan finishedWord : data.getFinishedWork()) {
			flightRecordSheetToPlanService.save(finishedWord);
		}
		flightRecordSheetToPlanService.deleteNotExist(data);
		// 保存飞行记录单附件
		saveAttachments(data);
		// 删除游离数据
		deleteDetachmentData(data.getId());
		return data.getId();
	}
	
	/**
	 * 保存飞行记录单数据
	 */
	private String saveFlightRecordSheet(FlightRecordSheet data){
		// 新增
		if(StringUtils.isBlank(data.getId())){	
			// 生成uuid
			data.setId(UUID.randomUUID().toString());
			// 生成飞行记录单号
			String fjzch = data.getFjzch();
			String last4=fjzch.substring(fjzch.length()-4 <= 0 ? 0 : fjzch.length()-4,fjzch.length());
			User user = ThreadVarUtil.getUser();
			try {
				data.setFxjldh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.FXJL.getName(), last4));
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			// 设置新增默认值
			setInsertValue(data);
			// 状态 = 保存
			data.setZt(FlightRecordSheetStatusEnum.SAVE.getId());
			// 保存数据
			flightRecordSheetMapper.insertSelective(data);
			// 插入日志
			if(data.getLogOperationEnum() == LogOperationEnum.SAVE_WO){
				commonRecService.write(data.getId(), TableEnum.B_S_006, ThreadVarUtil.getUser().getId(), data.getCzls(),
						data.getLogOperationEnum(), UpdateTypeEnum.SAVE, data.getId());
			}
		} 
		// 修改
		else {
			// 设置修改默认值
			setUpdateValue(data);
			// 状态 = 修订
			//data.setZt(FlightRecordSheetStatusEnum.REVISE.getId());
			// 保存数据
			flightRecordSheetMapper.updateByPrimaryKeySelective(data);
			// 插入日志
			if(data.getLogOperationEnum() == LogOperationEnum.SAVE_WO){
				commonRecService.write(data.getId(), TableEnum.B_S_006, ThreadVarUtil.getUser().getId(), data.getCzls(),
						data.getLogOperationEnum(), UpdateTypeEnum.UPDATE, data.getId());
			}
		}
		// 设置飞行记录单id
		setFxjldid(data);
		return data.getId();
	}
	
	/**
	 * 删除游离的数据
	 * 例如：删除整个完成任务，只会删除完成任务单表，并不会删除其子表，现在统一删除，避免影响后面的程序
	 * @param data
	 */
	private void deleteDetachmentData(String fxjldid){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramMap.put("fxjldid", fxjldid);
		paramMap.put("whdwid", user.getBmdm());
		paramMap.put("whrid", user.getId());
		paramMap.put("whsj", new Date());
		paramMap.put("tbbs", SynchronzeEnum.TO_DO.getCode());
		paramMap.put("zt", 0);
		flightRecordSheetToDisassemblyMapper.deleteInvalid(paramMap);
		mountLoadingListMapper.deleteInvalid(paramMap);
	}
	
	/**
	 * 设置飞行记录单id
	 * @param data
	 */
	private void setFxjldid(FlightRecordSheet data){
		for (FlightRecord flightData : data.getFlightData()) {
			flightData.setFxjldid(data.getId());
			flightData.setCzls(data.getCzls());
			flightData.setLogOperationEnum(data.getLogOperationEnum());
			flightData.setDprtcode(data.getDprtcode());
		}
		for (InspectionRecord jcr : data.getJcr()) {
			jcr.setFxjldid(data.getId());
			jcr.setCzls(data.getCzls());
			jcr.setLogOperationEnum(data.getLogOperationEnum());
			jcr.setDprtcode(data.getDprtcode());
		}
		for (FlightRecordSheetToPlan finishedWork : data.getFinishedWork()) {
			finishedWork.setFxjldid(data.getId());
			finishedWork.setWcrq(data.getFxrq());
			finishedWork.setCzls(data.getCzls());
			finishedWork.setLogOperationEnum(data.getLogOperationEnum());
			finishedWork.setDprtcode(data.getDprtcode());
		}
	}
	
	/**
	 * 设置新增默认值
	 * @param data
	 */
	private void setInsertValue(FlightRecordSheet data){
		User user = ThreadVarUtil.getUser();
		data.setZddwid(user.getBmdm());
		data.setZdrid(user.getId());
		data.setZdsj(new Date());
		data.setIsXdtx(0);
		setUpdateValue(data);
	}
	
	/**
	 * 设置修改默认值
	 * @param ll
	 */
	private void setUpdateValue(FlightRecordSheet data){
		User user = ThreadVarUtil.getUser();
		data.setXdrid(user.getId());
		data.setXdsj(new Date());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}

	/**
	 * 获取所有飞行记录单相关数据
	 */
	@Override
	public FlightRecordSheet getAllData(String id) {
		// 获取飞行数据详细
		FlightRecordSheet totalData = flightRecordSheetMapper.getFlightDataDetail(id);
		// 获取检查人
		totalData.setJcr(inspectionRecordService.findByFxjldid(id));
		// 获取完成工作
		totalData.setFinishedWork(flightRecordSheetToPlanService.findByFxjldid(id));
		// 完成工作-->关联计划
		for (FlightRecordSheetToPlan plan : totalData.getFinishedWork()) {
			
			//TODO 为什么附件是用rwdid进行关联的
			//FIXME 20170522 徐勇
			Attachment attachment= new Attachment();
			attachment.setMainid(plan.getRwdid());
			plan.setAttachments(attachmentMapper.queryListAttachments(attachment));
			// 关联计划-->拆解记录
			for (FlightRecordSheetToDisassembly dismount : plan.getDismountRecord()) {
				// 拆解记录-->装机清单-->子节点
				if(dismount.getOn() != null){
					// 时控件相关数据
					dismount.getOn().setChildren(mountSubcomponentService.findByMainid(dismount.getOn().getId()));
					MountTimeMonitorTotal timeMonitor = new MountTimeMonitorTotal();
					timeMonitor.setTsn(dismount.getOn().getTsn());
					timeMonitor.setTso(dismount.getOn().getTso());
					timeMonitor.setSettings(mountTimeMonitorService.findByMainid(dismount.getOn().getId()));
					timeMonitor.setConditions(mountSpecialConditionService.findByMainid(dismount.getOn().getId()));
					dismount.setTimeMonitor(timeMonitor);
					
					// 定检件相关数据
					dismount.setFixedMonitor(mountFixedMonitorService.findByMainid(dismount.getOn().getId()));
				}
			}
			
		}
		// 获取装上的时控件
		return totalData;
	}

	/**
	 * 根据id查找飞行记录单
	 */
	@Override
	public FlightRecordSheet findById(String id) {
		return flightRecordSheetMapper.selectByPrimaryKey(id);
	}

	/**
	 * 验证记录本页码和飞行日期
	 */
	@Override
	public Map<String, Object> validate(FlightRecordSheet flightRecordSheet) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", true);
		// 飞行日期不能小于库里已经存在的数据（强校验）
		FlightRecordSheet param3 = new FlightRecordSheet();
		param3.setFjzch(flightRecordSheet.getFjzch());
		param3.setDprtcode(flightRecordSheet.getDprtcode());
		param3.setParamsMap(flightRecordSheet.getParamsMap());
		param3.setZt(-1);
		Date maxFxrq = flightRecordSheetMapper.findMaxFxrq(param3);
		
		if(maxFxrq != null && flightRecordSheet.getFxrq().compareTo(maxFxrq) < 0){
			resultMap.put("success", false);
			resultMap.put("type", 3);
			resultMap.put("message", "系统存在此日期之后的飞行数据，不能创建"+Utils.DT.formatDate(flightRecordSheet.getFxrq())+"的飞行数据！");
			return resultMap;
		}
		// 飞行日期不能大于当前日期（强校验）
		if(flightRecordSheet.getFxrq().compareTo(new Date()) > 0){
			resultMap.put("success", false);
			resultMap.put("type", 3);
			resultMap.put("message", "不可创建飞行日期大于当前日期的飞行数据！");
			return resultMap;
		}
		// 记录本页码避免重复（弱校验）
		FlightRecordSheet param1 = new FlightRecordSheet();
		param1.setFjzch(flightRecordSheet.getFjzch());
		param1.setDprtcode(flightRecordSheet.getDprtcode());
		param1.setJlbym(flightRecordSheet.getJlbym());
		param1.setValid("1");
		param1.setParamsMap(flightRecordSheet.getParamsMap());
		List<FlightRecordSheet> list1 = flightRecordSheetMapper.queryList(param1);
		if(!list1.isEmpty()){
			resultMap.put("success", false);
			resultMap.put("type", 1);
			resultMap.put("message", Utils.DT.formatDate(list1.get(0).getZdsj())+"已经创建了此页码的飞行记录单，是否继续？");
			return resultMap;
		}
		// 记录本页码校验连续（弱校验）
		FlightRecordSheet param2 = new FlightRecordSheet();
		param2.setFjzch(flightRecordSheet.getFjzch());
		param2.setDprtcode(flightRecordSheet.getDprtcode());
		param2.setValid("1");
		String currentPage = flightRecordSheet.getJlbym();
		String previousPage;
		try {
			previousPage = String.format("%0"+currentPage.length()+"d", Integer.parseInt(currentPage)-1);
			param2.setJlbym(previousPage);
			param2.setParamsMap(flightRecordSheet.getParamsMap());
			List<FlightRecordSheet> list2 = flightRecordSheetMapper.queryList(param2);
			if(list2.isEmpty()){
				resultMap.put("success", false);
				resultMap.put("type", 2);
				resultMap.put("message", "不存在 "+previousPage+"的 页码，是否继续？");
				return resultMap;
			}
		} catch (NumberFormatException e) {
			// 记录本页码非数字
		}
		return resultMap;
	}

	/**
	 * 加载部件实际使用值
	 */
	@Override
	public Map<String, Object> loadActuallyUsed(ComponentUsage param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 随部件走的数据
		ComponentUsage usage = componentUsageService.sumComponentUsage(param);
		FlightRecordSheet param1 = new FlightRecordSheet();
		param1.setFjzch(param.getFjzch());
		param1.setDprtcode(param.getDprtcode());
		param1.setId(param.getFxjldid());
		// 随飞机走的数据
		FlightRecord flightRecord = flightRecordSheetMapper.countUsageByFjzch(param1);
		if(usage != null){
			resultMap.put(MonitorItemEnum.SEARCH_LIGHT_TIME.name().toLowerCase(), usage.getSsdsj());
			resultMap.put(MonitorItemEnum.WINCH_TIME.name().toLowerCase(), usage.getJcsj());
			resultMap.put(MonitorItemEnum.WINCH_CYCLE.name().toLowerCase(), usage.getJcxh());
			resultMap.put(MonitorItemEnum.EXT_SUSPENSION_LOOP.name().toLowerCase(), usage.getDgxh());
			resultMap.put(MonitorItemEnum.N1.name(), usage.getFdjN1());
			resultMap.put(MonitorItemEnum.N2.name(), usage.getFdjN2());
			resultMap.put(MonitorItemEnum.N3.name(), usage.getFdjN3());
			resultMap.put(MonitorItemEnum.N4.name(), usage.getFdjN4());
			resultMap.put(MonitorItemEnum.N5.name(), usage.getFdjN5());
			resultMap.put(MonitorItemEnum.N6.name(), usage.getFdjN6());
		}
		if(flightRecord != null){
			resultMap.put(MonitorItemEnum.LANDING_GEAR_CYCLE.name().toLowerCase(), flightRecord.getQljxh());
			resultMap.put(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.name().toLowerCase(), flightRecord.getFxsj());
			resultMap.put(MonitorItemEnum.SPECIAL_FIRST.name().toLowerCase(), flightRecord.getTs1());
			resultMap.put(MonitorItemEnum.SPECIAL_SECOND.name().toLowerCase(), flightRecord.getTs2());
		}
		// 飞机初始化数据
		Map<String, Object> initDatasMap = PlaneInitDataService.findByFjzch(param.getFjzch(), param.getDprtcode());
		if(initDatasMap != null){
			if(!Utils.Str.isEmpty(String.valueOf(initDatasMap.get("INIT_TIME_JSFX")))){
				String value1 = resultMap.get(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.name().toLowerCase()) == null ?
						"0" : String.valueOf(resultMap.get(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.name().toLowerCase()));
				String value2 = String.valueOf(initDatasMap.get("INIT_TIME_JSFX"));
				resultMap.put(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.name().toLowerCase(),
						addOperation(value1, value2, MonitorItemEnum.FUSELAGE_FLIGHT_TIME));
			}
			if(!Utils.Str.isEmpty(String.valueOf(initDatasMap.get("INIT_LOOP_QLJ")))){
				String value1 = resultMap.get(MonitorItemEnum.LANDING_GEAR_CYCLE.name().toLowerCase()) == null ?
						"0" : String.valueOf(resultMap.get(MonitorItemEnum.LANDING_GEAR_CYCLE.name().toLowerCase()));
				String value2 = String.valueOf(initDatasMap.get("INIT_LOOP_QLJ"));
				resultMap.put(MonitorItemEnum.LANDING_GEAR_CYCLE.name().toLowerCase(),
						addOperation(value1, value2, MonitorItemEnum.LANDING_GEAR_CYCLE));
			}
			if(!Utils.Str.isEmpty(String.valueOf(initDatasMap.get("INIT_LOOP_TS1")))){
				String value1 = resultMap.get(MonitorItemEnum.SPECIAL_FIRST.name().toLowerCase()) == null ?
						"0" : String.valueOf(resultMap.get(MonitorItemEnum.SPECIAL_FIRST.name().toLowerCase()));
				String value2 = String.valueOf(initDatasMap.get("INIT_LOOP_TS1"));
				resultMap.put(MonitorItemEnum.SPECIAL_FIRST.name().toLowerCase(),
						addOperation(value1, value2, MonitorItemEnum.SPECIAL_FIRST));
			}
			if(!Utils.Str.isEmpty(String.valueOf(initDatasMap.get("INIT_LOOP_TS2")))){
				String value1 = resultMap.get(MonitorItemEnum.SPECIAL_SECOND.name().toLowerCase()) == null ?
						"0" : String.valueOf(resultMap.get(MonitorItemEnum.SPECIAL_SECOND.name().toLowerCase()));
				String value2 = String.valueOf(initDatasMap.get("INIT_LOOP_TS2"));
				resultMap.put(MonitorItemEnum.SPECIAL_SECOND.name().toLowerCase(),
						addOperation(value1, value2, MonitorItemEnum.SPECIAL_SECOND));
			}
		}
		return resultMap;
	}
	
	/**
	 * 保存飞行记录单附件
	 * @param data
	 */
	private void saveAttachments(FlightRecordSheet data){
		List<Attachment> attachments = data.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachments) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(data.getId());
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
		ids.add(data.getId());
		commonRecService.write("mainid", ids, TableEnum.D_011, ThreadVarUtil.getUser().getId(), data.getCzls(),
				data.getLogOperationEnum(), UpdateTypeEnum.SAVE, data.getId());
	}

	/**
	 * 判断部件是否在库存中
	 */
	@Override
	public boolean isExistInStock(LoadingList ll) {
		return flightRecordSheetMapper.getCountInStock(ll) > 0;
	}
	
	/**
	 * 判断部件是否在外场库存中
	 */
	@Override
	public boolean isExistInOutStock(LoadingList ll) {
		return flightRecordSheetMapper.getCountInOutStock(ll) > 0;
	}

	/**
	 * 获取飞行记录单的上一页或下一页
	 */
	@Override
	public String getPageId(String id, Integer pageFlag) {
		FlightRecordSheet sheet = new FlightRecordSheet();
		sheet.setId(id);
		// 上一页
		if(PREVIOUS_PAGE.equals(pageFlag)){
			return flightRecordSheetMapper.getPreviousPage(sheet);
		}
		
		// 下一页
		else if(NEXT_PAGE.equals(pageFlag)){
			if(StringUtils.isNotBlank(id)){
				return flightRecordSheetMapper.getNextPage(sheet);
			}
			return "";
		}
		return null;
	}

	@Override
	public List<FlightRecordSheet> getHbhFxrqFlightRecord(FlightRecordSheet sheet) {
		
		return flightRecordSheetMapper.getHbhFxrqFlightRecord(sheet);
	}

	@Override
	public List<FlightSheet> queryFxrqFlightRecordList(FlightSheet sheet) {
		return flightSheetMapper.queryFlightSheetList(sheet);
	}

}
