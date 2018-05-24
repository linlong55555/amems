package com.eray.thjw.training.control;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.YearPlan;
import com.eray.thjw.training.service.YearPlanService;

/**
 * @author liub
 * @description 年度计划控制器
 */
@Controller
@RequestMapping("/training/yearplan")
public class YearPlanController extends BaseController{
	
	/**
	 * @author liub
	 * @description 年度计划Service
	 */
	@Resource
	private YearPlanService yearPlanService;
	
	
	/**
	 * @author liub
	 * @description 新增或修改年度计划
	 * @param yearPlan
	 * @throws BusinessException 
	 */
	@Privilege(code="training:course:main:02")
	@ResponseBody
	@RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
	public String addOrUpdate(@RequestBody YearPlan yearPlan) throws BusinessException{
		try {
			return yearPlanService.addOrUpdate(yearPlan);
		} catch (Exception e) {
			 throw new BusinessException("修改年度计划失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据年度、机构代码查询年度计划、附件数
	 * @param nd、dprtcode
	 * @return YearPlan
	 */
	@ResponseBody
	@RequestMapping(value = "selectByNdAndDprt",method={RequestMethod.POST,RequestMethod.GET})
	public YearPlan selectByNdAndDprt(Integer nd,String dprtcode) throws BusinessException {
		try {
			return yearPlanService.selectByNdAndDprt(nd, dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询年度计划失败!",e);
		}
	}
	
}
