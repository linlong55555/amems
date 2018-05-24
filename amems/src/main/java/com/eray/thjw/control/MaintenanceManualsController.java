package com.eray.thjw.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.baseinfo.po.DirectoryStructure;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycled;
import com.eray.thjw.po.MaintenancePackage;
import com.eray.thjw.po.TaskInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.service.FileCatalogService;
import com.eray.thjw.service.FileRecycledService;
import com.eray.thjw.service.MaintenancePackageService;
import com.eray.thjw.service.TaskService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.UnZipUtil;

import enu.common.DocumentEnum;

/**
 * 
 * @author ll
 * @description 文档管理控制器
 */
@Controller
@RequestMapping("/maintenance/maintenancemanuals")
public class MaintenanceManualsController extends BaseController {
	 
	/**
	 * 目录service
	 */
	@Resource
	private FileCatalogService fileCatalogService;
	
	/**
	 * 文件夹service
	 */
	@Resource
	private MaintenancePackageService maintenancePackageService;
	
	@Resource
	private FileRecycledService fileRecycledService;
	@Resource
	private TaskService taskService;
	
	/**
	 * 
	 * @Description 新增下载任务
	 * @CreateTime 2018年4月14日 上午10:49:08
	 * @CreateBy 岳彬彬
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:10,maintenance:maintenancemanuals:manage1:10,maintenance:maintenancemanuals:manage:10,produce:file:scmain:10,training:file:zlmain:10,material:file:hcmain:10")
	@ResponseBody
	@RequestMapping(value = "doExportFile", method = { RequestMethod.POST })
	public String doExportFile(@RequestBody TaskInfo taskInfo ) throws Exception {
		taskService.addTask(taskInfo);//新增任务
		taskService.doTodoTask(taskInfo);//处理该任务
		return "success";
	}
	 /**
	  * 
	  * @Description 下载页面树形结构
	  * @CreateTime 2018年4月14日 下午4:40:58
	  * @CreateBy 岳彬彬
	  * @param fileCatalog
	  * @return
	  * @throws RuntimeException
	  */
	@Privilege(code="maintenance:maintenancemanuals:manage:10,maintenance:maintenancemanuals:manage1:10,maintenance:maintenancemanuals:manage:10,produce:file:scmain:10,training:file:zlmain:10,material:file:hcmain:10")
	@ResponseBody
	@RequestMapping(value="exportList", method = RequestMethod.POST)
	public List<Map<String, Object>> exportList(@RequestBody FileCatalog fileCatalog) throws RuntimeException {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();  
		
		User user=ThreadVarUtil.getUser();
		fileCatalog.setDprtcode(user.getJgdm());
		menuList = fileCatalogService.exportList(fileCatalog);
	  	
		return menuList;
	}
	 /**
	  * 
	  * @Description 获取所有的任务
	  * @CreateTime 2018年4月14日 上午10:23:29
	  * @CreateBy 岳彬彬
	  * @param taskInfo
	  * @return
	  * @throws Exception
	  */
	@Privilege(code="maintenance:maintenancemanuals:manage:10,maintenance:maintenancemanuals:manage1:10,maintenance:maintenancemanuals:manage:10,produce:file:scmain:10,training:file:zlmain:10,material:file:hcmain:10")
	@ResponseBody
	@RequestMapping(value = "taskList", method = { RequestMethod.POST })
	public Map<String, Object> taskList(@RequestBody TaskInfo taskInfo ) throws Exception {
		return taskService.getAll(taskInfo);
	}
	  
	/**
	 * 跳转至文档管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("mkdm", DocumentEnum.FD_SCWD.getCode());
		return new ModelAndView("project2/document/document_main", model);
	}
	
	/**
	 * 跳转至质量管理的文档管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage1")
	@RequestMapping(value = "/manage1", method = RequestMethod.GET)
	public ModelAndView main1(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("mkdm", DocumentEnum.FD_ZLWD.getCode());
		return new ModelAndView("project2/document/document_main", model);
	}
	
	 /**
	  * 查询目录树
	  * @param request
	  * @param model
	  * @return
	  * @throws Exception
	  */
	@ResponseBody
	@RequestMapping(value="directorylist", method = RequestMethod.POST)
	public List<Map<String, Object>> directorylist(String id,String mkdm) throws RuntimeException {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();  
		
		User user=ThreadVarUtil.getUser();
		FileCatalog fileCatalog2=new FileCatalog();
		fileCatalog2.setDprtcode(user.getJgdm());
		fileCatalog2.setMkdm(mkdm);
	  	List<FileCatalog> fileCataloglist = fileCatalogService.findAlls(fileCatalog2);
	  	/*
	  	//虚拟根目录
	  	FileCatalog root = new FileCatalog();
	  	root.setId(mkdm);
	  	root.setFmlid("#");
	  	root.setMlmc(DocumentEnum.getName(mkdm));
	  	fileCataloglist.add(root);*/
	  	boolean selected = false;
	    for (FileCatalog fileCatalog : fileCataloglist) {  
	    	Map<String, Object> childrenMap = new HashMap<String, Object>();
	    	Map<String, Object> childrenMap1 = new HashMap<String, Object>();
	    	
	    	FileCatalog fileCatalog1=new FileCatalog();
	    	fileCatalog1.setId(fileCatalog.getId());
	    	fileCatalog1.setYxzt(1);
	    	fileCatalog1.setFmlid(fileCatalog.getId());
    		int num=fileCatalogService.queryCount(fileCatalog1);//根据父id查询文件夹的个数
    		int num1=maintenancePackageService.queryCount(fileCatalog1);//根据id查询文件的个数
    	
        	childrenMap.put("id", fileCatalog.getId());
        	childrenMap.put("parent", fileCatalog.getFmlid());
        	childrenMap.put("text", fileCatalog.getMlmc()+"("+num+"个文件夹/"+num1+"个文件)");
        	if(StringUtils.isBlank(id)){
        		if(!selected && fileCatalog.getFmlid().equals("#")){
        			selected = true;
	        		childrenMap1.put("selected", selected);
	        	}
        	}else{
        		if(fileCatalog.getId().equals(id)){
	        		childrenMap1.put("selected", true);
	        	}
        	}
        	childrenMap1.put("opened", true);
        	childrenMap.put("state", childrenMap1);
        	menuList.add(childrenMap);  
	    }  
		return menuList;
	}
	
	
	/**
	 * 保存文件夹
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:01,maintenance:maintenancemanuals:manage1:01,project2:document:main:01,produce:file:scmain:01,training:file:zlmain:01,material:file:hcmain:01")
	@ResponseBody
	@RequestMapping(value = "saveFile", method={RequestMethod.POST})
	public Map<String, Object> saveFile(@RequestBody FileCatalog fileCatalog) throws BusinessException {
		try {
			return fileCatalogService.saveFile(fileCatalog);
		}  catch (Exception e) {
			throw new BusinessException("新增失败!", e);
		}
	}
	
	/**
	 * 导入压缩包，自动解压生成文件夹，文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:08,maintenance:maintenancemanuals:manage1:08,maintenance:maintenancemanuals:manage:08,produce:file:scmain:08,training:file:zlmain:08,material:file:hcmain:08")
	@ResponseBody
	@RequestMapping(value = "saveImportZipFile", method={RequestMethod.POST})
	public Map<String, Object> saveImportZipFile(@RequestBody FileCatalog fileCatalog) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "success");//默认是成功的
		
		//获取压缩包
		Attachment zipFile = fileCatalog.getDocZipAttachment();
		if(null == zipFile || StringUtils.isEmpty(zipFile.getCflj())){
			resultMap.put("state", "error");
			resultMap.put("message", "请上传文件压缩包!");
			return resultMap;
		}
		//String upZipPathId = UUID.randomUUID().toString(); 
		String savePath = zipFile.getCflj().replaceFirst("\\\\", "").concat(File.separator);
		
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String filePath = rootPath.concat(File.separator).concat(savePath).concat(zipFile.getNbwjm()).concat(".").concat(zipFile.getHzm());
		String upZipPath = rootPath.concat(File.separator).concat(savePath).concat(File.separator);
		
		//目录结构
		List<DirectoryStructure> dirStructures = null;
		try {
			dirStructures = new UnZipUtil().getDirectoryStructure(filePath,upZipPath, fileCatalog.getFmlid());
		} catch (Exception e) {
			resultMap.put("state", "error");
			resultMap.put("message", "文件解压失败!");
			return resultMap;
		}
		
		//校验目录结构
		resultMap = validateDirStructures(fileCatalog,dirStructures,resultMap);
		
		//处理
		try {
			if(resultMap.get("state").equals("success")){
				resultMap = fileCatalogService.saveImportFile(dirStructures,upZipPath,rootPath,resultMap,fileCatalog.getMkdm());
			}
			return resultMap;
		} catch (Exception e) {
			if(e.getClass().getName().contains("BusinessException")){
				resultMap.put("state", "error");
				resultMap.put("message",e.getMessage());
				return resultMap;
			}else{
				throw new BusinessException("导入失败!", e);
			}
		}
	}
	
	/**
	 * @Description 校验目录结构
	 * @CreateTime 2018-3-29 下午2:27:07
	 * @CreateBy 雷伟
	 * @param fileCatalog 父目录
	 * @param dirStructures 导入的目录结构
	 */
	private Map<String, Object> validateDirStructures(FileCatalog fileCatalog,List<DirectoryStructure> dirStructures,Map<String, Object> resultMap) {
		if(null == dirStructures || dirStructures.size() <= 0){
			resultMap.put("state", "error");
			resultMap.put("message", "压缩包内容不能为空!");
			return resultMap;
		}
		
		//获取压缩包的一级目录名称
		List<String> firstDirNames = new ArrayList<String>();
		for (DirectoryStructure dirObj : dirStructures) {
			if(dirObj.getParentId().equals(fileCatalog.getFmlid())){
				//如果是父目录是根目录，一级目录不能是文件
				if(fileCatalog.getFmlid().equals("#") && dirObj.getType().equals("wj")){
					resultMap.put("state", "error");
					resultMap.put("message", "父目录是根目录，根目录下不能挂文件!");
					return resultMap;
				}
				firstDirNames.add(dirObj.getName());
			}
		}
		
		//判断根目录下，是否存在同名目录
		for (int i = 0; null != firstDirNames && i < firstDirNames.size(); i++) {
			fileCatalog.setMlmc(firstDirNames.get(i));
			resultMap = fileCatalogService.checkUpdMt(fileCatalog);
			if(resultMap.get("state").equals("error")){
				resultMap.put("message", "目录名称"+firstDirNames+"不能重复!");
			}
		}
		
		return resultMap;
	}

	/** @author ll
	 * @description 检查文件夹code是否可以新增
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(@RequestBody FileCatalog fileCatalog) throws RuntimeException {

		return fileCatalogService.checkUpdMt(fileCatalog);
	}
		
	/**
	 * 文件夹更名
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:02,maintenance:maintenancemanuals:manage1:02,project2:document:main:02,produce:file:scmain:02,training:file:zlmain:02,material:file:hcmain:02")
	@ResponseBody
	@RequestMapping(value = "updateFile", method={RequestMethod.POST})
	public Map<String, Object> updateFile(@RequestBody FileCatalog fileCatalog) throws RuntimeException {
		
		return fileCatalogService.updateFile(fileCatalog);
	}
	
	/**
	 * @Description 修改父目录
	 * @CreateTime 2018-3-16 下午1:37:32
	 * @CreateBy 刘兵
	 * @param fileCatalog
	 * @return
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:02,maintenance:maintenancemanuals:manage1:02,project2:document:main:02,produce:file:scmain:02,training:file:zlmain:02,material:file:hcmain:02")
	@ResponseBody
	@RequestMapping(value = "updateFmlid", method={RequestMethod.POST})
	public Map<String, Object> updateFmlid(@RequestBody FileCatalog fileCatalog){
		return fileCatalogService.updateFmlid(fileCatalog);
	}

	/** @author ll
	 * @description 检查文件夹code是否可以删除
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value="checkdel",method = RequestMethod.POST)
	public Map<String, Object> checkdel(HttpServletRequest request,String id) throws RuntimeException {
		
		return fileCatalogService.checkdel(id);
	}
	
	/**
	 * @Description 检查文件夹是否存在
	 * @CreateTime 2017-9-15 上午9:27:27
	 * @CreateBy 刘兵
	 * @param id 文件夹id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "checkExist", method = RequestMethod.POST)
	public void checkExist(String id) throws BusinessException {
		try {
			fileCatalogService.checkExist(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询文件夹失败!",e);
		}
	}
	
	/**
	 * 删除文件夹
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:03,maintenance:maintenancemanuals:manage1:03,project2:document:main:03,produce:file:scmain:03,training:file:zlmain:03,material:file:hcmain:03")
	@ResponseBody
	@RequestMapping(value = "deleFile", method={RequestMethod.POST})
	public Map<String, Object> deleFile(@RequestBody FileCatalog fileCatalog) throws BusinessException {
		
		try {
			return fileCatalogService.deleteFolder(fileCatalog);
		} catch (Exception e) {
			throw new BusinessException("删除文件夹失败！", e);
		}
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 文件列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "maintenanceList", method = RequestMethod.POST)
	public Map<String, Object> maintenanceList(@RequestBody MaintenancePackage maintenancePackage,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取登入user
		User user=ThreadVarUtil.getUser();
	
		maintenancePackage.setDprtcode(user.getJgdm());
		maintenancePackage.setYxzt(1);
		resultMap.put("rows",maintenancePackageService.queryAllPageList(maintenancePackage));
		return resultMap;
	}

	/**
	 * 修改文件名
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:05,maintenance:maintenancemanuals:manage1:05,project2:document:main:05,produce:file:scmain:05,training:file:zlmain:05,material:file:hcmain:05")
	@ResponseBody
	@RequestMapping(value = "updtaeuploadingFile", method={RequestMethod.POST})
	public Map<String, Object> updtaeuploadingFile(@RequestBody MaintenancePackage maintenancePackage) throws BusinessException {
		try {
			return maintenancePackageService.updtaeuploadingFile(maintenancePackage);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("修改文件失败!",e);
		}
	}
	
	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:04,maintenance:maintenancemanuals:manage1:04,project2:document:main:04,produce:file:scmain:04,training:file:zlmain:04,material:file:hcmain:04")
	@ResponseBody
	@RequestMapping(value = "uploadingFile", method={RequestMethod.POST})
	public Map<String, Object> uploadingFile(@RequestBody List<MaintenancePackage> maintenancePackage) throws BusinessException {
		try {
			return maintenancePackageService.uploadingFile(maintenancePackage);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("上传文件失败!",e);
		}
	}
	
	/**
	 * 移动文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:09,maintenance:maintenancemanuals:manage1:09,maintenance:maintenancemanuals:manage:09,produce:file:scmain:09,training:file:zlmain:09,material:file:hcmain:09")
	@ResponseBody
	@RequestMapping(value = "updateMainid", method={RequestMethod.POST})
	public Map<String, Object> updateMainid(@RequestBody MaintenancePackage maintenancePackage) throws BusinessException {
		try {
			return maintenancePackageService.updateMainid(maintenancePackage);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("移动文件失败!",e);
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="maintenance:maintenancemanuals:manage:06,maintenance:maintenancemanuals:manage1:06,project2:document:main:06,produce:file:scmain:06,training:file:zlmain:06,material:file:hcmain:06")
	@ResponseBody
	@RequestMapping(value = "deleteuploadingFile", method={RequestMethod.POST})
	public Map<String, Object> deleteuploadingFile(@RequestBody MaintenancePackage maintenancePackage) throws RuntimeException {
		
		return maintenancePackageService.deleteuploadingFile(maintenancePackage);
	}
	
	/**
	  * 查询目录树
	  * @param request
	  * @param model
	  * @return
	  * @throws Exception
	  */
	@ResponseBody
	@RequestMapping(value="queryWinList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryWinList(@RequestBody FileCatalog param) throws RuntimeException {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();  
		User user=ThreadVarUtil.getUser();
		param.setDprtcode(user.getJgdm());
	  	List<FileCatalog> fileCataloglist = fileCatalogService.queryAlls(param);
	  	/*
	  	//虚拟根目录
	  	FileCatalog root = new FileCatalog();
	  	root.setId(mkdm);
	  	root.setFmlid("#");
	  	root.setMlmc(DocumentEnum.getName(mkdm));
	  	fileCataloglist.add(root);*/
	  	
	  	if(param.getParamsMap() != null && param.getParamsMap().get("hideId") != null){
	  		Map<String, Object> childrenMap = new HashMap<String, Object>();
	    	Map<String, Object> childrenMap1 = new HashMap<String, Object>();
	       	childrenMap.put("id", "gml");
	       	childrenMap.put("parent", "#");
	       	childrenMap.put("text", "根目录");
	       	childrenMap1.put("opened", true);
	       	childrenMap.put("state", childrenMap1);
	       	menuList.add(childrenMap);  
	  	}
	  	
	  	boolean selected = false;
	    for (FileCatalog fileCatalog : fileCataloglist) {  
	    	Map<String, Object> childrenMap = new HashMap<String, Object>();
	    	Map<String, Object> childrenMap1 = new HashMap<String, Object>();
	    	
	    	FileCatalog fileCatalog1=new FileCatalog();
	    	fileCatalog1.setId(fileCatalog.getId());
	    	fileCatalog1.setYxzt(1);
	    	fileCatalog1.setFmlid(fileCatalog.getId());
   		int num=fileCatalogService.queryCount(fileCatalog1);//根据父id查询文件夹的个数
   		int num1=maintenancePackageService.queryCount(fileCatalog1);//根据id查询文件的个数
   		if(param.getParamsMap() != null && param.getParamsMap().get("parentId") != null && fileCatalog.getId().equals(param.getParamsMap().get("parentId").toString())){
   			num--;
   		}
       	childrenMap.put("id", fileCatalog.getId());
       	if(param.getParamsMap() != null && param.getParamsMap().get("hideId") != null && fileCatalog.getFmlid().equals("#")){
       		childrenMap.put("parent", "gml");
       	}else{
       		childrenMap.put("parent", fileCatalog.getFmlid());
       	}
       	childrenMap.put("text", fileCatalog.getMlmc()+"("+num+"个文件夹/"+num1+"个文件)");
       	if(StringUtils.isBlank(param.getId())){
       		if(!selected && fileCatalog.getFmlid().equals("#")){
       				selected = true;
	        		childrenMap1.put("selected", selected);
	        	}
       	}else{
       		if(fileCatalog.getId().equals(param.getId())){
	        		childrenMap1.put("selected", true);
	        	}
       	}
       	childrenMap1.put("opened", true);
       	childrenMap.put("state", childrenMap1);
       	menuList.add(childrenMap);  
	    }  
		return menuList;
	}
	
	/**
	 * 下载
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="/downfile",method={RequestMethod.GET})
	public ResponseEntity<byte[]> downfile(HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException {

		String relativePath =request.getParameter("wjdz");
		String displayName1 =request.getParameter("wj")+relativePath.substring(relativePath.indexOf("."),relativePath.length());
		String displayName=new String(displayName1.getBytes("ISO-8859-1"),"utf-8");
		return download(relativePath, displayName, request, response);
	}
	
	/**
	 * @Description 查询回收站列表
	 * @CreateTime 2018年1月29日 下午2:06:49
	 * @CreateBy 韩武
	 * @param file
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "recycledlist", method={RequestMethod.POST})
	public List<FileRecycled> queryRecycledList(@RequestBody FileCatalog file) throws BusinessException {
		try {
			return fileRecycledService.queryRecycledList(file);
		} catch (Exception e) {
			throw new BusinessException("查询回收站列表失败！", e);
		}
	}
	
	/**
	 * @Description 回收站撤销
	 * @CreateTime 2018年1月29日 下午4:15:42
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "resotre", method={RequestMethod.POST})
	public void resotre(@RequestBody List<FileRecycled> list) throws BusinessException {
		try {
			fileRecycledService.doResotre(list);
		} catch (Exception e) {
			throw new BusinessException("回收站撤销失败！", e);
		}
	}
	
	/**
	 * @Description 回收站删除
	 * @CreateTime 2018年1月29日 下午6:01:42
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "confirmdelete", method={RequestMethod.POST})
	public void confirmdelete(@RequestBody List<FileRecycled> list) throws BusinessException {
		try {
			fileRecycledService.doConfirmDelete(list);
		} catch (Exception e) {
			throw new BusinessException("回收站删除失败！", e);
		}
	}
	
	/**
	 * @Description 清空回收站
	 * @CreateTime 2018年1月30日 下午1:45:33
	 * @CreateBy 韩武
	 * @param fileCatalog
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "empty", method={RequestMethod.POST})
	public void empty(@RequestBody FileCatalog fileCatalog) throws BusinessException {
		try {
			fileRecycledService.doEmpty(fileCatalog);
		} catch (Exception e) {
			throw new BusinessException("清空回收站失败！", e);
		}
	}
	/**
	 * 
	 * @Description 删除任务
	 * @CreateTime 2018年5月9日 上午10:21:09
	 * @CreateBy 岳彬彬
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTask", method = { RequestMethod.POST })
	public String deleteTask(@RequestBody TaskInfo taskInfo ) throws Exception {
		return taskService.deleteTask(taskInfo.getId());
	}
	
	
}
