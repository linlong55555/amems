package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProductionOrderMonitorMapper;
import com.eray.thjw.project2.po.ProductionOrder;
import com.eray.thjw.project2.po.ProductionOrderMonitor;
import com.eray.thjw.project2.service.ProductionOrderMonitorService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 生产指令-监控项设置服务实现类
 * @CreateTime 2018年5月4日 上午9:47:10
 * @CreateBy 韩武
 */
@Service
public class ProductionOrderMonitorServiceImpl implements ProductionOrderMonitorService {
	
	@Resource
	private ProductionOrderMonitorMapper productionOrderMonitorMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存生产指令-监控项设置
	 * @CreateTime 2018年5月4日 上午9:47:43
	 * @CreateBy 韩武
	 * @param order
	 */
	@Override
	public void save(ProductionOrder order) {
		User user = ThreadVarUtil.getUser();
		// 删除生产指令-监控项设置
		delete(order);
		// 保存生产指令-监控项设置
		if(order.getMonitors() != null){
			List<String> idList = new ArrayList<String>();
			for (ProductionOrderMonitor monitor : order.getMonitors()) {
				monitor.setId(UUID.randomUUID().toString());
				monitor.setMainid(order.getId());
				monitor.setZt(EffectiveEnum.YES.getId());
				monitor.setWhrid(user.getId());
				monitor.setWhsj(new Date());
				monitor.setWhbmid(user.getBmdm());
				// 小时转换为分钟
				switchHourToMinute(monitor);
				idList.add(monitor.getId());
				productionOrderMonitorMapper.insertSelective(monitor);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01401, user.getId(), 
						order.getCzls(), order.getLogOperationEnum(), UpdateTypeEnum.SAVE, order.getId());
			}
		}
	}

	/**
	 * @Description 删除生产指令-监控项设置
	 * @CreateTime 2018年5月4日 上午9:56:28
	 * @CreateBy 韩武
	 * @param order
	 */
	@Override
	public void delete(ProductionOrder order) {
		User user = ThreadVarUtil.getUser();
		List<String> delList = new ArrayList<String>();
		delList.add(order.getId());
		// 保存历史记录信息
		commonRecService.write("mainid", delList, TableEnum.B_G2_01401, user.getId(), 
				order.getCzls(), order.getLogOperationEnum(), UpdateTypeEnum.DELETE, order.getId());
		// 删除生产指令-监控项设置
		productionOrderMonitorMapper.deleteByMainid(order.getId());
	}
	
	/**
	 * @Description 小时转换为分钟
	 * @CreateTime 2018年5月4日 上午10:00:54
	 * @CreateBy 韩武
	 * @param monitor
	 */
	private void switchHourToMinute(ProductionOrderMonitor monitor){
		
		// 是时间类型的监控项目
		if(MonitorProjectEnum.isTime(monitor.getJklbh())){
			
			monitor.setScz(StringAndDate_Util.convertToMinuteStr(monitor.getScz()));
			
			monitor.setZqz(StringAndDate_Util.convertToMinuteStr(monitor.getZqz()));
			
			monitor.setYdzQ(StringAndDate_Util.convertToMinuteStr(monitor.getYdzQ()));
			
			monitor.setYdzH(StringAndDate_Util.convertToMinuteStr(monitor.getYdzH()));
		}
	}

}
