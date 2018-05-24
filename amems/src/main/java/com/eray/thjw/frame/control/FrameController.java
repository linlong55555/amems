package com.eray.thjw.frame.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.control.BaseController;

/**
 * @Description 页面嵌入接口控制器
 * @CreateTime 2018年1月31日 下午5:12:24
 * @CreateBy 韩武
 */
@Controller				
@RequestMapping("/frame")
public class FrameController extends BaseController {
	
	/**
	 * @Description 跳转到飞行记录本主页面
	 * @CreateTime 2018年1月31日 下午5:12:47
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/flb/main", method = RequestMethod.GET)
	public ModelAndView flbmain(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("frame/flb/flightlogbook_main", responseParamMap);
	}
	
	/**
	 * @Description 跳转到飞行记录本查看页面
	 * @CreateTime 2018年2月1日 上午9:37:58
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/flb/view", method = RequestMethod.GET)
	public ModelAndView flbview(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("frame/flb/flightlogbook_view", responseParamMap);
	}
}
