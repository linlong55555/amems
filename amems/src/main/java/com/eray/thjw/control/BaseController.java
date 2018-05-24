package com.eray.thjw.control;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.util.Utils;

@Component
public  class BaseController {
	
	//日志记录器
	private  final Logger logger = LoggerFactory.getLogger(getClass());
	protected static final String SUCCESS = "success";
	protected static final String MESSAGE = "message";
	protected static final String ATTACHMENT = "attachments";
	 
	/*@SuppressWarnings("serial")
	private Map<String, Object> result = new HashMap<String, Object>(){
		{
			this.put(SUCCESS, Boolean.TRUE);
		}
	};*/
	
	public Map<String, Object> getResult() {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				this.put(SUCCESS, Boolean.TRUE);
			}
		};
		return result;
	}

	/*public void setResult(Map<String, Object> result) {
		this.result = result;
	} */
	
	public Map<String, Object> getErrorMsg(String msg ) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				this.put(SUCCESS, Boolean.TRUE);
			}
		};
		result.put(SUCCESS, Boolean.FALSE);
		result.put(MESSAGE, msg);
		return result;
		
	}
	
	public Map<String, Object> getSuccessMsg(String msg ) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				this.put(SUCCESS, Boolean.TRUE);
			}
		};
		result.put(SUCCESS, Boolean.TRUE);
		result.put(MESSAGE, msg);
		return result;
	}
	
	public Map<String, Object> setResultData(String attr,Object obj) {
		Map<String, Object> result = new HashMap<String, Object>(){
			{
				this.put(SUCCESS, Boolean.TRUE);
			}
		};
		result.put(SUCCESS, Boolean.TRUE);
		result.put(attr, obj);
		return result;
	}

	protected void htmlWrite(HttpServletResponse response, Object json) {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().print(
					Utils.Json.toJson(json, "yyyy-MM-dd HH:mm:ss"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ResponseEntity<byte[]> download(String relativePath,String displayName,
			HttpServletRequest res,HttpServletResponse resp) throws IOException{
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String filePath = rootPath.concat(File.separator).concat(relativePath);
		String dfileName = new String(displayName.getBytes("UTF-8"), "iso8859-1"); 
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		headers.setContentDispositionFormData("attachment", dfileName); 
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)), headers, HttpStatus.CREATED); 
	}
	
		@InitBinder
		public void initBinder(WebDataBinder binder) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    dateFormat.setLenient(false);
		    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		}

		public Logger getLogger() {
			return logger;
		}
		
		/**
	     * 获取客户端IP地址
	     * @param request
	     * @return
	     */
		public String getIpAddr(HttpServletRequest request) {
			String ipAddress = null;
			ipAddress = request.getHeader("X-Forwarded-For");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
				logger.debug("Proxy-Client-IP ".concat(StringUtils.isNotBlank(ipAddress)?ipAddress:""));
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
				logger.debug("WL-Proxy-Client-IP ".concat(StringUtils.isNotBlank(ipAddress)?ipAddress:""));
			}
			 if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
				 ipAddress = request.getHeader("HTTP_CLIENT_IP");   
				 logger.debug("HTTP_CLIENT_IP ".concat(StringUtils.isNotBlank(ipAddress)?ipAddress:""));
	        }   
	        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	        	ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");   
	        	logger.debug("HTTP_X_FORWARDED_FOR ".concat(StringUtils.isNotBlank(ipAddress)?ipAddress:""));
	        }   
		        
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				logger.debug("remoteAddr ".concat(StringUtils.isNotBlank(ipAddress)?ipAddress:""));
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					if(inet!=null){
						ipAddress = inet.getHostAddress();
					}
				}
			}

			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
				logger.debug("多个IP ipAddress ".concat(StringUtils.isNotBlank(ipAddress)?ipAddress:""));											// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
				
			}
			
			ipAddress = "0:0:0:0:0:0:0:1".equals(ipAddress)?"127.0.0.1":ipAddress;
			return ipAddress;
		} 

		public String outReport( String type, String dprtcode, String reportKey, Model model){
			 // 报表数据源  
			String path = getClass().getResource("/").getPath();
			path = path.substring(1, path.indexOf("classes"));
			
			StringBuffer filepath = new StringBuffer();
			filepath.append("/WEB-INF/views/reports/");
			filepath.append(dprtcode);
			filepath.append("/");
			filepath.append(reportKey);
			filepath.append(".jasper");
			
			File file = new File(path.concat(filepath.substring(8).toString()));  
		    if(!file.exists()){
		    	filepath.delete(0, filepath.length());
		    	filepath.append("/WEB-INF/views/reports/common/");
				filepath.append(reportKey);
				filepath.append(".jasper");
		    	model.addAttribute("url", "/WEB-INF/views/reports/common/"+reportKey+".jasper");  
		    }
		    model.addAttribute("url", filepath.toString());  
		    
		    // 动态指定报表模板url  
		    model.addAttribute("format", type); // 报表格式  
		    return "customerReport"; // 对应jasper-defs.xml中的bean id
			
		}
		
		/**
		 * 获取绝对路径
		 * @return
		 */
		public String getProjectPath(){
			String projectPath = getClass().getResource("/").getPath();
			projectPath = projectPath.replaceAll("%20", " ");  
			return projectPath.substring(0, projectPath.indexOf("WEB-INF")+"WEB-INF".length());  
		}

		
}
