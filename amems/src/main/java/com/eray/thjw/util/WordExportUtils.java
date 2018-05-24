package com.eray.thjw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/** 
 * @Description work文档导出工具类
 * @CreateTime 2018-3-27 下午4:05:10
 * @CreateBy 刘兵	
 */
public class WordExportUtils {

	//配置信息,代码本身写的还是很可读的,就不过多注解了  
	private static Configuration configuration = null;  
	//这里注意的是利用WordExportUtils的类加载器动态获得模板文件的位置  
	private static final String path = WordExportUtils.class.getClassLoader().getResource("/").getPath();
	static {
		StringBuffer templateFolder = new StringBuffer();
		templateFolder.append(path.substring(1, path.indexOf("classes"))).append("templet");//File.separator;
		configuration = new Configuration();  
		configuration.setDefaultEncoding("utf-8");  
		try {  
			configuration.setDirectoryForTemplateLoading(new File(templateFolder.toString()));  
		} catch (IOException e) { 
			throw new RuntimeException("加载模板文件失败!");  
		}  
	}  
	   
	/**
	 * @Description 导出work文档
	 * @CreateTime 2018-3-27 下午6:11:26
	 * @CreateBy 刘兵
	 * @param response 
	 * @param map 数据
	 * @param fileTitle 导出后生成的文件名
	 * @param templetFile 模本文件名
	 * @throws IOException
	 */
	public static void exportWord(HttpServletResponse response, Map<?, ?> map, String fileTitle , String templetFile) throws IOException {  
		Template freemarkerTemplate = configuration.getTemplate(new StringBuffer().append(templetFile).append(".xml").toString());  
		File file = null;  
		InputStream fin = null;  
		ServletOutputStream out = null;  
		try {  
			//调用工具类的createDoc方法生成Word文档  
            file = createDoc(map, freemarkerTemplate, templetFile);  
            fin = new FileInputStream(file);  
            response.setCharacterEncoding("utf-8");  
            response.setContentType("application/msword");  
            //设置浏览器以下载的方式处理该文件名  
            response.setHeader("Content-Disposition", "attachment;filename="  
        		.concat(new String(fileTitle.concat(".doc").getBytes("GBK"), "iso8859-1")));  
            out = response.getOutputStream();  
            byte[] buffer = new byte[512];// 缓冲区  
            int bytesToRead = -1;  
            //通过循环将读入的Word文件的内容输出到浏览器中  
            while((bytesToRead = fin.read(buffer)) != -1) {  
            	out.write(buffer, 0, bytesToRead);  
            }  
		} finally {  
			if(fin != null) fin.close();  
			if(out != null) out.close();  
			if(file != null) file.delete(); // 删除临时文件  
		}  
	}  
   
	/**
	 * @Description 生成Word文档  
	 * @CreateTime 2018-3-27 下午6:07:44
	 * @CreateBy 刘兵
	 * @param dataMap 数据
	 * @param template 模板
	 * @param fileName 文件名
	 * @return File文件
	 */
	private static File createDoc(Map<?, ?> dataMap, Template template, String fileName) {  
		File f = new File(new StringBuffer().append(fileName).append(".doc").toString());  
		Template t = template;  
		try {  
			// 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
			Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
			t.process(dataMap, w);  
			w.close();  
		} catch (Exception ex) {  
			ex.printStackTrace();  
			throw new RuntimeException(ex);  
		}  
		return f;  
	}  
}
