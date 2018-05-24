package com.eray.thjw.produce.control;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.service.ProductionForecastService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.project2.MonitorProjectEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
 
/**
 * 
 * @Description 生产计划预测
 * @CreateTime 2017年9月11日 上午10:01:08
 * @CreateBy 朱超
 */
@Controller
@RequestMapping("/produce/maintenance/forecast")
public class ProductionForecastController extends BaseController{
	 
	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private ProductionForecastService productionForecastService;

	@Resource
	private CommonService commonService;
	
	
	/**
	 * 
	 * @Description 跳转至生产计划预测界面
	 * @CreateTime 2017年9月11日 上午10:04:40
	 * @CreateBy 朱超
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="produce:maintenance:forecast:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView commonalityPage() throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		//用户属于超级机构，查所有部门，否则查当前机构下的所有部门。
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		
		//准备最大截止日期
		try {
			Date date = commonService.getSysdate();
			String currentDate = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, date);
			model.put("currentDateStr", DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE).replaceAll("-", "/"));
			model.put("limitDateStr", DateUtil.getOffsetDate(currentDate, 3, Calendar.YEAR).replaceAll("-", "/"));
		} catch (ParseException e) {
			 throw new BusinessException("计算最大截止日期失败!", e);
		}	
		
		return new ModelAndView("produce/maintenanceforecast/forecast_list",model);
	}
	  
	/**
	 * 
	 * @Description 查询当前值
	 * @CreateTime 2017年10月12日 上午9:41:46
	 * @CreateBy 朱超
	 * @param aircraftinfoStatus
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "currnetInfo", method = RequestMethod.POST)
	public Map<String, Object> currnetInfo(@RequestBody AircraftinfoStatus aircraftinfoStatus,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = productionForecastService.calculationCurrent(aircraftinfoStatus);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 查询预测数据
	 * @CreateTime 2017年10月27日 上午10:24:04
	 * @CreateBy 朱超
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryList", method = RequestMethod.POST)
	public Map<String, Object> queryList(@RequestBody MonitoringCurrent record,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = productionForecastService.queryList(record);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017年12月20日 下午4:18:18
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@Privilege(code="produce:maintenance:forecast:main:01")
	@RequestMapping(value = "forecast.xls" ,method={RequestMethod.GET})
	public String exportExcel(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			MonitoringCurrent record = Utils.Json.toObject(paramjson, MonitoringCurrent.class);
			Map<String, Object> resultMap = productionForecastService.queryList(record);
			List<Map<String,Object>> list = (List<Map<String, Object>>) resultMap.get("list");
			for (Map<String, Object> map : list) {
				List<MonitoringPlan>  monitorList= (List<MonitoringPlan>) map.get("monitoringPlans");
				doJhSy(monitorList,map);
				List<Map<String, Object>> subList = (List<Map<String, Object>>) map.get("sublist");
				if(null != subList && subList.size()>0){
					for (Map<String, Object> subMap : subList) {
						List<MonitoringPlan>  subMonitorList= (List<MonitoringPlan>) subMap.get("monitoringPlans");
						doJhSy(subMonitorList,subMap);
					}
				}
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "forecast", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	/**
	 * 
	 * @Description 处理列表计划和剩余字段
	 * @CreateTime 2017年12月20日 下午4:18:58
	 * @CreateBy 岳彬彬
	 * @param monitorList
	 * @param map
	 */
	private void doJhSy(List<MonitoringPlan>  monitorList,Map<String,Object> map){
		if(null != monitorList && monitorList.size()>0){
			StringBuffer jh = new StringBuffer();//计划
			StringBuffer sy = new StringBuffer();//剩余
			for (MonitoringPlan monitoringPlan : monitorList) {
				String jklbh = monitoringPlan.getJklbh();
				String value =  monitoringPlan.getJhz();
				String scz = (String) monitoringPlan.getParamsMap().get("SYZ");
				if(null != jklbh && MonitorProjectEnum.isTime(jklbh)){
					value = StringAndDate_Util.convertToHour(value);
					scz = StringAndDate_Util.convertToHour(scz);
				}
				if(null != jklbh && MonitorProjectEnum.isCalendar(jklbh)){
					scz = scz+"D";
				}
				if(null != jklbh && !"".equals(jklbh)){
					jklbh =MonitorProjectEnum.getUnit(jklbh);
				}
				jh.append(value).append(jklbh).append(" ");
				sy.append(scz).append(jklbh).append(",");
			}
			map.put("jh", jh.substring(0, jh.length()-1).toString());		
			map.put("sy", sy.substring(0, sy.length()-1).toString());		
		}
	}
}
