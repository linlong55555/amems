package com.eray.thjw.control;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 报表控制器
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

	/**
	 * 
	 * @param type pdf、xls、html
	 * @param dprtcode
	 * @param reportKey
	 * @param model
	 * @return
	 */
	@RequestMapping("/{type}/{dprtcode}/{reportKey}")
	public String print(@PathVariable String type, @PathVariable String dprtcode, @PathVariable String reportKey, Model model){
		 // 报表数据源  
//	    JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());  
//	    model.addAttribute("jrMainDataSource", jrDataSource);  
		
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
	
}
