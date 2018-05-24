package ftp;

import java.io.File;
import java.io.IOException;

import com.eray.thjw.po.FtpConfig;
import com.eray.thjw.util.FTP_Util;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FTPTest {
	public static FtpConfig getFtpConfig() {
		FtpConfig ftpconfig = new FtpConfig();
		ftpconfig.setFtpUrl("127.0.0.1");
		ftpconfig.setFtpPort(21);
		ftpconfig.setFtpUsername("hchu");
		ftpconfig.setFtpPassword("hchu");
		return ftpconfig;
	}
	
	public static void main(String[] args) {
		FtpClient ftpClient = null;
		try {
			ftpClient = FTP_Util.connectFTP(FTPTest.getFtpConfig());
			ftpClient.makeDirectory("20180410");
			//FTP_Util.upload("C:\\Users\\hchu\\Desktop\\F-EN-012技术支援申请单模板.docx", "/20180410/01/F-EN-012技术支援申请单模板.docx", ftpClient);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		}
	}
}
