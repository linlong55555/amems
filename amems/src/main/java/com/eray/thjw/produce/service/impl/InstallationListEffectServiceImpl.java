package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
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

import com.eray.thjw.aerialmaterial.dao.ComponentHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.OutFieldStockMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.ComponentUseCountMapper;
import com.eray.thjw.produce.dao.ComponentUseMapper;
import com.eray.thjw.produce.dao.InstallationListEditable2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEditableMapper;
import com.eray.thjw.produce.dao.InstallationListEffective2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.dao.WorkOrderIORecordMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.ComponentUse;
import com.eray.thjw.produce.po.ComponentUseCount;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEditable2Init;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.InstallationListEffective2Init;
import com.eray.thjw.produce.service.AircraftStatusService;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.produce.service.InstallationListEffectService;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.service.EOApplicabilityService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.MaterialSecondTypeEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.MaterialEnum;
import enu.aerialmaterial.MaterialLevelEnum;
import enu.aerialmaterial.OutFieldStockSourceEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;
import enu.common.PartSnValidationEnum;
import enu.common.WhetherEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.InstalledStatusEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 装机清单生效service实现类
 * @CreateTime 2017年10月10日 下午4:16:55
 * @CreateBy 韩武
 */
@Service
public class InstallationListEffectServiceImpl implements InstallationListEffectService  {
	
	@Resource
	private AircraftinfoService aircraftinfoService;
	
	@Resource
	private InstallationListEditableMapper installationListEditableMapper;
	
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	
	@Resource
	private ComponentUseMapper componentUseMapper;
	
	@Resource
	private MonitorDataService monitorDataService;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private ComponentHistoryMapper componentHistoryMapper;
	
	@Resource
	private ComponentMapper componentMapper;
	
	@Resource
	private OutFieldStockMapper outFieldStockMapper;
	
	@Resource
	private MaterialRecService materialRecService;
	
	@Resource
	private InstallationListEffective2InitMapper installationListEffective2InitMapper;
	
	@Resource
	private ComponentUseCountMapper componentUseCountMapper;
	
	@Resource
	private AircraftStatusService aircraftStatusService;
	
	@Resource
	private OutFieldStockService outFieldStockService;
	
	@Resource
	private EOApplicabilityService eOApplicabilityService;
	
	@Resource
	private InstallationListEditable2InitMapper installationListEditable2InitMapper;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private WorkOrderIORecordMapper workOrderIORecordMapper;
	
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	/**
	 * @Description 装机清单生效
	 * @CreateTime 2017年10月10日 下午4:15:35
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void doEffect(Aircraftinfo aircraftinfo) throws BusinessException {
		
		// 装机清单生效验证
		Map<String,Object> map = validate(aircraftinfo);
		aircraftinfo = (Aircraftinfo) map.get("info");
		/* 装机清单生效逻辑 */
		//获取待同步数据及其部件初始化数据
		List<InstallationListEditable> toBeSyncList = (List<InstallationListEditable>) map.get("toBeSyncList");
		//获取待同步部件在机使用量
		List<ComponentUse> compnentUsageList = this.componentUseMapper.selectToBeSyncInstallCompnentUsageList(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch());
		Map<String, ComponentUse> compnentUsageMap = new HashMap<String, ComponentUse>(compnentUsageList.size());
		for (ComponentUse compnentUsage : compnentUsageList) {
			compnentUsageMap.put(compnentUsage.getZjqdid(), compnentUsage);
		}
		
		//更新已执行任务的实际值
		String batchNo = UUID.randomUUID().toString();
		//生成对部件的初始值及累计值，写入到临时表， 需要在编辑区同步到生效区前完成
		this.monitorDataService.addTemp4Component(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch(), batchNo);
		
		boolean isInstall = true;//是否装上
		boolean isNew = true;//是否新的装机清单数据
		boolean hasEngine = false;//是否有发动机同步
		boolean hasApu = false;//是否有APU同步
		//更新装机清单生效区数据
		for (InstallationListEditable installationListEditable : toBeSyncList) {
			//当生效区已存在该装机清单，则该记录不为新数据
			if(installationListEditable.getEffective() != null 
					&& StringUtils.isNotBlank(installationListEditable.getEffective().getId())){
				isNew = false; 
			}
			//是否装上件
			isInstall = installationListEditable.getZjzt() == InstalledStatusEnum.INSTALLED.getId();
			
			//处理装机清单编辑区数据生效逻辑
			if(isNew){
				//生效装机清单，并更新部件使用汇总
				this.addInstallEffective(installationListEditable, compnentUsageMap);
				//新的序列号装上件，更新其监控数据
				if(isInstall && StringUtils.isNotBlank(installationListEditable.getXlh())){//装上件生成新的监控数据
					this.eOApplicabilityService.addExecObject4Component(installationListEditable.getDprtcode(), aircraftinfo.getFjzch(), installationListEditable.getBjh(), installationListEditable.getXlh());
					this.monitorDataService.add4installComponent(installationListEditable.getDprtcode(), aircraftinfo.getFjjx(), aircraftinfo.getFjzch(), aircraftinfo.getXlh(), installationListEditable.getId(), installationListEditable.getBjh(), installationListEditable.getXlh(), this.getCalInit(installationListEditable));
				}
			}else{
				//生效装机清单，并更新部件使用汇总
				this.saveInstallEffective(installationListEditable, compnentUsageMap);
				if(!isInstall){//拆下件删除当前监控数据
					this.monitorDataService.removeByZjqdid(installationListEditable.getId());
				}
				
			}
			//当部件为一级关键部件更新飞机状态数据、更新对飞机的已执行任务实际值（写入临时表）
			if(installationListEditable.getCj() == 1 && installationListEditable.getWz() != InstalledPositionEnum.BODY.getId()){
				if(isInstall){
					ComponentUse componentUsage = compnentUsageMap.get(installationListEditable.getId());
					
					this.saveAircraftStatus(installationListEditable, componentUsage);
					
					if(installationListEditable.getWz() == InstalledPositionEnum.APU.getId()){
						//是否更新对飞机的含APU的已执行任务实际值（写入临时表）
						hasApu = true;
					}else{
						//是否更新对飞机的该发动机的已执行任务实际值（写入临时表）
						hasEngine = true;
					}
				}else{
					//根据装机清单删除
					this.aircraftStatusService.removeByZjqdid(installationListEditable.getId());
				}
			}
		}
		//删除装机清单编辑区拆下数据，修改装机清单编辑区数据同步标识为无需同步
		this.updateInstallEditable(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch());
		
		//更新对飞机的发动机/APU的已执行任务实际值（写入临时表）
		if(hasEngine){
			this.monitorDataService.addTemp4PlaneEngine(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch(), batchNo);
		}
		if(hasApu){
			this.monitorDataService.addTemp4PlaneAPU(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch(), batchNo);
		}
		//通过临时表更新已执行任务
		this.monitorDataService.updateFromTemp(batchNo);
		
	}
	
	/**
	 * @Description 保存飞机状态数据
	 * @CreateTime 2017年10月30日 下午2:45:48
	 * @CreateBy 徐勇
	 * @param installationListEditable
	 * @param compnentUsageMap
	 */
	private void saveAircraftStatus(InstallationListEditable installationListEditable, ComponentUse componentUsage){
		List<InstallationListEditable2Init> list = installationListEditable.getInitDatas();

		AircraftinfoStatus aircraftinfoStatus = null;
		
		List<AircraftinfoStatus> infoStatusList = new ArrayList<AircraftinfoStatus>(list.size());
		for (InstallationListEditable2Init init : list) {
			//日历不需要存
			if(MonitorProjectEnum.isCalendar(init.getJklbh())){
				continue;
			}
			if((InstalledPositionEnum.BODY.getId() == installationListEditable.getWz() && (MonitorProjectEnum.FH.getCode().equals(init.getJklbh()) || MonitorProjectEnum.FC.getCode().equals(init.getJklbh()))) 
					|| (InstalledPositionEnum.isEngine(installationListEditable.getWz()) && MonitorProjectEnum.isEngine(init.getJklbh()))
						|| (InstalledPositionEnum.APU.getId() == installationListEditable.getWz() && MonitorProjectEnum.isApu(init.getJklbh()))){
				aircraftinfoStatus = new AircraftinfoStatus();
				aircraftinfoStatus.setId(UUID.randomUUID().toString());
				aircraftinfoStatus.setFjzch(installationListEditable.getFjzch());
				aircraftinfoStatus.setDprtcode(installationListEditable.getDprtcode());
				aircraftinfoStatus.setWz(installationListEditable.getWz());
				aircraftinfoStatus.setZjqdid(installationListEditable.getId());
				aircraftinfoStatus.setJh(installationListEditable.getBjh());
				aircraftinfoStatus.setXlh(installationListEditable.getXlh());
				aircraftinfoStatus.setJklbh(init.getJklbh());
				aircraftinfoStatus.setJkflbh(init.getJkflbh());
				aircraftinfoStatus.setLjz(componentUsage==null?Integer.valueOf(0):componentUsage.getByJklbh(init.getJklbh(), installationListEditable.getWz()));
				aircraftinfoStatus.setCsz(Integer.valueOf(init.getCsz()==null?"0":init.getCsz()));
				infoStatusList.add(aircraftinfoStatus);
			}
		}
		this.aircraftStatusService.save4installComponent(infoStatusList);
	}
	
	/**
	 * @Description 保存飞机状态数据
	 * @CreateTime 2017年10月30日 下午2:45:48
	 * @CreateBy 徐勇
	 * @param installationListEditable
	 * @param compnentUsageMap
	 */
	private void saveAircraftStatus(InstallationListEffective installationListEffective, ComponentUse componentUsage){
		List<InstallationListEffective2Init> list = installationListEffective.getInitDatas();

		AircraftinfoStatus aircraftinfoStatus = null;
		
		List<AircraftinfoStatus> infoStatusList = new ArrayList<AircraftinfoStatus>(list.size());
		for (InstallationListEffective2Init init : list) {
			//日历不需要存
			if(MonitorProjectEnum.isCalendar(init.getJklbh())){
				continue;
			}
			if((InstalledPositionEnum.BODY.getId() == installationListEffective.getWz() && (MonitorProjectEnum.FH.getCode().equals(init.getJklbh()) || MonitorProjectEnum.FC.getCode().equals(init.getJklbh()))) 
					|| (InstalledPositionEnum.isEngine(installationListEffective.getWz()) && MonitorProjectEnum.isEngine(init.getJklbh()))
						|| (InstalledPositionEnum.APU.getId() == installationListEffective.getWz() && MonitorProjectEnum.isApu(init.getJklbh()))){
				aircraftinfoStatus = new AircraftinfoStatus();
				aircraftinfoStatus.setId(UUID.randomUUID().toString());
				aircraftinfoStatus.setFjzch(installationListEffective.getFjzch());
				aircraftinfoStatus.setDprtcode(installationListEffective.getDprtcode());
				aircraftinfoStatus.setWz(installationListEffective.getWz());
				aircraftinfoStatus.setZjqdid(installationListEffective.getId());
				aircraftinfoStatus.setJh(installationListEffective.getBjh());
				aircraftinfoStatus.setXlh(installationListEffective.getXlh());
				aircraftinfoStatus.setJklbh(init.getJklbh());
				aircraftinfoStatus.setJkflbh(init.getJkflbh());
				aircraftinfoStatus.setLjz(componentUsage==null?Integer.valueOf(0):componentUsage.getByJklbh(init.getJklbh(), installationListEffective.getWz()));
				aircraftinfoStatus.setCsz(Integer.valueOf(init.getZssyy()==null?"0":init.getZssyy()));
				infoStatusList.add(aircraftinfoStatus);
			}
		}
		this.aircraftStatusService.save4installComponent(infoStatusList);
	}
	
	/**
	 * @Description 获取装机清单日历 初始值
	 * @CreateTime 2017年10月25日 下午4:11:44
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单日历初始值
	 * @return
	 */
	private String getCalInit(InstallationListEditable installationListEditable){
		List<InstallationListEditable2Init> list = installationListEditable.getInitDatas();
		for (InstallationListEditable2Init installationListEditable2Init : list) {
			if(MonitorProjectEnum.isCalendar(installationListEditable2Init.getJklbh())){
				return installationListEditable2Init.getCsz();
			}
		}
		return null;
	}
	
	/**
	 * @Description 获取装机清单日历 初始值
	 * @CreateTime 2017年10月25日 下午4:11:44
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单日历初始值
	 * @return
	 */
	private String getCalInit(InstallationListEffective installationListEffective){
		List<InstallationListEffective2Init> list = installationListEffective.getInitDatas();
		for (InstallationListEffective2Init installationListEffective2Init : list) {
			if(MonitorProjectEnum.isCalendar(installationListEffective2Init.getJklbh())){
				return installationListEffective2Init.getZssyy();
			}
		}
		return null;
	}
	
	/**
	 * @Description 新增装机清单生效区数据
	 * @CreateTime 2017年10月19日 下午5:20:14
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单编辑区数据
	 * @param compnentUsageMap 部件在机使用量
	 */
	private void addInstallEffective(InstallationListEditable installationListEditable, Map<String, ComponentUse> compnentUsageMap){
		//新增生效区装机清单
		this.installationListEffectiveMapper.insertFromEditable(installationListEditable.getId(), null, null);
		//新增生效区装机清单初始化数据
		ComponentUse componentUsage = compnentUsageMap.get(installationListEditable.getId());
		this.saveInstallInit(installationListEditable, componentUsage);
		//更新部件累计值
		this.saveComponentUseCount(installationListEditable, componentUsage, true);
		//更新航材主数据，没有记录更新 则新增主数据
		this.updateMaterial(installationListEditable);
		//更新部件数据（涉及b_h2_025更新及 b_h_010更新）
		this.updateComponentInfo(installationListEditable);
		//装上件减少外场库存
		if(installationListEditable.getZjzt() == InstalledStatusEnum.INSTALLED.getId()){
			this.outFieldStockService.update4InstallComponent(installationListEditable.getDprtcode(), installationListEditable.getBjh(), installationListEditable.getXlh(), installationListEditable.getPch(), installationListEditable.getZjsl());
		}
	}
	
	/**
	 * @Description 更新装机清单生效区数据
	 * @CreateTime 2017年10月19日 下午5:20:19
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单编辑区数据
	 * @param compnentUsageMap 部件在机使用量
	 */
	private void saveInstallEffective(InstallationListEditable installationListEditable, Map<String, ComponentUse> compnentUsageMap){
		//更新生效区装机清单
		this.installationListEffectiveMapper.updateFromEditable(installationListEditable.getId());
		//删除生效区装机清单的初始化数据
		this.installationListEffective2InitMapper.deleteByMainid(installationListEditable.getId());
		//更新生效区装机清单初始化数据
		ComponentUse componentUsage = compnentUsageMap.get(installationListEditable.getId());
		this.saveInstallInit(installationListEditable, componentUsage);
		//更新部件累计值
		this.saveComponentUseCount(installationListEditable, componentUsage, false);
		//更新航材主数据，没有记录更新 则新增主数据
		String bjid = this.updateMaterial(installationListEditable);
		//更新部件数据（涉及b_h2_025更新及 b_h_010更新）
		this.updateComponentInfo(installationListEditable);
		//拆下件增加外场库存
		if(installationListEditable.getZjzt() == InstalledStatusEnum.REMOVED.getId()){
			User user = ThreadVarUtil.getUser();
			
			if(StringUtils.isBlank(bjid)){//当部件id为空时去查询部件ID
				//查询部件 id
				HCMainData hCMainData = new HCMainData();
				hCMainData.setBjh(installationListEditable.getBjh());
				hCMainData.setDprtcode(installationListEditable.getDprtcode());
				List<HCMainData> hCMainDataList = this.hCMainDataMapper.checkMaterial(hCMainData);
				if(hCMainDataList.size() > 0){
					bjid = hCMainDataList.get(0).getId();
				}
			}
			
			//增加外场库存
			OutFieldStock outFieldStock = new OutFieldStock();
			String uuid = UUID.randomUUID().toString();
			outFieldStock.setId(uuid);
			outFieldStock.setXgdjid(installationListEditable.getId());
			outFieldStock.setKcid(UUID.randomUUID().toString());
			outFieldStock.setDprtcode(installationListEditable.getDprtcode());
			outFieldStock.setSjly(OutFieldStockSourceEnum.REMOVE.getId());
			outFieldStock.setBjid(bjid);
			outFieldStock.setBjh(installationListEditable.getBjh());
			outFieldStock.setPch(installationListEditable.getPch());
			outFieldStock.setSn(installationListEditable.getXlh());
			outFieldStock.setZwms(installationListEditable.getZwmc());
			outFieldStock.setYwms(installationListEditable.getZwmc());
			outFieldStock.setJldw(installationListEditable.getJldw());
			outFieldStock.setKcsl(installationListEditable.getZjsl());
			outFieldStock.setZt(StockStatusEnum.NORMAL.getId());
			outFieldStock.setTsn(installationListEditable.getTsn());
			outFieldStock.setTso(installationListEditable.getTso());
			outFieldStock.setCsn(installationListEditable.getCsn()==null?null:installationListEditable.getCsn().toString());
			outFieldStock.setCso(installationListEditable.getCso()==null?null:installationListEditable.getCso().toString());
			outFieldStock.setBjgzjl(installationListEditable.getBjgzjl());
			outFieldStock.setLlklx(installationListEditable.getLlklx());
			outFieldStock.setLlkbh(installationListEditable.getLlkbh());
			outFieldStock.setWhrid(user.getId());
			this.outFieldStockMapper.insert(outFieldStock);
			
			this.materialRecService.writeOutfieldRec(uuid, uuid, installationListEditable.getId(), null,
					OutfieldRecBizTypeEnum.TYPE4, UpdateTypeEnum.SAVE, "","","","","", installationListEditable.getZjsl());
		}
	}
	
	/**
	 * @Description 保存装机清单生效区初始数据
	 * @CreateTime 2017年10月20日 下午5:06:53
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单-编辑区数据
	 * @param componentUsage 部件在机使用量
	 */
	private void saveInstallInit(InstallationListEditable installationListEditable, ComponentUse componentUsage){
		
		List<InstallationListEditable2Init> list = installationListEditable.getInitDatas();
		List<InstallationListEffective2Init> effectList = new ArrayList<InstallationListEffective2Init>(list.size());
		
		InstallationListEffective2Init installationListEffective2Init = null;
		for (InstallationListEditable2Init installationListEditable2Init : list) {
			installationListEffective2Init = installationListEditable2Init.toEffective();
			//拆下的需要处理在机使用量和拆下是已用
			if(installationListEditable.getZjzt() == InstalledStatusEnum.REMOVED.getId()){
				if(MonitorProjectEnum.isCalendar(installationListEditable2Init.getJklbh())){
					//当监控项为日历时 拆下时已用为 拆下日期，在机使用量为拆下日期-安装日期(安装日期为空则在机使用量为空)
					Date ccsjDate = installationListEditable.getCcsj();
					String ccsjStr = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, ccsjDate);
					installationListEffective2Init.setCxsyy(ccsjStr);
					Date azsjDate = installationListEditable.getAzsj();
					if(azsjDate != null){
						String azsjStr = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, azsjDate);
						try {
							installationListEffective2Init.setZjsyl(String.valueOf(DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE, azsjStr, ccsjStr)));
						} catch (ParseException e) {}
					}
				}else{
					//非日历 使用 在机使用量
					if(componentUsage != null){
						Integer zjsyl = componentUsage.getByJklbh(installationListEditable2Init.getJklbh(), installationListEditable.getWz());
						if(zjsyl != null){
							installationListEffective2Init.setZjsyl(String.valueOf(zjsyl));
							installationListEffective2Init.setCxsyy(String.valueOf(zjsyl + Integer.valueOf(StringUtils.isNotBlank(installationListEffective2Init.getZssyy())?installationListEffective2Init.getZssyy():"0")));
						}
					}
				}
			}
			effectList.add(installationListEffective2Init);
		}
		if(effectList.size() > 0){
			//保存装机清单生效区数据
			this.installationListEffective2InitMapper.insert4Batch(effectList);
		}
	}
	
	/**
	 * @Description 保存部件汇总记录
	 * @CreateTime 2017年10月25日 下午5:25:54
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单
	 * @param componentUsage 部件在机使用量
	 * @param flag 保存标识 true:新增 false:修改，新装机清单为true，其它为false
	 */
	private void saveComponentUseCount(InstallationListEditable installationListEditable, ComponentUse componentUsage, boolean flag){
		List<InstallationListEditable2Init> list = installationListEditable.getInitDatas();
		MonitorProjectEnum[] monitorProjectEnum = MonitorProjectEnum.values();
		ComponentUseCount componentUseCount = null;
		
		List<ComponentUseCount> useCountList = new ArrayList<ComponentUseCount>(monitorProjectEnum.length - 1);
		for (MonitorProjectEnum enumItem : monitorProjectEnum) {
			//日历不需要存
			if(MonitorProjectEnum.isCalendar(enumItem.getCode())){
				continue;
			}
			componentUseCount = new ComponentUseCount();
			if(flag){
				componentUseCount.setId(UUID.randomUUID().toString());
			}
			componentUseCount.setDprtcode(installationListEditable.getDprtcode());
			componentUseCount.setJh(installationListEditable.getBjh());
			componentUseCount.setXlh(installationListEditable.getXlh());
			componentUseCount.setFjzch(installationListEditable.getFjzch());
			componentUseCount.setZjqdid(installationListEditable.getId());
			componentUseCount.setJklbh(enumItem.getCode());
			componentUseCount.setJkflbh(enumItem.getFl());
			componentUseCount.setSjFz(componentUsage!=null?componentUsage.getByJklbh(enumItem.getCode(), installationListEditable.getWz()):Integer.valueOf(0));
			for (InstallationListEditable2Init init : list) {
				if(init.getJklbh().equals(enumItem.getCode())){
					componentUseCount.setCsz(Integer.valueOf(init.getCsz()==null?"0":init.getCsz()));
					break;
				}
			}
			useCountList.add(componentUseCount);
		}
		if(flag){
			//批量新增部件使用汇总
			this.componentUseCountMapper.insert4Batch(useCountList);
		}else{
			//批量修改部件使用汇总
			this.componentUseCountMapper.update4Batch(useCountList);
		}
		
	}
	
	
	/**
	 * @Description 更新航材主数据，没有记录更新 则新增主数据
	 * @CreateTime 2017年10月20日 上午10:06:54
	 * @CreateBy 徐勇
	 * @param record 装机清单数据
	 */
	private String updateMaterial(InstallationListEditable record){
		int updateCount = this.hCMainDataMapper.updateByInstallationList(record);
		//当航材主数据没有能够更新时，执行新增操作
		if(updateCount == 0){
			User user = ThreadVarUtil.getUser();
			//新增航材主数据
			String uuid = UUID.randomUUID().toString();
			HCMainData hCMainData = new HCMainData();
			hCMainData.setId(uuid);
			hCMainData.setBjh(record.getBjh());
			hCMainData.setDprtcode(record.getDprtcode());
			hCMainData.setZwms(record.getZwmc());
			hCMainData.setYwms(record.getYwmc());
			hCMainData.setJldw(record.getJldw());
			hCMainData.setXingh(record.getXh());
			hCMainData.setCjjh(record.getCjjh());
			hCMainData.setZjh(record.getZjh());
			hCMainData.setGljb(StringUtils.isNotBlank(record.getXlh())?MaterialLevelEnum.HCLevel_3.getId():(StringUtils.isNotBlank(record.getPch())?MaterialLevelEnum.HCLevel_2.getId():MaterialLevelEnum.HCLevel_1.getId()));
			hCMainData.setHclx(MaterialEnum.HC_1.getId());
			hCMainData.setHclxEj(MaterialSecondTypeEnum.BODY_CONSUMPTION.getId());
			hCMainData.setXingh(record.getXh());
			hCMainData.setZt(EffectiveEnum.YES.getId());
			hCMainData.setBmid(user.getBmdm());
			hCMainData.setCjrid(user.getId());
			hCMainData.setSxkz(WhetherEnum.NO.getId());
			this.hCMainDataMapper.insertSelective(hCMainData);
			return uuid;
		}
		return null;
	}
	
	/**
	 * @Description 更新部件信息，涉及b_h2_025部件数据更新 及b_h_010部件数据
	 * @CreateTime 2017年10月20日 上午10:08:10
	 * @CreateBy 徐勇
	 * @param installationListEditable 装机清单编辑区数据
	 */
	private void updateComponentInfo(InstallationListEditable installationListEditable){
		if(StringUtils.isNotBlank(installationListEditable.getXlh())){
			if(installationListEditable.getEffective() != null 
					&& StringUtils.isNotBlank(installationListEditable.getEffective().getId())){
				//根据装机清单ID更新b_h2_025部件数据
				this.componentHistoryMapper.updateByInstallList(installationListEditable);
			}else{
				//新增 b_h2_025 部件数据
				this.componentHistoryMapper.insertByInstallList(installationListEditable);
			}
			//汇总b_h2_025取最新时间记录更新到b_h_010部件数据中
			int updateCount = this.componentMapper.updateCompnent2Newest(installationListEditable);
			if(updateCount == 0){
				this.componentMapper.insertByInstallList(installationListEditable);
			}
		}
	}
	
	/**
	 * @Description 装机清单生效时 处理装机清单编辑区数据，删除拆下数据，修改待同步标识为无需同步
	 * @CreateTime 2017年10月19日 下午2:12:43
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 */
	private void updateInstallEditable(String dprtcode, String fjzch){
		//删除拆下数据的从表信息
		this.installationListEditableMapper.deleteRemovedComponentInit(dprtcode, fjzch);
		//删除拆下数据
		this.installationListEditableMapper.deleteRemovedComponent(dprtcode, fjzch);
		//更新需要同步 为 无需同步
		this.installationListEditableMapper.update2NoNeedSync(dprtcode, fjzch);
	}
	
	
	/**
	 * @Description 装机清单生效验证
	 * @CreateTime 2017年10月10日 下午4:19:02
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @throws BusinessException
	 */
	private Map<String,Object> validate(Aircraftinfo info) throws BusinessException {
		
		// 验证飞机状态
		info = aircraftinfoService.selectByfjzchAndDprtcode(info);
		if(EffectiveEnum.NO.getId().equals(info.getZt())){
			throw new BusinessException("飞机已被禁用，装机清单无法生效！");
		}
		List<InstallationListEditable> toBeSyncList = this.installationListEditableMapper.selectToBeSyncWithInit(info.getDprtcode(), info.getFjzch());
		// 验证装机清单数据唯一
		List<Stock> stockList = new ArrayList<Stock>();
		for (InstallationListEditable installationListEditable : toBeSyncList) {
			Stock stock ;
			if (installationListEditable.getZjzt() == InstalledStatusEnum.INSTALLED.getId()
					&& null != installationListEditable.getXlh() && !"".equals(installationListEditable.getXlh())) {
				stock = new Stock();
				stock.setId(installationListEditable.getId());
				stock.setBjh(installationListEditable.getBjh());
				stock.setSn(installationListEditable.getXlh());
				stock.setDprtcode(installationListEditable.getDprtcode());
				stockList.add(stock);
			}
		}
		stockSerivce.getCount4ValidationBjAndXlh(stockList, PartSnValidationEnum.INSTALL.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("info", info);
		map.put("toBeSyncList", toBeSyncList);
		return map;
	}
     
	
	/**
	 * @Description  装上件从临时区生效
	 * @CreateTime 2017年11月17日 上午10:56:35
	 * @CreateBy 徐勇
	 * @param install 装机清单信息
	 * @param componentUsage 部件使用情况
	 * @param workIOId 工单拆装记录id
	 * @throws BusinessException 
	 */
	public void doInstalledEffectFromTemp(InstallationListEditable install, ComponentUse componentUsage, String workIOId) throws BusinessException{
		
		//验证序列号部件唯一性，一级节点唯一性
		try{
			validate(install);
		}catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		User user = ThreadVarUtil.getUser();
		install.setWhbmid(user.getBmdm());
		install.setWhrid(user.getId());
		
		//装机清单数据继承外场库存的产权和成本信息
		List<OutFieldStock> stockList = this.outFieldStockMapper.selectStockByComponent(install.getDprtcode(), install.getBjh(), install.getXlh(), install.getPch());
		if(stockList != null && !stockList.isEmpty()){
			OutFieldStock outStock = stockList.get(0);
			install.setKccb(outStock.getKccb());
			install.setBiz(outStock.getBiz());
			install.setCqid(outStock.getCqid());
		}
		
		//写入装机清单编辑区
		install.setTbbs(WhetherEnum.NO.getId());
		this.installationListEditableMapper.insertSelective(install);
		//写入装机清单编辑区初始化数据(根据装机清单ID从临时区同步）
		this.installationListEditable2InitMapper.insertFromTemp(install.getId());
		
		//从装机清单临时区写入生效区
		this.installationListEffectiveMapper.insertFromEditable(install.getId(), install.getCjZsjjh(), install.getCjZsjxlh());
		//保存生效区初始化数据
		this.saveInstallInit(install, componentUsage);
		
		//更新航材主数据，没有记录更新 则新增主数据
		this.updateMaterial(install);
		//更新部件数据（历史b_h2_025及当前b_h_010）
		this.updateComponentInfo(install);
		//更新部件累计值
		this.saveComponentUseCount(install, componentUsage, true);
		//装上件减少外场库存
		String[] kcArray = this.outFieldStockService.update4InstallComponent(install.getDprtcode(), install.getBjh(), install.getXlh(), install.getPch(), install.getZjsl());
		if(StringUtils.isNotBlank(kcArray[0])){//当有扣除库存，更新工单拆装记录的安装库存和库存履历
			//更新工单拆装记录和飞行记录本装上库存字段
			this.workOrderIORecordMapper.updateAzKcId(workIOId, kcArray[0], kcArray[1]);
			this.workOrderIORecordMapper.updateFlbIOByWorkorderIO(workIOId);
		}
		
		//关键部件更新飞机状态数据
		if(install.getCj() == 1 && install.getWz() != InstalledPositionEnum.BODY.getId()){
			this.saveAircraftStatus(install, componentUsage);
		}
		
		//序列号不为空时处理监控数据
		if(StringUtils.isNotBlank(install.getXlh())){
			Aircraftinfo info = new Aircraftinfo();
			info.setDprtcode(install.getDprtcode());
			info.setFjzch(install.getFjzch());
			info = aircraftinfoService.selectByfjzchAndDprtcode(info);
			this.eOApplicabilityService.addExecObject4Component(install.getDprtcode(), install.getFjzch(), install.getBjh(), install.getXlh());
			this.monitorDataService.add4installComponent(install.getDprtcode(), info.getFjjx(), install.getFjzch(), info.getXlh(), install.getId(), install.getBjh(), install.getXlh(), this.getCalInit(install));
		}
	}
	
	/**
	 * @Description 拆下件生效
	 * @CreateTime 2017年11月17日 下午1:44:29
	 * @CreateBy 徐勇
	 * @param install 装机清单
	 * @param componentUsage 部件使用
	 * @workIOId 工单拆装记录id
	 * @throws BusinessException 
	 */
	public void doRemovedEffect(InstallationListEditable install, ComponentUse componentUsage, String workIOId) throws BusinessException{
		//查询装机清单生效区数据
		InstallationListEffective installationListEffective = this.installationListEffectiveMapper.selectWithInitById(install.getId());
		if(installationListEffective==null || installationListEffective.getYxbs().intValue() == WhetherEnum.NO.getId().intValue()){
			throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"在装机清单中不存在！");
		}
		if(installationListEffective.getZjzt().intValue() == InstalledStatusEnum.REMOVED.getId().intValue()){
			throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"已被拆下！");
		}
		
		//删除编辑区数据（附带明细）
		this.installationListEditable2InitMapper.deleteByMainid(install.getId());
		this.installationListEditableMapper.deleteByPrimaryKey(install.getId());
		//更新生效区数据
		this.updateEffective4Remove(installationListEffective, install, componentUsage);
		
		// 关键部件删除b_s2_911
		this.aircraftStatusService.removeByZjqdid(install.getId());

		//拆下件入库
		//查询部件
		HCMainData hCMainData = new HCMainData();
		hCMainData.setBjh(installationListEffective.getBjh());
		hCMainData.setDprtcode(installationListEffective.getDprtcode());
		List<HCMainData> hCMainDataList = this.hCMainDataMapper.checkMaterial(hCMainData);
		if(hCMainDataList.size() > 0){
			hCMainData = hCMainDataList.get(0);
		}
	
		//增加外场库存
		OutFieldStock outFieldStock = new OutFieldStock();
		String uuid = UUID.randomUUID().toString();
		outFieldStock.setId(uuid);
		outFieldStock.setXgdjid(installationListEffective.getId());
		outFieldStock.setKcid(uuid);
		outFieldStock.setDprtcode(installationListEffective.getDprtcode());
		outFieldStock.setSjly(OutFieldStockSourceEnum.REMOVE.getId());
		outFieldStock.setBjid(hCMainData.getId());
		outFieldStock.setBjh(installationListEffective.getBjh());
		outFieldStock.setPch(installationListEffective.getPch());
		outFieldStock.setSn(installationListEffective.getXlh());
		outFieldStock.setZwms(installationListEffective.getZwmc());
		outFieldStock.setYwms(installationListEffective.getYwmc());
		outFieldStock.setJldw(hCMainData.getJldw());
		outFieldStock.setKcsl(installationListEffective.getZjsl());
		outFieldStock.setZt(StockStatusEnum.NORMAL.getId());
		outFieldStock.setTsn(installationListEffective.getTsn());
		outFieldStock.setTso(installationListEffective.getTso());
		outFieldStock.setCsn(installationListEffective.getCsn()==null?null:installationListEffective.getCsn().toString());
		outFieldStock.setCso(installationListEffective.getCso()==null?null:installationListEffective.getCso().toString());
		outFieldStock.setBjgzjl(installationListEffective.getBjgzjl());
		outFieldStock.setLlklx(installationListEffective.getLlklx());
		outFieldStock.setLlkbh(installationListEffective.getLlkbh());
		outFieldStock.setWhrid(ThreadVarUtil.getUser().getId());
		
		outFieldStock.setKccb(installationListEffective.getKccb());
		outFieldStock.setBiz(installationListEffective.getBiz());
		outFieldStock.setCqid(installationListEffective.getCqid());
		outFieldStock.setFjzch(installationListEffective.getFjzch());
		this.outFieldStockMapper.insert(outFieldStock);
		
		this.materialRecService.writeOutfieldRec(uuid, uuid, install.getId(), null,
				OutfieldRecBizTypeEnum.TYPE4, UpdateTypeEnum.SAVE, "","","","","", install.getZjsl());
		
		MaterialHistory materialHistory = new MaterialHistory(outFieldStock);
		materialHistory.setId(UUID.randomUUID().toString());
		this.materialHistoryService.insertSelective(materialHistory);
		//更新工单拆装记录拆下库存信息
		this.workOrderIORecordMapper.updateCxKcId(workIOId, outFieldStock.getId(), materialHistory.getId());
		this.workOrderIORecordMapper.updateFlbIOByWorkorderIO(workIOId);
		//删除当前监控数据
		this.monitorDataService.removeByZjqdid(install.getId());
	}
	
	/**
	 * @Description 拆下时更新生效区数据
	 * @CreateTime 2017年12月6日 上午11:30:29
	 * @CreateBy 徐勇
	 * @param installationListEffective 生效区数据
	 * @param install 执行拆下的数据
	 * @param componentUsage 部件使用记录
	 */
	private void updateEffective4Remove(InstallationListEffective installationListEffective, InstallationListEditable install, ComponentUse componentUsage){
		//更新生效区数据
		installationListEffective.setCcsj(install.getCcsj());
		installationListEffective.setCcjldid(install.getCcjldid());
		installationListEffective.setCcjldh(install.getCcjldh());
		installationListEffective.setCcrid(install.getCcrid());
		installationListEffective.setCcr(install.getCcr());
		installationListEffective.setCcbz(install.getCcbz());
		installationListEffective.setZjzt(InstalledStatusEnum.REMOVED.getId());
		installationListEffective.setCjZsjjh(install.getCjZsjjh());
		installationListEffective.setCjZsjxlh(install.getCjZsjxlh());
		this.installationListEffectiveMapper.updateByPrimaryKeySelective(installationListEffective);
		//更新生效区明细
		List<ComponentUseCount> componentUseCountList = new ArrayList<ComponentUseCount>();
		ComponentUseCount componentUseCount = null;
		User user = ThreadVarUtil.getUser();
		for (InstallationListEffective2Init installationListEffective2Init : installationListEffective.getInitDatas()) {
			if(MonitorProjectEnum.isCalendar(installationListEffective2Init.getJklbh())){
				//当监控项为日历时 拆下时已用为 拆下日期，在机使用量为拆下日期-安装日期(安装日期为空则在机使用量为空)
				Date ccsjDate = installationListEffective.getCcsj();
				String ccsjStr = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, ccsjDate);
				installationListEffective2Init.setCxsyy(ccsjStr);
				Date azsjDate = installationListEffective.getAzsj();
				if(azsjDate != null){
					String azsjStr = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, azsjDate);
					try {
						installationListEffective2Init.setZjsyl(String.valueOf(DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE, azsjStr, ccsjStr)));
					} catch (ParseException e) {}
				}
			}else{
				//非日历 使用 在机使用量
				if(componentUsage != null){
					Integer zjsyl = componentUsage.getByJklbh(installationListEffective2Init.getJklbh(), installationListEffective.getWz());
					if(zjsyl != null){
						installationListEffective2Init.setZjsyl(formatIntToStr(zjsyl));
						installationListEffective2Init.setCxsyy(formatIntToStr(zjsyl + Integer.valueOf(StringUtils.isNotBlank(installationListEffective2Init.getZssyy())?installationListEffective2Init.getZssyy():"0")));
					}
				}
				if(StringUtils.isBlank(installationListEffective2Init.getZjsyl())){
					installationListEffective2Init.setZjsyl("0");
				}
				if(StringUtils.isBlank(installationListEffective2Init.getCxsyy())){
					installationListEffective2Init.setCxsyy(formatIntToStr(0 + formatStrToInt(StringUtils.isNotBlank(installationListEffective2Init.getZssyy())?installationListEffective2Init.getZssyy():"0")));
				}
				//组装待更新的b_s2_913累计值
				componentUseCount = new ComponentUseCount();
				componentUseCount.setZjqdid(installationListEffective.getId());
				componentUseCount.setJklbh(installationListEffective2Init.getJklbh());
				componentUseCount.setSjFz(formatStrToInt(installationListEffective2Init.getZjsyl()));
				componentUseCountList.add(componentUseCount);
			}
			installationListEffective2Init.setWhbmid(user.getBmdm());
			installationListEffective2Init.setWhrid(user.getId());
			this.installationListEffective2InitMapper.updateByPrimaryKey(installationListEffective2Init);
		}
		//更新的b_s2_913累计值
		if(componentUseCountList.size() > 0){
			this.componentUseCountMapper.updateSJ4Batch(componentUseCountList);
		}
	}
	
	/**
	 * @Description  装上件从临时区生效
	 * @CreateTime 2017年11月17日 上午10:56:35
	 * @CreateBy 徐勇
	 * @param install 装机清单信息
	 * @param componentUsage 部件使用情况
	 * @throws BusinessException 
	 */
	public void doInstalledEffectFromTemp4Edit(InstallationListEditable install, ComponentUse componentUsage) throws BusinessException{
		
		//验证序列号部件唯一性，一级节点唯一性
		try{
			validate(install);
		}catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		User user = ThreadVarUtil.getUser();
		InstallationListEffective installationListEffective = this.installationListEffectiveMapper.selectWithInitById(install.getId());
		
		boolean isJhChanged = false;//件号是否发生变化
		boolean isXlhChanged = false;//序列号是否发生变化
		
		if(!install.getBjh().equals(installationListEffective.getBjh())){
			isJhChanged = true;
		}
		if(StringUtils.isNotBlank(install.getXlh()) && !install.getXlh().equals(installationListEffective.getXlh())){
			isXlhChanged = true;
		}
		if(StringUtils.isNotBlank(installationListEffective.getXlh()) && !installationListEffective.getXlh().equals(install.getXlh())){
			isXlhChanged = true;
		}
		
		if((isJhChanged || isXlhChanged) && installationListEffective.getZjzt().intValue() == InstalledStatusEnum.REMOVED.getId().intValue()){
			throw new BusinessException(this.getPartStr(install.getBjh(), install.getXlh(), install.getPch())+"原装机状态已被改变，不能修改件号序列号！");
		}
		
		if(installationListEffective.getZjzt().intValue() == InstalledStatusEnum.REMOVED.getId().intValue()){
			
			this.installationListEffective2InitMapper.deleteByMainid(install.getId());
			installationListEffective.setZwmc(install.getZwmc());
			installationListEffective.setYwmc(install.getYwmc());
			installationListEffective.setPch(install.getPch());
			installationListEffective.setZjsl(install.getZjsl());
			installationListEffective.setLlklx(install.getLlklx());
			installationListEffective.setLlkbh(install.getLlkbh());
			installationListEffective.setFjzw(install.getFjzw());
			installationListEffective.setChucrq(install.getChucrq());
			installationListEffective.setTsn(install.getTsn());
			installationListEffective.setTso(install.getTso());
			installationListEffective.setCsn(install.getCsn());
			installationListEffective.setCso(install.getCso());
			installationListEffective.setBjgzjl(install.getBjgzjl());
			installationListEffective.setSkbs(install.getSkbs());
			installationListEffective.setSsbs(install.getSsbs());
			installationListEffective.setAzFxjlbid(install.getAzFxjlbid());
			installationListEffective.setAzsj(install.getAzsj());
			installationListEffective.setAzjldid(install.getAzjldid());
			installationListEffective.setAzjldh(install.getAzjldh());
			installationListEffective.setAzrid(install.getAzrid());;
			installationListEffective.setAzr(install.getAzr());
			installationListEffective.setAzbz(install.getAzbz());
			installationListEffective.setWhbmid(user.getBmdm());
			installationListEffective.setWhrid(install.getId());
			this.installationListEffectiveMapper.updateByPrimaryKeySelective(installationListEffective);
			install.setZjzt(installationListEffective.getZjzt());
			install.setCcsj(installationListEffective.getCcsj());
		}else{
			this.installationListEditable2InitMapper.deleteByMainid(install.getId());
			this.installationListEditableMapper.deleteByPrimaryKey(install.getId());
			this.installationListEffective2InitMapper.deleteByMainid(install.getId());
			this.installationListEffectiveMapper.deleteByPrimaryKey(install.getId());
			
			//装机清单数据继承外场库存的产权和成本信息
			install.setKccb(installationListEffective.getKccb());
			install.setBiz(installationListEffective.getBiz());
			install.setCqid(installationListEffective.getCqid());
			
			install.setWhbmid(user.getBmdm());
			install.setWhrid(user.getId());
			//写入装机清单编辑区
			install.setTbbs(WhetherEnum.NO.getId());
			this.installationListEditableMapper.insertSelective(install);
			//写入装机清单编辑区初始化数据(根据装机清单ID从临时区同步）
			this.installationListEditable2InitMapper.insertFromTemp(install.getId());
			
			//从装机清单临时区写入生效区
			this.installationListEffectiveMapper.insertFromEditable(install.getId(), install.getCjZsjjh(), install.getCjZsjxlh());
		}
		//保存生效区初始化数据
		this.saveInstallInit(install, componentUsage);
		
		//更新航材主数据，没有记录更新 则新增主数据
		this.updateMaterial(install);
		
		//更新部件数据（历史b_h2_025及当前b_h_010）
		install.setEffective(installationListEffective);
		this.updateComponentInfo(install);
		if(isJhChanged || isXlhChanged){
			//更新原部件b_h_010
			if(StringUtils.isNotBlank(installationListEffective.getXlh())){
				//刷新b_h_010
				InstallationListEditable installtemp = new InstallationListEditable();
				installtemp.setBjh(installationListEffective.getBjh());
				installtemp.setXlh(installationListEffective.getXlh());
				installtemp.setDprtcode(installationListEffective.getDprtcode());
				this.componentMapper.updateCompnent2Newest(installtemp);
			}
		}
		//根据装机清单id删除部件累计数据913
		this.componentUseCountMapper.deleteByZjqdid(install.getId());
		//更新部件累计值
		this.saveComponentUseCount(install, componentUsage, true);
		
		//关键部件更新飞机状态数据
		if(	installationListEffective.getZjzt().intValue() == InstalledStatusEnum.INSTALLED.getId().intValue() && install.getCj() == 1 && install.getWz() != InstalledPositionEnum.BODY.getId()){
			this.saveAircraftStatus(install, componentUsage);
		}
		
		if(isJhChanged){
			if(StringUtils.isNotBlank(installationListEffective.getXlh())){
				//删除当前监控数据
				this.monitorDataService.removeByZjqdid(install.getId());
				//删除监控数据 (监控数据未执行且不存在非指定结束的工单)
				this.monitorDataService.removeMonitoringObjectByZjqdid(install.getId());
			}
			//序列号不为空时处理监控数据
			if(StringUtils.isNotBlank(install.getXlh())){
				Aircraftinfo info = new Aircraftinfo();
				info.setDprtcode(install.getDprtcode());
				info.setFjzch(install.getFjzch());
				info = aircraftinfoService.selectByfjzchAndDprtcode(info);
				this.eOApplicabilityService.addExecObject4Component(install.getDprtcode(), install.getFjzch(), install.getBjh(), install.getXlh());
				this.monitorDataService.add4installComponent(install.getDprtcode(), info.getFjjx(), install.getFjzch(), info.getXlh(), install.getId(), install.getBjh(), install.getXlh(), this.getCalInit(install));
			}
		}else{
			if(isXlhChanged){
				if(StringUtils.isNotBlank(install.getXlh())){
					//添加EO执行对象
					Aircraftinfo info = new Aircraftinfo();
					info.setDprtcode(install.getDprtcode());
					info.setFjzch(install.getFjzch());
					info = aircraftinfoService.selectByfjzchAndDprtcode(info);
					this.eOApplicabilityService.addExecObject4Component(install.getDprtcode(), install.getFjzch(), install.getBjh(), install.getXlh());
					//更新901和902的序列号
					this.monitorDataService.updateXlhByZjqdid(install.getId(), install.getXlh());
				}else if(StringUtils.isNotBlank(installationListEffective.getXlh())){
					//删除当前监控数据
					this.monitorDataService.removeByZjqdid(install.getId());
					//删除监控数据 (监控数据未执行且不存在非指定结束的工单)
					this.monitorDataService.removeMonitoringObjectByZjqdid(install.getId());
				}
			}
		}
	}
	
	
	/**
	 * @Description 拆下件修改
	 * @CreateTime 2017年11月17日 下午1:44:29
	 * @CreateBy 徐勇
	 * @param install 装机清单
	 * @param componentUsage 部件使用
	 * @throws BusinessException 
	 */
	public void doRemovedEdit(InstallationListEditable install, ComponentUse componentUsage) throws BusinessException{
		//查询装机清单生效区数据
		InstallationListEffective installationListEffective = this.installationListEffectiveMapper.selectWithInitById(install.getId());
		if(installationListEffective==null || installationListEffective.getYxbs().intValue() == WhetherEnum.NO.getId().intValue()){
			throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"在装机清单中不存在！");
		}
		if(installationListEffective.getZjzt().intValue() != InstalledStatusEnum.REMOVED.getId().intValue()){
			throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"装机状态已被改变！");
		}
		//更新生效区数据及拆下初始化数据
		this.updateEffective4Remove(installationListEffective, install, componentUsage);
	}
	
	/**
	 * @Description 装上件撤销
	 * @CreateTime 2017年11月24日 下午3:29:57
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 * @param workIOId 工单拆装记录id
	 * @param kcid 装上时库存id
	 * @param kcllid 装上时库存履历id
	 * @throws BusinessException 
	 */
	public void doInstalledUndo(String zjqdid, String workIOId, String kcid, String kcllid) throws BusinessException{
		//查询装机清单生效区数据
		InstallationListEffective installationListEffective = this.installationListEffectiveMapper.selectWithInitById(zjqdid);

		if(installationListEffective == null || installationListEffective.getYxbs() == WhetherEnum.NO.getId()){
			return;
		}
		if(installationListEffective.getZjzt() == InstalledStatusEnum.REMOVED.getId()){
			throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"装机状态已被改变！");
		}
		
		//删除当前监控数据
		this.monitorDataService.removeByZjqdid(zjqdid);
		
		//删除监控数据 (监控数据未执行且不存在非指定结束的工单)
		this.monitorDataService.removeMonitoringObjectByZjqdid(zjqdid);
		
		//根据装机清单id删除部件累计数据913
		this.componentUseCountMapper.deleteByZjqdid(zjqdid);
		
		//关键部件删除b_s2_911
		this.aircraftStatusService.removeByZjqdid(zjqdid);
		
		User user = ThreadVarUtil.getUser();
		//还原库存数据
		if(StringUtils.isNotBlank(kcid)){
			int count = this.outFieldStockMapper.updateKcslAddById(kcid, installationListEffective.getZjsl());
			if(count == 0 && StringUtils.isNotBlank(kcllid)){
				//从库存履历中恢复数据
				outFieldStockMapper.insertFromHistory(kcllid, installationListEffective.getZjsl(), user.getId());
			}
		}
		
		//序列号件根据装机清单id删除部件历史数据b_h2_025，并且刷新b_h_010数据
		if(StringUtils.isNotBlank(installationListEffective.getXlh())){
			this.componentHistoryMapper.deleteByZjqdid(zjqdid);
			//刷新b_h_010
			InstallationListEditable install = new InstallationListEditable();
			install.setBjh(installationListEffective.getBjh());
			install.setXlh(installationListEffective.getXlh());
			install.setDprtcode(installationListEffective.getDprtcode());
			this.componentMapper.updateCompnent2Newest(install);
		}
		//删除装机清单编辑区及编辑区初始化数据
		this.installationListEditable2InitMapper.deleteByMainid(zjqdid);
		this.installationListEditableMapper.deleteByPrimaryKey(zjqdid);
		//将装机清单生效区数据改为无效
		InstallationListEffective newEffect = new InstallationListEffective();
		newEffect.setId(zjqdid);
		newEffect.setYxbs(WhetherEnum.NO.getId());
		this.installationListEffectiveMapper.updateByPrimaryKeySelective(newEffect);
	}
	
	/**
	 * @Description 拆下件撤销
	 * @CreateTime 2017年11月24日 下午3:40:43
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 * @param workIOId 工单拆装记录id
	 * @param kcid 拆下时的库存id
	 * @param kcllid 拆下时的库存履历id
	 * @param componentUse部件使用
	 * @throws BusinessException
	 */
	public void doRemovedUndo(String zjqdid, String workIOId, String kcid, String kcllid, ComponentUse componentUse) throws BusinessException{
		
		InstallationListEffective installationListEffective = this.installationListEffectiveMapper.selectWithInitById(zjqdid);
		if(installationListEffective == null || installationListEffective.getYxbs() == WhetherEnum.NO.getId()){
			return;
		}
		
		if(StringUtils.isNotBlank(kcid)){
			//删除库存
			OutFieldStock outfieldStock = this.outFieldStockMapper.selectByPrimaryKey(kcid);
			if(outfieldStock == null){
				throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"撤回时外场库存不足");
			}
			BigDecimal kysl = outfieldStock.getKcsl().subtract(outfieldStock.getDjsl()==null?BigDecimal.ZERO:outfieldStock.getDjsl());
			if(kysl.compareTo(installationListEffective.getZjsl()) >= 0){
				if(outfieldStock.getDjsl()==null || outfieldStock.getDjsl().compareTo(BigDecimal.ZERO) == 0){
					if(kysl.compareTo(installationListEffective.getZjsl()) == 0){
						this.outFieldStockMapper.deleteByPrimaryKey(kcid);
					}else{
						//减库存
						this.outFieldStockMapper.updateKcslById(kcid, outfieldStock.getKcsl().subtract(installationListEffective.getZjsl()));
					}
				}else{
					//减库存
					this.outFieldStockMapper.updateKcslById(kcid, outfieldStock.getKcsl().subtract(installationListEffective.getZjsl()));
				}
				this.outFieldStockService.update4InstallComponent(installationListEffective.getDprtcode(), installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch(), installationListEffective.getZjsl());
			}else{
				throw new BusinessException(this.getPartStr(installationListEffective.getBjh(), installationListEffective.getXlh(), installationListEffective.getPch())+"撤回时外场库存不足");
			}
		}
		
		InstallationListEditable validateParam = new InstallationListEditable();
		validateParam.setId(zjqdid);
		validateParam.setDprtcode(installationListEffective.getDprtcode());
		validateParam.setWz(installationListEffective.getWz());
		validateParam.setBjh(installationListEffective.getBjh());
		validateParam.setXlh(installationListEffective.getXlh());
		validateParam.setCj(installationListEffective.getCj());
		validateParam.setFjzch(installationListEffective.getFjzch());
		validateParam.setAzsj(installationListEffective.getAzsj());
		validateParam.setCcsj(installationListEffective.getCcsj());
		validate(validateParam);
		
		//更新装机清单生效区数据为装上状态
		this.installationListEffectiveMapper.update4ClearRemoveDataById(zjqdid);
		
		//清除装机清单生效区初始化数据中在机使用量和拆下时已用
		this.installationListEffective2InitMapper.update4ClearRemoveDataByMainid(zjqdid);
		//同步生效区数据到编辑区
		this.installationListEditable2InitMapper.insertFromEffectiveByMainId(zjqdid);
		this.installationListEditableMapper.insertFromEffectiveById(zjqdid);
		
		// 关键部件更新飞机状态数据
		if(installationListEffective.getCj() == 1 && installationListEffective.getWz() != InstalledPositionEnum.BODY.getId()){
			this.saveAircraftStatus(installationListEffective, componentUse);
		}
		//生成监控数据
		if(StringUtils.isNotBlank(installationListEffective.getXlh())){
			Aircraftinfo info = new Aircraftinfo();
			info.setDprtcode(installationListEffective.getDprtcode());
			info.setFjzch(installationListEffective.getFjzch());
			info = aircraftinfoService.selectByfjzchAndDprtcode(info);
			this.eOApplicabilityService.addExecObject4Component(installationListEffective.getDprtcode(), installationListEffective.getFjzch(), installationListEffective.getBjh(), installationListEffective.getXlh());
			this.monitorDataService.add4RemoveComponentUndo(installationListEffective.getDprtcode(), info.getFjjx(), installationListEffective.getFjzch(), info.getXlh(), installationListEffective.getId(), installationListEffective.getBjh(), installationListEffective.getXlh(), this.getCalInit(installationListEffective));
		}
	}
	
	/**
	 * @Description 监控值 由 string 转 int， 为空则返回0
	 * @CreateTime 2017年11月20日 下午5:20:17
	 * @CreateBy 徐勇
	 * @param str
	 * @return
	 */
	private int formatStrToInt(String str){
		if(StringUtils.isNotBlank(str)){
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * @Description 监控值 由 int 转 String， 为空则返回0
	 * @CreateTime 2017年11月20日 下午5:20:17
	 * @CreateBy 徐勇
	 * @param i
	 * @return
	 */
	private String formatIntToStr(Integer i){
		if(i == null){
			i = 0;
		}
		return i.toString();
	}
	
	/**
	 * @Description 验证装机清单数据
	 * @CreateTime 2017年9月28日 下午4:38:02
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	private void validate(InstallationListEditable record) throws BusinessException{
		// 验证一级部件（除机身）位置唯一
		if(!InstalledPositionEnum.BODY.getId().equals(record.getWz())
				&& record.getCj() == 1){
			if(installationListEditableMapper.getLevelOneCount(record) > 0){
				throw new BusinessException("位置为" + InstalledPositionEnum.getName(record.getWz())+ 
						"的一级部件最多只能有一个！");
			}
		}
		if(StringUtils.isNotBlank(record.getXlh())){
			stockSerivce.getCount4ValidationBjAndXlh(record.getId(), record.getBjh(), record.getXlh(), record.getDprtcode(), PartSnValidationEnum.INSTALL.getId());
		}
	}
	
	private String getPartStr(String bjh, String xlh, String pch){
		StringBuilder sb = new StringBuilder();
		sb.append("件号[").append(bjh).append("]");
		if(StringUtils.isNotBlank(xlh)){
			sb.append(" 序列号[").append(xlh).append("]");
		}
		if(StringUtils.isNotBlank(pch)){
			sb.append(" 批次号[").append(pch).append("]");
		}
		return sb.toString();
	}
	
}