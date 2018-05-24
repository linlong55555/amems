package com.eray.thjw.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.eray.thjw.baseinfo.po.DirectoryStructure;

/**
 * @Description ZIP解压工具类.
 * @CreateTime 2018-3-29 下午1:45:26
 * @CreateBy 雷伟
 */
public class UnZipUtil {
	
	/**
	 * 解压文件到指定路径
	 *    由于ZipEntry解压有bug,所以必须先生成文件夹,再生成文件
	 * @param filePath
	 * @param upZipPath
	 * @return 返回解压的文件集合
	 */
	@SuppressWarnings("unchecked")
	public static List<DirectoryStructure> unZip(String filePath, String upZipPath) throws IOException, IllegalArgumentException{
		List<DirectoryStructure> directoryList = new ArrayList<DirectoryStructure>();
		
		File inputFile = new File(filePath);
		File targetDirectory = new File(upZipPath);

		ZipFile zipFile = new ZipFile(inputFile,"GBK");
		Enumeration<ZipEntry> entries = zipFile.getEntries();
		
		//先生成,目录
		while(entries.hasMoreElements()) {
			ZipEntry zipEntry = entries.nextElement();
			if(zipEntry.isDirectory()) {
				String nbwjm = UUID.randomUUID().toString();
				/*File dir = new File(targetDirectory.getAbsolutePath() + File.separator + nbwjm);
				dir.mkdirs();*/
				createDirectoryEntry(zipEntry.getName(),"wjj",directoryList,nbwjm);
			}
		}
		
		//再生成,文件
		entries = zipFile.getEntries();
		while(entries.hasMoreElements()) {
			ZipEntry zipEntry = entries.nextElement();
			if(!zipEntry.isDirectory()) {
				File dir = new File(targetDirectory.getAbsolutePath() + File.separator);
				if(!dir.exists()){
					dir.mkdirs();
				}
				String nbwjm = UUID.randomUUID().toString();
				File file = new File(targetDirectory.getAbsolutePath() + File.separator + nbwjm);
				file.createNewFile();
				InputStream input = zipFile.getInputStream(zipEntry);
				OutputStream output = new FileOutputStream(file);
				int temp;
				while(-1 != (temp = input.read())){
					output.write(temp);
				}
				input.close();
				output.close();
				createDirectoryEntry(zipEntry.getName(),"wj",directoryList,nbwjm);
			}
		}
		
		zipFile.close();
		
		return directoryList;
	}

	/**
	 * @Description 创建目录实体
	 * @CreateTime 2018-3-29 上午10:47:13
	 * @CreateBy 雷伟
	 * @param path 目录路径
	 * @param type 类型 wjj：文件夹  wj：文件
	 * @param directoryList 所有目录实体
	 */
	private static void createDirectoryEntry(String path, String type,List<DirectoryStructure> directoryList, String nbwjm) {
		DirectoryStructure directoryEntry = new DirectoryStructure();
		directoryEntry.setId(UUID.randomUUID().toString());
		directoryEntry.setPath(path);
		directoryEntry.setType(type);
		directoryEntry.setNbwjm(nbwjm);
		directoryList.add(directoryEntry);
	}

	/**
	 * @Description 获取目录结构
	 * @CreateTime 2018-3-29 下午1:39:41
	 * @CreateBy 雷伟
	 * @param filePath 文件路径
	 * @param upZipPath 解压路径
	 * @param saveMlId 存放的目录ID
	 * @return
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static List<DirectoryStructure> getDirectoryStructure(String filePath,String upZipPath, String saveMlId)
			throws IllegalArgumentException, IOException{
		List<DirectoryStructure> directoryList = UnZipUtil.unZip(filePath,upZipPath); //1.解压文件
		
		Map<String,DirectoryStructure> entryMap = new HashMap<String,DirectoryStructure>();
		for (DirectoryStructure directoryEntry : directoryList) {
			String parentPath = "";
			String selfName = "";
			String[] arr = directoryEntry.getPath().split("/");
			for (int i = 0; null != arr && i < arr.length; i++) {
				selfName = arr[i];
				if((arr.length-1) == i){
					directoryEntry.setName(selfName);
				}else{
					parentPath += selfName+"/";
				}
			}
			if(parentPath.length() > 0){
				directoryEntry.setParentPath(parentPath);
			}
			entryMap.put(directoryEntry.getPath(),directoryEntry);
		}
		
		for (DirectoryStructure directoryEntry : directoryList) {
			String parentId = entryMap.get(directoryEntry.getParentPath())==null?saveMlId:entryMap.get(directoryEntry.getParentPath()).getId();
			directoryEntry.setParentId(parentId);
		}
		
		return directoryList;
	}
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
    @SuppressWarnings("unused")
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	
	public static void main(String[] args) throws Exception {
		//1.存放的目录ID
		String saveMlId = "0"; 
		//2.目录结构
		List<DirectoryStructure> dirStructures = UnZipUtil.getDirectoryStructure("C:\\thjw_upload\\20180329\\doczip\\123.zip","C:\\thjw_upload\\zip\\",saveMlId);
		
		for (DirectoryStructure directoryEntry : dirStructures) {
			System.out.println(directoryEntry);
		}
	}
}