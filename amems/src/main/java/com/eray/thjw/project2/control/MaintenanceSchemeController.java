package com.eray.thjw.project2.control;

import java.util.HashMap;
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
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.service.MaintenanceSchemeService;

/**
 * @Description 维修方案controller
 * @CreateTime 2017年8月16日 下午2:29:07
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/project2/maintenancescheme")
public class MaintenanceSchemeController extends BaseController {

	@Resource
	private MaintenanceSchemeService maintenanceSchemeService;
	
	/**
	 * @Description 根据飞机机型查询维修方案版本
	 * @CreateTime 2017年8月16日 下午2:29:17
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryByFjjx", method = RequestMethod.POST)
	public List<MaintenanceScheme> queryByFjjx(@RequestBody MaintenanceScheme scheme) {
		return maintenanceSchemeService.queryByFjjx(scheme);
	}
	
	/**
	 * @Description 保存维修方案
	 * @CreateTime 2017年8月16日 下午2:29:24
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:01,project2:maintenanceproject:main:02")
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doSave(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存维修方案失败!", e);
		}
	}
	
	/**
	 * @Description 提交维修方案
	 * @CreateTime 2017年8月16日 下午2:29:32
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:03")
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void submit(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doSubmit(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交维修方案失败!", e);
		}
	}
	
	/**
	 * @Description 维修方案审核通过
	 * @CreateTime 2017年8月21日 下午5:03:15
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:audit:main:01")
	@ResponseBody
	@RequestMapping(value = "/auditagree", method = RequestMethod.POST)
	public void auditAgree(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doAuditAgree(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("维修方案审核通过失败!", e);
		}
	}
	
	/**
	 * @Description 维修方案审核驳回
	 * @CreateTime 2017年8月21日 下午5:03:15
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:audit:main:01")
	@ResponseBody
	@RequestMapping(value = "/auditreject", method = RequestMethod.POST)
	public void auditReject(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doAuditReject(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("维修方案审核驳回失败!", e);
		}
	}
	
	/**
	 * @Description 维修方案审批通过
	 * @CreateTime 2017年8月21日 下午5:04:00
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:approval:main:01")
	@ResponseBody
	@RequestMapping(value = "/approveagree", method = RequestMethod.POST)
	public void approveAgree(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doApproveAgree(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("维修方案审批通过失败!", e);
		}
	}
	
	/**
	 * @Description 维修方案审批驳回
	 * @CreateTime 2017年8月21日 下午5:04:00
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:approval:main:01")
	@ResponseBody
	@RequestMapping(value = "/approvereject", method = RequestMethod.POST)
	public void approveReject(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doApproveReject(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("维修方案审批驳回失败!", e);
		}
	}
	
	/**
	 * @Description 改版维修方案
	 * @CreateTime 2017年8月16日 下午2:30:32
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:08")
	@ResponseBody
	@RequestMapping(value = "/revision", method = RequestMethod.POST)
	public void revision(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doRevision(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("改版维修方案失败!", e);
		}
	}
	
	/**
	 * @Description 提交生产维修方案
	 * @CreateTime 2017年8月16日 下午2:31:45
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:main:08")
	@ResponseBody
	@RequestMapping(value = "/submitproduction", method = RequestMethod.POST)
	public void submitProduction(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doSubmitProduction(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交生产维修方案失败!", e);
		}
	}
	
	/**
	 * @Description 生效维修方案
	 * @CreateTime 2017年8月21日 下午5:05:15
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:maintenanceproject:effect:main:01")
	@ResponseBody
	@RequestMapping(value = "/confirmProduction", method = RequestMethod.POST)
	public void confirmProduction(@RequestBody MaintenanceScheme scheme) throws BusinessException{
		try {
			maintenanceSchemeService.doConfirmProduction(scheme);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生效维修方案失败!", e);
		}
	}
	
	/**
	 * @Description 查询待审核维修方案
	 * @CreateTime 2017年8月22日 上午10:56:02
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "auditlist", method = RequestMethod.POST)
	public List<MaintenanceScheme> auditList(@RequestBody MaintenanceScheme scheme) {
		return maintenanceSchemeService.queryAuditList(scheme);
	}
	
	/**
	 * @Description 查询待批准维修方案
	 * @CreateTime 2017年8月22日 上午10:56:02
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "approvelist", method = RequestMethod.POST)
	public List<MaintenanceScheme> approveList(@RequestBody MaintenanceScheme scheme) {
		return maintenanceSchemeService.queryApproveList(scheme);
	}
	
	/**
	 * @Description 查询待生效维修方案
	 * @CreateTime 2017年8月22日 上午10:56:02
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "effectlist", method = RequestMethod.POST)
	public List<MaintenanceScheme> effectList(@RequestBody MaintenanceScheme scheme) {
		return maintenanceSchemeService.queryEffectList(scheme);
	}
	
	/**
	 * @Description 跳转到维修方案查看差异
	 * @CreateTime 2017年8月16日 下午2:28:28
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/different", method = RequestMethod.GET)
	public ModelAndView viewProjectDifferent(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_project_different", responseParamMap);
	}
	
	/**
	 * @Description 跳转到维修方案查看
	 * @CreateTime 2017年8月16日 下午2:28:35
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewProject(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/maintenance/maintenance_project_view", responseParamMap);
	}
	
	/**
	 * @Description id查询维修方案详情
	 * @CreateTime 2017年8月28日 下午3:05:11
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public MaintenanceScheme queryDetail(@RequestBody MaintenanceScheme scheme) throws BusinessException {
		try {
			return maintenanceSchemeService.queryDetail(scheme.getId());
		} catch (Exception e) {
			throw new BusinessException("id查询维修方案详情失败!", e);
		}
	}
	
	/**
	 * @Description 查询差异数据
	 * @CreateTime 2017年8月28日 下午3:30:53
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDifferenceData", method = RequestMethod.POST)
	public Map<String, MaintenanceScheme> queryDifferenceData(
			@RequestBody MaintenanceScheme scheme) throws BusinessException {
		try {
			return maintenanceSchemeService.queryDifferenceData(scheme);
		} catch (Exception e) {
			throw new BusinessException("查询差异数据失败!", e);
		}
	}
	
	/**
	 * @Description 查询维修方案版本历史版本
	 * @CreateTime 2017年8月30日 上午9:18:13
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "versionlist",method={RequestMethod.POST,RequestMethod.GET})
	public List<MaintenanceScheme> queryVersionList(@RequestBody MaintenanceScheme scheme) throws BusinessException {
		try {
			return maintenanceSchemeService.queryVersionList(scheme);
		} catch (Exception e) {
			throw new BusinessException("查询维修方案版本历史版本失败!",e);
		}
	}
}



