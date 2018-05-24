package com.eray.thjw.training.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.service.BusinessService;

/**
 * 
 * @Description 维修人员培训检测
 * @CreateTime 2018-3-26 下午5:47:27
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/training/testing")
public class TrainingTestingController extends BaseController{
	
	@Resource
	private BusinessService businessService;
	
	/**
	 * 
	 * @Description 跳转维修人员培训检测
	 * @CreateTime 2018-3-26 下午5:48:00
	 * @CreateBy 孙霁
	 * @param request
	 * @return
	 */
	@Privilege(code="training:testing:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest request) {
		return new ModelAndView("training/testing/trainingTesting_main");
	}
	
	/**
	 * 
	 * @Description 根据组织机构查询岗位信息
	 * @CreateTime 2018-3-27 上午10:49:19
	 * @CreateBy 孙霁
	 * @param business
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> main(@RequestBody Business business,HttpServletRequest request)throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", businessService.queryAllBusiness(business));
		return resultMap;
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-27 下午2:03:39
	 * @CreateBy 孙霁
	 * @param business
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Map<String, Object> list(@RequestBody Business business,HttpServletRequest request)throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", businessService.queryAllResults(business));
		return resultMap;
	}
}
