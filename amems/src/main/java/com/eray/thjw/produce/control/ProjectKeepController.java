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
import com.eray.thjw.produce.po.ProjectKeep;
import com.eray.thjw.produce.service.ProjectKeepService;

import enu.ThresholdEnum;
import enu.produce.FailureKeepStatusEnum;

/**
 * 
 * @Description 项目保留Controller
 * @CreateTime 2017年9月9日 下午5:43:05
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/reservation/item")
public class ProjectKeepController extends BaseController{

	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Resource
	private ProjectKeepService projectKeepService;
	
	/**
	 * @Description 项目保留
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:item:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/projectkeep/projectkeep_main",model);
		} catch (Exception e) {
			throw new BusinessException("项目保留列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 项目保留监控
	 * @CreateTime 2017年9月11日 上午9:41:38
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:item:monitor")
	@RequestMapping(value = "monitor", method = RequestMethod.GET)
	public ModelAndView monitor(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/projectmonitor/projectmonitor_main",model);
		} catch (Exception e) {
			throw new BusinessException("项目保留监控列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description  获取当前组织机构的项目保留阀值
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
			throw new BusinessException("获取项目保留阀值失败!",e);
		}
	}
	
	/**
	 * @Description 项目保留列表加载
	 * @CreateTime 2017年10月10日 下午2:50:02
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody ProjectKeep projectKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = projectKeepService.queryAllPageList(projectKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("项目保留列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 项目保留监控列表加载
	 * @CreateTime 2017年10月10日 下午2:50:02
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitorAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryMonitorAllPageList(@RequestBody ProjectKeep projectKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = projectKeepService.queryMonitorAllPageList(projectKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("项目保留监控列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 保存项目保留
	 * @CreateTime 2017年10月12日 上午11:55:31
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:01,produce:reservation:fault:monitor:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			
			projectKeep.setZt(FailureKeepStatusEnum.SAVE.getId());//状态为保存
			return projectKeepService.save(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存项目保留失败!",e);
		}
	}

	/**
	 * @Description 新增提交项目保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 项目保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:01,produce:reservation:fault:monitor:01")
	@ResponseBody
	@RequestMapping(value = "saveSubmit", method = RequestMethod.POST)
	public String saveSubmit(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			projectKeep.setZt(FailureKeepStatusEnum.ASSESSMENT.getId());//状态为提交
			return projectKeepService.save(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 查询项目保留
	 * @CreateTime 2017年10月12日 上午11:58:58
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByProjectKeeId", method = RequestMethod.POST)
	public ProjectKeep getByProjectKeeId(@RequestBody ProjectKeep projectKeep)throws BusinessException {
		try {
			return projectKeepService.getInfoById(projectKeep);
		} catch (Exception e) {
			throw new BusinessException("查询项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 查询工单信息
	 * @CreateTime 2017年10月12日 上午11:58:58
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getBygdId", method = RequestMethod.POST)
	public ProjectKeep getBygdId(@RequestBody ProjectKeep projectKeep)throws BusinessException {
		try {
			return projectKeepService.selectGetBygdId(projectKeep);
		} catch (Exception e) {
			throw new BusinessException("查询工单信息失败!",e);
		}
	}
	
	/**
	 * @Description 修改保存项目保留
	 * @CreateTime 2017年9月27日 下午4:34:50
	 * @CreateBy 林龙
	 * @param failureKeep 项目保留
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:02,produce:reservation:fault:monitor:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			return projectKeepService.update(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 修改提交项目保留
	 * @CreateTime 2017年9月27日 下午4:36:53
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:02,produce:reservation:fault:monitor:02")
	@ResponseBody
	@RequestMapping(value = "updateSubmit", method = RequestMethod.POST)
	public String updateSubmit(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			projectKeep.setZt(FailureKeepStatusEnum.ASSESSMENT.getId());//状态为提交
			return projectKeepService.update(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 删除项目保留
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:03,produce:reservation:fault:monitor:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			projectKeepService.delete(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 重新执行
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:03,produce:reservation:fault:monitor:03")
	@ResponseBody
	@RequestMapping(value = "updateReExecute", method = RequestMethod.POST)
	public String updateReExecute(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			return projectKeepService.updateReExecute(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException(" 重新执行项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 关闭项目保留
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:07,produce:reservation:fault:monitor:07")
	@ResponseBody
	@RequestMapping(value = "endModal", method = RequestMethod.POST)
	public void endModal(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			projectKeepService.updateEndModal(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭项目保留失败!",e);
		}
	}
	
	/**
	 * @Description 指定结束项目保留
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:fault:main:08,produce:reservation:fault:monitor:08")
	@ResponseBody
	@RequestMapping(value = "gConfirm", method = RequestMethod.POST)
	public void gConfirm(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			projectKeepService.updategConfirm(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束项目保留失败!",e);
		}
	}
	
	/**
	 * @Description  项目保留查看页面跳转
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
			return new ModelAndView("/produce/projectkeep/projectkeep_find");
		} catch (RuntimeException e) {
			throw new BusinessException("项目保留查看页面跳转失败!",e);
		}
	}
	
	/**
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:56:46
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "filesDownSub", method = RequestMethod.POST)
	public String filesDownSub(@RequestBody ProjectKeep projectKeep) throws BusinessException{
		try {
			return projectKeepService.updatefilesDownSub(projectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("附件上传失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 项目保留导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ItemReservation.xls")
	public String export(ProjectKeep projectKeep,String ztList,String sqBeginDate,String sqEndDate,String blBeginDate,String blEndDate, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			projectKeep.setDprtcode(new String (projectKeep.getDprtcode().getBytes("iso8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			projectKeep.setPagination(p);
			projectKeep.getParamsMap().put("ztList", ztList);
			projectKeep.getParamsMap().put("sqBeginDate", sqBeginDate);
			projectKeep.getParamsMap().put("sqEndDate", sqEndDate);
			projectKeep.getParamsMap().put("blBeginDate", blBeginDate);
			projectKeep.getParamsMap().put("blEndDate", blEndDate);
			Map<String, Object> resultMap = projectKeepService.queryAllPageList(projectKeep);
			List<ProjectKeep> list = (List<ProjectKeep>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "xmbl", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
