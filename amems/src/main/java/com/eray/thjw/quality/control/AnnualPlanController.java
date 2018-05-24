package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.AnnualAuditPlan;
import com.eray.thjw.quality.po.AnnualPlan;
import com.eray.thjw.quality.service.AnnualAuditPlanService;
import com.eray.thjw.quality.service.AnnualPlanService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.quality.AnnualPlanStatusEnum;
/**
 * 
 * @Description 年度计划
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/annualplan")
public class AnnualPlanController extends BaseController {
	
	@Resource
	private AnnualPlanService annualPlanService;
	@Resource
	private AnnualAuditPlanService annualAuditPlanService;
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * @Description 年度计划
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 * @UpdateBy 林龙 
	 * @UpdateTime 2018年1月4日 上午11:15:10
	 */
	@Privilege(code="quality:annualplan:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("/quality/annualplan/annual_plan",model);
		} catch (Exception e) {
			 throw new BusinessException("年度计划跳转失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 获取最新版本相关数据
	 * @CreateTime 2018年1月4日 下午1:38:57
	 * @CreateBy 林龙
	 * @param dprtcode
	 * @param nf
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getLatestVersionList", method = RequestMethod.POST)
	public List<AnnualPlan> getLatestVersionList(String dprtcode,String nf)throws BusinessException {
		try {
			return annualPlanService.getLatestVersionList(nf,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取最新版本相关数据失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 获取最新版本相关数据
	 * @CreateTime 2018年1月4日 下午1:38:57
	 * @CreateBy 林龙
	 * @param dprtcode
	 * @param nf
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getLatestVersion", method = RequestMethod.POST)
	public AnnualPlan getLatestVersion(String dprtcode,String nf,String bbh)throws BusinessException {
		try {
			return annualPlanService.getLatestVersion(nf,dprtcode,bbh);
		} catch (Exception e) {
			throw new BusinessException("获取最新版本相关数据失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 保存年度计划
	 * @CreateTime 2018年1月8日 上午10:04:19
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:01")
	@ResponseBody
	@RequestMapping(value = "plan/save", method = RequestMethod.POST)
	public String save(@RequestBody AnnualPlan annualPlan) throws BusinessException{
		try {
			return annualPlanService.save(annualPlan);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存年度计划失败!",e);
		}
	}
	/**
	 * 
	 * @Description 修改年度计划
	 * @CreateTime 2018年1月8日 上午10:04:19
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:02")
	@ResponseBody
	@RequestMapping(value = "plan/update", method = RequestMethod.POST)
	public String update(@RequestBody AnnualPlan annualPlan) throws BusinessException{
		try {
			return annualPlanService.update(annualPlan);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存年度计划失败!",e);
		}
	}
	
	/**
	 * @Description 提交年度计划
	 * @CreateTime 2018-1-19 上午11:01:30
	 * @CreateBy 刘兵
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:03")
	@ResponseBody
	@RequestMapping(value = "plan/doSubmit", method = RequestMethod.POST)
	public String doSubmit(@RequestBody AnnualPlan annualPlan) throws BusinessException{
		try {
			return annualPlanService.doSubmit(annualPlan);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交年度计划失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 年度审核计划列表
	 * @CreateTime 2018年1月8日 下午2:16:13
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody AnnualAuditPlan annualAuditPlan,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = annualAuditPlanService.queryAllPageList(annualAuditPlan);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("年度审核计划列表加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 新增年度审核计划
	 * @CreateTime 2018年1月8日 上午10:04:19
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	//@Privilege(code="quality:annualplan:main:01")
	@ResponseBody
	@RequestMapping(value = "audit/save", method = RequestMethod.POST)
	public String saveAudit(@RequestBody AnnualAuditPlan annualAuditPlan) throws BusinessException{
		try {
			return annualAuditPlanService.save(annualAuditPlan);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("新增年度审核计划失败!",e);
		}
	}

	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018年1月9日 下午2:14:04
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public AnnualAuditPlan getById(@RequestBody AnnualAuditPlan annualAuditPlan)throws BusinessException {
		try {
			return annualAuditPlanService.getById(annualAuditPlan);
		} catch (Exception e) {
			throw new BusinessException("查询年度审核计划失败!",e);
		}
	}
	
	/**
	 * @Description 根据年度计划id查询年度计划
	 * @CreateTime 2018-4-27 上午11:04:25
	 * @CreateBy 刘兵
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getInfoById",method={RequestMethod.POST,RequestMethod.GET})
	public AnnualPlan selectById(String id) throws BusinessException {
		try {
			return annualPlanService.getInfoById(id);
		} catch (Exception e) {
			throw new BusinessException("查询年度计划失败!",e);
		}
	}
	
	
	/**
	 * 
	 * @Description 修改年度审核计划
	 * @CreateTime 2018年1月9日 下午2:35:23
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:02")
	@ResponseBody
	@RequestMapping(value = "audit/update", method = RequestMethod.POST)
	public String update(@RequestBody AnnualAuditPlan annualAuditPlan) throws BusinessException{
		try {
			return annualAuditPlanService.update(annualAuditPlan);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改年度审核计划失败!",e);
		}
	}
	
	
	/**
	 * 
	 * @Description 删除年度审核计划
	 * @CreateTime 2018年1月9日 下午4:15:15
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	@Privilege(code="produce:reservation:defect:main:03")
	@ResponseBody
	@RequestMapping(value = "audit/delete", method = RequestMethod.POST)
	public String delete(@RequestBody AnnualAuditPlan annualAuditPlan) throws BusinessException{
		try {
			return annualAuditPlanService.delete(annualAuditPlan);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除年度审核计划失败!",e);
		}
	}
	
	/**
	 * @Description 审核年度计划
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:audit")
	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public ModelAndView audit(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
	    return new ModelAndView("/quality/annualplan/annual_plan_audit",model);
	
	}
	/**
	 * @Description 批准年度计划
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:approval")
	@RequestMapping(value = "approval", method = RequestMethod.GET)
	public ModelAndView approval(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
	    return new ModelAndView("/quality/annualplan/annual_plan_approval",model);
	}
	
	/**
	 * @Description 查询变更记录
	 * @CreateTime 2018年1月11日 下午3:06:05
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "changerecord", method = RequestMethod.POST)
	public List<AnnualPlan> queryChangeRecord(@RequestBody AnnualPlan record)throws BusinessException{
		try {
			return annualPlanService.queryChangeRecord(record);
		} catch (Exception e) {
			throw new BusinessException("查询变更记录失败！", e);
		}
	}
	
	/**
	 * @Description 根据状态，组织机构获取数据
	 * @CreateTime 2018年1月11日 下午3:06:05
	 * @CreateBy sunji
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllByZt", method = RequestMethod.POST)
	public List<AnnualPlan> queryAllByZt(@RequestBody AnnualPlan record)throws BusinessException{
		try {
			return annualPlanService.queryAllByZt(record);
		} catch (Exception e) {
			throw new BusinessException("查询变更记录失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 根据id查询审核附件数据
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("queryFjByNdshId")
	public Map<String, Object> queryFjByNdshId(HttpServletRequest request, String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows",  attachmentService.selectAttachments(id));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 批准驳回
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:08")
	@ResponseBody
	@RequestMapping("approvalBh")
	public void approvalBh(@RequestBody AnnualPlan annualPlan) throws BusinessException {
		try {
			User user = ThreadVarUtil.getUser();		//当前登陆人
			annualPlan.setPfrid(user.getId());
			annualPlanService.updateApprovalAndAudit(annualPlan,AnnualPlanStatusEnum.APPROVALDOWN.getId(),AnnualPlanStatusEnum.AUDIT.getId());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 批准通过
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:08")
	@ResponseBody
	@RequestMapping("approval")
	public void approval(@RequestBody AnnualPlan annualPlan) throws BusinessException {
		try {
			User user = ThreadVarUtil.getUser();		//当前登陆人
			annualPlan.setPfrid(user.getId());
			annualPlanService.updateApprovalAndAudit(annualPlan,AnnualPlanStatusEnum.APPROVAL.getId(),AnnualPlanStatusEnum.AUDIT.getId());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 审核通过
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:06")
	@ResponseBody
	@RequestMapping("audit")
	public void audit(@RequestBody AnnualPlan annualPlan) throws BusinessException {
		try {
			User user = ThreadVarUtil.getUser();		//当前登陆人
			annualPlan.setShrid(user.getId());
			annualPlanService.updateApprovalAndAudit(annualPlan,AnnualPlanStatusEnum.AUDIT.getId(),AnnualPlanStatusEnum.ASSESSMENT.getId());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 审核驳回
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:06")
	@ResponseBody
	@RequestMapping("auditBh")
	public void auditBh(@RequestBody AnnualPlan annualPlan) throws BusinessException {
		try {
			User user = ThreadVarUtil.getUser();		//当前登陆人
			annualPlan.setShrid(user.getId());
			annualPlanService.updateApprovalAndAudit(annualPlan,AnnualPlanStatusEnum.AUDITDOWN.getId(),AnnualPlanStatusEnum.ASSESSMENT.getId());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败！",e);
		}
		
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramjson 当前参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@Privilege(code="quality:annualplan:main:05")
	@RequestMapping(value = "annualPlan.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    AnnualAuditPlan annualAuditPlan = JSON.parseObject(paramjson, AnnualAuditPlan.class);
			List<AnnualAuditPlan> list = annualAuditPlanService.doExportExcel(annualAuditPlan);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "annualplan", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}

}
