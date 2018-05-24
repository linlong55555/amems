package com.eray.thjw.quality.control;

import java.util.HashMap;
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
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.service.AirworthinessService;
import enu.project2.AirworthinessMonitorStatusEnum;
/**
 * 
 * @Description 适航资料监控控制层
 * @CreateTime 2017年10月11日 下午4:25:24
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/quality/airworthiness")
public class AirworthinessQaulityController {
	
	@Resource
	private AirworthinessService airworthinessService;
	/**
	 * 
	 * @Description 适航性资料监控页面
	 * @CreateTime 2017年10月11日 上午10:38:59
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Privilege(code="quality:airworthiness:monitoring:main")
	@RequestMapping(value = "monitoring/main", method = RequestMethod.GET)
	public ModelAndView monitoring() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ztEnum", AirworthinessMonitorStatusEnum.enumToListMap());
		return new ModelAndView("quality/airworthiness/airworthiness_monitoring_main",model);
	}
	/**
	 * 
	 * @Description 适航性资料监控列表
	 * @CreateTime 2017年10月11日 上午10:39:18
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "monitoring/queryList", method = RequestMethod.POST)
	public Map<String, Object> queryList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = airworthinessService.queryList(baseEntity);						
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 适航性资料监控关闭
	 * @CreateTime 2017年10月11日 下午3:13:35
	 * @CreateBy 岳彬彬
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "quality:airworthiness:monitoring:main:01")
	@ResponseBody
	@RequestMapping(value = "monitoring/doClose", method = RequestMethod.POST)
	public String doClose(@RequestBody Airworthiness airworthiness,HttpServletRequest request,Model model)throws BusinessException{
		try {
			airworthinessService.update4Close(airworthiness);					
			return airworthiness.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭失败！", e);
		}
	}
}
