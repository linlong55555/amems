package com.eray.framework.common;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.eray.framework.saibong.SNGeneratorFactory;


/** 
 * @Description 系统配置文件
 * @CreateTime 2018年1月2日 下午4:26:58
 * @CreateBy 徐勇	
 */
public class SysConfig implements ApplicationContextAware, InitializingBean, DisposableBean {
	
	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);
	
	//是否开启Redis
	public static boolean REDIS_ENABLED = false;
	//Redis ip
	public static String REDIS_IP = "";
	//Redis port
	public static int REDIS_PORT = 0;
	//Redis 验证信息
	public static String REDIS_AUTH = "";
	
	public void afterPropertiesSet() throws Exception {
		initConfigProperties();
		initSaibongRules();
	}
	
	private static void initConfigProperties() {
		// 加载配置文件信息
		Properties prop = new Properties();
		try {
			prop.load(SysConfig.class.getClassLoader().getResourceAsStream("conf/framework-config.properties"));
			String strRedisEnabled = prop.getProperty("REDIS.ENABLED");
			if("yes".equalsIgnoreCase(strRedisEnabled)){
				SysConfig.REDIS_ENABLED = true;
			}
			SysConfig.REDIS_IP = prop.getProperty("REDIS.IP");
			String strRedisPort = prop.getProperty("REDIS.PORT");
			SysConfig.REDIS_PORT = Integer.parseInt(strRedisPort);
			SysConfig.REDIS_AUTH = prop.getProperty("REDIS.AUTH");
		} catch (Exception e) {
			logger.error("加载config.properties失败，停止启动服务", e);
			System.exit(-1);
		}
	}
	
	private void initSaibongRules(){
		try {
			SNGeneratorFactory.loadRules();
		} catch (Exception e) {
			logger.error("加载采番规则失败，停止启动服务", e);
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
