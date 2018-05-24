package com.eray.thjw.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.User;
import com.eray.thjw.service.JobCardService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.JobCardStatusEnum;

@Controller
@RequestMapping("/project/jobCard")
public class JobCardController extends BaseController{

	@Autowired 
	private JobCardService jobCardService;
	
	/**
	 * 跳转至定检工卡
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="project:jobCard:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Annunciate annunciate) {
		return new ModelAndView("project/maintenance/jobCardMain");
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 页面视图project/maintenance/jobCard/jobCardMain.jsp
	 * @throws BusinessException 
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryJobCardList", method = RequestMethod.POST)
	public Object queryJobCardList(@RequestBody JobCard jobCard,HttpServletRequest request,Model model) throws BusinessException{
			return jobCardService.queryAllJobCard(jobCard);
	}
	/**
	 * @author 根据工卡编号查询数据
	 * @description 
	 * @param request,model
	 * @return 页面视图project/maintenance/jobCard/jobCardMain.jsp
	 * @develop date 2016.08.05
	 */
	@RequestMapping("selectByCode")
	public String selectByCode(Model model,String gdbh) {
		model.addAttribute("jobCard",jobCardService.selectByCode(gdbh));
			return "project/maintenance/view_jobCard";
	}
	
	/**
	 * 初始化增加定检工卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping("intoAddJobCard")
	@Privilege(code="project:jobCard:main:01")
	public ModelAndView intoAddJobCard(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		User user=ThreadVarUtil.getUser();
		try {
			String id=request.getParameter("id");
			if(id!=null && !"".equals(id)){
				model.put("djgznrid", id);
			}else{
				model.put("djgznrid", 1);
			}
			
			model.put("workgroup", SysContext.WORKGROUP_MUST);
		} catch (Exception e) {
			throw new BusinessException("跳转失败!",e);
		}
		return new ModelAndView("project/maintenance/add_jobCard", model);
	}
	/**
	 * 保存提交技术文件
	 * 
	 * @return 
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "saveMainJobCard", method = RequestMethod.POST)
	public String saveMainJobCard(@RequestBody JobCard jobCard,HttpServletRequest request) throws BusinessException{
		try {
			String id=jobCardService.insertJobCard(jobCard);
			return id;
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "validationJobCardBH", method = RequestMethod.POST)
	public Object validationJobCardBH(@RequestParam String gdbh,@RequestParam String oldgdbh,@RequestParam String dprtcode,HttpServletRequest request) throws BusinessException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
		JobCard jobCard=new JobCard();
		jobCard.setGdbh(gdbh);
		jobCard.setOldgdbh(oldgdbh);
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		jobCard.setDprtcode(dprtcode);
		jobCard.getParamsMap().put("userId", user.getId());
		jobCard.getParamsMap().put("userType", user.getUserType());
		int i =jobCardService.queryCount(jobCard);
		resultMap.put("total", i);
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 检查定检工卡修改权限
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,@RequestParam String id
			) throws BusinessException {
		try {
		return jobCardService.checkUpdMt(SessionUtil.getUserFromSession(request),id);
	} catch (Exception e) {
		throw new BusinessException("操作失败",e);
	}
		
	}
	/**
	 * @author sunji
	 * @description 初始化定检工卡修改
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoEditMainJobCard")
	@Privilege(code="project:jobCard:main:02")
	//@Privilege(code = "thjw_person_add" , type = PrivilegeTypeEnum.BUTTON)
	public String intoEditMainJobCard(Model model,@RequestParam String id,@RequestParam String dprtcode
			) {
		JobCard jobCard=jobCardService.getByPrimaryKey(id);
		if(jobCard!=null && jobCard.getZt()!=null){
			jobCard.setZtText(JobCardStatusEnum.getName(jobCard.getZt()));
		}
		model.addAttribute("jobCard", jobCard);
		model.addAttribute("workgroup", SysContext.WORKGROUP_MUST);
		return "project/maintenance/edit_jobCard_tj";
	}
	
	@ResponseBody
	@RequestMapping("selectJobCardByid")
	public Map<String, Object> selectJobCardByid(HttpServletRequest request,@RequestParam String id
			) throws BusinessException {
			try {
				Map<String,Object> map=new HashMap<String, Object>();
				JobCard jobCard=jobCardService.getByPrimaryKey(id);
				map.put("jobCard", jobCard);
				map.put("workgroup", SysContext.WORKGROUP_MUST);
				return map;
			} catch (Exception e) {
				throw new BusinessException("查询失败",e);
			}
		
	}
	
	/**
	 * @author sunji
	 * @description  修改保存状态的信息
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "modifyJobCard", method = RequestMethod.POST)
	public String modifyAnnunciate(@RequestBody JobCard jobCard,HttpServletRequest request) throws BusinessException{
		try {
			jobCardService.modifyJobCard(jobCard);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return jobCard.getId();
		
	}
	
	
	
	
	/**
	 * @author sunji
	 * @description 初始化定检工卡（查看）
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoViewMainJobCard")
	public String intoViewMainJobCard(Model model,@RequestParam String id,@RequestParam String dprtCode
			) {
		model.addAttribute("jobCard", jobCardService.getByPrimaryKey(id));
			return "project/maintenance/view_jobCard";
	}
	
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("deleteJobCard")
	@Privilege(code="project:jobCard:main:03")
	public Map<String, Object> deleteJobCard(HttpServletRequest request,String id
			) throws Exception {
		Map<String, Object> resultMap = jobCardService.deleteJobCard(id);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 关闭(指定结束)
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("offJobCard")
	@Privilege(code="project:jobCard:main:04")
	public Map<String, Object> offJobCard(@RequestBody JobCard jobCard, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = jobCardService.offJobCard(jobCard);
		return resultMap;
	}
	
	
	
	/**
	 * @author sunji
	 * @description 初始化定检工卡（审核）
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoShenheMainJobCard")
	@Privilege(code="project:jobCard:main:05")
	public String intoShenheMainJobCard(Model model,@RequestParam String id,@RequestParam String dprtcode) {
		model.addAttribute("jobCard", jobCardService.getByPrimaryKey(id));
			return "project/maintenance/shenhe_jobCard";
	}
	/**
	 * @author sunji
	 * @description 初始化定检工卡（批复）
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoPifuMainJobCard")
	@Privilege(code="project:jobCard:main:06")
	public String intoPifuMainJobCard(Model model,@RequestParam String id,@RequestParam String dprtcode) {
		model.addAttribute("jobCard", jobCardService.getByPrimaryKey(id));
			return "project/maintenance/pifu_jobCard";
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（审核提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "submitshenheMainJobCard", method = RequestMethod.POST)
	public String submitshenheMainJobCard(@RequestBody JobCard jobCard,HttpServletRequest request) throws BusinessException{
		try {
			jobCardService.updateshenheJobCard(jobCard);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return jobCard.getId();
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（批复提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "submitpifuMainJobCard", method = RequestMethod.POST)
	public String submitpifuMainJobCard(@RequestBody JobCard jobCard,HttpServletRequest request) throws BusinessException{
		try {
			jobCardService.updatepifuJobCard(jobCard);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return jobCard.getId();
	}
	/**
	 * @author sunji
	 * @description 批量审核
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:jobCard:main:05")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return jobCardService.updateBatchAudit(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核失败",e);
		}
	}
	/**
	 * @author liub
	 * @description 批量批准
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:jobCard:main:06")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		
		try {
			return jobCardService.updateBatchApprove(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量批准失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 更加定检工作内容id查询定检信息
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("selectBydjgznrid")
	public Map<String, Object> selectBydjgznrid(HttpServletRequest request,@RequestParam String id
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JobCard jobCard=jobCardService.selectBydjgznrid(id);
		if(jobCard==null){
			throw new BusinessException("查询失败");
		}
		resultMap.put("jobCard", jobCard);
		return resultMap;
	}
	
	/**
	 * @author liub
	 * @description 根据飞机机型查询工卡(弹窗)
	 * @param jobCard
	 * @return Map<String, Object> 
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinByFjjx", method = RequestMethod.POST)
	public Map<String, Object> queryWinByFjjx(@RequestBody JobCard jobCard) throws BusinessException{
		try {
			PageHelper.startPage(jobCard.getPagination());
			List<JobCard> list = jobCardService.queryWinByFjjx(jobCard);
			return PageUtil.pack4PageHelper(list, jobCard.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询工卡失败!",e);
		}
	}
	
}
