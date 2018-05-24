package com.eray.thjw.produce.control;

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
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.service.ComponentiohistoryService;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-11 上午10:57:56
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("/produce/componentiohistory")
public class ComponentiohistoryController  extends BaseController{

	@Resource
	private DepartmentService departmentService;
	@Resource
	private MonitorsettingsService monitorsettingsService;
	@Resource
	private ComponentiohistoryService componentiohistoryService;
	@Resource(name="componentiohistoryExcelImporter")
	private BaseExcelImporter<LoadingList> excelImporter;
	/**
	 * 
	 * @Description 跳转到拆装记录
	 * @CreateTime 2017-9-11 上午10:59:39
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="produce:componentiohistory:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("produce/componentiohistory/componentiohistory_main",model);
	}
	
	/**
	 * 
	 * @Description 主列表查询
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody InstallationListEffective installationListEffective,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return componentiohistoryService.queryAll(installationListEffective);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	@ResponseBody
	@RequestMapping("invalid")
	//@Privilege(code="produce:componentiohistory:main:01")
	public void invalid(HttpServletRequest request,String id) throws BusinessException {
		try {
			componentiohistoryService.deleteRecord(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("作废失败！",e);
		}
		
	}
	/**
	 * 导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@Privilege(code="produce:componentiohistory:main:01")
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response
		    ) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "拆装记录导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "拆装记录入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	
}
