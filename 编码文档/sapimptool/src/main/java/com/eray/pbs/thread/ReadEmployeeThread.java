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

import com.eray.pbs.po.Employee;
import com.eray.pbs.po.FileImpt;
import com.eray.pbs.po.ImptError;
import com.eray.pbs.po.ImptFailed;
import com.eray.pbs.service.EmployeeService;
import com.eray.pbs.service.FileImptService;
import com.eray.pbs.service.ImptErrorService;
import com.eray.pbs.service.ImptFailedService;
import com.eray.util.file.FileUtil;
import com.eray.util.framework.SpringContextHolder;

public class ReadEmployeeThread implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ReadEmployeeThread.class);

    private String employeeURL = "/employee/";
    private String employeeProceedURL = "/employee/proceed/";
    private String employeeErrorURL = "/employee/error/";
    private String tab = "\t";
    private File file;
    private Long sleepTime = 420000l; //毫秒 = 7分钟 = 7*60*1000
    private String error = "";
    private Long waitTime;
    private FileImptService fileImptService;
    private Map<String, Object> criteria;
    private List<FileImpt> fileImptList;
    private BufferedReader reader;
    private String line;
    private Employee employee;
    private Employee tempEmployee;
    private EmployeeService employeeService;
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
        file = new File(pbsDataPath+employeeProceedURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        file = new File(pbsDataPath+employeeErrorURL);
        if (!file.exists())
        {
            file.mkdir();
        }
        fileImptService = SpringContextHolder.getBean("fileImptService");
        employeeService = SpringContextHolder.getBean("employeeService");
        imptFailedService = SpringContextHolder.getBean("imptFailedService");
        imptErrorService = SpringContextHolder.getBean("imptErrorService");
        while (true)
        {
            waitTime = System.nanoTime();
            file = new File(pbsDataPath+employeeURL);
            if (file.isDirectory())
            {
                for (File employeeFile : file.listFiles())
                {
                    if (employeeFile.isDirectory())
                    {
                        continue;
                    }
                    importFile(employeeFile);
                }
                waitTime = System.nanoTime() - waitTime;
                if (sleepTime > waitTime)
                {
                    try
                    {
                        Thread.sleep(sleepTime - waitTime);
                    } catch (InterruptedException e)
                    {
                        logger.error("Employee Thread is interrupted!");
                    }
                }
            } else
            {
                error = "Employee Directory is Wrong!";
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
                    logger.error("Employee Thread is interrupted!");
                }
            }
        }
    }

    private void importFile(File employeeFile)
    {
        // check Exist
        if (fileImptService == null)
        {
            logger.error("fileImptService is null");
            return;
        }
        criteria = new HashMap<String, Object>();
        criteria.put("EQ_fileName", employeeFile.getName());
        criteria.put("EQ_fileSize", employeeFile.length());
        lastModified = new Timestamp(employeeFile.lastModified());
        criteria.put("EQ_fileDate", lastModified);
        fileImptList = fileImptService.findAll(criteria);
        if (fileImptList == null || fileImptList.size() < 1)
        {
            // delete All fileImpt
            // delete All imptFailed
            if (employeeService == null)
            {
                logger.error("employeeService is null");
                return;
            }
            if (imptFailedService == null)
            {
                logger.error("imptFailedService is null");
                return;
            }
            try
            {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(employeeFile), "utf-8"));
                success = 0;
                failed = 0;
                while ((line = reader.readLine()) != null)
                {
                    if (line.length() < 1)
                    {
                        continue;
                    }
                    para = line.split(tab);
                    if (para.length < 7)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(employeeFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(2);
                        imptFailed.setErrorInfo("Not enough columns");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    if ("".equals(para[0]) || "".equals(para[2]) )
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(employeeFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(2);
                        imptFailed.setErrorInfo("employee key is empty");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    employee = new Employee();
                    employee.setEmployeeId(para[0]);
                    employee.setQualificationId(para[2]);
                    employee.setLine(line);
                    employee.setImptFilename(employeeFile.getName());
                    tempEmployee = employeeService.findLast(employee.getEmployeeId(), employee.getQualificationId());
                    if (tempEmployee != null && line.equals(tempEmployee.getLine()))
                    {
                        employee = tempEmployee;
                    } else
                    {
                        employee = employeeService.save(employee, para);
                    }
                    if (employee.getId() == null)
                    {
                        failed++;
                        imptFailed = new ImptFailed();
                        imptFailed.setFileName(employeeFile.getName());
                        imptFailed.setLine(line);
                        imptFailed.setFileType(2);
                        imptFailed.setErrorInfo("Insert failed");
                        imptFailed.setDealflag(0);
                        imptFailedService.save(imptFailed);
                        continue;
                    }
                    success++;
                }
                fileImpt = new FileImpt();
                fileImpt.setFileName(employeeFile.getName());
                fileImpt.setFileSize(employeeFile.length());
                fileImpt.setFileDate(lastModified);
                fileImpt.setFileType(2);
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
                logger.error(employeeFile.getName() + ":I/O Exception");
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                    logger.error(e1.getMessage());
                }
                saveImptError(employeeFile.getName(),2,"i/o error!");
                FileUtil.moveFile(pbsDataPath+employeeErrorURL + employeeFile.getName(), employeeFile);
                return;
            }
        }
        FileUtil.moveFile(pbsDataPath+employeeProceedURL + employeeFile.getName(), employeeFile);
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
