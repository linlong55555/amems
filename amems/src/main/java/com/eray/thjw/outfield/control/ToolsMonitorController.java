package com.eray.thjw.outfield.control;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.outfield.po.ToolsMonitor;
import com.eray.thjw.outfield.po.ToolsMonitorDetail;
import com.eray.thjw.outfield.service.ToolsMonitorService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.ThresholdEnum;
import enu.outfield.MeasurementMarkEnum;

/**
 * @author liub
 * @description 计量工具控制层
 * @develop date 2016.11.30
 */
@Controller
@RequestMapping("/outfield/toolsmonitor")
public class ToolsMonitorController extends BaseController {
	
	/**
	 * @author liub
	 * @description 外场管理service
	 * @develop date 2016.11.30
	 */
	@Autowired
	private ToolsMonitorService toolsMonitorService ;
	
	/**
	 * @author liub
	 * @description 系统阀值设置 Service
	 * @develop date 2016.12.22
	 */
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	/**
	 * @author liub
	 * @description 跳转至计量工具监控设置界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.17
	 *
	 */
	@Privilege(code="outfield:toolsmonitor:setting")
	@RequestMapping(value = "setting", method = RequestMethod.GET)
	public String setting(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.addAttribute("measurementMarkEnum", MeasurementMarkEnum.enumToListMap());
		return "outfield/toolsmonitor/toolsmonitor_setting";
	}
	
	/**
	 * @author liub
	 * @description 跳转至计量工具监控设置界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.17
	 *
	 */
	@Privilege(code="outfield:toolsmonitor:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return "outfield/toolsmonitor/toolsmonitor_main";
	}
	
	/**
	 * @author liub
	 * @description 新增计量工具监控数据
	 * @param toolsMonitorDetail
	 * @develop date 2016.12.01
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "addToolsMonitor", method = RequestMethod.POST)
	public Map<String, Object> addToolsMonitor(@RequestBody ToolsMonitorDetail toolsMonitorDetail) throws BusinessException{
		Map<String, Object> map = null;
		try {
			map = toolsMonitorService.addToolsMonitor(toolsMonitorDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存失败!",e);
		}
		return map;
	}
	
	/**
	 * @author liub
	 * @description 新增计量工具监控详情数据
	 * @param toolsMonitorDetail
	 * @develop date 2016.12.06
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "addToolsMonitorDetail", method = RequestMethod.POST)
	public Map<String, Object> addToolsMonitorDetail(@RequestBody ToolsMonitorDetail toolsMonitorDetail) throws BusinessException{
		Map<String, Object> map = null;
		try {
			map = toolsMonitorService.addToolsMonitorDetail(toolsMonitorDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存失败!",e);
		}
		return map;
	}
	
	/**
	 * @author liub
	 * @description 批量新增计量工具监控详情数据
	 * @param toolsMonitor
	 * @develop date 2016.12.15
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "addToolsMonitorDetailList", method = RequestMethod.POST)
	public Map<String, Object> addToolsMonitorDetailList(@RequestBody ToolsMonitor toolsMonitor) throws BusinessException{
		Map<String, Object> map = null;
		try {
			map = toolsMonitorService.addToolsMonitorDetailList(toolsMonitor);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存失败!",e);
		}
		return map;
	}
	
	/**
	 * @author liub
	 * @description 根据mainid、编号删除计量工具监控详情数据
	 * @param toolsMonitorDetail
	 * @develop date 2016.12.06
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "deleteDetailByMainIdAndBjxlh", method = RequestMethod.POST)
	public Map<String, Object> deleteDetailByMainIdAndBjxlh(@RequestBody ToolsMonitorDetail toolsMonitorDetail) throws BusinessException{
		Map<String, Object> map = null;
		try {
			map = toolsMonitorService.deleteDetailByMainIdAndBjxlh(toolsMonitorDetail);
		} catch (Exception e) {
			 throw new BusinessException("删除失败!",e);
		}
		return map;
	}
	
	/**
	 * @author liub
	 * @description 根据detailId删除计量工具监控详情
	 * @param detailId
	 * @develop date 2016.12.06
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "deleteDetail", method = RequestMethod.POST)
	public Map<String, Object> deleteDetail(String detailId,String mainid) throws BusinessException{
		Map<String, Object> map = null;
		try {
			map = toolsMonitorService.deleteDetail(detailId,mainid);
		} catch (Exception e) {
			 throw new BusinessException("删除失败!",e);
		}
		return map;
	}
	
	
	/**
	 * @author liub
	 * @description 根据条件分页查询库存及外场虚拟库存列表
	 * @param request,model
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2016.11.30
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllStockPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllStockPageList(@RequestBody ToolsMonitor toolsMonitor,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(toolsMonitor.getPagination());
			List<Map<String, Object>> list = this.toolsMonitorService.queryAllStockPageList(toolsMonitor);
			return PageUtil.pack4PageHelper(list, toolsMonitor.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询计量监控列表
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2016.12.15
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(baseEntity.getPagination());
			List<Map<String, Object>> list = this.toolsMonitorService.queryAllPageList(baseEntity);
			return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据计量工具id查询详情信息(最新检查日期)
	 * @param id,model
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.06
	 */
	@ResponseBody
	@RequestMapping(value = "queryDetailByMainId",method={RequestMethod.POST,RequestMethod.GET})
	public List<ToolsMonitorDetail> queryDetailByMainId(Model model,String mainid) throws BusinessException {
		List<ToolsMonitorDetail> takeStockDetailList = null;
		try {
			takeStockDetailList = toolsMonitorService.queryDetailByMainId(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询计量详情信息失败!",e);
		}
		return takeStockDetailList;
	}
	
	/**
	 * @author liub
	 * @description 根据计量工具id,编号查询历史详情信息
	 * @param id,model
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.14
	 */
	@ResponseBody
	@RequestMapping(value = "queryDetailByMainIdAndBjxlh",method={RequestMethod.POST,RequestMethod.GET})
	public List<ToolsMonitorDetail> queryDetailByMainIdAndBjxlh(Model model,String mainid,String bjxlh) throws BusinessException {
		List<ToolsMonitorDetail> takeStockDetailList = null;
		try {
			takeStockDetailList = toolsMonitorService.queryDetailByMainIdAndBjxlh(mainid,bjxlh);
		} catch (Exception e) {
			throw new BusinessException("查询计量详情信息失败!",e);
		}
		return takeStockDetailList;
	}
	
	/**
	 * @author liub
	 * @description 根据dprtcode查询系统阀值
	 * @param dprtcode
	 * @return Map<String, Object>
	 * @develop date 2017.04.17
	 */
	@ResponseBody
	@RequestMapping(value = "getByKeyDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getByKeyDprtcode(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GJJK.getName(),dprtcode));
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值失败!",e);
		}
		return model;
	}
}
