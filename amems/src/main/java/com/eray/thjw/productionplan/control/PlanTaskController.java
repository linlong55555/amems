

package com.eray.thjw.productionplan.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.PlanTask;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.po.PlanTaskHistory;
import com.eray.thjw.productionplan.service.PlanTaskService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.SessionUtil;

import enu.DefectStatisticalType;
import enu.PlanTaskSubType;
import enu.PlanTaskType;
import enu.SortEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author zhuchao
 * @description 计划任务控制器
 */
@Controller
@RequestMapping("/productionplan/plantask")
public class PlanTaskController extends BaseController {
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private PlanTaskService planTaskService;
	
	/**
	 * 跳转至计划任务管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:plantask:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage() throws BusinessException {
		return new ModelAndView("productionplan/plantask/plantask_manage");
	}
	
	/**
	 * 查询计划任务
	 * @param planTask
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:manage")
	@ResponseBody
	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	public Map<String, Object> queryList( PlanTaskExt planTask,HttpServletRequest request) throws BusinessException {
		 User user = SessionUtil.getUserFromSession(request);
		 planTask.getParamsMap().put("userId", user.getId());
		 planTask.getParamsMap().put("userType", user.getUserType());
		 return planTaskService.queryPlanTaskList(planTask);
	}
	
	/**
	 * 进入指定结束页面
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:manage:02")
	@RequestMapping(value = "/end", method = RequestMethod.GET)
	public ModelAndView intoEnd(String id) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		PlanTask plantask = planTaskService.selectByPrimaryKey(id);
		model.put("plantask", plantask);
		return new ModelAndView("productionplan/plantask/plantask_end", model);
	}
	
	/**
	 * 计划任务指定结束
	 * @param planTask
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:manage:02")
	@ResponseBody
	@RequestMapping(value="/end",method={RequestMethod.POST})
	public Map<String, Object> end(@RequestBody PlanTaskExt planTask) throws BusinessException {
		Map<String, Object> result = planTaskService.doEnd(planTask);
		return result;
	}
	
	/**
	 * 修改监控备注
	 * @param planTask
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:manage:01")
	@ResponseBody
	@RequestMapping(value="/editJkRemark",method={RequestMethod.POST})
	public void editJkRemark(PlanTask planTask) throws BusinessException {
		 planTaskService.editJkRemark(planTask);
	}
	
	/**
	 * 计划任务完成
	 * @param planTask
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:planpanel:01")
	@ResponseBody
	@RequestMapping(value="/complete",method={RequestMethod.POST})
	public Map<String, Object> complete(PlanTask planTask) throws BusinessException {
		Map<String, Object> result = planTaskService.doComplete(planTask);
		return result;
	}
	 
	/**
	 * 进入计划看板
	 * @return 
	 */
	@Privilege(code="productionplan:plantask:planpanel")
	@RequestMapping(value = "/planpanel", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView planpanel(PlanTask planTask) throws BusinessException {
		return new ModelAndView("productionplan/plantask/plantask_planpanel");
	}
	
	/**
	 * 查询计划看板
	 * @param planTask
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="productionplan:plantask:planpanel")
	@RequestMapping(value = "/planpanelList", method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> planpanelList(PlanTask planTask,HttpServletRequest request) throws BusinessException {
		User user = SessionUtil.getUserFromSession(request);
		planTask.getParamsMap().put("userId", user.getId());
		planTask.getParamsMap().put("userType", user.getUserType());
		Map<String, Object> result =  planTaskService.queryPanelSummary(planTask);
		return result;
	}
	
	
	/**
	 * 任务历史清单页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:plantask:history")
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ModelAndView history() throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("planTaskType", PlanTaskType.enumToListMap());
			model.put("planTaskSubType", PlanTaskSubType.enumToListMap());
			return new ModelAndView("productionplan/plantask/plantask_history",model);
		} catch (Exception e) {
			 throw new BusinessException("进入历史任务清单页失败");
		}
	}
	
	/**
	 * 计划任务历史数据列表
	 * @param planTask
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:history")
	@ResponseBody
	@RequestMapping(value="/queryHistoryList",method={RequestMethod.POST})
	public Map<String, Object> queryHistoryList(PlanTaskHistory planTask,HttpServletRequest request) throws BusinessException {
		User user = SessionUtil.getUserFromSession(request);
		planTask.getParamsMap().put("userId", user.getId());
		planTask.getParamsMap().put("userType", user.getUserType());
		return 	planTaskService.queryHistoryList(planTask);
	}
	
	/**
	 * 航材工具缺件统计页面
	 * @param request
	 * @param planTaskIds
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="productionplan:plantask:manage:04")
	@RequestMapping(value = "/hcStatistics", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView hcStatistics(HttpServletRequest request,  String fjzch,String dprtcode,String isLoadedParts,@RequestParam(value="planTaskIds") String[]planTaskIds) throws BusinessException {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("planTaskIds", planTaskIds);
			User user = SessionUtil.getUserFromSession(request);
			PlanTask planTask = new PlanTask();
			planTask.setIdEquals(Arrays.asList(planTaskIds));
			planTask.getParamsMap().put("userId", user.getId());
			planTask.getParamsMap().put("userType", user.getUserType());
			planTask.setDprtcode(dprtcode);
			planTask.setIsLoadedParts(isLoadedParts);
			planTask.setFjzch(fjzch);
			List<PlanTask> tasks = planTaskService.queryAllList(planTask );
			model.put("tasks", tasks);
			return new ModelAndView("productionplan/plantask/plantask_hcstatistics",model);
		} catch (Exception e) {
			 throw new BusinessException("进入航材缺件统计页面失败");
		}
		
	}
	
	 
	/**
	 * 航材工具缺件查询
	 * @param request
	 * @param planTaskIds
	 * @param defectStatisticalType
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code = "productionplan:plantask:manage:04")
	@RequestMapping(value = "/hcStatisticsData", method = RequestMethod.POST)
	public Map<String, Object> hcStatistics(HttpServletRequest request,
			@RequestParam("planTaskIds[]") List<String> planTaskIds,
			@RequestParam(value = "defectStatisticalType", required = true) DefectStatisticalType defectStatisticalType,
			@RequestParam(value = "type", required = true) String type)
			throws BusinessException {
		Map<String, Object> result = null;
		try {
			HCMainData hcMainData = new HCMainData();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("planTaskIds", planTaskIds);
			paramsMap.put("hclxs", defectStatisticalType.getHcLxs());
			paramsMap.put("type", type);
			hcMainData.setParamsMap(paramsMap);
			if (DefectStatisticalType.HC_DEFECT.equals(defectStatisticalType)) {
				result = planTaskService.hcStatistics(hcMainData);
			}
			else {
				result = planTaskService.toolStatistics(hcMainData);
			}
			return result;
		} catch (BusinessException e) {
			throw e;
		}
		catch (Exception e) {
			throw new BusinessException("航材缺件统计失败",e);
		}
	}
	
	@RequestMapping(value="planTask.xls")
	public String export(PlanTaskExt planTask,HttpServletRequest request,Model  model,HttpServletResponse response) throws BusinessException {
		try { 
			@SuppressWarnings("unchecked")
			List<PlanTaskExt> list = (List<PlanTaskExt>)planTaskService.queryPlanTaskList(planTask).get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);  
		    model.addAttribute("jrMainDataSource", jrDataSource); 
		    return outReport("xls", planTask.getDprtcode(), "MaintenancePlan", model);
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
	}
	
	@RequestMapping(value="/exportftp/{type}")
	public String exportftp(@PathVariable String type,PlanTaskExt planTask,HttpServletRequest request,Model  model) throws BusinessException {
		try { 
			planTask.setDprtcode(SessionUtil.getUserFromSession(request).getJgdm());
		    return outReport(type, "common", "reportftp", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
		
	}
	
	/**
	 * 任务历史清单导出
	 * @param keyword
	 * @param dprtcode
	 * @param fjzch
	 * @param dprtname
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="historyOut.xls")
	public String ypoutExcel(PlanTaskHistory planTask, HttpServletRequest request,Model  model) throws BusinessException {
		try { 
			User user = SessionUtil.getUserFromSession(request);
			planTask.getParamsMap().put("userId", user.getId());
			planTask.getParamsMap().put("userType", user.getUserType());
			Pagination p=new Pagination();
			p.setOrder(SortEnum.AUTO.getName());
			p.setRows(Integer.MAX_VALUE);
			planTask.setPagination(p);
			List<PlanTaskHistory> list=(List<PlanTaskHistory>) planTaskService.queryHistoryList(planTask).get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);  
		    model.addAttribute("jrMainDataSource", jrDataSource); 
			return outReport("xls", "common", "PlanTask_history", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
	}
	
	/**
	 * 检查计划任务显示状态是否已完成
	 * @param id  
	 * @param idSource id来源 (1定检工单、2非例行工单、3EO工单 、4定检任务单、5计划任务)
	 * 
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/alreadyCompleted",method={RequestMethod.POST})
	public Map<String,Object> alreadyCompleted(String id, String idSource) throws BusinessException {
		try {
			Map<String,Object> result = new HashMap<String, Object>();			
			boolean alreadyCompleted = planTaskService.alreadyCompleted(id, idSource);
			result.put("alreadyCompleted", alreadyCompleted);
			return result;
		} catch (Exception e) {
			 throw new BusinessException("计划任务指定结束失败",e);
		}
	}
}
