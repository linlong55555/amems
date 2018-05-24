package com.eray.thjw.flightdata.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.aerialmaterial.service.ComponentUsageService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.LegacyTrouble;
import com.eray.thjw.flightdata.service.FlightRecordSheetEffiectiveService;
import com.eray.thjw.flightdata.service.FlightRecordSheetService;
import com.eray.thjw.flightdata.service.LegacyTroubleService;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.PlanTaskService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.SpecialFlightConditionService;
import com.eray.thjw.service.MaintenanceService;
import com.eray.thjw.util.SessionUtil;
import com.google.gson.Gson;

/**
 * 飞行记录控制器
 * @author hanwu
 *
 */
@Controller
@RequestMapping("/flightdata/flightrecordsheet")
public class FlightRecordSheetController extends BaseController{
	
	@Resource
	private FlightRecordSheetService flightRecordSheetService;
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private SpecialFlightConditionService specialFlightConditionService;
	
	@Resource
	private PlanTaskService planTaskService;
	
	@Resource
	private MaintenanceService maintenanceService;
	
	@Resource
	private ComponentUsageService componentUsageService;
	
	@Resource
	private FlightRecordSheetEffiectiveService flightRecordSheetEffiectiveService;
	
	@Resource
	private LegacyTroubleService legacyTroubleService;
	
	@Resource
	private LoadingListService loadingListService;
	
	/** 新增 */
	private static final String FORWARD_TYPE_ADD = "1";
	
	/** 修改 */
	private static final String FORWARD_TYPE_EDIT = "2";
	
	/** 查看 */
	private static final String FORWARD_TYPE_VIEW = "3";
	
	/** 上一页 */
	private static final Integer PREVIOUS_PAGE = -1;
	
	/** 下一页 */
	private static final Integer NEXT_PAGE = 1;
	
	/**
	 * 跳转至飞行记录单主页面
	 * @return 
	 * @throws BusinessException 
	 */
	@Privilege(code="flightdata:flightrecordsheet:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request,  @RequestParam(value = "fjzch", required = false) String fjzch) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PlaneData pd = new PlaneData();
			pd.setDprtcodes(SessionUtil.getDprtcodeList(request));
			model.put("planes", new Gson().toJson(planeDataService.findAllWithFjjxInJson(pd)));
			model.put("defaultPlane", fjzch);
			return new ModelAndView("flightdata/flightrecordsheet/flightrecordsheet_main", model);
		} catch (RuntimeException e) {
			 throw new BusinessException("跳转至飞行记录单主页面失败!",e);
		}
	}
	
	/**
	 * 飞行记录单分页查询
	 * @param sheet
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryByPage",method={RequestMethod.POST})
	public Map<String, Object> queryByPage(HttpServletRequest request, @RequestBody FlightRecordSheet sheet) throws BusinessException {
		try {
			//没有选择机构，可查当前用户授权的所有机构
			if (null == sheet.getDprtcode() || StringUtils.isEmpty(sheet.getDprtcode())) {
				List<String> dprtcodes = SessionUtil.getDprtcodeList(request);
				sheet.setDprtcodes(dprtcodes);
			}
			return flightRecordSheetService.queryByPage(sheet);
		} catch (RuntimeException e) {
			throw new BusinessException("查询飞行记录单失败",e);
		}
	}
	
	/**
	 * 跳转至新增飞行记录单页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PlaneData pd = new PlaneData();
			pd.setDprtcodes(SessionUtil.getDprtcodeList(request));
			model.put("planes", new Gson().toJson(planeDataService.findAllWithFjjxInJson(pd)));
			model.put("conditions", specialFlightConditionService.findAll());
			model.put("type", FORWARD_TYPE_ADD);
			model.put("previousPage", flightRecordSheetService.getPageId("", PREVIOUS_PAGE));
			model.put("nextPage", flightRecordSheetService.getPageId("", NEXT_PAGE));
			return new ModelAndView("flightdata/flightrecordsheet/flightrecordsheet_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至飞行记录单新增页面失败!",e);
		}
	}
	
	/**
	 * 保存飞行记录单
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save",method={RequestMethod.POST})
	public Map<String, Object> save(@RequestBody FlightRecordSheet data) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = flightRecordSheetService.save(data);
			resultMap.put("id", id);
			resultMap.put("previousPage", flightRecordSheetService.getPageId(id, PREVIOUS_PAGE));
			resultMap.put("nextPage", flightRecordSheetService.getPageId(id, NEXT_PAGE));
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("保存飞行记录单失败!",e);
		}
		return resultMap;
	}
	
	/**
	 * 加载飞行前数据
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadPreflightData", method = RequestMethod.POST)
	public Map<String, Object> loadPreflightData(@RequestBody FlightRecordSheet sheet) throws BusinessException{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("flightrecords", flightRecordSheetService.loadPreflightData(sheet));
			return resultMap;
		} catch (Exception e) {
			 throw new BusinessException("加载飞行前数据失败!",e);
		}
	}
	
	/**
	 * 重新加载飞行前数据
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/reloadPreflightData", method = RequestMethod.POST)
	public ComponentUsage reloadPreflightData(@RequestBody ComponentUsage usage) throws BusinessException{
		try {
			return componentUsageService.sumComponentUsage(usage);
		} catch (Exception e) {
			 throw new BusinessException("重新加载飞行前数据失败!",e);
		}
	}
	
	/**
	 * 跳转至修改飞行记录单页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id, HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PlaneData pd = new PlaneData();
			pd.setDprtcodes(SessionUtil.getDprtcodeList(request));
			model.put("planes", new Gson().toJson(planeDataService.findAllWithFjjxInJson(pd)));
			model.put("conditions", specialFlightConditionService.findAll());
			model.put("flightrecordsheet", flightRecordSheetService.findById(id));
			model.put("type", FORWARD_TYPE_EDIT);
			model.put("previousPage", flightRecordSheetService.getPageId(id, PREVIOUS_PAGE));
			model.put("nextPage", flightRecordSheetService.getPageId(id, NEXT_PAGE));
		} catch (Exception e) {
			 throw new BusinessException("跳转至飞行记录单修改页面失败!",e);
		}
		return new ModelAndView("flightdata/flightrecordsheet/flightrecordsheet_detail", model);
	}
	
	/**
	 * 跳转至查看飞行记录单页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id, HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PlaneData pd = new PlaneData();
			pd.setDprtcodes(SessionUtil.getDprtcodeList(request));
			model.put("planes", new Gson().toJson(planeDataService.findAllWithFjjxInJson(pd)));
			model.put("conditions", specialFlightConditionService.findAll());
			model.put("flightrecordsheet", flightRecordSheetService.findById(id));
			model.put("type", FORWARD_TYPE_VIEW);
			model.put("previousPage", flightRecordSheetService.getPageId(id, PREVIOUS_PAGE));
			model.put("nextPage", flightRecordSheetService.getPageId(id, NEXT_PAGE));
		} catch (Exception e) {
			 throw new BusinessException("跳转至飞行记录单查看页面失败!",e);
		}
		return new ModelAndView("flightdata/flightrecordsheet/flightrecordsheet_detail", model);
	}
	
	/**
	 * 查询飞行记录单所有数据
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/getTotalData",method={RequestMethod.POST})
	public FlightRecordSheet getTotalData(@RequestBody FlightRecordSheet data) throws BusinessException {
		try {
			return flightRecordSheetService.getAllData(data.getId());
		} catch (RuntimeException e) {
			throw new BusinessException("查询飞行记录单数据失败",e);
		}
	}
	
	
	/**
	 * 查询关联工作
	 * @param planTask
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryAssociateWork",method={RequestMethod.POST})
	public Map<String, Object> queryAssociateWork(@RequestBody PlanTaskExt planTask,HttpServletRequest request) throws BusinessException {
		try {
			return planTaskService.queryByFlightRecord(planTask);
		} catch (RuntimeException e) {
			throw new BusinessException("查询计划任务失败",e);
		}
	}
	
	/**
	 * 根据条件查询飞行记录单
	 * @param sheet
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	public List<FlightRecordSheet> queryList(@RequestBody FlightRecordSheet sheet) throws BusinessException {
		try {
			return flightRecordSheetService.queryList(sheet);
		} catch (RuntimeException e) {
			throw new BusinessException("查询飞行记录单失败",e);
		}
	}
	
	/**
	 * 根据飞机注册号查询记录本页码
	 * @param fjzch
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryRecordNumByFjzch",method={RequestMethod.POST})
	public List<String> queryRecordNumByFjzch(@RequestBody String fjzch) throws BusinessException {
		try {
			return flightRecordSheetService.queryRecordNumByFjzch(fjzch);
		} catch (RuntimeException e) {
			throw new BusinessException("查询记录本页码失败",e);
		}
	}
	

	/**
	 * 获取定检件项目
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/loadFixedMonitorItems",method={RequestMethod.POST})
	public Map<String, Object> loadFixedMonitorItems(@RequestBody LoadingList loadingList) throws BusinessException{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
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
			// 查询部件使用量（tc）
			resultMap.put("usage", loadingListService.sumComponentUsageIfEverDisassembled(loadingList));
			return resultMap;
		} catch (RuntimeException e) {
			throw new BusinessException("获取定检件项目失败",e);
		}
	}
	
	/**
	 * 验证记录本页码和飞行日期
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/validate",method={RequestMethod.POST})
	public Map<String, Object> validate(@RequestBody FlightRecordSheet flightRecordSheet) throws BusinessException{
		try {
			return flightRecordSheetService.validate(flightRecordSheet);
		} catch (RuntimeException e) {
			throw new BusinessException("验证记录本页码和飞行日期失败",e);
		}
	}
	 
	/**
	 * 飞行记录单提交生效
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/takeEffect",method={RequestMethod.POST})
	public Map<String, Object> takeEffect(@RequestBody FlightRecordSheet data) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String id = flightRecordSheetEffiectiveService.doTakeEffect(data);
			resultMap.put("id", id);
			resultMap.put("previousPage", flightRecordSheetService.getPageId(id, PREVIOUS_PAGE));
			resultMap.put("nextPage", flightRecordSheetService.getPageId(id, NEXT_PAGE));
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			throw new BusinessException("飞行记录单提交生效失败！",e);
		}
		return resultMap;
	}
	
	/**
	 * 飞行记录单提交撤销
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/cancel",method={RequestMethod.POST})
	public void cancel(@RequestBody FlightRecordSheet data) throws BusinessException{
		try {
			flightRecordSheetEffiectiveService.doCancel(data);
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			throw new BusinessException("飞行记录单提交撤销失败！",e);
		}
	}
	
	/**
	 * 查询故障保留单
	 * @param data
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryLegacyTrouble",method={RequestMethod.POST})
	public List<LegacyTrouble> queryLegacyTrouble(@RequestBody FlightRecordSheet data) throws BusinessException {
		try {
			return legacyTroubleService.queryListByFlightRecord(data);
		} catch (RuntimeException e) {
			throw new BusinessException("查询故障保留单失败",e);
		}
	}
	
	/**
	 * 加载部件已用数据
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadTcIfEverDisassembled", method = RequestMethod.POST)
	public Map<String, Object> loadTcIfEverDisassembled(@RequestBody LoadingList loadingList) throws BusinessException{
		try {
			return loadingListService.sumComponentUsageIfEverDisassembled(loadingList);
		} catch (Exception e) {
			 throw new BusinessException("加载部件已用数据失败!",e);
		}
	}
	
	/**
	 * 加载部件实际使用值
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadActuallyUsed", method = RequestMethod.POST)
	public Map<String, Object> loadActuallyUsed(@RequestBody ComponentUsage data) throws BusinessException{
		try {
			return flightRecordSheetService.loadActuallyUsed(data);
		} catch (Exception e) {
			 throw new BusinessException("加载飞行前数据失败!",e);
		}
	}
	
	/**
	 * 判断部件是否在库存中
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/isExistInStock", method = RequestMethod.POST)
	public int isExistInStock(@RequestBody LoadingList ll) throws BusinessException{
		try {
			if(flightRecordSheetService.isExistInStock(ll)){
				return 1;
			}else if(flightRecordSheetService.isExistInOutStock(ll)){
				return 0;
			}
			return 2;
		} catch (Exception e) {
			 throw new BusinessException("判断部件是否在库存中失败!",e);
		}
	}

}
