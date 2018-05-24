package com.eray.pbs.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpTest {
	private static FTPClient ftp; 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*FtpTest test = new FtpTest();
		OutputStream is = null;
		File tmpFile = null;
		try {
			boolean status = test.connect("/zinterface/barcode/out/wkc", "10.7.0.242", 21, "ftpbar1", "Dre@ml1n#r");
			if (status) {
				FTPFile[]  files = test.ftp.listFiles();
				//System.out.println("==");
				if (files.length >0) {
					for (int i=0;i<files.length;i++) {
						if (files[i].getName().startsWith("ZWVA")) {
							System.out.println(files[i].getName());
							tmpFile = new File("E:\\pbdata\\" + files[i].getName());
							is = new FileOutputStream(tmpFile);
							ftp.retrieveFile(files[i].getName(), is);
						}
					}
				}
				is.close();
				ftp.disconnect();
				//System.out.println("=xx=");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} //test
*/
		String fileName = "ZSPT" + new SimpleDateFormat("yyyyMMddHHmmssms").format(new Date()) + ".TXT";
		System.out.println(fileName);
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

}
