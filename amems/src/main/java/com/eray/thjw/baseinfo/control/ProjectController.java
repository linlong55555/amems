package com.eray.thjw.baseinfo.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.baseinfo.po.ProjectAircraft;
import com.eray.thjw.baseinfo.servcice.ProjectAircraftService;
import com.eray.thjw.baseinfo.servcice.ProjectService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Pagination;


/** 
 * @Description 项目控制器
 * @CreateTime 2017-9-15 下午5:51:20
 * @CreateBy 甘清	
 */
@Controller
@RequestMapping("/baseinfo/project")
public class ProjectController extends BaseController{
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private ProjectAircraftService projectAircraftService; //项目与关机关联的信息
	/**
	 * @Description 项目主列表
	 * @CreateTime 2017-9-20 上午10:26:05
	 * @CreateBy 甘清
	 * @return
	 */
	@Privilege(code = "baseinfo:project:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		return new ModelAndView("baseinfo/project/project_main");
	}
	
	/**
	 * @Description 获得列表数据
	 * @CreateTime 2017-9-20 上午10:26:43
	 * @CreateBy 甘清
	 * @param project 前端搜索参数
	 * @return map 返回查询结果
	 */
	@ResponseBody
	@RequestMapping(value = "getprojectList", method = RequestMethod.POST)
	public Map<String, Object> getProjectList(@RequestBody Project project) {	
		Map<String, Object> map = projectService.getProjectList(project);
		return map;
	}
	/**
	 * @Description 添加项目信息
	 * @CreateTime 2017-9-20 下午3:43:21
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @throws BusinessException
	 */
	@Privilege(code = "baseinfo:project:main:add")
	@ResponseBody
	@RequestMapping(value = "addproject", method = RequestMethod.POST)
	public Map<String, Object> addProject(@RequestBody Project project) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{ 
			Project pro = projectService.addProject(project);
			map.put("project", pro);
			return map;
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("添加项目信息失败！", e);
		}
	}
	/**
	 * @Description 更新项目
	 * @CreateTime 2017-9-20 下午3:48:35
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @throws BusinessException
	 */
	@Privilege(code = "baseinfo:project:main:edit")
	@ResponseBody
	@RequestMapping(value = "updateproject", method = RequestMethod.POST)
	public void updateProject(@RequestBody Project project) throws BusinessException {
		try{
			projectService.updateProject(project);
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("更新项目信息失败！", e);
		}
	}
	
	/**
	 * @Description 根据项目编号获得项目信息
	 * @CreateTime 2017-9-25 下午10:16:51
	 * @CreateBy 甘清
	 * @param project 前端条件参数
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "getprojectbyid", method = RequestMethod.POST)
	public Map<String, Object> getProjectById(@RequestBody  Project project) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", projectService.getProjectById(project));
		return map;
	}
	/**
	 * @Description 获得项目与飞机关联的信息
	 * @CreateTime 2017-12-13 下午2:24:48
	 * @CreateBy 甘清
	 * @param airdraft 飞机信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getprojectaircraft", method = RequestMethod.POST)
	public Map<String, Object> getProjectAircraft(@RequestBody ProjectAircraft airdraft) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProjectAircraft air = projectAircraftService.findProjectAircraft(airdraft);
		if (air != null) {
			map.put("projectaircraft", air);
		} else {
			map.put("projectaircraft", "null");
		}
		return map;
	}
	
	/**
	 * @Description 显示项目的详细信息
	 * @CreateTime 2017-10-13 下午3:49:06
	 * @CreateBy 甘清
	 * @param model
	 * @param request requset对象
	 * @return 
	 */
	@RequestMapping(value = "showProject", method = RequestMethod.GET)
	public String showProject(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		model.addAttribute("pid", id);
		return "baseinfo/project/project_detail";
		
	}
	/**
	 * 
	 * @Description 项目下拉框
	 * @CreateTime 2017年10月17日 下午2:43:55
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getProjects", method = RequestMethod.POST)
	public List<Project> getProjects(String dprtcode) throws BusinessException {
		try {
			return projectService.getProjectByDprt(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 项目信息弹窗
	 * @CreateTime 2017年10月18日 上午10:57:47
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getProjectsList", method = RequestMethod.POST)
	public Map<String,Object> getProjectsList(@RequestBody  Project project) throws BusinessException {
		try {
			return projectService.getProjectsList(project);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 删除项目信息
	 * @CreateTime 2017-12-7 下午5:16:39
	 * @CreateBy 甘清
	 * @param id 项目ID
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "baseinfo:project:main:del")
	@ResponseBody
	@RequestMapping(value = "del/{id}", method = RequestMethod.POST)
	public Map<String,Object> delCustomer(@PathVariable("id")String id) throws BusinessException {
		try {
			return projectService.deleteProject(id);
		} catch(BusinessException e) {
			 throw e;
		} catch(Exception e) {
			 throw new BusinessException("删除客户失败！", e);
		}
	}
	/**
	 * @Description 根据飞机注册号获得飞机基本信息
	 * @CreateTime 2017-12-7 下午5:19:23
	 * @CreateBy 甘清
	 * @param projectAircraft 前端查询信息
	 * @return 返回飞机信息
	 */
	public Map<String,Object> getProjectAircraftinfo(@RequestBody ProjectAircraft projectAircraft) {
		return projectService.getProjectAircraft(projectAircraft);
	}
	
	/**
	 * 
	 * @Description 导出项目信息
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ProjectInfo.xls")
	public String export(Project project, HttpServletRequest request,Model model) throws BusinessException {
		try {
			project.setDprtcode(new String (project.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			project.setPagination(p);
			Map<String, Object> resultMap = projectService.getProjectList(project);
			List<Customer> list = (List<Customer>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "xmxx", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
