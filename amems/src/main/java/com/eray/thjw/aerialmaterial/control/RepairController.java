package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.service.RepairService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.SessionUtil;

import enu.EnquiryStatusEnum;
import enu.MaterialProgressEnum;
import enu.ReserveStatusEnum;
import enu.UrgencyEnum;

/**
 * @author liub
 * @description 航材送修管理控制层
 * @develop date 2016.10.26
 */
@Controller
@RequestMapping("/aerialmaterial/repair")
public class RepairController extends BaseController {
	
	/**
	 * @author liub
	 * @description 航材送修管理service
	 * @develop date 2016.10.12
	 */
	@Autowired
	private RepairService repairService;
	
	/**
	 * @author liub
	 * @description 跳转至航材送修申请界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.26
	 *
	 */
	@Privilege(code="aerialmaterial:repair:manage")
	@RequestMapping(value = "manage", method = RequestMethod.GET)
	public String manage(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		model.addAttribute("materialProgressEnum", MaterialProgressEnum.enumToListMap());
		User user = SessionUtil.getUserFromSession(request);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("type", "manage");
		return "material/repair/repair_main";
	}
	
	/**
	 * @author liub
	 * @description 跳转至航材送修确认界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.26
	 *
	 */
	@Privilege(code="aerialmaterial:repair:approve")
	@RequestMapping(value = "approve", method = RequestMethod.GET)
	public String approve(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		model.addAttribute("materialProgressEnum", MaterialProgressEnum.enumToListMap());
		model.addAttribute("type", "approve");
		return "material/repair/repair_main";
	}
	
	/**
	 * @author liub
	 * @description 查看送修单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.04
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		getLogger().info("初始化查看送修单  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("reserveMain", repairService.selectMapById(id));
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化查看送修单失败!",e);
		}
		return new ModelAndView("material/repair/repair_view");
	}
	
	/**
	 * @author liub
	 * @description 初始化增加送修单
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.10.26
	 *
	 */
	@Privilege(code="aerialmaterial:repair:manage:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("urgencyEnum", UrgencyEnum.enumToListMap());
		return new ModelAndView("material/repair/repair_add", model);
	}
	
	/**
	 * @author liub
	 * @description 保存送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:manage:03")
	@ResponseBody
	@RequestMapping(value = "addSave", method = RequestMethod.POST)
	public String add(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("新增送修单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.SAVE.getId());
			return repairService.add(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 提交送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:manage:04")
	@ResponseBody
	@RequestMapping(value = "addSubmit", method = RequestMethod.POST)
	public String addSubmit(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("提交送修单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.SUBMIT.getId());
			return repairService.add(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查送修单编辑权限
	 * @param request,id
	 * @return
	 * @develop date 2016.11.03
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(HttpServletRequest request,String id) throws BusinessException {
		getLogger().info("检查送修单编辑权限 前端参数:id:{}", new Object[]{id});
		try {
			repairService.checkEdit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化编辑送修单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.01
	 *
	 */
	@Privilege(code="aerialmaterial:repair:manage:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id) throws BusinessException{
		getLogger().info("初始化编辑送修单  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("reserveMain", repairService.selectMapById(id));
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化编辑送修单失败!");
		}
		return new ModelAndView("material/repair/repair_edit");
	}
	

	/**
	 * @author liub
	 * @description 编辑后保存送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:manage:03")
	@ResponseBody
	@RequestMapping(value = "editSave", method = RequestMethod.POST)
	public void editSave(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("编辑后保存送修单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			repairService.edit(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 编辑后提交repair
	 * @param reserve
	 * @develop date 2016.11.02
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:manage:04")
	@ResponseBody
	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public void editSubmit(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("编辑后提交送修单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.SUBMIT.getId());
			repairService.edit(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核通过,修改询价状态为待询价
	 * @param materialRepair
	 * @develop date 2016.12.21
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:approve:01")
	@ResponseBody
	@RequestMapping(value = "updateEquiryStatus", method = RequestMethod.POST)
	public void updateEquiryStatus(@RequestBody MaterialRepair materialRepair) throws BusinessException{
		getLogger().info("修改询价状态  前端参数:materialRepair:{}", new Object[]{materialRepair});
		try {
			materialRepair.setXjzt(EnquiryStatusEnum.WAIT_ENQUIRY.getId());
			repairService.updateMaterialRepair(materialRepair);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查送修单审核权限
	 * @param request,id
	 * @return
	 * @develop date 2016.11.02
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkAudit", method = RequestMethod.POST)
	public void checkAudit(HttpServletRequest request,String id) throws BusinessException {
		getLogger().info("检查送修单审核权限 前端参数:id:{}", new Object[]{id});
		try {
			repairService.checkAudit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化审核送修单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.02
	 *
	 */
	@Privilege(code="aerialmaterial:repair:approve:01")
	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public ModelAndView audit(Model model,String id) throws BusinessException{
		getLogger().info("初始化审核送修单  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("reserveMain", repairService.selectMapById(id));
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化审核送修单失败!",e);
		}
		return new ModelAndView("material/repair/repair_audit");
	}
	
	/**
	 * @author liub
	 * @description 审核后保存送修单
	 * @param reserve
	 * @develop date 2016.11.02
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:approve:05")
	@ResponseBody
	@RequestMapping(value = "auditSave", method = RequestMethod.POST)
	public void auditSave(@RequestBody ReserveMain reserve) throws BusinessException {
		getLogger().info("审核后保存提订单  前端参数:reserve:{}", new Object[] { reserve });
		try {
			repairService.editAudit(reserve);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("审核送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核后提交送修单
	 * @param reserve
	 * @develop date 2016.11.02
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:repair:approve:06")
	@ResponseBody
	@RequestMapping(value = "auditSubmit", method = RequestMethod.POST)
	public void auditSubmit(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("审核后提交提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.AUDIT_PASS.getId());
			repairService.editAudit(reserve);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("审核送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,id
	 * @return
	 * @develop date 2016.11.03
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:repair:manage:05")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		getLogger().info("作废操作  前端参数:id{}", new Object[]{id});
		try {
			repairService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废送修单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param reserve
	 * @return
	 * @develop date 2016.11.03
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:repair:approve:07")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("指定结束  前端参数:id{}", new Object[]{reserve});
		try {
			repairService.updateShutDown(reserve);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束失败!",e);
		}
	}
	
}
