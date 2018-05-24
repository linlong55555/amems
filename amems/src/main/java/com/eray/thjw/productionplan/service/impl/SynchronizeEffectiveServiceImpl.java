package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.productionplan.dao.SynchronizeEffectiveMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.SynchronizeEffectiveService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.PlaneComponentPositionEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * 同步生效区数据service
 * @author hanwu
 *
 */
@Service
public class SynchronizeEffectiveServiceImpl implements SynchronizeEffectiveService {
    
	@Resource
	private SynchronizeEffectiveMapper  synchronizeEffectiveMapper;
	
	@Resource
	private LoadingListService loadingListService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
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
	

	/**
	 * 同步监控数据，将编辑区的数据同步到生效区中
	 */
	@Override
	public List<String> doSynchronize(PlaneData pd) throws RuntimeException {
		// 验证数据是否配置完全
		List<String> errorList = this.validate(pd);
		if(errorList.isEmpty()){
			// 同步装机清单数据(b_s_003)
			this.synchronizeLoadingList(pd);
			// 同步时控件监控设置(b_s_00301)
			this.synchronizeTimeMonitor(pd);
			// 同步特殊飞行情况(b_s_00302)
			this.synchronizeSpecialFlightSetting(pd);
			// 同步定检项目数据(b_s_00303)
			this.synchronizeFixedItem(pd);
			// 同步定检监控数据(b_s_00304)
			this.synchronizeFixedMonitor(pd);
			// 同步定检件监控计划(b_s_008)
			this.synchronizeFixedMonitorPlan(pd);
			// 同步部件(b_h_010)
			this.synchronizeComponent(pd);
			// 同步部件拆解记录(b_h_01002)
			this.synchronizeComponentDisassembleRecord(pd);
			// 同步部件使用情况(b_h_01001)
			this.synchronizeComponentUsage(pd);
			// 修改同步标识
			this.updateTbbs(pd);
			// 记录rec
			logRec(pd);
		}
		return errorList;
	}
	
	/**
	 * 验证数据是否配置完全
	 */
	private List<String> validate(PlaneData pd){
		
		List<String> errorMsgs = new ArrayList<String>();
		
		// 验证是否有飞机权限
		List<String> list = new ArrayList<String>();
		list.add(pd.getFjzch());
		if(!planeModelDataService.existsAircraft(ThreadVarUtil.getUser().getId(), 
				ThreadVarUtil.getUser().getUserType(), ThreadVarUtil.getUser().getJgdm(), list)){
			errorMsgs.add("无该飞机权限！");
			return errorMsgs;
		}
				
		// 验证时控件的监控配置
		List<LoadingList> timeMonitorDatas = synchronizeEffectiveMapper.findUnfinishedTimeMonitorData(pd);
		if(!timeMonitorDatas.isEmpty()){
			errorMsgs.add(buildErrorMsg(timeMonitorDatas, "未进行时控件监控配置。"));
		}
		
		// 验证定检件的监控配置
		List<LoadingList> fixedMonitorDatas = synchronizeEffectiveMapper.findUnfinishedFixedMonitorData(pd);
		if(!fixedMonitorDatas.isEmpty()){
			errorMsgs.add(buildErrorMsg(fixedMonitorDatas, "未进行定检件监控配置。"));
		}
		
		// 验证时控件的未完成工单
		List<LoadingList> timeMonitorPlans = synchronizeEffectiveMapper.findUnfinishedTimeMonitorPlan(pd);
		if(!timeMonitorPlans.isEmpty()){
			errorMsgs.add(buildErrorMsg(timeMonitorPlans, "还有未完成的时控件工单计划。"));
		}
		
		// 验证定检件的未完成工单
		List<LoadingList> fixedMonitorPlans = synchronizeEffectiveMapper.findUnfinishedFixedMonitorPlan(pd);
		if(!fixedMonitorPlans.isEmpty()){
			errorMsgs.add(buildErrorMsg(fixedMonitorPlans, "还有未完成的定检件工单计划。"));
		}
		
		// 验证定检件对应的最新版本定检项目状态是否为3
		List<LoadingList> scrapFixedMonitors = synchronizeEffectiveMapper.findScrapFixedMonitor(pd);
		if(!scrapFixedMonitors.isEmpty()){
			errorMsgs.add(buildErrorMsg(scrapFixedMonitors, "的最新定检项目未批准。"));
		}
		
		// 获取未配置完全的定检监控项目（由于升版导致）
		List<LoadingList> notFinishedFixedMonitors = synchronizeEffectiveMapper.findNotFinishedFixedMonitor(pd);
		if(!notFinishedFixedMonitors.isEmpty()){
			errorMsgs.add(buildErrorMsg(notFinishedFixedMonitors, "的监控项目未配置完全。"));
		}
		
		// 获取未关联部件
		List<LoadingList> noParentData = synchronizeEffectiveMapper.findNoParentData(pd);
		if(!noParentData.isEmpty()){
			errorMsgs.add(buildErrorMsg(noParentData, "未关联部件。"));
		}
		
		return errorMsgs;
	}
	
	/**
	 * 拼装错误信息
	 * @param list
	 * @param msg
	 * @return
	 */
	private String buildErrorMsg(List<LoadingList> list, String msg){
		StringBuilder builder = new StringBuilder();
		for (LoadingList ll : list) {
			builder.append(ll.getDisplayName()).append("、");
		}
		builder.deleteCharAt(builder.lastIndexOf("、"));
		builder.append(msg);
		return builder.toString();
	}
	
	/**
	 * 同步装机清单数据(b_s_001---->b_s_003)
	 * @param fjzch
	 */
	private void synchronizeLoadingList(PlaneData pd){
		// 同步装机清单数据到生效区（新增、修改）
		synchronizeEffectiveMapper.synchronizeLoadingList(pd);
		// 参数map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 飞机注册号
		paramMap.put("fjzch", pd.getFjzch());
		paramMap.put("dprtcode", pd.getDprtcode());
		// 部门id
		paramMap.put("bmid", ThreadVarUtil.getUser().getBmdm());
		// 创建人id
		paramMap.put("cjrid", ThreadVarUtil.getUser().getId());
		// 同步航材主数据
		synchronizeEffectiveMapper.synchronizeAerialMaterialMainData(paramMap);
		
	}
	 
	
	/**
	 * 同步时控件监控设置(b_s_00101---->b_s_00301)
	 * @param fjzch
	 */
	private void synchronizeTimeMonitor(PlaneData pd){
		// 同步时控件监控设置（新增、修改）
		synchronizeEffectiveMapper.synchronizeTimeMonitor(pd);
		// 同步时控件监控设置（删除）
		synchronizeEffectiveMapper.synchronizeTimeMonitorDeleteData(pd);
	}
	
	/**
	 * 同步特殊飞行情况(b_s_00102---->b_s_00302)
	 * @param fjzch
	 */
	private void synchronizeSpecialFlightSetting(PlaneData pd){
		// 同步特殊飞行情况（新增、修改）
		synchronizeEffectiveMapper.synchronizeSpecialFlightSetting(pd);
		// 同步特殊飞行情况（删除）
		synchronizeEffectiveMapper.synchronizeSpecialFlightSettingDeleteData(pd);
	}
	
	/**
	 * 同步定检项目数据(b_s_00103---->b_s_00303)
	 * @param fjzch
	 */
	private void synchronizeFixedItem(PlaneData pd){
		// 同步定检项目数据（新增、修改）
		synchronizeEffectiveMapper.synchronizeFixedItem(pd);
		// 删除无效的定检项目数据
		synchronizeEffectiveMapper.deleteInvalidFixedItem(pd);
	}
	
	/**
	 * 同步定检监控数据(b_s_0010301---->b_s_00304)
	 * @param fjzch
	 */
	private void synchronizeFixedMonitor(PlaneData pd){
		// 同步定检监控项数据（新增、修改）
		synchronizeEffectiveMapper.synchronizeFixedMonitor(pd);
		// 同步定检监控项数据中的删除数据（删除）
		synchronizeEffectiveMapper.synchronizeFixedMonitorDeleteData(pd);
		
	}
	
	/**
	 * 同步定检件监控计划(b_s_00304---->b_s_008)
	 * @param fjzch
	 */
	private void synchronizeFixedMonitorPlan(PlaneData pd){
		// 参数map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 飞机注册号
		paramMap.put("fjzch", pd.getFjzch());
		paramMap.put("dprtcode", pd.getDprtcode());
		// 制单人id
		paramMap.put("zdrid", ThreadVarUtil.getUser().getId());
		// 同步定检件监控计划数据（新增、修改）
		synchronizeEffectiveMapper.synchronizeFixedMonitorPlan(paramMap);
		// 同步定检件监控计划数据中的删除数据（删除）
		synchronizeEffectiveMapper.synchronizeFixedMonitorPlanDeleteData(pd);
	}
	
	/**
	 * 同步部件(b_s_001----b_h_010)
	 * @param fjzch
	 */
	private void synchronizeComponent(PlaneData pd){
		// 同步部件
		synchronizeEffectiveMapper.synchronizeComponent(pd);
	}
	
	/**
	 * 同步部件拆解记录(b_s_001----b_h_01002)
	 * @param fjzch
	 */
	private void synchronizeComponentDisassembleRecord(PlaneData pd){
		// 同步部件拆解记录
		synchronizeEffectiveMapper.synchronizeComponentDisassembleRecord(pd);
	}
	
	/**
	 * 同步部件使用情况(b_s_001----b_h_01001)
	 * @param fjzch
	 */
	private void synchronizeComponentUsage(PlaneData pd){
		// 所有部件的使用情况
		List<ComponentUsage> list = synchronizeEffectiveMapper.getComponentUsage(pd);
		// 参数map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (ComponentUsage usage : list) {
			// 装机清单id
			paramMap.put("zjqdid", usage.getZjqdid());
			if(isUsed(usage, usage.getWz()) && isEverDisassembled(usage)){
//				//初始标记-调整值
//				paramMap.put("csbj", CSBJ_ADJUST_VALUE);
//				paramMap.put("type", CSBJ_ZJHYY_CODE);
//				// 同步部件已用使用情况
//				synchronizeEffectiveMapper.synchronizeComponentUsage(paramMap);
			}else{
				//初始标记
				paramMap.put("csbj", CSBJ_BJYY_VALUE);
				paramMap.put("type", CSBJ_BJYY_CODE);
				// 同步部件已用使用情况
				synchronizeEffectiveMapper.synchronizeComponentUsage(paramMap);
				
				//初始标记
				paramMap.put("csbj", CSBJ_ZJHYY_VALUE);
				paramMap.put("type", CSBJ_ZJHYY_CODE);
				// 同步装机后-->进入系统前情况
				synchronizeEffectiveMapper.synchronizeComponentUsage(paramMap);
			}
		}
	}
	
	/**
	 * 根据部件拆解记录，判断该部件是否曾经装过飞机
	 * @param usage
	 * @return
	 */
	private boolean isEverDisassembled(ComponentUsage usage){
		return synchronizeEffectiveMapper.isEverDisassembled(usage);
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
	 * 修改同步标识
	 * @param fjzch
	 */
	private void updateTbbs(PlaneData pd){
		// 更新装机清单编辑区的同步标识
		synchronizeEffectiveMapper.updateLoadingListTbbs(pd);
		// 更新时控件监控设置编辑区的同步标识
		synchronizeEffectiveMapper.updateTimeMonitorTbbs(pd);
		// 更新同步特殊飞行情况编辑区的同步标识
		synchronizeEffectiveMapper.updateSpecialFlightSettingTbbs(pd);
		// 更新同步定检项目数据编辑区的同步标识
		synchronizeEffectiveMapper.updateFixedItemTbbs(pd);
		// 更新定检监控项数据编辑区的同步标识
		synchronizeEffectiveMapper.updateFixedMonitorTbbs(pd);
	}
	
	/**
	 * 记录日志
	 * @param fjzch
	 */
	private void logRec(PlaneData pd){
		commonRecService.writeByWhere(" b.fjzch = '"+pd.getFjzch().replaceAll("'", "''")+"' and b.dprtcode = '"+ThreadVarUtil.getUser().getJgdm()+"'"
				, TableEnum.D_007, ThreadVarUtil.getUser().getId(), UUID.randomUUID().toString()
				, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE
				, pd.getFjzch().concat(",").concat(ThreadVarUtil.getUser().getJgdm()));
	}
	
}
