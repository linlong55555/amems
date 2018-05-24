package com.eray.pbs.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.eray.pbs.module.AccessModule;
import com.eray.pbs.module.CheckPbsLogModule;
import com.eray.pbs.module.CopyFtpFileModule;
import com.eray.pbs.module.EhrModule;
import com.eray.pbs.module.EmployeeModule;
import com.eray.pbs.module.MaterialShortageModule;
import com.eray.pbs.module.ProjectModule;
import com.eray.pbs.module.RevisionModule;
import com.eray.pbs.module.SpentHoursModule;
import com.eray.pbs.module.StatusModule;
import com.eray.pbs.module.SyncKqDataDoorModule;
import com.eray.pbs.module.TaskModule;
import com.eray.pbs.module.WorkModule;
import com.eray.pbs.module.WorkSpentBackSAPModule;
import com.eray.pbs.module.WorkSpentHourGrapModule;
import com.eray.pbs.util.Module;

/**
 * @author Hao.Z
 * @date 2014-03-17 15:28:36
 */
public class DbServer<T> implements ApplicationContextAware
{
    private static final Logger logger = LoggerFactory.getLogger(DbServer.class);
    private DbServer<T> instance;
    private String hostName;
    private ClassLoader classLoader;

    /**
     * All modules loaded by this server
     */
    private Map<Class<T>, Module<T>> modules = new LinkedHashMap<Class<T>, Module<T>>();

    private DbServer()
    {
    }

    public DbServer<T> getInstance()
    {
        if (instance == null)
        {
            instance = new DbServer<T>();
        }
        return instance;
    }

    public void start()
    {
        logger.info("DbServer Thread Start:{}", Thread.currentThread().getName());
        try
        {
            init();
            loadModules();
            initModules();
            startModules();
        } catch (Exception e)
        {
            shutdown();
        }
    }

    private void loadModules() throws Exception
    {
        Properties prop = new Properties() ;
        try { 
            InputStream inputFile = new FileInputStream("C:/config.properties"); 
            prop.load(inputFile); 
            inputFile.close(); 
        } catch (FileNotFoundException ex){ 
            System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在"); 
            ex.printStackTrace(); 
        } catch (IOException ex){ 
            System.out.println("装载文件--->失败!"); 
            ex.printStackTrace(); 
        }
        if(prop.getProperty("state").equals("1")){
        	loadModule(EhrModule.class.getName());
            loadModule(EmployeeModule.class.getName());
            loadModule(ProjectModule.class.getName());
            loadModule(RevisionModule.class.getName());
        }else if(prop.getProperty("state").equals("2")){
            loadModule(AccessModule.class.getName());
        }else if(prop.getProperty("state").equals("3")){
            loadModule(TaskModule.class.getName());
        }else if(prop.getProperty("state").equals("4")){
            loadModule(WorkModule.class.getName());
        }else if(prop.getProperty("state").equals("5")){
            loadModule(MaterialShortageModule.class.getName());
        }else if(prop.getProperty("state").equals("6")){
            loadModule(SpentHoursModule.class.getName());
        }else if(prop.getProperty("state").equals("7")){
        	loadModule(StatusModule.class.getName());
        }else if(prop.getProperty("state").equals("8")){
        	//loadModule(SearchRevisionModule.class.getName());
        	loadModule(CopyFtpFileModule.class.getName());
        }else if(prop.getProperty("state").equals("9")){
         	loadModule(AccessModule.class.getName());
        	loadModule(EhrModule.class.getName());
            loadModule(EmployeeModule.class.getName());
            loadModule(ProjectModule.class.getName());
            loadModule(RevisionModule.class.getName());
            loadModule(MaterialShortageModule.class.getName());
            loadModule(TaskModule.class.getName());
            loadModule(WorkModule.class.getName());
            
           // loadModule(SpentHoursModule.class.getName()); //不再从老barcode读取spent hour
            //loadModule(StatusModule.class.getName());---
            loadModule(CopyFtpFileModule.class.getName());
            loadModule(CheckPbsLogModule.class.getName());
            
            loadModule(WorkSpentHourGrapModule.class.getName()); //抓取spent hour回写至工单信息表 2017.01.10
            loadModule(SyncKqDataDoorModule.class.getName()); //同步考勤数据            
            loadModule(WorkSpentBackSAPModule.class.getName()); //工单spent回传 客户推翻此需求,2017.1.11,文件由pbs生成 
        	//loadModule(RevisonSpentTotalBackSAPModule.class.getName()); //根据工包总工时回传spent数据,该方法移植至pbs下2016.12.08
        }
    }

    @SuppressWarnings("unchecked")
    private void loadModule(String module) throws Exception
    {
        logger.debug("moduleSize:" + modules.size());
        try
        {
            Class<T> modClass = (Class<T>) classLoader.loadClass(module);
            Module<T> mod = (Module<T>) modClass.newInstance();
            this.modules.put(modClass, mod);
        } catch (ClassNotFoundException e)
        {
            logger.error("未找到加载类异常:{}", e.getMessage());
            throw new Exception();
        } catch (InstantiationException e)
        {
            logger.error("实例化异常:{}", e.getMessage());
            throw new Exception();
        } catch (IllegalAccessException e)
        {
            logger.error("非法入口异常:{}", e.getMessage());
            throw new Exception();
        }
    }

    private void initModules()
    {
        logger.debug("moduleSize:" + modules.size());
        for (Module<T> module : modules.values())
        {
            try
            {
                logger.info("initModule:" + module.getClass());
                module.init(this);
            } catch (Exception e)
            {
                logger.error("Failed to init {}...", module.getClass().getSimpleName());
                logger.error(e.getMessage());
                // Remove the failed initialized module
                this.modules.remove(module.getClass());
                module.stop();
                module.destroy();
            }
        }
    }

    private void startModules()
    {
        logger.debug("moduleSize:" + modules.size());
        for (Module<T> module : modules.values())
        {
            logger.info("startModule:{}", module.getClass());
            try
            {
                module.start();
            } catch (Exception e)
            {
                logger.error("Failed to start {}...", module.getClass().getSimpleName());
                logger.error(e.getMessage());
                module.stop();
                module.destroy();
            }
        }
    }

    private void init() throws Exception
    {
        try
        {
            hostName = InetAddress.getLocalHost().getHostName();
            logger.info("HostName:{}", hostName);
        } catch (UnknownHostException e)
        {
            logger.error("未知主机名异常:{}", e.getMessage());
            throw new Exception();
        }
        classLoader = Thread.currentThread().getContextClassLoader();
    }

    public void shutdown()
    {
        // If we don't have modules then the server has already been shutdown
        if (modules.isEmpty())
        {
            return;
        }
        // Get all modules and stop and destroy them
        for (Module<T> module : modules.values())
        {
            module.stop();
            module.destroy();
        }
        modules.clear();

        // hack to allow safe stopping
        logger.error("DbServer stopped");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
    }

}
