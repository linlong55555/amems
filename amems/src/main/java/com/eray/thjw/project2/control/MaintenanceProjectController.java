package com.eray.thjw.project2.control;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.MaintenanceProjectList;
import com.eray.thjw.project2.service.MaintenanceProjectService;

/**
 * @Description 维修项目清单Controller
 * @CreateTime 2017年8月16日 下午2:26:47
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/project2/maintenanceproject")
public class MaintenanceProjectController extends BaseController {
	
	@Resource
	private MaintenanceProjectService maintenanceProjectService;
	@Resource(name="maintenanceprojectlistexcelimporter")
	private BaseExcelImporter<MaintenanceProjectList> excelImporter;
	
	/**
	 * @Description 跳转到维修方案管理
	 * @CreateTime 2017年8月16日 下午2:26:59
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "project2:maintenanceproject:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_plan", responseParamMap);
	}
	
	/**
	 * @Description 保存维修项目
	 * @CreateTime 2017年8月16日 上午9:33:10
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:maintenanceproject:main:06,project2:maintenanceproject:main:07")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public MaintenanceProject save(@RequestBody MaintenanceProject maintenanceProject) throws BusinessException{
		try {
			return maintenanceProjectService.doSave(maintenanceProject);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存维修项目失败!",e);
		}
	}
	
	/**
	 * @Description 根据维修方案查询对应的维修项目（按照ATA章节号分组）
	 * @CreateTime 2017年8月16日 下午2:27:16
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/groupByATA", method = RequestMethod.POST)
	public List<Map<String, Object>> groupByATA(@RequestBody MaintenanceProject project) throws BusinessException{
		try {
			return maintenanceProjectService.groupByATA(project);
		} catch (Exception e) {
			throw new BusinessException("根据维修方案查询对应的维修项目（按照ATA章节号分组）失败!",e);
		}
	}
	
	/**
	 * @Description 根据维修方案查询对应的维修项目（按照目录分组）
	 * @CreateTime 2017年8月16日 下午2:27:24
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/groupByCatalog", method = RequestMethod.POST)
	public List<Map<String, Object>> groupByCatalog(@RequestBody MaintenanceProject project) throws BusinessException{
		try {
			return maintenanceProjectService.groupByCatalog(project);
		} catch (Exception e) {
			throw new BusinessException("根据维修方案查询对应的维修项目（按照目录分组）失败!",e);
		}
	}
	
	/**
	 * @Description 查询相关维修项目
	 * @CreateTime 2017年8月16日 下午2:27:39
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRelatedMaintenanceProject", method = RequestMethod.POST)
	public List<MaintenanceProject> queryRelatedMaintenanceProject(@RequestBody MaintenanceProject project) throws BusinessException{
		try {
			return maintenanceProjectService.queryRelatedMaintenanceProject(project);
		} catch (Exception e) {
			throw new BusinessException("查询相关维修项目失败!",e);
		}
	}
	
	/**
	 * @Description 查询监控项目版本
	 * @CreateTime 2017年8月16日 下午2:27:48
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMonitorItemVersion", method = RequestMethod.POST)
	public List<MaintenanceProject> queryMonitorItemVersion(@RequestBody MaintenanceProject project) throws BusinessException{
		try {
			return maintenanceProjectService.queryMonitorItemVersion(project);
		} catch (Exception e) {
			throw new BusinessException("查询监控项目版本失败!",e);
		}
	}
	
	/**
	 * @Description 撤销维修项目
	 * @CreateTime 2017年8月16日 下午2:27:56
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:09")
	@ResponseBody
	@RequestMapping(value = "/revoke", method = RequestMethod.POST)
	public MaintenanceProject revoke(@RequestBody MaintenanceProject project) throws BusinessException{
		try {
			return maintenanceProjectService.doRevoke(project);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("撤销维修项目失败!", e);
		}
	}

	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询维修项目信息(弹窗需要的数据)
	 * @param project
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryWinAllPageList(@RequestBody MaintenanceProject maintenanceProject)throws BusinessException {
		try {
			return maintenanceProjectService.queryWinAllPageList(maintenanceProject);
		} catch (Exception e) {
			throw new BusinessException("维修项目查询失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件查询定检包信息
	 * @param maintenanceProject
	 * @return List<MaintenanceProject>
	 */
	@ResponseBody
	@RequestMapping(value = "queryFixedPackageByWxfn", method = RequestMethod.POST)
	public List<MaintenanceProject> queryFixedPackageByWxfn(@RequestBody MaintenanceProject maintenanceProject)throws BusinessException {
		try {
			return maintenanceProjectService.queryFixedPackageByWxfn(maintenanceProject);
		} catch (Exception e) {
			throw new BusinessException("定检包查询失败!",e);
		}
	}
	
	/**
	 * @Description 跳转到维修方案审核页面
	 * @CreateTime 2017年8月16日 下午2:28:08
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "project2:maintenanceproject:audit:main")
	@RequestMapping(value = "/audit/main", method = RequestMethod.GET)
	public ModelAndView auditMain(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_plan_audit", responseParamMap);
	}
	
	/**
	 * @Description 跳转到维修方案批准页面
	 * @CreateTime 2017年8月16日 下午2:28:14
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "project2:maintenanceproject:approval:main")
	@RequestMapping(value = "/approval/main", method = RequestMethod.GET)
	public ModelAndView approvalMain(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_plan_approval", responseParamMap);
	}
	
	/**
	 * @Description 跳转到维修方案确认生效页面
	 * @CreateTime 2017年8月16日 下午2:28:21
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "project2:maintenanceproject:effect:main")
	@RequestMapping(value = "/effect/main", method = RequestMethod.GET)
	public ModelAndView effectMain(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_plan_effect", responseParamMap);
	}
	
	/**
	 * @Description 跳转到维修项目查看差异
	 * @CreateTime 2017年8月16日 下午2:28:43
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/different", method = RequestMethod.GET)
	public ModelAndView viewItemDifferent(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_item_different", responseParamMap);
	}
	
	/**
	 * @Description 跳转到维修项目查看
	 * @CreateTime 2017年8月16日 下午2:28:50
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewItem(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_item_view", responseParamMap);
	}
	
	/**
	 * @Description 根据查询条件分页查询维修项目信息(工卡弹窗需要的数据)
	 * @CreateTime 2017-8-21 下午3:47:29
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinAllPageList4WorkCard", method = RequestMethod.POST)
	public Map<String, Object> queryWinAllPageList4WorkCard(@RequestBody MaintenanceProject maintenanceProject)throws BusinessException {
		try {
			return maintenanceProjectService.queryWinAllPageList4WorkCard(maintenanceProject);
		} catch (Exception e) {
			throw new BusinessException("维修项目查询失败!",e);
		}
	}
	
	/**
	 * @Description 查询维修项目详情
	 * @CreateTime 2017年8月24日 下午6:30:26
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public MaintenanceProject queryDetail(@RequestBody MaintenanceProject project) throws BusinessException{
		try {
			return maintenanceProjectService.queryDetail(project);
		} catch (Exception e) {
			throw new BusinessException("查询维修项目详情失败!",e);
		}
	}
	
	/**
	 * @Description 查询维修项目版本历史版本
	 * @CreateTime 2017年8月25日 上午10:39:04
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "versionlist",method={RequestMethod.POST,RequestMethod.GET})
	public List<MaintenanceProject> queryVersionList(@RequestBody MaintenanceProject project) throws BusinessException {
		try {
			return maintenanceProjectService.queryVersionList(project);
		} catch (Exception e) {
			throw new BusinessException("查询维修项目版本历史版本失败!",e);
		}
	}
	
	/**
	 * @Description 改版维修项目
	 * @CreateTime 2017年8月26日 下午4:57:06
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:05")
	@ResponseBody
	@RequestMapping(value = "revision", method = RequestMethod.POST)
	public MaintenanceProject revision(@RequestBody MaintenanceProject maintenanceProject) throws BusinessException{
		try {
			return maintenanceProjectService.doRevision(maintenanceProject);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("改版维修项目失败!",e);
		}
	}
	
	/**
	 * @Description 查询监控项目适用性
	 * @CreateTime 2017-8-26 下午3:10:49
	 * @CreateBy 刘兵
	 * @param id 维修项目id
	 * @return MaintenanceProject 维修项目
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMonitorItemModel", method = RequestMethod.POST)
	public MaintenanceProject queryMonitorItemModel(String id) throws BusinessException{
		try {
			return maintenanceProjectService.queryMonitorItemModel(id);
		} catch (Exception e) {
			throw new BusinessException("查询监控项目适用性失败!",e);
		}
	}
	
	/**
	 * @Description 根据条件查询监控项目适用性
	 * @CreateTime 2017-9-20 下午3:19:18
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return MaintenanceProject 维修项目
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMonitorItemModelByWxxm", method = RequestMethod.POST)
	public MaintenanceProject queryMonitorItemModelByWxxm(@RequestBody MaintenanceProject maintenanceProject) throws BusinessException{
		try {
			return maintenanceProjectService.queryMonitorItemModelByWxxm(maintenanceProject);
		} catch (Exception e) {
			throw new BusinessException("查询监控项目适用性失败!",e);
		}
	}
	
	/**
	 * @Description 查询差异数据
	 * @CreateTime 2017年8月28日 下午5:36:27
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDifferenceData", method = RequestMethod.POST)
	public Map<String, MaintenanceProject> queryDifferenceData(
			@RequestBody MaintenanceProject project) throws BusinessException {
		try {
			return maintenanceProjectService.queryDifferenceData(project);
		} catch (Exception e) {
			throw new BusinessException("查询差异数据失败!", e);
		}
	}
	
	/**
	 * @Description 查询适用维修项目
	 * @CreateTime 2017年9月27日 下午2:06:10
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "applylist", method = RequestMethod.POST)
	public List<MaintenanceProject> queryApplyList(@RequestBody InstallationListEditable record)throws BusinessException {
		try {
			return maintenanceProjectService.queryApplyList(record);
		} catch (Exception e) {
			throw new BusinessException("查询适用维修项目失败!",e);
		}
	}
	
	/**
	 * @Description 查询适用维修项目(航材/工具检验) 
	 * @CreateTime 2018-5-2 上午10:54:14
	 * @CreateBy 刘兵
	 * @param bjh
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryApplyListByBjh", method = RequestMethod.POST)
	public List<MaintenanceProject> queryApplyListByBjh(String bjh, String dprtcode)throws BusinessException {
		try {
			return maintenanceProjectService.queryApplyListByBjh(bjh, dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询适用维修项目失败!",e);
		}
	}
	
	/**
	 * 维修项目清单导入
	 * @Description 
	 * @CreateTime 2017-11-29 上午11:38:14
	 * @CreateBy 雷伟
	 * @param multipartRequest
	 * @param response
	 * @param wxfaid 维修方案ID
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:11")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,HttpServletResponse response,String wxfaid) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("wxfaId", wxfaid);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "维修项目清单导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "维修项目清单导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @Description 维修项目导出
	 * @CreateTime 2017年12月18日 下午1:44:17
	 * @CreateBy 韩武
	 * @param record
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:10")
	@RequestMapping(value = "wxxm.xls")
	public String export(String json, Model model) throws BusinessException {
		try {
			MaintenanceProject record = JSON.parseObject(json, MaintenanceProject.class);
			List<Map<String, Object>> list = maintenanceProjectService.getExportList(record);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "maintenanceproject", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-1-29 下午3:17:36
	 * @CreateBy 刘兵
	 * @param paramjson 当前参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "difference.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			MaintenanceProject record = JSON.parseObject(paramjson, MaintenanceProject.class);
			List<MaintenanceProject> list = maintenanceProjectService.doExportExcel(record);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "difference", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
}



