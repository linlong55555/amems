package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.flightdata.dao.MountLoadingListMapper;
import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.MountFixedMonitor;
import com.eray.thjw.flightdata.po.MountLoadingList;
import com.eray.thjw.flightdata.po.MountSpecialCondition;
import com.eray.thjw.flightdata.po.MountSubcomponent;
import com.eray.thjw.flightdata.po.MountTimeMonitor;
import com.eray.thjw.flightdata.service.MountFixedMonitorService;
import com.eray.thjw.flightdata.service.MountLoadingListService;
import com.eray.thjw.flightdata.service.MountSpecialConditionService;
import com.eray.thjw.flightdata.service.MountSubcomponentService;
import com.eray.thjw.flightdata.service.MountTimeMonitorService;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.LoadingListEditableMapper;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ComponentTypeEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 装上件-装机清单信息实现
 * @author hanwu
 *
 */
@Service
public class MountLoadingListServiceImpl implements MountLoadingListService {

	@Resource
	private MountLoadingListMapper mountLoadingListMapper;
	
	@Resource
	private MountTimeMonitorService mountTimeMonitorService;
	
	@Resource
	private MountSpecialConditionService mountSpecialConditionService;
	
	@Resource
	private MountSubcomponentService mountSubcomponentService;
	
	@Resource
	private MountFixedMonitorService mountFixedMonitorService;
	
	@Resource
	private LoadingListEditableMapper loadingListEditableMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private PlaneDataMapper planeDataMapper;
	
	@Resource
	private HCMainDataService hcMainDataService;


	/**
	 * 保存装上件-装机清单信息
	 */
	@Override
	public void save(FlightRecordSheetToDisassembly data) {
		// 失效原有的拆解记录下的装上
		MountLoadingList deleteData = new MountLoadingList();
		deleteData.setMainid(data.getId());
		// 设置默认值
		setDefaultValue(deleteData);
		mountLoadingListMapper.delete(deleteData);
		
		MountLoadingList ll = data.getOn();
		if(ll.getJh() == null){
			return;
		}
		// 当前操作   1：新增   2.修改 
		UpdateTypeEnum operation = UpdateTypeEnum.UPDATE;
		// 生成id
		if(StringUtils.isBlank(ll.getId())){
			ll.setId(UUID.randomUUID().toString());
			operation = UpdateTypeEnum.SAVE;
		}
		// 设置默认值
		setDefaultValue(ll);
		// 保存记录
		mountLoadingListMapper.save(ll);
		// 插入日志
		commonRecService.write(ll.getId(), TableEnum.B_S_006020101, ThreadVarUtil.getUser().getId(), data.getCzls(),
				data.getLogOperationEnum(), operation, data.getFxjldid());
		// 修改飞行记录单装上件时，同步航材主数据
		hcMainDataService.updateByLoadingList(ll,  data.getCzls());
		// 设置mainid
		setMainid(data);
		// 二级节点不能有子节点
		if(ll.getCj() == 2){
			ll.setChildren(new ArrayList<MountSubcomponent>());
		}
		// 保存装上件-时控件数据
		mountTimeMonitorService.save(data.getTimeMonitor().getSettings(), data.getOn().getId(), 
				data.getCzls(), data.getLogOperationEnum(), data.getFxjldid(), data.getDprtcode());
		// 保存装上件-特殊情况设置
		mountSpecialConditionService.save(data.getTimeMonitor().getConditions(), data.getOn().getId(), 
				data.getCzls(), data.getLogOperationEnum(), data.getFxjldid(), data.getDprtcode());
		// 保存子部件关系
		mountSubcomponentService.save(data.getOn().getChildren(), data.getOn().getId(), 
				data.getCzls(), data.getLogOperationEnum(), data.getFxjldid(), data.getDprtcode());
		// 保存定检项目
		mountFixedMonitorService.save(data.getFixedMonitor(), 
				data.getCzls(), data.getLogOperationEnum(), data.getFxjldid(), data.getDprtcode());
	}
	
	/**
	 * 设置mainid
	 * @param data
	 */
	private void setMainid(FlightRecordSheetToDisassembly data){
		if(data.getTimeMonitor().getSettings() != null){
			for (MountTimeMonitor time : data.getTimeMonitor().getSettings()) {
				time.setMainid(data.getOn().getId());
				time.setFxjldid(data.getFxjldid());
				time.setCzls(data.getCzls());
				time.setLogOperationEnum(data.getLogOperationEnum());
			}
		}
		if(data.getTimeMonitor().getConditions() != null){
			for (MountSpecialCondition condition : data.getTimeMonitor().getConditions()) {
				condition.setMainid(data.getOn().getId());
				condition.setFxjldid(data.getFxjldid());
				condition.setCzls(data.getCzls());
				condition.setLogOperationEnum(data.getLogOperationEnum());
			}
		}
		if(data.getOn().getChildren() != null){
			for (MountSubcomponent child : data.getOn().getChildren()) {
				child.setMainid(data.getOn().getId());
				child.setFxjldid(data.getFxjldid());
				child.setCzls(data.getCzls());
				child.setLogOperationEnum(data.getLogOperationEnum());
			}
		}
		if(data.getFixedMonitor() != null){
			for (MountFixedMonitor fixed : data.getFixedMonitor()) {
				fixed.setMainid(data.getOn().getId());
				fixed.setFxjldid(data.getFxjldid());
				fixed.setCzls(data.getCzls());
				fixed.setLogOperationEnum(data.getLogOperationEnum());
			}
		}
	}
	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(MountLoadingList data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
		data.setBjlx(ComponentTypeEnum.COMPONENT.getCode());
	}
	
}
