package com.eray.thjw.productionplan.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.productionplan.po.SpecialFlightCondition;
import com.eray.thjw.productionplan.service.SpecialFlightConditionService;

/**
 * 
 * @author hanwu
 * @description 特殊飞行情况
 * @develop date 2016年9月18日
 */
@Controller
@RequestMapping("/productionplan/specialFlightCondition")
public class SpecialFlightConditionController extends BaseController{
	
	@Resource
	private SpecialFlightConditionService specialFlightConditionService;
	
	
	/**
	 * 根据飞机机型特殊飞行情况查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAll",method={RequestMethod.POST})
	public List<SpecialFlightCondition> findAll(){
		return specialFlightConditionService.findAll();
	}
}
