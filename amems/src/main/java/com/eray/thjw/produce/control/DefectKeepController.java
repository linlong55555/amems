package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.produce.po.DefectKeep;
import com.eray.thjw.produce.service.DefectKeepService;

import enu.produce.FailureKeepStatusEnum;
import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;

/**
 * 
 * @Description 缺陷保留Controller
 * @CreateTime 2017年9月9日 下午5:43:05
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/reservation/defect")
public class DefectKeepController  extends BaseController{

	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Resource
	private DefectKeepService defectKeepService;
	
	/**
	 * 
	 * @Description 缺陷保留
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/defectkeep/defectkeep_main",model);
		} catch (Exception e) {
			throw new BusinessException("缺陷保留列表跳转失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 缺陷保留监控
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:monitor")
	@RequestMapping(value = "monitor", method = RequestMethod.GET)
	public ModelAndView monitor(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/defectmonitor/defectmonitor_main",model);
		} catch (Exception e) {
			throw new BusinessException("缺陷保留监控列表跳转失败!",e);
		}
	}
	
	
	/**
	 * @Description 缺陷保留列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 缺陷保留
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody DefectKeep defectKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = defectKeepService.queryAllPageList(defectKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("缺陷保留列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 缺陷保留监控列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 缺陷保留
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitorAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryMonitorAllPageList(@RequestBody DefectKeep defectKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = defectKeepService.queryMonitorAllPageList(defectKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("缺陷保留监控列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 新增保存缺陷保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			defectKeep.setZt(FailureKeepStatusEnum.SAVE.getId());//状态为保存
			return defectKeepService.save(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存缺陷保留失败!",e);
		}
	}
	
	/**
	 * @Description 新增提交缺陷保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:01")
	@ResponseBody
	@RequestMapping(value = "saveSubmit", method = RequestMethod.POST)
	public String saveSubmit(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			defectKeep.setZt(FailureKeepStatusEnum.ASSESSMENT.getId());//状态为提交
			return defectKeepService.save(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交缺陷保留失败!",e);
		}
	}
	
	/**
	 * @Description 根据缺陷保留id查询缺陷保留信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByDefectKeepId", method = RequestMethod.POST)
	public DefectKeep getByDefectKeepId(@RequestBody DefectKeep defectKeep)throws BusinessException {
		try {
			return defectKeepService.getInfoById(defectKeep);
		} catch (Exception e) {
			throw new BusinessException("查询缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 修改保存缺陷保留
	 * @CreateTime 2017年9月27日 下午4:34:50
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			return defectKeepService.update(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 修改提交缺陷保留
	 * @CreateTime 2017年9月27日 下午4:36:53
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:02")
	@ResponseBody
	@RequestMapping(value = "updateSubmit", method = RequestMethod.POST)
	public String updateSubmit(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			defectKeep.setZt(FailureKeepStatusEnum.ASSESSMENT.getId());//状态为提交
			return defectKeepService.update(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 删除缺陷保留
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			defectKeepService.delete(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 批准缺陷保留单
	 * @CreateTime 2017年9月28日 上午11:01:01
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:04")
	@ResponseBody
	@RequestMapping(value = "passed", method = RequestMethod.POST)
	public String passed(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			defectKeep.setZt(FailureKeepStatusEnum.APPROVAL.getId()); //状态为已批准
			return defectKeepService.approval(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准缺陷保留单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 批准驳回缺陷保留单
	 * @CreateTime 2017年9月28日 上午11:01:01
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:04")
	@ResponseBody
	@RequestMapping(value = "turnDown", method = RequestMethod.POST)
	public String turnDown(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			defectKeep.setZt(FailureKeepStatusEnum.APPROVALDOWN.getId()); //状态为批准驳回
			return defectKeepService.approval(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准驳回缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 关闭缺陷保留
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:07")
	@ResponseBody
	@RequestMapping(value = "endModal", method = RequestMethod.POST)
	public String endModal(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			return defectKeepService.updateEndModal(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 指定结束缺陷保留
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:06")
	@ResponseBody
	@RequestMapping(value = "gConfirm", method = RequestMethod.POST)
	public String gConfirm(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			return defectKeepService.updategConfirm(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束缺陷保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description  缺陷保留查看页面跳转
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
				return new ModelAndView("/produce/defectkeep/defectkeep_find");
		} catch (RuntimeException e) {
			throw new BusinessException("缺陷保留查看页面跳转失败!",e);
		}
	}
	
	
	/**
	 * 
	 * @Description  工单管理列表跳转135
	 * @CreateTime 2017年9月29日 下午4:17:56
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openOrder135/{id}")
	public ModelAndView openOrder135(@PathVariable("id") String id,Model model)throws BusinessException {
		try {
			 model.addAttribute("workorderStatusEnum", WorkorderStatusEnum.enumToListMap());//工单状态枚举
			 model.addAttribute("workorderTypeEnum", WorkorderTypeEnum.enumToListMap());//工单类型枚举
			 model.addAttribute("feedbackStatusEnum", FeedbackStatusEnum.enumToListMap());//反馈枚举
			 model.addAttribute("blLx", 2);
			 model.addAttribute("blId", id);
			return new ModelAndView("/produce/nrc/135/nrc_main");
		} catch (RuntimeException e) {
			throw new BusinessException("工单管理列表跳转失!",e);
		}
	}
	/**
	 * 
	 * @Description  工单管理列表跳转145
	 * @CreateTime 2017年9月29日 下午4:17:56
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openOrder145/{id}")
	public ModelAndView openOrder145(@PathVariable("id") String id,Model model)throws BusinessException {
		try {
			model.addAttribute("workorderStatusEnum", WorkorderStatusEnum.enumToListMap());//工单状态枚举
			model.addAttribute("workorderTypeEnum", WorkorderTypeEnum.enumToListMap());//工单类型枚举
			model.addAttribute("feedbackStatusEnum", FeedbackStatusEnum.enumToListMap());//反馈枚举
			model.addAttribute("blLx", 2);
			model.addAttribute("blId", id);
			return new ModelAndView("/produce/nrc/145/nrc145_main");
		} catch (RuntimeException e) {
			throw new BusinessException("工单管理列表跳转失!",e);
		}
	}
	
	/**
	 * 
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:56:46
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "filesDownSub", method = RequestMethod.POST)
	public String filesDownSub(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			return defectKeepService.updatefilesDownSub(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("附件上传失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 导出缺陷保留
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
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

	}
}
