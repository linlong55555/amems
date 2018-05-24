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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.service.ComponenthistoryService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;


/**
 * @Description 物料履历
 * @CreateTime 2018-3-26 下午1:51:24
 * @CreateBy 雷伟
 */
@Controller
@RequestMapping("/produce/materialhistory")
public class MaterialhistoryController {

	@Resource
	private DepartmentService departmentService;
	@Resource
	private ComponenthistoryService componenthistoryService;
	
	/**
	 * @Description 跳转到物料履历
	 * @CreateTime 2017-9-11 上午10:59:39
	 * @CreateBy 雷伟
	 * @return
	 */
	@Privilege(code="produce:materialhistory:main")
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
		return new ModelAndView("produce/materialhistory/materialhistory_main",model);
	}
	
	/**
	 * 
	 * @Description 主列表查询
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 雷伟
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
			return componenthistoryService.queryMaterialAll(installationListEffective);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 跳转到查看部件履历
	 * @CreateTime 2017-10-20 下午2:14:54
	 * @CreateBy 雷伟
	 * @param model
	 * @param bjh
	 * @param xlh
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view()throws BusinessException {
		return new ModelAndView("produce/materialhistory/materialhistory_view");
	}
	
	/**
	 * 
	 * @Description 查询部件履历
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 雷伟
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
			return componenthistoryService.queryMaterialBj(flightSheetVoyage);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
}
