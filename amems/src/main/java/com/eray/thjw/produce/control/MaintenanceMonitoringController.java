package com.eray.thjw.produce.control;

import java.util.ArrayList;
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
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringWorkpackage;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.service.MaintenanceMonitoringService;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.util.Utils;

import enu.project2.MaintenanceProjectTypeEnum;

/**
 * @Description 飞机维修监控控制层 
 * @CreateTime 2017-9-14 上午10:00:27
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/produce/maintenance/monitoring")
public class MaintenanceMonitoringController extends BaseController{
	
	@Resource
	private MaintenanceMonitoringService maintenanceMonitoringService;
	
	/**
	 * @Description 跳转至飞机维修监控
	 * @CreateTime 2017-9-14 上午10:02:50
	 * @CreateBy 刘兵
	 * @return
	 */
	@Privilege(code="produce:maintenance:monitoring:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("maintenanceProjectTypeEnum", MaintenanceProjectTypeEnum.enumToListMap());//维修项目类型枚举
		return new ModelAndView("produce/maintenancemonitoring/maintenance_monitoring_main", model);
	}
	
	/**
	 * @Description 跳转到航材工具需求清单
	 * @CreateTime 2017-9-13 下午2:07:09
	 * @CreateBy 刘兵
	 * @return 页面视图
	 */
	@RequestMapping(value = "material/tool/detail", method = RequestMethod.GET)
	public ModelAndView project() {
		return new ModelAndView("common/produce/material_tool_require_detail_view");
	}
	
	/**
	 * @Description 保存监控
	 * @CreateTime 2017-10-12 上午10:41:24
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return String
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody MonitoringObject monitoringObject) throws BusinessException{
		try {
			return maintenanceMonitoringService.save(monitoringObject);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存监控失败!",e);
		}
	}
	
	/**
	 * @Description 保存选中的待组包的监控项目
	 * @CreateTime 2017-10-13 下午5:21:53
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return int 已选中数量
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main:01")
	@ResponseBody
	@RequestMapping(value = "saveChecked", method = RequestMethod.POST)
	public int saveChecked(@RequestBody MonitoringObject monitoringObject) throws BusinessException{
		try {
			return maintenanceMonitoringService.saveChecked(monitoringObject);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存选中的待组包的监控项目失败!",e);
		}
	}
	
	/**
	 * @Description 移除选中的待组包的监控项目
	 * @CreateTime 2017-10-13 下午5:21:53
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main:02")
	@ResponseBody
	@RequestMapping(value = "deleteChecked", method = RequestMethod.POST)
	public void deleteChecked(@RequestBody MonitoringObject monitoringObject) throws BusinessException{
		try {
			maintenanceMonitoringService.deleteChecked(monitoringObject);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("移除选中的待组包的监控项目失败!",e);
		}
	}
	
	/**
	 * @Description 组包
	 * @CreateTime 2017-10-16 下午1:59:51
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main:03")
	@ResponseBody
	@RequestMapping(value = "addWorkpackage", method = RequestMethod.POST)
	public int addWorkpackage(@RequestBody Workpackage workpackage) throws BusinessException{
		try {
			return maintenanceMonitoringService.addWorkpackage(workpackage);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("组包失败!",e);
		}
	}
	
	/**
	 * @Description 添加到已有工包
	 * @CreateTime 2017-10-16 下午1:59:51
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main:03")
	@ResponseBody
	@RequestMapping(value = "add2WorkPackage", method = RequestMethod.POST)
	public int add2WorkPackage(@RequestBody Workpackage workpackage) throws BusinessException{
		try {
			return maintenanceMonitoringService.add2WorkPackage(workpackage);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("添加到已有工包失败!",e);
		}
	}
	
	/**
	 * @Description 删除预组包
	 * @CreateTime 2017-10-17 上午11:15:15
	 * @CreateBy 刘兵
	 * @param id 工包id
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main:05")
	@ResponseBody
	@RequestMapping(value = "deletePackage", method = RequestMethod.POST)
	public void deletePackage(String id) throws BusinessException{
		try {
			maintenanceMonitoringService.deletePackage(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("删除预组包失败!",e);
		}
	}
	
	/**
	 * @Description 提交预组包
	 * @CreateTime 2017-10-17 上午11:15:15
	 * @CreateBy 刘兵
	 * @param id 工包id
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main")
	@ResponseBody
	@RequestMapping(value = "submitPackage", method = RequestMethod.POST)
	public int submitPackage(String id) throws BusinessException{
		try {
			return maintenanceMonitoringService.doSubmitPackage(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("提交预组包失败!",e);
		}
	}
	
	/**
	 * @Description 删除工包下工单
	 * @CreateTime 2017-10-18 上午10:35:33
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @throws BusinessException
	 */
	@Privilege(code="produce:maintenance:monitoring:main:04")
	@ResponseBody
	@RequestMapping(value = "deleteWorkOrder4WorkPackage", method = RequestMethod.POST)
	public void deleteWorkOrder4WorkPackage(@RequestBody Workpackage workpackage) throws BusinessException{
		try {
			maintenanceMonitoringService.deleteWorkOrder4WorkPackage(workpackage);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("删除工包下工单失败!",e);
		}
	}
	
	
	/**
	 * @Description 根据飞机注册号、机构代码获取已选中的数量
	 * @CreateTime 2017-10-14 下午2:47:44
	 * @CreateBy 刘兵
	 * @param fjzch 飞机注册号
	 * @param dprtcode 机构代码
	 * @return int 已选中数量
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getCheckCount",method={RequestMethod.POST,RequestMethod.GET})
	public int getCheckCount(String fjzch, String dprtcode) throws BusinessException {
		try {
			return maintenanceMonitoringService.getCheckCount(fjzch, dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询已选中的数量失败!",e);
		}
	}
	
	/**
	 * @Description 根据飞机注册号、机构代码获取预组包数量
	 * @CreateTime 2017-10-16 下午3:18:21
	 * @CreateBy 刘兵
	 * @param fjzch 飞机注册号
	 * @param dprtcode 机构代码
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getBurstificationCount",method={RequestMethod.POST,RequestMethod.GET})
	public int getBurstificationCount(String fjzch, String dprtcode) throws BusinessException {
		try {
			return maintenanceMonitoringService.getBurstificationCount(fjzch, dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询预组包数量失败!",e);
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
			return maintenanceMonitoringService.queryAllPageMaintenanceList(monitoringCurrent);
		} catch (Exception e) {
			throw new BusinessException("当前监控数据查询失败!",e);
		}
	}
	
	/**
	 * @Description 根据查询条件查询维修清单
	 * @CreateTime 2017-10-28 下午3:38:39
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object> 页面数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryPackageMaintenanceDetailList", method = RequestMethod.POST)
	public Map<String, Object> queryPackageMaintenanceDetailList(@RequestBody MonitoringCurrent monitoringCurrent)throws BusinessException {
		try {
			return maintenanceMonitoringService.queryPackageMaintenanceDetailList(monitoringCurrent);
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
			return maintenanceMonitoringService.queryAllPageEOList(monitoringCurrent);
		} catch (Exception e) {
			throw new BusinessException("当前监控数据查询失败!",e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)
	 * @CreateTime 2018-5-8 下午2:49:06
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object> 页面数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPagePOList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPagePOList(@RequestBody MonitoringCurrent monitoringCurrent)throws BusinessException {
		try {
			return maintenanceMonitoringService.queryAllPagePOList(monitoringCurrent);
		} catch (Exception e) {
			throw new BusinessException("当前监控数据查询失败!",e);
		}
	}
	
	/**
	 * @Description 根据条件查询已选中的监控数据列表
	 * @CreateTime 2017-10-14 下午4:05:52
	 * @CreateBy 刘兵
	 * @param monitoringWorkpackage 待组包的监控项目
	 * @return List<MonitoringWorkpackage> 已选中的监控数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCheckedList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryCheckedList(@RequestBody MonitoringWorkpackage monitoringWorkpackage) throws BusinessException {
		try {
			List<MonitoringWorkpackage> mList = maintenanceMonitoringService.queryCheckedList(monitoringWorkpackage);
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			for (MonitoringWorkpackage mWorkpackage : mList) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", mWorkpackage.getId());
				resultMap.put("lx", mWorkpackage.getLx());
				resultMap.put("dprtcode", mWorkpackage.getDprtcode());
				resultMap.put("gbid", mWorkpackage.getGbid());
				resultMap.put("fjzch", mWorkpackage.getFjzch());
				resultMap.put("jksjgdid", mWorkpackage.getJksjgdid());
				resultMap.put("paramsMap", mWorkpackage.getParamsMap());
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)(导出)
	 * @CreateTime 2017-9-25 下午3:17:26
	 * @CreateBy 刘兵
	 * @param paramjson 当前监控数据参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "eoMonitoring.xls", method = RequestMethod.GET)
	public String exportExcelEO(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			MonitoringCurrent monitoringCurrent = Utils.Json.toObject(paramjson, MonitoringCurrent.class);
			List<MonitoringCurrent> list = maintenanceMonitoringService.doExportExcelEO(monitoringCurrent);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "mteo", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)(导出)
	 * @CreateTime 2017-9-25 下午3:17:26
	 * @CreateBy 刘兵
	 * @param paramjson 当前监控数据参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "maintenanceMonitoring.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			MonitoringCurrent monitoringCurrent = Utils.Json.toObject(paramjson, MonitoringCurrent.class);
			List<MonitoringCurrent> list = maintenanceMonitoringService.exportExcelMt(monitoringCurrent);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "maintenanceMonitor", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
}
