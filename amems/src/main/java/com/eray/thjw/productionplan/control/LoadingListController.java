package com.eray.thjw.productionplan.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.service.ComponentUsageService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.LoadingListToSpecialCondition;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.LoadingListToSpecialConditionService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.ScheduledCheckItemService;
import com.eray.thjw.productionplan.service.SpecialFlightConditionService;
import com.eray.thjw.productionplan.service.SynchronizeEffectiveService;
import com.eray.thjw.productionplan.service.TimeMonitorSettingService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.service.MaintenanceService;
import com.eray.thjw.util.SessionUtil;
import com.google.gson.Gson;

/**
 * 
 * @author hanwu
 * @description 飞机装机清单控制器
 */
@Controller
@RequestMapping("/productionplan/loadingList")
public class LoadingListController extends BaseController {

	@Resource
	private LoadingListService loadingListService;

	@Resource
	private PlaneDataService planeDataService;

	@Resource
	private FixChapterService fixChapterService;

	@Resource
	private SpecialFlightConditionService specialFlightConditionService;

	@Resource
	private LoadingListToSpecialConditionService LoadingListToSpecialConditionService;

	@Resource
	private TimeMonitorSettingService timeMonitorSettingService;

	@Resource
	private ComponentUsageService componentUsageService;

	@Resource
	private MaintenanceService maintenanceService;

	@Resource
	private ScheduledCheckItemService scheduledCheckItemService;

	@Resource
	private SynchronizeEffectiveService synchronizeEffectiveService;
	
	@Resource(name="loadinglistexcelimporter")
	private BaseExcelImporter<LoadingList> excelImporter;

	/**
	 * 跳转至飞机装机清单主页面
	 * 
	 * @return
	 */
	@Privilege(code = "planeData:loadingListAndMonitorSetting:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PlaneData pd = new PlaneData();
			pd.setDprtcodes(SessionUtil.getDprtcodeList(request));
			model.put("planeList", new Gson().toJson(planeDataService.findAllWithFjjxInJson(pd)));
			return new ModelAndView("productionplan/loadingList/loadingList_main", model);
		} catch (Exception e) {
			throw new BusinessException("跳转页面失败!",e);
		}
	}

	/**
	 * 飞机装机清单查询-树状
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEditableTree", method = { RequestMethod.POST })
	public List<LoadingList> queryEditable(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.selectEditableInTree(loadingList);
		} catch (Exception e) {
			throw new BusinessException("飞机装机清单查询失败!",e);
		}
	}

	/**
	 * 飞机装机清单查询-表状-编辑区
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEditableTable", method = { RequestMethod.POST })
	public Map<String, Object> queryEditableTable(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.selectEditableInTable(loadingList);
		} catch (Exception e){
			throw new BusinessException("飞机装机清单查询-编辑区失败!",e);
		}
	}

	/**
	 * 飞机装机清单查询-表状-生效区
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTable", method = { RequestMethod.POST })
	public Map<String, Object> queryTable(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.selectInTable(loadingList);
		} catch (Exception e) {
			throw new BusinessException("飞机装机清单查询-生效区失败!",e);
		}
	}

	/**
	 * 查询指定飞机无且无子部件的零件清单
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNoChildList", method = { RequestMethod.POST })
	public Map<String, Object> queryNoChildList(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryNoChildList(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询无子部件的零件清单-编辑区失败!",e);
		}
	}
	
	/**
	 * 查询指定飞机无且无子部件的零件清单-生效区
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEffectiveNoChildList", method = { RequestMethod.POST })
	public Map<String, Object> queryEffectiveNoChildList(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryEffectiveNoChildList(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询无子部件的零件清单-生效区失败!",e);
		}
	}

	/**
	 * 改变父节点（维护子部件关系）
	 * 
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changeParent", method = { RequestMethod.POST })
	public void changeParent(@RequestBody List<LoadingList> list) throws BusinessException {
		try {
			loadingListService.doChangeParent(list);
		} catch (Exception e) {
			throw new BusinessException("维护子部件关联失败!", e);
		}
	}

	/**
	 * 修改飞机基本信息
	 * 
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertOrUpdateEditable", method = { RequestMethod.POST })
	public String insertOrUpdateEditable(@RequestBody LoadingList ll) throws BusinessException {
		try {
			return loadingListService.doInsertOrUpdateEditable(ll);
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("新增/修改飞机基本信息失败！",e);
		}
	}

	/**
	 * 根据件号和序列号判断部件是否存在
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/judgeXlhExist", method = { RequestMethod.POST })
	public Map<String, Object> judgeXlhExist(@RequestBody LoadingList loadingList) {
		List<LoadingList> list = loadingListService.findByJhAndXlh(loadingList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (list.isEmpty()) {
			resultMap.put("isExist", false);
		} else if (list.size() == 1) {
			resultMap.put("isExist", true);
			resultMap.put("loadingList", list.get(0));
		} else {
			throw new RuntimeException("同一件号、序列号的部件存在多个！");
		}
		return resultMap;
	}

	/**
	 * 作废部件
	 * 
	 * @param ll
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/scrapEditable", method = { RequestMethod.POST })
	public void scrapEditable(@RequestBody LoadingList ll) throws BusinessException {
		try {
			loadingListService.doScrapEditable(ll);
		} catch (Exception e) {
			throw new BusinessException("作废部件基本信息失败，" + e.getMessage());
		}
	}

	/**
	 * 级联作废部件
	 * 
	 * @param ll
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/cascadeScrapEditable", method = { RequestMethod.POST })
	public void cascadeScrapEditable(@RequestBody LoadingList ll) throws BusinessException {
		try {
			loadingListService.doCascadeScrapEditable(ll);
		} catch (Exception e) {
			throw new BusinessException("作废部件基本信息失败，" + e.getMessage());
		}
	}

	/**
	 * 查询时控件-编辑区
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEditableTimeMonitor", method = { RequestMethod.POST })
	public List<LoadingList> queryEditableTimeMonitor(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryEditableTimeMonitor(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询时控件失败!", e);
		}
	}

	/**
	 * 获取时控件的详细信息
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/loadTimeMonitorDetail", method = { RequestMethod.POST })
	public Map<String, Object> loadTimeMonitorDetail(@RequestBody LoadingList param) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 查询装机清单数据
			LoadingList loadingList = loadingListService.selectEditableByPrimaryKey(param);
			// 查询飞机初始化数据
			PlaneData pd = new PlaneData();
			pd.setFjzch(loadingList.getFjzch());
			pd.setDprtcode(loadingList.getDprtcode());
			resultMap.put("plane", planeDataService.queryByFjzch(pd));
			resultMap.put("loadingList", loadingList);
			// 查询特殊情况设置
			LoadingListToSpecialCondition con = new LoadingListToSpecialCondition();
			con.setZjqdid(param.getId());
			con.setZt(1);
			resultMap.put("conditions", LoadingListToSpecialConditionService.select(con));
			// 查询时控件设置
			TimeMonitorSetting setting = new TimeMonitorSetting();
			setting.setZjqdid(param.getId());
			setting.setZt(1);
			resultMap.put("settings", timeMonitorSettingService.selectEditable(setting));
			// 查询tc(当该部件曾经拆解过时)
			resultMap.put("usage", loadingListService.sumComponentUsageIfEverDisassembled(loadingList));
			// 查询tc和tv(和定检件同步)
			resultMap.put("tcAndTv", loadingListService.getTcAndTv(loadingList));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("获取时控件的详细信息失败!", e);
		}
	}

	/**
	 * 修改时控件设置
	 * 
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyTimeMonitorEditable", method = { RequestMethod.POST })
	public void modifyTimeMonitorEditable(@RequestBody LoadingList ll) throws BusinessException {
		try {
			loadingListService.modifyTimeMonitorEditable(ll);
		} catch (Exception e) {
			throw new BusinessException("修改时控件设置", e);
		}
	}

	/**
	 * 查询定检件-编辑区
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEditableFixedMonitor", method = { RequestMethod.POST })
	public List<LoadingList> queryEditableFixedMonitor(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryEditableFixedMonitor(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询定检件-编辑区失败!", e);
		}
	}

	/**
	 * 获取定检件的详细信息
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/loadFixedMonitorDetail", method = { RequestMethod.POST })
	public Map<String, Object> loadFixedMonitorDetail(@RequestBody LoadingList param) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 查询装机清单数据
			LoadingList loadingList = loadingListService.selectEditableByPrimaryKey(param);
			// 查询飞机初始化数据
			resultMap.put("loadingList", loadingList);
			// 查询定检项目
			resultMap.put("items", scheduledCheckItemService.queryEditableByZjqdid(loadingList.getId()));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("获取定检件的详细信息失败!", e);
		}
	}

	/**
	 * 获取定检件项目
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/loadFixedMonitorItems", method = { RequestMethod.POST })
	public Map<String, Object> loadFixedMonitorItems(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			loadingList = loadingListService.selectEditableByPrimaryKey(loadingList);
			PlaneData pd = new PlaneData();
			pd.setFjzch(loadingList.getFjzch());
			pd.setDprtcode(loadingList.getDprtcode());
			Map<String, Object> plane = planeDataService.queryByFjzch(pd);
			// 飞机主数据
			resultMap.put("plane", plane);
			// 机型对应的维修方案以及定检项目
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fjjx", String.valueOf(plane.get("fjjx")));
			paramMap.put("dprtcode", String.valueOf(plane.get("dprtCode")));
			paramMap.put("zjqdid", loadingList.getId());
			resultMap.put("maintenance", maintenanceService.selectByFjjx(paramMap));
			// 查询tc(当该部件曾经拆解过时)
			resultMap.put("usage", loadingListService.sumComponentUsageIfEverDisassembled(loadingList));
			// 查询tc和tv(和时控件同步)
			resultMap.put("tcAndTv", loadingListService.getTcAndTv(loadingList));
			// 查询定检项目
			resultMap.put("items", scheduledCheckItemService.queryEditableByZjqdid(loadingList.getId()));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("获取定检件项目失败!", e);
		}
	}

	/**
	 * 保存定检监控项目
	 * 
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveFixMonitorEditable", method = { RequestMethod.POST })
	public void saveFixMonitorEditable(@RequestBody ScheduledCheckItem item) throws BusinessException {
		try {
			loadingListService.saveFixMonitorEditable(item);
		} catch (Exception e) {
			throw new BusinessException("保存定检监控项目失败!", e);
		}
	}

	/**
	 * 删除定检监控项目
	 * 
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFixMonitorEditable", method = { RequestMethod.POST })
	public void deleteFixMonitorEditable(@RequestBody ScheduledCheckItem item) throws BusinessException {
		try {
			loadingListService.deleteFixMonitorEditable(item);
		} catch (Exception e) {
			throw new BusinessException("删除定检监控项目失败!", e);
		}
	}

	/**
	 * 修改定检监控项目
	 * 
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateFixMonitorEditable", method = { RequestMethod.POST })
	public void updateFixMonitorEditable(@RequestBody ScheduledCheckItem item) throws BusinessException {
		try {
			loadingListService.updateFixMonitorEditable(item);
		} catch (Exception e) {
			throw new BusinessException("修改定检监控项目失败!", e);
		}
	}

	/**
	 * 获取未匹配定检件项目
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/findUnmatchItem", method = { RequestMethod.POST })
	public Map<String, Object> findUnmatchItem(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			PlaneData pd = new PlaneData();
			pd.setFjzch(loadingList.getFjzch());
			pd.setDprtcode(loadingList.getDprtcode());
			Map<String, Object> plane = planeDataService.queryByFjzch(pd);
			// 机型对应的维修方案以及定检项目
			resultMap.put("maintenance", maintenanceService.findUnmatchItem(plane));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("获取未匹配定检件项目失败!", e);
		}
	}

	/**
	 * 同步生效区
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/synchronizeEffective", method = { RequestMethod.POST })
	public List<String> synchronizeEffective(@RequestBody LoadingList loadingList) throws BusinessException {
		
		try {
			PlaneData pd = new PlaneData(loadingList.getFjzch(), loadingList.getDprtcode());
			return synchronizeEffectiveService.doSynchronize(pd);
		}  catch (Exception e) {
			throw new BusinessException("系统错误，同步生效区失败！", e);
		}
	}

	/**
	 * 根据条件查询
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList", method = { RequestMethod.POST })
	public List<LoadingList> queryList(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryList(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单数据-生效区失败!", e);
		}
	}

	/**
	 * 
	 * @param loadingListParam
	 * @param listzt
	 * @param ccjldh
	 * @param llkbh
	 * @param azjldh
	 * @param bz
	 * @param scrq
	 * @param azrq
	 * @param ccrq
	 * @param keyword
	 * @param request
	 * @param model
	 * @return 飞机清单Excel
	 * @throws BusinessException
	 */
	@RequestMapping(value = "LoadingListExcel.xls")
	public String export( LoadingList loadingList, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p=new Pagination();
			p.setSort("auto");
			p.setRows(Integer.MAX_VALUE);
			loadingList.setPagination(p);
			List<LoadingList>list=(List<LoadingList>) loadingListService.selectEditableInTable(loadingList).get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "LoadingList", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}

	}
	
	/**
	 * 飞机装机清单父节点查询-树状
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEditableParentTree", method = { RequestMethod.POST })
	public List<LoadingList> queryEditableParentTree(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryEditableParentTree(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询飞机装机清单父节点失败!", e);
		}
	}
	
	
	/**
	 * 飞机装机清单查询-树状
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEffectiveTree", method = { RequestMethod.POST })
	public List<LoadingList> queryEffectiveTree(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.selectEffectiveInTree(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单树状-生效区失败!", e);
		}
	}
	
	
	/**
	 * 飞机装机清单导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response,@RequestParam(value="fjzch" ,required=true) String fjzch
		    ) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("fjzch", fjzch);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "飞机装机清单导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "飞机装机清单导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据条件查询装机清单数据
	 * @param loadingList
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryByParam", method = { RequestMethod.POST })
	public List<LoadingList> queryByParam(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.queryByParam(loadingList);
		} catch (Exception e) {
			throw new BusinessException("飞机装机清单查询失败!",e);
		}
	}
	
	/**
	 * 查询装机清单编辑区详细
	 * @param loadingList
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadEditableDetail", method = { RequestMethod.POST })
	public LoadingList loadEditableDetail(@RequestBody LoadingList loadingList) throws BusinessException {
		try {
			return loadingListService.selectEditableByPrimaryKey(loadingList);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单编辑区详细失败!",e);
		}
	}
}
