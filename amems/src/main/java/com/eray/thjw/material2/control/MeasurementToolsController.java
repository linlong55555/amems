package com.eray.thjw.material2.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.MeasurementTools;
import com.eray.thjw.material2.po.MeasurementToolsDetails;
import com.eray.thjw.material2.service.MeasurementToolsDetailsService;
import com.eray.thjw.material2.service.MeasurementToolsService;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.quality.po.ProcessRecord;

import enu.ThresholdEnum;

/**
 * 
 * @Description 计量工具Controller
 * @CreateTime 2018年2月7日 上午9:46:45
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/quality/toolcheck")
public class MeasurementToolsController  extends BaseController{

	@Resource
	private MonitorsettingsService monitorsettingsService;
	@Resource
	private MeasurementToolsDetailsService measurementToolsDetailsService;
	@Resource
	private MeasurementToolsService measurementToolsService;
	
	/**
	 * 
	 * @Description 计量工具列表跳转
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:toolcheck:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//初始化数据
			model.put("jldj", SysContext.MEASUREMENT_LEVEL);
			return new ModelAndView("material2/toolcheck/toolcheck_main",model);
		} catch (Exception e) {
			throw new BusinessException("计量工具列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description  获取当前组织机构的阀值
	 * @CreateTime 2017年8月14日 上午9:39:15
	 * @CreateBy 林龙
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getTechnicalThreshold", method = RequestMethod.POST)
	public Map<String, Object> getTechnicalThreshold(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GJJK.getName(),dprtcode));
			return model;
		} catch (Exception e) {
			throw new BusinessException("获取阀值失败!",e);
		}
	}
	
	/**
	 * @Description 计量工具列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 计量工具
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody MeasurementToolsDetails measurementToolsDetails,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = measurementToolsDetailsService.queryAllPageList(measurementToolsDetails);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("计量工具列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 新增保存计量工具
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 计量工具
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="produce:reservation:defect:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody MeasurementTools measurementTools) throws BusinessException{
		try {
//			measurementToolsDetails.setZt(FailureKeepStatusEnum.SAVE.getId());//状态为保存
			return measurementToolsService.save(measurementTools);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存计量工具失败!",e);
		}
	}
	
	/**
	 * @Description 根据计量工具id查询计量工具信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByDefectKeepId", method = RequestMethod.POST)
	public MeasurementToolsDetails getByDefectKeepId(@RequestBody MeasurementToolsDetails measurementToolsDetails)throws BusinessException {
		try {
			return measurementToolsDetailsService.getInfoById(measurementToolsDetails);
		} catch (Exception e) {
			throw new BusinessException("查询计量工具失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 修改保存计量工具
	 * @CreateTime 2017年9月27日 下午4:34:50
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody MeasurementTools measurementTools) throws BusinessException{
		try {
			return measurementToolsDetailsService.update(measurementTools);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改计量工具失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 删除计量工具
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody MeasurementToolsDetails measurementToolsDetails) throws BusinessException{
		try {
			measurementToolsDetailsService.delete(measurementToolsDetails);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除计量工具失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description  计量工具查看页面跳转
	 * @CreateTime 2017年9月29日 下午4:17:56
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("find/{id}")
	public ModelAndView find(@PathVariable("id") String id,Model model)throws BusinessException {
		try {
			 model.addAttribute("viewid", id);
				return new ModelAndView("/material2/toolcheck/toolcheck_find");
		} catch (RuntimeException e) {
			throw new BusinessException("计量工具查看页面跳转失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 查询校验历史列表
	 * @CreateTime 2018年2月28日 下午2:07:55
	 * @CreateBy 林龙
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllLogList", method = RequestMethod.POST)
	public List<MeasurementToolsDetails> queryAllLogList(@RequestBody MeasurementToolsDetails measurementToolsDetails)throws BusinessException{
		try {
			return measurementToolsDetailsService.queryAllLogList(measurementToolsDetails);
		} catch (Exception e) {
			throw new BusinessException("校验历史列表查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 导出计量工具
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "DefectReservation.xls")
	public String export(DefectKeep defectKeep, String sqBeginDate,String sqEndDate,String xfBeginDate,String xfEndDate,String sqr,HttpServletRequest request,Model model) throws BusinessException {
		try {
			defectKeep.setDprtcode(new String (defectKeep.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			sqr=new String (sqr.getBytes("iso8859-1"),"utf-8");
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			defectKeep.setPagination(p);
			defectKeep.getParamsMap().put("sqBeginDate", sqBeginDate);
			defectKeep.getParamsMap().put("sqEndDate", sqEndDate);
			defectKeep.getParamsMap().put("xfBeginDate", xfBeginDate);
			defectKeep.getParamsMap().put("xfEndDate", xfEndDate);
			defectKeep.getParamsMap().put("sqr",sqr);
			Map<String, Object> resultMap = defectKeepService.queryAllPageList(defectKeep);
			List<DefectKeep> list = (List<DefectKeep>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "qxbl", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}*/
}
