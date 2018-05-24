package com.eray.thjw.project2.control;

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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.produce.service.FlightSheetVoyageService;
/**
 * 
 * @Description 发动机监控
 * @CreateTime 2017年10月12日 上午10:41:45
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/project2/oil/consumption/monitor")
public class EngineMonitorController {
	
	@Resource
	private FlightSheetVoyageService flightSheetVoyageService;
	/**
	 * 
	 * @Description 发动机监控视图
	 * @CreateTime 2017年10月14日 上午10:41:59
	 * @CreateBy 岳彬彬
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:oil:consumption:monitor:engine")
	@RequestMapping(value = "engine", method = RequestMethod.GET)
	public ModelAndView engine(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
		try {
			return new ModelAndView("project2/engineerMonitor/engineMonitor_main",model);
		} catch (Exception e) {
			throw new BusinessException("发动机监控列表跳转失败!",e);
		}
	}
	/**
	 * 
	 * @Description 发动机监控数据
	 * @CreateTime 2017年10月14日 上午10:42:09
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryEngineList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryEngineList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return flightSheetVoyageService.getMonitorData(baseEntity);
		} catch (Exception e) {
			throw new BusinessException("发动机监控加载失败!",e);
		}	
	}
	/**
	 * 
	 * @Description AUP监控视图
	 * @CreateTime 2017年10月14日 上午10:42:23
	 * @CreateBy 岳彬彬
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:oil:consumption:monitor:apu")
	@RequestMapping(value = "apu", method = RequestMethod.GET)
	public ModelAndView apu(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
		try {
			return new ModelAndView("project2/engineerMonitor/apuMonitor_main",model);
		} catch (Exception e) {
			throw new BusinessException("APU监控列表跳转失败!",e);
		}
	}
	/**
	 * 
	 * @Description APU监控数据
	 * @CreateTime 2017年10月14日 上午10:42:34
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryApuList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryApuList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return flightSheetVoyageService.getApuData(baseEntity);
		} catch (Exception e) {
			throw new BusinessException("APU监控加载失败!",e);
		}	
	}
}
