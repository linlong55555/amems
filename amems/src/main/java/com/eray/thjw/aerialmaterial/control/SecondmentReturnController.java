package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;

/**
 * 
 * @author 林龙
 * @description 借调归还控制器
 */
@Controller
@RequestMapping("/aerialmaterial/secondment")
public class SecondmentReturnController extends BaseController {
	
	
	@Resource
	private ExpatriateService expatriateService;
	
	/**
	 * 跳转至借调归还管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:secondment:notreturn")
	@RequestMapping(value = "/notreturn", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/secondmentreturn/secondmentreturn_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 借入归还列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "expatriateList", method = RequestMethod.POST)
	public Map<String, Object> expatriateList(@RequestBody Expatriate expatriate,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(expatriate.getPagination());
		List<Expatriate> list = this.expatriateService.queryAllPageListjie(expatriate);
		return PageUtil.pack4PageHelper(list, expatriate.getPagination());
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 借出归还列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "checkoutList", method = RequestMethod.POST)
	public Map<String, Object> checkoutList(@RequestBody Expatriate expatriate,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(expatriate.getPagination());
		List<Expatriate> list = this.expatriateService.queryAllPageListhai(expatriate);
		return PageUtil.pack4PageHelper(list, expatriate.getPagination());
	}
}
