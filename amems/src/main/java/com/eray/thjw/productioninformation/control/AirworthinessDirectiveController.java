package com.eray.thjw.productioninformation.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.production.po.AirworthinessDirective;
import com.eray.thjw.production.service.AirworthinessDirectiveService;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.SessionUtil;

/**
 * 
 * @author zhuchao
 * @description 适航指令控制器
 */
@Controller
@RequestMapping("/production/airworthinessdirective")
public class AirworthinessDirectiveController extends BaseController {
	
	@Resource
	private AirworthinessDirectiveService airworthinessDirectiveService; 
	
	@Resource
	private PlaneDataService planeDataService; 
	
	@Resource
	private UserService userService; 
	
	/**
	 * 跳转至适航指令管理页面
	 * @return 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Privilege(code="production:airworthinessdirective:manage")
	@RequestMapping(value = "/manage")
	public ModelAndView manage(HttpServletRequest request,String fjzch,String dprtcode,String curheight ,Model model)
			throws BusinessException {
		
		/*
		 * 默认机构：
		 * 1.如果授权机构包含用户机构，以用户机构作为默认机构
		 * 2.如果授权机构不包含用户机构，以第一个授权机构作为默认机构
		 */
		if(StringUtils.isBlank(dprtcode)) {
			User user = SessionUtil.getUserFromSession(request);
			List<Department> accessDepartment = SessionUtil.getAttr(request, "accessDepartment", List.class);
			if (accessDepartment!=null && !accessDepartment.isEmpty()) {
				for (Department department : accessDepartment) {
					if(department.getId().endsWith(user.getJgdm())){
						dprtcode = user.getJgdm();
						break;
					}
				}
				if(StringUtils.isBlank(dprtcode)) {
					dprtcode = accessDepartment.get(0).getId();
				}
			}
		}
		
		//查询飞机
		PlaneData planeData = new PlaneData();
		planeData.setDprtcode(dprtcode);
		List<PlaneData> planes = planeDataService.queryPlanes(planeData);
		
		model.addAttribute("planes", planes);
		if(StringUtils.isNotBlank(fjzch)){
			model.addAttribute("fjzch", fjzch);
		}
		
		if(StringUtils.isNotBlank(dprtcode)){
			model.addAttribute("dprtcode", dprtcode);
		}
		if(StringUtils.isNotBlank(curheight)){
			model.addAttribute("curheight", curheight);
		}
		return new ModelAndView("production/airworthinessdirective/airworthinessdirective_manage");
	}
	
	/**
	 * 查询适航指令（分页）
	 * @param request
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="production:airworthinessdirective:manage")
	@ResponseBody
	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	public Map<String, Object> queryList(HttpServletRequest request, AirworthinessDirective record) throws BusinessException {
		try {
			record.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
			record.getParamsMap().put("userType", SessionUtil.getUserFromSession(request).getUserType());
			Map<String, Object> result = airworthinessDirectiveService.queryList(record);
			return result;
		} catch (RuntimeException e) {
			throw new BusinessException("查询适航指令失败",e);
		}
	}
	
	/**
	 * 查询适航指令（分页）
	 * @param request
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	//@Privilege(code="production:airworthinessdirective:manage")
	@ResponseBody
	@RequestMapping(value="/queryPage",method={RequestMethod.POST})
	public Map<String, Object> queryPage(HttpServletRequest request, AirworthinessDirective record) throws BusinessException {
		try {
			User user  = SessionUtil.getUserFromSession(request);
			record.getParamsMap().put("userType", user.getUserType());
			record.getParamsMap().put("userId", user.getId());
			Map<String, Object> result = airworthinessDirectiveService.queryPage(record);
			return result;
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("查询适航指令失败",e);
		}
	}
	
	/**
	 * 新增一条适航指令
	 * @param request
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code="production:airworthinessdirective:saveorupdate")
	@ResponseBody
	@RequestMapping(value="/saveOrUpdate",method={RequestMethod.POST})
	public AirworthinessDirective saveOrUpdate(HttpServletRequest request,  AirworthinessDirective record) throws BusinessException {
		airworthinessDirectiveService.saveOrUpdate(record);
		return record;
	}
	
	/**
	 * 查询一个适航指令
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/load",method={RequestMethod.GET})
	public AirworthinessDirective view(HttpServletRequest request,Model model,String id) throws BusinessException {
		AirworthinessDirective record = airworthinessDirectiveService.load(id);
		return record;
	}
	
	/**
	 * 作废一条适航指令
	 * @param request
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code="production:airworthinessdirective:cancel")
	@ResponseBody
	@RequestMapping(value="/cancel",method={RequestMethod.POST})
	public void cancel(HttpServletRequest request, AirworthinessDirective record) throws BusinessException {
		airworthinessDirectiveService.cancel(record);
	}
	
	/**
	 * 绑定日期处理格式
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	 
	  
}
