package com.eray.pbs.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.po.MaterialShortage;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.MaterialShortageService;
import com.eray.pbs.service.WorkService;
import com.eray.util.Global;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadMaterialShortageThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(ReadMaterialShortageThread.class);

	private String materialShortageURL = "/material/";
	private String materialShortageProceedURL = "/material/proceed/";
	private String materialShortageErrorURL = "/material/error/";
	private String tab = "\t";
	private File file;
	private Long sleepTime = 1200000l;
	private String error = "";
	private Long waitTime;
	private FileImptService fileImptService;
	private Map<String, Object> criteria;
	private List<FileImpt> fileImptList;
	private BufferedReader reader;
	private String line;
	private MaterialShortage materialShortage;
	//private WorkPbs workPbs;
	private MaterialShortage tempMaterialShortage;
	private MaterialShortageService materialShortageService;
	private ImptFailedService imptFailedService;
	private ImptFailed imptFailed;
	private String[] para;
	private FileImpt fileImpt;
	private Timestamp lastModified;

	private int success = 0;
	private int failed = 0;

	private ImptErrorService imptErrorService;
	private WorkService workService;

	private String pbsDataPath;

	@Override
	public void run()
	{
		Properties prop = new Properties();
		try
		{
			InputStream inputFile = new FileInputStream("C:/config.properties");
			prop.load(inputFile);
			inputFile.close();
		} catch (Exception ex)
		{
			System.out.println("读取属性文件--->失败！");
		}
		pbsDataPath = prop.getProperty("pbsDataPath");
		file = new File(pbsDataPath + materialShortageProceedURL);
		if (!file.exists())
		{
			file.mkdir();
		}
		file = new File(pbsDataPath + materialShortageErrorURL);
		if (!file.exists())
		{
			file.mkdir();
		}
		fileImptService = SpringContextHolder.getBean("fileImptService");
		materialShortageService = SpringContextHolder.getBean("materialShortageService");
		imptFailedService = SpringContextHolder.getBean("imptFailedService");
		imptErrorService = SpringContextHolder.getBean("imptErrorService");
		workService = SpringContextHolder.getBean("workService");
		while (true)
		{
			waitTime = System.nanoTime();
			file = new File(pbsDataPath + materialShortageURL);

			if (file.isDirectory())
			{
				for (File materialShortageFile : file.listFiles())
				{
					if (materialShortageFile.isDirectory())
					{
						continue;
					}
					importFile(materialShortageFile);
				}
				waitTime = System.nanoTime() - waitTime;
				if (sleepTime > waitTime)
				{
					try
					{
						Thread.sleep(sleepTime - waitTime);
					} catch (InterruptedException e)
					{
						logger.error("MaterialShortage Thread is interrupted!");
					}
				}
			} else
			{
				error = "MaterialShortage Directory is Wrong!";
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
					logger.error("MaterialShortage Thread is interrupted!");
				}
			}
		}
	}

	private void importFile(File materialShortageFile)
	{
		logger.info("materialShortageFile {} begin parse.",materialShortageFile.getName());
		// check Exist
		if (fileImptService == null)
		{
			logger.error("fileImptService is null");
			return;
		}
		if (workService == null)
		{
			logger.error("workService is null");
			return;
		}
		criteria = new HashMap<String, Object>();
		criteria.put("EQ_fileName", materialShortageFile.getName()); 
		criteria.put("EQ_fileSize", materialShortageFile.length());
		lastModified = new Timestamp(materialShortageFile.lastModified());
		criteria.put("EQ_fileDate", lastModified);
		fileImptList = fileImptService.findAll(criteria);
		if (fileImptList == null || fileImptList.size() < 1)
		{
			List<Long> materialShortageIds = new ArrayList<Long>(); //缺航材的记录ID add 20161009
			//List<WorkPbs> materialShortageWids = new ArrayList<WorkPbs>(); //缺航材的工单 add 20161009
			List<String> workMaterialwids = new ArrayList<String>(); //保存缺失航材的工单ID
			
			// delete All fileImpt
			// delete All imptFailed
			if (materialShortageService == null)
			{
				logger.error("materialShortageService is null");
				return;
			}
			if (imptFailedService == null)
			{
				logger.error("imptFailedService is null");
				return;
			}
			//materialShortageService.resetIsLast(0); //是否最新  0否 1是, 更新pbs_materialshortage表isLast字段 0:已到   1:未到
			//workService.resetMsStatus(1, 2); //将缺失的航材置为到达 , 航材到达:1   有航材缺失:2, 更新pbs_work表msStatus字段
			try
			{
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(materialShortageFile), "utf-8"));
				success = 0;
				failed = 0;
				while ((line = reader.readLine()) != null)
				{
					try {
						if (line.length() < 1)
						{
							continue;
						} 
						para = line.split(tab);
						if (para.length < 22)
						{
							failed++;
							imptFailed = new ImptFailed();
							imptFailed.setFileName(materialShortageFile.getName());
							imptFailed.setLine(line);
							imptFailed.setFileType(9);
							imptFailed.setErrorInfo("Not enough columns");
							imptFailed.setDealflag(0);
							imptFailedService.save(imptFailed);
							continue;
						}
						if ("".equals(para[1]) || "".equals(para[3]) || "".equals(para[7]))
						{
							failed++;
							imptFailed = new ImptFailed();
							imptFailed.setFileName(materialShortageFile.getName());
							imptFailed.setLine(line);
							imptFailed.setFileType(9);
							imptFailed.setErrorInfo("materialShortage key is empty");
							imptFailed.setDealflag(0);
							imptFailedService.save(imptFailed);
							continue;
						}
						materialShortage = new MaterialShortage();
						materialShortage.setWorkOrder(Global.removeZeroBefore(para[1]));
						materialShortage.setMaterialNumber(para[3]);
						materialShortage.setMrCreationDate(para[7]);
						materialShortage.setLine(line);
						materialShortage.setImptFilename(materialShortageFile.getName());
						tempMaterialShortage = materialShortageService.findLast(materialShortage.getWorkOrder(),
						        materialShortage.getMaterialNumber(), materialShortage.getMrCreationDate());
						if (tempMaterialShortage != null && line.equals(tempMaterialShortage.getLine()))
						{
							materialShortage = tempMaterialShortage;
						}
							
						materialShortage = materialShortageService.save(materialShortage, para);
						
						if (materialShortage.getId() == null)
						{
							failed++;
							imptFailed = new ImptFailed();
							imptFailed.setFileName(materialShortageFile.getName());
							imptFailed.setLine(line);
							imptFailed.setFileType(9);
							imptFailed.setErrorInfo("Insert failed");
							imptFailed.setDealflag(0);
							imptFailedService.save(imptFailed);
							continue;
						}else{
							materialShortageIds.add(materialShortage.getPbsMaterialShortageId()); //缺航材的记录ID
						}
						
						//找到workPbs，将MsStatus设为2
						/*workPbs = workService.findPbsByWorkOrder(materialShortage.getWorkOrder());
						if (workPbs != null)
						{
							if (workPbs.getMsStatus() == null || workPbs.getMsStatus() != 2)
							{
								workPbs.setMsStatus(2);
								//workService.saveWorkPbs(workPbs); //modify 20161009
								materialShortageWids.add(workPbs); //add 20161009
							}
						}*/
						workMaterialwids.add(materialShortage.getWorkOrder());
						success++;
					} catch (Exception e) {
						logger.error("Exception msg:{}",e.getMessage());
						continue;
					}
				}
				
				if(materialShortageIds != null && materialShortageIds.size() > 0){
					//将不缺的航材,更新为 0:已到 (pbs_materialshortage表isLast字段)
					materialShortageService.updateMaterialToZero(materialShortageIds); //add 20161009
				}
				
				if(workMaterialwids.size() > 0){
					//更新缺航材的工单   航材到达:1   有航材缺失:2 (新pbs_work表msStatus字段)
					//workService.saveWorkPbs(materialShortageWids); //add 20161009
					workService.updateWorkPbsMaterialStatus(workMaterialwids);
				}
				//保存文件状态
				fileImpt = new FileImpt();
				fileImpt.setFileName(materialShortageFile.getName());
				fileImpt.setFileSize(materialShortageFile.length());
				fileImpt.setFileDate(lastModified);
				fileImpt.setFileType(9);
				fileImpt.setIsImpt(1);
				fileImpt.setImptTime(new Timestamp(System.currentTimeMillis()));
				fileImpt.setSuccess(success);
				fileImpt.setFailed(failed);
				fileImptService.save(fileImpt);
				try
				{
					reader.close();
				} catch (IOException e)
				{
					logger.error(e.getMessage());
				}
			} catch (IOException e)
			{
				logger.error(materialShortageFile.getName() + ":I/O Exception");
				try
				{
					reader.close();
				} catch (IOException e1)
				{
					logger.error(e1.getMessage());
				}
				saveImptError(materialShortageFile.getName(), 9, "i/o error!");
				FileUtil.moveFile(pbsDataPath + materialShortageErrorURL + materialShortageFile.getName(),materialShortageFile);
				return;
			}
		}
		FileUtil.moveFile(pbsDataPath + materialShortageProceedURL + materialShortageFile.getName(), materialShortageFile);
		logger.info("materialShortageFile {} end parse.",materialShortageFile.getName());
	}

	private void saveImptError(String fileName, int fileType, String error)
	{
		ImptError imptError = new ImptError();
		imptError.setFileName(fileName);
		imptError.setFileType(fileType);
		imptError.setImptTime(new Timestamp(System.currentTimeMillis()));
		imptError.setErrorInfo(error);
		imptErrorService.save(imptError);
	}
}
