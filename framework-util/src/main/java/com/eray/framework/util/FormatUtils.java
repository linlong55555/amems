package com.eray.framework.util;

import java.math.BigDecimal;


/**
 * @Description 基本类型工具类
 * @CreateTime 2018-1-8 上午10:21:05
 * @CreateBy 刘兵
 */
public class FormatUtils { 
	
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
