package com.eray.pbs.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.po.Work;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.WorkService;
import com.eray.pbs.util.idoc.IDoc;
import com.eray.pbs.util.idoc.IDocHelper;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadWorkThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(ReadWorkThread.class);
	private String workURL = "/work/";
	private String workProceedURL = "/work/proceed/";
	private String workErrorURL = "/work/error/";
	private File file;
	private Long sleepTime = 600000l;  //毫秒 = 10分钟 = 10*60*1000
	private String error = "";
	private Long waitTime;
	private FileImptService fileImptService;
	private Map<String, Object> criteria;
	private List<FileImpt> fileImptList;
	private WorkService workService;
	private ImptFailedService imptFailedService;
	private ImptFailed imptFailed;
	private FileImpt fileImpt;
	private Timestamp lastModified;
	private Work work;

	private int success = 0;
	private int failed = 0;
	
	private boolean status;
	private FTPClient ftpClient;
	private int reply;
	
	private ImptErrorService imptErrorService;
	private String pbsDataPath;
	
    private Date handleDate;
	private String fileName; //文件名=处理时间_+文件名

	@Override
	public void run()
	{
		handleDate = new Date();
		
		Properties prop = new Properties() ;
        try { 
            InputStream inputFile = new FileInputStream("C:/config.properties"); 
            prop.load(inputFile); 
            inputFile.close(); 
        } catch (Exception ex){ 
            System.out.println("读取属性文件--->失败！"); 
        }
		pbsDataPath = prop.getProperty("pbsDataPath");
		file = new File(pbsDataPath+workProceedURL);
		if (!file.exists())
		{
			file.mkdir();
		}
		file = new File(pbsDataPath+workErrorURL);
		if (!file.exists())
		{
			file.mkdir();
		}
		workService = SpringContextHolder.getBean("workService");
		fileImptService = SpringContextHolder.getBean("fileImptService");
        imptFailedService = SpringContextHolder.getBean("imptFailedService");
        imptErrorService = SpringContextHolder.getBean("imptErrorService");
		while (true)
		{
			waitTime = new Date().getTime();
			file = new File(pbsDataPath+workURL);

			if (file.isDirectory())
			{
				for (File workFile : file.listFiles())
				{
					if (workFile.isDirectory())
					{
						continue;
					}
					importFile(workFile);
				}
				waitTime = new Date().getTime() - waitTime;
				if (sleepTime > waitTime)
				{
					try
					{
						Thread.sleep(sleepTime - waitTime);
					} catch (InterruptedException e)
					{
						logger.error("Work Thread is interrupted!");
					}
				}
			} else
			{
				error = "Work Directory is Wrong!";
			}
			if (!error.equals(""))
			{
				logger.error(error);
				error = "";
				try
				{
					Thread.sleep(sleepTime);
				} catch (InterruptedException e)
				{
					logger.error("Thread is interrupted!");
				}
			}
		}
	}

	private void importFile(File workFile)
	{
		fileName = new SimpleDateFormat("yyyyMMddHHmmss_").format(handleDate) + workFile.getName();
		
		// check Exist
		if (fileImptService == null)
		{
			logger.error("fileImptService is null");
			return;
		}
		criteria = new HashMap<String, Object>();
		criteria.put("EQ_fileName", workFile.getName());
		criteria.put("EQ_fileSize", workFile.length());
		lastModified = new Timestamp(workFile.lastModified());
		criteria.put("EQ_fileDate", lastModified);
		fileImptList = fileImptService.findAll(criteria);
		if (fileImptList == null || fileImptList.size() < 1)
		{
			// delete All fileImpt
			// delete All imptFailed
			if (workService == null)
			{
				logger.error("workService is null");
				return;
			}
			if (imptFailedService == null)
			{
				logger.error("imptFailedService is null");
				return;
			}
			try
			{
				success = 0;
				failed = 0;
				IDoc iDoc = IDocHelper.parseTxt(workFile);
				if (iDoc == null)
				{
					failed++;
					imptFailed = new ImptFailed();
					imptFailed.setFileName(fileName);
					imptFailed.setLine(null);
					imptFailed.setFileType(4);
					imptFailed.setErrorInfo("Insert failed");
					imptFailed.setDealflag(0);
					imptFailedService.save(imptFailed);
				}
				work = new Work();
				work.setImptFilename(fileName);
				try
				{
					if (workService.saveIDocWork(work, iDoc))
					{
						success++;
					}else{
						failed++;
					}
				} catch (Exception e)
				{
					failed++;
				}
				
				if(failed>0){
					imptFailed = new ImptFailed();
					imptFailed.setFileName(fileName);
					imptFailed.setLine(null);
					imptFailed.setFileType(4);
					imptFailed.setErrorInfo("Insert failed");
					imptFailed.setDealflag(0);
					imptFailedService.save(imptFailed);
					logger.error(fileName + ":Insert Error");
					saveImptError(fileName,4,"Insert Error");
					FileUtil.moveFile(pbsDataPath+workErrorURL + fileName, workFile);
					moveErrorOnFTP(fileName);
					return;
				}
				
				fileImpt = new FileImpt();
				fileImpt.setFileName(workFile.getName());
				fileImpt.setFileSize(workFile.length());
				fileImpt.setFileDate(lastModified);
				fileImpt.setFileType(4);
				fileImpt.setIsImpt(1);
				fileImpt.setImptTime(new Timestamp(handleDate.getTime()));
				fileImpt.setSuccess(success);
				fileImpt.setFailed(failed);
				fileImptService.save(fileImpt);
				FileUtil.moveFile(pbsDataPath+workProceedURL + fileName, workFile);
			} catch (IOException e)
			{
				logger.error(fileName + ":I/O Exception");
				saveImptError(fileName,4,"i/o error!");
				FileUtil.moveFile(pbsDataPath+workErrorURL + fileName, workFile);
				moveErrorOnFTP(fileName);
				return;
			}
		} else
		{
			FileUtil.moveFile(pbsDataPath+workProceedURL + fileName, workFile);
		}
	}
	
	private void moveErrorOnFTP(String name)
    {
		try{
			status = connectFtp("/zinterface/barcode/out/wkc/processed/","10.7.0.221",21,"ftppb","Bsas@014");
		}catch(Exception e){
			logger.error("FTP 连接出错");
		}
		if (status)
		{
			try
            {
	            ftpClient.rename("/zinterface/barcode/out/wkc/processed/PBS_processed/"+name, "/zinterface/barcode/out/wkc/processed/PBS_error/"+name);
            } catch (IOException e)
            {
            	logger.error("文件移动失败");
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
    }

	private void saveImptError(String fileName, int fileType, String error)
    {
		ImptError imptError=new ImptError();
        imptError.setFileName(fileName);
        imptError.setFileType(fileType);
        imptError.setImptTime(new Timestamp(handleDate.getTime()));
        imptError.setErrorInfo(error);
        imptErrorService.save(imptError);
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
