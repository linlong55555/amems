package com.eray.thjw.produce.control;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sun.text.resources.FormatData;

import org.apache.commons.lang.StringUtils;
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
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.service.FailureKeepService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;
import enu.produce.FailureKeepStatusEnum;
import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;

/**
 * @Description 故障保留Controller
 * @CreateTime 2017年9月9日 下午5:43:05
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/reservation/fault")
public class FailureKeepController extends BaseController{

	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Resource
	private FailureKeepService failureKeepService;
	
	@Resource
	private FixChapterService fixChapterService;
	
	@Resource
	private DeptInfoService deptInfoService;
	/**
	 * @Description 故障保留
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/failurekeep/failurekeep_main",model);
		} catch (Exception e) {
			throw new BusinessException("故障保留列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 故障保留监控
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:monitor")
	@RequestMapping(value = "monitor", method = RequestMethod.GET)
	public ModelAndView monitor(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/faultmonitor/faultmonitor_main",model);
		} catch (Exception e) {
			throw new BusinessException("故障保留监控列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 故障保留列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 故障保留
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitorAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryMonitorAllPageList(@RequestBody FailureKeep failureKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = failureKeepService.queryMonitorAllPageList(failureKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("故障保留监控列表加载失败!",e);
		}
	}
	
	/**
	 * @Description  获取当前组织机构的故障保留阀值
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
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GZBLD.getName(),dprtcode));
			return model;
		} catch (Exception e) {
			throw new BusinessException("获取故障保留阀值失败!",e);
		}
	}
	
	/**
	 * @Description 故障保留列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 故障保留
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody FailureKeep failureKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = failureKeepService.queryAllPageList(failureKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("故障保留列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 新增保存故障保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:01,produce:reservation:fault:monitor:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			
			boolean isExistATA = this.isExistATA(failureKeep);
			if(!isExistATA){
				throw new BusinessException("章节号不存在!");
			}
			
			failureKeep.setZt(FailureKeepStatusEnum.SAVE.getId());//状态为保存
			return failureKeepService.save(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 章节号是否存在
	 * @CreateTime 2018-2-1 下午4:21:00
	 * @CreateBy 雷伟
	 * @param failureKeep
	 * @return
	 * @throws Exception 
	 */
	private boolean isExistATA(FailureKeep failureKeep) throws Exception {
		
		//章节号为空，不校验！(因为章节号可以不填写)
		if(StringUtils.isBlank(failureKeep.getZjh())){
			return true;
		}
		
		FixChapter fc = new FixChapter();
		fc.setDprtcode(failureKeep.getDprtcode());
		fc.setZjh(failureKeep.getZjh());
		int ataNums = fixChapterService.selectCount(fc);
		return ataNums > 0 ? true : false;
	}

	/**
	 * @Description 新增提交故障保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:01,produce:reservation:fault:monitor:01")
	@ResponseBody
	@RequestMapping(value = "saveSubmit", method = RequestMethod.POST)
	public String saveSubmit(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			
			boolean isExistATA = this.isExistATA(failureKeep);
			if(!isExistATA){
				throw new BusinessException("章节号不存在!");
			}
			
			failureKeep.setZt(FailureKeepStatusEnum.ASSESSMENT.getId());//状态为提交
			return failureKeepService.save(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 根据故障保留id查询故障保留信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByFailureKeepId", method = RequestMethod.POST)
	public FailureKeep getByFailureKeepId(@RequestBody FailureKeep failureKeep)throws BusinessException {
		try {
			return failureKeepService.getInfoById(failureKeep);
		} catch (Exception e) {
			throw new BusinessException("查询故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 修改保存故障保留
	 * @CreateTime 2017年9月27日 下午4:34:50
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:02,produce:reservation:fault:monitor:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			
			boolean isExistATA = this.isExistATA(failureKeep);
			if(!isExistATA){
				throw new BusinessException("章节号不存在!");
			}
			
			return failureKeepService.update(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 修改提交故障保留
	 * @CreateTime 2017年9月27日 下午4:36:53
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:02,produce:reservation:fault:monitor:02")
	@ResponseBody
	@RequestMapping(value = "updateSubmit", method = RequestMethod.POST)
	public String updateSubmit(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			
			boolean isExistATA = this.isExistATA(failureKeep);
			if(!isExistATA){
				throw new BusinessException("章节号不存在!");
			}
			
			failureKeep.setZt(FailureKeepStatusEnum.ASSESSMENT.getId());//状态为提交
			return failureKeepService.update(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 删除故障保留
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:03,produce:reservation:fault:monitor:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeepService.delete(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 批准故障保留单
	 * @CreateTime 2017年9月28日 上午11:01:01
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:04,produce:reservation:fault:monitor:04")
	@ResponseBody
	@RequestMapping(value = "passed", method = RequestMethod.POST)
	public String passed(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeep.setZt(FailureKeepStatusEnum.APPROVAL.getId()); //状态为已批准
			return failureKeepService.approval(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准故障保留单失败!",e);
		}
	}
	
	/**
	 * @Description 批准驳回故障保留单
	 * @CreateTime 2017年9月28日 上午11:01:01
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:04,produce:reservation:fault:monitor:04")
	@ResponseBody
	@RequestMapping(value = "turnDown", method = RequestMethod.POST)
	public String turnDown(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeep.setZt(FailureKeepStatusEnum.APPROVALDOWN.getId()); //状态为批准驳回
			return failureKeepService.approval(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准驳回故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 再次保留故障保留
	 * @CreateTime 2017年9月28日 下午4:50:46
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:05,produce:reservation:fault:monitor:05")
	@ResponseBody
	@RequestMapping(value = "saveagainkeep", method = RequestMethod.POST)
	public String saveagainkeep(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			return failureKeepService.saveagainkeep(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("再次保留故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 关闭故障保留
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:07,produce:reservation:fault:monitor:07")
	@ResponseBody
	@RequestMapping(value = "endModal", method = RequestMethod.POST)
	public void endModal(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeepService.updateEndModal(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭故障保留失败!",e);
		}
	}
	
	/**
	 * @Description 指定结束故障保留
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:08,produce:reservation:fault:monitor:08")
	@ResponseBody
	@RequestMapping(value = "gConfirm", method = RequestMethod.POST)
	public void gConfirm(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeepService.updategConfirm(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束故障保留失败!",e);
		}
	}
	
	/**
	 * @Description  故障保留查看页面跳转
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
			return new ModelAndView("/produce/failurekeep/failurekeep_find");
		} catch (RuntimeException e) {
			throw new BusinessException("故障保留查看页面跳转失败!",e);
		}
	}
	
	
	/**
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
			 model.addAttribute("blLx", 1);
			 model.addAttribute("blId", id);
			return new ModelAndView("/produce/nrc/135/nrc_main");
		} catch (RuntimeException e) {
			throw new BusinessException("工单管理列表跳转失!",e);
		}
	}
	
	/**
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
			model.addAttribute("blLx", 1);
			model.addAttribute("blId", id);
			return new ModelAndView("/produce/nrc/145/nrc145_main");
		} catch (RuntimeException e) {
			throw new BusinessException("工单管理列表跳转失!",e);
		}
	}
	
	/**
	 * @Description 飞行记录本查询故障保留单
	 * @CreateTime 2017年10月24日 下午2:08:54
	 * @CreateBy 韩武
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByFLB", method = RequestMethod.POST)
	public List<FailureKeep> queryListByFLB(@RequestBody FlightSheet sheet) throws BusinessException{
		try {
			return failureKeepService.queryListByFLB(sheet);
		} catch (Exception e) {
			throw new BusinessException("飞行记录本查询故障保留单失败!",e);
		}
	}
	
	/**
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:56:46
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:07,produce:reservation:fault:monitor:07")
	@ResponseBody
	@RequestMapping(value = "filesDownSub", method = RequestMethod.POST)
	public String filesDownSub(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			return failureKeepService.updatefilesDownSub(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("附件上传失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 导出故障保留
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "FaultReservation.xls")
	public String export(FailureKeep failureKeep,String ztList,String sqBeginDate,String sqEndDate,String qxBeginDate,String qxEndDate, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			failureKeep.setDprtcode(new String (failureKeep.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			failureKeep.setPagination(p);
			failureKeep.getParamsMap().put("ztList", ztList);
			failureKeep.getParamsMap().put("sqBeginDate", sqBeginDate);
			failureKeep.getParamsMap().put("sqEndDate", sqEndDate);
			failureKeep.getParamsMap().put("qxBeginDate", qxBeginDate);
			failureKeep.getParamsMap().put("qxEndDate", qxEndDate);
			Map<String, Object> resultMap = failureKeepService.queryAllPageList(failureKeep);
			List<FailureKeep> list = (List<FailureKeep>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "gzbl", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}
	}
	
	/**
	 * @Description 获取飞行时间、飞行循环默认值
	 * @CreateTime 2018-1-29 下午5:07:49
	 * @CreateBy 雷伟
	 * @param sheet
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getDefaultFHFC", method = RequestMethod.POST)
	public List<AircraftinfoStatus> getDefaultFHFC(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			return failureKeepService.getDefaultFHFC(failureKeep);
		} catch (Exception e) {
			throw new BusinessException("获取飞行时间、飞行循环失败!",e);
		}
	}
	/**
	 * 
	 * @Description 打印pdf
	 * @CreateTime 2018年3月14日 下午5:52:22
	 * @CreateBy 岳彬彬
	 * @param failureKeep
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "fault.pdf")
	public String export(FailureKeep failureKeep, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			model.addAttribute("id", failureKeep.getId());
			User user = ThreadVarUtil.getUser();
			DeptInfo DeptInfo = deptInfoService.selectById(user.getJgdm());
			String imagePath = "zx.jpg";
			if(null != DeptInfo){
				if("145".equals(DeptInfo.getDeptType())){
					imagePath = "hx.jpg";
				}
			}
			failureKeep = failureKeepService.getInfoById(failureKeep);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(failureKeep.getZcblbs() ==0){
				model.addAttribute("blqx", failureKeep.getScBlqx() != null ?df.format(failureKeep.getScBlqx()):"");
				model.addAttribute("fxsj", failureKeep.getBlfxsj());
				model.addAttribute("blfxxh", failureKeep.getBlfxxh());
			}else{
				model.addAttribute("blqx", failureKeep.getZcBlqx()!= null ?df.format(failureKeep.getZcBlqx()):"");
				model.addAttribute("fxsj", failureKeep.getZcblfxsj());
				model.addAttribute("blfxxh", failureKeep.getZcblfxxh());
			}
			String path = request.getSession().getServletContext().getRealPath("/static/images/report");
			model.addAttribute("images_path", path.concat(File.separator).concat(imagePath));
			return outReport("pdf", "common", "fault", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}
	}
}
