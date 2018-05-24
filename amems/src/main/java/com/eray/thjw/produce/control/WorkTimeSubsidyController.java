package com.eray.thjw.produce.control;

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
import com.eray.thjw.basic.po.HourCost;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.WorkTimeSubsidyService;
import com.eray.thjw.util.Utils;

/**
 * @author 裴秀
 * @description 工时补贴统计
 */
@Controller
@RequestMapping("produce/worktimesubsidy")
public class WorkTimeSubsidyController extends BaseController{
	@Resource
	private WorkTimeSubsidyService workTimeSubsidyService;
	/**
	 * @Description 工时补贴统计
     * @CreateTime 2018年03月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="produce:worktimesubsidy:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Model model)throws BusinessException {
	    return new ModelAndView("/produce/worktimesubsidy/worktimesubsidy");
	
	}
	/**
	 * 
	 * @Description 列表
	 * @CreateTime 2018年4月4日 下午2:38:42
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getAll", method = RequestMethod.POST)
	public Map<String, Object> getAll(@RequestBody Workorder record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workTimeSubsidyService.getWorkTimeSubsidy(record);
		} catch (Exception e) {
			throw new BusinessException("跳转工时补贴统计页面失败!", e);
		}
	}
	/**
	 * 
	 * @Description 存储数据
	 * @CreateTime 2018年4月4日 下午2:38:30
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRecord(@RequestBody List<HourCost> record, HttpServletRequest request) throws BusinessException {
		try {
			workTimeSubsidyService.addRecord(record);
			return "success";
		} catch (Exception e) {
			throw new BusinessException("保存数据失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取数据
	 * @CreateTime 2018年4月4日 下午2:38:20
	 * @CreateBy 岳彬彬
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "get", method = RequestMethod.POST)
	public List<HourCost> getRecord( HttpServletRequest request) throws BusinessException {
		try {
			return workTimeSubsidyService.getRecord();
		} catch (Exception e) {
			throw new BusinessException("获取数据失败!", e);
		}
	}
	/**
	 * 
	 * @Description 导出excle
	 * @CreateTime 2018年4月4日 下午2:47:01
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "workTime" ,method={RequestMethod.GET})
	public String exportPdf(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Workorder workorder = Utils.Json.toObject(paramjson, Workorder.class);
			Map<String, Object> resultMap = workTimeSubsidyService.getWorkTimeSubsidy(workorder);
			model.addAttribute("map", resultMap);
			return outReport("pdf", "common", "worktimesubsidy", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
	}
	/**
	 * 
	 * @Description 导出excel
	 * @CreateTime 2018年4月4日 下午5:22:32
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "workTime.xls" ,method={RequestMethod.GET})
	public String exportExcel(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Workorder workorder = Utils.Json.toObject(paramjson, Workorder.class);
			Map<String, Object> resultMap = workTimeSubsidyService.getWorkTimeSubsidy(workorder);
			model.addAttribute("map", resultMap);
			return outReport("xls", "common", "worktime", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
