package com.eray.thjw.produce.control;

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
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.ThresholdAir;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.produce.service.ThresholdAirService;

/**
 * @Description 生产监控预警设置Controller
 * @CreateTime 2017年9月9日 下午5:43:05
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/monitorsetting")
public class AircraftMonitorSettingController {

	@Resource
	private AircraftinfoService aircraftinfoService;
	
	@Resource
	private ThresholdAirService thresholdAirService;
	
	/**
	 * @Description 生产监控预警设置列表跳转
	 * @CreateTime 2017年9月18日 上午10:25:18
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:monitorsetting:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/aircraftmonitorsetting/aircraftmonitorsetting_main",model);
		} catch (Exception e) {
			throw new BusinessException("生产监控预警设置列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 生产监控预计设置列表加载
	 * @CreateTime 2017年9月19日 上午11:39:00
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = aircraftinfoService.queryAllPageList(aircraftinfo);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("生产监控预计设置加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据飞机注册号和组织机构查询系统阀值设置-飞机数据
	 * @CreateTime 2017年9月19日 下午3:02:00
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitorSettingByfjzch", method = RequestMethod.POST)
	public List<ThresholdAir> queryMonitorSettingByfjzch(@RequestBody ThresholdAir thresholdAir,HttpServletRequest request,Model model)throws BusinessException{
		try {
			List<ThresholdAir> thresholdAirList= thresholdAirService.queryMonitorSettingByfjzch(thresholdAir);
			return thresholdAirList;
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值设置-飞机数据失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 保存监控设置
	 * @CreateTime 2017年9月19日 下午5:09:10
	 * @CreateBy 林龙
	 * @param thresholdAir
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody ThresholdAir thresholdAir) throws BusinessException{
		try {
			return thresholdAirService.save(thresholdAir);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存监控设置失败!",e);
		}
	}
}
