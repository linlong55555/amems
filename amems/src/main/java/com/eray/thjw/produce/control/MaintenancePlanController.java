package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;

import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderTypeEnum;

/**
 * 
 * @Description 维修计划
 * @CreateTime 2017年9月12日 上午9:36:01
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/produce/maintenanceplan")
public class MaintenancePlanController {
	
	/**
	 * 
	 * @Description 维修计划
	 * @CreateTime 2017年9月12日 上午9:34:46
	 * @CreateBy 岳彬彬
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenanceplan:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workorderTypeEnum", WorkorderTypeEnum.enumToListMap());//工单类型枚举
		model.put("feedbackStatusEnum", FeedbackStatusEnum.enumToListMap());//反馈枚举
		return new ModelAndView("produce/maintenanceplan/135/maintenanceplan_main", model);
	}
}
