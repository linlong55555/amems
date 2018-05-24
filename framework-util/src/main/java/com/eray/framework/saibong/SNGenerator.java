package com.eray.framework.saibong;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.framework.enu.PosiEnum;
import com.eray.framework.exception.CustomRedisException;
import com.eray.framework.exception.SaibongException;
import com.eray.framework.util.DateUtils;
import com.eray.framework.util.FormatUtils;
import com.google.gson.Gson;

/**
 * @Description 
 * @CreateTime 2018-1-3 下午3:23:01
 * @CreateBy 刘兵
 */
public abstract class SNGenerator{
	
	Map<String, Object> ruleMap;//规则解析后实体
	Object entity;//业务属性实体
	SNGenerator(){}
	SNGenerator(Object obj){
		this.entity = obj;
//		this.getEntity(obj);
	}
	/**
	 * @Description 采番规则解析
	 * @CreateTime 2018-1-5 下午2:22:52
	 * @CreateBy 刘兵
	 * @param rule
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> analysisRule(String rule) throws SaibongException{
		if (rule == null || "".equals(rule.trim())) {
			throw new SaibongException("采番异常，无采蕃规则");
		}
		ruleMap = new Gson().fromJson(rule.trim(), Map.class);
		Map<String, Object> tempMap = null;
		int d_num = 0;//流水字段类型的计数器
		StringBuffer snno = new StringBuffer();
		int size = ruleMap.size();
		for (int num = 1; num <= size; num++) {
			tempMap = (Map<String, Object>) ruleMap.get("sn:" + num);
			if(null == tempMap){
				continue;
			}
			if ("$N".equals(tempMap.get("type"))) {
				ruleMap.put("numbertype", tempMap.get("cont"));//将流水类型存入，方便后面引用
				++d_num;
			} else if ("$T".equals(tempMap.get("type"))) {
				try {
					tempMap.put("value", DateUtils.getDateStr(tempMap.get("cont").toString()));
				} catch (Exception e) {
					throw new SaibongException("采番异常，时间格式错误!");//时间格式化后存入value中，发生错误则报错：时间格式错误
				}
				if ("Y".equalsIgnoreCase(tempMap.get("join") != null?tempMap.get("join").toString():"Y")) {
					snno.append(tempMap.get("value"));//加入到snno中
				}
			} else if ("$S".equals(tempMap.get("type"))) {
				tempMap.put("value",tempMap.get("cont"));//固定字符存入value中
				if ("Y".equalsIgnoreCase(tempMap.get("join") != null?tempMap.get("join").toString():"Y")) {
					snno.append(tempMap.get("cont"));//加入到snno中
				}
			} else if ("$D".equals(tempMap.get("type"))) {
				String d_str = get_DtypeValue(tempMap);
				tempMap.put("value",d_str);//业务字段存入value中
				if ("Y".equalsIgnoreCase(tempMap.get("join") != null?tempMap.get("join").toString():"Y")) {
					snno.append(d_str);//加入到snno中
				}
			} else {
				throw new SaibongException("采番异常，格式错误!");//报错：格式错误
			}
		}
		if(d_num != 1){
			throw new SaibongException("采番异常，流水类型有且只有一个!");//报错：规则定义错误，流水类型有且只有一个。
		}
		ruleMap.put("snno",snno.toString());//snno存入
		return ruleMap;
	}
	
	/**
	 * @Description 拼接番号
	 * @CreateTime 2018-1-11 上午10:59:56
	 * @CreateBy 刘兵
	 * @param ruleMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String appendFh(Map<String, Object> ruleMap){
		StringBuffer fh = new StringBuffer();
		//拼接番号
		for (int num = 1; num <= ruleMap.size(); num++) {
			Map<String, Object> tempMap = (Map<String, Object>) ruleMap.get("sn:" + num);
			if(null != tempMap){
				if ("$N".equals(tempMap.get("type"))) {
					fh.append(ruleMap.get("sn_str"));
				}else{
					fh.append(tempMap.get("value"));
				}
			}
		}
		return fh.toString();
	}
	
	/**
	 * @Description 获取业务字段值
	 * @CreateTime 2018-1-5 下午2:22:29
	 * @CreateBy 刘兵
	 * @param map
	 * @return 业务字段值
	 */
	@SuppressWarnings("unchecked")
	private String get_DtypeValue(Map<String, Object> map) {
		if(map == null){
			return "";
		}
		Map<String, Object> contMap = (Map<String, Object>) map.get("cont");
		Map<String, String> patternMap = (Map<String, String>) contMap.get("pattern");
		String retStr = getDeclaredFieldValue((String)contMap.get("field"));//根据contMap.get("field")做java反射获取并执行匹配的po的get方法;
		if(StringUtils.isNotBlank(retStr)){
			int start = FormatUtils.ObjectToInt(contMap.get("start"));
			int end = contMap.get("end") != null ? FormatUtils.ObjectToInt(contMap.get("end")) : retStr.length() - 1;
			if(start < end){
				if(start < 0){//支持负数，方便从后截取
					start = start + retStr.length();
				}
				if(end < 0){//支持负数，方便从后截取
					end = end + retStr.length();
				}
				retStr = retStr.substring(start, end);
			}
			
			if(patternMap != null){
				if(patternMap.get(retStr) != null){
					retStr = patternMap.get(retStr);
				}else if(contMap.get("pattern-default") != null){
					retStr = (String)contMap.get("pattern-default");
				}
			}
		}
		return retStr;
	}
	
	/**
	 * @Description 根据字段名获取字段值
	 * @CreateTime 2018-1-5 下午1:44:13
	 * @CreateBy 刘兵
	 * @param field
	 * @return 字段值
	 */
	public String getValueByField(String field){
		return getDeclaredFieldValue(field);
	}
	
	/**
	 * @Description 通过反射获取字段值
	 * @CreateTime 2018-1-5 下午2:21:07
	 * @CreateBy 刘兵
	 * @param field
	 * @return 字段值
	 */
	private String getDeclaredFieldValue(String field){
		String fieldvalue = "";
		if(StringUtils.isNotBlank(field) && null != entity){
			Class<? extends Object> clazz = entity.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				try{
					Field f = entity.getClass().getDeclaredField(field);//获得属性
					f.setAccessible(true);
					if(null != f.get(entity)){
						fieldvalue = String.valueOf(f.get(entity));
					}
					break;
				} catch (Exception e){
					continue;
				}
			}
		}
		return fieldvalue;
	}
	
	/**
	 * @Description 流水号格式化
	 * @CreateTime 2018-1-5 下午3:51:36
	 * @CreateBy 刘兵
	 * @param num 流水号 
	 * @param length 格式化长度
	 * @param fill 填补字符
	 * @param posi 补0方式，B前补 A后补，N不补
	 * @return String 流水号
	 */
	protected String format(String num, int length, String fill, String posi){
		if(PosiEnum.B.toString().equals(posi)){//前补
			num = StringUtils.leftPad(num, length, fill);
		}else if(PosiEnum.A.toString().equals(posi)){//后补
			num = StringUtils.rightPad(num, length, fill);
		}
		return num;
	}
	
	
	/**
	 * @Description 获取番号
	 * @CreateTime 2018-1-8 上午11:49:49
	 * @CreateBy 刘兵
	 * @param dprtcode 组织机构代码
	 * @param key 采蕃Key
	 * @return String 番号
	 * @throws CustomRedisException 
	 * @throws Exception
	 */
	abstract String generateSN(String dprtcode, String key) throws Exception;
	
	/**
	 * @Description 加载采番规则
	 * @CreateTime 2018年1月18日 上午11:31:51
	 * @CreateBy 徐勇
	 */
	abstract void loadRules() throws Exception;
}
