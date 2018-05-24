package com.eray.thjw.training.control;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.quality.service.MaintenancePersonnelService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.WorkRequireService;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToCourse;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.service.BusinessService;
import com.eray.thjw.training.service.BusinessToCourseService;
import com.eray.thjw.training.service.BusinessToMaintenancePersonnelService;
import com.eray.thjw.training.service.CourseService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.course.CycleEnum;


@Controller
@RequestMapping("/training/posttracking")
public class PosttrackingController  extends BaseController{

	
	@Autowired
	private BusinessService businessService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private MaintenancePersonnelService maintenancePersonnelService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private BusinessToCourseService businessToCourseService;
	@Autowired
	private BusinessToMaintenancePersonnelService businessToMaintenancePersonnelService;
	@Autowired
	private WorkRequireService workRequireService;

	/**
	 * 跳转至岗位人员课程跟踪
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="training:posttracking:main")
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
		return new ModelAndView("training/posttracking/posttracking_main",model);
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return resultMap
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody BusinessToMaintenancePersonnel businessToMaintenancePersonnel,
			HttpServletRequest request)throws BusinessException{
		//获取人员课程信息
		return businessToMaintenancePersonnelService.queryAll(businessToMaintenancePersonnel);
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2018-2-2 上午10:35:29
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "posttracking.xls")
	public String posttrackingExcel(BusinessToMaintenancePersonnel businessToMaintenancePersonnel,Model model)throws BusinessException {
		try {
			List<BusinessToMaintenancePersonnel> list =businessToMaintenancePersonnelService.queryAllList(businessToMaintenancePersonnel);
			for (BusinessToMaintenancePersonnel btm : list) {
				if(btm.getParamsMap() != null && btm.getParamsMap().get("zqdw") != null && btm.getParamsMap().get("zqz") != null){
					btm.getParamsMap().put("zqz",btm.getParamsMap().get("zqz") + CycleEnum.getName(Integer.valueOf(btm.getParamsMap().get("zqdw")+"")));
				}
			}
			String wjmc="posttracking";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}


}
