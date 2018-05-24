package com.eray.thjw.flyway;

import java.util.Properties;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.eray.rest.common.SysConfig;
import com.eray.rest.common.SysContextFactory;

/**
 * @Description 执行数据库版本升级
 * @CreateTime 2018年4月14日 上午11:26:07
 * @CreateBy hchu
 * @return
 */
public class FlywayMigration implements ApplicationContextAware, InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);

	public void afterPropertiesSet() throws Exception {
		migration();
	}

	private static void migration() {
		// 加载配置文件信息
		Properties prop = new Properties();
		try {
			prop.load(SysConfig.class.getClassLoader().getResourceAsStream("conf/jdbc.properties"));
			// Create the Flyway instance
			Flyway flyway = new Flyway();

			// Point it to the database
			flyway.setDataSource(prop.getProperty("jdbc_url"), prop.getProperty("jdbc_username"),
					prop.getProperty("jdbc_password"));

			flyway.setBaselineOnMigrate(true);
			
			// Start the migration
			flyway.migrate();
		} catch (Exception e) {
			logger.error("执行数据库版本升级失败，停止启动服务", e);
//			System.exit(-1);
		}
	}

	public void destroy() throws Exception {
		SysContextFactory.setApplicationContext(null);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SysContextFactory.setApplicationContext(applicationContext);
	}

}
