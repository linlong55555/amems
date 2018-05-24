package com.eray.pbs.thread;

import java.math.BigDecimal;
import java.util.List;

import com.eray.pbs.po.OperationPbs;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.service.WorkService;
import com.eray.util.framework.SpringContextHolder;

public class ReadStatusThread implements Runnable
{
	private WorkService workService;
	private BigDecimal workhours;
	private BigDecimal duration;
	private BigDecimal pointOne = new BigDecimal("0.1");

	@Override
	public void run()
	{
		// 扫描所有NRC
		workService = SpringContextHolder.getBean("workService");
		List<WorkPbs> workList = workService.findNrcWork();
		List<OperationPbs> operationList;
		for (WorkPbs work : workList)
		{
			workhours = BigDecimal.ZERO;
			duration = BigDecimal.ZERO;
			// 获取NRC的工序
			operationList = workService.findOperationByWorkId(work.getWid());
			for (OperationPbs operation : operationList)
			{
				// 过滤掉0.1重新计算工时
				if (operation.getWorkActivity().compareTo(pointOne) > 0)
				{
					workhours = workhours.add(operation.getWorkActivity());
				}
				if (operation.getWorkhours().compareTo(pointOne) > 0)
				{
					duration = duration.add(operation.getWorkhours());
				}
			}
			// 保存新NRC工时
			work.setWorkhours(workhours);
			work.setDuration(duration);
			workService.saveWorkPbs(work);
		}
	}
	// private static final Logger logger =
	// LoggerFactory.getLogger(ReadStatusThread.class);
	// private String statusURL = "smb://10.7.0.230/Exchange4PB/wst/";
	// private String statusProceedURL =
	// "smb://10.7.0.230/Exchange4PB/wst/proceed/";
	// private String statusErrorURL =
	// "smb://10.7.0.230/Exchange4PB/wst/error/";
	// private String tab = "\t";
	// private SmbFile file;
	// private Long sleepTime = 120000l;
	// private String error = "";
	// private Long waitTime;
	// private FileImptService fileImptService;
	// private Map<String, Object> criteria;
	// private List<FileImpt> fileImptList;
	// private BufferedReader reader;
	// private String line;
	// private Status status;
	// private Status tempStatus;
	// private StatusService statusService;
	// private ImptFailedService imptFailedService;
	// private ImptFailed imptFailed;
	// private String[] para;
	// private FileImpt fileImpt;
	//
	// private int success = 0;
	// private int failed = 0;
	//
	// private ImptErrorService imptErrorService;
	//
	// @Override
	// public void run()
	// {
	// try
	// {
	// file = new SmbFile(statusProceedURL);
	// if (!file.exists())
	// {
	// file.mkdir();
	// }
	// file = new SmbFile(statusErrorURL);
	// if (!file.exists())
	// {
	// file.mkdir();
	// }
	// } catch (MalformedURLException | SmbException e1)
	// {
	// logger.error("Status Directory Error!");
	// }
	// fileImptService = SpringContextHolder.getBean("fileImptService");
	// statusService = SpringContextHolder.getBean("statusService");
	// imptFailedService = SpringContextHolder.getBean("imptFailedService");
	// while (true)
	// {
	// waitTime = System.currentTimeMillis();
	// try
	// {
	// file = new SmbFile(statusURL);
	//
	// if (file.isDirectory())
	// {
	// for (SmbFile statusFile : file.listFiles())
	// {
	// if (statusFile.isDirectory())
	// {
	// continue;
	// }
	// importFile(statusFile);
	// }
	// waitTime = System.currentTimeMillis() - waitTime;
	// if (sleepTime > waitTime)
	// {
	// Thread.sleep(sleepTime - waitTime);
	// }
	// } else
	// {
	// error = "Status Directory is Wrong!";
	// }
	// } catch (MalformedURLException e)
	// {
	// error = "StatusURL is not readable!";
	// } catch (SmbException e)
	// {
	// error = "Method Exception:isDirectory()!";
	// } catch (InterruptedException e)
	// {
	// error = "Thread is interrupted!";
	// }
	// if (!error.equals(""))
	// {
	// logger.error(error);
	// error = "";
	// try
	// {
	// Thread.sleep(sleepTime);
	// } catch (InterruptedException e)
	// {
	// logger.error("Thread is interrupted!");
	// }
	// }
	// }
	// }
	//
	// private void importFile(SmbFile statusFile)
	// {
	// // check Exist
	// criteria = new HashMap<String, Object>();
	// criteria.put("EQ_fileName", statusFile.getName());
	// criteria.put("EQ_fileSize", statusFile.getContentLength());
	// criteria.put("EQ_fileDate", new Date(statusFile.getLastModified()));
	// fileImptList = fileImptService.findAll(criteria);
	// if (fileImptList == null || fileImptList.size() < 1)
	// {
	// // delete All fileImpt
	// // delete All imptFailed
	// try
	// {
	// reader = new BufferedReader(new InputStreamReader(new
	// SmbFileInputStream(statusFile), "gbk"));
	// success = 0;
	// failed = 0;
	// while ((line = reader.readLine()) != null)
	// {
	// if (line.length() < 1)
	// {
	// continue;
	// }
	// para = line.split(tab);
	// if (para.length < 6)
	// {
	// failed++;
	// imptFailed = new ImptFailed();
	// imptFailed.setFileName(statusFile.getName());
	// imptFailed.setLine(line);
	// imptFailed.setFileType(8);
	// imptFailed.setErrorInfo("not enough columns");
	// imptFailedService.save(imptFailed);
	// continue;
	// }
	// if ("".equals(para[0])||"".equals(para[1]))
	// {
	// failed++;
	// imptFailed = new ImptFailed();
	// imptFailed.setFileName(statusFile.getName());
	// imptFailed.setLine(line);
	// imptFailed.setFileType(8);
	// imptFailed.setErrorInfo("status key is empty");
	// imptFailedService.save(imptFailed);
	// continue;
	// }
	// status = new Status();
	// status.setWorkOrder(Global.removeZeroBefore(para[0]));
	// status.setOperation(para[1]);
	// status.setLine(line);
	// status.setImptFilename(statusFile.getName());
	// tempStatus=statusService.findLast(status.getWorkOrder(),status.getOperation());
	// if(tempStatus!=null && line.equals(tempStatus.getLine())){
	// status=tempStatus;
	// }else{
	// status=statusService.save(status,para);
	// }
	// if (status.getId() == null)
	// {
	// failed++;
	// imptFailed = new ImptFailed();
	// imptFailed.setFileName(statusFile.getName());
	// imptFailed.setLine(line);
	// imptFailed.setFileType(8);
	// imptFailed.setErrorInfo("insert failed");
	// imptFailedService.save(imptFailed);
	// } else
	// {
	// success++;
	// }
	// }
	// fileImpt = new FileImpt();
	// fileImpt.setFileName(statusFile.getName());
	// fileImpt.setFileSize(statusFile.getContentLengthLong());
	// fileImpt.setFileDate(new Timestamp(new
	// Date(statusFile.getLastModified()).getTime()));
	// fileImpt.setFileType(8);
	// fileImpt.setIsImpt(1);
	// fileImpt.setImptTime(new Timestamp(new Date().getTime()));
	// fileImpt.setSuccess(success);
	// fileImpt.setFailed(failed);
	// fileImptService.save(fileImpt);
	// moveToProceed(statusFile);
	// } catch (IOException e)
	// {
	// logger.error(statusFile.getName() + ":I/O Exception");
	// saveImptError(statusFile.getName(),8,"i/o error!");
	// moveToError(statusFile);
	// }
	// } else
	// {
	// moveToProceed(statusFile);
	// }
	// }
	//
	// private void moveToError(SmbFile statusFile)
	// {
	// try
	// {
	// statusFile.copyTo(new SmbFile(statusErrorURL + statusFile.getName()));
	// statusFile.delete();
	// } catch (SmbException | MalformedURLException e)
	// {
	// logger.error(statusFile.getName() +
	// ":Failed to move file to error Directory");
	// }
	// }
	//
	// private void moveToProceed(SmbFile statusFile)
	// {
	// try
	// {
	// statusFile.copyTo(new SmbFile(statusProceedURL + statusFile.getName()));
	// statusFile.delete();
	// } catch (SmbException | MalformedURLException e)
	// {
	// logger.error(statusFile.getName() +
	// ":Failed to move file to proceed Directory");
	// }
	// }
	//
	// private void saveImptError(String fileName, int fileType, String error)
	// {
	// ImptError imptError=new ImptError();
	// imptError.setFileName(fileName);
	// imptError.setFileType(fileType);
	// imptError.setImptTime(new Timestamp(System.currentTimeMillis()));
	// imptError.setErrorInfo(error);
	// imptErrorService.save(imptError);
	// }
}
