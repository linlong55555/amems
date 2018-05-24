package com.eray.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 日期工具类
 * @CreateTime 2018-1-8 上午10:21:05
 * @CreateBy 刘兵
 */
public class DateUtils { 
	
	/**
	 * @Description 将日期转换为字符串
	 * @CreateTime 2018-1-8 上午10:21:21
	 * @CreateBy 刘兵
	 * @param format 格式化参数
	 * @param date 日期
	 * @return String 日期格式的字符串
	 */
	public static String getDateStr(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	/**
	 * @Description 将日期转换为字符串
	 * @CreateTime 2018-1-8 上午10:21:21
	 * @CreateBy 刘兵
	 * @param format 格式化参数
	 * @param date 日期
	 * @return String 日期格式的字符串
	 */
	public static String getDateStr(String format,Object date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
}  
