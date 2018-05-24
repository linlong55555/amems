package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.TaskhistoryService;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.ThresholdEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-11 上午10:57:56
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("/produce/taskhistory")
public class TaskhistoryController extends BaseController{

	@Resource
	private DepartmentService departmentService;
	@Resource
	private TaskhistoryService taskhistoryService;
	
	/**
	 * 
	 * @Description 跳转到历史任务
	 * @CreateTime 2017-9-11 上午10:59:39
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="produce:taskhistory:main")
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
		return new ModelAndView("produce/taskhistory/taskhistory_main",model);
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
	public Map<String, Object> queryAll(@RequestBody Workorder workorder,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return taskhistoryService.queryAll(workorder);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "queryAllRecordByGdid", method = RequestMethod.POST)
	public Map<String, Object> queryAllRecordByGdid(String gdid,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", taskhistoryService.queryAllRecordByGdid(gdid));
			return map;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "selectGdBygdid", method = RequestMethod.POST)
	public Map<String, Object> selectGdBygdid(String gdid,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("workOrder", taskhistoryService.selectGdBygdid(gdid));
			return map;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "queryAllRecord145ByGdid", method = RequestMethod.POST)
	public Map<String, Object> queryAllRecord145ByGdid(String gdid,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", taskhistoryService.queryAllRecord145ByGdid(gdid));
			return map;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017-12-21 下午2:40:30
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:taskhistory:main:01")
	@RequestMapping(value = "taskhistory.xls")
	public String taskhistoryExcel(String paramjson,Model model)throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Workorder workorder = Utils.Json.toObject(paramjson, Workorder.class);
			List<Workorder> list =taskhistoryService.getList(workorder);
			String wjmc="taskhistory";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
