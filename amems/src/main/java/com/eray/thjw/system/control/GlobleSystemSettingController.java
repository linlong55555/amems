package com.eray.thjw.system.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.system.po.GlobleSystemConfig;
import com.eray.thjw.system.service.GlobleSystemConfigService;
import com.eray.thjw.system.service.GlobleSystemInfoService;

@Controller
@RequestMapping("sys/settings")
public class GlobleSystemSettingController {
	@Autowired
	private GlobleSystemInfoService globleSystemInfoService;
	@Autowired
	private GlobleSystemConfigService globleSystemConfigService;

	@Privilege(code = "sys:settings:main")
	@RequestMapping("main")
	public ModelAndView getIndex() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("info", globleSystemInfoService.getAll());
		mv.setViewName("sys/globleSystemSetting/globleSystemSetting_main");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/getBySyscode", method = RequestMethod.POST)
	public List<GlobleSystemConfig> getBySyscode(@RequestBody GlobleSystemConfig config, HttpServletRequest request)
			throws Exception {		
		return globleSystemConfigService.getBySyscode(config.getSyscode());
	}
	
	@Privilege(code="sys:settings:main:01")
	@ResponseBody
	@RequestMapping("/update")
	public void update(@RequestBody List<GlobleSystemConfig> list, HttpServletRequest request)
			throws Exception {
		globleSystemConfigService.updateBySyscode(list);
		globleSystemConfigService.initGlobalSettings();
	}

}
