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
import com.eray.pbs.po.Project;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.pbs.service.ProjectService;
import com.eray.util.Global;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadProjectThread implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ReadProjectThread.class);
    private String projectURL = "/project/";
    private String projectProceedURL = "/project/proceed/";
    private String projectErrorURL = "/project/error/";
    private String tab = "\t";
    private File file;
    private Long sleepTime = 120000l;
    private String error = "";
    private Long waitTime;
    private FileImptService fileImptService;
    private Map<String, Object> criteria;
    private List<FileImpt> fileImptList;
    private BufferedReader reader;
    private String line;
    private Project project;
    private Project tempProject;
    private ProjectService projectService;
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
        file = new File(pbsDataPath+projectProceedURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        file = new File(pbsDataPath+projectErrorURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        fileImptService = SpringContextHolder.getBean("fileImptService");
        projectService = SpringContextHolder.getBean("projectService");
        imptFailedService = SpringContextHolder.getBean("imptFailedService");
        imptErrorService = SpringContextHolder.getBean("imptErrorService");
        while (true)
        {
            waitTime = System.nanoTime();
            file = new File(pbsDataPath+projectURL);

            if (file.isDirectory())
            {
                for (File projectFile : file.listFiles())
                {
                    if (projectFile.isDirectory())
                    {
                        continue;
                    }
                    importFile(projectFile);
                }
                waitTime = System.nanoTime() - waitTime;
                if (sleepTime > waitTime)
                {
                    try
                    {
                        Thread.sleep(sleepTime - waitTime);
                    } catch (InterruptedException e)
                    {
                        logger.error("Project Thread is interrupted!");
                    }
                }
            } else
            {
                error = "Project Directory is Wrong!";
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
                    logger.error("Project Thread is interrupted!");
                }
            }
        }
    }

    private void importFile(File projectFile)
    {
        // check Exist
        if (fileImptService == null)
        {
            logger.error("fileImptService is null");
            return;
        }
        criteria = new HashMap<String, Object>();
        criteria.put("EQ_fileName", projectFile.getName());
        criteria.put("EQ_fileSize", projectFile.length());
        lastModified = new Timestamp(projectFile.lastModified());
        criteria.put("EQ_fileDate", lastModified);
        fileImptList = fileImptService.findAll(criteria);
        if (fileImptList == null || fileImptList.size() < 1)
        {
            // delete All fileImpt
            // delete All imptFailed
            if (projectService == null)
            {
                logger.error("projectService is null");
                return;
            }
            if (imptFailedService == null)
            {
                logger.error("imptFailedService is null");
                return;
            }
            try
            {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(projectFile), "gbk"));
                success = 0;
                failed = 0;
                while ((line = reader.readLine()) != null)
                {
                    if (line.length() < 1)
                    {
                        continue;
                    }
                    para = line.split(tab);
                    if (para.length < 5)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(projectFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(1);
                        imptFailed.setErrorInfo("Not enough columns");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    if ("".equals(para[0]) || "".equals(para[1]))
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(projectFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(1);
                        imptFailed.setErrorInfo("key is empty");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    project = new Project();
                    project.setRevisionId(Global.removeZeroBefore(para[0]));
                    project.setProjectId(Global.removeZeroBefore(para[1]));
                    project.setLine(line);
                    project.setImptFilename(projectFile.getName());
                    tempProject = projectService.findLast(project.getRevisionId(), project.getProjectId());
                    if (tempProject != null && line.equals(tempProject.getLine()))
                    {
                        project = tempProject;
                    } else
                    {
                        project = projectService.save(project,para);
                    }
                    if (project.getId() == null)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(projectFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(1);
                        imptFailed.setErrorInfo("Insert failed");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    success++;
                }
                fileImpt = new FileImpt();
                fileImpt.setFileName(projectFile.getName());
                fileImpt.setFileSize(projectFile.length());
                fileImpt.setFileDate(lastModified);
                fileImpt.setFileType(1);
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
                logger.error(projectFile.getName() + ":I/O Exception");
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                    logger.error(e1.getMessage());
                }
                saveImptError(projectFile.getName(),1,"i/o error!");
                FileUtil.moveFile(pbsDataPath+projectErrorURL + projectFile.getName(), projectFile);
                return;
            }
        }
        FileUtil.moveFile(pbsDataPath+projectProceedURL + projectFile.getName(), projectFile);
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
