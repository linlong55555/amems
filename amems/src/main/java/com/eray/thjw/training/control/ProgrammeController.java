package com.eray.thjw.training.control;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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


@Controller
@RequestMapping("/training/programme")
public class ProgrammeController  extends BaseController{

	
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
	 * 跳转至培训大纲
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="training:programme:main")
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
		return new ModelAndView("training/programme/programme_main",model);
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return resultMap
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody Business business,HttpServletRequest request)throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", businessService.queryAllBusiness(business));
		return resultMap;
	}
	
	/**
	 * @author sunji
	 * @description 根据id查询所有人员信息和
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 *
	 */
	@ResponseBody
	@RequestMapping("list")
	public Map<String, Object> list(HttpServletRequest request,@RequestBody Business business) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取课程信息
		resultMap.put("kcList", businessToCourseService.queryAllBygwid(business.getId(),business.getDprtcode()));
		//获取人员信息
		BusinessToMaintenancePersonnel businessPer = new BusinessToMaintenancePersonnel();
		businessPer.setGwid(business.getId());
		businessPer.getParamsMap().put("bs", "1");
		resultMap.put("ryList", businessToMaintenancePersonnelService.queryAllPage(businessPer));;
		//获取岗位需求信息
		 resultMap.put("gxList",workRequireService.queryWorkRequiresAllByGwid(business.getId()));
		
		return resultMap;
	}
	/**@description sunji
	 * @description 保存大纲
	 * @return 
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@Privilege(code="training:programme:main:01")
	public void add(@RequestBody BusinessToCourse businessToCourse,HttpServletRequest request) throws BusinessException{
		try {
			businessToCourseService.insert(businessToCourse);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败！",e);
		}
	}
	/**@description sunji
	 * @description 修改大纲
	 * @return 
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@Privilege(code="training:programme:main:02")
	public void edit(@RequestBody BusinessToCourse businessToCourse,HttpServletRequest request) throws BusinessException{
		try {
			businessToCourseService.update(businessToCourse);
		} catch (BusinessException e) {
			throw new BusinessException("数据修改失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("delete")
	@Privilege(code="training:programme:main:03")
	public void delete(HttpServletRequest request,String id) throws BusinessException {
		try {
			businessToCourseService.delete(id);
		} catch (BusinessException e) {
			throw new BusinessException("数据作废失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 根据id查询所有人员信息和培训信息
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 *
	 */
	@ResponseBody
	@RequestMapping("matlist")
	public Map<String, Object> matlist(HttpServletRequest request,String wxrydaid) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取人员课程信息
		resultMap.put("matlist", businessToMaintenancePersonnelService.queryAllBywxrydaid(wxrydaid));
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value="addWorkRequires", method = RequestMethod.POST)
	@Privilege(code="training:programme:main:04")
	public void saveWorkRequire(@RequestBody WorkRequire workRequire,HttpServletRequest request) throws BusinessException{
		try {
			workRequireService.saveWorkRequires(workRequire);
		} catch (BusinessException e) {
			   throw new BusinessException("数据添加失败",e);
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="updateSaveWorks",method = RequestMethod.POST)
	@Privilege(code="training:programme:main:05")
	public void editSaveWorkRequire(@RequestBody WorkRequire workRequire,HttpServletRequest request) throws BusinessException{
		try {
			workRequireService.updateWorkRequires(workRequire);
		} catch (BusinessException e) {
			   throw new BusinessException("数据修改失败",e);
		}
		
	}
	
	@ResponseBody
	@RequestMapping("deleteWorkRequire")
	@Privilege(code="training:programme:main:06")
	public void deleteWorkRequire(String id,HttpServletRequest request) throws BusinessException{
		try {
			   WorkRequire  require=new WorkRequire();
			     require.setId(id);
			workRequireService.deleteWorkRequires(require);
		} catch (BusinessException e) {
			   throw new BusinessException("数据删除失败",e);
		}
		
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年12月11日 下午2:00:43
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param keyword
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="training:programme:main:07")
	@RequestMapping(value = "programme")
	public String export(String dprtcode,String keyword, HttpServletRequest request,RedirectAttributesModelMap model) throws BusinessException {
		try {
			StringBuffer sbf = new StringBuffer("");
			sbf.append(" where zt =1 and dprtcode ='"+dprtcode+"' ");
			if (!"".equals(keyword)) {
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				sbf.append(" and (dgbh like '%" + keyword + "%' or dgmc like '%" + keyword + "%' )");
			}
			model.addFlashAttribute("sql", sbf.toString());
			return "redirect:/report/".concat("xls").concat("/").concat(dprtcode).concat("/programme.xls");
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectById")
	public Map<String, Object> selectById(HttpServletRequest request, String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("row",  businessToCourseService.selectByPrimaryKey(id));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 查询课程编号
	 * @CreateTime 2018-1-22 下午4:36:29
	 * @CreateBy 孙霁
	 * @param business
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectDistinctKcbh", method = RequestMethod.POST)
	public Map<String, Object> selectDistinctKcbh(@RequestBody Course course,HttpServletRequest request)throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", courseService.selectDistinctKcbh(course));
		return resultMap;
	}

}
