package com.eray.thjw.componentchangerecord.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.componentchangerecord.dao.ComponentchangerecordMapper;
import com.eray.thjw.componentchangerecord.service.ComponentchangerecordService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.PartsDisassemblyRecord;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.StringAndDate_Util;

import enu.MonitorItemEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
@Service
public class ComponentchangerecordServiceImpl implements ComponentchangerecordService{

	@Autowired
	private ComponentchangerecordMapper componentchangerecordMapper;
	/**
	 * @author sunji
	 * @description 根据查询条件分页查询维修记录
	 * @param PartsDisassemblyRecord
	 * @return List<PartsDisassemblyRecord>
	 * @develop date 2016.08.15
	 */
	public List<PartsDisassemblyRecord> queryPartsDisassemblyRecordList (
			PartsDisassemblyRecord partsDisassemblyRecord) throws BusinessException{
		List<PartsDisassemblyRecord> partsDisassemblyRecordList=componentchangerecordMapper.queryPartsDisassemblyRecordList(partsDisassemblyRecord);
		List<String> ids=new ArrayList<String>();
		for (PartsDisassemblyRecord pdr : partsDisassemblyRecordList) {
			ids.add(pdr.getId());
		}
		partsDisassemblyRecord.setIds(ids);
		if(ids.size()<=0){
			return partsDisassemblyRecordList;
		}
		
		List<Map<String,Object>> mountMap=componentchangerecordMapper.queryMountByid(partsDisassemblyRecord);
		List<Map<String,Object>> strikeMap=componentchangerecordMapper.queryStrikeByid(partsDisassemblyRecord);
		
		if (mountMap != null && !mountMap.isEmpty()) {
			strikeMap.addAll(mountMap);
		}
		if(strikeMap!=null){
			
			Collections.sort(strikeMap,new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return (o1.get("JKLBH")==null?"":o1.get("JKLBH").toString()).compareTo(o2.get("JKLBH")==null?"":o2.get("JKLBH").toString());
				}
			});
			
			if (strikeMap != null && !strikeMap.isEmpty()) {
				for (PartsDisassemblyRecord pdr : partsDisassemblyRecordList) {
					calculationData(strikeMap,pdr);
				}
			}
			Collections.sort(strikeMap,new Comparator<Map<String, Object>>() {
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return (o1.get("JKLBH")==null?"":o1.get("JKLBH").toString()).compareTo(o2.get("JKLBH")==null?"":o2.get("JKLBH").toString());
				}
			});
		}
		
		return partsDisassemblyRecordList;
	}
	/**
	 * 计算动态数据
	 * 1）、ZSSY 装上的剩余寿命 
	 * 2）、CXYY 拆下时已用  
	 * 3）、CXSY 拆下的剩余寿命
	 * @param partsDisassemblyRecords
	 * @throws BusinessException
	 */
	private void calculationData(List<Map<String, Object>> partsDisassemblyRecords,PartsDisassemblyRecord pdr) throws BusinessException {
	
		//监控类别
		Object jklbhObj = null;
		//部件预拆
		String bjyc = null;
		//安装日志
		String az_zxrq = null;
		
		//拆解日期
		String cj_zxrq = null;
		
		//装上的剩余寿命
		String zssy = null;
		
		//拆下的剩余寿命
		String cxsy = null; 
		
		//规定上限
		String gdsx = null; 
		
		//装机前已用
		String bjyy = null; 
		
		//拆下时已用
		String cxyy = null; 
		
		//在机使用
		String zjsy = null; 
		
		List<Map<String, Object>> pdb= new ArrayList<Map<String,Object>>();
		if (partsDisassemblyRecords != null && !partsDisassemblyRecords.isEmpty()) {
			
			for (Map<String, Object> row : partsDisassemblyRecords) {
				if(pdr.getId().equals(row.get("MAINID"))){
					pdb.add(row);
					jklbhObj = row.get("JKLBH");
					if (null != jklbhObj && StringUtils.isNotBlank(jklbhObj.toString())) {
						
						/*
						 * 公式说明： 一、装上/拆下信息sql中的mainid字段与主表中id字段对应，
						 * 第一个sql的id和第二个sql的mainid存在1对多的关系 
						 * 二、计算公式分情况说明： A、日历
						 * 1）、装上的剩余寿命 = 部件预拆 - 安装日期（单位：日）
						 * 2）、拆下时已用 = 在机使用 （单位：日历）
						 * 3）、拆下的剩余寿命 = 部件预拆 - 拆解日期（单位：日）
						 */
						if (MonitorItemEnum.isCalendar(jklbhObj.toString())) {
							bjyc = row.get("BJYC") == null ? DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE)
									: row.get("BJYC").toString();
							az_zxrq = row.get("AZ_ZXRQ") == null ? DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE)
									: row.get("AZ_ZXRQ").toString();
							// ZSSY 装上的剩余寿命 = 部件预拆 - 安装日期（单位：日）
							try {
								zssy = String.valueOf(DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,
										az_zxrq.toString(), bjyc.toString()));
								row.put("ZSSY", zssy);
							} catch (ParseException e) {
								throw new BusinessException("日历类型监控项目，日期格式化失败");
							}
							
							//拆卸飞行记录单id为空，没有拆下数据ZJSY，CXYY，CXSY
							if(row.get("CJ_FXJLDID")==null || row.get("CJ_FXJLDID").equals("")){
								row.put("ZJSY","");
							}
							else{
								// CXYY 拆下时已用 = 在机使用 （单位：日历）
								row.put("CXYY", row.get("ZJSY"));

								// CXSY 拆下的剩余寿命 = 部件预拆 - 拆解日期（单位：日）
								cj_zxrq = row.get("CJ_ZXRQ") == null ? DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE)
										: row.get("CJ_ZXRQ").toString();
								try {
									cxsy = String.valueOf(DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,
											cj_zxrq.toString(), bjyc.toString()));
									row.put("CXSY", cxsy);
								} catch (ParseException e) {
									throw new BusinessException("日历类型监控项目，日期格式化失败");
								}
							}
							
						}
						/*
						 * 公式说明： 一、装上/拆下信息sql中的mainid字段与主表中id字段对应，
						 * 第一个sql的id和第二个sql的mainid存在1对多的关系 二、计算公式分情况说明：
						 * B、时间和循环（应该考虑小时:分钟格式的加减，和循环单位格式的加减） 
						 * 1）、装上的剩余寿命 = 规定上限 - 装机前已用 
						 * 2）、拆下时已用 = 装机前已用 + 在机使用
						 * 3）、拆下的剩余寿命 = 装上的剩余寿命 - 在机使用
						 */
						else if (MonitorItemEnum.isTime(jklbhObj.toString())) {
							gdsx = row.get("GDSX") == null 
									? "0:0" 
									:StringAndDate_Util.operateTime(row.get("GDSX").toString(), "0:0", TimeSeparatorEnum.COLON,
									TimeOperationEnum.ADD);
							bjyy = row.get("BJYY") == null 
									? "0:0" 
									:StringAndDate_Util.operateTime(row.get("BJYY").toString(), "0:0", TimeSeparatorEnum.COLON,
									TimeOperationEnum.ADD);
							zjsy = row.get("ZJSY") == null 
									? "0:0" 
									: StringAndDate_Util.operateTime(row.get("ZJSY").toString(), "0:0", TimeSeparatorEnum.COLON,
									TimeOperationEnum.ADD);
							row.put("GDSX",gdsx);
							row.put("BJYY",bjyy);
							row.put("ZJSY",zjsy);
							
							// ZSSY 装上的剩余寿命 = 规定上限 - 装机前已用
							zssy = StringAndDate_Util.operateTime(gdsx, bjyy, TimeSeparatorEnum.COLON,
									TimeOperationEnum.SUBTRACT);
							row.put("ZSSY", zssy);

							//拆卸飞行记录单id为空，没有拆下数据ZJSY，CXYY，CXSY
							if(row.get("CJ_FXJLDID")==null || row.get("CJ_FXJLDID").equals("")){
								row.put("ZJSY","");
							}
							else{
								// CXYY 拆下时已用 = 装机前已用 + 在机使用
								cxyy = StringAndDate_Util.operateTime(bjyy, zjsy, TimeSeparatorEnum.COLON,
										TimeOperationEnum.ADD);
								
								row.put("CXYY", cxyy );
		
								// CXSY 拆下的剩余寿命 = 装上的剩余寿命 - 在机使用
								cxsy = StringAndDate_Util.operateTime(zssy, zjsy, TimeSeparatorEnum.COLON,
										TimeOperationEnum.SUBTRACT);
								row.put("CXSY", cxsy );
								
							}

						}
						// B2、时间和循环（应该考虑小时:分钟格式的加减，和循环单位格式的加减）
						
						else if (MonitorItemEnum.isLoop(jklbhObj.toString())) {
							
							row.put("GDSX",row.get("GDSX"));
							row.put("BJYY",format2Bit(row.get("BJYY")));
							row.put("ZJSY",format2Bit(row.get("ZJSY")));
							
							gdsx = row.get("GDSX") == null ? "0" : row.get("GDSX").toString();
							bjyy = row.get("BJYY") == null ? "0" : row.get("BJYY").toString();
							zjsy = row.get("ZJSY") == null ? "0" : row.get("ZJSY").toString();
							
							// ZSSY 装上的剩余寿命 = 规定上限 - 装机前已用
							zssy = String.valueOf(new BigDecimal(gdsx).subtract(new BigDecimal(bjyy)).doubleValue());
							row.put("ZSSY", format2Bit(zssy));

							//拆卸飞行记录单id为空，没有拆下数据ZJSY，CXYY，CXSY
							if(row.get("CJ_FXJLDID")==null || row.get("CJ_FXJLDID").equals("")){
								row.put("ZJSY","");
							}
							else{
								// CXYY 拆下时已用 = 装机前已用 + 在机使用
								cxyy = String.valueOf(new BigDecimal(bjyy).add(new BigDecimal(zjsy)).doubleValue());
								row.put("CXYY", format2Bit(cxyy));
		
								// CXSY 拆下的剩余寿命 = 装上的剩余寿命 - 在机使用
								cxsy = String.valueOf(new BigDecimal(zssy).subtract(new BigDecimal(zjsy)).doubleValue());
								row.put("CXSY", format2Bit(cxsy));
							}
						}
						
					}
					else {
						row.put("ZJSY","");
					}
			}
		}

			// 规则：按飞机注册号合并监控项目
			Map<String, Object> map=mergeMonitorItem(pdb,pdr);

			Iterator<Map<String, Object>> it = partsDisassemblyRecords.iterator();
			while (it.hasNext()) {
				Map<String, Object> row = it.next();
				if (map!=null &&map.get("FJZCH")!=null && row.get("FJZCH").equals(map.get("FJZCH")) && row.get("UUID")==null) {
					it.remove();
				}
			}
			if(map!=null){
				pdr.setGdsx(map.get("GDSX")==null?"":map.get("GDSX").toString());
				pdr.setBjyy(map.get("BJYY")==null?"":map.get("BJYY").toString());
				pdr.setZjsy(map.get("ZJSY")==null?"":map.get("ZJSY").toString());
				pdr.setZssy(map.get("ZSSY")==null?"":map.get("ZSSY").toString());
				pdr.setCxyy(map.get("CXYY")==null?"":map.get("CXYY").toString());
				pdr.setCxsy(map.get("CXSY")==null?"":map.get("CXSY").toString());
			}
		}
	}
	/**
	 * 规则：按飞机注册号合并监控项目
	 * @param partsDisassemblyRecords
	 * @return
	 */
	public Map<String, Object> mergeMonitorItem(List<Map<String, Object>> partsDisassemblyRecords,PartsDisassemblyRecord pdr) {
		Map<Object, Map<String, Object>> uniqueMaps = new HashMap<Object, Map<String, Object>>();
		Object fjzch = null;
		Map<String, Object> map = null;
		StringBuffer sb = new StringBuffer();
		
		for (Map<String, Object> row : partsDisassemblyRecords) {
			sb.delete(0, sb.length());
			fjzch = row.get("MAINID");
			if (row.get("JKLBH")!=null) {
				
				if (!uniqueMaps.containsKey(fjzch)) {
					sb.delete(0, sb.length());
					sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
					sb.append(":");
					sb.append(row.get("GDSX").toString());
					row.put("GDSX", sb.toString());
					
					
					sb.delete(0, sb.length());
					sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
					sb.append(":");
					sb.append(row.get("BJYY").toString());
					row.put("BJYY", sb.toString());
					
					sb.delete(0, sb.length());
					sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
					sb.append(":");
					sb.append(row.get("ZSSY").toString());
					row.put("ZSSY", sb.toString());
					
					//拆卸飞行记录单id为空，没有拆下数据ZJSY，CXYY，CXSY
					if(row.get("CJ_FXJLDID")!=null && !row.get("CJ_FXJLDID").equals("")){
						
						sb.delete(0, sb.length());
						sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
						sb.append(":");
						sb.append(row.get("CXYY").toString());
						row.put("CXYY", sb.toString());
						
						
						sb.delete(0, sb.length());
						sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
						sb.append(":");
						sb.append(row.get("CXSY").toString());
						row.put("CXSY", sb.toString());
						
						
						sb.delete(0, sb.length());
						sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
						sb.append(":");
						sb.append(row.get("ZJSY").toString());
						row.put("ZJSY", sb.toString());
						
					}
					
					row.put("UUID" ,UUID.randomUUID().toString());
					uniqueMaps.put(fjzch, row);
				}else {
					sb.delete(0, sb.length());
					map = uniqueMaps.get(fjzch);
					sb.append(map.get("GDSX").toString());
					sb.append(",");
					sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
					sb.append(":");
					sb.append(row.get("GDSX").toString());
					map.put("GDSX", sb.toString());

					sb.delete(0, sb.length());
					sb.append(map.get("BJYY").toString());
					sb.append(",");
					sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
					sb.append(":");
					sb.append(row.get("BJYY").toString());
					map.put("BJYY", sb.toString());

					sb.delete(0, sb.length());
					sb.append(map.get("ZSSY").toString());
					sb.append(",");
					sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
					sb.append(":");
					sb.append(row.get("ZSSY").toString());
					map.put("ZSSY", sb.toString());

					if(row.get("CJ_FXJLDID")!=null && !row.get("CJ_FXJLDID").equals("")){
						sb.delete(0, sb.length());
						sb.append(map.get("CXYY").toString());
						sb.append(",");
						sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
						sb.append(":");
						sb.append(row.get("CXYY").toString());
						map.put("CXYY", sb.toString());

						sb.delete(0, sb.length());
						sb.append(map.get("CXSY").toString());
						sb.append(",");
						sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
						sb.append(":");
						sb.append(row.get("CXSY").toString());
						map.put("CXSY", sb.toString());

						sb.delete(0, sb.length());
						sb.append(map.get("ZJSY").toString());
						sb.append(",");
						sb.append(MonitorItemEnum.getName(row.get("JKLBH").toString()));
						sb.append(":");
						sb.append(row.get("ZJSY").toString());
						map.put("ZJSY", sb.toString());
					}
				}
			}
			if(partsDisassemblyRecords.size()==1){
				map = uniqueMaps.get(fjzch);
			}
		}
		return map;
	}
	
	private static String format2Bit(Object pi) {
		if (pi==null || StringUtils.isBlank(pi.toString()) ) {
			return "0.00";
		}
		else{
			BigDecimal bd = new BigDecimal(pi.toString());  
	        DecimalFormat df = new DecimalFormat("#######0.00");//小数点点不够两位补0，例如："0" --> 0.00（个位数补成0因为传入的是0则会显示成：.00，所以各位也补0；）  
	        String xs = df.format(bd.setScale(2, BigDecimal.ROUND_DOWN));//直接截取小数点后两位（不四舍五入）
			return xs;
		}
	}
	
	
	
}
