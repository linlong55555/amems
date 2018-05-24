package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.baseinfo.servcice.CustomerService;
import com.eray.thjw.baseinfo.servcice.ProjectService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workpackage145;
import com.eray.thjw.produce.service.WorkOrderNew145Service;
import com.eray.thjw.produce.service.Workpackage145Service;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.produce.SourceTypeEnum;
import enu.produce.WorkpackageStatusEnum;

/**
 * 
 * @Description 维修计划145
 * @CreateTime 2017年9月12日 上午9:36:01
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/produce/maintenanceplan145")
public class MaintenancePlan145Controller {
	
	@Resource
	private Workpackage145Service workpackage145Service;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private CustomerService  customerService;;
	@Resource
	private ProjectService projectService;
	@Resource
	private WorkOrderNew145Service workOrderNew145Service;
	
	/**
	 * 
	 * @Description 维修计划
	 * @CreateTime 2017年9月12日 上午9:34:46
	 * @CreateBy 裴秀
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @UpdateBy 林龙
	 */
	@Privilege(code="produce:maintenanceplan145:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("sourceTypeEnum", SourceTypeEnum.enumToListMap());
			model.put("departmentList", departmentService.getBmList(ThreadVarUtil.getUser().getJgdm()));
			model.put("khList", customerService.getCustomerList(ThreadVarUtil.getUser().getJgdm()));
			model.put("xmList", projectService.getProjectByDprt(ThreadVarUtil.getUser().getJgdm()));
			model.put("workpackageStatusEnum", WorkpackageStatusEnum.enumToListMap());
			return new ModelAndView("/produce/maintenanceplan/145/maintenanceplan_main", model);
		} catch (Exception e) {
			throw new BusinessException("维修计划列表跳转失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 获取维修计划列表
	 * @CreateTime 2017年10月23日 下午1:41:43
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkpackage145List", method = RequestMethod.POST)
	public Map<String, Object> getWorkpackage145List(@RequestBody Workpackage145 record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workpackage145Service.getWorkpackagePlanList(record);
		} catch (Exception e) {
			throw new BusinessException("获取维修计划列表失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 工种完工注水图数据
	 * @CreateTime 2017年10月23日 下午2:51:58
	 * @CreateBy 林龙
	 * @param baseEntity
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryDiagramList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryDiagramList(@RequestBody Workorder145 workorder145,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return workOrderNew145Service.queryDiagramList(workorder145);
		} catch (Exception e) {
			throw new BusinessException("工种完工注水图加载失败!",e);
		}	
	}
	
	/**
	 * 
	 * @Description 工种执行进度图
	 * @CreateTime 2017年10月23日 下午2:51:58
	 * @CreateBy 林龙
	 * @param baseEntity
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryProgressList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryProgressList(@RequestBody Workorder145 workorder145,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return workOrderNew145Service.queryProgressList(workorder145);
		} catch (Exception e) {
			throw new BusinessException("工种执行进度图加载失败!",e);
		}	
	}
	
	/**
	 * 
	 * @Description 查询航材工具需求清单
	 * @CreateTime 2017年10月25日 上午10:10:02
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getWorpackageDetailList", method = RequestMethod.POST)
	public List<Map<String, Object>> getWorpackageDetailList(@RequestBody Workpackage145 workpackage145, HttpServletRequest request) throws BusinessException {
		try {
			return workpackage145Service.getMaterialsDetail(workpackage145);
		} catch (Exception e) {
			throw new BusinessException("查询航材工具需求清单失败", e);
		}
	}
	
	/**
	 * 
	 * @Description 修订工包
	 * @CreateTime 2017年11月24日 下午2:52:36
	 * @CreateBy 林龙
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doXDClose", method = RequestMethod.POST)
	public String doXDClose(@RequestBody Workpackage145 workpackage145) throws BusinessException {
		try {
			return workpackage145Service.doXDClose(workpackage145);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工包修订失败!", e);
		}
	}
}
