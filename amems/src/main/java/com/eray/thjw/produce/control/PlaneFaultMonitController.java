package com.eray.thjw.produce.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.flightdata.service.FlightRecordSheetToPlanService;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.PlaneFaultMonitoring;
import com.eray.thjw.produce.po.PlaneFaultMonitoringInfo;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.FlightSheetService;
import com.eray.thjw.produce.service.PlaneFaultMonitoringInfoService;
import com.eray.thjw.produce.service.PlaneFaultMonitoringService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Description 飞机故障监控
 * @CreateTime 2017年9月11日 下午5:14:29
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/fault/monitoring")
public class PlaneFaultMonitController extends BaseController {

	@Resource
	private PlaneFaultMonitoringService planeFaultMonitoringService;
	@Resource
	private FlightSheetService flightSheetService;
	@Resource
	private FlightRecordSheetToPlanService flightRecordSheetToPlanService;
	@Resource
	private PlaneFaultMonitoringInfoService planeFaultMonitoringInfoService;
	@Resource
	private AttachmentService attachmentService;

	/**
	 * @Description 跳转至飞机故障监控
	 * @CreateTime 2017年9月11日 下午5:24:53
	 * @CreateBy 林龙
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:fault:monitoring:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model) throws BusinessException {
		return new ModelAndView("produce/planefaultmonitoring/planefaultmonitoring_main", model);
	}

	/**
	 * @Description 飞机故障监控分页查询
	 * @CreateTime 2017年9月15日 上午9:37:11
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getListPage", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody PlaneFaultMonitoring planeFaultMonitoring, HttpServletRequest request,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = planeFaultMonitoringService.getList(planeFaultMonitoring);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("飞机故障监控列表加载失败!",e);
		}
	}
	
	@ResponseBody
	@RequestMapping("/queryWorkorderList")
	public Map<String, Object> queryZlhList(@RequestBody Workorder data)
			throws Exception {
		PageHelper.startPage(data.getPagination());
		List<Workorder> sheetList = flightRecordSheetToPlanService.getWorkorderList(data);
		return PageUtil.pack4PageHelper(sheetList, data.getPagination());
	}
	
	/**
	 * @Description 新增飞机故障监控
	 * @CreateTime 2017年9月14日 下午4:46:45
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addPalneFaultMonitoring")
	public String addMaintenanceRecord(@RequestBody PlaneFaultMonitoring planeFaultMonitoring) throws BusinessException {
		try {
			return planeFaultMonitoringService.insertPlaneFaultMonitoring(planeFaultMonitoring);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("新增飞机故障监控失败!",e);
		}
	}
	
	/**
	 * @Description 修改飞机故障监控
	 * @CreateTime 2017年9月14日 下午4:46:45
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updatePalneFaultMonitoring")
	public String updatePalneFaultMonitoring(@RequestBody PlaneFaultMonitoring planeFaultMonitoring) throws BusinessException {
		try {
			return planeFaultMonitoringService.updatePlaneFaultMonitoringById(planeFaultMonitoring);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修改飞机故障监控失败!",e);
		}
	}

	/**
	 * 查看关闭
	 * 
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getgbyy")
	public PlaneFaultMonitoring getgbyy(@RequestBody PlaneFaultMonitoring record, HttpServletRequest request)
			throws Exception {

		return planeFaultMonitoringService.getGbyy(record);
	}

	/**
	 * 增加关闭
	 * 
	 * @param record
	 * @param request
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/addgbyy")
	public void addgbyy(@RequestBody PlaneFaultMonitoring record, HttpServletRequest request) throws Exception {
		planeFaultMonitoringService.insertGbyy(record);
	}

	/**
	 * 查看页面
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/look", method = RequestMethod.GET)
	public ModelAndView look(String id, HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("planeFaule", planeFaultMonitoringService.getPlaneFaultMonitoringById(id));
			return new ModelAndView("produce/planefaultmonitoring/planefaultmonitoring_view", model);
		} catch (Exception e) {
			throw new BusinessException("跳转至飞机故障查看页面失败!", e);
		}
	}
	
	@Privilege(code = "produce:fault:monitoring:main:02")
	@ResponseBody
	@RequestMapping("/edit")
	public PlaneFaultMonitoring edit(@RequestBody PlaneFaultMonitoringInfo record, HttpServletRequest request)throws Exception {
		return planeFaultMonitoringService.getPlaneFaultMonitoringById(record.getId());
	}

	@ResponseBody
	@RequestMapping("/querySheetList")
	public Map<String, Object> queryPartList(@RequestBody FlightSheet sheet, HttpServletRequest request)
			throws Exception {
		PageHelper.startPage(sheet.getPagination());
		List<FlightSheet> sheetList = flightSheetService.getHbhFxrqFlightRecord(sheet);
		return PageUtil.pack4PageHelper(sheetList, sheet.getPagination());
	}

	@ResponseBody
	@RequestMapping("/queryZlhList")
	public Map<String, Object> queryZlhList(@RequestBody FlightRecordSheetToPlan sheet, HttpServletRequest request)
			throws Exception {
		PageHelper.startPage(sheet.getPagination());
		List<FlightRecordSheetToPlan> sheetList = flightRecordSheetToPlanService.getZlhAndId(sheet);
		return PageUtil.pack4PageHelper(sheetList, sheet.getPagination());
	}
	
	/**
	 * 
	 * @Description 保存故障处理记录
	 * @CreateTime 2017年9月14日 下午12:02:21
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addGzcl", method = RequestMethod.POST)
	public String addGzcl(@RequestBody PlaneFaultMonitoringInfo planeFaultMonitoringInfo)throws BusinessException {
		try {
			return planeFaultMonitoringService.addGzcl(planeFaultMonitoringInfo);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存故障处理记录失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 修改故障处理记录
	 * @CreateTime 2017年9月14日 下午12:02:21
	 * @CreateBy 林龙
	 * @param record
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateGzcl")
	public String updateGzcl(@RequestBody PlaneFaultMonitoringInfo record, HttpServletRequest request) throws BusinessException {
		try {
			return planeFaultMonitoringInfoService.updateById(record);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存故障处理记录失败!",e);
		}
	}

	/**
	 * @Description 删除故障监控
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody PlaneFaultMonitoringInfo record) throws BusinessException{
		try {
			planeFaultMonitoringService.delete(record);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除故障监控失败!",e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/getInfoList")
	public Map<String, Object> getInfoList(@RequestBody PlaneFaultMonitoringInfo record, HttpServletRequest request,
			Model model) throws Exception {
		String id = record.getId();// 用户刚编辑过的记录 ID
		// 清除查询条件中的ID，保证列表查询结果集的正确性
		record.setId(null);
		PageHelper.startPage(record.getPagination());
		List<PlaneFaultMonitoringInfo> recordList = planeFaultMonitoringInfoService.getInfoList(record.getMainid());
		if (((Page) recordList).getTotal() > 0) {
			if (StringUtils.isNotBlank(id)) {
				if (!PageUtil.hasRecord(recordList, id)) {
					List<PlaneFaultMonitoringInfo> newList = planeFaultMonitoringInfoService.getInfoList(id);
					if (newList != null && newList.size() == 1) {
						recordList.add(0, newList.get(0));
					}
				}
			}
			this.getAttacments(recordList);
			return PageUtil.pack4PageHelper(recordList, record.getPagination());
		} else {
			List<PlaneFaultMonitoringInfo> newRecordList = new ArrayList<PlaneFaultMonitoringInfo>(1);
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				newRecordList = planeFaultMonitoringInfoService.getInfoList(id);
				if (newRecordList != null && newRecordList.size() == 1) {
					return PageUtil.pack(1, newRecordList, record.getPagination());
				}
			}
			this.getAttacments(newRecordList);
			return PageUtil.pack(0, newRecordList, record.getPagination());
		}
	}
	
	private void getAttacments(List<PlaneFaultMonitoringInfo> recordList){
		
		if(recordList!=null && recordList.size()>0){
			List<String> ids=new ArrayList<String>();
			for (PlaneFaultMonitoringInfo planeFaultMonitoringInfo : recordList) {
				ids.add(planeFaultMonitoringInfo.getId());
			}
			if(ids.size()>0){
				List<Attachment> attachments = attachmentService.queryPlaneDataListAttachmentsByMainids(ids);
				for (Attachment attachment : attachments) {
					for (PlaneFaultMonitoringInfo planeFaultMonitoringInfo : recordList) {
						if(planeFaultMonitoringInfo.getId().equals(attachment.getMainid())){
							if(planeFaultMonitoringInfo.getAttachmentList()==null){
								List<Attachment> attachmentList=new ArrayList<Attachment>();
								attachmentList.add(attachment);
								planeFaultMonitoringInfo.setAttachmentList(attachmentList);
							}else{
								planeFaultMonitoringInfo.getAttachmentList().add(attachment);
							}
						}
					}
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/editInfo")
	public PlaneFaultMonitoringInfo editInfo(@RequestBody PlaneFaultMonitoringInfo record, HttpServletRequest request)
			throws Exception {
		return planeFaultMonitoringInfoService.getInfoById(record.getId());
	}
	
	@ResponseBody
	@RequestMapping("/deleteInfo")
	public void deleteInfo(@RequestBody PlaneFaultMonitoringInfo record, HttpServletRequest request)
			throws Exception {
		 planeFaultMonitoringInfoService.deleteById(record);
	}
	
	@ResponseBody
	@RequestMapping("/getZsCx")
	public Map<String,List<String>> getZsCx(@RequestBody PlaneFaultMonitoringInfo record, HttpServletRequest request)
			throws Exception {
		return	 planeFaultMonitoringInfoService.getZsClxx(record.getFxjldid());
	}
	
	@ResponseBody
	@RequestMapping("/getZsCxInfo")
	public Map<String, List<String>> getZsCxInfo(@RequestBody Workorder data)throws Exception{
		return planeFaultMonitoringInfoService.getZsCxInfo(data);
	}
	
	
	@RequestMapping(value = "planeFaultMonitoringOutExcel")
	public String export(PlaneFaultMonitoringInfo record,HttpServletRequest request, RedirectAttributesModelMap model) throws BusinessException {
		try{	
			StringBuffer sbf=new StringBuffer();
			sbf.append(" where 1=1  ");
			sbf.append(" and b.fjzch = '"+record.getFjzch()+"' ");
			sbf.append(" and b.dprtcode = '"+record.getDprtcode()+"' ");
			if(!record.getKeyword().equals("")&&!record.getKeyword().equals("undefined")){
				String keyword=record.getKeyword();
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				sbf.append(" and (b.gzxx like '%"+keyword+"%' or b.fjzch like '%"+keyword+"%')");
			}
			model.addFlashAttribute("sql", sbf.toString());
			return "redirect:/report/".concat("xls").concat("/").concat(ThreadVarUtil.getUser().getJgdm()).concat("/Planefaultmonitoring.xls");
			
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
