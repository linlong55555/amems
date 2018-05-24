package com.eray.thjw.util;

/**
 * @author Meizhiliang
 * @description 使用ftp传输协议 链接本地和服务器 对文件进行上传和下载操作
 * @develop date 2016-7-27
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.eray.thjw.po.FtpConfig;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FTP_Util {
	/**
	 * 连接ftp服务器 JDK 1.7
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @return FtpClient
	 */
	public static FtpClient connectFTP(String url, int port, String username,String password) throws IOException,FtpProtocolException  { 
		FtpClient ftp = null;               
		SocketAddress addr = new InetSocketAddress(url, port);
		ftp = FtpClient.create();
		ftp.connect(addr); 
		ftp.login(username, password.toCharArray());
		ftp.setBinaryType();
		return ftp;
	}
	
	public static FtpClient connectFTP(FtpConfig ftpConfig) throws IOException,FtpProtocolException  { 
		SocketAddress addr = new InetSocketAddress(ftpConfig.getFtpUrl(), ftpConfig.getFtpPort());
		FtpClient ftp = FtpClient.create();
		ftp.connect(addr); 
		ftp.login(ftpConfig.getFtpUsername(), ftpConfig.getFtpPassword().toCharArray());
		ftp.setBinaryType();
		return ftp;
	}

	/**
	 * 切换链接的当前目录
	 * @param ftp
	 * @param path
	 */
	/*public static void changeDirectory(FtpClient ftp, String path) {
		try {
			ftp.changeDirectory(path);
			System.out.println(ftp.getWorkingDirectory());
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 上传文件
	 * @param localFile
	 * @param ftpFile
	 * @param ftp
	 */
	public static void upload(String localFile, String ftpFile, FtpClient ftp) {
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			// 将ftp文件加入输出流中。输出到ftp上
			os = ftp.putFileStream(ftpFile);
			File file = new File(localFile);
			// 创建一个缓冲区
			fis = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = fis.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			System.out.println("***********upload success!!*********");
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void upload(File localFile, String ftpFile, FtpClient ftp) {
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			// 将ftp文件加入输出流中。输出到ftp上
			os = ftp.putFileStream(ftpFile);
			// 创建一个缓冲区
			fis = new FileInputStream(localFile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = fis.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			System.out.println("***********upload success!!*********");
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*//**
	 * 文件下载
	 * @param localFile
	 * @param ftpFile
	 * @param ftp
	 *//*
	public static void download(String localFile, String ftpFile, FtpClient ftp) {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			// 获取ftp上的文件
			is = ftp.getFileStream(ftpFile);
			File file = new File(localFile);
			byte[] bytes = new byte[1024];
			int i;
			fos = new FileOutputStream(file);
			while ((i = is.read(bytes)) != -1) {
				fos.write(bytes, 0, i);
			}
			System.out.println("******download success!!*******");
		} catch (FtpProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	public static void main(String[] args) throws IOException, FtpProtocolException {
		FtpClient cl = FTP_Util.connectFTP("127.0.0.1", 21, "zc", "123456");
		cl.makeDirectory("/2017/dd/");
		FTP_Util.upload(new File("e:\\tools\\[数据结构与算法].王晓东.文字版.pdf"), "/2017/dd/[数据结构与算法].王晓东.文字版.pdf", cl);
		
		
	}
}
