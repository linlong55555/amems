package com.eray.pbs.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.po.SpentHours;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.SpentHoursService;
import com.eray.util.Global;
import com.eray.util.framework.SpringContextHolder;

public class ReadSpentHoursThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(ReadSpentHoursThread.class);
	private String spentHoursURL = "smb://10.7.0.230/Exchange4PB/wtc/";
	
	private String spentHoursProceedURL = "smb://10.7.0.230/Exchange4PB/wtc/processed/";
	private String spentHoursErrorURL = "smb://10.7.0.230/Exchange4PB/wtc/error/";
	private String tab = "\t";
	private SmbFile file;
	private Long sleepTime = 120000l;
	private String error = "";
	private Long waitTime;
	private FileImptService fileImptService;
	private Map<String, Object> criteria;
	private List<FileImpt> fileImptList;
	private BufferedReader reader;
	private String line;
	private SpentHours spentHours;
	private SpentHours tempSpentHours;
	private SpentHoursService spentHoursService;
	private ImptFailedService imptFailedService;
	private ImptFailed imptFailed;
	private String[] para;
	private FileImpt fileImpt;

	private int success = 0;
	private int failed = 0;
	
	private ImptErrorService imptErrorService;

	@Override
	public void run()
	{
		try
		{
			file = new SmbFile(spentHoursProceedURL);
			if (!file.exists())
			{
				file.mkdir();
			}
			file = new SmbFile(spentHoursErrorURL);
			if (!file.exists())
			{
				file.mkdir();
			}
		} catch (MalformedURLException | SmbException e1)
		{
			logger.error("SpentHours Directory Error!");
		}
		fileImptService = SpringContextHolder.getBean("fileImptService");
		spentHoursService = SpringContextHolder.getBean("spentHoursService");
		imptFailedService = SpringContextHolder.getBean("imptFailedService");
		imptErrorService = SpringContextHolder.getBean("imptErrorService");
		while (true)
		{
			waitTime = System.currentTimeMillis();
			try
			{
				file = new SmbFile(spentHoursURL);

				if (file.isDirectory())
				{
					for (SmbFile spentHoursFile : file.listFiles())
					{
						if (spentHoursFile.isDirectory())
						{
							continue;
						}
						importFile(spentHoursFile);
					}
					waitTime = System.currentTimeMillis() - waitTime;
					if (sleepTime > waitTime)
					{
						Thread.sleep(sleepTime - waitTime);
					}
				} else
				{
					error = "SpentHours Directory is Wrong!";
				}
			} catch (MalformedURLException e)
			{
				error = "SpentHoursURL is not readable!";
			} catch (SmbException e)
			{
				error = "Method Exception:SpentHours.isDirectory()!";
			} catch (InterruptedException e)
			{
				error = "SpentHours Thread is interrupted!";
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
					logger.error("SpentHours Thread is interrupted!");
				}
			}
		}
	}

	private void importFile(SmbFile spentHoursFile)
	{
		// check Exist
		criteria = new HashMap<String, Object>();
		criteria.put("EQ_fileName", spentHoursFile.getName());
		criteria.put("EQ_fileSize", spentHoursFile.getContentLength());
		criteria.put("EQ_fileDate", new Date(spentHoursFile.getLastModified()));
		fileImptList = fileImptService.findAll(criteria);
		if (fileImptList == null || fileImptList.size() < 1)
		{
			// delete All fileImpt
			// delete All imptFailed
			try
			{
				reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(spentHoursFile), "gbk"));
				reader.readLine();
				success = 0;
				failed = 0;
				while ((line = reader.readLine()) != null)
				{
					if (line.trim().length() < 1)
					{
						continue;
					}
					para = line.split(tab);
					if (para.length < 11)
					{
						failed++;
						imptFailed = new ImptFailed();
						imptFailed.setFileName(spentHoursFile.getName());
						imptFailed.setLine(line);
						imptFailed.setFileType(7);
						imptFailed.setErrorInfo("not enough columns");
						imptFailed.setDealflag(0);
						imptFailedService.save(imptFailed);
						continue;
					}
					if("".equals(para[0])||"".equals(para[1])||"".equals(para[3])){
						failed++;
						imptFailed = new ImptFailed();
						imptFailed.setFileName(spentHoursFile.getName());
						imptFailed.setLine(line);
						imptFailed.setFileType(7);
						imptFailed.setErrorInfo("spentHours key is empty");
						imptFailed.setDealflag(0);
						imptFailedService.save(imptFailed);
						continue;
					}
					spentHours = new SpentHours();
					spentHours.setWorkOrder(Global.removeZeroBefore(para[0]));
					spentHours.setOperation(para[1]);
					spentHours.setWorkCenter(para[3]);
					spentHours.setLine(line);
					spentHours.setImptFilename(spentHoursFile.getName());
					tempSpentHours=spentHoursService.findLast(spentHours.getWorkOrder(),spentHours.getOperation(),spentHours.getWorkCenter());
					if(tempSpentHours!=null && line.equals(tempSpentHours.getLine())){
						spentHours=tempSpentHours;
					}else{
						spentHours = spentHoursService.save(spentHours, para);
					}
					if (spentHours.getId() == null)
					{
						failed++;
						imptFailed = new ImptFailed();
						imptFailed.setFileName(spentHoursFile.getName());
						imptFailed.setLine(line);
						imptFailed.setFileType(7);
						imptFailed.setErrorInfo("insert failed");
						imptFailed.setDealflag(0);
						imptFailedService.save(imptFailed);
					} else
					{
						success++;
					}
				}
				fileImpt = new FileImpt();
				fileImpt.setFileName(spentHoursFile.getName());
				fileImpt.setFileSize(spentHoursFile.getContentLengthLong());
				fileImpt.setFileDate(new Timestamp(spentHoursFile.getLastModified()));
				fileImpt.setFileType(7);
				fileImpt.setIsImpt(1);
				fileImpt.setImptTime(new Timestamp(new Date().getTime()));
				fileImpt.setSuccess(success);
				fileImpt.setFailed(failed);
				fileImptService.save(fileImpt);
				moveToProceed(spentHoursFile);
			} catch (IOException e)
			{
				logger.error(spentHoursFile.getName() + ":I/O Exception");
				saveImptError(spentHoursFile.getName(),7,"i/o error!");
				moveToError(spentHoursFile);
			}
		} else
		{
			moveToProceed(spentHoursFile);
		}
	}

	private void moveToError(SmbFile spentHoursFile)
	{
		try
		{
			spentHoursFile.copyTo(new SmbFile(spentHoursErrorURL + spentHoursFile.getName()));
			spentHoursFile.delete();
		} catch (SmbException | MalformedURLException e)
		{
			logger.error(spentHoursFile.getName() + ":Failed to move file to error Directory");
		}
	}

	private void moveToProceed(SmbFile spentHoursFile)
	{
		try
		{
			spentHoursFile.copyTo(new SmbFile(spentHoursProceedURL + spentHoursFile.getName()));
			spentHoursFile.delete();
		} catch (SmbException | MalformedURLException e)
		{
			logger.error(spentHoursFile.getName() + ":Failed to move file to proceed Directory");
		}
	}
	
	private void saveImptError(String fileName, int fileType, String error)
    {
		ImptError imptError=new ImptError();
        imptError.setFileName(fileName);
        imptError.setFileType(fileType);
        imptError.setImptTime(new Timestamp(System.currentTimeMillis()));
        imptError.setErrorInfo(error);
        imptErrorService.save(imptError);
    }
}
