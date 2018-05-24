package com.eray.thjw.training.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.eray.thjw.control.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.service.PlanPersonService;
import com.eray.thjw.util.ThreadVarUtil;


import enu.ThresholdEnum;

/**
 * 
 * 维修人员培训跟踪控制器
 * @author sunji
 *
 */
@Controller
@RequestMapping("/training/tracking")
public class TrackingController extends BaseController{
	
	@Autowired
	private DepartmentService departmentService; 
	
	@Autowired
	private PlanPersonService planPersonService;
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	
	
	/**
	 * 跳转至维修人员培训跟踪
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="training:tracking:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest request) {
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
		return new ModelAndView("training/tracking/tracking_main",model);
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return resultMap
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody PlanPerson planPerson)throws BusinessException{
		Map<String, Object> resultMap=planPersonService.qeryAllPageList(planPerson);
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.RYKCPX.getName(), planPerson.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
		 
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017年12月26日 上午10:20:36
	 * @CreateBy 岳彬彬
	 * @param planPerson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="training:tracking:main:01")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "PersonnelTrainingTracking.xls")
	public String export(PlanPerson planPerson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			planPerson.setPagination(p);
			if(null != planPerson.getKeyword() && !"".equals(planPerson.getKeyword())){
				String keyword = planPerson.getKeyword();
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				planPerson.setKeyword(keyword);
			}
			Map<String, Object> resultMap = planPersonService.qeryAllPageList(planPerson);
			List<PlanPerson> list = (List<PlanPerson>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.RYKCPX.getName(), planPerson.getDprtcode());
			model.addAttribute("yjjb1", monitorsettings.getYjtsJb1());
			model.addAttribute("yjjb2", monitorsettings.getYjtsJb2());	
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "tracking", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}

}
