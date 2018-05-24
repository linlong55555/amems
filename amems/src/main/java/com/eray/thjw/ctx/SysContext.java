package com.eray.thjw.ctx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.eray.thjw.log.po.LogAccessRule;
import com.eray.thjw.system.po.Dict;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.system.po.UpdateLog;

public class SysContext {
	
	private static ApplicationContext applicationContext = null;
    //机构代码：机构内字典 map(字典ID，字典项列表)  	
	private static Map<String, Map<Integer, List<Map<String,String>>>> dprtDicMap = new HashMap<String, Map<Integer, List<Map<String,String>>>>();
	private static Map<String, Object> enumMap = new HashMap<String, Object>();
	private static Map<String, LogAccessRule> logConfigMap = new HashMap<String, LogAccessRule>();
	private static List<UpdateLog> updateLogMap = new ArrayList<UpdateLog>();
	
	/** 系统类型 AMEMS*/
	public static String APP_NAME = "AMEMS";
	
	/** 技术评估界面类型 */
	public static String ASSESSMENT_TYPE = "";
	/** 客户代码 */
	public static String CUSTOMERCODE = "";
	
	/** 计量校验计量等级 */
	public static String MEASUREMENT_LEVEL = "";
	
	/** 加密使用的盐值  */
	public static String SALT_VALUE = "";
	
	public static String FTP_URL;

	public static String FTP_PORT;

	public static String FTP_USERNAME;

	public static String FTP_PASSWORD;
	
	public static String FTP_DOWN_USERNAME;

	public static String FTP_DOWN_PASSWORD;

	public static Boolean FTP_ENABLED = false;
	
	/** 工作组是否必填  */
	public static boolean WORKGROUP_MUST = true;
	
	/** OSMS系统是否开启 */
	public static boolean OSMS_ENABLED = true;
	
	/** OSMS系统URL */
	public static String OSMS_URL = "";
	
	/** SCHEDULE系统URL */
	public static String SCHEDULE_URL = "";
	
	/** SMS系统URL */
	public static String SMS_URL = "";
	
	/** 客户端IP获取方式  CLIENT:客户端获取， SERVER:服务端获取 */
	public static String GETIP_STYLE = "SERVER";
	/** 是否生产模式 */
	public static Boolean PRODUCT_MODEL = Boolean.FALSE;
	/** 滑油监控取样数 */
	public static Integer QYS;
	/** 批处理执行数据量 */
	public static final int BATCH_PROCESS_SIZE = 200;
	
	/** 是否使用二维码，true:二维码，false:条形码 */
	public static boolean QRCODE_ENABLED = false;
	/** 待办工作是否发送邮件 */
	public static boolean EMAILSWITCH = false;
	/** 待办工作邮件发送人地址 */
	public static String SENDERADDRESS = "";
	/** 待办工作邮件发送人账户 */
	public static String SENDERACCOUNT = "";
	/** 待办工作邮件发送人密码 */
	public static String SENDERPASSWORD = "";
	/** 待办工作smtp邮箱地址 */		
	public static String SMTPADDRESS = "";
	
	/**用以区分当前系统所应用的客户,用以生成项目编号。HX,JS**/
	public static String PROJECT_CUSTNAME;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SysContext.applicationContext = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) SysContext.getApplicationContext().getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return SysContext.getApplicationContext().getBean(requiredType);
	}
	
	public static  List<Map<String,String>> getDicItemsByDicId(Integer dicId,String dprtcode) {
		if (getDprtDicMap().containsKey(dprtcode)) {
			Map<Integer, List<Map<String, String>>> dicMap = getDprtDicMap().get(dprtcode);
			if (dicMap!=null) {
				if (dicMap.containsKey(dicId)) { 
					return dicMap.get(dicId);
				}else{
					if("-1".equals(dprtcode)){
						return new ArrayList<Map<String,String>>();
					}
					return getDicItemsByDicId(dicId, "-1");
				} 
			}else{
				if("-1".equals(dprtcode)){
					return new ArrayList<Map<String,String>>();
				}
				return getDicItemsByDicId(dicId, "-1");
			}
		}else{
			if("-1".equals(dprtcode)){
				return new ArrayList<Map<String,String>>();
			}
			return getDicItemsByDicId(dicId, "-1");
		}
	}

	public static Map<String, Object> getEnumMap() {
		return enumMap;
	}

	public static void setEnumMap(Map<String, Object> enumMap) {
		SysContext.enumMap = enumMap;
	}

	/**
	 * 刷新一个字典
	 * @param dic
	 */
	public static void refreshDic(Dict dic) {
		 
		Map<Integer, List<Map<String,String>>> dicMap = null;	
		if (SysContext.getDprtDicMap().containsKey(dic.getDprtcode())) {
			dicMap = SysContext.getDprtDicMap().get(dic.getDprtcode());
		}
		else{
			dicMap = new HashMap<Integer, List<Map<String,String>>>();
		}
		SysContext.getDprtDicMap().put(dic.getDprtcode(),dicMap);
		
		List<Map<String,String>> itemlist = null;
		if (dicMap.containsKey(dic.getLxid())) {
			itemlist = dicMap.get(dic.getLxid());
		}
		else{
			itemlist = new ArrayList<Map<String,String>>();
		}
		dicMap.put(dic.getLxid(), itemlist);
		
		if (dic.getItemList() !=null && !dic.getItemList().isEmpty()) {
			List<DictItem> dicItems = dic.getItemList();
			
			for (DictItem dicItem : dicItems) {
				Map<String, String> mapItem = new HashMap<String, String>();
				mapItem.put("pk", dicItem.getId().toString());
				mapItem.put("desc", dicItem.getMc());
				mapItem.put("name", dicItem.getSz());
				mapItem.put("id", dicItem.getSz());
				itemlist.add(mapItem);
			}
		}
	}

	public static Map<String, LogAccessRule> getLogConfigMap() {
		return logConfigMap;
	}

	public static void setLogConfigMap(Map<String, LogAccessRule> logConfigMap) {
		SysContext.logConfigMap = logConfigMap;
	}
	
	public static void registLogConfig(LogAccessRule logConfig) {
		SysContext.logConfigMap.put(logConfig.getCode().toString(), logConfig);
	}
	public static void registUpdateLog(UpdateLog updateLog) {
		if(updateLog!=null){
			SysContext.updateLogMap.add(updateLog);
		}
	}
	
	public static List<UpdateLog> getUpdateLogList() {
		return SysContext.updateLogMap;
	}
	
	
	public static LogAccessRule getLogConfig(String code) {
		return SysContext.logConfigMap.get(code.toString());
	}
	
	public static String getFtpUrl(){
		return "ftp://".concat(FTP_USERNAME.trim()).concat(":").concat(FTP_PASSWORD.trim()).concat("@").concat(FTP_URL).concat(":").concat(FTP_PORT);
	}
	
	public static String getDownFtpUrl(){
		return "ftp://".concat(FTP_DOWN_USERNAME.trim()).concat(":").concat(FTP_DOWN_PASSWORD.trim()).concat("@").concat(FTP_URL).concat(":").concat(FTP_PORT);
	}

	public static Map<String, Map<Integer, List<Map<String,String>>>> getDprtDicMap() {
		return dprtDicMap;
	}

	public static void setDprtDicMap(Map<String, Map<Integer, List<Map<String,String>>>> dprtDicMap) {
		SysContext.dprtDicMap = dprtDicMap;
	}

}
