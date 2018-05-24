package com.eray.thjw.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.aware.SpringContextHolder;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Button;
import com.eray.thjw.po.MonitorOptionClass;
import com.eray.thjw.po.User;
import com.eray.thjw.service.ButtonService;
import com.eray.thjw.service.MonitorOptionClassService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.StatusEnum;


//TODO 样例开发

@RequestMapping(value="/demo")
@Controller("demoController2")
public class DemoController extends BaseController{
 
	
	@Autowired
	private MonitorOptionClassService monitorClassService;
	
	@Autowired
	private ButtonService buttonService;
	
	@RequestMapping(value="/datatable",method={RequestMethod.POST,RequestMethod.GET})
	public String datatable(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/datatable";
	}
	
	
	/*@InitBinder("button")   
    public void initBinder1(WebDataBinder binder) {   
            binder.setFieldDefaultPrefix("button.");   
    }  */
	
	@ResponseBody
	@RequestMapping(value="/datatable/list",method={RequestMethod.POST,RequestMethod.GET})
	public Map<String, Object> datatableList(HttpServletRequest request,Model model, 
			 Button button ) {
		
		PageHelper.startPage(button.getPagination());
		List<Button> list = buttonService.selectButtonList(button);
		return PageUtil.pack4PageHelper(list, button.getPagination());
	}
	
	@RequestMapping(value="/detail",method={RequestMethod.POST,RequestMethod.GET})
	public String workdetail(HttpServletRequest request,Model model,Button button ) {
		
		model.addAttribute("enumItems", StatusEnum.enumToListMap());
//		model.addAttribute("dicItems", SysContext.getDicItemsByDicId(1));
		 return "/web/demo/workdetail";
	}
	
	@RequestMapping(value="/revisionlist",method={RequestMethod.POST,RequestMethod.GET})
	public String revisionlist(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/revisionlist";
	}
	
	@ResponseBody
	@RequestMapping(value="/getRevisions",method={RequestMethod.POST,RequestMethod.GET})
	public Map<String, Object> getRevisions(HttpServletRequest request,Model model,@RequestBody Button button ) {
		PageHelper.startPage(button.getPagination());
		List<Button> list = buttonService.selectButtonList(button);
		return PageUtil.pack4PageHelper(list, button.getPagination());
	}
	
	@RequestMapping(value="/dic/list",method={RequestMethod.POST,RequestMethod.GET})
	public String dicDemo(HttpServletRequest request,Model model) {
		 return "/web/demo/dic_list";
	}
	
	@RequestMapping(value="/dic/reload",method={RequestMethod.POST,RequestMethod.GET})
	public String reload(HttpServletRequest request,Model model) {
		// SpringContextHolder.initConfig();
		 SpringContextHolder.initDic();
		 SpringContextHolder.initEnum();
		 return "redirect:/main";
	}
	
	@RequestMapping(value="/upload",method={RequestMethod.GET})
	public String uploadDemo(HttpServletRequest request,Model model) {
		 return "/web/demo/demo_upload";
	}
	
	
	@RequestMapping(value="/export")
	public String export(HttpServletRequest request,RedirectAttributesModelMap  model) throws FileNotFoundException {
		
		model.addFlashAttribute("ID", "111333ID");
	    model.addFlashAttribute("user_name", "ZHUCHAO朱超");
	    model.addFlashAttribute("SUBREPORT_DIR", "C:\\Users\\lenovo\\Desktop\\demo\\111\\"); 
	    return "redirect:/report/pdf/11/master";
	}
	
	/*@RequestMapping("/{type}/{dprtcode}/{reportKey}")
	public String print(@PathVariable String type, @PathVariable String dprtcode, @PathVariable String reportKey, Model model) throws BusinessException{
		 // 报表数据源  
//	    JRDataSource jrDataSource = new JRBeanCollectionDataSource(JavaBeanPerson.getList());  
	    
		String path = getClass().getResource("/").getPath();
		path = path.substring(1, path.indexOf("classes"));
		
		StringBuffer filepath = new StringBuffer();
		//TODO string 要优化
		filepath.append(path);
		filepath.append("/views/reports/");
		filepath.append(dprtcode);
		filepath.append("/");
		filepath.append(reportKey);
		filepath.append(".jasper");
		
		File file = new File(filepath.toString());  
	    if(file.exists()){
//	    	model.addAttribute("url", "/WEB-INF/views/reports/"+dprtcode+"/"+reportKey+".jasper");
	    	model.addAttribute("url", filepath.toString());
	    }else{
//	    	model.addAttribute("url", "/WEB-INF/views/reports/common/"+reportKey+".jasper");  
	    	filepath.append(path);
			filepath.append("/views/reports/common");
			filepath.append("/");
			filepath.append(reportKey);
			filepath.append(".jasper");
			File fileCommon = new File(filepath.toString());  
			if (!fileCommon.exists()) {
				throw new BusinessException("报表模板找不到:".concat(filepath.toString()));
			}
	    }
	    
	    model.addAttribute("url", filepath.toString());
	    // 动态指定报表模板url  
	    model.addAttribute("format", type); // 报表格式  
//	    model.addAttribute("jrMainDataSource", jrDataSource);  
	    return "customerReport"; // 对应jasper-defs.xml中的bean id
		
	}*/
	
	/**
	 * 附件上传样例
	 * @param files
	 * @param request
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadfile",method={RequestMethod.POST,RequestMethod.GET})
	public void uploadfile(@RequestParam("files") CommonsMultipartFile[] files,
			HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException {
//		List<Map<String,Object>> list = upload(files, request, response);
	}
	 

	/**
	 * 附件下载
	 * 测试地址：http://localhost:8080/thjw/demo/downfile
	 * @param relativePath:入文件相对路径(包含文件名称)，。
	 * @param displayName :用于显示的文件名称（含扩展名）
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="/downfile",method={RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<byte[]> downfile(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException {

		/*String relativePath = "\\20160816\\default\\e754e09d-1c65-4245-b5cf-4119c0144b5e.jpg";
		String displayName = "胜多负少.jpg";
		return download(relativePath, displayName, request, response);*/
		return null;
	}
	
	/**
	 * 目前日期已经统一处理，json响应时统一将Date返回yyyy-MM-dd HH:mm:ss
	 * 测试地址：http://localhost:8080/thjw/demo/date_demo
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/date",method={RequestMethod.POST,RequestMethod.GET})
	public User dateDemo( HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException {
		User user =new User();
		user.setLastvisit(new Date());
		return user;
	}
	
	/**
	 * 监控设置
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="/moniter/view",method={RequestMethod.POST,RequestMethod.GET})
	public String moniterDemo( HttpServletRequest request,HttpServletResponse response,Model model) throws FileNotFoundException, IOException {
		 
		List<MonitorOptionClass> list = monitorClassService.queryAll();
		model.addAttribute("list", list);
		return "/web/demo/moniter_view";
	}
	
	
	@RequestMapping(value="/exception/main",method={RequestMethod.POST,RequestMethod.GET})
	public String exceptionMain( HttpServletRequest request,HttpServletResponse response,Model model) throws BusinessException {
		  
		return "/web/demo/exception_main";
	}
	
	@RequestMapping(value="/exception/normal",method={RequestMethod.POST,RequestMethod.GET})
	public String exceptionNormal( HttpServletRequest request,HttpServletResponse response,Model model) throws BusinessException {
		  
		User user = new User();
		try {
			user.setLastvisit(new Date());
		} catch (Exception e) {
			throw new BusinessException("不能重复创建用户!");
		}
		return "/web/demo/moniter_view";
	}
	 
	@ResponseBody
	@RequestMapping(value="/exception/ajax",method={RequestMethod.POST,RequestMethod.GET})
	public User exceptionAjax( HttpServletRequest request,HttpServletResponse response,Model model) throws BusinessException {
		User user = new User();
		try {
			user.setLastvisit(new Date());
		} catch (Exception e) {
			throw new BusinessException("不能重复创建用户!");
		}
		return user;
	}
	
	@RequestMapping(value="/page1",method={RequestMethod.POST,RequestMethod.GET})
	public String page1(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page1";
	}
	
	@RequestMapping(value="/uiAdd",method={RequestMethod.POST,RequestMethod.GET})
	public String uiAdd(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/add";
	}
	
	@RequestMapping(value="/uiEdit",method={RequestMethod.POST,RequestMethod.GET})
	public String uiEdit(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/edit";
	}
	
	@RequestMapping(value="/page2",method={RequestMethod.POST,RequestMethod.GET})
	public String page2(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page2";
	}
	
	@RequestMapping(value="/page3",method={RequestMethod.POST,RequestMethod.GET})
	public String page3(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page3";
	}
	
	@RequestMapping(value="/page4",method={RequestMethod.POST,RequestMethod.GET})
	public String page4(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page4";
	}
	
	@RequestMapping(value="/page5",method={RequestMethod.POST,RequestMethod.GET})
	public String page5(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page5";
	}
	
	@RequestMapping(value="/page6",method={RequestMethod.POST,RequestMethod.GET})
	public String page6(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page6";
	}
	
	@RequestMapping(value="/page7",method={RequestMethod.POST,RequestMethod.GET})
	public String page7(HttpServletRequest request,Model model,Button button ) {
		 return "/web/demo/page7";
	}
		
}
