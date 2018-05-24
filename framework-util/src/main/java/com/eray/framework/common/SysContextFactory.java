package com.eray.framework.common;

import org.springframework.context.ApplicationContext;

/**
 * @Description 
 * @CreateTime 2018-1-10 下午12:03:24
 * @CreateBy 刘兵
 */
public class SysContextFactory {
	
	private static ApplicationContext applicationContext = null;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SysContextFactory.applicationContext = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) SysContextFactory.getApplicationContext().getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return SysContextFactory.getApplicationContext().getBean(requiredType);
	}

}
