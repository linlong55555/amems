package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.ScheduledCheckMonitorItemEditableMapper;
import com.eray.thjw.productionplan.dao.ScheduledCheckMonitorItemMapper;
import com.eray.thjw.productionplan.dao.TimeMonitorSettingEditableMapper;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;
import com.eray.thjw.productionplan.service.ScheduledCheckMonitorItemService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.MonitorItemEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;


@Service
public class ScheduledCheckMonitorItemServiceImpl implements ScheduledCheckMonitorItemService {
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private ScheduledCheckMonitorItemMapper scheduledCheckMonitorItemMapper;
	
	@Autowired
	private ScheduledCheckMonitorItemEditableMapper scheduledCheckMonitorItemEditableMapper;
	
	@Autowired
	private TimeMonitorSettingEditableMapper timeMonitorSettingEditableMapper;
	
	@Autowired
	private CommonRecService commonRecService;
	
	
	@Override
	public List<ScheduledCheckMonitorItem> queryAllList(Map<String, Object> map) throws Exception {
		return scheduledCheckMonitorItemMapper.queryAllList(map);
	}


	@Override
	public List<ScheduledCheckMonitorItem> queryAllsj(Map<String, Object> map)throws Exception {
		return scheduledCheckMonitorItemMapper.queryAllsj(map);
	}


	/**
	 * 编辑区-保存监控项目
	 */
	@Override
	public void saveEditable(List<ScheduledCheckMonitorItem> list, String mainid, String zjqdid
			, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		List<String> ids = new ArrayList<String>();
		for (ScheduledCheckMonitorItem item : list) {
			// 生成id
			item.setId(UUID.randomUUID().toString());
			// 定检项目id
			item.setMainid(mainid);
			// 状态
			item.setZt(1);
			// 设置默认值
			setDefaultValue(item);
			ids.add(item.getId());
		}
		// 保存监控项目
		scheduledCheckMonitorItemEditableMapper.batchInsert(list);
		// 插入日志
		commonRecService.writeByWhere(" b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		// 同步时控件的部件已用和装机后已用
		scheduledCheckMonitorItemEditableMapper.synchronizeTimeMonitorTcAndTv(zjqdid);
		// 插入日志
		commonRecService.writeByWhere(" b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
				TableEnum.B_S_00101, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		// 重新计算预拆值
		recalcExpectRemoveValue(zjqdid);
	}
	
	/**
	 * 重新计算预拆值
	 * @param zjqdid
	 */
	private void recalcExpectRemoveValue(String zjqdid){
		TimeMonitorSetting param = new TimeMonitorSetting();
		param.setZjqdid(zjqdid);
		param.setZt(1);
		List<TimeMonitorSetting> list = timeMonitorSettingEditableMapper.select(param);
		for (TimeMonitorSetting setting : list) {
			// ta = 规定上限
			String ta = setting.getGdsx();
			// tb = 装机已用
			String tb = setting.getZjyy();
			// tc = 部件已用
			String tc = setting.getBjyy();
			// yc = 预拆值
			String yc = setting.getBjyc();
			if(MonitorItemEnum.isTime(setting.getJklbh())){	// 时间
				// yc = ta + tb - tc;
				String temp = StringAndDate_Util.operateTime(ta, tb, TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);
				yc = StringAndDate_Util.operateTime(temp, tc, TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);
			}else if(MonitorItemEnum.isLoop(setting.getJklbh())){	// 循环
				// yc = ta + tb - tc;
				BigDecimal temp = new BigDecimal(ta).add(new BigDecimal(tb));
				yc = temp.subtract(new BigDecimal(tc)).toString();
			}else{	// 日历
				continue;
			}
			setting.setBjyc(yc);
			// doUpdate
			timeMonitorSettingEditableMapper.updateByPrimaryKeySelective(setting);
		}
	} 
	
	/**
	 * 设置默认值
	 * @param ll
	 */
	private void setDefaultValue(ScheduledCheckMonitorItem item){
		// 当前登录用户
		User user = ThreadVarUtil.getUser();
		// 待同步
		item.setTbbs(SynchronzeEnum.TO_DO.getCode());
		// 维护人id
		item.setWhrid(user.getId());
		// 维护单位id
		item.setWhdwid(user.getBmdm());
		// 维护时间
		item.setWhsj(new Date());
		// 组织机构
		item.setDprtcode(user.getJgdm());
	}


	/**
	 * 删除监控项目
	 */
	@Override
	public void deleteEditable(String mainid, String czls, LogOperationEnum logOperationEnum,
			String zjqdid, boolean writeRec) throws RuntimeException {
		ScheduledCheckMonitorItem param = new ScheduledCheckMonitorItem();
		// 定检项目id
		param.setMainid(mainid);
		// 状态
		param.setZt(0);
		// 设置默认值
		setDefaultValue(param);
		// 根据监控项目删除
		scheduledCheckMonitorItemEditableMapper.deleteByMainid(param);
		if(writeRec){
			// 插入日志
			int num = commonRecService.writeByWhere(" b.zjqdid = '"+zjqdid+"' and b.zt = 1", 
					TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
					czls, logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
			if(num == 0){
				List<String> list = new ArrayList<String>();
				list.add(mainid);
				commonRecService.write("mainid", list, TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
						czls, logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
			}
		}
	}


	/**
	 * 编辑区-根据装机清单id删除监控项目
	 */
	@Override
	public void deleteByZjqdid(String zjqdid, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		ScheduledCheckMonitorItem param = new ScheduledCheckMonitorItem();
		// 装机清单id
		param.setZjqdid(zjqdid);
		// 状态
		param.setZt(0);
		// 设置默认值
		setDefaultValue(param);
		// 根据监控项目删除
		scheduledCheckMonitorItemEditableMapper.deleteByZjqdid(param);
		// 插入日志
		List<String> list = new ArrayList<String>();
		list.add(zjqdid);
		commonRecService.write("zjqdid", list, TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}


	/**
	 * 编辑区-级联删除监控项目
	 */
	@Override
	public void cascadeDeleteByZjqdid(String zjqdid, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		ScheduledCheckMonitorItem param = new ScheduledCheckMonitorItem();
		// 装机清单id
		param.setZjqdid(zjqdid);
		// 状态
		param.setZt(0);
		// 设置默认值
		setDefaultValue(param);
		// 根据监控项目删除
		scheduledCheckMonitorItemEditableMapper.cascadeDeleteByZjqdid(param);
		// 插入日志
		List<String> list = new ArrayList<String>();
		list.add(zjqdid);
		commonRecService.write("zjqdid", list, TableEnum.B_S_0010301, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
	}

}
