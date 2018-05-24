package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringLast;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.service.MaintenanceInitializationService;

import enu.produce.MaintenanceTypeEnum;
import enu.project2.MaintenanceProjectTypeEnum;

/**
 * @Description 维修计划初始化控制层 
 * @CreateTime 2017-9-11 下午1:47:40
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/produce/maintenance/initialization")
public class MaintenanceInitializationController extends BaseController{
	
	@Resource
	private MaintenanceInitializationService maintenanceInitializationService;
	
	
	/**
	 * @Description 跳转至维修计划初始化
	 * @CreateTime 2017-9-11 下午1:51:26
	 * @CreateBy 刘兵
	 * @return 页面视图
	 */
	@Privilege(code="produce:maintenance:initialization:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("maintenanceProjectTypeEnum", MaintenanceProjectTypeEnum.enumToListMap());//维修项目类型枚举
		return new ModelAndView("produce/maintenanceinitialization/maintenance_initialization_main", model);
	}
	
	/**
	 * @Description 保存监控
	 * @CreateTime 2017-9-29 下午5:30:51
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return String
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:initialization:main:01,produce:maintenance:initialization:main:02")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody MonitoringObject monitoringObject) throws BusinessException{
		try {
			return maintenanceInitializationService.save(monitoringObject);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存监控失败!",e);
		}
	}
	
	/**
	 * @Description 跳转到维修项目历史
	 * @CreateTime 2017-9-13 下午2:07:09
	 * @CreateBy 刘兵
	 * @param id 维修项目id
	 * @param model
	 * @return 页面视图
	 */
	@RequestMapping(value = "project/history", method = RequestMethod.GET)
	public ModelAndView project(String id, String fjzch, String dprtcode, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("fjzch", fjzch);
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("type", MaintenanceTypeEnum.PROJECT.getId());
		return new ModelAndView("produce/maintenanceinitialization/maintenance_history_view");
	}
	
	/**
	 * @Description 跳转到维修历史(EO)
	 * @CreateTime 2017-9-13 下午2:07:09
	 * @CreateBy 刘兵
	 * @param id EOid
	 * @param model
	 * @return 页面视图
	 */
	@RequestMapping(value = "eo/history", method = RequestMethod.GET)
	public ModelAndView eo(String id, String fjzch, String dprtcode, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("fjzch", fjzch);
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("type", MaintenanceTypeEnum.EO.getId());
		return new ModelAndView("produce/maintenanceinitialization/maintenance_history_view");
	}
	
	/**
	 * @Description 跳转到维修历史(PO)
	 * @CreateTime 2018-5-7 下午4:11:53
	 * @CreateBy 刘兵
	 * @param id POid
	 * @param model
	 * @return 页面视图
	 */
	@RequestMapping(value = "po/history", method = RequestMethod.GET)
	public ModelAndView po(String id, String fjzch, String dprtcode, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("fjzch", fjzch);
		model.addAttribute("dprtcode", dprtcode);
		model.addAttribute("type", MaintenanceTypeEnum.PRODUCTION_ORDER.getId());
		return new ModelAndView("produce/maintenanceinitialization/maintenance_history_view");
	}
	
	/**
	 * @Description 根据监控数据id查询任务信息(维修项目)
	 * @CreateTime 2017-9-28 上午10:59:11
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectRelProjectById",method={RequestMethod.POST,RequestMethod.GET})
	public MonitoringCurrent selectRelProjectById(String id) throws BusinessException {
		try {
			return maintenanceInitializationService.selectRelProjectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询任务信息失败!",e);
		}
	}
	
	/**
	 * @Description 根据监控数据id查询任务信息(EO)
	 * @CreateTime 2017-9-28 上午10:59:11
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectRelEOById",method={RequestMethod.POST,RequestMethod.GET})
	public MonitoringCurrent selectRelEOById(String id) throws BusinessException {
		try {
			return maintenanceInitializationService.selectRelEOById(id);
		} catch (Exception e) {
			throw new BusinessException("查询任务信息失败!",e);
		}
	}
	
	/**
	 * @Description 根据监控数据id查询任务信息(PO)
	 * @CreateTime 2018-5-7 下午5:05:23
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectRelPoById",method={RequestMethod.POST,RequestMethod.GET})
	public MonitoringCurrent selectRelPoById(String id) throws BusinessException {
		try {
			return maintenanceInitializationService.selectRelPoById(id);
		} catch (Exception e) {
			throw new BusinessException("查询任务信息失败!",e);
		}
	}
	
	/**
	 * @Description 根据监控数据id查询监控数据-上次执行数据
	 * @CreateTime 2017-9-28 上午10:59:11
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return List<MonitoringLast> 监控数据-上次执行数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitoringLastById",method={RequestMethod.POST,RequestMethod.GET})
	public List<MonitoringLast> queryMonitoringLastById(String id) throws BusinessException {
		try {
			return maintenanceInitializationService.queryMonitoringLastById(id);
		} catch (Exception e) {
			throw new BusinessException("查询任务信息失败!",e);
		}
	}
	
	/**
	 * @Description 根据监控数据id查询监控对象及监控数据-上次执行数据
	 * @CreateTime 2017-9-30 上午9:42:15
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringObject 监控对象
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getMonitoringObjectById",method={RequestMethod.POST,RequestMethod.GET})
	public MonitoringObject getMonitoringObjectById(String id) throws BusinessException {
		try {
			return maintenanceInitializationService.getMonitoringObjectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询监控对象失败!",e);
		}
	}
	
	/**
	 * @Description 根据监控数据id查询监控数据-(计划)执行数据
	 * @CreateTime 2017-9-28 上午10:59:11
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return List<MonitoringLast> 监控数据-上次执行数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitoringPlanByJksjid",method={RequestMethod.POST,RequestMethod.GET})
	public List<MonitoringPlan> queryMonitoringPlanByJksjid(String id) throws BusinessException {
		try {
			return maintenanceInitializationService.queryMonitoringPlanByJksjid(id);
		} catch (Exception e) {
			throw new BusinessException("查询下次计划失败!",e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)
	 * @CreateTime 2017-9-25 下午3:17:26
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object> 页面数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageMaintenanceList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageMaintenanceList(@RequestBody MonitoringCurrent monitoringCurrent)throws BusinessException {
		try {
			return maintenanceInitializationService.queryAllPageMaintenanceList(monitoringCurrent);
		} catch (Exception e) {
			throw new BusinessException("当前监控数据查询失败!",e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)
	 * @CreateTime 2017-9-25 下午3:17:26
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object> 页面数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageEOList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageEOList(@RequestBody MonitoringCurrent monitoringCurrent)throws BusinessException {
		try {
			return maintenanceInitializationService.queryAllPageEOList(monitoringCurrent);
		} catch (Exception e) {
			throw new BusinessException("当前监控数据查询失败!",e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)
	 * @CreateTime 2018-5-7 下午3:31:59
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object> 页面数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPagePOList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPagePOList(@RequestBody MonitoringCurrent monitoringCurrent)throws BusinessException {
		try {
			return maintenanceInitializationService.queryAllPagePOList(monitoringCurrent);
		} catch (Exception e) {
			throw new BusinessException("当前监控数据查询失败!",e);
		}
	}
	
}
