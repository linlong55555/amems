package com.eray.thjw.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eray.thjw.po.User;

import enu.ThreadVar;

public class ThreadVarUtil {
	
	    private static final ThreadLocal<Object> SESSION_MAP = new ThreadLocal<Object>();  
	    
	    protected ThreadVarUtil() {  
	    }  
	  
	    /** 
	     * 获得线程中保存的属性. 
	     * @param attribute  属性名称 
	     * @return 属性值 
	     */  
	    @SuppressWarnings("unchecked")
		public static Object get(String attribute) {  
	        Map<String,Object> map = (Map<String,Object>) SESSION_MAP.get();  
	        return map.get(attribute);  
	    }  
	  
	    /** 
	     * 获得线程中保存的属性，使用指定类型进行转型. 
	     * @param attribute 属性名称 
	     * @param clazz  类型 
	     * @param <T>  自动转型 
	     * @return 属性值 
	     */  
	    @SuppressWarnings("unchecked")
		public static <T> T get(String attribute, Class<T> clazz) {  
	        return (T) get(attribute);  
	    }  
	    
	    /**
	     * 获取线程用户
	     * @return
	     */
		public static User getUser( ) {  
	        return  get(ThreadVar.CURRENT_USER.name(),User.class);  
	    }  
		
		/**
	     * 设置线程用户
	     * @return
	     */
		public static void setUser(Object value) {  
	        set(ThreadVar.CURRENT_USER.name(),value);  
	    }  
		
		/**
	     * 获取线程消息
	     * @return
	     */
		public static String getMessage() {  
	        return  get(ThreadVar.MESSAGE.name(),String.class);  
	    }  
		
		/**
	     * 设置线程消息
	     * @return
	     */
		public static void setMessage(String value) {  
	        set(ThreadVar.MESSAGE.name(),value);  
	    }
		
	    /** 
	     * 设置制定属性名的值. 
	     * @param attribute   属性名称 
	     * @param value  属性值 
	     */  
	    @SuppressWarnings("unchecked")
		public static void set(String attribute, Object value) {  
	        Map<String,Object> map = (Map<String,Object>) SESSION_MAP.get();  
	        if (map == null) {  
	            map = new HashMap<String,Object>();  
	            SESSION_MAP.set(map);  
	        }  
	        map.put(attribute, value);  
	    }
	    
	    @SuppressWarnings("unchecked")
		public static boolean exists(String attribute) {  

	        Map<String,Object> map = (Map<String,Object>) SESSION_MAP.get();  
	        if (map != null) {
	        	return map.containsKey(attribute);  
			}
	        else{
	        	return false;
	        }
	    }

		public static String getClientIp() {
			return  get(ThreadVar.CLIENT_IP.name(),String.class);  
		} 
		
		/**
	     * 获取线程消息
	     * @return
	     */
		@SuppressWarnings("unchecked")
		public static List<String> getExcelErrorMessage() {  
	        return  get(ThreadVar.EXCEL_ERROR_MESSAGE.name(),List.class);  
	    }  
		
		/**
	     * 设置线程消息
	     * @return
	     */
		public static void setExcelErrorMessage(String msg) { 
			List<String> list = getExcelErrorMessage();
			if(list == null){
				list = new ArrayList<String>();
			}
			list.add(msg);
			set(ThreadVar.EXCEL_ERROR_MESSAGE.name(),list);  
	    }
}
