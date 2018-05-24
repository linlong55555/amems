package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.dao.WorkOrderIORecordMapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.TaskhistoryService;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.github.pagehelper.PageHelper;

import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.MonitorProjectEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-9 上午11:29:59
 * @CreateBy 孙霁	
 */
@Service 
public class TaskhistoryServiceImpl implements TaskhistoryService{

	@Resource
	private WorkorderMapper workorderMapper;
	@Resource
	private WorkOrderIORecordMapper workOrderIORecordMapper;
	/**

	 * 
	 * @Description 条件查询历史任务主列表
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return List<Workorder>
	 */
	@Override
	public Map<String, Object> queryAll(Workorder workorder) {
		PageHelper.startPage(workorder.getPagination());
		List<Workorder> list = workorderMapper.queryAllPerformHistory(workorder);
		return PageUtil.pack4PageHelper(list, workorder.getPagination());
	}
	/**
	 * 
	 * @Description 根据工单id获取拆换件记录
	 * @CreateTime 2017-10-10 下午3:50:04
	 * @CreateBy 孙霁
	 * @param mainid
	 * @return
	 */
	@Override
	public List<WorkOrderIORecord> queryAllRecordByGdid(String mainid) {
		return workOrderIORecordMapper.queryAllByGdid(mainid);
	}
	/**
	 * 
	 * @Description 根据gdid获取工单主数据
	 * @CreateTime 2017-10-10 下午5:49:09
	 * @CreateBy 孙霁
	 * @param mainid
	 * @return
	 */
	@Override
	public Workorder selectGdBygdid(String gdid) {
		return workorderMapper.selectById(gdid);
	}
	@Override
	public List<WorkOrderIORecord> queryAllRecord145ByGdid(String gdid) {
		return workOrderIORecordMapper.queryAllRecord145ByGdid(gdid);
	}
	/**
	 * 
	 * @Description 获取导出数据
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	@Override
	public List<Workorder> getList(Workorder record) {
		
		List<Workorder> workorderList = workorderMapper.queryAllPerformHistory(record);
		StringBuffer str = new StringBuffer();
		for (Workorder workorder : workorderList) {
			str.delete(0,str.length());
			//状态
			workorder.getParamsMap().put("ztText", WorkorderStatusEnum.getName(workorder.getZt()));
			//工单类型
			if(workorder.getGdlx() == WorkorderTypeEnum.RTN.getId()){
				workorder.getParamsMap().put("gdlxText", MaintenanceProjectTypeEnum.getName(Integer.valueOf(workorder.getParamsMap().get("gdzlx").toString())));	
			}else{
				workorder.getParamsMap().put("gdlxText", WorkorderTypeEnum.getName(workorder.getGdlx()));	
			}
			//任务bb
			if(workorder.getParamsMap().get("rwbb") != null && !"".equals(workorder.getParamsMap().get("rwbb"))){
				str.append(workorder.getParamsMap().get("rwh")).append(" R").append(workorder.getParamsMap().get("rwbb"));
				workorder.getParamsMap().put("rwh",str.toString());
			}
			formatLastfjjlb(workorder);
			formatLastData(workorder);
		}
		
		return workorderList;
	}
	/**
	 * @Description 格式化飞行记录本
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy sunji
	 * @param workorder 
	 * @throws BusinessException
	 */
	private void formatLastfjjlb(Workorder workorder){
		if(null != workorder.getParamsMap().get("fxjlbxx")){
			StringBuffer jlbbh = new StringBuffer();
			StringBuffer jlbym =  new StringBuffer();
			String[] arr1 = String.valueOf(workorder.getParamsMap().get("fxjlbxx")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				jlbbh.append( arr2[1]);
				jlbym.append( arr2[2]);
				if(i != arr1.length - 1){
					jlbbh.append("\n");
					jlbym.append("\n");
				}
			}
			workorder.getParamsMap().put("jlbbh", jlbbh);
			workorder.getParamsMap().put("jlbym", jlbym);
		}
	}
	
	/**
	 * @Description 格式化上次数据
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	private void formatLastData(Workorder workorder){
		if(null != workorder.getParamsMap().get("jhsjsj")){
			StringBuffer scjh = new StringBuffer();
			StringBuffer scsj =  new StringBuffer();
			String[] arr1 = String.valueOf(workorder.getParamsMap().get("jhsjsj")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				scjh.append(formatJkz(arr2[0], arr2[1], " ", "", true));
				scsj.append(formatJkz(arr2[0], arr2[2], " ", "", true));
				if(i != arr1.length - 1){
					scjh.append("\n");
					scsj.append("\n");
				}
			}
			workorder.getParamsMap().put("scjh", scjh);
			workorder.getParamsMap().put("scsj", scsj);
		}
	}
	
	/**
	 * @Description 格式化监控值
	 * @CreateTime 2017-12-20 上午9:50:09
	 * @CreateBy 刘兵
	 * @param jklbh 监控类编号
	 * @param value 监控值
	 * @param backupValue 备用值
	 * @param dw 单位
	 * @param addDw 是否增加单位
	 * @return 监控值
	 */
	private String formatJkz(String jklbh, String value, String backupValue, String dw, boolean addDw){
		StringBuffer jkz = new StringBuffer();
		if(StringUtils.isNotBlank(value)){
			//判断是否是时间格式
			if(MonitorProjectEnum.isTime(jklbh)){
				value = StringAndDate_Util.convertToHour(value);
			}
		}else{
			value = backupValue;
		}
		jkz.append(value);
		if(addDw && !" ".equals(value)){
			jkz.append(MonitorProjectEnum.getUnit(jklbh, dw));
		}
		return jkz.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
