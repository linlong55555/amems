package com.eray.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.eray.util.Global;
import com.eray.util.format.FormatUtil;

public class DateCalculation {
	/**
	 * 将字符串转成时间格式
	 * @param dateStr
	 * @return
	 */
	public static Date StringToDate(String dateStr){
		DateFormat dd=new SimpleDateFormat("yyyyMMdd");
		Date date=null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将字符串转换成时间 2016.07.25
	 * @param dateStr 时间字符串
	 * @param style 时间样式
	 * @return Date
	 */
	public static Date StringToDate(String dateStr,String style) {
		DateFormat dd=new SimpleDateFormat(style);
		Date date=null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static void main(String[] args) {
		Date date = DateCalculation.StringToDate("2016072524:00", "yyyyMMddHH:mm");
		System.out.println(date);
		//String s = UUID.randomUUID().toString();
		//System.out.println(s.length());
		//System.out.println(DateCalculation.getProDate("20170601", -1));
	}
	
	/**
	 * 计算yyyyMMdd(如20170607)的前gap（1）天（20160607）
	 * @param dateStr 时间字符串
	 * @param gap 相隔的天数，正数就是后gap天，负数就是前gap天
	 * @return
	 */
	public static String getProDate(String dateStr,int gap) {
		Date date = DateCalculation.StringToDate(dateStr, "yyyyMMdd");
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, gap);
			return FormatUtil.formatDate(cal.getTime(),"yyyyMMdd");
		}
		return null;
	}
	
	/**
	 * 计算时间间距
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long gapDate(Date start,Date end){
		Long t = end.getTime() - start.getTime();
		if (t <0){
			return 0L;
		}
		return t/(1000 * 60 * 60 *24) + 1;
	}
	
	/**
	 * 获得date时间的上个月
	 * @param date 起始月份
	 * @param i 相隔的月份
	 * @return Date
	 */
	public static Date getLastMonth(Date date,int i) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
		return calendar.getTime();
	}
	
	/**
	 * 时间在当前的天数上加上几天
	 * @param date 参照时间
	 * @param i 相加的天数
	 * @return DATE
	 */
	@SuppressWarnings("static-access")
	public static Date dateAdd(Date date,Float i){
		if (date == null) {
			return null;
		}
		if (i == null || i == 0){
			return date;
		}
		Integer add = 0;
		if (i != null && i > 0f) { //每天按6个工作时间算
			Integer workHour = 6;
			if (i <= workHour){
				add = 0;
			} else {
				if (i % workHour == 0){
					add = (int) (i / workHour) - 1;
				} else {
					add = (int) (i / workHour) ;
				}
			}
		}
		Calendar calendar=Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.add(calendar.DATE, add);
		return calendar.getTime();
	}
}
