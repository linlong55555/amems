package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.dao.ComponentUseCountMapper;
import com.eray.thjw.produce.dao.ComponentUseMapper;
import com.eray.thjw.produce.dao.FlightSheetMapper;
import com.eray.thjw.produce.dao.FlightSheetVoyageMapper;
import com.eray.thjw.produce.dao.InstallationListEffective2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.ComponentUse;
import com.eray.thjw.produce.po.ComponentUseCount;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetDisassemblyRecord;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;
import com.eray.thjw.produce.po.FlightSheetLeg;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.FlightSheetWorker;
import com.eray.thjw.produce.po.InstallAndRemove;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.InstallationListEffective2Init;
import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.WorkOrderWorker;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.FlightSheetLegService;
import com.eray.thjw.produce.service.FlightSheetService;
import com.eray.thjw.produce.service.FlightSheetVoyageService;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils.ObjUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.WhetherEnum;
import enu.produce.FlbStatusEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.InstalledStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.project2.MonitorProjectEnum;
/**
 * 
 * @Description 飞机记录本serviceimpl
 * @CreateTime 2017年10月9日 下午4:51:01
 * @CreateBy 林龙
 */
@Service("flightSheetService")
public class FlightSheetServiceImpl implements FlightSheetService  {

	@Resource
	private FlightSheetMapper flightSheetMapper;
	
	@Resource
	private FlightSheetVoyageMapper flightSheetVoyageMapper;
	
	@Resource
	private FlightSheetVoyageService flightSheetVoyageService;
	
	@Resource
	private FlightSheetLegService flightSheetLegService;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private WorkOrderNewService workOrderNewService;
	
	@Resource
	private ComponentUseMapper componentUseMapper;
	
	@Resource
	private ComponentUseCountMapper componentUseCountMapper; 
	
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	
	@Resource
	private InstallationListEffective2InitMapper installationListEffective2InitMapper;
	
	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;
	
	@Resource
	private MonitorDataService monitorDataService;
	
	
	/** 航次最大数量 */
	private final static Integer FLB_VOYAGE_MAX_COUNT = 4;
	
	/**
	 * 
	 * @Description  飞机记录本列表加载
	 * @CreateTime 2017年10月9日 下午4:49:21
	 * @CreateBy 林龙
	 * @param flightSheet 飞机记录本
	 * @return map 
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllList(FlightSheet flightSheet)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = flightSheet.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		flightSheet.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		flightSheet.getParamsMap().put("userId", user != null ? user.getId() : "");
		flightSheet.getParamsMap().put("userType", user != null ? user.getUserType() : "admin");//验证飞机注册号权限权限
		PageHelper.startPage(flightSheet.getPagination());
		List<FlightSheet> list = flightSheetMapper.queryAllList(flightSheet);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					FlightSheet param = new FlightSheet();
					param.setId(id);
					List<FlightSheet> newRecordList = flightSheetMapper.queryAllList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, flightSheet.getPagination());
		}else{
			List<FlightSheet> newRecordList = new ArrayList<FlightSheet>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				FlightSheet param = new FlightSheet();
				param.setId(id);
				newRecordList = flightSheetMapper.queryAllList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, flightSheet.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, flightSheet.getPagination());
		}
	}

	/**
	 * @Description 保存飞行记录本
	 * @CreateTime 2017年10月24日 下午3:50:18
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doSave(FlightSheet record) throws BusinessException {
		
		// 保存飞行记录本-主表
		saveFLB(record);
		
		// 保存飞行记录本-飞行数据
		flightSheetVoyageService.save(record);
		
		// 保存飞行记录本-航段数据
		flightSheetLegService.save(record);
		
		// 保存飞行记录本-附件
		saveAttachments(record);
		
		return record.getId();
	}
	
	/**
	 * @Description 保存飞行记录本-主表
	 * @CreateTime 2017年10月25日 上午10:30:50
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @throws BusinessException
	 */
	private String saveFLB(FlightSheet record) throws BusinessException {
		
		// 验证飞行记录本
		validate(record);
		
		User user = ThreadVarUtil.getUser();
		record.setXdrid(user.getId());
		record.setXdsj(new Date());
		record.setXddwid(user.getBmdm());
		record.setIsXdtx(WhetherEnum.NO.getId());
		//操作流水
		if(StringUtils.isBlank(record.getCzls())){
			record.setCzls(UUID.randomUUID().toString());
		}
		
		if (StringUtils.isBlank(record.getId())){	// 新增飞行记录本
			record.setId(UUID.randomUUID().toString());
			// 生成飞行记录单号
			record.setFxjlbh(generateFxjlbh(record));
			record.setZt(FlbStatusEnum.SAVE.getId());
			record.setDprtcode(user.getJgdm());
			record.setZdrid(user.getId());
			record.setZdsj(new Date());
			record.setZddwid(user.getBmdm());
			if(record.getLogOperationEnum() == null){
				record.setLogOperationEnum(LogOperationEnum.SAVE_WO);
			}
			flightSheetMapper.insertSelective(record);
			// 保存历史记录信息
			commonRecService.write(record.getId(), TableEnum.B_S2_006, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getId());
			
		} else {	// 修改飞行记录本
			if(record.getLogOperationEnum() == null){
				record.setLogOperationEnum(LogOperationEnum.EDIT);
			}
			flightSheetMapper.updateByPrimaryKeySelective(record);
			// 保存历史记录信息
			commonRecService.write(record.getId(), TableEnum.B_S2_006, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.UPDATE, record.getId());
		}
		
		return record.getId();
	}
	
	/**
	 * @Description 生成飞行记录编号
	 * @CreateTime 2017年10月25日 上午10:41:27
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	private String generateFxjlbh(FlightSheet record){
		String fjzch = record.getFjzch();
		String last4 = fjzch.substring(fjzch.length() - 4 <= 0 ? 0 : fjzch.length()-4, fjzch.length());
		User user = ThreadVarUtil.getUser();
		try {
			return saibongUtilService.generate(user.getJgdm(), SaiBongEnum.FXJL.getName(), last4);
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @Description 验证飞行记录本
	 * @CreateTime 2017年10月25日 上午10:32:26
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	private void validate(FlightSheet record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 验证是否有飞机权限
		List<String> list = new ArrayList<String>();
		list.add(record.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), 
				user.getUserType(), record.getDprtcode(), list);
		
		// 验证记录本页码唯一
		if(flightSheetMapper.getJlbymCount(record) > 0){
			throw new BusinessException("页码为"+record.getJlbym()+"的飞行记录本已存在！");
		}
		
//		// 验证飞行日期比以前的大
//		if(StringUtils.isBlank(record.getId()) && flightSheetMapper.getFxrqCount(record) > 0){
//			throw new BusinessException("系统存在此日期之后的飞行数据，不能创建"+Utils.DT.formatDate(record.getFxrq())+"的飞行数据！");
//		}
//		
//		// 验证系统存在开车时间之后的飞行数据
//		if(StringUtils.isBlank(record.getId()) 
//				&& !record.getFlightSheetVoyageList().isEmpty() 
//				&& flightSheetMapper.getFxsjCount(record) > 0){
//			throw new BusinessException("系统存在开车时间之后的飞行数据！");
//		}
	}
	
	/**
	 * @Description 保存附件
	 * @CreateTime 2017年10月25日 上午11:50:53
	 * @CreateBy 韩武
	 * @param record
	 */
	private void saveAttachments(FlightSheet record){
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), 
				record.getCzls(), record.getId(), record.getDprtcode(), record.getLogOperationEnum());
	}

	/**
	 * @Description 查询飞行记录本详情
	 * @CreateTime 2017年10月26日 下午5:07:04
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	@Override
	public FlightSheet queryDetail(FlightSheet flightSheet) {
		
		FlightSheet detail = flightSheetMapper.queryDetail(flightSheet);
		
		// 查询航次数据
		loadFlightData(detail);;
		
		// 加载飞行前数据
		loadPreflightData(detail);
		
		return detail;
	}
	
	/**
	 * @Description 查询飞行记录本详情（上一页、下一页）
	 * @CreateTime 2017年12月5日 下午4:19:36
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	@Override
	public FlightSheet queryDetailWithPage(FlightSheet flightSheet) {
		
		FlightSheet detail = queryDetail(flightSheet);
		
		FlightSheet page = flightSheetMapper.queryClosePage(detail);
		detail.setNextPage(page == null ? "" : page.getNextPage());
		detail.setLastPage(page == null ? "" : page.getLastPage());
		
		return detail;
	}
	
	/**
	 * @Description 加载飞行前数据
	 * @CreateTime 2017年10月30日 上午9:38:02
	 * @CreateBy 韩武
	 * @param flightSheet
	 */
	private void loadFlightData(FlightSheet flightSheet){
		flightSheet.setFlightSheetVoyageList(flightSheetVoyageMapper.queryByMainid(flightSheet.getId()));
	}
	
	/**
	 * @Description 加载飞行前数据
	 * @CreateTime 2017年10月28日 下午12:51:16
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FlightSheet loadPreflightData(FlightSheet flightSheet){
		
		// 初始化航次
		initFlightSheetVoyage(flightSheet);
		// 循环航次
		for (FlightSheetVoyage voyage : flightSheet.getFlightSheetVoyageList()) {
			
			// 查询航次对应的部件累积值
			if(voyage.getKcsj() != null){
				// 获取飞行记录本累计值
				List<ComponentUse> list1 = componentUseMapper.selectByFjzch(flightSheet.getDprtcode(), flightSheet.getFjzch(), voyage.getKcsj());
				// 获取飞机一级部件
				List<InstallationListEffective> list2 = installationListEffectiveMapper.queryLevelOnePart(flightSheet.getDprtcode(), flightSheet.getFjzch(), voyage.getKcsj());
				// 复制集合
				List<ComponentUse> preflightData = ObjUtil.deepClone(list1, List.class);
				List<InstallationListEffective> parts = ObjUtil.deepClone(list2, List.class);
				// 填充飞行前数据的部件信息
				fillPartInfo(preflightData, parts);
				// 累积值加上初始值(飞行循环和飞行小时)
				List<String> zjqdidList = new ArrayList<String>();
				for (ComponentUse componentUse : preflightData) {
					if(StringUtils.isNotBlank(componentUse.getZjqdid())){
						zjqdidList.add(componentUse.getZjqdid());
					}
				}
				// 获取飞机初始值
				List<AircraftinfoStatus> aircraftinfoStatusList = aircraftinfoStatusMapper.queryList4FlightBook(flightSheet.getDprtcode(), flightSheet.getFjzch());
				// 获取部件初始值
				List<InstallationListEffective2Init> initList = null;
				if(zjqdidList.size() > 0){
					initList = installationListEffective2InitMapper.queryByMainidList(zjqdidList);
				}
				for (ComponentUse componentUse : preflightData) {
					formatComponentUse(componentUse, aircraftinfoStatusList, initList);
				}
				// 如果累计值不存在，则直接添加初始值
				if(preflightData.size() == 0){
					addToPreflightData(preflightData, aircraftinfoStatusList);
				}
				
				if(StringUtils.isBlank(voyage.getId())){
					voyage.setKcsj(null);
				}
				
				voyage.setPreflightData(preflightData);
			}
		}
		return flightSheet;
	}
	
	/**
	 * @Description 填充飞行前数据的部件信息
	 * @CreateTime 2017年11月30日 上午10:34:38
	 * @CreateBy 韩武
	 * @param preflightData
	 * @param parts
	 */
	private void fillPartInfo(List<ComponentUse> preflightData, List<InstallationListEffective> parts){
		
		InstallationListEffective body = new InstallationListEffective();
		body.setWz(InstalledPositionEnum.BODY.getId());
		parts.add(body);
		for (InstallationListEffective part : parts) {
			boolean hasPre = false;
			for (ComponentUse pre : preflightData) {
				if(part.getWz().equals(pre.getWz())){
					pre.setBjh(part.getBjh());
					pre.setXlh(part.getXlh());
					pre.setZjqdid(part.getId());
					hasPre = true;
				}
			}
			if(!hasPre){
				ComponentUse pre = new ComponentUse();
				pre.setBjh(part.getBjh());
				pre.setXlh(part.getXlh());
				pre.setZjqdid(part.getId());
				pre.setWz(part.getWz());
				pre.setSj(0);
				pre.setXh(0);
				preflightData.add(pre);
			}
		}
	}
	
	/**
	 * @Description 初始化初始值
	 * @CreateTime 2017-11-25 下午4:00:30
	 * @CreateBy 刘兵
	 * @param componentUse 部件使用量
	 * @param aircraftinfoStatusList 飞机初始值
	 */
	private void addToPreflightData(List<ComponentUse> preflightData, List<AircraftinfoStatus> aircraftinfoStatusList){
		//飞行时间和飞行循环初始值
		if(null != aircraftinfoStatusList && aircraftinfoStatusList.size() > 0){
			ComponentUse use = new ComponentUse();
			use.setWz(InstalledPositionEnum.BODY.getId());
			for (AircraftinfoStatus aircraftinfoStatus : aircraftinfoStatusList) {
				if(MonitorProjectEnum.FH.getCode().equals(aircraftinfoStatus.getJklbh())){
					use.setSj(aircraftinfoStatus.getCsz());
				}
				if(MonitorProjectEnum.FC.getCode().equals(aircraftinfoStatus.getJklbh())){
					use.setXh(aircraftinfoStatus.getCsz());
				}
			}
			preflightData.add(use);
		}
	}
	
	/**
	 * @Description 累积值加上初始值
	 * @CreateTime 2017-11-25 下午4:00:30
	 * @CreateBy 刘兵
	 * @param componentUse 部件使用量
	 * @param aircraftinfoStatusList 飞机初始值
	 * @param initList 部件初始值
	 */
	private void formatComponentUse(ComponentUse componentUse, List<AircraftinfoStatus> aircraftinfoStatusList, List<InstallationListEffective2Init> initList){
		if(componentUse.getWz().equals(InstalledPositionEnum.BODY.getId())){
			//飞行时间和飞行循环初始值
			for (AircraftinfoStatus aircraftinfoStatus : aircraftinfoStatusList) {
				if(MonitorProjectEnum.FH.getCode().equals(aircraftinfoStatus.getJklbh()) && componentUse.getWz().equals(aircraftinfoStatus.getWz())){
					componentUse.setSj((componentUse.getSj()==null?0:componentUse.getSj()) + aircraftinfoStatus.getCsz());
				}
				if(MonitorProjectEnum.FC.getCode().equals(aircraftinfoStatus.getJklbh()) && componentUse.getWz().equals(aircraftinfoStatus.getWz())){
					componentUse.setXh((componentUse.getXh()==null?0:componentUse.getXh()) + aircraftinfoStatus.getCsz());
				}
			}
		}else{
			//(发动机和APU)
			if(null != initList){
				for (InstallationListEffective2Init init : initList) {
					if(init.getMainid().equals(componentUse.getZjqdid())){
						componentUse.setBjh(String.valueOf(init.getParamsMap().get("bjh")));
						componentUse.setXlh(String.valueOf(init.getParamsMap().get("xlh")));
						if(componentUse.getWz().equals(InstalledPositionEnum.APU.getId())){
							if(MonitorProjectEnum.APUH.getCode().equals(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
								componentUse.setSj((componentUse.getSj()==null?0:componentUse.getSj()) + Integer.parseInt(init.getZssyy()));
							}else if(MonitorProjectEnum.APUC.getCode().equals(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
								componentUse.setXh((componentUse.getXh()==null?0:componentUse.getXh()) +  Integer.parseInt(init.getZssyy()));
							}
						}else{
							if(MonitorProjectEnum.EH.getCode().equals(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
								componentUse.setSj((componentUse.getSj()==null?0:componentUse.getSj()) + Integer.parseInt(init.getZssyy()));
							}else if(MonitorProjectEnum.EC.getCode().equals(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
								componentUse.setXh((componentUse.getXh()==null?0:componentUse.getXh()) +  Integer.parseInt(init.getZssyy()));
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * @Description 初始化航次
	 * @CreateTime 2017年10月28日 上午11:16:28
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @param preflightData
	 */
	private void initFlightSheetVoyage(FlightSheet flightSheet){
		
		if(flightSheet.getFlightSheetVoyageList() == null){
			flightSheet.setFlightSheetVoyageList(new ArrayList<FlightSheetVoyage>());
		}
		
		Date kcsj = null;
		for (int i = 1; i <= FLB_VOYAGE_MAX_COUNT; i++) {
			int hc = i * 2;
			boolean iscontain = false;
			for (FlightSheetVoyage t : flightSheet.getFlightSheetVoyageList()) {
				if(hc == t.getHc()){
					iscontain = true;
					
					// 如果开车时间为空，继承上个航次的开车时间
					if(t.getKcsj() != null){
						kcsj = t.getKcsj();
					}else{
						t.setKcsj(kcsj);
					}
				}
			}
			
			// 补满4个航次
			if(!iscontain){
				FlightSheetVoyage voyage = new FlightSheetVoyage();
				voyage.setHc(hc);
				voyage.setKcsj(kcsj != null ? kcsj : flightSheet.getKcsj());
				flightSheet.getFlightSheetVoyageList().add(voyage);
			}
		}
	}
	
	/**
	 * @Description 提交飞行记录本
	 * @CreateTime 2017年11月14日 上午10:09:49
	 * @CreateBy 徐勇
	 * @param flightSheet 飞行记录本
	 * @throws BusinessException 
	 */
	public void doSubmit(FlightSheet flightSheet) throws BusinessException{
		this.doSave(flightSheet);
		//更新飞行记录本状态到提交状态
		FlightSheet newFlightSheet = new FlightSheet();
		newFlightSheet.setId(flightSheet.getId());
		newFlightSheet.setZt(FlbStatusEnum.ASSESSMENT.getId());
		flightSheetMapper.updateByPrimaryKeySelective(newFlightSheet);
		
		// 保存历史记录信息
		commonRecService.write(flightSheet.getId(), TableEnum.B_S2_006, ThreadVarUtil.getUser().getId(), 
				UUID.randomUUID().toString() , LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE, flightSheet.getId());
		
		//修改该飞行记录本后面的记录本修订提醒标识
		this.flightSheetMapper.updateReviseFlag(flightSheet.getDprtcode(), flightSheet.getFjzch(), flightSheet.getKcsj(), flightSheet.getJlbym(), flightSheet.getId());
		
		List<String> workOrderIds = new ArrayList<String>();//flb涉及的所有工单
		List<Workorder> addWOList = new ArrayList<Workorder>();//待新增工单
		List<Workorder> editWOList = new ArrayList<Workorder>();//待修改工单
		List<WorkOrderIORecord> addIOList = new ArrayList<WorkOrderIORecord>();//待新增工单拆装记录
		
		//同步工作到工单
		for (FlightSheetLeg leg : flightSheet.getLegs()){
			for (FlightSheetFinishedWork work : leg.getFinishedWorks()) {
				if(work.getSgbs() == WhetherEnum.YES.getId()){
					//手工完成的任务，创建工单并同步拆下装上信息到工单
					this.buildAddWorkOrder(work, flightSheet.getFjzch(), flightSheet.getDprtcode(), addWOList, addIOList);
				}else{
					this.builderUpdateWorkOrder(work, editWOList, addIOList);
				}
				workOrderIds.add(work.getGdid());
			}
		}
		
		this.workOrderNewService.doWOUpdate4FLB(null, null, addWOList, editWOList, addIOList, null, null, null, workOrderIds);
		
		Date minKcsj = null;//飞行记录本最小开车时间
		Date maxKcsj = null;//飞行记录本最大开车时间
		for (FlightSheetVoyage flightSheetVoyage: flightSheet.getFlightSheetVoyageList()){
			if(flightSheetVoyage.getKcsj() != null){
				if(minKcsj == null){
					 minKcsj = flightSheetVoyage.getKcsj();
					 maxKcsj = flightSheetVoyage.getKcsj();	 
				}else{
					maxKcsj = flightSheetVoyage.getKcsj();
				}
			}
		}
		if(minKcsj == null){
			//当最小开车时间为空时，表示本飞行记录本没有航次数据，后续业务无需处理
			return;
		}
		
		//更新部件累计值
		this.updateComponentUsage(flightSheet.getDprtcode(), flightSheet.getFjzch(), minKcsj, maxKcsj);
		//更新已执行任务实际值
		this.monitorDataService.updateTask4FLB(flightSheet.getDprtcode(), flightSheet.getFjzch(), minKcsj);
		
	}
	
	/**
	 * @Description 飞行记录本非flb工单 构建工单修改和新增的拆装记录
	 * @CreateTime 2017年12月12日 下午3:42:32
	 * @CreateBy 徐勇
	 * @param work 飞行记录本完成工作
	 * @param editWOList 待编辑工单
	 * @param addIOList 待新增拆装记录
	 */
	private void builderUpdateWorkOrder(FlightSheetFinishedWork work, List<Workorder> editWOList, List<WorkOrderIORecord> addIOList){
		builderUpdateWorkOrder(work, editWOList);
		
		//非手工完完成的任务 同步手工标识 的装上拆下件
		for (FlightSheetDisassemblyRecord disassembly : work.getDisassemblies()){
			if(disassembly.getSgbs() == WhetherEnum.NO.getId()){
				//当装上拆下件手工标识为否，不做任务操作
				continue;
			}
			//装上拆下信息同步到工单
			WorkOrderIORecord workOrderIORecord = disassembly.toWorkOrderIORecord();
			workOrderIORecord.setMainid(work.getGdid());
			addIOList.add(workOrderIORecord);
		}
	}
	
	/**
	 * @Description 飞行记录本非flb工单 构建工单修改和新增的拆装记录
	 * @CreateTime 2017年12月12日 下午3:42:32
	 * @CreateBy 徐勇
	 * @param work 飞行记录本完成工作
	 * @param editWOList 待编辑工单
	 */
	private void builderUpdateWorkOrder(FlightSheetFinishedWork work, List<Workorder> editWOList){
		User user = ThreadVarUtil.getUser();
		Workorder workorder = new Workorder();
		workorder.setId(work.getGdid());
		workorder.setWhdwid(user.getBmdm());
		workorder.setWhrid(user.getId());
		workorder.setGbrid(user.getId());
		workorder.setZdbmid(user.getBmdm());
		workorder.setZdrid(user.getId());
		workorder.setGbid(work.getGbid());
		workorder.setGdbh(work.getGdbh());
		workorder.setGdbt(work.getGznr());
		workorder.setSjJsrq(work.getWcrq());
		workorder.setSjGzz(work.getGzz());
		workorder.setSjJcz(work.getZrr());
		workorder.setSjGs(work.getSjgs());
		workorder.setSjZd(work.getSjZd());
		workorder.setYbgs(work.getGzbg());
		workorder.setGzxx(work.getGzxx());
		workorder.setClcs(work.getClcs());
		workorder.setSjJczid(work.getZrrid());
		workorder.setHsgs(work.getHsgs());
		List<WorkOrderWorker> workers = new ArrayList<WorkOrderWorker>();
		if(work.getWorkers() != null && !work.getWorkers().isEmpty()){
			for (FlightSheetWorker temp : work.getWorkers()) {
				WorkOrderWorker worker = new WorkOrderWorker();
				worker.setGzz(temp.getGzz());
				worker.setGzzid(temp.getGzzid());
				workers.add(worker);
			}
		}
		workorder.setWorkers(workers);
		
		editWOList.add(workorder);
		
	}
	
	/**
	 * @Description 飞行记录本手工任务 构建工单和拆装记录
	 * @CreateTime 2017年12月12日 下午3:23:18
	 * @CreateBy 徐勇
	 * @param work 飞行记录本手工完成工作
	 * @param fjzch 飞机注册号
	 * @param dprtcode 组织机构
	 * @param addWOList 待添加工单
	 * @param addIOList 待添加拆装记录
	 */
	private void buildAddWorkOrder(FlightSheetFinishedWork work, String fjzch, String dprtcode, List<Workorder> addWOList, List<WorkOrderIORecord> addIOList){
		User user = ThreadVarUtil.getUser();
		Workorder workorder = new Workorder();
		workorder.setId(work.getGdid());
		workorder.setGdsbid(work.getGdid());
		workorder.setDprtcode(dprtcode);
		workorder.setWhdwid(user.getBmdm());
		workorder.setWhrid(user.getId());
		workorder.setGbrid(user.getId());
		workorder.setZdbmid(user.getBmdm());
		workorder.setZdrid(user.getId());
		workorder.setZt(WorkorderStatusEnum.CLOSETOFINISH.getId());
		workorder.setWgbs(WhetherEnum.YES.getId());
		workorder.setGbid(work.getGbid());
		workorder.setGdbh(work.getGdbh());
		workorder.setGdlx(WorkorderTypeEnum.FLB.getId());
		workorder.setGdbt(work.getGznr());
		workorder.setFjzch(fjzch);
		workorder.setSjJsrq(work.getWcrq());
		workorder.setSjGzz(work.getGzz());
		workorder.setSjJcz(work.getZrr());
		workorder.setSjGs(work.getSjgs());
		workorder.setSjZd(work.getSjZd());
		workorder.setYbgs(work.getGzbg());
		workorder.setGzxx(work.getGzxx());
		workorder.setClcs(work.getClcs());
		workorder.setSjJczid(work.getZrrid());
		workorder.setHsgs(work.getHsgs());
		List<WorkOrderWorker> workers = new ArrayList<WorkOrderWorker>();
		if(work.getWorkers() != null && !work.getWorkers().isEmpty()){
			for (FlightSheetWorker temp : work.getWorkers()) {
				WorkOrderWorker worker = new WorkOrderWorker();
				worker.setGzz(temp.getGzz());
				worker.setGzzid(temp.getGzzid());
				workers.add(worker);
			}
		}
		workorder.setWorkers(workers);
		
		addWOList.add(workorder);
		
		for (FlightSheetDisassemblyRecord disassembly : work.getDisassemblies()){
			WorkOrderIORecord record = disassembly.toWorkOrderIORecord();
			record.setMainid(workorder.getId());
			record.setWhdwid(user.getBmdm());
			record.setWhrid(user.getId());
			addIOList.add(record);
		}
	}
	
	/**
	 * @Description 更新部件使用汇总，更新飞机状态数据中累计值
	 * @CreateTime 2017年11月14日 下午3:49:53
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号 
	 * @param minKcsj 最小开车时间
	 * @param maxKcsj 最大开车时间
	 */
	private void updateComponentUsage(String dprtcode, String fjzch, Date minKcsj, Date maxKcsj){
		
		//查询飞行记录本记录期间在机件的累计值
		List<ComponentUse> list = this.componentUseMapper.selectInstalledCompnentUsageList(dprtcode, fjzch, minKcsj, maxKcsj);
		List<ComponentUseCount> useCountList = new ArrayList<ComponentUseCount>();
		ComponentUseCount componentUseCount = null;
		MonitorProjectEnum[] monitorProjectEnum = MonitorProjectEnum.values();
		for (ComponentUse componentUse : list) {
			for (MonitorProjectEnum enumItem : monitorProjectEnum) {
				//日历不需要存
				if(MonitorProjectEnum.isCalendar(enumItem.getCode())){
					continue;
				}
				componentUseCount = new ComponentUseCount();
				componentUseCount.setZjqdid(componentUse.getZjqdid());
				componentUseCount.setJklbh(enumItem.getCode());
				componentUseCount.setSjFz(componentUse.getByJklbh(enumItem.getCode(), componentUse.getWz()));
				useCountList.add(componentUseCount);
			}
		}
		
		//批量修改部件使用汇总
		for (int i = 0; i <= useCountList.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < useCountList.size()){
				this.componentUseCountMapper.updateSJ4Batch(useCountList.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > useCountList.size() ? useCountList.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
		
		//更新 飞机状态数据 累计值
		//从部件使用汇总中更新非机身的
		this.aircraftinfoStatusMapper.updateLJFromComponentUseCount(dprtcode, fjzch);
		//更新 机身的 累计值
		this.aircraftinfoStatusMapper.updateBodyLJ(dprtcode, fjzch);
	}
	

	/**
	 * @Description 删除飞行记录本数据
	 * @CreateTime 2017年10月28日 下午4:51:07
	 * @CreateBy 韩武
	 * @param flightSheet
	 */
	@Override
	public void doDelete(FlightSheet flightSheet) throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
//		flightSheet.setXdrid(user.getId());
//		flightSheet.setXdsj(new Date());
//		flightSheet.setXddwid(user.getBmdm());
//		flightSheet.setZt(FlbStatusEnum.CLOSE.getId());
//		flightSheetMapper.updateByPrimaryKeySelective(flightSheet);
		
		// 保存历史记录信息
		commonRecService.write(flightSheet.getId(), TableEnum.B_S2_006, user.getId(), 
				UUID.randomUUID().toString() , LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, flightSheet.getId());
		
		flightSheetMapper.deleteByPrimaryKey(flightSheet.getId());
	}
	
	/**
	 * @Description 修订飞行记录本
	 * @CreateTime 2017年11月22日 上午11:00:03
	 * @CreateBy 徐勇
	 * @param flightSheet 飞行记录本数据
	 * @throws BusinessException 
	 */
	public void doRevise(FlightSheet flightSheet) throws BusinessException{
		
		if(StringUtils.isBlank(flightSheet.getId())){
			throw new BusinessException("数据提交异常");
		}
		FlightSheet oldflightSheet = new FlightSheet();
		oldflightSheet.setId(flightSheet.getId());
		oldflightSheet = this.queryDetail(flightSheet);
		if(oldflightSheet == null){
			throw new BusinessException("飞行记录本不存在");
		}
		//状态为提交或修订时才能作废
		if(oldflightSheet.getZt() != FlbStatusEnum.ASSESSMENT.getId() && oldflightSheet.getZt() != FlbStatusEnum.REVISED.getId()){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		
		flightSheet.setZt(FlbStatusEnum.REVISED.getId());
		flightSheet.setLogOperationEnum(LogOperationEnum.REVISE);
		//保存飞行记录本主表数据
		this.saveFLB(flightSheet);
		//保存航次数据
		flightSheetVoyageService.save(flightSheet);
		
		// 保存飞行记录本-航段数据
		flightSheetLegService.save(flightSheet);
		
		//处理完成工作的修订
		this.doReviseWork(flightSheet, oldflightSheet);
		
		
		// 保存飞行记录本-附件
		saveAttachments(flightSheet);
		
		//修改该飞行记录本后面的记录本修订提醒标识
		this.flightSheetMapper.updateReviseFlag(flightSheet.getDprtcode(), flightSheet.getFjzch(), flightSheet.getKcsj().compareTo(oldflightSheet.getKcsj())<0?flightSheet.getKcsj():oldflightSheet.getKcsj(), flightSheet.getJlbym(), flightSheet.getId());
				
		//更新部件累计值、已执行任务实际值
		Date oldMinKcsj = null;//原飞行记录本最小开车时间
		Date oldMaxKcsj = null;//原飞行记录本最大开车时间
		for (FlightSheetVoyage flightSheetVoyage: oldflightSheet.getFlightSheetVoyageList()){
			if(flightSheetVoyage.getKcsj() != null){
				if(oldMinKcsj == null){
					oldMinKcsj = flightSheetVoyage.getKcsj();
					oldMaxKcsj = flightSheetVoyage.getKcsj();	 
				}else{
					oldMaxKcsj = flightSheetVoyage.getKcsj();
				}
			}
		}
		Date newMinKcsj = null;//修订后飞行记录本最小开车时间
		Date newMaxKcsj = null;//修订后飞行记录本最大开车时间
		for (FlightSheetVoyage flightSheetVoyage: flightSheet.getFlightSheetVoyageList()){
			if(flightSheetVoyage.getKcsj() != null){
				if(newMinKcsj == null){
					newMinKcsj = flightSheetVoyage.getKcsj();
					newMaxKcsj = flightSheetVoyage.getKcsj();	 
				}else{
					newMaxKcsj = flightSheetVoyage.getKcsj();
				}
			}
		}
		//最小开车时间取两者较小，最大开车时间取两者较大
		if(newMinKcsj == null){
			newMinKcsj = oldMinKcsj;
		}
		if(newMinKcsj == null){
			//当最小开车时间为空时，表示本飞行记录本没有航次数据，后续业务无需处理
			return;
		}
		if(oldMinKcsj != null){
			newMinKcsj = newMinKcsj.compareTo(oldMinKcsj)<0?newMinKcsj:oldMinKcsj;
		}
		
		if(newMaxKcsj == null){
			newMaxKcsj = oldMaxKcsj;
		}
		if(oldMaxKcsj != null){
			newMaxKcsj = newMaxKcsj.compareTo(oldMaxKcsj)<0?oldMaxKcsj:newMaxKcsj;
		}
		//更新部件累计值
		this.updateComponentUsage(flightSheet.getDprtcode(), flightSheet.getFjzch(), newMinKcsj, newMaxKcsj);
		//更新已执行任务实际值
		this.monitorDataService.updateTask4FLB(flightSheet.getDprtcode(), flightSheet.getFjzch(), newMinKcsj);
	}
	
	/**
	 * @Description 处理修订工作
	 * @CreateTime 2017年11月24日 下午1:45:56
	 * @CreateBy 徐勇
	 * @param flightSheet
	 * @param oldFlightSheet
	 * @throws BusinessException 
	 */
	private void doReviseWork(FlightSheet flightSheet, FlightSheet oldFlightSheet) throws BusinessException{
		List<FlightSheetLeg> legs = flightSheet.getLegs();
		List<FlightSheetLeg> oldLegs = oldFlightSheet.getLegs();
		
		List<String> flbWorkOrderUndoIds = new ArrayList<String>();//待撤销的flb工单
		List<String> workOrderIOUndoIds = new ArrayList<String>();//待撤销的拆装记录
		List<String> workOrderIds = new ArrayList<String>();//flb涉及的所有工单
		List<Workorder> addWOList = new ArrayList<Workorder>();//待新增工单
		List<Workorder> editWOList = new ArrayList<Workorder>();//待修改工单
		List<WorkOrderIORecord> addIOList = new ArrayList<WorkOrderIORecord>();//待新增工单拆装记录
		List<WorkOrderIORecord> editIOList = new ArrayList<WorkOrderIORecord>();//待修改工单拆装记录
		List<InstallAndRemove> installedRemovedUndoList = new ArrayList<InstallAndRemove>(); 
		List<InstallAndRemove> installedRemovedList = new ArrayList<InstallAndRemove>(); 
		
		//在原航段数据中比对，处理被删除的航段
		for (FlightSheetLeg oldLeg : oldLegs) {
			//当原航段在新航段数据中不存在，表示该航段不存在了，原航段数据需要全部撤销
			if(!isExistsLeg(oldLeg.getId(), legs)){
				//撤销航段中的工作数据
				for (FlightSheetFinishedWork work : oldLeg.getFinishedWorks()) {
					if(work.getSgbs() == WhetherEnum.YES.getId()){
						flbWorkOrderUndoIds.add(work.getGdid());
					}
					for (FlightSheetDisassemblyRecord disassembly : work.getDisassemblies()){
						if(disassembly.getSgbs() == WhetherEnum.YES.getId()){
							workOrderIOUndoIds.add(disassembly.getGdczjlid());
						}
					}
					workOrderIds.add(work.getGdid());
				}
			}
		}
		//在新航段中与原航段比对，处理新增/修改的航段
		for (FlightSheetLeg leg : legs) {
			FlightSheetLeg oldLeg = getExistsLeg(leg.getId(), oldLegs);
			//当航段不存在于旧数据，则航段工作全是新增
			if(oldLeg == null){
				//处理航段工作同步到工单
				//新增该航段数据
				for (FlightSheetFinishedWork work : leg.getFinishedWorks()) {
					if(work.getSgbs() == WhetherEnum.YES.getId()){
						//手工完成的任务，创建工单并同步拆下装上信息到工单
						this.buildAddWorkOrder(work, flightSheet.getFjzch(), flightSheet.getDprtcode(), addWOList, addIOList);
					}else{
						this.builderUpdateWorkOrder(work, editWOList, addIOList);
					}
					workOrderIds.add(work.getGdid());
				}
			}else{
				//撤销 旧完成工作被 删除的数据
				for (FlightSheetFinishedWork work : oldLeg.getFinishedWorks()) {
					if(!isExistsWork(work.getId(), leg.getFinishedWorks())){//旧的完成工作不在新航段的完成工作中存在，执行撤销
						if(work.getSgbs() == WhetherEnum.YES.getId()){
							flbWorkOrderUndoIds.add(work.getGdid());
						}
						for (FlightSheetDisassemblyRecord disassembly : work.getDisassemblies()){
							if(disassembly.getSgbs() == WhetherEnum.YES.getId()){
								workOrderIOUndoIds.add(disassembly.getGdczjlid());
							}
						}
						workOrderIds.add(work.getGdid());
					}
				}
				//新的工作集合，处理新增和修改部分
				for (FlightSheetFinishedWork work : leg.getFinishedWorks()) {
					FlightSheetFinishedWork oldWork = this.getExistsWork(work.getId(), oldLeg.getFinishedWorks());
					if(oldWork == null){//旧工作中不存在，表示该工作是新增的
						if(work.getSgbs() == WhetherEnum.YES.getId()){
							//手工完成的任务，创建工单并同步拆下装上信息到工单
							this.buildAddWorkOrder(work, flightSheet.getFjzch(), flightSheet.getDprtcode(), addWOList, addIOList);
						}else{
							this.builderUpdateWorkOrder(work, editWOList, addIOList);
						}
						workOrderIds.add(work.getGdid());
					}else{//旧工作存在，进行工作修订
						
						workOrderIds.add(work.getGdid());
						
						//比对出新增、修改、删除了的数据传递给工单进行修订，只涉及到手工
						for (FlightSheetDisassemblyRecord rec : oldWork.getDisassemblies()) {
							//删除了的手工拆装记录
							if(rec.getSgbs() == WhetherEnum.YES.getId() && !isExistsIORecord(rec.getId(), work.getDisassemblies())){
								workOrderIOUndoIds.add(rec.getGdczjlid());
							}
						}
						for (FlightSheetDisassemblyRecord rec : work.getDisassemblies()) {
							if(rec.getSgbs() == WhetherEnum.NO.getId()){
								//当装上拆下件手工标识为否，不做任务操作
								continue;
							}
							FlightSheetDisassemblyRecord oldRec = getExistsIORecord(rec.getId(), oldWork.getDisassemblies());
							if(oldRec == null){//新增的记录
								//装上拆下信息同步到工单
								WorkOrderIORecord workOrderIORecord = rec.toWorkOrderIORecord();
								workOrderIORecord.setMainid(work.getGdid());
								addIOList.add(workOrderIORecord);
							}else{
								//判断是否修改
								if(StringUtils.isNotBlank(oldRec.getCxZjqdid()) && !oldRec.getCxZjqdid().equals(rec.getCxZjqdid())){
									installedRemovedUndoList.add(new InstallAndRemove(oldRec.getCxZjqdid(), oldRec.getCxSj(), InstalledStatusEnum.REMOVED.getId(), oldRec.getGdczjlid(), oldRec.getCxWckcid(), oldRec.getCxKclvid()));
								}
								if(StringUtils.isNotBlank(rec.getCxZjqdid())){
									if(!rec.getCxZjqdid().equals(oldRec.getCxZjqdid())){
										installedRemovedList.add(new InstallAndRemove(rec.getCxZjqdid(), rec.getCxSj(), InstalledStatusEnum.REMOVED.getId(), rec.getGdczjlid(), rec.getCxWckcid(), rec.getCxKclvid()));
									}else{
										installedRemovedList.add(new InstallAndRemove(rec.getCxZjqdid(), rec.getCxSj(), InstalledStatusEnum.REMOVED.getId(), true, rec.getGdczjlid(), oldRec.getCxWckcid(), oldRec.getCxKclvid()));
									}
								}
								
								//装上是否有修改
								if(StringUtils.isNotBlank(oldRec.getZsZjqdid()) && !oldRec.getZsZjqdid().equals(rec.getZsZjqdid())){
									installedRemovedUndoList.add(new InstallAndRemove(oldRec.getZsZjqdid(), oldRec.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), oldRec.getGdczjlid(), oldRec.getZsWckcid(), oldRec.getZsKclvid()));
								}
								if(StringUtils.isNotBlank(rec.getZsZjqdid())){
									if(!rec.getZsZjqdid().equals(oldRec.getZsZjqdid())){
										installedRemovedList.add(new InstallAndRemove(rec.getZsZjqdid(), rec.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), rec.getGdczjlid(), rec.getZsWckcid(), rec.getZsKclvid()));
									}else{
										installedRemovedList.add(new InstallAndRemove(rec.getZsZjqdid(), rec.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), true, rec.getGdczjlid(), oldRec.getZsWckcid(), oldRec.getZsKclvid()));
									}
								}
								WorkOrderIORecord workOrderIORecord = rec.toWorkOrderIORecord();
								workOrderIORecord.setMainid(work.getGdid());
								editIOList.add(workOrderIORecord);
							}
						}
						this.builderUpdateWorkOrder(work, editWOList);
					}
				}
			}
		}
		this.workOrderNewService.doWOUpdate4FLB(flbWorkOrderUndoIds, workOrderIOUndoIds, addWOList, editWOList, addIOList, editIOList, installedRemovedUndoList, installedRemovedList, workOrderIds);
	}
	
	/**
	 * @Description 是否存在该拆装记录
	 * @CreateTime 2017年11月28日 下午2:13:30
	 * @CreateBy 徐勇
	 * @param id
	 * @param recordList
	 * @return
	 */
	private FlightSheetDisassemblyRecord getExistsIORecord(String id, List<FlightSheetDisassemblyRecord> recordList){
		for (FlightSheetDisassemblyRecord record : recordList) {
			if(id.equals(record.getId())){
				return record;
			}
		}
		return null;
	}
	
	/**
	 * @Description 是否存在该拆装记录
	 * @CreateTime 2017年11月28日 下午2:13:30
	 * @CreateBy 徐勇
	 * @param id
	 * @param recordList
	 * @return
	 */
	private boolean isExistsIORecord(String id, List<FlightSheetDisassemblyRecord> recordList){
		for (FlightSheetDisassemblyRecord record : recordList) {
			if(id.equals(record.getId())){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @Description 判断 list中是否存在某一个完成工作
	 * @CreateTime 2017年12月1日 上午9:43:31
	 * @CreateBy 徐勇
	 * @param id 工作id
	 * @param works 工作集合
	 * @return
	 */
	private boolean isExistsWork(String id, List<FlightSheetFinishedWork> works){
		for (FlightSheetFinishedWork work : works) {
			if(id.equals(work.getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Description 获取 list中是否存在的某一个完成工作
	 * @CreateTime 2017年12月1日 上午9:43:31
	 * @CreateBy 徐勇
	 * @param id 工作id
	 * @param works 工作集合
	 * @return
	 */
	private FlightSheetFinishedWork getExistsWork(String id, List<FlightSheetFinishedWork> works){
		for (FlightSheetFinishedWork work : works) {
			if(id.equals(work.getId())){
				return work;
			}
		}
		return null;
	}
	
	/**
	 * @Description 判断航段列表中是否存在某航段
	 * @CreateTime 2017年11月24日 下午2:16:44
	 * @CreateBy 徐勇
	 * @param id 航段id
	 * @param legs 航段list
	 */
	private boolean isExistsLeg(String id, List<FlightSheetLeg> legs){
		for (FlightSheetLeg flightSheetLeg : legs) {
			if(id.equals(flightSheetLeg.getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Description 取出航段列表中存在的某航段
	 * @CreateTime 2017年11月24日 下午2:16:44
	 * @CreateBy 徐勇
	 * @param id 航段id
	 * @param legs 航段list
	 */
	private FlightSheetLeg getExistsLeg(String id, List<FlightSheetLeg> legs){
		for (FlightSheetLeg flightSheetLeg : legs) {
			if(id.equals(flightSheetLeg.getId())){
				return flightSheetLeg;
			}
		}
		return null;
	}
	
	
	
	/**
	 * @Description 作废飞行记录本
	 * @CreateTime 2017年11月15日 下午1:44:29
	 * @CreateBy 徐勇
	 * @param id 飞行记录本ID
	 * @throws BusinessException 
	 */
	public void doDiscard(String id) throws BusinessException{
		
		FlightSheet flightSheet = new FlightSheet();
		flightSheet.setId(id);
		flightSheet = this.queryDetail(flightSheet);
		if(flightSheet == null){
			throw new BusinessException("飞行记录本不存在");
		}
		//状态为提交或修订时才能作废
		if(flightSheet.getZt() != FlbStatusEnum.ASSESSMENT.getId() && flightSheet.getZt() != FlbStatusEnum.REVISED.getId()){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		
		//更新飞行记录本状态到作废状态
		FlightSheet newFlightSheet = new FlightSheet();
		newFlightSheet.setId(flightSheet.getId());
		newFlightSheet.setZt(FlbStatusEnum.CLOSE.getId());
		flightSheetMapper.updateByPrimaryKeySelective(newFlightSheet);
		
		// 保存历史记录信息
		commonRecService.write(flightSheet.getId(), TableEnum.B_S2_006, ThreadVarUtil.getUser().getId(), 
				UUID.randomUUID().toString() , LogOperationEnum.ZUOFEI, UpdateTypeEnum.SAVE, flightSheet.getId());
		
		//修改该飞行记录本后面的记录本修订提醒标识
		this.flightSheetMapper.updateReviseFlag(flightSheet.getDprtcode(), flightSheet.getFjzch(), flightSheet.getKcsj(), flightSheet.getJlbym(), flightSheet.getId());
		
		//处理完成工作的撤回
		List<String> flbWorkOrderUndoIds = new ArrayList<String>();//待撤销的flb工单
		List<String> workOrderIOUndoIds = new ArrayList<String>();//待撤销的拆装记录
		List<String> workOrderIds = new ArrayList<String>();//flb涉及的所有工单
		for (FlightSheetLeg oldLeg : flightSheet.getLegs()) {
			//撤销航段中的工作数据
			for (FlightSheetFinishedWork work : oldLeg.getFinishedWorks()) {
				if(work.getSgbs() == WhetherEnum.YES.getId()){
					flbWorkOrderUndoIds.add(work.getGdid());
				}
				for (FlightSheetDisassemblyRecord disassembly : work.getDisassemblies()){
					//当装上拆下件手工标识为是
					if(disassembly.getSgbs() == WhetherEnum.YES.getId()){
						workOrderIOUndoIds.add(disassembly.getGdczjlid());
					}
				}
				workOrderIds.add(work.getGdid());
			}
		}
		//查询flb涉及的所有工单
		List<Workorder> woList = this.workOrderNewService.queryByIds(workOrderIds);
		//执行工单和拆装记录的撤销操作
		this.workOrderNewService.doWOUndo4FLB(flbWorkOrderUndoIds, workOrderIOUndoIds, woList, null);
		
		Date minKcsj = null;//飞行记录本最小开车时间
		Date maxKcsj = null;//飞行记录本最大开车时间
		
		for(FlightSheetVoyage flightSheetVoyage: flightSheet.getFlightSheetVoyageList()){
			if(flightSheetVoyage.getKcsj() != null){
				if(minKcsj == null){
					 minKcsj = flightSheetVoyage.getKcsj();
					 maxKcsj = flightSheetVoyage.getKcsj();	 
				}else{
					maxKcsj = flightSheetVoyage.getKcsj();
				}
			}
		}
		if(minKcsj == null){
			//当最小开车时间为空时，表示本飞行记录本没有航次数据，后续业务无需处理
			return;
		}
		//更新部件累计值
		this.updateComponentUsage(flightSheet.getDprtcode(), flightSheet.getFjzch(), minKcsj, maxKcsj);
		//更新已执行任务实际值
		this.monitorDataService.updateTask4FLB(flightSheet.getDprtcode(), flightSheet.getFjzch(), minKcsj);
	}

	@Override
	public List<FlightSheet> getHbhFxrqFlightRecord(FlightSheet sheet) {
		return flightSheetMapper.getHbhFxrqFlightRecord(sheet);
	}

}