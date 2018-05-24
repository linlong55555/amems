package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.ReflectionUtils;
import com.eray.thjw.util.StringAndDate_Util;

import enu.MonitorItemEnum;
import enu.PlanTaskDispalyState;
import enu.PlanTaskState;
import enu.PlanTaskSubType;
import enu.PlanTaskType;
import enu.PlaneInitDataEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;

@SuppressWarnings("serial")
public class PlanTaskExt extends PlanTask{
    
    //扩展区域
	private String rwlxText;
	
	private String rwzlxText;
	
	private String ztText;
	
	private String xsztText;
	
    /**
     * 计划
     */
    private String jh;
    
    /**
     * 剩余
     */
    private String sy;
    
    /**
     * 剩余str
     */
    private String syStr;
     
    
    /**
     * 剩余(天)
     */
    private String sydays;
    
    /**
     * 第一个监控项  监控项编号   
     */
    private String jkxmbh_f;
    /**
     * 第一个监控项  监控分类
     */
    private String jkflbh_f;
    /**
     * 第一个监控项  描述
     */
    private String ms_f;
    /**
     * 第一个监控项  计划值
     */
    private String jkz_f;
    /**
     * 第一个监控项  实际值
     */
    private String sj_f;
    
    /**
     * 第一个监控项  剩余
     */
    private String sy_f;
    
    
    /**
     * 第二个监控项  监控项编号   
     */
    private String jkxmbh_s;
    /**
     * 第二个监控项  监控分类
     */
    private String jkflbh_s;
    /**
     * 第二个监控项  描述
     */
    private String ms_s;
    /**
     * 第二个监控项  计划值
     */
    private String jkz_s;
    /**
     * 第二个监控项  实际值
     */
    private String sj_s;
    
    /**
     * 第二个监控项 剩余值
     */
    private String sy_s;
    
    
    /**
     * 第三个监控项  监控项编号   
     */
    private String jkxmbh_t;
    /**
     * 第三个监控项  监控分类
     */
    private String jkflbh_t;
    /**
     * 第三个监控项  描述
     */
    private String ms_t;
    /**
     * 第三个监控项  计划值
     */
    private String jkz_t;
    /**
     * 第三个监控项  实际值
     */
    private String sj_t;
    
    private String sy_t;
     
    /**
     * 重要系数
     */
    private Integer zyxs;
    
    /**
     * 装机清单ID
     */
    private String zjqdid;
    
    /**
     * 定检项目编号
     */
    private String djxmbh;
    /**
     * 定检项目id
     */
    private String djxmid;
    
    
    /**
     * 飞机初始化数据
     * 用于计算剩余天数
     */
    private PlaneData planeData;
    
    /**
     * 飞机日使用量
     * 用于计算剩余天数
     */
    private PlaneModelData planeModelData;
    
    /**
     * 剩余天数集合
     */
    private List<Integer> syItems = new ArrayList<Integer>(); 
    
    /**
     * 监控项目列表
     */
    private List<Map<String, String>> calculateParams = null; 
    
    /**
     * 排序字段
     */
    private Integer sortVal = null;
    
    private String bgcolor = null; 
    
	public String getJh() {
		StringBuffer sb = new StringBuffer();
		List<Map<String, String>> params = this.getCalculateParams();
		for (Map<String, String> map : params) {
			if (StringUtils.isNotBlank(map.get("jkxmbh"))) {
					sb.append(MonitorItemEnum.getName(map.get("jkxmbh")).concat(":")
							.concat(StringUtils.isEmpty(map.get("jkz"))?"":map.get("jkz")));
					sb.append("</br>");
			}
		}
		jh = sb.toString();
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}
	
	public String getSydays() {
		return sydays;
	}
	
	/**
	 * 计算计划任务的剩余天数
	 * @throws ParseException 
	 * @throws Exception 
	 */
	public void calculateRemainderdays() throws BusinessException {
		List<Map<String, String>> params = this.getCalculateParams();
		if (params!= null && !params.isEmpty()) {
			for (Map<String, String> calculateParam : params) {
				//计算各个监控项目的剩余天数
				this.calculateRemainderResult(calculateParam);
			}
		}
		
		//计划任务的剩余天数 = min(各个监控项目的剩余天数)
		if (!syItems.isEmpty()) {
			sydays = String.valueOf(Collections.min(syItems));
		}else {
			sydays = "0";
		}
	}

	/**
	 * 计算各个监控项目的剩余天数
	 * @param param
	 * @throws ParseException 
	 */
	private void calculateRemainderResult(Map<String, String> param) throws BusinessException  {
		//项目编号
		String jkxmbh = param.get("jkxmbh").toUpperCase();
		//实际值
		String sj = param.get("sj");
		//计划值
		String jkz = param.get("jkz");
		//对应的剩余字段
		String syField = param.get("syField");
		
		
		if (StringUtils.isNotBlank(this.getFjzch())) {
			// 装机件计算所有监控项
			if (StringUtils.isNotBlank(jkxmbh)) {
				//日期类： 剩余=计划值-实际值
				if (MonitorItemEnum.isCalendar(jkxmbh)) {
					try {
						String sy = calculateSyByCalendar(sj, jkz,syField);
						syItems.add(Integer.valueOf(sy));
					} catch (ParseException e) {
						 throw new BusinessException("计算剩余天数失败",e);
					}
				} else if (MonitorItemEnum.isTime(jkxmbh)) {
					//时间类： 剩余=计划值-实际值，单位：（时：分）
					String sy = StringAndDate_Util.operateTime(jkz, sj, TimeSeparatorEnum.COLON,
							TimeOperationEnum.SUBTRACT);
					//非时控件 而且监控数据随飞机的，剩余=计划值-实际值-飞机注册信息
					if((!PlanTaskSubType.isTimeControlPart(this.getRwzlx())) &&  MonitorItemEnum.isInvolvedAircraft(jkxmbh)){
						// 计算时间类监控项初始化值
						String initStr = getMonitorItemInit(jkxmbh);
						sy = StringAndDate_Util.operateTime(sy, initStr==null?"0":initStr, TimeSeparatorEnum.COLON,
								TimeOperationEnum.SUBTRACT);
					}
					ReflectionUtils.setFieldValue(this, syField, sy.contains(":")?sy:sy.concat(":0"));
					Integer daysUsage = calculateUsedDays(sy.contains(":")?sy:sy.concat(":0"));
					syItems.add(daysUsage);
					
				} else if (MonitorItemEnum.isLoop(jkxmbh)) {
					// 如果是循环监控类，单位：（number(12.2)）
					BigDecimal daysSy = calculateSyByLoop(jkz, sj, jkxmbh,syField);
					syItems.add(daysSy.intValue());
				}
			}
		} else {
			// 非装机件只算日历
			if (StringUtils.isNotBlank(jkxmbh)) {
				if (MonitorItemEnum.isCalendar(jkxmbh)) {
					try {
						// 剩余=计划值-实际值，结果整数，如果是日历监控类，单位：天
						String sy = calculateSyByCalendar(sj, jkz,syField);
						syItems.add(Integer.valueOf(sy));
					} catch (ParseException e) {
						throw new BusinessException("",e);
					}
				}
			}
		}
	}

	private Integer calculateUsedDays(String sy) {
		Integer hours  = Integer.valueOf(sy.split(":")[0]);
		Integer minutes  = Integer.valueOf(sy.split(":")[1]);
		BigDecimal hoursDailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrJsfxsj();
		Integer minutesDailyUsage = null;
		//时间日使用量换算成分钟
		if (hoursDailyUsage == null || hoursDailyUsage.intValue() == 0) {
			 minutesDailyUsage = 1;
		}else {
			 minutesDailyUsage = hoursDailyUsage.multiply(BigDecimal.valueOf(60L)).intValue();
		}
		//时间已使用量换算成分钟
		Integer minutesUsed = hours>=0?(hours *60 + minutes):(hours *60 - minutes) ;
		BigDecimal days = new BigDecimal(minutesUsed).divide(new BigDecimal(minutesDailyUsage),10,BigDecimal.ROUND_UP);
		Double d = Math.floor(days.doubleValue());
		return  d.intValue();
	}

	/**
	 * 获取监控项初始化值
	 * @param planeInitDataMap
	 * @param jkxmbh
	 * @return
	 */
	private String getMonitorItemInit(String jkxmbh) {
		Map<String, PlaneInitData> planeInitDataMap = getPlaneInitMap();
		String initStr = null;
		PlaneInitData initData = null;
		//时间类监控项默认值(0:0)
		if (MonitorItemEnum.isTime(jkxmbh)) {
			if (jkxmbh.equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString())) {
				initData = planeInitDataMap.get(PlaneInitDataEnum.INIT_TIME_JSFX.getCode());
				if (initData!=null && StringUtils.isNotBlank(initData.getInitValue())) {
					initStr = initData.getInitValue();
				}
				else {
					initStr = "0:0";
				}
			}
		}
		else if(MonitorItemEnum.isLoop(jkxmbh)){
			//循环类监控项默认值(0)
			
			if ( jkxmbh.equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString())) {
				initData = planeInitDataMap.get(PlaneInitDataEnum.INIT_LOOP_QLJ.getCode());
			}
			else if (jkxmbh.equals(MonitorItemEnum.SPECIAL_FIRST.toString())) {
				initData = planeInitDataMap.get(PlaneInitDataEnum.INIT_LOOP_TS1.getCode());
			}
			else if (jkxmbh.equals(MonitorItemEnum.SPECIAL_SECOND.toString())) {
				initData = planeInitDataMap.get(PlaneInitDataEnum.INIT_LOOP_TS2.getCode());
			}
			
			if (initData!=null && StringUtils.isNotBlank(initData.getInitValue())) {
				initStr = initData.getInitValue();
			}
			else {
				initStr = "0";
			}
		}
		
		return initStr;
	}

	/**
	 * 获取飞机初始化数据map
	 * @return
	 */
	private Map<String, PlaneInitData> getPlaneInitMap() {
		
		Map<String,PlaneInitData> planeInitDataMap = new HashMap<String, PlaneInitData>();
		if (planeData!=null && planeData.getInitDatas()!=null) {
			List<PlaneInitData> initDatas = planeData.getInitDatas();
			if (initDatas!=null && !initDatas.isEmpty()) {
				for (PlaneInitData planeInitData : initDatas) {
					planeInitDataMap.put(planeInitData.getInitXmbh(), planeInitData);
				}
			}
		}
		return planeInitDataMap;
	}
    
	/**
	 * 计算日期类监控项目的剩余天数
	 * @param sj 实际日期
	 * @param jkz 计划日期
	 * @return
	 */
	private String calculateSyByCalendar(String sj,String jkz,String filed)throws ParseException {
		String sy = "0";
		sy = String.valueOf(DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE, sj, jkz));
		ReflectionUtils.setFieldValue(this, filed, sy);
		return sy;
	}

	/**
	 * 循环监控项计算剩余.
     * @param jkz 计划
	 * @param sjz 实际
	 * @param jkxmbh 监控项目编号
	 * @return
	 * 监控数据随飞机：ts1,ts2,起落架循环</br>
	 * 监控数据随部件：n1-n6,外吊挂,绞车</br>
	 * 监控数据随飞机 而且  非时控件类：  剩余 = 计划值-(实际值+飞机注册信息)/日使用量</br>
	 * 否则：  剩余 = 计划值-(实际值)/日使用量
	 */
	private BigDecimal calculateSyByLoop(String jkz,String sjz,String jkxmbh,String field) {
		//循环类监控项目： 剩余  = （计划-实际）/日使用量
		BigDecimal sy = new BigDecimal(jkz)
		.subtract(new BigDecimal(sjz));
		//将剩余写入对应字段
		ReflectionUtils.setFieldValue(this, field, new java.text.DecimalFormat("#.00").format(sy.doubleValue()));
		
		//日使用量
		Integer dailyUsage = getdailyUsageByLoop(jkxmbh.toUpperCase());
		//随飞机的，非时控循环类监控项目： 剩余  = （计划-实际-飞机初始数据）/日使用量
		if((!PlanTaskSubType.isTimeControlPart(this.getRwzlx())) &&  MonitorItemEnum.isInvolvedAircraft(jkxmbh)){
			//计算时间类监控项初始化值
			String initStr = getMonitorItemInit(jkxmbh);
			sy = sy.subtract(new BigDecimal(initStr==null?"0":initStr));
			ReflectionUtils.setFieldValue(this, field, new java.text.DecimalFormat("#.00").format(sy.doubleValue()));
		}
		sy = sy.divide(BigDecimal.valueOf(dailyUsage),2, RoundingMode.HALF_DOWN);
		if (sy == null) {
			sy = BigDecimal.valueOf(0L);
		}
		
		return sy;
	}

	/**
	 * 获取循环类监控项目日使用量
	 * @param jkxmbh
	 * @return
	 */
	private Integer getdailyUsageByLoop(String jkxmbh) {
		Integer dailyUsage = null;
		//随飞机的循环类监控项目： 剩余 = （计划-实际-飞机初始数据）/日使用量
		if (jkxmbh.equals(MonitorItemEnum.SPECIAL_FIRST.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrTsjk1();
		}
		//随飞机的循环类监控项目： 剩余 = （计划-实际-飞机初始数据）/日使用量
		else if (jkxmbh.equals(MonitorItemEnum.SPECIAL_SECOND.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrTsjk2();
		}
		//随飞机的循环类监控项目： 剩余 = （计划-实际-飞机初始数据）/日使用量
		else if (jkxmbh.equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrQljxh();
		}
		//以下随部件的循环类监控项目： 剩余 = （计划-实际）/日使用量
		else if (jkxmbh.equals(MonitorItemEnum.EXT_SUSPENSION_LOOP.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrWdgxh();
		}
		else if ( jkxmbh.equals(MonitorItemEnum.WINCH_CYCLE.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrJcxh();
		}
		else if (jkxmbh.equals(MonitorItemEnum.N1.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrN1();
		}
		else if (jkxmbh.equals(MonitorItemEnum.N2.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrN2();
		}
		else if (jkxmbh.equals(MonitorItemEnum.N3.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrN3();
		}
		else if (jkxmbh.equals(MonitorItemEnum.N4.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrN4();
		}
		else if (jkxmbh.equals(MonitorItemEnum.N5.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrN5();
		}
		else if (jkxmbh.equals(MonitorItemEnum.N6.toString())) {
			dailyUsage = this.getPlaneModelData()==null?null:this.getPlaneModelData().getrN6();
		}
		return (dailyUsage!=null && !dailyUsage.equals(Integer.valueOf(0)))?dailyUsage:Integer.valueOf(1);
	}
	

	public void setSydays(String sydays) {
		this.sydays = sydays;
	}

	 
	public String getJkxmbh_f() {
		return jkxmbh_f;
	}

	public void setJkxmbh_f(String jkxmbh_f) {
		this.jkxmbh_f = jkxmbh_f;
	}

	public String getJkflbh_f() {
		return jkflbh_f;
	}

	public void setJkflbh_f(String jkflbh_f) {
		this.jkflbh_f = jkflbh_f;
	}

	public String getMs_f() {
		return ms_f;
	}

	public void setMs_f(String ms_f) {
		this.ms_f = ms_f;
	}

	public String getJkz_f() {
		if (!StringUtils.isEmpty(this.getJkxmbh_f())) {
			if (StringUtils.isEmpty(jkz_f)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_f())) {
					this.setJkz_f(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_f())) {
					this.setJkz_f("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_f())) {
					this.setJkz_f("0");
				}
			}
		}
		return jkz_f;
	}

	public void setJkz_f(String jkz_f) {
		this.jkz_f = jkz_f;
	}

	public String getSj_f() {
		
			if (StringUtils.isEmpty(sj_f)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_f()==null?"":this.getJkxmbh_f())) {
					this.setSj_f(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_f()==null?"":this.getJkxmbh_f())) {
					this.setSj_f("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_f()==null?"":this.getJkxmbh_f())) {
					this.setSj_f("0");
				}
			}
			else {
				if (MonitorItemEnum.isTime(this.getJkxmbh_f()==null?"":this.getJkxmbh_f())) {
					this.setSj_f(StringAndDate_Util.operateTime(sj_f, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD));
				}
			}
		
		return sj_f;
	}

	public void setSj_f(String sj_f) {
		this.sj_f = sj_f;
	}

	public String getJkxmbh_s() {
		return jkxmbh_s;
	}

	public void setJkxmbh_s(String jkxmbh_s) {
		this.jkxmbh_s = jkxmbh_s;
	}

	public String getJkflbh_s() {
		return jkflbh_s;
	}

	public void setJkflbh_s(String jkflbh_s) {
		this.jkflbh_s = jkflbh_s;
	}

	public String getMs_s() {
		return ms_s;
	}

	public void setMs_s(String ms_s) {
		this.ms_s = ms_s;
	}

	public String getJkz_s() {
		if (!StringUtils.isEmpty(this.getJkxmbh_s())) {
			if (StringUtils.isEmpty(jkz_s)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_s())) {
					this.setJkz_s(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_s())) {
					this.setJkz_s("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_s())) {
					this.setJkz_s("0");
				}
			}
		}
		
		return jkz_s;
	}

	public void setJkz_s(String jkz_s) {
		this.jkz_s = jkz_s;
	}

	public String getSj_s() {
		if (!StringUtils.isEmpty(this.getJkxmbh_s())) {
			if (StringUtils.isEmpty(sj_s)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_s())) {
					this.setSj_s(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_s())) {
					this.setSj_s("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_s())) {
					this.setSj_s("0");
				}
			}
			else{
				if (MonitorItemEnum.isTime(this.getJkxmbh_s()==null?"":this.getJkxmbh_s())) {
					this.setSj_s(StringAndDate_Util.operateTime(sj_s, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD));
				}
			}
		}
		return sj_s;
	}

	public void setSj_s(String sj_s) {
		this.sj_s = sj_s;
	}

	public String getJkxmbh_t() {
		return jkxmbh_t;
	}

	public void setJkxmbh_t(String jkxmbh_t) {
		this.jkxmbh_t = jkxmbh_t;
	}

	public String getJkflbh_t() {
		return jkflbh_t;
	}

	public void setJkflbh_t(String jkflbh_t) {
		this.jkflbh_t = jkflbh_t;
	}

	public String getMs_t() {
		return ms_t;
	}

	public void setMs_t(String ms_t) {
		this.ms_t = ms_t;
	}

	public String getJkz_t() {
		if (StringUtils.isNotBlank(this.getJkxmbh_t())) {
			if (StringUtils.isEmpty(jkz_t)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_t())) {
					this.setJkz_t(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_t())) {
					this.setJkz_t("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_t())) {
					this.setJkz_t("0");
				}
			}
		}
		return jkz_t;
	}

	public void setJkz_t(String jkz_t) {
		this.jkz_t = jkz_t;
	}

	public String getSj_t() {
		if (StringUtils.isNotBlank(this.getJkxmbh_t())) {
			if (StringUtils.isEmpty(sj_t)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_t())) {
					this.setSj_t(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_t())) {
					this.setSj_t("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_t())) {
					this.setSj_t("0");
				}
			}
			else{
				if (MonitorItemEnum.isTime(this.getJkxmbh_t()==null?"":this.getJkxmbh_t())) {
					this.setSj_t(StringAndDate_Util.operateTime(sj_t, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD));
				}
			}
		}
		return sj_t;
	}

	public void setSj_t(String sj_t) {
		this.sj_t = sj_t;
	}

	public Integer getZyxs() {
		if(zyxs == null || zyxs == 0){
			this.setZyxs(1);
		};
		return zyxs;
	}

	public void setZyxs(Integer zyxs) {
		this.zyxs = zyxs;
	}

	public String getZjqdid() {
		return zjqdid;
	}

	public void setZjqdid(String zjqdid) {
		this.zjqdid = zjqdid;
	}

	public String getSy_f() {
		if (!StringUtils.isEmpty(this.getJkxmbh_f())) {
			if (StringUtils.isEmpty(sy_f)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_f())) {
					this.setSy_f(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_f())) {
					this.setSy_f("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_f())) {
					this.setSy_f("0");
				}
			}
		}
		return sy_f;
	}

	public void setSy_f(String sy_f) {
		this.sy_f = sy_f;
	}

	public String getSy_s() {
		if (!StringUtils.isEmpty(this.getJkxmbh_s())) {
			if (StringUtils.isEmpty(sy_s)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_s())) {
					this.setSy_s(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_s())) {
					this.setSy_s("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_s())) {
					this.setSy_s("0");
				}
			}
		}
		return sy_s;
		
	}

	public void setSy_s(String sy_s) {
		this.sy_s = sy_s;
	}

	public String getSy_t() {
		
		
		if (!StringUtils.isEmpty(this.getJkxmbh_t())) {
			if (StringUtils.isEmpty(sy_t)) {
				if (MonitorItemEnum.isCalendar(this.getJkxmbh_t())) {
					this.setSy_t(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
				}
				else if (MonitorItemEnum.isTime(this.getJkxmbh_t())) {
					this.setSy_t("0:0");
				}
				else if (MonitorItemEnum.isLoop(this.getJkxmbh_t())) {
					this.setSy_t("0");
				}
			}
		}
		return sy_t;
		
	}

	public void setSy_t(String sy_t) {
		this.sy_t = sy_t;
	}

	public PlaneData getPlaneData() {
		return planeData;
	}

	public void setPlaneData(PlaneData planeData) {
		this.planeData = planeData;
	}

	public PlaneModelData getPlaneModelData() {
		return planeModelData;
	}

	public void setPlaneModelData(PlaneModelData planeModelData) {
		this.planeModelData = planeModelData;
	}

	public String getSy() {
		return sy;
	}
	
	public String getSyStr() {
		
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(this.getJkxmbh_f())) {
			sb.append(MonitorItemEnum.getName(this.getJkxmbh_f()).concat(":")
					.concat(this.getSy_f()));
			sb.append("</br>");
		}
		if (StringUtils.isNotBlank(this.getJkxmbh_s())) {
			sb.append(MonitorItemEnum.getName(this.getJkxmbh_s()).concat(":")
					.concat(this.getSy_s()));
			sb.append("</br>");
		}
		if (StringUtils.isNotBlank(this.getJkxmbh_t())) {
			sb.append(MonitorItemEnum.getName(this.getJkxmbh_t()).concat(":")
					.concat(this.getSy_t()));
		}
		
		syStr = sb.toString();
		return syStr;
	}

	public void setSy(String sy) {
		this.sy = sy;
	}

	public List<Map<String, String>> getCalculateParams() {
		if (null==calculateParams) {
			calculateParams = new ArrayList<Map<String,String>>();
			if (StringUtils.isNotBlank(this.getJkxmbh_f())) {
				Map<String, String>monitorItemFir = new HashMap<String, String>();
				monitorItemFir.put("jkz", this.getJkz_f());
				monitorItemFir.put("sj", this.getSj_f());
				monitorItemFir.put("jkxmbh", this.getJkxmbh_f());
				monitorItemFir.put("syField", "sy_f");
				calculateParams.add(monitorItemFir);
			}
			
			if (StringUtils.isNotBlank(this.getJkxmbh_s())) {
				Map<String, String>monitorItemSec = new HashMap<String, String>();
				monitorItemSec.put("jkz", this.getJkz_s());
				monitorItemSec.put("sj", this.getSj_s());
				monitorItemSec.put("jkxmbh", this.getJkxmbh_s());
				monitorItemSec.put("syField", "sy_s");
				calculateParams.add(monitorItemSec);
			}
			
			if (StringUtils.isNotBlank(this.getJkxmbh_t())) {
				Map<String, String>monitorItemThr = new HashMap<String, String>();
				monitorItemThr.put("jkz", this.getJkz_t());
				monitorItemThr.put("sj", this.getSj_t());
				monitorItemThr.put("jkxmbh", this.getJkxmbh_t());
				monitorItemThr.put("syField", "sy_t");
				calculateParams.add(monitorItemThr);
			}
		}
		
		return calculateParams;
	}

	public void setCalculateParams(List<Map<String, String>> calculateParams) {
		this.calculateParams = calculateParams;
	}

	public Integer getSortVal() {
		return sortVal;
	}

	public void setSortVal(Integer sortVal) {
		this.sortVal = sortVal;
	}
	
	/**
	 * 计算排序值
	 */
	public void calculateSortVal() {
		if (PlanTaskType.isCheckBill(this.getRwlx())) {
			this.setSortVal(Integer.valueOf(this.getSydays())/(this.getZyxs()==null?1:this.getZyxs()));
		}
		else{
			this.setSortVal(this.getZyxs());
		}
	}

	public void setSyStr(String syStr) {
		this.syStr = syStr;
	}
	
	public String getDyrstr() {
		return this.getDyr()!=null?this.getDyr().getRealname():"";
	}

	public String getRwlxText() {
		rwlxText = this.getRwlx() == null?"":PlanTaskType.getName(Integer.valueOf(this.getRwlx()));
		return rwlxText;
	}

	public void setRwlxText(String rwlxText) {
		this.rwlxText = rwlxText;
	}

	public String getRwzlxText() {
		rwzlxText = this.getRwzlx() == null?"":PlanTaskSubType.getName(Integer.valueOf(this.getRwzlx()));
		return rwzlxText ;
	}

	public void setRwzlxText(String rwzlxText) {
		this.rwzlxText = rwzlxText;
	}

	public String getZtText() {
		ztText = this.getZt()==null?"":PlanTaskState.getName(this.getZt());
		return ztText;
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public String getXsztText() {
		xsztText = this.getXszt()==null?"":PlanTaskDispalyState.getName(this.getXszt());
		return xsztText;
	}

	public void setXsztText(String xsztText) {
		this.xsztText = xsztText;
	}

	public String getDjxmbh() {
		return djxmbh;
	}

	public void setDjxmbh(String djxmbh) {
		this.djxmbh = djxmbh;
	}

	public String getDjxmid() {
		return djxmid;
	}

	public void setDjxmid(String djxmid) {
		this.djxmid = djxmid;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	
	
}