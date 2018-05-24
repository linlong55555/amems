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

import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.po.Revision;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.RevisionService;
import com.eray.util.Global;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadRevisionThread implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ReadRevisionThread.class);
    private String revisionURL = "/revision/";
    private String revisionProceedURL = "/revision/proceed/";
    private String revisionErrorURL = "/revision/error/";
    private String tab = "\t";
    private File file;
    private Long sleepTime = 540000l; ////毫秒 = 9分钟 = 9*60*1000
    private String error = "";
    private Long waitTime;
    private FileImptService fileImptService;
    private Map<String, Object> criteria;
    private List<FileImpt> fileImptList;
    private BufferedReader reader;
    private String line;
    private Revision revision;
    private Revision tempRevision;
    private RevisionService revisionService;
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
        file = new File(pbsDataPath+revisionProceedURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        file = new File(pbsDataPath+revisionErrorURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        fileImptService = SpringContextHolder.getBean("fileImptService");
        revisionService = SpringContextHolder.getBean("revisionService");
        imptFailedService = SpringContextHolder.getBean("imptFailedService");
        imptErrorService = SpringContextHolder.getBean("imptErrorService");
        while (true)
        {
            waitTime = System.nanoTime();
            file = new File(pbsDataPath+revisionURL);

            if (file.isDirectory())
            {
                for (File revisionFile : file.listFiles())
                {
                    if (revisionFile.isDirectory())
                    {
                        continue;
                    }
                    importFile(revisionFile);
                }
                waitTime = System.nanoTime() - waitTime;
                if (sleepTime > waitTime)
                {
                    try
                    {
                        Thread.sleep(sleepTime - waitTime);
                    } catch (InterruptedException e)
                    {
                        logger.error("Revision Thread is interrupted!");
                    }
                }
            } else
            {
                error = "Revision Directory is Wrong!";
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
                    logger.error("Revision Thread is interrupted!");
                }
            }
        }
    }

    private void importFile(File revisionFile)
    {
        // check Exist
        if (fileImptService == null)
        {
            logger.error("fileImptService is null");
            return;
        }
        criteria = new HashMap<String, Object>();
        criteria.put("EQ_fileName", revisionFile.getName());
        criteria.put("EQ_fileSize", revisionFile.length());
        lastModified = new Timestamp(revisionFile.lastModified());
        criteria.put("EQ_fileDate", lastModified);
        fileImptList = fileImptService.findAll(criteria);
        if (fileImptList == null || fileImptList.size() < 1)
        {
            // delete All fileImpt
            // delete All imptFailed
            if (revisionService == null)
            {
                logger.error("revisionService is null");
                return;
            }
            if (imptFailedService == null)
            {
                logger.error("imptFailedService is null");
                return;
            }
            try
            {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(revisionFile), "utf-8"));
                success = 0;
                failed = 0;
                while ((line = reader.readLine()) != null)
                {
                    if (line.length() < 1)
                    {
                        continue;
                    }
                    para = line.split(tab);
                    if (para.length < 12)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(revisionFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(3);
                        imptFailed.setErrorInfo("Not enough columns");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    if ("".equals(para[0]))
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(revisionFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(3);
                        imptFailed.setErrorInfo("key is empty");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    revision = new Revision();
                    revision.setRevisionId(Global.removeZeroBefore(para[0]));
                    revision.setLine(line);
                    revision.setImptFilename(revisionFile.getName());
                    tempRevision = revisionService.findLast(revision.getRevisionId());
                    if (tempRevision != null && line.equals(tempRevision.getLine()))
                    {
                        revision = tempRevision;
                    } else
                    {
                        revision = revisionService.save(revision,para);
                    }
                    if (revision.getId() == null)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(revisionFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(3);
                        imptFailed.setErrorInfo("Insert failed");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                    }
                    success++;
                }
                fileImpt = new FileImpt();
                fileImpt.setFileName(revisionFile.getName());
                fileImpt.setFileSize(revisionFile.length());
                fileImpt.setFileDate(lastModified);
                fileImpt.setFileType(3);
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
                logger.error(revisionFile.getName() + ":I/O Exception");
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                    logger.error(e1.getMessage());
                }
                saveImptError(revisionFile.getName(),3,"i/o error!");
                FileUtil.moveFile(pbsDataPath+revisionErrorURL + revisionFile.getName(), revisionFile);
                return;
            }
        }
        FileUtil.moveFile(pbsDataPath+revisionProceedURL + revisionFile.getName(), revisionFile);
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
