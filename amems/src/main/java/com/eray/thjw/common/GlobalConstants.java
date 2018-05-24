package com.eray.thjw.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统全局常量
 * @author xu.yong
 *
 */
public class GlobalConstants {
	
	/** 编码：退出系统路径 */
	public static final String LOGIN_OUT_KEY = "LOGIN_OUT";
	/** 编码：session失效路径 */
	public static final String SESSION_LOSE_KEY = "SESSION_LOSE";
	/** 编码：登陆失败路径 */
	public static final String LOGIN_ERROR_KEY = "LOGIN_ERROR";
	/** 编码：附件根目录 */
	public static final String ATT_ROOT_PATH_KEY = "ATT_ROOT_PATH";
	/** 编码：系统默认密码 */
	public static final String DEFAULT_PASSWORD_KEY = "DEFAULT_PASSWORD";
	/** 编码：MAC地址限制 */
	public static final String MAC_LIMIT_KEY = "MAC_LIMIT";
	/** 编码：超级机构代码 */
	public static final String SUPER_AGENCY_KEY = "SUPER_AGENCY";
	/** 编码：每页显示记录数 */
	public static final String PAGING_NUM_KEY = "PAGING_NUM";
	/** 编码：机构类型 */
	public static final String AGENCY_TYPE_KEY = "AGENCY_TYPE";
	
	public static String OIL_DENSITY_KEY = "0.8";
	
	/** 所有值存储Map */
	public static Map<String, Object> VALUE_MAP = new HashMap<String, Object>();
	
	/**
	 * 根据key获取string类型的设置
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		try{
			String str = (String)VALUE_MAP.get(key);
			if(str == null){
				return "";
			}
			return str;
		}catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 根据key获取int类型的设置
	 * @param key
	 * @return
	 */
	public static int getInt(String key){
		try{
			String str = (String)VALUE_MAP.get(key);
			if(str == null){
				return 0;
			}
			return Integer.parseInt(str);
		}catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 根据key获取List类型的设置
	 * @param key
	 * @return
	 */
	public static List<String> getList(String key){
		try{
			if(VALUE_MAP.get(key) == null){
				return new ArrayList<String>();
			}
			return (List<String>)VALUE_MAP.get(key);
		}catch (Exception e) {
			return new ArrayList<String>();
		}
	}
	
	/**
	 * 根据key获取boolean类型的设置
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		try{
			return "TRUE".equalsIgnoreCase((String)VALUE_MAP.get(key));
		}catch (Exception e) {
			return false;
		}
	}
	
}
