package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.dao.MonitoringCurrentMapper;
import com.eray.thjw.produce.dao.PlanUsageMapper;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.po.PlanUsage;
import com.eray.thjw.produce.service.ProductionForecastService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.Utils.ObjUtil;

import enu.BinaryEnum;
import enu.project2.ExecutionEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.MonitorProjectUnitEnum;

@Service
public class ProductionForecastServiceImpl implements ProductionForecastService {

	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;
	
	@Resource
	private  CommonMapper commonMapper;
	
	@Resource
	private  PlanUsageMapper planUsageMapper;
	
	@Resource
	private  MonitoringCurrentMapper monitoringCurrentMapper;
	
	
	
	@Override
	public Map<String, Object> calculationCurrent(AircraftinfoStatus aircraftinfoStatus) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = commonMapper.getSysdate();
		List<AircraftinfoStatus> list = aircraftinfoStatusMapper.selectList4forecast(aircraftinfoStatus);
		if (list!=null && !list.isEmpty()) {
			//mapCode= 监控类+位置 ，//非发动机，位置统一为‘-’，发动机，位置保持不变。
			for (AircraftinfoStatus status : list) {
				status.getParamsMap().put("val", calculationCurrent4Item(status,date));
			}
			
			//默认情况下取监控类别号第二位做排序字段，如果是发动机就按监控类别号排序字段，确保以下顺序。
			//			飞行小时，飞行循环，APU小时，APU循环，
			//			1#发小时，1#发循环，
			//			2#发小时，2#发循环，
			//			3#发小时，3#发循环，
			Collections.sort(list, new Comparator<AircraftinfoStatus>() {
				@Override
				public int compare(AircraftinfoStatus fir, AircraftinfoStatus sec) {
					String sortFir = fir.getJklbh().split("_")[1];
					String sortSec = sec.getJklbh().split("_")[1];
					if (MonitorProjectEnum.isEngine(fir.getJklbh())) {
						sortFir = sortFir.concat(fir.getWz().toString());
					} 
					if (MonitorProjectEnum.isEngine(sec.getJklbh())) {
						sortSec = sortSec.concat(sec.getWz().toString());
					} 
					return sortFir.compareTo(sortSec);
				}
			});
			
		} 

		//查日使用量
		String currentDate = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, date);
		Map<String, String>planUsageMap = getPlanUsageMap(aircraftinfoStatus.getFjzch(), aircraftinfoStatus.getDprtcode());
		map.put("currentDate", currentDate);
		map.put("planUsageMap", planUsageMap);	
		map.put("currents", list);	
		return map;
	}
	
	/**
	 * 
	 * @Description 获取监控类编号与日使用量的映射表
	 * @CreateTime 2017年10月13日 上午9:57:50
	 * @CreateBy 朱超
	 * @param fjzch
	 * @param drptcode
	 * @return
	 */
	private Map<String, String> getPlanUsageMap(String fjzch,String drptcode) {
		Map<String, String> planUsageMap = new HashMap<String, String>();
		List<PlanUsage>planUsageList = planUsageMapper.queryByFjzch(fjzch, drptcode);
		for (PlanUsage planUsage : planUsageList) {
			if ((StringUtils.isBlank(planUsage.getRsyl()))) {
				planUsage.setRsyl("0");
			}
			planUsageMap.put(planUsage.getJklbh(), planUsage.getRsyl());
		}
		return planUsageMap;
	}
	
 

	/**
	 * 
	 * @Description 计算当前值=
	 * @CreateTime 2017年10月11日 下午4:23:07
	 * @CreateBy 朱超
	 * @param status
	 * @param date
	 * @return
	 */
	private Object calculationCurrent4Item(AircraftinfoStatus status,Date date) {
		//默认循环监控类
		if (null == status.getCsz()) {
			status.setCsz(0);
		}
		if (null == status.getLjz()) {
			status.setLjz(0);
		}
		
		Integer current = status.getLjz();
		String currentStr = current.toString();
		if(MonitorProjectEnum.isTime(status.getJklbh())) {
			//如果是时间类需要格式化小时分钟。
			currentStr = StringAndDate_Util.convertToHour(String.valueOf(current));
		} 
		else if(MonitorProjectEnum.isCalendar(status.getJklbh())) {
			//日历日
			currentStr = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, date);
		}
		return currentStr;
	}
	
	
	@Override
	public Map<String, Object> queryList(MonitoringCurrent record) throws BusinessException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		//合并集合（维修项目，EO，生产指令）
		List<Map<String, Object>> mergelist = new ArrayList<Map<String, Object>>();
		//首次监控数据集合
		List<Map<String, Object>> destlistFir = new ArrayList<Map<String, Object>>();
		//首次小于目标值的map<jksjid,监控数据>
		Map<String, Map<String, Object>> jkdataMap = new HashMap<String, Map<String, Object>>();
		//维修项目集合
		List<Map<String, Object>> mplist = monitoringCurrentMapper.selectAll4Mp(record);
		//EO集合
		List<Map<String, Object>> eolist = monitoringCurrentMapper.selectAll4Eo(record);
		//生产指令集合
		List<Map<String, Object>> polist = monitoringCurrentMapper.selectAll4Po(record);
		//日使用里
		Map<String, String> planUsageMap = getPlanUsageMap(record.getFjzch(), record.getDprtcode());
		//当前日期
		Date date = commonMapper.getSysdate();
		
		//将三个数据合并成一个集合
		mergelist.addAll(mplist);
		mergelist.addAll(eolist);
		mergelist.addAll(polist);
		
		//1、获取当前监控数据
		getMonitordata(record, mergelist, planUsageMap, date,destlistFir,jkdataMap);
		
		//2、维修监控演变
		calculationMonitordata(record, destlistFir, jkdataMap, planUsageMap, date);
		
		map.put("list", destlistFir);
		return map;
	}

		
	/**
	 * @Description 将监控项单位转换为日历单位
	 * @CreateTime 2017年10月10日 下午8:21:52
	 * @CreateBy 徐勇
	 * @param unit 监控项单位
	 * @return
	 * @throws ParseException 
	 */
	public int getOffsetUnit(int unit) throws ParseException{
		if(unit == MonitorProjectUnitEnum.DAY.getCode()){
			return Calendar.DATE;
		}else if(unit == MonitorProjectUnitEnum.MONTH.getCode()){
			return Calendar.MONTH;
		}
		throw new ParseException(String.valueOf(unit), 0);
	}
		
	/**
	 * 
	 * @Description 监控数据演变
	 * @CreateTime 2017年10月16日 下午2:45:16
	 * @CreateBy 朱超
	 * @param record
	 * @param destlistFir
	 * @param destlistSec
	 * @param jkdataMap
	 * @param planUsageMap
	 * @param date
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void calculationMonitordata(MonitoringCurrent record, List<Map<String, Object>> destlistFir,
			Map<String, Map<String, Object>> jkdataMap, 
			Map<String, String> planUsageMap, Date date)
			throws BusinessException {
		if (!destlistFir.isEmpty()) {
			
			try {
				//遍历每一条监控数据
				for (Iterator<Map<String, Object>> iterator = destlistFir.iterator(); iterator.hasNext();) {
					//计算每一个监控项目的剩余值的预计剩余天数
					Map<String, Object> targetParam = record.getParamsMap();//目标值映射表
					Map<String, Object> item = iterator.next();//当前监控数据
					String jksjid = item.get("JKSJID").toString();//监控数据ID
					final String isHdwz = item.get("IS_HDWZ").toString();//后到为准
					//是否EO单次/分段
					if(null != item.get("ZXFS") && (ExecutionEnum.isEoOnceOrSection(item.get("ZXFS").toString()))) {
						jkdataMap.remove(jksjid);//移除map中的数据
						//iterator.remove();
						continue;
					}
					else {
						boolean leTarget = Boolean.TRUE;//小于等于目标值
							
						//获取缓存数据
						Map<String, Object> itemInMap = jkdataMap.get(jksjid);
						String jhrq = item.get("JHRQ").toString();
						//剔除周期值为空的监控项目
						this.eliminateJkxm4NPeriodic(itemInMap,jhrq,planUsageMap);
						//获取最值（根据后到为准而定）
						MonitoringPlan plan = this.getLimitPlan(itemInMap, isHdwz);
						
						//当获取最值为空时，表示没有监控项有周期
						if(plan == null){
							continue;
						}
						
						leTarget = this.isLeTarget(plan, targetParam);
						//小于等于目标值,将该监控数据整个剔除出集合，克隆原始数据到Map
						if(leTarget){
							
							Map<String, Object> itemClone = ObjUtil.deepClone(itemInMap, Map.class);
							//1.计算 计划日期=当前日期+剩余天数，
							itemClone.put("JHRQ", calculationPlanDate(date, plan));
							
							//2.加入子列表结果集合
							itemClone.put("limit_jklbh", plan.getJklbh());
							addSubItem(item,itemClone);
							//3.继续演算
							planningCalculus(item, planUsageMap, date, targetParam, itemInMap);
							
						}
					}
				//end iterator
					//4872 [飞机维修预测]列表中查询出的数据如果为0时，这条数据不应显示，查询条件字段不正确
					/*if (item.get("sublist") != null ) {
						ArrayList<Map<String,Object>> sublist = (ArrayList<Map<String,Object>>)item.get("sublist");	
						if ( sublist.isEmpty()) {
							iterator.remove();
						}
					}*/
					 
					//4872 [飞机维修预测]列表中查询出的数据如果为0时，这条数据不应显示，查询条件字段不正确
				}
			} catch (ParseException e) {
				throw new BusinessException("日期计算失败", e);
			}
			
			
		}
	}

	/**
	 * 
	 * @Description 循环演算计划
	 * @CreateTime 2017年10月24日 下午4:14:17
	 * @CreateBy 朱超
	 * @param item
	 * @param planUsageMap
	 * @param date
	 * @param targetParam
	 * @param leTarget
	 * @param itemInMap
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void planningCalculus(Map<String, Object> item,Map<String, String> planUsageMap, Date date,
			Map<String, Object> targetParam, Map<String, Object> itemInMap)
			throws BusinessException {
		try {
			boolean leTarget = Boolean.TRUE;
			final String isHdwz = item.get("IS_HDWZ").toString();//后到为准
			do {
				List<MonitoringPlan> list = this.getMonitoringPlans(itemInMap);
				for (Iterator<MonitoringPlan> ite = list.iterator(); ite.hasNext();) {
					MonitoringPlan monitoringPlan = (MonitoringPlan) ite.next();
					
					//计算每个监控项的：剩余值
					this.calculateResidualValue(monitoringPlan);
					//计算每个监控项的：剩余天数
					this.calculateRemainDays(monitoringPlan,planUsageMap);
					//获取最值（根据后到为准而定）
					MonitoringPlan planNew = this.getLimitPlan(itemInMap, isHdwz);
					leTarget = this.isLeTarget(planNew, targetParam);
					//小于等于目标值继续演变，否则结束本层循环。
					if (leTarget) {
						Map<String, Object> itemNew = ObjUtil.deepClone(itemInMap, Map.class);
						//1.计算 计划日期=当前日期+剩余天数，
						itemNew.put("JHRQ", calculationPlanDate(date, planNew));
						//2.加入子列表结果集合
						itemNew.put("limit_jklbh", planNew.getJklbh());
						addSubItem(item,itemNew);
						itemInMap.put("SYTS", "");
					}
					else {
						break;
					}
				}
				
			} while (leTarget);
		} catch (ParseException e) {
			throw new BusinessException("日期计算失败!", e);
		}
	}

	/**
	 * 
	 * @Description 剔除周期值为空的监控项目
	 * @CreateTime 2017年10月24日 下午2:37:40
	 * @CreateBy 朱超
	 * @param monitoringPlan
	 * @param jhrq
	 * @throws BusinessException 
	 * 
	 */
	private void eliminateJkxm4NPeriodic(Map<String, Object> itemInMap,String jhrq,Map<String, String>planUsageMap ) throws BusinessException {
		try {
			List<MonitoringPlan> list = this.getMonitoringPlans(itemInMap);
			if ((null != list) && (!list.isEmpty())) {
				for (Iterator<MonitoringPlan> iterator = list.iterator(); iterator.hasNext();) {
					MonitoringPlan monitoringPlan = (MonitoringPlan) iterator.next();
					if (StringUtils.isBlank(monitoringPlan.getZqz())) {
						iterator.remove();
					}
					else{
						//是否存在计划值为空，并且周期值不为空的情况： 
						//此监控项目的计划值=周期值，如果是日历日，那么等于计划日期+周期值（注意单位）
						this.initPlannedValue(jhrq, monitoringPlan);
						//计算每个监控项的：剩余值
						this.calculateResidualValue(monitoringPlan);
						//计算每个监控项的：剩余天数
						this.calculateRemainDays(monitoringPlan,planUsageMap);
					}
				}
			}
		} catch (ParseException e) {
			throw new BusinessException("日期格式化失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 计算剩余天数
	 * @CreateTime 2017年10月24日 下午2:32:34
	 * @CreateBy 朱超
	 * @param monitoringPlan
	 * @param planUsageMap
	 */
	private void calculateRemainDays(MonitoringPlan monitoringPlan, Map<String, String> planUsageMap) {
		String jklbh = monitoringPlan.getJklbh();//监控类编号
		String syz = monitoringPlan.getParamsMap().get("SYZ").toString();//剩余值
		Integer syts = 0;
		if (StringUtils.isBlank(syz) || syz.equals("0")) {
			syts = 0;
		}
		else {
			//日历：日使用量=1
			String rsyl = MonitorProjectEnum.isCalendar(jklbh) ? "1": planUsageMap.get(jklbh);
			
			//当日使用量为空，或0时，默认给1，时间类型给60
			if (StringUtils.isEmpty(rsyl) || ("0".equals(rsyl))) {
				rsyl = "1";
				if (MonitorProjectEnum.isTime(jklbh)) {
					rsyl = "60";
				}
			}
			// syts = Integer.valueOf(syz) /Integer.valueOf(rsyl) ;
			syts = new BigDecimal(syz).divide(new BigDecimal(rsyl), 0, RoundingMode.FLOOR).intValue();
		}
		monitoringPlan.getParamsMap().put("SYTS", syts);
	}
	
	/**
	 * 
	 * @Description 计算计划值，剩余值
	 * @CreateTime 2017年10月24日 下午2:27:23
	 * @CreateBy 朱超
	 * @param monitoringPlan
	 * @param isFirst
	 * @throws ParseException
	 */
	private void calculateResidualValue(MonitoringPlan monitoringPlan) throws ParseException {
		String syz = null;
		String jhz = null;
		
		//重新计算计划值、剩余值(计划值=计划值+周期，剩余值=计划值-实际值)
		if (MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())) {//是日历类型
			int unit  = Calendar.DAY_OF_MONTH;
			//首次演变通过首次周期计算
			if (null != monitoringPlan.getDwZqz()) {
				unit = getOffsetUnit(monitoringPlan.getDwZqz());
			}
			
			jhz = DateUtil.getOffsetDate(monitoringPlan.getJhz(), Integer.valueOf(monitoringPlan.getZqz()), unit);
			syz = String.valueOf(DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE, monitoringPlan.getSjz(), jhz));
		}
		else if (MonitorProjectEnum.isTime(monitoringPlan.getJklbh())) {//sql中时间全是分钟,目标值 = 小时:分钟
			jhz = String.valueOf(Integer.valueOf(monitoringPlan.getJhz()) + Integer.valueOf(monitoringPlan.getZqz()));
			syz = String.valueOf(Integer.valueOf(jhz) - Integer.valueOf(monitoringPlan.getSjz()));
		}
		else if (MonitorProjectEnum.isLoop(monitoringPlan.getJklbh())) {//是循环类型
			jhz = String.valueOf(Integer.valueOf(monitoringPlan.getJhz()) + Integer.valueOf(monitoringPlan.getZqz()) );
			syz = String.valueOf(Integer.valueOf(jhz) - Integer.valueOf(monitoringPlan.getSjz()) );
		}
		monitoringPlan.setJhz(jhz);
		monitoringPlan.getParamsMap().put("SYZ", syz);
	}

	/**
	 * 
	 * @Description 初始化计划值
	 * @CreateTime 2017年10月24日 下午2:25:24
	 * @CreateBy 朱超
	 * @param jhrq
	 * @param monitoringPlan
	 */
	private void initPlannedValue(String jhrq, MonitoringPlan monitoringPlan) {
		if (StringUtils.isBlank(monitoringPlan.getJhz()) && StringUtils.isNotBlank(monitoringPlan.getZqz())) {
			if (MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())) {
				monitoringPlan.setJhz(jhrq);
			}
			else {
				monitoringPlan.setJhz("0");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @Description 获取当前记录的监控值列表
	 * @CreateTime 2017年10月24日 下午2:10:31
	 * @CreateBy 朱超
	 * @param itemInMap
	 * @return
	 */
	private List<MonitoringPlan> getMonitoringPlans(Map<String, Object> item) {
		List<MonitoringPlan> monitorprojects = null;
		if (null != item.get("monitoringPlans")) {
			monitorprojects = (List<MonitoringPlan>)item.get("monitoringPlans");
		}
		return monitorprojects;
	}

	/**
	 * 
	 * @Description 计算计划日期
	 * @CreateTime 2017年10月16日 下午2:34:54
	 * @CreateBy 朱超
	 * @param date
	 * @param plan
	 * @return
	 */
	private String calculationPlanDate(Date date, MonitoringPlan plan) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String limit = plan.getParamsMap().get("SYTS").toString();
		calendar.add(Calendar.DAY_OF_MONTH,Integer.valueOf(limit));
		String jhrq =  DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, calendar.getTime()) ;
		return jhrq;
	} 

	/**
	 * 	
	 * @Description 将新演算后的对象复制到子列表中
	 * @CreateTime 2017年10月16日 上午11:42:39
	 * @CreateBy 朱超
	 * @param item
	 * @param itemInMap
	 */
	@SuppressWarnings({ "unchecked" })
	private void addSubItem(Map<String, Object> item, Map<String, Object> itemClone) {
		 
		Set<String>jhrqSet = new HashSet<String>();
		//演变计划日期
		String currentJhrq = itemClone.get("JKSJID").toString().concat(itemClone.get("JHRQ").toString());
		//首次计划日期
		String firstJhrq = item.get("JKSJID").toString().concat(item.get("JHRQ").toString());
		if (jhrqSet.isEmpty()) {
			jhrqSet.add(firstJhrq);
		}
		
		List<Map<String, Object>> sublist = null;
		if (item.get("sublist") == null) {
			sublist = new ArrayList<Map<String,Object>>();
			item.put("sublist",sublist); 
		}
		sublist = (List<Map<String, Object>>) item.get("sublist");
		
		// 演算结果不能直接返回，需要排除同一个监控数据下相同的预测数据。  
		//sublist.add(itemClone);
		//缓存计划执行日期
		if(!sublist.isEmpty()){
			for (Map<String, Object> sub : sublist) {
				jhrqSet.add(sub.get("JKSJID").toString().concat(sub.get("JHRQ").toString()));
			}
		}
		//如果缓存里没有计划执行日，就将演算结果加入列表。 
		if (!jhrqSet.contains(currentJhrq)) {
			sublist.add(itemClone);
		}
		//演算结果不能直接返回，需要排除同一个监控数据下相同的预测数据。  
		
		
	}

	
	/**
	 *   
	 * @Description 将监控数据缓存到Map
	 * @CreateTime 2017年10月14日 下午2:42:00
	 * @CreateBy 朱超
	 * @param mplist
	 * @param jkdataMap
	 */
	@SuppressWarnings("unused")
	private void jkdataToMap(List<Map<String, Object>> mplist,Map<String, Map<String, Object>> jkdataMap) {
		if ((null != mplist) && (!mplist.isEmpty())) {
			for (Map<String, Object> row : mplist) {
				jkdataMap.put(row.get("JKSJID").toString(), row);
			}
		}
	}

	/**
	 *  
	 * @Description 获取当前监控数据
	 * @CreateTime 2017年10月14日 下午5:44:54
	 * @CreateBy 朱超
	 * @param record 页面参数
	 * @param mergelist 合并后的维修项目和EO监控数据
	 * @param planUsageMap 日使用量
	 * @param date 当前数据库时间
	 * @param destlistFir 
	 * @param originalMap
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private void getMonitordata(MonitoringCurrent record, List<Map<String, Object>> mergelist,
			Map<String, String> planUsageMap, Date date,List<Map<String, Object>> destlistFir,Map<String,Map<String, Object>> originalMap ) throws BusinessException {
		//集合非空，需要继续处理，否则直接返回。
		if (!mergelist.isEmpty()) {
			try {
				for (Map<String, Object> item: mergelist) {
					//计算每一个监控项目的剩余值的预计剩余天数
					Map<String, Object> targetParam = record.getParamsMap();
					
					if (item.get("JHSJSJ") != null) {
						final String isHdwz = item.get("IS_HDWZ").toString();//后到为准
						generatMonitoringPlans(item);
						List<MonitoringPlan> monitoringPlans =  this.getMonitoringPlans(item);
						
						//计算每个监控项的：剩余天数
						for (Iterator<MonitoringPlan> iterator2 = monitoringPlans.iterator(); iterator2.hasNext();) {
							MonitoringPlan monitoringPlan = (MonitoringPlan) iterator2.next();
							this.calculateRemainDays(monitoringPlan,planUsageMap);
						}
	 
						//获取最值（根据后到为准而定）
						Map<String, Object> itemClone = ObjUtil.deepClone(item, Map.class);
						//计划值为空的明细记录剔除
						List<MonitoringPlan> mPlans =  this.getMonitoringPlans(item);
						for (Iterator<MonitoringPlan> ite = mPlans.iterator(); ite.hasNext();) {
							MonitoringPlan monitoringPlan = (MonitoringPlan) ite.next();
							if (StringUtils.isBlank(monitoringPlan.getJhz())) {
								ite.remove();
							}
						}
						
						MonitoringPlan plan = this.getLimitPlan(item, isHdwz);
						if (plan != null) {
							boolean greater = isLeTarget(plan, targetParam);
							//小于等于目标值,将该监控数据整个剔除出集合，克隆原始数据到Map
							if(greater){
								//将原始数据加入MAP
								
								originalMap.put(itemClone.get("JKSJID").toString(), itemClone);
								generatMonitoringPlans(itemClone);
								itemClone.put("SYTS", "");
								
								//计划值为空的明细记录剔除（如果计划值为空将会导致isLeTarget无法正常执行）
								
								//加入结果集合
								item.put("JHRQ", calculationPlanDate(date, plan));
								item.put("limit_jklbh", plan.getJklbh());
								destlistFir.add(item);
							}
						}
						
						
					}
					//else 监控项目集合null，移除(文档中未记录逻辑)
				}
			} catch (ParseException e) {
				throw new BusinessException("计算日期失败", e);
			}
			
		}
	}
	
	/**
	 * 
	 * @Description 生成监控数据计划集合，便于日以后计算
	 * @CreateTime 2017年10月24日 下午1:54:13
	 * @CreateBy 朱超
	 * @param item
	 */
	private void generatMonitoringPlans(Map<String, Object> item) {
		List<MonitoringPlan>list = new ArrayList<MonitoringPlan>();
		if (null != item.get("JHSJSJ")) {
			//jhsj:多个监控数据（包含jklbh：监控类编号，jhqsz：计划起算值，jhz：计划值，sjz：实际值，syz：剩余值，zqz：周期值，dw_zqz：周期值单位）
			String jhsjsj = item.get("JHSJSJ").toString();
			if (StringUtils.isNotBlank(jhsjsj)) {
				String []arr = jhsjsj.split(",");
				
				for (int i = 0; i < arr.length; i++) {
					
					String []row = arr[i].split("#_#",-1);
					MonitoringPlan plan = new MonitoringPlan();
					plan.setJklbh(row[0]);	
					plan.setJhqsz(row[1] );
					plan.setJhz(row[2]);
					plan.setSjz(row[3] );
					plan.getParamsMap().put("SYZ", row[4]) ;
					plan.setZqz(row[5]);
					plan.setDwZqz(StringUtils.isNotBlank(row[6]) ? Integer.valueOf(row[6]) : null );
					plan.setWz(item.get("WZ")==null?null:Integer.valueOf(item.get("WZ").toString()));
					if (row.length > 7) {
						plan.getParamsMap().put("SYTS", row[7]); 
					}
					list.add(plan);
				}
			}
			item.put("monitoringPlans", list);
			
		}  
	}

	/**
	 * 
	 * @Description 根据集合，后到为准，获取最值
	 * @CreateTime 2017年10月14日 上午9:44:25
	 * @CreateBy 朱超
	 * @param itemInMap
	 * @param isHdwz
	 * @return
	 */
	private MonitoringPlan getLimitPlan(Map<String, Object> itemInMap, final String isHdwz) {
		List<MonitoringPlan> monitoringPlanList = this.getMonitoringPlans(itemInMap);
		if ((monitoringPlanList != null) && !monitoringPlanList.isEmpty()) {
			return Collections.max(monitoringPlanList,new Comparator<MonitoringPlan>(){
				@Override
				public int compare(MonitoringPlan o1, MonitoringPlan o2) {
					Integer o1sy = Integer.valueOf(o1.getParamsMap().get("SYTS").toString());
					Integer o2sy = Integer.valueOf(o2.getParamsMap().get("SYTS").toString());
					//后到为准，返回最大值，否则，返回最小值
					if (BinaryEnum.isYes4Str(isHdwz)) {
						return Integer.compare(o1sy, o2sy);
					}
					else {
						return Integer.compare(o2sy,o1sy);
					}
				}});
		}
		else {
			return null;
		}
		
	}
	
	/**
	 *  
	 * @Description 小于目标值
	 * @CreateTime 2017年10月16日 下午1:54:28
	 * @CreateBy 朱超
	 * @param plan
	 * @param targetParam
	 * @return
	 * @throws BusinessException
	 * @throws ParseException 
	 */
	private boolean isLeTarget(MonitoringPlan plan, Map<String, Object> targetParam)
			throws ParseException {
		boolean less = Boolean.FALSE; //大于目标值
		String jhz = plan.getJhz();
		String mbz = getTarget4Jklbh(targetParam, plan); //目标值
		 
		if (MonitorProjectEnum.isCalendar(plan.getJklbh())) {
			//是日历类型
			less = DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE, mbz, jhz) < 0;
		}
		else if (MonitorProjectEnum.isTime(plan.getJklbh())) {
			//sql中时间全是分钟,目标值 = 小时:分钟
			less = Integer.valueOf(jhz) < StringAndDate_Util.convertToMinute(mbz);
		}
		else if (MonitorProjectEnum.isLoop(plan.getJklbh())) {
			//是循环类型
			less = Integer.valueOf(jhz) < Integer.valueOf(mbz);
		}
		return less;
	}
 
	/**
	 * 
	 * @Description 获取发动机的目标值
	 * @CreateTime 2017年10月13日 下午5:26:02
	 * @CreateBy 朱超
	 * @param targetParam
	 * @param plan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getTarget4Jklbh(Map<String, Object> targetParam, MonitoringPlan plan) {
		Integer wz = plan.getWz(); //位置
		String mbz = null;
		//不是发动机
		if (!MonitorProjectEnum.isEngine(plan.getJklbh())) {
			if (targetParam.containsKey(plan.getJklbh())) {
				mbz = targetParam.get(plan.getJklbh()).toString();
			}
		}
		else {
			if (targetParam.containsKey(plan.getJklbh()) && wz!=null) {
				//是发动机
				Map<String, String> targetMap = (Map<String, String>)targetParam.get(plan.getJklbh());
				mbz = targetMap.get(wz.toString());
			}
		}
		
		if (StringUtils.isEmpty(mbz)) {
			mbz = "0";
		}
		return mbz;
	}
	
}
