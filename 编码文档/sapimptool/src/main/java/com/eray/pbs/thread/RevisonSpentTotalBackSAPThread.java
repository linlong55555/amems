package com.eray.pbs.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.SpentHrsTotalBackSAPLog;
import com.eray.pbs.service.SpentHrsTotalBackSAPLogService;
import com.eray.util.format.FormatUtil;
import com.eray.util.framework.SpringContextHolder;

/**
 * 回传
 * @author ganqing
 *
 */
public class RevisonSpentTotalBackSAPThread implements Runnable {

private static final Logger logger = LoggerFactory.getLogger(RevisonSpentTotalBackSAPThread.class);
	
	private String workTotalSpentURL = "/worktotalspent/"; //存放生成文件的地方
	private String workTotalSpentBackURL = "/worktotalspent/back/"; //存放备份文件的地方
	private String uploadPath = "/zinterface/barcode/in/wkc";
	private String pbsDataPath;
	
	private Long sleepTime = 1800000L;  //毫秒 = 30分钟 = 30*60*1000
	private Long waitTime;
	private FTPClient ftp;  
	
	private SpentHrsTotalBackSAPLogService spentHrsTotalBackSAPLogService;
	@Override
	public void run(){
		Properties prop = new Properties() ;
		try { 
			InputStream inputFile = new FileInputStream("C:/config.properties"); 
			prop.load(inputFile); 
			inputFile.close(); 
		} catch (Exception ex){ 
			System.out.println("读取属性文件--->失败！"); 
		}
		pbsDataPath = prop.getProperty("pbsDataPath");
		spentHrsTotalBackSAPLogService = SpringContextHolder.getBean("spentHrsTotalBackSAPLogService");
		while (true) {
			waitTime = new Date().getTime();
			try {
				//创建一个revision测试文件,上线请屏蔽
				//this.writeFileTest();
				//boolean status = this.connect(uploadPath, "10.7.0.221", 21, "ftppb", "Bsas@014");
				boolean status = this.connect(uploadPath, "10.7.0.242", 21, "ftpbar1", "Dre@ml1n#r"); //test
				if (status) {
					logger.info("connet ftp,check files if there has file need to upload.");
					File[] otherFiles = this.ftpFiles();
					if (otherFiles != null && otherFiles.length > 0) {
						for (File f : otherFiles) {
							logger.info("file " + f.getName() + " upload to ftp.");
							this.uploadFileToFtp(f);
							this.moveFile(f);
						}
					} else { //检查是否是每个月的开始
						if (this.helpPbsSchedule()) {
							otherFiles = this.ftpFiles();
							if (otherFiles != null && otherFiles.length > 0) {
								for (File f : otherFiles) {
									logger.info("file " + f.getName() + " upload to ftp.");
									this.uploadFileToFtp(f);
									this.moveFile(f);
								}
							}
						}
					}
					this.closeServer(); //关闭ftp服务
				}
			} catch (Exception e) {
				logger.error("upload to ftp fail,the reason:" + e);
				e.printStackTrace();
			} 
			//不管成功与否，休眠一段时间后重试
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
	 * 协助pbs程序共同完成数据的传输,以防pbs程序失效
	 */
	private boolean  helpPbsSchedule() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int date_index = cal.get(Calendar.DATE); 
		if (date_index == 1) { //检查是否是每个月的一号
			int hour_j = cal.get(Calendar.HOUR_OF_DAY);
			if (hour_j <=7 && hour_j >6) { //每个月的1号6-7点之间回传数据
				String fileName = "ZSPT" + new SimpleDateFormat("yyyyMMddHHmmssms").format(new Date()) + ".TXT";
				File f = new File(pbsDataPath + workTotalSpentURL + fileName);
				List<SpentHrsTotalBackSAPLog>  lists = spentHrsTotalBackSAPLogService.backDatasToSAP();
				try {
					this.writeFile(lists, f);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	private void writeFile(List<SpentHrsTotalBackSAPLog>  lists,File f) throws IOException {
		logger.info("start write file.");
		if (lists != null && lists.size() > 0) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true)));
			StringBuilder sap = new StringBuilder();
			for (SpentHrsTotalBackSAPLog back : lists) {
				logger.info("the Back Data:{}" , back);
				sap.append(back.getSpentMonth()).append("\t").append(FormatUtil.formatRid(back.getRid())).append("\t");
				sap.append(formatSaphrs(back.getBacksaphrs().setScale(2 , BigDecimal.ROUND_HALF_UP).toString())).append("\r\n");//换行符注意
				writer.write(sap.toString());
				sap.delete(0, sap.length()); //清空
			}
			writer.flush();
			writer.close();
		}
		logger.info("end write file end.");
	}
	/**
	 * 将文件移至另外一个文件夹
	 * @param f 文件名
	 */
	private void moveFile(File f) {
		if (f.exists() && !f.isDirectory()) {
			try {
				File backFile = new File(pbsDataPath + workTotalSpentBackURL + f.getName());
				if (!backFile.getParentFile().exists()) {
					backFile.getParentFile().mkdirs();
				}
				backFile.createNewFile();
				FileUtils.copyFile(f, backFile);
				f.delete();
			} catch (IOException e) {
				logger.error("file restore fail：{}", f.getName());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 处理先前上传失败的文件（失败的原因很多：ftp不稳定;app在上传过程中突然关闭）
	 * @return File[]
	 */
	private File[] ftpFiles() {
		File directory = new File(pbsDataPath + workTotalSpentURL);
		logger.info("check files:{}",directory.getAbsoluteFile());
		if (directory.exists() && directory.isDirectory()) {
			File[] fs = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.isFile()) {
						return true;
					}
					return false;
				}
			});
			return fs;
		}
		return null;
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
            ftp.storeFile(file.getName(), input);      
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
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();      
            return result;      
        } 
        this.createDir(path);
        ftp.changeWorkingDirectory(path);   
        result = true;      
        return result;      
    }
    
    /** 
     * 关闭FTP服务器 
     * @throws IOException 
     */  
    public void closeServer() throws IOException {  
        if (ftp != null) {  
            ftp.logout();  
            ftp.disconnect();  
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
			retCode = ftp.cwd(dir);
			return FTPReply.isPositiveCompletion(retCode);  
		} catch (IOException e) {
			return false;
		}  
    } 
    
    /** 
     * 创建FTP多级目录 
     * @param remoteFilePath 
     * @throws IOException 
     */  
    public void createDir(String dir) throws IOException {  
        if (!isDirExist(dir)) {  
            File file = new File(dir);  
            this.createDir(file.getParent());  
            ftp.makeDirectory(dir);  
        }  
    }
    
    /**
	 * 生成回传的文件(test，与波音联调的测试数据)
	 * @param lists 待回传的数据
	 * @param f 生成的文件
	 * @throws IOException 
	 */
	private void writeFileTest() throws IOException {
		List<SpentHrsTotalBackSAPLog> lists = new ArrayList<SpentHrsTotalBackSAPLog>();
		SpentHrsTotalBackSAPLog b = new SpentHrsTotalBackSAPLog();
		b.setSpentMonth("2016-08");
		b.setRid("00003652");
		b.setBacksaphrs(new BigDecimal(3.17).setScale(2, BigDecimal.ROUND_HALF_UP));
		lists.add(b);
		String fileName = "ZSPT" + new SimpleDateFormat("yyyyMMddHHmmssms").format(new Date()) + ".TXT";
		File f = new File(pbsDataPath + workTotalSpentURL + fileName);
		if (lists != null && lists.size() > 0) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			f.createNewFile();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),"UTF-8"));
			StringBuilder sap = new StringBuilder();
			for (SpentHrsTotalBackSAPLog back : lists) {
				sap.append(back.getSpentMonth()).append("\t").append(back.getRid()).append("\t");
				sap.append(formatSaphrs(back.getBacksaphrs().toString())).append("\r\n");//换行符注意
				writer.write(sap.toString());
				sap.setLength(0); //清空
			}
			writer.flush();
			writer.close();
		}
	}
	
	/**
	 * 格式化sap回传数据，保证其长度是18位 2016.08.23 GQ
	 * @param saphrs 原始的sap格式
	 * @return 格式化后的asp数据
	 */
	private String formatSaphrs(String saphrs) {
		int len = saphrs.length(); 
		if(len >= 18) {
			return saphrs;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(saphrs);
		int offset = 18 - len;
		for (int i=0; i<offset; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

}
