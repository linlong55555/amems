package com.eray.thjw.project2.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.TEApplicability;
import com.eray.thjw.project2.service.AirworthinessService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils.Json;

import enu.ThresholdEnum;
import enu.project2.AirworthinessMonitorStatusEnum;
/**
 * 
 * @Description 适航资料控制层
 * @CreateTime 2017-8-18 下午8:13:54
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/project2/airworthiness")
public class AirworthinessController extends BaseController{
	
	@Resource
	private MonitorsettingsService monitorsettingsService;
	@Resource
	private AirworthinessService airworthinessService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private UserService userService;
	@Resource
	private AircraftinfoService aircraftinfoService;

	/**
	 * 跳转至适航性资料界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="project2:airworthiness:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("project2/airworthiness/airworthiness_main",model);
	}
	
	/**
	 * 
	 * @Description 跳转到查看界面
	 * @CreateTime 2017-8-15 下午6:43:19
	 * @CreateBy 孙霁
	 * @param id
	 * @return airworthiness_view
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,@RequestParam String id)throws BusinessException {
		try {
			Airworthiness airworthiness = airworthinessService.selectById(id);
			model.addAttribute("airworthiness", airworthiness);
			return new ModelAndView("project2/airworthiness/airworthiness_view");
		} catch (BusinessException e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody Airworthiness airworthiness,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = airworthinessService.queryAll(airworthiness);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 新增适航性资料
	 * @CreateTime 2017-8-15 下午6:45:27
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@Privilege(code="project2:airworthiness:main:01")
	public String add(@RequestBody Airworthiness airworthiness,HttpServletRequest request) throws BusinessException{
		try {
			return airworthinessService.insert(airworthiness);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("数据添加失败！",e);
		}
	}
	/**
	 * 
	 * @Description 修改适航性资料
	 * @CreateTime 2017-8-15 下午6:46:57
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@Privilege(code="project2:airworthiness:main:02")
	public String edit(@RequestBody Airworthiness airworthiness,HttpServletRequest request) throws BusinessException{
		try {
			return airworthinessService.update(airworthiness);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("数据修改失败！",e);
		}
	}
	/**
	 * 
	 * @Description 作废适航性资料
	 * @CreateTime 2017-8-15 下午6:47:09
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("invalid")
	@Privilege(code="project2:airworthiness:main:03")
	public Map<String, Object> invalid(HttpServletRequest request,String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = airworthinessService.deleteRecord(id);
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("作废失败！",e);
		}
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-9-14 下午5:27:23
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectById")
	public Map<String, Object> selectById(HttpServletRequest request,String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("airworthiness",  airworthinessService.selectById(id));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 适航性资料监控页面
	 * @CreateTime 2017年10月11日 上午10:38:59
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Privilege(code="project2:airworthiness:monitoring:main")
	@RequestMapping(value = "monitoring/main", method = RequestMethod.GET)
	public ModelAndView monitoring() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ztEnum", AirworthinessMonitorStatusEnum.enumToListMap());
		model.put("fjList", aircraftinfoService.getFjByDprtcode(ThreadVarUtil.getUser().getJgdm()));
		return new ModelAndView("project2/airworthiness/airworthiness_monitoring_main",model);
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
	 * @Description AD适航性资料监控页面
	 * @CreateTime 2018-4-2 下午2:08:25
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="project2:airworthiness:admonitoring:main")
	@RequestMapping(value = "admonitoring/main", method = RequestMethod.GET)
	public ModelAndView admonitoring() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ztEnum", AirworthinessMonitorStatusEnum.enumToListMap());
		model.put("fjList", aircraftinfoService.getFjByDprtcode(ThreadVarUtil.getUser().getJgdm()));
		return new ModelAndView("project2/airworthiness/airworthiness_admonitoring_main",model);
	}
	/**
	 * 
	 * @Description AD适航性资料监控列表
	 * @CreateTime 2018-4-2 下午2:08:07
	 * @CreateBy 孙霁
	 * @param baseEntity
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "admonitoring/queryAllAd", method = RequestMethod.POST)
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
	 * @Description 适航性资料监控关闭
	 * @CreateTime 2017年10月11日 下午3:13:35
	 * @CreateBy 岳彬彬
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:airworthiness:monitoring:main:09")
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
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017-12-7 上午10:56:32
	 * @CreateBy 孙霁
	 * @param zoneStation
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:airworthiness:main:04")
	@RequestMapping(value = "airworthiness.xls")
	public String airworthinessExcel(Airworthiness airworthiness,Model model)throws BusinessException {
		try {
			List<Airworthiness> list =airworthinessService.getAirworthinessList(airworthiness);
			String wjmc="airworthiness";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	@Privilege(code = "project2:airworthiness:monitoring:main:02")
	@RequestMapping(value = "CADMonitor.xls")
	public String exportCadExcel(String params,Model model)throws BusinessException {
		try {
			BaseEntity baseEntity=Json.toObject(params, BaseEntity.class);
			List<Map<String,Object>> list=airworthinessService.exportCadList(baseEntity);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "cad", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
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
	/**
	 * 
	 * @Description 关闭技术文件监控
	 * @CreateTime 2018-4-3 上午10:34:30
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("closeMonitoring")
	@Privilege(code="project2:airworthiness:main:03")
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
	 * @CreateTime 2018-4-3 上午10:34:30
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("closeApplyMonitoring")
	@Privilege(code="project2:airworthiness:main:03")
	public void closeApplyMonitoring(@RequestBody TEApplicability  tEApplicability ,HttpServletRequest request) throws BusinessException {
		try {
			airworthinessService.closeApplyMonitoring(tEApplicability);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭适用性监控失败！",e);
		}
	}
}
