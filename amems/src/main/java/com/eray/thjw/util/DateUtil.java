package com.eray.thjw.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil { 
	
	public  static final String DEFAULT_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public  static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
	
	public static String getDateStr() {
		 return getDateStr(DEFAULT_FORMAT_DATE_TIME);
	}
	
	/**
	 * @Description 为日期增加偏移量
	 * @CreateTime 2017年10月10日 下午5:14:32
	 * @CreateBy 徐勇
	 * @param strDate 字符串日期 yyyy-MM-dd
	 * @param offset 偏移量，可为非0正负整数
	 * @param unit Calendar.MONTH 月； Calendar.DATE 天
	 * @return
	 * @throws ParseException 
	 */
	public static String getOffsetDate(String strDate, int offset, int unit) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getStr2Date(DEFAULT_FORMAT_DATE, strDate));
		cal.add(unit, offset);
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
		return formatter.format(cal.getTime());
	}
	
	public static String getOffsetDate(Date strDate, int offset, int unit) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(strDate);
		cal.add(unit, offset);
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
		return formatter.format(cal.getTime());
	}
	
	public static String getDatePlus(String datestr,int amount) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getStr2Date(DEFAULT_FORMAT_DATE_TIME, datestr));
		cal.add(Calendar.DAY_OF_MONTH, amount);
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
		return formatter.format(cal.getTime());
	}
	public static String getDatePlus(Date date,int amount) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
		return formatter.format(cal.getTime());
	}
	
	public static String getDateStr(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
	public static String getDateStr(String format,Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public static Date getStr2Date(String format,String datestr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(datestr);   
	}
	
	public static int getConsumeDays(String format,String startDatestr) throws ParseException {
		Long formats = getStr2Date(format, startDatestr).getTime();
		Long day=formats/(24*60*60*1000); 
		 return day.intValue();
	}
	
	public static int getConsumeDay(String format,String startDatestr,String endDatestr) throws ParseException {
		Long end = getStr2Date(format, endDatestr).getTime();
		Long start = getStr2Date(format, startDatestr).getTime();
		Long day=(end-start)/(24*60*60*1000); 
		 return day.intValue();
	}
	
	public static int getConsumeDay(Date start,Date end) throws ParseException {
		Long day=(end.getTime()-start.getTime())/(24*60*60*1000); 
		return day.intValue();
	}
	
	public static String getConsumeTime(Long start,Long end) {
		long ss=(end - start)/(1000); //共计秒数
		long day=(end-start)/(24*60*60*1000); 
		long hh= (long)ss/3600 - (day*24);  //共计小时数  ;
		long mm = (long)ss/60 - (day*24*60) -(hh*60) ;   //共计分钟数  
		long s = ss - (day*24*3600) - (hh*3600) - mm*60 ;
		return "共"+day+"天 准确时间是："+hh+" 小时 "+mm+" 分钟"+s+" 秒 ";  
	}
	
    /**  
    * 提供精确的加法运算。  
    * @param v1 被加数  
    * @param v2 加数  
    * @return 两个参数的和  
    */  
    public static double add(double v1,double v2){   
    BigDecimal b1 = new BigDecimal(Double.toString(v1));   
    BigDecimal b2 = new BigDecimal(Double.toString(v2));   
    return b1.add(b2).doubleValue();   
    }   
    /**  
    * 提供精确的减法运算。  
    * @param v1 被减数  
    * @param v2 减数  
    * @return 两个参数的差  
    */  
    public static double sub(double v1,double v2){   
    BigDecimal b1 = new BigDecimal(Double.toString(v1));   
    BigDecimal b2 = new BigDecimal(Double.toString(v2));   
    return b1.subtract(b2).doubleValue();   
    }   
    /**  
    * 提供精确的乘法运算。  
    * @param v1 被乘数  
    * @param v2 乘数  
    * @return 两个参数的积  
    */  
    public static double mul(double v1,double v2){   
    BigDecimal b1 = new BigDecimal(Double.toString(v1));   
    BigDecimal b2 = new BigDecimal(Double.toString(v2));   
    return b1.multiply(b2).doubleValue();   
    }

	public static String getDate4Month() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE,cal.getTime()).concat(" ~ ").concat(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
	}  
	
	public static String getFirst4Month() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE,cal.getTime());
	}
	
	/**
	 * @author liub
	 * @description 根据年获取月份及每月的周数(月数,周数)
	 * @param year
	 * @return Map<Integer, Integer>
	 */
	public static Map<Integer, Integer> getMonthAndWeek(Integer year){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Calendar c = Calendar.getInstance(); 
        c.set(Calendar.YEAR, year); 
		for(int i = 0 ; i < 12; i++){
			c.set(Calendar.MONTH, i); 
			map.put(i, c.getActualMaximum(Calendar.WEEK_OF_MONTH));
		}
		return map;
	}
}  
