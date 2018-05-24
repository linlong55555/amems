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

public class CopyFtpFileThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(CopyFtpFileThread.class);

	private boolean status;
	private FTPClient ftpClient;
	private int reply;
	private FTPFile[] fs;
	private File tmpFile;
	private File localFile;
	private OutputStream is;

	private Long sleepTime = 3600000l;
	private Long waitTime;

	public void run()
	{
		Properties prop = new Properties() ;
        try { 
            InputStream inputFile = new FileInputStream("C:/config.properties"); 
            prop.load(inputFile); 
            inputFile.close(); 
        } catch (Exception ex){ 
            System.out.println("读取属性文件--->失败！"); 
        }
		String pbsDataPath = prop.getProperty("pbsDataPath");
		System.out.println("CopyFtpFileThread start");
		while (true)
		{
			waitTime = System.nanoTime();
			try
			{
				status = connectFtp("/zinterface/Planning_Board/out/", "10.7.0.221", 21, "ftppb", "Bsas@014");
			} catch (Exception e)
			{
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
						if(file.getName().startsWith("ZEHR")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/Planning_Board/out/"+file.getName(), "/zinterface/Planning_Board/out/processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/ehr/"+file.getName());
	                            tmpFile.renameTo(localFile);
	                            tmpFile.delete();
                            } catch (FileNotFoundException e)
                            {
                            	logger.error("FTP 文件出错"+file.getName());
                            } catch (IOException e)
                            {
                            	logger.error("FTP 文件下载出错"+file.getName());
                            }
						}else if(file.getName().startsWith("ZPID")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/Planning_Board/out/"+file.getName(), "/zinterface/Planning_Board/out/processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/employee/"+file.getName());
	                            tmpFile.renameTo(localFile);
	                            tmpFile.delete();
                            } catch (FileNotFoundException e)
                            {
                            	logger.error("FTP 文件出错"+file.getName());
                            } catch (IOException e)
                            {
                            	logger.error("FTP 文件下载出错"+file.getName());
                            }
						}else if(file.getName().startsWith("ZMTS")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/Planning_Board/out/"+file.getName(), "/zinterface/Planning_Board/out/processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/material/"+file.getName());
	                            tmpFile.renameTo(localFile);
	                            tmpFile.delete();
                            } catch (FileNotFoundException e)
                            {
                            	logger.error("FTP 文件出错"+file.getName());
                            } catch (IOException e)
                            {
                            	logger.error("FTP 文件下载出错"+file.getName());
                            }
						}else if(file.getName().startsWith("ZPRJ")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/Planning_Board/out/"+file.getName(), "/zinterface/Planning_Board/out/processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/project/"+file.getName());
	                            tmpFile.renameTo(localFile);
	                            tmpFile.delete();
                            } catch (FileNotFoundException e)
                            {
                            	logger.error("FTP 文件出错"+file.getName());
                            } catch (IOException e)
                            {
                            	logger.error("FTP 文件下载出错"+file.getName());
                            }
						}else if(file.getName().startsWith("ZREV")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/Planning_Board/out/"+file.getName(), "/zinterface/Planning_Board/out/processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/revision/"+file.getName());
	                            tmpFile.renameTo(localFile);
	                            tmpFile.delete();
                            } catch (FileNotFoundException e)
                            {
                            	logger.error("FTP 文件出错"+file.getName());
                            } catch (IOException e)
                            {
                            	logger.error("FTP 文件下载出错"+file.getName());
                            }
						}else if(file.getName().startsWith("ZTLT")){
							tmpFile = new File(pbsDataPath+"/tmp/" + file.getName());
							try
                            {
	                            is = new FileOutputStream(tmpFile);
	                            ftpClient.retrieveFile(file.getName(), is);
	                            is.close();
	                            ftpClient.rename("/zinterface/Planning_Board/out/"+file.getName(), "/zinterface/Planning_Board/out/processed/"+file.getName());
	                            localFile=new File(pbsDataPath+"/tasklist/"+file.getName());
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
				logger.error("CopyFtpFile Thread is interrupted!");
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
