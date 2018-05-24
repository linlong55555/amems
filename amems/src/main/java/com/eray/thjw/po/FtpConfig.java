package com.eray.thjw.po;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpConfig {
	 private String ftpUrl;  
	 
	 private int ftpPort;  
	 
	 private String ftpUsername; 
	 
	 private String ftpPassword; 
	 
	 public String getFtpUrl() {
			return ftpUrl;
		}

		 @Value("${ftp_url}")  
		public void setFtpUrl(String ftpUrl) {
			this.ftpUrl = ftpUrl;
		}

		public String getFtpUsername() {
			return ftpUsername!=null?ftpUsername.trim():null;
		}

		 @Value("${ftp_username}")
		public void setFtpUsername(String ftpUsername) {
			this.ftpUsername = ftpUsername;
		}

		public String getFtpPassword() {
			return ftpPassword!=null?ftpPassword.trim():null;
		}

		@Value("${ftp_password}") 
		public void setFtpPassword(String ftpPassword) {
			this.ftpPassword = ftpPassword;
		}

		public int getFtpPort() {
			return ftpPort;
		}
		
		@Value("${ftp_port}") 
		public void setFtpPort(int ftpPort) {
			this.ftpPort = ftpPort;
		}

   
  
}