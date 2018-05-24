package com.eray.thjw.project2.control;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.TaskReplace;
import com.eray.thjw.project2.service.TaskReplaceService;

/**
 * @Description 任务替代Controller
 * @CreateTime 2017年12月11日 
 * @CreateBy 刘邓
 */


@Controller
@RequestMapping("/produce/maintenance/taskreplace")
public class TaskReplaceController extends BaseController{

@Autowired	
private TaskReplaceService taskReplaceService;
	
	@Privilege(code = "produce:maintenance:taskreplace:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/maintenancetaskreplace/maintenance_taskreplace_main", responseParamMap);
	}	
	
	@ResponseBody
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public List<TaskReplace> query(@RequestBody TaskReplace taskReplace) throws BusinessException{
		List<TaskReplace> list=taskReplaceService.queryAll(taskReplace);	
	                      return list;
	}
	
	@Privilege(code = "produce:maintenance:taskreplace:save")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public int save(@RequestBody TaskReplace taskReplace) throws BusinessException{
		taskReplaceService.save(taskReplace);      
		return 0;
	}
	
	@ResponseBody
	@RequestMapping(value = "initProjectByid", method = RequestMethod.POST)
	public List<TaskReplace> initProjectByid(@RequestBody TaskReplace taskReplace) throws BusinessException{
		return taskReplaceService.initProjectByid(taskReplace);
	}
	
	@ResponseBody
	@RequestMapping(value = "initProjectWindow", method = RequestMethod.POST)
	public Map<String, Object> initProjectWindow(@RequestBody TaskReplace taskReplace) throws BusinessException{
		Map<String, Object> map = taskReplaceService.initProjectWindow(taskReplace);
		if (map != null)
			System.out.println(map.size());
	    	return map;
	}
	
}
