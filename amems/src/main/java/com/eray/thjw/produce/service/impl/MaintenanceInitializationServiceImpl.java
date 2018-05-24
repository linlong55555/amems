package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.MonitoringCurrentMapper;
import com.eray.thjw.produce.dao.MonitoringLastMapper;
import com.eray.thjw.produce.dao.MonitoringObjectMapper;
import com.eray.thjw.produce.dao.MonitoringPlanMapper;
import com.eray.thjw.produce.dao.PlanUsageMapper;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringLast;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.po.PlanUsage;
import com.eray.thjw.produce.service.MaintenanceInitializationService;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.common.EffectiveEnum;

/**
 * @Description 维修计划初始化服务层
 * @CreateTime 2017-9-28 上午11:04:27
 * @CreateBy 刘兵
 */
@Service
public class MaintenanceInitializationServiceImpl implements MaintenanceInitializationService{

	@Resource
	private MonitoringCurrentMapper monitoringCurrentMapper;
	
	@Resource
	private MonitoringObjectMapper monitoringObjectMapper;
	
	@Resource
	private MonitoringLastMapper monitoringLastMapper;
	
	@Resource
	private MonitoringPlanMapper monitoringPlanMapper;
	
	@Resource
	private PlanUsageMapper planUsageMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 保存监控
	 * @CreateTime 2017-9-29 下午5:32:19
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return String
	 */
	@Override
	public String save(MonitoringObject monitoringObject){
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		Map<String, MonitoringLast> lastMap = new HashMap<String, MonitoringLast>();//用于存放已经存在数据库的上次执行数据
		List<String> jklbhList = new ArrayList<String>();//用于存放(计划)执行数据监控类编号
		MonitoringObject mo = monitoringObjectMapper.selecyById(monitoringObject.getId());
		if(mo.getMonitoringLastList() != null && mo.getMonitoringLastList().size() > 0){
			//将上次执行数据放入map集合
			for (MonitoringLast lastMonitoringLast : mo.getMonitoringLastList()) {
				lastMap.put(lastMonitoringLast.getJklbh(), lastMonitoringLast);
			}
		}
		monitoringObject.setWhrid(user.getId());
		monitoringObject.setWhbmid(user.getBmdm());
		//更新监控对象
		monitoringObjectMapper.updateByPrimaryKeySelective(monitoringObject);
		//保存历史记录信息
//		commonRecService.write(monitoringObject.getId(), TableEnum.B_S2_901, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, monitoringObject.getId());
		//更新当前监控数据
		MonitoringCurrent monitoringCurrent = new MonitoringCurrent();
		monitoringCurrent.setId(monitoringObject.getId());
		monitoringCurrent.setWhrid(user.getId());
		monitoringCurrent.setWhbmid(user.getBmdm());
		monitoringCurrentMapper.updateByPrimaryKeySelective(monitoringCurrent);
		//判断是否存在监控数据-(计划)执行数据
		if(monitoringObject.getMonitoringPlanList() != null && monitoringObject.getMonitoringPlanList().size() > 0){
			for (MonitoringPlan monitoringPlan : monitoringObject.getMonitoringPlanList()) {
				//如果前监控数据id为空,则可以更新上次执行数据
				if(StringUtils.isBlank(mo.getfJksjid())){
					MonitoringLast monitoringLast = monitoringPlan.getMonitoringLast();
					monitoringLast.setJksjid(monitoringObject.getId());
					monitoringLast.setWhrid(user.getId());
					monitoringLast.setWhdwid(user.getBmdm());
					//判断数据库是否存在监控项,存在则修改,不存在则新增
					if(lastMap.get(monitoringLast.getJklbh()) == null){
						//如果上次计划和上次实际有一个不为空,则新增上次执行数据
						if(StringUtils.isNotBlank(monitoringLast.getScjhz()) || StringUtils.isNotBlank(monitoringLast.getScsjz())){
							//新增监控数据-上次执行数据
							monitoringLast.setId(UUID.randomUUID().toString());
							monitoringLast.setZt(EffectiveEnum.YES.getId());
							monitoringLastMapper.insertSelective(monitoringLast);
//							commonRecService.write(monitoringLast.getId(), TableEnum.B_S2_903, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.SAVE, monitoringObject.getId());
						}
					}else{
						monitoringLast.setId(lastMap.get(monitoringLast.getJklbh()).getId());
						//如果上次计划和上次实际有一个不为空,则编辑上次执行数据,否则删除上次执行数据
						if(StringUtils.isNotBlank(monitoringLast.getScjhz()) || StringUtils.isNotBlank(monitoringLast.getScsjz())){
							//更新监控数据-上次执行数据
							monitoringLastMapper.updateById(monitoringLast);
//							commonRecService.write(monitoringLast.getId(), TableEnum.B_S2_903, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, monitoringObject.getId());
						}else{
//							commonRecService.write(monitoringLast.getId(), TableEnum.B_S2_903, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.DELETE, monitoringObject.getId());
							monitoringLastMapper.deleteByPrimaryKey(monitoringLast.getId());
						}
					}
					jklbhList.add(monitoringLast.getJklbh());
				}
				//更新监控数据-(计划)执行数据
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlanMapper.updateByPrimaryKeySelective(monitoringPlan);
//				commonRecService.write(monitoringPlan.getId(), TableEnum.B_S2_904, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, monitoringObject.getId());
			}
			//如果监控类编号在计划执行数据中不存在则删除
			if(jklbhList.size() > 0 && mo.getMonitoringLastList() != null && mo.getMonitoringLastList().size() > 0){
				for (MonitoringLast lastMonitoringLast : mo.getMonitoringLastList()) {
					if(!jklbhList.contains(lastMonitoringLast.getJklbh())){
//						commonRecService.write(lastMonitoringLast.getId(), TableEnum.B_S2_903, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.DELETE, monitoringObject.getId());
						monitoringLastMapper.deleteByPrimaryKey(lastMonitoringLast.getId());
					}
				}
			}
		}
		return monitoringObject.getId();
	}
	
	/**
	 * @Description 根据监控数据id查询任务信息(维修项目)
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 */
	@Override
	public MonitoringCurrent selectRelProjectById(String id){
		return monitoringCurrentMapper.selectRelProjectById(id);
	}
	
	/**
	 * @Description 根据监控数据id查询任务信息(EO)
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 */
	@Override
	public MonitoringCurrent selectRelEOById(String id){
		return monitoringCurrentMapper.selectRelEOById(id);
	}
	
	/**
   	 * @Description 根据监控数据id查询任务信息(PO)
   	 * @CreateTime 2018-5-7 下午5:02:39
   	 * @CreateBy 刘兵
   	 * @param id 监控数据id
   	 * @return MonitoringCurrent 监控数据
   	 */
	@Override
	public MonitoringCurrent selectRelPoById(String id){
		return monitoringCurrentMapper.selectRelPoById(id);
	}
	
	/**
	 * @Description 根据监控数据id查询监控数据-上次执行数据
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return List<MonitoringLast> 监控数据-上次执行数据集合
	 */
	@Override
	public List<MonitoringLast> queryMonitoringLastById(String id){
		return monitoringLastMapper.queryByJksjid(id);
	}
	
	/**
	 * @Description 根据监控数据id查询监控对象及监控数据-上次执行数据
	 * @CreateTime 2017-9-30 上午9:42:15
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringObject 监控对象
	 */
	@Override
	public MonitoringObject getMonitoringObjectById(String id){
		return monitoringObjectMapper.selecyById(id);
	}
	
	/**
	 * @Description 根据监控数据id查询监控数据-(计划)执行数据
	 * @CreateTime 2017-9-29 上午11:41:22
	 * @CreateBy 刘兵
	 * @param jksjid 监控数据id
	 * @return List<MonitoringPlan> 监控数据-(计划)执行数据
	 */
	@Override
	public List<MonitoringPlan> queryMonitoringPlanByJksjid(String id){
		return monitoringPlanMapper.queryByJksjid(id);
	}

	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPageMaintenanceList(MonitoringCurrent monitoringCurrent) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = monitoringCurrent.getId();
		monitoringCurrent.setId(null);
		PageHelper.startPage(monitoringCurrent.getPagination());
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryAllPageMaintenanceList(monitoringCurrent);
		if (((Page) list).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(list, id)) {
					// 在查询条件中增加ID
					MonitoringCurrent newRecord = new MonitoringCurrent();
					newRecord.setId(id);
					newRecord.setDprtcode(monitoringCurrent.getDprtcode());
					newRecord.getParamsMap().put("fjzch", monitoringCurrent.getParamsMap().get("fjzch"));
					List<MonitoringCurrent> newRecordList = monitoringCurrentMapper.queryAllPageMaintenanceList(monitoringCurrent);
					if (newRecordList != null && newRecordList.size() == 1) {
						list.add(0, newRecordList.get(0));
					}
				}
			}
		}
		resultMap = PageUtil.pack4PageHelper(list, monitoringCurrent.getPagination());
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		resultMap.put("planUsageList", planUsageToMap(planUsageList));
		resultMap.put("rows", monitoringCurrentmaintenanceToMap(list));
		return resultMap;
	}
	
	/**
	 * @Description 维修监控对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> monitoringCurrentmaintenanceToMap(List<MonitoringCurrent> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (MonitoringCurrent monitoringCurrent : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			MaintenanceProject maintenanceProject = monitoringCurrent.getMaintenanceProject();
			if(null != maintenanceProject){
				monitoringCurrent.getParamsMap().put("id", maintenanceProject.getId());
				monitoringCurrent.getParamsMap().put("wxxmlx", maintenanceProject.getWxxmlx());
				monitoringCurrent.getParamsMap().put("rwh", maintenanceProject.getRwh());
				monitoringCurrent.getParamsMap().put("bb", maintenanceProject.getBb());
				monitoringCurrent.getParamsMap().put("ckh", maintenanceProject.getCkh());
				monitoringCurrent.getParamsMap().put("rwms", maintenanceProject.getRwms());
				monitoringCurrent.getParamsMap().put("gzlx", maintenanceProject.getGzlx());
				monitoringCurrent.getParamsMap().put("isHdwz", maintenanceProject.getIsHdwz());
			}
			resultMap.put("id", monitoringCurrent.getId());
			resultMap.put("dprtcode", monitoringCurrent.getDprtcode());
			resultMap.put("bjh", monitoringCurrent.getBjh());
			resultMap.put("xlh", monitoringCurrent.getXlh());
			resultMap.put("whsj", monitoringCurrent.getWhsj());
			resultMap.put("paramsMap", monitoringCurrent.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 飞机日使用量监控指标对象转map
	 * @CreateTime 2018-4-11 下午2:07:26
	 * @CreateBy 刘兵
	 * @param planUsageList
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> planUsageToMap(List<PlanUsage> planUsageList){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (PlanUsage planUsage : planUsageList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("jklbh", planUsage.getJklbh());
			resultMap.put("rsyl", planUsage.getRsyl());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPageEOList(MonitoringCurrent monitoringCurrent) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = monitoringCurrent.getId();
		monitoringCurrent.setId(null);
		PageHelper.startPage(monitoringCurrent.getPagination());
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryAllPageEOList(monitoringCurrent);
		if (((Page) list).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(list, id)) {
					// 在查询条件中增加ID
					MonitoringCurrent newRecord = new MonitoringCurrent();
					newRecord.setId(id);
					newRecord.setDprtcode(monitoringCurrent.getDprtcode());
					newRecord.getParamsMap().put("fjzch", monitoringCurrent.getParamsMap().get("fjzch"));
					List<MonitoringCurrent> newRecordList = monitoringCurrentMapper.queryAllPageEOList(monitoringCurrent);
					if (newRecordList != null && newRecordList.size() == 1) {
						list.add(0, newRecordList.get(0));
					}
				}
			}
		}
		resultMap = PageUtil.pack4PageHelper(list, monitoringCurrent.getPagination());
		
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		resultMap.put("planUsageList", planUsageToMap(planUsageList));
		resultMap.put("rows", monitoringCurrentEOToMap(list));
		return resultMap;
	}
	
	/**
	 * @Description EO对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> monitoringCurrentEOToMap(List<MonitoringCurrent> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (MonitoringCurrent monitoringCurrent : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			EngineeringOrder engineeringOrder = monitoringCurrent.getEngineeringOrder();
			if(null != engineeringOrder){
				monitoringCurrent.getParamsMap().put("id", engineeringOrder.getId());
				monitoringCurrent.getParamsMap().put("eobh", engineeringOrder.getEobh());
				monitoringCurrent.getParamsMap().put("bb", engineeringOrder.getBb());
				monitoringCurrent.getParamsMap().put("eozt", engineeringOrder.getEozt());
				monitoringCurrent.getParamsMap().put("hdwz", engineeringOrder.getHdwz());
				monitoringCurrent.getParamsMap().put("zxfs", engineeringOrder.getZxfs());
			}
			resultMap.put("id", monitoringCurrent.getId());
			resultMap.put("dprtcode", monitoringCurrent.getDprtcode());
			resultMap.put("bjh", monitoringCurrent.getBjh());
			resultMap.put("xlh", monitoringCurrent.getXlh());
			resultMap.put("whsj", monitoringCurrent.getWhsj());
			resultMap.put("eoxc", monitoringCurrent.getEoxc());
			resultMap.put("paramsMap", monitoringCurrent.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPagePOList(MonitoringCurrent monitoringCurrent) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = monitoringCurrent.getId();
		monitoringCurrent.setId(null);
		PageHelper.startPage(monitoringCurrent.getPagination());
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryAllPagePOList(monitoringCurrent);
		if (((Page) list).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(list, id)) {
					// 在查询条件中增加ID
					MonitoringCurrent newRecord = new MonitoringCurrent();
					newRecord.setId(id);
					newRecord.setDprtcode(monitoringCurrent.getDprtcode());
					newRecord.getParamsMap().put("fjzch", monitoringCurrent.getParamsMap().get("fjzch"));
					List<MonitoringCurrent> newRecordList = monitoringCurrentMapper.queryAllPageEOList(monitoringCurrent);
					if (newRecordList != null && newRecordList.size() == 1) {
						list.add(0, newRecordList.get(0));
					}
				}
			}
		}
		resultMap = PageUtil.pack4PageHelper(list, monitoringCurrent.getPagination());
		
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		resultMap.put("planUsageList", planUsageToMap(planUsageList));
		resultMap.put("rows", monitoringCurrentPOToMap(list));
		return resultMap;
	}
	
	/**
	 * @Description EO对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> monitoringCurrentPOToMap(List<MonitoringCurrent> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (MonitoringCurrent monitoringCurrent : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", monitoringCurrent.getId());
			resultMap.put("dprtcode", monitoringCurrent.getDprtcode());
			resultMap.put("whsj", monitoringCurrent.getWhsj());
			resultMap.put("paramsMap", monitoringCurrent.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
}
