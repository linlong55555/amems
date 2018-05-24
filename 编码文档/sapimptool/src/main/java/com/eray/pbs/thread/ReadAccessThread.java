package com.eray.pbs.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.AccessMh;
import com.eray.pbs.po.DbTask;
import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.Group;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.po.MIConfigBase;
import com.eray.pbs.po.WorkCenter;
import com.eray.pbs.service.AccessMhService;
import com.eray.pbs.service.DbTaskService;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.ReadConfigService;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;
import com.eray.util.poi.ExcelUtil;

public class ReadAccessThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(ReadAccessThread.class);

	private String accessURL = "/access/";
	private String accessProceedURL = "/access/proceed/";
	private String accessErrorURL = "/access/error/";
	private String tab = "\t";
	private File file;
	private Long sleepTime = 660000l;  //毫秒 = 11分钟 = 11*60*1000
	private String error = "";
	private Long waitTime;
	private FileImptService fileImptService; 
	private Map<String, Object> criteria;
	private List<FileImpt> fileImptList;
	private DbTask dbTask;
	private DbTask tempDbTask;
	private DbTaskService dbTaskService;
	private ImptFailedService imptFailedService;
	private ImptFailed imptFailed;
	private FileImpt fileImpt;
	private Timestamp lastModified;
	private StringBuilder sb;

	private AccessMh accessMh;
	private AccessMh tempAccessMh;
	private AccessMhService accessMhService;

	private ImptErrorService imptErrorService;
	
	private ReadConfigService readConfigService;

	private static Workbook wb;
	private static Sheet sheet;
	private static Row row;

	private int success;
	private int failed;
	private String aircraftType;

	private int coloumNum;
	private int rowNum;
	private String pbsDataPath;
	private Date handleDate;
	private String fileName; //文件名=处理时间_处理人_+文件名
	private String handleMan; //处理人
	
	private Map<String, Group> groupMap = new HashMap<String, Group>(); //key=name
	private Map<String, WorkCenter> workcenterNameMap = new HashMap<String, WorkCenter>(); //key=workCenter
	private Map<Long, WorkCenter> workcenterIdMap = new HashMap<Long, WorkCenter>(); //key=id
	private Map<String, MIConfigBase> miConfigMap = new HashMap<String, MIConfigBase>(); //key=workCenter
	private Map<String, WorkCenter> workcenterMap = new HashMap<String, WorkCenter>(); //全称

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
		file = new File(pbsDataPath+accessProceedURL);
		if (!file.exists())
		{
			file.mkdir();
		}
		file = new File(pbsDataPath+accessErrorURL);
		if (!file.exists())
		{
			file.mkdir();
		}
		fileImptService = SpringContextHolder.getBean("fileImptService");
		dbTaskService = SpringContextHolder.getBean("dbTaskService");
		accessMhService = SpringContextHolder.getBean("accessMhService");
		imptFailedService = SpringContextHolder.getBean("imptFailedService");
		imptErrorService = SpringContextHolder.getBean("imptErrorService");
		readConfigService = SpringContextHolder.getBean("readConfigService");
		while (true)
		{
			//获取所有的Group、WorkCenter、MH配置信息
			getConfigInfo();
			
			waitTime = new Date().getTime();
			
			file = new File(pbsDataPath+accessURL);
			if (file.isDirectory())
			{
				for (File accessFile : file.listFiles())
				{
					if (accessFile.isDirectory())
					{
						continue;
					}
					importFile(accessFile); //导入文件
				}
				waitTime = new Date().getTime() - waitTime;
				if (sleepTime > waitTime)
				{
					try
					{
						Thread.sleep(sleepTime - waitTime);
					} catch (InterruptedException e)
					{
						logger.error("Access Thread is interrupted!");
					}
				}
			} else
			{
				error = "Access Directory is Wrong!";
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
					logger.error("Access Thread is interrupted!");
				}
			}
		}
	}

	private void importFile(File accessFile)
	{
		handleMan = accessFile.getName().split("@")[1];
		fileName =  new SimpleDateFormat("yyyyMMddHHmmss_").format(handleDate) +handleMan+"_"+ accessFile.getName();
		
		//校验操作的service是否为null
		if (fileImptService == null)
		{
			logger.error("fileImptService is null");
			return;
		}
		
		if (dbTaskService == null)
		{
			logger.error("dbTaskService is null");
			return;
		}
		if (accessMhService == null)
		{
			logger.error("accessMhService is null");
			return;
		}
		if (imptFailedService == null)
		{
			logger.error("imptFailedService is null");
			return;
		}
		
		//判断文件是否已经导入过了,没导入,才进行导入处理
		criteria = new HashMap<String, Object>();
		criteria.put("EQ_fileName", accessFile.getName());
		criteria.put("EQ_fileSize", accessFile.length());
		lastModified = new Timestamp(accessFile.lastModified());
		criteria.put("EQ_fileDate", lastModified);
		fileImptList = fileImptService.findAll(criteria);
		if (fileImptList == null || fileImptList.size() < 1)
		{
			try
			{
				wb = create(new FileInputStream(accessFile)); //IO读入文件
			} catch (Exception e)
			{
				logger.error("XSSFWorkbook i/o error!");
				saveImptError(fileName, 10, "XSSFWorkbook i/o error!");
				FileUtil.moveFile(pbsDataPath+accessErrorURL + accessFile.getName(), accessFile);
				return;
			}

			//解析第一个sheet
			if(readFirstSheet(accessFile)){
				return ;
			}
			
			//解析第二个sheet
			if(readSecondSheet(accessFile)){
				return ;
			}

			//导入成功，将文件记录sap_fileimpt成功表
			fileImpt = new FileImpt();
			fileImpt.setFileName(accessFile.getName());
			fileImpt.setFileSize(accessFile.length());
			fileImpt.setFileDate(new Timestamp(accessFile.lastModified()));
			fileImpt.setFileType(10);
			fileImpt.setIsImpt(1);
			fileImpt.setImptTime(new Timestamp(handleDate.getTime()));
			fileImpt.setSuccess(success);
			fileImpt.setFailed(failed);
			fileImptService.save(fileImpt);
		}
		
		//将导入的文件，移动到proceed文件夹下
		FileUtil.moveFile(pbsDataPath+accessProceedURL + fileName , accessFile);
		
		//刷新所有Stage为空的NRC工单
		dbTaskService.refreshNoStageRoutingWork();
	}

	//第二个sheet
	private boolean readSecondSheet(File accessFile) {
		sheet = wb.getSheetAt(1);
		
		if (sheet == null)
		{
			logger.error("ACCESS & ACCESS PNL sheet is not exist!");
			saveImptError(fileName, 10, "ACCESS & ACCESS PNL sheet is not exist!");
			FileUtil.moveFile(pbsDataPath+accessErrorURL + fileName, accessFile);
			return true;
		}
		
		if (sheet != null)
		{
			rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++)
			{
				row = sheet.getRow(i);
				accessMh = new AccessMh();
				accessMh.setAccessNo(ExcelUtil.getCellValue(row.getCell(0)));
				sb = new StringBuilder();
				sb.append(ExcelUtil.getCellValue(row.getCell(0)));
				sb.append(tab);
				sb.append(ExcelUtil.getCellValue(row.getCell(1)));
				sb.append(tab);
				sb.append(ExcelUtil.getCellValue(row.getCell(2)));
				sb.append(tab);
				sb.append(ExcelUtil.getCellValue(row.getCell(3)));
				sb.append(tab);
				sb.append(ExcelUtil.getCellValue(row.getCell(4)));
				sb.append(tab);
				sb.append(ExcelUtil.getCellValue(row.getCell(5)));
				accessMh.setLine(sb.toString());
				accessMh.setAircraftType(aircraftType);
				
				if(StringUtils.isEmpty(accessMh.getLine().replaceAll("\\s*", ""))){
					continue; //如果整行数据都为空,直接返回
				}
				
				if (accessMh.getAccessNo() == null || accessMh.getAircraftType().equals(""))
				{
					failed++;
					imptFailed = new ImptFailed();
					imptFailed.setFileName(fileName);
					imptFailed.setLine(accessMh.getLine());
					imptFailed.setFileType(12);
					imptFailed.setErrorInfo("AccessNo is empty");
					imptFailed.setDealflag(0);
					imptFailedService.save(imptFailed);
					continue;
				}
				accessMh.setImptFilename(accessFile.getName());
				tempAccessMh = accessMhService.findLast(accessMh.getAccessNo(), accessMh.getAircraftType());
				if (tempAccessMh != null && accessMh.getLine().equals(tempAccessMh.getLine()))
				{
					accessMh = tempAccessMh;
				} else
				{
					accessMh = accessMhService.save(accessMh, row);
				}
				if (accessMh.getId() == null)
				{
					failed++;
					imptFailed = new ImptFailed();
					imptFailed.setFileName(fileName);
					imptFailed.setLine(accessMh.getLine());
					imptFailed.setFileType(12);
					imptFailed.setErrorInfo("Insert failed");
					imptFailed.setDealflag(0);
					imptFailedService.save(imptFailed);
					continue;
				}
				success++;
			}
		}
		return false;
	}
	
	//第一个sheet
	private boolean readFirstSheet(File accessFile) {
		sheet = wb.getSheetAt(0); //获取第一个sheet
		if (sheet == null)
		{
			logger.error("TRADE sheet is not exist!");
			saveImptError(fileName, 10, "TRADE sheet is not exist!");
			FileUtil.moveFile(pbsDataPath+accessErrorURL + fileName, accessFile);
			return true;
		}
		coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();//获得总列数
		rowNum = sheet.getLastRowNum(); //获取总行数
		success = 0;                    //重置成功记录为0
		failed = 0;                     //重置失败记录为0
		aircraftType = accessFile.getName().split("@")[0].toUpperCase(); //飞机类型： 文件名称拆分后第一个元素
		
		//begin：循环读取每一个行数据
		for (int i = 1; i <= rowNum; i++)
		{
			row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			
			dbTask = new DbTask(); //构建原始数据对象sap_task
			dbTask.setCardNumber(ExcelUtil.getCellValue(row.getCell(0)));
			
			sb = new StringBuilder();
			for (int j = 0; j < coloumNum; j++) {
				sb.append(ExcelUtil.getCellValue(row.getCell(j)));
				if(j != coloumNum-1){
					sb.append(tab);
				}
			}
			dbTask.setLine(sb.toString());
			
			//工卡或飞机类型不能为空
			if (dbTask.getCardNumber() == null || dbTask.getCardNumber().equals("") || aircraftType.equals(""))
			{
				failed++;
				imptFailed = new ImptFailed();
				imptFailed.setFileName(fileName);
				imptFailed.setLine(dbTask.getLine());
				imptFailed.setFileType(10);
				imptFailed.setErrorInfo("cardNumber or aircraftType is empty");
				imptFailed.setDealflag(0);
				imptFailedService.save(imptFailed);
				continue;
			}
			dbTask.setAircraftType(aircraftType);
			dbTask.setImptFilename(fileName);
			
			tempDbTask = dbTaskService.findLast(dbTask.getCardNumber(), dbTask.getAircraftType());
			if (tempDbTask != null && dbTask.getLine().equals(tempDbTask.getLine()))
			{
				dbTask = tempDbTask;
			} else
			{
				dbTask = dbTaskService.save(dbTask, row,groupMap,workcenterNameMap,workcenterIdMap,miConfigMap,workcenterMap,handleMan,handleDate);
			}
			if (dbTask.getId() == null)
			{
				failed++;
				imptFailed = new ImptFailed();
				imptFailed.setFileName(fileName);
				imptFailed.setLine(dbTask.getLine());
				imptFailed.setFileType(10);
				imptFailed.setErrorInfo("Insert failed："+dbTask.getErrorMsg());
				imptFailed.setDealflag(0);
				imptFailedService.save(imptFailed);
				continue;
			}
			success++;
		}
		//end：循环读取每一个行数据
		
		return false;
	}

	private void saveImptError(String fileName, int fileType, String error)
	{
		ImptError imptError = new ImptError();
		imptError.setFileName(fileName);
		imptError.setFileType(fileType);
		imptError.setImptTime(new Timestamp(handleDate.getTime()));
		imptError.setErrorInfo(error);
		imptErrorService.save(imptError);
	}

	public static Workbook create(InputStream in) throws     
	IOException,InvalidFormatException {
		if (!in.markSupported()) {
			in = new PushbackInputStream(in, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			return new HSSFWorkbook(in);
		}
		if (POIXMLDocument.hasOOXMLHeader(in)) {
			return new XSSFWorkbook(OPCPackage.open(in));
		}
		throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}
	
	
	/**
	 * 获取所有的Group、WorkCenter、MH配置信息
	 * 每次读取文件前,都将配置信息准备好,避免循环查询
	 */
	private void getConfigInfo() {
		groupMap = readConfigService.getGroupMap();
		workcenterNameMap = readConfigService.getWorkcenterNameMap();
		workcenterIdMap = readConfigService.getWorkcenterIdMap();
		miConfigMap = readConfigService.getMiConfigMap();
		workcenterMap = new HashMap<String,WorkCenter>();
	}
}
