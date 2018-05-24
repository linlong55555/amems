package com.eray.thjw.training.control;

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
import com.eray.thjw.po.User;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.service.PlanService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.course.CycleEnum;

/**
 * 
 * 培训通知控制器
 * @author ll
 *
 */
@Controller
@RequestMapping("/training/trainingnotice")
public class TrainingNoticeController  extends BaseController{
	
	@Resource
	private PlanService planService;
	
	/**
	 * 跳转至培训通知界面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="training:trainingnotice:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/training/trainingnotice/trainingnotice_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 培训通知列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "trainingnoticeList", method = RequestMethod.POST)
	public Map<String, Object> trainingnoticeList(@RequestBody TrainingPlan trainingPlan,HttpServletRequest request,Model model) throws BusinessException{
		String id = trainingPlan.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		User user = ThreadVarUtil.getUser();
		trainingPlan.setId(null);
		trainingPlan.setJsid(user.getId());
		PageHelper.startPage(trainingPlan.getPagination());
		List<TrainingPlan> list = planService.queryAllPagetrainingnoticeList(trainingPlan);
		
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					TrainingPlan trainingPlan1 = new TrainingPlan();
					trainingPlan1.setId(id);
					List<TrainingPlan> newRecordList = planService.queryAllPagetrainingnoticeList(trainingPlan1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, trainingPlan.getPagination());
		}else{
			List<TrainingPlan> newRecordList = new ArrayList<TrainingPlan>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				TrainingPlan trainingPlan1 = new TrainingPlan();
				trainingPlan1.setId(id);
				newRecordList = planService.queryAllPagetrainingnoticeList(trainingPlan1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, trainingPlan.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, trainingPlan.getPagination());
		}
	}
	
	
	/**
	 * 通过培训通知查询培训计划
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception ll
	 */
	@RequestMapping("find/{id}")
	public ModelAndView find(@PathVariable("id") String id, HttpServletRequest request,HttpServletResponse resp,Map<String, Object> model) throws Exception {
		try {
			model.put("id", id);
			return new ModelAndView("/training/trainingnotice/trainingnotice_find", model);
		} catch (RuntimeException e) {
			throw new BusinessException("培训计划页面加载失败",e);
		}
	}
}
