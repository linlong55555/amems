package com.eray.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eray.pbs.thread.DbServerThread;
import com.eray.pbs.util.LogbackConfigurer;
import com.eray.util.framework.ThreadPoolManager;

public class Run
{
    private static final Logger logger = LoggerFactory.getLogger(Run.class);
    /**
     * spring的bean工厂
     */
    public static void main(String[] args) throws Exception {

        logger.info("Server is initializing...");

        logger.info("Init logback.");
        // 加载日志配置文件
        LogbackConfigurer.initLogging("classpath:" + "logback.xml");

        logger.info("Init spring application context.");
        // 加载spring的bean工厂
        new ClassPathXmlApplicationContext("applicationContext.xml");

        logger.info("Init thread pool.");
        ThreadPoolManager.initThreadPool();
        
        logger.info("Server is initializing...");
        ThreadPoolManager.getThreadPoolExecutor().execute(new DbServerThread());
    }
}
