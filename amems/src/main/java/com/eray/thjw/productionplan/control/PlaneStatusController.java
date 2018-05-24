package com.eray.thjw.productionplan.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

/**
 * 
 * @author hanwu
 * @description 飞机状态控制器
 * @develop date 2017年4月10日
 */
@Controller
@RequestMapping("/productionplan/planestatus")
public class PlaneStatusController extends BaseController{
	
	/**
	 * 跳转至飞机状态主页面
	 * @return 
	 */
	@Privilege(code="productionplan:planestatus:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main() throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("productionplan/planestatus/planestatus_main", model);
	}
	
}
