package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.WorkorderProcessHoursMapper;
import com.eray.thjw.produce.po.WorkorderProcessHours;
import com.eray.thjw.produce.service.WorkorderProcessHoursService;
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.service.CommonRecService;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/** 
 * @Description 工单工序列表
 * @CreateTime 2017-10-23 下午3:16:22
 * @CreateBy 雷伟	
 */
@Service
public class WorkorderProcessHoursServiceImpl implements WorkorderProcessHoursService {

	@Resource
	private WorkorderProcessHoursMapper processHoursMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 保存工单工序工时
	 * @CreateTime 2017-10-23 下午3:21:04
	 * @CreateBy 雷伟
	 * @param processList 工单工序列表
	 * @param user 当前用户
	 * @param gdid 工单ID
	 * @param currentDate 当前数据库时间
	 * @param czls 操作流水
	 * @param operation 操作类型
	 */
	@Override
	public void saveProcessHoursList(List<WorkorderProcessHours> processList,
			User user, String gdid, Date currentDate, String czls,
			LogOperationEnum operation) {
		
		if(processList == null || processList.size() <= 0) {
			return;
		}
		
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		for (WorkorderProcessHours processHours : processList) {
			String id = UUID.randomUUID().toString();
			processHours.setId(id);
			processHours.setMainid(gdid); //业务ID
			processHours.setZt(EffectiveEnum.YES.getId());//状态
			processHours.setWhdwid(user.getBmdm());
			processHours.setWhrid(user.getId());
			processHours.setWhsj(currentDate);
			columnValueList.add(id);
		}
		processHoursMapper.insert4Batch(processList);
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_S2_01402, user.getId(), czls, operation, UpdateTypeEnum.SAVE, gdid);
		}
	}

	/**
	 * @Description 根据mainId删除记录
	 * @CreateTime 2017-10-23 下午5:03:31
	 * @CreateBy 雷伟
	 * @param mainId
	 */
	@Override
	public void deleteByMainid(String mainId) {
		processHoursMapper.deleteByMainid(mainId);
	}

	@Override
	public List<WorkorderProcessHours> queryListByParam(WorkorderProcessHours processHours) {
		return processHoursMapper.queryListByParam(processHours);
	}
}
