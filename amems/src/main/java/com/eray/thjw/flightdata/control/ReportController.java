package com.eray.thjw.flightdata.control;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.flightdata.service.ReportService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.SessionUtil;
import com.google.gson.Gson;

/**
 * 报表<br/>
 * 减速器滑耗（机身减速器滑油消耗量统计）<br/>
 * 发动机健康监控
 * @author xu.yong
 *
 */
@Controller(value="flightdataController")
@RequestMapping("/flightdata/report")
public class ReportController {
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private ReportService flightdataReportService;
	
	/**
	 * 页面跳转：减速器滑耗（机身减速器滑油消耗量统计）
	 * @param model
	 * @return
	 */
	@Privilege(code="flightdata:report:reducerOilConsumption")
	@RequestMapping("/reducerOilConsumption")
	public String reducerOilConsumption(Model model, HttpServletRequest request){
		
		model.addAttribute("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		
		return "flightdata/reports/reducer_oil_consumption";
	} 
	
	/**
	 * 页面跳转：发动机健康监控
	 * @param model
	 * @return
	 */
	@Privilege(code="flightdata:report:engineHealthMonitor")
	@RequestMapping("/engineHealthMonitor")
	public String engineHealthMonitor(Model model, HttpServletRequest request){
		
		model.addAttribute("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		
		return "flightdata/reports/engine_health_monitor";
	} 
	
	/**
	 * 查询减速器滑耗（机身减速器滑油消耗量统计）/发动机健康监控
	 * @param map
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody Map<String, Object> list(@RequestBody Map<String, Object> map){
		return this.flightdataReportService.query(map);
	}
	
}
