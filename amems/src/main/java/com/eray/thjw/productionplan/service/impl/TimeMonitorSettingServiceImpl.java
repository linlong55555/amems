package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.LoadingListEditableMapper;
import com.eray.thjw.productionplan.dao.TimeMonitorSettingEditableMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;
import com.eray.thjw.productionplan.service.TimeMonitorSettingService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


@Service
public class TimeMonitorSettingServiceImpl implements TimeMonitorSettingService{
	
	@Resource
	private TimeMonitorSettingEditableMapper timeMonitorSettingEditableMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private LoadingListEditableMapper loadingListEditableMapper;

	/**
	 * 查询时控件设置-编辑区
	 */
	@Override
	public List<TimeMonitorSetting> selectEditable(TimeMonitorSetting setting)
			throws RuntimeException {
		return timeMonitorSettingEditableMapper.select(setting);
	}


	/**
	 * 保存时控件设置-编辑区
	 */
	@Override
	public void saveEditable(List<TimeMonitorSetting> settings, String zjqdid, String czls, LogOperationEnum logOperationEnum)
			throws RuntimeException {
		// 删除原有时控件设置
		deleteEditable(zjqdid, czls, logOperationEnum);
		// 新增时控件设置
		for (TimeMonitorSetting setting : settings) {
			setting.setId(UUID.randomUUID().toString());
			setting.setZt(1);
			setDefaultValue(setting);
			timeMonitorSettingEditableMapper.insertSelective(setting);
			commonRecService.write(setting.getId(), TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(), 
					czls, logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		}
		// 同步定检件的部件已用和装机后已用
		timeMonitorSettingEditableMapper.synchronizeFixedMonitorTcAndTv(zjqdid);
		// 插入日志
		commonRecService.writeByWhere(" b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
	}
	
	/**
	 * 设置默认值
	 * @param ll
	 */
	private void setDefaultValue(TimeMonitorSetting setting){
		User user = ThreadVarUtil.getUser();
		// 待同步
		setting.setTbbs(SynchronzeEnum.TO_DO.getCode());
		setting.setWhrid(user.getId());
		setting.setWhdwid(user.getBmdm());
		setting.setWhsj(new Date());
		setting.setDprtcode(user.getJgdm());
	}


	/**
	 * 删除时控件设置-编辑区
	 */
	@Override
	public void deleteEditable(String zjqdid, String czls, LogOperationEnum logOperationEnum) {
		TimeMonitorSetting param = new TimeMonitorSetting();
		param.setZjqdid(zjqdid);
		timeMonitorSettingEditableMapper.deleteByParam(param);
		List<String> keys = new ArrayList<String>();
		keys.add(zjqdid);
		commonRecService.write("zjqdid", keys, TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}


	/**
	 * 级联删除时控件设置-编辑区
	 */
	@Override
	public void cascadeDeleteEditable(String zjqdid, String czls, LogOperationEnum logOperationEnum) {
		TimeMonitorSetting param = new TimeMonitorSetting();
		param.setZjqdid(zjqdid);
		timeMonitorSettingEditableMapper.cascadeDeleteEditable(param);
		// 记录日志
		LoadingList ll = new LoadingList();
		ll.setId(zjqdid);
		commonRecService.write("zjqdid", loadingListEditableMapper.getChildrenId(ll), TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(),
				czls , logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}
	


}
