package com.eray.thjw.project2.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.MaintenanceClass;
import com.eray.thjw.project2.service.MaintenanceClassService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @Description 
 * @CreateTime 2017-8-17 上午11:18:52
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/project/maintenanceclass")
public class MaintenanceClassController extends BaseController {

	@Resource
	private MaintenanceClassService maintenanceClassService;

	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource(name="maintenanceclassExcelimporter")
	private BaseExcelImporter<MaintenanceClass> excelImporter;
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-8-17 上午11:17:56
	 * @CreateBy 韩武
	 * @param maintenanceClass
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinByFjjx", method = RequestMethod.POST)
	public Map<String, Object> queryWinByFjjx(@RequestBody MaintenanceClass maintenanceClass) throws BusinessException{
		try {
			PageHelper.startPage(maintenanceClass.getPagination());
			List<MaintenanceClass> list = maintenanceClassService.queryWinByFjjx(maintenanceClass);
			return PageUtil.pack4PageHelper(list, maintenanceClass.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询维修方案大类失败！",e);
		}
	}
	
	/**
	 * @Description 根据飞机机型查询维修方案大类(下拉框)
	 * @CreateTime 2017年8月16日 下午2:26:13
	 * @CreateBy 韩武
	 * @param maintenanceClass
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "querySelectByFjjx", method = RequestMethod.POST)
	public List<MaintenanceClass> querySelectByFjjx(@RequestBody MaintenanceClass maintenanceClass) throws BusinessException{
		try {
			return maintenanceClassService.queryWinByFjjx(maintenanceClass);
		} catch (Exception e) {
			throw new BusinessException("查询维修方案大类失败！",e);
		}
	}
	
	/**
	 * 
	 * @Description 跳转至适航性资料界面
	 * @CreateTime 2017-8-17 上午11:19:09
	 * @CreateBy 孙霁
	 * @param annunciate
	 * @return
	 */
	@Privilege(code="project:maintenanceclass:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Annunciate annunciate) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		/*User use=new User();
		use.setJgdm(user.getJgdm());*/
		//model.put("jxList", planeModelDataService.findAllType(user.getJgdm()));
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("project2/maintenanceClass/maintenanceClass_main",model);
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-8-17 上午11:19:21
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody MaintenanceClass maintenanceClass,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = maintenanceClassService.queryAll(maintenanceClass);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 保存
	 * @CreateTime 2017-8-17 上午11:19:33
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@Privilege(code="project:maintenanceclass:main:01")
	public void add(@RequestBody MaintenanceClass maintenanceClass,HttpServletRequest request) throws BusinessException{
		try {
			maintenanceClassService.insert(maintenanceClass);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("操作失败！", e);
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-8-17 上午11:19:39
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@Privilege(code="project:maintenanceclass:main:02")
	public void edit(@RequestBody MaintenanceClass maintenanceClass,HttpServletRequest request) throws BusinessException{
		try {
			maintenanceClassService.update(maintenanceClass);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("操作失败！", e);
		}
	}
	/**
	 * 
	 * @Description 作废
	 * @CreateTime 2017-8-17 上午11:19:45
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("delete")
	@Privilege(code="project:maintenanceclass:main:03")
	public void delete(HttpServletRequest request,String id)  throws BusinessException{
		
		try {
			maintenanceClassService.delete(id);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("操作失败！", e);
		}
	}
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-9-14 下午5:55:02
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectById")
	public Map<String, Object> selectById(HttpServletRequest request,String id)  throws BusinessException{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("maintenanceclass", maintenanceClassService.selectById(id));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("操作失败！", e);
		}
	}
	
	/**
	 * @Description 维修项目大类模板导入
	 * @author liudeng
	 * @param  dprtcode,multipartRequest,response
	 * @throws BusinessException
	 */
	@Privilege(code="project:maintenanceclass:main:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,String dprtcode,
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("dprtcode", dprtcode);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "维修项目大类数据导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "维修项目大类数据导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
}



