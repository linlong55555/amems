package com.eray.pbs.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyFtpFileWorkThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(CopyFtpFileWorkThread.class);
	
	private boolean status;
	private FTPClient ftpClient;
	private int reply;
	private FTPFile[] fs;
	private File tmpFile;
	private File localFile;
	private OutputStream is;

	private Long sleepTime = 300000l;
	private Long waitTime;
	
	public void run(){
		Properties prop = new Properties() ;
        try { 
            InputStream inputFile = new FileInputStream("C:/config.properties"); 
            prop.load(inputFile); 
            inputFile.close(); 
        } catch (Exception ex){ 
            System.out.println("读取属性文件--->失败！"); 
        }
		String pbsDataPath = prop.getProperty("pbsDataPath");
		System.out.println("CopyFtpFileWorkThread start");
		while (true)
        {
			waitTime = System.nanoTime();
			try{
				status = connectFtp("/zinterface/barcode/out/wkc/processed/","10.7.0.221",21,"ftppb","Bsas@014");
			}catch(Exception e){
				logger.error("FTP 连接出错");
			}
			if (status)
			{
				try
				{
					fs = ftpClient.listFiles();
				} catch (IOException e)
				{
					logger.error("FTP 目录出错");
				}
				if(fs.length>0){
					for (FTPFile file : fs) {
						if(file.isDirectory()){
							continue;
						}
						if(file.getName().startsWith("O_")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/barcode/out/wkc/processed/"+file.getName(), "/zinterface/barcode/out/wkc/processed/PBS_processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/work/"+file.getName());
	                            tmpFile.renameTo(localFile);
	                            tmpFile.delete();
                            } catch (FileNotFoundException e)
                            {
                            	logger.error("FTP 文件出错"+file.getName());
                            } catch (IOException e)
                            {
                            	logger.error("FTP 文件下载出错"+file.getName());
                            }
						}
					}
				}
			}
			try
            {
	            ftpClient.logout();
	            ftpClient.disconnect();
            } catch (IOException e1)
            {
            	logger.error("FTP 退出异常");
            }
			waitTime = System.nanoTime() - waitTime;
			try
			{
				Thread.sleep(sleepTime);
			} catch (InterruptedException e)
			{
				logger.error("CopyFtpFileWork Thread is interrupted!");
			}
        }
	}
	
	private boolean connectFtp(String path, String addr, int port, String username, String password) throws Exception
	{
		ftpClient = new FTPClient();
		ftpClient.setControlKeepAliveTimeout(300);
		ftpClient.connect(addr, port);
		ftpClient.login(username, password);
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply))
		{
			ftpClient.disconnect();
			return false;
		}
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.changeWorkingDirectory(path);
		return true;
	}
}
