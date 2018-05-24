package com.eray.thjw.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;

/**
 * @description 对字符串和日期类的补充操作
 * @author meizhiliang
 *@develop date 2016-7-27
 */
public class StringAndDate_Util {
	
	    
	    /** 分钟-小时，进位值 */
	    private final static int TIME_CARRY_NUMBER = 60; 
	 
	
      /**
       * @description 字符串的前置插入操作
       * @param str1 被操作的字符串 
       * @param str2  要插入的字符串
       * @param partStr1 表示位置的字符串
       */
	public  static StringBuffer connetString(String str1,String str2,String partStr1) throws NullPointerException{
	      StringBuffer s1 = new StringBuffer(str1);
	      Pattern p = Pattern.compile(partStr1);
		  Matcher m = p.matcher(s1.toString());
			if(m.find()){
			  s1.insert((m.start()), str2);
			}
	    	return s1;
	     }
	/**
	 * @description 字符串前后补字符串操作
	 * @param str1 被操作的字符串 
	 * @param str2 补入的字符串
	 * @param length 返回字符串的长度
	 * @parnm flag 前补还是后补  flag = true 表示前补  flag=false 表示后补
	 */
	public static String addString(String str1,String str2,int length,boolean flag){
		
		StringBuffer  sb =null;
		String str = null;
		
		if(flag&&null != str1 && !"".equals(str1)&&length>str1.length()){
			int str1length=str1.length();
			sb = new StringBuffer();
			for (int i = 0; i < length-str1length; i++) {
				sb.append(str2);
		    }
			   sb.append(str1);
			   str=sb.toString();
		}else {
				str=str1;
		}
		return str;
	}
	/**
	 *  @description 根据当前日历，给定日期格式，根据参数实现日历的偏移。
	 * @param count 日历的偏移量  count<0 表示向过去偏移;count>0 表示向未来偏移;count=0表示当前时间
	 * @param pattern 给定的格式化模式
	 * @return  String 
	 */
	 public  static String getSomeDayForString(int count,String pattern){
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.DATE,count);
	        String str =null;
	        try{
	        	if(null != pattern && !"".equals(pattern)){
	        		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
	        		str =sdf.format(calendar.getTime());
	        	}
	        }catch(IllegalArgumentException e){
	        	e.printStackTrace();
	        }finally{};
	        return str;
	 }
	 /**
      * @description 取得给定日期的下一天
      *@param  date
      */
	 public static String getTomorrow(Date date){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_MONTH, 1);
		  Date date1 = new Date(calendar.getTimeInMillis());
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		  return sdf.format(date1);
      }
	 
	 
	 /**
	  * @description 将时间对象转成时间戳,参数是一个日期
	  * @param time
	  * @return long
	  */
	 public  static long DateToLong(Date time) throws Exception{
	     long timeL = time.getTime();
	     return timeL ;
	  }
	 /**
	     * @description 根据日期得到星期几,得到数字。
	     *@param  7, 1, 2, 3, 4, 5, 6 表示星期的天数
	     * @return Integer 如：6
	     */
	 public static  int dayOfWeekInt() {
	        Integer dayNames[] = { 7, 1, 2, 3, 4, 5, 6 };
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(new Date());
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	        if (dayOfWeek < 0)
	            dayOfWeek = 0;
	        return dayNames[dayOfWeek];
	 }
	  /**
	     * 将当前时间通过特定格式转化成时间字符串
	     * @param dateStr 时间字符串
	     * @param dataFormat 当前时间字符串的格式。
	     * @return net.maxt.util.Date 日期 ,转换异常时返回null。
	     */
	    public static String parseString(String pattern){
	    	   String strTime=null;
	    	   try{
		        	if(null != pattern && !"".equals(pattern)){
		        		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		        		strTime =sdf.format(new Date());
		        	}
		        }catch(IllegalArgumentException e){
		        	e.printStackTrace();
		        }finally{};
		        return strTime;
	    }        
	    
	    /**
	     * 时间运算
	     * @param time1	时间1
	     * @param time2	时间2
	     * @param separator	输出的分隔符（":"或者"."），为null则分隔符为默认的":"
	     * @param operation	操作（"+"或"-"），为null则操作为默认的"+"
	     * @return
	     */
	    public static String operateTime(String time1, String time2, TimeSeparatorEnum separator, TimeOperationEnum operation){
	    	int minuteAllTotal = 0;
	    	// 转换成分钟
	    	int minute1Total = convertToMinute(time1);
	    	int minute2Total = convertToMinute(time2);
	    	// 操作为空默认为加法
	    	operation = operation == null ? TimeOperationEnum.ADD : operation;
	    	// 分隔符为空默认为冒号
	    	separator = separator == null ? TimeSeparatorEnum.COLON : separator;
	    	if(operation == TimeOperationEnum.ADD){
	    		minuteAllTotal = minute1Total + minute2Total;
	    	}else if(operation == TimeOperationEnum.SUBTRACT){
	    		minuteAllTotal = minute1Total - minute2Total;
	    	}
	    	return convertToHour(minuteAllTotal, separator);
	    }
	    
	    /**
	     * 在时间中获取小时
	     * 例如：输入162:25，则返回162
	     * @param time
	     * @return
	     */
	    private static int getHour(String time){
	    	for (TimeSeparatorEnum separator : TimeSeparatorEnum.values()) {
				if(time.contains(separator.getDescribe())){
					return Integer.parseInt(time.split(separator.getValue())[0]);
				}
			}
	    	return Integer.parseInt(time);
	    }
	    
	    /**
	     * 在时间中获取分钟
	     * 例如：输入162:25，则返回25
	     * @param time
	     * @return
	     */
	    private static int getMinute(String time){
	    	for (TimeSeparatorEnum separator : TimeSeparatorEnum.values()) {
				if(time.contains(separator.getDescribe()) 
						&& time.split(separator.getValue()).length == 2){
					return Integer.parseInt(time.split(separator.getValue())[1]);
				}
			}
	    	return 0;
	    }
	    
	    /**
	     * @Description 转换成分钟
	     * @CreateTime 2017年9月14日 上午9:32:47
	     * @CreateBy 韩武
	     * @param time
	     * @return
	     */
	    public static String convertToMinuteStr(String time){
	    	if(StringUtils.isBlank(time)){
	    		return null;
	    	}
	    	return String.valueOf(convertToMinute(time));
	    }
	    
	    /**
	     * 转换成分钟
	     * @param time
	     * @return
	     */
	    public static Integer convertToMinute(String time){
	    	int hour = getHour(time);
	    	int minute = getMinute(time);
	    	boolean reverseFlag = false;
	    	if(hour < 0){
	    		reverseFlag = true;
	    		hour = 0 - hour;
	    	}
	    	minute = hour * TIME_CARRY_NUMBER + minute;
	    	return reverseFlag ? (0 - minute) : minute;
	    }
	    
	    /**
	     * 转换成小时
	     * @Description 
	     * @CreateTime 2017年8月30日 下午5:00:48
	     * @CreateBy 韩武
	     * @param minuteTotal
	     * @return
	     */
	    public static String convertToHour(String minuteTotal){
	    	if(StringUtils.isBlank(minuteTotal)){
	    		return null;
	    	}
	    	int minute = Integer.parseInt(minuteTotal);
	    	return convertToHour(minute, TimeSeparatorEnum.COLON);
	    }
	    
	    /**
	     * 转换成小时
	     * @param minute
	     * @param separator
	     * @return
	     */
	    public static String convertToHour(int minuteTotal, TimeSeparatorEnum separator){
	    	boolean reverseFlag = false;
	    	if(minuteTotal < 0){
	    		reverseFlag = true;
	    		minuteTotal = 0 - minuteTotal;
	    	}
	    	int hour = minuteTotal / TIME_CARRY_NUMBER;
	    	int minute = minuteTotal % TIME_CARRY_NUMBER;
	    	if(minute == 0){
	    		return (reverseFlag ? "-":"") + hour;
	    	}else{
	    		return (reverseFlag ? "-":"") + hour + separator.getDescribe() + String.format("%02d", minute);
	    	}
	    }
	    
	    /**
		 * @Description 将Object转换成int
		 * @CreateTime 2018-1-8 下午3:24:16
		 * @CreateBy 刘兵
		 * @param value 参数
		 * @return int
		 */
		public static int ObjectToInt(Object value) {  
		 	int num = 0;
	        if(value != null) {  
	            if(value instanceof BigDecimal) { 
	            	num = ((BigDecimal)value).intValue();  
	            }else if(value instanceof Integer) {  
	            	num = (Integer)value;  
	            }else if(value instanceof Double) { 
	            	num = ((Double)value).intValue();  
	            }else{  
	                throw new ClassCastException("格式错误!");  
	            }  
	        }  
	        return num;  
		}  
		
}
