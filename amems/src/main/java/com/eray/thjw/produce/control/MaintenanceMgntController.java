package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.MaintenanceReportConfig;
import com.eray.thjw.produce.service.MaintenanceMgntService;
import com.eray.thjw.util.WordExportUtils;

import enu.material2.ExportFileTempletEnum;

/**
 * @Description 维修管理controller
 * @CreateTime 2018年4月24日 下午1:49:43
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/produce/maintenancemgnt")
public class MaintenanceMgntController extends BaseController {
	
	@Resource
	private MaintenanceMgntService maintenanceMgntService;
	
	/**
	 * @Description 跳转到维修执管月报
	 * @CreateTime 2018年4月24日 下午1:51:51
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "produce:maintenancemgnt:monthlyreport")
	@RequestMapping(value = "/monthlyreport", method = RequestMethod.GET)
	public ModelAndView toMonthlyReport(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/maintenancemgnt/monthlyreport", responseParamMap);
	}
	
	/**
	 * @Description 查询维修执管月报
	 * @CreateTime 2018年4月24日 下午4:03:46
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/querymonthlyreport", method = RequestMethod.POST)
	public Map<String, Object> queryMonthlyReport(@RequestBody MaintenanceReportConfig config) throws BusinessException{
		try {
			return maintenanceMgntService.queryMonthlyReport(config);
		} catch (Exception e) {
			throw new BusinessException("查询维修执管月报失败!",e);
		}
	}
	
	/**
	 * @Description 保存工时费用设置
	 * @CreateTime 2018年4月26日 上午11:05:11
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public MaintenanceReportConfig save(@RequestBody MaintenanceReportConfig config) throws BusinessException{
		try {
			return maintenanceMgntService.doSave(config);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存工时费用设置失败!",e);
		}
	}
	
	/**
	 * @Description 查询工时费用设置
	 * @CreateTime 2018年4月26日 下午1:49:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public MaintenanceReportConfig queryDetail(@RequestBody MaintenanceReportConfig config) throws BusinessException{
		try {
			return maintenanceMgntService.queryDetail(config);
		} catch (Exception e) {
			throw new BusinessException("查询工时费用设置失败!",e);
		}
	}
	
	/**
	 * @Description 导出word
	 * @CreateTime 2018年4月27日 下午1:48:08
	 * @CreateBy 韩武
	 * @param paramjson
	 * @param request
	 * @param model
	 * @param response
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/exportWord", method = RequestMethod.GET)
	public void exportWord(String paramjson, HttpServletRequest request, Model model, HttpServletResponse response) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    MaintenanceReportConfig config = JSON.parseObject(paramjson, MaintenanceReportConfig.class);
		    Map<String, Object> map = maintenanceMgntService.queryMonthlyReport(config);
		    String templetFile = ExportFileTempletEnum.MONTHLYREPORT.getName();
		    WordExportUtils.exportWord(response, map, config.getExportName(), templetFile);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
