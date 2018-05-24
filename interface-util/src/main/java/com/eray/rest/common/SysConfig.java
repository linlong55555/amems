package com.eray.rest.common;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.eray.rest.enu.EnableEnum;


/**
 * @Description 系统配置文件
 * @CreateTime 2018年2月2日 下午1:12:26
 * @CreateBy 韩武
 */
public class SysConfig implements ApplicationContextAware, InitializingBean, DisposableBean {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);
	
	//是否开启海航运控接口
	public static boolean HHYK_ENABLED = false;
	//海航运控 ip
	public static String HHYK_IP = "";
	//海航运控 port
	public static int HHYK_PORT = 0;
	
	/** 海航运控接口 */
	public final static String REST_HHYK = "HHYK";
	
	/** 故障保留单接口路径 */
	public final static String HHYK_DD_PATH = "/bjc.intg.esd/e-ray/dd_sync";
	
	/** 故障保留单接口路径 */
	public final static String HHYK_PP_PATH = "/bjc.intg.esd/e-ray/planeparking_sync";
	
	public void afterPropertiesSet() throws Exception {
		initConfigProperties();
	}
	
	/**
	 * @Description 获取海航运控url
	 * @CreateTime 2018年2月2日 下午1:40:07
	 * @CreateBy 韩武
	 * @return
	 */
	public static String getHhykUrl(){
		return HHYK_IP + ":" + HHYK_PORT;
	}
	
	/**
	 * @Description 海航运控接口是否启用
	 * @CreateTime 2018年2月5日 上午9:59:53
	 * @CreateBy 韩武
	 * @return
	 */
	public static boolean isHhykEnabled(){
		return HHYK_ENABLED;
	}
	
	private static void initConfigProperties() {
		// 加载配置文件信息
		Properties prop = new Properties();
		try {
			prop.load(SysConfig.class.getClassLoader().getResourceAsStream("conf/interface-config.properties"));
			String strHhykEnabled = prop.getProperty("HHYK.ENABLED");
			if(EnableEnum.ENABLED.getId().equalsIgnoreCase(strHhykEnabled)){
				SysConfig.HHYK_ENABLED = true;
			}
			SysConfig.HHYK_IP = prop.getProperty("HHYK.IP");
			String strHhykPort = prop.getProperty("HHYK.PORT");
			SysConfig.HHYK_PORT = Integer.parseInt(strHhykPort);
		} catch (Exception e) {
			logger.error("加载interface-config.properties失败，停止启动服务", e);
			System.exit(-1);
		}
	}
	
	public void destroy() throws Exception {
		SysContextFactory.setApplicationContext(null);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SysContextFactory.setApplicationContext(applicationContext);
	}

}
