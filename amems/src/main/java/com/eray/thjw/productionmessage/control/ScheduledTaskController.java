package com.eray.thjw.productionmessage.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.ScheduledTask;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.ScheduledTaskSerivce;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

import enu.SortEnum;
import enu.ThresholdEnum;

/**
 * 
 * @author 林龙
 * @description 工单工时控制器
 */
@Controller
@RequestMapping("/productionmessage/workersimplex")
public class ScheduledTaskController extends BaseController {
	
	@Resource
	private ScheduledTaskSerivce scheduledTaskSerivce;
	
	@Resource
	private PlaneDataService planeDataService; 
	
	@Resource
	private UserService userService; 
	
	@Resource
	private MonitorsettingsService monitorsettingsService; 
	/**
	 * 跳转至工单工时管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="productionmessage:workersimplex:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(HttpServletRequest request,String fjzch) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		if(fjzch!=null&&!fjzch.equals("")){
			model.put("fjzch1",fjzch);
		}
		return new ModelAndView("/productionmessage/workersimplex/workersimplex_main", model);
	}
	
	/**
	 * @author ll
	 * @description 根据dprtcode查询系统阀值
	 * @param dprtcode
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "getByKeyDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getByKeyDprtcode(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GDGS.getName(),dprtcode));
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值失败!",e);
		}
		return model;
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 工单工时列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "scheduledTaskList", method = RequestMethod.POST)
	public Map<String, Object> scheduledTaskList(@RequestBody ScheduledTask scheduledTask,HttpServletRequest request,Model model) throws BusinessException{
		String id = scheduledTask.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		scheduledTask.setId(null);
		
		scheduledTask.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		scheduledTask.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		
		PageHelper.startPage(scheduledTask.getPagination());
		List<ScheduledTask> list = scheduledTaskSerivce.queryAllPageList(scheduledTask);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			//分页查询
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ScheduledTask scheduledTask1 = new ScheduledTask();
					scheduledTask1.setId(id);
					List<ScheduledTask> newRecordList = scheduledTaskSerivce.queryAllPageList(scheduledTask1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, scheduledTask.getPagination());
		}else{
			List<ScheduledTask> newRecordList = new ArrayList<ScheduledTask>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ScheduledTask scheduledTask1 = new ScheduledTask();
				scheduledTask1.setId(id);
				newRecordList = scheduledTaskSerivce.queryAllPageList(scheduledTask1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, scheduledTask.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, scheduledTask.getPagination());
		}
	
	}
	
	/**
	 * 初始化确认界面
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	@Privilege(code="productionmessage:workersimplex:manage:01")
	@RequestMapping("initModify/{id}")
	public ModelAndView initModify(@PathVariable("id") String id,HttpServletRequest request,
			HttpServletResponse resp) throws  Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		ScheduledTask scheduledTask=new ScheduledTask();
		scheduledTask.setId(id);
		Pagination pagination=new Pagination();
		pagination.setPage(1);
		pagination.setOrder(SortEnum.DESC.getName());
		pagination.setSort("auto"); 
		pagination.setRows(10);
		scheduledTask.setPagination(pagination);
	    List<ScheduledTask> scheduledTasklist= scheduledTaskSerivce.queryAllPageList(scheduledTask);
		
	    if(scheduledTasklist.get(0).getFxrq()==null){
	    	 scheduledTasklist.get(0).setFxrq("");
	    }else{
	    	scheduledTasklist.get(0).setFxrq(scheduledTasklist.get(0).getFxrq().substring(0,10));
	    }
	    
		model.put("scheduledTasklist", scheduledTasklist.get(0));
		
		User user=new User();
		user.setJgdm(scheduledTasklist.get(0).getDprtcode());
		List<User> users=userService.queryUserAll(user);
		
		model.put("userToRole", users);
		return new ModelAndView("/productionmessage/workersimplex/workersimplex_update", model);
	}
	
	/**
	 * 修改确认界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("modifyWorkersimplex")
	public Map<String, Object> modifyWorkersimplex(HttpServletRequest request,HttpServletResponse response,ScheduledTask scheduledTask) throws Exception {
		
		return scheduledTaskSerivce.modify(scheduledTask);
	}
	
	/**
	 * 审核通过确认界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("subWorkersimplex")
	public Map<String, Object> subWorkersimplex(HttpServletRequest request,HttpServletResponse response,ScheduledTask scheduledTask) throws Exception {
		
		return scheduledTaskSerivce.subWorkersimplex(scheduledTask);
	}
	 
}
