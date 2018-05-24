package com.eray.thjw.util;
/**
 * @author Meizhiliang
 * @description 对文件（文件夹的操作）
 * @develop date 2016-7-27
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

import com.eray.thjw.common.GlobalConstants;

public class File_Util {
	
	/**
	 * @description 删除文件夹
	 * @param folderPath
	 * @throws Exception
	 */
	 public static void delFolder(String folderPath)throws Exception {  
        delAllFile(folderPath);                                    // 删除完里面所有内容  
        String filePath = folderPath;  
        filePath = filePath.toString();  
        File  myFilePath = new File(filePath);  
        myFilePath.delete();                                      // 删除空文件夹  
    }  
	 /**
		 * @description 删除指定文件夹下面所有的内容
		 * @param folderPath   文件夹的据对路径
		 * @throws Exception
		 */
	 public static boolean delAllFile(String path)throws Exception {  
	        boolean flag = false;  
	        File file = new File(path);  
	        if (!file.exists()) {  
	            return flag;  
	        }  
	        if (!file.isDirectory()) {  
	            return flag;  
	        }  
	        String[] tempList = file.list();  
	        File temp = null;  
	        if(tempList!=null){
	        	for (int i = 0; i < tempList.length; i++) {  
	        		if (path.endsWith(File.separator)) {  
	        			temp = new File(path + tempList[i]);  
	        		} else {  
	        			temp = new File(path + File.separator + tempList[i]);  
	        		}  
	        		if (temp.isFile()) {  
	        			temp.delete();  
	        		}  
	        		if (temp.isDirectory()) {  
	        			delAllFile(path + "/" + tempList[i]);              // 先删除文件夹里面的文件  
	        			delFolder(path + "/" + tempList[i]);             // 再删除空文件夹  
	        			flag = true;  
	        		}  
	        	}  
	        }
	        return flag;  
	    }  
	 
	   /**
	    * @description  新建目录或文件夹
	    * @param path
	    * @return boolean
	    */
	    public static boolean newDir(String path) throws Exception {  
	        File file = new File(path);  
	        return file.mkdirs();
	    }  
	  
	   /**
	    * @description 更新目录或文件夹(重命名)
	    * @param path
	    * @param newPath
	    * @return boolean
	    */
	    public static boolean updateDir(String path, String newPath) throws Exception {  
	        File file = new File(path);  
	        File newFile = new File(newPath);  
	        return file.renameTo(newFile);  
	    }  
	    /**
	     * @description 创建多级目录
	     * @param path 目录绝对路径
	     */
	    public void createMultilevelDir(String path) {  
	        try {  
	            StringTokenizer st = new StringTokenizer(path, "/");  
	            String path1 = st.nextToken() + "/";  
	            String path2 = path1;  
	            while (st.hasMoreTokens()) {  
	                path1 = st.nextToken() + "/";  
	                path2 += path1;  
	                File inbox = new File(path2);  
	                if (!inbox.exists())  
	                    inbox.mkdir();  
	            }  
	        } catch (Exception e) {  
	            System.out.println("目录创建失败" + e);  
	            e.printStackTrace();  
	        }  
	    }  
	    /**
	     * @description 获取某个文件的后缀名
	     * @param fileName
	     * @return String
	     */
	    public String getFileSuffix(String fileName) throws Exception {  
	        return fileName.substring(fileName.lastIndexOf(".") + 1,  
	                fileName.length());  
	    }  
	    /**
	     * @description copy文件
	     * @param oldPath  源文件路径
	     * @param newPath 复制文件路径
	     */
	    public void copyFile(String oldPath, String newPath){
	    	File oldFile = null;
	    	FileInputStream fis= null;
	    	FileOutputStream fos =null;
	    	try{
	    		oldFile = new File(oldPath);
	    		if(oldFile.exists()){
	    			fis = new FileInputStream(oldPath);
	    			fos = new FileOutputStream(newPath);
	    			byte[] buf = new byte[1024 * 2];  
	    	        int num = 0;  
	    	        while ((num = fis.read(buf)) != -1) {  
	    	            fos.write(buf, 0, num);  
	    	        }  
	    		}else{
	    			System.out.println("要复制的文件不存在");
	    		}
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	    }
	  
	public static String getExtensionName(String fileName) {
		String prefix = null;
		if (null != fileName && !fileName.isEmpty()) {
			int dot = fileName.lastIndexOf('.');
			if ((dot >-1) && (dot < (fileName.length()))) { 
				prefix = fileName.substring(dot+1); 
            }
		}
		return prefix != null ? prefix : "";
	}
	
	/**
	 * @Description 移除后缀名
	 * @CreateTime 2017-8-22 下午2:48:07
	 * @CreateBy 刘兵
	 * @param name 文件名
	 * @return String 移除后缀的文件名
	 */
	public static String removeSuffix(String name){
	   if(name != null && name.indexOf(".") != -1){
		   name = name.substring(0,name.lastIndexOf("."));
	   }
	   return name;
	}
	
	/**
	 * @Description 删除文件
	 * @CreateTime 2018年1月30日 上午11:15:13
	 * @CreateBy 韩武
	 * @param path
	 * @param nbwjm
	 * @return
	 */
    public static boolean delFile(String path, String nbwjm){
    	// 根路径
    	String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
    	// 相对路径
    	String relativePath = File.separator.concat(path + File.separator + nbwjm);
    	// 完全路径
    	String direcory = rootPath.concat(relativePath);
        File file=new File(direcory);
        // 文件存在且文件类型
        if(file.exists() && file.isFile()){
        	file.delete();
        }
        return false;
    }
    
    public static String getFilePath(String filePath){
    	String path = File_Util.class.getResource("/").getPath();
		path = path.substring(1, path.indexOf("WEB-INF"));
    	return new StringBuffer().append(path).append(filePath).toString();
    }
    
    //获得图片的base64码
    public static String getImageBase(String path) {
  		if(StringUtils.isBlank(path)){
  			return "";
  		}
  		File file = new File(path);
  		if(!file.exists()) {
  			return "";
  		}
  		InputStream in = null;
  		byte[] data = null;  
  		try {
           in = new FileInputStream(file);
  		} catch (FileNotFoundException e1) {
           e1.printStackTrace();
  		}
  		try {  
           data = new byte[in.available()];  
           in.read(data);  
           in.close();  
  		} catch (IOException e) {  
  			e.printStackTrace();  
  		} 
  		BASE64Encoder encoder = new BASE64Encoder();
  		return encoder.encode(data);
  	}
    
}
