package com.eray.thjw.monitorsettings.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;
/**
 * 
 * @author sunji
 * @description  Monitorsettings
 */
@Controller 
@RequestMapping("/sys/monitorsettings")
public class MonitorsettingsController extends BaseController {
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 跳转至监控设置
	 * @return 
	 * @throws BusinessException 
	 */
	@Privilege(code="sys:monitorsettings:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
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
		return new ModelAndView("sys/monitorsettings/monitorsettings", model);
	}
	
	/**
	 *监控设置
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "monitorsettingsList", method = RequestMethod.POST)
	public Map<String, Object> monitorsettingsList(@RequestParam String dprtcode,HttpServletRequest request,Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Monitorsettings> monitorsettingsList=monitorsettingsService.queryThresholdByDprtcode(dprtcode); 
				resultMap.put("rows",monitorsettingsList);
		} catch (BusinessException e) {
			throw new BusinessException("查询失败",e);
		}
		return resultMap;
	}
	/**
	 * 修改保存
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateMonitorsettings", method = RequestMethod.POST)
	public Map<String, Object> updateMonitorsettings(@RequestBody Monitorsettings monitorsettings,HttpServletRequest request)throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			monitorsettingsService.update(monitorsettings);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return resultMap;
		
	}
	
	
	/**
	 * 获取，适航性资料子类型
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getShxzlSubTypeList", method = RequestMethod.POST)
	public Map<String, Object> getShxzlSubTypeList(@RequestParam String dprtcode,HttpServletRequest request,Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Monitorsettings> subTypeList = monitorsettingsService.queryShxzlSubTypeByDprtcode(dprtcode); 
			resultMap.put("subTypes",subTypeList);
		} catch (BusinessException e) {
			throw new BusinessException("查询适航性资料子类型失败",e);
		}
		return resultMap;
	}
}
