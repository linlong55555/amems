package com.eray.pbs.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.Ehr;
import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.service.EhrService;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.util.Global;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadEhrThread implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ReadEhrThread.class);

    private String ehrURL = "/ehr/";
    private String ehrProceedURL = "/ehr/proceed/";
    private String ehrErrorURL = "/ehr/error/";
    private String tab = "\t";
    private File file;
    private Long sleepTime = 640000L;  //毫秒 = 14分钟 = 14*60*1000
    private String error = "";
    private Long waitTime;
    private FileImptService fileImptService;
    private Map<String, Object> criteria;
    private List<FileImpt> fileImptList;
    private BufferedReader reader;
    private String line;
    private Ehr ehr;
    private Ehr tempEhr;
    private EhrService ehrService;
    private ImptFailedService imptFailedService;
    private ImptFailed imptFailed;
    private String[] para;
    private FileImpt fileImpt;
    private Timestamp lastModified;

    private int success = 0;
    private int failed = 0;
    
    private ImptErrorService imptErrorService;
    private String pbsDataPath;
    @Override
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
        pbsDataPath = prop.getProperty("pbsDataPath");
        file = new File(pbsDataPath+ehrProceedURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        file = new File(pbsDataPath+ehrErrorURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        fileImptService = SpringContextHolder.getBean("fileImptService");
        ehrService = SpringContextHolder.getBean("ehrService");
        imptFailedService = SpringContextHolder.getBean("imptFailedService");
        imptErrorService = SpringContextHolder.getBean("imptErrorService");
        while (true)
        {
            waitTime = System.nanoTime();
            file = new File(pbsDataPath+ehrURL);
            if (file.isDirectory())
            {
                for (File ehrFile : file.listFiles())
                {
                    if (ehrFile.isDirectory())
                    {
                        continue;
                    }
                    importFile(ehrFile);
                }
                waitTime = System.nanoTime() - waitTime;
                if (sleepTime > waitTime)
                {
                    try
                    {
                        Thread.sleep(sleepTime - waitTime);
                    } catch (InterruptedException e)
                    {
                        logger.error("Ehr Thread is interrupted!");
                    }
                }
            } else
            {
                error = "Ehr Directory is Wrong!";
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
                    logger.error("Ehr Thread is interrupted!");
                }
            }
        }
    }

    private void importFile(File ehrFile)
    {
        // check Exist
        if (fileImptService == null)
        {
            logger.error("fileImptService is null");
            return;
        }
        criteria = new HashMap<String, Object>();
        criteria.put("EQ_fileName", ehrFile.getName());
        criteria.put("EQ_fileSize", ehrFile.length());
        lastModified = new Timestamp(ehrFile.lastModified());
        criteria.put("EQ_fileDate", lastModified);
        fileImptList = fileImptService.findAll(criteria);
        if (fileImptList == null || fileImptList.size() < 1)
        {
            // delete All fileImpt
            // delete All imptFailed
            if (ehrService == null)
            {
                logger.error("ehrService is null");
                return;
            }
            if (imptFailedService == null)
            {
                logger.error("imptFailedService is null");
                return;
            }
            try
            {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(ehrFile), "utf-8"));
                success = 0;
                failed = 0;
                while ((line = reader.readLine()) != null)
                {
                    if (line.length() < 1)
                    {
                        continue;
                    }
                    para = line.split(tab);
                    if (para.length < 10)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(ehrFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(11);
                        imptFailed.setErrorInfo("Not enough columns");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    if ("".equals(para[1]) )
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(ehrFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(11);
                        imptFailed.setErrorInfo("ehr key is empty");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    ehr = new Ehr();
                    ehr.setWorkOrder(Global.removeZeroBefore(para[1]));
                    ehr.setLine(line);
                    ehr.setImptFilename(ehrFile.getName());
                    tempEhr = ehrService.findLast(ehr.getWorkOrder());
                    if (tempEhr != null && line.equals(tempEhr.getLine()))
                    {
                        ehr = tempEhr;
                    } else
                    {
                        ehr = ehrService.save(ehr, para);
                    }
                    if (ehr.getId() == null)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(ehrFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(11);
                        imptFailed.setErrorInfo("Insert failed");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    success++;
                }
                fileImpt = new FileImpt();
                fileImpt.setFileName(ehrFile.getName());
                fileImpt.setFileSize(ehrFile.length());
                fileImpt.setFileDate(lastModified);
                fileImpt.setFileType(11);
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
                logger.error(ehrFile.getName() + ":I/O Exception");
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                    logger.error(e1.getMessage());
                }
                saveImptError(ehrFile.getName(),11,"i/o error!");
                FileUtil.moveFile(pbsDataPath+ehrErrorURL + ehrFile.getName(), ehrFile);
                return;
            }
        }
        FileUtil.moveFile(pbsDataPath+ehrProceedURL + ehrFile.getName(), ehrFile);
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
