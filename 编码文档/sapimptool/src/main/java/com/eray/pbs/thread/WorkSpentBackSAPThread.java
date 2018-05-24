package com.eray.pbs.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.service.ActivityTypeReWorkCenterService;
import com.eray.pbs.vo.WorkSpentBackSAP;
import com.eray.util.format.FormatUtil;
import com.eray.util.framework.SpringContextHolder;

/**
 * 返回数据至SAP线程（该线程只负责将文件上传至ftp，同时更新Activity文件,生成文件的事情交由pbs系统来做）
 * @author ganqing
 *
 */
public class WorkSpentBackSAPThread implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkSpentBackSAPThread.class);
	
	private String workSpentURL = "/workspentEveryDay/";
	private String workSpentBackURL = "/workspentEveryDay/back/"; 
	private String uploadPath = "/zinterface/barcode/in/wkc/"; //向ftp上传文件
	private String downloadPath = "/zinterface/barcode/out/wkc/"; //从ftp下载activity type的接口文件
	private String downloadBackPath ="/zinterface/barcode/out/wkc/processed/"; //将读取完成的接口文件拷贝至另外一个文件夹
	private String activityURL = "/activity/"; //下载activity type的接口文件至本地
	private String activityBackURL = "/activity/back/"; //下载activity type的接口文件解析后保存至指定目录
	private String pbsDataPath;
	
	private Long sleepTime = 3000000L;  //毫秒 = 50分钟 = 50*60*1000
	//private Date handleDate;
	private Long waitTime;
	//private String fileName; //上传至ftp的文件名
	private FTPClient ftpClient;  
	private FTPFile[] fs; //FTP文件目录
	private File tmpFile; //临时文件
	private OutputStream is = null;
	@Override
	public void run(){
		//handleDate = new Date();
		Properties prop = new Properties() ;
		try { 
			InputStream inputFile = new FileInputStream("C:/config.properties"); 
			prop.load(inputFile); 
			inputFile.close(); 
		} catch (Exception ex){ 
			System.out.println("reade config.properties fail！"); 
		}
		pbsDataPath = prop.getProperty("pbsDataPath");
	//	WorkSpentBackService backService =  SpringContextHolder.getBean("workSpentBackService");
		ActivityTypeReWorkCenterService activityService = SpringContextHolder.getBean("activityTypeReWorkCenterService");
		while (true) {
			Long count = activityService.getCueerntActivityCount();
			if(count < 1){
				this.downLoadFilesFromFtp(); //从ftp下载activity type接口文件
				activityService.saveActivity(pbsDataPath, activityURL, activityBackURL); //解析文件并保存至DB
			}
			//fileName = "ZITC" + new SimpleDateFormat("yyyyMMddHHmmssms").format(new Date()) + ".TXT";
			//List<WorkSpentBackSAP> lists = backService.getDatasBackSAP(); //返回至SAP的值
			//List<WorkSpentBackSAP> lists = backService.testFtpDatas(); //测试数据,正式环境该字段注释掉
			waitTime = new Date().getTime();
		//	try {
		//		this.writeFile(lists);
		//	} catch (IOException e1) {
		//		logger.error("write file error:{}" , e1);
		//		e1.printStackTrace();
		//	} //生成文件
			try {
				logger.info("check exist files");
				File[] otherFiles = this.getCopyFiles(); //文件由pbs端定时产生
				if (otherFiles != null && otherFiles.length > 0) { //上传文件夹下的所有文件
					logger.info("the file count:{}", otherFiles.length);
					boolean status = this.connect(uploadPath, "10.7.0.221", 21, "ftppb", "Bsas@014"); //正式环境
				    //boolean status = this.connect(uploadPath, "10.7.0.242", 21, "ftpbar1", "Dre@ml1n#r"); //test
				    if (status) {
				    	Integer fileCount = 0;
						for (File file : otherFiles) {
							this.uploadFileToFtp(file);
							this.moveFile(file);
							fileCount ++ ;
						}
						//文件删除
						for (File file : otherFiles) {
							this.deleteFile(file);
						}
						logger.info("Total file:{},Handle file:{}",new Object[]{otherFiles.length, fileCount});
					}
					this.closeServer(); //关闭ftp服务
				}
			} catch (Exception e) {
				logger.error("upload to ftp fail:{}" , e);
				e.printStackTrace();
			} 
			//不管成功与否，休眠一段时间后，再重试
			waitTime = new Date().getTime() - waitTime;
			if (sleepTime > waitTime) {
				try {
					Thread.sleep(sleepTime - waitTime);
				} catch (InterruptedException e) {
					logger.error("spent Thread is interrupted!");
				}
			}
		}
	}
	/**
	 * 将文件移至另外一个文件夹
	 * @param f 文件名
	 * @throws IOException 
	 */
	private void moveFile(File f) throws IOException {
		if (f.exists() && !f.isDirectory()) {
				String dateStr = FormatUtil.formatDate(new Date(), "MMdd");
				String year = FormatUtil.formatDate(new Date(), "yyyy");
				File backFile = new File(pbsDataPath + workSpentBackURL + year + File.separator + dateStr + File.separator +f.getName());
				if (!backFile.getParentFile().exists()) {
					backFile.getParentFile().mkdirs();
				}
				backFile.createNewFile();
				//FileUtils.copyFile(f, backFile);
				this.copyFile(f, backFile);
				logger.info("file {} have remove away.", f.getName());
				f.delete();
			}
	}
	/**
	 * 文件拷贝
	 * @param srcFile 源文件
	 * @param destFile 目标文件
	 * @throws IOException 
	 */
	private void copyFile(File srcFile, File destFile) throws IOException {
		FileInputStream input = new FileInputStream(srcFile);
		FileOutputStream output = new FileOutputStream(destFile);
		byte[] buffer = new byte[2048];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
		    output.write(buffer, 0, n);
		    output.flush();
		}
		input.close();
		output.close();
	}
	/**
	 * 删除文件
	 * @param f 要删除的文件
	 */
	private void deleteFile(File f) {
		if (f.exists()) {
			f.delete();
		}
	}
	
	/**
	 * 获得文件夹下所有待上传的文件
	 * @return File[]
	 */
	private File[] getCopyFiles() {
		File directory = new File(pbsDataPath + workSpentURL);
		if (directory.exists() && directory.isDirectory()) {
			File[] fs = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) {
						return false;
					}
					return true;
				}
			});
			return fs;
		}
		return null;
	}
	
	/**
	 * 生成回传的文件(一条记录对应一个文件) 2016.08.19
	 * @param lists 待回传的数据
	 * @param f 生成的文件
	 * @throws IOException 
	 */
	private void writeFile(List<WorkSpentBackSAP> lists) throws IOException {
		if (lists != null && lists.size() > 0) { //有数据才生成文件
			BufferedWriter writer = null;
			StringBuilder sap = new StringBuilder();
			for (WorkSpentBackSAP back : lists) {
				String fileNames = "ZITC" + new SimpleDateFormat("yyyyMMddHHmmssms").format(new Date()) + ".TXT";
				File f = new File(pbsDataPath + workSpentURL + fileNames);
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				f.createNewFile();
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),"UTF-8"));
				//----------------添加头部分,这部分内容必须要 2016.08.23----------
				sap.append(this.fastenTestContent());
				//----------------
				
				sap.append(back.getWid()).append("\t").append(back.getOperNum()).append("\t");
				sap.append(back.getReservedField()== null ? " " : back.getReservedField()).append("\t"); //无实际值，站位
				sap.append(back.getWorkCenter()).append("\t").append(back.getWorkHour()).append("\t");
				sap.append(back.getHourUnit()).append("\t").append(back.getBookOnDate()).append("\t");
				sap.append(back.getBookOnTime()).append("\t").append(back.getBookOffDate()).append("\t");
				sap.append(back.getBookOffTime()).append("\t").append(back.getEmployeeid()).append("\t");
				sap.append(back.getActivityType()).append("\t").append(back.getLastUnknownWord()== null ? " " : back.getLastUnknownWord());//append("\r\n");//换行符注意
				writer.write(sap.toString());
				writer.flush();
				logger.info("file {} have product.", f.getName());
				sap.setLength(0); //清空
				try {
					Thread.sleep(1000); //暂停1000毫秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			writer.close();
		} else {
			logger.info("there is no data in DB");
		}
	}
	/**
	 * 回传工单部分SAP固定的抬头内容,测试环境内容
	 * @return string
	 */
	private String fastenTestContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("SAPECQ").append("\t");
		sb.append("ECQ_888_EP").append("\t");
		sb.append("LS").append("\t");
		sb.append("888ECQCLNT").append("\t");
		sb.append("888ECQCLNT").append("\t");
		sb.append("LS").append("\t");
		sb.append("ZMROCONFCREATE").append("\r\n"); //换行
		return sb.toString();
	}
	/**
	 * 回传工单部分SAP固定的抬头内容，正式环境使用
	 * @return
	 */
	private String fastenOfficialContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("SAPECQ").append("\t");
		sb.append("ECQ_888_EP").append("\t");
		sb.append("LS").append("\t");
		sb.append("888ECQCLNT").append("\t");
		sb.append("888ECQCLNT").append("\t");
		sb.append("LS").append("\t");
		sb.append("ZMROCONFCREATE").append("\r\n"); //换行
		return sb.toString();
	}
	
	/**
	 * 将文件上传至FTP
	 * @param f 需要上传的文件
	 * @throws Exception
	 */
	private void uploadFileToFtp(File f) throws Exception {
		if (f.exists() && !f.isDirectory()) {
			File file = new File(f.getPath());      
			FileInputStream input = new FileInputStream(file);      
            ftpClient.storeFile(file.getName(), input);  
            logger.info("file {} have to FTP", file.getName());
            input.close();
            input = null;
            System.gc();
		}
	}
	
	
	 /**  
     *   
     * @param path 上传到ftp服务器哪个路径下     
     * @param addr 地址  
     * @param port 端口号  
     * @param username 用户名  
     * @param password 密码  
     * @return  
     * @throws Exception  
     */    
    private  boolean connect(String path,String addr,int port,String username,String password) throws Exception {      
        boolean result = false;      
        ftpClient = new FTPClient();      
        int reply;      
        ftpClient.connect(addr,port);      
        ftpClient.login(username,password);      
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftpClient.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftpClient.disconnect();      
            return result;      
        } 
        this.createDir(path);
        ftpClient.changeWorkingDirectory(path);   
        //System.out.println("当前工作目录：" + ftp.printWorkingDirectory());
        result = true;      
        return result;      
    }
    
    /** 
     * 关闭FTP服务器 
     * @throws IOException 
     */  
    public void closeServer() throws IOException {  
        if (ftpClient != null) {  
            ftpClient.logout();  
            ftpClient.disconnect();  
         }  
    }  
    
    /** 
     * 是否存在FTP目录 
     * @param dir 
     * @param ftpClient 
     * @return 
     * @throws IOException 
     */  
    public boolean isDirExist(String dir) {  
         int retCode;
		try {
			retCode = ftpClient.cwd(dir);
			return FTPReply.isPositiveCompletion(retCode);  
		} catch (IOException e) {
			return false;
		}  
    } 
    
    /** 
     * 创建FTP多级目录 
     * http://blog.csdn.net/haha_mingg/article/details/7991796
     * @param remoteFilePath 
     * @throws IOException 
     */  
    public void createDir(String dir) throws IOException {  
        if (!isDirExist(dir)) {  
            File file = new File(dir);  
            this.createDir(file.getParent());  
            ftpClient.makeDirectory(dir);  
        }  
    }
    
    /**
     * 从ftp下载workcenter与avtivity type配对的文件
     * 并将解析完之后的文件拷贝至processed目录下
     */
    private void downLoadFilesFromFtp() {
    	logger.info("strat download avtivity type form ftp.");
    	try {
			boolean status = this.connect(downloadPath, "10.7.0.242", 21, "ftpbar1", "Dre@ml1n#r");
    		//boolean status = this.connect(uploadPath, "10.7.0.221", 21, "ftppb", "Bsas@014");
			if  (status) {
				fs = ftpClient.listFiles();
		    	logger.info("avtivity type size:{}",fs.length);
				if (fs.length > 0) {
					for (FTPFile file : fs) {
						if(file.isDirectory()){
							continue;
						}
						if(file.getName().startsWith("ZWVA")) {
							logger.info("avtivity type file name:{}",file.getName());
							tmpFile = new File(pbsDataPath + activityURL + file.getName());
							if (!tmpFile.getParentFile().exists()) {
								tmpFile.getParentFile().mkdirs();
							}
							is = new FileOutputStream(tmpFile);
							ftpClient.retrieveFile(file.getName(), is);
							ftpClient.rename(downloadPath + file.getName(), downloadBackPath + file.getName()); //备份文件
						}
					}
					if (is != null) {
						is.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
}
