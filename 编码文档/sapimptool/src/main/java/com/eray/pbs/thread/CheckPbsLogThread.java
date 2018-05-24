package com.eray.pbs.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.util.JavaMail;


public class CheckPbsLogThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(CheckPbsLogThread.class);
	private JavaMail javaMail;
	@Override
	public void run() {
		Properties prop = new Properties() ;
        try { 
            InputStream inputFile = new FileInputStream("C:/config.properties"); 
            prop.load(inputFile); 
            inputFile.close(); 
        } catch (Exception ex){ 
            System.out.println("读取属性文件--->失败！"); 
        }
        String pbsLogPath = prop.getProperty("pbsLogPath");
        File file = new File(pbsLogPath);
        long lastDate=0l;
        long tmpDate;
        if(file.exists() && file.isDirectory()){
        	for(File logFile:file.listFiles()){
        		tmpDate=logFile.lastModified();
        		if(tmpDate>lastDate){
        			lastDate=tmpDate;
        		}
        	}
        	long now=new Date().getTime();
        	long distance=now-lastDate;
        	if(distance>1000*60*60*24){
        		if(distance>1000*60*60*24*2){
        			if(distance>1000*60*60*24*5){
        				logger.error("PBS日志已有5天没有记录");
        				//发邮件
        				String sendMail = prop.getProperty("sendMail");
        				if ("true".equals(sendMail)){
        					sendAlartMail("PBS日志已有5天没有记录，请检查！");
        				}
        			}else{
        				logger.error("PBS日志已有2天没有记录");
        				//发邮件
        				String sendMail = prop.getProperty("sendMail");
        				if ("true".equals(sendMail)){
        					sendAlartMail("PBS日志已有2天没有记录，请检查！");
        				}
        			}
        		}else{
        			logger.error("PBS日志已有1天没有记录");
        		}
        	}
        }
        try
		{
			Thread.sleep(1000*60*60*24);
		} catch (InterruptedException e)
		{
			logger.error("CheckPbsLog Thread is interrupted!");
		}
	}

	private void sendAlartMail(String message) {
		javaMail = new JavaMail(true);
		javaMail.doSendHtmlEmail("PBS日志线程报警",message,
		        "bsas.it@boeingshanghai.com", "pmsadmin@boeingshanghai.com");
	}

}
