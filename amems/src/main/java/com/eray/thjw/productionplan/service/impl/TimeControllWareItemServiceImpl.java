package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.TimeControllWareItemMapper;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.po.ScheduledTask;
import com.eray.thjw.productionplan.po.TimeControlOptions;
import com.eray.thjw.productionplan.po.TimeControllWareItem;
import com.eray.thjw.productionplan.service.CheckTaskBillService;
import com.eray.thjw.productionplan.service.ScheduledTaskSerivce;
import com.eray.thjw.productionplan.service.TimeControlOptionsService;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.NonroutineService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.StringAndDate_Util;

import enu.MonitorItemEnum;
import enu.OrderZtEnum;
import enu.ScheduledEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;


@Service
public class TimeControllWareItemServiceImpl implements TimeControllWareItemService {
	DecimalFormat df = new DecimalFormat("0.00");
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private TimeControllWareItemMapper timeControllWareItemMapper;
	
	@Resource
	private TimeControlOptionsService timeControlOptionsService; //b_s_00304 生效区-定检监控项目service
	
	@Resource
	private NonroutineService nonroutineService; //时控件service生成工单
	
	@Resource
	private ScheduledTaskSerivce scheduledTaskSerivce; //时控件service提交计划
	
	@Resource
	private CommonService commonService; //當前時間
	
	@Resource
	private CheckTaskBillService checkTaskBillService; //當前時間
	
	@Override
	public List<TimeControllWareItem> queryAllPageList(
			TimeControllWareItem timeControllWareItem) throws Exception {
		return timeControllWareItemMapper.queryAllPageList(timeControllWareItem);
	}

	@Override
	public int queryCount(TimeControllWareItem timeControllWareItem)
			throws Exception {
		return timeControllWareItemMapper.queryCount(timeControllWareItem);
	}
	
	/*
	 * 格式化数据
	 * (non-Javadoc)
	 * @see com.eray.thjw.productionplan.service.TimeControllWareItemService#geshihua(java.util.List)
	 */
	public List<TimeControllWareItem> geshihua(List<TimeControllWareItem> list,PlaneModelData planeModelData, Pagination pagination)throws Exception{
		Map<String, String> resultMap = new HashMap<String, String>();
		StringBuffer yc=new StringBuffer();//预拆
		StringBuffer sy=new StringBuffer();//剩余
		String syts=null;//剩余天数
		StringBuffer fengzhi=new StringBuffer();//峰值
		
		List<TimeControlOptions> list1=timeControlOptionsService.queryAllsj();//查询 b_s_00301 生效区-时控件监控设置表集合
		
		//遍历 b_s_003 生效区-飞机装机清单集合
		for (TimeControllWareItem timeControllWareItem : list) {
			//清空StringBuffer
			yc.delete(0,yc.length());
			sy.delete(0,sy.length());
			fengzhi.delete(0,fengzhi.length());
			resultMap.clear();
			//遍历  b_s_00301 生效区-时控件监控设置表集合
			for (TimeControlOptions timeControlOptions : list1) {
				//装机清单id比较监控设置装机清单id 
				if(timeControllWareItem.getId().equals(timeControlOptions.getZjqdid())){
					
					yc.append(timeControlOptions.getMs()+":"+timeControlOptions.getBjyc()+",");
					sy.append(timeControlOptions.getMs()+":"+shengyu(timeControlOptions)+",");
					fengzhi.append(timeControlOptions.getMs()+":"+timeControlOptions.getGdsx()+",");
					
					resultMap.put(timeControlOptions.getJklbh(), shengyu(timeControlOptions));
				}
			}
			
			//根据飞机注册号查询部件日使用量
			if(planeModelData.getFjzch().equals(timeControllWareItem.getFjzch())){
				//计算剩余天数
				if(resultMap.size()!=0){
					syts=shengyutianshu(planeModelData,resultMap);
					if(syts==null){
						syts="";
					}
				}
			}
			
			timeControllWareItem.setYc(yc.toString());//预拆
			timeControllWareItem.setFengzhi(fengzhi.toString());//峰值
			timeControllWareItem.setSy(sy.toString());//剩余
			timeControllWareItem.setSyts(syts);//剩余(天)
		
			//工单编号
			if(timeControllWareItem.getGdid()!=null){
				if(timeControllWareItem.getGdzt()!=null){
					if(timeControllWareItem.getGdbh()!=null){
					timeControllWareItem.setGdbh("("+	OrderZtEnum.getName(Integer.valueOf(timeControllWareItem.getGdzt()))+")"+timeControllWareItem.getGdbh()+"/"+timeControllWareItem.getGdwhr()+"/"+timeControllWareItem.getGdwhsj());
					}
				}
			}
			
			//任务编号
			if(timeControllWareItem.getRwid()!=null){
				if(timeControllWareItem.getRwdh()!=null&&timeControllWareItem.getRwwhr()!=null&&timeControllWareItem.getRwwhsj()!=null){
					timeControllWareItem.setRwdh(timeControllWareItem.getRwdh()+"/"+timeControllWareItem.getRwwhr()+"/"+timeControllWareItem.getRwwhsj());
				}
			}
		}
		
		List<TimeControllWareItem> list2=new ArrayList<TimeControllWareItem>();
		if("auto".equals(pagination.getSort())){
			list2=sytspaixu(list);//剩余天数排序
		}else{
			list2=list;
		}
		
		return list2;
	}

	/**
	 * 剩余天数排序正序
	 * @param list
	 * @return
	 */
	public List<TimeControllWareItem> sytspaixuzheng(List<TimeControllWareItem> list){
		
		Collections.sort(list, new Comparator<TimeControllWareItem>() {
			@Override
			public int compare(TimeControllWareItem o1, TimeControllWareItem o2) {
	              	
				if(StringUtils.isNotBlank(o1.getRwdh())){
					return 1;
				}
				if(StringUtils.isNotBlank(o2.getRwdh())){
					return -1;
				}
				
				if(StringUtils.isNotBlank(o1.getSyts()) && StringUtils.isNotBlank(o2.getSyts())){
					return Integer.valueOf(o2.getSyts()).compareTo(  Integer.valueOf(o1.getSyts()));
				}else{
					return StringUtils.isNotBlank(o1.getSyts())?-1:(StringUtils.isNotBlank(o1.getSyts())?1:0);
				}
			}
			
		});
		
		return list ;
	}
	
	/**
	 * 剩余天数排序daoxu 
	 * @param list
	 * @return
	 */
	public List<TimeControllWareItem> sytspaixu(List<TimeControllWareItem> list){
		
		Collections.sort(list, new Comparator<TimeControllWareItem>() {
			@Override
			public int compare(TimeControllWareItem o1, TimeControllWareItem o2) {
	              	
				if(StringUtils.isNotBlank(o1.getRwdh())){
					return 1;
				}
				if(StringUtils.isNotBlank(o2.getRwdh())){
					return -1;
				}
				
				if(StringUtils.isNotBlank(o1.getSyts()) && StringUtils.isNotBlank(o2.getSyts())){
					return Integer.valueOf(o1.getSyts()).compareTo( Integer.valueOf(o2.getSyts()));
				}else{
					return StringUtils.isNotBlank(o1.getSyts())?-1:(StringUtils.isNotBlank(o1.getSyts())?1:0);
				}
			}
			
		});
		
		return list ;
	}
	
	
	/**
	 * 计算剩余天数 计算公式：日期：剩余 ， 时间转化为分钟计算:剩余*60/日使用量*60 ， 循环：剩余/日使用量
	 */
	@Override
	public String shengyutianshu(PlaneModelData planeModelData,Map<String, String> resultMap)throws Exception {
		StringBuffer shengyu=new StringBuffer();//剩余天数
		String shij=null;
		int maohaoh=0; //剩余
		 for (String key : resultMap.keySet()) {
			// shengyu.delete(0,shengyu.length());
			 if(StringUtils.isEmpty(resultMap.get(key))) {
				 continue;
			 }
			 if(key.equals(MonitorItemEnum.CALENDAR.toString().toLowerCase())){
				 shengyu.append(resultMap.get(key)+",");
				 
			 }else if(key.equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString().toLowerCase())){
				 shij=resultMap.get(key);
				 if(shij.indexOf(":")>=0){
					 maohaoh=Integer.valueOf(shij.substring(shij.indexOf(":")+1));//获取分钟
					 shij=shij.substring(0,shij.indexOf(":")); //获取小时
				 }
				
				 double risyl;
				 
				 if(planeModelData==null||planeModelData.getrJsfxsj()==null||planeModelData.getrJsfxsj().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrJsfxsj().doubleValue();//日使用量
				 }
				 
				 BigDecimal b1 = new BigDecimal(Double.valueOf(shij)); 
				 BigDecimal b2 = new BigDecimal("60"); 
				 BigDecimal b3= ((b1.multiply(b2)).add(new BigDecimal(maohaoh))).divide(new BigDecimal(risyl).multiply(b2),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.SEARCH_LIGHT_TIME.toString().toLowerCase())){
				 shij=resultMap.get(key);
					 if(shij.indexOf(":")>=0){
						 maohaoh=Integer.valueOf(shij.substring(shij.indexOf(":")+1));//获取分钟
						 shij=shij.substring(0,shij.indexOf(":"));//获取小时
					 }
				 double risyl;
				 if(planeModelData==null||planeModelData.getrSsdsj()==null){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrSsdsj().doubleValue();//日使用量
				 }
					
				 BigDecimal b1 = new BigDecimal(Double.valueOf(shij)); 
				 BigDecimal b2 = new BigDecimal("60"); 
				 BigDecimal b3= ((b1.multiply(b2)).add(new BigDecimal(maohaoh))).divide(new BigDecimal(risyl).multiply(b2),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.WINCH_TIME.toString().toLowerCase())){
				 shij=resultMap.get(key);
					 if(shij.indexOf(":")>=0){
						 maohaoh=Integer.valueOf(shij.substring(shij.indexOf(":")+1));//获取分钟
						 shij=shij.substring(0,shij.indexOf(":"));//获取小时
					 }
				 double risyl;
				 if(planeModelData==null||planeModelData.getrJcsj()==null||planeModelData.getrJcsj().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrJcsj().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(Double.valueOf(shij)); 
				 BigDecimal b2 = new BigDecimal("60"); 
				 BigDecimal b3= ((b1.multiply(b2)).add(new BigDecimal(maohaoh))).divide(new BigDecimal(risyl).multiply(b2),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrQljxh()==null||planeModelData.getrQljxh().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrQljxh().doubleValue();//日使用量
				 }
				 
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.WINCH_CYCLE.toString().toLowerCase())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrJcxh()==null||planeModelData.getrJcxh().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrJcxh().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.EXT_SUSPENSION_LOOP.toString().toLowerCase())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrWdgxh()==null||planeModelData.getrWdgxh().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrWdgxh().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrTsjk1()==null||planeModelData.getrTsjk1().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrTsjk1().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrTsjk2()==null||planeModelData.getrTsjk2().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrTsjk2().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.N1.getName())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrN1()==null||planeModelData.getrN1().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrN1().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.N2.getName())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrN2()==null||planeModelData.getrN2().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrN2().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.N3.getName())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrN3()==null||planeModelData.getrN3().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrN3().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.N4.getName())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrN4()==null||planeModelData.getrN4().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrN4().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.N5.getName())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrN5()==null||planeModelData.getrN5().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrN5().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }else if(key.equals(MonitorItemEnum.N6.getName())){
				 double risyl;
				 if(planeModelData==null||planeModelData.getrN6()==null||planeModelData.getrN6().doubleValue()==0){
					 	risyl=1;
				 }else{
					  risyl=planeModelData.getrN6().doubleValue();//日使用量
				 }
				 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
				 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
				 shengyu.append(b3.intValue()+",");
				 
			 }
		 }
		//取最小值
		 String max ="";
		 if(!shengyu.toString().equals("")){
			 String[] index =new String[0];
				if(!"".equals(shengyu.toString())){
					index = shengyu.toString().split(",");
				}
						
				 //取最小值
				  max =index[0];
				 
				 for (int i = 0; i < index.length; i++) {
					 if(Integer.valueOf(index[i])<Integer.valueOf(max)){
						 max = index[i];
					 }
				 }
		 }else{
			 max="";
		 }
		 
		
		 
		return max;
	}
	
	/**
	 * 计算
	 * @param planeModelData
	 * @param resultMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public void calculationScheduledCheck(PlaneModelData planeModelData,Map<String, String> resultMap,ScheduledCheckItem item,Map<String, BigDecimal> zqzMap,Map<String, String> cursjMap,Map<String, String>curjhMap)throws Exception {
		String shij=null;
		int maohaoh=0; //剩余
		String syts = null ;
		
		List<ScheduledCheckMonitorItem> list = item.getMonitorItemList();
		
		//更新各个定检项，周期值，剩余天数，日使用量
		 for (String key : resultMap.keySet()) {
			 shij=resultMap.get(key);
			 if (!StringUtils.isBlank(shij)) {
				 if(key.equals(MonitorItemEnum.CALENDAR.toString().toLowerCase())){
					 fillMonitorItem(item, zqzMap, shij, key, 1);
				 }else if(key.equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString().toLowerCase())){
					 if(shij.indexOf(":")>=0){
						 maohaoh=Integer.valueOf(shij.substring(shij.indexOf(":")+1));//获取分钟
						 shij=shij.substring(0,shij.indexOf(":")); //获取小时
					 }
					
					 double risyl;
					 if(planeModelData.getrJsfxsj()==null||planeModelData.getrJsfxsj().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrJsfxsj().doubleValue();//日使用量
					 }
					 syts = String.valueOf(BigDecimal.valueOf((Double.valueOf(shij)*60+maohaoh)/(risyl*60)).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.SEARCH_LIGHT_TIME.toString().toLowerCase())){
					 if(shij.indexOf(":")>=0){
						 maohaoh=Integer.valueOf(shij.substring(shij.indexOf(":")+1));//获取分钟
						 shij=shij.substring(0,shij.indexOf(":"));//获取小时
					 }
					 double risyl;
					 
					 if(planeModelData==null||planeModelData.getrSsdsj()==null){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrSsdsj().doubleValue();//日使用量
					 }
						
					 syts = String.valueOf(BigDecimal.valueOf((Double.valueOf(shij)*60+maohaoh)/(risyl*60)).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
				 }else if(key.equals(MonitorItemEnum.WINCH_TIME.toString().toLowerCase())){
					 if(shij.indexOf(":")>=0){
						 maohaoh=Integer.valueOf(shij.substring(shij.indexOf(":")+1));//获取分钟
						 shij=shij.substring(0,shij.indexOf(":"));//获取小时
					 }
					 double risyl;
					 if(planeModelData.getrJcsj()==null||planeModelData.getrJcsj().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrJcsj().doubleValue();//日使用量
					 }
					
					 syts = String.valueOf(BigDecimal.valueOf((Double.valueOf(shij)*60+maohaoh)/(risyl*60)).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
				 }else if(key.equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
					 double risyl;
					 if(planeModelData.getrQljxh()==null||planeModelData.getrQljxh().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrQljxh().doubleValue();//日使用量
					 }
					 syts = String.valueOf(BigDecimal.valueOf(Double.valueOf(shij)/risyl).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.WINCH_CYCLE.toString().toLowerCase())){
					 double risyl;
					 if(planeModelData.getrJcxh()==null||planeModelData.getrJcxh().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrJcxh().doubleValue();//日使用量
					 }
					 syts = String.valueOf(BigDecimal.valueOf(Double.valueOf(shij)/risyl).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.EXT_SUSPENSION_LOOP.toString().toLowerCase())){
					 double risyl;
					 if(planeModelData.getrWdgxh()==null||planeModelData.getrWdgxh().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrWdgxh().doubleValue();//日使用量
					 }
					 syts = String.valueOf(BigDecimal.valueOf(Double.valueOf(shij)/risyl).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
					 double risyl;
					 if(planeModelData.getrTsjk1()==null||planeModelData.getrTsjk1().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrTsjk1().doubleValue();//日使用量
					 }

					 syts = String.valueOf(BigDecimal.valueOf(Double.valueOf(shij)/risyl).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){
					 double risyl;
					 if(planeModelData.getrTsjk2()==null||planeModelData.getrTsjk2().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrTsjk2().doubleValue();//日使用量
					 }
					 syts = String.valueOf(BigDecimal.valueOf(Double.valueOf(shij)/risyl).intValue()) ;
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.N1.getName())){
					 
					 double risyl;
					 if(planeModelData==null||planeModelData.getrN1()==null||planeModelData.getrN1().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrN1().doubleValue();//日使用量
					 }
					 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
					 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
					 syts = String.valueOf(b3.intValue());
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.N2.getName())){
					 
					 double risyl;
					 if(planeModelData==null||planeModelData.getrN2()==null||planeModelData.getrN2().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrN2().doubleValue();//日使用量
					 }
					 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
					 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
					 syts = String.valueOf(b3.intValue());
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.N3.getName())){
					  
					 double risyl;
					 if(planeModelData==null||planeModelData.getrN3()==null||planeModelData.getrN3().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrN3().doubleValue();//日使用量
					 }
					 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
					 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
					 syts = String.valueOf(b3.intValue());
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.N4.getName())){
					 
					 
					 double risyl;
					 if(planeModelData==null||planeModelData.getrN4()==null||planeModelData.getrN4().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrN4().doubleValue();//日使用量
					 }
					 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
					 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
					 syts = String.valueOf(b3.intValue());
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.N5.getName())){
					 
					 double risyl;
					 if(planeModelData==null||planeModelData.getrN5()==null||planeModelData.getrN5().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrN5().doubleValue();//日使用量
					 }
					 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
					 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
					 syts = String.valueOf(b3.intValue());
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
					 
				 }else if(key.equals(MonitorItemEnum.N6.getName())){
					  
					 double risyl;
					 if(planeModelData==null||planeModelData.getrN6()==null||planeModelData.getrN6().doubleValue()==0){
						 	risyl=1;
					 }else{
						  risyl=planeModelData.getrN6().doubleValue();//日使用量
					 }
					 BigDecimal b1 = new BigDecimal(resultMap.get(key)); 
					 BigDecimal b3= b1.divide(new BigDecimal(risyl),0, RoundingMode.FLOOR);
					 syts = String.valueOf(b3.intValue());
					 fillMonitorItem(item, zqzMap, syts, key, risyl);
				 }
			 }
			 
		 }
		  
		 if(list!=null && !list.isEmpty()){
			 Comparator<ScheduledCheckMonitorItem> comp = new Comparator<ScheduledCheckMonitorItem>() {
					@Override
					public int compare(ScheduledCheckMonitorItem o1, ScheduledCheckMonitorItem o2) {
						if (o1 == null) {
							o1 = new ScheduledCheckMonitorItem();
						}  
						if (o2 == null) {
							o2 = new ScheduledCheckMonitorItem();
						}  
						
						return new BigDecimal(StringUtils.isEmpty(o1.getSyts())?String.valueOf(Long.MAX_VALUE):o1.getSyts()).compareTo(new BigDecimal(StringUtils.isEmpty(o2.getSyts())?String.valueOf(Long.MAX_VALUE):o2.getSyts()));
					}
				 } ;
				ScheduledCheckMonitorItem minItem = Collections.min(list, comp);
			    item.setSyts(StringUtils.isNotBlank(minItem.getSyts())?minItem.getSyts():"0");
			    item.setRsyl(minItem.getRsyl());
			    item.setZqz(minItem.getZqz());
			    item.setCurjh(minItem.getJshz());
			    item.setCursj(minItem.getSj());
		 }
		 else{
			    item.setSyts(null);
			    item.setRsyl(null);
			    item.setZqz(null);
			    item.setCurjh(null);
			    item.setCursj(null);
		 }
		 
	    
	}

	private void fillMonitorItem(ScheduledCheckItem item, Map<String, BigDecimal> zqzMap,
			String syts, String key, double risyl) {
		ScheduledCheckMonitorItem monitorItem;
		monitorItem = item.getScheduledCheckMonitorMap().get(key);
		 if (monitorItem!=null) {
			 monitorItem.setZqz(zqzMap.get(key));
			 monitorItem.setSyts(syts);
			 monitorItem.setRsyl(String.valueOf(risyl));
		 }
	}

	/**
	 * 计算剩余 ,剩余算法：上限=计划值-实际值（日历除外，日历计划值=预拆值，sql已实现）
	 */
	@Override
	public String shengyu(TimeControlOptions timeControlOptions)
			throws Exception {
		String sy=null;//剩余 
	    if(timeControlOptions.getGdsx_dw().intValue()==ScheduledEnum.calendar.getId().intValue()||timeControlOptions.getGdsx_dw().intValue()==ScheduledEnum.DAY.getId().intValue()||timeControlOptions.getGdsx_dw().intValue()==ScheduledEnum.MOON.getId().intValue()||timeControlOptions.getGdsx_dw().intValue()==ScheduledEnum.YEAR.getId().intValue()){
			sy=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,StringUtils.isNotBlank(timeControlOptions.getSj())?timeControlOptions.getSj():"0", timeControlOptions.getGdsx())+"";
			 
		}else if(timeControlOptions.getGdsx_dw().intValue()==ScheduledEnum.TIME.getId().intValue()){
			sy=StringAndDate_Util.operateTime(timeControlOptions.getGdsx(), StringUtils.isNotBlank(timeControlOptions.getSj())?timeControlOptions.getSj():"0", TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);
			
		}else if (timeControlOptions.getGdsx_dw().intValue()==ScheduledEnum.LOOP.getId().intValue()){
			sy=(df.format(Double.valueOf(timeControlOptions.getGdsx())-Double.valueOf(StringUtils.isNotBlank(timeControlOptions.getSj())?timeControlOptions.getSj():"0")))+"";
			
		}
		if(sy==null){
			sy="";
		}
		return sy;
	}

	/**
	 * 时控件监控生成工单
	 */
	@Override
	public Map<String, Object> subgd(TimeControllWareItem timeControllWareItem)throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		try {
			nonroutineService.save(timeControllWareItem);//时控件监控生成工单
			returnMap.put("state", "success");
			
		} catch (Exception e) {
			returnMap.put("state", "error");
		}
		
		return returnMap;
	}
	
	@Override
	public Map<String, Object> checkUpdMt(User user, String id)throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		TimeControllWareItem timeControllWareItem=new TimeControllWareItem();
		timeControllWareItem.setId(id);
	
		List<TimeControllWareItem> list= timeControllWareItemMapper.queryAllPageList1(timeControllWareItem);
	
		if(list.get(0).getGdzt().equals("3")){
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "未审批完成,不可提交计划!");
			return returnMap;
		}
		
		if(list.get(0).getRwid()==null){
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "该任务单已存在不可提交!");
			return returnMap;
		}
		returnMap.put("state", "success");
		
		return returnMap;
	}
	
	/**
	 * 时控件监控提交计划
	 */
	@Override
	public Map<String, Object> subjh(TimeControllWareItem timeControllWareItem)throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		try {
			
			scheduledTaskSerivce.saveskj(timeControllWareItem);
			returnMap.put("state", "success");
			
		} catch (Exception e) {
			returnMap.put("state", "error");
		}
		
		return returnMap;
	}

	@Override
	public List<TimeControllWareItem> queryAllPageList1(
			TimeControllWareItem timeControllWareItem) throws Exception{
		return timeControllWareItemMapper.queryAllPageList1(timeControllWareItem);
	}

	@Override
	public Map<String, Object> saveJkbz(
			TimeControllWareItem timeControllWareItem) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		timeControllWareItemMapper.update(timeControllWareItem);
		
		try {
			
		 List<TimeControllWareItem> list=	timeControllWareItemMapper.queryAllPageList1(timeControllWareItem);
		if(list.get(0).getRwid()!=null&&!list.get(0).getRwid().equals("")){
			
			 ScheduledTask scheduledTask=  scheduledTaskSerivce.queryKey(list.get(0).getRwid());
			 scheduledTask.setBz(timeControllWareItem.getJkbz());
			 scheduledTaskSerivce.update(scheduledTask);//更新BS009表备注
			 
		
		}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 returnMap.put("state", "success");
		 return returnMap;
	}

	@Override
	public Map<String, Object> checkdg(User userFromSession, String id)
			throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		TimeControllWareItem timeControllWareItem=new TimeControllWareItem();
		timeControllWareItem.setId(id);
	
		List<TimeControllWareItem> list= timeControllWareItemMapper.queryAllPageList1(timeControllWareItem);
	
		if(list.get(0).getGdid()==null||list.get(0).getGdzt().equals("8")||list.get(0).getGdzt().equals("4")||list.get(0).getGdzt().equals("9")){
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "该工单已存在不可提交!");
			return returnMap;
		}
		returnMap.put("state", "success");
		
		return returnMap;
	}

	
	



}
