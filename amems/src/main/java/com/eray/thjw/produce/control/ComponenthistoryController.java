package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.service.ComponenthistoryService;
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
@RequestMapping("/produce/componenthistory")
public class ComponenthistoryController {

	@Resource
	private DepartmentService departmentService;
	@Resource
	private ComponenthistoryService componenthistoryService;
	
	/**
	 * 
	 * @Description 跳转到部件履历
	 * @CreateTime 2017-9-11 上午10:59:39
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="produce:componenthistory:main")
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
		return new ModelAndView("produce/componenthistory/componenthistory_main",model);
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
			return componenthistoryService.queryAll(installationListEffective);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 查看部件履历主列表
	 * @CreateTime 2017-10-20 下午2:14:40
	 * @CreateBy 孙霁
	 * @param componentCertificate
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCertificateList", method = RequestMethod.POST)
	public Map<String, Object> queryCertificateList(@RequestBody ComponentCertificate componentCertificate,HttpServletRequest request,Model model)throws BusinessException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("rows",componenthistoryService.queryCertificateList(componentCertificate));
			return map;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 跳转到查看部件履历
	 * @CreateTime 2017-10-20 下午2:14:54
	 * @CreateBy 孙霁
	 * @param model
	 * @param bjh
	 * @param xlh
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view()throws BusinessException {
			return new ModelAndView("produce/componenthistory/componenthistory_view");
	}
	
	/**
	 * 
	 * @Description 查询部件履历
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllBj", method = RequestMethod.POST)
	public Map<String, Object> queryAllBj(@RequestBody FlightSheetVoyage flightSheetVoyage,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return componenthistoryService.queryBj(flightSheetVoyage);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
}
