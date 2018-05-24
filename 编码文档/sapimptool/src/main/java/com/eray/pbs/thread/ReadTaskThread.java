package com.eray.pbs.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.po.Task;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.TaskService;
import com.eray.util.Global;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadTaskThread implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ReadTaskThread.class);
    private String taskURL = "/tasklist/";
    private String taskProceedURL = "/tasklist/proceed/";
    private String taskErrorURL = "/tasklist/error/";
    private String tab = "\t";
    private File file;
    private Long sleepTime = 120000l;  //毫秒 = 2分钟 = 2*60*1000
    private String error = "";
    private Long waitTime;
    private FileImptService fileImptService;
    private Map<String, Object> criteria;
    private List<FileImpt> fileImptList;
    private BufferedReader reader;
    private String line;
    private Task task;
    private Task tempTask;
    private TaskService taskService;
    private ImptFailedService imptFailedService;
    
    private ImptFailed imptFailed;
    private String[] para;
    private FileImpt fileImpt;
    private Timestamp lastModified;

    private int success = 0;
    private int failed = 0;

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
        file = new File(pbsDataPath+taskProceedURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        file = new File(pbsDataPath+taskErrorURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        fileImptService = SpringContextHolder.getBean("fileImptService");
        taskService = SpringContextHolder.getBean("taskService");
        imptFailedService = SpringContextHolder.getBean("imptFailedService");
        imptErrorService = SpringContextHolder.getBean("imptErrorService");
        while (true)
        {
            waitTime = new Date().getTime();
            file = new File(pbsDataPath+taskURL);
            
            if (file.isDirectory())
            {
                for (File taskFile : file.listFiles())
                {
                    if (taskFile.isDirectory())
                    {
                        continue;
                    }
                    importFile(taskFile);
                }
                waitTime = new Date().getTime() - waitTime;
                if (sleepTime > waitTime)
                {
                    try
                    {
                        Thread.sleep(sleepTime - waitTime);
                    } catch (InterruptedException e)
                    {
                        logger.error("Task Thread is interrupted!");
                    }
                }
            } else
            {
                error = "Task Directory is Wrong!";
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
                    logger.error("Task Thread is interrupted!");
                }
            }
        }
    }

    private void importFile(File taskFile)
    {
    	fileName = new SimpleDateFormat("yyyyMMddHHmmss_").format(handleDate) + taskFile.getName();
    	
        // check Exist
        if (fileImptService == null)
        {
            logger.error("fileImptService is null");
            return;
        }
        criteria = new HashMap<String, Object>();
        criteria.put("EQ_fileName", taskFile.getName());
        criteria.put("EQ_fileSize", taskFile.length());
        lastModified = new Timestamp(taskFile.lastModified());
        criteria.put("EQ_fileDate", lastModified);
        fileImptList = fileImptService.findAll(criteria);
        if (fileImptList == null || fileImptList.size() < 1)
        {
            // delete All fileImpt
            // delete All imptFailed
            if (taskService == null)
            {
                logger.error("taskService is null");
                return;
            }
            if (imptFailedService == null)
            {
                logger.error("imptFailedService is null");
                return;
            }
            try
            {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(taskFile), "utf-8"));
                success = 0;
                failed = 0;
                while ((line = reader.readLine()) != null)
                {
                    if (line.length() < 1)
                    {
                        continue;
                    }
                    para = line.split(tab);
                    if (para.length < 29)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(fileName);
                        imptFailed.setLine(line);
                        imptFailed.setFileType(6);
                        imptFailed.setErrorInfo("Not enough columns");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    if ("".equals(para[0]) || "".equals(para[1])|| "".equals(para[3])|| "".equals(para[8]))
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(fileName);
                        imptFailed.setLine(line);
                        imptFailed.setFileType(6);
                        imptFailed.setErrorInfo("Task key is empty");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                    }
                    task = new Task();
                    task.setCardNumber(para[0]);
                    task.setGroupId(Global.removeZeroBefore(para[1]));
                    task.setKeyDate(para[3]);
                    if("737N".equals(para[8])){
                    	task.setAircraftType("B737NG");
                    }else if("737C".equals(para[8])){
                    	task.setAircraftType("B737CL");
                    }else{
                    	task.setAircraftType(para[8]);
                    }
                    task.setOperationNumber(para[14]);
                    if(!"0010".equals(task.getOperationNumber())){
                        continue;
                    }
                    task.setLine(line);
                    task.setImptFilename(fileName);
                    tempTask = taskService.findLast(task.getCardNumber(),task.getGroupId(),task.getKeyDate(), task.getAircraftType());
                    if (tempTask != null && line.equals(tempTask.getLine()))
                    {
                        task = tempTask;
                    } else
                    {
                        task = taskService.save(task,para);
                    }
                    if (task.getId() == null)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(fileName);
                        imptFailed.setLine(line);
                        imptFailed.setFileType(6);
                        imptFailed.setErrorInfo("Insert failed");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                    }
                    success++;
                }
                fileImpt = new FileImpt();
                fileImpt.setFileName(taskFile.getName());
                fileImpt.setFileSize(taskFile.length());
                fileImpt.setFileDate(lastModified);
                fileImpt.setFileType(6);
                fileImpt.setIsImpt(1);
                fileImpt.setImptTime(new Timestamp(handleDate.getTime()));
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
                logger.error(taskFile.getName() + ":I/O Exception");
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                    logger.error(e1.getMessage());
                }
                saveImptError(taskFile.getName(),6,"i/o error!");
                FileUtil.moveFile(pbsDataPath+taskErrorURL + fileName, taskFile);
                return;
            }
        }
        FileUtil.moveFile(pbsDataPath+taskProceedURL + fileName, taskFile);
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
}
