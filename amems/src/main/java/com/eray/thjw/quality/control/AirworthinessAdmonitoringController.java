package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.TEApplicability;
import com.eray.thjw.project2.service.AirworthinessService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils.Json;

import enu.project2.AirworthinessMonitorStatusEnum;

/** 
 * @Description 
 * @CreateTime 2018-4-12 上午10:42:08
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("/quality/airworthiness/admonitoring")
public class AirworthinessAdmonitoringController extends BaseController{

	@Resource
	private AircraftinfoService aircraftinfoService;
	@Resource
	private AirworthinessService airworthinessService;
	/**
	 * 
	 * @Description AD适航性资料监控页面
	 * @CreateTime 2018-4-12 上午10:46:05
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="quality:airworthiness:admonitoring:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView admonitoring() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ztEnum", AirworthinessMonitorStatusEnum.enumToListMap());
		model.put("fjList", aircraftinfoService.getFjByDprtcode(ThreadVarUtil.getUser().getJgdm()));
		return new ModelAndView("quality/airworthinessAdmonitoring/airworthiness_admonitoring_main",model);
	}
	/**
	 * 
	 * @Description  AD适航性资料监控列表
	 * @CreateTime 2018-4-12 上午10:48:46
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllAd", method = RequestMethod.POST)
	public Map<String, Object> queryAllAd(@RequestBody Airworthiness airworthiness,HttpServletRequest request,Model model)throws BusinessException{
		try {		
			Map<String, Object> resultMap = airworthinessService.queryAllAd(airworthiness);						
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 关闭技术文件监控
	 * @CreateTime 2018-4-12 上午10:49:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @param zt
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("closeMonitoring")
	public void closeMonitoring(HttpServletRequest request,String id,Integer zt) throws BusinessException {
		try {
			airworthinessService.closeMonitoring(id,zt);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭技术文件监控失败！",e);
		}
	}
	/**
	 * 
	 * @Description 关闭适用性监控
	 * @CreateTime 2018-4-12 上午10:49:59
	 * @CreateBy 孙霁
	 * @param tEApplicability
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("closeApplyMonitoring")
	public void closeApplyMonitoring(@RequestBody TEApplicability  tEApplicability ,HttpServletRequest request) throws BusinessException {
		try {
			airworthinessService.closeApplyMonitoring(tEApplicability);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭适用性监控失败！",e);
		}
	}
	/**
	 * 
	 * @Description ad导出
	 * @CreateTime 2018-4-12 下午5:23:49
	 * @CreateBy 孙霁
	 * @param params
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "ADMonitor.xls")
	public String exportAdExcel(String params,Model model)throws BusinessException {
		try {
			Airworthiness airworthiness=Json.toObject(params, Airworthiness.class);
			List<Airworthiness> list=airworthinessService.exportAdList(airworthiness);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "ad", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
