package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.EOMonitorIemSetMapper;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.service.EOMonitorIemSetService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.MonitorProjectEnum;

/** 
 * @Description EO-监控项设置 业务实现
 * @CreateTime 2017-8-23 上午10:23:29
 * @CreateBy 雷伟	
 */
@Service
public class EOMonitorIemSetServiceImpl implements EOMonitorIemSetService {
	@Resource
	private EOMonitorIemSetMapper eoMonitorIemSetMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 新增EO-监控项设置
	 * @CreateTime 2017-8-23 上午10:16:03
	 * @CreateBy 雷伟
	 * @param jkxszList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	@Override
	public void saveMonitorIemSetList(List<EOMonitorIemSet> jkxszList,User user, String eoId, Date currentDate, String czls,LogOperationEnum operation) {
		if(jkxszList == null || jkxszList.size() <= 0) {
			return;
		}
		
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		for (EOMonitorIemSet eoMonitorIemSet : jkxszList) {
			String id = UUID.randomUUID().toString();
			eoMonitorIemSet.setId(id);
			eoMonitorIemSet.setMainid(eoId); //业务ID
			eoMonitorIemSet.setZt(EffectiveEnum.YES.getId());//状态
			eoMonitorIemSet.setWhbmid(user.getBmdm());
			eoMonitorIemSet.setWhrid(user.getId());
			eoMonitorIemSet.setWhsj(currentDate);
			this.switchHourToMinute(eoMonitorIemSet);// 小时转换为分钟
			columnValueList.add(id);
		}
		eoMonitorIemSetMapper.insert4Batch(jkxszList);
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_01002, user.getId(), czls, operation, UpdateTypeEnum.SAVE, eoId);
		}
	}

	/**
	 * @Description 查询EO监控项设置 
	 * @CreateTime 2017-8-24 下午8:07:12
	 * @CreateBy 雷伟
	 * @param eoMonitorIemSet
	 * @return
	 */
	@Override
	public List<EOMonitorIemSet> queryAllList(EOMonitorIemSet eoMonitorIemSet) {
		return eoMonitorIemSetMapper.queryAllList(eoMonitorIemSet);
	}
	
	
	/**
	 * @Description 小时转换为分钟
	 * @CreateTime 2017年8月30日 下午7:03:02
	 * @CreateBy 雷伟
	 * @param monitor
	 */
	private void switchHourToMinute(EOMonitorIemSet monitor){
		// 是时间类型的监控项目
		if(MonitorProjectEnum.isTime(monitor.getJklbh())){
			
			monitor.setScz(StringAndDate_Util.convertToMinuteStr(monitor.getScz()));
			
			monitor.setZqz(StringAndDate_Util.convertToMinuteStr(monitor.getZqz()));
			
			monitor.setYdzQ(StringAndDate_Util.convertToMinuteStr(monitor.getYdzQ()));
			
			monitor.setYdzH(StringAndDate_Util.convertToMinuteStr(monitor.getYdzH()));
		}
	}

	@Override
	public void deleteByMainid(EngineeringOrder engineeringOrder,User user, Date currentDate, String czls, LogOperationEnum operation) {
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_01002, user.getId(), czls, operation, UpdateTypeEnum.DELETE, engineeringOrder.getId());
		eoMonitorIemSetMapper.deleteByMainid(engineeringOrder.getId());
	}

	@Override
	public List<EOMonitorIemSet> queryByMainid(String mainid) {
		
		return eoMonitorIemSetMapper.queryByMainid(mainid);
	}
	
	
}
